/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.dozer.converters;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import it.wego.cross.dto.dozer.UtenteDTO;
import it.wego.cross.dto.dozer.NotaDTO;
import it.wego.cross.entity.NotePratica;
import it.wego.cross.entity.Pratica;
import java.util.List;
import org.dozer.ConfigurableCustomConverter;
import org.dozer.Mapper;
import org.dozer.MapperAware;

/**
 *
 * @author Gabriele
 */
public class PraticaNoteCustomConverter implements ConfigurableCustomConverter, MapperAware {

    private Mapper mapper;

    @Override
    public void setParameter(String string) {
    }

    @Override
    public Object convert(Object destinationFieldValue, Object sourceFieldValue, Class<?> destinationClass, Class<?> sourceClass) {

        Pratica pratica = (Pratica) sourceFieldValue;
        List<NotePratica> notePraticaList = pratica.getNotePraticaList();
        List<NotaDTO> notePratica = Lists.newArrayList(Iterables.transform(notePraticaList, new Function<NotePratica, NotaDTO>() {

            @Override
            public NotaDTO apply(NotePratica input) {
                NotaDTO dto = new NotaDTO();
                dto.setDataInserimento(input.getDataInserimento());
                dto.setIdNotePratica(input.getIdNotePratica());
                dto.setTesto(input.getTesto());
                UtenteDTO utente = mapper.map(input.getIdUtente(), UtenteDTO.class);
                dto.setIdUtente(utente);
                return dto;
            }
        }));

        return notePratica;

    }

    @Override
    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }
}
