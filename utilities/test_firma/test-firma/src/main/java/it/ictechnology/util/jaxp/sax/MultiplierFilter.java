package it.ictechnology.util.jaxp.sax;

import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public abstract class MultiplierFilter extends DefaultHandler
{
	protected final ThrowableHandler throwableHandler;

	public static class ThrowableHandlerImpl implements MultiplierFilter.ThrowableHandler
	{
		private Map errors = new HashMap();

		public ContentHandler handle(Throwable err, ContentHandler handler)
		{
			this.errors.put(handler, err);
			return null;
		}

		public Throwable getError(ContentHandler handler)
		{
			return (Throwable) this.errors.get(handler);
		}
	}

	protected MultiplierFilter(ThrowableHandler handler)
	{
		this.throwableHandler = handler;
	}

	public abstract ListIterator getOutputsListIterator();

	public void characters(char[] ch, int start, int length) throws SAXException
	{
		ContentHandler temp = null;
		for (ListIterator i = getOutputsListIterator(); i.hasNext();)
		{
			try
			{
				temp = (ContentHandler) i.next();
				temp.characters(ch, start, length);
			}
			catch (Throwable err)
			{
				ContentHandler replacement = this.throwableHandler.handle(err, temp);

				if (replacement == null)
				{
					i.remove();
				}
				else
					i.set(replacement);
			}
		}
	}

	public void endDocument() throws SAXException
	{
		ContentHandler temp = null;
		for (ListIterator i = getOutputsListIterator(); i.hasNext();)
		{
			try
			{
				temp = (ContentHandler) i.next();
				temp.endDocument();
			}
			catch (Throwable err)
			{
				ContentHandler replacement = this.throwableHandler.handle(err, temp);

				if (replacement == null)
				{
					i.remove();
				}
				else
					i.set(replacement);
			}
		}
	}

	public void endElement(String namespaceURI, String localName, String qName) throws SAXException
	{
		ContentHandler temp = null;
		for (ListIterator i = getOutputsListIterator(); i.hasNext();)
		{
			try
			{
				temp = (ContentHandler) i.next();
				temp.endElement(namespaceURI, localName, qName);
			}
			catch (Throwable err)
			{
				ContentHandler replacement = this.throwableHandler.handle(err, temp);

				if (replacement == null)
				{
					i.remove();
				}
				else
				{
					i.set(replacement);
				}
			}
		}
	}

	public void endPrefixMapping(String prefix) throws SAXException
	{
		ContentHandler temp = null;
		for (ListIterator i = getOutputsListIterator(); i.hasNext();)
		{
			try
			{
				temp = (ContentHandler) i.next();
				temp.endPrefixMapping(prefix);
			}
			catch (Throwable err)
			{
				ContentHandler replacement = this.throwableHandler.handle(err, temp);

				if (replacement == null)
				{
					i.remove();
				}
				else
					i.set(replacement);
			}
		}
	}

	public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException
	{
		ContentHandler temp = null;
		for (ListIterator i = getOutputsListIterator(); i.hasNext();)
		{
			try
			{
				temp = (ContentHandler) i.next();
				temp.ignorableWhitespace(ch, start, length);
			}
			catch (Throwable err)
			{
				ContentHandler replacement = this.throwableHandler.handle(err, temp);

				if (replacement == null)
				{
					i.remove();
				}
				else
					i.set(replacement);
			}
		}
	}

	public void processingInstruction(String target, String data) throws SAXException
	{
		ContentHandler temp = null;
		for (ListIterator i = getOutputsListIterator(); i.hasNext();)
		{
			try
			{
				temp = (ContentHandler) i.next();
				temp.processingInstruction(target, data);
			}
			catch (Throwable err)
			{
				ContentHandler replacement = this.throwableHandler.handle(err, temp);

				if (replacement == null)
				{
					i.remove();
				}
				else
					i.set(replacement);
			}
		}
	}

	public void setDocumentLocator(Locator locator)
	{
		ContentHandler temp = null;
		for (ListIterator i = getOutputsListIterator(); i.hasNext();)
		{
			try
			{
				temp = (ContentHandler) i.next();
				temp.setDocumentLocator(locator);
			}
			catch (Throwable err)
			{
				ContentHandler replacement = this.throwableHandler.handle(err, temp);

				if (replacement == null)
				{
					i.remove();
				}
				else
					i.set(replacement);
			}
		}
	}

	public void skippedEntity(String name) throws SAXException
	{
		ContentHandler temp = null;
		for (ListIterator i = getOutputsListIterator(); i.hasNext();)
		{
			try
			{
				temp = (ContentHandler) i.next();
				temp.skippedEntity(name);
			}
			catch (Throwable err)
			{
				ContentHandler replacement = this.throwableHandler.handle(err, temp);

				if (replacement == null)
				{
					i.remove();
				}
				else
					i.set(replacement);
			}
		}
	}

	public void startDocument() throws SAXException
	{
		ContentHandler temp = null;
		for (ListIterator i = getOutputsListIterator(); i.hasNext();)
		{
			try
			{
				temp = (ContentHandler) i.next();
				temp.startDocument();
			}
			catch (Throwable err)
			{
				ContentHandler replacement = this.throwableHandler.handle(err, temp);

				if (replacement == null)
				{
					i.remove();
				}
				else
					i.set(replacement);
			}
		}
	}

	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException
	{
		ContentHandler temp = null;
		for (ListIterator i = getOutputsListIterator(); i.hasNext();)
		{
			try
			{
				temp = (ContentHandler) i.next();
				temp.startElement(namespaceURI, localName, qName, atts);
			}
			catch (Throwable err)
			{
				ContentHandler replacement = this.throwableHandler.handle(err, temp);

				if (replacement == null)
				{
					i.remove();
				}
				else
					i.set(replacement);
			}
		}
	}

	public void startPrefixMapping(String prefix, String uri) throws SAXException
	{
		ContentHandler temp = null;
		for (ListIterator i = getOutputsListIterator(); i.hasNext();)
		{
			try
			{
				temp = (ContentHandler) i.next();
				temp.startPrefixMapping(prefix, uri);
			}
			catch (Throwable err)
			{
				ContentHandler replacement = this.throwableHandler.handle(err, temp);

				if (replacement == null)
				{
					i.remove();
				}
				else
					i.set(replacement);
			}
		}
	}

	public ThrowableHandler getThrowableHandler()
	{
		return this.throwableHandler;
	}

	public static abstract interface ThrowableHandler
	{
		public abstract ContentHandler handle(Throwable paramThrowable, ContentHandler paramContentHandler);
	}
}