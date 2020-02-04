<%
    String path = request.getContextPath();
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<tiles:insertAttribute name="body_error" />
<form id="creazioneEventoForm" action="#" class="uniForm inlineLabels comunicazione" method="post" enctype="multipart/form-data">
    <div class="inlineLabels">
        <h2 class="short" style="text-align:center">HELLO WORLD</h2>
    </div>
</form>