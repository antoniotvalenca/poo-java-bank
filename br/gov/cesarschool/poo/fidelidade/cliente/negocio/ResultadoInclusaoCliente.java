package br.gov.cesarschool.poo.fidelidade.cliente.negocio;

public class ResultadoInclusaoCliente {
    private String numeroFidelidade;
    private String mensagemErroValidacao;

    public ResultadoInclusaoCliente(String numeroFidelidade, String mensagemErroValidacao){
        this.numeroFidelidade = numeroFidelidade;
        this.mensagemErroValidacao = mensagemErroValidacao;
    }
	public String getNumeroFidelidade() {
		return numeroFidelidade;
	}
	public String getMensagemErroValidacao() {
		return mensagemErroValidacao;
	}
}
