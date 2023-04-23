package br.gov.cesarschool.poo.fidelidade.util;

public class ValidadorCPF {

    private ValidadorCPF() {
    }

    public static boolean ehCpfValido(String cpf) {
        if (cpf == null || cpf.length() != 11 || !cpf.matches("\\d{11}")) {
            return false;
        }

        int[] numeros = new int[11];
        for (int i = 0; i < 11; i++) {
            numeros[i] = Integer.parseInt(cpf.substring(i, i + 1));
        }

        int soma1 = 0;
        int soma2 = 0;
        for (int i = 0; i < 9; i++) {
            soma1 += numeros[i] * (10 - i);
            soma2 += numeros[i] * (11 - i);
        }

        int digito1 = (soma1 * 10) % 11;
        int digito2 = (soma2 * 10) % 11;
        if (digito1 == 10) {
            digito1 = 0;
        }
        if (digito2 == 10) {
            digito2 = 0;
        }

        if (digito1 != numeros[9] || digito2 != numeros[10]) {
            return false;
        }

        return true;
    }
}
