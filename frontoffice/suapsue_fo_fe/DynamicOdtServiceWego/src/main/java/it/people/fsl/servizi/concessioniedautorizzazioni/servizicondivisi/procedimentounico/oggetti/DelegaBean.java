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

public class DelegaBean extends BaseBean 
{
    public DelegaBean()
    {
        codiceCausaleDelega = "";
        descrizioneCausaleDelega = "";
        altraCausaleDelega = "";
        codiceTramiteDelega = "";
        descrizioneTramitedelega = "";
        altroTramiteDelega = "";
        dataSottoscrizioneDelega = "";
        luogoOriginaleDelega = "";
    }
    private String codiceCausaleDelega;
    private String descrizioneCausaleDelega;
    private String altraCausaleDelega;
    private String codiceTramiteDelega;
    private String descrizioneTramitedelega;
    private String altroTramiteDelega;
    private String dataSottoscrizioneDelega;
    private String luogoOriginaleDelega;
    
    public String getAltraCausaleDelega() {
        return altraCausaleDelega;
    }
    public void setAltraCausaleDelega(String altraCausaleDelega) {
        this.altraCausaleDelega = altraCausaleDelega;
    }
    public String getAltroTramiteDelega() {
        return altroTramiteDelega;
    }
    public void setAltroTramiteDelega(String altroTramiteDelega) {
        this.altroTramiteDelega = altroTramiteDelega;
    }
    public String getCodiceCausaleDelega() {
        return codiceCausaleDelega;
    }
    public void setCodiceCausaleDelega(String codiceCausaleDelega) {
        this.codiceCausaleDelega = codiceCausaleDelega;
    }
    public String getCodiceTramiteDelega() {
        return codiceTramiteDelega;
    }
    public void setCodiceTramiteDelega(String codiceTramiteDelega) {
        this.codiceTramiteDelega = codiceTramiteDelega;
    }
    public String getDataSottoscrizioneDelega() {
        return dataSottoscrizioneDelega;
    }
    public void setDataSottoscrizioneDelega(String dataSottoscrizioneDelega) {
        this.dataSottoscrizioneDelega = dataSottoscrizioneDelega;
    }
    public String getDescrizioneCausaleDelega() {
        return descrizioneCausaleDelega;
    }
    public void setDescrizioneCausaleDelega(String descrizioneCausaleDelega) {
        this.descrizioneCausaleDelega = descrizioneCausaleDelega;
    }
    public String getDescrizioneTramitedelega() {
        return descrizioneTramitedelega;
    }
    public void setDescrizioneTramitedelega(String descrizioneTramitedelega) {
        this.descrizioneTramitedelega = descrizioneTramitedelega;
    }
    public String getLuogoOriginaleDelega() {
        return luogoOriginaleDelega;
    }
    public void setLuogoOriginaleDelega(String luogoOriginaleDelega) {
        this.luogoOriginaleDelega = luogoOriginaleDelega;
    }
}
