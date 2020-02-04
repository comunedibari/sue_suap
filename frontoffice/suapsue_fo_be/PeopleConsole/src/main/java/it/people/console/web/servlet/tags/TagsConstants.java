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
package it.people.console.web.servlet.tags;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 04/gen/2011 09.25.05
 *
 */
public interface TagsConstants {

	public static final String SHOW_DETAIL = "show";
	
	public static final String HIDE_DETAIL = "hide";

	public static final String STATE_SUFFIX = "_state";

	public static final String FILTER_VALUE_PREFIX = "_filtervalue_";
	
	public static final String FILTER_TAG_CHECKBOX_SUFFIX = "_cb_";

	public static final String LISTABLE_FILTER_TAG_CHECKBOX_SUFFIX = "_ls_";

	public static final String NOT_LISTABLE_FILTER_TAG_CHECKBOX_SUFFIX = "_nls_";
	
	public static final String FILTER_TAG_LOGICAL_OPERATORS_SUFFIX = "_lo_";

	public static final String LISTABLE_FILTER_TAG_SELECT_SUFFIX = "_se_";

	public static final String NOT_LISTABLE_FILTER_TAG_TEXT_SUFFIX = "_tx_";

	public static final String FILTER_ROW_CLEARFIX_CLASS = "clearfix";
	
	public static final String FILTER_ROW_WIDTH_CLASS = "filterrowwidth";

	public static final String FILTER_ROW_LEFT_COLUMN_CLASS = "filterleftcolumn";
	
	public static final String FILTER_ROW_TWO_COLUMNS_CLASS = "filtertwocolumns";

	public static final String FILTER_ROW_RIGHT_COLUMN_CLASS = "filterrightcolumn";

	public static final String FILTER_ROW_MAIN_COLUMN_CLASS = "filtermaincolumn";
	
	public static final String LIST_HOLDER_TABLE_DEFAULT_FIRST_BUTTON_ENABLED_IMAGE = "arrow-first.gif";
	
	public static final String LIST_HOLDER_TABLE_DEFAULT_PREVIOUS_BUTTON_ENABLED_IMAGE = "arrow-previous.gif";
	
	public static final String LIST_HOLDER_TABLE_DEFAULT_NEXT_BUTTON_ENABLED_IMAGE = "arrow-next.gif";

	public static final String LIST_HOLDER_TABLE_DEFAULT_REFRESH_PAGE_SIZE_BUTTON_IMAGE = "view-refresh.png";
	
	public static final String LIST_HOLDER_TABLE_DEFAULT_LAST_BUTTON_ENABLED_IMAGE = "arrow-last.gif";

	public static final String LIST_HOLDER_TABLE_DEFAULT_FIRST_BUTTON_DISABLED_IMAGE = "arrow-first-dis.gif";
	
	public static final String LIST_HOLDER_TABLE_DEFAULT_PREVIOUS_BUTTON_DISABLED_IMAGE = "arrow-previous-dis.gif";
	
	public static final String LIST_HOLDER_TABLE_DEFAULT_NEXT_BUTTON_DISABLED_IMAGE = "arrow-next-dis.gif";
	
	public static final String LIST_HOLDER_TABLE_DEFAULT_LAST_BUTTON_DISABLED_IMAGE = "arrow-last-dis.gif";

	public static final String LIST_HOLDER_TABLE_DEFAULT_REFRESH_PAGE_SIZE_BUTTON_DISABLED_IMAGE = "view-refresh-dis.png";
	
	public static final String LIST_HOLDER_TABLE_DEFAULT_ADD_ROW_IMAGE = "table-row-insert.png";
	
	public static final String LIST_HOLDER_TABLE_DEFAULT_PAGER_PAGE_TITLE = "Pagina ";

	public static final String LIST_HOLDER_TABLE_DEFAULT_PAGER_PAGE_OF_VALUE = " di ";

	public static final String LIST_HOLDER_TABLE_PREFIX = "_lht_";
	
	public static final String LIST_HOLDER_TABLE_PAGER_PREFIX = "_pg_";

	public static final String LIST_HOLDER_TABLE_PAGER_UPDATE_ROWS_PER_PAGE_BUTTON_PREFIX = "_urppb_";

	public static final String LIST_HOLDER_TABLE_PAGER_UPDATE_ROWS_PER_PAGE_HIDDEN_BUTTON_PREFIX = "_urpphb_";
	
	public static final String LIST_HOLDER_TABLE_PAGER_UPDATE_ROWS_PER_PAGE_VALUE_PREFIX = "_urppv_";
	
	public static final String LIST_HOLDER_TABLE_PAGER_FIRST_BUTTON_PREFIX = "_fb_";

	public static final String LIST_HOLDER_TABLE_PAGER_PREVIOUS_BUTTON_PREFIX = "_pb_";

	public static final String LIST_HOLDER_TABLE_PAGER_NEXT_BUTTON_PREFIX = "_nb_";

	public static final String LIST_HOLDER_TABLE_PAGER_LAST_BUTTON_PREFIX = "_lb_";

	public static final String LIST_HOLDER_TABLE_ACTION_PREFIX = "_act_";

	public static final String LIST_HOLDER_TABLE_INPUT_PREFIX = "_input_";

	public static final String LIST_HOLDER_TABLE_NEW_ROW_PREFIX = "_newrow_";
	
	public static final String LIST_HOLDER_TABLE_COMMAND_ACTION_DUMMY_PARAM = "_dp_";
	
	public static final String LIST_HOLDER_TABLE_COMMAND_ACTION_QUERY_PARAM = "action";

	public static final String LIST_HOLDER_TABLE_COMMAND_ACTION_TYPE_QUERY_PARAM = "type";
	
	public static final String LIST_HOLDER_PARAM_SEPARATOR = "#@@#";

	public static final String DEFAULT_LEFT_ALIGNMENT_CSS_STYLE = "text-align: left;";

	public static final String DEFAULT_CENTER_ALIGNMENT_CSS_STYLE = "text-align: center;";

	public static final String DEFAULT_RIGHT_ALIGNMENT_CSS_STYLE = "text-align: right;";

	public static final String NEW_ROW_ACTION_TYPE = "new";
	
	public static final String DELETE_GUARD_SUFFIX = "_deleteGuard";

	public static final String DELETE_GUARD_VERIFIED = "deleteVerified";

	public static final String DELETE_GUARD_NOT_VERIFIED = "deleteNotVerified";
	
	public static final String DEFAULT_VERSION_NUMBER_SEPARATOR = ".";
	
	public static final String DETAILS_STATUSES_HASHMAP_SESSION_KEY = "DETAILS_STATUSES_HASHMAP_SESSION_KEY";
	
}
