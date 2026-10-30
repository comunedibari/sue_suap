
package it.wego.cross.validator;
import it.wego.cross.validator.impl.PartitaIvaValidatorImpl;
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
@Constraint(validatedBy = PartitaIvaValidatorImpl.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface PartitaIva {

	String message() default "{validator.PartitaIvaValidator}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}