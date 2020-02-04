<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="portlet"          uri="http://java.sun.com/portlet_2_0"         %>
<%@ taglib prefix="c"                uri="http://java.sun.com/jsp/jstl/core"       %>
<%@ taglib prefix="fn"               uri="http://java.sun.com/jsp/jstl/functions"  %>
<%@ taglib prefix="fmt"              uri="http://java.sun.com/jsp/jstl/fmt"        %>
<%@ taglib prefix="liferay-portlet"  uri="http://liferay.com/tld/portlet" %>
<%@ taglib prefix="liferay-security" uri="http://liferay.com/tld/security" %>
<%@ taglib prefix="liferay-theme"    uri="http://liferay.com/tld/theme" %>
<%@ taglib prefix="liferay-ui"       uri="http://liferay.com/tld/ui" %>
<%@ taglib prefix="liferay-util"     uri="http://liferay.com/tld/util" %>


<%@ page import="com.liferay.portal.kernel.dao.search.DisplayTerms" %>
<%@ page import="com.liferay.portal.kernel.dao.search.ResultRow" %>
<%@ page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>
<%@ page import="com.liferay.portal.kernel.util.Validator" %>
<%@ page import="com.liferay.portal.kernel.util.WebKeys" %>
<%@ page import="com.liferay.portal.theme.ThemeDisplay" %>
<%@ page import="com.liferay.portal.kernel.util.ListUtil" %>
<%@ page import="com.liferay.portal.util.PortalUtil"%>

<%@ page import="java.util.List" %>	
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Collection" %>	

<%@ page import=" it.exprivia.pal.avbari.suapsue.dto.Pratica" %>

<%@ page import="javax.portlet.PortletURL" %>

<portlet:defineObjects/>

<liferay-theme:defineObjects/>