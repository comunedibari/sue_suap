package it.reporter.xsd.inputParameters;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder={"inputParameters"})
@XmlRootElement(name="inputParametersRoot")
public class InputParametersRoot
{
  protected List<InputParameters> inputParameters;
  
  public List<InputParameters> getInputParameters()
  {
    if (this.inputParameters == null) {
      this.inputParameters = new ArrayList();
    }
    return this.inputParameters;
  }
  
  @XmlAccessorType(XmlAccessType.FIELD)
  @XmlType(name="", propOrder={"name", "value", "format"})
  public static class InputParameters
  {
    @XmlElement(required=true)
    protected String name;
    @XmlElement(required=true)
    protected String value;
    @XmlElementRef(name="format", type=JAXBElement.class, required=false)
    protected JAXBElement<String> format;
    
    public String getName()
    {
      return this.name;
    }
    
    public void setName(String value)
    {
      this.name = value;
    }
    
    public String getValue()
    {
      return this.value;
    }
    
    public void setValue(String value)
    {
      this.value = value;
    }
    
    public JAXBElement<String> getFormat()
    {
      return this.format;
    }
    
    public void setFormat(JAXBElement<String> value)
    {
      this.format = value;
    }
  }
}
