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
<br />
<table border="1" width="100%" cellspacing="0" summary=".">
	<tr> 
		<td><b>&nbsp;A L T R I&nbsp;&nbsp;&nbsp;D I C H I A R A N T I</b></td>
	</tr>
	<% int i = 1; %>
	<logic:notEmpty name="pplProcess" property="data.altriRichiedenti">
		<logic:iterate id="rowsId" name="pplProcess" property="data.altriRichiedenti">
			<tr>
				<td>
					<table border="0" width="100%" cellspacing="0" summary=".">
					<tr>
						<logic:equal name="rowsId" property="fineCompilazione" value="false">
							<td width="50%" style="font-size:120%;color:red;">
								<img src="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/img/semaforo_rosso.gif" alt="La parte dell'anagrafica non è completa" />&nbsp;
								<b>&nbsp;<%=i%>.&nbsp;Altro Dichiarante &nbsp;&nbsp;</b><ppl:linkLoopback property="compilaRichiedente.jsp" propertyIndex="<%=i%>" styleClass="btn" >COMPILA</ppl:linkLoopback>
							</td>
						</logic:equal>
						<logic:equal name="rowsId" property="fineCompilazione" value="true">
							<td width="50%" style="font-size:120%;color:green;">
								<logic:notEqual name="rowsId" property="fineCompilazione" value="false">
									<img src="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/img/semaforo_verde.gif" alt="La parte dell'anagrafica non è completa" />&nbsp;
								</logic:notEqual>
								<b>&nbsp;<%=i%>.&nbsp;Altro Dichiarante &nbsp;&nbsp;</b><ppl:linkLoopback property="compilaRichiedente.jsp" propertyIndex="<%=i%>" styleClass="btn" >COMPILA</ppl:linkLoopback>
							</td>
						</logic:equal>
						<td width="50%" align="right">
							 <!-- <a href="/people/loopBack.do?propertyName=removeRichiedente.jsp&index=<%=i%>" class="button_AeC_operazioni"><b>&nbsp;[X]&nbsp;</b></a -->
							<ppl:linkLoopback property="removeRichiedente.jsp" propertyIndex="<%=i%>">
								<img src="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/img/x.png" alt="Elimina dichiarante" />
							</ppl:linkLoopback>
						</td>
					</tr>

					
					<logic:iterate id="rowsIdHistory" name="rowsId" property="htmlHistory">
						<tr>
							<td colspan="2">
								<bean:write name="rowsIdHistory" filter="yes" />
							</td>
						</tr>
					</logic:iterate>
					<logic:empty  name="rowsId" property="htmlHistory">
						<tr>
							<td colspan="2">
								<bean:write name="rowsId" property="htmlStepAttuale" filter="yes" />
							</td>
						</tr>					
					</logic:empty>
					
					</table>
				</td>
			</tr>
			<% i++; %>
		</logic:iterate>
	</logic:notEmpty>
	<tr>
		<td>
			<br />&nbsp;Per aggiungere un nuovo dichiarante clicca <a href="/people/loopBack.do?propertyName=addRichiedente.jsp" class="btn"><b style="color: red;">&nbsp;&nbsp;QUI&nbsp;&nbsp;</b></a><br />
			<br />
		</td>
	</tr>
	
<!--
	<tr>
		<logic:equal name="pplProcess" property="data.anagrafica.fineCompilazione" value="false">
		<td style="color:red;font-size:120%;">
			<img src="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/img/semaforo_rosso.gif" alt="La parte dell'anagrafica non è completa" />&nbsp;			
			<b>&nbsp;&nbsp;ALTRI RICHIEDENTI</b>&nbsp;<a href="/people/loopBack.do?propertyName=anagrafica.jsp" class="btn"><b>&nbsp;COMPILA&nbsp;&nbsp;&nbsp;</b></a>
		</td>
		</logic:equal>
		<logic:equal name="pplProcess" property="data.anagrafica.fineCompilazione" value="true">
		<td style="color:green;font-size:120%;">
			<img src="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/img/semaforo_verde.gif" alt="Anagrafica completa" />&nbsp;
			<b>&nbsp;&nbsp;ALTRI RICHIEDENTI</b>&nbsp;<a href="/people/loopBack.do?propertyName=anagrafica.jsp" class="btn"><b>&nbsp;COMPILA&nbsp;&nbsp;&nbsp;</b></a>
		</td>
		</logic:equal>
	</tr>
	<logic:iterate id="rowsIdAnagrafica" name="pplProcess" property="data.anagrafica.htmlHistory">
	<tr>
		<td>
			<bean:write name="rowsIdAnagrafica" filter="yes" />
		</td>
	</tr>
	</logic:iterate>
	<logic:empty  name="pplProcess" property="data.anagrafica.htmlHistory">
	<tr>
		<td>
			<bean:write name="htmlIniziale" filter="yes" scope="request"/>
		</td>
	</tr>
	</logic:empty>
-->
</table>
