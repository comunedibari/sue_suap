<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/html/common/init.jsp"%>
<portlet:renderURL var="ricercaPraticheURL">
	<portlet:param name="azione" value="ricerca" />
</portlet:renderURL>

<portlet:resourceURL var="findStatoSportello" />


<liferay-ui:error key="errore.comuni" message="errore.comuni" />
<liferay-ui:error key="errore.generico" message="errore.generico" />
<liferay-ui:error key="errore.data.inizio" message="errore.data.inizio" />
<liferay-ui:error key="errore.data.fine" message="errore.data.fine" />
<liferay-ui:error key="errore.ricerca.pratiche" message="errore.ricerca.pratiche" />
<liferay-ui:error key="errore.end.point" message="errore.end.point" />

<%	
ThemeDisplay td = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
String url = td.getURLCurrent();

String idSportello=ParamUtil.getString(request, "idSportello");
String data_da=ParamUtil.getString(request, "data_da");
String data_a=ParamUtil.getString(request, "data_a");

List<Pratica> list = (List<Pratica>)request.getAttribute("listaPratiche");	
%>

<c:if test="${not empty aggStato}">
	<div>${aggStato}</div>
</c:if>
<div class="container">
	<form id="ricercaForm" action="<%=ricercaPraticheURL.toString()%>" method="post">
		<span><strong>Comune:</strong></span>
	<c:if test="${empty nomeComune}">
		<span>
			<select id="sportello_ente" name="<portlet:namespace />idSportello">
			<c:forEach items="${listaSportelli}" var="sportello">
				<option id="${sportello.id}" value="${sportello.id}">${sportello.denominazione}</option>
			</c:forEach>
			</select>
		</span>
	</c:if>
	<c:if test="${not empty nomeComune}">
		<span id="denComune">&nbsp;${nomeComune}</span>
		<input id="sportello_ente" type="hidden" name="<portlet:namespace />idSportello" value="${idSportello}" />
		<input type="hidden" name="<portlet:namespace />nomeComune" value="${nomeComune}" />
	</c:if>
		 <span id="sportelloNonattivo" class="nonDisponibile <c:if test="${flagAttivo}">ui-helper-hidden</c:if>">&nbsp;
		  Il servizio non è attualmente in esercizio
		</span>
		<span>&nbsp;
			<a id="vai_al_servizio" <c:if test="${flagAttivo eq false}">class="ui-helper-hidden"</c:if> href="${urlServizio}">Vai al servizio&nbsp;<i class="fa fa-angle-double-right"></i></a>
		</span>
		<br />
		<br />
		<a id="accedi_button_2" <c:if test="${flagAttivo eq false}">class="ui-helper-hidden"</c:if> href="${urlPeople}" target="_blank"><input  type="button" value="Le Mie Pratiche" /></a>
		<br />
	    <br />
	   
	    <div id="ricerca"class="descrizioneServizioDiv  <c:if test="${flagAttivo eq false}">ui-helper-hidden</c:if>  ">
	    	<div class="nomeServizio"><span>Ricerca Pratiche</span></div>
	    	<div id="filtri-ricerca">
	    		<span>
					<label>Dalla data </label>
					<input type="text" id="stData" class="data" name="<portlet:namespace />data_da" value="<%=data_da%>" />
				</span>
				<span class="data"> 
					<label>Alla data </label>
					<input type="text" id="enData" class="data" name="<portlet:namespace />data_a" value="<%=data_a%>" />
				</span>
				<span>
					<input id="ricerca_button" type="button" value="Ricerca" />
				</span>
			</div>
		<%if (list != null) {%>
			<div id="risultati-ricerca">
				<liferay-ui:search-container iteratorURL="${iteratorUrl}" delta="10" deltaConfigurable="<%= false %>"
					emptyResultsMessage="Nessuna pratica trovata">
					
					<liferay-ui:search-container-results total="<%= list.size() %>"
						results="<%= ListUtil.subList(list, searchContainer.getStart(), searchContainer.getEnd()) %>" />
						
					<liferay-ui:search-container-row modelVar="pratica"
						className="it.exprivia.pal.avbari.suapsue.dto.Pratica">
						<%if(pratica.getDataRicezione()!=null){%>
							<liferay-ui:search-container-column-text name='Data'  value="<%= pratica.getDataRicezione()%>"/>
						<%}else{%>
							<liferay-ui:search-container-column-text name='Data'  value=""/>
						<%}%>
						
						<%if(pratica.getIdentificativoPratica()!=null){%>
							<liferay-ui:search-container-column-text name='Identificativo'  value="<%= pratica.getIdentificativoPratica()%>"/>
						<%}else{%>
							<liferay-ui:search-container-column-text name='Identificativo'  value=""/>
						<%}%>
						
						<%if(pratica.getOggetto()!=null){%>
							<liferay-ui:search-container-column-text name='Oggetto'  value="<%= pratica.getOggetto()%>"/>
						<%}else{%>
							<liferay-ui:search-container-column-text name='Oggetto'  value=""/>
						<%}%>
						
						<%if(pratica.getDescrizioneStatoPratica()!=null){%>
							<liferay-ui:search-container-column-text name='Stato'  value="<%= pratica.getDescrizioneStatoPratica()%>"/>
						<%}else{%>
							<liferay-ui:search-container-column-text name='Stato'  value=""/>
						<%}%>
						
						<%if(pratica.getUbicazione()!=null){%>
							<liferay-ui:search-container-column-text name='Ubicazione'  value="<%= pratica.getUbicazione()%>"/>
						<%}else{%>
							<liferay-ui:search-container-column-text name='Ubicazione'  value=""/>
						<%}%>
						
						<portlet:renderURL var="dettaglioURL">
							<portlet:param name="azione" value="dettaglio" />
							<%if(pratica.getIdPratica()!=null){
							%>
							<portlet:param name="idPratica"value="<%=pratica.getIdPratica().toString() %>" />
							<%} 
							%>
							<portlet:param name="idSportello"value="<%=idSportello %>" />
							<portlet:param name="data_da"value="<%=data_da %>" />
							<portlet:param name="data_a"value="<%=data_a %>" />
							<portlet:param name="nomeComune"value="${nomeComune}" />
							<portlet:param name="currentURL" value="<%=url %>" />
						</portlet:renderURL>
						<liferay-ui:search-container-column-text name="Dettaglio" href="${dettaglioURL}" value="VISUALIZZA" />
					</liferay-ui:search-container-row>
					
					<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" paginate="<%= true %>" />
				</liferay-ui:search-container>
			</div>
		<% } %>
		</div>
	</form>
