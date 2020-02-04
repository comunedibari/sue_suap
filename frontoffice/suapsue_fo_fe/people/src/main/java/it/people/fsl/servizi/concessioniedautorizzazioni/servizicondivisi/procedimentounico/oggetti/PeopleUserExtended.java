/**
 * 
 */
package it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti;

import it.people.core.PplUser;

/**
 * @author Riccardo Forafò - Engineering Ingegneria Informatica - Genova
 * Oct 25, 2013
 *
 */
public class PeopleUserExtended extends PplUser {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6290629449573716766L;

	private boolean isBookmarkAdmin = false;
	
	private boolean isPUAdmin = false;

	/**
	 * @return the isBookmarkAdmin
	 */
	public final boolean isBookmarkAdmin() {
		return isBookmarkAdmin;
	}

	/**
	 * @param isBookmarkAdmin the isBookmarkAdmin to set
	 */
	public final void setBookmarkAdmin(boolean isBookmarkAdmin) {
		this.isBookmarkAdmin = isBookmarkAdmin;
	}

	/**
	 * @return the isPUAdmin
	 */
	public final boolean isPUAdmin() {
		return isPUAdmin;
	}

	/**
	 * @param isPUAdmin the isPUAdmin to set
	 */
	public final void setPUAdmin(boolean isPUAdmin) {
		this.isPUAdmin = isPUAdmin;
	}
	
}
