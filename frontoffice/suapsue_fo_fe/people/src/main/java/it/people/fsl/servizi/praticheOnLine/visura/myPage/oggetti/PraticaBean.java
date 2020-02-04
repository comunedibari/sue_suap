package it.people.fsl.servizi.praticheOnLine.visura.myPage.oggetti;

import java.util.ArrayList;

public class PraticaBean
{
  private String processDataID;
  private String contentName;
  private String userID;
  private String creation_time;
  private String last_modified_time;
  private String submitted_time;
  private String processName;
  private String status;
  private String oid;
  private String peopleProtocollId;
  private String editableProcessId;
  private ArrayList activityHistory;
  private String oggetto;
  private String descrizione;
  private String paymentStatus;
  private DatiEstesiMyPage datiEstesiMyPage;
  private boolean sendError;
  
  public PraticaBean()
  {
    setSendError(false);
  }
  
  public String getProcessDataID()
  {
    return this.processDataID;
  }
  
  public void setProcessDataID(String processDataID)
  {
    this.processDataID = processDataID;
  }
  
  public String getContentName()
  {
    return this.contentName;
  }
  
  public void setContentName(String contentName)
  {
    this.contentName = contentName;
  }
  
  public String getUserID()
  {
    return this.userID;
  }
  
  public void setUserID(String userID)
  {
    this.userID = userID;
  }
  
  public String getCreation_time()
  {
    return this.creation_time;
  }
  
  public void setCreation_time(String creation_time)
  {
    this.creation_time = creation_time;
  }
  
  public String getProcessName()
  {
    return this.processName;
  }
  
  public void setProcessName(String processName)
  {
    this.processName = processName;
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setStatus(String status)
  {
    this.status = status;
  }
  
  public String getOid()
  {
    return this.oid;
  }
  
  public void setOid(String oid)
  {
    this.oid = oid;
  }
  
  public String getPeopleProtocollId()
  {
    return this.peopleProtocollId;
  }
  
  public void setPeopleProtocollId(String peopleProtocollId)
  {
    this.peopleProtocollId = peopleProtocollId;
  }
  
  public String getEditableProcessId()
  {
    return this.editableProcessId;
  }
  
  public void setEditableProcessId(String editableProcessId)
  {
    this.editableProcessId = editableProcessId;
  }
  
  public ArrayList getActivityHistory()
  {
    return this.activityHistory;
  }
  
  public void setActivityHistory(ArrayList activityHistory)
  {
    this.activityHistory = activityHistory;
  }
  
  public void addActivityHistory(ActivityBean activity)
  {
    if (this.activityHistory == null) {
      this.activityHistory = new ArrayList();
    }
    this.activityHistory.add(this.activityHistory);
  }
  
  public String getOggetto()
  {
    return this.oggetto;
  }
  
  public void setOggetto(String oggetto)
  {
    this.oggetto = oggetto;
  }
  
  public String getDescrizione()
  {
    return this.descrizione;
  }
  
  public void setDescrizione(String descrizione)
  {
    this.descrizione = descrizione;
  }
  
  public String getLast_modified_time()
  {
    return this.last_modified_time;
  }
  
  public void setLast_modified_time(String last_modified_time)
  {
    this.last_modified_time = last_modified_time;
  }
  
  public String getSubmitted_time()
  {
    return this.submitted_time;
  }
  
  public void setSubmitted_time(String submitted_time)
  {
    this.submitted_time = submitted_time;
  }
  
  public final String getPaymentStatus()
  {
    return this.paymentStatus;
  }
  
  public final void setPaymentStatus(String paymentStatus)
  {
    this.paymentStatus = paymentStatus;
  }
  
  public final boolean isSendError()
  {
    return this.sendError;
  }
  
  public final void setSendError(boolean sendError)
  {
    this.sendError = sendError;
  }
  
  public DatiEstesiMyPage getDatiEstesiMyPage()
  {
    return this.datiEstesiMyPage;
  }
  
  public void setDatiEstesiMyPage(DatiEstesiMyPage datiEstesiMyPage)
  {
    this.datiEstesiMyPage = datiEstesiMyPage;
  }
}
