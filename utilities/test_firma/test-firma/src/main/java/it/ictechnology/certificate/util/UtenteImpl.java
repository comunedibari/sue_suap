package it.ictechnology.certificate.util;

import it.ictechnology.certificate.Utente;

public class UtenteImpl implements Utente
{
	private String nome;
	private String cognome;
	private String codiceFiscale;
	private String eMail;

	public class Certificato
	{
		private String stato;
		private String unitaOrganizzativa;
		private String organizzazione;
		private String enteCertificatore;

		public Certificato()
		{
		}

		public String getStato()
		{
			return this.stato;
		}

		public void setStato(String stato)
		{
			this.stato = stato;
		}

		public String getUnitaOrganizzativa()
		{
			return this.unitaOrganizzativa;
		}

		public void setUnitaOrganizzativa(String unitaOrganizzativa)
		{
			this.unitaOrganizzativa = unitaOrganizzativa;
		}

		public String getOrganizzazione()
		{
			return this.organizzazione;
		}

		public void setOrganizzazione(String organizzazione)
		{
			this.organizzazione = organizzazione;
		}

		public String getEnteCertificatore()
		{
			return this.enteCertificatore;
		}

		public void setEnteCertificatore(String enteCertificatore)
		{
			this.enteCertificatore = enteCertificatore;
		}
	}

	private Certificato certificato = new Certificato();

	public String getCodiceFiscale()
	{
		return this.codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale)
	{
		this.codiceFiscale = codiceFiscale;
	}

	public String getCognome()
	{
		return this.cognome;
	}

	public void setCognome(String cognome)
	{
		this.cognome = cognome;
	}

	public String getEmail()
	{
		return this.eMail;
	}

	public void setEmail(String eMail)
	{
		this.eMail = eMail;
	}

	public String getNome()
	{
		return this.nome;
	}

	public void setNome(String nome)
	{
		this.nome = nome;
	}

	public Certificato getCertificato()
	{
		return this.certificato;
	}

	public void setCertificato(Certificato certificato)
	{
		this.certificato = certificato;
	}

	public String toString()
	{
		return "Utente:\n  cf      = " + this.codiceFiscale + "\n  nome    = " + this.nome + "\n  cognome = " + this.cognome + "\n  email   = " + this.eMail
				+ (this.certificato == null ? "" : new StringBuffer().append("\n   stato = ").append(this.certificato.stato).append("\n   unitaOrganizzativa = ").append(this.certificato.unitaOrganizzativa).append("\n   organizzazione = ").append(this.certificato.organizzazione).append("\n   enteCertificatore = ").append(this.certificato.enteCertificatore).toString());
	}
}