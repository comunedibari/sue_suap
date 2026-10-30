<%
String path = request.getContextPath();
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="it.wego.cross.dto.comunicazione.AnagraficaMinifiedDTO"%>

<c:if test="${immobile.idTipoSistemaCatastale == 1}">
    <c:set var="class_dato_catastale_tavolare" value="hidden"/>
    <c:set var="class_dato_catastale" value=""/>
</c:if>
<c:if test="${immobile.idTipoSistemaCatastale == 2}">
    <c:set var="class_dato_catastale_tavolare" value=""/>
    <c:set var="class_dato_catastale" value="hidden"/>
</c:if>
                    <!--  <c:if test="${immobile.provenienza eq 'CIVILIA'}"> sfondorosso</c:if> -->
<td id="TDdatoCatastale_${immobile.idImmobile}" class="DatiCatastaliElement">
    <div id="datoCatastale_${immobile.idImmobile}">
        <div class="circle <c:if test="${loop.last}">lastCircle</c:if> 
            <c:choose>
			  <c:when test="${immobile.provenienza eq 'CIVILIA'}">sfondorosso</c:when>
			  <c:when test="${immobile.provenienza eq 'DMS'}">sfondoverde</c:when>
			   <c:otherwise>sfondobianco</c:otherwise>
			</c:choose>">
              <div class="ctrlHolder">
                  <label class="required" style="display:inline">
                  <spring:message code="datiCatastali.catasto.title"/>
                </label>
                <a class="nascondiutente datoCatstale box-unlink-datoCatastale" box-unlink-datoCatastale" data-holder="${immobile.idImmobile}">
                   <img src="${path}/themes/default/images/icons/link_break.png" alt="<spring:message code="datiCatastali.button.eliminaCatastale"/>" title="<spring:message code="datiCatastali.button.eliminaCatastale"/>">
                    <spring:message code="datiCatastali.button.eliminaCatastale"/>
                </a>
            </div>
            <div class="ctrlHolder">
                <label class="required">
                    <spring:message code="pratica.comunicazione.dettaglio.immobile.tipocatasto"/>
                </label>
                <input class="input_anagrafica_disable tipoCatasto" type="text" disabled="" value="${immobile.desSistemaCatastale}">
            </div> 
            <div class="box_tipoUnita ctrlHolder ${class_dato_catastale}">
                <label class="required">
                    <spring:message code="pratica.comunicazione.dettaglio.immobile.tipoUnita"/>
                </label>
                <input class="input_anagrafica_disable tipoUnita" type="text" disabled="" value="${immobile.desTipoUnita}">
            </div>
            <div class="box_tipoParticella ctrlHolder ${class_dato_catastale_tavolare}">
                <label class="required">
                    <spring:message code="pratica.comunicazione.dettaglio.immobile.tipoParticella"/>
                </label>
                <input class="input_anagrafica_disable tipoParticella" type="text" disabled="" value="${immobile.desTipoParticella}">
            </div>                                    
            <div class="box_sezione ctrlHolder ${class_dato_catastale}">
                <label class="required">
                    <spring:message code="pratica.comunicazione.dettaglio.immobile.sezione"/>
                </label>
                <input class="input_anagrafica_disable sezione" type="text" disabled="" value="${immobile.sezione}">
            </div>
            <div class="box_comuneCensuario ctrlHolder ${class_dato_catastale_tavolare}">
                <label class="required">
                    <spring:message code="pratica.comunicazione.dettaglio.immobile.comuneCensuario"/>
                </label>
                <input class="input_anagrafica_disable comuneCensuario" type="text" disabled="" value="${immobile.comuneCensuario}">
            </div>
            <div class="box_foglio ctrlHolder ${class_dato_catastale}">
                <label class="required">
                    <spring:message code="pratica.comunicazione.dettaglio.immobile.foglio"/>
                </label>
                <input class="input_anagrafica_disable foglio" type="text" disabled="" value="${immobile.foglio}">
            </div>
            <div class="box_mappaleTavolare ctrlHolder ${class_dato_catastale_tavolare}">
                <label class="required">
                    <spring:message code="pratica.comunicazione.dettaglio.immobile.particella"/>
                </label>
                <input class="input_anagrafica_disable mappaleTavolare" type="text" disabled="" value="${immobile.mappale}">
            </div>                                    
            <div class="box_mappale ctrlHolder ${class_dato_catastale}">
                <label class="required">
                    <spring:message code="pratica.comunicazione.dettaglio.immobile.mappale"/>
                </label>
                <input class="input_anagrafica_disable mappale" type="text" disabled="" value="${immobile.mappale}">
            </div>
            <div class="box_estensioneParticella ctrlHolder ${class_dato_catastale_tavolare}">
                <label class="required">
                    <spring:message code="pratica.comunicazione.dettaglio.immobile.estensioneParticella"/>
                </label>
                <input class="input_anagrafica_disable estensioneParticella" type="text" disabled="" value="${immobile.estensioneParticella}">
            </div>                                    
            <div class="box_subalterno ctrlHolder ${class_dato_catastale}">
                <label class="required">
                    <spring:message code="pratica.comunicazione.dettaglio.immobile.subalterno"/>
                </label>
                <input class="input_anagrafica_disable subalterno" type="text" disabled="" value="${immobile.subalterno}">
            </div>
            <div class="box_subalternoTavolare ctrlHolder ${class_dato_catastale_tavolare}">
                <label class="required">
                    <spring:message code="pratica.comunicazione.dettaglio.immobile.subalterno"/>
                </label>
                <input class="input_anagrafica_disable subalternoTavolare" type="text" disabled="" value="${immobile.subalterno}">
            </div>
              <div class="box_categoria ctrlHolder ${class_dato_catastale_catastale}">
                <label class="required">
                    <spring:message code="pratica.comunicazione.dettaglio.immobile.categoria"/>
                </label>
                <input class="input_anagrafica_disable categoria" type="text" disabled="" value="${immobile.categoria}">
            </div>
            <c:if test="${! empty immobile.urlCatasto}">
                <a href="${immobile.urlCatasto}" target="_blank">
                    <img src="${path}/themes/default/images/icons/magnifier.png" alt="<spring:message code="pratica.comunicazione.dettaglio.immobile.linkgeolocale"/>" title="<spring:message code="pratica.comunicazione.dettaglio.immobile.linkgeolocale"/>">
                    <spring:message code="pratica.comunicazione.dettaglio.immobile.linkgeolocale"/>
                </a>
            </c:if>
            
            <c:if test="${immobile.getProvenienza() == null}">
	            <div class="datoCatstale cerca_lente_rosso showdetail-datoCatastale" data-holder="${immobile.idImmobile}"><spring:message code="pratica.comunicazione.dettaglio.immobile.button"/></div>
  			</c:if>
        </div>
    </div>
</td>