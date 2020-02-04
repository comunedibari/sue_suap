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
  
<%@ taglib uri="/WEB-INF/people.tld" prefix="people" %>
<%@ taglib uri="/WEB-INF/app.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%@ page import="it.people.Activity,
    it.people.vsl.*,
    java.net.URLEncoder,
    it.people.core.*,
    it.people.util.ProcessUtils,
    java.util.Collection,
    java.util.Iterator,
    org.apache.commons.beanutils.PropertyUtils,
    it.people.process.data.PplProcessData,
    org.apache.ojb.broker.query.Criteria,
    it.people.process.*,
    java.text.SimpleDateFormat,
    it.people.core.persistence.exception.peopleException,
    it.people.error.*,
    it.people.util.DateFormatter,
    it.people.util.debug.Debugger,
    it.people.content.ContentImpl,
    it.people.util.*,
    it.people.process.data.*,
    it.people.layout.*"
%>    

<jsp:useBean id="City" scope="session" type="it.people.City" />

<%

	String myPage = request.getSession().getServletContext().getInitParameter("myPage");
	boolean myPageActive = (myPage!=null && myPage.equalsIgnoreCase("TRUE"));

    Collection processesPersistentData = null;
    Collection processesSub = null;

	PeopleContext pplContext = PeopleContext.create(request);
    String communeId = pplContext.getCommune().getKey();

	if (!myPageActive) { 
	        
	    try {
		
		    // verifica se l'utente che tenta l'accesso e' anonimo
			if (pplContext.getUser().isAnonymous()) {
			    errorMessage error = MessagesFactory.getInstance().getErrorMessage(communeId, "praticheOnLine.anonymousError");   		    
		        error.setErrorForward("/framework/genericErrors/ProcessError.jsp");
		        request.setAttribute("errorMessage", error);
		        request.getRequestDispatcher("/eventError.do").forward(request,response);
				return;		
			}
				
	        // Carica solo il persistent data del pending process per
	        // evitare di istanziare ogni procedimento.
	        processesPersistentData = ProcessSearchManager.getInstance().getMinePplPersistentData(pplContext, null, Boolean.FALSE);
	        processesSub = SubmittedProcessSearchManager.getInstance().get(pplContext, null, Boolean.FALSE);            
	
	    } catch(peopleException e){	
		    errorMessage error = MessagesFactory.getInstance().getErrorMessage(communeId, "praticheOnLine.dbError");   		    
	        error.setErrorForward("/framework/genericErrors/ProcessError.jsp");
	        request.setAttribute("errorMessage", error);
	        request.getRequestDispatcher("/eventError.do").forward(request,response);
			return;
	    } catch(Exception e){	
		    errorMessage error = MessagesFactory.getInstance().getErrorMessage(communeId, "praticheOnLine.genericError");
	        error.setErrorForward("/framework/genericErrors/ProcessError.jsp");
	        request.setAttribute("errorMessage", error);
	        request.getRequestDispatcher("/eventError.do").forward(request,response);
			return;
	    }
    }
    
%>

<!-- praticheOnLine.jsp -->
<html:html xhtml="true">
<head>
    <meta http-equiv="Expires" content="0" />
    <title><bean:message key="label.windowTitle"/></title>
    <people:frameworkCss />
