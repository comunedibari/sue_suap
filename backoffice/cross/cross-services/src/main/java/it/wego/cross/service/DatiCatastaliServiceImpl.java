/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import it.wego.cross.dao.PraticaDao;
import it.wego.cross.dto.dozer.DatiCatastaliDTO;
import it.wego.cross.entity.DatiCatastali;
import it.wego.cross.xml.Immobile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author giuseppe
 */
@Service
public class DatiCatastaliServiceImpl implements DatiCatastaliService {

    @Autowired
    private PraticaDao praticaDao;

    @Override
    public Immobile trovaStrutturaXmlPerIdImmobile(DatiCatastali datiDB, it.wego.cross.xml.DatiCatastali datiCatastali) {
        return trovaStrutturaXmlPerIdImmobile(datiDB.getIdImmobile(), datiCatastali);
    }

    @Override
    public Immobile trovaStrutturaXmlPerIdImmobile(Integer idImmobile, it.wego.cross.xml.DatiCatastali datiCatastali) {
        it.wego.cross.xml.Immobile retImmobile = null;
        for (it.wego.cross.xml.Immobile immobile : datiCatastali.getImmobile()) {
            if (immobile.getIdImmobile() != null && idImmobile != null) {
                if (idImmobile == immobile.getIdImmobile().intValue()) {
                    retImmobile = immobile;
                    break;
                }
            }
        }
        return retImmobile;
    }

    @Override
    public Immobile trovaStrutturaXmlPerCounter(DatiCatastaliDTO datiCatastaliDTO, it.wego.cross.xml.DatiCatastali datiCatastali) {
        return trovaStrutturaXmlPerCounter(datiCatastaliDTO.getCounter(), datiCatastali);
    }

    @Override
    public Immobile trovaStrutturaXmlPerCounter(Integer counter, it.wego.cross.xml.DatiCatastali datiCatastali) {
        it.wego.cross.xml.Immobile retImmobile = null;
        for (it.wego.cross.xml.Immobile immobile : datiCatastali.getImmobile()) {
            if (immobile.getCounter() != null && counter != null) {
                if (counter == immobile.getCounter().intValue()) {
                    retImmobile = immobile;
                    break;
                }
            }
        }
        return retImmobile;
    }

    @Override
    public DatiCatastali findByIdImmobile(Integer idImmobile) {
        return praticaDao.findDatiCatastali(idImmobile);
    }

}
