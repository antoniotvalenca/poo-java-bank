package br.gov.cesarschool.poo.fidelidade.util;

public class ValidadorCPF {
	 
	private ValidadorCPF() {}
	
	public static boolean ehCpfValido(String cpf) {
	    if (cpf == null) {
        	return false;
	    }
	    if (cpf.length() != 11) {
        	return false;
	    }
	    for (int i = 0; i < cpf.length(); i++) {
	        char c = cpf.charAt(i);
	        if (!Character.isDigit(c)) {
	        	return false;
	        }
	    }
	    int[] numeros = new int[11];
        for (int i = 0; i < 11; i++) {
            numeros[i] = Character.getNumericValue(cpf.charAt(i));
        }
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += numeros[i] * (10 - i);
        }
        int resto = soma % 11;
        int dv1 = resto < 2 ? 0 : 11 - resto;

        if (numeros[9] != dv1) {
            return false;
        }
        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += numeros[i] * (11 - i);
        }
        resto = soma % 11;
        int dv2 = resto < 2 ? 0 : 11 - resto;
        if (numeros[10] != dv2) {
            return false;
        }        
        int cont = 0;
        //verifica cpf com todos ou praticamente todos com numeros iguais
        for (int i = 1; i < cpf.length(); i++) {
            if (cpf.charAt(i) == cpf.charAt(0)) {
            	cont++;
            }
        } 
        if (cont >= 10) {
        	return false;
        }
	    return true;
	}
}
	

