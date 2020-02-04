/**
 * 
 */
package it.people.process.sign;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.people.process.AbstractPplProcess;
import it.people.process.sign.entity.SigningData;

/**
 * @author Riccardo Forafò - Engineering Ingegneria Informatica - Genova
 *         27/mag/2012 16:53:11
 */
public interface IPdfSignStep {

    public SigningData getSigningData(AbstractPplProcess pplProcess,
	    HttpServletRequest request, HttpServletResponse response);

}
