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

<logic:equal value="true" name="pplProcess" property="data.internalError">
	<jsp:include page="defaultError.jsp" flush="true" />
</logic:equal>

<logic:notEqual value="true" name="pplProcess" property="data.internalError">

	<logic:messagesPresent>
		<table style="border:2px dotted red; padding: 3px; width:96%;">
			<tr>
				<td><img src="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/img/iconWarning.gif" alt="attenzione" />
				<b><ppl:errors /></b>
				</td>
			</tr>
		</table>
		<br/>
	</logic:messagesPresent>
	
	<table style="border:1px solid #EAEAEA; padding: 5px; width:96%;" id="scelta">
		<tr><td colspan="2" style="font-size:130%; color:#005094;" align="center">Pagina per l'importazione dei bookmark di Ravenna</td></tr>
		<tr><td colspan="2">&nbsp;</td></tr>
		<tr><td colspan="2"><b>Modalità di importazione</b></td></tr>
		<tr><td colspan="2">&nbsp;&nbsp;&nbsp;<input type="radio" name="tipo" id="tipo" value="0" onclick="document.getElementById('tutti').style.display = 'none';document.getElementById('singoloDB').style.display = 'none';document.getElementById('singolo').style.display = '';"> Singolo bookmark</td></tr>
		<tr><td colspan="2">&nbsp;&nbsp;&nbsp;<input type="radio" name="tipo" id="tipo" value="1" onclick="document.getElementById('singolo').style.display = 'none';document.getElementById('singoloDB').style.display = 'none';document.getElementById('tutti').style.display = '';"> Tutti i Bookmark</td></tr>
		<tr><td colspan="2">&nbsp;&nbsp;&nbsp;<input type="radio" name="tipo" id="tipo" value="2" onclick="document.getElementById('singolo').style.display = 'none';document.getElementById('tutti').style.display = 'none';document.getElementById('singoloDB').style.display = '';"> Singolo Bookmark da DB</td></tr>
	</table>

	<table style="border:1px solid #EAEAEA; padding: 5px; width:96%;" id="tutti">
		<tr><td colspan="2" align="center">&nbsp;ATTENZIONE : Verranno importati tutti i bookmark presenti nella tabella SERVIZI_OLD.<br/><br/></td></tr>
		<tr>
			<td colspan="2"  align="center">
				<ppl:commandLoopback commandProperty="eseguiImport" styleClass="btn"  >
				Importa bookmark
				</ppl:commandLoopback><br/><br/>
			</td>
		</tr>
	</table>
	
	<table style="border:1px solid #EAEAEA; padding: 5px; width:96%;" id="singoloDB" >
		<tr>
			<td>
				<br/><br/>
				<b>Bookmark da importare</b>
				<br/>
				<select name="cod_bookmark" >
					<logic:iterate id="rows" name="listaBookmark" scope="request">
						<optgroup label='<bean:write name="rows" property="descrizione" />'  >
							<logic:iterate id="rowsID2" name="rows" property="rows">
								<option value='<bean:write name="rowsID2" property="codice" />'><bean:write name="rowsID2" property="descrizione" />  </option>
							</logic:iterate>
						</optgroup>
					</logic:iterate>
				</select>
			</td>
		</tr>
		<tr>
			<td >
				<br/><br/>
				<b>XML configurazione</b>
				<br/>
				<textarea cols="40" rows="15" name="configurazione" id="configurazione" style="width: 450px; padding: 5px;"></textarea>
				<br/>
			</td>
		</tr>
		<tr>
			<td align="center">
				<ppl:commandLoopback commandProperty="eseguiImport" styleClass="btn"  >
				Importa bookmark
				</ppl:commandLoopback><br/><br/>
			</td>
		</tr>		
	</table>
	
	
	
	<table style="border:1px solid #EAEAEA; padding: 5px; width:96%;" id="singolo" >
		<tr><td colspan="2">&nbsp;</td></tr>
		<tr>
			<td width="15%"><b>Titolo Servizio</b></td>
			<td><input type="text" name="titolo_servizio" id="codice_servizio"  size="80" maxlength="250" /></td>
		</tr>	
		<tr>
			<td width="15%"><b>Descrizione Servizio</b></td>
			<td><input type="text" name="desc_servizio" id="codice_servizio" size="80" maxlength="250" /></td>
		</tr>	
		<tr>
			<td width="15%"><b>Codice Comune</b></td>
			<td><input type="text" name="codice_comune" id="codice_comune"/></td>
		</tr>	
		<tr>
			<td width="15%"><b>Codice Evento Vita</b></td>
			<td><input type="text" name="codice_evento_vita" id="codice_evento_vita"/></td>
		</tr>	
		<tr>
			<td >
				<br/><br/>
				<b>XML bookmark Ravenna da importare</b>
				<br/>
				<textarea cols="20" rows="30" name="xmlServizio" id="xmlServizio" style="width: 400px; padding: 5px;"></textarea>
				<br/>
			</td>
			<td >
				<br/><br/>
				<b>XML configurazione</b>
				<br/>
				<textarea cols="40" rows="15" name="configurazione" id="configurazione" style="width: 450px; padding: 5px;"></textarea>
				<br/>
			</td>
		</tr>
			
		<tr>
			<td colspan="2"  align="center">
				<ppl:commandLoopback commandProperty="eseguiImport" styleClass="btn"  >
				Importa bookmark
				</ppl:commandLoopback><br/><br/>
			</td>
		</tr>		
	</table>
	
	<table style="border:1px solid #EAEAEA; padding: 5px; width:96%;" id="singolo" >
		<tr>
			<td colspan="2" align="center"><br/>
				<ppl:commandLoopback commandProperty="back" styleClass="btn"  >
				Torna alla pagina del servizio
				</ppl:commandLoopback><br/><br/>
			</td>
		</tr>
	</table>


<script type="text/javascript">
	document.getElementById('singolo').style.display = 'none';
	document.getElementById('tutti').style.display = 'none';
	document.getElementById('singoloDB').style.display = 'none';
</script>
</logic:notEqual>
	
