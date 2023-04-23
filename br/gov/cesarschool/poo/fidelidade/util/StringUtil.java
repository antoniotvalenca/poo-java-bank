package br.gov.cesarschool.poo.fidelidade.util;

public class StringUtil {

    private StringUtil() {
    }

    public static boolean ehNuloOuBranco(String str) {
        if (str == null || str.trim().isEmpty()) {
            return true;
        }
        return false;
    }
}
