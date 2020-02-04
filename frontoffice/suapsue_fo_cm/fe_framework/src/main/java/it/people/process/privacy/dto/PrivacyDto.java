/**
 * 
 */
package it.people.process.privacy.dto;

import it.people.process.dto.PeopleDto;

/**
 * @author Riccardo Forafò - Engineering Ingegneria Informatica - Genova
 *         02/giu/2012 17:04:05
 */
public class PrivacyDto extends PeopleDto {

    private boolean accepted = false;

    public PrivacyDto() {

    }

    /**
     * @return the accepted
     */
    public final boolean isAccepted() {
	return this.accepted;
    }

    /**
     * @param accepted
     *            the accepted to set
     */
    public final void setAccepted(boolean accepted) {
	this.accepted = accepted;
    }

}
