<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

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
                            $("#destinatari_a").tokenInput(url, {
                    theme: 'facebook',
                            queryParam: 'query',
                    <c:if test="${processo_evento.maxDestinatari != null && processo_evento.maxDestinatari > 0}">
                    tokenLimit: '${processo_evento.maxDestinatari}',
                    </c:if>
                    onAdd : function(destinatario){
                    if (destinatario.email == "<spring:message code="evento.posta.ordinaria"/>")
                    {
                    gestionePostaOrdinari(destinatario);
                    }
                    },
                            prePopulate: [
                    <c:forEach items="${destinatari}" begin="0" var="destinatario" varStatus="status">
                            {
                            id: '${destinatario.id}',
                                    description: '${destinatario.description}',
                                    email: '${destinatario.email}',
                                    type: '${destinatario.type}'
                            }${not status.last ? ',' : ''}
                    </c:forEach>
                            ],
                            propertyToSearch: "description",
                            tokenFormatter: function(item){
                            return "<li>" +
                                    "<div style='display: inline-block; padding-left: 10px;'>\n\
                                        <div class='full_name'>" + item.description + " (" + item.type + ")</div>\n\
                                        <div class='email'>" + item.email + "</div></div></li>" },
                            resultsFormatter :function(item){
                            return "<li>" +
                                    "<div style='display: inline-block; padding-left: 10px;'>\n\
                                        <div class='full_name'>" + item.description + " (" + item.email + ")</div>\n\</div></li>" }
                    });
                    });
                </script>

                <form:label path="destinatariIds" class="required"><spring:message code="pratica.comunicazione.evento.destinatari"/></form:label>

                <form:input class="textInput" path="destinatariIds" id="destinatari_a"/>
            </c:otherwise>
        </c:choose>
    </div>        
</div>