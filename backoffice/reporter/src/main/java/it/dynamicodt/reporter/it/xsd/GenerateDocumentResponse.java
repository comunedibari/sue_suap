package it.dynamicodt.reporter.it.xsd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder={"_return"})
@XmlRootElement(name="generateDocumentResponse")
public class GenerateDocumentResponse
{
  @XmlElement(name="return", required=true, nillable=true)
  protected byte[] _return;
  
  public byte[] getReturn()
  {
    return this._return;
  }
  
  public void setReturn(byte[] value)
  {
    this._return = value;
  }
}
