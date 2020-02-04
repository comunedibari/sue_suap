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
<%@ page import="it.people.sirac.accr.beans.*,
				 it.people.sirac.accr.*, 
				 it.people.sirac.core.*, 
				 it.people.core.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html:xhtml />
<% it.people.layout.LayoutMenu theMenu = (it.people.layout.LayoutMenu)session.getAttribute("menuObject"); %>
<table cellpadding="0" cellspacing="0" border="0" width="100%" class="menu">
    <tr>
        <td class="box_contestuale">
	        <% 
	            String nomeutente = "";
	            try { 
	                PplUserData user = PeopleContext.create(request).getUser().getUserData();
	                nomeutente = user.getNome() + " " + user.getCognome(); 
	            } catch (Exception e) { ; } 
	        %>
            <bean:message key="label.menu.benvenuto"/><br />
			<p><%=nomeutente%></p>
         </td>
     </tr>

	<%-- *************************************************************  --%>

    <%
    	String tipoQualificaSess = (String)session.getAttribute(SiracConstants.SIRAC_ACCR_TIPOQUALIFICA);
    	String descrQualificaSess = (String)session.getAttribute(SiracConstants.SIRAC_ACCR_DESCRQUALIFICA);
    	Accreditamento accrSel = (Accreditamento)session.getAttribute(SiracConstants.SIRAC_ACCR_ACCRSEL);
    	
    	String codFiscaleQualifica = null;
    	String pivaQualifica = null;
    	if (accrSel != null && accrSel.getProfilo()!=null) {
	    	codFiscaleQualifica = accrSel.getProfilo().getCodiceFiscaleIntermediario();
    		pivaQualifica = accrSel.getProfilo().getPartitaIvaIntermediario();
    		if (pivaQualifica != null) {
    			pivaQualifica = (pivaQualifica.trim().equals("")) ? "-----" : pivaQualifica;
    		}
    	}
    	    	
    	ProfiloPersonaFisica profiloOperatore = (ProfiloPersonaFisica) session.getAttribute(SiracConstants.SIRAC_ACCR_OPERATORE);
    	
    	//String codFiscaleUtente = user.getCodiceFiscale();
    	String codFiscaleUtente = profiloOperatore.getCodiceFiscale();
    	
    	String tipoQualifica  = (tipoQualificaSess==null) ? "Utente" : tipoQualificaSess;
    	String descrQualifica = (descrQualificaSess==null) ? "-----" : descrQualificaSess;
    	String codFiscale = (codFiscaleQualifica==null && pivaQualifica==null) ? codFiscaleUtente : codFiscaleQualifica;
    	String partitaIva = (codFiscaleQualifica==null && pivaQualifica==null) ? "-----" : pivaQualifica;
    	
    %>
 
	<tr>
        <td class="box_contestuale">
	        <bean:message key="label.menu.tipoQualifica" /> <%= tipoQualifica %><br />
	        <bean:message key="label.menu.descrizioneQualifica"/> <%= descrQualifica %><br />
	        <bean:message key="label.menu.codiceFiscale"/> <%= codFiscale %><br />
	        <bean:message key="label.menu.partitaIva"/> <%= partitaIva %>
        </td>
     </tr>

	<%-- *************************************************************  --%>
     <tr><td class="box_contestuale">
	<%   
        ArrayList listaContesti = theMenu.getContextElements();
        if (listaContesti.isEmpty())
        {
        	%><p><bean:message key="label.menu.nessunContesto"/></p><%        	
        }
        else
        {
    		%>
	        <p><bean:message key="label.menu.elencoContesto"/></p>
	        <ul>    
	    		<%
	            Iterator theContextIterator = listaContesti.iterator();
	            while(theContextIterator.hasNext()){
	                ContextElement theElement = 
	                            (ContextElement)theContextIterator.next();
	                String name = theElement.getName();
	                %><li><%=name%></li><%
	            }
	            %>
	        </ul>            
            <%
        }
	%>
     </td></tr>
	<%-- **************** Menu attività ***************************  --%>
     <tr><td class="box_contestuale">
        <p><bean:message key="label.menu.elencoAttivita"/></p>
     </td></tr>

	<tr>
		<td class="current">
			<bean:message key="label.sign.menu.AttivitaFirma"/>
		</td>
	</tr>
	<tr>
		<td class="step">
			<table>
				<tr class="step">
					<td class="stepCurrent">
						<bean:message key="label.sign.menu.StepFirma"/>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	
	<%-- *************************************************************  --%>
    <tr><td class="serv_conn">
	<%             	
		ArrayList listaServizi = theMenu.getConnectedServices();
		if (listaServizi.isEmpty()) {
			%><p><strong><bean:message key="label.menu.nessunServizioConnesso"/></strong></p><%			
		} else {
			%><p><strong><bean:message key="label.menu.elencoServiziConnessi"/></strong></p>
			<ul><%
			Iterator theServiceIterator = listaServizi.iterator();
			while(theServiceIterator.hasNext()){
		    	ConnectedService theService = (ConnectedService)theServiceIterator.next();
		    	String label = theService.getLabel();
		    	String uri = theService.getUri();
		    	%><li><a href="<%= uri %>"><%= label %></a></li><%
			}%>
			</ul>
			<%
		}
	%>    
    </td></tr>
	
</table>
