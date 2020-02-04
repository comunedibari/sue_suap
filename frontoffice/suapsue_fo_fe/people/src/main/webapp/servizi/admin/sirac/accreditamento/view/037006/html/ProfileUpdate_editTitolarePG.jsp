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
         <td>
<%--        			<input type="text" name="profiloTitolareCodiceFiscale" value="<c:out value='${profiloRichiedenteTitolareBean.profiloTitolare.codiceFiscale}'/>" size="12" maxlength="11"/>--%>
									<input type="text" name="profiloTitolareCodiceFiscale" value="" size="12" maxlength="11"/>
   			</td>
  </tr>
  <tr>
        <td class="pathBold"><bean:message key="label.profiloPersonaGiuridica.partitaIva"/></td>
        <td>
<%--        			<input type="text" name="profiloTitolarePartitaIva" value="<c:out value='${profiloRichiedenteTitolareBean.profiloTitolare.partitaIva}'/>" size="12" maxlength="11"/>--%>
									<input type="text" name="profiloTitolarePartitaIva" value="" size="12" maxlength="11"/>
	      </td>
 	</tr>
 	<tr>
       <td class="pathBold"><bean:message key="label.profiloPersonaGiuridica.denominazione"/></td>
        <td>
<%--           <input type="text" name="profiloTitolareDenominazione" value="<c:out value='${profiloRichiedenteTitolareBean.profiloTitolare.denominazione}'/>" size="70" maxlength="100"/>--%>
							 <input type="text" name="profiloTitolareDenominazione" value="" size="70" maxlength="100"/>
	      </td>
  </tr>
  <tr>
       <td class="pathBold"><bean:message key="label.profiloPersonaGiuridica.sedeLegale"/></td>
        <td>
<%--      			<input type="text" name="profiloTitolareSedeLegale" value="<c:out value='${profiloRichiedenteTitolareBean.profiloTitolare.sedeLegale}'/>" size="70" maxlength="100"/>--%>
								<input type="text" name="profiloTitolareSedeLegale" value="" size="70" maxlength="100"/>
	     	</td>
  </tr>
</table>

<h5>Rappresentante Legale</h5>

<div class="text_block" align="left">
	<table>
    <tr>
      <td class="pathBold"><bean:message key="label.profiloPersonaFisica.nome"/></td>
      <td>
<%--      		<input type="text" name="profiloTitolareRappresentanteLegaleNome" value="<c:out value='${profiloRichiedenteTitolareBean.profiloTitolare.rappresentanteLegale.nome}'/>" size="70" maxlength="100"/>--%>
							<input type="text" name="profiloTitolareRappresentanteLegaleNome" value="" size="70" maxlength="100"/>
  		</td>
    </tr>
    <tr>
      <td class="pathBold"><bean:message key="label.profiloPersonaFisica.cognome"/></td>
	    <td>
<%--	 	      <input type="text" name="profiloTitolareRappresentanteLegaleCognome" value="<c:out value='${profiloRichiedenteTitolareBean.profiloTitolare.rappresentanteLegale.cognome}'/>" size="70" maxlength="100"/>--%>
    	 	      <input type="text" name="profiloTitolareRappresentanteLegaleCognome" value="" size="70" maxlength="100"/>
	    </td>								      
		</tr>
    <tr>
      <td class="pathBold"><bean:message key="label.profiloPersonaFisica.codiceFiscale"/></td>
      <td>
<%--     			<input type="text" name="profiloTitolareRappresentanteLegaleCodiceFiscale"  value="<c:out value='${profiloRichiedenteTitolareBean.profiloTitolare.rappresentanteLegale.codiceFiscale}'/>" size="20" maxlength="20"/>--%>
							<input type="text" name="profiloTitolareRappresentanteLegaleCodiceFiscale"  value="" size="18" maxlength="16"/>
 			</td>
    </tr>
	</table>
</div>