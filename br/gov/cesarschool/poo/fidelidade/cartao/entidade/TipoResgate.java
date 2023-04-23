package br.gov.cesarschool.poo.fidelidade.cartao.entidade;

public enum TipoResgate {
    PRODUTO(1, "produto"),
    SERVICO(2, "serviço"),
    VIAGEM(3, "viagem");

    private int codigo;
    private String descricao;

    TipoResgate(int codigo, String descricao) {
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
