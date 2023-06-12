package br.gov.cesarschool.poo.fidelidade.cartao.negocio;

import br.gov.cesarschool.poo.fidelidade.cartao.dao.CartaoFidelidadeDAO;
import br.gov.cesarschool.poo.fidelidade.cartao.dao.LancamentoExtratoDAO;
import br.gov.cesarschool.poo.fidelidade.cartao.entidade.CartaoFidelidade;
import br.gov.cesarschool.poo.fidelidade.cartao.entidade.LancamentoExtrato;
import br.gov.cesarschool.poo.fidelidade.cartao.entidade.LancamentoExtratoPontuacao;
import br.gov.cesarschool.poo.fidelidade.cartao.entidade.LancamentoExtratoResgate;
import br.gov.cesarschool.poo.fidelidade.cartao.entidade.RetornoConsultaExtrato;
import br.gov.cesarschool.poo.fidelidade.cartao.entidade.TipoResgate;
import br.gov.cesarschool.poo.fidelidade.cliente.entidade.Cliente;
import br.gov.cesarschool.poo.fidelidade.util.ValidadorCPF;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;

public class CartaoFidelidadeMediator {

    private static final String ERRO_AO_INCLUIR_LANCAMENTO = "Erro ao incluir lançamento";
    private static final String ERRO_NA_ALTERACAO_DO_CARTAO = "Erro na alteração do cartão";
    private static final String CARTAO_INEXISTENTE = "Cartão não existe";
    private static final String QUANTIDADE_DE_PONTOS_MENOR_QUE_ZERO = "Quantidade de pontos menor que zero";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");

    private static CartaoFidelidadeMediator instance;

    private CartaoFidelidadeDAO repositorioCartao;
    private LancamentoExtratoDAO repositorioLancamento;

    private CartaoFidelidadeMediator() {
        repositorioCartao = new CartaoFidelidadeDAO();
        repositorioLancamento = new LancamentoExtratoDAO();
    }

    public static CartaoFidelidadeMediator getInstance() {
        if (instance == null) {
            instance = new CartaoFidelidadeMediator();
        }
        return instance;
    }

    public String gerarCartao(Cliente cliente) {
        String cpf = cliente.getCpf();
        if (!ValidadorCPF.ehCpfValido(cpf)) {
            return "";
        }

        String cpfSemDigitosVerificadores = cpf.substring(0, cpf.length() - 2);
        String cartao = cpfSemDigitosVerificadores + DATE_FORMAT.format(new Date());
        CartaoFidelidade cardinhu = new CartaoFidelidade(cartao);
        repositorioCartao.incluir(cardinhu);
        return cartao;
    }

    private String processarAlteracaoCartaoInclusaoLancamento(CartaoFidelidade cardinhu, int qtdPontos, TipoResgate tipo) {
        boolean res = repositorioCartao.alterar(cardinhu);
        if (!res) {
            return ERRO_NA_ALTERACAO_DO_CARTAO;
        }
        LocalDateTime now = LocalDateTime.now();
        LancamentoExtrato extrato;
       
        if (tipo == null) {
            extrato = new LancamentoExtratoPontuacao(String.valueOf(cardinhu.getNumeroFidelidade()), qtdPontos, now);
        } else {
            extrato = new LancamentoExtratoResgate(String.valueOf(cardinhu.getNumeroFidelidade()), qtdPontos, now, tipo);
        }
        res = repositorioLancamento.incluir(extrato);
        if (!res) {
            return ERRO_AO_INCLUIR_LANCAMENTO;
        }
        return null;
    }

    public String pontuar(String numeroCartao, int qtdPontos) {
        if (qtdPontos <= 0) {
            return QUANTIDADE_DE_PONTOS_MENOR_QUE_ZERO;
        }
        CartaoFidelidade cardinhu = repositorioCartao.buscar(numeroCartao);
        if (cardinhu == null) {
            return CARTAO_INEXISTENTE;
        }
        cardinhu.creditar(qtdPontos);
        return processarAlteracaoCartaoInclusaoLancamento(cardinhu, qtdPontos, null);
    }

    public String resgatar(String numeroCartao, int qtdPontos, TipoResgate tipo) {
        if (qtdPontos <= 0) {
            return QUANTIDADE_DE_PONTOS_MENOR_QUE_ZERO;
        }
        CartaoFidelidade cardinhu = repositorioCartao.buscar(numeroCartao);
        if (cardinhu == null) {
            return CARTAO_INEXISTENTE;
        }
        if (cardinhu.getSaldo() < qtdPontos) {
            return "Saldo insuficiente para realizar o resgate.";
        }
        cardinhu.debitar(qtdPontos);
        return processarAlteracaoCartaoInclusaoLancamento(cardinhu, qtdPontos, tipo);
    }

    public CartaoFidelidade buscarCartao(String numeroCartao) {
        return repositorioCartao.buscar(numeroCartao);
    }

    public RetornoConsultaExtrato consultaEntreDatas(String numeroCartao, LocalDateTime inicio, LocalDateTime fim) {
        if (ValidadorCPF.ehCpfValido(numeroCartao) && inicio != null && (fim == null || fim.isAfter(inicio))) {
            LancamentoExtrato[] lancamentos = repositorioLancamento.buscarTodos();
            lancamentos = Arrays.stream(lancamentos)
                    .filter(l -> l.getNumeroCartao().equals(numeroCartao))
                    .filter(l -> l.getDataHoraLancamento().isAfter(inicio))
                    .filter(l -> fim == null || l.getDataHoraLancamento().isBefore(fim))
                    .sorted((l1, l2) -> l2.getDataHoraLancamento().compareTo(l1.getDataHoraLancamento()))
                    .toArray(LancamentoExtrato[]::new);

            return new RetornoConsultaExtrato(lancamentos, null);
        } else {
            return new RetornoConsultaExtrato(null, "Parâmetros inválidos para consulta de extrato");
        }
    }
}
