package it.wego.cross.controller;

import java.util.List;

import org.dozer.Mapper;

import it.wego.cross.dao.PraticaDao;
import it.wego.cross.dto.AnagraficaDTO;
import it.wego.cross.dto.PraticaDTO;
import it.wego.cross.dto.comunicazione.AnagraficaMinifiedDTO;
import it.wego.cross.dto.comunicazione.DettaglioPraticaDTO;
import it.wego.cross.entity.Anagrafica;
import it.wego.cross.entity.Pratica;
import it.wego.cross.service.AnagraficheServiceImpl;
import it.wego.cross.services.PraticheServiceImpl;

public class Pratiche {

	PraticheServiceImpl praticheService;
	AnagraficheServiceImpl anagraficheService;
	Mapper mapper;
	PraticaDao praticaDao;
	
	
	
	public Pratiche(PraticheServiceImpl praticheService, AnagraficheServiceImpl anagraficheService, Mapper mapper,
			PraticaDao praticaDao) {
		super();
		this.praticheService = praticheService;
		this.anagraficheService = anagraficheService;
		this.mapper = mapper;
		this.praticaDao = praticaDao;
	}


	// public String aperturaDettaglio(
	public PraticaDTO aperturaDettaglio(int idPratica) throws Exception{
		PraticaDTO pratica = praticheService.dettaglioXmlPratica(idPratica);
        Pratica praticafull = praticaDao.findPratica(idPratica);
        DettaglioPraticaDTO dettaglio = praticheService.getDettaglioPratica(praticafull);
        List<AnagraficaMinifiedDTO> anagraficheCollegate = dettaglio.getAnagrafiche();
        for (AnagraficaDTO an : pratica.getAnagraficaList()) {
            if ("S".equals(an.getDaRubrica())) {
                an.setConfermata("S");
            }
            for (AnagraficaMinifiedDTO anagraficacoll : anagraficheCollegate) {
                String codicefiscale = anagraficacoll.getCodiceFiscale();
                //Risoluzione JIRA #789 - alla pratica è associata una anagrafica con CF e PIVA nulle
                if (org.springframework.util.StringUtils.hasText(codicefiscale) && (codicefiscale.equals(an.getCodiceFiscale())) && (anagraficacoll.getIdRuolo().equals(an.getIdTipoRuolo()))) {
                    if (an.getIdAnagrafica() == null) {
                        an.setIdAnagrafica(anagraficacoll.getIdAnagrafica());
                    }
                    an.setCollegata("yes");
                    an.setDataInizioValidita(anagraficacoll.getDataInizioValidita());
                }
            }
            if (an.getCollegata() == null || !an.getCollegata().equalsIgnoreCase("yes")) {
                an.setConfermata(null);
            }
            if (an.getIdAnagrafica() == null) {
                Anagrafica anagrafica = anagraficheService.findAnagraficaByCodFiscale(an.getCodiceFiscale());
                if (anagrafica != null) {
                    an.setIdAnagrafica(anagrafica.getIdAnagrafica());
                }
            }
        }
        return pratica;
	}
}
