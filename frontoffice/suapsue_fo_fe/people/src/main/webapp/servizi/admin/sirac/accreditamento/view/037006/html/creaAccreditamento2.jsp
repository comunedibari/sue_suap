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
<jsp:useBean id="pplProcess" scope="session" type="it.people.process.AbstractPplProcess" />
<jsp:useBean id="accrIntrmForm" scope="request" type="it.people.sirac.web.forms.AccrIntrmForm" />

<bean:define id="loopBackCmdLoadRapprLegale" value="javascript:executeSubmit('loopBack.do?propertyName=loadRLData');" />
<bean:define id="loopBackCmdClearRapprLegale" value="javascript:executeSubmit('loopBack.do?propertyName=clearRLData');" />
<bean:define id="loopBackCmdClearDatiProfilo" value="javascript:executeSubmit('loopBack.do?propertyName=clearProfileData');" />

<%
  // codice da spostare nella service()
  HttpServletRequestDelegateWrapperHelper rwh = new HttpServletRequestDelegateWrapperHelper(request);

  //rwh.addSiracError("errore1", "messaggio1");
  pageContext.setAttribute("siracErrorsMap", rwh.getSiracErrors());

  //pageContext.setAttribute("qualificheAccreditabili", request.getAttribute("qualificheAccreditabili"));
  ProcessData myData = (ProcessData)pplProcess.getData();

  //pageContext.setAttribute("accrIntrmForm", myData.getAccrIntrmForm());
  pageContext.setAttribute("accrIntrmForm", request.getAttribute("accrIntrmForm"));
%>
<%
  ProfiloLocale pl = ((ProcessData)pplProcess.getData()).getProfiloLocale();

  pageContext.setAttribute("pl", pl);
%>
  <div class="admin_sirac_accr_titolo">
    <bean:message key="label.creaAccr2.titolo" />
  </div>
