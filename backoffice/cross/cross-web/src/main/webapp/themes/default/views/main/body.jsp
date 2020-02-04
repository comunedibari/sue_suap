<%-- 
    Document   : body
    Created on : 25-gen-2012, 16.48.33
    Author     : CS
--%>
<%@page import="java.util.Enumeration"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div id="layout-column_column-2" class="portlet-dropzone portlet-column-content">
    <%-- <div id="layout-column_column-2" class="portlet-dropzone portlet-column-content" style="margin-left:203px;"> --%>
    <div class="portlet">

        <div class="portlet-content">
            <div class="portlet-content-container">
                <div class="portlet-body">
                    <%-- <h3 class="short"> <tiles:insertAttribute name="body_title" /> </h3> --%>
                    <tiles:insertAttribute name="body_header" />
                     
                     <!-- Aggiunto il 25/01/2016 -->                  
				    <c:if test="${!empty avvisiList }">
					    <div class="message warning"><br/>
						   <c:forEach items="${avvisiList}" var="avviso">
							   <div style="font-weight:bold;text-decoration:important;font-size: 18px;line-height: 1.5">
<%-- 								   <span class="value"><fmt:formatDate pattern="dd/MM/yyyy" value="${avviso.scadenza}" />: </span> --%>
								    <span>${avviso.testo}</span>
							   </div>
							   <br/>
						   </c:forEach>
						 </div> 			    
				    </c:if>
                   
                    <tiles:insertAttribute name="body_center" />
                    <tiles:insertAttribute name="body_footer" />



                    <div style="clear:both;"></div>
                                    

                </div>
            </div>
        </div>
    </div>
</div>