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
                 it.people.fsl.servizi.admin.sirac.accreditamento.model.ProcessData" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>
<%-- JSTL --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<jsp:useBean id="pplProcess"   scope="session" type="it.people.process.AbstractPplProcess" />
<jsp:useBean id="datiDelegaMap" scope="request" type="java.util.HashMap" />

<%

  HttpServletRequestDelegateWrapperHelper rwh = new HttpServletRequestDelegateWrapperHelper(request);
  pageContext.setAttribute("siracErrorsMap", rwh.getSiracErrors());
  //pageContext.setAttribute("accreditamenti", request.getAttribute("accreditamenti"));
	String userAgent = request.getHeader("User-Agent"); 
%>

  <div class="admin_sirac_accr_titolo">
      <bean:message key="label.creaOAC3.titolo" />
  </div>
	<br><br>
<%
	if(rwh.getSiracErrors().isEmpty()) {
%>
    <div class="text_block" align="left">
      <!-- <h2>-->
      <!--<bean:message key="label.creaOAC3.info" />-->
      <ppl:errors />
      <!-- </h2>-->
    </div>
<%--     <br>
   	<table border="0" class="maintable" cellspacing="0" width="100%">
	  <tr align="left"> 
		<td>
			<html:textarea property="data.delegaForm.certificazione" rows="25"  cols="60"/>
		</td>
	  </tr>
	 </table>-->
    
          <%-- ================================================================= --%>
      
      <script language="javascript" src="./servizi/admin/sirac/accreditamento/view/default/html/firma_accr_delega.js"></script>
      
      <%--<form id="signForm" method="post" action="signStepComplete.do">--%>
      <%--<c:set scope="page" var="autoCertData" value="<%= data.%>"--%>
        <div id="Content" style="background:white; padding: 10px 10px 10px 10px; border: 1px solid #7F9DB9; background-image: url(/people/img/SignBackground.gif)">
            <c:import url="./servizi/admin/sirac/accreditamento/view/default/html/template/delegaOAC.jsp"/>
            
            <!--<c:out value="${pplProcess.data.accrIntrmForm.autoCert}" />-->
         </div>
        <br />
        <table id="footer" border="0" cellpadding="0" cellspacing="0" width="100%">
          <tr>
            <td>
              <%-- 
              <input type="button" class="btn" name="annulla" value="     Annulla    " onClick="executeSubmit('abortSign.do?processId=161&processName=it.people.fsl.servizi.esempi.tutorial.serviziotutorial1')">
              &nbsp;&nbsp;&nbsp;&nbsp;
              <input type="button" class="btn" name="stampa" value="Versione Stampabile" onClick="executeSubmit('signPrintProcess.do')">
              &nbsp;&nbsp;&nbsp;&nbsp;onClick="SignClickDelega()
              --%>
              <input type="Button" value="<< Schermata Precedente"  class="btn"  onclick="javascript:executeSubmit('prevStepProcess.do')" /> 
              <input type="button" class="btn" name="firma" value="       Firma       " onClick="SignClickNew('signedData','nextStepProcess.do')">

            </td>
          </tr>
        </table>
        <input type="hidden" id="signedData" name="signedData" value="Test">
        <html:hidden property="data.delegaForm.certificazione"/>
        <input type="hidden" name="userID" value="1507F2037AD2440A2910C942EBFF20A5">
      <%--</form>--%>
      
      <%-- <c:import url="./template/autocert_intermediario.jsp"/>    --%>
      <%-- ================================================================= --%>
    
    
    
  </div>
  

<%
	} else {	// Gestione lista errori accodati nella request
%>
  <div class="text_block" align="left">
    Si sono verificati i seguenti errori:<br>

	  <c:forEach items='${siracErrorsMap}' var='mapItem'>
	
	        <%--(<c:out value='${mapItem.key}'/>
	
	         <c:out value='${mapItem.value}'/>)--%>
	        <c:out value='${mapItem.value}'/>
	
	</c:forEach>
  </div>
<%
	rwh.cleanSiracErrors();
	}
%>