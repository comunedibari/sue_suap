/**
 * 
 */
package it.people.taglib;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

/**
 * @author Riccardo Forafò - Engineering Ingegneria Informatica - Genova
 *         21/mag/2012 22:38:01
 */
public class OffLineSignFileUploadCommandLoopBack extends
	org.apache.struts.taglib.html.FileTag {

    // private static final String HIDDEN_FIELD_OPEN =
    // "<input type=\"hidden\" ";
    // private static final String HIDDEN_FIELD_NAME_OPEN = "name=\"";
    // private static final String HIDDEN_FIELD_NAME_CLOSE = "\" ";
    // private static final String HIDDEN_FIELD_VALUE_OPEN = "value=\"";
    // private static final String HIDDEN_FIELD_VALUE_CLOSE = "\" ";
    // private static final String HIDDEN_FIELD_CLOSE = "/>";

    private static final long serialVersionUID = 3985788057159330665L;

    private String jsCallback;

    /**
     * @return the jsCallback
     */
    protected final String getJsCallback() {
	return (this.jsCallback == null) ? "" : "'" + this.jsCallback + "'";
    }

    /**
     * @param jsCallback
     *            the jsCallback to set
     */
    public final void setJsCallback(String jsCallback) {
	this.jsCallback = jsCallback;
    }

    protected String renderInputElement() throws JspException {

	String browser = ((HttpServletRequest) pageContext.getRequest())
		.getHeader("User-Agent");
	boolean isMSIE = browser.indexOf("MSIE") > 0;

	StringBuffer results = new StringBuffer(getElementOpen(isMSIE));

	String nameAttribute = prepareName();
	prepareAttribute(results, "name", nameAttribute);
	prepareAttribute(results, "accesskey", getAccesskey());
	prepareAttribute(results, "accept", getAccept());
	prepareAttribute(results, "maxlength", getMaxlength());
	prepareAttribute(results, "size", getCols());
	prepareAttribute(results, "tabindex", getTabindex());
	if (this.getStyleClass() != null
		&& !this.getStyleClass().equalsIgnoreCase("")) {
	    prepareAttribute(results, "class", this.getStyleClass());
	}

	// if (getId() != null)
	// prepareAttribute(results, "id", getId());
	// else
	// prepareAttribute(results, "id", nameAttribute);

	prepareValue(results);
	results.append(this.prepareEventHandlers());
	// results.append(this.prepareStyles());
	// prepareOtherAttributes(results);
	results.append(this.getElementClose());

	if (isMSIE) {
	    results.append("<input type=\"button\" class=\"pulsanteFirma\" value=\""
		    + this.getValue()
		    + "\" name=\"navigation.button.offLineSignFileUpload\" onclick=\"javascript:peopleOffLineSign.uploadDocument("
		    + this.getJsCallback() + ");\" />");
	}

	results.append(getUploadDocumentFunction(isMSIE));
	return results.toString();
    }

    private String getElementOpen(boolean isMSIE) {
	if (isMSIE) {
	    return "<input type=\"file\"" + " class=\"pulsanteFirma\" ";
	}

	return "<input type=\"button\" class=\"pulsanteFirma\" id=\"pseudobutton\" value=\""
		+ this.getValue()
		+ "\" onclick=\"javascript:peopleOffLineSign.browseDocument("
		+ this.getJsCallback()
		+ ");\"><input type=\"file\""
		+ " class=\"offLineSignUploadHide\" id=\"openssme\" onchange=\"javascript:peopleOffLineSign.uploadDocument();\" ";

    }

    private String getUploadDocumentFunction(boolean isMSIE) {

	if (isMSIE) {
	    return new StringBuilder()
		    .append("<script type=\"text/javascript\">")
		    .append("var peopleOffLineSign = peopleOffLineSign || {};")
		    .append("peopleOffLineSign.executeFunctionByName = function (functionName, context) {")
		    .append("  var args = Array.prototype.slice.call(arguments).splice(2);")
		    .append("  var namespaces = functionName.split(\".\");")
		    .append("  var func = namespaces.pop();")
		    .append("  for(var i = 0; i < namespaces.length; i++) {")
		    .append("    context = context[namespaces[i]];")
		    .append("  };")
		    .append("  return context[func].apply(this, args);")
		    .append("};")
		    .append("")
		    .append("peopleOffLineSign.uploadDocument = function(callBack) {")
		    .append("	var proceed = true;")
		    .append("	if (callBack) { proceed = peopleOffLineSign.executeFunctionByName(callBack, window); };")
		    .append("	if (proceed) {")
		    .append("		var hiddenField = document.createElement(\"input\");")
		    .append("		hiddenField.setAttribute(\"type\", \"hidden\");")
		    .append("		hiddenField.setAttribute(\"name\", \"navigation.button.offLineSignFileUpload\");")
		    .append("		hiddenField.setAttribute(\"value\", \"\");")
		    .append("		document.getElementById(\"pplProcess\").appendChild(hiddenField);")
		    .append("		document.forms[0].submit();").append("	};")
		    .append("}").append("</script>").toString();
	}

	return new StringBuilder()
		.append("<script type=\"text/javascript\">")
		.append("var peopleOffLineSign = peopleOffLineSign || {};")
		.append("peopleOffLineSign.executeFunctionByName = function (functionName, context) {")
		.append("  var args = Array.prototype.slice.call(arguments).splice(2);")
		.append("  var namespaces = functionName.split(\".\");")
		.append("  var func = namespaces.pop();")
		.append("  for(var i = 0; i < namespaces.length; i++) {")
		.append("    context = context[namespaces[i]];")
		.append("  };")
		.append("  return context[func].apply(this, args);")
		.append("};")
		.append("")
		.append("peopleOffLineSign.browseDocument = function(callBack) {")
		.append("	var proceed = true;")
		.append("	if (callBack) { proceed = peopleOffLineSign.executeFunctionByName(callBack, window); };")
		.append("	if (proceed) {")
		.append("		document.getElementById(\"openssme\").click();")
		.append("	};")
		.append("};")
		.append("")
		.append("peopleOffLineSign.uploadDocument = function() {")
		.append("var hiddenField = document.createElement(\"input\");")
		.append("hiddenField.setAttribute(\"type\", \"hidden\");")
		.append("hiddenField.setAttribute(\"name\", \"navigation.button.offLineSignFileUpload\");")
		.append("hiddenField.setAttribute(\"value\", \"\");")
		.append("document.getElementById(\"pplProcess\").appendChild(hiddenField);")
		.append("document.forms[0].submit();").append("}")
		.append("</script>").toString();
    }

}
