
package it.wego.cross.validator.impl;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.wego.cross.validator.Alphabetic;
/**
 * @author CS
 */
public class AlphabeticValidatorImpl implements ConstraintValidator<Alphabetic, String> {

	// logger
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void initialize(Alphabetic parameters) {
	}// initialize

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
        return Controlla(value);
    }

    public boolean Controlla(String arg) {
        if(arg==null)
        {
            return false;
        }
        String patternStr = "^[a-zA-Z\\u00E0-\\u00FC\\-\\*\\(\\) .]+$";
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(arg);
        if(matcher.matches()) return true;
        else return false;
    }
}