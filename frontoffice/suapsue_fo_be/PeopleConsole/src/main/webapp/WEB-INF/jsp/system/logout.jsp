<%--
Copyright (c) 2011, Regione Emilia-Romagna, Italy
 
Licensed under the EUPL, Version 1.1 or - as soon they
will be approved by the European Commission - subsequent
versions of the EUPL (the "Licence");
You may not use this work except in compliance with the
Licence.

For convenience a plain text copy of the English version
of the Licence can be found in the file LICENCE.txt in
the top-level directory of this software distribution.

You may obtain a copy of the Licence in any of 22 European
Languages at:

http://joinup.ec.europa.eu/software/page/eupl

Unless required by applicable law or agreed to in
writing, software distributed under the Licence is
distributed on an "AS IS" basis,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
express or implied.

This product includes software developed by Yale University

See the Licence for the specific language governing
permissions and limitations under the Licence.
--%>
<?xml version="1.0" encoding="ISO-8859-15"?>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="pcform" uri="/WEB-INF/peopleconsole.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
    xml:lang="it" lang="it">
    <head>
        <%@ include file="/WEB-INF/sitemesh/styles/style.jsp"%>
        <%@ include file="/WEB-INF/sitemesh/javascript/javascript.jsp"%>
        <meta http-equiv="Content-Type" content="application/xhtml+xml;charset=ISO-8859-15" />
		<title><spring:message code="logout.title" /></title>
    </head>
	<body>
		<form method="post">
			<h1><spring:message code="logout.title" /></h1>
			<div>
				<p>
				${message}
				</p>
			</div>
			<div class="buttonsbar">
				<input type="submit" id="doLogout" name="doLogout" value="Sì" class="button" />
				<input type="submit" id="cancel" name="cancel" value="No" class="button" />
			</div>
		</form>
    </body>
</html>
