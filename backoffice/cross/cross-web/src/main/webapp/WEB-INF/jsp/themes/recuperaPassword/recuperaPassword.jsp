<%@page import="com.sun.xml.rpc.processor.model.Request"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page import="it.wego.cross.utils.Utils"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
<head>
<title>CAS &#8211; Central Authentication Service</title>


<c:url var="casCss" value="/themes/default/css/recuperaPassword/css/cas.css"></c:url>
<c:url var="login" value=""></c:url>
<link type="text/css" rel="stylesheet" href="${casCss}" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="icon" href="/cas/favicon.ico" type="image/x-icon" />
</head>
<body id="cas" class="fl-theme-iphone">
	<div class="flc-screenNavigator-view-container">
		<div class="fl-screenNavigator-view">
			<div id="header" class="flc-screenNavigator-navbar fl-navbar fl-table">
				<h1 id="company-name">Jasig</h1>			
				<h1 id="app-name" class="fl-table-cell">Central AuthenticationService (CAS)</h1>
			</div>
			<div id="content" class="fl-screenNavigator-scroll-container">

				<c:if test="${error != null and not empty error.messages}">
					<div id="msg" class="errors">
						<ul>
							<c:forEach var="messaggio" begin="0" items="${error.messages}">
								<li>${messaggio}</li>
							</c:forEach>
						</ul>
					</div>
				</c:if>

					<c:if test="${success !=null and not empty success.messages}">
							<div  class="success" >
								<ul>
									<c:forEach var="messaggio" begin="0" items="${success.messages}">
										<li style="display:block;"  ><h1 align="center">${messaggio}</h1></li>
									</c:forEach>
									<br/><br/><br/>
									<li style="display:block;"  ><h1 align="center"><a href="../${login}">Torna al login</a></span></h1></li>
								</ul>
							</div>
						
				    </c:if>
					<c:if test="${null==success or empty success.messages}">
						<div class="box fl-panel" id="login">

							<c:url var="actionUrl" value="/utenti/recuperaPassword.htm"></c:url>

							<form:form id="fm1" action="${actionUrl}"
								cssClass="fm-v clearfix" method="post" modelAttribute="utente">

								<!-- Benvenuti al Central Authentication Service (CAS). -->
								<h2>Inserisci username e email</h2>

								<input type="hidden" name="action" value="recuperaPassword" />
								<div class="row fl-controls-left">
									<label for="username" class="fl-label"><span
										class="accesskey">U</span>sername:</label> <input id="username"
										name="username" class="required" tabindex="1" type="text"
										value="" size="25" />
									<p>
										<spring:message code="utenti.campo.obbligatorio" />
									</p>
								</div>
								<div class="row fl-controls-left">
									<label for="email" class="fl-label"><span
										class="accesskey">E</span>mail:</label> <input id="email" name="email"
										class="required" tabindex="2" type="text" size="25" />
									<p>
										<spring:message code="utenti.campo.obbligatorio" />
									</p>
								</div>


								<div class="row btn-row">
									<input class="btn-submit" name="submit" value="Recupera password" tabindex="4" type="submit" />
									<!-- 	                        <input class="btn-reset" name="reset" accesskey="c" value="ANNULLA" tabindex="5" type="reset" /> -->
								</div>
								<a href="../${login}">Torna indietro</a></span>
							</form:form>
						</div>
					
					</c:if>
			</div>

		</div>
		<div id="footer"
			class="fl-panel fl-note fl-bevel-white fl-font-size-80">
			<a id="jasig" href="http://www.jasig.org/"
				title="go to Jasig home page"></a>
			<div id="copyright">
				<p>Copyright © 2005 - 2012 Jasig, Inc. All rights reserved.</p>
				<p>
					Powered by <a href="http://www.jasig.org/cas">Jasig Central
						Authentication Service 3.5.2.1</a>
				</p>
			</div>
		</div>
	</div>
	<script type="text/javascript"
		src="<c:url value="/javascript/recuperaPassword/jquery-1.4.2.min.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/javascript/recuperaPassword/jquery-ui-1.8.5.min.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/javascript/recuperaPassword/cas.js"/>"></script>
</body>
</html>

