package it.wego.cross.test;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IdPraticaTest {
	private static final Logger logger = LoggerFactory.getLogger(IdPraticaTest.class.getName());
	
	@Test
	public void testRecuperoIdPratica()
	{
		try {
			logger.info(""+getIdPratica("1,945"));
			logger.info(""+getIdPratica("1.945"));
		} catch (Exception e) {
			logger.error("Errore recupero id pratica", e);
		}
	}
	private static Integer getIdPratica(String idPraticaString) throws ParseException
	{
		try
		{
			return Integer.parseInt(idPraticaString);
		}
		catch (NumberFormatException nfe) {
			logger.warn("NumberFormatException nel recupero ID pratica {}", nfe.getMessage());
			return getIdFromString(idPraticaString);
		}
	}
	private static Integer getIdFromString( String idPraticaString ) throws ParseException
	{
		NumberFormat nf = null;
		if( idPraticaString.indexOf(",") > -1 )
		{
			nf = NumberFormat.getInstance(Locale.ENGLISH);
		}
		else
		{
			nf = NumberFormat.getInstance(Locale.ITALIAN);
		}
		return nf.parse(idPraticaString).intValue();
	}
}
