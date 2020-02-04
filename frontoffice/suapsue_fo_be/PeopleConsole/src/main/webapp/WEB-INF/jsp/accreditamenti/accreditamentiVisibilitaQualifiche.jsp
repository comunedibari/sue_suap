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
	<title><spring:message code="accreditamentiVisibilitaQualifiche.page${page}.title" />
</title>
</head>
<body>
	<form:form modelAttribute="accrVisibilitaQualifica">
		
		<c:if test="${page == 1}">
			<div class="panel">
				
				<fieldset class="panelFieldset">
					<legend><spring:message code="accreditamentiVisibilitaQualifiche.page1.selectNodeFieldset" /></legend>
					<div class="panel">
						<div class="panelRow">
						<label for="selectedEnte"><spring:message code="accreditamentiManagement.nomeEnte" /></label>
						<form:select path="selectedNodeId" id="selectedNodeId">
							<form:options items="${feNodeList}" itemLabel="label" itemValue="value" />
						</form:select>
						</div>
					</div>
					<!-- Barra pulsanti -->
					<div class="buttonsbar">
							<input type="submit" id="cancel" name="cancel" value="Annulla" class="button" />
							<input type="submit" id="nextPage" name="nextPage" value="Avanti" class="button" />
					</div>
					
				</fieldset>
			</div>
			
		</c:if>
		
		<c:if test="${page == 2}">
			<div class="panel">
				<fieldset class="panelFieldset">
					<legend><spring:message code="accreditamentiVisibilitaQualifiche.page2.visibilityFieldset" /></legend>
			
					<div class="panel">
						<div class="panelRow">
							<c:forEach var="visibilitaQualifica" items="${accrVisibilitaQualifica.visibilitaQualifiche}">
								<label for="${visibilitaQualifica.idQualifica}">
									<form:checkbox class="lmargin20" id="${visibilitaQualifica.idQualifica}" value="${visibilitaQualifica.idQualifica}" path="selectedQualifiche" />
									${visibilitaQualifica.descrizione}
								</label>
							</c:forEach> 
						</div>
					</div>
					
					<div class="buttonsbar">
						<input type="submit" id="cancel1" name="cancel1" value="Annulla" class="button" />
						<input type="submit" id="previousPage1" name="previousPage1" value="Indietro" class="button" />
						<input type="submit" id="nextPage1" name="nextPage1" value="Avanti" class="button" />
					</div>
					
				</fieldset>
			</div>
			

		</c:if>
		
		<c:if test="${page == 3}">
			
			<div class="panel">
				<fieldset class="panelFieldset">
					<legend><spring:message code="accreditamentiVisibilitaQualifiche.page3.resultsFieldset" /></legend>
					<div class="panelRow">
						<div class="centeralign">
							Modifiche effettuate correttamente.
						</div>
						
						<c:if test="${singleNode == false}">
							<label for="page3.repeatWizard">
								<form:checkbox class="lmargin20" id="page3.repeatWizard" path="ripetiWizard" />
								<spring:message code="accreditamentiVisibilitaQualifiche.page3.repeatWizard" />		
							</label>
						</c:if>
					</div>
				
					<div class="buttonsbar">
						<input type="submit" id="close" name="close" value="Chiudi" class="button" />
					</div>
				</fieldset>
			</div>
			
		</c:if>
		

	</form:form>
</body>
</html>
