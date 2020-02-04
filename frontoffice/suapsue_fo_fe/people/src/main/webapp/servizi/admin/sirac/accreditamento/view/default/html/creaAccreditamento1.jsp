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
<jsp:useBean id="accrIntrmForm"   scope="request" type="it.people.sirac.web.forms.AccrIntrmForm" />

<%

  HttpServletRequestDelegateWrapperHelper rwh = new HttpServletRequestDelegateWrapperHelper(request);
  pageContext.setAttribute("siracErrorsMap", rwh.getSiracErrors());
  pageContext.setAttribute("qualificheAccreditabili", request.getAttribute("qualificheAccreditabili"));
  
   pageContext.setAttribute("accrIntrmForm", request.getAttribute("accrIntrmForm"));
	
%>

  <div class="admin_sirac_accr_titolo">
      <bean:message key="label.creaAccr1.titolo" />
  </div>

<%
	if(rwh.getSiracErrors().isEmpty()) {
%>
  <div class="text_block" align="left">
    <h2>
      <bean:message key="label.creaAccr1.info" />
    </h2>
    <ppl:errors />
    <br>
  </div>
  
  <div class="text_block" align="left">
        Selezionare dall'elenco a discesa sottostante la qualifica che si desidera autocertificare
  </div>
  

  
  <div class="text_block">
    <%--
     	<table border="0" cellspacing="0" width="100%">
  	  <tr align="left"> 
  	    <td height="40" align="left" valign="bottom" > 
    		  <span class="pathBold">
    		    	<bean:message key="label.creaAccr1.selQualifica" /> 
    		  </span> 
              <html:select property="data.accrIntrmForm.idQualifica">
    		  
                <html:options collection="qualificheAccreditabili" property="idQualifica" 
                          labelProperty="descrizione"/>
              
              </html:select>
  		  </td>
  	  </tr>
      <tr>
        <td class="spaziatore" ><br></td>
      </tr>
  	 </table>
   --%>
   <table>
      <tr>
          <td class="pathBold">
            <bean:message key="label.creaAccr1.selQualifica" /> 
          </td>
          <td>
            <html:select property="data.accrIntrmForm.idQualifica">
        
              <html:options collection="qualificheAccreditabili" property="idQualifica" 
                        labelProperty="descrizione"/>
            
            </html:select>
        </td>
      </tr>
        <tr>
          <td class="spaziatore" ><br></td>
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