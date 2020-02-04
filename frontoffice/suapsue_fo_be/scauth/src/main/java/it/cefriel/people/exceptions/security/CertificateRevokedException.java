package it.cefriel.people.exceptions.security;

public class CertificateRevokedException
  extends Exception
{
  public CertificateRevokedException(String message)
  {
    super(message);
  }
}

