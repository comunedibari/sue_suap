/**
 * 
 */
package it.people.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author Riccardo Forafò - Engineering Ingegneria Informatica - Genova
 *         21/mag/2012 09:19:35
 */
public class FormatterFunction {

    private String method;

    private List<String> parameters = new ArrayList<String>();

    /**
     * @param methodDefinition
     */
    public FormatterFunction(final String methodDefinition) {
	this.parseMethodDefinition(methodDefinition);
    }

    /**
     * @return the method
     */
    public final String getMethod() {
	return this.method;
    }

    /**
     * @param method
     *            the method to set
     */
    public final void setMethod(String method) {
	this.method = method;
    }

    /**
     * @return the parameters
     */
    public final List<String> getParameters() {
	return this.parameters;
    }

    /**
     * @param parameters
     *            the parameters to set
     */
    public final void setParameters(ArrayList<String> parameters) {
	this.parameters = parameters;
    }

    /**
     * @param parameter
     */
    public final void addParameter(final String parameter) {
	this.getParameters().add(parameter);
    }

    /**
     * @param methodDefinition
     */
    private void parseMethodDefinition(final String methodDefinition) {
	int indexOfOpenBracket = methodDefinition.indexOf('(');
	if (indexOfOpenBracket > 0) {
	    int indexOfCloseBracket = methodDefinition.indexOf(')');
	    this.setMethod(methodDefinition.substring(0, indexOfOpenBracket));
	    String parameters = methodDefinition.substring(
		    indexOfOpenBracket + 1, indexOfCloseBracket);
	    StringTokenizer tokenizer = new StringTokenizer(parameters, ",");
	    while (tokenizer.hasMoreTokens()) {
		this.addParameter(tokenizer.nextToken().trim());
	    }
	} else {
	    this.setMethod(methodDefinition);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {

	StringBuilder stringBuilder = new StringBuilder();

	stringBuilder.append("[").append(
		"method = '" + this.getMethod() + "';\n");
	if (!this.getParameters().isEmpty()) {
	    Iterator<String> parametersIterator = this.getParameters()
		    .iterator();
	    int index = 1;
	    while (parametersIterator.hasNext()) {
		stringBuilder.append("parameter " + index + " = '"
			+ parametersIterator.next() + "';\n");
		index++;
	    }
	}
	stringBuilder.append("]");

	return stringBuilder.toString();

    }

}
