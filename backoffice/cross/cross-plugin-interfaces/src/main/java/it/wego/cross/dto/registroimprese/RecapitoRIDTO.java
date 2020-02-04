/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto.registroimprese;

import java.util.List;
import java.util.Map;

/**
 *
 * @author giuseppe
 */
public class RecapitoRIDTO {

    private Map<String, String> eMail;
    private Map<String, String> telefono;
    private List<String> sitoWeb;

    public Map<String, String> geteMail() {
        return eMail;
    }

    public void seteMail(Map<String, String> eMail) {
        this.eMail = eMail;
    }

    public Map<String, String> getTelefono() {
        return telefono;
    }

    public void setTelefono(Map<String, String> telefono) {
        this.telefono = telefono;
    }

    public List<String> getSitoWeb() {
        return sitoWeb;
    }

    public void setSitoWeb(List<String> sitoWeb) {
        this.sitoWeb = sitoWeb;
    }
}
