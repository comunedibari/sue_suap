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
 * http://www.osor.eu/eupl
 * 
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
**/
package it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Sportello {
    public Sportello() {
        codiceSportello="";
        descrizioneSportello="";
        telefono="";
        fax="";
        email="";
        pec="";
        indirizzo="";
        cap="";
        citta="";
        provincia="";
        rup="";
        flgAttivo="N";
        flgPu="N";
        flgSu="N";
        procedimenti = new ArrayList();
    }
    private Log logger = LogFactory.getLog(this.getClass());
    private String codiceSportello;
    private String descrizioneSportello;
    private String telefono;
    private String fax;
    private String email;
    private String pec;
    private String indirizzo;
    private String cap;
    private String citta;
    private String provincia;
    private String rup;
    private String flgAttivo;
    private String flgPu;
    private String flgSu;
    
    private List procedimenti;
        
    /**
     * @return Returns the comune.
     */
    public List getProcedimenti() {
        return procedimenti;
    }
    /**
     * @param procedimento The procedimento to set.
     */
    public void setProcedimenti(List procedimenti) {
        this.procedimenti = procedimenti;
    }
    
    public void addProcedimenti(Procedimento bean){
        try {
            procedimenti.add(bean);
        } catch (Exception e) {
            logger.error(e);
        }       
    }
    /**
     * @return Returns the codiceSportello.
     */
    public String getCodiceSportello() {
        return codiceSportello;
    }
    /**
     * @param codiceSportello The codiceSportello to set.
     */
    public void setCodiceSportello(String codiceSportello) {
        this.codiceSportello = codiceSportello;
    }
    /**
     * @return Returns the cap.
     */
    public String getCap() {
        return cap;
    }
    /**
     * @param cap The cap to set.
     */
    public void setCap(String cap) {
        this.cap = cap;
    }
    /**
     * @return Returns the citta.
     */
    public String getCitta() {
        return citta;
    }
    /**
     * @param citta The citta to set.
     */
    public void setCitta(String citta) {
        this.citta = citta;
    }
    /**
     * @return Returns the descrizioneSportello.
     */
    public String getDescrizioneSportello() {
        return descrizioneSportello;
    }
    /**
     * @param descrizioneSportello The descrizioneSportello to set.
     */
    public void setDescrizioneSportello(String descrizioneSportello) {
        this.descrizioneSportello = descrizioneSportello;
    }
    /**
     * @return Returns the email.
     */
    public String getEmail() {
        return email;
    }
    /**
     * @param email The email to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * @return Returns the fax.
     */
    public String getFax() {
        return fax;
    }
    /**
     * @param fax The fax to set.
     */
    public void setFax(String fax) {
        this.fax = fax;
    }
    /**
     * @return Returns the flgAttivo.
     */
    public String getFlgAttivo() {
        return flgAttivo;
    }
    /**
     * @param flgAttivo The flgAttivo to set.
     */
    public void setFlgAttivo(String flgAttivo) {
        this.flgAttivo = flgAttivo;
    }
    /**
     * @return Returns the flgPu.
     */
    public String getFlgPu() {
        return flgPu;
    }
    /**
     * @param flgPu The flgPu to set.
     */
    public void setFlgPu(String flgPu) {
        this.flgPu = flgPu;
    }
    /**
     * @return Returns the flgSu.
     */
    public String getFlgSu() {
        return flgSu;
    }
    /**
     * @param flgSu The flgSu to set.
     */
    public void setFlgSu(String flgSu) {
        this.flgSu = flgSu;
    }
    /**
     * @return Returns the indirizzo.
     */
    public String getIndirizzo() {
        return indirizzo;
    }
    /**
     * @param indirizzo The indirizzo to set.
     */
    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }
    /**
     * @return Returns the pec.
     */
    public String getPec() {
        return pec;
    }
    /**
     * @param pec The pec to set.
     */
    public void setPec(String pec) {
        this.pec = pec;
    }
    /**
     * @return Returns the provincia.
     */
    public String getProvincia() {
        return provincia;
    }
    /**
     * @param provincia The provincia to set.
     */
    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }
    /**
     * @return Returns the telefono.
     */
    public String getTelefono() {
        return telefono;
    }
    /**
     * @param telefono The telefono to set.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    /**
     * @return Returns the rup.
     */
    public String getRup() {
        return rup;
    }
    /**
     * @param rup The rup to set.
     */
    public void setRup(String rup) {
        this.rup = rup;
    }
}
