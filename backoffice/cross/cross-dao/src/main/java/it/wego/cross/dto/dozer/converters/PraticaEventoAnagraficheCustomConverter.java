/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.dozer.converters;

import it.wego.cross.dto.dozer.AnagraficaDTO;
import it.wego.cross.dto.dozer.RecapitoAnagraficaDTO;
import it.wego.cross.dto.dozer.RecapitoDTO;
import it.wego.cross.entity.PraticheEventiAnagrafiche;
import java.util.ArrayList;
import java.util.List;
import org.dozer.ConfigurableCustomConverter;
import org.dozer.Mapper;
import org.dozer.MapperAware;

/**
 *
 * @author Gabriele
 */
public class PraticaEventoAnagraficheCustomConverter implements ConfigurableCustomConverter, MapperAware {

    private Mapper mapper;

    @Override
    public void setParameter(String string) {
    }

    @Override
    public Object convert(Object destinationFieldValue, Object sourceFieldValue, Class<?> destinationClass, Class<?> sourceClass) {

        List<PraticheEventiAnagrafiche> praticaEventoAnagraficheList = (List<PraticheEventiAnagrafiche>) sourceFieldValue;
        List<RecapitoAnagraficaDTO> result = new ArrayList<RecapitoAnagraficaDTO>();
        if (sourceFieldValue != null) {
            for (PraticheEventiAnagrafiche praticaEventoAnagrafica : praticaEventoAnagraficheList) {
                RecapitoAnagraficaDTO dto = new RecapitoAnagraficaDTO();
                if (praticaEventoAnagrafica.getIdRecapito() != null) {
                    RecapitoDTO recapitoDto = mapper.map(praticaEventoAnagrafica.getIdRecapito(), RecapitoDTO.class);
                    dto.setRecapito(recapitoDto);
                }

                if (praticaEventoAnagrafica.getAnagrafica() != null) {
                    AnagraficaDTO anagraficaDto = mapper.map(praticaEventoAnagrafica.getAnagrafica(), AnagraficaDTO.class);
                    dto.setAnagrafica(anagraficaDto);
                }

                result.add(dto);
            }
        }
        return result;

    }

    @Override
    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }
}
