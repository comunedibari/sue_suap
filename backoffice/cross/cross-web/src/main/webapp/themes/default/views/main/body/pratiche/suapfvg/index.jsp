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
                <ul>
                    <li class="active"><a href="#frame1"><spring:message code="suapfvg.upload.title"/></a></li>
                </ul>
                <div class="frames">
                    <div class="frame active" id="frame1">
                        <form id="uploadPraticaSuapFvg" method="post" enctype="multipart/form-data" class="uniForm inlineLabels comunicazione" action="${path}/suapfvg/upload.htm">  
                            <div class="ctrlHolder">
                                <spring:message code="suapfvg.upload.description"/>
                            </div>

                            <div class="ctrlHolder">
                                <span>
                                <label style="cursor:default" class="required"><spring:message code="suapfvg.upload.label"/></label> 
                                </span>
                                <input id="filePratica" type="file" name="filePratica" accept="application/pdf, application/pkcs7-mime, application/x-pkcs7-mime"/>  <br>
                                
                                </div>
                            <div class="buttonHolder">
                                <button value="Carica" class="primaryAction" name="submit" type="submit"><spring:message code="suapfvg.upload.button"/></button>
                            </div>
                        </form>  
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

