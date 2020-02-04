<%@ page contentType="text/html" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="it.people.sirac.accr.beans.*,
				 				 it.people.sirac.accr.*,
                 it.people.process.*,
                 it.people.*,
                 it.people.util.*,
                 it.people.sirac.services.accr.*,
                 it.people.sirac.web.forms.*,
                 it.people.wrappers.*,
                 it.people.fsl.servizi.admin.sirac.accreditamento.model.ProcessData,
                 it.people.fsl.servizi.admin.sirac.accreditamento.steps.utility.ServiceParameterConstants,
                 it.people.sirac.deleghe.beans.CriteriRicercaDeleganti"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>
<%-- JSTL --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<jsp:useBean id="pplProcess"   scope="session" type="it.people.process.AbstractPplProcess" />

<%--
<bean:define id="loopBackCmdLoadTitolarePF" value="javascript:executeSubmit('loopBack.do?propertyName=loadDatiTitolarePF');" />
<bean:define id="loopBackCmdClearTitolarePF" value="javascript:executeSubmit('loopBack.do?propertyName=clearDatiTitolarePF');" />
<bean:define id="loopBackCmdCopyDatiRichToTitolarePF" value="javascript:executeSubmit('loopBack.do?propertyName=copyDatiRichToTitolarePF');" />
--%>
<%

  HttpServletRequestDelegateWrapperHelper rwh = new HttpServletRequestDelegateWrapperHelper(request);
  pageContext.setAttribute("siracErrorsMap", rwh.getSiracErrors());

  ProcessData myData = (ProcessData) pplProcess.getData();
  ProfiloLocale pl = myData.getProfiloLocale();
  
  ProfiliHelper profiliHelper = myData.getSelAccrProfiliHelper();
  pageContext.setAttribute("profiliHelper", profiliHelper, PageContext.REQUEST_SCOPE);
  
  myData.setSelaccrTitolare(profiliHelper.getProfiloTitolare());

  Accreditamento accrSelected = myData.getElencoAccreditamenti()[myData.getSelezioneAccreditamentoIndex()];
  pageContext.setAttribute("accrSelected", accrSelected, PageContext.REQUEST_SCOPE);
	
	CriteriRicercaDeleganti criteriRicercaDeleganti = (CriteriRicercaDeleganti)request.getAttribute("criteriRicercaDeleganti");	
	

  pageContext.setAttribute("pl", pl);
  
  ServiceParameters serviceParams = (ServiceParameters)session.getAttribute("serviceParameters");
  boolean isDelegantLookupEnabled = new Boolean((String)serviceParams.get(ServiceParameterConstants.DELEGANT_LOOKUP_ENABLED)).booleanValue();
   
%>
	
	<div class="admin_sirac_accr_titolo">
      <bean:message key="label.selAccr3.titolo" />
  </div>
  
