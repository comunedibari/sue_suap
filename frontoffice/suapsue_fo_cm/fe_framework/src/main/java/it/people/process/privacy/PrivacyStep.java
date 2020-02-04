/**
 * 
 */
package it.people.process.privacy;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;

import it.people.DtoStep;
import it.people.IValidationErrors;
import it.people.core.exception.MappingException;
import it.people.parser.exception.ParserException;
import it.people.process.AbstractPplProcess;
import it.people.process.PplData;
import it.people.process.data.AbstractData;
import it.people.process.dto.PeopleDto;
import it.people.taglib.PrivacyDisclaimerTag;
import it.people.util.MessageBundleHelper;
import it.people.wrappers.IRequestWrapper;

/**
 * @author Riccardo Forafò - Engineering Ingegneria Informatica - Genova
 *         02/giu/2012 16:57:25
 */
public class PrivacyStep extends DtoStep {

    public static final String JSP_PATH_ROOT = "/framework/view/generic";
    public static final String JSP_NESTED_PATH = "/html";
    public static final String JSP_NAME = "privacy.jsp";
    public static final String DEFAULT_JSP_PATH = "/framework/view/generic/default/html/privacy.jsp";
    public static final String STEP_DEFAULT_NAME = "Privacy";
    public static final String STEP_NAME_KEY = "privacy.step.name";
    public static final String STEP_DTO_CLASS_NAME = "it.people.process.privacy.dto.PrivacyDto";

    /*
     * (non-Javadoc)
     * 
     * @see it.people.IStep#service(it.people.process.AbstractPplProcess,
     * it.people.wrappers.IRequestWrapper)
     */
    @Override
    public void service(AbstractPplProcess process, IRequestWrapper request)
	    throws IOException, ServletException {

	if (request.getUnwrappedRequest().getSession() != null) {
	    request.getUnwrappedRequest().getSession()
		    .setAttribute("fwkPrivacy", "fwkPrivacy");
	}

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.people.IStep#logicalValidate(it.people.process.AbstractPplProcess,
     * it.people.wrappers.IRequestWrapper, it.people.IValidationErrors)
     */
    @Override
    public boolean logicalValidate(AbstractPplProcess process,
	    IRequestWrapper request, IValidationErrors errors)
	    throws ParserException {

	AbstractData data = (AbstractData) process.getData();
	if (data.isShowPrivacyDisclaimer()
		&& data.isPrivacyDisclaimerRequireAcceptance()) {
	    if (!data.isPrivacyDisclaimerAccepted()) {
		String checkboxLabel = MessageBundleHelper.message(
			PrivacyDisclaimerTag.PRIVACY_CHECKBOX_LABEL_KEY, null,
			process.getProcessName(),
			process.getCommune().getKey(), process.getContext()
				.getLocale());
		if (checkboxLabel != null
			&& !checkboxLabel.equalsIgnoreCase("")) {
		    errors.add("errors.privacy.required.acceptance",
			    checkboxLabel);
		} else {
		    errors.add("errors.privacy.required.acceptance", "");
		}
	    }
	    return data.isPrivacyDisclaimerAccepted();
	}

	return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.people.DtoStep#writeDto(it.people.process.PplData,
     * it.people.process.dto.PeopleDto)
     */
    @Override
    public void writeDto(PplData data, PeopleDto dto) throws MappingException {
	// TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see it.people.DtoStep#readDto(it.people.process.PplData,
     * it.people.process.dto.PeopleDto)
     */
    @Override
    public void readDto(PplData data, PeopleDto dto) throws MappingException {
	// TODO Auto-generated method stub

    }

}
