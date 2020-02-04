package it.ictechnology.util.jaxp.sax;

import java.io.IOException;
import java.io.Writer;

import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.collections.ArrayStack;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class SAXUtility
{
	private static final Logger logger = LoggerFactory.getLogger(SAXUtility.class);

	private static final ArrayStack N_NV_READER_FREE_LIST = new ArrayStack();
	private static final ArrayStack N_V_READER_FREE_LIST = new ArrayStack();
	private static final ArrayStack NN_NV_READER_FREE_LIST = new ArrayStack();
	private static final ArrayStack NN_V_READER_FREE_LIST = new ArrayStack();
	private static final ArrayStack N_NV_READER_BUSY_LIST = new ArrayStack();
	private static final ArrayStack N_V_READER_BUSY_LIST = new ArrayStack();
	private static final ArrayStack NN_NV_READER_BUSY_LIST = new ArrayStack();
	private static final ArrayStack NN_V_READER_BUSY_LIST = new ArrayStack();

	private static final ThreadLocal N_NV_READER = new ThreadLocal()
	{
		protected Object initialValue()
		{
			try
			{
				SAXParserFactory parserFactory = SAXParserFactory.newInstance();

				parserFactory.setNamespaceAware(true);
				parserFactory.setValidating(false);

				return parserFactory.newSAXParser().getXMLReader();
			}
			catch (Exception e)
			{
				SAXUtility.logger.error("Cannot allocate XMLReader: " + e);
				throw new RuntimeException(e);
			}
		}
	};
	private static final ThreadLocal N_V_READER = new ThreadLocal()
	{
		protected Object initialValue()
		{
			try
			{
				SAXParserFactory parserFactory = SAXParserFactory.newInstance();

				parserFactory.setNamespaceAware(true);
				parserFactory.setValidating(true);

				return parserFactory.newSAXParser().getXMLReader();
			}
			catch (Exception e)
			{
				SAXUtility.logger.error("Cannot allocate XMLReader: " + e);
				throw new RuntimeException(e);
			}
		}
	};
	private static final ThreadLocal NN_NV_READER = new ThreadLocal()
	{
		protected Object initialValue()
		{
			try
			{
				SAXParserFactory parserFactory = SAXParserFactory.newInstance();

				parserFactory.setNamespaceAware(false);
				parserFactory.setValidating(false);

				return parserFactory.newSAXParser().getXMLReader();
			}
			catch (Exception e)
			{
				SAXUtility.logger.error("Cannot allocate XMLReader: " + e);
				throw new RuntimeException(e);
			}
		}
	};
	private static final ThreadLocal NN_V_READER = new ThreadLocal()
	{
		protected Object initialValue()
		{
			try
			{
				SAXParserFactory parserFactory = SAXParserFactory.newInstance();

				parserFactory.setNamespaceAware(false);
				parserFactory.setValidating(true);

				return parserFactory.newSAXParser().getXMLReader();
			}
			catch (Exception e)
			{
				SAXUtility.logger.error("Cannot allocate XMLReader: " + e);
				throw new RuntimeException(e);
			}
		}
	};

	/**
	 * @deprecated
	 */
	public static XMLReader getXMLReader(boolean nameSpaceAware, boolean validating)
	{
		if ((nameSpaceAware) && (!validating))
			return (XMLReader) N_NV_READER.get();
		if ((nameSpaceAware) && (validating))
			return (XMLReader) N_V_READER.get();
		if ((!nameSpaceAware) && (!validating))
		{
			return (XMLReader) NN_NV_READER.get();
		}
		return (XMLReader) NN_V_READER.get();
	}

	/**
	 * @deprecated
	 */
	public static XMLReader getValidatingXMLReader(boolean nameSpaceAware)
	{
		XMLReader result = getXMLReader(nameSpaceAware, true);

		result.setErrorHandler(new SimpleErrorHandler());
		result.setEntityResolver(new URLEntityResolver());

		return result;
	}

	public static StreamDuplicatorFilter createSerializingStub(ContentHandler target, Writer writer, int indent)
	{
		OutputFormat of = new OutputFormat();
		XMLSerializer ser = new XMLSerializer(writer, of);
		StreamDuplicatorFilter duplicator = new StreamDuplicatorFilter(ser, target);

		of.setIndent(indent);

		return duplicator;
	}

	public static XMLReader retrieveXMLReader(boolean nameSpaceAware, boolean validating)
	{
		ArrayStack listaLiberi = null;
		ArrayStack listaOccupati = null;

		if ((nameSpaceAware) && (!validating))
		{
			listaLiberi = N_NV_READER_FREE_LIST;
			listaOccupati = N_NV_READER_BUSY_LIST;
		}
		else if ((nameSpaceAware) && (validating))
		{
			listaLiberi = N_V_READER_FREE_LIST;
			listaOccupati = N_V_READER_BUSY_LIST;
		}
		else if ((!nameSpaceAware) && (!validating))
		{
			listaLiberi = NN_NV_READER_FREE_LIST;
			listaOccupati = NN_NV_READER_BUSY_LIST;
		}
		else
		{
			listaLiberi = NN_V_READER_FREE_LIST;
			listaOccupati = NN_V_READER_BUSY_LIST;
		}

		synchronized (listaLiberi)
		{
			XMLReader ret = null;

			if (listaLiberi.isEmpty())
			{
				try
				{

					SAXParserFactory parserFactory = SAXParserFactory.newInstance();

					parserFactory.setNamespaceAware(nameSpaceAware);
					parserFactory.setValidating(validating);

					ret = parserFactory.newSAXParser().getXMLReader();
				}
				catch (Exception e)
				{
					logger.error("Cannot allocate XMLReader: " + e);
					throw new RuntimeException(e);
				}

			}
			else
			{
				ret = (XMLReader) listaLiberi.pop();
			}

			listaOccupati.push(ret);

			return ret;
		}
	}

	public static void releaseXMLReader(XMLReader reader)
	{
		if (reader == null)
		{
			return;
		}
		synchronized (N_NV_READER_FREE_LIST)
		{
			if (N_NV_READER_BUSY_LIST.remove(reader))
			{
				N_NV_READER_FREE_LIST.push(reader);
				return;
			}
		}
		synchronized (N_V_READER_FREE_LIST)
		{
			if (N_V_READER_BUSY_LIST.remove(reader))
			{
				N_V_READER_FREE_LIST.push(reader);
				return;
			}
		}
		synchronized (NN_NV_READER_FREE_LIST)
		{
			if (NN_NV_READER_BUSY_LIST.remove(reader))
			{
				NN_NV_READER_FREE_LIST.push(reader);
				return;
			}
		}
		synchronized (NN_V_READER_FREE_LIST)
		{
			if (NN_V_READER_BUSY_LIST.remove(reader))
			{
				NN_V_READER_FREE_LIST.push(reader);
				return;
			}
		}

		throw new IllegalArgumentException("Unknow XMLReader cannot be released");
	}

	public static void parse(boolean nameSpaceAware, boolean validating, InputSource inputSource, ContentHandler out) throws IOException, SAXException
	{
		parse(nameSpaceAware, validating, inputSource, out, null, null, null);
	}

	public static void parse(boolean nameSpaceAware, boolean validating, InputSource inputSource, ContentHandler out, DTDHandler dtdHandler, EntityResolver entityResolver, ErrorHandler errorHandler) throws IOException, SAXException
	{
		XMLReader parser = null;

		try
		{
			parser = retrieveXMLReader(nameSpaceAware, validating);

			parser.setContentHandler(out);
			if (dtdHandler != null)
				parser.setDTDHandler(dtdHandler);
			if (entityResolver != null)
				parser.setEntityResolver(entityResolver);
			if (errorHandler != null)
			{
				parser.setErrorHandler(errorHandler);
			}

			parser.parse(inputSource);
		}
		finally
		{
			releaseXMLReader(parser);
		}
	}
}