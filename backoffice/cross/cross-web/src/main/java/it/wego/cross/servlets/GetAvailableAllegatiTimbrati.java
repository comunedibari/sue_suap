package it.wego.cross.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.wego.cross.plugins.GestioneProtocolloRuvo;

@WebServlet("/GetAvailableAllegatiTimbrati")
public class GetAvailableAllegatiTimbrati extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(GetAvailableAllegatiTimbrati.class.getName());

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String message = "KO";
		
		String nomefileZippato = request.getParameter("nomefile").trim();
		
		//nomefileZippato = "C:/usr/local/pratica_allegati_protocollati_291850_.zip";
		
		GestioneProtocolloRuvo protocolloRuvo = new GestioneProtocolloRuvo();
		
		byte[] stampedDocument = protocolloRuvo.recuperaZipfileTimbrati(nomefileZippato);
		logger.info("GetAvailableAllegatiTimbrati - recuperaZipfileTimbrati length file:" + 
				(stampedDocument != null ? stampedDocument.length : 0));
		
		if(stampedDocument != null) {
			message = "OK";
		} else {
			message = "KO";			
		}
        response.setContentType("text/html");
        response.getWriter().write(message);
	}
}