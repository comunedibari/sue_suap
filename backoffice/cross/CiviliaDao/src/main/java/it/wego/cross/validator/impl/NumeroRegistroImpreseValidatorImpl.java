/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.validator.impl;

import it.wego.cross.validator.NumeroRegistroImprese;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author giuseppe
 */
public class NumeroRegistroImpreseValidatorImpl implements ConstraintValidator<NumeroRegistroImprese, String> {

    @Override
    public void initialize(NumeroRegistroImprese constraintAnnotation) {
        //Nothing to do
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return check(value);
    }

    private boolean check(String value) {
        if (value == null || "".equals(value)) {
            return false;
        } 
        boolean isNumber = value.matches("[0-9]*");
        if (isNumber) {
            return true;
        } else {
            return checkCodiceFiscale(value);
        }
    }
    
    public boolean checkCodiceFiscale(String codiceFiscale) {
        int i, s, c;
        String cf2;
        int setdisp[] = {
            1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 2, 4, 18, 20,
            11, 3, 6, 8, 12, 14, 16, 10, 22, 25, 24, 23
        };
        if (codiceFiscale.length() == 0) {
            return false;
        }
        if (codiceFiscale.length() != 16) {
            return false;
        }
        cf2 = codiceFiscale.toUpperCase();
        for (i = 0; i < 16; i++) {
            c = cf2.charAt(i);
            if (!(c >= '0' && c <= '9' || c >= 'A' && c <= 'Z')) {
                return false;
            }
        }
        s = 0;
        for (i = 1; i <= 13; i += 2) {
            c = cf2.charAt(i);
            if (c >= '0' && c <= '9') {
                s = s + c - '0';
            } else {
                s = s + c - 'A';
            }
        }
        for (i = 0; i <= 14; i += 2) {
            c = cf2.charAt(i);
            if (c >= '0' && c <= '9') {
                c = c - '0' + 'A';
            }
            s = s + setdisp[c - 'A'];
        }
        char a = (char) (s % 26 + 'A');
        if (a != cf2.charAt(15)) {
            return false;
        }
        return true;
    }
}
