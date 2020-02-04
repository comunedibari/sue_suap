<%@ page language="java" contentType="text/html" %>
<%-- JSTL --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<%-- meta tags --%>
<c:set var="it_people_meta" scope="application">
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
    <meta http-equiv="Cache-Control" content="max-age=0" />
	<meta http-equiv="Cache-Control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>
	<meta http-equiv="Expires" content="Tue, 01 Jan 1980 1:00:00 GMT"/>
	<meta http-equiv="Pragma" content="no-cache"/>
</c:set>
<%-- foglio di stile --%>
<c:set var="it_people_css" scope="application">
    <style type="text/css" media="all">@import "<c:url value="/css/sirac.css"/>";</style>
	<!--<link rel="stylesheet" href="<c:url value="/css/style.css"/>" type="text/css" />-->
</c:set>

<%-- title globale --%>
<c:set var="it_people_pageTitle" value="PEOPLE SIRAC" scope="application"/>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
                      "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
                     
