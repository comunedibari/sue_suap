/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.dozer.converters;

import it.wego.cross.dao.ProcedimentiDao;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.Scadenze;
import java.util.Date;
import java.util.List;
import org.dozer.ConfigurableCustomConverter;
import org.dozer.Mapper;
import org.dozer.MapperAware;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Gabriele
 */
public class NumGiorniPrimaScadenzaCustomConverter implements ConfigurableCustomConverter, MapperAware {

    private Mapper mapper;
    @Autowired
    private ProcedimentiDao procedimentiDao;

    @Override
    public void setParameter(String string) {
    }

    @Override
    public Object convert(Object destinationFieldValue, Object sourceFieldValue, Class<?> destinationClass, Class<?> sourceClass) {

        Pratica pratica = (Pratica) sourceFieldValue;
        List<Scadenze> scadenzeList = pratica.getScadenzeList();
        Date result = null;
        Integer giorniScadenza = null;

        if (scadenzeList != null && !scadenzeList.isEmpty()) {
            for (Scadenze scadenza : scadenzeList) {
                if (!scadenza.getIdStato().getGrpStatoScadenza().equals("C")) {
                    if (result == null) {
                        result = scadenza.getDataFineScadenza();
                    } else {
                        if (result.after(scadenza.getDataFineScadenza())) {
                            result = scadenza.getDataFineScadenza();
                        }
                    }
                }

            }
            DateTime today = new DateTime();
            DateTime dataScadenza = new DateTime(result);

            Days daysBetween = Days.daysBetween(today, dataScadenza);
            giorniScadenza = daysBetween.getDays();
        }

        return giorniScadenza;
    }

    @Override
    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }
}
