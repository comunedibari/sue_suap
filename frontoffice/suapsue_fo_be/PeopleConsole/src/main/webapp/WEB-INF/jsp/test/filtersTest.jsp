<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="pcfn" uri="/WEB-INF/peopleconsole-fn.tld" %>
<html>
	<head>
		<title>Test filters</title>
	</head>
	<body>
		<form:form>
			<p>Test filters.</p>
			<p>Test.</p>
			<%@ include file="/WEB-INF/jsp/includes/filters.jsp"%>
			<%@ include file="/WEB-INF/jsp/includes/listHolderTable.jsp"%>
			<p>Test.</p>
			<p>Test.</p>
		</form:form>
	</body>
</html>