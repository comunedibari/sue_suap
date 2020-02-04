package it.reporter.xsd.data;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder={"connections", "definitions", "queryes"})
@XmlRootElement(name="documentRoot")
public class DocumentRoot
{
  protected Connections connections;
  protected Definitions definitions;
  protected Queryes queryes;
  
  public Connections getConnections()
  {
    return this.connections;
  }
  
  public void setConnections(Connections value)
  {
    this.connections = value;
  }
  
  public Definitions getDefinitions()
  {
    return this.definitions;
  }
  
  public void setDefinitions(Definitions value)
  {
    this.definitions = value;
  }
  
  public Queryes getQueryes()
  {
    return this.queryes;
  }
  
  public void setQueryes(Queryes value)
  {
    this.queryes = value;
  }
  
  @XmlAccessorType(XmlAccessType.FIELD)
  @XmlType(name="", propOrder={"connectionString", "connectionJNDI", "connectionFileExt"})
  public static class Connections
  {
    protected ConnectionString connectionString;
    protected String connectionJNDI;
    protected String connectionFileExt;
    
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
    
    public String getConnectionFileExt()
    {
      return this.connectionFileExt;
    }
    
    public void setConnectionFileExt(String value)
    {
      this.connectionFileExt = value;
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
  
  @XmlAccessorType(XmlAccessType.FIELD)
  @XmlType(name="", propOrder={"metaDato"})
  public static class Definitions
  {
    protected List<MetaDato> metaDato;
    
    public List<MetaDato> getMetaDato()
    {
      if (this.metaDato == null) {
        this.metaDato = new ArrayList();
      }
      return this.metaDato;
    }
    
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"nomeSimbolico", "tipo", "descrizione", "path", "format", "ifNull"})
    public static class MetaDato
    {
      @XmlElement(required=true)
      protected String nomeSimbolico;
      @XmlElement(required=true)
      protected String tipo;
      @XmlElementRef(name="descrizione", type=JAXBElement.class, required=false)
      protected JAXBElement<String> descrizione;
      @XmlElementRef(name="path", type=JAXBElement.class, required=false)
      protected JAXBElement<String> path;
      @XmlElementRef(name="format", type=JAXBElement.class, required=false)
      protected JAXBElement<String> format;
      @XmlElementRef(name="ifNull", type=JAXBElement.class, required=false)
      protected JAXBElement<String> ifNull;
      
      public String getNomeSimbolico()
      {
        return this.nomeSimbolico;
      }
      
      public void setNomeSimbolico(String value)
      {
        this.nomeSimbolico = value;
      }
      
      public String getTipo()
      {
        return this.tipo;
      }
      
      public void setTipo(String value)
      {
        this.tipo = value;
      }
      
      public JAXBElement<String> getDescrizione()
      {
        return this.descrizione;
      }
      
      public void setDescrizione(JAXBElement<String> value)
      {
        this.descrizione = value;
      }
      
      public JAXBElement<String> getPath()
      {
        return this.path;
      }
      
      public void setPath(JAXBElement<String> value)
      {
        this.path = value;
      }
      
      public JAXBElement<String> getFormat()
      {
        return this.format;
      }
      
      public void setFormat(JAXBElement<String> value)
      {
        this.format = value;
      }
      
      public JAXBElement<String> getIfNull()
      {
        return this.ifNull;
      }
      
      public void setIfNull(JAXBElement<String> value)
      {
        this.ifNull = value;
      }
    }
  }
  
  @XmlAccessorType(XmlAccessType.FIELD)
  @XmlType(name="", propOrder={"query"})
  public static class Queryes
  {
    protected List<Query> query;
    
    public List<Query> getQuery()
    {
      if (this.query == null) {
        this.query = new ArrayList();
      }
      return this.query;
    }
    
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"value"})
    public static class Query
    {
      @XmlValue
      protected String value;
      @XmlAttribute(name="id")
      protected String id;
      @XmlAttribute(name="child")
      protected String child;
      
      public String getValue()
      {
        return this.value;
      }
      
      public void setValue(String value)
      {
        this.value = value;
      }
      
      public String getId()
      {
        return this.id;
      }
      
      public void setId(String value)
      {
        this.id = value;
      }
      
      public String getChild()
      {
        return this.child;
      }
      
      public void setChild(String value)
      {
        this.child = value;
      }
    }
  }
}
