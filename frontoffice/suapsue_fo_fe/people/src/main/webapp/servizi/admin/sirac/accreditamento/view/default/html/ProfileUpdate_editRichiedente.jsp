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
<%
	ProfiloPersonaFisica profiloRichiedente = profiloRichiedenteTitolareBean.getProfiloRichiedente();
%>

<table>
    <tr>
        <td class="pathBold"><bean:message key="label.profiloPersonaFisica.nome"/></td>
        <td>
	          <input type="text" name="profiloRichiedenteNome" value="<c:out value='${profiloRichiedente.nome}'/>" size="70" maxlength="100"/>
        </td>
      </tr>
    <tr>
        <td class="pathBold"><bean:message key="label.profiloPersonaFisica.cognome"/></td>
 	      <td>
						<input type="text" name="profiloRichiedenteCognome" value="<c:out value='${profiloRichiedente.cognome}'/>" size="70" maxlength="100"/>
 	      </td>
    </tr>
    <tr>
        <td class="pathBold"><bean:message key="label.profiloPersonaFisica.codiceFiscale"/></td>
        <td>
	          <input type="text" name="profiloRichiedenteCodiceFiscale" value="<c:out value='${profiloRichiedente.codiceFiscale}'/>" size="18" maxlength="16"/>
        </td>
    </tr>
    <tr>
        <td class="pathBold"><bean:message key="label.profiloPersonaFisica.dataNascita"/></td>
        <td>
	          <input type="text" name="profiloRichiedenteDataNascitaString" value="<c:out value='${profiloRichiedente.dataNascitaString}'/>" size="10" maxlength="10"/> (gg/mm/aaaa)
        </td>
    </tr>
    <tr>
        <td class="pathBold"><bean:message key="label.profiloPersonaFisica.luogoNascita"/></td>
        <td>
	          <input type="text" name="profiloRichiedenteLuogoNascita" value="<c:out value='${profiloRichiedente.luogoNascita}'/>" size="70" maxlength="70"/>
        </td>
    </tr>
    <tr>
        <td class="pathBold"><bean:message key="label.profiloPersonaFisica.provinciaNascita"/></td>
        <td>
	          <input type="text" name="profiloRichiedenteProvinciaNascita" value="<c:out value='${profiloRichiedente.provinciaNascita}'/>" size="2" maxlength="2"/>
			</td>										          
    </tr>
    <tr>
        <td class="pathBold"><bean:message key="label.profiloPersonaFisica.sesso"/></td>
        <td>
        	<c:choose>
        		<c:when test='${profiloRichiedente.sesso eq "F"}'>
		          M<input type="radio" name="profiloRichiedenteSesso" value="M" size="1" maxlength="1"/>
		          F<input type="radio" name="profiloRichiedenteSesso" value="F" size="1" maxlength="1" checked/>
						</c:when>
						<c:otherwise>
		          M<input type="radio" name="profiloRichiedenteSesso" value="M" size="1" maxlength="1" checked />
		          F<input type="radio" name="profiloRichiedenteSesso" value="F" size="1" maxlength="1"/>
			      </c:otherwise>
		      </c:choose>
        </td>
    </tr>
    <tr>
        <td class="pathBold"><bean:message key="label.profiloPersonaFisica.indirizzoResidenza"/></td>
        <td>
        			<input type="text" name="profiloRichiedenteIndirizzoResidenza" value="<c:out value='${profiloRichiedente.indirizzoResidenza}'/>" size="70" maxlength="70"/>
        </td>
    </tr>
	</table>