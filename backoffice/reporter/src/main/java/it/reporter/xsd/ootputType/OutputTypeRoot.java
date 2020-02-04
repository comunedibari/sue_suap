package it.reporter.xsd.ootputType;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder={"outputFormat", "outputPath"})
@XmlRootElement(name="outputTypeRoot")
public class OutputTypeRoot
{
  @XmlElement(required=true)
  protected String outputFormat;
  protected OutputPath outputPath;
  
  public String getOutputFormat()
  {
    return this.outputFormat;
  }
  
  public void setOutputFormat(String value)
  {
    this.outputFormat = value;
  }
  
  public OutputPath getOutputPath()
  {
    return this.outputPath;
  }
  
  public void setOutputPath(OutputPath value)
  {
    this.outputPath = value;
  }
  
  @XmlAccessorType(XmlAccessType.FIELD)
  @XmlType(name="", propOrder={"path", "name"})
  public static class OutputPath
  {
    @XmlElement(required=true)
    protected String path;
    @XmlElement(required=true)
    protected String name;
    
    public String getPath()
    {
      return this.path;
    }
    
    public void setPath(String value)
    {
      this.path = value;
    }
    
    public String getName()
    {
      return this.name;
    }
    
    public void setName(String value)
    {
      this.name = value;
    }
  }
}
