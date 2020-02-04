<%@ page contentType="text/html"%>
<%@ page pageEncoding="UTF-8"%>
<%@ page
  import="it.people.sirac.accr.beans.*,it.people.process.*,it.people.*,it.people.util.*,it.people.sirac.services.accr.*,it.people.sirac.web.forms.*,it.people.wrappers.*,it.people.fsl.servizi.admin.sirac.accreditamento.model.ProcessData,java.text.SimpleDateFormat,java.util.Date"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl"%>
<%-- JSTL --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>


<div class="admin_sirac_autocert_body" 
     style="text-transform:uppercase; text-align:center;">
  <c:out value='${datiAutoCertMap["qualifica"]}'/>
</div>
<br/>
<div class="admin_sirac_autocert_body">
      <c:if test='${not empty datiAutoCertMap["partitaIvaIntermediario"]}'>
        Partita IVA <c:out value='${datiAutoCertMap["partitaIvaIntermediario"]}'/>.
      </c:if>
      <%--
      <c:if test='${not empty datiAutoCertMap["sedeLegale"]}'>
         Sede legale in <c:out value='${datiAutoCertMap["sedeLegale"]}'/>.
      </c:if>
      
      <br/>
      --%>
      <c:if test='${not empty datiAutoCertMap["rifDocumentiUfficiali"]}'>
        Riferimenti ad atti o documenti ufficiali che attestano la qualifica: 
        <c:out value='${datiAutoCertMap["rifDocumentiUfficiali"]}'/>
        .
      </c:if>
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
