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

http://www.osor.eu/eupl

Unless required by applicable law or agreed to in
writing, software distributed under the Licence is
distributed on an "AS IS" basis,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
express or implied.
See the Licence for the specific language governing
permissions and limitations under the Licence.
--%>
<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ page import="it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.*"%>
<%@ page import="it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.*"%>
<%@ page import="it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData"%>
<%@ page import="it.people.process.AbstractPplProcess"%>
<%@ page import="it.people.util.MessageBundleHelper"%>

<%

	AbstractPplProcess pplProcessPB = (AbstractPplProcess) session.getAttribute("pplProcess");
	ProcessData dataPB = (ProcessData)pplProcessPB.getData();
 
	SportelloBean sportelloPB = (SportelloBean) request.getAttribute("sportello");

	int summaryToBeSignedCount = dataPB.getListaSportelli().size();

	String buffer = String.valueOf(sportelloPB.getIdx());
	int signedSummaryCount = -1;
	try {
		signedSummaryCount = Integer.parseInt(buffer);
	} catch (Exception e){}

	if (signedSummaryCount > 0) {
		signedSummaryCount--;
	}

	StringBuilder progressBarImageCounter = (signedSummaryCount == 0) ? new StringBuilder("0") : 
		new StringBuilder(String.valueOf(signedSummaryCount)).append("_").append(String.valueOf(summaryToBeSignedCount));
		
	StringBuilder progressBarImageSrc = new StringBuilder(request.getContextPath()) 
		.append("/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/img/firma/firmapg_")
		.append(progressBarImageCounter.toString())
		.append(".png");

	StringBuilder textCounter = new StringBuilder(String.valueOf(signedSummaryCount))
		.append(" di ").append(String.valueOf(summaryToBeSignedCount));

	boolean showProgressBar = sportelloPB != null 
		&& (sportelloPB.isOffLineSign() || sportelloPB.isOnLineSign()) 
		&& summaryToBeSignedCount > 1 && dataPB.getDatiTemporanei().getParametriPU().isAbilitaFirma();
	
%>

<% if (showProgressBar) { %>
<table id="progressBar" border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr><td>&nbsp;</td></tr>
	<tr>
		<td>
		  <table cellpadding="1" cellspacing="3" align="right">
		  	<tr>
		  		<td style="font-size: 10px;">Riepiloghi da firmare:</td>
		  		<td style="font-size: 10px;"><%=summaryToBeSignedCount %></td>
		  	</tr>
		  	<tr>
		  		<td style="font-size: 10px;">Riepiloghi firmati:</td>
		  		<td style="font-size: 10px;"><img src="<%=progressBarImageSrc.toString() %>" /></td>
		  	</tr>
		  	<tr>
		  		<td style="font-size: 10px;">&nbsp;</td>
		  		<td style="font-size: 10px;" align="center"><%=textCounter.toString() %></td>
		  	</tr>
		  </table>		
		</td>
	</tr>
</table>
<input type="hidden" id="signedDataIndex" name="signedDataIndex" value='-<%= (signedSummaryCount + 1) %>' />

<% } %>
