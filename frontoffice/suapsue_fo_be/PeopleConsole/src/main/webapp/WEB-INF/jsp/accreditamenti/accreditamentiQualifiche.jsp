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
<title><spring:message code="accreditamentiQualifiche.title" />
</title>
</head>
<body>
	<form:form modelAttribute="accreditamentiQualificheFilter">
		<div>
			<fieldset>
				<form:errors path="error" cssClass="error" />
				<div class="panel">
					<div class="panelRowTaxCode">
						<pcform:label required="*" for="qualifica.id_qualifica"> <spring:message code="accreditamentiQualifiche.label.id_qualifica" /> </pcform:label>
						<form:input id="qualifica.id_qualifica" path="qualifica.id_qualifica" maxlength="3" size="3" />
						<form:errors path="qualifica.id_qualifica" cssClass="error" />
					</div>
					<div class="panelRow">
						<pcform:label required="*" for="qualifica.descrizione"> <spring:message code="accreditamentiQualifiche.label.descrizione" /> </pcform:label>
						<form:input id="qualifica.descrizione" path="qualifica.descrizione" />
						<form:errors path="qualifica.descrizione" cssClass="error" />
					</div>
					<div class="panelRow">
						<label class="label" for="qualifica.tipo_qualifica"> <spring:message code="accreditamentiQualifiche.label.tipo_qualifica" /> </label>
						<form:select path="qualifica.tipo_qualifica" id="qualifica.tipo_qualifica" onchange="javascript:submit();">
							<form:options items="${tipiQualifica}" itemLabel="label" itemValue="value" />
						</form:select>
						<noscript>
							<input type="submit" id="aggiornaQualifica" name="aggiornaQualifica" value="Aggiorna" class="button" />
						</noscript>
					</div>
					<c:if test="${titolareVisible}"> 
						<div class="panelRow">
							<label class="label" for="qualifica.titolare"> <spring:message code="accreditamentiQualifiche.label.titolare" /> </label>
							<form:select path="qualifica.titolare" id="qualifica.titolare">
								<form:options items="${tipiTitolare}" itemLabel="label" itemValue="value" />
							</form:select>
						</div>
					</c:if>
					<div class="panelRow">
						<label class="label" for="qualifica.auto_certificabile"> <spring:message code="accreditamentiQualifiche.label.auto_certificabile" /> </label>
						<form:checkbox id="qualifica.auto_certificabile" path="qualifica.auto_certificabile" />
						<form:errors path="qualifica.auto_certificabile" cssClass="error"/>
					</div>
				</div>
				

				
				<div class="buttonsbar">
					<input type="submit" id="saveNewQualifica" name="saveNewQualifica" value="Salva" class="button" />
				</div>
			</fieldset>
		</div>
		
		
		<c:if test="${!empty accreditamentiQualifiche}">
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
			    <table class="log" summary="Qualifiche" title="Qualifiche">
				    <tr class=logLabelRow>
		                <th scope="col" class="left"><spring:message code="accreditamentiQualifiche.label.id_qualifica" /></th>
		                <th scope="col" class="left"><spring:message code="accreditamentiQualifiche.label.descrizione" /></th>
		                <th scope="col" class="left"><spring:message code="accreditamentiQualifiche.label.tipo_qualifica" /></th>
		                <th scope="col" class="center"><spring:message code="accreditamentiQualifiche.label.auto_certificabile" /></th>
		                <th scope="col" class="center"><spring:message code="accreditamentiQualifiche.label.rappresentante_legale" /></th>
				    	<th scope="col"></th>

		            </tr>    
		            
					<c:forEach var="accrBean" items="${accreditamentiQualifiche}" varStatus="rowCounter">
					  <c:choose>
					    <c:when test="${rowCounter.count % 2 == 0}">
					      <c:set var="rowStyle" scope="page" value="logOddRow"/>
					    </c:when>
					    <c:otherwise>
					      <c:set var="rowStyle" scope="page" value="logEvenRow"/>
					    </c:otherwise>
					  </c:choose>
					  <tr class="${rowStyle}">		
	  
						<td>${accrBean.id_qualifica}</td>
						<td>${accrBean.descrizione}</td>
						<td>${accrBean.tipo_qualifica}</td>
						<td align="center">
							<c:choose>
								<c:when test="${accrBean.auto_certificabile}">
									<spring:message code="accreditamentiQualifiche.label.yes" />
								</c:when>
								<c:otherwise>
									<spring:message code="accreditamentiQualifiche.label.no" />
								</c:otherwise>
							</c:choose>
						</td>
						<td align="center">
							<c:choose>
								<c:when test="${accrBean.has_rappresentante_legale}">
									<spring:message code="accreditamentiQualifiche.label.yes" />
								</c:when>
								<c:otherwise>
									<spring:message code="accreditamentiQualifiche.label.no" />
								</c:otherwise>
							</c:choose>
						</td>
						<td>
							<c:choose>
								<c:when test="${accrBean.qualifica_utilizzata}">
									<img src="/PeopleConsole/images/edit-dis.png" border="0" title="Qualifica già utilizzata" alt="Qualifica già utilizzata">
									<img src="/PeopleConsole/images/delete-dis.png" border="0" title="Qualifica già utilizzata" alt="Qualifica già utilizzata">
								</c:when> 
								<c:otherwise>
									<a href="${accrBean.editLink}"  id="${accrBean.editLink}">
									<img src="/PeopleConsole/images/edit.png" border="0" title="Modifica qualifica" alt="Modifica qualifica"></a>
									<a href="${accrBean.deleteLinkJS}"  id="${accrBean.deleteLinkJS}">
									<noscript>
										<a href="${accrBean.deleteLink}"  id="${accrBean.deleteLink}">
									</noscript>
									<img src="/PeopleConsole/images/delete.png" border="0" title="Elimina qualifica" alt="Elimina qualifica"
										onclick="if (confirm('Eliminare l\'elemento selezionato?')) {return true;} else { return false; } "></a>
								</c:otherwise>
							</c:choose>
						</td>
				 
					
					  </tr>
					</c:forEach>
					
			    </table>
		    </div>
		    </c:if>
	</form:form>
</body>
</html>
