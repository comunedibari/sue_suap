<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/html/common/init.jsp" %>

<liferay-ui:error key="errore.comuni" message="errore.comuni" />
<liferay-ui:error key="errore.generico" message="errore.generico" />

<div class="mainDiv">
	<div class="serviziPerComune">
	<div class="titoletto">Servizi per comune</div>
		<ul><c:forEach items="${sportelliAttiviList}" var="sportello">
			<li><a href="${sportello.urlPagina}"><i class="fa fa-angle-double-right"></i> ${sportello.denominazione}</a></li>
			</c:forEach>
		</ul>
	</div>
</div>