package it.exprivia.pal.avbari.suapsue.service;

import it.exprivia.pal.avbari.suapsue.dto.FiltriRicercaPratica;
import it.exprivia.pal.avbari.suapsue.dto.Pratica;
import it.wego.cross.PraticaSIT;

import java.rmi.RemoteException;
import java.util.Collection;

public interface PraticaService {
	
	Collection<Pratica> find(final FiltriRicercaPratica filtri) throws RemoteException;
	
	PraticaSIT findById(final Long idPratica ) throws RemoteException;
}