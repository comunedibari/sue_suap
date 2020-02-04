<%--
Copyright (c) 2011, Regione Emilia-Romagna, Italy
 
Licensed under the EUPL, Version 1.1 or - as soon they
will be approved by the European Commission - subsequent
versions of the EUPL (the "Licence");
You may not use this work except in compliance with the
Licence.

For convenience a plain text copy of the English version
of the Licence can be found in the file LICENCE.txt in
the top-level directory of this software distribution.

You may obtain a copy of the Licence in any of 22 European
Languages at:

http://joinup.ec.europa.eu/software/page/eupl

Unless required by applicable law or agreed to in
writing, software distributed under the Licence is
distributed on an "AS IS" basis,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
express or implied.

This product includes software developed by Yale University

See the Licence for the specific language governing
permissions and limitations under the Licence.
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="pcfn" uri="/WEB-INF/peopleconsole-fn.tld" %>
<%@ taglib prefix="pcform" uri="/WEB-INF/peopleconsole.tld" %>
<html>
	<head>
		<title><spring:message code="admin.settings.title" /></title>
	</head>
	<body>
		<form:form modelAttribute="consoleSettingsDTO">
			<c:if test="${saveMessageSuccess != null}">
				<div class="successMessage">
					${saveMessageSuccess}
				</div>
			</c:if>
			<c:if test="${saveMessageFailed != null}">
				<div class="error">
					${saveMessageFailed}
				</div>
			</c:if>
			
			<h1><spring:message code="admin.settings.mail.title" /></h1>
			<pcform:listHolderTable pagedListHolderId="mailSettings" 
				pagedListHoldersCache="${consoleSettingsDTO.pagedListHolders}" 
				componentClass="pagedListHolderTable" 
				pagerClass="pagedListHolderPager" 
				pagerPagesTitleClass="pagedListHolderPagerTitleClass" 
				useTableBodyEvenOddRows="true" 
				tableHeadRowClass="logLabelHeader" 
				tableHeadRowCellClass="logLabelHeader" 
				tableClass="log" 
				tableBodyEvenRowClass="logEvenRow" 
				tableBodyOddRowClass="logOddRow" 
				rowsForPageClass="textNormal" 
				rowsForPageList="${rowsForPageList}" 
				rowsForPageLabel="${rowsForPageLabel}" 
				rowsForPageRefreshLabel="${rowsForPageRefreshLabel}" 
				highlightTableRows="true" 
				tableRowsHighlightingColor="${tableRowsHighlightingColor}" />
			<div class="separator">&nbsp;</div>
			
			<h1><spring:message code="admin.settings.security.title" /></h1>
			<pcform:listHolderTable pagedListHolderId="securitySettings" 
				pagedListHoldersCache="${consoleSettingsDTO.pagedListHolders}" 
				componentClass="pagedListHolderTable" 
				pagerClass="pagedListHolderPager" 
				pagerPagesTitleClass="pagedListHolderPagerTitleClass" 
				useTableBodyEvenOddRows="true" 
				tableHeadRowClass="logLabelHeader" 
				tableHeadRowCellClass="logLabelHeader" 
				tableClass="log" 
				tableBodyEvenRowClass="logEvenRow" 
				tableBodyOddRowClass="logOddRow" 
				rowsForPageClass="textNormal" 
				rowsForPageList="${rowsForPageList}" 
				rowsForPageLabel="${rowsForPageLabel}" 
				rowsForPageRefreshLabel="${rowsForPageRefreshLabel}" 
				highlightTableRows="true" 
				tableRowsHighlightingColor="${tableRowsHighlightingColor}" />
			<div class="separator">&nbsp;</div>
			
			<h1><spring:message code="admin.settings.monitoring.title" /></h1>
			<pcform:listHolderTable pagedListHolderId="monitoringSettings" 
				pagedListHoldersCache="${consoleSettingsDTO.pagedListHolders}" 
				componentClass="pagedListHolderTable" 
				pagerClass="pagedListHolderPager" 
				pagerPagesTitleClass="pagedListHolderPagerTitleClass" 
				useTableBodyEvenOddRows="true" 
				tableHeadRowClass="logLabelHeader" 
				tableHeadRowCellClass="logLabelHeader" 
				tableClass="log" 
				tableBodyEvenRowClass="logEvenRow" 
				tableBodyOddRowClass="logOddRow" 
				rowsForPageClass="textNormal" 
				rowsForPageList="${rowsForPageList}" 
				rowsForPageLabel="${rowsForPageLabel}" 
				rowsForPageRefreshLabel="${rowsForPageRefreshLabel}" 
				highlightTableRows="true" 
				tableRowsHighlightingColor="${tableRowsHighlightingColor}" />
			<div class="separator">&nbsp;</div>
			
			
			<h1><spring:message code="admin.settings.scheduler.title" /></h1>
			<pcform:listHolderTable pagedListHolderId="schedulerSettings" 
				pagedListHoldersCache="${consoleSettingsDTO.pagedListHolders}" 
				componentClass="pagedListHolderTable" 
				pagerClass="pagedListHolderPager" 
				pagerPagesTitleClass="pagedListHolderPagerTitleClass" 
				useTableBodyEvenOddRows="true" 
				tableHeadRowClass="logLabelHeader" 
				tableHeadRowCellClass="logLabelHeader" 
				tableClass="log" 
				tableBodyEvenRowClass="logEvenRow" 
				tableBodyOddRowClass="logOddRow" 
				rowsForPageClass="textNormal" 
				rowsForPageList="${rowsForPageList}" 
				rowsForPageLabel="${rowsForPageLabel}" 
				rowsForPageRefreshLabel="${rowsForPageRefreshLabel}" 
				highlightTableRows="true" 
				tableRowsHighlightingColor="${tableRowsHighlightingColor}" />
			<div class="separator">&nbsp;</div>
			
			
			<div class="buttonsbar">
				<input type="submit" id="close" name="close" value="Chiudi" class="button" />
			</div>
			
			
		</form:form>
	</body>
</html>
