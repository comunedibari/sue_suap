package it.dynamicodt.reporter.it.xsd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder={"odtTemplate", "xmlData", "xmlStaticData", "xmlParams", "docOutputType"})
@XmlRootElement(name="generateDocument")
public class GenerateDocument
{
  @XmlElement(required=true)
  protected byte[] odtTemplate;
  @XmlElement(required=true)
  protected byte[] xmlData;
  @XmlElement(required=true)
  protected byte[] xmlStaticData;
  @XmlElement(required=true)
  protected byte[] xmlParams;
  @XmlElement(required=true)
  protected byte[] docOutputType;
  
  public byte[] getOdtTemplate()
  {
    return this.odtTemplate;
  }
  
  public void setOdtTemplate(byte[] value)
  {
    this.odtTemplate = value;
  }
  
  public byte[] getXmlData()
  {
    return this.xmlData;
  }
  
  public void setXmlData(byte[] value)
  {
    this.xmlData = value;
  }
  
  public byte[] getXmlStaticData()
  {
    return this.xmlStaticData;
  }
  
  public void setXmlStaticData(byte[] value)
  {
    this.xmlStaticData = value;
  }
  
  public byte[] getXmlParams()
  {
    return this.xmlParams;
  }
  
  public void setXmlParams(byte[] value)
  {
    this.xmlParams = value;
  }
  
  public byte[] getDocOutputType()
  {
    return this.docOutputType;
  }
  
  public void setDocOutputType(byte[] value)
  {
    this.docOutputType = value;
  }
}
