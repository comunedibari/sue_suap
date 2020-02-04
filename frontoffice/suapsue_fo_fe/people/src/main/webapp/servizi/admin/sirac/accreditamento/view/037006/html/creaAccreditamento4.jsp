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
<jsp:useBean id="accrIntrmForm"   scope="request" type="it.people.sirac.web.forms.AccrIntrmForm" />

<%
// codice da spostare nella service()

  HttpServletRequestDelegateWrapperHelper rwh = new HttpServletRequestDelegateWrapperHelper(request);
  //rwh.addSiracError("errore1", "messaggio1");
  pageContext.setAttribute("siracErrorsMap", rwh.getSiracErrors());
  
  pageContext.setAttribute("accrIntrmForm", request.getAttribute("accrIntrmForm"));
	
	ProcessData myData = (ProcessData) pplProcess.getData();
  
  ProfiliHelper profiliHelper = myData.getSelAccrProfiliHelper();
%>

<%
  
  ProfiloLocale pl = ((ProcessData)pplProcess.getData()).getProfiloLocale();

  pageContext.setAttribute("pl", pl);
  
  

%>
  <div class="admin_sirac_accr_titolo">
      <bean:message key="label.creaAccr4.titolo" />
  </div>

<%
	if(rwh.getSiracErrors().isEmpty()) {
%>
    <div class="text_block" align="left">
      <ppl:errors />
    </div>
	  <div class="text_block" align="left">
      <h2>
        <bean:message key="label.creaAccr4.info" />
      </h2>
  	  <br>
	    <table>
	      <tr>
	          <td class="pathBold"><ppl:fieldLabel key="label.creaAccr2.tipoQualifica" 
	          					  fieldName="data.accrIntrmForm.tipoQualifica"/></td>
	          <td>
		          <c:out value="${accrIntrmForm.tipoQualifica}" />
		      </td>
	      </tr>
	      <tr>
	          <td class="pathBold"><ppl:fieldLabel key="label.creaAccr2.descrQualifica" 
	          					  fieldName="data.accrIntrmForm.descrizioneQualifica"/></td>
  	        <td>
		          <c:out value="${accrIntrmForm.descrizioneQualifica}" />
		      </td>
	      </tr>
  	   </table>
    </div>
    
    <br>
    
    <div class="text_block" align="left">
      <bean:message key="label.creaAccr4.riepilogo1" />
	    <br><br>
 	    <table>
	      <tr>
          <td class="pathBold"><ppl:fieldLabel key="label.creaAccr2.codiceFiscaleIntermediario" 
          					  fieldName="data.accrIntrmForm.codiceFiscaleIntermediario"/></td>
          <td>
	          <c:out value="${accrIntrmForm.codiceFiscaleIntermediario}" />
		      </td>
	      </tr>
    	  <tr>
          <td class="pathBold"><ppl:fieldLabel key="label.creaAccr2.denominazione" 
          					  fieldName="data.accrIntrmForm.denominazione"/></td>
          <td>
	          <c:out value="${accrIntrmForm.denominazione}" />
	      	</td>
      	</tr>
	      <tr>
          <td class="pathBold"><ppl:fieldLabel key="label.creaAccr2.sedeLegale" 
          					  fieldName="data.accrIntrmForm.sedeLegale"/></td>
          <td>
	          <c:out value="${accrIntrmForm.sedeLegale}" />
		      </td>
	      </tr>
	      <tr>
          <td class="pathBold"><ppl:fieldLabel key="label.creaAccr2.rifDoc" 
          					  fieldName="data.accrIntrmForm.descrizione"/></td>
          <td>
	          <c:out value="${accrIntrmForm.descrizione}" />
		      </td>
	      </tr>
	    </table>
	  </div>
  
  	<br>
  	<% if ( profiliHelper.TIPOQUALIFICA_INTERMEDIARIO.equals(accrIntrmForm.getTipoQualifica()) ||
					  profiliHelper.TIPOQUALIFICA_RAPPRPERSGIURIDICA.equals(accrIntrmForm.getTipoQualifica())) { %>
	    <div class="text_block" align="left">
	      <bean:message key="label.creaAccr4.riepilogo2" />
		    <br><br>
		  	<table>
		      <tr>
		        <td class="pathBold"><ppl:fieldLabel key="label.personaFisica.nome" 
	          					  fieldName="data.accrIntrmForm.rapprLegaleForm.nome"/></td>
	          <td><c:out value="${accrIntrmForm.rapprLegaleForm.nome}" /></td>
		      </tr>
		      <tr>
	          <td class="pathBold"><ppl:fieldLabel key="label.personaFisica.cognome" 
	          					  fieldName="data.accrIntrmForm.rapprLegaleForm.cognome"/></td>
	          <td><c:out value="${accrIntrmForm.rapprLegaleForm.cognome}" /></td>
		      </tr>
		  
		      <tr>
	          <td class="pathBold"><ppl:fieldLabel key="label.personaFisica.codiceFiscale" 
	          					  fieldName="data.accrIntrmForm.rapprLegaleForm.codiceFiscale"/></td>
	          <td><c:out value="${accrIntrmForm.rapprLegaleForm.codiceFiscale}" /></td>
		      </tr>
		      <tr>
	          <td class="pathBold"><ppl:fieldLabel key="label.personaFisica.dataNascita" 
	          					  fieldName="data.accrIntrmForm.rapprLegaleForm.dataNascita"/></td>
	          <td><c:out value="${accrIntrmForm.rapprLegaleForm.dataNascita}" /></td>
		      </tr>
		      <tr>
	          <td class="pathBold"><ppl:fieldLabel key="label.personaFisica.luogoNascita" 
	          					  fieldName="data.accrIntrmForm.rapprLegaleForm.luogoNascita"/></td>
	          <td><c:out value="${accrIntrmForm.rapprLegaleForm.luogoNascita}" /></td>
		      </tr>
		      <tr>
	          <td class="pathBold"><ppl:fieldLabel key="label.personaFisica.provinciaNascita" 
	          					  fieldName="data.accrIntrmForm.rapprLegaleForm.provinciaNascita"/></td>
	          <td><c:out value="${accrIntrmForm.rapprLegaleForm.provinciaNascita}" /></td>
		      </tr>
		      <tr>
	          <td class="pathBold"><ppl:fieldLabel key="label.personaFisica.sesso" 
	          					  fieldName="data.accrIntrmForm.rapprLegaleForm.sesso"/></td>
	          <td><c:out value="${accrIntrmForm.rapprLegaleForm.sesso}" /></td>
		      </tr>
		      <tr>
	          <td class="pathBold"><ppl:fieldLabel key="label.personaFisica.indirizzoResidenza" 
	          					  fieldName="data.accrIntrmForm.rapprLegaleForm.indirizzoResidenza"/></td>
	          <td><c:out value="${accrIntrmForm.rapprLegaleForm.indirizzoResidenza}" /></td>
		      </tr>
	  		</table>
			</div>
    <% } %>
  
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