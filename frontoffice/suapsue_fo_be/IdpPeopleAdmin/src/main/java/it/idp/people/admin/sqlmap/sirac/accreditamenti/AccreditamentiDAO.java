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
package it.idp.people.admin.sqlmap.sirac.accreditamenti;

import it.cefriel.utility.security.PKCSHelper;
import it.idp.people.admin.common.TableDAO;
import it.idp.people.admin.faces.Manager;
import it.idp.people.admin.sqlmap.sirac.IbatisSirac;

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

import com.ibatis.common.jdbc.exception.NestedSQLException;
import com.ibatis.sqlmap.client.SqlMapClient;

public class AccreditamentiDAO implements TableDAO {
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

	public final static String ERROR_INSERT = "Errore nell'inserimento della qualifica";

	public final static String ERROR_UPDATE = "Errore nell'aggiornamento della qualifica";

	public final static String ERROR_DELETE = "Errore nella cancellazione della qualifica";

	public final static String ERROR_DUPLICATE = "Duplicato";

	public final static String ERROR_FOREIGN_KEY = "Chiave esterna non esiste";

	public final static String ERROR_P7M = "File P7M errato";

	public final static String ERROR_CF = "Codice fiscale non corretto";

	public final static String ERROR_PIVA = "Partita IVA non corretta";

	public final static String ERROR_CF_INTERMEDIARIO = "Codice fiscale intermediario non corretto";

	public AccreditamentiDAO() {
		sqlMap = IbatisSirac.getInstance();
	}

	public UIInput getSelectQualifica() {
		return selectQualifica;
	}

	public void setSelectQualifica(UIInput selectQualifica) {
		this.selectQualifica = selectQualifica;
	}

	public void valueChange(ActionEvent e) {
		String qualifica = (String) selectQualifica.getSubmittedValue();
		current.setIdQualifica(qualifica);
		checkIntermediario(current);
	}

	public String delete(Object current) {
		SqlMapClient sqlMap = IbatisSirac.getInstance();
		if (sqlMap != null) {
			try {
				sqlMap.delete("deleteAccreditamenti", current);
			} catch (SQLException e) {
				setError(AccreditamentiDAO.ERROR_DELETE);
				return "failed";
			}
		}
		setError("");
		return "success";
	}

