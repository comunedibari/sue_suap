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
    <td class="pathBold"><bean:message key="label.profiloPersonaGiuridica.codiceFiscale"/></td>
    <td><c:out value="${profiloRichiedenteTitolareBean.profiloTitolare.codiceFiscale}"/></td>
  </tr>
  <tr>
    <td class="pathBold"><bean:message key="label.profiloPersonaGiuridica.partitaIva"/></td>
    <td><c:out value="${profiloRichiedenteTitolareBean.profiloTitolare.partitaIva}"/></td> 
  </tr>
 	<tr>
    <td class="pathBold"><bean:message key="label.profiloPersonaGiuridica.denominazione"/></td>
    <td><c:out value="${profiloRichiedenteTitolareBean.profiloTitolare.denominazione}"/></td> 	
  </tr>
 	<tr>
    <td class="pathBold"><bean:message key="label.profiloPersonaGiuridica.sedeLegale"/></td>
    <td><c:out value="${profiloRichiedenteTitolareBean.profiloTitolare.sedeLegale}"/></td> 	
  </tr>
</table>

<h5>Rappresentante Legale</h5>

<div class="text_block" align="left">
	<table>
    <tr>
      <td class="pathBold"><bean:message key="label.profiloPersonaFisica.nome"/></td>
   		<td><c:out value="${profiloRichiedenteTitolareBean.profiloTitolare.rappresentanteLegale.nome}"/></td>
    </tr>
    <tr>
      <td class="pathBold"><bean:message key="label.profiloPersonaFisica.cognome"/></td>
   		<td><c:out value="${profiloRichiedenteTitolareBean.profiloTitolare.rappresentanteLegale.cognome}"/></td>
		</tr>
    <tr>
      <td class="pathBold"><bean:message key="label.profiloPersonaFisica.codiceFiscale"/></td>
   		<td><c:out value="${profiloRichiedenteTitolareBean.profiloTitolare.rappresentanteLegale.codiceFiscale}"/></td>
    </tr>
	</table>
</div>	

<input type="hidden" name="profiloTitolareCodiceFiscale" value="<c:out value='${profiloRichiedenteTitolareBean.profiloTitolare.codiceFiscale}'/>"/>
<input type="hidden" name="profiloTitolarePartitaIva" value="<c:out value='${profiloRichiedenteTitolareBean.profiloTitolare.partitaIva}'/>"/>
<input type="hidden" name="profiloTitolareDenominazione" value="<c:out value='${profiloRichiedenteTitolareBean.profiloTitolare.denominazione}'/>"/>
<input type="hidden" name="profiloTitolareSedeLegale" value="<c:out value='${profiloRichiedenteTitolareBean.profiloTitolare.sedeLegale}'/>"/>
<input type="hidden" name="profiloTitolareRappresentanteLegaleNome" value="<c:out value='${profiloRichiedenteTitolareBean.profiloTitolare.rappresentanteLegale.nome}'/>"/>
<input type="hidden" name="profiloTitolareRappresentanteLegaleCognome" value="<c:out value='${profiloRichiedenteTitolareBean.profiloTitolare.rappresentanteLegale.cognome}'/>"/>
<input type="hidden" name="profiloTitolareRappresentanteLegaleCodiceFiscale" value="<c:out value='${profiloRichiedenteTitolareBean.profiloTitolare.rappresentanteLegale.codiceFiscale}'/>"/>

