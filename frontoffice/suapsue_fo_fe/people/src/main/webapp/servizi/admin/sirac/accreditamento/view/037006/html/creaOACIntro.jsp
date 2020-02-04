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
	
%>

  <div class="admin_sirac_accr_titolo">
      <bean:message key="label.creaOACIntro.info" />
  </div>

<%
	if(rwh.getSiracErrors().isEmpty()) {
%>
    	<div class="text_block" align="left">
		    <h2>
		    <bean:message key="label.creaOACIntro.titolo" />
		    <ppl:errors />
		    </h2>
	  	</div>
	
		<div class="text_block" align="left">
			<table class="maintable" border="0" cellspacing="1" width="100%">
			    <tr>
			        <td>
			            <span class="FixTextLabel">
			            	Questa attivit&agrave; consente di definire, da parte di un Rappresentante di Associazione di Categoria, dei soggetti che potranno 
			            	successivamente operare per suo conto nell'accesso ai servizi definiti presso questo ente.<br><br>
			           	    L'accesso a questa funzionalit&agrave; &egrave; consentito soltanto se l&#8217;accreditamento corrente   
			           	    ha associato il tipo di qualifica "Intermediario" e la descrizione "Rappresentante Associazione di Categoria".<br>
			            </span>
			        </td>
			    </tr>
			    <tr>
			        <%if ((pplProcess.getView()).isBottomNavigationBarEnabled()) { %>
			           <td>Selezionare 'Continua' per selezionare una tra le qualifiche "Rappresentante Associazione di Categoria" attive</td>
			        <% } else { %>
			           <td>Selezionare una delle attivit&agrave; presenti nel men&ugrave; a sinistra&nbsp;</td>
			        <% } %>
			    </tr>
			</table>
		</div>
		
		<br>
		<br>

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
