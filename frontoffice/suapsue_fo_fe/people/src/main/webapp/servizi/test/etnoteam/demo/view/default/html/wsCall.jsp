<%@ page import="it.people.fsl.servizi.esempi.tutorial.serviziotutorial1.oggetti.*,
                it.people.util.*,
                it.people.fsl.servizi.test.etnoteam.demo.model.*" %>
<jsp:useBean id="pplProcess" scope="session" type="it.people.process.AbstractPplProcess" />
<h1>Sono proprio io!</h1>

<%

UtenteDocument ud = UtenteDocument.Factory.newInstance();

UtenteDocument.Utente utente = ud.addNewUtente();

utente.setNome("Ciccio");
utente.setCognome("Salsiccio");

String ret = pplProcess.callService("BACKEND_TEST", ud);

%>


<p>Il webservice ha risposto con il seguente documento XML:</P>
<p><%= ret %></p>



