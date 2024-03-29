<%@ page import="java.util.HashMap" %>
<%@ page import="it.people.core.*" %>
<%@ page import="it.people.sirac.core.*" %>
<%@ page import="it.people.sirac.accr.*" %>
<%@ page import="it.people.sirac.accr.beans.*" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/people.tld" prefix="people" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<% it.people.layout.LayoutMenu theMenu = (it.people.layout.LayoutMenu)session.getAttribute("menuObject"); %>
<html:xhtml/>
<table cellpadding="0" cellspacing="0" border="0" width="100%" class="menu">
    <tr>
        <td class="box_contestuale">
        <% 
            String nomeutente = "";
 	                try {
            	PplUser peopleUser = PeopleContext.create(request).getUser();
            	if (peopleUser.isAnonymous())
            		nomeutente = "Utente Anonimo";
            	else
	                nomeutente = peopleUser.getUserData().getNome() 
	                	+ " " + peopleUser.getUserData().getCognome();
 			            } catch (Exception e) {} 
        %>
            <bean:message key="label.menu.benvenuto"/><br />
		 	<p><%= nomeutente %></p>
			
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
    	
    	/*
    	PplUserData user = null;
            try { 
                user = PeopleContext.create(request).getUser().getUserData();
                nomeutente = user.getNome() + " " + user.getCognome(); 
            } catch (Exception e) { ; } 
    	*/
    	
    	ProfiloPersonaFisica profiloOperatore = (ProfiloPersonaFisica) session.getAttribute(SiracConstants.SIRAC_ACCR_OPERATORE);
    	
    	//String codFiscaleUtente = user.getCodiceFiscale();
    	String codFiscaleUtente = profiloOperatore.getCodiceFiscale();
    	
    	String tipoQualifica  = (tipoQualificaSess==null) ? "Utente" : tipoQualificaSess;
    	String descrQualifica = (descrQualificaSess==null) ? "-----" : descrQualificaSess;
    	String codFiscale = (codFiscaleQualifica==null && pivaQualifica==null) ? codFiscaleUtente : codFiscaleQualifica;
    	
    	try {
			PplUser peopleUser = PeopleContext.create(request).getUser();
            if (peopleUser.isAnonymous())
				codFiscale = "-----";
        } catch (Exception e) {}     	    	
    	
    	String partitaIva = (codFiscaleQualifica==null && pivaQualifica==null) ? "-----" : pivaQualifica;

    	ProfiloPersonaFisica profiloRichiedente = 
    	  (ProfiloPersonaFisica) session.getAttribute(SiracConstants.SIRAC_ACCR_RICHIEDENTE);

    	AbstractProfile profiloTitolare =
    	  (AbstractProfile) session.getAttribute(SiracConstants.SIRAC_ACCR_TITOLARE);
    	  
    	java.util.HashMap datiTitolare = new HashMap();
    	  
    	if (profiloTitolare.isPersonaFisica()) {
    	  ProfiloPersonaFisica profiloTitolarePF = (ProfiloPersonaFisica) profiloTitolare;
    	  datiTitolare.put("CF", profiloTitolarePF.getCodiceFiscale());
        datiTitolare.put("Nome", profiloTitolarePF.getCognome() + " " + profiloTitolarePF.getNome());
    	} else if (profiloTitolare.isPersonaGiuridica()) {
    	  ProfiloPersonaGiuridica profiloTitolarePG = (ProfiloPersonaGiuridica) profiloTitolare;
    	  datiTitolare.put("CF",  profiloTitolarePG.getCodiceFiscale());
    	  datiTitolare.put("P.IVA: ", profiloTitolarePG.getPartitaIva());
    	  datiTitolare.put("R.Sociale: ", profiloTitolarePG.getDenominazione());
    	}   
    	
    %>
 
    <tr>
        <td class="box_contestuale">
	        <bean:message key="label.menu.tipoQualifica" /> <%= tipoQualifica %><br />
	        <bean:message key="label.menu.descrizioneQualifica"/> <%= descrQualifica %><br />
	        <bean:message key="label.menu.codiceFiscale"/> <%= codFiscale %><br />
	        <bean:message key="label.menu.partitaIva"/> <%= partitaIva %>
        </td>
     </tr>

     <% if (!"utente".equalsIgnoreCase(tipoQualifica)) { %>
    <tr>
        <td class="box_contestuale">
          <b>Richiedente :</b><br>
          CF: <%= profiloRichiedente.getCodiceFiscale() %>
          <br>
          <%= profiloRichiedente.getCognome() %> <%= profiloRichiedente.getNome() %>
          <br>
        </td>
    </tr>
    <tr>
        <td class="box_contestuale">
          <b>Titolare :</b>
            <%
              java.util.Iterator it = datiTitolare.keySet().iterator();
              while (it.hasNext()) {
                String curKey = (String)it.next();
                String curValue = (String)datiTitolare.get(curKey);
            %>
                <br/><%= curKey %>: <%= curValue %> 
            <%
              }
            %>
          <br>
        </td>
    </tr>
     <% } %>
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

	<%-- **************** Menu attivit� ***************************  --%>
     <tr><td class="box_contestuale">
        <p><bean:message key="label.menu.elencoAttivita"/></p>
     </td></tr>
