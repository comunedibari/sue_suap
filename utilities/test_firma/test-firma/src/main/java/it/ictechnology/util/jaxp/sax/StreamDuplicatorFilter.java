package it.ictechnology.util.jaxp.sax;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class StreamDuplicatorFilter extends MultiplierFilter
{
	public static final Attributes noAttrs = new AttributesImpl();

	protected ContentHandler[] outs = new ContentHandler[2];

	protected char[] charsBuffer;

	public StreamDuplicatorFilter(ContentHandler out, ContentHandler out2, MultiplierFilter.ThrowableHandler handler)
	{
		super(handler);
		this.outs[0] = out;
		this.outs[1] = out2;
	}

	public StreamDuplicatorFilter(ContentHandler out, ContentHandler out2)
	{
		this(out, out2, new MultiplierFilter.ThrowableHandlerImpl());
	}

	public ContentHandler getOut()
	{
		return this.outs[0];
	}

	public void setOut(ContentHandler out)
	{
		this.outs[0] = out;
	}

	public ContentHandler getOut2()
	{
		return this.outs[1];
	}

	public void setOut2(ContentHandler out)
	{
		this.outs[1] = out;
	}

	public void characters(char[] ch, int start, int length) throws SAXException
	{
		try
		{
			if (this.outs[0] != null)
			{
				this.outs[0].characters(ch, start, length);
			}
		}
		catch (Throwable e)
		{
			this.outs[0] = this.throwableHandler.handle(e, this.outs[0]);
		}
		try
		{
			if (this.outs[1] != null)
			{
				this.outs[1].characters(ch, start, length);
			}
		}
		catch (Throwable e)
		{
			this.outs[1] = this.throwableHandler.handle(e, this.outs[1]);
		}
	}

	public void endDocument() throws SAXException
	{
		try
		{
			if (this.outs[0] != null)
			{
				this.outs[0].endDocument();
			}
		}
		catch (Throwable e)
		{
			this.outs[0] = this.throwableHandler.handle(e, this.outs[0]);
		}
		try
		{
			if (this.outs[1] != null)
			{
				this.outs[1].endDocument();
			}
		}
		catch (Throwable e)
		{
			this.outs[1] = this.throwableHandler.handle(e, this.outs[1]);
		}
	}

	public void endElement(String namespaceURI, String localName, String qName) throws SAXException
	{
		try
		{
			if (this.outs[0] != null)
			{
				this.outs[0].endElement(namespaceURI, localName, qName);
			}
		}
		catch (Throwable e)
		{
			this.outs[0] = this.throwableHandler.handle(e, this.outs[0]);
		}
		try
		{
			if (this.outs[1] != null)
			{
				this.outs[1].endElement(namespaceURI, localName, qName);
			}
		}
		catch (Throwable e)
		{
			this.outs[1] = this.throwableHandler.handle(e, this.outs[1]);
		}
	}

	public void endPrefixMapping(String prefix) throws SAXException
	{
		try
		{
			if (this.outs[0] != null)
			{
				this.outs[0].endPrefixMapping(prefix);
			}
		}
		catch (Throwable e)
		{
			this.outs[0] = this.throwableHandler.handle(e, this.outs[0]);
		}
		try
		{
			if (this.outs[1] != null)
			{
				this.outs[1].endPrefixMapping(prefix);
			}
		}
		catch (Throwable e)
		{
			this.outs[1] = this.throwableHandler.handle(e, this.outs[1]);
		}
	}

	public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException
	{
		try
		{
			if (this.outs[0] != null)
			{
				this.outs[0].ignorableWhitespace(ch, start, length);
			}
		}
		catch (Throwable e)
		{
			this.outs[0] = this.throwableHandler.handle(e, this.outs[0]);
		}
		try
		{
			if (this.outs[1] != null)
			{
				this.outs[1].ignorableWhitespace(ch, start, length);
			}
		}
		catch (Throwable e)
		{
			this.outs[1] = this.throwableHandler.handle(e, this.outs[1]);
		}
	}

	public void processingInstruction(String target, String data) throws SAXException
	{
		try
		{
			if (this.outs[0] != null)
			{
				this.outs[0].processingInstruction(target, data);
			}
		}
		catch (Throwable e)
		{
			this.outs[0] = this.throwableHandler.handle(e, this.outs[0]);
		}
		try
		{
			if (this.outs[1] != null)
			{
				this.outs[1].processingInstruction(target, data);
			}
		}
		catch (Throwable e)
		{
			this.outs[1] = this.throwableHandler.handle(e, this.outs[1]);
		}
	}

	public void setDocumentLocator(Locator locator)
	{
		try
		{
			if (this.outs[0] != null)
			{
				this.outs[0].setDocumentLocator(locator);
			}
		}
		catch (Throwable e)
		{
			this.outs[0] = this.throwableHandler.handle(e, this.outs[0]);
		}
		try
		{
			if (this.outs[1] != null)
			{
				this.outs[1].setDocumentLocator(locator);
			}
		}
		catch (Throwable e)
		{
			this.outs[1] = this.throwableHandler.handle(e, this.outs[1]);
		}
	}

	public void skippedEntity(String name) throws SAXException
	{
		try
		{
			if (this.outs[0] != null)
			{
				this.outs[0].skippedEntity(name);
			}
		}
		catch (Throwable e)
		{
			this.outs[0] = this.throwableHandler.handle(e, this.outs[0]);
		}
		try
		{
			if (this.outs[1] != null)
			{
				this.outs[1].skippedEntity(name);
			}
		}
		catch (Throwable e)
		{
			this.outs[1] = this.throwableHandler.handle(e, this.outs[1]);
		}
	}

	public void startDocument() throws SAXException
	{
		try
		{
			if (this.outs[0] != null)
			{
				this.outs[0].startDocument();
			}
		}
		catch (Throwable e)
		{
			this.outs[0] = this.throwableHandler.handle(e, this.outs[0]);
		}
		try
		{
			if (this.outs[1] != null)
			{
				this.outs[1].startDocument();
			}
		}
		catch (Throwable e)
		{
			this.outs[1] = this.throwableHandler.handle(e, this.outs[1]);
		}
	}

	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException
	{
		try
		{
			if (this.outs[0] != null)
			{
				this.outs[0].startElement(namespaceURI, localName, qName, atts);
			}
		}
		catch (Throwable e)
		{
			this.outs[0] = this.throwableHandler.handle(e, this.outs[0]);
		}
		try
		{
			if (this.outs[1] != null)
			{
				this.outs[1].startElement(namespaceURI, localName, qName, atts);
			}
		}
		catch (Throwable e)
		{
			this.outs[1] = this.throwableHandler.handle(e, this.outs[1]);
		}
	}

	public void startPrefixMapping(String prefix, String uri) throws SAXException
	{
		try
		{
			if (this.outs[0] != null)
			{
				this.outs[0].startPrefixMapping(prefix, uri);
			}
		}
		catch (Throwable e)
		{
			this.outs[0] = this.throwableHandler.handle(e, this.outs[0]);
		}
		try
		{
			if (this.outs[1] != null)
			{
				this.outs[1].startPrefixMapping(prefix, uri);
			}
		}
		catch (Throwable e)
		{
			this.outs[1] = this.throwableHandler.handle(e, this.outs[1]);
		}
	}

	public void characters(String s) throws SAXException
	{
		if (s != null)
		{
			if ((this.charsBuffer == null) || (this.charsBuffer.length < s.length()))
			{
				this.charsBuffer = new char[s.length()];
			}
			s.getChars(0, s.length(), this.charsBuffer, 0);

			try
			{
				if (this.outs[0] != null)
				{
					this.outs[0].characters(this.charsBuffer, 0, s.length());
				}
			}
			catch (Throwable e)
			{
				this.outs[0] = this.throwableHandler.handle(e, this.outs[0]);
			}
			try
			{
				if (this.outs[1] != null)
				{
					this.outs[1].characters(this.charsBuffer, 0, s.length());
				}
			}
			catch (Throwable e)
			{
				this.outs[1] = this.throwableHandler.handle(e, this.outs[1]);
			}
		}
	}

	public void element(String nsuri, String lname, String qname, Attributes attrs, String pcdata) throws SAXException
	{
		try
		{
			if (this.outs[0] != null)
			{
				this.outs[0].startElement(nsuri, lname, qname, attrs);
			}
		}
		catch (Throwable e)
		{
			this.outs[0] = this.throwableHandler.handle(e, this.outs[0]);
		}
		try
		{
			if (this.outs[1] != null)
			{
				this.outs[1].startElement(nsuri, lname, qname, attrs);
			}
		}
		catch (Throwable e)
		{
			this.outs[1] = this.throwableHandler.handle(e, this.outs[1]);
		}

		characters(pcdata);

		try
		{
			if (this.outs[0] != null)
			{
				this.outs[0].endElement(nsuri, lname, qname);
			}
		}
		catch (Throwable e)
		{
			this.outs[0] = this.throwableHandler.handle(e, this.outs[0]);
		}
		try
		{
			if (this.outs[1] != null)
			{
				this.outs[1].endElement(nsuri, lname, qname);
			}
		}
		catch (Throwable e)
		{
			this.outs[1] = this.throwableHandler.handle(e, this.outs[1]);
		}
	}

	public ListIterator getOutputsListIterator()
	{
		return Collections.unmodifiableList(Arrays.asList(this.outs)).listIterator();
	}
}