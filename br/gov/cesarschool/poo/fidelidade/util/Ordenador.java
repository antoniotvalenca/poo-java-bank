package br.gov.cesarschool.poo.fidelidade.util;

import br.gov.cesarschool.poo.fidelidade.cliente.entidade.Cliente;

public class Ordenador {

    public static void ordenar(Cliente[] array) {
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (array[j].getNomeCompleto().compareTo(array[j + 1].getNomeCompleto()) > 0) {
                    Cliente temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }
}
