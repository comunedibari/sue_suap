/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.actions;

import it.wego.cross.dao.DefDatiEstesiDao;
import it.wego.cross.dao.LookupDao;
import it.wego.cross.dto.dozer.DefDatiEstesiDTO;
import it.wego.cross.entity.DefDatiEstesi;
import it.wego.cross.entity.LkTipoOggetto;
import it.wego.cross.service.DefDatiEstesiService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author giuseppe
 */
@Component
public class DatiEstesiAction {

    @Autowired
    private DefDatiEstesiDao defDatiEstesiDao;
    @Autowired
    private DefDatiEstesiService defDatiEstesiService;
    @Autowired
    private Mapper mapper;
    @Autowired
    private LookupDao lookupDao;

    @Transactional
    public void aggiornaDatoEsteso(DefDatiEstesiDTO defDatiEstesiDTO) throws Exception {
        DefDatiEstesi de = defDatiEstesiDao.findById(defDatiEstesiDTO.getIdDatiEstesi());
        de = mapper.map(defDatiEstesiDTO, DefDatiEstesi.class);
        de.setIdTipoOggetto(lookupDao.findTipoOggettoById(defDatiEstesiDTO.getIdTipoOggetto()));
        defDatiEstesiDao.merge(de);
    }

    @Transactional
    public void salvaDatoEsteso(DefDatiEstesiDTO defDatiEstesiDTO) throws Exception {
        DefDatiEstesi de = mapper.map(defDatiEstesiDTO, DefDatiEstesi.class);
        LkTipoOggetto lk = lookupDao.findTipoOggettoByCodice(defDatiEstesiDTO.getCodTipoOggetto());
        de.setIdTipoOggetto(lk);
        defDatiEstesiDao.insert(de);
    }

    @Transactional
    public void salvaDatoEstesoEntity(DefDatiEstesi de) throws Exception {
        defDatiEstesiDao.insert(de);
    }

    @Transactional
    public void cancellaDatoEsteso(DefDatiEstesiDTO defDatiEstesiDTO) throws Exception {
        DefDatiEstesi de = defDatiEstesiDao.findById(defDatiEstesiDTO.getIdDatiEstesi());
        defDatiEstesiDao.delete(de);
    }

}
