/**
 * 
 */
package it.people.taglib;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import it.people.util.IUserPanel;

/**
 * @author Riccardo Forafò - Engineering Ingegneria Informatica - Genova
 *         29/ott/2012 11:43:12
 */
public class UserPanelTag extends TagSupport {

    private static final long serialVersionUID = 4868852078846716030L;

    public int doStartTag() throws JspException {

	try {
	    String parameter = String.valueOf(pageContext.getRequest()
		    .getAttribute(IUserPanel.USER_PANEL_PARAMETER_KEY));
	    if (parameter == null) {
		parameter = IUserPanel.ViewTypes.main.getViewName();
	    }
	    pageContext.include("/include/userPanel/main.jsp?parameter="
		    + parameter);
	} catch (IOException e) {
	    throw new JspException();
	} catch (ServletException e) {
	    throw new JspException(e);
	}

	return SKIP_BODY;
    }

}
