package br.gov.cesarschool.poo.fidelidade.cartao.entidade;

import br.gov.cesarschool.poo.fidelidade.geral.entidade.Identificavel;

import java.util.Date;

public class CartaoFidelidade extends Identificavel {

	private String numero;
	private double saldo;
	private Date dataHoraAtualizacao = new Date();

	public CartaoFidelidade(String numero) {
		this.numero = numero;
	}

	public String getNumeroFidelidade() {
		return numero;
	}

	public double getSaldo() {
		return saldo;
	}

	public Date getDataHoraAtualizacao() {
		return dataHoraAtualizacao;
	}

	public void creditar(double valor) {
		saldo += valor;
		dataHoraAtualizacao = new Date();
	}

	public void debitar(double valor) {
		saldo -= valor;
		dataHoraAtualizacao = new Date();
	}

	@Override
	public String obterChave() {
		return numero; // Utilizando o número do cartão como chave de identificação
	}
}
