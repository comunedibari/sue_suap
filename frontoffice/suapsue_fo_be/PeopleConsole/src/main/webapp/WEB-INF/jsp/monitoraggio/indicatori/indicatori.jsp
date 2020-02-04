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
		<title><spring:message code="monitoraggio.indicatori.title" /></title>
	</head>
	<body>
		<form:form modelAttribute="indicator">
			<div class="pagedListHolderPanel">
				
				<fieldset>
					<legend><spring:message code="monitoraggio.indicatori.filtriFieldsetTitle" /></legend>
					
					
					<div class="panel">
						<div class="leftcol">
				            <label class="labelDate">
				            	<spring:message code="monitoraggio.indicatori.filterByDate_from" />
				            </label>
			
							<!-- calendar attaches to existing form element -->
							<form:input path="from" maxlength="10" name="fromInput" id="fromInput" />
							<form:errors path="from" cssClass="error"/>
							
							<script language="JavaScript">
								var o_cal = 
								new tcal ({
									// input name
									'controlname': 'fromInput'
								});
								
								// individual template parameters can be modified via the calendar variable
								o_cal.a_tpl.yearscroll = false;
								o_cal.a_tpl.weekstart = 1;
							</script>
						</div>
						
						<div class="leftcol">
							<label class="labelDate">            	
				            	<spring:message code="monitoraggio.indicatori.filterByDate_to" />
				            </label>
							<form:input path="to" maxlength="10" name="toInput" id="toInput"/>
							<form:errors path="to" cssClass="error"/>
			
							<script language="JavaScript">
								var o_cal2 = 
								new tcal ({
									'controlname': 'toInput'
								}, o_cal2);
							</script>
						</div>
					</div>
					
					<div class="panel panelrowclearer">
						<br />
						<div class="leftcol">
							<label class="labelDate" for="selectedEnti"><spring:message code="monitoraggio.indicatori.filterByEnti" /></label>
							<form:select id="selectedEnti" path="selectedEnti" items="${entiList}" itemValue="value" 
								itemLabel="label" multiple="true" size="8" class="wide20em" />
						</div>
						
						<div class="leftcol">
							<label class="labelDate" for="selectedAttivita"><spring:message code="monitoraggio.indicatori.filterBySoluzioni" /></label>
							<form:select path="selectedAttivita" items="${attivitaList}" itemValue="value" 
								itemLabel="label" multiple="true" size="8" class="wide20em" />	
						</div>
				    </div>
	
					<!-- Buttons to send data or go back -->
					<div class="panelrowclearer buttonsbar">
						<form:errors path="error" cssClass="error"/>
						<input type="submit" id="getResult" name="getResult" value="Calcola" class="button"  alt="Calcola gli indicatori" title="Calcola" />
					</div>
				</fieldset>
				
				<fieldset>
					<legend><spring:message code="monitoraggio.indicatori.legendaFieldsetTitle" /></legend>
					<p>Questo strumento consente di estrarre i dati e calcolare gli indicatori chiave di performance per le soluzioni dispiegate.
					 I risultati, calcolati sulla base dei valori inseriti dall'utente, sono presentati nella tabella sottostante.</p>
					
					<div class="panel">
					<p> 
						<span class="textBolder"> NTC:</span> numero di transazioni sul nuovo canale <br />
						<span class="textBolder">NTR:</span> numero di transazioni fuori orario sportello <br />
						<span class="textBolder">NAD:</span> numero di documenti scambiati
					</p>
					
<!-- 						<div class="panelRow"> -->
<!-- 			            	<label class="label">NTC: numero di transazioni sul nuovo canale</label> -->
<!-- 				       </div> -->
<!-- 			            <div class="panelRow"> -->
<!-- 			            	<label class="label">NTR: numero di transazioni fuori orario sportello</label> -->
<!-- 			            </div> -->
<!-- 			            <div class="panelRow"> -->
<!-- 			            	<label class="label">NAD: numero di documenti scambiati</label> -->
<!-- 			            </div> -->
		            </div>
				</fieldset>
				
			</div>

			<pcform:listHolderTable pagedListHolderId="monitoringIndicatorsList" 
				pagedListHoldersCache="${indicator.pagedListHolders}" 
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

				<div class="buttonsbar">
					<br />
					<c:if test="${isSend}">
						<c:choose>
							<c:when test="${!transferResult.error}">
								<c:set var="divClass" value="successMessage" />
							</c:when>
							<c:otherwise>
								<c:set var="divClass" value="error" />
							</c:otherwise>
						</c:choose>
						
						<div class="${divClass}">
							${transferResult.message} (${transferResult.errorMessage})<br/><br/>
							(Nome file: ${transferResult.transferredFileName})
						</div>
					</c:if>
					
					<input type="submit" id="send" name="send" value="Invia a osservatorio" class="button"  alt="Invia a osservatorio" title="Invia a osservatorio"/>
					<br />
				</div>
				

		</form:form>
		
	</body>
</html>
