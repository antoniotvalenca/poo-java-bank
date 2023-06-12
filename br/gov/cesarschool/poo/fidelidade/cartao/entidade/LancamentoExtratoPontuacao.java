package br.gov.cesarschool.poo.fidelidade.cartao.entidade;

import java.time.LocalDateTime;

public class LancamentoExtratoPontuacao extends LancamentoExtrato {

	public LancamentoExtratoPontuacao(String numeroCartao, double quantidadePontos, LocalDateTime dataHoraLancamento) {
		super(numeroCartao, quantidadePontos, dataHoraLancamento);
	}

	@Override
	public String getIdentificadorTipo() {
		return "P";
	}
}
