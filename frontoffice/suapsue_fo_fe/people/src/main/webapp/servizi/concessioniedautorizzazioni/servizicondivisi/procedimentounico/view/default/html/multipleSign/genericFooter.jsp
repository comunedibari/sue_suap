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

<script type="text/javascript">
function check(){
	alert("");
	var el = document.getElementById("presaVisionePratica");
	if (el!=null){
		alert("1");
		alert(el.checked);
	} else {
		alert("2");
	}

}
function annullaFirma(){
	if(confirm('Nessun invio è stato effettuato.\nLe firme effettuate fino ad ora verranno perse.\n\nContinuare?'))
		executeSubmit('loopBack.do?propertyName=abortMultipleSign');
}

function annullaFirmaSenzaConferma(){
	executeSubmit('loopBack.do?propertyName=abortMultipleSign');
}

function beforeOffLineSignUpload() {
	var el = document.getElementById("presaVisionePratica");
	if (el!=null){
		if (el.checked) {
			return true;
		} else {
			alert("Spuntare la voce di presa visione della pratica in formato PDF prima di procedere con la firma digitale della domanda");
			return false;
		}
	}
	return true;
}

function SignClickNew() {
	var el = document.getElementById("presaVisionePratica");
	if (el!=null){
		if (el.checked) {
			// var content = encodeURIComponent("<html><body>" + document.getElementById("Content").innerHTML + "</body></html>");
			var content = document.getElementById("Content").innerHTML;
			SignContentNew(content);
		} else {
			alert("Spuntare la voce di presa visione della pratica in formato PDF prima di procedere con la firma digitale della domanda");
		}
	} else {
		var content = document.getElementById("Content").innerHTML;
		SignContentNew(content);
	}
}

function SignContentNew(content) {

    var codiceFiscale = document.forms[0].codiceFiscale.value;
    // var hostname = 'people.gruppoinit.it';
    var hostname = window.location.hostname;
    var port = window.location.port;
    if (port!="") {
       port=":"+port;
    }
    var protocol = window.location.protocol;
    var url = protocol+'//'+hostname+port+'/';
    var newdiv = document.createElement('div');
    newdiv.setAttribute('id','appletSign');
    var object = '<object height = "0" width = "0" classid = "clsid:8AD9C840-044E-11D1-B3E9-00805F499D93" codebase = ".">\
        <param name = "height" value = "0" />\
        <param name = "width" value = "0" />\
        <param name = "code" value = "pdfconverterandsigner/ConverterAndSignerMain" />\
        <param name = "archive" value = "SignedpdfConverterAndSignerApplet.jar" />\
        <param name = "AppletMode" value = "normale" />\
        <param name = "downloadFilesServiceURL" value = "'+url+'firmasemplice/DownloadRequiredFiles" />\
        <param name = "TrustAllCertificate" value = "true" />\
        <param name = "TestFirma" value = "false" />\
        <param name = "InputFile" value = "'+content+'" />\
		<param name = "InputType" value = "binary" />\
		<param name = "CodFisc" value = "" />\
        <param name = "InputIDToStoreResult" value = "signedData" />\
        <param name = "SubmitActionToPerform" value = "loopBack.do?propertyName=multipleSign&onlinesign=true" />\
        <param name = "FormIDToSubmit" value = "pplProcess" />\
        <param name = "type" value = "application/x-java-applet" />\
        <param name = "scriptable" value = "false" />\
        <embed type = "application/x-java-applet"\
           height = "0" \
           width  = "0" \
           code = "pdfconverterandsigner/ConverterAndSignerMain"\
           archive = "SignedpdfConverterAndSignerApplet.jar"\
           AppletMode = "normale"\
           downloadFilesServiceURL = "'+url+'firmasemplice/DownloadRequiredFiles"\
           TrustAllCertificate = "true"\
           TestFirma = "false"\
           InputFile = "'+ content +'"\
		   InputType = "binary"\
		   CodFisc = ""\
           InputIDToStoreResult = "signedData"\
           SubmitActionToPerform = "loopBack.do?propertyName=multipleSign&onlinesign=true"\
           FormIDToSubmit = "pplProcess"\
           scriptable = "false"\
           pluginspage = "http://java.sun.com/products/plugin/index.html#download"\
        />\
      </object>';  
        newdiv.innerHTML = object;
        document.body.appendChild(newdiv);
       
    }

</script>


<% 
String basebb = request.getContextPath()+"/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/view/default/html/moduloDinamico.jsp?cod=";
// "/loopBack.do?propertyName=moduloInBianco.jsp";
%>

