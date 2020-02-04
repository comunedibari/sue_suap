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
                 it.people.fsl.servizi.admin.sirac.accreditamento.model.ProcessData" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>
<%-- JSTL --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<jsp:useBean id="pplProcess"   scope="session" type="it.people.process.AbstractPplProcess" />

<bean:define id="loopBackCmdLoadTitolarePF" value="javascript:executeSubmit('loopBack.do?propertyName=loadDatiTitolarePF');" />
<bean:define id="loopBackCmdClearTitolarePF" value="javascript:executeSubmit('loopBack.do?propertyName=clearDatiTitolarePF');" />
<bean:define id="loopBackCmdCopyDatiRichToTitolarePF" value="javascript:executeSubmit('loopBack.do?propertyName=copyDatiRichToTitolarePF');" />

<%

  HttpServletRequestDelegateWrapperHelper rwh = new HttpServletRequestDelegateWrapperHelper(request);
  //rwh.addSiracError("errore1", "messaggio1");
  pageContext.setAttribute("siracErrorsMap", rwh.getSiracErrors());

  ProcessData myData = (ProcessData) pplProcess.getData();
  
  //ProfiliHelper profiliHelper = (ProfiliHelper)request.getAttribute("profiliHelper");
  ProfiliHelper profiliHelper = myData.getSelAccrProfiliHelper();
  pageContext.setAttribute("profiliHelper", profiliHelper, PageContext.REQUEST_SCOPE);
  
  myData.setSelaccrTitolare(profiliHelper.getProfiloTitolare());

  Accreditamento accrSelected = myData.getElencoAccreditamenti()[myData.getSelezioneAccreditamentoIndex()];
  pageContext.setAttribute("accrSelected", accrSelected, PageContext.REQUEST_SCOPE);
 	
%>

<%
  
  ProfiloLocale pl = ((ProcessData)pplProcess.getData()).getProfiloLocale();

  pageContext.setAttribute("pl", pl);
  
  

%>

  <div class="admin_sirac_accr_titolo">
      <bean:message key="label.selAccr3.titolo" />
  </div>
  
<%
	if(rwh.getSiracErrors().isEmpty()) {
%>
  <div class="text_block" align="left">
    <ppl:errors />
    <% if (profiliHelper.getSceltaProfiloTitolare() != null &&
          ProfiliHelper.SCELTAPROFILO_PROFILOACCREDITAMENTO.equals(profiliHelper.getSceltaProfiloTitolare())) {
    %>
      	  <bean:message key="label.selAccr3.titolareDaProfiloAccr.info" />
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
  <div class="text_block" align="left">
    <br>
    <h2 style="margin-bottom:0px"><bean:message key="label.selAccr3.info" /></h2>
  <% if ( profiliHelper.getSceltaProfiloTitolare().equals(ProfiliHelper.SCELTAPROFILO_PERSONAFISICA)) 
      {
  %>
          <table border="0" cellpadding="0" cellspacing="1">
            <tr>
              <td>
                <div CLASS="admin_sirac_accr_btn" align="right" style="margin-top:0px;">
                  <A HREF="<%= loopBackCmdClearTitolarePF %>" 
                     TITLE="Cancella i dati del richiedente">
                       <bean:message key="label.selaccr3.LoopbackClearDatiTitolarePF" />
                  </A>
                </div>
              </td>
              <td>
                <div CLASS="admin_sirac_accr_btn" align="right" style="margin-top:0px;">
                  <A HREF="<%= loopBackCmdLoadTitolarePF %>" 
                     TITLE="Carica i dati dell'utente autenticato">
                       <bean:message key="label.selaccr3.LoopbackLoadDatiTitolarePF" />
                  </A>
                </div>
              </td>
<%--               <td>
                <div CLASS="admin_sirac_accr_btn" align="right" style="margin-top:0px;">
                  <A HREF="<%= loopBackCmdCopyDatiRichToTitolarePF %>" 
                     TITLE="Copia i dati del richiedente">
                       <bean:message key="label.selaccr3.LoopbackCopyDatiRichToTitolarePF" />
                  </A>
                </div>
              </td>
              --%>
            </tr>
          </table>
  <% 
      }
  %>
  </div>
  
  <% if ( profiliHelper.getSceltaProfiloTitolare().equals(ProfiliHelper.SCELTAPROFILO_PERSONAFISICA)) 
  		{
  %>
      <div class="text_block" align="left" style="margin-top:0px">
    	  <bean:message key="label.selAccr3.profilotitolare.insertPF" />
    	</div>
    	<%@ include file="./profilotitolarepf.jsp" %>
  <% } else if ( profiliHelper.getSceltaProfiloTitolare().equals(ProfiliHelper.SCELTAPROFILO_PERSONAGIURIDICA)) 
  		{ 
  %>
        <div class="text_block" align="left">
    	  <bean:message key="label.selAccr3.profilotitolare.insertPG" />
    	</div>
    	<%@ include file="./profilotitolarepg.jsp" %>
  <% } else if ( profiliHelper.getSceltaProfiloTitolare().equals(ProfiliHelper.SCELTAPROFILO_PROFILOACCREDITAMENTO)) 
  		{ 
  %>
        <div class="text_block" align="left">
    	</div>
    	<%@ include file="./profilotitolarepg.jsp" %>
  <%
     }
  %>  
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