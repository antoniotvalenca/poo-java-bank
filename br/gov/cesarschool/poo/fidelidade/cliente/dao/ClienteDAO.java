package br.gov.cesarschool.poo.fidelidade.cliente.dao;

import br.gov.cesarschool.poo.fidelidade.cliente.entidade.Cliente;
import br.gov.cesarschool.poo.fidelidade.geral.dao.DAOGenerico;
import br.gov.cesarschool.poo.fidelidade.geral.entidade.Identificavel;

public class ClienteDAO {
    private static final String DIR_BASE = "." + System.getProperty("file.separator") + "fidelidade" +
            System.getProperty("file.separator") + "cliente";

    private DAOGenerico<Cliente> daoEncapsulado;

    public ClienteDAO() {
        daoEncapsulado = new DAOGenerico<>(DIR_BASE);
    }

    public boolean incluir(Cliente cliente) {
        return daoEncapsulado.incluir(cliente);
    }

    public boolean alterar(Cliente cliente) {
        return daoEncapsulado.alterar(cliente);
    }

    public Cliente buscar(String cpf) {
        return (Cliente) daoEncapsulado.buscar(cpf);
    }

	public Cliente[] buscarTodos() {
		Identificavel[] identificaveis = daoEncapsulado.buscarTodos();
		Cliente[] clientes = new Cliente[identificaveis.length];
		for (int i = 0; i < identificaveis.length; i++) {
			clientes[i] = (Cliente) identificaveis[i];
		}
		return clientes;
	}
	
}
