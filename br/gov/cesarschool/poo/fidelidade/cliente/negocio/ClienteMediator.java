package br.gov.cesarschool.poo.fidelidade.cliente.negocio;

import br.gov.cesarschool.poo.fidelidade.cliente.negocio.ResultadoInclusaoCliente;
import br.gov.cesarschool.poo.fidelidade.cartao.negocio.CartaoFidelidadeMediator;
import br.gov.cesarschool.poo.fidelidade.cliente.dao.ClienteDAO;
import br.gov.cesarschool.poo.fidelidade.cliente.entidade.Cliente;
import br.gov.cesarschool.poo.fidelidade.geral.entidade.Endereco;
import br.gov.cesarschool.poo.fidelidade.util.StringUtil;
import br.gov.cesarschool.poo.fidelidade.util.ValidadorCPF;
import java.util.Date;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;

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
        ResultadoInclusaoCliente resultado = null;
    
        String validacao = validar(cliente);
        if (validacao == null) {
            boolean incluido = repositorioCliente.incluir(cliente);
            if (incluido) {
                long numeroCartao = cartaoMediator.gerarCartao(cliente);
                resultado = new ResultadoInclusaoCliente(numeroCartao, null);
            } else {
                resultado = new ResultadoInclusaoCliente(0, "Erro ao incluir o cliente no repositório.");
            }
        } else {
            resultado = new ResultadoInclusaoCliente(0, validacao);
        }
    
        return resultado;
    }
    

    public String alterar(Cliente cliente) {
        String mensagemErro = validar(cliente);
        if (mensagemErro == null) {
            Cliente clienteExistente = repositorioCliente.buscar(cliente.getCpf());
            if (clienteExistente != null) {
                clienteExistente.setNomeCompleto(cliente.getNomeCompleto());
                clienteExistente.setSexo(cliente.getSexo());
                clienteExistente.setDataNascimento(cliente.getDataNascimento());
                clienteExistente.setRenda(cliente.getRenda());
                clienteExistente.setEndereco(cliente.getEndereco());
                repositorioCliente.alterar(clienteExistente);
                return null;
            } else {
                return "Cliente não encontrado no repositório.";
            }
        } else {
            return mensagemErro;
        }
    }

    private String validar(Cliente cliente) {
        String cpf = cliente.getCpf();
        if (StringUtil.ehNuloOuBranco(cpf) || !ValidadorCPF.ehCpfValido(cpf)) {
            return "CPF inválido.";
        }

    if (StringUtil.ehNuloOuBranco(cliente.getNomeCompleto())) {
        return "Nome completo é obrigatório.";
    }

    if (cliente.getSexo() == null) {
        return "Sexo é obrigatório.";
    }

    Date dataNascimentoCliente = cliente.getDataNascimento();
    LocalDate dataNascimento = dataNascimentoCliente.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    LocalDate dataAtual = LocalDate.now();
    int idade = Period.between(dataNascimento, dataAtual).getYears();
    
    if (idade < 18) {
        System.out.println("O cliente é menor de idade.");
    } else {
        System.out.println("O cliente é maior de idade.");
    }

    if (cliente.getRenda() < 0) {
        return "Renda deve ser maior ou igual a zero.";
    }

    Endereco endereco = cliente.getEndereco();
    if (endereco == null) {
        return "Endereço é obrigatório.";
    }

    if (StringUtil.ehNuloOuBranco(endereco.getLogradouro()) || endereco.getLogradouro().length() < 4) {
        return "Logradouro é obrigatório e deve ter pelo menos 4 caracteres.";
    }

    if (endereco.getNumero() < 0) {
        return "Número do endereço deve ser maior ou igual a zero.";
    }

    if (StringUtil.ehNuloOuBranco(endereco.getCidade())) {
        return "Cidade é obrigatória.";
    }

    if (StringUtil.ehNuloOuBranco(endereco.getEstado())) {
        return "Estado é obrigatório.";
    }

    if (StringUtil.ehNuloOuBranco(endereco.getPais())) {
        return "País é obrigatório.";
    }

    return null;

    }
}
