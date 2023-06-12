package br.gov.cesarschool.poo.fidelidade.cliente.relatorios;

import br.gov.cesarschool.poo.fidelidade.cliente.entidade.Cliente;
import br.gov.cesarschool.poo.fidelidade.cliente.negocio.ClienteMediator;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RelatorioCliente {

    public static void gerarRelatorioClientes() {
        ClienteMediator clienteMediator = ClienteMediator.getInstance();
        Cliente[] clientes = clienteMediator.consultarClientesOrdenadosPorNome();

        for (Cliente cliente : clientes) {
            String nome = cliente.getNomeCompleto();
            String dataNascimento = formatarData(cliente.getDataNascimento());
            String renda = formatarRenda(cliente.getRenda());
            System.out.println(nome + " - " + dataNascimento + " - " + renda);
        }
    }

    private static String formatarData(Date data) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(data);
    }

    private static String formatarRenda(double renda) {
        DecimalFormat decimalFormat = new DecimalFormat("##,###.00");
        return decimalFormat.format(renda);
    }

    public static void main(String[] args) {
        gerarRelatorioClientes();
    }
}

