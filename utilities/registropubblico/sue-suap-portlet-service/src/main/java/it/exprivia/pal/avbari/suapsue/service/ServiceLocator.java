package it.exprivia.pal.avbari.suapsue.service;

import java.io.IOException;
import java.sql.SQLException;

import it.exprivia.pal.avbari.suapsue.service.impl.PraticaServiceImpl;
import it.exprivia.pal.avbari.suapsue.service.impl.SportelloServiceDAO;

public class ServiceLocator {	
	
	public static SportelloService getSportelloService() throws SQLException {
		return new SportelloServiceDAO();
	}
	
	public static PraticaService getPraticaService() throws SQLException, IOException {
		return new PraticaServiceImpl();
	}
}