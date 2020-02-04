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
<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/people.tld" prefix="people" %>
<%@ taglib uri="/WEB-INF/app.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

    <table style="text-align: center; border: 1px solid black; width: 400px;" cellpadding="8" cellspacing="0">
        <tbody>
            <tr>
             <td>
             	<html:img styleClass="runtimewarnmsg" page="/img/alert.png" alt="Attenzione!"></html:img>
			</td>
             <td>
             <strong><span class="usrmsg">
             	<bean:message key="label.requiredUserData.info" />
             </span></strong></td>
            </tr>
            <tr>
	           	<td style="vertical-align: middle; text-align: right; width: 80px;">
	           		<span class="txtNormal"><bean:message key="label.requiredUserData.email" /></span>
	           	</td>
	            <td style="vertical-align: middle; text-align: left;">
	            	<input class="txtNormal" type="text" width="200" name="EMAILADDRESS" value="" styleClass="smallbtn" />
	           	</td>
	        </tr>
	        <tr>
				<td colspan="2">
					<input type="submit" value="<bean:message key="label.requiredUserData.confirmButton" />" name="submit" id="submit" />
	        	</td>
	        </tr>
    </table>
