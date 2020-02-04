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

<c:choose>
  <c:when test='${dichType =="DichiarazioneSostitutivaCertificazione"}'>
         <div class="admin_sirac_autocert_title">
           Dichiarazione sostitutiva della certificazione<br>
           di <c:out value='${autoCertType}'/><br>
         </div>
         <div class="admin_sirac_autocert_subtitle">
           (Art. 46 D.P.R. 28 dicembre 2000, n. 445)<br>
         </div>
  </c:when>
  <c:when test='${dichType =="DichiarazioneSostitutivaAttoNotorieta"}'>
         <div class="admin_sirac_autocert_title">
           Dichiarazione sostitutiva dell'Atto di Notoriet&agrave;<br>
           Modulo di Delega<br>
         </div>
         <div class="admin_sirac_autocert_subtitle">
           (Art. 47 D.P.R. 28 dicembre 2000, n. 445)<br>
         </div>
  </c:when>
  <c:otherwise>
  </c:otherwise>
</c:choose>
         <br><br><br><br>
         <div class="admin_sirac_autocert_body">
           Il sottoscritto <c:out value='${datiAutoCertMap["nome"]}'/>  <c:out value='${datiAutoCertMap["cognome"]}' /> 
           <br/><br/>
           nato a <c:out value='${datiAutoCertMap["luogoNascita"]}'/> 
           il <c:out value='${datiAutoCertMap["dataNascita"]}'/>
           <br/><br/>
           codice Fiscale <c:out value='${datiAutoCertMap["codiceFiscale"]}'/>
           <br/><br/>
           residente a <c:out value='${datiAutoCertMap["indirizzoResidenza"]}'/>
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
        <div class="admin_sirac_autocert_title">DICHIARA</div>
        <br/>
        <div class=admin_sirac_autocert_body 
             style="text-transform:uppercase; text-align:center;">
          <c:choose>
            <c:when test='${dichType =="DichiarazioneSostitutivaCertificazione"}'>
                di essere in possesso della qualifica di
            </c:when>
            <c:when test='${dichType =="DichiarazioneSostitutivaAttoNotorieta"}'>
                di delegare
            </c:when>
            <c:otherwise>
            </c:otherwise>
          </c:choose>
        </div>
        <br/>
         