<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<form:hidden path="inviaEmail" value="false"/>
<div class="ctrlHolder dettaglio_liv_0">
    <div class="mittenti">
        <c:choose>
            <c:when test="${processo_evento.flgVisualizzaProcedimenti=='S'}">
                <tiles:insertAttribute name="comunicazione_procedimenti" />
            </c:when>
            <c:otherwise>
                <script>
                    $(document).ready(function() {
                    var url = '${path}/search/destinatari.htm?id_pratica=' + idPratica + '&id_evento=' + idEvento;
                            $("#mittenti_a").tokenInput(url, {
                    theme: 'facebook',
                            queryParam: 'query',
                    <c:if test="${processo_evento.maxDestinatari != null && processo_evento.maxDestinatari gt 0}">
                    tokenLimit: '${processo_evento.maxDestinatari}',
                    </c:if>
                    prePopulate: [
                    <c:forEach items="${mittenti}" begin="0" var="mittente" varStatus="status">
                    {
                    id: '${mittente.id}',
                            description: '${mittente.description}',
                            email: '${mittente.email}',
                            type: '${mittente.type}'
                    }${not status.last ? ',' : ''}
                    </c:forEach>
                    ],
                            propertyToSearch: "description",
                            resultsFormatter: function(item) {
                            return "<li>" +
                                    "<div style='display: inline-block; padding-left: 10px;'>\n\
                                        <div class='full_name'>" + item.description + " (" + item.type + ")</div>\n\
            <div class='email'>" + item.email + "</div></div></li>"
                            }
                    });
                    });
                </script>
                <form:input class="textInput" path="mittentiIds" id="mittenti_a"/>

            </c:otherwise>
        </c:choose>
    </div>
    <c:if test="${processo_evento.flgProtocollazione == 'S'}">
        <tiles:insertAttribute name="comunicazione_protocollo_manuale" />
    </c:if>
</div>