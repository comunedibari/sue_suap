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
<%@ page contentType="text/html" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="it.people.sirac.accr.beans.*,
                 it.people.process.*,
                 it.people.*,
                 it.people.util.*,
                 it.people.sirac.services.accr.*,
                 it.people.sirac.web.forms.*,
                 it.people.sirac.core.SiracHelper,
                 it.people.wrappers.*,
                 it.people.fsl.servizi.admin.sirac.accreditamento.model.ProcessData, java.util.*" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>
<%-- JSTL --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<jsp:useBean id="pplProcess" scope="session" type="it.people.process.AbstractPplProcess" />
<%--<jsp:useBean id="accrIntrmForm"   scope="request" type="it.people.sirac.web.forms.AccrIntrmForm" />--%>
<jsp:useBean id="datiAutoCertMap" scope="request" type="java.util.HashMap" />



<%
  // codice da spostare nella service()
  HttpServletRequestDelegateWrapperHelper rwh = new HttpServletRequestDelegateWrapperHelper(request);

  //rwh.addSiracError("errore1", "messaggio1");
  pageContext.setAttribute("siracErrorsMap", rwh.getSiracErrors());
%>
<%
  ProfiloLocale pl = ((ProcessData)pplProcess.getData()).getProfiloLocale();

  pageContext.setAttribute("pl", pl);
  
  HashMap intrmMap = (HashMap)request.getAttribute("accrIntrmFormMap");
  
    String userAgent = request.getHeader("User-Agent"); 

	String labelLegendFirmaOnLine = MessageBundleHelper.message("label.onLineSign.legend", null, pplProcess.getProcessName(), 
			pplProcess.getCommune().getKey(), pplProcess.getContext().getLocale());
	String labelFirmaOnLine = MessageBundleHelper.message("label.onLineSign.sign", null, pplProcess.getProcessName(),
			pplProcess.getCommune().getKey(), pplProcess.getContext().getLocale());
%>

<script type="text/javascript">
	function beforeOffLineSignDownload() {
		var el = document.getElementById("Content").innerHTML;
		if (el!=null){
			var signingData = document.getElementById("signingData");
			signingData.setAttribute("value", el);
		} else {
			alert("Si è verificato un errore in fase di firma. Utilizzare la firma on line.");
			return false;
		}
		return true;
	}
</script>

  
  <div class="admin_sirac_accr_titolo">
      <bean:message key="label.creaAccr3.titolo" />
  </div>
  <br><br><br>
<%
  if (rwh.getSiracErrors().isEmpty()) {
%>
    <div class="text_block" align="left">
      <!--<bean:message key="label.creaAccr3.info" />-->
      <ppl:errors />
    </div>
<%--      
      <table border="0" class="maintable" cellspacing="0" width="100%">
        <tr align="left">
          <td>
            <html:textarea property="data.accrIntrmForm.autoCert" rows="25" cols="60" />
          </td>
        </tr>
      </table>
--%>
      <%-- ================================================================= --%>
    <div class="text_block" align="left">
      <script language="javascript" type="text/javascript" src="./servizi/admin/sirac/accreditamento/view/default/html/firma_accr_delega.js"></script>
      
        <div id="Content" style="background:white; padding: 10px 10px 10px 10px; border: 1px solid #7F9DB9; background-image: url(/people/img/SignBackground.gif)">
            
            <c:import url="./servizi/admin/sirac/accreditamento/view/default/html/template/autocert_selector.jsp"/>
            
            <!--<c:out value="${pplProcess.data.accrIntrmForm.autoCert}" />-->
         </div>
        <br />
        <table id="footer" border="0" cellpadding="0" cellspacing="0" width="100%">
          <tr>
              <%-- 
              <input type="button" class="btn" name="annulla" value="     Annulla    " onClick="executeSubmit('abortSign.do?processId=161&processName=it.people.fsl.servizi.esempi.tutorial.serviziotutorial1')">
              &nbsp;&nbsp;&nbsp;&nbsp;
              <input type="button" class="btn" name="stampa" value="Versione Stampabile" onClick="executeSubmit('signPrintProcess.do')">
              &nbsp;&nbsp;&nbsp;&nbsp;SignClick()
              --%>
          <td>
		  <table cellpadding="0" cellspacing="2" align="right">
		  	<tr>
            <td>
		  		<fieldset style="float: right; border: none; padding-top: 18px;">
	              <input type="Button" value="<< Schermata Precedente"  class="btn"  onclick="javascript:executeSubmit('prevStepProcess.do')" />
	            </fieldset> 
            </td>
              <% if (pplProcess.isOnLineSign()) { %>
              <td>
							<fieldset style="float: right; padding: 5px;">
								<legend style="font-weight: bold;"><%=labelLegendFirmaOnLine %></legend>
				              	<input type="button" class="btn" name="firma" value="       Firma       " onClick="SignClickNew('signedData','nextStepProcess.do')">
							</fieldset>
			</td>
              <% } %>


			  <%
				if (pplProcess.isOffLineSign()) {
							
			        		String offLineSignFileDownloadLabel = MessageBundleHelper.message("label.offLineSign.downloadDocument", null, pplProcess.getProcessName(), 
			        				pplProcess.getCommune().getKey(), pplProcess.getContext().getLocale());
			        		String offLineSignFileUploadLabel = MessageBundleHelper.message("label.offLineSign.uploadSignedDocument", null, pplProcess.getProcessName(), 
			        				pplProcess.getCommune().getKey(), pplProcess.getContext().getLocale());
			        		String offLineSignLegendLabel = MessageBundleHelper.message("label.offLineSign.legend", null, pplProcess.getProcessName(), 
			        				pplProcess.getCommune().getKey(), pplProcess.getContext().getLocale());

					%>
		  		<td align="center">
		  			<fieldset style="float: right; padding: 5px;">
		  				<legend style="font-weight: bold;"><%=offLineSignLegendLabel %></legend>
						<ppl:offLineSignFileDownloadCommandLoopBack styleClass="btn" style="text-decoration: none;font-weight:bold;color:#FF7525;" 
							value="<%=offLineSignFileDownloadLabel %>" commandProperty="" commandIndex="" onclick="javascript:beforeOffLineSignDownload();" inputFormat="string" />
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<ppl:offLineSignFileUploadLoopBack styleClass="btn" style="text-decoration: none;font-weight:bold;color:#FF7525;" 
							value="<%=offLineSignFileUploadLabel %>" jsCallback="" property="data.signedDocumentUploadFile" />
					</fieldset>
		  		</td>
					<%	} %>
		  		</tr>
				</table>
			</td>



              
              
              <input type="hidden" name="policyID" value="12">
          </tr>
        </table>
        
        <input type="hidden" id="signingData" name="signingData" value="empty">
        <input type="hidden" id="signedData" name="signedData" value="Test">
        <html:hidden property="data.accrIntrmForm.autoCert"/>
        <input type="hidden" name="userID" value="1507F2037AD2440A2910C942EBFF20A5">
            
       <%--<c:import url="./template/autocert_selector.jsp"/>--%>
      <%-- ================================================================= --%>

    </div>
           
    
<%
  } else {
    // Gestione lista errori accodati nella request
%>
    <div class="text_block" align="left">
      Si sono verificati i seguenti errori:
      <br>
      <c:forEach items='${siracErrorsMap}' var='mapItem'>
        <%--(<c:out value='${mapItem.key}'/>
	
	         <c:out value='${mapItem.value}'/>)--%>
        <c:out value='${mapItem.value}' />
      </c:forEach>
    </div>
<%
    rwh.cleanSiracErrors();
  }
%>