<%
	if(rwh.getSiracErrors().isEmpty()) {
%>
  <div class="text_block" align="left">
    <ppl:errors />
	  <% if ("OAC".equals(accrSelected.getQualifica().getIdQualifica()) || "RCT".equals(accrSelected.getQualifica().getIdQualifica())) { %>
				<bean:message key="label.selAccr3.titolareNonImpostato.info" />
		<% } else {%>
	    <% if (profiliHelper.getSceltaProfiloTitolare() != null &&
	          ProfiliHelper.SCELTAPROFILO_PROFILOACCREDITAMENTO.equals(profiliHelper.getSceltaProfiloTitolare())) {
	    %>
	      	  <bean:message key="label.selAccr3.titolareDaProfiloAccr.info" />
	    <% } %>
		<% } %>  
    <br>
  </div>
  <div class="text_block" align="left">
    <table>
      <tr>
          <td class="pathBold"><bean:message key="label.creaAccr2.tipoQualifica" /></td>
          <td><b><c:out value="${accrSelected.qualifica.tipoQualifica}" /></b></td>
      </tr>
      <tr>
          <td class="pathBold"><bean:message key="label.creaAccr2.descrQualifica" /></td>
          <td><b><c:out value="${accrSelected.qualifica.descrizione}" /></b></td>
      </tr>
    </table>
  </div>
  <br>
<% if (!"OAC".equals(accrSelected.getQualifica().getIdQualifica()) && !"RCT".equals(accrSelected.getQualifica().getIdQualifica())) { %>  
  <%-- Se è abilitata la ricerca deleganti, mostra la form di ricerca --%>
  <% if (isDelegantLookupEnabled && !ProfiliHelper.TIPOQUALIFICA_RAPPRPERSGIURIDICA.equals(profiliHelper.getTipoQualifica())) { 
  		if ( profiliHelper.getSceltaProfiloTitolare().equals(ProfiliHelper.SCELTAPROFILO_PERSONAFISICA)) { %>
			  <c:set scope="request" var="profiloTitolare" value="PersonaFisica" />
			<% } else { %>
		  	<c:set scope="request" var="profiloTitolare" value="PersonaGiuridica" />
		  <% } %>
		  <c:set scope="request" var="delegatoString" value="${requestScope.delegatoString}"/>
			<c:import url="./servizi/admin/sirac/accreditamento/view/default/html/ricercaDeleganti.jsp"/>
	<% } %>
  
  <div class="text_block" align="left">
    <br>
    <h2 style="margin-bottom:0px"><bean:message key="label.selAccr3.info" /></h2>
    <% if ( profiliHelper.getSceltaProfiloTitolare().equals(ProfiliHelper.SCELTAPROFILO_PERSONAFISICA)) { %>
	    <table border="0" cellpadding="1" cellspacing="1">
	      <tr>
	        <td>
	          <ppl:linkLoopback styleClass="btn" property="clearDatiTitolarePF">
	          	<bean:message key="label.selaccr3.LoopbackClearDatiTitolarePF" />
	          </ppl:linkLoopback>
	        </td>
	        <td>
	          <ppl:linkLoopback styleClass="btn" property="loadDatiTitolarePF">
	          	<bean:message key="label.selaccr3.LoopbackLoadDatiTitolarePF" />
	          </ppl:linkLoopback>
	        </td>
	      </tr>
	    </table>
    <% } else if ( profiliHelper.getSceltaProfiloTitolare().equals(ProfiliHelper.SCELTAPROFILO_PERSONAGIURIDICA)) { %> 
     <table border="0" cellpadding="1" cellspacing="1">
	      <tr>
	        <td>
	          <ppl:linkLoopback styleClass="btn" property="clearDatiTitolarePG">
	          	<bean:message key="label.selaccr3.LoopbackClearDatiTitolarePG" />
	          </ppl:linkLoopback>
	        </td>
	      </tr>
	    </table>
    <% } %>  
  </div>
  
  <% if ( profiliHelper.getSceltaProfiloTitolare().equals(ProfiliHelper.SCELTAPROFILO_PERSONAFISICA)) { %>
      <div class="text_block" align="left" style="margin-top:0px">
    	  <bean:message key="label.selAccr3.profilotitolare.insertPF" />
    	</div>
    	<%@ include file="./profilotitolarepf.jsp" %>
  <% } else if ( profiliHelper.getSceltaProfiloTitolare().equals(ProfiliHelper.SCELTAPROFILO_PERSONAGIURIDICA)) { %>
      <div class="text_block" align="left">
    	  <bean:message key="label.selAccr3.profilotitolare.insertPG" />
    	</div>
    	<%@ include file="./profilotitolarepg.jsp" %>
  <% } else if ( profiliHelper.getSceltaProfiloTitolare().equals(ProfiliHelper.SCELTAPROFILO_PROFILOACCREDITAMENTO)) {  %>
      <div class="text_block" align="left">
    	</div>
    	<%@ include file="./profilotitolarepg.jsp" %>
  <% } %>  
  
<%-- <% } else { %>  
	<div class="text_block" align="left" style="margin-top:0px">
    	  <bean:message key="label.selAccr3.profilotitolare.insertPF" />
  </div>
<% } %>  --%>

<% } %>
  
<%
	} else {	// Gestione lista errori accodati nella request
%>
  <div class="text_block" align="left">
    Si sono verificati i seguenti errori:<br>

	  <c:forEach items='${siracErrorsMap}' var='mapItem'>
	        <c:out value='${mapItem.value}'/>
   	</c:forEach>
  </div>
<%
	rwh.cleanSiracErrors();
	}
%>