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
  String reqType = ProcessData.RICHIESTA_PRELIMINARE_DELEGA;
  request.setAttribute("requestType", reqType);
  
%>
  <div class="admin_sirac_accr_titolo">
      <bean:message key="label.selAccr1.titolo" />
  </div>

<%
  if(rwh.getSiracErrors().isEmpty()) {
%>
  <div class="text_block" align="left">
    <ppl:errors />
  </div>

  <div class="text_block" align="left">
    <h2>
      <bean:message key="label.selAccr1.elencoAccr.info" />
    </h2>
  </div>
  
  <div class="text_block" align="left">
    <c:choose>
      <c:when test="${empty accreditamenti}">
          Nessun accreditamento presente.
      </c:when>
      <c:otherwise>
          Selezionare uno degli accreditamenti attivati dalla tabella sottostante<br/><br/>
          <table class="table_data" width="100%">
          <thead>
          <tr>
              <th>#</th>
              <th>Qualifica</th>
              <th>Codice Fiscale</th>
              <th>Partita IVA</th>
              <th>Nome e Cognome o <br/>Ragione Sociale</th>
              <%--<th>Sede Legale</th>--%>
              <%--<th>Descrizione</th>--%>
              <%--<th>Data Creazione</th>--%>
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
              <%--<td><c:out value="${tmp.profilo.sedeLegale}" /></td>--%>
              <%--<td><c:out value="${tmp.profilo.descrizione}" /></td>--%>
              <%--<td><c:out value="${tmp.profilo.timestampAutoCert}" /></td>--%>
          </tr>
          </c:forEach>
          </tbody>
        </table>
      </c:otherwise>
    </c:choose>
    <br><br>
    <div class="text_block" align="left">
	    <h2>
	        <bean:message key="label.creaPrelimDelega1.tipoRichiesta" />
	    </h2>
    </div>
    <dl>
	    <dd>
	   		<html:radio property="data.tipoRichiestaDelega" value="<%=ProcessData.RICHIESTA_PRELIMINARE_DELEGA%>">
		  		Creazione preliminare delega
	   		</html:radio>
	   	</dd>
	   	<dd>
	   	&nbsp;
	   	</dd>
	   	<dd>
	        <html:radio property="data.tipoRichiestaDelega" value="<%=ProcessData.RICHIESTA_ATTIVAZIONE_DELEGA%>">
	   	   		Attivazione delega
	  		</html:radio>
	    </dd>
  	</dl>
  </div>
  <br/>
  <br/>

<%
  } else {  // Gestione lista errori accodati nella request
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