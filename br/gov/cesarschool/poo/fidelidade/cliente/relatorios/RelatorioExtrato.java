package br.gov.cesarschool.poo.fidelidade.cliente.relatorios;

import br.gov.cesarschool.poo.fidelidade.cartao.entidade.LancamentoExtrato;
import br.gov.cesarschool.poo.fidelidade.cartao.entidade.RetornoConsultaExtrato;
import br.gov.cesarschool.poo.fidelidade.cartao.negocio.CartaoFidelidadeMediator;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RelatorioExtrato {

    public static void gerarRelatorioExtratos(String numeroCartao, LocalDateTime inicio, LocalDateTime fim) {
        CartaoFidelidadeMediator cartaoMediator = CartaoFidelidadeMediator.getInstance();

        RetornoConsultaExtrato retorno = cartaoMediator.consultaEntreDatas(numeroCartao, inicio, fim);
        if (retorno.getMensagemErro() != null) {
            System.out.println(retorno.getMensagemErro());
            return;
        }

        LancamentoExtrato[] lancamentos = retorno.getLancamentos();
        for (LancamentoExtrato lancamento : lancamentos) {
            String dataHora = formatarDataHora(lancamento.getDataHoraLancamento());
            String valor = formatarValor(lancamento.getQuantidadePontos());
            String tipo = (lancamento.getQuantidadePontos() > 0) ? "R" : "P";
            System.out.println(dataHora + " - " + valor + " - " + tipo);
        }
    }

    private static String formatarDataHora(LocalDateTime dataHora) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return dataHora.format(formatter);
    }

    private static String formatarValor(double valor) {
        DecimalFormat decimalFormat = new DecimalFormat("##,###.00");
        return decimalFormat.format(valor);
    }

    public static void main(String[] args) {
        // Reading input from the console
        // Replace the following lines with your logic to read the input from the console
        String numeroCartao = "1234567890";
        LocalDateTime inicio = LocalDateTime.now().minusDays(7);
        LocalDateTime fim = LocalDateTime.now();

        gerarRelatorioExtratos(numeroCartao, inicio, fim);
    }
}
