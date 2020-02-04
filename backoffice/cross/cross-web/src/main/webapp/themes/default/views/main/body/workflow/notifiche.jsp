<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!--<script language="javascript">
    var path = '${path}';
</script>-->

<link rel="stylesheet" type="text/css" href="<c:url value="/themes/default/css/core.css"/>"  media="all"/>
<script  type="text/javascript" src="<c:url value="/javascript/cross/notifiche.js"/>"></script>

<tiles:insertAttribute name="operazioneRiuscitaAjax" />

<div id="notifiche">
    <tiles:insertAttribute name="body_error" />
    <div class="notificheTableContainer">
        <table id="notificheTable"><tr><td/></tr></table> 
        <div id="notifichePager"></div> 
    </div>
</div>

