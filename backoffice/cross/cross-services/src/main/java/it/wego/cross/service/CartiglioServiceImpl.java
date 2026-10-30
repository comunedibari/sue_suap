/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.wego.cross.constants.Constants;
import it.wego.cross.dao.PraticaProcuratoreDao;
import it.wego.cross.dto.Cartiglio;
import it.wego.cross.dto.EventoDTO;
import it.wego.cross.dto.dozer.AnagraficaDTO;
import it.wego.cross.entity.Anagrafica;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticaAnagrafica;
import it.wego.cross.entity.PraticaOperazione;
import it.wego.cross.entity.PraticaProcuratore;
import it.wego.cross.entity.PraticheEventi;

/**
 * Service per la gestione della pratiche
 *
 * @author CS
 */
@Service
public class CartiglioServiceImpl implements CartiglioService {
	
	public static final Integer APPLICANT_ROLE_CODE = 1;
	public static final Integer PROFESSIONAL_ROLE_CODE = 3;
	public static final Integer INSTRUCTOR_ROLE_CODE = 5;

	public static final Set<Integer> technicianAgentTypes = new HashSet<>();
	static {
		technicianAgentTypes.add(APPLICANT_ROLE_CODE);
		technicianAgentTypes.add(PROFESSIONAL_ROLE_CODE);
	}
	
	@Autowired
	private PraticaProcuratoreDao praticaProcuratoreDao;
	@Autowired
    private Mapper mapper;    
	@Autowired
	private CiviliaService civiliaService;	
	
	public static Set<Integer> SUAP_INSTITUTION = new HashSet<>();
    static{
    	SUAP_INSTITUTION.add(Constants.SUAP_BARI);
    	SUAP_INSTITUTION.add(Constants.SUAP_GIOIA_DEL_COLLE);
    }
	@Override
	public Cartiglio createCartiglio(Pratica pratica) {
		return this.createCartiglio(pratica, null);
	}
	@Override
	public Cartiglio createCartiglio(Pratica pratica, EventoDTO evento) {

		Cartiglio cartiglio = new Cartiglio();
		cartiglio.setIdPratica(pratica.getIdPratica());
		if(pratica.getIdUtente() != null)
			cartiglio.setIdUtente(pratica.getIdUtente().getIdUtente());

		if(evento != null)
			cartiglio.setIdPraticaEvento(evento.getIdPraticaEvento());
		
		if(pratica.getTipoPratica() != null) {
			cartiglio.setTipoPratica(pratica.getTipoPratica().getDescrizione());
			cartiglio.setCodTipoPratica(pratica.getTipoPratica().getTipoPratica());
		}
		cartiglio.setAnnoPratica(pratica.getAnno());
		cartiglio.setNumeroFascicolo(pratica.getProtocollo());
		cartiglio.setAnnoFascicolo(pratica.getAnnoRiferimento());
		cartiglio.setDataRicezione(pratica.getDataRicezione());
		if(pratica.getIdStatoPratica() != null)
			cartiglio.setStato(pratica.getIdStatoPratica().getDescrizione());
		cartiglio.setCivilia(civiliaService.isCivilia(pratica));
		cartiglio.setCodiceInterno(pratica.getIdPratica());
		cartiglio.setNumeroPratica(pratica.getNumeroPratica());
		
		if(civiliaService.isCivilia(pratica)) {
			PraticheEventi first = getFirstRICEvent(pratica.getPraticheEventiList());
			if(first != null && first.getProtocollo() != null && first.getProtocollo().length() > 0) {
				cartiglio.setNumeroProtocollo(first.getProtocollo());
				cartiglio.setDataProtocollo(first.getDataProtocollo());
			}else {
				cartiglio.setNumeroProtocollo(pratica.getProtocollo());
				cartiglio.setDataProtocollo(pratica.getDataRicezione());
			}
			Anagrafica professional = getPersonalDataByCode(pratica.getPraticaAnagraficaList(), PROFESSIONAL_ROLE_CODE);
			if(professional != null) 
				cartiglio.setProcuratore(mapper.map(professional, AnagraficaDTO.class));
			
			Anagrafica instructor = getPersonalDataByCode(pratica.getPraticaAnagraficaList(), INSTRUCTOR_ROLE_CODE);
			if(instructor != null) 
				cartiglio.setAssegnatario(mapper.map(instructor, AnagraficaDTO.class));
			
			cartiglio.setOggettoIntervento(pratica.getOggettoPratica());
		}
		else {
			if(!Constants.CARICAMENTO_MANUALE.equalsIgnoreCase(pratica.getIdStaging().getTipoMessaggio()))
				cartiglio.setCodiceFrontOffice(pratica.getIdentificativoPratica());
			PraticheEventi first = getFirstRICEvent(pratica.getPraticheEventiList());
			if(first != null) {
				cartiglio.setNumeroProtocollo(first.getProtocollo());
				cartiglio.setDataProtocollo(first.getDataProtocollo());
			}
			PraticaProcuratore technician = praticaProcuratoreDao.getActiveTechnicianAgent(pratica.getIdPratica());
			if(technician != null)
				cartiglio.setProcuratore(mapper.map(technician.getAnagrafica(), AnagraficaDTO.class));
			
			if(pratica.getIdUtente() != null) {
				AnagraficaDTO anagraficaDTO = new AnagraficaDTO();
				anagraficaDTO.setCognome(pratica.getIdUtente().getCognome());
				anagraficaDTO.setNome(pratica.getIdUtente().getNome());
				cartiglio.setAssegnatario(anagraficaDTO);
			}
			cartiglio.setOggettoIntervento(pratica.getDescrizioneIntervento());
			
			cartiglio.setTipologiaIntervento(extractTipologiaIntervento(pratica.getPraticaOperazioni()));
		}
		cartiglio.setSuapData(SUAP_INSTITUTION.contains(pratica.getIdProcEnte().getIdEnte().getIdEnte()));
		cartiglio.setNumeroProtocolloSuap(pratica.getProt_suap());
		cartiglio.setDataProtocolloSuap(pratica.getData_prot_suap());
	
		if(pratica.getIdModello() != null)
			cartiglio.setIdModello(pratica.getIdModello().getId());
		return cartiglio;
	}

