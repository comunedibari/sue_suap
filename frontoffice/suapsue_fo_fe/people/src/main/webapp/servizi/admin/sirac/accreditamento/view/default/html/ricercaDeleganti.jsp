<%@ page contentType="text/html" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="it.people.sirac.accr.beans.*,
                 it.people.process.*,
                 it.people.*,
                 it.people.util.*,
                 it.people.sirac.web.forms.*,
                 it.people.sirac.accr.ProfiliHelper,
                 it.people.wrappers.*,
                 it.people.fsl.servizi.admin.sirac.accreditamento.model.ProcessData,
                 it.people.sirac.accr.ProfiliHelper,
                 java.util.List" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>
<%-- JSTL --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<jsp:useBean id="pplProcess"   scope="session" type="it.people.process.AbstractPplProcess" />

	<bean:define id = "loopbackClearDatiRicercaDeleganti" value = "javascript:executeSubmit('loopBack.do?propertyName=clearDatiRicercaDeleganti')" />
	<bean:define id = "loopbackStartRicercaDeleganti" value = "javascript:executeSubmit('loopBack.do?propertyName=startRicercaDeleganti')" />
	<bean:define id = "loopbackConfermaDelegante" value = "javascript:executeSubmit('loopBack.do?propertyName=confermaDelegante')" />

	<%
		String delegatoString = (String)request.getAttribute("delegatoString");
	 %>

	<div class="text_block" align="left">
		<h2 style="margin-bottom:0px">
			<bean:message key="label.ricercaDeleganti.info"/><%=delegatoString%>  		
		</h2>

	</div>
	
	<div class="text_block" align="left">
  <bean:message key="label.ricercaDeleganti.descrizione" /><br>
	<c:if test='${profiloTitolare=="PersonaFisica"}'>
			<bean:message key="label.ricercaDeleganti.descrizionePF"/><br><br>
		  <table border="0" cellpadding="1" cellspacing="1">
		    <tr>
		      <td class="pathBold">
		      	<ppl:fieldLabel key="label.personaFisica.nome" 
		      	     					  fieldName="data.criteriRicercaDeleganti.nomeDelegante"/>
		      </td>
		      <td>
		      	<html:text property="data.criteriRicercaDeleganti.nomeDelegante" size="70" maxlength="100"/>
		      </td>
		    </tr>
		    <tr>
		      <td class="pathBold">
		      	<ppl:fieldLabel key="label.personaFisica.cognome" 
		      	     					  fieldName="data.criteriRicercaDeleganti.cognomeDelegante"/>
		      </td>
		      <td>
		      	<html:text property="data.criteriRicercaDeleganti.cognomeDelegante" size="70" maxlength="100"/>
		      </td>
		    </tr>
		    <tr>
		      <td class="pathBold">
		      	<ppl:fieldLabel key="label.personaFisica.codiceFiscale" 
		      	     					  fieldName="data.criteriRicercaDeleganti.codiceFiscaleDelegante"/>
		      </td>
		      <td>
		      	<html:text property="data.criteriRicercaDeleganti.codiceFiscaleDelegante" size="16" maxlength="16"/>
		      </td>
		    </tr>
		  </table>
	</c:if>
	<c:if test='${profiloTitolare=="PersonaGiuridica"}'>
			<bean:message key="label.ricercaDeleganti.descrizionePG"/><br><br>
			<table border="0" cellpadding="1" cellspacing="1">
		    <tr>
		      <td class="pathBold">
		      	<ppl:fieldLabel key="label.personaGiuridica.denominazione" 
		      	     					  fieldName="data.criteriRicercaDeleganti.ragioneSocialeDelegante"/>
		      </td>
		      <td>
		      	<html:text property="data.criteriRicercaDeleganti.ragioneSocialeDelegante" size="70" maxlength="100"/>
		      </td>
		    </tr>
		    <tr>
		      <td class="pathBold">
		      	<ppl:fieldLabel key="label.personaGiuridica.codiceFiscale" 
		      	     					  fieldName="data.criteriRicercaDeleganti.codiceFiscaleDelegante"/>
		      <td>
		      	<html:text property="data.criteriRicercaDeleganti.codiceFiscaleDelegante" size="11" maxlength="11"/>
		      </td>
		    </tr>
		    <tr>
		      <td class="pathBold">
		      	<ppl:fieldLabel key="label.personaGiuridica.partitaIva" 
		      	     					  fieldName="data.criteriRicercaDeleganti.partitaIvaDelegante"/>
		      <td>
		      	<html:text property="data.criteriRicercaDeleganti.partitaIvaDelegante" size="11" maxlength="11"/>
		      </td>
		    </tr>
		  </table>
	</c:if>
	
	<table border="0" cellpadding="1" cellspacing="1">
     <tr>
       <td>
	       <div class="admin_sirac_accr_btn" align="right" style="margin-top:0px;">
		       <a href="<%=loopbackClearDatiRicercaDeleganti%>" title="Azzera i dati di ricerca deleganti">
		       	<bean:message key="label.ricercaDeleganti.loopbackClearDatiRicercaDeleganti" />
		       </a>
	       </div>
