package it.sirac.admin.sqlmap.common;

import java.util.HashMap;
import java.util.Map;

public abstract class FilterBean
{
  Map filterTypeNumeric = new HashMap();
  Map filterTypeText = new HashMap();
  
  public Map getFilterTypeNumeric()
  {
    this.filterTypeNumeric.put(" ", "0");
    this.filterTypeNumeric.put("=", "1");
    this.filterTypeNumeric.put("<", "2");
    this.filterTypeNumeric.put(">", "3");
    this.filterTypeNumeric.put("Da", "4");
    return this.filterTypeNumeric;
  }
  
  public Map getFilterTypeText()
  {
    this.filterTypeText.put(" ", "0");
    this.filterTypeText.put("contiene", "1");
    this.filterTypeText.put("escluso", "2");
    this.filterTypeText.put("Da", "3");
    return this.filterTypeText;
  }
  
  public String textFilterBuilder(String whereClause, String type, String from, String to)
  {
    whereClause = " (" + whereClause;
    switch (Integer.parseInt(type))
    {
    case 1: 
      whereClause = whereClause + " LIKE '%" + from + "%') ";
      break;
    case 2: 
      whereClause = whereClause + " NOT LIKE '%" + from + "%') ";
      break;
    case 3: 
      if ((to != null) && (to != "")) {
        whereClause = whereClause + " BETWEEN '" + from + "' AND '" + to + "zzz" + "') ";
      }
      break;
    default: 
      whereClause = "";
    }
    return whereClause;
  }
  
  public String numberFilterBuilder(String whereClause, String type, String from, String to)
  {
    whereClause = " (" + whereClause;
    switch (Integer.parseInt(type))
    {
    case 1: 
      whereClause = whereClause + " = " + from + ") ";
      break;
    case 2: 
      whereClause = whereClause + " = " + from + ") ";
      break;
    case 3: 
      whereClause = whereClause + " = " + from + ") ";
      break;
    case 4: 
      if ((to != null) && (to != "")) {
        whereClause = whereClause + " BETWEEN " + from + " AND " + to + ") ";
      }
      break;
    default: 
      whereClause = "";
    }
    return whereClause;
  }
}
