/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.dozer.converters;

import it.wego.cross.entity.ProcedimentiTesti;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.dozer.ConfigurableCustomConverter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 *
 * @author Gabriele
 */
public class ProcedimentiTestiListConverter implements ConfigurableCustomConverter {

    private static final String lang = "IT";

    @Override
    public void setParameter(String string) {
    }

    @Override
    public Object convert(Object destinationFieldValue, Object sourceFieldValue, Class<?> destinationClass, Class<?> sourceClass) {

        //TODO: recupero la lingua dall'utente in sessione. Dove si trova?
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest req = sra.getRequest();

        List<ProcedimentiTesti> procedimentiTestiList = (List<ProcedimentiTesti>) sourceFieldValue;

        //Cerco la descrizione per la lingua corrente
        for (ProcedimentiTesti procedimentoTesto : procedimentiTestiList) {
            if (procedimentoTesto.getLingue().getCodLang().equalsIgnoreCase(lang)) {
                return procedimentoTesto.getDesProc();
            }
        }

        //Se non ho trovato la descrizione per la lingua corrente restituisco la prima descrizione che trovo
        if (procedimentiTestiList.size() > 0) {
            return procedimentiTestiList.get(0).getDesProc();
        }

        //altrimenti restituisco null
        return null;

    }
}
