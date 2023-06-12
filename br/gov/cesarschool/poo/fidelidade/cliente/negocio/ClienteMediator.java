package br.gov.cesarschool.poo.fidelidade.cliente.negocio;

import br.gov.cesarschool.poo.fidelidade.util.Ordenador;
import br.gov.cesarschool.poo.fidelidade.cliente.dao.ClienteDAO;
import br.gov.cesarschool.poo.fidelidade.cliente.entidade.Cliente;
import br.gov.cesarschool.poo.fidelidade.cartao.negocio.CartaoFidelidadeMediator;

public class ClienteMediator {
    private static ClienteMediator instance;
    private ClienteDAO repositorioCliente;
    private CartaoFidelidadeMediator cartaoMediator;

    private ClienteMediator() {
        repositorioCliente = new ClienteDAO();
        cartaoMediator = CartaoFidelidadeMediator.getInstance();
    }

    public static ClienteMediator getInstance() {
        if (instance == null) {
            instance = new ClienteMediator();
        }
        return instance;
    }

    public ResultadoInclusaoCliente incluir(Cliente cliente) {
        String numeroCartao = "0";
        boolean res = repositorioCliente.incluir(cliente);
        if (res) {
            numeroCartao = cartaoMediator.gerarCartao(cliente);
        }
        String msgErro = res ? null : "Erro ao incluir cliente no repositório";
        return new ResultadoInclusaoCliente(numeroCartao, msgErro);
    }

    public String alterar(Cliente cliente) {
        boolean res = repositorioCliente.alterar(cliente);
        String msgErro = res ? null : "Erro ao alterar cliente no repositório";
        return msgErro;
    }

    public Cliente buscarCliente(String cpf) {
        return repositorioCliente.buscar(cpf);
    }

    public Cliente[] consultarClientesOrdenadosPorNome() {
        Cliente[] clientes = repositorioCliente.buscarTodos();
        Ordenador.ordenar(clientes);
        return clientes;
    }
}
