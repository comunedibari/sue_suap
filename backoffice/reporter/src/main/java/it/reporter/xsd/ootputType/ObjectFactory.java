package it.reporter.xsd.ootputType;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory
{
  public OutputTypeRoot createOutputTypeRoot()
  {
    return new OutputTypeRoot();
  }
  
  public OutputTypeRoot.OutputPath createOutputTypeRootOutputPath()
  {
    return new OutputTypeRoot.OutputPath();
  }
}
