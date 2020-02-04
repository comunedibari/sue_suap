package it.infocamere.util.signature;

import java.security.cert.X509Certificate;

public class Signature
{
	protected String stato = null;
	protected String organizzazione = null;
	protected String unita = null;
	protected String firmatario = null;
	protected String cf = null;
	protected String identificativo = null;
	protected String ente = null;
	protected String validoDal = null;
	protected String validoAl = null;
	protected String seriale = null;
	protected boolean valid = false;
	protected X509Certificate x509Certificate = null;

	public String toString()
	{
		StringBuffer buffer = new StringBuffer();
		buffer.append("Firmatario [\n");
		buffer.append("  Cofice Fiscale: " + this.cf + "\n");
		buffer.append("  Firmatario: " + this.firmatario + "\n");
		buffer.append("  Numero di serie: " + this.seriale + "\n");
		buffer.append("  Certificato valido dal: " + this.validoDal + "\n");
		buffer.append("  Certificato valido al: " + this.validoAl + "\n");
		buffer.append("  Stato: " + this.stato + "\n");
		buffer.append("  Organizzazione: " + this.organizzazione + "\n");
		buffer.append("  Unita' Organizzativa: " + this.unita + "\n");
		buffer.append("  Codice Identificativo: " + this.identificativo + "\n");
		buffer.append("  Ente Certificatore: " + this.ente + "\n");
		buffer.append("  Valido: " + this.valid + "\n");
		buffer.append("]");
		return buffer.toString();
	}

	public boolean isValid()
	{
		return this.valid;
	}

	public void setValid(boolean valid)
	{
		this.valid = valid;
	}

	public String getCf()
	{
		return this.cf;
	}

	public void setCf(String cf)
	{
		this.cf = cf;
	}

	public String getEnte()
	{
		return this.ente;
	}

	public void setEnte(String ente)
	{
		this.ente = ente;
	}

	public String getFirmatario()
	{
		return this.firmatario;
	}

	public void setFirmatario(String firmatario)
	{
		this.firmatario = firmatario;
	}

	public String getIdentificativo()
	{
		return this.identificativo;
	}

	public void setIdentificativo(String identificativo)
	{
		this.identificativo = identificativo;
	}

	public String getOrganizzazione()
	{
		return this.organizzazione;
	}

	public void setOrganizzazione(String organizzazione)
	{
		this.organizzazione = organizzazione;
	}

	public String getSeriale()
	{
		return this.seriale;
	}

	public void setSeriale(String seriale)
	{
		this.seriale = seriale;
	}

	public String getStato()
	{
		return this.stato;
	}

	public void setStato(String stato)
	{
		this.stato = stato;
	}

	public String getUnita()
	{
		return this.unita;
	}

	public void setUnita(String unita)
	{
		this.unita = unita;
	}

	public String getValidoAl()
	{
		return this.validoAl;
	}

	public void setValidoAl(String validoAl)
	{
		this.validoAl = validoAl;
	}

	public String getValidoDal()
	{
		return this.validoDal;
	}

	public void setValidoDal(String validoDal)
	{
		this.validoDal = validoDal;
	}

	public X509Certificate getX509Certificate()
	{
		return this.x509Certificate;
	}

	public void setX509Certificate(X509Certificate x509Certificate)
	{
		this.x509Certificate = x509Certificate;
	}
}