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
		<title><spring:message code="fenodes.listAndAdd.title" /></title>
	</head>
	<body>
		<form:form modelAttribute="feNode">
		<fieldset>
			<form:errors path="error" cssClass="error"/>
			<div class="panel">
				<div class="panelRow">
		            <label class="label" for="name">
		            	<spring:message code="fenodes.listAndAdd.nodeName" />
		            </label>
					<form:input id="name" path="name" />
					<form:errors path="name" cssClass="error"/>
				</div>
				<div class="panelRow">
		            <label class="label" for="feServiceURL">
		            	<spring:message code="fenodes.listAndAdd.nodeFEWSURL" />
		            </label>
					<form:input id="feServiceURL" path="feServiceURL" />
					<form:errors path="feServiceURL" cssClass="error"/>
				</div>
				<div class="panelRow">
		            <label class="label" for="municipality">
		            	<spring:message code="fenodes.listAndAdd.communeName" />
		            </label>
					<form:input id="municipality" path="municipality" />
					<form:errors path="municipality" cssClass="error"/>
				</div>
				<div class="panelRow">
		            <label class="label" for="municipalityCode">
		            	<spring:message code="fenodes.listAndAdd.communeCode" />
		            </label>
					<form:input id="municipalityCode" path="municipalityCode" />
					<form:errors path="municipalityCode" cssClass="error"/>
				</div>
				<div class="panelRow">
		            <label class="label" for="delegationControlEnabled">
		            	<spring:message code="fenodes.listAndAdd.delegationControlEnabled" />
		            </label>
		            <form:checkbox id="delegationControlEnabled" path="delegationControlEnabled" />
					<form:errors path="delegationControlEnabled" cssClass="error"/>
				</div>
				<div class="panelRow">
		            <label class="label" for="delegationControlServiceURL">
		            	<spring:message code="fenodes.listAndAdd.delegationControlWSURL" />
		            </label>
					<form:input id="delegationControlServiceURL" path="delegationControlServiceURL" />
					<form:errors path="delegationControlServiceURL" cssClass="error"/>
				</div>
				<div class="panelRow">
		            <label class="label" for="aooPrefix">
		            	<spring:message code="fenodes.listAndAdd.aooPrefix" />
		            </label>
					<form:input id="aooPrefix" path="aooPrefix" />
					<form:errors path="aooPrefix" cssClass="error"/>
				</div>
			</div>
			<div class="buttonsbar">
				<input type="submit" id="saveNewFeNode" name="saveNewFeNode" value="Salva" class="button" />
			</div>
		</fieldset>

		<%@ include file="/WEB-INF/jsp/includes/filters.jsp"%>
		<pcform:listHolderTable pagedListHolderId="nodesList" 
			pagedListHoldersCache="${feNode.pagedListHolders}" 
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
			
		</form:form>
	</body>
</html>
