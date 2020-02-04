<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@ page import="it.people.util.NavigatorHelper, it.people.IStep, java.util.Iterator,
				 java.util.ArrayList, it.people.Activity, it.people.ActivityState,
				 java.util.StringTokenizer, it.people.City, it.people.core.Logger,
				 it.people.core.CategoryManager, it.people.core.PplUserData,
				 it.people.content.Category, it.people.error.errorMessage,
				 it.people.error.MessagesFactory, it.people.core.PeopleContext,
                 it.people.content.CategoryImpl, it.people.core.persistence.exception.peopleException,
                 it.people.util.debug.Debugger, java.util.Collection,
                 it.people.util.*, it.people.*, it.people.layout.*"%>
                 
<%@ taglib uri="/WEB-INF/people.tld" prefix="people" %>
<%@ taglib uri="/WEB-INF/app.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<jsp:useBean id="pplProcess"   scope="session" type="it.people.process.AbstractPplProcess" />
<jsp:useBean id="City"         scope="session" type="it.people.City" />
<jsp:useBean id="ConcreteView" scope="request" type="it.people.process.view.ConcreteView" />
<jsp:useBean id="CurrentStep"  scope="request" type="it.people.Step" />
<jsp:useBean id="CurrentActivity" scope="request" type="it.people.Activity" />


<%
    boolean hideRightBtn = false;
    boolean hideLeftBtn = false;    
    
    /*
	Questi due vettori vengono utilizzati da genericFooter e genericaSave per 
	capire quando un bottone deve essere visualizzato oppure nascosto.
	Questo comportamento "sovrascrive" il comportamento normale dovuto al flusso
	del procedimento.
	Se nelle singole pagine jsp dei singoli step, viene aggiunta una stringa nell'array
	bottoniVisibili, questo bottone diviene sempre visibile.
    */
    
    ArrayList bottoniVisibili=new ArrayList(); 
    ArrayList bottoniNascosti=new ArrayList();
    request.setAttribute("bottoniVisibili", bottoniVisibili);
    request.setAttribute("bottoniNascosti", bottoniNascosti);
    
	//debug
    Logger.debug(" --  main.jsp -- ");   
    Logger.debug("" + Debugger.printJspPageContext(pageContext) );
    
    String currentCategoryName = null;
    String currentContentName = null;

    //Recupero il currentCategory
    currentCategoryName = pplProcess.getCurrentCategory();

    //Recupero il currentContent
    currentContentName = pplProcess.getCurrentContent();

    String jspath = pplProcess.getProcessName();
    jspath = "/people/" + jspath.replaceFirst( "it.people.fsl.", "" );
    jspath = jspath.replace('.', '/');
    jspath += "/view/default/html/";
%>
<!-- main.jsp -->
<html:html xhtml="true">
<head>
    <meta http-equiv="Expires" content="0" />
    <title><bean:message key="label.windowTitle"/></title>
    <people:frameworkCss />
    <people:serviceCss processName="pplProcess" />
    <script type="text/javascript" src="<%=jspath + "people.js"%>"></script>
    <script type="text/javascript">
		function executeSubmit(newAction) {
			if (newAction != "") {
				document.forms[0].action = newAction;
			}   
			document.forms[0].submit();
		}

        function openHelpUrl(url){
            var v = window.open(url,'_frame','width=550,height=300,toolbar=no,scrollbars=yes,resizable=yes');
        }
    </script>
    
    <%
        try {
            String jspHeader = CurrentStep.getJspPath();
            jspHeader = jspHeader.replaceFirst(".jsp", "_header.jsp");
            pageContext.include(jspHeader);
        } catch (IllegalStateException e) {
        } catch (ServletException e) {
            }
    %>
</head>
<body>
	<html:form action="/lookupDispatchProcess.do" enctype="multipart/form-data">
	    <table cellspacing="0" onkeypress="if (event.keyCode == 13) return false;">
	        <tr><td colspan="2">
	        	<people:include rootPath="/framework/view/generic" 
        			nestedPath="/html" 
        			elementName="header.jsp" />	        			
        	</td></tr>
	        <tr><td colspan="2">				
				<%@include file="navbar.jsp"%>
	        </td></tr>
	        <tr>
	        	<td class="menu">
	            	<%@include file="menu.jsp"%>
	        	</td>
	        	<td valign="top" class="main">
		            <%@include file="body.jsp"%> 
		            <%if (pplProcess.getView().isBottomSaveBarEnabled()) {%>
		                <%@include file="genericSave.jsp"%>
		            <%}%>
		            <%if (pplProcess.getView().isBottomNavigationBarEnabled()) {%>
		                <%@include file="genericPager.jsp" %>
		            <%}%>
	        	</td>
	        </tr>
	    </table>
    </html:form>
</body>
</html:html>