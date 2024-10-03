package br.edu.ifpb.pweb2.estagion.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidadorCPF implements ConstraintValidator<CPF, String> {

    @Override
    public void initialize(CPF constraintAnnotation) {
        // Código de inicialização, se necessário
    }

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {
        if (cpf == null || cpf.length() != 11 || !cpf.matches("\\d+")) {
            return false;
        }

        // Verificação básica para CPFs com dígitos repetidos (CPFs inválidos)
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        // Valida os dígitos verificadores
        return isCPFValido(cpf);
    }

    private boolean isCPFValido(String cpf) {
        int[] pesos = {10, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] pesosSegundoDigito = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

        try {
            int primeiroDigito = calcularDigito(cpf.substring(0, 9), pesos);
            int segundoDigito = calcularDigito(cpf.substring(0, 9) + primeiroDigito, pesosSegundoDigito);

            return cpf.equals(cpf.substring(0, 9) + primeiroDigito + segundoDigito);
        } catch (Exception e) {
            return false;
        }
    }

    private int calcularDigito(String str, int[] pesos) {
        int soma = 0;
        for (int i = 0; i < str.length(); i++) {
            soma += Character.getNumericValue(str.charAt(i)) * pesos[i];
        }
        int resto = 11 - (soma % 11);
        return resto > 9 ? 0 : resto;
    }
}