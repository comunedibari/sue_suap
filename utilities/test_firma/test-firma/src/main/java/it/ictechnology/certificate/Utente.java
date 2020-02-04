package it.ictechnology.certificate;

public abstract interface Utente
{
	public abstract String getCodiceFiscale();

	public abstract void setCodiceFiscale(String paramString);

	public abstract String getCognome();

	public abstract void setCognome(String paramString);

	public abstract String getEmail();

	public abstract void setEmail(String paramString);

	public abstract String getNome();

	public abstract void setNome(String paramString);
}