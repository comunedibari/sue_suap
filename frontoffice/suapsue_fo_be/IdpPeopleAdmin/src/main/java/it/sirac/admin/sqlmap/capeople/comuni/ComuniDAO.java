package it.sirac.admin.sqlmap.capeople.comuni;

import com.ibatis.sqlmap.client.SqlMapClient;
import it.sirac.admin.common.TableDAO;
import it.sirac.admin.sqlmap.capeople.IbatisCAPeople;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class ComuniDAO
  implements TableDAO
{
  public static final String ERROR_INSERT = "Errore nell'inserimento del comune";
  public static final String ERROR_UPDATE = "Errore nell'aggiornamento del comune";
  public static final String ERROR_DELETE = "Errore nella cancellazione del comune";
  private SqlMapClient sqlMap = null;
  private boolean ascending;
  private String sortColumn = null;
  private HashMap headers;
  private String whereClause;
  private String error;
  
  public ComuniDAO()
  {
    this.sqlMap = IbatisCAPeople.getInstance();
  }
  
  public List getList()
  {
    List list = null;
    try
    {
      if (this.sqlMap != null) {
        list = this.sqlMap.queryForList("getComuniList", null);
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
          queryParameters.put("column", "comune");
          queryParameters.put("ascending", "ASC");
        }
        if (this.whereClause != null) {
          queryParameters.put("whereClause", this.whereClause);
        }
        list = this.sqlMap.queryForList("getComuniList", queryParameters);
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
      return ((Integer)this.sqlMap.queryForObject("getComuniCount", queryParameters)).intValue();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return 0;
  }
  
  public String update(Object current)
  {
    SqlMapClient sqlMap = IbatisCAPeople.getInstance();
    if (sqlMap != null) {
      try
      {
        sqlMap.update("updateComune", current);
      }
      catch (SQLException e)
      {
        setError("Errore nell'aggiornamento del comune");
        return "failed";
      }
    }
    setError("");
    return "success";
  }
  
  public String delete(Object current)
  {
    SqlMapClient sqlMap = IbatisCAPeople.getInstance();
    if (sqlMap != null) {
      try
      {
        sqlMap.delete("deleteComune", current);
      }
      catch (SQLException e)
      {
        setError("Errore nella cancellazione del comune");
        return "failed";
      }
    }
    setError("");
    return "success";
  }
  
  public String insert(Object current)
  {
    SqlMapClient sqlMap = IbatisCAPeople.getInstance();
    if (sqlMap != null) {
      try
      {
        sqlMap.insert("insertComune", current);
      }
      catch (SQLException e)
      {
        setError("Errore nell'inserimento del comune");
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
  
  public HashMap getHeaders()
  {
    if (this.headers == null)
    {
      this.headers = new HashMap();
      this.headers.put("comune", "Comune");
      this.headers.put("provincia", "Provincia");
      this.headers.put("regione", "Regione");
      this.headers.put("cap", "CAP");
      this.headers.put("prefisso", "Prefisso");
      this.headers.put("codiceComune", "Codice comune");
      this.headers.put("codiceIstat", "Codice Istat");
    }
    return this.headers;
  }
  
  public Vector getColumnName()
  {
    Vector columnName = new Vector();
    columnName.add("COMUNE");
    columnName.add("PROVINCIA");
    columnName.add("REGIONE");
    columnName.add("CAP");
    columnName.add("PREFISSO");
    columnName.add("CODICE_COMUNE");
    columnName.add("CODICE_ISTAT");
    return columnName;
  }
  
  public String getWhereClause()
  {
    return this.whereClause;
  }
  
  public void setWhereClause(String whereClause)
  {
    this.whereClause = whereClause;
  }
  
  public String getError()
  {
    return this.error;
  }
  
  public void setError(String error)
  {
    this.error = error;
  }
}
