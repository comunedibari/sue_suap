/**
 * Copyright (c) 2011, Regione Emilia-Romagna, Italy
 *  
 * Licensed under the EUPL, Version 1.1 or - as soon they
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the
 * Licence.
 * 
 * For convenience a plain text copy of the English version
 * of the Licence can be found in the file LICENCE.txt in
 * the top-level directory of this software distribution.
 * 
 * You may obtain a copy of the Licence in any of 22 European
 * Languages at:
 * 
 * http://joinup.ec.europa.eu/software/page/eupl
 * 
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * 
 * This product includes software developed by Yale University
 * 
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
 **/
package it.people.taglib;

import it.people.City;
import it.people.IStep;
import it.people.PeopleConstants;
import it.people.annotations.DeveloperTaskEnd;
import it.people.annotations.DeveloperTaskStart;
import it.people.core.Logger;
import it.people.process.AbstractPplProcess;
import it.people.util.MessageBundleHelper;
import it.people.validator.PeopleValidatorUtil;
import it.people.validator.ValidatorLoader;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.commons.validator.Field;
import org.apache.commons.validator.Form;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorResources;
import org.apache.commons.validator.util.ValidatorUtils;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.html.Constants;
import org.apache.struts.taglib.html.FormTag;
import org.apache.struts.validator.FieldChecks;

/**
 * Created by IntelliJ IDEA. User: sergio Date: Sep 29, 2003 Time: 4:37:30 PM To
 * change this template use Options | File Templates.
 */
public class FieldLabelTag extends MessageTag {

    private String m_fieldName;
    private String m_fieldBean;
    private String m_fieldLabel;

    // classe css per segnalare l'errore configurabile nel tag (default:
    // 'labelInputFieldError' )
    private String m_errCssClass = "labelInputFieldError";

    public FieldLabelTag() {
	super();
	m_fieldName = null;
	m_fieldBean = Constants.BEAN_KEY;
	m_fieldLabel = null;
    }

    public void release() {
	super.release();
	m_fieldName = null;
	m_fieldBean = Constants.BEAN_KEY;
	m_fieldLabel = null;
    }

    public int doStartTag() throws JspException {
	// Look up this key to see if its a field of the current form
	boolean requiredField = false;
	boolean validationError = false;

	int firstDot = m_fieldName.indexOf('.');
	String dataProperty = m_fieldName.substring(0, firstDot);

	TagUtils tagUtils = TagUtils.getInstance();

	String fieldProperty = m_fieldName.substring(firstDot + 1);
	AbstractPplProcess process = (AbstractPplProcess) tagUtils.lookup(
		pageContext, m_fieldBean, null, null);

	Logger.debug("---------------- PROCESS NAME ====> "
		+ process.getProcessName());
	IStep step = process.getView().getCurrentActivity().getCurrentStep();

	ValidatorResources resources = getValidatorResources(process);

	Locale locale = (Locale) pageContext.getSession().getAttribute(
		PeopleConstants.USER_LOCALE_KEY);

	if (locale == null) {
	    locale = Locale.getDefault();
	}

	FormTag formTag = (FormTag) pageContext.getAttribute(
		Constants.FORM_KEY, PageContext.REQUEST_SCOPE);

	String formName = step.getFullIdentifier();

	if (resources != null) {
	    Form form = resources.get(locale, formName);

	    if (form != null) {

		@DeveloperTaskStart(name = "Riccardo Foraf�", date = "14.05.2011", bugDescription = "Errato calcolo del nome generale della proprit� "
			+ "nel caso in cui vi siano pi� propriet� indicizzate concatenate.", description = "Cambiata la sostituzione degli indici"
			+ "[n] delle propriet� con l'utilizzo di un'espressione regolare.")
		// int indexedPropRightBrack = fieldProperty.lastIndexOf(']');
		// int indexedPropLeftBrack = fieldProperty.lastIndexOf('[');
		// if (indexedPropLeftBrack != -1 && indexedPropRightBrack != -1
		// && indexedPropLeftBrack < indexedPropRightBrack) {
		// String generalIndexPropertyName =
		// fieldProperty.substring(0,indexedPropLeftBrack+1) +
		// fieldProperty.substring(indexedPropRightBrack);
		// fieldProperty = generalIndexPropertyName;
		// }
		String buffer = fieldProperty.replaceAll("\\[\\d+\\]", "[]");
		fieldProperty = buffer;
		@DeveloperTaskEnd(name = "Riccardo Forafo", date = "14.05.2011")
		Field field = (Field) form.getFieldMap().get(fieldProperty);

		if (field != null) {
		    if (field.isDependency("required")) {
			requiredField = true;
		    }

		    if (field.isDependency("requiredif")) {
			ActionErrors errors = (ActionErrors) pageContext
				.getRequest().getAttribute(Globals.ERROR_KEY);
			Validator validator = PeopleValidatorUtil
				.getValidatorForStep(step, process, process
					.getContext().getSession()
					.getServletContext(),
					(HttpServletRequest) pageContext
						.getRequest(), errors);
			Object bean = validator
				.getParameterValue(org.apache.commons.validator.Validator.BEAN_PARAM);

			int i = 0;
			String fieldJoin = "AND";
			if (!GenericValidator.isBlankOrNull(field
				.getVarValue("fieldJoin"))) {
			    fieldJoin = field.getVarValue("fieldJoin");
			}

			if (fieldJoin.equalsIgnoreCase("AND")) {
			    requiredField = true;
			}

			while (!GenericValidator.isBlankOrNull(field
				.getVarValue("field[" + i + "]"))) {
			    String dependProp = field.getVarValue("field[" + i
				    + "]");
			    String dependTest = field.getVarValue("fieldTest["
				    + i + "]");
			    String dependTestValue = field
				    .getVarValue("fieldValue[" + i + "]");
			    String dependIndexed = field
				    .getVarValue("fieldIndexed[" + i + "]");

			    if (dependIndexed == null) {
				dependIndexed = "false";
			    }

			    String dependVal = null;
			    boolean thisRequired = false;
			    if (field.isIndexed()
				    && dependIndexed.equalsIgnoreCase("true")) {
				String key = field.getKey();
				if ((key.indexOf("[") > -1)
					&& (key.indexOf("]") > -1)) {
				    String ind = key.substring(0,
					    key.indexOf(".") + 1);
				    dependProp = ind + dependProp;
				}
			    }

			    dependVal = ValidatorUtils.getValueAsString(bean,
				    dependProp);
			    if (dependTest.equals(FieldChecks.FIELD_TEST_NULL)) {
				if ((dependVal != null)
					&& (dependVal.length() > 0)) {
				    thisRequired = false;
				} else {
				    thisRequired = true;
				}
			    }

			    if (dependTest
				    .equals(FieldChecks.FIELD_TEST_NOTNULL)) {
				if ((dependVal != null)
					&& (dependVal.length() > 0)) {
				    thisRequired = true;
				} else {
				    thisRequired = false;
				}
			    }

			    if (dependTest.equals(FieldChecks.FIELD_TEST_EQUAL)) {
				thisRequired = dependTestValue
					.equalsIgnoreCase(dependVal);
			    }

			    if (fieldJoin.equalsIgnoreCase("AND")) {
				requiredField = requiredField && thisRequired;
			    } else {
				requiredField = requiredField || thisRequired;
			    }

			    i++;
			}

		    }

		}
	    }
	}
	boolean isErrors = false;
	try {

	    ActionErrors errors = (ActionErrors) pageContext.getRequest()
		    .getAttribute(Globals.ERROR_KEY);

	    String errorsFieldPropertyAccessor = m_fieldName
		    .substring(firstDot + 1);
	    if (errors.get(errorsFieldPropertyAccessor).hasNext())
		isErrors = true;

	} catch (Exception ex) {
	}

	// Eseguo il tag
	if (isErrors)
	    tagUtils.write(pageContext, "<span class=\"" + m_errCssClass
		    + "\">");
	int returnValue = this.labelToHtml(requiredField, process, locale);
	if (isErrors)
	    tagUtils.write(pageContext, "</span>");

	// Return
	return returnValue;

    }

