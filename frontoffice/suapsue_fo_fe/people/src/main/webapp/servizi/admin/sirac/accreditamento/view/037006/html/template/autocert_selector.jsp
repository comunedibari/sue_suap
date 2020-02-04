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
  <c:when test="${datiAutoCertMap.tipoQualifica =='Professionista'}">
    <c:set scope="request" var="dichType" value="DichiarazioneSostitutivaCertificazione" />
    <c:set scope="request" var="autoCertType" value="qualifica Professionale" />
    <c:import url="./servizi/admin/sirac/accreditamento/view/default/html/template/autocert_header.jsp" />
    <c:import url="./servizi/admin/sirac/accreditamento/view/default/html/template/autocert_professionista.jsp" />
  </c:when>
  <c:when test="${datiAutoCertMap.tipoQualifica =='Intermediario'}">
  	<c:choose>
  		<c:when test="${datiAutoCertMap.descrizioneQualifica =='Rappresentante Associazione Categoria'}">
		    <c:set scope="request" var="dichType" value="DichiarazioneSostitutivaCertificazione" />
		    <c:set scope="request" var="autoCertType" value="rappresentante di agenzia di intermediazione / soggetto intermediario" />
		    <c:import url="./servizi/admin/sirac/accreditamento/view/default/html/template/autocert_header_rac.jsp" />
		    <c:import url="./servizi/admin/sirac/accreditamento/view/default/html/template/autocert_intermediario_rac.jsp" />
  		</c:when>
  		<c:otherwise>
		    <c:set scope="request" var="dichType" value="DichiarazioneSostitutivaCertificazione" />
		    <c:set scope="request" var="autoCertType" value="rappresentante di agenzia di intermediazione" />
		    <c:import url="./servizi/admin/sirac/accreditamento/view/default/html/template/autocert_header.jsp" />
		    <c:import url="./servizi/admin/sirac/accreditamento/view/default/html/template/autocert_intermediario.jsp" />
  		</c:otherwise>
  	</c:choose>
  </c:when>
  <c:when test="${datiAutoCertMap.tipoQualifica =='Rappresentante Persona Giuridica'}">
    <c:set scope="request" var="dichType" value="DichiarazioneSostitutivaCertificazione" />
    <c:set scope="request" var="autoCertType" value="rappresentante di persona giuridica" />
    <c:import url="./servizi/admin/sirac/accreditamento/view/default/html/template/autocert_header.jsp" />
    <c:import url="./servizi/admin/sirac/accreditamento/view/default/html/template/autocert_rappresentantePG.jsp" />
  </c:when>
  <c:otherwise>
    ERRORE. TIPO QUALIFICA SCONOSCIUTA: <c:out value='${datiAutoCertMap.tipoQualifica}' />
  </c:otherwise>
</c:choose>
