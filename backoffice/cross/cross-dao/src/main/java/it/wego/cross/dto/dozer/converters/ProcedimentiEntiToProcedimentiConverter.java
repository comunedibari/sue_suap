/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.dozer.converters;

import it.wego.cross.dto.dozer.ProcedimentoDTO;
import it.wego.cross.entity.ProcedimentiEnti;
import java.util.ArrayList;
import java.util.List;
import org.dozer.ConfigurableCustomConverter;
import org.dozer.Mapper;
import org.dozer.MapperAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Gabriele
 */
public class ProcedimentiEntiToProcedimentiConverter implements ConfigurableCustomConverter, MapperAware {

    private Mapper mapper;

    @Override
    public void setParameter(String string) {
    }

    @Override
    public Object convert(Object destinationFieldValue, Object sourceFieldValue, Class<?> destinationClass, Class<?> sourceClass) {

        List<ProcedimentiEnti> procedimentiEntiList = (List<ProcedimentiEnti>) sourceFieldValue;

        List<ProcedimentoDTO> procedimentiList = new ArrayList<ProcedimentoDTO>();

        ProcedimentoDTO procedimentoLoop;
        for (ProcedimentiEnti procedimentoEnte : procedimentiEntiList) {
            procedimentoLoop = mapper.map(procedimentoEnte.getIdProc(), it.wego.cross.dto.dozer.ProcedimentoDTO.class);
            if (!procedimentiList.contains(procedimentoLoop)) {
                procedimentiList.add(procedimentoLoop);
            }
        }

        return procedimentiList;

    }

    @Override
    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }
}