    public int labelToHtml(boolean isRequiredField, AbstractPplProcess process,
	    Locale locale) throws JspException {

	TagUtils tagUtils = TagUtils.getInstance();

	String key = this.key;
	// ******** RETROCOMPATIBILITA' ************************
	// Per retrocompatibilit� con le label che sostituivano {0} con il
	// carattere *
	setArg0("");
	// *****************************************************
	if (key == null) {
	    Object value = tagUtils.lookup(pageContext, name, property, scope);
	    if (value != null && !(value instanceof String)) {
		JspException e = new JspException(messages.getMessage(
			"message.property", key));
		tagUtils.saveException(pageContext, e);
		throw e;
	    }
	    key = (String) value;
	}
	Object args[] = new Object[5];
	args[0] = arg0;
	args[1] = arg1;
	args[2] = arg2;
	args[3] = arg3;
	args[4] = arg4;

	City city = (City) pageContext.getSession().getAttribute("City");
	String comuneKey = null;
	if (city != null)
	    comuneKey = city.getOid();

	String rendering = "<label for=\"" + this.m_fieldName + "\">";

	String message = MessageBundleHelper.message(key, args,
		process.getProcessName(), comuneKey, locale);
	if (this.m_fieldLabel == null) {
	    if (message == null)
		message = key;
	} else {
	    message = this.m_fieldLabel;
	}

	rendering += message;

	if (pageContext.getAttribute("requiredFieldsPresents",
		PageContext.REQUEST_SCOPE) == null) {
	    pageContext.setAttribute("requiredFieldsPresents", Boolean.FALSE,
		    PageContext.REQUEST_SCOPE);
	}
	if (isRequiredField) {
	    rendering += " (*) ";
	    pageContext.setAttribute("requiredFieldsPresents", Boolean.TRUE,
		    PageContext.REQUEST_SCOPE);
	}

	rendering += "</label>";

	tagUtils.write(pageContext, rendering);
	return 0;
    }

    public int doEndTag() throws JspException {
	return super.doEndTag();
    }

    // FieldName
    public String getFieldName() {
	return m_fieldName;
    }

    public void setFieldName(String p_fieldName) {
	m_fieldName = p_fieldName;
    }

    // FieldBean
    public String getFieldBean() {
	return m_fieldBean;
    }

    public void setFieldBean(String p_fieldBean) {
	m_fieldBean = p_fieldBean;
    }

    // ErrCssClass
    public String getErrCssClass() {
	return m_errCssClass;
    }

    public void setErrCssClass(String m_errCssClass) {
	this.m_errCssClass = m_errCssClass;
    }

    // FieldLabel
    public String getFieldLabel() {
	return m_fieldLabel;
    }

    public void setFieldLabel(String p_fieldLabel) {
	m_fieldLabel = p_fieldLabel;
    }

    // Validator
    public ValidatorResources getValidatorResources(AbstractPplProcess process) {
	try {

	    return ValidatorLoader.getInstance().getResourcesForProcess(
		    process, process.getCommune());

	} catch (Exception ex) {
	}
	return null;
    }
}
