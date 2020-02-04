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

  pageContext.setAttribute("delegaForm", request.getAttribute("delegaForm"));
	
%>

  <div class="admin_sirac_accr_titolo">
    <bean:message key="label.creaOAC4.info" />
  </div>

<%
	if(rwh.getSiracErrors().isEmpty()) {
%>
  <div class="text_block" align="left">
    <h2>
    <bean:message key="label.creaOAC4.titolo" />
    </h2>
    <ppl:errors />
  </div>
 
  <div class="text_block" align="left">
      <table>
      <tr>
          <td><ppl:fieldLabel key="label.creaOAC2.descrQualifica" 
          					  fieldName="data.delegaForm.descrQualifica"/></td>
          <td><c:out value="${delegaForm.descrQualifica}" /></td>
      </tr>
      <tr>
          <td><ppl:fieldLabel key="label.creaOAC2.nomeDelegato" 
          					  fieldName="data.delegaForm.nome"/></td>
          <td><c:out value="${delegaForm.nome}" /></td>
      </tr>
      <tr>
          <td><ppl:fieldLabel key="label.creaOAC2.cognomeDelegato" 
          					  fieldName="data.delegaForm.cognome"/></td>
          <td><c:out value="${delegaForm.cognome}" /></td>
      </tr>
      <tr>
          <td><ppl:fieldLabel key="label.creaOAC2.codiceFiscaleDelegato" 
          					  fieldName="data.delegaForm.codiceFiscaleDelegato"/></td>
          <td><c:out value="${delegaForm.codiceFiscaleDelegato}" /></td>
      </tr>
    </table>
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