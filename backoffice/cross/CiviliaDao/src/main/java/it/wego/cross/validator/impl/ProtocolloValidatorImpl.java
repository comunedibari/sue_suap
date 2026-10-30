package it.wego.cross.validator.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.wego.cross.validator.Protocollo;

/**
 * @author CS
 */
public class ProtocolloValidatorImpl implements ConstraintValidator<Protocollo, String> {

    // logger
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void initialize(Protocollo constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
       return contolla(value);
    }
    public Boolean contolla(String protocollo){
        if(protocollo!=null&&!"".equalsIgnoreCase(protocollo))
        {
           String [] s = protocollo.split("/");
           if(s.length!=3)
           {
               return false;
           }
           if(!StringUtils.isAlphanumeric(s[0])||
              s[1].length()!=4 || !StringUtils.isNumeric(s[1]) ||
               !StringUtils.isNumeric(s[2]) )
           {
               return false;
           }
        }
        //^^ viene generato dal potocollo
        return true;
    }
}