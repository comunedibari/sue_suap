
package it.wego.cross.validator;
import it.wego.cross.validator.impl.NumericValidatorImpl;
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
@Constraint(validatedBy = NumericValidatorImpl.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Numeric {

	String message() default "{validator.CodiceFiscaleValidator}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}