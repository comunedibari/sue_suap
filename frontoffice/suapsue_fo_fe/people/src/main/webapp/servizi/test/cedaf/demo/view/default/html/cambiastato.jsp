<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>
<jsp:useBean id="pplProcess" scope="session" type="it.people.process.AbstractPplProcess" />

<%@ taglib uri="/WEB-INF/app.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>

<html:xhtml/>
<h1>Pagina per modificare lo stato da pending a submitted</h1>
<br />
<br />
<ppl:errors/>

<a href="javascript:executeSubmit('loopBack.do')"  class="bottone" >cambia stato</a>

<p>
ultimo valore inserito:
<ppl:fieldWrite name="pplProcess" property="data.prova" default="..."  />
</p>

