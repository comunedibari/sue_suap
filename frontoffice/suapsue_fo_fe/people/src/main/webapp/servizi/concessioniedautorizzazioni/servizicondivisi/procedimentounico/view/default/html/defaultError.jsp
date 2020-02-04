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
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>

<link rel="stylesheet" type="text/css" href="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/view/default/html/style.css" />

<html:xhtml/>
<br/><br/>
<table border="0" width="100%" cellspacing="0" summary=".">
	<tr>
		<td width="33%" align="right">
			<img src="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/img/erroreIrreversibile.jpg" alt="errore irreversibile" />
		</td>
		<td width="34%" align="center" class="titoloErrore" valign="bottom">
			&nbsp;&nbsp;GESTIONE ERRORI A&#38;C&nbsp;&nbsp;
		</td>
		<td width="33%" align="left" >
			<img src="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/img/erroreIrreversibile.jpg" alt="errore irreversibile" />
		</td>
	</tr>
	<tr>
		<td colspan="3" align="center" class="desc1Errore">
			<br/><br/><b><bean:write name="pplProcess" property="data.errore.descrizione" filter="yes" /></b><br/>
		</td>
	</tr>
</table>
<br/>

<logic:equal value="true" name="pplProcess" property="data.errore.viewStackTrace">
	<logic:iterate id="rowsId" name="pplProcess" property="data.errore.stackTrace">
		&nbsp;&nbsp;&nbsp;&nbsp;<bean:write name="rowsId"/><br/>
	</logic:iterate>
</logic:equal>