<%
  if (rwh.getSiracErrors().isEmpty()) {
%>
    <div class="text_block" align="left">
      <ppl:errors />
    </div>
    <div class="text_block" align="left">
      <table>
      	<tr>
          <td class="pathBold">
            <ppl:fieldLabel key="label.creaAccr2.tipoQualifica" fieldName="data.accrIntrmForm.tipoQualifica" />
          </td>
          <%--
          <td><html:text property="data.accrIntrmForm.tipoQualifica" 
          				 readonly="true" disabled="true"/>
          </td>
          --%>
          <td>
            <b><c:out value="${accrIntrmForm.tipoQualifica}" /></b>
          </td>
        </tr>
        <tr>
          <td class="pathBold">
            <ppl:fieldLabel key="label.creaAccr2.descrQualifica" fieldName="data.accrIntrmForm.descrizioneQualifica" />
          </td>
          <%--
          <td><html:text property="data.accrIntrmForm.descrizioneQualifica" 
          				 readonly="true" disabled="true"/></td>
          --%>
          <td>
            <b><c:out value="${accrIntrmForm.descrizioneQualifica}" /></b>
          </td>
        </tr>
      </table>
    </div>
    <div class="text_block" align="left">
      <h2 style="margin-bottom:0px">
        <bean:message key="label.creaAccr2.info" />
      </h2>
      <table border="0" cellpadding="0" cellspacing="1">
        <tr>
          <td>
            <div CLASS="admin_sirac_accr_btn" align="right" style="margin-top:0px;">
              <A HREF="<%= loopBackCmdClearDatiProfilo %>" 
                 TITLE="Cancella i dati attualmente associati al profilo ">
                   <bean:message key="label.LoopbackClearDatiProfilo" />
              </A>
            </div>
          </td>
        </tr>
      </table>
    </div>
    <div class="text_block" align="left">
      <table>
        <%--
    <tr>
        <td><ppl:fieldLabel key="label.formTest.nome" fieldName="data.nome" /></td>
        <td><ppl:textField property="data.nome" size="40" maxlength="80" /> </td>
    </tr>

      <tr>
        <!--<td><ppl:fieldLabel key="label.profiloLocale.codiceFiscale" fieldName="data.nome"/></td>-->
        <!--<td><html:text property="data.profiloLocale.codiceFiscale" readonly="true" disabled="true"/></td>-->
        <!--<td><html:text property="data.accrIntrmForm.codiceFiscale" readonly="true" disabled="true"/></td>-->
    --%>
        
        
        <c:choose>
          <c:when test="${accrIntrmForm.tipoQualifica == 'Professionista'}">
            <%-- Gestione Qualifica 'Professionista' --%>
            <tr>
              <td class="pathBold">
                <ppl:fieldLabel key="label.creaAccr2.codiceFiscaleIntermediario" fieldName="data.accrIntrmForm.codiceFiscaleIntermediario" />
              </td>
              <td>
                <%--<html:text property="data.accrIntrmForm.codiceFiscaleIntermediario" maxlength="16" />--%>
                <b><c:out value="${accrIntrmForm.codiceFiscaleIntermediario}" /></b>
              </td>
            </tr>
            <tr>
              <td class="pathBold">
                <ppl:fieldLabel key="label.creaAccr2.partitaIvaIntermediario" fieldName="data.accrIntrmForm.partitaIvaIntermediario" />
              </td>
              <td>
                <html:text property="data.accrIntrmForm.partitaIvaIntermediario" maxlength="11" />
              </td>
            </tr>
            <tr>
              <td class="pathBold">
                <%--<ppl:fieldLabel key="label.creaAccr2.denominazione" fieldName="data.accrIntrmForm.denominazione" />--%>
                <ppl:fieldLabel key="label.creaAccr2.nomecognome" fieldName="data.accrIntrmForm.denominazione" />
              </td>
              <td>
                <%--<html:text property="data.accrIntrmForm.denominazione" size="70" maxlength="100" />--%>
                <b><c:out value="${accrIntrmForm.denominazione}" /></b>
              </td>
            </tr>
            <tr>
              <td class="pathBold">
                <%--<ppl:fieldLabel key="label.creaAccr2.sedeLegale" fieldName="data.accrIntrmForm.sedeLegale" />--%>
                <ppl:fieldLabel key="label.creaAccr2.indirizzo" fieldName="data.accrIntrmForm.sedeLegale" />
              </td>
              <td>
                <%--<html:text property="data.accrIntrmForm.sedeLegale" size="70" maxlength="100" />--%>
                <b><c:out value="${accrIntrmForm.sedeLegale}" /></b>
              </td>
            </tr>
          </c:when>
          
          <c:otherwise>
            <%-- Gestione Qualifiche Intermediario e Rappresentante PG --%>
            <tr>
              <td class="pathBold">
                <ppl:fieldLabel key="label.creaAccr2.codiceFiscaleIntermediario" fieldName="data.accrIntrmForm.codiceFiscaleIntermediario" />
              </td>
              <td>
                <html:text property="data.accrIntrmForm.codiceFiscaleIntermediario" maxlength="11" />
              </td>
            </tr>
            <tr>
              <td class="pathBold">
                <ppl:fieldLabel key="label.creaAccr2.partitaIvaIntermediario" fieldName="data.accrIntrmForm.partitaIvaIntermediario" />
              </td>
              <td>
                <html:text property="data.accrIntrmForm.partitaIvaIntermediario" maxlength="11" />
              </td>
            </tr>
            <tr>
              <td class="pathBold">
                <ppl:fieldLabel key="label.creaAccr2.denominazione" fieldName="data.accrIntrmForm.denominazione" />
              </td>
              <td>
                <html:text property="data.accrIntrmForm.denominazione" size="70" maxlength="100" />
              </td>
            </tr>
            <tr>
              <td class="pathBold">
                <ppl:fieldLabel key="label.creaAccr2.sedeLegale" fieldName="data.accrIntrmForm.sedeLegale" />
              </td>
              <td>
                <html:text property="data.accrIntrmForm.sedeLegale" size="70" maxlength="100" />
              </td>
            </tr>
          
          </c:otherwise>
        </c:choose>
        <tr>
          <td class="pathBold">
            <ppl:fieldLabel key="label.creaAccr2.rifDoc" fieldName="data.accrIntrmForm.descrizione" />
          </td>
          <td>
            <html:text property="data.accrIntrmForm.descrizione" size="70" maxlength="255" />
          </td>
        </tr>
        <tr>
          <td class="pathBold">
            <ppl:fieldLabel key="label.creaAccr2.domicilioElettronico" fieldName="data.accrIntrmForm.domicilioElettronico" />
          </td>
          <td>
             <html:text property="data.accrIntrmForm.domicilioElettronico" size="70" maxlength="255" />
          </td>
        </tr>
      </table>
    </div>
<%
    if (accrIntrmForm.getHasRappresentanteLegale()) {
%>
      <div class="text_block">
        <h2 style="margin-bottom:0px">
          <bean:message key="label.creaAccr2.titoloRapprLegale" />
        </h2>
      <table border="0" cellpadding="0" cellspacing="1">
        <tr>
          <td>
            <div CLASS="admin_sirac_accr_btn" align="right" style="margin-top:0px;">
              <A HREF="<%= loopBackCmdLoadRapprLegale %>" 
                 TITLE="Carica il profilo del rappresentante legale con i dati utente autenticato">
                   <bean:message key="label.LoopbackLoadDatiRL" />
              </A>
            </div>
          </td>
          <td>
            <div CLASS="admin_sirac_accr_btn" align="right" style="margin-top:0px;">
              <A HREF="<%= loopBackCmdClearRapprLegale %>" 
                 TITLE="Cancella i dati attualmente associati al profilo del rappresentante legale">
                   <bean:message key="label.LoopbackClearDatiRL" />
              </A>
            </div>
          </td>
        </tr>
      </table>
      </div>
      <div class="text_block">
        La tipologia di qualifica selezionata (
        <c:out value="${accrIntrmForm.tipoQualifica}" />
        ) richiede
        di specificare i dati del rappresentante legale associato al profilo.<br/>
        Utilizzare i pulsanti forniti per precaricare i dati dell'utente autenticato 
        o per cancellare il contenuto attuale dei campi associati al rappresentante legale.
      </div>
        <div class="text_block" align="left">
          <table width="100%">
            <tr>
              <td class="pathBold">
                <ppl:fieldLabel key="label.personaFisica.nome" fieldName="data.accrIntrmForm.rapprLegaleForm.nome" />
              </td>
              <td>
                <html:text property="data.accrIntrmForm.rapprLegaleForm.nome" size="70" maxlength="100" />
              </td>
            </tr>
            <tr>
              <td class="pathBold">
                <ppl:fieldLabel key="label.personaFisica.cognome" fieldName="data.accrIntrmForm.rapprLegaleForm.cognome" />
              </td>
              <td>
                <html:text property="data.accrIntrmForm.rapprLegaleForm.cognome" size="70" maxlength="100" />
              </td>
            </tr>
            <tr>
              <td class="pathBold">
                <ppl:fieldLabel key="label.personaFisica.codiceFiscale" fieldName="data.accrIntrmForm.rapprLegaleForm.codiceFiscale" />
              </td>
              <td>
                <html:text property="data.accrIntrmForm.rapprLegaleForm.codiceFiscale" size="20" maxlength="20" />
              </td>
            </tr>
            <tr>
              <td class="pathBold">
                <ppl:fieldLabel key="label.personaFisica.dataNascita" fieldName="data.accrIntrmForm.rapprLegaleForm.dataNascita" />
              </td>
              <td>
                <html:text property="data.accrIntrmForm.rapprLegaleForm.dataNascita" size="70" maxlength="70" />
              </td>
            </tr>
            <tr>
              <td class="pathBold">
                <ppl:fieldLabel key="label.personaFisica.luogoNascita" fieldName="data.accrIntrmForm.rapprLegaleForm.luogoNascita" />
              </td>
              <td>
                <html:text property="data.accrIntrmForm.rapprLegaleForm.luogoNascita" size="70" maxlength="70" />
              </td>
            </tr>
            <tr>
              <td class="pathBold">
                <ppl:fieldLabel key="label.personaFisica.provinciaNascita" fieldName="data.accrIntrmForm.rapprLegaleForm.provinciaNascita" />
              </td>
              <td>
                <html:text property="data.accrIntrmForm.rapprLegaleForm.provinciaNascita" size="20" maxlength="70" />
              </td>
            </tr>
            <tr>
              <td class="pathBold">
                <ppl:fieldLabel key="label.personaFisica.sesso" fieldName="data.accrIntrmForm.rapprLegaleForm.sesso" />
              </td>
              <td>
                <html:text property="data.accrIntrmForm.rapprLegaleForm.sesso" size="1" maxlength="1" />
              </td>
            </tr>
            <tr>
              <td class="pathBold">
                <ppl:fieldLabel key="label.personaFisica.indirizzoResidenza" fieldName="data.accrIntrmForm.rapprLegaleForm.indirizzoResidenza" />
              </td>
              <td>
                <html:text property="data.accrIntrmForm.rapprLegaleForm.indirizzoResidenza" size="70" maxlength="70" />
              </td>
            </tr>
          </table>
        </div>
<%
      }
%>
<%
    } else {
      // Gestione lista errori accodati nella request
%>
      <div class="text_block" align="left">
        Si sono verificati i seguenti errori:
        <br>
        <c:forEach items='${siracErrorsMap}' var='mapItem'>
          <%--(<c:out value='${mapItem.key}'/>
	
	         <c:out value='${mapItem.value}'/>)--%>
          <c:out value='${mapItem.value}' />
        </c:forEach>
      </div>
<%
      rwh.cleanSiracErrors();
    }
%>
