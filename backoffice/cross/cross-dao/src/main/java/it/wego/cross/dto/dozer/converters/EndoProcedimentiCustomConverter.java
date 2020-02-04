/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.dozer.converters;

import it.wego.cross.dao.ProcedimentiDao;
import it.wego.cross.dto.dozer.PraticaDTO;
import it.wego.cross.dto.dozer.ProcedimentoEnteDTO;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticaProcedimenti;
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
@Component
public class EndoProcedimentiCustomConverter implements ConfigurableCustomConverter, MapperAware {

    private Mapper mapper;
    @Autowired
    private ProcedimentiDao procedimentiDao;

    @Override
    public void setParameter(String string) {
    }

    @Override
    public Object convert(Object destinationFieldValue, Object sourceFieldValue, Class<?> destinationClass, Class<?> sourceClass) {

        Pratica pratica = (Pratica) sourceFieldValue;
        List<ProcedimentoEnteDTO> endoprocedimentiEntiList = new ArrayList<ProcedimentoEnteDTO>();
        List<Pratica> praticheFiglie = pratica.getPraticaList();
        ProcedimentiEnti procedimentoEnte;
        for (PraticaProcedimenti praticaProcedimento : pratica.getPraticaProcedimentiList()) {
            procedimentoEnte = procedimentiDao.findProcedimentiEntiByProcedimentoEnte(praticaProcedimento.getEnti().getIdEnte(), praticaProcedimento.getProcedimenti().getIdProc());
            if (procedimentoEnte != null) {
                ProcedimentoEnteDTO procedimentoEnteDTO = mapper.map(procedimentoEnte, ProcedimentoEnteDTO.class);
                if (praticheFiglie != null && !praticheFiglie.isEmpty()) {
                    for (Pratica praticaFiglia : praticheFiglie) {
                        if (praticaFiglia.getIdProcEnte().equals(procedimentoEnte)) {
                            procedimentoEnteDTO.setPratica(mapper.map(praticaFiglia, PraticaDTO.class));
                            break;
                        }
                    }
                }
                endoprocedimentiEntiList.add(procedimentoEnteDTO);
            }
        }

        return endoprocedimentiEntiList;

    }

    @Override
    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }
}