<!--  logic:notEqual name="dynamicDocument" value="" scope="request" -->
<table id="footer" border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr><td>&nbsp;</td></tr>
	<tr>
		<td>
		<% SportelloBean sportello = (SportelloBean) request.getAttribute("sportello");
			String idx = String.valueOf(sportello.getIdx());
			int idxNum=0;
			try {
				idxNum=Integer.parseInt(idx);
			} catch (Exception e){}
		%>
		  <table cellpadding="0" cellspacing="2" align="right">
		  	<tr>
		  		<td align="center">
		  			<fieldset style="float: right; border: none; padding-top: 18px;">
		  			<ppl:linkLoopback  property="abortMultipleSign" propertyIndex="" styleClass="pulsanteFirma" title="Cliccando su questo pulsante tutte le eventuali firme saranno annullate" style="text-decoration: none;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Indietro</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</ppl:linkLoopback>&nbsp;&nbsp;&nbsp;&nbsp;
		  			<%
		  			AbstractPplProcess pplProcess2 = (AbstractPplProcess) session.getAttribute("pplProcess");
		  			ProcessData dataForm2 = (ProcessData)pplProcess2.getData();
		  			String labelLegendFirmaOnLine = MessageBundleHelper.message("label.onLineSign.legend", null, pplProcess2.getProcessName(), 
		  					pplProcess2.getCommune().getKey(), pplProcess2.getContext().getLocale());
		  			String labelFirmaOnLine = MessageBundleHelper.message("label.onLineSign.sign", null, pplProcess2.getProcessName(),
		  					pplProcess2.getCommune().getKey(), pplProcess2.getContext().getLocale());
		  			String labelLegendPraticaPdf = MessageBundleHelper.message("label.legend.pratica.pdf", null, pplProcess2.getProcessName(), 
	        				pplProcess2.getCommune().getKey(), pplProcess2.getContext().getLocale());
		  			String labelPdfCompilato = MessageBundleHelper.message("label.pdf.compilato", null, pplProcess2.getProcessName(), 
		  					pplProcess2.getCommune().getKey(), pplProcess2.getContext().getLocale());
		  			String labelPdfInBianco = MessageBundleHelper.message("label.pdf.in.bianco", null, pplProcess2.getProcessName(),
		  					pplProcess2.getCommune().getKey(), pplProcess2.getContext().getLocale());
		  			%>
		  		</td>
		  		<td align="center">
							<fieldset style="float: right; padding: 8px;">
								<legend style="font-weight: bold;"><%=labelLegendPraticaPdf %></legend>
		  			<logic:equal value="false" property="data.bandiAttivi" name="pplProcess">
		  				<% if (sportello.isShowPDFVersion()) { %>
			  				<a class="pulsanteFirma" href="<%=basebb+sportello.getCodiceSportello()%>"  target="_blank" style="text-decoration: none;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b><%=labelPdfCompilato %></b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a> 
			  			<% } %>
		  			</logic:equal>
	  				<% if (sportello.isShowPrintBlankTemplate()) { %>
			  			<a class="pulsanteFirma" href="<%=basebb+sportello.getCodiceSportello()+"&white=TRUE"%>"  target="_blank" style="text-decoration: none;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b><%=labelPdfInBianco %></b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
		  			<% } %>
		  			</fieldset>
		  		</td>
		  			<%
					if (dataForm2.getFirmaBookmark().equalsIgnoreCase(Costant.conFirmaLabel) && !dataForm2.getTipoBookmark().equalsIgnoreCase(Costant.bookmarkTypeLivello2Label)){
						if (sportello.isOnLineSign()) {
					%>
		  		<td align="center">
							<fieldset style="float: right; padding: 5px;">
								<legend style="font-weight: bold;"><%=labelLegendFirmaOnLine %></legend>
								<input type="button" class="pulsanteFirma"  style="text-decoration: none;font-weight:bold;color:#FF7525;" id="firma2" name="firma2" value=" <%=labelFirmaOnLine %> "  onclick="SignClickNew()" />
							</fieldset>
							<input type="hidden"  name="codiceFiscale" value=""   />
		  		</td>
					<% }
						
						if (sportello.isOffLineSign()) {
							
			        		String offLineSignFileDownloadLabel = MessageBundleHelper.message("label.offLineSign.downloadDocument", null, pplProcess2.getProcessName(), 
			        				pplProcess2.getCommune().getKey(), pplProcess2.getContext().getLocale());
			        		String offLineSignFileUploadLabel = MessageBundleHelper.message("label.offLineSign.uploadSignedDocument", null, pplProcess2.getProcessName(), 
			        				pplProcess2.getCommune().getKey(), pplProcess2.getContext().getLocale());
			        		String offLineSignLegendLabel = MessageBundleHelper.message("label.offLineSign.legend", null, pplProcess2.getProcessName(), 
			        				pplProcess2.getCommune().getKey(), pplProcess2.getContext().getLocale());

					%>
		  		<td align="center">
		  			<fieldset style="float: right; padding: 5px;">
		  				<legend style="font-weight: bold;"><%=offLineSignLegendLabel %></legend>
						<ppl:offLineSignFileDownloadCommandLoopBack styleClass="pulsanteFirma" style="text-decoration: none;font-weight:bold;color:#FF7525;" 
							value="<%=offLineSignFileDownloadLabel %>" commandProperty="" commandIndex="" inputFormat="binary" />
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<ppl:offLineSignFileUploadLoopBack styleClass="pulsanteFirma" style="text-decoration: none;font-weight:bold;color:#FF7525;" 
							value="<%=offLineSignFileUploadLabel %>" jsCallback="beforeOffLineSignUpload" property="data.signedDocumentUploadFile" />
					</fieldset>
		  		</td>
					<%	}
						
					}  else {%>
		  		<td align="center">
		  			<fieldset style="float: right; border: none; padding-top: 18px;">
						<% if (dataForm2.getTipoBookmark().equalsIgnoreCase(Costant.bookmarkTypeLivello2Label) && dataForm2.getListaSportelli().size()==idxNum) { %>
						<b><ppl:commandLoopback commandProperty="terminaServizio" styleClass="pulsanteFirma" style="text-decoration: none;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Termina servizio&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</ppl:commandLoopback></b>
						<%} else { %>
						<b><ppl:commandLoopback commandProperty="avantiSenzaFirma"  styleClass="pulsanteFirma" style="text-decoration: none;font-weight:bold;color:#FF7525;"  >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Avanti&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</ppl:commandLoopback></b>
						<%} %>
					</fieldset>
		  		</td>
					<% } %>
		  		</td>
		  	</tr>
		  </table>
		</td>
	</tr>
</table>
<!--  /logic:notEqual -->
<br/>
<br/>
