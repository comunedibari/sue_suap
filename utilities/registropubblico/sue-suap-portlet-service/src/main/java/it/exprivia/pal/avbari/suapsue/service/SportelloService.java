package it.exprivia.pal.avbari.suapsue.service;

import it.exprivia.pal.avbari.suapsue.dto.SportelloEnte;
import it.exprivia.pal.avbari.suapsue.dto.TipoPratica;

import java.sql.SQLException;
import java.util.Collection;

public interface SportelloService {
	
	Collection<SportelloEnte> findAll() throws SQLException;
	
	Collection<SportelloEnte> findByTipo(TipoPratica tipo) throws SQLException;
	
	SportelloEnte findById(long id) throws SQLException;

	void updateStatoSportello(int en, boolean st) throws SQLException;

	SportelloEnte findByTipoAndComuneEgov(int parseInt, TipoPratica tipo) throws SQLException;

	Collection<SportelloEnte> findByTipoAndStato(TipoPratica tipo, boolean flag_attivo) throws SQLException;
}