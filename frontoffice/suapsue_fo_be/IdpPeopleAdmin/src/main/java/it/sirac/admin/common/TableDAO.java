package it.sirac.admin.common;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public abstract interface TableDAO
{
  public abstract List getList();
  
  public abstract List getPaginatedList(int paramInt1, int paramInt2);
  
  public abstract int getRowsCount();
  
  public abstract String update(Object paramObject);
  
  public abstract String delete(Object paramObject);
  
  public abstract String insert(Object paramObject);
  
  public abstract boolean isAscending();
  
  public abstract void setAscending(boolean paramBoolean);
  
  public abstract String getSortColumn();
  
  public abstract void setSortColumn(String paramString);
  
  public abstract HashMap getHeaders();
  
  public abstract Vector getColumnName();
  
  public abstract String getError();
  
  public abstract void setError(String paramString);
}
