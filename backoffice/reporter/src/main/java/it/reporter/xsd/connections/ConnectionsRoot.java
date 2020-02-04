package it.reporter.xsd.connections;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder={"connectionString", "connectionJNDI"})
@XmlRootElement(name="connectionsRoot")
public class ConnectionsRoot
{
  protected ConnectionString connectionString;
  protected String connectionJNDI;
  
  public ConnectionString getConnectionString()
  {
    return this.connectionString;
  }
  
  public void setConnectionString(ConnectionString value)
  {
    this.connectionString = value;
  }
  
  public String getConnectionJNDI()
  {
    return this.connectionJNDI;
  }
  
  public void setConnectionJNDI(String value)
  {
    this.connectionJNDI = value;
  }
  
  @XmlAccessorType(XmlAccessType.FIELD)
  @XmlType(name="", propOrder={"driver", "connection", "user", "password"})
  public static class ConnectionString
  {
    @XmlElement(required=true)
    protected String driver;
    @XmlElement(required=true)
    protected String connection;
    @XmlElement(required=true)
    protected String user;
    @XmlElement(required=true)
    protected String password;
    
    public String getDriver()
    {
      return this.driver;
    }
    
    public void setDriver(String value)
    {
      this.driver = value;
    }
    
    public String getConnection()
    {
      return this.connection;
    }
    
    public void setConnection(String value)
    {
      this.connection = value;
    }
    
    public String getUser()
    {
      return this.user;
    }
    
    public void setUser(String value)
    {
      this.user = value;
    }
    
    public String getPassword()
    {
      return this.password;
    }
    
    public void setPassword(String value)
    {
      this.password = value;
    }
  }
}
