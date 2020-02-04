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
import it.people.PeopleConstants;
import it.people.process.AbstractPplProcess;
import it.people.util.MessageBundleHelper;
import it.people.action.SaveProcess;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.html.Constants;

public class ShowSaveMessageTag extends TagSupport {

    private static final long serialVersionUID = 2680252780836900431L;

    private static Logger logger = Logger.getLogger(ShowSaveMessageTag.class);

    private static final String DEFAULT_SAVE_MESSAGE_KEY = "save.message";
    private static final String DEFAULT_SAVE_ERROR_MESSAGE_KEY = "errors.save.message";
    private static final String DEFAULT_CSS_CLASS = "saveMessage";
    private static final String DEFAULT_IMG = "/people/img/jQuery/info.png";
    private static final String DEFAULT_IMG_ERROR = "/people/img/jQuery/error.png";
    private static final String DEFAULT_IMG_WIDTH = "32";
    private static final String DEFAULT_IMG_HIGHT = "32";

    private String saveMessageKey = DEFAULT_SAVE_MESSAGE_KEY;
    private String saveErrorMessageKey = DEFAULT_SAVE_ERROR_MESSAGE_KEY;
    private String cssClass = DEFAULT_CSS_CLASS;
    private String image = DEFAULT_IMG;
    private String imageError = DEFAULT_IMG_ERROR;
    private String imageWidth = DEFAULT_IMG_WIDTH;
    private String imageHeight = DEFAULT_IMG_HIGHT;

    public ShowSaveMessageTag() {
	super();

    }

    public void release() {
	super.release();

    }

    /**
     * @return the saveMessageKey
     */
    protected final String getSaveMessageKey() {
	return (this.saveMessageKey == null || (this.saveMessageKey != null && this.saveMessageKey
		.equalsIgnoreCase(""))) ? DEFAULT_SAVE_MESSAGE_KEY
		: this.saveMessageKey;
    }

    /**
     * @param saveMessageKey
     *            the saveMessageKey to set
     */
    public final void setSaveMessageKey(String saveMessageKey) {
	this.saveMessageKey = saveMessageKey;
    }

    /**
     * @return the saveErrorMessageKey
     */
    protected final String getSaveErrorMessageKey() {
	return (this.saveErrorMessageKey == null || (this.saveErrorMessageKey != null && this.saveErrorMessageKey
		.equalsIgnoreCase(""))) ? DEFAULT_SAVE_ERROR_MESSAGE_KEY
		: this.saveErrorMessageKey;
    }

    /**
     * @param saveErrorMessageKey
     *            the saveErrorMessageKey to set
     */
    public final void setSaveErrorMessageKey(String saveErrorMessageKey) {
	this.saveErrorMessageKey = saveErrorMessageKey;
    }

    /**
     * @return the cssClass
     */
    protected final String getCssClass() {
	return (this.cssClass == null || (this.cssClass != null && this.cssClass
		.equalsIgnoreCase(""))) ? DEFAULT_CSS_CLASS : this.cssClass;
    }

    /**
     * @param cssClass
     *            the cssClass to set
     */
    public final void setCssClass(String cssClass) {
	this.cssClass = cssClass;
    }

    /**
     * @return the image
     */
    protected final String getImage() {
	return (this.image == null || (this.image != null && this.image
		.equalsIgnoreCase(""))) ? DEFAULT_IMG : this.image;
    }

    /**
     * @param image
     *            the image to set
     */
    public final void setImage(String image) {
	this.image = image;
    }

    /**
     * @return the imageError
     */
    protected final String getImageError() {
	return (this.imageError == null || (this.imageError != null && this.imageError
		.equalsIgnoreCase(""))) ? DEFAULT_IMG_ERROR : this.imageError;
    }

    /**
     * @param imageError
     *            the imageError to set
     */
    public final void setImageError(String imageError) {
	this.imageError = imageError;
    }

    /**
     * @return the imageWidth
     */
    protected final String getImageWidth() {
	return (this.imageWidth == null || (this.imageWidth != null && this.imageWidth
		.equalsIgnoreCase(""))) ? DEFAULT_IMG_WIDTH : this.imageWidth;
    }

    /**
     * @param imageWidth
     *            the imageWidth to set
     */
    public final void setImageWidth(String imageWidth) {
	this.imageWidth = imageWidth;
    }

