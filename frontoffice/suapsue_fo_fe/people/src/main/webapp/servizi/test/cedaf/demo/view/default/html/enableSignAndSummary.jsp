<%@ taglib uri="/WEB-INF/app.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>
<jsp:useBean id="pplProcess" scope="session" type="it.people.process.AbstractPplProcess" />

<html:xhtml/>
<div class="title1">
    <h1><bean:message key="label.enableSignAndSummary.titolo" /></h1>
</div>
<ppl:errors/>
<div class="text_block">
    <bean:message key="label.enableSignAndSummary.descrizione" /><br />
    <ppl:errors />
	<br />
	<table>
		<tr>
			<td><bean:message key="label.enableSignAndSummary.abilitaFirma"/></td>
			<td>
				<html:radio property="data.abilitaFirma" value="true" >
            		<ppl:label key="label.Yes"/>
        		</html:radio>
			</td>
			<td>
		        <html:radio property="data.abilitaFirma" value="false" >
		            <ppl:label key="label.No"/>
		        </html:radio>
			</td>
		</tr>
		<tr>
			<td><bean:message key="label.enableSignAndSummary.abilitaRiepilogo"/></td>
			<td>
				<html:radio property="data.abilitaRiepilogo" value="always" >
            		<ppl:label key="label.enableSignAndSummary.abilitaRiepilogo.always"/>
        		</html:radio>
			</td>
			<td>
		        <html:radio property="data.abilitaRiepilogo" value="finally" >
		            <ppl:label key="label.enableSignAndSummary.abilitaRiepilogo.finally"/>
		        </html:radio>
			</td>
			<td>
				<html:radio property="data.abilitaRiepilogo" value="none" >
            		<ppl:label key="label.enableSignAndSummary.abilitaRiepilogo.none"/>
        		</html:radio>
			</td>
		</tr>
	</table>
</div>
