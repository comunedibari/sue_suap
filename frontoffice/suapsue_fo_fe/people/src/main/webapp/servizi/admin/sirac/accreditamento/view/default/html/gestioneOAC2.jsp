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
<%@ page import="it.people.fsl.servizi.admin.sirac.accreditamento.dto.ListaOperatoriDto" %>
<%@ page import="it.people.fsl.servizi.admin.sirac.accreditamento.dto.OperatoreDto" %>
<%@ page import="java.util.ArrayList" %>
                 
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>
<%-- JSTL --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<jsp:useBean id="pplProcess"   scope="session" type="it.people.process.AbstractPplProcess" />

<%

  HttpServletRequestDelegateWrapperHelper rwh = new HttpServletRequestDelegateWrapperHelper(request);
  pageContext.setAttribute("siracErrorsMap", rwh.getSiracErrors());
	
%>
<% ListaOperatoriDto dto = (ListaOperatoriDto) pplProcess.getDto(); 
   ArrayList listaOperatori = (ArrayList) dto.getListaOperatori();%>

  <div class="admin_sirac_accr_titolo">
      <bean:message key="label.gestioneOAC2.titolo" />
  </div>
  
<%
	if(rwh.getSiracErrors().isEmpty()) {
%>
  <div class="text_block" align="left">
    <ppl:errors />
    <h2>
    <bean:message key="label.gestioneOAC2.info" />
    </h2>
  </div>

<% 
	if ( listaOperatori.size()!=0 ) { 
%> 

  <div class="text_block" align="left">
	<table class="maintable" border="0" cellspacing="1" width="100%">
	    <tr>
	    	<td>
			  <table border="0" cellpadding="0" cellspacing="0" class="table_data" summary="Tabella che contiene gli operatori dell'associazione di categoria" >		
			     <tr>
			       <th class="FixTextLabel" >
				     <bean:message key="label.gestioneOAC2.codiceFiscale"/>&nbsp;
			  	   </th>
			       <th class="FixTextLabel" >
				     <bean:message key="label.gestioneOAC2.qualifica"/>&nbsp;
			  	   </th>
			       <th class="FixTextLabel" >
				     <bean:message key="label.gestioneOAC2.attivo"/>&nbsp;
			  	   </th>
			       <th class="FixTextLabel" >
				     <bean:message key="label.gestioneOAC2.deleted"/>&nbsp;
			  	   </th>
			     </tr>
<%   for (int i=0; i<listaOperatori.size(); i++){
            OperatoreDto operatore = (OperatoreDto) listaOperatori.get(i); %>
			     <tr>
			    	<td class="FixTextField" >
				     	<bean:write name="pplProcess" property='<%="dto.listaOperatori[" + i + "].codiceFiscale"%>'/>&nbsp;	  
				    </td>  
			    	<td class="FixTextField" >
				     	<bean:write name="pplProcess" property='<%="dto.listaOperatori[" + i + "].qualifica.descrizione"%>'/>&nbsp;	    
				    </td>  
			    	<td class="FixTextField" >
			<%-- 	     	<bean:write name="pplProcess" property='<%="dto.listaOperatori[" + i + "].attivo"%>'/>&nbsp;	
			 --%>	     	<c:choose>
							<c:when test='<%=operatore.isAttivo()%>'>					 
								<input type="checkbox" checked id='attivo_<%=operatore.getId()%>' name='attivo_<%=operatore.getId()%>'>
								<input type="hidden" id='attivo_<%=operatore.getId()%>' name='attivo_<%=operatore.getId()%>' value="off">					 
							</c:when>
							<c:otherwise>					 
								<input type="checkbox" id='attivo_<%=operatore.getId()%>' name='attivo_<%=operatore.getId()%>'>
								<input type="hidden" id='attivo_<%=operatore.getId()%>' name='attivo_<%=operatore.getId()%>' value="off">					 
							</c:otherwise>
						</c:choose>	    
				    </td>  
			    	<td class="FixTextField" >
			<%-- 	     	<bean:write name="pplProcess" property='<%="dto.listaOperatori[" + i + "].deleted"%>'/>&nbsp;
			 --%>	     		     	<c:choose>
							<c:when test='<%=operatore.isDeleted()%>'>					 
								<input type="checkbox" checked id='deleted_<%=operatore.getId()%>' name='deleted_<%=operatore.getId()%>'>
								<input type="hidden" id='deleted_<%=operatore.getId()%>' name='deleted_<%=operatore.getId()%>' value="off">					 
							</c:when>
							<c:otherwise>					 
								<input type="checkbox" id='deleted_<%=operatore.getId()%>' name='deleted_<%=operatore.getId()%>'>
								<input type="hidden" id='deleted_<%=operatore.getId()%>' name='deleted_<%=operatore.getId()%>' value="off">					 
							</c:otherwise>
						</c:choose>	    
				    </td>  
			     </tr>
<%   } %>

			  </table>
			</td>
		</tr>

	</table>
  </div>
  
<% } else { %>
 <bean:message key="label.gestioneOAC2.noOperatori"/>&nbsp;
<% } %>
  
  

  
  
  <br><br>
  <table align="right">
  <tr><td>
	<ppl:commandLoopback commandProperty="salvaOperatori" styleClass="btn" >
		&nbsp;<bean:message key="label.gestioneOAC2.salva"/>&nbsp;
	</ppl:commandLoopback>
  </td></tr>
  </table>
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