package br.gov.cesarschool.poo.fidelidade.cartao.entidade;

import java.time.LocalDateTime;

public class LancamentoExtratoResgate extends LancamentoExtrato {
    private TipoResgate tipoResgate;

    public LancamentoExtratoResgate(long numeroCartao, int quantidadePontos, LocalDateTime dataHoraLancamento, TipoResgate tipoResgate) {
        super(numeroCartao, quantidadePontos, dataHoraLancamento);
        this.tipoResgate = tipoResgate;
    }

    public TipoResgate getTipoResgate() {
        return tipoResgate;
    }
}