<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<tiles:insertAttribute name="body_error" />



<div>
    <div  class="uniForm ">

        <div class="sidebar_auto">
            <div class="page-control" data-role="page-control">
                <span class="menu-pull"></span> 
                <div class="menu-pull-bar"></div>
                <!-- Etichette cartelle -->
                <ul>
                    <li class="active"><a href="#frame1"><spring:message code="comunica.upload.title"/></a></li>
                </ul>
                <!-- Contenuto cartelle -->
                <div class="frames">
                    <div class="frame active" id="frame1">
                        <form id="uploadPraticaComunica" method="post" enctype="multipart/form-data" class="uniForm inlineLabels comunicazione" action="${path}/comunica/upload.htm">  
                            <div class="ctrlHolder">
                                <spring:message code="comunica.upload.description"/>
                            </div>

                            <div class="ctrlHolder">
                                <span>
                                <label style="cursor:default" class="required"><spring:message code="comunica.upload.label"/></label> 
                                </span>
                                <input id="filePratica" type="file" name="filePratica" accept="application/xml, text/xml"/>  <br>
                                
                                </div>
                            <div class="buttonHolder">
                                <button value="Carica" class="primaryAction" name="submit" type="submit"><spring:message code="comunica.upload.button"/></button>
                            </div>
                        </form>  
                    </div>
                </div>
                <div class="frames">
                    <div class="frame active" id="frame1">
                        <form id="uploadPraticaComunica" method="post" enctype="multipart/form-data" class="uniForm inlineLabels comunicazione" action="${path}/comunica/uploadSue.htm">  
                            <div class="ctrlHolder">
                                <spring:message code="comunica.uploadSue.description"/>
                            </div>

                            <div class="ctrlHolder">
                                <span>
                                <label style="cursor:default" class="required"><spring:message code="comunica.uploadSue.label"/></label> 
                                </span>
                                <input id="filePratica" type="file" name="filePratica" accept="application/xml, text/xml"/>  <br>
                                
                                </div>
                            <div class="buttonHolder">
                                <button value="Carica" class="primaryAction" name="submit" type="submit"><spring:message code="comunica.upload.button"/></button>
                            </div>
                        </form>  
                    </div>
            </div>
        </div>
    </div>
</div>

