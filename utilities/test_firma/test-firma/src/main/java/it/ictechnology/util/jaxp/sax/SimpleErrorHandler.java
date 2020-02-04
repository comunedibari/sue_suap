package it.ictechnology.util.jaxp.sax;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class SimpleErrorHandler implements ErrorHandler
{
	private static final Logger logger = LoggerFactory.getLogger(SimpleErrorHandler.class);

	private int errors = 0;
	private int fatals = 0;
	private int warnings = 0;

	public boolean isValid()
	{
		return this.errors + this.fatals == 0;
	}

	public void error(SAXParseException exception) throws SAXException
	{
		this.errors += 1;

		logger.debug("", exception);
	}

	public void fatalError(SAXParseException exception) throws SAXException
	{
		this.fatals += 1;

		logger.debug("", exception);
	}

	public void warning(SAXParseException exception) throws SAXException
	{
		this.warnings += 1;

		logger.debug("", exception);
	}

	public int getErrors()
	{
		return this.errors;
	}

	public int getFatals()
	{
		return this.fatals;
	}

	public int getWarnings()
	{
		return this.warnings;
	}
}
