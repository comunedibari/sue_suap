<%-- 
    Document   : apertura
    Created on : 05-Mar-2013, 15:47:13
    Author     : CS
--%>

<%
    String path = request.getContextPath();
    String url = path + "/pratiche/caricamento/daNumeroProtocolloSubmit.htm";
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<tiles:insertAttribute name="body_error" />
<script type="text/javascript">
    $(function() {
        $('button[name="submit"]').click(function(){
            $.isLoading({ text: "Caricamento..." });
        });    
    });
    
</script>
<div>
    <div  class="uniForm ">

        <div class="sidebar_auto">
            <div class="page-control" data-role="page-control">
                <span class="menu-pull"></span> 
                <div class="menu-pull-bar"></div>
                <!-- Etichette cartelle -->
                <ul>
                    <li class="active"><a href="#frame1"><spring:message code="protocollo.ricerca.danumero.title"/></a></li>
                </ul>
                <!-- Contenuto cartelle -->
                <div class="frames">
                    <div class="frame active" id="frame1">
                        <div><spring:message code="protocollo.ricerca.danumero.message"/></div>
                        <form action="<%=url%>" class="uniForm inlineLabels" method="post">

                            <div>
                                <div class="inlineLabels">
                                    <div class="ctrlHolder">
                                        <label style="cursor:default" for="numeroProtocollo" class="required"><spring:message code="protocollo.ricerca.danumero.numeroprotocollo"/></label>
                                        <input type="text" id="numeroProtocollo" name="numeroProtocollo" />
                                    </div>     
                                </div>
                            </div>

                            <div class="buttonHolder">
                                <a href="<%=path%>/index.htm" class="secondaryAction">&larr; Indietro</a>
                                <button type="submit" name="submit" class="primaryAction" value="<spring:message code="protocollo.ricerca.danumero.button"/>"><spring:message code="protocollo.ricerca.danumero.button"/></button>
                            </div>

                        </form>

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

