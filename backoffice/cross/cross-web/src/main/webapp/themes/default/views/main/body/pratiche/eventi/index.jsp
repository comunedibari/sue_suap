<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<tiles:insertAttribute name="body_error" />
<form action="${path}/pratica/evento/crea.htm" class="uniForm inlineLabels comunicazione" method="post">

    <div class="uniForm uniform2">

        <div class="content_sidebar">	
            <div class="sidebar_left">

                <h3><spring:message code="pratica.comunicazione.dettaglio.identificativo"/> <strong>${pratica.identificativoPratica}</strong></h3>

                <div class="sidebar_elemento">

                    ${pratica.oggettoPratica}
                    <p><strong><spring:message code="pratica.comunicazione.evento.pratica.numero"/>:</strong>
                        <c:choose>
                            <c:when test="${not empty pratica.protocollo and not empty pratica.annoRiferimento}">
                                ${pratica.protocollo}/${pratica.annoRiferimento}
                            </c:when>
                            <c:otherwise>
                                <spring:message code="evento.protocollo.empty"/>
                            </c:otherwise>
                        </c:choose>
                    </p>
                    <p><strong><spring:message code="pratica.comunicazione.evento.pratica.dataricezione"/>:</strong> <fmt:formatDate pattern="dd/MM/yyyy" value="${pratica.dataRicezione}" /></p>
                    <p><strong><spring:message code="pratica.comunicazione.evento.pratica.stato"/>:</strong> ${pratica.idStatoPratica.descrizione}</p>


                    <c:choose>
                        <c:when test="${not empty pratica.idModello}">
                            <p style="margin-top:20px;">
                                <a href="${path}/download/pratica.htm" class="scarica" target="_blank">
                                    <spring:message code="pratica.comunicazione.scaricaIstanza"/>
                                </a>
                            </p>
                        </c:when>
                        <c:otherwise>
                            <span class="value"><spring:message code="pratica.comunicazione.evento.pratica.nofile"/></span>
                        </c:otherwise>
                    </c:choose>


                </div>

            </div>

            <div class="sidebar_auto">


                <div class="page-control" data-role="page-control">
                    <span class="menu-pull"></span> 
                    <div class="menu-pull-bar"></div>
                    <!-- Etichette cartelle -->
                    <ul>
                        <li class="active"><a href="#frame1"><spring:message code="pratica.comunicazione.evento.nuovo"/></a></li>
                    </ul>
                    <!-- Contenuto cartelle -->
                    <div class="frames">
                        <div class="frame active" id="frame1">

                            <div class="inlineLabels">

                                <div>
                                    <div class="ctrlHolder"> 
                                        <label for="id_evento" class="required"><spring:message code="pratica.comunicazione.evento.pratica.scegli"/></label>
                                        <select id="id_evento" name="id_evento">
                                            <c:forEach items="${eventi}" begin="0"  var="evento">
                                                <option value="${evento.idEvento}">${evento.descrizione}
                                                    <c:if test="${evento.statoPost != null}">
                                                        &nbsp;(${evento.statoPost.descrizione})
                                                    </c:if>
                                                </option>
                                            </c:forEach>
                                        </select>

                                        <input type="hidden" name="id_pratica" value="${pratica.idPratica}" />
                                        <input type="hidden" name="verso" value="${verso}" />
                                        <input type="hidden" name="id_protocollo" value="${id_protocollo}" />

                                    </div>    
                                </div>


                            </div>
                        </div>
                        <!-- fine cartelle -->
                    </div>
                    <div class="buttonHolder">
                        <a href="${path}/pratiche/dettaglio.htm?id_pratica=${pratica.idPratica}&currentTab=frame7" class="secondaryAction">&larr; <spring:message code="pratica.button.indietro"/></a>
                        <button type="submit" class="primaryAction"><spring:message code="pratica.comunicazione.evento.crea"/></button>
                    </div>
                </div>
                <div class="clear"></div>
            </div>




        </div>

</form>
