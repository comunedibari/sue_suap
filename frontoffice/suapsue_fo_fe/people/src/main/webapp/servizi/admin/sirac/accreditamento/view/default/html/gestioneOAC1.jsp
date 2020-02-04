<%@ page contentType="text/html" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="it.people.sirac.accr.beans.*,
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

<%

  HttpServletRequestDelegateWrapperHelper rwh = new HttpServletRequestDelegateWrapperHelper(request);
  pageContext.setAttribute("siracErrorsMap", rwh.getSiracErrors());
  pageContext.setAttribute("accreditamenti", request.getAttribute("accreditamenti"));
  Accreditamento accrRCT = (Accreditamento)session.getAttribute("accreditamentoRCT");
  ProcessData myData = (ProcessData) pplProcess.getData();
	
%>

  <div class="admin_sirac_accr_titolo">
      <bean:message key="label.creaOAC1.titolo" />
  </div>

<%
	if(rwh.getSiracErrors().isEmpty()) {
%>

<%
	if(accrRCT!=null){
		myData.setSelezioneAccreditamentoIndex(999);
%>
	  <div class="text_block" align="left">
	    <h2>
  		  <bean:message key="label.creaOAC1.alreadyRCT1" /><br>
   		  <bean:message key="label.creaOAC1.alreadyRCT2" />
        </h2>
  	 
	    <table class="table_data" width="100%">
	      <thead>
	          <tr>
	              <th></th>
	              <th>Qualifica</th>
	              <th>Codice Fiscale</th>
	              <th>Partita IVA</th>
	              <th>Nome e Cognome o <br/>Ragione Sociale</th>
	          </tr>
          </thead>
          <tbody>
	      	<tr>
		  	  <td align="left">
			      <html:radio property="data.selezioneAccreditamentoIndex" value="999" />
			  </td>
			  <td><%=accrRCT.getQualifica().getDescrizione()%></td>            
        <td><%=accrRCT.getProfilo().getCodiceFiscaleIntermediario()%></td>            
        <td><%=accrRCT.getProfilo().getPartitaIvaIntermediario() %></td>            
        <td><%=accrRCT.getProfilo().getDenominazione() %></td>
			</tr>
          </tbody>
	 	</table>
	  </div>
	 
	  <div class="text_block" align="left">
	    <h2>
	      <bean:message key="label.creaOAC1.info" />
	    </h2>
	  </div>
<%
	} 
%>	
  	  <div class="text_block" align="left">
		<table class="maintable" border="0" cellspacing="1" width="100%">
		    <tr>
		    	<td>
				    <c:choose>
				      <c:when test="${empty accreditamenti}">
						<bean:message key="error.gestioneOAC1.nessunAccreditamento" /><br>
				      </c:when>
				      <c:otherwise>
				          <table class="table_data" width="100%">
					          <thead>
						          <tr>
						              <th></th>
						              <th>Qualifica</th>
						              <th>Codice Fiscale</th>
						              <th>Partita IVA</th>
						              <th>Nome e Cognome o <br/>Ragione Sociale</th>
						          </tr>
					          </thead>
					          <tbody>
					        <% int i=0; %>        
					          <c:forEach var="tmp" varStatus="status" items="${requestScope.accreditamenti}">
						          <tr>
						              <td align="left">
						                <html:radio property="data.selezioneAccreditamentoIndex" value="<%=String.valueOf(i++)%>" />
						              </td>
						              <td><c:out value="${tmp.qualifica.descrizione}" /></td>            
						              <td><c:out value="${tmp.profilo.codiceFiscaleIntermediario}" /></td>            
						              <td><c:out value="${tmp.profilo.partitaIvaIntermediario}" /></td>            
						              <td><c:out value="${tmp.profilo.denominazione}" /></td>
						          </tr>
					          </c:forEach>
					          </tbody>
				          </table>
				      </c:otherwise>
				    </c:choose>
			    </td>
		    </tr>
		    
<%-- 
		    <tr>
		        <%if ((pplProcess.getView()).isBottomNavigationBarEnabled()) { %>
		           <td>Selezionare 'Continua' per inserire il profilo del delegato&nbsp;</td>
		        <% } else { %>
		           <td>Selezionare una delle attivit&agrave; presenti nel men&ugrave; a sinistra&nbsp;</td>
		        <% } %>
		    </tr>
--%>		
		</table>
	  </div>
	  <br><br>
  

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
