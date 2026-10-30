package it.wego.cross.webservices.utils;

import org.springframework.stereotype.Service;

import it.wego.cross.entity.LkTipoPratica;
import it.wego.cross.entity.Pratica;

@Service
public interface CustomPraticaHandler {
	
	LkTipoPratica searchNewType(Pratica pratica);
}
