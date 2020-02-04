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
<%@ page contentType="text/html"%>
<%@ page pageEncoding="UTF-8"%>
<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>
<jsp:useBean id="esitoPagamento" scope="request" type="it.people.util.payment.EsitoPagamento" />

<html:xhtml/>
<div class="title1">
	<h1><bean:message key ="label.esitoPagamenti.titolo" /></h1>
</div>    
<br />
<div class="text_block"> 
	<bean:message key="label.esitoPagamenti.info" /><br /><br />
	
	<bean:message key="label.esitoPagamenti.numeroOperazione" />
	<bean:write name="esitoPagamento" property="numeroOperazione" /><br />
	
	<bean:message key="label.esitoPagamenti.descrizioneSistemaPagamento" />
	<bean:write name="esitoPagamento" property="descrizioneSistemaPagamento" /><br />
	
	<bean:message key="label.esitoPagamenti.descrizioneCircuitoAutorizzativo" />
	<bean:write name="esitoPagamento" property="descrizioneCircuitoAutorizzativo" /><br />
	
	<bean:message key="label.esitoPagamenti.descrizioneCircuitoSelezionato" />
	<bean:write name="esitoPagamento" property="descrizioneCircuitoSelezionato" /><br />
	
	<%-- 
	Attenzione gli importi sono estratti dalla ProcessData dove 
	sono gi� stati convertiti da centesimi di euro ad euro.
	--%>
	<bean:message key="label.esitoPagamenti.importoPagato" />
	<bean:write name="pplProcess" property="data.importoPagato" format="#,##0.00"/><br />
	
	<bean:message key="label.esitoPagamenti.importoCommissioni" />
	<bean:write name="pplProcess" property="data.importoCommissioni" format="#,##0.00" /><br />
	
	<bean:message key="label.esitoPagamenti.dataOrdine" />
	<bean:write name="esitoPagamento" property="dataOrdine" /><br />
	
	<bean:message key="label.esitoPagamenti.descrizioneEsito" />
	<bean:write name="esitoPagamento" property="descrizioneEsito" /><br />
	
	<bean:message key="label.esitoPagamenti.numeroAutorizzazione" />
	<bean:write name="esitoPagamento" property="numeroAutorizzazione" /><br />
	
	<bean:message key="label.esitoPagamenti.datiSpecifici" />
	<bean:write name="esitoPagamento" property="datiSpecifici" /><br />
</div>
