<%--
Copyright (c) 2011, Regione Emilia-Romagna, Italy
 
Licensed under the EUPL, Version 1.1 or - as soon they
will be approved by the European Commission - subsequent
versions of the EUPL (the "Licence");
You may not use this work except in compliance with the
Licence.

For convenience a plain text copy of the English version
of the Licence can be found in the file LICENCE.txt in
the top-level directory of this software distribution.

You may obtain a copy of the Licence in any of 22 European
Languages at:

http://joinup.ec.europa.eu/software/page/eupl

Unless required by applicable law or agreed to in
writing, software distributed under the Licence is
distributed on an "AS IS" basis,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
express or implied.

This product includes software developed by Yale University

See the Licence for the specific language governing
permissions and limitations under the Licence.
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="it.people.util.NavigatorHelper, it.people.IStep, java.util.Iterator,
				 java.util.ArrayList, it.people.Activity, it.people.ActivityState,
				 java.util.StringTokenizer, it.people.City, it.people.core.Logger,
				 it.people.core.CategoryManager, it.people.core.PplUserData,
				 it.people.content.Category, it.people.error.errorMessage,
				 it.people.error.MessagesFactory, it.people.core.PeopleContext,
                 it.people.content.CategoryImpl, it.people.core.persistence.exception.peopleException,
                 it.people.util.debug.Debugger, java.util.Collection,
                 it.people.util.*, it.people.*, it.people.layout.*, it.people.action.PeopleRequestProcessor"%>
                 
<%@ taglib uri="/WEB-INF/people.tld" prefix="people" %>
<%@ taglib uri="/WEB-INF/app.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<jsp:useBean id="pplProcess"   scope="session" type="it.people.process.GenericProcess" />
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

    boolean isContextualHelpActive = pplProcess.getView().getCurrentActivity().getCurrentStep().isContextualHelpActive();
    String colspan = isContextualHelpActive ? "3" : "2";
    String mainCssClass = isContextualHelpActive ? "mainNoWidth" : "main";
    
    boolean isUserPanel = pplProcess.getView().getCurrentActivity().getCurrentStep().isUserPanelActive();
    String subColspan = isContextualHelpActive ? "2" : "1";    
    
%>
<!-- main.jsp -->
<html:html xhtml="true">
<head>
    <meta http-equiv="Expires" content="0" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title><bean:message key="label.windowTitle"/></title>
	<link rel="stylesheet" type="text/css" href="/people/css/msgBoxLight.css">
    <people:frameworkCss />
    <people:serviceCss processName="pplProcess" />
	<script type="text/javascript" src="/people/javascript/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="/people/javascript/jquery.msgBox.js"></script>
	<script type="text/javascript" src="/people/javascript/ckeditor/ckeditor.js"></script>
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
        function getCaretPosition (ctrl) {
        	var caretPos = 0;	// IE Support
        	if (document.selection) {
        	ctrl.focus ();
        		var Sel = document.selection.createRange ();
        		Sel.moveStart ('character', -ctrl.value.length);
        		caretPos = Sel.text.length;
        	}
        	// Firefox support
        	else if (ctrl.selectionStart || ctrl.selectionStart == '0')
        		caretPos = ctrl.selectionStart;
        	return (caretPos);
        }        
        function setCaretPosition(ctrl, pos){
        	if(ctrl.setSelectionRange)
        	{
        		ctrl.focus();
        		ctrl.setSelectionRange(pos,pos);
        	}
        	else if (ctrl.createTextRange) {
        		var range = ctrl.createTextRange();
        		range.collapse(true);
        		range.moveEnd('character', pos);
        		range.moveStart('character', pos);
        		range.select();
        	}
        }        
        function enableTextAreasReturn() {
        	var textAreasNumber = document.getElementsByTagName('textarea').length;
        	if (textAreasNumber > 0) {
        		var textAreas = document.getElementsByTagName('textarea');
        		for (index = 0; index < textAreasNumber; index++) {
        			textAreas[index].onkeypress = function(e) {
        				e=e||window.event;
        			    if(e.keyCode == 13){
            				var caretPosition = getCaretPosition(this);
        			    	this.value=this.value.substring(0, caretPosition) + String.fromCharCode(e.keyCode) + this.value.substring(caretPosition);
        			    	setCaretPosition(this, caretPosition + 1);
        			    	eval(this.getAttribute("onkeypress"));
        			        return true;
        			    }
        			}
        		}
        	}
        }
        function enabledJSGuard() {
        	var pplJG = document.createElement("pplJG");
        	pplJG.setAttribute("type", "hidden");
        	pplJG.setAttribute("name", "pplJG");
        	pplJG.setAttribute("value", "true");
        	document.getElementById("guard").appendChild(pplJG);
        }
        function addOnlineHelpCkeditor() {
        	CKEDITOR.replace( 'onLineHelpManagement.text' );
        }
    </script>

</head>
<body onload="enableTextAreasReturn();enabledJSGuard();addOnlineHelpCkeditor();">
	<a name="peoplePageTop" />
	<html:form action="/lookupDispatchProcess.do" enctype="multipart/form-data">
		<span id="guard"></span>
	    <table cellspacing="0" width="99%" onkeypress="if (event.keyCode == 13) return false;">
	        <tr><td colspan="<%=colspan %>">
	        	<people:include rootPath="/framework/view/generic" 
        			nestedPath="/html" 
        			elementName="header.jsp" />	        			
        	</td></tr>
	        <tr><td colspan="<%=colspan %>">				
	        	<%@include file="navbar.jsp"%>	        			
	        </td></tr>
	        <tr>
	        	<td class="menu">
	            	<%@include file="menu.jsp"%>
	        	</td>
	        	<% if ( !isUserPanel ) { %>
		        	<td valign="top" class="<%=mainCssClass %>">
		        		<people:saveMessage />
			            <%@include file="body.jsp"%> 
		                <%@include file="genericSave.jsp"%>
			            <%if (pplProcess.getView().isBottomNavigationBarEnabled()) {%>
			                <%@include file="genericPager.jsp" %>
			            <%}%>
		        	</td>
	
		        	<% if ( isContextualHelpActive ) { %>
		        	<td width="40%">
		        		<people:onLineHelpReader processName="pplProcess" />
		        	</td>
		            <%}%>
		        <% } else { %>
		        	<td colspan="subColspan">
		        		<ppl:userPanel />
		        	</td>
	        	<% } %>
	        </tr>
	    </table>
    </html:form>
</body>
</html:html>
