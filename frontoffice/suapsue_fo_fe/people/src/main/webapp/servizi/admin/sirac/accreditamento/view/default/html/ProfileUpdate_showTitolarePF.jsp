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
       <td><c:out value="${profiloRichiedenteTitolareBean.profiloTitolare.nome}"/></td>
   </tr>
   <tr>
       <td class="pathBold"><bean:message key="label.profiloPersonaFisica.cognome"/></td>
       <td><c:out value="${profiloRichiedenteTitolareBean.profiloTitolare.cognome}"/></td>
   </tr>
   <tr>
       <td class="pathBold"><bean:message key="label.profiloPersonaFisica.codiceFiscale"/></td>
       <td><c:out value="${profiloRichiedenteTitolareBean.profiloTitolare.codiceFiscale}"/></td>
   </tr>
   <tr>
       <td class="pathBold"><bean:message key="label.profiloPersonaFisica.dataNascita"/></td>
       <td><c:out value="${profiloRichiedenteTitolareBean.profiloTitolare.dataNascitaString}"/></td>
   </tr>
   <tr>
       <td class="pathBold"><bean:message key="label.profiloPersonaFisica.luogoNascita"/></td>
       <td><c:out value="${profiloRichiedenteTitolareBean.profiloTitolare.luogoNascita}"/></td>
   </tr>
   <tr>
       <td class="pathBold"><bean:message key="label.profiloPersonaFisica.provinciaNascita"/></td>
       <td><c:out value="${profiloRichiedenteTitolareBean.profiloTitolare.provinciaNascita}"/></td>
   </tr>
   <tr>
       <td class="pathBold"><bean:message key="label.profiloPersonaFisica.sesso"/></td>
       <td>
        	<c:choose>
        		<c:when test='${profiloRichiedenteTitolareBean.profiloTitolare.sesso eq "F"}'>F</c:when>
						<c:otherwise>M</c:otherwise>
		      </c:choose>
       </td>
   </tr>
   <tr>
       <td class="pathBold"><bean:message key="label.profiloPersonaFisica.indirizzoResidenza"/></td>
       <td><c:out value="${profiloRichiedenteTitolareBean.profiloTitolare.indirizzoResidenza}"/></td>
	 </tr>
</table>

<input type="hidden" name="profiloTitolareNome" value="<c:out value='${profiloRichiedenteTitolareBean.profiloTitolare.nome}'/>"/>
<input type="hidden" name="profiloTitolareCognome" value="<c:out value='${profiloRichiedenteTitolareBean.profiloTitolare.cognome}'/>"/>
<input type="hidden" name="profiloTitolareCodiceFiscale" value="<c:out value='${profiloRichiedenteTitolareBean.profiloTitolare.codiceFiscale}'/>"/>
<input type="hidden" name="profiloTitolareLuogoNascita" value="<c:out value='${profiloRichiedenteTitolareBean.profiloTitolare.luogoNascita}'/>"/>
<input type="hidden" name="profiloTitolareSesso" value="<c:out value='${profiloRichiedenteTitolareBean.profiloTitolare.sesso}'/>"/>
<input type="hidden" name="profiloTitolareDataNascitaString" value="<c:out value='${profiloRichiedenteTitolareBean.profiloTitolare.dataNascitaString}'/>"/>
<input type="hidden" name="profiloTitolareProvinciaNascita" value="<c:out value='${profiloRichiedenteTitolareBean.profiloTitolare.provinciaNascita}'/>"/>
<input type="hidden" name="profiloTitolareIndirizzoResidenza" value="<c:out value='${profiloRichiedenteTitolareBean.profiloTitolare.indirizzoResidenza}'/>"/>
