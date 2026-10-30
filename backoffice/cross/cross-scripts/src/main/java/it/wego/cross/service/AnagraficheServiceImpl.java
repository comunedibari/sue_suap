package it.wego.cross.service;

import java.util.ArrayList;

import javax.persistence.EntityManager;

import it.wego.cross.dao.AnagraficheDao;
import it.wego.cross.dao.PraticaDao;
import it.wego.cross.dao.StagingDao;
import it.wego.cross.dto.AnagraficaDTO;
import it.wego.cross.dto.RecapitoDTO;
import it.wego.cross.entity.Anagrafica;
import it.wego.cross.entity.AnagraficaRecapiti;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.Staging;
import it.wego.cross.utils.AnagraficaUtils;
import it.wego.cross.utils.PraticaUtils;
import it.wego.cross.utils.RecapitoUtils;

public class AnagraficheServiceImpl {

	private AnagraficheDao anagraficheDao;
	private AnagraficaUtils anagraficaUtils;
	private RecapitoUtils recapitoUtils;
	private PraticaDao praticaDao;
	private StagingDao stagingDao;
	
	


	public AnagraficheServiceImpl(AnagraficheDao anagraficheDao, AnagraficaUtils anagraficaUtils,
			RecapitoUtils recapitoUtils, PraticaDao praticaDao, StagingDao stagingDao) {
		super();
		this.anagraficheDao = anagraficheDao;
		this.anagraficaUtils = anagraficaUtils;
		this.recapitoUtils = recapitoUtils;
		this.praticaDao = praticaDao;
		this.stagingDao = stagingDao;
	}

	public AnagraficheServiceImpl(EntityManager em) {
		this.anagraficheDao = new AnagraficheDao(em);
	}

    public Anagrafica findAnagraficaById(Integer idAnagrafica) {
        Anagrafica anagrafica = anagraficheDao.findById(idAnagrafica);
        return anagrafica;
    }
	public Anagrafica findAnagraficaByCodFiscale(String codiceFiscale) {
        return anagraficheDao.findByCodiceFiscale(codiceFiscale);
    }
	
	public AnagraficaDTO getAnagrafica(AnagraficaDTO anagraficaDTO) throws Exception {
        AnagraficaDTO dto = new AnagraficaDTO();
        Anagrafica anagrafica = null;
        if (anagraficaDTO.getIdAnagrafica() != null) {
            anagrafica = anagraficheDao.findById(anagraficaDTO.getIdAnagrafica());
        } else {
            anagrafica = anagraficheDao.findByCodiceFiscale(anagraficaDTO.getCodiceFiscale());
        }
        if (anagrafica != null) {
            anagraficaUtils.copyEntity2Anagrafica(anagrafica, dto);
            if (dto.getRecapiti() == null) {
                dto.setRecapiti(new ArrayList<RecapitoDTO>());
            }
            for (AnagraficaRecapiti recapito : anagrafica.getAnagraficaRecapitiList()) {
                RecapitoDTO recapitoDTO = new RecapitoDTO();
                RecapitoUtils.copyEntity2Recapito(recapito.getIdRecapito(), recapitoDTO);
                if (recapito.getIdTipoIndirizzo() != null) {
                    recapitoDTO.setDescTipoIndirizzo(recapito.getIdTipoIndirizzo().getDescrizione());
                    recapitoDTO.setIdTipoIndirizzo(recapito.getIdTipoIndirizzo().getIdTipoIndirizzo());
                }
                dto.getRecapiti().add(recapitoDTO);
            }
        }
        return dto;
    }
	
    public AnagraficaDTO getAnagraficaXML(AnagraficaDTO anagraficaDTO) throws Exception {
        Pratica pratica = praticaDao.findPratica(anagraficaDTO.getIdPratica());
        Staging stg = stagingDao.findByCodStaging(pratica.getIdStaging().getIdStaging());
        it.wego.cross.xml.Pratica praticaXml = PraticaUtils.getPraticaFromXML(new String(stg.getXmlPratica()));
        for (it.wego.cross.xml.Anagrafiche anaXML : praticaXml.getAnagrafiche()) {
            if (anagraficaUtils.equals(anagraficaDTO, anaXML.getAnagrafica())) {
                anagraficaUtils.copyXML2Anagrafica(anaXML, anagraficaDTO, pratica);
                break;
            }
        }
        return anagraficaDTO;
    }
}
