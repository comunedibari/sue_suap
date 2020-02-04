package it.sirac.admin.sqlmap.sirac.qualifiche;

import it.sirac.admin.faces.Manager;
import it.sirac.admin.sqlmap.common.FilterBean;
import java.util.Map;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

public class QualificheFilterBean
  extends FilterBean
{
  private String fromQualifica;
  private String toQualifica;
  private String typeQualifica;
  private String fromDescr;
  private String toDescr;
  private String typeDescr;
  private String fromTipologia;
  private String toTipologia;
  private String typeTipologia;
  
  public void filter()
  {
    Manager tableManager = (Manager)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tableManager");
    if (tableManager != null)
    {
      QualificheDAO qualificheDAO = (QualificheDAO)tableManager.getTableAction();
      
      String whereClause = "";
      if ((this.typeQualifica.equals("0")) && (this.typeDescr.equals("0")) && (this.typeTipologia.equals("0"))) {
        return;
      }
      String QualificaClause = textFilterBuilder("id_qualifica", this.typeQualifica, this.fromQualifica, this.toQualifica);
      
      String DescrClause = textFilterBuilder("descrizione", this.typeDescr, this.fromDescr, this.toDescr);
      
      String TipologiaClause = textFilterBuilder("tipo_qualifica", this.typeTipologia, this.fromTipologia, this.toTipologia);
      if ((QualificaClause != null) && (!QualificaClause.equals(""))) {
        if (whereClause.equals("")) {
          whereClause = whereClause + QualificaClause;
        } else {
          whereClause = whereClause + " AND " + QualificaClause;
        }
      }
      if ((DescrClause != null) && (!DescrClause.equals(""))) {
        if (whereClause.equals("")) {
          whereClause = whereClause + DescrClause;
        } else {
          whereClause = whereClause + " AND " + DescrClause;
        }
      }
      if ((TipologiaClause != null) && (!TipologiaClause.equals(""))) {
        if (whereClause.equals("")) {
          whereClause = whereClause + TipologiaClause;
        } else {
          whereClause = whereClause + " AND " + TipologiaClause;
        }
      }
      qualificheDAO.setWhereClause(whereClause);
    }
  }
  
  public String cancel()
  {
    Manager tableManager = (Manager)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tableManager");
    if (tableManager != null)
    {
      QualificheDAO qualificheDAO = (QualificheDAO)tableManager.getTableAction();
      
      qualificheDAO.setWhereClause(null);
    }
    return "reset";
  }
  
  public void typeQualificaChanged(ValueChangeEvent e)
  {
    this.typeQualifica = ((String)e.getNewValue());
  }
  
  public void typeDescrChanged(ValueChangeEvent e)
  {
    this.typeDescr = ((String)e.getNewValue());
  }
  
  public void typeTipologiaChanged(ValueChangeEvent e)
  {
    this.typeTipologia = ((String)e.getNewValue());
  }
  
  public String getFromDescr()
  {
    return this.fromDescr;
  }
  
  public void setFromDescr(String fromDescr)
  {
    this.fromDescr = fromDescr;
  }
  
  public String getFromQualifica()
  {
    return this.fromQualifica;
  }
  
  public void setFromQualifica(String fromQualifica)
  {
    this.fromQualifica = fromQualifica;
  }
  
  public String getFromTipologia()
  {
    return this.fromTipologia;
  }
  
  public void setFromTipologia(String fromTipologia)
  {
    this.fromTipologia = fromTipologia;
  }
  
  public String getToDescr()
  {
    return this.toDescr;
  }
  
  public void setToDescr(String toDescr)
  {
    this.toDescr = toDescr;
  }
  
  public String getToQualifica()
  {
    return this.toQualifica;
  }
  
  public void setToQualifica(String toQualifica)
  {
    this.toQualifica = toQualifica;
  }
  
  public String getToTipologia()
  {
    return this.toTipologia;
  }
  
  public void setToTipologia(String toTipologia)
  {
    this.toTipologia = toTipologia;
  }
  
  public String getTypeDescr()
  {
    return this.typeDescr;
  }
  
  public void setTypeDescr(String typeDescr)
  {
    this.typeDescr = typeDescr;
  }
  
  public String getTypeQualifica()
  {
    return this.typeQualifica;
  }
  
  public void setTypeQualifica(String typeQualifica)
  {
    this.typeQualifica = typeQualifica;
  }
  
  public String getTypeTipologia()
  {
    return this.typeTipologia;
  }
  
  public void setTypeTipologia(String typeTipologia)
  {
    this.typeTipologia = typeTipologia;
  }
}