</div>

<script>
//Mostra/nasconde e valorizza i link "Vai al servizio" e "Accedi alle mie pratiche"
function findStatoSportello(id){
	 $.ajax({
         url : '${findStatoSportello}',
         data : {
                 id:id,
                 azioneAjax:"findStatoSportello"
         }, 
        type: 'POST',
        dataType : "json",
        success : function(data) {
      	  if(data.flagAttivo=="1"){
      		
      		$("#vai_al_servizio").attr("href", data.urlPagina);
      		$("#accedi_button_2").attr("href", data.url);
      		$("#vai_al_servizio").show();
      		$("#accedi_button_2").show(); 
      		$("#sportelloNonattivo").hide(); 
      		$("#ricerca").show();
      		
      	  }else{
      		$("#vai_al_servizio").hide();
      		$("#accedi_button_2").hide();
      		$("#sportelloNonattivo").show(); 
      		$("#ricerca").hide();
      		$("#stData").val("");
      		$("#enData").val("");
      		$("#risultati-ricerca").hide();
      		
      		
      	  }

        },
        error: function () {
           console.log('Errore ajax');
         }
	  });		
	}

//Mostra/nasconde e valorizza i link "Vai al servizio" e "Accedi alle mie pratiche" sul chenge della select
$("#sportello_ente").change(function() {	 	
	 var id= $("#sportello_ente").val();
	 findStatoSportello(id);
	
});



$( document ).ready(function() {

// 	var flag_attivo=${flagAttivo};
// 	if(flag_attivo){
// 		$("#vai_al_servizio").show();
// 		$("#accedi_button_2").show(); 		
// 	}else{
// 		$("#vai_al_servizio").hide();
// 		$("#accedi_button_2").hide(); 		
// 	}
	
	
			
//Preseleziona il valore delle select	
<%if(idSportello!=null & idSportello!=""){%>
	var idSportello="<%=idSportello%>";
	$("#"+idSportello).attr("selected", "selected");
	<%}%>	
	


<%if(data_da!=null && data_da!=""){%>
	 var data_da="<%=data_da%>";
	 $("#stData").val(data_da);
	 $("#stData").datepicker('setDate', data_da);
	<%}%>
	


	//aggangio il datepicker
	  $("#stData").datepicker({
		yearRange: "-100:+100",
		changeMonth : true,
		changeYear : true,
		showAnim: 'slideDown',
		onClose: function( selectedDate ) {
	         	$("#enData").datepicker( "option", "minDate", selectedDate );
	        }
	    });
	
	  <%if(data_a!=null && data_a!="" ){%>
	     var data_a="<%=data_a%>";

		$("#enData").val(data_a);
		$("#enData").datepicker('setDate', data_a);
<%}%>
	$("#enData").datepicker({
			yearRange : "-100:+100",
			changeMonth : true,
			changeYear : true,
			showAnim : 'slideDown',
			onClose : function(selectedDate) {
				$("#stData").datepicker("option", "maxDate", selectedDate);
			}
		});

		$("#ricerca_button").click(function() {
			if ($("#stData").val() == null || $("#stData").val() == "") {
				alert("Data di Inizio obbligataria");
				return false;
			} else if ($("#enData").val() == null || $("#enData").val() == "") {
				alert("Data di Fine obbligataria");
				return false;
			} else {
				$("#ricercaForm").submit();
			}
		});

	});
</script>