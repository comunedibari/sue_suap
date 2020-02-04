package it.wego.cross.validator.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.wego.cross.validator.CodiceFiscale;

/**
 * @author CS
 */
public class CodiceFiscaleValidatorImpl implements ConstraintValidator<CodiceFiscale, String> {

    // logger
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void initialize(CodiceFiscale parameters) {
    }// initialize

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Controlla(value);
    }

    public boolean Controlla(String value) {
        if (value == null || "".equals(value)) {
            return false;
        }
        boolean isNumber = value.matches("[0-9]*");
        boolean isValid;
        if (isNumber) {
            isValid = controllaCfPersonaguiridica(value);
        } else {
            isValid = ControllaCF(value);
        }
        return isValid;
    }

    public boolean ControllaCF(String cf) {
        int i, s, c;
        String cf2;
        int setdisp[] = {
            1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 2, 4, 18, 20,
            11, 3, 6, 8, 12, 14, 16, 10, 22, 25, 24, 23
        };
        if (cf.length() == 0) {
            return false;
        }
        if (cf.length() != 16) {
            return false;
        }
        cf2 = cf.toUpperCase();
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
    
    private boolean controllaCfPersonaguiridica(String partitaIva){
        log.debug("PartitaIVAValidator: " + partitaIva);
        if (partitaIva == null) {
            return false;
        }

        partitaIva = partitaIva.trim();
        if (partitaIva.length() == 0) {
            return true;
        }
        if (partitaIva.length() != 11) {
            return false;
        }
//        for (int i = 0; i < 11; i++) {
//            if (partitaIva.charAt(i) < '0' || partitaIva.charAt(i) > '9') {
//                return false;
//            }
//        }
        int s = 0;
        for (int i = 0; i <= 9; i += 2) {
            s += partitaIva.charAt(i) - '0';
        }
        int c;
        for (int i = 1; i <= 9; i += 2) {
            c = 2 * (partitaIva.charAt(i) - '0');
            if (c > 9) {
                c = c - 9;
            }
            s += c;
        }
        char a= (char)((10 - s % 10) % 10);
        char b= (char)(partitaIva.charAt(10) - '0');
        return a == b;
    }
}