package it.reporter.xsd.inputParameters;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory
{
  private static final QName _InputParametersRootInputParametersFormat_QNAME = new QName("", "format");
  
  public InputParametersRoot createInputParametersRoot()
  {
    return new InputParametersRoot();
  }
  
  public InputParametersRoot.InputParameters createInputParametersRootInputParameters()
  {
    return new InputParametersRoot.InputParameters();
  }
  
  @XmlElementDecl(namespace="", name="format", scope=InputParametersRoot.InputParameters.class)
  public JAXBElement<String> createInputParametersRootInputParametersFormat(String value)
  {
    return new JAXBElement(_InputParametersRootInputParametersFormat_QNAME, String.class, InputParametersRoot.InputParameters.class, value);
  }
}
