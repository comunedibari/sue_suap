package it.sirac.admin.sqlmap.capeople.comuni;

import it.sirac.admin.faces.Manager;
import it.sirac.admin.sqlmap.common.FilterBean;
import java.util.Map;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

public class ComuniFilterBean
  extends FilterBean
{
  private String fromComune;
  private String fromCap;
  private String toComune;
  private String toCap;
  private String typeComune;
  private String typeCap;
  
  public void filter()
  {
    Manager tableManager = (Manager)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tableManager");
    if (tableManager != null)
    {
      ComuniDAO comuniDAO = (ComuniDAO)tableManager.getTableAction();
      
      String whereClause = "";
      if ((this.typeComune.equals("0")) && (this.typeCap.equals("0"))) {
        return;
      }
      String comuneClause = textFilterBuilder("comune", this.typeComune, this.fromComune, this.toComune + "zzz");
      String capClause = textFilterBuilder("cap", this.typeCap, this.fromCap, this.toCap + "99999");
      
      whereClause = whereClause + comuneClause;
      if ((comuneClause != null) && (capClause != null) && (!comuneClause.equals("")) && (!capClause.equals(""))) {
        whereClause = whereClause + " AND ";
      }
      whereClause = whereClause + capClause;
      
      comuniDAO.setWhereClause(whereClause);
    }
  }
  
  public String cancel()
  {
    this.fromComune = "";
    this.fromCap = "";
    this.toComune = "";
    this.toCap = "";
    this.typeComune = "";
    this.typeCap = "";
    Manager tableManager = (Manager)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tableManager");
    if (tableManager != null)
    {
      ComuniDAO comuniDAO = (ComuniDAO)tableManager.getTableAction();
      comuniDAO.setWhereClause(null);
    }
    return "reset";
  }
  
  public void typeComuneChanged(ValueChangeEvent e)
  {
    this.typeComune = ((String)e.getNewValue());
  }
  
  public void typeCapChanged(ValueChangeEvent e)
  {
    this.typeCap = ((String)e.getNewValue());
  }
  
  public String getTypeComune()
  {
    return this.typeComune;
  }
  
  public String getTypeCap()
  {
    return this.typeCap;
  }
  
  public String getFromCap()
  {
    return this.fromCap;
  }
  
  public void setFromCap(String fromCap)
  {
    this.fromCap = fromCap;
  }
  
  public String getFromComune()
  {
    return this.fromComune;
  }
  
  public void setFromComune(String fromComune)
  {
    this.fromComune = fromComune;
  }
  
  public String getToCap()
  {
    return this.toCap;
  }
  
  public void setToCap(String toCap)
  {
    this.toCap = toCap;
  }
  
  public String getToComune()
  {
    return this.toComune;
  }
  
  public void setToComune(String toComune)
  {
    this.toComune = toComune;
  }
}
