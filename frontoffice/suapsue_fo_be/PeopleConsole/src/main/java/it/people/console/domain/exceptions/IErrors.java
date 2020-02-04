package it.people.console.domain.exceptions;

public abstract interface IErrors
{
  public abstract String getReferer();
  
  public abstract void setReferer(String paramString);
  
  public abstract String getException();
  
  public abstract void setException(String paramString);
  
  public abstract String getMessage();
  
  public abstract void setMessage(String paramString);
  
  public abstract void clear();
}


/* Location:           C:\Users\delpretea\Desktop\PeopleConsole\WEB-INF\classes\
 * Qualified Name:     it.people.console.domain.exceptions.IErrors
 * JD-Core Version:    0.7.0.1
 */