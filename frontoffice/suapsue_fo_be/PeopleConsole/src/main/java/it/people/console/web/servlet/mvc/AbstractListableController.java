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
package it.people.console.web.servlet.mvc;

import static it.people.console.web.servlet.tags.TagsConstants.*;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;

import it.people.console.beans.Option;
import it.people.console.beans.support.IFilter;
import it.people.console.beans.support.IFilters;
import it.people.console.domain.IPagedListHolderBean;
import it.people.console.persistence.beans.support.EditableRowInputData;
import it.people.console.persistence.beans.support.FilterProperties;
import it.people.console.persistence.beans.support.ILazyPagedListHolder;
import it.people.console.persistence.exceptions.LazyPagedListHolderException;
import it.people.console.persistence.jdbc.core.ColumnHeaderInformation;
import it.people.console.persistence.jdbc.core.EditableRow;
import it.people.console.security.AbstractCommand;
import it.people.console.utils.Constants;
import it.people.console.utils.StringUtils;
import it.people.console.web.servlet.tags.TagsConstants;
import it.people.console.web.utils.ParsedQueryString;
import it.people.console.web.utils.WebUtils;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 30/nov/2010 21.49.52
 *
 */
public abstract class AbstractListableController extends MessageSourceAwareController {

/*	
	pager button

	<pagedlistholder_prefix>.<pager>.<button>.<pagedlistholderid>

	pager rows per page

	<pagedlistholder_prefix>.<pager>.<button>.<pagedlistholderid>

	row action

	<pagedlistholder_prefix>.<action_prefix>.<actionquerystring>.<pagedlistholderid>

	row input

	<pagedlistholder_prefix>.<rowinput_prefix>.<columnid>.<pagedlistholderid>

	new row add

	<pagedlistholder_prefix>.<newrow_prefix>.<dummyparam>.<pagedlistholderid>


	al process action

	    un oggetto action
	    un oggetto con gli eventuali input e se la riga è nuova
	    
	    se delete vi sono i nomi colonna da query string
	    se save vi sono i nomi degli input	
*/
	
	
	@ModelAttribute("filtersList")
	public IFilters setupFilters() {
		return prepareFilters();
	}

	protected abstract IFilters prepareFilters();
	
	/**
	 * @return
	 */
	protected List<Option> getRowsPerPageList() {
		
		List<Option> result = new ArrayList<Option>();
		
		result.add(new Option("Tutte", ""));
		String pagedListsRowsPerPageList = this.getProperty("pagedlists.rowsPerPageList");
		StringTokenizer tokenizer = new StringTokenizer(pagedListsRowsPerPageList, "|");
		while(tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			result.add(new Option(token.trim(), token.trim()));
		}
		
		return result;
		
	}
	
	/**
	 * @param model
	 */
	protected void setRowsPerPageDefaultModelAttributes(ModelMap model) {
    	model.addAttribute("rowsForPageList", this.getRowsPerPageList());
    	model.addAttribute("rowsForPageLabel", this.getProperty("pagedlistholders.rowsPerPageLabel"));
    	model.addAttribute("rowsForPageRefreshLabel", this.getProperty("pagedlistholders.rowsPerPageRefreshLabel"));
    	model.addAttribute("tableRowsHighlightingColor", StringUtils.nullToEmpty(this.getProperty("pagedlistholders.defaultHiglightingColor")).trim());
	}

	protected List<FilterProperties> updateAppliedFilters(HttpServletRequest request, IFilters filtersList) {

		List<FilterProperties> result = new ArrayList<FilterProperties>();
		Vector<String> updatingFilters = new Vector<String>();

		@SuppressWarnings("unchecked")
		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = paramNames.nextElement();
			if (paramName.startsWith(FILTER_VALUE_PREFIX) && 
					paramName.endsWith(FILTER_TAG_CHECKBOX_SUFFIX)) {
				String filterName = StringUtils.extractString(FILTER_VALUE_PREFIX, 
						FILTER_TAG_CHECKBOX_SUFFIX, paramName);
				updatingFilters.add(filterName);
				if (logger.isDebugEnabled()) {
					logger.debug("Found applied filter = " + filterName);
				}
			}
		}
		if (!updatingFilters.isEmpty()) {
			result = updateFilters(updatingFilters, filtersList, request);
		}

