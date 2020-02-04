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
<html>
	<head>
		<title><spring:message code="accreditamentiManagementDetails.title" /></title>
	</head>
	<body>
	
	<form:form modelAttribute="bean">
		<fieldset>
		<div class="panel">
			<div class="panelTitle">
				<spring:message code="accreditamentiManagementDetails.title" />
		  	</div>
		  	<div class="panelRow25 ">
	            <label class="label" for="nomeEnte">
	            	<spring:message code="accreditamentiManagement.label.nomeEnte" />
	            </label>
				<div id="nomeEnte" class="textBlack">${bean.nomeEnte}</div>
			</div>
			<div class="panelRow25 ">
	            <label class="label" for="taxcodeUtente">
	            	<spring:message code="accreditamentiManagement.label.taxcodeUtente" />
	            </label>
				<div id="taxcodeUtente" class="textBlack">${bean.taxcodeUtente}&nbsp;</div>
			</div>
		  	<div class="panelRow25 ">
	            <label class="label" for="tipoQualifica">
	            	<spring:message code="accreditamentiManagement.label.qualifica" />
	            </label>
				<div id="tipoQualifica" class="textBlack">${bean.tipoQualifica}</div>
			</div>
			<div class="panelRow25 ">
	            <label class="label" for="tipologiaIntermediario">
	            	<spring:message code="accreditamentiManagement.label.tipologiaIntermediario" />
	            </label>
				<div id="tipologiaIntermediario" class="textBlack">${bean.tipologiaIntermediario}&nbsp;</div>
			</div>

		  	<div class="panelRow25 ">
	            <label class="label" for="taxcodeIntermediario">
	            	<spring:message code="accreditamentiManagement.label.taxcodeIntermediario" />
	            </label>
				<div id="taxcodeIntermediario" class="textBlack">${bean.taxcodeIntermediario}&nbsp;</div>
			</div>
		  	<div class="panelRow25 ">
	            <label class="label" for="vatnumberIntermediario">
	            	<spring:message code="accreditamentiManagement.label.vatnumberIntermediario" />
	            </label>
				<div id="vatnumberIntermediario" class="textBlack">${bean.vatnumberIntermediario}&nbsp;</div>
			</div>
		  	<div class="panelRow25 ">
	            <label class="label" for="e_addressIntermediario">
	            	<spring:message code="accreditamentiManagement.label.e_addressIntermediario" />
	            </label>
				<div id="e_addressIntermediario" class="textBlack">${bean.e_addressIntermediario}&nbsp;</div>
			</div>
		  	<div class="panelRow25 ">
	            <label class="label" for="sedeLegale">
	            	<spring:message code="accreditamentiManagement.label.addressIntermediario" />
	            </label>
				<div id="sedeLegale" class="textBlack">${bean.sedeLegale}&nbsp;</div>
			</div>
			
			<c:if test="${bean.hasRappresentanteLegale == true}">
				<div></div>
				<div></div>
				
				<div class="clearer"></div>
				
				<div class="panelTitle">
					<spring:message code="auditConversationDetails.rapprLegale.title" />
				</div>
				<div class="panelRow25 ">
					<label class="label" for="rapprLegale.firstName">
			    		<spring:message code="auditConversationDetails.np.firstName" />
	            	</label>
					<div id="rapprLegale.firstName" class="textBlack">${bean.rappLegale_first_name}&nbsp;</div>
				</div>
				<div class="panelRow25 ">
					<label class="label" for="rapprLegale.lastName">
			    		<spring:message code="auditConversationDetails.np.lastName" />
	            	</label>
					<div id="rapprLegale.lastName" class="textBlack">${bean.rappLegale_last_name}&nbsp;</div>
				</div>
				<div class="panelRow25 ">
					<label class="label" for="rapprLegale.taxcode">
			    		<spring:message code="auditConversationDetails.np.taxcode" />
	            	</label>
					<div id="rapprLegale.taxcode" class="textBlack">${bean.rappLegale_tax_code}&nbsp;</div>
				</div>
				<div class="panelRow25 ">
					<label class="label" for="rapprLegale.address">
			    		<spring:message code="auditConversationDetails.np.address" />
	            	</label>
					<div id="rapprLegale.address" class="textBlack">${bean.rappLegale_address}&nbsp;</div>
				</div>
			</c:if>
			<div class="buttonsbar">
				<input type="submit" id="esci" name="cancel" value="< Indietro" class="button" alt="Torna alla pagina precedente" title="Torna alla pagina precedente" />
			</div>
		</div>	
	</fieldset>
		  	<c:if test="${!empty operatori}">
		  	<form:form modelAttribute="operatoriFilter">
		  	<form:hidden path="pageSize" />
		  	
		  	<div></div>
		  	<div></div>
		  	<div></div>
		  		
		  	<div class="clearer"></div>
		  		
		  	<div class="panel">
			<div class="panelTitle">
				<spring:message code="accreditamentiManagementDetails.operatori.title" />
			</div>
			</div>
			
			<fieldset>
				<div class="midbar">
					<div class="panelRowResultForPage">
						<label for="resultForPage" class="panelRowResultForPage"><spring:message code="pageNavigation.label.resultForPage" /></label>		
						<form:select items="${resultForPageList}" itemLabel="label" 
							itemValue="value" path="pageNavigationSettings.resultsForPage" cssClass="panelRowResultForPage"/>
							<input type="submit" id="resultsForPage" name="resultsForPage" value="Aggiorna" class="button" alt="Aggiorna" title="Aggiorna" />
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

			<div class="tablelog" >
			    <table class="log" summary="Operatori accreditati" title="Operatori accreditati">
				    <tr class=logLabelRow>
		                <th scope="col" class="left"><spring:message code="accreditamentiManagement.label.taxcodeUtente" /></th>
		                <th scope="col" class="left"><spring:message code="accreditamentiManagement.label.qualifica" /></th>
		                <th scope="col" class="left" colspan="2" >
		                							 <spring:message code="accreditamentiManagement.label.stato" /></th>
		                <%-- 
		                <th scope="col" class="center"><spring:message code="accreditamentiManagement.label.eliminare" /></th> 
		                --%>
		                <th scope="col"><spring:message code="accreditamentiManagement.label.file_autocertificazione" /></th>
		            </tr>    
		            
					<c:forEach var="accrBean" items="${operatori}" varStatus="rowCounter">
					  <c:choose>
					    <c:when test="${rowCounter.count % 2 == 0}">
					      <c:set var="rowStyle" scope="page" value="logOddRow"/>
					    </c:when>
					    <c:otherwise>
					      <c:set var="rowStyle" scope="page" value="logEvenRow"/>
					    </c:otherwise>
					  </c:choose>
					  <tr class="${rowStyle}">		

						<td>${accrBean.taxcodeUtente}</td>
						<td>${accrBean.tipoQualifica}</td>
						
						<c:choose>
							<c:when test="${accrBean.attivo == true}">
								<td >
									<spring:message code="accreditamentiManagement.label.attivo" />
								</td>
								<td >
									<input type="submit" id="updateAttivo" name="update_0_${accrBean.id}" 
										class="buttonStatus" 
										value=<spring:message code="accreditamentiManagement.label.disattiva" /> 
										alt=<spring:message code="accreditamentiManagement.label.disattiva" /> 
										title=<spring:message code="accreditamentiManagement.label.disattiva" /> 
									 />
								</td>	 
								<!-- 	
								<input type="checkbox" checked id="attivo_${accrBean.id}" name="attivo_${accrBean.id}" >
								<input type="hidden" id="attivo_${accrBean.id}" name="attivo_${accrBean.id}" value="off">
								 -->
							</c:when>
							<c:otherwise>
								<td >
									<spring:message code="accreditamentiManagement.label.disattivo" />
								</td>
								<td >
									<input type="submit" id="updateDisattivo" name="update_1_${accrBean.id}" 
										class="buttonStatus" 
										value=<spring:message code="accreditamentiManagement.label.attiva" /> 
										alt=<spring:message code="accreditamentiManagement.label.attiva" /> 
										title=<spring:message code="accreditamentiManagement.label.attiva" /> 
									 />
								 </td>
								 <!-- 
								<input type="checkbox" id="attivo_${accrBean.id}" name="attivo_${accrBean.id}">
								<input type="hidden" id="attivo_${accrBean.id}" name="attivo_${accrBean.id}" value="off">
								 -->
							</c:otherwise>
						</c:choose>
