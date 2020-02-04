package it.reporter.xsd.data;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory
{
  private static final QName _DocumentRootDefinitionsMetaDatoDescrizione_QNAME = new QName("", "descrizione");
  private static final QName _DocumentRootDefinitionsMetaDatoIfNull_QNAME = new QName("", "ifNull");
  private static final QName _DocumentRootDefinitionsMetaDatoPath_QNAME = new QName("", "path");
  private static final QName _DocumentRootDefinitionsMetaDatoFormat_QNAME = new QName("", "format");
  
  public DocumentRoot createDocumentRoot()
  {
    return new DocumentRoot();
  }
  
  public DocumentRoot.Queryes createDocumentRootQueryes()
  {
    return new DocumentRoot.Queryes();
  }
  
  public DocumentRoot.Definitions createDocumentRootDefinitions()
  {
    return new DocumentRoot.Definitions();
  }
  
  public DocumentRoot.Connections createDocumentRootConnections()
  {
    return new DocumentRoot.Connections();
  }
  
  public DocumentRoot.Queryes.Query createDocumentRootQueryesQuery()
  {
    return new DocumentRoot.Queryes.Query();
  }
  
  public DocumentRoot.Definitions.MetaDato createDocumentRootDefinitionsMetaDato()
  {
    return new DocumentRoot.Definitions.MetaDato();
  }
  
  public DocumentRoot.Connections.ConnectionString createDocumentRootConnectionsConnectionString()
  {
    return new DocumentRoot.Connections.ConnectionString();
  }
  
  @XmlElementDecl(namespace="", name="descrizione", scope=DocumentRoot.Definitions.MetaDato.class)
  public JAXBElement<String> createDocumentRootDefinitionsMetaDatoDescrizione(String value)
  {
    return new JAXBElement(_DocumentRootDefinitionsMetaDatoDescrizione_QNAME, String.class, DocumentRoot.Definitions.MetaDato.class, value);
  }
  
  @XmlElementDecl(namespace="", name="ifNull", scope=DocumentRoot.Definitions.MetaDato.class)
  public JAXBElement<String> createDocumentRootDefinitionsMetaDatoIfNull(String value)
  {
    return new JAXBElement(_DocumentRootDefinitionsMetaDatoIfNull_QNAME, String.class, DocumentRoot.Definitions.MetaDato.class, value);
  }
  
  @XmlElementDecl(namespace="", name="path", scope=DocumentRoot.Definitions.MetaDato.class)
  public JAXBElement<String> createDocumentRootDefinitionsMetaDatoPath(String value)
  {
    return new JAXBElement(_DocumentRootDefinitionsMetaDatoPath_QNAME, String.class, DocumentRoot.Definitions.MetaDato.class, value);
  }
  
  @XmlElementDecl(namespace="", name="format", scope=DocumentRoot.Definitions.MetaDato.class)
  public JAXBElement<String> createDocumentRootDefinitionsMetaDatoFormat(String value)
  {
    return new JAXBElement(_DocumentRootDefinitionsMetaDatoFormat_QNAME, String.class, DocumentRoot.Definitions.MetaDato.class, value);
  }
}
