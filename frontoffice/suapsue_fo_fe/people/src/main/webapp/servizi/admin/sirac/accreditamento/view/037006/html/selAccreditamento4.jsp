<%@ page contentType="text/html" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="java.util.HashMap,java.util.Iterator,it.people.sirac.accr.beans.*,it.people.sirac.accr.*,it.people.sirac.core.*,it.people.process.*,it.people.*,it.people.util.*,it.people.sirac.services.accr.*,it.people.sirac.web.forms.*,it.people.wrappers.*,it.people.fsl.servizi.admin.sirac.accreditamento.model.ProcessData" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>
<%-- JSTL --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<jsp:useBean id="pplProcess"   scope="session" type="it.people.process.AbstractPplProcess" />

<%

  HttpServletRequestDelegateWrapperHelper rwh = new HttpServletRequestDelegateWrapperHelper(request);
  pageContext.setAttribute("siracErrorsMap", rwh.getSiracErrors());
  
  ProcessData myData = (ProcessData) pplProcess.getData();
  
  ProfiliHelper profiliHelper = myData.getSelAccrProfiliHelper();
  pageContext.setAttribute("profiliHelper", profiliHelper, PageContext.REQUEST_SCOPE);
  
  int accrSelectedIndex = myData.getSelezioneAccreditamentoIndex();
  Accreditamento accrSelected = null;
  if (accrSelectedIndex !=myData.getAnnullaSelezioneAccreditamentoIndexValue()) {
    accrSelected = myData.getElencoAccreditamenti()[accrSelectedIndex];
  } else {
  	accrSelected = (Accreditamento)session.getAttribute(SiracConstants.SIRAC_ACCR_ACCRSEL);
  }  
  pageContext.setAttribute("accrSelected", accrSelected, PageContext.REQUEST_SCOPE);
	
  ProfiloLocale pl = ((ProcessData)pplProcess.getData()).getProfiloLocale();

  pageContext.setAttribute("pl", pl);

%>

  <div class="admin_sirac_accr_titolo">
      <bean:message key="label.selAccr4.titolo" />
  </div>

<%
	if(rwh.getSiracErrors().isEmpty()) {
%>
  <div class="text_block" align="left">
    <ppl:errors />
    <br>
  </div>
  <div class="text_block" align="left">
    <br>
    <h2><bean:message key="label.selAccr4.info" /></h2>
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
  <div class="text_block" align="left">
    <br>
    <h2><bean:message key="label.selAccr4.profiloRichiedente" /></h2>
 	<% if ("OAC".equals(accrSelected.getQualifica().getIdQualifica()) || "RCT".equals(accrSelected.getQualifica().getIdQualifica())) { %>
 			<bean:message key="label.selAccr2.richiedenteNonImpostato.info" />
 	<% } else { %>
	  <% if (profiliHelper.getSceltaProfiloRichiedente() != null && 
	        ProfiliHelper.SCELTAPROFILO_PROFILOREGISTRAZIONE.equals(profiliHelper.getSceltaProfiloRichiedente())) { %>
	          <bean:message key="label.selAccr2.richiedenteDaProfiloReg.info" />
	  <% } %>
	  <%@ include file="./profilorichiedentepf.jsp" %>
	<% } %>  
	</div>
	
	<% if ("OAC".equals(accrSelected.getQualifica().getIdQualifica()) || "RCT".equals(accrSelected.getQualifica().getIdQualifica())) { %>
        <div class="text_block" align="left">
          <h2><bean:message key="label.selAccr4.soggettoAssociatoAlProfilo.info" /></h2>
    	  </div>
      	<%@ include file="./profilointermediariopg.jsp" %>
 	<% } else { %>
	  <% if ( (ProfiliHelper.TIPOQUALIFICA_INTERMEDIARIO.equals(profiliHelper.getTipoQualifica()) ||
				    ProfiliHelper.TIPOQUALIFICA_RAPPRPERSGIURIDICA.equals(profiliHelper.getTipoQualifica())) &&
				    !profiliHelper.getSceltaProfiloTitolare().equals(ProfiliHelper.SCELTAPROFILO_PROFILOACCREDITAMENTO)) { %>
	        <div class="text_block" align="left">
	          <h2><bean:message key="label.selAccr4.soggettoAssociatoAlProfilo.info" /></h2>
	    	  </div>
	      	<%@ include file="./profilointermediariopg.jsp" %>
	  <% } %>
  <% } %>

   <br>
	  <div class="text_block" align="left">
    <h2><bean:message key="label.selAccr4.profiloTitolare" /></h2>
  <% if ("OAC".equals(accrSelected.getQualifica().getIdQualifica()) || "RCT".equals(accrSelected.getQualifica().getIdQualifica())) { %>
 			<bean:message key="label.selAccr3.titolareNonImpostato.info" />
 	<% } else { %>
   <% if ( profiliHelper.getSceltaProfiloTitolare().equals(ProfiliHelper.SCELTAPROFILO_PERSONAFISICA)) { %>
	    	<%@ include file="./profilotitolarepf.jsp" %>
	  <% } else if ( profiliHelper.getSceltaProfiloTitolare().equals(ProfiliHelper.SCELTAPROFILO_PERSONAGIURIDICA)) { %>
	    	<%@ include file="./profilotitolarepg.jsp" %>
	  <% } else if ( profiliHelper.getSceltaProfiloTitolare().equals(ProfiliHelper.SCELTAPROFILO_PROFILOACCREDITAMENTO)) { %>
	      <div class="text_block" align="left">
	        <bean:message key="label.selAccr3.titolareDaProfiloAccr.info" />
	    	</div>
	    	<%@ include file="./profilotitolarepg.jsp" %>
	  <% } %>  
	  </div>
  <% } %>
  	<table width="100%">
  		<tr><td align="center"><b>
  			<%
  				String servizio = (String) request.getSession().getAttribute("servizioSel");
  				if (servizio==null || servizio.equalsIgnoreCase("") ) {
  					servizio="";
  				} else {
	  				String param="";
  					HashMap mappaParametriServizio= (HashMap)request.getSession().getAttribute("parametriServizio");
  					if (mappaParametriServizio!=null){
  						request.getSession().removeAttribute("parametriServizio");
  						Iterator it= mappaParametriServizio.keySet().iterator();
  						while (it.hasNext()){
  							String key = (String)it.next();
  							String val = "";
  							if (key!=null&& !key.equalsIgnoreCase("processName")){
  								val = (String) mappaParametriServizio.get(key);
  								if (val==null) {
  									val="";
  								};
  							}
  							param += "&"+key+"="+val;
  						}
  					}
  					String link = "/people/initProcess.do?processName="+servizio+"&accreditato=TRUE"+param;%>
					<a href='<%=link%>' >
						<h3>[VAI AL SERVIZIO]</h3>
					</a>
				<%} %>
		</b></td></tr>
	</table>
  
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