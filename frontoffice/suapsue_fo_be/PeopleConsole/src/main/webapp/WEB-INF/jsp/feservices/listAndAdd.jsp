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
		<title><spring:message code="feservices.list.title" /></title>
	</head>
	<body>
		<form:form modelAttribute="feServiceRegistration">
		<fieldset>
			<form:errors path="error" cssClass="error"/>
			<div class="panel">
				<div class="panelRow narrow">
		            <label class="label" for="selectedFeNode">
		            	<spring:message code="feservices.list.feNodesList" />
		            </label>
					<form:select path="selectedFeNode" id="selectedFeNode">
						<form:options items="${feNodesList}" itemLabel="feNodeName" itemValue="feNodeId" />
					</form:select>
				</div>
				<div class="panelRow narrow">
		            <label class="label" for="feServicePackage">
		            	<spring:message code="feservices.list.package" />
		            </label>
					<form:input id="feServicePackage" path="feServicePackage" />
					<form:errors path="feServicePackage" cssClass="error"/>
				</div>
			</div>
			<div class="buttonsbar">
				<input type="submit" id="registerFeService" name="registerFeService" value="Registra" class="button" />
			</div>
		</fieldset>

		<%@ include file="/WEB-INF/jsp/includes/filters.jsp"%>
		<pcform:listHolderTable pagedListHolderId="feServicesList" 
			pagedListHoldersCache="${feServiceRegistration.pagedListHolders}" 
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