    /**
     * @return the imageHeight
     */
    protected final String getImageHeight() {
	return (this.imageHeight == null || (this.imageHeight != null && this.imageHeight
		.equalsIgnoreCase(""))) ? DEFAULT_IMG : this.imageHeight;
    }

    /**
     * @param imageHeight
     *            the imageHeight to set
     */
    public final void setImageHeight(String imageHeight) {
	this.imageHeight = imageHeight;
    }

    public int doStartTag() throws JspException {

	Locale locale = (Locale) pageContext.getSession().getAttribute(
		PeopleConstants.USER_LOCALE_KEY);

	if (locale == null)
	    locale = pageContext.getRequest().getLocale();

	if (locale == null)
	    locale = Locale.getDefault();

	try {
	    AbstractPplProcess process = null;
	    try {
		process = (AbstractPplProcess) TagUtils.getInstance().lookup(
			pageContext, Constants.BEAN_KEY, null);
	    } catch (Exception ex) {
	    }
	    ;

	    // se process == null, sono nel caso di formbean diversi
	    // da AbstractPplProcess oe mancanza di formbeans
	    return this.saveMessage(process, locale);

	} catch (JspException jspe) {
	    // in caso di errore tenta comunque di mostrare il messaggio di
	    // default
	    logger.warn("Problema nella localizzazione del messaggio:\n"
		    + jspe.getMessage());
	    return super.doStartTag();
	}
    }

    public int saveMessage(AbstractPplProcess process, Locale locale)
	    throws JspException {

	HttpServletRequest request = (HttpServletRequest) pageContext
		.getRequest();
	HttpSession session = null;
	if (request != null) {
	    session = request.getSession();
	}

	if (session == null) {
	    return 0;
	} else {
	    Object saveStatus = session.getAttribute(SaveProcess.SAVE_STATUS);
	    if (saveStatus == null) {
		return 0;
	    }
	}

	City city = (City) pageContext.getSession().getAttribute("City");
	String comuneKey = null;
	if (city != null)
	    comuneKey = city.getOid();

	if (logger.isDebugEnabled())
	    logger.debug("Localizzazione per comune key [" + comuneKey + "]");

	String message = this.getSaveMessageKey();
	if (process == null) {
	    // messaggio relativo al framework
	    message = MessageBundleHelper.message(this.getSaveMessageKey(),
		    null, comuneKey, locale);
	} else {
	    // messaggio relativo allo svolgimento di una pratica
	    message = MessageBundleHelper.message(this.getSaveMessageKey(),
		    null, process.getProcessName(), comuneKey, locale);
	}

	StringBuilder tag = new StringBuilder();
	tag.append("<script type=\"text/javascript\">")
		.append("    function showSaveMessage() {")
		.append("        $.msgBox({")
		.append("            title: \"\",")
		.append("            content: \"")
		.append(message)
		.append("\",")
		.append("            type: \"info\",")
		.append("            showButtons: false,")
		.append("            opacity: 0.9,")
		.append("            autoClose:true,")
		.append("            afterClose: function() {")
		.append("                var removeElem = document.getElementById('jQueryStaticSaveMessageBoxContent');")
		.append("                removeElem.parentNode.removeChild(removeElem);")
		.append("            }")
		.append("        });")
		.append("    }")
		.append("</script>")
		.append("")
		.append("            <div id=\"jQueryStaticSaveMessageBoxContent\">")
		.append("                <div class=\"jQueryMsgBoxImage\"><img src=\"")
		.append(this.getImage())
		.append("\" width=\"")
		.append(this.getImageWidth())
		.append("\" height=\"")
		.append(this.getImageHeight())
		.append("\" alt=\"")
		.append(message)
		.append("\" onload=\"javascript:showSaveMessage()\" /></div><div class=\"jQueryMsgBoxContent\">")
		.append(message).append("</div>").append("            </div>");
	session.removeAttribute(SaveProcess.SAVE_STATUS);

	JspWriter out = pageContext.getOut();
	try {
	    out.write(tag.toString());
	} catch (IOException e) {
	    throw new JspException(e);
	}

	return 0;
    }

    public int doEndTag() throws JspException {
	return super.doEndTag();
    }

}