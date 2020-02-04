/**
 * 
 */
package it.people.vsl.transport;

/**
 * @author Riccardo Forafò - Engineering Ingegneria Informatica - Genova
 *         08/ott/2012 16:46:02
 */
public enum PplMailType {

    generalError(true), sendError(true), notSendProcessDumpError(true), userSignalledBug(
	    true), userSuggestion(true), userSignalledBugUserReceipt(false), userSuggestionUserReceipt(
	    false), newAccreditation(true), sendErrorNotifyUser(false), sendNotifyUser(
	    false);

    private boolean forAdmins;

    /**
     * @param forAdmins
     */
    private PplMailType(final boolean forAdmins) {
	this.setForAdmins(forAdmins);
    }

    /**
     * @param forAdmins
     *            the forAdmins to set
     */
    private void setForAdmins(boolean forAdmins) {
	this.forAdmins = forAdmins;
    }

    /**
     * @return the forAdmins
     */
    public final boolean isForAdmins() {
	return this.forAdmins;
    }

}
