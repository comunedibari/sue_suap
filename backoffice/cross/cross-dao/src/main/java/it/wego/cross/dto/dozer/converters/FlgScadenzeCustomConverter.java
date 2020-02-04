/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.dozer.converters;

import it.wego.cross.entity.ProcessiEventiScadenze;
import java.util.List;
import org.dozer.ConfigurableCustomConverter;

/**
 *
 * @author Gabriele
 */
public class FlgScadenzeCustomConverter implements ConfigurableCustomConverter {

    @Override
    public void setParameter(String string) {
    }

    @Override
    public Object convert(Object destinationFieldValue, Object sourceFieldValue, Class<?> destinationClass, Class<?> sourceClass) {

        List<ProcessiEventiScadenze> processiEventiScadenzeList = (List<ProcessiEventiScadenze>) sourceFieldValue;
        
        //Verifico se c e una scadenza da aprire
        for (ProcessiEventiScadenze pes : processiEventiScadenzeList) {
		if (pes.getFlgVisualizzaScadenza().equalsIgnoreCase("S") && pes.getIdStatoScadenza().getGrpStatoScadenza().equals("A")) {
			return "S";
		}
	}
        //altrimenti restituisco "N"
        return "N";

    }
}
