
package it.wego.cross.validator.impl;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.wego.cross.validator.AlphaNumeric;
/**
 * @author CS
 */
public class AlphaNumericValidatorImpl implements ConstraintValidator<AlphaNumeric, String> {

	// logger
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void initialize(AlphaNumeric parameters) {
	}// initialize

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
            if(value==null)
            {
                return false;
            }
        return Controlla(value);
    }

    public boolean Controlla(String arg) {
        String patternStr = "^[a-zA-Z0-9. /']*$";
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(arg);
        if(matcher.matches()) return true;
        else return false;
    }
}