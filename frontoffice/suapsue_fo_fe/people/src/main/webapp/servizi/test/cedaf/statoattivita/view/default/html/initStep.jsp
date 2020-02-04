<%@ page language="java" %>
<%@ page import="it.people.layout.*, it.people.ActivityState, it.people.StepState"%>
<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<html:xhtml/>
<jsp:useBean id="pplProcess" scope="session" type="it.people.process.AbstractPplProcess" />

<h1> <bean:message key="initStep.titolo" /></h1>
<br />
<br />
<ppl:errors/>

<table>
    <logic:iterate id="activity" name="pplProcess" property="data.activityList" indexId="index">
    <tr>
        <td><strong><bean:message key="initStep.activity" /> <bean:write property="id" name="activity"/></strong></td>
		<td>
	        <html:radio property='<%="data.activity[" + index + "].statusCode"%>' value="<%="" + ActivityState.ACTIVE.getCode()%>" >
	            <ppl:label key="initStep.activityState.active"/>
	        </html:radio>
	        <html:radio property='<%="data.activity[" + index + "].statusCode"%>' value="<%="" + ActivityState.INACTIVE.getCode()%>" >
	            <ppl:label key="initStep.activityState.inactive"/>
	        </html:radio>
	        <html:radio property='<%="data.activity[" + index + "].statusCode"%>' value="<%="" + ActivityState.COMPLETED.getCode()%>" >
	            <ppl:label key="initStep.activityState.completed"/>
	        </html:radio>
	        	<table>	
		    	<logic:iterate id="step" name="pplProcess" property='<%="data.activity[" + index + "].stepList"%>' indexId="stepIndex">
		    	<tr>
		    		<td><strong><bean:message key="initStep.step" /> <bean:write property="id" name="step"/></strong></td>
		    		<td>
				        <html:radio property='<%="data.activity[" + index + "].step[" + stepIndex + "].statusCode"%>' value="<%="" + StepState.ACTIVE.getCode()%>" >
				            <ppl:label key="initStep.stepState.active"/>
				        </html:radio>
				        <html:radio property='<%="data.activity[" + index + "].step[" + stepIndex + "].statusCode"%>' value="<%="" + StepState.COMPLETED.getCode()%>" >
				            <ppl:label key="initStep.stepState.completed"/>
				        </html:radio>
		    		</td>
		    	</tr>
		    	
			</logic:iterate>	        
			</table>
		</td>    
    </tr>
	</logic:iterate>
</table>

<ppl:commandLoopback styleClass="btn">
	<bean:message key="initStep.button.loopBack"/>
</ppl:commandLoopback>

