<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://displaytag.sf.net" prefix="display" %>
<head>
	<link rel="stylesheet" type="text/css" href="<c:url value="/themes/default/css/displaytag.css"/>">
</head>



<tiles:insertAttribute name="body_error" />

<h2 style="text-align: center"><spring:message code="avvisi.gestione"/></h2> 


<display:table
	class="paginated"
	pagesize="10" 
	name="avvisiListTot"
	sort="list" 							
	uid="avviso" 
	cellpadding="5" 
	cellspacing="5" 
	list="avvisiListTot" 
	requestURI="${path}/avvisi/listaAvvisi.htm"
	>
 							 				
 	<display:column titleKey="" property="idAvviso" title="ID"  sortable="true"   />
 	<display:column titleKey="" property="scadenza" title="Scadenza" format="{0,date,dd/MM/yyyy}" sortable="true"/>
 	<display:column titleKey="" property="testo" title="Testo"  />   
 	<display:column titleKey="" title="Azioni"  >
 	  <a href="${path}/avvisi/apriDettaglioAvviso.htm?idAvviso=${avviso.idAvviso}"  title="modifica"><img src="${path}/themes/default/css/images/pencil.png"></a>
 	  <a  class="delete" href="${path}/avvisi/deleteAvviso.htm?idAvviso=${avviso.idAvviso}" title="elimina"><img src="${path}/themes/default/css/images/basket.png"></a>
 	</display:column>        						
</display:table> 

<br/>
 <div class="buttonHolder" align="left"> 
     <a  href="${path}/avvisi/creaAvviso.htm"> <button  type="submit" class="primaryAction">Crea Avviso</button></a>
 </div> 

<script>
$(".delete").click(function(){
    if(confirm("Sei sicuro di voler eliminare questo avviso?")){
       return true;
    }
    else{
        return false;
    }
});

</script>

