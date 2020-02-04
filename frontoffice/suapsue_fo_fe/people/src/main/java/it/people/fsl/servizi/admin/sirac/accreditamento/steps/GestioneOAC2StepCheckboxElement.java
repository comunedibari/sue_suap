package it.people.fsl.servizi.admin.sirac.accreditamento.steps;

import java.io.Serializable;

class GestioneOAC2Step$CheckboxElement
  implements Serializable
{
  private static final long serialVersionUID = -8785844993061925064L;
  private Boolean attivo;
  private Boolean deleted;
  
  public void GestioneOAC2StepCheckboxElement(GestioneOAC2Step paramGestioneOAC2Step, Boolean attivo, Boolean deleted)
  {
    setAttivo(attivo);
    setDeleted(deleted);
  }
  
  public GestioneOAC2Step$CheckboxElement(GestioneOAC2Step paramGestioneOAC2Step, String attivo, String deleted)
  {
    setAttivo(convertValue(attivo));
    setDeleted(convertValue(deleted));
  }
  
  public Boolean getAttivo()
  {
    return this.attivo;
  }
  
  public void setAttivo(Boolean attivo)
  {
    this.attivo = attivo;
  }
  
  public void setAttivo(String attivo)
  {
    this.attivo = convertValue(attivo);
  }
  
  public Boolean getDeleted()
  {
    return this.deleted;
  }
  
  public void setDeleted(Boolean deleted)
  {
    this.deleted = deleted;
  }
  
  public void setDeleted(String deleted)
  {
    this.deleted = convertValue(deleted);
  }
  
  private Boolean convertValue(String value)
  {
    if ((value.equals("on")) || (value.equals("true"))) {
      return Boolean.valueOf(true);
    }
    return Boolean.valueOf(false);
  }
}
