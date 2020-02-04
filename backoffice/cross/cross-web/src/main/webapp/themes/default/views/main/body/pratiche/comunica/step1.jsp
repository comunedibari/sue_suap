<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String url = path + "/comunica/salva.htm";
    String back = path + "/comunica/index.htm";
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<tiles:insertAttribute name="body_error" />
<h2 class="short" style="text-align:left"><spring:message code="comunica.riepilogo.step1"/></h2>
<div class="content_sidebar">	
    <div class="sidebar_center">

        <form:form method="post" enctype="multipart/form-data" commandName="comunica" action="<%= url%>" class="uniForm inlineLabels comunicazione">
            <div class="page-control" data-role="page-control">
                <span class="menu-pull"></span> 
                <div class="menu-pull-bar"></div>
                <ul>
                    <li class="active"><a href="#frame1"><spring:message code="comunica.riepilogo.title"/></a></li>
                    <li><a href="#frame2"><spring:message code="comunica.impresa.title"/></a></li>
                    <li><a href="#frame3"><spring:message code="comunica.legrap.title"/></a></li>
                    <li><a href="#frame4"><spring:message code="comunica.dichiarante.title"/></a></li>
                    <li><a href="#frame5"><spring:message code="comunica.allegti.title"/></a></li>
                </ul>
                <div class="frames">
                    <div class="frame active" id="frame1">
                        <tiles:insertAttribute name="comunica_riepilogo" />
                    </div>
                    <div class="frame" id="frame2">
                        <tiles:insertAttribute name="comunica_impresa" />
                    </div>
                    <div class="frame" id="frame3">
                        <tiles:insertAttribute name="comunica_legale_rappresentante" />
                    </div>
                    <div class="frame" id="frame4">
                        <tiles:insertAttribute name="comunica_dichiarante" />
                    </div>
                    <div class="frame" id="frame5">                        
                        <tiles:insertAttribute name="comunica_allegati" />
                    </div>
                </div>
            </div>        
            <div class="buttonHolder">
                <a href="<%= back %>" class="secondaryAction">&larr; <spring:message code="comunica.button.back"/></a>
                <button value="<spring:message code="comunica.button.submit"/>" class="primaryAction" name="submit" type="submit"><spring:message code="comunica.button.submit"/></button>
            </div>
        </form:form>

    </div>
    <div class="clear"></div>
</div>