</head>
<body>
	<%if (myPageActive) { 
        String redirectURL = "/people/initProcess.do?processName=it.people.fsl.servizi.praticheOnLine.visura.myPage";
        response.sendRedirect(redirectURL);
    } else { %>
    <table cellspacing="0" width="100%">
        <tr><td colspan="2">
    		<people:include rootPath="/framework/view/generic" 
    			nestedPath="/html" 
    			elementName="header.jsp" />	        			
        </td></tr>
        <tr><td>
			<jsp:include page="/include/navbar.jsp" />
        </td></tr>
        <tr><td valign="top" colspan="2">
        	<table>

				<%-- Pending Process --%>        	
	            <tr class="spaziatoreMenu">
	                <td colspan="3">&nbsp;</td>
	            </tr>
	            <tr class="spaziatoreMenu">
	                <td colspan="3">&nbsp;</td>
	            </tr>
	            <tr><td class="txtPercorso" colspan="3">
	                <b><bean:message key="label.modulionline.PratichePendenti" /></b>
	            </td></tr>
	            <tr class="spaziatoreMenu">
	                <td colspan="3">&nbsp;</td>
	            </tr>								
				<% if(processesPersistentData.isEmpty()) {%> 
					<tr><td class="voceMenu" colspan="3">
						<b><bean:message key="label.modulionline.NessunaPraticaPendente" /></b>
					</td></tr>
				<% } %>
	            <logic:iterate id="pending" collection="<%=processesPersistentData%>">
	            	<% PplPersistentData pendingData = (PplPersistentData)pending; %>
		            <tr><td class="txtNormal" colspan="2">
		                <table cellpadding="0" cellspacing="0" border="0">
		                    <tr>
		                        <td class="puntoElenco">	                        
		                            <img src="/people/framework/img/active.gif" alt="<bean:message key="alt.puntoDiElenco"/>" />
		                        </td>
		                        <td>
		                        	<% 
		                        	   String serviceDesc = 
		                        	   	(pendingData.getContentName() != null ?
		                        	   	pendingData.getContentName() + "&nbsp;-" : "");
		                        	%>		                        	   
		                            <span class="txtNormal">&nbsp;<strong><%=serviceDesc%></strong></span>		                        
		                        	<% String initQuery = "/initProcess.do?"
		                        		+ "processName=" + pendingData.getProcessName()
		                        		+ "&amp;processId=" + pendingData.getProcessDataID(); 
		                        	%>
		                        	<% if(pendingData.getStatus().equals("S_SIGN")){%>
		                                <span class="txtNormal"><strong><%=pendingData.getProcessDataID()%></strong></span>
		                        	<%} else {%>
			                            <html:link page='<%=initQuery%>' >
			                                <span class="txtNormal">&nbsp;<strong><%=pendingData.getProcessDataID()%></strong></span>
			                            </html:link>
									<%}%>		                                                          
		                            <span class="txtNormal">&nbsp;&nbsp; <bean:message key="label.modulionline.Richiedente" /> [<%=pendingData.getUserID()%>]</span>
		                        </td>
		                    </tr>
		                    <tr>
		                        <td>&nbsp;</td>
		                        <td>
		                            <span class="txtNormal"> 
		                                &nbsp;<bean:message key="label.modulionline.CreatoIl" />
		                                <%= DateFormatter.format(pendingData.getCreationTime()) %>
		                            </span>
		                        </td>
		                    </tr>
		            	<% if(pendingData.getStatus().equals("S_SIGN")){%>
		                    <tr>
		                        <td>&nbsp;</td>
		                        <td>
		                            <span class="txtNormal">
		                                &nbsp; [ <bean:message key="label.modulionline.InFaseDiFirma" />
		                                <html:link page='<%= "/abortSign.do?processName=" + pendingData.getProcessName() 
		                       									+ "&amp;processId=" + pendingData.getOid()%>' >
		                                    <span class="txtNormal"><bean:message key="label.modulionline.AnnullaFirma" /></span>
		                                </html:link>
		                                ]
		                            </span>
		                        </td>
		                    </tr>
		                <% } else if(pendingData.getStatus().equals("S_EDIT")){%>
		                    <tr>
		                        <td>&nbsp;</td>
		                        <td>
		                            <span class="txtNormal">
		                                &nbsp; [ <bean:message key="label.modulionline.InFaseDiModifica" />
		                                <html:link page='<%= "/framework/protected/deleteProcess.jsp?processId=" + pendingData.getOid()%>' >
		                                    <span class="txtNormal"><bean:message key="label.modulionline.ElimaProcedimento" /></span>
		                                </html:link>
		                                ]
		                            </span>
		                        </td>
		                    </tr>
		            	<% } %>		                    
		                </table>
		            </td></tr>
		            <tr class="spaziatoreMenu">
		                <td colspan="2">&nbsp;</td>
		            </tr>		            
	            </logic:iterate>
	            
	            
	            <tr class="spaziatoreMenu">
	                <td colspan="2">&nbsp;</td>
	            </tr>
	            
	            
	            <%-- Submitted Process --%>
	            <tr class="spaziatoreMenu">
	                <td colspan="3">&nbsp;</td>
	            </tr>
	            <tr class="spaziatoreMenu">
	                <td colspan="3">&nbsp;</td>
	            </tr>
	            <tr><td class="txtPercorso" colspan="3">
	                <b><bean:message key="label.modulionline.PraticheInviate" /></b>
	            </td></tr>
	            <tr class="spaziatoreMenu">
	                <td colspan="3">&nbsp;</td>
	            </tr>	            
				<% if(processesSub.isEmpty()) {%> 
					<tr><td class="voceMenu" colspan="3">
						<b><bean:message key="label.modulionline.NessunPraticaInviata" /></b>
					</td></tr>
				<% } %>
	            <logic:iterate id="submitted" collection="<%=processesSub%>">
	            	<% SubmittedProcess submittedProcess = (SubmittedProcess)submitted; %>
		            <tr><td class="txtNormal" colspan="2">
		                <table cellpadding="0" cellspacing="0" border="0">
		                    <tr>
		                        <td class="puntoElenco">
		                            <img src="/people/framework/img/active.gif" alt="<bean:message key="alt.puntoDiElenco"/>" />
		                        </td>
		                        <td>
		                        	<% 
		                        	   String serviceDesc = 
		                        	   	(submittedProcess.getPersistentData().getContentName() != null ?
		                        	   	submittedProcess.getPersistentData().getContentName() + "&nbsp;-" : "");
		                        	%>		                        	   		                        
		                            <span class="txtNormal">&nbsp;<strong><%= serviceDesc %></strong></span>		                                          
		                            <span class="txtNormal">&nbsp;<strong><%= submittedProcess.getPeopleProtocollId() %></strong></span>
		                            <span class="txtNormal">&nbsp;&nbsp; <bean:message key="label.modulionline.Richiedente" /> [<%= submittedProcess.getUser().getUserID() %>]</span>
		                        </td>
		                    </tr>
		                    <logic:iterate id="history" collection="<%=submittedProcess.getHistoryState()%>">
		                    	<% SubmittedProcessHistory submittedHistory = (SubmittedProcessHistory)history; %>
			                    <tr>
			                        <td>&nbsp;</td>
			                        <td>
			                            <% String date = DateFormatter.format(submittedHistory.getTransactionTime()); %>
			                            <span class="txtNormal">
			                            	<% if (submittedHistory.getState() == SubmittedProcessState.INITIALIZING) {%>
			                                	<%=date%>&nbsp;<bean:message key="label.modulionline.PresoInCarico" />
			                                <% } else if (submittedHistory.getState() == SubmittedProcessState.SUBMITTED) {%>
			                                	<%=date%>&nbsp;<bean:message key="label.modulionline.InviatoAEnte" />
			                                <% } %>
			                            </span>
			                        </td>
			                    </tr>
		                    </logic:iterate>
						</table>		            
	            	</td></tr>
		            <tr class="spaziatoreMenu">
		                <td colspan="2">&nbsp;</td>
		            </tr>
				</logic:iterate>	            		            
			</table>			
		</td></tr>
    </table>
    <%} %>
</body>
</html:html>
