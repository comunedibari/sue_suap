package it.sirac.admin.sqlmap.sirac.accreditamenti;

import com.ibatis.common.jdbc.exception.NestedSQLException;
import com.ibatis.sqlmap.client.SqlMapClient;
import it.cefriel.utility.security.PKCSHelper;
import it.sirac.admin.common.TableDAO;
import it.sirac.admin.faces.Manager;
import it.sirac.admin.sqlmap.sirac.IbatisSirac;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.component.UIInput;
import javax.faces.event.ActionEvent;
import org.apache.myfaces.custom.fileupload.UploadedFile;

public class AccreditamentiDAO
  implements TableDAO
{
  private SqlMapClient sqlMap = null;
  private boolean ascending;
  private String sortColumn = null;
  private HashMap headers;
  private String whereClause = null;
  private Accreditamenti current = null;
  private UploadedFile file;
  private String error = null;
  private boolean intermediario = false;
  private UIInput selectQualifica = null;
  public static final String ERROR_INSERT = "Errore nell'inserimento della qualifica";
  public static final String ERROR_UPDATE = "Errore nell'aggiornamento della qualifica";
  public static final String ERROR_DELETE = "Errore nella cancellazione della qualifica";
  public static final String ERROR_DUPLICATE = "Duplicato";
  public static final String ERROR_FOREIGN_KEY = "Chiave esterna non esiste";
  public static final String ERROR_P7M = "File P7M errato";
  public static final String ERROR_CF = "Codice fiscale non corretto";
  public static final String ERROR_PIVA = "Partita IVA non corretta";
  public static final String ERROR_CF_INTERMEDIARIO = "Codice fiscale intermediario non corretto";
  
  public AccreditamentiDAO()
  {
    this.sqlMap = IbatisSirac.getInstance();
  }
  
  public UIInput getSelectQualifica()
  {
    return this.selectQualifica;
  }
  
  public void setSelectQualifica(UIInput selectQualifica)
  {
    this.selectQualifica = selectQualifica;
  }
  
  public void valueChange(ActionEvent e)
  {
    String qualifica = (String)this.selectQualifica.getSubmittedValue();
    this.current.setIdQualifica(qualifica);
    checkIntermediario(this.current);
  }
  
  public String delete(Object current)
  {
    SqlMapClient sqlMap = IbatisSirac.getInstance();
    if (sqlMap != null) {
      try
      {
        sqlMap.delete("deleteAccreditamenti", current);
      }
      catch (SQLException e)
      {
        setError("Errore nella cancellazione della qualifica");
        return "failed";
      }
    }
    setError("");
    return "success";
  }
  
  public Vector getColumnName()
  {
    Vector columnName = new Vector();
    columnName.add("codice_fiscale");
    columnName.add("id_accreditamento");
    columnName.add("id_comune");
    columnName.add("id_qualifica");
    columnName.add("domicilio_elettronico");
    columnName.add("codicefiscale_intermediario");
    columnName.add("partitaiva_intermediario");
    columnName.add("descrizione");
    columnName.add("timestamp_certificazione");
    columnName.add("sede_legale");
    columnName.add("denominazione");
    columnName.add("attivo");
    columnName.add("deleted");
    columnName.add("auto_certificazione_filename");
    columnName.add("auto_certificazione");
    return columnName;
  }
  
  public HashMap getHeaders()
  {
    if (this.headers == null)
    {
      this.headers = new HashMap();
      this.headers.put("codiceFiscale", "Codice Fiscale");
      this.headers.put("idComune", "Cod. Ente");
      this.headers.put("idQualifica", "Qualifica");
      this.headers.put("domicilioElettronico", "Dom. elettronico");
      this.headers.put("codicefiscaleIntermediario", "CF Intermediario");
      this.headers.put("partitaivaIntermediario", "PIVA Intermediario");
      this.headers.put("descrizione", "Descrizione");
      this.headers.put("dateCertificazione", "Certificato il");
      this.headers.put("sedeLegale", "Sede legale");
      this.headers.put("denominazione", "Denominazione");
      this.headers.put("attivo", "Attivo");
      this.headers.put("deleted", "Eliminato");
    }
    return this.headers;
  }
  
  public List getList()
  {
    List list = null;
    try
    {
      if (this.sqlMap != null) {
        list = this.sqlMap.queryForList("getAccreditamentiList", null);
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return list;
  }
  
  public List getPaginatedList(int exclude, int maxrows)
  {
    List list = null;
    try
    {
      if (this.sqlMap != null)
      {
        Map queryParameters = new HashMap();
        queryParameters.put("exclude", new Integer(exclude));
        queryParameters.put("max", new Integer(maxrows));
        if (this.sortColumn != null)
        {
          int columnIndex = Integer.parseInt(this.sortColumn.substring(3));
          String columnName = (String)getColumnName().get(columnIndex);
          
          queryParameters.put("column", columnName);
          if (this.ascending) {
            queryParameters.put("ascending", "ASC");
          } else {
            queryParameters.put("ascending", "DESC");
          }
        }
        else
        {
          queryParameters.put("column", "id_accreditamento");
          queryParameters.put("ascending", "ASC");
        }
        if (this.whereClause != null) {
          queryParameters.put("whereClause", this.whereClause);
        }
        list = this.sqlMap.queryForList("getAccreditamentiList", queryParameters);
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return list;
  }
  
  public int getRowsCount()
  {
    try
    {
      Map queryParameters = new HashMap();
      if (this.whereClause != null) {
        queryParameters.put("whereClause", this.whereClause);
      }
      return ((Integer)this.sqlMap.queryForObject("getAccreditamentiCount", queryParameters)).intValue();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return 0;
  }
  
  public String insert(Object current)
  {
    Accreditamenti accreditamento = (Accreditamenti)current;
    SqlMapClient sqlMap = IbatisSirac.getInstance();
    if (sqlMap != null) {
      try
      {
        prepareAccreditamento(accreditamento);
        if ((this.error != null) && (!this.error.equals(""))) {
          return "failed";
        }
        sqlMap.insert("insertAccreditamenti", current);
      }
      catch (NestedSQLException ex)
      {
        String cause = ex.getCause().getMessage();
        if (cause.startsWith("Duplicate entry")) {
          setError("Duplicato");
        } else if (cause.equals("Cannot add or update a child row: a foreign key constraint fails")) {
          setError("Chiave esterna non esiste");
        } else {
          setError("Errore nell'inserimento della qualifica");
        }
        return "failed";
      }
      catch (Exception e)
      {
        setError("Errore nell'inserimento della qualifica");
        return "failed";
      }
    }
    this.file = null;
    setError("");
    return "success";
  }
  
  public boolean isAscending()
  {
    return this.ascending;
  }
  
  public void setAscending(boolean ascending)
  {
    this.ascending = ascending;
  }
  
  public String getSortColumn()
  {
    return this.sortColumn;
  }
  
  public void setSortColumn(String sortColumn)
  {
    this.sortColumn = sortColumn;
  }
  
  private void prepareAccreditamento(Accreditamenti accreditamento)
  {
    accreditamento.setIdComune(Manager.PortalID());
    if (!this.intermediario) {
      accreditamento.setCodicefiscaleIntermediario(accreditamento.getCodiceFiscale());
    }
    if (this.file == null)
    {
      byte[] nofile = new byte[0];
      accreditamento.setAutoCertificazione(nofile);
      accreditamento.setAutoCertificazioneFilename("NA");
      accreditamento.setTimestampCertificazione("");
    }
    else
    {
      try
      {
        Date timestamp = new Date();
        accreditamento.setTimestampCertificazione(String.valueOf(timestamp.getTime()));
        accreditamento.setAutoCertificazioneFilename(this.file.getName());
        accreditamento.setAutoCertificazione(this.file.getBytes());
      }
      catch (IOException e) {}
    }
  }
  
  public String update(Object current)
  {
    Accreditamenti accreditamento = (Accreditamenti)current;
    SqlMapClient sqlMap = IbatisSirac.getInstance();
    if (sqlMap != null) {
      try
      {
        prepareAccreditamento(accreditamento);
        if ((this.error != null) && (!this.error.equals(""))) {
          return "failed";
        }
        sqlMap.update("updateAccreditamenti", accreditamento);
      }
      catch (SQLException e)
      {
        setError("Errore nell'aggiornamento della qualifica");
        return "failed";
      }
    }
    setError("");
    return "success";
  }
  
  public UploadedFile getFile()
  {
    return this.file;
  }
  
  public void setFile(UploadedFile file)
  {
    this.file = null;
    if ((file != null) && 
      (file.getContentType().equals("application/pkcs7-mime"))) {
      try
      {
        boolean verified = PKCSHelper.isP7MVerified(file.getBytes());
        if (verified) {
          this.file = file;
        }
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
  }
  
  public Map getIdQualifiche()
  {
    Map map = null;
    try
    {
      if (this.sqlMap != null) {
        map = this.sqlMap.queryForMap("getAllQualificheMap", null, "descrizione", "idQualifica");
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return map;
  }
  
  public String getError()
  {
    return this.error;
  }
  
  public void setError(String error)
  {
    this.error = error;
  }
  
  public String getWhereClause()
  {
    return this.whereClause;
  }
  
  public void setWhereClause(String whereClause)
  {
    this.whereClause = whereClause;
  }
  
  public boolean isIntermediario()
  {
    return this.intermediario;
  }
  
  public void setIntermediario(boolean intermediario)
  {
    this.intermediario = intermediario;
  }
  
  public void checkIntermediario(Accreditamenti current)
  {
    this.current = current;
    String qualifica = current.getIdQualifica();
    Map param = new HashMap();
    param.put("whereClause", " id_qualifica LIKE '" + qualifica + "'");
    String tipoQualifica = null;
    try
    {
      tipoQualifica = (String)this.sqlMap.queryForObject("isIntermediario", param);
    }
    catch (SQLException e1)
    {
      e1.printStackTrace();
    }
    if ((tipoQualifica != null) && (tipoQualifica.equalsIgnoreCase("intermediario")))
    {
      this.intermediario = true;
      current.setCodicefiscaleIntermediario(current.getPartitaivaIntermediario());
    }
    else
    {
      this.intermediario = false;
      current.setCodicefiscaleIntermediario(current.getCodiceFiscale());
    }
  }
}
