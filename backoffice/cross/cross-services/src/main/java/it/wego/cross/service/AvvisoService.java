package it.wego.cross.service;

import it.wego.cross.entity.Avviso;

import java.util.Date;
import java.util.List;

public interface AvvisoService {

	public void creaAvviso(Avviso avviso) throws Exception;

	public void updateAvviso(Avviso avviso) throws Exception;

	public void deleteAvviso(Avviso avviso) throws Exception;

	public List<Avviso> findAllAvvisi() throws Exception;

	public List<Avviso> findAvvisiNonScaduti(Date dataAttuale) throws Exception;

	public Avviso findbyIdAvvisi(int idAvviso) throws Exception;


}
