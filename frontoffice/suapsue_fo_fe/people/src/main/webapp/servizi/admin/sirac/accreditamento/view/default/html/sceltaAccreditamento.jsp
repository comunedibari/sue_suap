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
<html:html xhtml="true">
	<head>
	    <title><bean:message key="label.windowTitle"/></title>
		<link rel="stylesheet" type="text/css" href="/people/css/people.css" />
		<script type="text/javascript">
			<!--


			function validate_form() {
			    valid = true;
		
					if(document.profileForm.edit.value == "false") {
						return true;
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
	        													
			
			    if ( !profiloRichiedenteCompleto || !profiloTitolarePGCompleto)
			    {
			        valid = false;
			    }
			
			    return valid;
			}
			
			function submit_form() {
			
					document.forms[0].submit();

			}
			
			//-->
		</script>
	</head>
	<body onload="javascript:submit_form()">
	
<!--	
		<table border="0" cellpadding="0" cellspacing="0" width="100%" class="header">
			<tr>
				<td>
					<c:import url="/framework/view/generic/default/html/header.jsp"/>
				</td>
			</tr>
		</table>
-->	    
		<form name="profileForm" method="post" action="/people/ProfileSelectService">
			<br />
			<div>
				<table>
					<tr>
						<td>
							<noscript>
								<div><input type="submit" value="Click here if you are not automatically redirected in 10 seconds"/></div>
							</noscript>
						</td>
					</tr>
				</table>
			</div>
	  	</form>	  


	</body>
</html:html>