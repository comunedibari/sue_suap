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
<jsp:useBean id="profiloRichiedenteTitolareBean" scope="request" type="it.people.sirac.authentication.beans.ProfiloRichiedenteTitolareBean" />

<table>
    <tr>
        <td class="pathBold"><bean:message key="label.profiloPersonaFisica.nome"/></td>
        <td>
	          <input type="text" name="profiloTitolareNome" value="<c:out value='${profiloRichiedenteTitolareBean.profiloTitolare.nome}'/>" size="70" maxlength="100"/>
        </td>
      </tr>
    <tr>
        <td class="pathBold"><bean:message key="label.profiloPersonaFisica.cognome"/></td>
 	      <td>
						<input type="text" name="profiloTitolareCognome" value="<c:out value='${profiloRichiedenteTitolareBean.profiloTitolare.cognome}'/>" size="70" maxlength="100"/>
 	      </td>
    </tr>
    <tr>
        <td class="pathBold"><bean:message key="label.profiloPersonaFisica.codiceFiscale"/></td>
        <td>
	          <input type="text" name="profiloTitolareCodiceFiscale" value="<c:out value='${profiloRichiedenteTitolareBean.profiloTitolare.codiceFiscale}'/>" size="18" maxlength="16"/>
        </td>
    </tr>
    <tr>
        <td class="pathBold"><bean:message key="label.profiloPersonaFisica.dataNascita"/></td>
        <td>
	          <input type="text" name="profiloTitolareDataNascitaString" value="<c:out value='${profiloRichiedenteTitolareBean.profiloTitolare.dataNascitaString}'/>" size="10" maxlength="10"/> (gg/mm/aaaa)
        </td>
    </tr>
    <tr>
        <td class="pathBold"><bean:message key="label.profiloPersonaFisica.luogoNascita"/></td>
        <td>
	          <input type="text" name="profiloTitolareLuogoNascita" value="<c:out value='${profiloRichiedenteTitolareBean.profiloTitolare.luogoNascita}'/>" size="70" maxlength="70"/>
        </td>
    </tr>
    <tr>
        <td class="pathBold"><bean:message key="label.profiloPersonaFisica.provinciaNascita"/></td>
        <td>
	          <input type="text" name="profiloTitolareProvinciaNascita" value="<c:out value='${profiloRichiedenteTitolareBean.profiloTitolare.provinciaNascita}'/>" size="2" maxlength="2"/>
			</td>										          
    </tr>
    <tr>
        <td class="pathBold"><bean:message key="label.profiloPersonaFisica.sesso"/></td>
        <td>
        	<c:choose>
        		<c:when test='${profiloRichiedenteTitolareBean.profiloTitolare.sesso eq "F"}'>
		          M<input type="radio" name="profiloTitolareSesso" value="M" size="1" maxlength="1"/>
		          F<input type="radio" name="profiloTitolareSesso" value="F" size="1" maxlength="1" checked/>
						</c:when>
						<c:otherwise>
		          M<input type="radio" name="profiloTitolareSesso" value="M" size="1" maxlength="1" checked />
		          F<input type="radio" name="profiloTitolareSesso" value="F" size="1" maxlength="1"/>
			      </c:otherwise>
		      </c:choose>
        </td>
    </tr>
    <tr>
        <td class="pathBold"><bean:message key="label.profiloPersonaFisica.indirizzoResidenza"/></td>
        <td>
        			<input type="text" name="profiloTitolareIndirizzoResidenza" value="<c:out value='${profiloRichiedenteTitolareBean.profiloTitolare.indirizzoResidenza}'/>" size="70" maxlength="70"/>
        </td>
    </tr>
	</table>