package com.teste.estagio.api.utils;

public class CPFFormatterUtils {

    public static String formatCPF(String cpf) {
        if (cpf == null)
            return null;

        return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }

    public static String cleanCPF(String cpf) {
        if (cpf == null)
            return null;

        return cpf.replaceAll("[^\\d]", "");
    }
}
