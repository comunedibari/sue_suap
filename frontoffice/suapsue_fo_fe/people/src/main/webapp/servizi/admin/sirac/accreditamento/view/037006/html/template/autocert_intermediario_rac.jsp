<%@ page contentType="text/html" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="it.people.sirac.accr.beans.*,
                 it.people.process.*,
                 it.people.*,
                 it.people.util.*,
                 it.people.sirac.services.accr.*,
                 it.people.sirac.web.forms.*,
                 it.people.wrappers.*,
                 it.people.fsl.servizi.admin.sirac.accreditamento.model.ProcessData,
                 java.text.SimpleDateFormat,
                 java.util.Date" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>
<%-- JSTL --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<div class="admin_sirac_autocert_body" 
     style="text-transform:uppercase; text-align:center;">
  <u>rappresentante legale / titolare</u>
</div>
<br/>
<div class="admin_sirac_autocert_body">
      <%--<c:out value='${datiAutoCertMap["qualifica"]}'/> <br>--%>
      Di <c:out value='${datiAutoCertMap["denominazione"]}'/>  
      con sede in <c:out value='${datiAutoCertMap["sedeLegale"]}'/> 
      <c:if test='${not empty datiAutoCertMap["codiceFiscaleIntermediario"]}'>
        , codice fiscale <c:out value='${datiAutoCertMap["codiceFiscaleIntermediario"]}'/>
      </c:if>
      <c:if test='${not empty datiAutoCertMap["partitaIvaIntermediario"]}'>
        , partita IVA <c:out value='${datiAutoCertMap["partitaIvaIntermediario"]}'/>
      </c:if>
      .<br/>
      <br/>
      <c:if test='${not empty datiAutoCertMap["rifDocumentiUfficiali"]}'>
      <ul>
      	<li>di svolgere attivit&agrave; di assistenza alle imprese / 
      	soci / clienti (in caso di Associazione ai sensi del proprio statuto), nella 
      	compilazione e nell'invio delle pratiche amministrative rivolte ai comuni di 
      	Castel Bolognese, Cervia, Faenza, Russi, Solarolo, Unione dei Comuni della 
      	Bassa Romagna e Unione dei Comuni di Brisighella, Casola Valsenio e Riolo Terme;</li>
      	<li>che le attivit&agrave; di supporto amministrativo, ivi compresa l'eventuale elezione 
      	di domicilio elettronico presso questa Associazione / Agenzia / Studio Professionale 
      	/ Soggetto intermediario, sono svolte per conto dei soci / clienti a seguito di 
      	richiesta degli stessi;</li>
      	<li>di essere, pertanto, nelle condizioni di poter accreditare presso l'Associazione / 
      	Agenzia / Studio Professionale / Soggetto intermediario da me rappresentato all'utilizzo 
      	dei servizi on line dei comuni di 
      	Castel Bolognese, Cervia, Faenza, Russi, Solarolo, Unione dei Comuni della 
      	Bassa Romagna e Unione dei Comuni di Brisighella, Casola Valsenio e Riolo Terme.</li>
      </ul>
      </c:if>
      <br/>
      <br/>
<%--      
      Dati del Rappresentante Legale:<br/>
      Nome: <c:out value='${datiAutoCertMap["nomeRapprLegale"]}'/><br/>
      Cognome: <c:out value='${datiAutoCertMap["cognomeRapprLegale"]}'/> <br/>
      Codice Fiscale: <c:out value='${datiAutoCertMap["codiceFiscaleRapprLegale"]}'/> <br/>
      Data di nascita: <c:out value='${datiAutoCertMap["dataNascitaRapprLegale"]}'/> <br/>
      Luogo di Nascita: <c:out value='${datiAutoCertMap["luogoNascitaRapprLegale"]}'/> <br/>
      Provincia Nascita: <c:out value='${datiAutoCertMap["provinciaNascitaRapprLegale"]}'/> <br/>
      Indirizzo di residenza: <c:out value='${datiAutoCertMap["indirizzoResidenzaRapprLegale"]}'/> <br/>
      <br/>
      <br/>
      <br/>--%>
      <center>
        Esente da imposta di bollo ai sensi dell'art. 37 D.P.R. 28 dicenbre 2000, n. 445
      </center>
      <br/>
      <br/>
      <div class="right">
      <c:out value='${datiAutoCertMap["dataCorrente"]}'/>
      </div>

</div>
