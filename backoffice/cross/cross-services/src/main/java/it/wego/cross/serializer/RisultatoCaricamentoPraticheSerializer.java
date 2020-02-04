package it.wego.cross.serializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.wego.cross.dao.UtentiDao;
import it.wego.cross.dto.RisultatoCaricamentoPraticheDTO;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.RisultatoCaricamentoPratiche;
import it.wego.cross.entity.Utente;
import it.wego.cross.service.PraticheService;
import it.wego.cross.service.RisultatoCaricamentoPraticheService;


@Component
public class RisultatoCaricamentoPraticheSerializer {
	@Autowired
	private RisultatoCaricamentoPraticheService risultatoCaricamentoPraticheService;
	
	@Autowired
	private PraticheService praticheService;
	@Autowired
	private UtentiDao utentiDao;
	
	public RisultatoCaricamentoPratiche serialize(RisultatoCaricamentoPraticheDTO rCaricamentoPraticheDTO) throws Exception {
		RisultatoCaricamentoPratiche risultatoCaricamentoPratiche = new RisultatoCaricamentoPratiche();
        if (rCaricamentoPraticheDTO != null) {
             risultatoCaricamentoPratiche = risultatoCaricamentoPraticheService.findRisultatoCaricamentoPraticheByIdentificativoPratica(rCaricamentoPraticheDTO.getIdentifcativoPratica());
            risultatoCaricamentoPratiche.setIdentificativoPratica(rCaricamentoPraticheDTO.getIdentifcativoPratica());
            risultatoCaricamentoPratiche.setDataCaricamento(rCaricamentoPraticheDTO.getDataCaricamento());
            risultatoCaricamentoPratiche.setDescrizioneErrore(rCaricamentoPraticheDTO.getDescrizioneErrore());
            
            if (rCaricamentoPraticheDTO.getIdPratica() != null) {
                Pratica pratica = praticheService.getPratica(rCaricamentoPraticheDTO.getIdPratica());
                risultatoCaricamentoPratiche.setIdPratica(pratica);
            }
            if (rCaricamentoPraticheDTO.getIdUtente() != null) {
                Utente idUtente = utentiDao.findUtenteByIdUtente(rCaricamentoPraticheDTO.getIdUtente());
                risultatoCaricamentoPratiche.setIdUtente(idUtente);
            }
            
        }
        return risultatoCaricamentoPratiche;
    }
	public RisultatoCaricamentoPraticheDTO serialize(RisultatoCaricamentoPratiche risultatoCaricamentoPratiche) {
		RisultatoCaricamentoPraticheDTO dto = new RisultatoCaricamentoPraticheDTO();
        if (risultatoCaricamentoPratiche.getIdentificativoPratica() != null) {
            dto.setIdentificativoPratica(risultatoCaricamentoPratiche.getIdentificativoPratica());
        }
        dto.setDataCaricamento(risultatoCaricamentoPratiche.getDataCaricamento());
        dto.setDescrizioneErrore(risultatoCaricamentoPratiche.getDescrizioneErrore());
        dto.setIdRisultatoCaricamento(risultatoCaricamentoPratiche.getIdRisultatoCaricamento());
        if (risultatoCaricamentoPratiche.getIdPratica() != null) {
            dto.setIdPratica(risultatoCaricamentoPratiche.getIdPratica().getIdPratica());
        }
        if (risultatoCaricamentoPratiche.getIdUtente() != null) {
            dto.setIdUtente(risultatoCaricamentoPratiche.getIdUtente().getIdUtente());
        }
        dto.setEsitoCaricamento(risultatoCaricamentoPratiche.getEsitoCaricamento());
        dto.setNomeFileCaricato(risultatoCaricamentoPratiche.getNomeFileCaricato());
        return dto;
	}
	
}
