<%
    String path = request.getContextPath();
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="ctrlHolder">
    <script>
        $(document).ready(function() {
            var url = '<%=path%>/search/destinatari.htm';
            $( "#destinatari_a" ).tokenInput(url, {
                theme: 'facebook',
                queryParam: 'query',
                onAdd : function(destinatario){
                    if(destinatario.email == "<spring:message code="evento.posta.ordinaria"/>" )
                    {
                        gestionePostaOrdinari(destinatario);
                    }
                },
                prePopulate: [
            <c:set var="i" value="0"/>
        <c:forEach items="${comunicazione.destinatari}" begin="0" var="destinatario" varStatus="status">
                        {
                            id: '${destinatario.id}', 
                            description: '${destinatario.description}',
                            email: '${destinatario.email}',
                            type: '${destinatario.type}'
                        }${not status.last ? ',' : ''}
        <c:set var="i" value="${i+1}"/>
        </c:forEach>
                    ],
                    propertyToSearch: "description",
                    tokenFormatter: function(item){ 
                        return "<li>" + 
                            "<div style='display: inline-block; padding-left: 10px;'>\n\
                            <div class='full_name'>" + item.description + " (" + item.type + ")</div>\n\
                            <div class='email'>" + item.email +"</div></div></li>" },
                    resultsFormatter :function(item){ 
                        return "<li>" + 
                            "<div style='display: inline-block; padding-left: 10px;'>\n\
                            <div class='full_name'>" + item.description + " (" + item.email + ")</div>\n\</div></li>" }
                });
            });
    </script>

    <label for="destinatari_a" class="required">
        <spring:message code="pratica.comunicazione.evento.destinatari"/>
    </label>
    <div class="destinatari">
        <input class="textInput" id="destinatari_a" name="destinatariIds" />
    </div>
</div>