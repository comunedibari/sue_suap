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

http://www.osor.eu/eupl

Unless required by applicable law or agreed to in
writing, software distributed under the Licence is
distributed on an "AS IS" basis,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
express or implied.
See the Licence for the specific language governing
permissions and limitations under the Licence.
--%>
<%@page import="org.slf4j.LoggerFactory"%>
<%@page import="org.slf4j.Logger"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.people.Activity,
		 it.people.core.CategoryManager,
		 it.people.core.PeopleContext,
		 it.people.error.*,
		 it.people.ActivityState,
		 it.people.PeopleConstants,
         it.people.util.PeopleProperties,
		 it.people.City,
		 it.exprivia.GetToken,
         java.io.FileInputStream,
		 java.util.Properties,
		 java.util.StringTokenizer"%>
<%@ taglib uri="/WEB-INF/app.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@page import="it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData"%>
<%@page import="it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.Costant"%>
<jsp:useBean id="pplProcess" scope="session" type="it.people.process.AbstractPplProcess" />
<jsp:useBean id="City" scope="session" type="it.people.City" />
<link rel="stylesheet" type="text/css" href="/people/css/style.css" />
<%

Logger logger = LoggerFactory.getLogger("fineInvio.jsp");

// recupera il category manager
            CategoryManager myCatMgr = (CategoryManager) request.getSession().getAttribute("categoryManager");
            String comuneLabel = "";
            if (myCatMgr == null) {
                try {
                    myCatMgr = CategoryManager.getInstance();
                    if (myCatMgr == null) {
                        throw new Exception("Cannot Instantiate Category Manager");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("", e);
                    String communeKey = null;
                    if (City != null) {
                        communeKey = City.getKey();
                    } else {
                        communeKey = "000000";
                    }
                    errorMessage error = MessagesFactory.getInstance().getErrorMessage(communeKey, "backend.cannotInstantiateCategoryMgr");
                    error.setErrorForward("/framework/genericErrors/ProcessError.jsp");
                    request.setAttribute("errorMessage", error);
                    request.getRequestDispatcher("/eventError.do").forward(request, response);
                    return;
                }
            }
            ProcessData dataForm = (ProcessData) pplProcess.getData();
            String PeopleProtocollID = dataForm.getIdentificatorePeople().getIdentificatoreProcedimento();
            String communeId = ((City) session.getAttribute(PeopleConstants.SESSION_NAME_COMMUNE)).getKey();
            String linkNuovePratiche = PeopleProperties.SERVIZIPEOPLE_ADDRESS.getValueString(communeId);
            if (linkNuovePratiche == null) {
                linkNuovePratiche = request.getContextPath();
            }
            //if (request.getSession().getAttribute("byPassStepSceltaComune") != null) {
            //    linkNuovePratiche = request.getContextPath() + "?codEnte=" + request.getSession().getAttribute("byPassStepSceltaComune");
            //}

            //Label Comune
            //comuneLabel = (myCatMgr.get(PeopleContext.create(request),City.getOid())).getLabel();
%>

<body>
    <table cellpadding="0" cellspacing="0" border="0" width="100%">
        <tr>
            <td colspan="2" class="txtNormal" style="padding-left:23px;padding-top:23px;padding-right:23px;vertical-align:top;text-align:center">
                <h4><bean:message key="label.firmaCompletata.titolo"/></h4>
                <p><b><bean:message key="conferma.mailconferma" /></b></p>
                <bean:message key="label.firmaCompletata.Conferma" arg1="<%= PeopleProtocollID%>"/><br /><br /><br />
                <bean:message key="label.firmaCompletata.NuovePratiche"/>
                <a href="<%= linkNuovePratiche%>">
                    <bean:message key="label.firmaCompletata.Link"/>
                </a>
                <br /><br /><br /><br/>
				
		<%
			try {
				
				Properties prop = new Properties();
				String propFileName="${people.jsp.fineInvio.propFileName}"+communeId+".properties";
			    prop.load(new FileInputStream(propFileName));
				
				  String urlMlf="";
				    urlMlf=prop.getProperty("url.mlf");
				  
				  if(!urlMlf.isEmpty() && urlMlf!=null){
					 String result="";
				     result=GetToken .getToken(urlMlf);			
               
					%>
					<a id="urlSatisfaction" href="http://opinioni.egov.ba.it/ls/index.php/<bean:message key="cod.servizio"/><%=result%>" target="_blank">Valutazione del grado di soddisfazione dell'utente </a>		
					
					<%
			
				  }
			   
			   
			   
				
				
			} catch (Exception e){
				
				e.printStackTrace();
				logger.error("", e);
				
			}
				
			
			
		
		%>
		 

                <% if (dataForm.isComunica(dataForm.getAnagrafica())) {
                %>
                <table style="width:100%;">
                    <tr><td width="12">&nbsp;</td>
                        <td style="border:2px dotted red; padding: 3px; width:76%;" align="center"><b><br/><bean:message key="conferma.alertComunica" /><br/><br/></b>
                        </td>
                        <td width="12">&nbsp;</td>
                    </tr>
                </table>
                <% } else if (dataForm.getTipoBookmark().equalsIgnoreCase(Costant.bookmarkTypeCortesiaLabel)) {%>
                <table style="width:100%;">
                    <tr><td width="12">&nbsp;</td>
                        <td style="border:2px dotted red; padding: 3px; width:76%;" align="center"><b><br/><bean:message key="conferma.alert" /><br/><br/></b>
                        </td>
                        <td width="12">&nbsp;</td>
                    </tr>
                </table>
                <%}%>
            </td>
        </tr>

    </table>
</body>



