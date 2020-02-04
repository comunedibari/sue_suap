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
package it.idp.people.admin.faces;

import it.idp.people.admin.common.TableDAO;

import java.io.IOException;
import java.lang.reflect.Field;
import java.security.Provider;
import java.security.Security;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
 
import javax.faces.component.UIColumn;
import javax.faces.component.UICommand;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.el.ValueBinding;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.myfaces.component.html.ext.HtmlCommandLink;
import org.apache.myfaces.component.html.ext.HtmlDataTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class Manager {
	private static Logger logger = LoggerFactory.getLogger(Manager.class);

	private HashMap headers;

	private HtmlDataTable dynamicDataTable;

	private List list = null;

	private DataModel tableDataModel = null;

	private Object current = null;

	private TableDAO tableAction = null;

	private Class daoClass = null;

	private Class modelClass = null;

	private Paging paging = null;

	private static Properties properties = new Properties();;

	private boolean insert = false;

	private boolean enableFilter = false;

	private int view;

	public Manager() {
		boolean exist = false;
		Provider[] providers = Security.getProviders();
		for (int i = 0; i < providers.length; i++) {
			Provider tempProvider = (Provider) providers[i];
			if (tempProvider.getClass().equals(BouncyCastleProvider.class)) {
				exist = true;
				break;
			}
		}
		if (!exist) {
			Security.addProvider(new BouncyCastleProvider());
		}

		paging = new Paging();

		loadProperties();

		loadView();
	}

	public static void loadProperties() {
		java.net.URL url = Manager.class
				.getResource("/properties/app.properties");
		try {
			properties.load(url.openStream());
		} catch (IOException e) {
			logger.error("", e);
		}
	}

	public void createClassInstance() {
		try {
			daoClass = Class.forName(properties.getProperty("daoClassName" + view));
			modelClass = Class.forName(properties.getProperty("modelClassName" + view));
			tableAction = (TableDAO) daoClass.newInstance();
			headers = tableAction.getHeaders();
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	public void loadView() {
		Object viewObj = queryString("view");
		if (viewObj != null) {
			int tempView = Integer.parseInt((String) viewObj);
			if (view != tempView) {
				view = tempView;
				if (view < 1)
					view = 1;
				int maxview = Integer.parseInt(properties.getProperty("viewCount"));
				if (view > maxview)
					view = maxview;

				tableDataModel = new ListDataModel();
				dynamicDataTable = new HtmlDataTable();
				paging = new Paging();

				createClassInstance();
			}
		}
	}

	public static Object queryString(String key) {
		try {
			HttpServletRequest request = (HttpServletRequest) FacesContext
					.getCurrentInstance().getExternalContext().getRequest();
			return request.getParameter(key);
		} catch (Exception e) {
			logger.error("", e);
			return null;
		}
	}

	public List getList() {
		loadView();
		list = tableAction.getPaginatedList(paging.getFirstRowIndex(), paging.getrowsPerPage());
		paging.setRowsCount(tableAction.getRowsCount());
		populateDynamicDataTable();
		return list;
	}

	public DataModel getTableDataModel() {
		tableDataModel = new ListDataModel();
		tableDataModel.setWrappedData(list);
		setCurrent(null);
		return tableDataModel;
	}

	public void populateDynamicDataTable() {
		if (list != null && list.size() > 0) {
			dynamicDataTable = new HtmlDataTable();

			try {
				// Tramite reflection recupero i nomi delle propriet� del bean
				// i campi che non sono indicati nell'hashMap sono esclusi (es.
				// id)
				headers = tableAction.getHeaders();
				Field f[] = modelClass.getDeclaredFields();
				for (int i = 0; i < f.length; i++) {
					if (headers.containsKey(f[i].getName())) {
						// Creo l'header della colonna prendendo il nome dalla
						// lista di header
						UIOutput header = new UIOutput();
						header.setValue(headers.get(f[i].getName()));

						// Associo ad ogni record il link per accedere ai
						// dettagli
						HtmlCommandLink link = new HtmlCommandLink();
						MethodBinding cmdlink = FacesContext
								.getCurrentInstance().getApplication()
								.createMethodBinding("#{tableManager.select}",
										null);
						link.setAction(cmdlink);

						// Associo il valore della colonna con la propriet� del
						// bean
						// Il nome Item deve essere uguale al valore "var" della
						// dataTable nella jsp
						ValueBinding Item = FacesContext.getCurrentInstance()
								.getApplication().createValueBinding(
										"#{Item." + f[i].getName() + "}");
						link.setValueBinding("value", Item);

						// Costruisco le colonne e la tabella
						UIColumn column = new UIColumn();
						column.setHeader(header);
						column.getChildren().add(link);

						dynamicDataTable.getChildren().add(column);
					}
				}

				// Aggiungo in coda i pulsanti per selezione e eliminazione
				//
				// Creo l'ultima colonna, con un punto dello stesso colore dello
				// sfondo
				// per avere i bordi uniformi alle altre intestazioni
				UIColumn lastColumn = new UIColumn();
				UIOutput headerLastColumn = new UIOutput();
				headerLastColumn.setValue(" ");
				// headerLastColumn.getAttributes().put("style",
				// "color:#CAE8EA;"); leftmost
				lastColumn.setHeader(headerLastColumn);

				// Creo il pulsante di selezione
				UICommand commandSelect = new UICommand();
				MethodBinding commandSelectMethod = FacesContext
						.getCurrentInstance().getApplication()
						.createMethodBinding("#{tableManager.select}", null);
				commandSelect.setAction(commandSelectMethod);
				commandSelect.getAttributes().put("image", properties.getProperty("selectImage"));

				// Creo il pulsante di eliminazione
				UICommand commandDelete = new UICommand();
				MethodBinding commandDeleteMethod = FacesContext
						.getCurrentInstance().getApplication()
						.createMethodBinding("#{tableManager.delete}", null);
				commandDelete.setAction(commandDeleteMethod);
				commandDelete.getAttributes().put("image", properties.getProperty("deleteImage"));
				commandDelete.getAttributes().put("onclick", "if (!confirm('Confermare la cancellazione?')) return false");

				// Aggiungo i pulsanti alla colonna e la colonna al datatable
				lastColumn.getChildren().add(commandSelect);
				lastColumn.getChildren().add(commandDelete);
				dynamicDataTable.getChildren().add(lastColumn);
			} catch (Exception e) {
				logger.error("", e);
			}
		}
	}

	public HtmlDataTable getDynamicDataTable() {
		//getList();
		return dynamicDataTable;
	}

	public String select() {
		Object selected = tableDataModel.getRowData();
		setCurrent(selected);
		insert = false;
		return "selected";
	}

	public String delete() {
		Object selected = tableDataModel.getRowData();
		setCurrent(selected);
		return tableAction.delete(current);
	}

	public String setInsert() {
		Object selected;
		try {
			selected = modelClass.newInstance();
			setCurrent(selected);
			insert = true;
		} catch (Exception e) {
			return "error";
		}
		return "insert";
	}

	public Object getCurrent() {
		return current;
	}

	public void setCurrent(Object current) {
		this.current = current;
	}

	public Paging getPaging() {
		return paging;
	}

	public void setPaging(Paging paging) {
		this.paging = paging;
	}

	public TableDAO getTableAction() {
		return tableAction;
	}

	public String getButtonLabel() {
		if (insert) {
			return "Inserisci";
		} else {
			return "Aggiorna";
		}
	}

	public String buttonAction() {
		String result = "failed";
		if (insert) {
			result = tableAction.insert(current);
		} else {
			result = tableAction.update(current);
		}
		return result;
	}

	public String getDetailsPage() {
		loadView();
		return properties.getProperty("details" + view);
	}

	public static String PortalID() {
		if (properties == null)
			loadProperties();

		return properties.getProperty("idcomune");
	}

	public String getPortalID() {
		return properties.getProperty("idcomune");
	}

	public String getFilterPage() {
		loadView();
		return properties.getProperty("filter" + view);
	}

	public boolean isAscending() {
		return tableAction.isAscending();
	}

	public void setAscending(boolean ascending) {
		tableAction.setAscending(ascending);
	}

	public String getSortColumn() {
		return tableAction.getSortColumn();
	}

	public void setSortColumn(String sortColumn) {
		tableAction.setSortColumn(sortColumn);
	}

	public boolean isEnableFilter() {
		return enableFilter;
	}

	public void switchFilter() {
		if (enableFilter) {
			enableFilter = false;
		} else {
			enableFilter = true;
		}
	}

	public int getView() {
		return view;
	}

	public void setView(int view) {
		this.view = view;
	}

	public boolean isInsert() {
		return insert;
	}
}
