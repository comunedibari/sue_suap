package it.dynamicodt.reporter.it.xsd;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory
{
  public GenerateDocument createGenerateDocument()
  {
    return new GenerateDocument();
  }
  
  public GenerateDocumentResponse createGenerateDocumentResponse()
  {
    return new GenerateDocumentResponse();
  }
}
