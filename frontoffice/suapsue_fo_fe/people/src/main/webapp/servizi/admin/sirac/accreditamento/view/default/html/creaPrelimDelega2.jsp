<%@ page contentType="text/html" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="it.people.sirac.accr.beans.*,
                 it.people.process.*,
                 it.people.*,
                 it.people.util.*,
                 it.people.sirac.core.*,
                 it.people.sirac.accr.*,
                 it.people.sirac.accr.beans.*,
                 it.people.sirac.web.forms.*,
                 it.people.wrappers.*,
                 it.people.layout.Pager,
                 it.people.fsl.servizi.admin.sirac.accreditamento.steps.utility.ServiceParameterConstants,
                 it.people.fsl.servizi.admin.sirac.accreditamento.model.ProcessData" %>
<%@ page import="it.people.core.Logger" %>
<%@ page import="it.people.util.NavigatorHelper" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>
<%-- JSTL --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<jsp:useBean id="pplProcess"   scope="session" type="it.people.process.AbstractPplProcess" />
<jsp:useBean id="CurrentActivity" scope="request" type="it.people.Activity" />
<%
	java.util.ArrayList bottoniVisibili=new java.util.ArrayList(); 
	java.util.ArrayList bottoniNascosti=new java.util.ArrayList();
	Pager pager = new Pager(request, pplProcess, CurrentActivity, bottoniVisibili, bottoniNascosti);
	
  HttpServletRequestDelegateWrapperHelper rwh = new HttpServletRequestDelegateWrapperHelper(request);
  pageContext.setAttribute("siracErrorsMap", rwh.getSiracErrors());
  
  ServiceParameters serviceParams = (ServiceParameters)session.getAttribute("serviceParameters");
  String servizioCreazioneDelegheURL = serviceParams.get(ServiceParameterConstants.DELEGATE_CREATION_SERVICE_URL);
  String servizioAttivazioneDelegheURL = serviceParams.get(ServiceParameterConstants.DELEGATE_ACTIVATION_SERVICE_URL);

  ProcessData myData = (ProcessData) pplProcess.getData();
  
  ProfiliHelper profiliHelper = (ProfiliHelper)request.getAttribute("profiliHelper");
  pageContext.setAttribute("profiliHelper", profiliHelper, PageContext.REQUEST_SCOPE);
  
  if(rwh.getSiracErrors().isEmpty()) {
	  myData.setSelaccrRichiedente(profiliHelper.getProfiloRichiedente());
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
      <bean:message key="label.creaPrelimDelega2.titolo" />
      <br>
  </div>


  <div class="text_block" align="left">
    <ppl:errors />
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
		<h2>
	    <bean:message key="label.creaPrelimDelega2.richiedenteDaProfiloReg.info" />
		</h2>
	</div>

  <%@ include file="./profilorichiedentepf.jsp" %>
  
  <div class="text_block" align="left">
  	<h2>
	    <bean:message key="label.creaPrelimDelega2.titolareDaProfiloReg.info1" /><br>
    </h2>
  </div>
    
  <div class="text_block" align="left">
    <bean:message key="label.creaPrelimDelega2.titolareDaProfiloReg.info2" />
  </div>

	<%@ include file="./profilotitolarepf.jsp" %>
	
	<% if ( (profiliHelper.TIPOQUALIFICA_INTERMEDIARIO.equals(profiliHelper.getTipoQualifica()) ||
			    profiliHelper.TIPOQUALIFICA_RAPPRPERSGIURIDICA.equals(profiliHelper.getTipoQualifica())) &&
			    !profiliHelper.getSceltaProfiloTitolare().equals(ProfiliHelper.SCELTAPROFILO_PROFILOACCREDITAMENTO)) { %>
        <div class="text_block" align="left">
          <h2><bean:message key="label.selAccr4.soggettoAssociatoAlProfilo.info" /></h2>
    	  </div>
      	<%@ include file="./profilointermediariopg.jsp" %>
  <% } %>

<br>

	<table id="footer" border="0" cellpadding="0" cellspacing="0" width="100%">
	    <tr><td>
	    	<% if(myData.getTipoRichiestaDelega().equals(ProcessData.RICHIESTA_PRELIMINARE_DELEGA)) { %>
					<a style="display:block;width:268px;height:15px;float:right; padding: 1px 5px 0px; " class="<%=pager.GetNextCssClass()%>" href="<%=servizioCreazioneDelegheURL%>" title="Procedi alla creazione preliminare deleghe">
					 	<bean:message key="label.creaPrelimDelega2.transferToCreazioneDelegheService" />
					</a>
			<% } else { %>
					<a style="display:block;width:200px;height:15px;float:right; padding: 1px 5px 0px; " class="<%=pager.GetNextCssClass()%>" href="<%=servizioAttivazioneDelegheURL%>" title="Procedi alla attivazione deleghe">
					 	<bean:message key="label.creaPrelimDelega2.transferToAttivazioneDelegheService" />
					</a>
			<% } %>
	
		<%-- Pulsante Previous --%>
	    <html:submit property="<%=pager.GetPreviousLabel()%>" styleClass="<%=pager.GetPreviousCssClass()%>">
	      <bean:message key="<%=pager.GetPreviousLabel()%>"/>
	    </html:submit>
	
		<%-- Elenco step --%>        
		<%
		int numberOfSteps = CurrentActivity.getStepCount();
	    for (int index=0; numberOfSteps > 1 && index < numberOfSteps; index ++) {
	        String currentClass = "txtStepEnable";
		
	        if (index == CurrentActivity.getCurrentStepIndex())
	            currentClass = "txtStepCurrent";
		%>
	        <span class="<%=currentClass%>"> 
	            <%= Integer.toString(index+1) %> 
	        </span>
		<%
	    }
	    // se non ho STEPs (o ne ho uno solo) metto cmq degli spazi 
	    // cosi' separo i bottoni NEXT e PREV
	    
	    if(numberOfSteps <= 1 ){%>
	        &nbsp;&nbsp;&nbsp;&nbsp;
		<%}%>
	
		<%-- Pulsante Next --%>     
<%--       	<div class="<%=pager.GetNextCssClass()%>" align="right" style="margin-top:0px;">  --%>

<%--				</div> --%>
		<script>
			try {
				document.all('<%=pager.GetNextLabel()%>').focus();
			} catch(e) {}		
		</script>	 
	
	    </td></tr>
	</table>










<%--
	<table border="0" cellpadding="1" cellspacing="1">
     <tr>
      <td>
			</td>
		</tr>
	</table>
  --%>
  
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