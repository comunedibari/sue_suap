/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.dozer.converters;

import it.wego.cross.dto.dozer.AllegatoDTO;
import it.wego.cross.entity.Allegati;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.entity.PraticheEventiAllegati;
import java.util.ArrayList;
import java.util.List;
import org.dozer.ConfigurableCustomConverter;
import org.dozer.Mapper;
import org.dozer.MapperAware;

/**
 *
 * @author Gabriele
 */
public class PraticaAllegatiCustomConverter implements ConfigurableCustomConverter, MapperAware {

    private Mapper mapper;

    @Override
    public void setParameter(String string) {
    }

    @Override
    public Object convert(Object destinationFieldValue, Object sourceFieldValue, Class<?> destinationClass, Class<?> sourceClass) {

        Pratica pratica = (Pratica) sourceFieldValue;
        List<PraticheEventi> praticheEventiList = pratica.getPraticheEventiList();
        List<AllegatoDTO> allegatiList = new ArrayList<AllegatoDTO>();

        for (PraticheEventi evento : praticheEventiList) {
            List<PraticheEventiAllegati> praticheEventiAllegatiList = evento.getPraticheEventiAllegatiList();
            if (praticheEventiAllegatiList != null && !praticheEventiAllegatiList.isEmpty()) {
                for (PraticheEventiAllegati allegatoEvento : praticheEventiAllegatiList) {
                    Allegati allegato = allegatoEvento.getAllegati();
                    AllegatoDTO allegatoDto = mapper.map(allegato, AllegatoDTO.class);
                    if (!allegatiList.contains(allegatoDto)) {
                        allegatiList.add(allegatoDto);
                    }
                }
            }
        }

        return allegatiList;

    }

    @Override
    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }
}
