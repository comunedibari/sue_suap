<%@ page contentType="text/html" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="it.people.sirac.accr.beans.*,
                 it.people.process.*,
                 it.people.*,
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
  ProfiloLocale pl = ((ProcessData)pplProcess.getData()).getProfiloLocale();

  pageContext.setAttribute("pl", pl);

  HttpServletRequestDelegateWrapperHelper rwh = new HttpServletRequestDelegateWrapperHelper(request);
  //rwh.addSiracError("errore1", "messaggio1");
  pageContext.setAttribute("siracErrorsMap", rwh.getSiracErrors());
%>
  <div class="admin_sirac_accr_titolo">
      <bean:message key="label.profiloLocale.titolo" />
  </div>

<%
	if(rwh.getSiracErrors().isEmpty()) {
%>
  <div class="text_block" align="left">
    <ppl:errors />
  </div>

  <div class="text_block" align="left">
    <h2>
      <bean:message key="label.profiloLocale.info" />
    </h2>
  </div>
  
    <div class="text_block" align="left">
    <table class="table_data">
      <%--
    <tr>
        <td><ppl:fieldLabel key="label.formTest.nome" fieldName="data.nome" /></td>
        <td><ppl:textField property="data.nome" size="40" maxlength="80" /> </td>
    </tr>

      <tr>
        <!--<td><ppl:fieldLabel key="label.profiloLocale.codiceFiscale" fieldName="data.nome"/></td>-->
        <!--<td><html:text property="data.profiloLocale.codiceFiscale" readonly="true" disabled="true"/></td>-->
        <!--<td><html:text property="data.accrIntrmForm.codiceFiscale" readonly="true" disabled="true"/></td>-->
    --%>
    
<!--     
      <tr>
       <td class="pathBold">
          <bean:message key="label.profiloLocale.codiceFiscale" />
        </td>
        <td>
          <c:out value="${pl.codiceFiscale}" />
        </td>
      </tr>
-->
      <tr>
        <td class="pathBold">
          <bean:message key="label.profiloLocale.idCA" />
        </td>
        <td>
          <c:out value="${pl.idCA}" />
        </td>
      </tr>
      <tr>
        <td class="pathBold">
          <bean:message key="label.profiloLocale.username" />
        </td>
        <td>
          <c:out value="${pl.username}" />
        </td>
      </tr>
      <tr>
        <td class="pathBold">
          <bean:message key="label.profiloLocale.idComune" />
        </td>
        <td>
          <%=pplProcess.getCommune().getLabel() %>&nbsp;(<c:out value="${pl.idComune}" />)
        </td>
      </tr>
      <tr>
        <td class="pathBold">
          <bean:message key="label.profiloLocale.domicilioElettronico" />
        </td>
        <td>
          <c:out value="${pl.domicilioElettronico}" />
        </td>
      </tr>
    </table>
    
    <table>
      <tr>
        <td class="spaziatore" />
      </tr>
      <tr>
        <td class="didascalia">
          Cliccare su "Selezione Attivita'" per tornare al menu di gestione degli accreditamenti.
        </td>
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