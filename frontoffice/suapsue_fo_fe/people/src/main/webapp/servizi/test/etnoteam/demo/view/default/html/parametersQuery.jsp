<%@ page import="it.people.util.*" %>

<%
    ServiceParameters spar = (ServiceParameters) session.getAttribute("serviceParameters");
%>

<p>Questa pagina recupera i parametri associati al servizio attivo.</p>
<p>Per richiamare i parametri di un servizio e' sufficiente recuperare dalla
sessione l'oggetto serviceParameters. Da questo oggetto e' possibile estrarre
i parametri di configurazione agendo sul metdo <pre>get(String paramName)</pre>
</p>
<p><strong>parametro1: </strong> <%= spar.get("parametro1")%></p>
<p>Il realizzatore del servizo e' (recuperato dal parametro di configurazione): 
<%= spar.get("realizzatore") %> </p>

