/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import it.wego.cross.dto.dozer.DatiCatastaliDTO;
import it.wego.cross.entity.DatiCatastali;
import it.wego.cross.xml.Immobile;
import org.springframework.stereotype.Service;

/**
 *
 * @author giuseppe
 */
@Service
public interface DatiCatastaliService {

    public Immobile trovaStrutturaXmlPerIdImmobile(DatiCatastali datiDB, it.wego.cross.xml.DatiCatastali datiCatastali);

    public Immobile trovaStrutturaXmlPerIdImmobile(Integer idImmobile, it.wego.cross.xml.DatiCatastali datiCatastali);

    public Immobile trovaStrutturaXmlPerCounter(DatiCatastaliDTO datiCatastaliDTO, it.wego.cross.xml.DatiCatastali datiCatastali);

    public Immobile trovaStrutturaXmlPerCounter(Integer counter, it.wego.cross.xml.DatiCatastali datiCatastali);

    public DatiCatastali findByIdImmobile(Integer idImmobile);

}
