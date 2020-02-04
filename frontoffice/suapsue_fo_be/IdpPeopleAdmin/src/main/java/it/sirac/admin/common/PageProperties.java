package it.sirac.admin.common;

public class PageProperties
{
  private String name;
  private String modelClassName;
  private String daoClassName;
  private String detailsPage;
  private String filterPage;
  private int index;
  
  public String getDaoClassName()
  {
    return this.daoClassName;
  }
  
  public void setDaoClassName(String daoClassName)
  {
    this.daoClassName = daoClassName;
  }
  
  public String getDetailsPage()
  {
    return this.detailsPage;
  }
  
  public void setDetailsPage(String detailsPage)
  {
    this.detailsPage = detailsPage;
  }
  
  public String getFilterPage()
  {
    return this.filterPage;
  }
  
  public void setFilterPage(String filterPage)
  {
    this.filterPage = filterPage;
  }
  
  public String getModelClassName()
  {
    return this.modelClassName;
  }
  
  public void setModelClassName(String modelClassName)
  {
    this.modelClassName = modelClassName;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public int getIndex()
  {
    return this.index;
  }
  
  public void setIndex(int index)
  {
    this.index = index;
  }
}