		return result;

	}

	protected List<FilterProperties> updateFilters(Vector<String> updatingFilters, IFilters filtersList, HttpServletRequest request) {

		List<FilterProperties> result = new ArrayList<FilterProperties>();

		Iterator<IFilter> filtersListIterator = filtersList.getFilters().iterator();
		while(filtersListIterator.hasNext()) {
			IFilter filter = filtersListIterator.next();
			if (updatingFilters.contains(filter.getName())) {
				filter.setActive(true);
				String operatorValue = request.getParameter(FILTER_VALUE_PREFIX + 
						filter.getName() + FILTER_TAG_LOGICAL_OPERATORS_SUFFIX);
				String filterValue = "";
				if (filter.isListable()) {
					filterValue = request.getParameter(FILTER_VALUE_PREFIX + 
							filter.getName() + LISTABLE_FILTER_TAG_SELECT_SUFFIX);
				}
				else {
					filterValue = request.getParameter(FILTER_VALUE_PREFIX + 
							filter.getName() + NOT_LISTABLE_FILTER_TAG_TEXT_SUFFIX);
				}
				filter.setOperator(operatorValue);
				filter.setValue(filterValue);
				result.add(new FilterProperties(filter.getName(), operatorValue, filterValue, filter.getType()));
				if (logger.isDebugEnabled()) {
					logger.debug("Filter " + filter.getName() + " data:");
					logger.debug("\tOperator value = '" + operatorValue + "'.");
					logger.debug("\tFilter value = '" + filterValue + "'.");
				}
			}
			else {
				filter.setActive(false);
			}
		}

		return result;

	}

	@SuppressWarnings("unchecked")
	protected void processListHoldersRequests(HttpServletRequest request, 
			IPagedListHolderBean pagedListHolderBean, ModelMap modelMap) {

		if (logger.isDebugEnabled()) {
			logger.debug("Processing list holders requests...");
		}

		String[] pagedListHolderRequestParams = WebUtils.getPagedListHolderRequestParams(request);

		if (logger.isDebugEnabled()) {
			logger.debug("Paged list holder request params length = " + pagedListHolderRequestParams.length);
		}

		String[] parsedAction = getPagedListHolderAction(pagedListHolderRequestParams);

		
		if (parsedAction.length > 0) {
			
			String pagedListHolderId = parsedAction[3];

			if (parsedAction[1].equalsIgnoreCase(LIST_HOLDER_TABLE_PAGER_PREFIX)) {
				if (logger.isDebugEnabled()) {
					logger.debug("Action type = pager");
					logger.debug("Paged list holder id = " + pagedListHolderId);
				}
				if (parsedAction[2].equalsIgnoreCase(LIST_HOLDER_TABLE_PAGER_FIRST_BUTTON_PREFIX)) {
					pagedListHolderBean.getPagedListHolder(pagedListHolderId).firstPage();
				}
				if (parsedAction[2].equalsIgnoreCase(LIST_HOLDER_TABLE_PAGER_PREVIOUS_BUTTON_PREFIX)) {
					pagedListHolderBean.getPagedListHolder(pagedListHolderId).previousPage();
				}
				if (parsedAction[2].equalsIgnoreCase(LIST_HOLDER_TABLE_PAGER_NEXT_BUTTON_PREFIX)) {
					pagedListHolderBean.getPagedListHolder(pagedListHolderId).nextPage();
				}
				if (parsedAction[2].equalsIgnoreCase(LIST_HOLDER_TABLE_PAGER_LAST_BUTTON_PREFIX)) {
					pagedListHolderBean.getPagedListHolder(pagedListHolderId).lastPage();
				}
				if (parsedAction[2].equalsIgnoreCase(LIST_HOLDER_TABLE_PAGER_UPDATE_ROWS_PER_PAGE_HIDDEN_BUTTON_PREFIX)) {
					
					String valueId = LIST_HOLDER_TABLE_PAGER_UPDATE_ROWS_PER_PAGE_VALUE_PREFIX  + LIST_HOLDER_PARAM_SEPARATOR 
						+ pagedListHolderId;
					String requiredPageSize = (String)request.getParameter(valueId);
					if (requiredPageSize != null) {
						try {
							int newPageSize = 0;
							if (requiredPageSize.equalsIgnoreCase("")) {
								newPageSize = 1000000;
							} else {
								newPageSize = Integer.parseInt(requiredPageSize);
							}
							pagedListHolderBean.getPagedListHolder(pagedListHolderId).setPageSize(newPageSize);
							pagedListHolderBean.getPagedListHolder(pagedListHolderId).update();
						} catch(Exception e) {
							
						}
					}
				}
			}
			if (parsedAction[1].equalsIgnoreCase(LIST_HOLDER_TABLE_ACTION_PREFIX)) {
				if (logger.isDebugEnabled()) {
					logger.debug("Action type = row action");
				}
				String actionQuery = parsedAction[2];
				ParsedQueryString parsedQueryString = WebUtils.parseQueryString(actionQuery);
	
				//Specific action for controller
				String action = parsedQueryString.getQueryStringValues().get(LIST_HOLDER_TABLE_COMMAND_ACTION_QUERY_PARAM);
	
				//Integer id = Integer.parseInt(parsedQueryString.getQueryStringValues().get("id"));
	
				if (action.equalsIgnoreCase(AbstractCommand.CommandActions.edit.getAction())) {
					List<EditableRow> editableRows = (List<EditableRow>)pagedListHolderBean.getPagedListHolder(pagedListHolderId).getPageList();
					for(EditableRow row : editableRows) {
						//    					Integer rowId = (Integer)row.getRow().get("id");
						if (isSelectedRow(pagedListHolderBean.getPagedListHolder(pagedListHolderId).getRowColumnsIdentifiers(), row, parsedQueryString)) {
							row.setEditEnabled(true);
							pagedListHolderBean.getPagedListHolder(pagedListHolderId).setEditMode(true);
						}
					}
				}
	
				if (action.equalsIgnoreCase(AbstractCommand.CommandActions.save.getAction())) {
					List<EditableRow> editableRows = (List<EditableRow>)pagedListHolderBean.getPagedListHolder(pagedListHolderId).getPageList();
					for(EditableRow row : editableRows) {
						//    					Integer rowId = (Integer)row.getRow().get("id");
						if (isSelectedRow(pagedListHolderBean.getPagedListHolder(pagedListHolderId).getRowColumnsIdentifiers(), row, parsedQueryString)) {
							row.setEditEnabled(false);
							pagedListHolderBean.getPagedListHolder(pagedListHolderId).setEditMode(false);
						}
					}
				}
	
				if (action.equalsIgnoreCase(AbstractCommand.CommandActions.cancel.getAction())) {
					List<EditableRow> editableRows = (List<EditableRow>)pagedListHolderBean.getPagedListHolder(pagedListHolderId).getPageList();
					for(EditableRow row : editableRows) {
						//    					Integer rowId = (Integer)row.getRow().get("id");
						if (isSelectedRow(pagedListHolderBean.getPagedListHolder(pagedListHolderId).getRowColumnsIdentifiers(), row, parsedQueryString)) {
							row.setEditEnabled(false);
							pagedListHolderBean.getPagedListHolder(pagedListHolderId).setEditMode(false);
						}
					}
				}
	
				if (action.equalsIgnoreCase(AbstractCommand.CommandActions.cancelNew.getAction()) || 
						action.equalsIgnoreCase(AbstractCommand.CommandActions.saveNew.getAction())) {
					pagedListHolderBean.getPagedListHolder(pagedListHolderId).setNewRowEditMode(false);
				}
				
				if (action.equalsIgnoreCase(AbstractCommand.CommandActions.delete.getAction()) || 
						action.equalsIgnoreCase(AbstractCommand.CommandActions.save.getAction()) || 
						action.equalsIgnoreCase(AbstractCommand.CommandActions.saveNew.getAction())) {
					EditableRowInputData editableRowInputData = getEditableRowInputData(pagedListHolderRequestParams, parsedQueryString, request);
					if (AbstractCommand.CommandActions.getCommandAction(action).equals(AbstractCommand.CommandActions.delete)) {
						editableRowInputData.getInputData().put(DELETE_GUARD_SUFFIX, getDeleteGuardValue(request, pagedListHolderId));
					}
					preProcessAction(pagedListHolderId, 
							AbstractCommand.CommandActions.getCommandAction(action), 
							editableRowInputData, request, modelMap);
					//aggiorna la pagina 
					Collection<ILazyPagedListHolder> pagedListHoldersCollection = pagedListHolderBean.getPagedListHolders().values();
					Iterator<ILazyPagedListHolder> pagedListHoldersIterator = pagedListHoldersCollection.iterator();
					while(pagedListHoldersIterator.hasNext()) {
						pagedListHoldersIterator.next().update();
					}
	//				pagedListHolderBean.getPagedListHolder(pagedListHolderId).update();
				}
				
			}
			if (parsedAction[1].equalsIgnoreCase(LIST_HOLDER_TABLE_NEW_ROW_PREFIX)) {
				if (logger.isDebugEnabled()) {
					logger.debug("Action type = new row");
				}
				pagedListHolderBean.getPagedListHolder(pagedListHolderId).setNewRowEditMode(true);
			}
			
		}

	}

	private boolean isSelectedRow(List<String> rowColumnsIdentifiers, EditableRow row, ParsedQueryString parsedQueryString) {

		// Parameter "action" from parsedQueryString should not belong to bitset 
		int bitSetSize = parsedQueryString.getQueryStringValues().size() - 1;

		BitSet rowQueryStringComparisonResults = new BitSet(bitSetSize);
		int bitIndex = 0;
		for(String rowColumnsIdentifier : rowColumnsIdentifiers) {
			String valueFromRow = String.valueOf(row.getRow().get(rowColumnsIdentifier));
			String valueFromQueryString = String.valueOf(parsedQueryString.getQueryStringValues().get(rowColumnsIdentifier));
			rowQueryStringComparisonResults.set(bitIndex, (valueFromRow.compareTo(valueFromQueryString) == 0));
			bitIndex++;
		}

		return rowQueryStringComparisonResults.cardinality() == bitSetSize;

	}

	private String[] getPagedListHolderAction(String[] pagedListHolderRequestParams) {

		String[] result = {};

		for(String requestElement : pagedListHolderRequestParams) {

			String[] parsedRequest = WebUtils.parsePagedListHolderParam(requestElement);
			if (parsedRequest[1].equalsIgnoreCase(LIST_HOLDER_TABLE_PAGER_PREFIX) || 
					parsedRequest[1].equalsIgnoreCase(LIST_HOLDER_TABLE_ACTION_PREFIX) || 
					parsedRequest[1].equalsIgnoreCase(LIST_HOLDER_TABLE_NEW_ROW_PREFIX)) {
				result = parsedRequest;
				break;
			}

		}

		return result;

	}

	private EditableRowInputData getEditableRowInputData(String[] pagedListHolderRequestParams, ParsedQueryString parsedQueryString, 
			HttpServletRequest request) {
		
		Map<String, Object> inputData = new HashMap<String, Object>();
		
		Map<String, Object> rowIdentifiers = new HashMap<String, Object>();
		if (logger.isDebugEnabled()) {
			logger.debug("Parsed request element query string has " + parsedQueryString.getQueryStringValues().size() + " token(s).");
			logger.debug("Parsed request element = '" + parsedQueryString + "'.");
		}
		rowIdentifiers.putAll(parsedQueryString.getQueryStringValues());
		
		if (logger.isDebugEnabled()) {
			logger.debug("Getting editable row input data...");
		}
		
		for(String requestElement : pagedListHolderRequestParams) {
			String[] parsedRequestElement = WebUtils.parsePagedListHolderParam(requestElement);
			if (logger.isDebugEnabled()) {
				logger.debug("Parsing requestElement = '" + requestElement + "'...");
				logger.debug("Found " + parsedRequestElement.length + " token(s).");
			}
			if (parsedRequestElement[1].equalsIgnoreCase(LIST_HOLDER_TABLE_INPUT_PREFIX)) {
				if (logger.isDebugEnabled()) {
					logger.debug("Parsed request element is of type '" + LIST_HOLDER_TABLE_INPUT_PREFIX + "'.");
				}
				String inputName = parsedRequestElement[2];
				String inputValue = WebUtils.getRequestParamValue(request, requestElement);
				if (logger.isDebugEnabled()) {
					logger.debug("Parsed request element data:");
					logger.debug("\tinput name = '" + inputName + "'.");
					logger.debug("\tinput value = '" + inputValue + "'.");
				}
				inputData.put(inputName, inputValue);
			}
		}
		
		return new EditableRowInputData(rowIdentifiers,inputData);
		
	}
	
	
	protected void processListHoldersRequests(String queryString, IPagedListHolderBean pagedListHolderBean) {
		
		ParsedQueryString parsedQueryString = WebUtils.parseQueryString("?" + queryString);
		if (parsedQueryString.getQueryStringValues() != null && !parsedQueryString.getQueryStringValues().isEmpty() 
				&& parsedQueryString.getQueryStringValues().get("plh") != null) {
			for(ColumnHeaderInformation columnHeaderInformation : 
				pagedListHolderBean.getPagedListHolder(parsedQueryString.getQueryStringValues().get("plh")).getColumnsAliases()) {
				if (columnHeaderInformation.getName().equalsIgnoreCase(parsedQueryString.getQueryStringValues().get("column"))) {
					
					String querySytringSort = parsedQueryString.getQueryStringValues().get("sort");
					if (querySytringSort != null) {
						if (querySytringSort.equalsIgnoreCase(ColumnHeaderInformation.SortingTypes.asc.getSortType())) {
							columnHeaderInformation.setSortingType(ColumnHeaderInformation.SortingTypes.asc);
						} else {
							columnHeaderInformation.setSortingType(ColumnHeaderInformation.SortingTypes.desc);
						}
					}
					
					if (columnHeaderInformation.getSortingType() == null) {
						columnHeaderInformation.setSortingType(ColumnHeaderInformation.SortingTypes.asc);
					}
					else if (columnHeaderInformation.getSortingType().compareTo(ColumnHeaderInformation.SortingTypes.asc) == 0) {
						columnHeaderInformation.setSortingType(ColumnHeaderInformation.SortingTypes.desc);
					}
					else {
						columnHeaderInformation.setSortingType(ColumnHeaderInformation.SortingTypes.asc);
					}
					break;
				}
			}
		}
	}
	
	private String getDeleteGuardValue(HttpServletRequest request, String pagedListHolderId) {

		return StringUtils.nullToEmpty(request.getParameter(pagedListHolderId + DELETE_GUARD_SUFFIX));
		
	}
	
	protected void applyColumnSorting(HttpServletRequest request, IPagedListHolderBean pagedListHolderBean) throws LazyPagedListHolderException {
		if (request.getQueryString() != null && request.getQueryString().startsWith("action=sort")) {
			processListHoldersRequests(request.getQueryString(), pagedListHolderBean);
			ParsedQueryString parsedColumnSort = WebUtils.parseQueryString("?" + request.getQueryString());
			pagedListHolderBean.getPagedListHolder(parsedColumnSort.getQueryStringValues().get("plh"))
				.applyOrder(parsedColumnSort.getQueryStringValues().get("column"), 
						parsedColumnSort.getQueryStringValues().get("sort"));
		}
	}

	private void preProcessAction(String pagedListHolderId, AbstractCommand.CommandActions action, 
			EditableRowInputData editableRowInputData, HttpServletRequest request, 
			ModelMap modelMap) {

		if (action == AbstractCommand.CommandActions.delete) {
			boolean deleteGuardVerified = ((String)editableRowInputData.getInputData()
					.get(TagsConstants.DELETE_GUARD_SUFFIX))
						.equalsIgnoreCase(TagsConstants.DELETE_GUARD_VERIFIED);
				if (logger.isDebugEnabled()) {
					if (!deleteGuardVerified) {
						logger.debug("Delete action not verified, switching to confirm page");
					} else {
						logger.debug("Delete action verified, proceeding with delete action");
					}
				}
				if (!deleteGuardVerified) {
					this.holdProcessActionData(pagedListHolderId, action, editableRowInputData, request);
				}
				request.setAttribute(Constants.ControllerUtils.DELETE_CONFIRMATION_REQUIRED, !deleteGuardVerified);
		}
		
		processAction(pagedListHolderId, action, editableRowInputData, request, modelMap);
	}
	
	protected abstract void processAction(String pagedListHolderId, 
			AbstractCommand.CommandActions action, EditableRowInputData editableRowInputData, 
			HttpServletRequest request, ModelMap modelMap);
	
}