	public Vector getColumnName() {
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

	public HashMap getHeaders() {
		if (headers == null) {
			headers = new HashMap();
			headers.put("codiceFiscale", "Codice Fiscale");
			headers.put("idComune", "Cod. Ente");
			headers.put("idQualifica", "Qualifica");
			headers.put("domicilioElettronico", "Dom. elettronico");
			headers.put("codicefiscaleIntermediario", "CF Intermediario");
			headers.put("partitaivaIntermediario", "PIVA Intermediario");
			headers.put("descrizione", "Descrizione");
			headers.put("dateCertificazione", "Certificato il");
			headers.put("sedeLegale", "Sede legale");
			headers.put("denominazione", "Denominazione");
			headers.put("attivo", "Attivo");
			headers.put("deleted", "Eliminato");
			// headers.put("autoCertificazioneFilename", "Cert. Nome file");
			// headers.put("autoCertificazione", "Certificazione");
		}
		return headers;
	}

	public List getList() {
		List list = null;
		try {
			if (sqlMap != null) {
				list = sqlMap.queryForList("getAccreditamentiList", null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List getPaginatedList(int exclude, int maxrows) {
		List list = null;
		try {
			if (sqlMap != null) {
				Map queryParameters = new HashMap();
				queryParameters.put("exclude", new Integer(exclude));
				queryParameters.put("max", new Integer(maxrows));
				if (sortColumn != null) {
					int columnIndex = Integer.parseInt(sortColumn.substring(3));
					String columnName = (String) getColumnName().get(
							columnIndex);
					queryParameters.put("column", columnName);
					if (ascending) {
						queryParameters.put("ascending", "ASC");
					} else {
						queryParameters.put("ascending", "DESC");
					}
				} else {
					queryParameters.put("column", "id_accreditamento");
					queryParameters.put("ascending", "ASC");
				}

				if (whereClause != null) {
					queryParameters.put("whereClause", whereClause);
				}

				list = sqlMap.queryForList("getAccreditamentiList",
						queryParameters);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public int getRowsCount() {
		try {
			Map queryParameters = new HashMap();
			if (whereClause != null) {
				queryParameters.put("whereClause", whereClause);
			}

			return ((Integer) sqlMap.queryForObject("getAccreditamentiCount",
					queryParameters)).intValue();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public String insert(Object current) {
		Accreditamenti accreditamento = (Accreditamenti) current;
		SqlMapClient sqlMap = IbatisSirac.getInstance();
		if (sqlMap != null) {
			try {
				prepareAccreditamento(accreditamento);
				if(error!=null && !error.equals(""))
				{
					return "failed";
				}
				sqlMap.insert("insertAccreditamenti", current);
			} catch (NestedSQLException ex) {
				String cause = ex.getCause().getMessage();
				if (cause.startsWith("Duplicate entry")) {
					setError(AccreditamentiDAO.ERROR_DUPLICATE);
				} else if (cause
						.equals("Cannot add or update a child row: a foreign key constraint fails")) {
					setError(AccreditamentiDAO.ERROR_FOREIGN_KEY);
				} else {
					setError(AccreditamentiDAO.ERROR_INSERT);
				}
				return "failed";
			} catch (Exception e) {
				setError(AccreditamentiDAO.ERROR_INSERT);
				return "failed";
			}
		}
		file = null;
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

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}
	
	private void prepareAccreditamento(Accreditamenti accreditamento){
		accreditamento.setIdComune(Manager.PortalID());
		if (!intermediario) {
			accreditamento.setCodicefiscaleIntermediario(accreditamento.getCodiceFiscale());
		}
		if (file == null) {
			byte[] nofile = {};
			accreditamento.setAutoCertificazione(nofile);
			accreditamento.setAutoCertificazioneFilename("NA");
			accreditamento.setTimestampCertificazione("");
		}
		else
		{
			try {
				Date timestamp = new Date();
				accreditamento.setTimestampCertificazione(String.valueOf(timestamp.getTime()));
				accreditamento.setAutoCertificazioneFilename(file.getName());
				accreditamento.setAutoCertificazione(file.getBytes());
			} catch (IOException e) {
			}					
		}
	}

	public String update(Object current) {
		Accreditamenti accreditamento = (Accreditamenti) current;
		SqlMapClient sqlMap = IbatisSirac.getInstance();
		if (sqlMap != null) {
			try {
				prepareAccreditamento(accreditamento);
				if(error!=null && !error.equals(""))
				{
					return "failed";
				}
				sqlMap.update("updateAccreditamenti", accreditamento);
			} catch (SQLException e) {
				setError(AccreditamentiDAO.ERROR_UPDATE);
				return "failed";
			}
		}
		setError("");
		return "success";
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = null;
		if(file!=null) {
			if (file.getContentType().equals("application/pkcs7-mime")) {
				try {
					boolean verified = PKCSHelper.isP7MVerified(file.getBytes());
					if (verified) {
						this.file = file;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public Map getIdQualifiche() {
		Map map = null;
		try {
			if (sqlMap != null) {
				map = sqlMap.queryForMap("getAllQualificheMap", null,
						"descrizione", "idQualifica");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getWhereClause() {
		return whereClause;
	}

	public void setWhereClause(String whereClause) {
		this.whereClause = whereClause;
	}

	public boolean isIntermediario() {
		return intermediario;
	}

	public void setIntermediario(boolean intermediario) {
		this.intermediario = intermediario;
	}
	
	public void checkIntermediario(Accreditamenti current) {
		this.current = current;
		String qualifica = current.getIdQualifica();
		Map param = new HashMap();
		param.put("whereClause", " id_qualifica LIKE '" + qualifica + "'");
		String tipoQualifica = null;
		try {
			tipoQualifica = (String) sqlMap.queryForObject("isIntermediario", param);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		if (tipoQualifica!=null && tipoQualifica.equalsIgnoreCase("intermediario")) {
			intermediario = true;
			current.setCodicefiscaleIntermediario(current.getPartitaivaIntermediario());
		} else {
			intermediario = false;
			current.setCodicefiscaleIntermediario(current.getCodiceFiscale());
		}
	}
}
