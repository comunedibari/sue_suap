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

import it.people.process.common.entity.Attachment;
import it.people.process.common.entity.SignedAttachment;
import it.people.process.common.entity.UnsignedSummaryAttachment;
import it.people.process.sign.entity.SignedInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SportelloBean implements Serializable {

	private static final long serialVersionUID = 6043426102334390231L;
	
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
    private String id_protocollo;
    private String id_mail_server;
    private String id_backoffice;
    //<RF - Aggiunta per spostamento parametri PEC e nuova gestione Connects
    private String template_oggetto_ricevuta;
    private String template_corpo_ricevuta;
    private String template_nome_file_zip;
    private String send_zip_file;
    private String send_single_files;
    private String send_xml;
    private String send_signature;
    private String send_protocollo;
    private String template_oggetto_mail_suap;
    private String send_ricevuta_dopo_protocollazione;
    private String send_ricevuta_dopo_invio_bo;
    //RF - Aggiunta per spostamento parametri PEC e nuova gestione Connects>
    //<RF - Aggiunta per integrazione PayER
    private String ae_codice_utente;
    private String ae_codice_ente;
    private String ae_tipo_ufficio;
    private String ae_codice_ufficio;
    private String ae_tipologia_servizio;
    //RF - Aggiunta per integrazione PayER>
    private List codProcedimenti;
    private int idx;
//    private RiepilogoFirmatoBean sInfo;
//    private UnsignedSummaryAttachment riepilogoNonFirmato;
    private ArrayList listaAllegati;
    private ArrayList listaRiepiloghiSpacchettati;
    private Map datiSegnaturaCittadino = new HashMap();
    private Map datiProtocollo = new HashMap();

	private String attachmentsUploadMaximumSize;
    private String attachmentsUploadUM;
    private int attachmentsUploadMaximumValue;
    
    private boolean riversamentoAutomatico;
    
    private boolean showPrintBlankTemplate;
    private boolean showPDFVersion;
    private boolean onLineSign;
    private boolean offLineSign;
    private boolean flgOptionAllegati;
    private boolean flgOggettoRiepilogo;
    private boolean flgOggettoRicevuta;
    
    public SportelloBean(){
		this.codiceSportello="";
		this.descrizioneSportello="";
		this.telefono="";
		this.fax="";
		this.email="";
		this.pec="";
		this.indirizzo="";
		this.cap="";
		this.citta="";
		this.provincia="";
		this.rup="";
		this.flgAttivo="";
		this.flgPu="";
		this.flgSu="";
		this.codProcedimenti=new ArrayList();
		this.idx=0;
//		this.riepilogoNonFirmato=null;
//		this.sInfo=null;
		this.listaAllegati=new ArrayList();
		this.listaRiepiloghiSpacchettati=new ArrayList();
		
		this.id_protocollo="";
		this.id_mail_server="";
		this.id_backoffice="";
		
		this.attachmentsUploadUM = "";
		
		this.riversamentoAutomatico = false;
		
		this.showPrintBlankTemplate = true;
		this.showPDFVersion = true;
		this.onLineSign = true;
		this.offLineSign = false;
        this.flgOptionAllegati = false;
        this.flgOggettoRiepilogo = true;
        this.flgOggettoRicevuta = true;

        this.setAttachmentsUploadMaximumValue(-1);
                
    }    
    
	public String getCodiceSportello() {
		return codiceSportello;
	}
	public void setCodiceSportello(String codiceSportello) {
		this.codiceSportello = codiceSportello;
	}
	public String getDescrizioneSportello() {
		return descrizioneSportello;
	}
	public void setDescrizioneSportello(String descrizioneSportello) {
		this.descrizioneSportello = descrizioneSportello;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPec() {
		return pec;
	}
	public void setPec(String pec) {
		this.pec = pec;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public String getCap() {
		return cap;
	}
	public void setCap(String cap) {
		this.cap = cap;
	}
	public String getCitta() {
		return citta;
	}
	public void setCitta(String citta) {
		this.citta = citta;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getRup() {
		return rup;
	}
	public void setRup(String rup) {
		this.rup = rup;
	}
	public String getFlgAttivo() {
		return flgAttivo;
	}
	public void setFlgAttivo(String flgAttivo) {
		this.flgAttivo = flgAttivo;
	}
	public String getFlgPu() {
		return flgPu;
	}
	public void setFlgPu(String flgPu) {
		this.flgPu = flgPu;
	}
	public String getFlgSu() {
		return flgSu;
	}
	public void setFlgSu(String flgSu) {
		this.flgSu = flgSu;
	}
	public List getCodProcedimenti() {
		return codProcedimenti;
	}
	public void setCodProcedimenti(List codProcedimenti) {
		this.codProcedimenti = codProcedimenti;
	}
	public void addCodProcedimenti(String codProcedimenti) {
		this.codProcedimenti.add(codProcedimenti);
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

//	public RiepilogoFirmatoBean getSInfo() {
//		return sInfo;
//	}
//
//	public void setSInfo(RiepilogoFirmatoBean info) {
//		sInfo = info;
//	}
//
//	public UnsignedSummaryAttachment getRiepilogoNonFirmato() {
//		return riepilogoNonFirmato;
//	}
//
//	public void setRiepilogoNonFirmato(UnsignedSummaryAttachment riepilogoNonFirmato) {
//		this.riepilogoNonFirmato = riepilogoNonFirmato;
//	}

	public ArrayList getListaAllegati() {
		return listaAllegati;
	}

	public void setListaAllegati(ArrayList listaAllegati) {
		this.listaAllegati = listaAllegati;
	}
	public void addListaAllegati(Attachment allegato) {
		this.listaAllegati.add(allegato);
	}

	public ArrayList getListaRiepiloghiSpacchettati() {
		return listaRiepiloghiSpacchettati;
	}

	public void setListaRiepiloghiSpacchettati(ArrayList listaRiepiloghiSpacchettati) {
		this.listaRiepiloghiSpacchettati = listaRiepiloghiSpacchettati;
	}
	public void addListaRiepiloghiSpacchettati(Attachment allegato) {
		this.listaRiepiloghiSpacchettati.add(allegato);
	}

	public String getId_protocollo() {
		return id_protocollo;
	}

	public void setId_protocollo(String id_protocollo) {
		this.id_protocollo = id_protocollo;
	}

	public String getId_mail_server() {
		return id_mail_server;
	}

	public void setId_mail_server(String id_mail_server) {
		this.id_mail_server = id_mail_server;
	}

	public String getId_backoffice() {
		return id_backoffice;
	}

	public void setId_backoffice(String id_backoffice) {
		this.id_backoffice = id_backoffice;
	}

	/**
	 * @return the template_oggetto_ricevuta
	 */
	public final String getTemplate_oggetto_ricevuta() {
		return this.template_oggetto_ricevuta;
	}

	/**
	 * @param template_oggetto_ricevuta the template_oggetto_ricevuta to set
	 */
	public final void setTemplate_oggetto_ricevuta(String template_oggetto_ricevuta) {
		this.template_oggetto_ricevuta = template_oggetto_ricevuta;
	}

	/**
	 * @return the template_corpo_ricevuta
	 */
	public final String getTemplate_corpo_ricevuta() {
		return this.template_corpo_ricevuta;
	}

	/**
	 * @param template_corpo_ricevuta the template_corpo_ricevuta to set
	 */
	public final void setTemplate_corpo_ricevuta(String template_corpo_ricevuta) {
		this.template_corpo_ricevuta = template_corpo_ricevuta;
	}

	/**
	 * @return the template_nome_file_zip
	 */
	public final String getTemplate_nome_file_zip() {
		return this.template_nome_file_zip;
	}

	/**
	 * @param template_nome_file_zip the template_nome_file_zip to set
	 */
	public final void setTemplate_nome_file_zip(String template_nome_file_zip) {
		this.template_nome_file_zip = template_nome_file_zip;
	}

	/**
	 * @return the send_zip_file
	 */
	public final String getSend_zip_file() {
		return this.send_zip_file;
	}

	/**
	 * @param send_zip_file the send_zip_file to set
	 */
	public final void setSend_zip_file(String send_zip_file) {
		this.send_zip_file = send_zip_file;
	}

	/**
	 * @return the send_single_files
	 */
	public final String getSend_single_files() {
		return this.send_single_files;
	}

	/**
	 * @param send_single_files the send_single_files to set
	 */
	public final void setSend_single_files(String send_single_files) {
		this.send_single_files = send_single_files;
	}

	/**
	 * @return the send_xml
	 */
	public final String getSend_xml() {
		return this.send_xml;
	}

	/**
	 * @param send_xml the send_xml to set
	 */
	public final void setSend_xml(String send_xml) {
		this.send_xml = send_xml;
	}

	/**
	 * @return the send_signature
	 */
	public final String getSend_signature() {
		return this.send_signature;
	}

	/**
	 * @param send_signature the send_signature to set
	 */
	public final void setSend_signature(String send_signature) {
		this.send_signature = send_signature;
	}

	/**
	 * @return the template_oggetto_mail_suap
	 */
	public final String getTemplate_oggetto_mail_suap() {
		return this.template_oggetto_mail_suap;
	}

	/**
	 * @param template_oggetto_mail_suap the template_oggetto_mail_suap to set
	 */
	public final void setTemplate_oggetto_mail_suap(
			String template_oggetto_mail_suap) {
		this.template_oggetto_mail_suap = template_oggetto_mail_suap;
	}

	/**
	 * @return the send_ricevuta_dopo_protocollazione
	 */
	public final String getSend_ricevuta_dopo_protocollazione() {
		return this.send_ricevuta_dopo_protocollazione;
	}

	/**
	 * @param send_ricevuta_dopo_protocollazione the send_ricevuta_dopo_protocollazione to set
	 */
	public final void setSend_ricevuta_dopo_protocollazione(
			String send_ricevuta_dopo_protocollazione) {
		this.send_ricevuta_dopo_protocollazione = send_ricevuta_dopo_protocollazione;
	}

	/**
	 * @return the send_ricevuta_dopo_invio_bo
	 */
	public final String getSend_ricevuta_dopo_invio_bo() {
		return this.send_ricevuta_dopo_invio_bo;
	}

	/**
	 * @param send_ricevuta_dopo_invio_bo the send_ricevuta_dopo_invio_bo to set
	 */
	public final void setSend_ricevuta_dopo_invio_bo(
			String send_ricevuta_dopo_invio_bo) {
		this.send_ricevuta_dopo_invio_bo = send_ricevuta_dopo_invio_bo;
	}

	/**
	 * @return the ae_codice_utente
	 */
	public final String getAe_codice_utente() {
		return this.ae_codice_utente;
	}

	/**
	 * @param ae_codice_utente the ae_codice_utente to set
	 */
	public final void setAe_codice_utente(String ae_codice_utente) {
		this.ae_codice_utente = ae_codice_utente;
	}

	/**
	 * @return the ae_codice_ente
	 */
	public final String getAe_codice_ente() {
		return this.ae_codice_ente;
	}

	/**
	 * @param ae_codice_ente the ae_codice_ente to set
	 */
	public final void setAe_codice_ente(String ae_codice_ente) {
		this.ae_codice_ente = ae_codice_ente;
	}

	/**
	 * @return the ae_tipo_ufficio
	 */
	public final String getAe_tipo_ufficio() {
		return this.ae_tipo_ufficio;
	}

	/**
	 * @param ae_tipo_ufficio the ae_tipo_ufficio to set
	 */
	public final void setAe_tipo_ufficio(String ae_tipo_ufficio) {
		this.ae_tipo_ufficio = ae_tipo_ufficio;
	}

	/**
	 * @return the ae_codice_ufficio
	 */
	public final String getAe_codice_ufficio() {
		return this.ae_codice_ufficio;
	}

	/**
	 * @param ae_codice_ufficio the ae_codice_ufficio to set
	 */
	public final void setAe_codice_ufficio(String ae_codice_ufficio) {
		this.ae_codice_ufficio = ae_codice_ufficio;
	}

	/**
	 * @return the ae_tipologia_servizio
	 */
	public final String getAe_tipologia_servizio() {
		return this.ae_tipologia_servizio;
	}

	/**
	 * @param ae_tipologia_servizio the ae_tipologia_servizio to set
	 */
	public final void setAe_tipologia_servizio(String ae_tipologia_servizio) {
		this.ae_tipologia_servizio = ae_tipologia_servizio;
	}

	/**
	 * @return the datiSegnaturaCittadino
	 */
	public final Map getDatiSegnaturaCittadino() {
		return this.datiSegnaturaCittadino;
	}

	/**
	 * @param datiSegnaturaCittadino the datiSegnaturaCittadino to set
	 */
	public final void setDatiSegnaturaCittadino(Map datiSegnaturaCittadino) {
		this.datiSegnaturaCittadino = datiSegnaturaCittadino;
	}

	public final void addDatiSegnaturaCittadino(String key, String value) {
		this.getDatiSegnaturaCittadino().put(key, value);
	}

	/**
	 * @return the attachmentsUploadUM
	 */
	public final String getAttachmentsUploadUM() {
		return this.attachmentsUploadUM;
	}

	/**
	 * @param attachmentsUploadUM the attachmentsUploadUM to set
	 */
	public final void setAttachmentsUploadUM(String attachmentsUploadUM) {
		this.attachmentsUploadUM = attachmentsUploadUM;
	}

	/**
	 * @return the attachmentsUploadMaximumSize
	 */
	public final String getAttachmentsUploadMaximumSize() {
		return this.attachmentsUploadMaximumSize;
	}

	/**
	 * @param attachmentsUploadMaximumSize the attachmentsUploadMaximumSize to set
	 */
	public final void setAttachmentsUploadMaximumSize(
			String attachmentsUploadMaximumSize) {
		this.attachmentsUploadMaximumSize = attachmentsUploadMaximumSize;
	}

	/**
	 * @return the riversamentoAutomatico
	 */
	public final boolean isRiversamentoAutomatico() {
		return this.riversamentoAutomatico;
	}

	/**
	 * @param riversamentoAutomatico the riversamentoAutomatico to set
	 */
	public final void setRiversamentoAutomatico(boolean riversamentoAutomatico) {
		this.riversamentoAutomatico = riversamentoAutomatico;
	}

	/**
	 * @return the showPrintBlankTemplate
	 */
	public final boolean isShowPrintBlankTemplate() {
		return this.showPrintBlankTemplate;
	}

	/**
	 * @param showPrintBlankTemplate the showPrintBlankTemplate to set
	 */
	public final void setShowPrintBlankTemplate(boolean showPrintBlankTemplate) {
		this.showPrintBlankTemplate = showPrintBlankTemplate;
	}

	/**
	 * @return the showPDFVersion
	 */
	public final boolean isShowPDFVersion() {
		return this.showPDFVersion;
	}

	/**
	 * @param showPDFVersion the showPDFVersion to set
	 */
	public final void setShowPDFVersion(boolean showPDFVersion) {
		this.showPDFVersion = showPDFVersion;
	}

	/**
	 * @return the onLineSign
	 */
	public final boolean isOnLineSign() {
		return this.onLineSign;
	}

	/**
	 * @param onLineSign the onLineSign to set
	 */
	public final void setOnLineSign(boolean onLineSign) {
		this.onLineSign = onLineSign;
	}

	/**
	 * @return the offLineSign
	 */
	public final boolean isOffLineSign() {
		return this.offLineSign;
	}

	/**
	 * @param offLineSign the offLineSign to set
	 */
	public final void setOffLineSign(boolean offLineSign) {
		this.offLineSign = offLineSign;
	}
	/**
	 * @return the send_protocollo
	 */
	public final String getSend_protocollo() {
		return this.send_protocollo;
	}

	/**
	 * @param send_signature the send_signature to set
	 */
	public final void setSend_protocollo(String send_protocollo) {
		this.send_protocollo = send_protocollo;
	}	
	/**
	 * @return the datiDatiProtocollo
	 */
	public final Map getDatiProtocollo() {
		return this.datiProtocollo;
	}

	/**
	 * @param datiProtocollo the datiProtocollo to set
	 */
	public final void setDatiProtocollo(Map datiProtocollo) {
		this.datiProtocollo = datiProtocollo;
	}

	public final void addDatiProtocollo(String key, String value) {
		this.getDatiProtocollo().put(key, value);
	}

    public boolean isFlgOptionAllegati() {
        return flgOptionAllegati;
    }

    public void setFlgOptionAllegati(boolean flgOptionAllegati) {
        this.flgOptionAllegati = flgOptionAllegati;
    }

    public boolean isFlgOggettoRicevuta() {
        return flgOggettoRicevuta;
    }

    public void setFlgOggettoRicevuta(boolean flgOggettoRicevuta) {
        this.flgOggettoRicevuta = flgOggettoRicevuta;
    }

    public boolean isFlgOggettoRiepilogo() {
        return flgOggettoRiepilogo;
    }

    public void setFlgOggettoRiepilogo(boolean flgOggettoRiepilogo) {
        this.flgOggettoRiepilogo = flgOggettoRiepilogo;
    }

	public final int getAttachmentsUploadMaximumValue() {
		return attachmentsUploadMaximumValue;
	}

	public final void setAttachmentsUploadMaximumValue(
			int attachmentsUploadMaximumValue) {
		this.attachmentsUploadMaximumValue = attachmentsUploadMaximumValue;
	}

}
