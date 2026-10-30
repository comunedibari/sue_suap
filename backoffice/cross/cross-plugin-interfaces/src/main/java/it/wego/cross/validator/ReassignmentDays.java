
package it.wego.cross.validator;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import it.wego.cross.validator.impl.ReassignmentDaysValidatorImpl;

/**
 * @author CS
 */
@Documented
@Constraint(validatedBy = ReassignmentDaysValidatorImpl.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ReassignmentDays {

	String message() default "";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}