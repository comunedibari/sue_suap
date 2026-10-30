
package it.wego.cross.validator.impl;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.wego.cross.dto.dozer.forms.ComunicazioneDTO;
import it.wego.cross.validator.ReassignmentDays;
/**
 * @author CS
 */
public class ReassignmentDaysValidatorImpl implements ConstraintValidator<ReassignmentDays, ComunicazioneDTO> {

	// logger
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void initialize(ReassignmentDays parameters) {
	}// initialize

	@Override
	public boolean isValid(ComunicazioneDTO value, ConstraintValidatorContext context) {
        return (value.getGiorniRiassegnazione() == null && value.getUtenteRiassegnazione() == null) || (value.getGiorniRiassegnazione() != null && value.getGiorniRiassegnazione() > 0);
    }
}