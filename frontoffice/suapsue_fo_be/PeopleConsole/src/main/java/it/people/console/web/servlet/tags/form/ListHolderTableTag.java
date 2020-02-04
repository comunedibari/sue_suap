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
package it.people.console.web.servlet.tags.form;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.util.Assert;
import org.springframework.web.servlet.tags.form.AbstractHtmlInputElementTag;
import org.springframework.web.servlet.tags.form.TagWriter;

import it.people.console.beans.Option;
import it.people.console.domain.PairElement;
import it.people.console.java.util.PagedListHoldersTreeMap;
import it.people.console.persistence.jdbc.core.ColumnHeaderInformation;
import it.people.console.persistence.jdbc.core.ColumnHeaderInformation.SortingTypes;
import it.people.console.persistence.jdbc.core.EditableRow;
import it.people.console.persistence.beans.support.EditableRowActions;
import it.people.console.persistence.beans.support.EditableRowCheckbox;
import it.people.console.persistence.beans.support.EditableRowSelect;
import it.people.console.persistence.beans.support.ILazyPagedListHolder;
import it.people.console.persistence.beans.support.RowCheckbox;
import it.people.console.persistence.jdbc.core.ColumnMetaData;
import it.people.console.persistence.jdbc.support.Decodable;
import it.people.console.persistence.jdbc.support.IRowsStatusModeler;
import it.people.console.persistence.jdbc.support.QueryColumnExecutor;
import it.people.console.persistence.jdbc.support.RowStatusModeler;
import it.people.console.security.AbstractCommand;
import it.people.console.security.Command;
import it.people.console.utils.CastUtils;
import it.people.console.utils.EscapeUtils;
import it.people.console.utils.StringUtils;
import it.people.console.web.servlet.tags.TagUtils;
import it.people.console.web.utils.WebUtils;

import static it.people.console.web.servlet.tags.TagsConstants.*;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 04/gen/2011 09.07.25
 *
 */
public class ListHolderTableTag extends AbstractHtmlInputElementTag {

	private static Logger logger = LoggerFactory.getLogger(ListHolderTableTag.class);
	
	private static final long serialVersionUID = 5306401934390281119L;

	private String pagerButtonsEnabledImages;

	private String pagerButtonsDisabledImages;
	
	private String pagerButtonsAlts;
	
	private String pagerButtonsTitles;
	
	private String pagerPageTitle;
	
	private String pagerPageOfValue;
	
	private Object pagedListHoldersCache;

	private Object rowsForPageList;
	
	private String pagedListHolderId;
	
	private String componentClass;
	
	private String pagerClass;
	
	private String pagerPagesTitleClass;
	
	private String tableClass;
	
	private String tableHeadClass;
	
	private String tableHeadRowClass;
	
	private String tableHeadRowCellClass;
	
	private String tableBodyClass;
	
	private String useTableBodyEvenOddRows;
	
	private String tableBodyRowClass;

	private String tableBodyEvenRowClass;

	private String tableBodyOddRowClass;
	
	private String tableBodyRowCellClass;
	
	private String rowsForPageClass;
	
	private String rowsForPageLabel;

	private String highlightTableRows;
	
	private String tableRowsHighlightingColor;
	
	private String rowsForPageRefreshLabel;
	
	private Vector<String> pagerButtonsEnabledImagesBuffer = new Vector<String>();

	private Vector<String> pagerButtonsDisabledImagesBuffer = new Vector<String>();
	
	private Vector<String> pagerButtonsAltsBuffer = new Vector<String>();

	private Vector<String> pagerButtonsTitlesBuffer = new Vector<String>();
	
	/**
	 * @param pagerButtonsEnabledImages the pagerButtonsEnabledImages to set
	 */
	public final void setPagerButtonsEnabledImages(String pagerButtonsEnabledImages) {
		this.pagerButtonsEnabledImages = pagerButtonsEnabledImages;
	}

	/**
	 * @param pagerButtonsDisabledImages the pagerButtonsDisabledImages to set
	 */
	public final void setPagerButtonsDisabledImages(String pagerButtonsDisabledImages) {
		this.pagerButtonsDisabledImages = pagerButtonsDisabledImages;
	}
	
	/**
	 * @param pagerButtonsAlts the pagerButtonsAlts to set
	 */
	public final void setPagerButtonsAlts(String pagerButtonsAlts) {
		this.pagerButtonsAlts = pagerButtonsAlts;
	}

	/**
	 * @param pagerButtonsTitles the pagerButtonsTitles to set
	 */
	public final void setPagerButtonsTitles(String pagerButtonsTitles) {
		this.pagerButtonsTitles = pagerButtonsTitles;
	}

	/**
	 * @param pagerPageTitle the pagerPageTitle to set
	 */
	public final void setPagerPageTitle(String pagerPageTitle) {
		this.pagerPageTitle = pagerPageTitle;
	}

	/**
	 * @param pagerPageOfValue the pagerPageOfValue to set
	 */
	public final void setPagerPageOfValue(String pagerPageOfValue) {
		this.pagerPageOfValue = pagerPageOfValue;
	}

	/**
	 * @param pagedListHoldersCache the pagedListHoldersCache to set
	 */
	public final void setPagedListHoldersCache(Object pagedListHoldersCache) {
		Assert.notNull(pagedListHoldersCache, "'pagedListHoldersCache' must not be null");
		Assert.isInstanceOf(PagedListHoldersTreeMap.class, pagedListHoldersCache, "'pagedListHoldersCache' must be of PagedListHoldersTreeMap type class");
		this.pagedListHoldersCache = pagedListHoldersCache;
	}

	/**
	 * @param rowsForPageList the rowsForPageList to set
	 */
	public final void setRowsForPageList(Object rowsForPageList) {
		this.rowsForPageList = rowsForPageList;
	}
	
	/**
	 * @param pagedListHolderId the pagedListHolderId to set
	 */
	public final void setPagedListHolderId(String pagedListHolderId) {
		Assert.hasText(pagedListHolderId, "'pagedListHolderId' must not be null or empty string");
		this.pagedListHolderId = pagedListHolderId;
	}

	/**
	 * @param componentClass the componentClass to set
	 */
	public final void setComponentClass(String componentClass) {
		this.componentClass = componentClass;
	}
	
	/**
	 * @param pagerClass the pagerClass to set
	 */
	public final void setPagerClass(String pagerClass) {
		this.pagerClass = pagerClass;
	}

	/**
	 * @param tableClass the tableClass to set
	 */
	public final void setTableClass(String tableClass) {
		this.tableClass = tableClass;
	}

	/**
	 * @param tableHeadClass the tableHeadClass to set
	 */
	public final void setTableHeadClass(String tableHeadClass) {
		this.tableHeadClass = tableHeadClass;
	}

	/**
	 * @param tableHeadRowClass the tableHeadRowClass to set
	 */
	public final void setTableHeadRowClass(String tableHeadRowClass) {
		this.tableHeadRowClass = tableHeadRowClass;
	}

	/**
	 * @param tableHeadRowCellClass the tableHeadRowCellClass to set
	 */
	public final void setTableHeadRowCellClass(String tableHeadRowCellClass) {
		this.tableHeadRowCellClass = tableHeadRowCellClass;
	}

	/**
	 * @param tableBodyClass the tableBodyClass to set
	 */
	public final void setTableBodyClass(String tableBodyClass) {
		this.tableBodyClass = tableBodyClass;
	}

	/**
	 * @param tableBodyRowClass the tableBodyRowClass to set
	 */
	public final void setTableBodyRowClass(String tableBodyRowClass) {
		this.tableBodyRowClass = tableBodyRowClass;
	}

	/**
	 * @param tableBodyRowCellClass the tableBodyRowCellClass to set
	 */
	public final void setTableBodyRowCellClass(String tableBodyRowCellClass) {
		this.tableBodyRowCellClass = tableBodyRowCellClass;
	}

	/**
	 * @return the pagerButtonsEnabledImages
	 */
	private String getPagerButtonsEnabledImages() {
		return pagerButtonsEnabledImages;
	}

	/**
	 * @return the pagerButtonsDisabledImages
	 */
	private String getPagerButtonsDisabledImages() {
		return pagerButtonsDisabledImages;
	}
	
	/**
	 * @return the pagerButtonsAlts
	 */
	private String getPagerButtonsAlts() {
		return pagerButtonsAlts;
	}

	/**
	 * @return the pagerButtonsTitles
	 */
	private String getPagerButtonsTitles() {
		return pagerButtonsTitles;
	}

	/**
	 * @return the pagerPageTitle
	 */
	private String getPagerPageTitle() {
		return pagerPageTitle;
	}

	/**
	 * @return the pagerPageOfValue
	 */
	private String getPagerPageOfValue() {
		return pagerPageOfValue;
	}

	/**
	 * @return the pagerPagesTitleClass
	 */
	public final String getPagerPagesTitleClass() {
		return pagerPagesTitleClass;
	}

	/**
	 * @param pagerPagesTitleClass the pagerPagesTitleClass to set
	 */
	public final void setPagerPagesTitleClass(String pagerPagesTitleClass) {
		this.pagerPagesTitleClass = pagerPagesTitleClass;
	}

	/**
	 * @return the pagedListHoldersCache
	 */
	private PagedListHoldersTreeMap getPagedListHoldersCache() {
		return (PagedListHoldersTreeMap)pagedListHoldersCache;
	}

	/**
	 * @return the pagedListHolderId
	 */
	private String getPagedListHolderId() {
		return pagedListHolderId;
	}

	/**
	 * @return the componentClass
	 */
	private String getComponentClass() {
		return componentClass;
	}
	
	/**
	 * @return the pagerClass
	 */
	private String getPagerClass() {
		return pagerClass;
	}

