package it.sirac.admin.sqlmap.capeople.userdata;

import it.sirac.admin.faces.Manager;
import it.sirac.admin.sqlmap.common.FilterBean;
import java.util.Map;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

public class UserdataFilterBean
  extends FilterBean
{
  private String fromNome;
  private String fromCognome;
  private String toNome;
  private String toCognome;
  private String typeNome;
  private String typeCognome;
  
  public void filter()
  {
    Manager tableManager = (Manager)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tableManager");
    if (tableManager != null)
    {
      UserdataDAO comuniDAO = (UserdataDAO)tableManager.getTableAction();
      
      String whereClause = "";
      if ((this.typeNome.equals("0")) && (this.typeCognome.equals("0"))) {
        return;
      }
      String nomeClause = textFilterBuilder("nome", this.typeNome, this.fromNome, this.toNome + "zzz");
      String cognomeClause = textFilterBuilder("cognome", this.typeCognome, this.fromCognome, this.toCognome + "zzz");
      
      whereClause = whereClause + nomeClause;
      if ((nomeClause != null) && (cognomeClause != null) && (!nomeClause.equals("")) && (!cognomeClause.equals(""))) {
        whereClause = whereClause + " AND ";
      }
      whereClause = whereClause + cognomeClause;
      
      comuniDAO.setWhereClause(whereClause);
    }
  }
  
  public String cancel()
  {
    this.fromNome = "";
    this.fromCognome = "";
    this.toNome = "";
    this.toCognome = "";
    this.typeNome = "";
    this.typeCognome = "";
    Manager tableManager = (Manager)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tableManager");
    if (tableManager != null)
    {
      UserdataDAO comuniDAO = (UserdataDAO)tableManager.getTableAction();
      comuniDAO.setWhereClause(null);
    }
    return "reset";
  }
  
  public void typeCognomeChanged(ValueChangeEvent e)
  {
    this.typeCognome = ((String)e.getNewValue());
  }
  
  public void typeNomeChanged(ValueChangeEvent e)
  {
    this.typeNome = ((String)e.getNewValue());
  }
  
  public String getFromCognome()
  {
    return this.fromCognome;
  }
  
  public void setFromCognome(String fromCognome)
  {
    this.fromCognome = fromCognome;
  }
  
  public String getFromNome()
  {
    return this.fromNome;
  }
  
  public void setFromNome(String fromNome)
  {
    this.fromNome = fromNome;
  }
  
  public String getToCognome()
  {
    return this.toCognome;
  }
  
  public void setToCognome(String toCognome)
  {
    this.toCognome = toCognome;
  }
  
  public String getToNome()
  {
    return this.toNome;
  }
  
  public void setToNome(String toNome)
  {
    this.toNome = toNome;
  }
  
  public String getTypeCognome()
  {
    return this.typeCognome;
  }
  
  public void setTypeCognome(String typeCognome)
  {
    this.typeCognome = typeCognome;
  }
  
  public String getTypeNome()
  {
    return this.typeNome;
  }
  
  public void setTypeNome(String typeNome)
  {
    this.typeNome = typeNome;
  }
}
