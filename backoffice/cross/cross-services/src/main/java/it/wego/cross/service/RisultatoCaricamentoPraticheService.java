package it.wego.cross.service;

import java.util.List;

import org.springframework.stereotype.Service;

import it.wego.cross.dto.RisultatoCaricamentoPraticheDTO;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.RisultatoCaricamentoPratiche;

@Service
public interface RisultatoCaricamentoPraticheService {

    public void inserisci(RisultatoCaricamentoPratiche risultatoCaricamentoPratiche) throws Exception;

    public void aggiorna(RisultatoCaricamentoPratiche risultatoCaricamentoPratiche) throws Exception;

    public Long countRisultatoCaricamentoPratiche();

    public RisultatoCaricamentoPraticheDTO findRisultatoCaricamentoPraticheById(Integer id);

	public RisultatoCaricamentoPratiche findRisultatoCaricamentoPraticheByIdentificativoPratica(String identifcativoPratica);

	public RisultatoCaricamentoPraticheDTO findRisultatoCaricamentoPraticheByIdentificativoPratica(Integer id);

	public RisultatoCaricamentoPraticheDTO findRisultatoCaricamentoPraticheByPratica(Pratica pratica);

	public List<RisultatoCaricamentoPraticheDTO> getRisultatoCaricamentoPratiche();
	
}
