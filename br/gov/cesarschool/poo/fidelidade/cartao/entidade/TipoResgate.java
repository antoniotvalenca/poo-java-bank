package br.gov.cesarschool.poo.fidelidade.cartao.entidade;

import java.io.Serializable;

public enum TipoResgate implements Serializable {
	PRODUTO(1, "PRODUTO"),
	SERVICO(2, "SERVICO"),
	VIAGEM(3, "VIAGEM");
	
	private int codigo;
	private String descricao;

	private TipoResgate(int codigo, String descricao) {
	    this.codigo = codigo;
	    this.descricao = descricao;
	}

	public int getCodigo() {
	    return codigo;
	}

	public String getDescricao() {
	    return descricao;
	}
}
