package br.gov.cesarschool.poo.fidelidade.cartao.entidade;

import java.time.LocalDateTime;

public class LancamentoExtratoPontuacao extends LancamentoExtrato {

    public LancamentoExtratoPontuacao(long numeroCartao, int quantidadePontos, LocalDateTime dataHoraLancamento) {
        super(numeroCartao, quantidadePontos, dataHoraLancamento);
    }

    public long getNumeroCartao() {
        return super.getNumeroCartao();
    }

    public int getQuantidadePontos() {
        return super.getQuantidadePontos();
    }

    public LocalDateTime getDataHoraLancamento() {
        return super.getDataHoraLancamento();
    }
}
