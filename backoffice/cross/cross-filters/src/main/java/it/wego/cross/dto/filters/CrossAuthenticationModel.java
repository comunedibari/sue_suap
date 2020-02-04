/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.filters;

/**
 *
 * @author piergiorgio
 */
public class CrossAuthenticationModel implements java.io.Serializable {

    public static final String AUTHENTICATIONDATA = "it.wego.cross.authentication";
    public static final String CAS = "JASIG-CAS";
    public static final String SIRAC = "SIRAC-PEOPLE";
    public static final String FEDERA = "ICAR-FEDERA";
    private String userId;
    private String authenticationSystem;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAuthenticationSystem() {
        return authenticationSystem;
    }

    public void setAuthenticationSystem(String authenticationSystem) {
        this.authenticationSystem = authenticationSystem;
    }
}
