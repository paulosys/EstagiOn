package br.edu.ifpb.pweb2.estagion.validators;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidadorCNPJ implements ConstraintValidator<CNPJ, String> {

    @Override
    public void initialize(CNPJ constraintAnnotation) {
        // Código de inicialização, se necessário
    }

    @Override
    public boolean isValid(String cnpj, ConstraintValidatorContext context) {
        if (cnpj == null || cnpj.length() != 14 || !cnpj.matches("\\d+")) {
            return false;
        }

        // Verificação básica para CNPJs com dígitos repetidos (CNPJs inválidos)
        if (cnpj.matches("(\\d)\\1{13}")) {
            return false;
        }

        // Valida os dígitos verificadores
        return isCNPJValido(cnpj);
    }

    private boolean isCNPJValido(String cnpj) {
        int[] pesosPrimeiroDigito = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] pesosSegundoDigito = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

        try {
            int primeiroDigito = calcularDigito(cnpj.substring(0, 12), pesosPrimeiroDigito);
            int segundoDigito = calcularDigito(cnpj.substring(0, 12) + primeiroDigito, pesosSegundoDigito);

            return cnpj.equals(cnpj.substring(0, 12) + primeiroDigito + segundoDigito);
        } catch (Exception e) {
            return false;
        }
    }

    private int calcularDigito(String str, int[] pesos) {
        int soma = 0;
        for (int i = 0; i < str.length(); i++) {
            soma += Character.getNumericValue(str.charAt(i)) * pesos[i];
        }
        int resto = soma % 11;
        return resto < 2 ? 0 : 11 - resto;
    }
}