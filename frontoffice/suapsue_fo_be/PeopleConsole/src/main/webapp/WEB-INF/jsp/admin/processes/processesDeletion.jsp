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
		<title><spring:message code="admin.processes.deletion.title" /></title>
	</head>
	
	<body>
		<form:form modelAttribute="process">
			
			<div class="pagedListHolderPanel">
									
					<fieldset>
						<legend>Filtra per data</legend>
						<div class="panel">
						
							<div class="nofloat">
								<form:radiobutton path="dateFilterType" id="dateFilterType" value="1"/>
								<label class="label" for="useDateFilter" >
									<spring:message code="admin.processes.deletion.useDateFilter" />
								</label>
							</div>
							
							<div class="leftcol lmargin30">
								<label class="labelDate" for="fromInput" >
									<spring:message code="admin.processes.deletion.dateFilterFrom" />
								</label>
								
								<form:input path="from" maxlength="10" name="fromInput" id="fromInput" />
								<script language="JavaScript">
									var o_cal = 
									new tcal ({
										'controlname': 'fromInput'
									});
									
									// individual template parameters can be modified via the calendar variable
									o_cal.a_tpl.yearscroll = false;
									o_cal.a_tpl.weekstart = 1;
								</script>
							</div>	
							
							<div class="leftcol">
								<label class="labelDate" for="toInput" >
									<spring:message code="admin.processes.deletion.dateFilterTo" />
								</label>
								
								<form:input path="to" maxlength="10" name="toInput" id="toInput" />
								
								<script language="JavaScript">
									var o_cal2 = 
									new tcal ({
										'controlname': 'toInput'
									}, o_cal2);
								</script>
							</div>
							<br />
						</div>
						

						<div class="panel panelrowclearer">
							<div class="nofloat">
								<form:radiobutton path="dateFilterType" id="dateFilterType" value="2"/>
								<label class="label" for="useOlderThanDaysFilter" >
									<spring:message code="admin.processes.deletion.useOlderThanDaysFilter" />
								</label>
							</div>
	
							<div class="leftcol nofloat lmargin30">
								<label class="labelSmall" for="olderThanDays">
									<spring:message code="admin.processes.deletion.olderThanDays" />
								</label>
								
								<form:input  path="olderThanDays" maxlength="4" size="5" name="olderThanDays" id="olderThanDays" />
							</div>
							<br />
						</div>
						<form:errors path="error" cssClass="error"/>
					
					</fieldset>
					
					<div class="panelrowclearer">
						<fieldset>
							<legend>Filtra per tipologia</legend>
							
							<div class="panel panelrowclearer">
								<label class="labelSmall" for="onlyNotSubmittable" >
									<form:checkbox id="onlyNotSubmittable" path="onlyNotSubmittable"/>
									<spring:message code="admin.processes.deletion.onlyNotSubmittable" />
								</label>
							</div>
							<div class="panel panelrowclearer">
								<label class="labelSmall" for="onlyPending" >
									<form:checkbox id="onlyPending" path="onlyPending"/>
									<spring:message code="admin.processes.deletion.onlyPending" />
								</label>
							</div>
							
							<div class="panel panelrowclearer">
								<label class="labelSmall" for="onlySubmitted" >
									<form:checkbox id="onlySubmitted" path="onlySubmitted"/>
									<spring:message code="admin.processes.deletion.onlySubmitted" />
								</label>
							</div>
						</fieldset>
					</div>
					
					<fieldset>
						<div class="panel panelrowclearer">
							<div class="leftcol">
								<label class="labelSmall" for="selectedUsers" >
									<spring:message code="admin.processes.deletion.useUsersFilter" />
								</label>
								<form:select id="selectedUsers" path="selectedUsers" items="${usersList}" itemValue="value" 
									itemLabel="label" multiple="true" size="6" class="wide25em" />
							</div>
				
							<div class="leftcol">
								<label class="labelSmall" for="selectedNodes" >
									<spring:message code="admin.processes.deletion.useNodesFilter" />  
								</label>
								<form:select id="selectedNodes" path="selectedNodes" items="${nodesList}" itemValue="value" 
									itemLabel="label" multiple="true" size="6" class="wide25em" />
							</div>
						</div>
						<form:errors path="selectionError" cssClass="error"/>
					</fieldset>
						
					<div class="panelrowclearer buttonsbar">
						<input type="submit" id="getResult" name="getResult" value="Recupera pratiche" class="button" alt="Recupera pratiche" title="Recupera le pratiche" />
					</div>	
		</div>
		
		<pcform:listHolderTable pagedListHolderId="processesList" 
 			pagedListHoldersCache="${process.pagedListHolders}"
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
		
		<div class="buttonsbar">
			<br />
			<input type="submit" id="archive" name="archive" value="Archivia pratiche" class="button"  alt="Archivia pratiche" title="Archivia le pratiche in un file"/>
			<input type="submit" id="delete" name="delete" value="Elimina pratiche" class="button"  alt="Elimina pratiche" title="Elimina le pratiche"/>
		</div>
		<br />
		
	</body>
</html>
