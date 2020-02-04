package it.sirac.admin.sqlmap.sirac.accreditamenti;

import it.sirac.admin.faces.Manager;
import it.sirac.admin.sqlmap.common.FilterBean;
import java.util.Map;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

public class AccreditamentiFilterBean
  extends FilterBean
{
  private String fromCF;
  private String toCF;
  private String typeCF;
  private String fromQualifica;
  private String toQualifica;
  private String typeQualifica;
  private String fromComune;
  private String toComune;
  private String typeComune;
  
  public void filter()
  {
    Manager tableManager = (Manager)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tableManager");
    if (tableManager != null)
    {
      AccreditamentiDAO accreditamentiDAO = (AccreditamentiDAO)tableManager.getTableAction();
      
      String whereClause = "attivo = 1";
      if ((this.typeCF.equals("0")) && (this.typeQualifica.equals("0")) && (this.typeComune.equals("0"))) {
        return;
      }
      String CFClause = textFilterBuilder("codice_fiscale", this.typeCF, this.fromCF, this.toCF);
      String QualificaClause = textFilterBuilder("id_qualifica", this.typeQualifica, this.fromQualifica, this.toQualifica);
      String ComuneClause = textFilterBuilder("id_comune", this.typeComune, this.fromComune, this.toComune);
      if ((CFClause != null) && (!CFClause.equals(""))) {
        whereClause = whereClause + " AND " + CFClause;
      }
      if ((QualificaClause != null) && (!QualificaClause.equals(""))) {
        whereClause = whereClause + " AND " + QualificaClause;
      }
      if ((ComuneClause != null) && (!ComuneClause.equals(""))) {
        whereClause = whereClause + " AND " + ComuneClause;
      }
      accreditamentiDAO.setWhereClause(whereClause);
    }
  }
  
  public String cancel()
  {
    Manager tableManager = (Manager)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tableManager");
    if (tableManager != null)
    {
      AccreditamentiDAO accreditamentiDAO = (AccreditamentiDAO)tableManager.getTableAction();
      accreditamentiDAO.setWhereClause(null);
    }
    return "reset";
  }
  
  public void typeQualificaChanged(ValueChangeEvent e)
  {
    this.typeQualifica = ((String)e.getNewValue());
  }
  
  public void typeCFChanged(ValueChangeEvent e)
  {
    this.typeCF = ((String)e.getNewValue());
  }
  
  public void typeComuneChanged(ValueChangeEvent e)
  {
    this.typeComune = ((String)e.getNewValue());
  }
  
  public String getFromCF()
  {
    return this.fromCF;
  }
  
  public void setFromCF(String fromCF)
  {
    this.fromCF = fromCF;
  }
  
  public String getFromComune()
  {
    return this.fromComune;
  }
  
  public void setFromComune(String fromComune)
  {
    this.fromComune = fromComune;
  }
  
  public String getFromQualifica()
  {
    return this.fromQualifica;
  }
  
  public void setFromQualifica(String fromQualifica)
  {
    this.fromQualifica = fromQualifica;
  }
  
  public String getToCF()
  {
    return this.toCF;
  }
  
  public void setToCF(String toCF)
  {
    this.toCF = toCF;
  }
  
  public String getToComune()
  {
    return this.toComune;
  }
  
  public void setToComune(String toComune)
  {
    this.toComune = toComune;
  }
  
  public String getToQualifica()
  {
    return this.toQualifica;
  }
  
  public void setToQualifica(String toQualifica)
  {
    this.toQualifica = toQualifica;
  }
  
  public String getTypeCF()
  {
    return this.typeCF;
  }
  
  public void setTypeCF(String typeCF)
  {
    this.typeCF = typeCF;
  }
  
  public String getTypeComune()
  {
    return this.typeComune;
  }
  
  public void setTypeComune(String typeComune)
  {
    this.typeComune = typeComune;
  }
  
  public String getTypeQualifica()
  {
    return this.typeQualifica;
  }
  
  public void setTypeQualifica(String typeQualifica)
  {
    this.typeQualifica = typeQualifica;
  }
}
