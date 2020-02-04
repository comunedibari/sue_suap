<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page contentType="text/html" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="it.people.sirac.accr.beans.*,
				 it.people.core.*, 
				 it.people.content.*, 
				 it.people.filters.*, 
				 java.util.*,
                 it.people.process.*,
                 it.people.*,
                 it.people.util.*,
                 it.people.sirac.accr.*,
                 it.people.sirac.web.forms.*,
                 it.people.wrappers.*,
                 it.people.sirac.core.SiracConstants" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>
<%-- JSTL --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<jsp:useBean id="profiloRichiedenteTitolareBean" scope="request" type="it.people.sirac.authentication.beans.ProfiloRichiedenteTitolareBean" />
<html:html xhtml="true">
	<head>
	    <title><bean:message key="label.windowTitle"/></title>
		<link rel="stylesheet" type="text/css" href="/people/css/people.css" />
		<script type="text/javascript">
			<!--

				Date.prototype.getDiff = function(date, interval){
				  if (typeof date == "string"){
				     date = new Date(date);
				  }
				  if (isNaN(date) || !(date instanceof Date)){
				     return NaN; 
				  }
				  if (typeof interval == "undefined") interval = "ms"; 
				  var diff = this - date; 
				  switch(interval.toLowerCase()){
				    case "s": 
				      diff = diff/1000; break;
				    case "n": 
				      diff = diff/(1000*60); break;
				    case "h": 
				      diff = diff/(1000*60*60); break;
				    case "d": 
				      diff = diff/(1000*60*60*24); break;
				    case "m": 
				      diff = diff/(1000*60*60*24*30); break;
				    case "y": 
				      diff = diff/(1000*60*60*24*365); break;
				    default:
				      ; 
				  }
				  return Math.floor(diff);
				}

			function checkBirthday(obj) {
				if( !/^(\d{1,2})\/(\d{1,2})\/(\d{4})$/.test(obj.value) ) {
					obj.focus();
					return false;
				}
				
				var d = new Date();
				var d2 = new Date(RegExp.$3, RegExp.$2, RegExp.$1);
				
				var diff = d.getDiff(d2, "y")
				if( isNaN(diff) ) {
					return false;
				}
				else if( diff < 2 ) {
					return false;
				}
				return true;
			}

			function validate_form() {
			    valid = 0;
		
					if(document.profileForm.edit.value == "false") {
						return 0;
					}
					
					profiloRichiedenteCompleto = ( document.profileForm.profiloRichiedenteNome.value != "" &&
												document.profileForm.profiloRichiedenteCognome.value != "" &&
												document.profileForm.profiloRichiedenteCodiceFiscale.value != "" &&
												document.profileForm.profiloRichiedenteDataNascitaString.value != "" &&
												document.profileForm.profiloRichiedenteLuogoNascita.value != "" &&
												document.profileForm.profiloRichiedenteProvinciaNascita.value != "" &&
												document.profileForm.profiloRichiedenteSesso.value != "" &&
												document.profileForm.profiloRichiedenteIndirizzoResidenza.value != ""
											);
																				
					profiloTitolarePGCompleto = ( document.profileForm.profiloTitolareCodiceFiscale.value != "" &&
	        								document.profileForm.profiloTitolarePartitaIva.value != "" &&
	        								document.profileForm.profiloTitolareDenominazione.value != "" &&
	        								document.profileForm.profiloTitolareSedeLegale.value != "" &&
	        								document.profileForm.profiloTitolareRappresentanteLegaleNome.value != "" &&
	        								document.profileForm.profiloTitolareRappresentanteLegaleCognome.value != "" &&
	        								document.profileForm.profiloTitolareRappresentanteLegaleCodiceFiscale.value != ""
	        							) ||
												( document.profileForm.profiloTitolareCodiceFiscale.value == "" &&
	        								document.profileForm.profiloTitolarePartitaIva.value == "" &&
													document.profileForm.profiloTitolareDenominazione.value == "" &&
	        								document.profileForm.profiloTitolareSedeLegale.value == "" &&
	        								document.profileForm.profiloTitolareRappresentanteLegaleNome.value == "" &&
	        								document.profileForm.profiloTitolareRappresentanteLegaleCognome.value == "" &&
	        								document.profileForm.profiloTitolareRappresentanteLegaleCodiceFiscale.value == ""
	        							)  
	        													

				dataNascitaValida = checkBirthday(document.profileForm.profiloRichiedenteDataNascitaString);
			
			    if ( !(profiloRichiedenteCompleto && dataNascitaValida) || !profiloTitolarePGCompleto)
			    {
			        valid = 1;
			    }
			    
			    if ((profiloRichiedenteCompleto && !dataNascitaValida)) {
			    	valid = 2;
			    }
			
			    return valid;
			}
			
			function submit_form() {
				valid = validate_form();
				if(valid == 0) {
					document.forms[0].submit();
				} else {
					if (valid == 1) {
				        alert("E' necessario valorizzare tutti i campi per il richiedente. Nel caso in cui il titolare e il richiedente non coincidano, è necessario riempire anche tutti i campi relativi al titolare, altrimenti tali campi devono essere lasciati vuoti.");
					}
					else if (valid == 2) {
				        alert("La data di nascita del richiedente non è corretta.");
				        document.profileForm.profiloRichiedenteDataNascitaString.focus();
					} else {
				        alert("E' necessario valorizzare tutti i campi per il richiedente. Nel caso in cui il titolare e il richiedente non coincidano, è necessario riempire anche tutti i campi relativi al titolare, altrimenti tali campi devono essere lasciati vuoti.");
					}
				}
			}
			
			//-->
		</script>
	</head>
	<body>

		<%
		
		  HttpServletRequestDelegateWrapperHelper rwh = new HttpServletRequestDelegateWrapperHelper(request);
		  pageContext.setAttribute("siracErrorsMap", rwh.getSiracErrors());
		  
		  if(rwh.getSiracErrors().isEmpty()) {

			  Accreditamento accrSelected = (Accreditamento)session.getAttribute(SiracConstants.SIRAC_ACCR_ACCRSEL);
			  ProfiloPersonaFisica profiloRichiedente = profiloRichiedenteTitolareBean.getProfiloRichiedente();
			  AbstractProfile profiloTitolare = profiloRichiedenteTitolareBean.getProfiloTitolare();
			  String domicilioElettronico = profiloRichiedenteTitolareBean.getDomicilioElettronicoAssociazione();
			  
			  String error = (String)request.getAttribute("error");
			  
		%>  
			<table border="0" cellpadding="0" cellspacing="0" width="100%">
				<tr>
					<td>
						<c:import url="/framework/view/generic/default/html/header.jsp"/>
					</td>
				</tr>
<%--			  <tr>
			   	<td>
				  	<c:import url="/framework/view/generic/default/html/navbar.jsp"/>
			   	</td>
				</tr>
--%>
			</table>	    
		<form name="profileForm" method="post" action="<c:out value='${profiloRichiedenteTitolareBean.formAction}'/>">
			<div class="text_block" align="left">
				<h2><bean:message key="label.aggiornamentoProfilo.titolo" /></h2>
			</div>
			<div class="text_block" align="left">
		    <c:choose>
					<c:when test="${profiloRichiedenteTitolareBean.editAllowed}">
						Per procedere con l'esecuzione del servizio selezionato con la qualifica di <%=accrSelected.getQualifica().getDescrizione()%> è necessario specificare i dati di richiedente e titolare. Se il titolare coincide con il richiedente (persona fisica), è sufficiente compilare unicamente i dati del richiedente; in tale caso è necessario lasciare vuoti i campi relativi al titolare. In alternativa il titolare sarà la persona giuridica rappresentanta dal richiedente. E' inoltre necessario confermare il domicilio elettronico a cui verranno inviate tutte le comunicazioni PEOPLE relative a questa pratica.
					</c:when>
					<c:otherwise>
						La pratica salvata presenta la configurazione dei profili per richiedente e titolare riportata nel seguito. E' possibile variare l'indirizzo del domicilio elettronico nel caso in cui quello specificato in precedenza non sia corretto.
					</c:otherwise>
				</c:choose>									          
			</div>
			<br/>
			<div align="center">
				<table>
					<tr>
						<td class="pathBold"><bean:message key="label.richiedente"/></td>
					</tr>					
					<tr>
						<td>
							<div class="text_block" align="left">
							    <c:choose>
									<c:when test="${profiloRichiedenteTitolareBean.editAllowed}">
										<c:import url="ProfileUpdate_editRichiedente.jsp"/>
									</c:when>
									<c:otherwise>
										<c:import url="ProfileUpdate_showRichiedente.jsp"/>
									</c:otherwise>
								</c:choose>									          
							</div>
						</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td class="pathBold">
							<bean:message key="label.titolare1"/><br/>
							<c:if test="${profiloRichiedenteTitolareBean.editAllowed}">
								<bean:message key="label.titolare2"/>		          	
							</c:if>
						</td>
					</tr>					
					<tr>
						<td>
							<div class="text_block" align="left">
								<c:choose>
									<c:when test="${profiloRichiedenteTitolareBean.editAllowed}">
										<c:import url="ProfileUpdate_editTitolarePG.jsp"/>
									</c:when>
									<c:otherwise>	
										<c:choose>
											<c:when test="${profiloRichiedenteTitolareBean.profiloTitolare.personaGiuridica}">
												<c:import url="ProfileUpdate_showTitolarePG.jsp"/>
											</c:when>
											<c:otherwise>		
												<c:import url="ProfileUpdate_showTitolarePF.jsp"/>
											</c:otherwise>
										</c:choose>											  	
									</c:otherwise>
								</c:choose>											  	
							</div>	  
						</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td class="pathBold"><bean:message key="label.titoloDomicilioElettronico"/></td>
					</tr>					
					<tr>
						<td>
							<div class="text_block" align="center">
								<table>
									<tr>
										<td class="pathBold"><bean:message key="label.profiloPersonaGiuridica.domicilioElettronico"/></td>
						  				<td><input type="text" name="domicilioElettronico" value="<%=domicilioElettronico%>" size="70" maxlength="100"/></td>
									</tr>
								</table>
							</div>
						</td>
					</tr>
				</table>
			</div>
			
			<input type="hidden" name="target" value="<c:out value='${profiloRichiedenteTitolareBean.target}'/>"/>
			<input type="hidden" name="edit" value="<c:out value='${profiloRichiedenteTitolareBean.editAllowed}'/>"/>
			
		</form>	  

		<div align="center">
			<table>
				<tr>
					<td>
						<div><button onclick="javascript:submit_form()">Prosegui</button></div>
					</td>
					<td>
						<div><button onclick="window.location='<%=application.getInitParameter("peopleFSLHostURL")%>/people'">Annulla</button></div>
					</td>
				</tr>
			</table>
		</div>
	  
		<%
			} else {	// Gestione lista errori accodati nella request
		%>
			<div class="text_block" align="left">
		    Si sono verificati i seguenti errori:<br/>

				<c:forEach items='${siracErrorsMap}' var='mapItem'>
					<c:out value='${mapItem.value}'/>
				</c:forEach>
		  </div>
		<%
			rwh.cleanSiracErrors();
			}
		%>

	</body>
</html:html>