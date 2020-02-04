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
		<title><spring:message code="admin.accounts.listAndAdd.title" /></title>
	</head>
	<body>
		<form:form modelAttribute="account">
		<fieldset>
			<div class="panel">
				<div class="autoWidthPanelRow">
					<pcform:label required="*" for="taxCode">
						<spring:message code="admin.accounts.listAndAdd.taxCode" />
					</pcform:label>
					<form:input id="taxCode" path="taxCode" maxlength="16" size="20"/>
					<form:errors path="taxCode" cssClass="error"/>
				</div>
				<div class="autoWidthPanelRow">
					<pcform:label required="*" for="accountRoles">
						<spring:message code="admin.accounts.listAndAdd.role" />
					</pcform:label>
					<span>&nbsp;</span>
					<form:errors path="roles" cssClass="error"/>
				</div>
				<c:forEach items="${rolesList}" var="role">
				<div class="lmargin50autoWidthPanelRow">
					<form:checkbox id="${role.value}" path="roles" value="${role.value}" label="${role.label}" />
					<c:if test="${role.key == 'NODE_ADMIN'}">
						<div class="lmargin50autoWidthPanelRow">
							<form:select class="textSmall" path="allowedNodes" items="${nodesList}" itemValue="value" itemLabel="label" multiple="true" />
						</div>
					</c:if>
					<c:if test="${role.key == 'FE_SERVICES_ADMIN'}">
						<div class="lmargin50autoWidthPanelRow">
							<form:select path="allowedFEServices" items="${feServicesList}" itemValue="value" itemLabel="label" multiple="true" />
						</div>
					</c:if>
					<c:if test="${role.key == 'BE_SERVICES_ADMIN'}">
						<div class="lmargin50autoWidthPanelRow">
							<form:select path="allowedBEServices" items="${beServicesList}" itemValue="value" itemLabel="label" multiple="true" />
						</div>
					</c:if>
					<c:if test="${role.key == 'PEOPLE_ADMIN'}">
						<div class="lmargin50autoWidthPanelRow">
							<form:select id="allowedPeopleAdministrationNodes" path="allowedPeopleAdministrationNodes" items="${nodesList}" itemValue="value" itemLabel="label" multiple="true" />
							<div class="text panelFieldset">
								<fieldset>
									<legend>Ricezione e-mail People</legend>
									<div class="panelFieldset">
										<form:checkbox id="generalErrorEMailReceiver" path="generalErrorEMailReceiver" value="${account.generalErrorEMailReceiver}" />
										<label for="generalErrorEMailReceiver"><spring:message code="admin.accounts.generalErrorEMailReceiver.label" /></label>
									</div>
									<div class="panelFieldset">
										<form:checkbox id="sendErrorEMailReceiver" path="sendErrorEMailReceiver" value="${account.sendErrorEMailReceiver}" />
										<label for="sendErrorEMailReceiver"><spring:message code="admin.accounts.sendErrorEMailReceiver.label" /></label>
									</div>
									<div class="panelFieldset">
										<form:checkbox id="userSignallingErrorsEMailReceiver" path="userSignallingErrorsEMailReceiver" value="${account.userSignallingErrorsEMailReceiver}" />
										<label for="userSignallingErrorsEMailReceiver"><spring:message code="admin.accounts.userSignallingErrorsEMailReceiver.label" /></label>
									</div>
									<div class="panelFieldset">
										<form:checkbox id="userSuggestionsEMailReceiver" path="userSuggestionsEMailReceiver" value="${account.userSuggestionsEMailReceiver}" />
										<label for="userSuggestionsEMailReceiver"><spring:message code="admin.accounts.userSuggestionsEMailReceiver.label" /></label>
									</div>
									<div class="panelFieldset">
										<form:checkbox id="newAccreditamentoEMailReceiver" path="newAccreditamentoEMailReceiver" value="${account.newAccreditamentoEMailReceiver}" />
										<label for="newAccreditamentoEMailReceiver"><spring:message code="admin.accounts.newAccreditamentoEMailReceiver.label" /></label>
									</div>
								</fieldset>
							</div>
						</div>
					</c:if>
				</div>
				</c:forEach>
			</div>
			<div class="buttonsbar">
				<input type="submit" id="saveNewAccount" name="saveNewAccount" value="Salva" class="button" />
			</div>
		</fieldset>

		<%@ include file="/WEB-INF/jsp/includes/filters.jsp"%>
		<pcform:listHolderTable pagedListHolderId="accountsList" 
			pagedListHoldersCache="${account.pagedListHolders}" 
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