<%-- 					<td  align="center">
							<c:choose>
								<c:when test="${accrBean.deleted == true}">
									<input type="checkbox" checked id="deleted_${accrBean.id}" name="deleted_${accrBean.id}">
									<input type="hidden" id="deleted_${accrBean.id}" name="deleted_${accrBean.id}"  value="off">
								</c:when>
								<c:otherwise>
									<input type="checkbox" id="deleted_${accrBean.id}" name="deleted_${accrBean.id}">
									<input type="hidden" id="deleted_${accrBean.id}" name="deleted_${accrBean.id}" value="off">
								</c:otherwise>
							</c:choose>
						</td> --%>
						<td align="center">
						<c:choose>
							<c:when test="${!empty accrBean.auto_certificazione_filename}">
								<input type="image" src="/PeopleConsole/images/save-icon.png"
									border="0" title="Salva autocertificazione" alt="Salva autocertificazione"
									id="downloadAutocertificazione_${accrBean.id}" name="downloadAutocertificazione_${accrBean.id}">
							</c:when>
							<c:otherwise>
								<spring:message code="accreditamentiManagement.label.file_autocertificazione.notavailable" />
							</c:otherwise>
						</c:choose>
						</td> 
						
					
					  </tr>
					</c:forEach>
			    </table>
		    
		    </div>
		    <div class="panel" >
				<div class="panelTitle">
					<spring:hasBindErrors name="operatoriFilter">
						<c:forEach var="error" items="${errors.allErrors}">
						
								<spring:message code="${error.code}" text="${error.defaultMessage}" />
						
						</c:forEach>
					</spring:hasBindErrors>
				</div>
			</div>
			
			<!--
		  	<div class="buttonsbar">
		  		<c:if test="${!empty operatori}">
					<input type="submit" id="update" name="update" value="Salva" class="button" alt="Salva" title="Salva" />
				</c:if>
			</div>
			-->
			
		    </form:form>
		    </c:if>
		  	
	
	</form:form>
	</body>
</html>