	private PraticheEventi getFirstRICEvent(List<PraticheEventi> praticheEventi ) {
		PraticheEventi first = null;

		if(praticheEventi != null) {
			
			for(PraticheEventi praticaEvento: praticheEventi) {
				if(praticaEvento.getIdEvento() != null 
						&& "RIC".equals(praticaEvento.getIdEvento().getCodEvento()) 
						&& (first == null || first.getDataEvento().compareTo(praticaEvento.getDataEvento())>0)) 
					first = praticaEvento;
			}
		}
		return first;
	}


	
	private Anagrafica getPersonalDataByCode(List<PraticaAnagrafica> anagrafiche, Integer role) {
		int i = -1;
		boolean found = false;
		PraticaAnagrafica professional = null;
		while(++i < anagrafiche.size() && !found) {
			professional = anagrafiche.get(i);
			found = role.equals(professional.getLkTipoRuolo().getIdTipoRuolo());
		}
		
		return found ? professional.getAnagrafica() : null;
	}

	private Anagrafica getTechnicianAgent(List<PraticaAnagrafica> anagrafiche, String taxCode) {
		int i = -1;
		boolean found = false;
		Anagrafica person = null;
		while(++i < anagrafiche.size() && !found) {
			person = anagrafiche.get(i).getAnagrafica();
			found = taxCode.equalsIgnoreCase(person.getCodiceFiscale()) && 
					'F' == person.getTipoAnagrafica() && 
					technicianAgentTypes.contains(anagrafiche.get(i).getLkTipoRuolo().getIdTipoRuolo());
		}
		
		return found ? person : null;
	}
	
	private List<String> extractTipologiaIntervento(List<PraticaOperazione> praticaOperazioni) {
		List<String> ret = new ArrayList<>();
		for(PraticaOperazione praticaOperazione: praticaOperazioni) {
			String code = praticaOperazione.getOperazione().getCodice();
			if(code != null && code.trim().length() > 0)
				ret.add(praticaOperazione.getOperazione().getDescrizione());
		}
		return ret;
	}	
}
