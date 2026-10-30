/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.validator;

import it.wego.cross.validator.impl.NumeroRegistroImpreseValidatorImpl;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 *
 * @author giuseppe
 */
@Documented
@Constraint(validatedBy = NumeroRegistroImpreseValidatorImpl.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NumeroRegistroImprese {

    String message() default "{validator.NumeroRegistroImpreseValidator}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}