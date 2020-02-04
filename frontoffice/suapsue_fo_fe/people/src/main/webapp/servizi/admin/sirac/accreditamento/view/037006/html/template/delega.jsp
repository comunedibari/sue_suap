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


 <div class="admin_sirac_autocert_title">
   Dichiarazione sostitutiva dell'Atto di Notoriet&agrave;<br>
   Modulo di Delega<br>
 </div>
 <div class="admin_sirac_autocert_subtitle">
   (Art. 47 D.P.R. 28 dicembre 2000, n. 445)<br>
 </div>

 <br><br><br><br>
 <div class="admin_sirac_autocert_body">
   Il sottoscritto <c:out value='${datiDelegaMap["cognomeDelegante"]}'/>  <c:out value='${datiDelegaMap["nomeDelegante"]}'/>, 
   <br/><br/>
   nato a <c:out value='${datiDelegaMap["luogoNascitaDelegante"]}'/> 
   il <c:out value='${datiDelegaMap["dataNascitaDelegante"]}'/>
   <br/><br/>
   codice fiscale <c:out value='${datiDelegaMap["codiceFiscaleDelegante"]}'/> 
   <br/><br/>
   residente a <c:out value='${datiDelegaMap["indirizzoResidenzaDelegante"]}'/>
   <br/><br/>
   in qualit&agrave; di rappresentante legale (<c:out value='${datiDelegaMap["qualificaIntermediario"]}'/>)
   dell'Agenzia di Intermediazione <c:out value='${datiDelegaMap["denominazione"]}'/>
   con sede in <c:out value='${datiDelegaMap["sedeLegale"]}'/>
    <c:if test='${not empty datiDelegaMap["codiceFiscaleIntermediario"]}'>
      , codice fiscale <c:out value='${datiDelegaMap["codiceFiscaleIntermediario"]}'/>
    </c:if>
    <c:if test='${not empty datiDelegaMap["partitaIvaIntermediario"]}'>
      , partita IVA <c:out value='${datiDelegaMap["partitaIvaIntermediario"]}'/>
    </c:if>
    .
   <br/><br/>
   
       consapevole che chiunque rilasci dichiarazioni mendaci &egrave; 
       punito ai sensi del codice penale e delle leggi speciali in materia, 
       ai sensi e per gli effetti dell'art. 46 D.P.R. n. 445/2000, 
       a conoscenza di quanto prescritto dall'art. 75 del D.P.R. 445/2000 
       sulla decadenza dei benefici eventualmente conseguenti al provvedimento 
       emanato sulla base di dichiarazioni non veritiere, 
       sotto la propria responsabilit&agrave; 
     <br/><br/><br/>
   </div>
  <div class="admin_sirac_autocert_title">DELEGA</div>
  <br/><br/><br/>
 <div class="admin_sirac_autocert_body">
    <c:out value='${datiDelegaMap["cognomeDelegato"]}'/> 
    <c:out value='${datiDelegaMap["nomeDelegato"]}'/> 
    <c:if test='${not empty datiDelegaMap["codiceFiscaleDelegato"]}'>
      , codice fiscale <c:out value='${datiDelegaMap["codiceFiscaleDelegato"]}'/>
    </c:if>
    <br/>
    ad operare per conto del sottoscritto nella fruizione dei servizi on-line di e-Government
    erogati da questo Comune. 
      <br/>
      <br/>
      <br/>
      <center>
        Esente da imposta di bollo ai sensi dell'art. 37 D.P.R. 28 dicenbre 2000, n. 445
      </center>
      <br/>
      <br/>
      <c:out value='${datiAutoCertMap["dataCorrente"]}'/>
    
 </div>
   