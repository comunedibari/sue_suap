<%@ page contentType="text/html" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="it.people.sirac.accr.beans.*,
                 it.people.process.*,
                 it.people.*,
                 it.people.util.*,
                 it.people.sirac.accr.*,
                 it.people.sirac.accr.beans.*,
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
<%--<jsp:useBean id="profilopf"   scope="request" type="it.people.sirac.accr.beans.ProfiloPersonaFisica" />--%>

<bean:define id="loopBackCmdLoadRichiedente" value="javascript:executeSubmit('loopBack.do?propertyName=loadDatiRich');" />
<bean:define id="loopBackCmdClearRichiedente" value="javascript:executeSubmit('loopBack.do?propertyName=clearDatiRich');" />

<%

  HttpServletRequestDelegateWrapperHelper rwh = new HttpServletRequestDelegateWrapperHelper(request);
  pageContext.setAttribute("siracErrorsMap", rwh.getSiracErrors());
  
  ProcessData myData = (ProcessData) pplProcess.getData();
  
  ProfiliHelper profiliHelper = (ProfiliHelper)request.getAttribute("profiliHelper");
  pageContext.setAttribute("profiliHelper", profiliHelper, PageContext.REQUEST_SCOPE);
  
  //pageContext.setAttribute("profiloPersonaFisica", profiliHelper.getProfiloRichiedente(), PageContext.REQUEST_SCOPE);

  if(rwh.getSiracErrors().isEmpty()) {
	  myData.setSelaccrRichiedente(profiliHelper.getProfiloRichiedente());
	    
    //boolean isRichiedenteDefined = profiliHelper.isProfiloRichiedenteDefined();
    //String[] opzioniTitolare = (String[])profiliHelper.getScelteProfiloTitolare().toArray();
	  //pageContext.setAttribute("opzioniTitolare", opzioniTitolare, PageContext.REQUEST_SCOPE);
	  Accreditamento accrSelected = myData.getElencoAccreditamenti()[myData.getSelezioneAccreditamentoIndex()];
	  pageContext.setAttribute("accrSelected", accrSelected, PageContext.REQUEST_SCOPE);
	  
%>  

  <div class="admin_sirac_accr_titolo">
      <bean:message key="label.selAccr2.titolo" />
  </div>


  <div class="text_block" align="left">
    <ppl:errors />
  	<% if ("OAC".equals(accrSelected.getQualifica().getIdQualifica()) || "RCT".equals(accrSelected.getQualifica().getIdQualifica())) { %>
  			<bean:message key="label.selAccr2.richiedenteNonImpostato.info" />
  	<% } else {%>
	    <% if (profiliHelper.getSceltaProfiloRichiedente() != null &&
	          ProfiliHelper.SCELTAPROFILO_PROFILOREGISTRAZIONE.equals(profiliHelper.getSceltaProfiloRichiedente())) {
	    %>
	        <bean:message key="label.selAccr2.richiedenteDaProfiloReg.info" />
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
    <% if (profiliHelper.getSceltaProfiloRichiedente() != null &&
          !ProfiliHelper.SCELTAPROFILO_PROFILOREGISTRAZIONE.equals(profiliHelper.getSceltaProfiloRichiedente())) {
    %>
        <div class="text_block" align="left">
          <h2 style="margin-bottom:0px"><bean:message key="label.selAccr2.info" /></h2>
            <table border="0" cellpadding="0" cellspacing="1">
              <tr>
                <td>
                  <div CLASS="admin_sirac_accr_btn" align="right" style="margin-top:0px;">
                    <A HREF="<%= loopBackCmdClearRichiedente %>" 
                       TITLE="Carica i dati dell'utente autenticato">
                         <bean:message key="label.selaccr2.LoopbackClearDatiRichiedente" />
                    </A>
                  </div>
                </td>
                <td>
                  <div CLASS="admin_sirac_accr_btn" align="right" style="margin-top:0px;">
                    <A HREF="<%= loopBackCmdLoadRichiedente %>" 
                       TITLE="Carica i dati dell'utente autenticato">
                         <bean:message key="label.selaccr2.LoopbackLoadDatiRichiedente" />
                    </A>
                  </div>
                </td>
              </tr>
            </table>
        </div>
    <% } %>
	<c:if test="${'OAC' != accrSelected.qualifica.idQualifica && 'RCT' != accrSelected.qualifica.idQualifica}">    
  	<%@ include file="./profilorichiedentepf.jsp" %>
  </c:if>
  <div>
  	<%----%>
  	<c:if test="${not profiliHelper.profiloTitolareDefined && 'OAC' != accrSelected.qualifica.idQualifica && 'RCT' != accrSelected.qualifica.idQualifica}">
  	  <br>
  	  <div class="text_block" align="left">
  	    <h2>
  	      <bean:message key="label.selAccr2.tipoTitolare" />
  	    </h2>
  	  </div>
	      <bean:message key="label.selAccr2.tipoTitolare.info" />
  		<%--
  		  <html:select property="data.selAccrProfiliHelper.sceltaProfiloTitolare">
		  
            
            <html:option value="data.selAccrProfiliHelper.scelteProfiloTitolare[0]">
            	<c:out value="${profiliHelper.scelteProfiloTitolare[0]}" />
            </html:option>
            
          </html:select>
        --%>
        <dl>
          <c:forEach var="tmp" varStatus="status" items="${profiliHelper.scelteProfiloTitolare}">
            <dd>
          			  <c:choose>
          			  <c:when test='${tmp == "personaFisica"}'>
                        <html:radio property="data.selAccrProfiliHelper.sceltaProfiloTitolare" 
               	   					value="personaFisica">
          			  		Persona Fisica
   				        </html:radio>
          			  </c:when>
          			  <c:otherwise>
                        <html:radio property="data.selAccrProfiliHelper.sceltaProfiloTitolare" 
               	   					value="personaGiuridica">
          			  		Persona Giuridica
   				        </html:radio>
          			  </c:otherwise>
          			  </c:choose>
		        </dd>
          </c:forEach>
  		</dl>
  	</c:if>
  	<%----%>
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