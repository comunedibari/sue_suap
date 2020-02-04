package it.sirac.admin.sqlmap.sirac;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import java.io.Reader;

public class IbatisSirac
{
  private static SqlMapClient sqlMap = null;
  
  static
  {
    try
    {
      String resource = "config/sirac/SiracSqlMapConfig.xml";
      Reader reader = Resources.getResourceAsReader(resource);
      sqlMap = SqlMapClientBuilder.buildSqlMapClient(reader);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  public static SqlMapClient getInstance()
  {
    return sqlMap;
  }
}
