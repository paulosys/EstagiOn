package br.edu.ifpb.pweb2.estagion.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidadorTelefone.class)
public @interface Telefone {

    String message() default "Telefone inválido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}