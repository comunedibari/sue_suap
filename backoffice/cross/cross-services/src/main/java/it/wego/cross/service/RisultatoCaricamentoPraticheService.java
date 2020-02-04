package it.wego.cross.service;

import it.wego.cross.entity.RisultatoCaricamentoPratiche;

import java.util.List;

import org.springframework.stereotype.Service;

import it.wego.cross.dto.RisultatoCaricamentoPraticheDTO;
import it.wego.cross.dto.filters.Filter;
@Service
public interface RisultatoCaricamentoPraticheService {

	


    public void inserisci(RisultatoCaricamentoPratiche RisultatoCaricamentoPratiche) throws Exception;

    public void aggiorna(RisultatoCaricamentoPratiche RisultatoCaricamentoPratiche) throws Exception;

    public Long countRisultatoCaricamentoPratiche();

    public RisultatoCaricamentoPraticheDTO findRisultatoCaricamentoPraticheById(Integer id);

	public RisultatoCaricamentoPratiche findRisultatoCaricamentoPraticheByIdentificativoPratica(
			String identifcativoPratica);

	public RisultatoCaricamentoPraticheDTO findRisultatoCaricamentoPraticheByIdentificativoPratica(Integer id);


	public List<RisultatoCaricamentoPraticheDTO> getRisultatoCaricamentoPratiche();
	
}
