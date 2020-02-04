package it.sirac.admin.sqlmap.capeople;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import java.io.Reader;

public class IbatisCAPeople
{
  private static SqlMapClient sqlMap = null;
  
  public static SqlMapClient getInstance()
  {
    if (sqlMap == null) {
      try
      {
        String resource = "config/capeople/CAPeopleSqlMapConfig.xml";
        Reader reader = Resources.getResourceAsReader(resource);
        sqlMap = SqlMapClientBuilder.buildSqlMapClient(reader);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    return sqlMap;
  }
}
