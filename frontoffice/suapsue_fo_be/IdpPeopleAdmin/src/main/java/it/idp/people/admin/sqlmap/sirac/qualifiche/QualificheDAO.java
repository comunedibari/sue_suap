/**
 * Copyright (c) 2011, Regione Emilia-Romagna, Italy
 *  
 * Licensed under the EUPL, Version 1.1 or - as soon they
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the
 * Licence.
 * 
 * For convenience a plain text copy of the English version
 * of the Licence can be found in the file LICENCE.txt in
 * the top-level directory of this software distribution.
 * 
 * You may obtain a copy of the Licence in any of 22 European
 * Languages at:
 * 
 * http://joinup.ec.europa.eu/software/page/eupl
 * 
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * 
 * This product includes software developed by Yale University
 * 
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
**/
/*

  Licenza:	    Licenza Progetto PEOPLE
  Fornitore:    CEFRIEL
  Autori:       M. Pianciamore, P. Selvini

  Questo codice sorgente � protetto dalla licenza valida nell'ambito del
  progetto PEOPLE. La propriet� intellettuale di questo codice � e rester�
  esclusiva di "CEFRIEL Societ� Consortile a Responsabilit� Limitata" con
  sede legale in via Renato Fucini 2, 20133 Milano (MI).

  Disclaimer:

  COVERED CODE IS PROVIDED UNDER THIS LICENSE ON AN "AS IS" BASIS, WITHOUT
  WARRANTY OF ANY KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING, WITHOUT 
  LIMITATION, WARRANTIES THAT THE COVERED CODE IS FREE OF DEFECTS, MERCHANTABLE,
  FIT FOR A PARTICULAR PURPOSE OR NON-INFRINGING. THE ENTIRE RISK AS TO THE
  QUALITY AND PERFORMANCE OF THE COVERED CODE IS WITH YOU. SHOULD ANY COVERED
  CODE PROVE DEFECTIVE IN ANY RESPECT, YOU (NOT THE INITIAL DEVELOPER OR ANY
  OTHER CONTRIBUTOR) ASSUME THE COST OF ANY NECESSARY SERVICING, REPAIR OR
  CORRECTION.
    
*/
package it.idp.people.admin.sqlmap.sirac.qualifiche;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import com.ibatis.sqlmap.client.SqlMapClient;
import it.idp.people.admin.common.TableDAO;
import it.idp.people.admin.sqlmap.sirac.IbatisSirac;

public class QualificheDAO implements TableDAO
{
	public final static String ERROR_INSERT = "Errore nell'inserimento della qualifica";
	public final static String ERROR_UPDATE = "Errore nell'aggiornamento della qualifica";
	public final static String ERROR_DELETE = "Errore nella cancellazione della qualifica";
	
	private SqlMapClient sqlMap = null;
	private boolean ascending;
	private String sortColumn = null;
	private HashMap headers;
	private String whereClause = null;
	private String error = null;
	private List tipoQualifiche = null;
	
	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public QualificheDAO()
	{
		sqlMap = IbatisSirac.getInstance();
	}
	
	public String delete(Object current) {
		SqlMapClient sqlMap = IbatisSirac.getInstance();
		if(sqlMap != null)
		{
			try 
			{
				sqlMap.delete("deleteQualifiche", current);
			} 
			catch (SQLException e) 
			{
				setError(QualificheDAO.ERROR_DELETE);
				return "failed";
			}
		}
		setError("");
		return "success";
	}

	public Vector getColumnName() {
		Vector columnName = new Vector();
		columnName.add("id_qualifica");
		columnName.add("descrizione");
    	columnName.add("tipo_qualifica");
    	columnName.add("auto_certificabile");
    	columnName.add("has_rappresentante_legale");
    	return columnName;
	}

	public HashMap getHeaders() {
		if(headers==null)
		{
			headers = new HashMap();
	        headers.put("idQualifica", "Qualifica");
	        headers.put("descrizione", "Descrizione");
	        headers.put("tipoQualifica", "Tipologia");
	        headers.put("autoCertificabile", "Autocertificabile");
	        headers.put("hasRappresentanteLegale", "Rapp. Legale");
		}
		return headers;
	}

	public List getList() {
		List list = null;
		try
		{
			if(sqlMap != null)
			{		
				list = sqlMap.queryForList("getQualificheList", null);				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}

	public List getTipoQualifiche()
	{
		tipoQualifiche = null;
		try
		{
			if(sqlMap != null)
			{		
				tipoQualifiche = sqlMap.queryForList("getTipoQualificheList", null);				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return tipoQualifiche;
	}
	
	public List getPaginatedList(int exclude, int maxrows) {
		List list = null;
		try
		{
			if(sqlMap != null)
			{		
				Map queryParameters = new HashMap();
				queryParameters.put("exclude", new Integer(exclude));
				queryParameters.put("max", new Integer(maxrows));
				if(sortColumn != null)
				{
					int columnIndex = Integer.parseInt(sortColumn.substring(3));
					String columnName = (String)getColumnName().get(columnIndex);
					queryParameters.put("column", columnName);
					if(ascending)
					{
						queryParameters.put("ascending", "ASC");
					}
					else
					{
						queryParameters.put("ascending", "DESC");
					}
				}
				else
				{
					queryParameters.put("column", "id_qualifica");
					queryParameters.put("ascending", "ASC");
				}
				
				if(whereClause != null)
				{
					queryParameters.put("whereClause", whereClause);
				}
				
				list = sqlMap.queryForList("getQualificheList", queryParameters);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}

	public int getRowsCount() {
		try 
		{
			Map queryParameters = new HashMap();
			if(whereClause!=null)
			{
				queryParameters.put("whereClause", whereClause);
			}
			
			return ((Integer)sqlMap.queryForObject("getQualificheCount", queryParameters)).intValue();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return 0;
		}
	}

	public String insert(Object current) {
		SqlMapClient sqlMap = IbatisSirac.getInstance();
		if(sqlMap != null)
		{
			try 
			{
				sqlMap.insert("insertQualifiche", current);
			} 
			catch (SQLException e) 
			{
				setError(QualificheDAO.ERROR_INSERT);
				return "failed";
			}
		}
		setError("");
		return "success";
	}

	public boolean isAscending() {
		return ascending;
	}

	public void setAscending(boolean ascending) {
		this.ascending = ascending;
	}

	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) 
	{
		this.sortColumn = sortColumn;
	}

	public String update(Object current) {
		SqlMapClient sqlMap = IbatisSirac.getInstance();
		if(sqlMap != null)
		{
			try 
			{
				sqlMap.update("updateQualifiche", current);
			} 
			catch (SQLException e) 
			{
				setError(QualificheDAO.ERROR_UPDATE);
				return "failed";
			}
		}
		setError("");
		return "success";
	}

	public String getWhereClause() {
		return whereClause;
	}

	public void setWhereClause(String whereClause) {
		this.whereClause = whereClause;
	}

	public void setTipoQualifiche(List tipoQualifiche) {
		this.tipoQualifiche = tipoQualifiche;
	}

}
