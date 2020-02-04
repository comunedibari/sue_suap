package it.sirac.admin.sqlmap.sirac.qualifiche;

import com.ibatis.sqlmap.client.SqlMapClient;
import it.sirac.admin.common.TableDAO;
import it.sirac.admin.sqlmap.sirac.IbatisSirac;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class QualificheDAO
  implements TableDAO
{
  public static final String ERROR_INSERT = "Errore nell'inserimento della qualifica";
  public static final String ERROR_UPDATE = "Errore nell'aggiornamento della qualifica";
  public static final String ERROR_DELETE = "Errore nella cancellazione della qualifica";
  private SqlMapClient sqlMap = null;
  private boolean ascending;
  private String sortColumn = null;
  private HashMap headers;
  private String whereClause = null;
  private String error = null;
  private List tipoQualifiche = null;
  
  public String getError()
  {
    return this.error;
  }
  
  public void setError(String error)
  {
    this.error = error;
  }
  
  public QualificheDAO()
  {
    this.sqlMap = IbatisSirac.getInstance();
  }
  
  public String delete(Object current)
  {
    SqlMapClient sqlMap = IbatisSirac.getInstance();
    if (sqlMap != null) {
      try
      {
        sqlMap.delete("deleteQualifiche", current);
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
    columnName.add("id_qualifica");
    columnName.add("descrizione");
    columnName.add("tipo_qualifica");
    columnName.add("auto_certificabile");
    columnName.add("has_rappresentante_legale");
    return columnName;
  }
  
  public HashMap getHeaders()
  {
    if (this.headers == null)
    {
      this.headers = new HashMap();
      this.headers.put("idQualifica", "Qualifica");
      this.headers.put("descrizione", "Descrizione");
      this.headers.put("tipoQualifica", "Tipologia");
      this.headers.put("autoCertificabile", "Autocertificabile");
      this.headers.put("hasRappresentanteLegale", "Rapp. Legale");
    }
    return this.headers;
  }
  
  public List getList()
  {
    List list = null;
    try
    {
      if (this.sqlMap != null) {
        list = this.sqlMap.queryForList("getQualificheList", null);
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return list;
  }
  
  public List getTipoQualifiche()
  {
    this.tipoQualifiche = null;
    try
    {
      if (this.sqlMap != null) {
        this.tipoQualifiche = this.sqlMap.queryForList("getTipoQualificheList", null);
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return this.tipoQualifiche;
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
          queryParameters.put("column", "id_qualifica");
          queryParameters.put("ascending", "ASC");
        }
        if (this.whereClause != null) {
          queryParameters.put("whereClause", this.whereClause);
        }
        list = this.sqlMap.queryForList("getQualificheList", queryParameters);
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
      return ((Integer)this.sqlMap.queryForObject("getQualificheCount", queryParameters)).intValue();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return 0;
  }
  
  public String insert(Object current)
  {
    SqlMapClient sqlMap = IbatisSirac.getInstance();
    if (sqlMap != null) {
      try
      {
        sqlMap.insert("insertQualifiche", current);
      }
      catch (SQLException e)
      {
        setError("Errore nell'inserimento della qualifica");
        return "failed";
      }
    }
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
  
  public String update(Object current)
  {
    SqlMapClient sqlMap = IbatisSirac.getInstance();
    if (sqlMap != null) {
      try
      {
        sqlMap.update("updateQualifiche", current);
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
  
  public String getWhereClause()
  {
    return this.whereClause;
  }
  
  public void setWhereClause(String whereClause)
  {
    this.whereClause = whereClause;
  }
  
  public void setTipoQualifiche(List tipoQualifiche)
  {
    this.tipoQualifiche = tipoQualifiche;
  }
}
