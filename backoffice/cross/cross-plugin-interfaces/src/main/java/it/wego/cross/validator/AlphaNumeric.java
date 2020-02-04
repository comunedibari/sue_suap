
package it.wego.cross.validator;
import it.wego.cross.validator.impl.AlphaNumericValidatorImpl;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * @author CS
 */
@Documented
@Constraint(validatedBy = AlphaNumericValidatorImpl.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface AlphaNumeric {

	String message() default "{validator.CodiceFiscaleValidator}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}