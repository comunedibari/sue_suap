<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page contentType="text/html" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="it.people.sirac.accr.beans.*,
								 it.people.core.*, 
								 it.people.content.*, 
								 it.people.filters.*, 
								 java.util.*,
                 it.people.process.*,
                 it.people.*,
                 it.people.util.*,
                 it.people.sirac.accr.*,
                 it.people.sirac.web.forms.*,
                 it.people.wrappers.*,
                 it.people.sirac.core.SiracConstants" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>
<%-- JSTL --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<jsp:useBean id="profiloRichiedenteTitolareBean" scope="request" type="it.people.sirac.authentication.beans.ProfiloRichiedenteTitolareBean" />

<table>
   <tr>
       <td class="pathBold"><bean:message key="label.profiloPersonaFisica.nome"/></td>
       <td><c:out value="${profiloRichiedenteTitolareBean.profiloRichiedente.nome}"/></td>
   </tr>
   <tr>
       <td class="pathBold"><bean:message key="label.profiloPersonaFisica.cognome"/></td>
       <td><c:out value="${profiloRichiedenteTitolareBean.profiloRichiedente.cognome}"/></td>
   </tr>
   <tr>
       <td class="pathBold"><bean:message key="label.profiloPersonaFisica.codiceFiscale"/></td>
       <td><c:out value="${profiloRichiedenteTitolareBean.profiloRichiedente.codiceFiscale}"/></td>
   </tr>
   <tr>
       <td class="pathBold"><bean:message key="label.profiloPersonaFisica.dataNascita"/></td>
       <td><c:out value="${profiloRichiedenteTitolareBean.profiloRichiedente.dataNascitaString}"/></td>
   </tr>
   <tr>
       <td class="pathBold"><bean:message key="label.profiloPersonaFisica.luogoNascita"/></td>
       <td><c:out value="${profiloRichiedenteTitolareBean.profiloRichiedente.luogoNascita}"/></td>
   </tr>
   <tr>
       <td class="pathBold"><bean:message key="label.profiloPersonaFisica.provinciaNascita"/></td>
       <td><c:out value="${profiloRichiedenteTitolareBean.profiloRichiedente.provinciaNascita}"/></td>
   </tr>
   <tr>
       <td class="pathBold"><bean:message key="label.profiloPersonaFisica.sesso"/></td>
       <td>
        	<c:choose>
        		<c:when test='${profiloRichiedenteTitolareBean.profiloRichiedente.sesso eq "F"}'>F</c:when>
						<c:otherwise>M</c:otherwise>
		      </c:choose>
       </td>
   </tr>
   <tr>
       <td class="pathBold"><bean:message key="label.profiloPersonaFisica.indirizzoResidenza"/></td>
       <td><c:out value="${profiloRichiedenteTitolareBean.profiloRichiedente.indirizzoResidenza}"/></td>
	 </tr>
</table>

<input type="hidden" name="profiloRichiedenteNome" value="<c:out value='${profiloRichiedenteTitolareBean.profiloRichiedente.nome}'/>"/>
<input type="hidden" name="profiloRichiedenteCognome" value="<c:out value='${profiloRichiedenteTitolareBean.profiloRichiedente.cognome}'/>"/>
<input type="hidden" name="profiloRichiedenteCodiceFiscale" value="<c:out value='${profiloRichiedenteTitolareBean.profiloRichiedente.codiceFiscale}'/>"/>
<input type="hidden" name="profiloRichiedenteLuogoNascita" value="<c:out value='${profiloRichiedenteTitolareBean.profiloRichiedente.luogoNascita}'/>"/>
<input type="hidden" name="profiloRichiedenteSesso" value="<c:out value='${profiloRichiedenteTitolareBean.profiloRichiedente.sesso}'/>"/>
<input type="hidden" name="profiloRichiedenteDataNascitaString" value="<c:out value='${profiloRichiedenteTitolareBean.profiloRichiedente.dataNascitaString}'/>"/>
<input type="hidden" name="profiloRichiedenteProvinciaNascita" value="<c:out value='${profiloRichiedenteTitolareBean.profiloRichiedente.provinciaNascita}'/>"/>
<input type="hidden" name="profiloRichiedenteIndirizzoResidenza" value="<c:out value='${profiloRichiedenteTitolareBean.profiloRichiedente.indirizzoResidenza}'/>"/>
<input type="hidden" name="profiloRichiedenteNome" value="<c:out value='${profiloRichiedenteTitolareBean.profiloRichiedente.nome}'/>"/>
