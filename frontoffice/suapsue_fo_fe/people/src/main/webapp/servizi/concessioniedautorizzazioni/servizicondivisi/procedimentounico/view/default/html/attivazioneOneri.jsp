<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/sql.tld" prefix="sql" %>
<%@ page import="it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData"%>
<%@ page import="it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.SportelloBean"%>
<%@ page import="it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.Costant"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.Properties"%>
<%@ page import="java.io.FileInputStream"%>
<jsp:useBean id="pplProcess" scope="session" type="it.people.process.AbstractPplProcess" />

<%
	ProcessData processData = (ProcessData)pplProcess.getData();
%>

<link rel="stylesheet" type="text/css" href="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/view/default/html/style.css" />
<html:xhtml/>

<logic:equal value="true" name="pplProcess" property="data.internalError">
	<jsp:include page="defaultError.jsp" flush="true" />
</logic:equal>

<logic:notEqual value="true" name="pplProcess" property="data.internalError">
	<logic:messagesPresent>
		<table style="border:2px dotted red; padding: 3px; width:96%;">
			<tr>
				<td><img src="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/img/iconWarning.gif" alt="attenzione" />
				<b><ppl:errors /></b>
				</td>
			</tr>
		</table>
	</logic:messagesPresent>
	
	<jsp:include page="webclock.jsp" flush="true" />
	
	<table style="border:1px solid #EAEAEA; padding: 5px; width:96%;">
		<tr>
			<td>
				<center><b><bean:message key="attivazionePagamentoOneri.avvertenza" /></b></center>
				<p><bean:message key="attivazionePagamentoOneri.descrizione" /></p>
			</td>
		</tr>
		<tr>
			<td>
				&nbsp;
			</td>
		</tr>

		<%
		
			String paymentPropertiesFile = request.getSession().getServletContext().getRealPath("/WEB-INF/payment.properties");
			Properties paymentsConfig = new Properties();
			paymentsConfig.load(new FileInputStream(paymentPropertiesFile));
			String paymentStrategyClassName = paymentsConfig.getProperty("payment.strategy.class");
			
			if (paymentStrategyClassName.equalsIgnoreCase("it.people.util.payment.PayERPaymentManager")) {
		
		%>

		<tr>
            <td>
            <%
                //Ricerca delo sportello
                SportelloBean sportello = null;
                Set chiaviSettore = processData.getListaSportelli().keySet();
                boolean trovato = false;
                Iterator chiaviSettoreIterator = chiaviSettore.iterator();
                while (chiaviSettoreIterator.hasNext() && !trovato){
                    String chiaveSettore = (String) chiaviSettoreIterator.next();
                    if (sportello == null) {
                        sportello = (SportelloBean) processData.getListaSportelli().get(chiaveSettore);
                        trovato = true;
                    }
                }
                //Se lo sportello viene trovato verifico che in banca dati i dati minimi di configurazione siano stati settati 
                String sportelloDest = "";
				boolean entePayerAttivo = false;
				if (trovato) {
					//Se utente, ente e servizio sono confiogurati nella tabella per lo sportello selezionato allora lo sportello � abilitato al pagamento
					entePayerAttivo = sportello.getAe_codice_utente() != null && sportello.getAe_codice_utente().length() > 0 && sportello.getAe_codice_ente() != null && sportello.getAe_codice_ente().length() > 0 && sportello.getAe_tipologia_servizio() != null && sportello.getAe_tipologia_servizio().length() > 0;
					if (entePayerAttivo) {
				%>
				<html:radio property="data.tipoAttivazioneOneri" id="attivazionePagamentoOneri.opt1" value="<%=Costant.ATTIVAZIONE_ONERI_PAGAMENTO_ON_LINE %>" name="pplProcess" />
				<label for="attivazionePagamentoOneri.opt1"><bean:message key="attivazionePagamentoOneri.opt1" /></label>
				<%
					}
                }
	            %>
	            
	            <% if ((processData.getTipoPagamentoBookmark().equalsIgnoreCase(Costant.forzaPagamentoLabel) && !processData.isModalitaPagamentoSoloOnLine()) 
	            		|| (!processData.getTipoPagamentoBookmark().equalsIgnoreCase(Costant.forzaPagamentoLabel) && !processData.isModalitaPagamentoOpzionaleSoloOnLine())) { %>
	                <br>
	                <html:radio property="data.tipoAttivazioneOneri" id="attivazionePagamentoOneri.opt3" value="<%=Costant.ATTIVAZIONE_ONERI_PAGAMENTO_OFF_LINE %>" name="pplProcess" />
					<label for="attivazionePagamentoOneri.opt3"><bean:message key="attivazionePagamentoOneri.opt3" /></label>
	            <% } %>

				<% if (!processData.getTipoPagamentoBookmark().equalsIgnoreCase(Costant.forzaPagamentoLabel)) { %>
                <br>
                <html:radio property="data.tipoAttivazioneOneri" id="attivazionePagamentoOneri.opt2" value="<%=Costant.ATTIVAZIONE_ONERI_SOLO_CALCOLO %>" name="pplProcess" />
				<label for="attivazionePagamentoOneri.opt2"><bean:message key="attivazionePagamentoOneri.opt2" /></label>
				<% } %>
            </td>
        </tr>
	
		<% } else { %>
		
		<tr>
			<td>
			<label for="attiva_oneri_1">
				<html:radio property="data.tipoAttivazioneOneri" id="attivazionePagamentoOneri.opt1" value="<%=Costant.ATTIVAZIONE_ONERI_PAGAMENTO_ON_LINE %>" name="pplProcess" />
				<label for="attivazionePagamentoOneri.opt1"><bean:message key="attivazionePagamentoOneri.opt1" /></label>
	            <% if ((processData.getTipoPagamentoBookmark().equalsIgnoreCase(Costant.forzaPagamentoLabel) && !processData.isModalitaPagamentoSoloOnLine()) 
	            		|| (!processData.getTipoPagamentoBookmark().equalsIgnoreCase(Costant.forzaPagamentoLabel) && !processData.isModalitaPagamentoOpzionaleSoloOnLine())) { %>
	                <br>
	                <html:radio property="data.tipoAttivazioneOneri" id="attivazionePagamentoOneri.opt3" value="<%=Costant.ATTIVAZIONE_ONERI_PAGAMENTO_OFF_LINE %>" name="pplProcess" />
					<label for="attivazionePagamentoOneri.opt3"><bean:message key="attivazionePagamentoOneri.opt3" /></label>
	            <% } %>
	            <% if (!processData.getTipoPagamentoBookmark().equalsIgnoreCase(Costant.forzaPagamentoLabel)) { %>
	        	<br>
	        	<html:radio property="data.tipoAttivazioneOneri" id="attivazionePagamentoOneri.opt2" value="<%=Costant.ATTIVAZIONE_ONERI_SOLO_CALCOLO %>" name="pplProcess" />
				<label for="attivazionePagamentoOneri.opt2"><bean:message key="attivazionePagamentoOneri.opt2" /></label>
				<% } %>
			</label>
			</td>
		</tr>

		<% } %>

		<tr>
			<td>
				&nbsp;
			</td>
		</tr>
	</table>
	<br/>
	<%@include file="bookmark.jsp"%>
</logic:notEqual>