<%--
         <ppl:linkLoopback styleClass="btn" property="clearDatiRicercaDeleganti">
         	<bean:message key="label.ricercaDeleganti.loopbackClearDatiRicercaDeleganti" />
				 </ppl:linkLoopback>
--%>
       </td>
       <td>
	       <div class="admin_sirac_accr_btn" align="right" style="margin-top:0px;">
		       <a href="<%=loopbackStartRicercaDeleganti%>" title="Cerca i deleganti">
		       	<bean:message key="label.ricercaDeleganti.loopbackStartRicercaDeleganti" />
		       </a>
	       </div>
<%--
         <ppl:linkLoopback styleClass="btn" property="startRicercaDeleganti">
         	<bean:message key="label.ricercaDeleganti.loopbackStartRicercaDeleganti" />
         </ppl:linkLoopback>
--%>
       </td>
     </tr>
  </table>
  <br><br>
  
  	<c:choose>
		  <c:when test="${pplProcess.data.elencoDelegantiTrovati != null}">
		    <bean:message key="label.ricercaDeleganti.descrizioneSelect" /><br><br>
		    <table>
  			<tr>
		  		<td class="pathBold">
					  <bean:message key="label.ricercaDeleganti.elencoDelegantiTrovati" />
					</td>
					<td>
					  <c:if test='${profiloTitolare=="PersonaFisica"}'>
								  <html:select property="data.codiceFiscaleDeleganteSelezionato">
								    <% 
								    		List elencoDeleganti = ((ProcessData)pplProcess.getData()).getElencoDelegantiTrovati();	
								    		for (int i=0; i < elencoDeleganti.size(); i++) {
								    			ProfiloPersonaFisica delegante = ((ProfiloPersonaFisica)elencoDeleganti.get(i));
								     %>
										  	<html:option value="<%=delegante.getCodiceFiscale()%>">
									  			<%=delegante.getCognome() + " " + delegante.getNome() + " - C.F.: " + delegante.getCodiceFiscale() %>
										  	</html:option>
										 <%
										    }
										  %>
						  	  </html:select>
						</c:if>
					  <c:if test='${profiloTitolare=="PersonaGiuridica"}'>
						   <html:select property="data.codiceFiscaleDeleganteSelezionato">
							    <% 
								    		List elencoDeleganti = ((ProcessData)pplProcess.getData()).getElencoDelegantiTrovati();	
								    		for (int i=0; i < elencoDeleganti.size(); i++) {
								    			ProfiloPersonaGiuridica delegante = ((ProfiloPersonaGiuridica)elencoDeleganti.get(i));
								     %>
										  	<html:option value="<%=delegante.getCodiceFiscale()%>">
									  			<%=delegante.getDenominazione() + " - P.IVA: " + delegante.getPartitaIva() + " - C.F.: " + delegante.getCodiceFiscale() %>
										  	</html:option>
										 <%
										    }
										  %>
						  </html:select>
						</c:if>
					</td>
					<c:if test='${pplProcess.data.mostraLinkConferma}'>
						<td>
						  <div class="admin_sirac_accr_btn" align="right" style="margin-top:0px;">
					       <a href="<%=loopbackConfermaDelegante%>" title="Conferma delegante selezionato">
					       	<bean:message key="label.ricercaDeleganti.loopbackConfermaDelegante" />
					       </a>
				      </div>
						</td>
					</c:if>
				</tr>
				</table>
			</c:when>
			<c:otherwise>
					<c:choose>
						<c:when test='${errorMessage!=null}'>
							<p style="color:#FF0000"><c:out value="${errorMessage}"/></p>
				    </c:when>
				    <c:otherwise>
							<bean:message key="label.ricercaDeleganti.richiestaInserimentoCriteriRicerca" />
						</c:otherwise>
					</c:choose>
			</c:otherwise>
		</c:choose>

 </div>	
	
	