	/**
	 * @return the tableClass
	 */
	private String getTableClass() {
		return tableClass;
	}

	/**
	 * @return the tableHeadClass
	 */
	private String getTableHeadClass() {
		return tableHeadClass;
	}

	/**
	 * @return the tableHeadRowClass
	 */
	private String getTableHeadRowClass() {
		return tableHeadRowClass;
	}

	/**
	 * @return the tableHeadRowCellClass
	 */
	private String getTableHeadRowCellClass() {
		return tableHeadRowCellClass;
	}

	/**
	 * @return the tableBodyClass
	 */
	private String getTableBodyClass() {
		return tableBodyClass;
	}

	/**
	 * @return the tableBodyRowClass
	 */
	private String getTableBodyRowClass() {
		return tableBodyRowClass;
	}
	
	/**
	 * @return the useTableBodyEvenOddRows
	 */
	private boolean isUseTableBodyEvenOddRows() {
		return Boolean.parseBoolean(this.useTableBodyEvenOddRows);
	}

	/**
	 * @param useTableBodyEvenOddRows the useTableBodyEvenOddRows to set
	 */
	public final void setUseTableBodyEvenOddRows(String useTableBodyEvenOddRows) {
		this.useTableBodyEvenOddRows = useTableBodyEvenOddRows;
	}

	/**
	 * @return the tableBodyEvenRowClass
	 */
	private String getTableBodyEvenRowClass() {
		return this.tableBodyEvenRowClass;
	}

	/**
	 * @param tableBodyEvenRowClass the tableBodyEvenRowClass to set
	 */
	public final void setTableBodyEvenRowClass(String tableBodyEvenRowClass) {
		this.tableBodyEvenRowClass = tableBodyEvenRowClass;
	}

	/**
	 * @return the tableBodyOddRowClass
	 */
	private String getTableBodyOddRowClass() {
		return this.tableBodyOddRowClass;
	}

	/**
	 * @param tableBodyOddRowClass the tableBodyOddRowClass to set
	 */
	public final void setTableBodyOddRowClass(String tableBodyOddRowClass) {
		this.tableBodyOddRowClass = tableBodyOddRowClass;
	}
	
	/**
	 * @return the tableBodyRowCellClass
	 */
	private String getTableBodyRowCellClass() {
		return tableBodyRowCellClass;
	}

	
	/**
	 * @return the pagerButtonsEnabledImagesBuffer
	 */
	private Vector<String> getPagerButtonsEnabledImagesBuffer() {
		return pagerButtonsEnabledImagesBuffer;
	}

	/**
	 * @param pagerButtonsEnabledImagesBuffer the pagerButtonsEnabledImagesBuffer to set
	 */
	private void setPagerButtonsEnabledImagesBuffer(Vector<String> pagerButtonsEnabledImagesBuffer) {
		this.pagerButtonsEnabledImagesBuffer = pagerButtonsEnabledImagesBuffer;
	}

	/**
	 * @return the pagerButtonsDisabledImagesBuffer
	 */
	private Vector<String> getPagerButtonsDisabledImagesBuffer() {
		return pagerButtonsDisabledImagesBuffer;
	}

	/**
	 * @param pagerButtonsDisabledImagesBuffer the pagerButtonsDisabledImagesBuffer to set
	 */
	private void setPagerButtonsDisabledImagesBuffer(Vector<String> pagerButtonsDisabledImagesBuffer) {
		this.pagerButtonsDisabledImagesBuffer = pagerButtonsDisabledImagesBuffer;
	}
	
	/**
	 * @return the pagerButtonsAltsBuffer
	 */
	private Vector<String> getPagerButtonsAltsBuffer() {
		return pagerButtonsAltsBuffer;
	}

	/**
	 * @param pagerButtonsAltsBuffer the pagerButtonsAltsBuffer to set
	 */
	private void setPagerButtonsAltsBuffer(Vector<String> pagerButtonsAltsBuffer) {
		this.pagerButtonsAltsBuffer = pagerButtonsAltsBuffer;
	}

	/**
	 * @return the pagerButtonsTitlesBuffer
	 */
	private Vector<String> getPagerButtonsTitlesBuffer() {
		return pagerButtonsTitlesBuffer;
	}

	/**
	 * @param pagerButtonsTitlesBuffer the pagerButtonsTitlesBuffer to set
	 */
	private void setPagerButtonsTitlesBuffer(Vector<String> pagerButtonsTitlesBuffer) {
		this.pagerButtonsTitlesBuffer = pagerButtonsTitlesBuffer;
	}

	/**
	 * @return the rowsForPageClass
	 */
	private String getRowsForPageClass() {
		return this.rowsForPageClass;
	}

	/**
	 * @param rowsForPageClass the rowsForPageClass to set
	 */
	public final void setRowsForPageClass(String rowsForPageClass) {
		this.rowsForPageClass = rowsForPageClass;
	}

	/**
	 * @return the rowsForPageLabel
	 */
	private String getRowsForPageLabel() {
		return this.rowsForPageLabel;
	}

	/**
	 * @param rowsForPageLabel the rowsForPageLabel to set
	 */
	public final void setRowsForPageLabel(String rowsForPageLabel) {
		this.rowsForPageLabel = rowsForPageLabel;
	}

	/**
	 * @return the highlightTableRows
	 */
	private Boolean isHighlightTableRows() {
		return Boolean.parseBoolean(this.highlightTableRows);
	}

	/**
	 * @param highlightTableRows the highlightTableRows to set
	 */
	public final void setHighlightTableRows(String highlightTableRows) {
		this.highlightTableRows = highlightTableRows;
	}

	/**
	 * @return the tableRowsHighlightingColor
	 */
	private String getTableRowsHighlightingColor() {
		return this.tableRowsHighlightingColor;
	}

	/**
	 * @param tableRowsHighlightingColor the tableRowsHighlightingColor to set
	 */
	public final void setTableRowsHighlightingColor(
			String tableRowsHighlightingColor) {
		this.tableRowsHighlightingColor = tableRowsHighlightingColor;
	}

	/**
	 * @return the rowsForPageRefreshLabel
	 */
	private String getRowsForPageRefreshLabel() {
		return this.rowsForPageRefreshLabel;
	}