<%
    ArrayList activityList = theMenu.getActivityList();
    Iterator activityIterator = activityList.iterator();
    Activity currentActivity = theMenu.getCurrentActivity();
    
    while (activityIterator.hasNext()) {
        String cssCellaTabella = "";
        String cssMenuText = "";
        String currentGif = "";
        String currentImgTag = "";
        String htmlLink = "";
        
        Activity temp = (Activity) activityIterator.next();        
        int i = activityList.indexOf(temp);

		if (temp == currentActivity)	{
			cssCellaTabella = "current";
		} else if (temp.isActive()) {
			if ( ActivityState.COMPLETED.equals(temp.getState())){
				cssCellaTabella = "compiled";
			} 
			else if ( ActivityState.ACTIVE.equals(temp.getState())) {
				cssCellaTabella = "active";
            } 
        } else {
			if ( ActivityState.COMPLETED.equals(temp.getState() ) ){ 
				cssCellaTabella = "compiled";
			}
			else if( ActivityState.ACTIVE.equals(temp.getState() ) ){
				cssCellaTabella = "active";
            } else {
				cssCellaTabella = "inactive";
            }
		}
                      
        if(!ActivityState.INACTIVE.equals(temp.getState()) ){%>
			<tr><td class="<%=cssCellaTabella %>">
				<% if (pplProcess.getProcessName().equalsIgnoreCase("it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico")) { %>
					<%=temp.getName()%>
				
				<% }  else {%>
				<people:wrappedSubmit name='<%="goActivityProcessTo" + i%>' value="<%=temp.getName()%>" lineLength="15" />
				<% } %>
			</td></tr>
	    <%} else{
            // se l'attivita' da stampare nel menu e' INACTIVE allora non deve essere clickabile %>
            <tr><td class="<%=cssCellaTabella %>"><%=temp.getName()%></td></tr>
        <%}%>
	<tr>
		<td class="step">
			<%
			if (temp == currentActivity) { // Fix by Max Pianciamore 17/02/2006
		        //ricavo la lista degli step per l'activity 
		        if (temp.getState().equals(ActivityState.ACTIVE) || temp.getState().equals(ActivityState.COMPLETED)){
		            ArrayList astepList = temp.getStepList();
		            
		            if (!astepList.isEmpty()) {
		            	%><table><%
			            Iterator astepIterator = astepList.iterator();
			            IStep stepTemp = null;
			            String stepName = null;
			            String cssCellaStep = "stepCompiled";		            
			            while (astepIterator.hasNext()){
			                cssCellaStep = "stepCompiled";
			                
			                stepTemp = (IStep) astepIterator.next();
			                stepName = stepTemp.getName();
			                if (!(NavigatorHelper.isStepEnabled(stepTemp, pplProcess))){
			                    cssCellaStep = "stepActive";
			                }
					
			                if (CurrentStep.getId().equalsIgnoreCase(stepTemp.getId())){
			                    cssCellaStep = "stepCurrent";
			                }
			                %>
					        <tr class="step">
					        	<td class="<%=cssCellaStep%>"><%=stepName%></td>
					        </tr>
					     <%-- <tr><td class="spaziatoreMenu"></td></tr> --%>
						<%
			            } //end while step
			            %></table><%			            
			    	} // end if not empty list 
		        } //end if step
		    }
	        %>
		</td>
	</tr>
	<%
    } // end while activity
%>

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

