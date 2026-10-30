/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.dozer.converters;

import java.util.ArrayList;
import java.util.List;

import org.dozer.ConfigurableCustomConverter;
import org.dozer.Mapper;
import org.dozer.MapperAware;

import it.wego.cross.dto.dozer.AllegatoDTO;
import it.wego.cross.entity.PraticheEventiAllegati;
import it.wego.cross.entity.PraticheEventiAnagrafiche;

/**
 *
 * @author Gabriele
 */
public class PraticaEventoAllegatiCustomConverter implements ConfigurableCustomConverter, MapperAware {

    private Mapper mapper;

    @Override
    public void setParameter(String string) {
    }

    @Override
    public Object convert(Object destinationFieldValue, Object sourceFieldValue, Class<?> destinationClass, Class<?> sourceClass) {

        List<PraticheEventiAllegati> praticheEventiAllegati = (List<PraticheEventiAllegati>) sourceFieldValue;
        List<AllegatoDTO> result = new ArrayList<AllegatoDTO>();
        
        for(PraticheEventiAllegati pea: praticheEventiAllegati) {
        	AllegatoDTO dto = new AllegatoDTO();
        	dto.setId(pea.getPraticheEventiAllegatiPK().getIdAllegato());
        	dto.setNomeFile(pea.getAllegati().getNomeFile());
        	dto.setPrincipale(pea.getFlgIsPrincipale());
        	result.add(dto);
        }
        return result;

    }

    @Override
    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }
}
