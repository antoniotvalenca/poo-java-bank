package br.gov.cesarschool.poo.fidelidade.cartao.negocio;

import br.gov.cesarschool.poo.fidelidade.cartao.dao.CartaoFidelidadeDAO;
import br.gov.cesarschool.poo.fidelidade.cartao.dao.LancamentoExtratoDAO;
import br.gov.cesarschool.poo.fidelidade.cartao.entidade.CartaoFidelidade;
import br.gov.cesarschool.poo.fidelidade.cliente.entidade.Cliente;
import br.gov.cesarschool.poo.fidelidade.cartao.entidade.LancamentoExtratoPontuacao;
import br.gov.cesarschool.poo.fidelidade.cartao.entidade.TipoResgate;
import br.gov.cesarschool.poo.fidelidade.cartao.entidade.LancamentoExtratoResgate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CartaoFidelidadeMediator {
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

    public long gerarCartao(Cliente cliente) {
        String numeroCartao = cliente.getCpf() + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        long numeroCartaoLong = Long.parseLong(numeroCartao); // Converter String para long
        CartaoFidelidade cartao = new CartaoFidelidade(numeroCartaoLong);
        boolean incluido = repositorioCartao.incluir(cartao);
        if (incluido) {
            return cartao.getNumero();
        } else {
            return 0;
        }
    }
    

    public String pontuar(long numeroCartao, double quantidadePontos) {
        if (quantidadePontos <= 0) {
            return "Quantidade de pontos inválida.";
        }
        CartaoFidelidade cartao = repositorioCartao.buscar(numeroCartao);
        if (cartao == null) {
            return "Cartão não encontrado.";
        }
        cartao.creditar(quantidadePontos);
        repositorioCartao.alterar(cartao);
        LancamentoExtratoPontuacao lancamento = new LancamentoExtratoPontuacao(numeroCartao, (int) quantidadePontos, LocalDateTime.now());
        repositorioLancamento.incluir(lancamento);
        return null;
    }

    public String resgatar(long numeroCartao, double quantidadePontos, TipoResgate tipo) {
        if (quantidadePontos <= 0) {
            return "Quantidade de pontos inválida.";
        }
        CartaoFidelidade cartao = repositorioCartao.buscar(numeroCartao);
        if (cartao == null) {
            return "Cartão não encontrado.";
        }
        if (cartao.getSaldo() < quantidadePontos) {
            return "Saldo insuficiente para resgate.";
        }
        cartao.debitar(quantidadePontos);
        repositorioCartao.alterar(cartao);
        LancamentoExtratoResgate lancamento = new LancamentoExtratoResgate(numeroCartao, (int) quantidadePontos, LocalDateTime.now(), tipo);
        repositorioLancamento.incluir(lancamento);
        return null;
    }
}