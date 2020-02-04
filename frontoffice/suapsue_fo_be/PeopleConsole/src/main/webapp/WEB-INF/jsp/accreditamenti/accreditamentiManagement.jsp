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
		<title><spring:message code="accreditamentiManagement.title" /></title>
	</head>
	<body>

	<c:choose>
		<c:when test="${empty accreditamentiManagementBeans}">
			<div class="textBolder text1">
			       	<spring:message code="accreditamenti.no.data" />
			</div>
		</c:when>
		<c:otherwise>
		<form:form modelAttribute="accreditamentiManagementFilter">
		<form:hidden path="pageSize" />
		
			<fieldset>
			<div class="panel">
				<div class="panelRow">
					<label for="selectedTipoQualifica"><spring:message code="accreditamentiManagement.tipoQualifica" /></label>
					<form:select path="tipoQualifica" id="tipoQualifica">
						<form:options items="${qualifiche}" itemLabel="label" itemValue="value" />
					</form:select>
				</div>
				<div class="panelRow">
					<label for="selectedEnte"><spring:message code="accreditamentiManagement.nomeEnte" /></label>
					<form:select path="nomeEnte" id="nomeEnte">
						<form:options items="${comuni}" itemLabel="label" itemValue="value" />
					</form:select>
				</div>
				<div class="panelRowTaxCode">
					<label>            	
		            	<spring:message code="accreditamentiManagement.taxcodeUtente" />
		            </label>
	            	<form:input path="taxcodeUtente" maxlength="16" size="20"/>
				</div>
				<div class="panelRowTaxCode">
					<label>            	
		            	<spring:message code="accreditamentiManagement.taxcodeIntermediario" />
		            </label>
	            	<form:input path="taxcodeIntermediario" maxlength="16" size="20"/>
				</div>
				<div class="panelRowTaxCode">
					<label>            	
		            	<spring:message code="accreditamentiManagement.vatnumberIntermediario" />
		            </label>
	            	<form:input path="vatnumberIntermediario" maxlength="11" size="15"/>
				</div>
				<div class="panelRow">
					<label>            	
		            	<spring:message code="accreditamentiManagement.e_addressIntermediario" />
		            </label>
	            	<form:input path="e_addressIntermediario" />
				</div>
				<div class="panelRow">
					<label for="selectedTipologiaIntermediario"><spring:message code="accreditamentiManagement.tipologiaIntermediario" /></label>
					<form:select path="tipologiaIntermediario" id="tipologiaIntermediario">
						<form:options items="${tipiQualifica}" itemLabel="label" itemValue="value" />
					</form:select>
				</div>
				<div class="panelRow">
					<label>            	
		            	<spring:message code="accreditamentiManagement.addressIntermediario" />
		            </label>
	            	<form:input path="sedeLegale" />
				</div>
				<div class="panelRow">
					<label for="selectedStato"><spring:message code="accreditamentiManagement.statoAccreditamento" /></label>
					<form:select path="statoAccreditamento" id="statoAccreditamento">
						<form:options items="${statoAccreditamento}" itemLabel="label" itemValue="value" />
					</form:select>
				</div>
			</div>
			
			<div class="buttonsbar">
				<input type="submit" id="view" name="view" value="Visualizza" class="button" alt="Visualizza" title="Visualizza" />
				<%-- <input type="submit" id="cancel" name="cancel" value="< Indietro" class="button" alt="Torna alla pagina precedente" title="Torna alla pagina precedente" /> --%>
			</div>
			
			</fieldset>
			
			<c:if test="${!empty accreditamentiManagementBeans}">
			<fieldset>
			
				<div class="midbar">
					<div class="panelRowResultForPage">
						<label for="resultForPage" class="panelRowResultForPage"><spring:message code="pageNavigation.label.resultForPage" /></label>		
						<form:select items="${resultForPageList}" itemLabel="label" 
							itemValue="value" path="pageNavigationSettings.resultsForPage" cssClass="panelRowResultForPage" onchange="javascript:submit();"/>
						<noscript>	
							<input type="submit" id="resultsForPage" name="resultsForPage" value="Aggiorna" class="button" alt="Aggiorna" title="Aggiorna" />
						</noscript>						
					</div>
					
					
					<form:hidden path="res_count" />
					<input type="image" src="/PeopleConsole/images/arrow-first.gif" id="view" name="view" value="Prima pagina" alt="Prima pagina" title="Prima pagina"/>
					<input type="image" src="/PeopleConsole/images/arrow-previous.gif" id="prev" name="prev" value="Pagina precedente" alt="Pagina precedente" title="Pagina precedente" />
					<span class="textNormal" >
					<form:input path="actualPage" readonly="readonly" size="1"/>&nbsp;di&nbsp;
					<form:input path="totalPages" readonly="readonly" size="1"/>
					</span>
					<input type="image" src="/PeopleConsole/images/arrow-next.gif" id="next" name="next" value="Pagina successiva" alt="Pagina successiva" title="Pagina successiva" />
					<input type="image" src="/PeopleConsole/images/arrow-last.gif" id="last" name="last" value="Ultima pagina" alt="Ultima pagina" title="Ultima pagina" />
				</div>
				
			</fieldset>

			<div class="tablelog">
			    <table class="log" summary="Accreditamenti" title="Accreditamenti">
				    <tr class=logLabelRow>
				    	<th scope="col"></th>
		                <th scope="col" class="left"><spring:message code="accreditamentiManagement.label.qualifica" /></th>
		                <th scope="col" class="left"><spring:message code="accreditamentiManagement.label.nomeEnte" /></th>
		                <th scope="col" class="left"><spring:message code="accreditamentiManagement.label.taxcodeUtente" /></th>
		                <th scope="col" class="left"><spring:message code="accreditamentiManagement.label.taxcodeIntermediario" /></th>
		                <th scope="col" class="left"><spring:message code="accreditamentiManagement.label.vatnumberIntermediario" /></th>
		                <th scope="col" class="left"><spring:message code="accreditamentiManagement.label.e_addressIntermediario" /></th> 
		                <th scope="col" class="left"><spring:message code="accreditamentiManagement.label.tipologiaIntermediario" /></th>
 		                <th scope="col" class="left"><spring:message code="accreditamentiManagement.label.addressIntermediario" /></th>
		                <th scope="col" class="left" colspan="2">
		                							<spring:message code="accreditamentiManagement.label.stato" /></th>
		         		<%-- 
		         		<th scope="col" class="center"><spring:message code="accreditamentiManagement.label.eliminare" /></th>   
		         		--%>
		                <th scope="col" class="center"><spring:message code="accreditamentiManagement.label.file_autocertificazione" /></th>
		            </tr>

						<c:forEach var="accrBean"
							items="${accreditamentiManagementBeans}" varStatus="rowCounter">
							<c:choose>
								<c:when test="${rowCounter.count % 2 == 0}">
									<c:set var="rowStyle" scope="page" value="logOddRow" />
								</c:when>
								<c:otherwise>
									<c:set var="rowStyle" scope="page" value="logEvenRow" />
								</c:otherwise>
							</c:choose>
							<tr class="${rowStyle}">

								<td><a href="${accrBean.detailPagelink}"
									id="${accrBean.detailPagelink}"> <img
										src="/PeopleConsole/images/lente.png" border="0"
										title="Dettaglio accreditamento"
										alt="Dettaglio accreditamento"></a></td>
								<td>${accrBean.tipoQualifica}</td>
								<td>${accrBean.nomeEnte}</td>
								<td>${accrBean.taxcodeUtente}</td>
								<td>${accrBean.taxcodeIntermediario}</td>
								<td>${accrBean.vatnumberIntermediario}</td>
								<td>${accrBean.e_addressIntermediario}</td>
								<td>${accrBean.tipologiaIntermediario}</td>
								<td>${accrBean.sedeLegale}</td>

								<c:choose>
									<c:when test="${accrBean.attivo == true}">
										<td><spring:message
												code="accreditamentiManagement.label.attivo" /></td>
										<td><input type="submit" id="updateAttivo"
											name="update_0_${accrBean.id}" class="buttonStatus"
											value=<spring:message code="accreditamentiManagement.label.disattiva" />
											alt=<spring:message code="accreditamentiManagement.label.disattiva" />
											title=<spring:message code="accreditamentiManagement.label.disattiva" /> />
										</td>
									<!-- 	
									<input type="checkbox" checked id="attivo_${accrBean.id}" name="attivo_${accrBean.id}" >
									<input type="hidden" id="attivo_${accrBean.id}" name="attivo_${accrBean.id}" value="off">
							 		-->
									</c:when>
									<c:otherwise>
										<td><spring:message
												code="accreditamentiManagement.label.disattivo" /></td>
										<td><input type="submit" id="updateDisattivo"
											name="update_1_${accrBean.id}" class="buttonStatus"
											value=<spring:message code="accreditamentiManagement.label.attiva" />
											alt=<spring:message code="accreditamentiManagement.label.attiva" />
											title=<spring:message code="accreditamentiManagement.label.attiva" /> />
										</td>
										<!-- 
										<input type="checkbox" id="attivo_${accrBean.id}" name="attivo_${accrBean.id}">
										<input type="hidden" id="attivo_${accrBean.id}" name="attivo_${accrBean.id}" value="off">
										-->
									</c:otherwise>
								</c:choose>


								<!-- 	
								<td align="center">
								<c:choose>
									<c:when test="${accrBean.deleted == true}">
										<input type="checkbox" checked id="deleted_${accrBean.id}" name="deleted_${accrBean.id}">
										<input type="hidden" id="deleted_${accrBean.id}" name="deleted_${accrBean.id}"  value="off">
									</c:when>
									<c:otherwise>
										<input type="checkbox"  id="deleted_${accrBean.id}"  name="deleted_${accrBean.id}">
										<input type="hidden" id="deleted_${accrBean.id}" name="deleted_${accrBean.id}" value="off">
									</c:otherwise>
									</c:choose>
								</td> 
								-->

								<td align="center"><c:choose>
										<c:when
											test="${!empty accrBean.auto_certificazione_filename}">
											<input type="image"
												src="/PeopleConsole/images/save-icon.png" border="0"
												title="Salva autocertificazione"
												alt="Salva autocertificazione"
												id="downloadAutocertificazione_${accrBean.id}"
												name="downloadAutocertificazione_${accrBean.id}">
										</c:when>
										<c:otherwise>
											<spring:message
												code="accreditamentiManagement.label.file_autocertificazione.notavailable" />
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</c:forEach>	
				</table>
		    </div>
		    </c:if>
		    <div class="panel" >
			<div class="panelTitle">
				<spring:hasBindErrors name="accreditamentiManagementFilter">
					<c:forEach var="error" items="${errors.allErrors}">
					
							<spring:message code="${error.code}" text="${error.defaultMessage}" />
					
					</c:forEach>
				</spring:hasBindErrors>
			</div>
			</div>
			
			<!-- 
			<div class="buttonsbar">
				<input type="submit" id="update" name="update" value="Salva" class="button" alt="Salva" title="Salva" />
			</div>
 			-->
 			
		</form:form>
		</c:otherwise>
	</c:choose>
		
	</body>
</html>
