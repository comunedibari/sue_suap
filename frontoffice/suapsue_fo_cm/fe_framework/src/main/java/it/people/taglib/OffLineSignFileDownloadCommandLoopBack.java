/**
 * 
 */
package it.people.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.html.SubmitTag;

import it.people.layout.ButtonKey;
import it.people.offlinesignservice.utils.Constants;

/**
 * @author Riccardo Forafò - Engineering Ingegneria Informatica - Genova
 *         21/mag/2012 22:38:01
 */
public class OffLineSignFileDownloadCommandLoopBack extends SubmitTag {

    private static final String HIDDEN_FIELD_OPEN = "<input type=\"hidden\" ";
    private static final String HIDDEN_FIELD_NAME_OPEN = "name=\"";
    private static final String HIDDEN_FIELD_NAME_CLOSE = "\" ";
    private static final String HIDDEN_FIELD_VALUE_OPEN = "value=\"";
    private static final String HIDDEN_FIELD_VALUE_CLOSE = "\" ";
    private static final String HIDDEN_FIELD_CLOSE = "/>";

    private static final long serialVersionUID = 7346222476280422294L;

    protected String commandProperty;

    protected String commandIndex;

    protected String xhtml;

    protected String inputFormat;

    /**
     * @return the commandProperty
     */
    protected final String getCommandProperty() {
	return this.commandProperty;
    }

    /**
     * @param commandProperty
     *            the commandProperty to set
     */
    public final void setCommandProperty(String commandProperty) {
	this.commandProperty = commandProperty;
    }

    /**
     * @return the commandIndex
     */
    protected final String getCommandIndex() {
	return this.commandIndex;
    }

    /**
     * @param commandIndex
     *            the commandIndex to set
     */
    public final void setCommandIndex(String commandIndex) {
	this.commandIndex = commandIndex;
    }

    /**
     * @return the xhtml
     */
    protected final String getXhtml() {
	return this.xhtml;
    }

    /**
     * @param xhtml
     *            the xhtml to set
     */
    public final void setXhtml(String xhtml) {
	this.xhtml = xhtml;
    }

    protected boolean isXhtmlRequired() {
	return Boolean.parseBoolean(this.getXhtml());
    }

    /**
     * @return the inputFormat
     */
    protected final String getInputFormat() {
	return this.inputFormat;
    }

    /**
     * @param inputFormat
     *            the inputFormat to set
     */
    public final void setInputFormat(String inputFormat) {
	if (inputFormat == null
		|| (inputFormat != null && (!inputFormat
			.equalsIgnoreCase(Constants.BINARY_INPUT_FORMAT) && !inputFormat
			.equalsIgnoreCase(Constants.STRING_INPUT_FORMAT)))) {
	    throw new IllegalArgumentException(
		    "Input format cannot be null and must match '"
			    + Constants.STRING_INPUT_FORMAT + "' or '"
			    + Constants.BINARY_INPUT_FORMAT + "' type.");
	}
	this.inputFormat = inputFormat;
    }

    protected String prepareName() throws JspException {

	try {
	    writeCommandPropertyHiddenField(
		    Constants.COMMAND_PROPERTY_PARAMETER_KEY,
		    this.getCommandProperty(), this.pageContext.getOut());
	    writeCommandIndexHiddenField(Constants.COMMAND_INDEX_PARAMETER_KEY,
		    this.getCommandIndex(), this.pageContext.getOut());
	    writeInputFormatHiddenField(Constants.INPUT_FORMAT_PARAMETER_KEY,
		    this.getInputFormat(), this.pageContext.getOut());
	} catch (Exception e) {
	    throw new JspException("", e);
	}

	StringBuffer results = new StringBuffer();
	results.append(ButtonKey.OFF_LINE_SIGN_DOWNLOAD);

	if (commandProperty != null
		&& !commandProperty.trim().equalsIgnoreCase("")) {
	    results.append("$" + this.commandProperty);

	    if (commandIndex != null) {
		results.append("$" + this.commandIndex);
	    }
	}

	return results.toString();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.struts.taglib.html.SubmitTag#doEndTag()
     */
    public int doEndTag() throws JspException {

	// Generate an HTML element
	StringBuffer results = new StringBuffer();
	results.append(getElementOpen());
	prepareAttribute(results, "name", prepareName());
	prepareButtonAttributes(results);
	results.append(prepareEventHandlers());
	results.append(prepareStyles());
	prepareOtherAttributes(results);
	if (this.getXhtml() != null) {
	    if (this.isXhtmlRequired()) {
		results.append(" />");
	    } else {
		results.append(">");
	    }
	} else {
	    results.append(getElementClose());
	}

	TagUtils.getInstance().write(pageContext, results.toString());

	return (EVAL_PAGE);

    }

    private void writeCommandPropertyHiddenField(String name, String value,
	    JspWriter writer) throws IOException {
	writeHiddenField(name, value, writer);
    }

    private void writeCommandIndexHiddenField(String name, String value,
	    JspWriter writer) throws IOException {
	writeHiddenField(name, value, writer);
    }

    private void writeInputFormatHiddenField(String name, String value,
	    JspWriter writer) throws IOException {
	writeHiddenField(name, value, writer);
    }

    private void writeHiddenField(String name, String value, JspWriter writer)
	    throws IOException {
	writer.append(HIDDEN_FIELD_OPEN + HIDDEN_FIELD_NAME_OPEN + name
		+ HIDDEN_FIELD_NAME_CLOSE + HIDDEN_FIELD_VALUE_OPEN + value
		+ HIDDEN_FIELD_VALUE_CLOSE + HIDDEN_FIELD_CLOSE);
    }

}
