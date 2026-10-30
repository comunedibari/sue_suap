package it.wego.cross.validator.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.wego.cross.validator.PartitaIva;

/**
 * @author CS
 */
public class PartitaIvaValidatorImpl implements ConstraintValidator<PartitaIva, String> {

    // logger
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void initialize(PartitaIva parameters) {
    }// initialize

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Controlla(value);
    }

    public boolean Controlla(String pi) {
            if (pi == null) {
            return false;
        }
        return ControllaPIVA(pi);
    }
    public boolean ControllaPIVA(String pi) {

        log.debug("PartitaIVAValidator: " + pi);
        if (pi == null) {
            return false;
        }

        pi = pi.trim();
        if (pi.length() == 0) {
            //puo essere vuoto - controlato da validatore NotEmpty
            return true;
        }
        if (pi.length() != 11) {
            //La lunghezza della partita IVA non e'
            //corretta: la partita IVA dovrebbe essere lunga
            //esattamente 11 caratteri.
            return false;
        }
        for (int i = 0; i < 11; i++) {
            if (pi.charAt(i) < '0' || pi.charAt(i) > '9') {
                //La partita IVA contiene dei caratteri non ammessi:
                //la partita IVA dovrebbe contenere solo cifre.
                return false;
            }
        }
        int s = 0;
        for (int i = 0; i <= 9; i += 2) {
            s += pi.charAt(i) - '0';
        }
        int c;
        for (int i = 1; i <= 9; i += 2) {
            c = 2 * (pi.charAt(i) - '0');
            if (c > 9) {
                c = c - 9;
            }
            s += c;
        }
        char a= (char)((10 - s % 10) % 10);
        char b= (char)(pi.charAt(10) - '0');
        return a == b;
    }
}