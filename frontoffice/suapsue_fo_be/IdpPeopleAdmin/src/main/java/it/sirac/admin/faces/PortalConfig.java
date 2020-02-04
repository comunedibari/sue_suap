package it.sirac.admin.faces;

import it.sirac.admin.common.PageProperties;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Properties;

public class PortalConfig
{
  private Properties properties = null;
  private HashMap pageMap = null;
  
  public void setPortalID(String idcomune)
  {
    this.properties.setProperty("idcomune", idcomune);
  }
  
  public void setSuffixCADomain(String suffixCADomain)
  {
    this.properties.setProperty("username.suffix", suffixCADomain);
  }
  
  public PortalConfig()
  {
    this.properties = new Properties();
    try
    {
      this.properties.load(getClass().getResource("/properties/app.properties").openStream());
      buildPageMap();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  
  public String getPortalID()
  {
    try
    {
      return (String)this.properties.get("idcomune");
    }
    catch (Exception e) {}
    return "";
  }
  
  public String getSuffixCADomain()
  {
    try
    {
      return (String)this.properties.get("username.suffix");
    }
    catch (Exception e) {}
    return "";
  }
  
  public String save()
  {
    try
    {
      this.properties.store(new FileOutputStream(getClass().getResource("/properties/app.properties").getFile()), null);
      return "success";
    }
    catch (Exception e) {}
    return "failed";
  }
  
  private void buildPageMap()
  {
    this.pageMap = new HashMap();
    
    int pageCount = Integer.parseInt(this.properties.getProperty("viewCount"));
    for (int i = 1; i <= pageCount; i++)
    {
      PageProperties page = new PageProperties();
      
      page.setName(this.properties.getProperty("name" + i));
      page.setModelClassName(this.properties.getProperty("modelClassName" + i));
      page.setDaoClassName(this.properties.getProperty("daoClassName" + i));
      page.setDetailsPage(this.properties.getProperty("details" + i));
      page.setFilterPage(this.properties.getProperty("filter" + i));
      page.setIndex(i);
      
      this.pageMap.put(new Integer(i), page);
    }
  }
  
  public HashMap getPageMap()
  {
    return this.pageMap;
  }
}
