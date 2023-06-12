package br.gov.cesarschool.poo.fidelidade.cartao.entidade;

import br.gov.cesarschool.poo.fidelidade.geral.entidade.Comparavel;
import br.gov.cesarschool.poo.fidelidade.geral.entidade.Identificavel;

import java.time.LocalDateTime;

public abstract class LancamentoExtrato extends Identificavel implements Comparavel {
    private String numeroCartao;
    private double quantidadePontos;
    private LocalDateTime dataHoraLancamento;

    public LancamentoExtrato(String numeroCartao, double quantidadePontos, LocalDateTime dataHoraLancamento) {
        this.numeroCartao = numeroCartao;
        this.quantidadePontos = quantidadePontos;
        this.dataHoraLancamento = dataHoraLancamento;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public double getQuantidadePontos() {
        return quantidadePontos;
    }

    public LocalDateTime getDataHoraLancamento() {
        return dataHoraLancamento;
    }

    @Override
    public String obterChave() {
        return numeroCartao; // Utilizando o número do cartão como chave de identificação
    }

    @Override
    public int comparar(Comparavel outro) {
        if (outro instanceof LancamentoExtrato) {
            LancamentoExtrato outroLancamento = (LancamentoExtrato) outro;
            return outroLancamento.dataHoraLancamento.compareTo(this.dataHoraLancamento);
        }
        return 0;
    }

    public abstract String getIdentificadorTipo();
}
