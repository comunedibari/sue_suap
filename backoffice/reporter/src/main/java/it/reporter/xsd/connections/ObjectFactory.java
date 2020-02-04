package it.reporter.xsd.connections;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory
{
  public ConnectionsRoot createConnectionsRoot()
  {
    return new ConnectionsRoot();
  }
  
  public ConnectionsRoot.ConnectionString createConnectionsRootConnectionString()
  {
    return new ConnectionsRoot.ConnectionString();
  }
}
