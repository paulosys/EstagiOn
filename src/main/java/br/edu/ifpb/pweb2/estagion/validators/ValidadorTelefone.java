package br.edu.ifpb.pweb2.estagion.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidadorTelefone implements ConstraintValidator<Telefone, String> {

    private static final String TELEFONE_REGEX = "\\(?\\d{2}\\)? ?9?\\d{4}[- ]?\\d{4}";

    @Override
    public void initialize(Telefone constraintAnnotation) {
        // Código de inicialização, se necessário
    }

    @Override
    public boolean isValid(String telefone, ConstraintValidatorContext context) {
        if (telefone == null) {
            return false;
        }

        // Verifica se o telefone corresponde ao formato esperado
        return telefone.matches(TELEFONE_REGEX);
    }
}