	/**
	 * @param rowsForPageRefreshLabel the rowsForPageRefreshLabel to set
	 */
	public final void setRowsForPageRefreshLabel(String rowsForPageRefreshLabel) {
		this.rowsForPageRefreshLabel = rowsForPageRefreshLabel;
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.tags.form.AbstractFormTag#writeTagContent(org.springframework.web.servlet.tags.form.TagWriter)
	 */
	@Override
	protected int writeTagContent(TagWriter tagWriter) throws JspException {

		if (!StringUtils.isEmptyString(this.getPagerButtonsEnabledImages())) {
			this.setPagerButtonsEnabledImagesBuffer(fillPagerButtonsEnabledImagesBuffer(this.getPagerButtonsEnabledImages()));
		}

		if (!StringUtils.isEmptyString(this.getPagerButtonsDisabledImages())) {
			this.setPagerButtonsDisabledImagesBuffer(fillPagerButtonsDisabledImagesBuffer(this.getPagerButtonsDisabledImages()));
		}
		
		if (!StringUtils.isEmptyString(this.getPagerButtonsAlts())) {
			this.setPagerButtonsAltsBuffer(fillPagerButtonsAltsBuffer(this.getPagerButtonsAlts()));
		}

		if (!StringUtils.isEmptyString(this.getPagerButtonsTitles())) {
			this.setPagerButtonsTitlesBuffer(fillPagerButtonsTitlesBuffer(this.getPagerButtonsTitles()));
		}

		boolean editableMode = this.getPagedListHoldersCache().get(this.getPagedListHolderId()).isEditMode() 
			|| this.getPagedListHoldersCache().get(this.getPagedListHolderId()).isNewRowEditMode();
		
		tagWriter.startTag("div");
		if (!StringUtils.isEmptyString(this.getComponentClass())) {
			tagWriter.writeAttribute("class", this.getComponentClass());
		}
		boolean writePagerButtons = this.getPagedListHoldersCache().get(this.getPagedListHolderId()).getPageCount() > 1;
		writePager(tagWriter, editableMode, writePagerButtons);
		writeDeleteGuard(tagWriter);
		writeTable(tagWriter);
		tagWriter.endTag(true);
		
		return EVAL_PAGE;
	}

	private Vector<String> fillPagerButtonsEnabledImagesBuffer(String pagerButtonsEnabledImages) {
		
		Vector<String> result = new Vector<String>();
		
		StringTokenizer tokenizer = new StringTokenizer(pagerButtonsEnabledImages , " ");
		while(tokenizer.hasMoreTokens()) {
			result.add(tokenizer.nextToken());
		}
		
		return result;
		
	}

	private Vector<String> fillPagerButtonsDisabledImagesBuffer(String pagerButtonsDisabledImages) {
		
		Vector<String> result = new Vector<String>();
		
		StringTokenizer tokenizer = new StringTokenizer(pagerButtonsDisabledImages , " ");
		while(tokenizer.hasMoreTokens()) {
			result.add(tokenizer.nextToken());
		}
		
		return result;
		
	}
	
	private Vector<String> fillPagerButtonsAltsBuffer(String pagerButtonsAlts) {
		
		Vector<String> result = new Vector<String>();
		
		StringTokenizer tokenizer = new StringTokenizer(pagerButtonsAlts , " ");
		while(tokenizer.hasMoreTokens()) {
			result.add(tokenizer.nextToken());
		}
		
		return result;
		
	}

	private Vector<String> fillPagerButtonsTitlesBuffer(String pagerButtonsTitles) {
		
		Vector<String> result = new Vector<String>();
		
		StringTokenizer tokenizer = new StringTokenizer(pagerButtonsTitles , " ");
		while(tokenizer.hasMoreTokens()) {
			result.add(tokenizer.nextToken());
		}
		
		return result;
		
	}
	
	private void writeDeleteGuard(TagWriter tagWriter) throws JspException {
		tagWriter.startTag("input");
		tagWriter.writeAttribute("type", "hidden");
		tagWriter.writeAttribute("name", this.getPagedListHolderId() + DELETE_GUARD_SUFFIX);
		tagWriter.writeAttribute("id", LIST_HOLDER_TABLE_PREFIX + LIST_HOLDER_PARAM_SEPARATOR + 
				this.getPagedListHolderId() + LIST_HOLDER_PARAM_SEPARATOR + DELETE_GUARD_SUFFIX);
		tagWriter.writeAttribute("value", DELETE_GUARD_NOT_VERIFIED);
		tagWriter.endTag(true);
	}
	
	private void writePager(TagWriter tagWriter, boolean editableMode, boolean writePagerButtons) throws JspException {
				
		tagWriter.startTag("div");
		if (!StringUtils.isEmptyString(this.getPagerClass())) {
			tagWriter.writeAttribute("class", this.getPagerClass());
		}

		writeRowsForPageList(tagWriter, editableMode);
		
		if (writePagerButtons) {
			writePagerFirstPrevButtons(tagWriter, editableMode);
			writePagerPageTitleAndOf(tagWriter, editableMode);
			writePagerNextLastButtons(tagWriter, editableMode);
		}
		tagWriter.endTag(true);
		
	}

	@SuppressWarnings("unchecked")
	private void writeRowsForPageList(TagWriter tagWriter, boolean editableMode) throws JspException {
		
		if (this.rowsForPageList != null && !((List<Option>)this.rowsForPageList).isEmpty()) {
			String hiddenButtonId = LIST_HOLDER_TABLE_PREFIX + LIST_HOLDER_PARAM_SEPARATOR 
				+ LIST_HOLDER_TABLE_PAGER_PREFIX  + LIST_HOLDER_PARAM_SEPARATOR 
				+ LIST_HOLDER_TABLE_PAGER_UPDATE_ROWS_PER_PAGE_HIDDEN_BUTTON_PREFIX  + LIST_HOLDER_PARAM_SEPARATOR 
				+ this.getPagedListHolderId();
			
			tagWriter.startTag("div");
			if (!StringUtils.isEmptyString(this.getRowsForPageClass())) {
				tagWriter.writeAttribute("class", this.getRowsForPageClass());
			}
			
			tagWriter.startTag("label");
			tagWriter.writeAttribute("for", LIST_HOLDER_TABLE_PAGER_UPDATE_ROWS_PER_PAGE_VALUE_PREFIX  + LIST_HOLDER_PARAM_SEPARATOR 
					+ this.getPagedListHolderId());
			tagWriter.appendValue(StringUtils.nullToEmpty(this.getRowsForPageLabel())+"&nbsp;");
			tagWriter.endTag();
			
			tagWriter.startTag("select");
			tagWriter.writeAttribute("id", LIST_HOLDER_TABLE_PAGER_UPDATE_ROWS_PER_PAGE_VALUE_PREFIX  + LIST_HOLDER_PARAM_SEPARATOR 
					+ this.getPagedListHolderId());
			tagWriter.writeAttribute("name", LIST_HOLDER_TABLE_PAGER_UPDATE_ROWS_PER_PAGE_VALUE_PREFIX  + LIST_HOLDER_PARAM_SEPARATOR 
					+ this.getPagedListHolderId());
			tagWriter.writeAttribute("onChange", "javascript:people.console.plh._addDynamicInput('hidden', '" + hiddenButtonId + "', '" + hiddenButtonId + "', '2'); submit();");		
			for(Option option : ((List<Option>)this.rowsForPageList)) {
				tagWriter.startTag("option");
				tagWriter.writeAttribute("value", option.getValue());
				if (String.valueOf(this.getPagedListHoldersCache().get(this.getPagedListHolderId()).getPageSize()).equalsIgnoreCase(option.getValue())) {
					tagWriter.writeAttribute("selected", "selected");
				}
				tagWriter.appendValue(option.getLabel());
				tagWriter.endTag(true);
			}
			tagWriter.endTag(true);
			
			tagWriter.startTag("noscript");

			if (editableMode) {
				tagWriter.startTag("img");
			} else {
				tagWriter.startTag("input");
				tagWriter.writeAttribute("type", "image");
				tagWriter.writeAttribute("name", LIST_HOLDER_TABLE_PREFIX + LIST_HOLDER_PARAM_SEPARATOR 
						+ LIST_HOLDER_TABLE_PAGER_PREFIX  + LIST_HOLDER_PARAM_SEPARATOR 
						+ LIST_HOLDER_TABLE_PAGER_UPDATE_ROWS_PER_PAGE_BUTTON_PREFIX  + LIST_HOLDER_PARAM_SEPARATOR 
						+ this.getPagedListHolderId());
				tagWriter.writeAttribute("id", LIST_HOLDER_TABLE_PREFIX + LIST_HOLDER_PARAM_SEPARATOR 
						+ LIST_HOLDER_TABLE_PAGER_PREFIX  + LIST_HOLDER_PARAM_SEPARATOR 
						+ LIST_HOLDER_TABLE_PAGER_UPDATE_ROWS_PER_PAGE_BUTTON_PREFIX  + LIST_HOLDER_PARAM_SEPARATOR 
						+ this.getPagedListHolderId());
				
				
			}
			tagWriter.writeAttribute("src", getButtonImage(PagerButtons.refreshPageSize, getRequest(), editableMode));
			tagWriter.endTag(true);

			tagWriter.startTag("label");
			tagWriter.writeAttribute("for", LIST_HOLDER_TABLE_PREFIX + LIST_HOLDER_PARAM_SEPARATOR 
					+ LIST_HOLDER_TABLE_PAGER_PREFIX  + LIST_HOLDER_PARAM_SEPARATOR 
					+ LIST_HOLDER_TABLE_PAGER_UPDATE_ROWS_PER_PAGE_BUTTON_PREFIX  + LIST_HOLDER_PARAM_SEPARATOR 
					+ this.getPagedListHolderId());
			tagWriter.appendValue(StringUtils.nullToEmpty(this.getRowsForPageRefreshLabel()));
			tagWriter.endTag(true);
			
			tagWriter.endTag(true);
			
			tagWriter.startTag("span");
			tagWriter.writeAttribute("id", "dynamicSpan");
			tagWriter.endTag();
			
			tagWriter.endTag(true);
		}
			
	}
	
	private void writePagerFirstPrevButtons(TagWriter tagWriter, boolean editableMode) throws JspException {
		
		if (editableMode) {
			tagWriter.startTag("img");
		} else {
			tagWriter.startTag("input");
			tagWriter.writeAttribute("type", "image");
			tagWriter.writeAttribute("name", LIST_HOLDER_TABLE_PREFIX + LIST_HOLDER_PARAM_SEPARATOR 
					+ LIST_HOLDER_TABLE_PAGER_PREFIX  + LIST_HOLDER_PARAM_SEPARATOR 
					+ LIST_HOLDER_TABLE_PAGER_FIRST_BUTTON_PREFIX  + LIST_HOLDER_PARAM_SEPARATOR 
					+ this.getPagedListHolderId());
			tagWriter.writeAttribute("id", LIST_HOLDER_TABLE_PREFIX + LIST_HOLDER_PARAM_SEPARATOR 
					+ LIST_HOLDER_TABLE_PAGER_PREFIX  + LIST_HOLDER_PARAM_SEPARATOR 
					+ LIST_HOLDER_TABLE_PAGER_FIRST_BUTTON_PREFIX  + LIST_HOLDER_PARAM_SEPARATOR 
					+ this.getPagedListHolderId());
		}
		tagWriter.writeAttribute("src", getButtonImage(PagerButtons.first, getRequest(), editableMode));
		tagWriter.endTag(true);

		if (editableMode) {
			tagWriter.startTag("img");
		} else {
			tagWriter.startTag("input");
			tagWriter.writeAttribute("type", "image");
			tagWriter.writeAttribute("name", LIST_HOLDER_TABLE_PREFIX + LIST_HOLDER_PARAM_SEPARATOR 
					+ LIST_HOLDER_TABLE_PAGER_PREFIX  + LIST_HOLDER_PARAM_SEPARATOR 
					+ LIST_HOLDER_TABLE_PAGER_PREVIOUS_BUTTON_PREFIX  + LIST_HOLDER_PARAM_SEPARATOR 
					+ this.getPagedListHolderId());
			tagWriter.writeAttribute("id", LIST_HOLDER_TABLE_PREFIX + LIST_HOLDER_PARAM_SEPARATOR 
					+ LIST_HOLDER_TABLE_PAGER_PREFIX  + LIST_HOLDER_PARAM_SEPARATOR 
					+ LIST_HOLDER_TABLE_PAGER_PREVIOUS_BUTTON_PREFIX  + LIST_HOLDER_PARAM_SEPARATOR 
					+ this.getPagedListHolderId());
		}
		tagWriter.writeAttribute("src", getButtonImage(PagerButtons.previous, getRequest(), editableMode));
		tagWriter.endTag(true);
		
	}

	private void writePagerPageTitleAndOf(TagWriter tagWriter, boolean editableMode) throws JspException {
		try {
			if (!StringUtils.isEmptyString(this.getPagerPagesTitleClass())) {
				getWriter().append("<span class=\"" + this.getPagerPagesTitleClass() + "\">");
			}
			getWriter().append(!StringUtils.isEmptyString(this.getPagerPageTitle()) ? 
					this.getPagerPageTitle() : LIST_HOLDER_TABLE_DEFAULT_PAGER_PAGE_TITLE);
			getWriter().append(String.valueOf(this.getPagedListHoldersCache().get(this.getPagedListHolderId()).getPage()));
			getWriter().append(!StringUtils.isEmptyString(this.getPagerPageOfValue()) ? 
					this.getPagerPageTitle() : LIST_HOLDER_TABLE_DEFAULT_PAGER_PAGE_OF_VALUE);
			getWriter().append(String.valueOf(this.getPagedListHoldersCache().get(this.getPagedListHolderId()).getPageCount()));
			if (!StringUtils.isEmptyString(this.getPagerPagesTitleClass())) {
				getWriter().append("</span>");
			}
		} catch (IOException e) {
			throw new JspException(e);
		}
	}

	private void writePagerNextLastButtons(TagWriter tagWriter, boolean editableMode) throws JspException {
		
		if (editableMode) {
			tagWriter.startTag("img");
		} else {
			tagWriter.startTag("input");
			tagWriter.writeAttribute("type", "image");
			tagWriter.writeAttribute("name", LIST_HOLDER_TABLE_PREFIX + LIST_HOLDER_PARAM_SEPARATOR 
					+ LIST_HOLDER_TABLE_PAGER_PREFIX  + LIST_HOLDER_PARAM_SEPARATOR 
					+ LIST_HOLDER_TABLE_PAGER_NEXT_BUTTON_PREFIX  + LIST_HOLDER_PARAM_SEPARATOR 
					+ this.getPagedListHolderId());
			tagWriter.writeAttribute("id", LIST_HOLDER_TABLE_PREFIX + LIST_HOLDER_PARAM_SEPARATOR 
					+ LIST_HOLDER_TABLE_PAGER_PREFIX  + LIST_HOLDER_PARAM_SEPARATOR 
					+ LIST_HOLDER_TABLE_PAGER_NEXT_BUTTON_PREFIX  + LIST_HOLDER_PARAM_SEPARATOR 
					+ this.getPagedListHolderId());
		}
		tagWriter.writeAttribute("src", getButtonImage(PagerButtons.next, getRequest(), editableMode));
		tagWriter.endTag(true);

		if (editableMode) {
			tagWriter.startTag("img");
		} else {
			tagWriter.startTag("input");
			tagWriter.writeAttribute("type", "image");
			tagWriter.writeAttribute("name", LIST_HOLDER_TABLE_PREFIX + LIST_HOLDER_PARAM_SEPARATOR 
					+ LIST_HOLDER_TABLE_PAGER_PREFIX  + LIST_HOLDER_PARAM_SEPARATOR 
					+ LIST_HOLDER_TABLE_PAGER_LAST_BUTTON_PREFIX  + LIST_HOLDER_PARAM_SEPARATOR 
					+ this.getPagedListHolderId());
			tagWriter.writeAttribute("id", LIST_HOLDER_TABLE_PREFIX + LIST_HOLDER_PARAM_SEPARATOR 
					+ LIST_HOLDER_TABLE_PAGER_PREFIX  + LIST_HOLDER_PARAM_SEPARATOR 
					+ LIST_HOLDER_TABLE_PAGER_LAST_BUTTON_PREFIX  + LIST_HOLDER_PARAM_SEPARATOR 
					+ this.getPagedListHolderId());
		}
		tagWriter.writeAttribute("src", getButtonImage(PagerButtons.last, getRequest(), editableMode));
		tagWriter.endTag(true);
		
	}
						
	private void writeTable(TagWriter tagWriter) throws JspException {
		
//		tagWriter.appendValue("<a name=\"" + this.getPagedListHolderId() + "\" />");
		tagWriter.startTag("table");
		if (!StringUtils.isEmptyString(this.getTableClass())) {
			tagWriter.writeAttribute("class", this.getTableClass());
		}
		
		if (this.isHighlightTableRows()) {
			tagWriter.writeAttribute("onMouseOver", "javascript:people.console.plh._trackTableHighlight(event, '" + this.getTableRowsHighlightingColor() + "');");
			tagWriter.writeAttribute("onMouseOut", "javascript:people.console.plh._highlightTableRow(0);");
		}
		
		writeTableHeaders(tagWriter, this.getPagedListHoldersCache().get(this.getPagedListHolderId()).getVisibleColumnsNames());
		writeTableBody(tagWriter);
		tagWriter.endTag(true);
		
	}

	private void writeTableHeaders(TagWriter tagWriter, List<String> visibleColumnsNames) throws JspException {

		tagWriter.startTag("thead");
		if (!StringUtils.isEmptyString(this.getTableHeadClass())) {
			tagWriter.writeAttribute("class", this.getTableHeadClass());
		}
		
		tagWriter.startTag("tr");
		if (!StringUtils.isEmptyString(this.getTableHeadRowClass())) {
			tagWriter.writeAttribute("class", this.getTableHeadRowClass());
		}
		
		if (this.getPagedListHoldersCache().get(this.getPagedListHolderId()).getRowsStatusModelers() != null) {
			tagWriter.startTag("th");
			tagWriter.endTag(true);
		}
		
		for(ColumnHeaderInformation columnHeaderInformation : this.getPagedListHoldersCache().get(this.getPagedListHolderId()).getColumnsAliases()) {
			if (visibleColumnsNames.contains(columnHeaderInformation.getName())) {
				tagWriter.startTag("th");
				if (!StringUtils.isEmptyString(this.getTableHeadRowCellClass())) {
					tagWriter.writeAttribute("class", this.getTableHeadRowCellClass());
				}
				tagWriter.appendValue(writeHeaderSortLink(columnHeaderInformation));
				tagWriter.endTag(true);
			}
		}

//		if (!this.getPagedListHoldersCache().get(this.getPagedListHolderId()).getRowActions().isEmpty()) {
			tagWriter.startTag("th");
			if (!StringUtils.isEmptyString(this.getTableHeadRowCellClass())) {
				tagWriter.writeAttribute("class", this.getTableHeadRowCellClass());
			}
			tagWriter.endTag(true);
//		}
		
		tagWriter.endTag(true);
		tagWriter.endTag(true);
		
	}

	private String writeHeaderSortLink(ColumnHeaderInformation columnHeaderInformation) {
		
		String result = "";
		
		if (columnHeaderInformation.getSortingType() == null) {
			result = "&nbsp;<a href=\"?action=sort&column=" + columnHeaderInformation.getName() + 
			"&sort=desc&plh=" + this.getPagedListHolderId() + "\">" + 
			columnHeaderInformation.getLabel() + getHeaderIcon(columnHeaderInformation) + "</a>";
		}
		else if (columnHeaderInformation.getSortingType().equals(ColumnHeaderInformation.SortingTypes.asc)) {
			result = "&nbsp;<a href=\"?action=sort&column=" + columnHeaderInformation.getName() + 
			"&sort=asc&plh=" + this.getPagedListHolderId() + "\">" + 
			columnHeaderInformation.getLabel() + getHeaderIcon(columnHeaderInformation) + "</a>";
		}
		else {
			result = "&nbsp;<a href=\"?action=sort&column=" + columnHeaderInformation.getName() + 
			"&sort=desc&plh=" + this.getPagedListHolderId() + "\">" + 
			columnHeaderInformation.getLabel() + getHeaderIcon(columnHeaderInformation) + "</a>";
		}
		
		return result;
		
	}
	
	private String getSortingType(ColumnHeaderInformation columnHeaderInformation) {
		return (columnHeaderInformation.getSortingType() == null) ? "" : "&sort=" + columnHeaderInformation.getSortingType().getSortType();
	}
	
	private String getHeaderIcon(ColumnHeaderInformation columnHeaderInformation) {
		
		String result = "";
		
		if (columnHeaderInformation.getSortingType() != null) {
			String image = "";
			if (columnHeaderInformation.getSortingType().equals(ColumnHeaderInformation.SortingTypes.asc)) {
				image = "sort-desc.png";
			}
			if (columnHeaderInformation.getSortingType().equals(ColumnHeaderInformation.SortingTypes.desc)) {
				image = "sort-asc.png";
			}
			result = "<img border=\"0\"" + TagUtils.writeHtmlSrcAttribute("src", image, getRequest()) + "\" />";
		}
		
		return result;
		
	}
	
	private void writeTableBody(TagWriter tagWriter) throws JspException {

		tagWriter.startTag("tbody");
		if (!StringUtils.isEmptyString(this.getTableBodyClass())) {
			tagWriter.writeAttribute("class", this.getTableBodyClass());
		}
		
		int rowIndex = 0;
		for(Object row : this.getPagedListHoldersCache().get(this.getPagedListHolderId()).getPageList()) {
			tagWriter.startTag("tr");
			if (!this.isUseTableBodyEvenOddRows()) {
				if (!StringUtils.isEmptyString(this.getTableBodyRowClass())) {
					tagWriter.writeAttribute("class", this.getTableBodyRowClass());
				}
			} else {
				if (!StringUtils.isEmptyString(this.getTableBodyEvenRowClass()) && 
						!StringUtils.isEmptyString(this.getTableBodyOddRowClass())) {
					tagWriter.writeAttribute("class", ((rowIndex % 2) == 0) ? 
							this.getTableBodyEvenRowClass() : this.getTableBodyOddRowClass());
				}
			}
			if (row instanceof EditableRow) {
				if (logger.isDebugEnabled()) {
					logger.debug("row instance of EditableRow.");
				}
				writeEditableRow(tagWriter, row, 
						this.getPagedListHoldersCache().get(this.getPagedListHolderId()).getEditableRowActions(), 
						this.getPagedListHoldersCache().get(this.getPagedListHolderId()).getRowColumnsIdentifiers(), 
						this.getPagedListHoldersCache().get(this.getPagedListHolderId()).getEditableRowColumns(), 
						this.getPagedListHoldersCache().get(this.getPagedListHolderId()).getVisibleColumnsNames(),
						this.getPagedListHoldersCache().get(this.getPagedListHolderId()).getEditableRowModelers(), 
						this.getPagedListHoldersCache().get(this.getPagedListHolderId()).getRowsStatusModelers(),
						this.getPagedListHoldersCache().get(this.getPagedListHolderId()).getExtendedColumnsData(),
						this.getPagedListHoldersCache().get(this.getPagedListHolderId()).getColumnsQueries());
			}
			else {
				if (logger.isDebugEnabled()) {
					logger.debug("row not instance of EditableRow.");
				}
				writeNotEditableRow(tagWriter, row, 
						this.getPagedListHoldersCache().get(this.getPagedListHolderId()).getRowActions(), 
						this.getPagedListHoldersCache().get(this.getPagedListHolderId()).getRowColumnsIdentifiers(), 
						this.getPagedListHoldersCache().get(this.getPagedListHolderId()).getVisibleColumnsNames(), 
						this.getPagedListHoldersCache().get(this.getPagedListHolderId()).getConverters(), 
						this.getPagedListHoldersCache().get(this.getPagedListHolderId()).getRowsStatusModelers(),
						this.getPagedListHoldersCache().get(this.getPagedListHolderId()).getExtendedColumnsData(),
						this.getPagedListHoldersCache().get(this.getPagedListHolderId()).getColumnsQueries(), 
						this.getPagedListHoldersCache().get(this.getPagedListHolderId()).getRowModelers());
			}
			tagWriter.endTag(true);
			rowIndex++;
		}
		
		if (this.getPagedListHoldersCache().get(this.getPagedListHolderId()).isCanAddRow()) {
			writeNewRowAdd(tagWriter, this.getPagedListHoldersCache().get(this.getPagedListHolderId()).getColumnsMetaData(), 
					this.getPagedListHoldersCache().get(this.getPagedListHolderId()).getVisibleColumnsNames(), 
					this.getPagedListHoldersCache().get(this.getPagedListHolderId()).getEditableRowColumns(), 
					this.getPagedListHoldersCache().get(this.getPagedListHolderId()).getEditableRowActions(), 
					this.getPagedListHoldersCache().get(this.getPagedListHolderId()).getRowColumnsIdentifiers(),
					this.getPagedListHoldersCache().get(this.getPagedListHolderId()).getRowsStatusModelers());
		}
		
		tagWriter.endTag(true);
		
	}

	private void writeEditableRow(TagWriter tagWriter, Object row, 
			EditableRowActions editableRowActions, 
			List<String> rowColumnsIdentifiers, List<String> editableRowColumns, 
			List<String> visibleColumnsNames, Map<String, Object> editableRowModelers, 
			IRowsStatusModeler<Integer> rowsStatusModelers, 
			Map<String, Vector<String>> extendedColumnsData, Map<String, String> columnsQueries) throws JspException {

		EditableRow editableRow = (EditableRow)row;
		Map<String, Object> mappedRow = editableRow.getRow();
		
		
		for(String columnKey : mappedRow.keySet()) {
			if (columnKey.equalsIgnoreCase("rowstatus") && rowsStatusModelers != null) {
				Integer statusKey = new Integer(String.valueOf(mappedRow.get(columnKey)));
//				Integer statusKey = (Integer)mappedRow.get(columnKey);
				RowStatusModeler rowStatusModeler = rowsStatusModelers.getRowStatusModeler(statusKey);
				if (rowStatusModeler != null) {
					tagWriter.startTag("td");
					tagWriter.startTag("img");
					tagWriter.writeAttribute("src",  WebUtils.getImagesBaseURL(getRequest()) + 
							rowStatusModeler.getImageFile());
					tagWriter.writeAttribute("alt",  rowStatusModeler.getAlt());
					tagWriter.writeAttribute("title",  rowStatusModeler.getTitle());
					tagWriter.endTag(true);
					tagWriter.endTag(true);
				}
			}
			if (visibleColumnsNames.contains(columnKey)) {
				tagWriter.startTag("td");
				if (!StringUtils.isEmptyString(this.getTableBodyRowCellClass())) {
					tagWriter.writeAttribute("class", this.getTableBodyRowCellClass());
				}
				else {
					tagWriter.writeAttribute("style", getDefaultTBodyCellCssClass(columnKey));
				}
				if (editableRow.isEditEnabled() && editableRowColumns.contains(columnKey)) {
					modelEditableRowControl(tagWriter, columnKey, mappedRow, editableRowModelers, true);
				}
				else {
					if (editableRowModelers != null && editableRowModelers.containsKey(columnKey)) {
						modelEditableRowControl(tagWriter, columnKey, mappedRow, editableRowModelers, false);
					}
					else {
						if (extendedColumnsData != null && extendedColumnsData.containsKey(columnKey)) {
							this.writeExtendedColumnData(tagWriter, extendedColumnsData.get(columnKey));
						} else if (columnsQueries != null && columnsQueries.containsKey(columnKey)) {
							this.writeQueryColumnData(tagWriter, columnsQueries, rowColumnsIdentifiers, columnKey, mappedRow);
						} else {
							tagWriter.appendValue(EscapeUtils.forHTML(String.valueOf(mappedRow.get(columnKey))).trim());
						}
					}
				}
				tagWriter.endTag(true);
			}
		}
		
		if (editableRowActions != null) {
			String actions = "";
			tagWriter.startTag("td");
			if (!StringUtils.isEmptyString(this.getTableBodyRowCellClass())) {
				tagWriter.writeAttribute("class", this.getTableBodyRowCellClass());
			}
			if (editableRow.isEditEnabled()) {
				actions += this.writeAction(editableRowActions.getCancelAction(), mappedRow, 
						rowColumnsIdentifiers, getRequest(), true) ;
				actions += this.writeAction(editableRowActions.getSaveAction(), mappedRow, 
						rowColumnsIdentifiers, getRequest(), true) ;
			}
			else {
				if (editableRowActions.isDeleteActionEnabled()) {
					actions += this.writeAction(editableRowActions.getDeleteAction(), mappedRow, 
							rowColumnsIdentifiers, getRequest(), false) ;
				}
				actions += this.writeAction(editableRowActions.getEditAction(), mappedRow, 
						rowColumnsIdentifiers, getRequest(), false) ;
			}
			tagWriter.appendValue(String.valueOf(actions));
			tagWriter.endTag(true);
		}
		
	}

	private void modelEditableRowControl(TagWriter tagWriter, String columnKey, Map<String, Object> mappedRow, 
			Map<String, Object> editableRowModelers, boolean enabled) throws JspException {

		if (editableRowModelers != null && editableRowModelers.containsKey(columnKey)) {
			if (editableRowModelers.get(columnKey) instanceof EditableRowCheckbox) {
				tagWriter.startTag("input");
				tagWriter.writeAttribute("type", "checkbox");
				tagWriter.writeAttribute("name", LIST_HOLDER_TABLE_PREFIX + LIST_HOLDER_PARAM_SEPARATOR + 
						LIST_HOLDER_TABLE_INPUT_PREFIX + LIST_HOLDER_PARAM_SEPARATOR + 
						columnKey + LIST_HOLDER_PARAM_SEPARATOR + this.getPagedListHolderId());
				tagWriter.writeAttribute("id", LIST_HOLDER_TABLE_PREFIX + LIST_HOLDER_PARAM_SEPARATOR + 
						LIST_HOLDER_TABLE_INPUT_PREFIX + LIST_HOLDER_PARAM_SEPARATOR + 
						columnKey + LIST_HOLDER_PARAM_SEPARATOR + this.getPagedListHolderId());
				if (CastUtils.stringToBoolean(String.valueOf(mappedRow.get(columnKey)))) {
					tagWriter.writeAttribute("checked", "checked");
				}
				if (!enabled) {
					tagWriter.writeAttribute("disabled", "disabled");
				}
				tagWriter.endTag(true);
			}
			if (editableRowModelers.get(columnKey) instanceof EditableRowSelect) {
				if (!enabled) {
					tagWriter.appendValue(String.valueOf(mappedRow.get(columnKey)));
				}
				else {
					tagWriter.startTag("select");
					tagWriter.writeAttribute("name", LIST_HOLDER_TABLE_PREFIX + LIST_HOLDER_PARAM_SEPARATOR + 
							LIST_HOLDER_TABLE_INPUT_PREFIX + LIST_HOLDER_PARAM_SEPARATOR + 
							columnKey + LIST_HOLDER_PARAM_SEPARATOR + this.getPagedListHolderId());
					tagWriter.writeAttribute("id", LIST_HOLDER_TABLE_PREFIX + LIST_HOLDER_PARAM_SEPARATOR + 
							LIST_HOLDER_TABLE_INPUT_PREFIX + LIST_HOLDER_PARAM_SEPARATOR + 
							columnKey + LIST_HOLDER_PARAM_SEPARATOR + this.getPagedListHolderId());
					EditableRowSelect editableRowSelect = (EditableRowSelect)editableRowModelers.get(columnKey);
					for(PairElement<String, String> selectData : editableRowSelect.getSelectData()) {
						tagWriter.startTag("option");
						tagWriter.writeAttribute("value", selectData.getValue());
						if (String.valueOf(mappedRow.get(columnKey)).equalsIgnoreCase(selectData.getValue())) {
							tagWriter.writeAttribute("selected", "selected");
						}
						tagWriter.appendValue(selectData.getLabel());
						tagWriter.endTag(true);
					}
					tagWriter.endTag(true);
				}
			}
		}
		else {
			tagWriter.startTag("input");
			tagWriter.writeAttribute("type", "text");
			tagWriter.writeAttribute("name", LIST_HOLDER_TABLE_PREFIX + LIST_HOLDER_PARAM_SEPARATOR + 
					LIST_HOLDER_TABLE_INPUT_PREFIX + LIST_HOLDER_PARAM_SEPARATOR + 
					columnKey + LIST_HOLDER_PARAM_SEPARATOR + this.getPagedListHolderId());
			tagWriter.writeAttribute("id", LIST_HOLDER_TABLE_PREFIX + LIST_HOLDER_PARAM_SEPARATOR + 
					LIST_HOLDER_TABLE_INPUT_PREFIX + LIST_HOLDER_PARAM_SEPARATOR + 
					columnKey + LIST_HOLDER_PARAM_SEPARATOR + this.getPagedListHolderId());
			tagWriter.writeAttribute("value", String.valueOf(mappedRow.get(columnKey)));
			if (!enabled) {
				tagWriter.writeAttribute("disabled", "disabled");
			}
			tagWriter.endTag(true);
		}
		
	}

	private void modelRowControl(TagWriter tagWriter, String columnKey, Map<String, Object> mappedRow, 
			Map<String, Object> rowModelers, boolean enabled) throws JspException {

		if (rowModelers != null && rowModelers.containsKey(columnKey)) {
			if (rowModelers.get(columnKey) instanceof RowCheckbox) {
				tagWriter.startTag("input");
				tagWriter.writeAttribute("type", "checkbox");
				tagWriter.writeAttribute("name", LIST_HOLDER_TABLE_PREFIX + LIST_HOLDER_PARAM_SEPARATOR + 
						LIST_HOLDER_TABLE_INPUT_PREFIX + LIST_HOLDER_PARAM_SEPARATOR + 
						columnKey + LIST_HOLDER_PARAM_SEPARATOR + this.getPagedListHolderId());
				tagWriter.writeAttribute("id", LIST_HOLDER_TABLE_PREFIX + LIST_HOLDER_PARAM_SEPARATOR + 
						LIST_HOLDER_TABLE_INPUT_PREFIX + LIST_HOLDER_PARAM_SEPARATOR + 
						columnKey + LIST_HOLDER_PARAM_SEPARATOR + this.getPagedListHolderId());
				if (CastUtils.stringToBoolean(String.valueOf(mappedRow.get(columnKey)))) {
					tagWriter.writeAttribute("checked", "checked");
				}
				tagWriter.writeAttribute("disabled", "disabled");
				tagWriter.endTag(true);
			}
		}
		
	}
	
	@SuppressWarnings("unchecked")
	private void writeNotEditableRow(TagWriter tagWriter, Object row, 
			List<Command> rowActions, List<String> rowColumnsIdentifiers, 
			List<String> visibleColumnsNames, 
			Map<String, Decodable> converters, 
			IRowsStatusModeler<Integer> rowsStatusModelers, 
			Map<String, Vector<String>> extendedColumnsData, 
			Map<String, String> columnsQueries, 
			Map<String, Object> rowModelers) throws JspException {

		Map<String, Object> mappedRow = (Map<String, Object>)row;
		
		for(String columnKey : mappedRow.keySet()) {
			if (columnKey.equalsIgnoreCase("rowstatus") && rowsStatusModelers != null) {
				Integer statusKey = (Integer)mappedRow.get(columnKey);
				RowStatusModeler rowStatusModeler = rowsStatusModelers.getRowStatusModeler(statusKey);
				if (rowStatusModeler != null) {
					tagWriter.startTag("td");
					tagWriter.startTag("img");
					tagWriter.writeAttribute("src",  WebUtils.getImagesBaseURL(getRequest()) + 
							rowStatusModeler.getImageFile());
					tagWriter.writeAttribute("alt",  rowStatusModeler.getAlt());
					tagWriter.writeAttribute("title",  rowStatusModeler.getTitle());
					tagWriter.endTag(true);
					tagWriter.endTag(true);
				}
			}			
			if (visibleColumnsNames.contains(columnKey)) {
				tagWriter.startTag("td");
				if (!StringUtils.isEmptyString(this.getTableBodyRowCellClass())) {
					tagWriter.writeAttribute("class", this.getTableBodyRowCellClass());
				}
				else {
					tagWriter.writeAttribute("style", getDefaultTBodyCellCssClass(columnKey));
				}
				if (converters != null && converters.get(columnKey) != null) {
					tagWriter.appendValue(converters.get(columnKey).decode(String.valueOf(mappedRow.get(columnKey))));
				}
				else {
					if (rowModelers != null && rowModelers.containsKey(columnKey)) {
						modelRowControl(tagWriter, columnKey, mappedRow, rowModelers, false);
					} else {
						if (extendedColumnsData != null && extendedColumnsData.containsKey(columnKey)) {
							this.writeExtendedColumnData(tagWriter, extendedColumnsData.get(columnKey));
						} else if (columnsQueries != null && columnsQueries.containsKey(columnKey)) {
							this.writeQueryColumnData(tagWriter, columnsQueries, rowColumnsIdentifiers, columnKey, mappedRow);
						} else {
							tagWriter.appendValue(String.valueOf(mappedRow.get(columnKey)));
						}
					}
				}
				tagWriter.endTag(true);
			}
		}
		
		if (rowActions != null) {
			String actions = "";
			tagWriter.startTag("td");
			if (!StringUtils.isEmptyString(this.getTableBodyRowCellClass())) {
				tagWriter.writeAttribute("class", this.getTableBodyRowCellClass());
			}
			for(Command command : rowActions) {
//				if (command.getCommandAction().equals(AbstractCommand.CommandActions.delete)) {
					actions += this.writeAction(command, mappedRow, 
							rowColumnsIdentifiers, getRequest(), false);
//				}
//				else {
//					actions += WebUtils.writeAction(command, getRequest());
//				}
			}
			tagWriter.appendValue(String.valueOf(actions));
			tagWriter.endTag(true);
		}
		
	}

	private void writeExtendedColumnData(TagWriter tagWriter, Vector<String> extendedColumnData) throws JspException {
		Iterator<String> extendedColumnDataIterator = extendedColumnData.iterator();
		while(extendedColumnDataIterator.hasNext()) {
			tagWriter.appendValue(extendedColumnDataIterator + "<br/>");
		}		
	}
	
	private void writeQueryColumnData(TagWriter tagWriter, Map<String, String> columnsQueries, List<String> 
		rowColumnsIdentifiers, String columnKey, Map<String, Object> mappedRow) throws JspException {
		String[] parameters = new String[rowColumnsIdentifiers.size()];
		Iterator<String> rowColumnsIdentifiersIterator = rowColumnsIdentifiers.iterator();
		int index = 0;
		while(rowColumnsIdentifiersIterator.hasNext()) {
			String rowColumnIdentifier = rowColumnsIdentifiersIterator.next();
			parameters[index] = String.valueOf(mappedRow.get(rowColumnIdentifier));
			index++;
		}
		QueryColumnExecutor<String> queryColumnExecutor = new QueryColumnExecutor<String>();
		Vector<String> values = queryColumnExecutor.execute(columnsQueries.get(columnKey), parameters);
		for(String value : values) {
			tagWriter.appendValue(value + "<br/>");
		}
	}
	
	private void writeNewRowAdd(TagWriter tagWriter, Map<String, ColumnMetaData> mappedRow, 
			List<String> visibleColumnsNames, List<String> editableRowColumns, 
			EditableRowActions editableRowActions, List<String> rowColumnsIdentifiers, 
			IRowsStatusModeler<Integer> rowsStatusModelers) throws JspException {

		tagWriter.startTag("tr");
		tagWriter.writeAttribute("id", "nohighlight");
		tagWriter.writeAttribute("style", "height : 9px;");
		tagWriter.startTag("td");
		tagWriter.writeAttribute("style", "border-top: 1px solid gray;");
		tagWriter.writeAttribute("colspan", String.valueOf(visibleColumnsNames.size() + ((editableRowActions != null) ? 1 : 0) + 
				((rowsStatusModelers != null) ? 1 : 0)));
		tagWriter.endTag(true);
		tagWriter.endTag(true);
		
		tagWriter.startTag("tr");
		tagWriter.writeAttribute("id", "nohighlight");		
		if (this.getPagedListHoldersCache().get(this.getPagedListHolderId()).isNewRowEditMode()) {
			for(String columnKey : mappedRow.keySet()) {
				if (visibleColumnsNames.contains(columnKey)) {
					tagWriter.startTag("td");
					if (!StringUtils.isEmptyString(this.getTableBodyRowCellClass())) {
						tagWriter.writeAttribute("class", this.getTableBodyRowCellClass());
					}
					else {
						tagWriter.writeAttribute("style", getDefaultTBodyCellCssClass(columnKey));
					}
//					if (editableRowColumns.contains(columnKey)) {
						tagWriter.startTag("input");
						tagWriter.writeAttribute("type", "text");
						tagWriter.writeAttribute("name", LIST_HOLDER_TABLE_PREFIX + LIST_HOLDER_PARAM_SEPARATOR + 
								LIST_HOLDER_TABLE_INPUT_PREFIX + LIST_HOLDER_PARAM_SEPARATOR + 
								columnKey + LIST_HOLDER_PARAM_SEPARATOR + this.getPagedListHolderId());
						tagWriter.writeAttribute("id", LIST_HOLDER_TABLE_PREFIX + LIST_HOLDER_PARAM_SEPARATOR + 
								LIST_HOLDER_TABLE_INPUT_PREFIX + LIST_HOLDER_PARAM_SEPARATOR + 
								columnKey + LIST_HOLDER_PARAM_SEPARATOR + this.getPagedListHolderId());
						tagWriter.writeAttribute("value", "");
						tagWriter.endTag(true);
//					}
//					else {
//						tagWriter.appendValue("&nbsp;");
//					}
					tagWriter.endTag(true);
				}
			}
			
			if (editableRowActions != null) {
				String actions = "";
				tagWriter.startTag("td");
				if (!StringUtils.isEmptyString(this.getTableBodyRowCellClass())) {
					tagWriter.writeAttribute("class", this.getTableBodyRowCellClass());
				}
				actions += this.writeNewRowAction(editableRowActions.getCancelNewAction(), mappedRow, 
						rowColumnsIdentifiers, getRequest()) ;
				actions += this.writeNewRowAction(editableRowActions.getSaveNewAction(), mappedRow, 
						rowColumnsIdentifiers, getRequest()) ;
				tagWriter.appendValue(String.valueOf(actions));
				tagWriter.endTag(true);
			}
		}
		else {
			tagWriter.startTag("td");
			tagWriter.writeAttribute("colspan", String.valueOf(getColspan(visibleColumnsNames)));
			
			String encodedCommandAction = LIST_HOLDER_TABLE_PREFIX + LIST_HOLDER_PARAM_SEPARATOR 
				+ LIST_HOLDER_TABLE_NEW_ROW_PREFIX + LIST_HOLDER_PARAM_SEPARATOR + LIST_HOLDER_TABLE_COMMAND_ACTION_DUMMY_PARAM 
				+ LIST_HOLDER_PARAM_SEPARATOR + this.getPagedListHolderId();
			String actionImageSrc = LIST_HOLDER_TABLE_DEFAULT_ADD_ROW_IMAGE;

			String addNewRowCommand = "Nuovo valore <input type=\"image\" " + TagUtils.writeHtmlAttribute("name", encodedCommandAction) 
			+ " " + TagUtils.writeHtmlAttribute("id", encodedCommandAction)
			+ " " + TagUtils.writeHtmlSrcAttribute("src", actionImageSrc, getRequest())
			+ " />";
			
			tagWriter.appendValue(addNewRowCommand);

			tagWriter.endTag(true);			
		}
		
		tagWriter.endTag(true);
		
	}
	
	private int getColspan(List<String> visibleColumnsNames) {
		
		return visibleColumnsNames.size();
		
	}
	
	private String getButtonImage(PagerButtons pagerButton, HttpServletRequest request, boolean editableMode) {
		
		String result = "";
		boolean isDefaultImage = false;
		String image = ""; 
		
		switch(pagerButton.getButton()) {
			case 0:
				image = (!editableMode) ? LIST_HOLDER_TABLE_DEFAULT_FIRST_BUTTON_ENABLED_IMAGE : 
					LIST_HOLDER_TABLE_DEFAULT_FIRST_BUTTON_DISABLED_IMAGE;
				if (this.getPagerButtonsEnabledImagesBuffer().isEmpty() || 
						this.getPagerButtonsEnabledImagesBuffer().size() < 1) {
					result = WebUtils.getImagesBaseURL(request) + "/" + image;
					isDefaultImage = true;
				}
				break;
			case 1:
				image = (!editableMode) ? LIST_HOLDER_TABLE_DEFAULT_PREVIOUS_BUTTON_ENABLED_IMAGE : 
					LIST_HOLDER_TABLE_DEFAULT_PREVIOUS_BUTTON_DISABLED_IMAGE;
				if (this.getPagerButtonsEnabledImagesBuffer().isEmpty() || 
						this.getPagerButtonsEnabledImagesBuffer().size() < 2) {
					result = WebUtils.getImagesBaseURL(request) + "/" + image;
					isDefaultImage = true;
				}
				break;
			case 2:
				image = (!editableMode) ? LIST_HOLDER_TABLE_DEFAULT_NEXT_BUTTON_ENABLED_IMAGE : 
					LIST_HOLDER_TABLE_DEFAULT_NEXT_BUTTON_DISABLED_IMAGE;
				if (this.getPagerButtonsEnabledImagesBuffer().isEmpty() || 
						this.getPagerButtonsEnabledImagesBuffer().size() < 3) {
					result = WebUtils.getImagesBaseURL(request) + "/" + image;
					isDefaultImage = true;
				}
				break;
			case 3:
				image = (!editableMode) ? LIST_HOLDER_TABLE_DEFAULT_LAST_BUTTON_ENABLED_IMAGE : 
					LIST_HOLDER_TABLE_DEFAULT_LAST_BUTTON_DISABLED_IMAGE;
				if (this.getPagerButtonsEnabledImagesBuffer().isEmpty() || 
						this.getPagerButtonsEnabledImagesBuffer().size() < 4) {
					result = WebUtils.getImagesBaseURL(request) + "/" + image;
					isDefaultImage = true;
				}
				break;
			case 4:
				image = (!editableMode) ? LIST_HOLDER_TABLE_DEFAULT_REFRESH_PAGE_SIZE_BUTTON_IMAGE : 
					LIST_HOLDER_TABLE_DEFAULT_REFRESH_PAGE_SIZE_BUTTON_DISABLED_IMAGE;
				if (this.getPagerButtonsEnabledImagesBuffer().isEmpty() || 
						this.getPagerButtonsEnabledImagesBuffer().size() < 4) {
					result = WebUtils.getImagesBaseURL(request) + "/" + image;
					isDefaultImage = true;
				}
				break;
		}
		if (!isDefaultImage) {
			image = (!editableMode) ? this.getPagerButtonsEnabledImagesBuffer().get(pagerButton.getButton()) : 
				this.getPagerButtonsDisabledImagesBuffer().get(pagerButton.getButton());
			result = WebUtils.getImagesBaseURL(request) + "/" + image;
		}
		
		return result;
		
	}
	
	private HttpServletRequest getRequest() {
		return (HttpServletRequest)pageContext.getRequest();
	}
	
	private JspWriter getWriter() {
		return pageContext.getOut();
	}
	
	
	
	
	private enum PagerButtons {
		
		first(0), previous(1), next(2), last(3), refreshPageSize(4);
	
		int button;
		
		private PagerButtons(int button) {
			this.setButton(button);
		}

		/**
		 * @return the button
		 */
		public final int getButton() {
			return button;
		}

		/**
		 * @param button the button to set
		 */
		public final void setButton(int button) {
			this.button = button;
		}
		
	}

	private String writeAction(final Command command, final Object row, 
			final List<String> rowColumnsIdentifiers, final HttpServletRequest request, 
			final boolean editableRow) throws JspException {
		
		String result = "";
		
		if (command.getType() == AbstractCommand.Types.input) {
			
			boolean isDeleteCommand = command.getCommandAction().equals(AbstractCommand.CommandActions.delete);
			
			String encodedCommandAction = encodeCommandAction(command, row, rowColumnsIdentifiers);
			if (!StringUtils.isEmptyString(command.getSrc())) {
				if (this.getPagedListHoldersCache().get(this.getPagedListHolderId()).isEditMode()) {
					if (!editableRow) {
						result += "<image " + TagUtils.writeHtmlSrcAttribute("src", command.getDisabledStateSrc(), request)
						+ " />";
					}
					else {
						result += "<input type=\"image\" " + TagUtils.writeHtmlAttribute("name", encodedCommandAction) 
						+ " " + TagUtils.writeHtmlAttribute("id", encodedCommandAction)
						+ " " + TagUtils.writeHtmlSrcAttribute("src", command.getSrc(), request)
						+ " />";
					}
				}
				else if (this.getPagedListHoldersCache().get(this.getPagedListHolderId()).isNewRowEditMode()) {
					result += "<image " + TagUtils.writeHtmlSrcAttribute("src", command.getDisabledStateSrc(), request)
					+ " />";
				}
				else {
					result += "<input type=\"image\" " + TagUtils.writeHtmlAttribute("name", encodedCommandAction) 
					+ " " + TagUtils.writeHtmlAttribute("id", encodedCommandAction)
					+ " " + TagUtils.writeHtmlSrcAttribute("src", command.getSrc(), request)
					+ writeDeleteOnClickAction(isDeleteCommand, encodedCommandAction) 
					+ " />";
				}
			} else {
				result += "<input type=\"submit\" " + TagUtils.writeHtmlAttribute("name", encodedCommandAction) 
					+ " " + TagUtils.writeHtmlAttribute("id", encodedCommandAction)
					+ " " +  TagUtils.writeHtmlAttribute("value", command.getValue())
					+ writeDeleteOnClickAction(isDeleteCommand, encodedCommandAction) 
					+ " />";
			}

		}

		if (command.getType() == AbstractCommand.Types.link) {
			
			String encodedLinkAction = encodeLinkAction(command, row, rowColumnsIdentifiers);
			if (!StringUtils.isEmptyString(command.getSrc())) {
				if (this.getPagedListHoldersCache().get(this.getPagedListHolderId()).isEditMode()) {
					if (!editableRow) {
						result += "<image border=\"0\" " + TagUtils.writeHtmlSrcAttribute("src", command.getDisabledStateSrc(), request)
							+ " />";
					}
					else {
						result += "<a " + TagUtils.writeHtmlAttribute("href", encodedLinkAction) 
							+ " " + TagUtils.writeHtmlAttribute("id", encodedLinkAction)
							+ " >" 
							+ "<image border=\"0\" " + TagUtils.writeHtmlSrcAttribute("src", command.getDisabledStateSrc(), request) + " />" 
							+ "</a>";
					}
				}
				else if (this.getPagedListHoldersCache().get(this.getPagedListHolderId()).isNewRowEditMode()) {
					result += "<image border=\"0\" " + TagUtils.writeHtmlSrcAttribute("src", command.getDisabledStateSrc(), request)
						+ " />";
				}
				else {
					result += "<a " + TagUtils.writeHtmlAttribute("href", encodedLinkAction) 
						+ " " + TagUtils.writeHtmlAttribute("id", encodedLinkAction)
						+ " >" 
						+ "<image border=\"0\" " + TagUtils.writeHtmlSrcAttribute("src", command.getSrc(), request) + " />" 
						+ "</a>";
				}
			} else {
				result += "<a " + TagUtils.writeHtmlAttribute("href", encodedLinkAction) 
					+ " " + TagUtils.writeHtmlAttribute("id", encodedLinkAction)
					+ " >" 
					+ command.getHrefTitle()  
					+ "</a>";
			}

		}
		
		return result;
		
	}

	private String writeDeleteOnClickAction(boolean isDeleteCommand, String encodedCommandAction) {
		if (isDeleteCommand) {
			return " onclick=\"javascript:return people.console.plh._delete('" + encodedCommandAction + "', '" 
				+ LIST_HOLDER_PARAM_SEPARATOR + "', '" + DELETE_GUARD_SUFFIX + "', '" + DELETE_GUARD_VERIFIED + "');\" ";
		}
		return "";
	}
	
	private String writeNewRowAction(final Command command, final Object row, 
			final List<String> rowColumnsIdentifiers, final HttpServletRequest request) throws JspException {
		
		String result = "";
		
		if (command.getType() == AbstractCommand.Types.input) {
			
			String encodedCommandAction = encodeNewRowCommandAction(command, row, rowColumnsIdentifiers);
			if (!StringUtils.isEmptyString(command.getSrc())) {
						result += "<input type=\"image\" " + TagUtils.writeHtmlAttribute("name", encodedCommandAction) 
						+ " " + TagUtils.writeHtmlAttribute("id", encodedCommandAction)
						+ " " + TagUtils.writeHtmlSrcAttribute("src", command.getSrc(), request)
						+ " />";
			} else {
				result += "<input type=\"submit\" " + TagUtils.writeHtmlAttribute("name", encodedCommandAction) 
					+ " " + TagUtils.writeHtmlAttribute("id", encodedCommandAction)
					+ " " +  TagUtils.writeHtmlAttribute("value", command.getValue())
					+ " />";
			}

		}
			
		return result;
		
	}
	
	@SuppressWarnings("unchecked")
	private String encodeCommandAction(final Command command, final Object row, final List<String> rowColumnsIdentifiers) {

		if (logger.isDebugEnabled()) {
			logger.debug("Encoding command action for command = " + command.getName());
		}
		
		boolean isDeleteCommand = command.getCommandAction().equals(AbstractCommand.CommandActions.delete);
		String actionName = (isDeleteCommand) ? "delete" : command.getName();
		String result = LIST_HOLDER_TABLE_PREFIX + LIST_HOLDER_PARAM_SEPARATOR + 
			LIST_HOLDER_TABLE_ACTION_PREFIX + LIST_HOLDER_PARAM_SEPARATOR + "?" + 
			LIST_HOLDER_TABLE_COMMAND_ACTION_QUERY_PARAM + "=" + actionName + "&";
		if (logger.isDebugEnabled()) {
			logger.debug("Initial encoded actions = '" + result + "'.");
		}
		Map<String, Object> mappedRow = (Map<String, Object>)row;
		
		if (rowColumnsIdentifiers != null && !rowColumnsIdentifiers.isEmpty()) {
			if (logger.isDebugEnabled()) {
				logger.debug("Row columns identifiers is not empty");
			}
			int index = 1;
			for(String rowColumnsIdentifier : rowColumnsIdentifiers) {
				if (mappedRow.containsKey(rowColumnsIdentifier)) {
					String value = String.valueOf(mappedRow.get(rowColumnsIdentifier));
					if (logger.isDebugEnabled()) {
						logger.debug("Value for row columns identifier '" + rowColumnsIdentifier + "' = '" + value + "'.");
					}
					String actionQuerySeparator = "";
					if (index < rowColumnsIdentifiers.size()) {
						actionQuerySeparator = "&";
					}
					if (value != null) {
						result += rowColumnsIdentifier + "=" + value + actionQuerySeparator;
					}
				}
				else {
					if (logger.isDebugEnabled()) {
						logger.debug("Columns identifier not present in mapped row.");
					}
				}
				index++;
			}
			if (result.endsWith("&")) {
				result = result.substring(0, result.length() - 1);
			}
			result += LIST_HOLDER_PARAM_SEPARATOR + this.getPagedListHolderId();
		}
		else {
			if (logger.isDebugEnabled()) {
				logger.debug("Row columns identifiers is empty");
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Final encoded actions = '" + result + "'.");
		}
		
		return result;
		
	}

	private String encodeNewRowCommandAction(final Command command, final Object row, final List<String> rowColumnsIdentifiers) {

		if (logger.isDebugEnabled()) {
			logger.debug("Encoding new row command action for command = " + command.getName());
		}
		
		boolean isDeleteCommand = command.getCommandAction().equals(AbstractCommand.CommandActions.delete);
		String actionName = (isDeleteCommand) ? "delete" : command.getName();
		String result = LIST_HOLDER_TABLE_PREFIX + LIST_HOLDER_PARAM_SEPARATOR + 
			LIST_HOLDER_TABLE_ACTION_PREFIX + LIST_HOLDER_PARAM_SEPARATOR + "?" + 
			LIST_HOLDER_TABLE_COMMAND_ACTION_QUERY_PARAM + "=" + actionName;
		if (logger.isDebugEnabled()) {
			logger.debug("Initial new row encoded actions = '" + result + "'.");
		}
		result += LIST_HOLDER_PARAM_SEPARATOR + this.getPagedListHolderId();
		if (logger.isDebugEnabled()) {
			logger.debug("Final new row encoded actions = '" + result + "'.");
		}
		
		return result;
		
	}
	

	@SuppressWarnings("unchecked")
	private String encodeLinkAction(final Command command, final Object row, final List<String> rowColumnsIdentifiers) {

		if (logger.isDebugEnabled()) {
			logger.debug("Encoding link action for command = " + command.getName());
		}
		
		String actionName = command.getCommandAction().getAction();
		String result = command.getHref() + "?" + 
			LIST_HOLDER_TABLE_COMMAND_ACTION_QUERY_PARAM + "=" + actionName + "&";
		if (logger.isDebugEnabled()) {
			logger.debug("Initial encoded actions = '" + result + "'.");
		}
		Map<String, Object> mappedRow = (Map<String, Object>)row;
		
		if (rowColumnsIdentifiers != null && !rowColumnsIdentifiers.isEmpty()) {
			if (logger.isDebugEnabled()) {
				logger.debug("Row columns identifiers is not empty");
			}
			int index = 1;
			for(String rowColumnsIdentifier : rowColumnsIdentifiers) {
				if (mappedRow.containsKey(rowColumnsIdentifier)) {
					String value = String.valueOf(mappedRow.get(rowColumnsIdentifier));
					if (logger.isDebugEnabled()) {
						logger.debug("Value for row columns identifier '" + rowColumnsIdentifier + "' = '" + value + "'.");
					}
					String actionQuerySeparator = "";
					if (index < rowColumnsIdentifiers.size()) {
						actionQuerySeparator = "&";
					}
					if (value != null) {
						result += rowColumnsIdentifier + "=" + value + actionQuerySeparator;
					}
				}
				else {
					if (logger.isDebugEnabled()) {
						logger.debug("Columns identifier not present in mapped row.");
					}
				}
				index++;
			}
			if (result.endsWith("&")) {
				result = result.substring(0, result.length() - 1);
			}
			result += "&plhId=" + this.getPagedListHolderId() + "&nocache";
		}
		else {
			if (logger.isDebugEnabled()) {
				logger.debug("Row columns identifiers is empty");
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Final encoded actions = '" + result + "'.");
		}
		
		return result;
		
	}
	
	private String getDefaultTBodyCellCssClass(String columnKey) {
		
		int columnType = this.getPagedListHoldersCache().get(this.getPagedListHolderId()).getColumnsMetaData().get(columnKey).getType();
		
		if (columnType == java.sql.Types.ARRAY || columnType == java.sql.Types.BLOB || columnType == java.sql.Types.CHAR || 
				columnType == java.sql.Types.CLOB || columnType == java.sql.Types.DATALINK || columnType == java.sql.Types.DISTINCT || 
				columnType == java.sql.Types.JAVA_OBJECT || columnType == java.sql.Types.LONGVARBINARY || columnType == java.sql.Types.LONGVARCHAR || 
				columnType == java.sql.Types.NULL || columnType == java.sql.Types.OTHER || columnType == java.sql.Types.REF || 
				columnType == java.sql.Types.STRUCT || columnType == java.sql.Types.VARBINARY || columnType == java.sql.Types.VARCHAR) {
			return DEFAULT_LEFT_ALIGNMENT_CSS_STYLE;
		}
		else if(columnType == java.sql.Types.BIGINT || columnType == java.sql.Types.BIT || columnType == java.sql.Types.BOOLEAN || 
				columnType == java.sql.Types.DECIMAL || columnType == java.sql.Types.DOUBLE || columnType == java.sql.Types.FLOAT || 
				columnType == java.sql.Types.INTEGER || columnType == java.sql.Types.NUMERIC || columnType == java.sql.Types.REAL || 
				columnType == java.sql.Types.SMALLINT || columnType == java.sql.Types.TINYINT) {
			return DEFAULT_CENTER_ALIGNMENT_CSS_STYLE;
		}
		else if (columnType == java.sql.Types.DATE || columnType == java.sql.Types.TIME || columnType == java.sql.Types.TIMESTAMP) {
			return DEFAULT_RIGHT_ALIGNMENT_CSS_STYLE;
		}
		else {
			return "";
		}
		
	}
	
}
