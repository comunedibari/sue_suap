<%
    String path = request.getContextPath();
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="destinatari">
    <script>
        $(document).ready(function() {
            var url = '<%=path%>/search/destinatari.htm';
            $( "#mittenti_a" ).tokenInput(url, {
                theme: 'facebook',
                queryParam: 'query',
                prePopulate: [

                    ],
                    propertyToSearch: "description",
                    resultsFormatter: function(item){ 
                        return "<li>" + 
                            "<div style='display: inline-block; padding-left: 10px;'>\n\
                            <div class='full_name'>" + item.description + " (" + item.type + ")</div>\n\
<div class='email'>" + item.email +"</div></div></li>" }
                });
            });
    </script>

    <div class="mittenti">
        <input class="textInput" id="mittenti_a" name="mittentiIds" />
    </div>
</div>