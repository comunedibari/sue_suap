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
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>  
<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>
<%@page import="java.util.*"%>
<%@page import="it.people.fsl.servizi.praticheOnLine.visura.myPage.oggetti.PraticaBean"%>
<%@page import="it.people.fsl.servizi.praticheOnLine.visura.myPage.model.ProcessData"%>
<jsp:useBean id="pplProcess" scope="session" type="it.people.process.AbstractPplProcess" />
<html:xhtml/>
<br />
<h3 align="center"><bean:message key="label.elimina.pratica.titolo" /> <bean:write name="pplProcess" property="data.praticaSelezionata.processDataID" /> ?</h3>
<table style="width:100%;" >
	<tr>
		<td width="100%" align="center">

			<a class="btn" href="/people/loopBack.do?propertyName=listaPratiche.jsp&amp;confirm=SI"  style="text-decoration: none;">&nbsp;&nbsp;<b><bean:message key="label.elimina.pratica.si" /></b>&nbsp;&nbsp;</a>
			&nbsp;&nbsp;
			<a class="btn" href="/people/loopBack.do?propertyName=listaPratiche.jsp&amp;confirm=NO"  style="text-decoration: none;">&nbsp;&nbsp;<b><bean:message key="label.elimina.pratica.no" /></b>&nbsp;&nbsp;</a>

 		</td>
 	</tr>
 </table>
