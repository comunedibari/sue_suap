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
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>
<%@ page import="it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData"%>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.text.*" %>
<%@ page import="java.util.logging.Formatter" %>
<jsp:useBean id="pplProcess" scope="session" type="it.people.process.AbstractPplProcess" />
<%
ProcessData data_ = (ProcessData)pplProcess.getData();
%>
<logic:equal value="true" property="data.bandiAttivi" name="pplProcess">
	<jsp:include page="webclock_var.jsp" flush="true" />
	<script type="text/javascript" src="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/view/default/html/it_time.js"> </script>
	<table width="96%">
		<tr>
			<td width="100%" align="right">
				<span style="font-size: 1pt;visibility:hidden;" id="txtOraLocale">&nbsp;</span> 
				<span style="color: #000000; font-size: 100%; font-weight: bold;" id="dataLocale">&nbsp;</span> 
				<span style="color: #000000; font-size: 100%; font-weight: bold;" id="oraLocale">&nbsp;</span>
			</td>
		</tr>
	</table>
	<% if (data_.isBandiAttivi())  {%> 
	<script type="text/javascript">
		inviaRichiesta(); 
		aggiornaOra();
	</script>
	<%} %>
	<noscript>
		<%Date d = new Date(); 
		Locale locale = Locale.ITALIAN;
		DateFormat formatter = new SimpleDateFormat("EEEE dd MMMM yyyy HH:mm:ss", locale); 
		// Formatter formatter = new Formatter("HH.mm.ss", locale);
		String s = formatter.format(d);
		%>
		<table width="96%">
			<tr>
				<td width="100%" align="right" style="color: #000000; font-size: 100%; font-weight: bold;"><b><%=s%></b></td>
			</tr>
		</table>
	</noscript>
</logic:equal>
