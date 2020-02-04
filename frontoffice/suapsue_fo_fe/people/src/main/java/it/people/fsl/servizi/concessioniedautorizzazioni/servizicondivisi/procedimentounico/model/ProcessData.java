/** 
 * Copyright (c) 2011, Regione Emilia-Romagna, Italy
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they will be approved by
 * the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 *
 * For convenience a plain text copy of the English version of the Licence can
 * be found in the file LICENCE.txt in the top-level directory of this software
 * distribution.
 *
 * You may obtain a copy of the Licence in any of 22 European Languages at:
 *
 * http://www.osor.eu/eupl
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * Licence for the specific language governing permissions and limitations under
 * the Licence.
 *
 */ 
package it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model;

import it.diviana.egov.b109.oggettiCondivisi.AllegatoFirmatoDigitalmente;
import it.diviana.egov.b109.oggettiCondivisi.AllegatononFirmato;
import it.diviana.egov.b109.oggettiCondivisi.Comune;
import it.diviana.egov.b109.oggettiCondivisi.ComuneEsteso;
import it.diviana.egov.b109.oggettiCondivisi.CredenzialiUtenteCertificate;
import it.diviana.egov.b109.oggettiCondivisi.IdentificatoreUnivoco;
import it.diviana.egov.b109.oggettiCondivisi.IdentificatorediProtocollo;
import it.diviana.egov.b109.oggettiCondivisi.IdentificatorediRichiesta;
import it.diviana.egov.b109.oggettiCondivisi.IndirizzoStrutturatoCompleto;
import it.diviana.egov.b109.oggettiCondivisi.Localita;
import it.diviana.egov.b109.oggettiCondivisi.PersonaFisica;
import it.diviana.egov.b109.oggettiCondivisi.PersonaGiuridica;
import it.diviana.egov.b109.oggettiCondivisi.RappresentanteLegale;
import it.diviana.egov.b109.oggettiCondivisi.Recapito;
import it.diviana.egov.b109.oggettiCondivisi.SceltaLuogoEsteso;
import it.diviana.egov.b109.oggettiCondivisi.SceltaResidenza;
import it.diviana.egov.b109.oggettiCondivisi.SceltaRiepilogoRichiesta;
import it.diviana.egov.b109.oggettiCondivisi.SceltaTitolare;
import it.diviana.egov.b109.oggettiCondivisi.Sede;
import it.diviana.egov.b109.oggettiCondivisi.Sesso;
import it.diviana.egov.b109.oggettiCondivisi.Toponimo;
import it.diviana.egov.b109.oggettiCondivisi.Recapito.Priorita.Enum;
import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.AnagraficaType;
import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.CampiHrefType;
import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.CampoAnagraficaType;
import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.CampoHrefType;
import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.ClasseEnteType;
import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.ClassiEnteType;
import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.CodiceType;
import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.CodiciDichiarazioniDinamicheType;
import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.CodiciDichiarazioniStaticheType;
import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.CodiciDocumentiType;
import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.CodiciModulisticaType;
import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.CodiciNormativeType;
import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.CudType;
import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.CudsType;
import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.DatoType;
import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.DestinatariType;
import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.DestinatarioType;
import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.DichiarazioneDinamicaType;
import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.DichiarazioneStaticaType;
import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.DichiarazioniDinamicheType;
import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.DichiarazioniStaticheType;
import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.DocumentiType;
import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.DocumentoType;
import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.EnteType;
import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.EsitoPagamentoType;
import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.InterventiType;
import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.InterventoType;
import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.ModulisticaType;
import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.ModuloType;
import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.NormativaType;
import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.NormativeType;
import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.OnereDettaglioType;
import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.OneriDettaglioType;
import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.OneriRiepilogoType;
import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.OperazioneType;
import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.OperazioniAttivitaType;
import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.OpzioneComboType;
import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.OpzioniComboType;
import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.ProcedimentiType;
import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.ProcedimentoType;
import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.RichiestaDocument;
import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.RichiestadiConcessioniEAutorizzazioniType;
import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.RiepilogoDomandaType;
import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.SettoreAttivitaType;
import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.SportelloType;
import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.SportelloType.DatiProtocollo;
import it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.SportelloType.DatiSegnaturaCittadino;
import it.gruppoinit.commons.DBCPManager;
import it.gruppoinit.commons.Utilities;
import it.people.b002.serviziCondivisi.envelope.RichiestadiInvio;
import it.people.core.PeopleContext;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.dao.BookmarkDAO;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.AllegatoBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.AnagraficaBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.BaseBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ComuneBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.DatiTemporaneiBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.DestinatarioBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.DichiarazioniStaticheBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.DocumentoBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ErrorBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.HrefCampiBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.IntermediariBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.InterventoBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ModulisticaBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.NormativaBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.OnereBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.OneriBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.OneriBeanComparator;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.OperazioneBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ProcedimentoBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.RiepilogoOneri;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.RiepilogoOneriPagati;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.SettoreBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.SezioneCompilabileBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.SportelloBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.Costant;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.XPathReader;
import it.people.fsl.servizi.oggetticondivisi.UtenteAutenticato;
import it.people.fsl.servizi.oggetticondivisi.profili.ProfiloPersonaFisica;
import it.people.java.util.LinkedHashMap;
import it.people.process.AbstractPplProcess;
import it.people.process.common.entity.Attachment;
import it.people.process.common.entity.SignedAttachment;
import it.people.process.common.entity.SignedSummaryAttachment;
import it.people.process.data.AbstractData;
import it.people.process.sign.entity.SignedInfo;
import it.people.util.IdentificatoreUnivoco.CodiceSistema;
import it.people.util.payment.EsitoPagamento;
import it.people.vsl.PipelineData;
import it.people.vsl.PipelineDataImpl;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector; 
    
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.upload.FormFile;
import org.apache.xerces.utils.Base64; 
import org.apache.xmlbeans.XmlObject;
 
public class ProcessData extends AbstractData {

    private static final long serialVersionUID = -2885623099705927858L;

    private ErrorBean errore;
    private boolean internalError;

    private boolean infomativaPrivacy;
    private boolean showInformativaPrivacyTitolare;
    private ComuneBean comuneSelezionato;
    private ArrayList alberoSettori;
    private SettoreBean settoreScelto;

    private int livelloSceltaSettore;
    private int livelloSceltaMinSettore;

    private ArrayList alberoOperazioni;
    // private ArrayList listaOperazioniSelezionate;
    private int livelloSceltaOp;
    private int livelloSceltaMinOp;
    private boolean fineSceltaOp;

    private ArrayList interventi;
    private ArrayList interventiFacoltativi;

    private LinkedHashMap listaAllegati;
    private HashMap listaProcedimenti;
    private HashMap listaSportelli;
    private HashMap listaNormative;

    private HashMap listaHref;
    private LinkedHashMap listaDichiarazioniStatiche;
    private LinkedHashMap listaModulistica;
    private LinkedHashMap listaDocRichiesti;
    private SezioneCompilabileBean oggettoIstanza;

    private ArrayList listaUpload;

    private String tmp;
    private String nteldic;
    private DatiTemporaneiBean datiTemporanei;
    private FormFile uploadFile;

    private String descBookmark;
    private String nomeBookmark;
    private String idBookmark;
    private String tipoBookmark;
    private String tipoPagamentoBookmark;
    private boolean modalitaPagamentoBookmarkSoloOnLine;
    private boolean modalitaPagamentoOpzionaleBookmarkSoloOnLine;
    private String firmaBookmark;

    private String lastStepId;
    private String lastActivityId;
    private String tipoAttivazioneOneri;
    private boolean attivaPagamenti;
    private boolean modalitaPagamentoSoloOnLine;
    private boolean modalitaPagamentoOpzionaleSoloOnLine;
    private boolean attestatoPagamentoObbligatorio;
    // private boolean forzaPagamento;
    private boolean oneriAnticipatiPresent;
    private boolean oneriCalcolatiPresent;

    private Set oneriAnticipati;
    private Set oneriPosticipati;
    private ArrayList listaAlberoOneri;

    private AnagraficaBean anagrafica;

    private ArrayList altriRichiedenti;

    private EsitoPagamento esitoPagamento;
    // Da valutare
    private RiepilogoOneri riepilogoOneri;
    private String[] oneriVec;
    private RiepilogoOneriPagati riepilogoOneriPagati;

    // private SignedInfo[] sInfo;
    private String credenzialiBase64;
    private List erroreSuHref;

    // intermediari
    IntermediariBean intermediari;

    boolean bandiAttivi;
    private String remoteAttachFile;

    private ArrayList listaProcuratori;

    private HashMap listaDestinatari;
    // PC - ordinamento dichiarazioni
    private ArrayList listaHrefOrdered;
	// PC - ordinamento dichiarazioni;

// PC - tieni traccia delle scelte su allegati facoltativi - inizio
    private HashMap listaAllegatiFacoltativi;
// PC - tieni traccia delle scelte su allegati facoltativi - fine

    private String entePeopleKey;
    private Object customObject;

    private int uploadedSize;
    private String language;

    private Vector registryRequestSavedFields;
    private ProfiloPersonaFisica titolarePagamento;

    public ProcessData() {
        super();
        initialize();
    }

    private void initialize() {
        this.errore = new ErrorBean();
        this.internalError = false;
        this.infomativaPrivacy = false;
        this.showInformativaPrivacyTitolare = false;
        this.comuneSelezionato = new ComuneBean();
        this.alberoSettori = new ArrayList();
        this.settoreScelto = null;
        this.livelloSceltaSettore = 0;
        this.livelloSceltaMinSettore = 0;

        this.alberoOperazioni = new ArrayList();
        this.livelloSceltaOp = 0;
        this.livelloSceltaMinOp = 0;// utilizzata per la creazione dei bookmark
        // a metà albero delle operazioni

        this.interventi = new ArrayList();
        this.interventiFacoltativi = new ArrayList();

        this.oneriAnticipati = new TreeSet(new OneriBeanComparator());
        this.oneriPosticipati = new TreeSet(new OneriBeanComparator());
        this.listaAlberoOneri = new ArrayList();
        this.riepilogoOneri = new RiepilogoOneri();

        this.listaAllegati = new LinkedHashMap();
        this.listaProcedimenti = new HashMap();
        this.listaSportelli = new HashMap();
        this.listaNormative = new HashMap();
        this.listaHref = new HashMap();
        this.listaDichiarazioniStatiche = new LinkedHashMap();
        this.listaModulistica = new LinkedHashMap();
        this.listaDocRichiesti = new LinkedHashMap();
        this.oggettoIstanza = new SezioneCompilabileBean();
        this.listaUpload = new ArrayList();

        this.tmp = "";
        this.nteldic = "";
        this.datiTemporanei = new DatiTemporaneiBean();
        this.uploadFile = null;
        this.attivaPagamenti = false;
        // this.forzaPagamento=false;
        this.oneriAnticipatiPresent = false;
        this.oneriCalcolatiPresent = false;
        this.oneriVec = new String[0];

        this.anagrafica = new AnagraficaBean();

        this.esitoPagamento = new EsitoPagamento();
        this.credenzialiBase64 = null;
        this.erroreSuHref = new ArrayList();

        this.tipoBookmark = Costant.bookmarkTypeCompleteLabel;
        this.tipoPagamentoBookmark = Costant.pagamentoOpzionaleLabel;
        this.firmaBookmark = Costant.conFirmaLabel;

        this.intermediari = new IntermediariBean();
        this.bandiAttivi = false;

        this.altriRichiedenti = new ArrayList();
        this.listaProcuratori = new ArrayList();
        this.remoteAttachFile = "";
        this.listaDestinatari = new HashMap();
        // PC - ordinamento dichiarazioni
        this.listaHrefOrdered = new ArrayList();
        // PC - ordinamento dichiarazioni
// PC - tieni traccia delle scelte su allegati facoltativi - inizio
        this.listaAllegatiFacoltativi = new HashMap();
// PC - tieni traccia delle scelte su allegati facoltativi - fine

        this.attestatoPagamentoObbligatorio = false;
        this.tipoAttivazioneOneri = Costant.ATTIVAZIONE_ONERI_SOLO_CALCOLO;
        this.customObject = null;
        this.language = "it";

        this.setRegistryRequestSavedFields(new Vector());
        this.titolarePagamento = null;

    }

    protected void doDefineValidators() {
        // TODO Auto-generated method stub
    }

    public void initialize(PeopleContext arg0, AbstractPplProcess arg1) {
        // TODO Auto-generated method stub
    }

    public void initializeUser(it.people.core.PplUser pplUser) {
        super.initializeUser(pplUser);
    }

    public boolean isInfomativaPrivacy() {
        return infomativaPrivacy;
    }

    public void setInfomativaPrivacy(boolean infomativaPrivacy) {
        this.infomativaPrivacy = infomativaPrivacy;
    }

    public final boolean isShowInformativaPrivacyTitolare() {
        return showInformativaPrivacyTitolare;
    }

    public final void setShowInformativaPrivacyTitolare(
            boolean showInformativaPrivacyTitolare) {
        this.showInformativaPrivacyTitolare = showInformativaPrivacyTitolare;
    }

    public String getTmp() {
        return tmp;
    }

    public void setTmp(String tmp) {
        this.tmp = tmp;
    }

    public ComuneBean getComuneSelezionato() {
        return comuneSelezionato;
    }

    public void setComuneSelezionato(ComuneBean comuneSelezionato) {
        this.comuneSelezionato = comuneSelezionato;
    }

    public SettoreBean getSettoreScelto() {
        return settoreScelto;
    }

    public void setSettoreScelto(SettoreBean settoreScelto) {
        this.settoreScelto = settoreScelto;
    }

    public ArrayList getAlberoSettori() {
        return alberoSettori;
    }

    public void setAlberoSettori(ArrayList alberoSettori) {
        this.alberoSettori = alberoSettori;
    }

    public void addAlberoSettori(SettoreBean settore) {
        this.alberoSettori.add(settore);
    }

    public ArrayList getAlberoOperazioni() {
        return alberoOperazioni;
    }

    public void setAlberoOperazioni(ArrayList alberoOperazioni) {
        this.alberoOperazioni = alberoOperazioni;
    }

    public void addAlberoOperazioni(OperazioneBean alberoOperazioni) {
        this.alberoOperazioni.add(alberoOperazioni);
    }

    private Log logger = LogFactory.getLog(this.getClass());

    public void exportToPipeline(PipelineData pd) {
        logger.debug("begin exportToPipeline()");
        SportelloBean sportello = null;
        Set chiaviSettore = this.getListaSportelli().keySet();
        boolean trovato = false;
        Iterator it = chiaviSettore.iterator();
        while (it.hasNext() && !trovato) {
            String chiaveSettore = (String) it.next();
            sportello = (SportelloBean) this.getListaSportelli().get(
                    chiaveSettore);
            if (sportello.getIdx() == this.getDatiTemporanei()
                    .getIndiceSportello()) {
                trovato = true;
            }
        }
        if (sportello == null) {
            logger.error("Nessuno sportello trovato");
            return;
        }

        RichiestaDocument richiestaDocument = RichiestaDocument.Factory
                .newInstance();
        RichiestadiConcessioniEAutorizzazioniType richiesta = richiestaDocument
                .addNewRichiesta();

        // lista allegati di riepilogo per i casi di spacchettamento
        if (sportello.getListaRiepiloghiSpacchettati() != null
                && sportello.getListaRiepiloghiSpacchettati().size() > 0) {
            AllegatononFirmato allegatoNonFirmato = null;
            byte[] decoded = null;
            for (Iterator iterator = sportello.getListaRiepiloghiSpacchettati()
                    .iterator(); iterator.hasNext();) {
                Attachment allegato = (Attachment) iterator.next();
                allegatoNonFirmato = richiesta.addNewAllegatononFirmato();

                decoded = Base64.decode(((Attachment) allegato).getData()
                        .getBytes());
                if (decoded == null && isFormatRemoteFile(allegato.getData())) {
                    decoded = allegato.getData().getBytes();
                }
                allegatoNonFirmato.setContenuto(decoded);
                allegatoNonFirmato.setDescrizione(((Attachment) allegato)
                        .getDescrizione());
                allegatoNonFirmato.setNomeFile(((Attachment) allegato)
                        .getName());
                logger.debug("inserito un allegato non firmato");
            }
        }
        Iterator iter = sportello.getListaAllegati().iterator();
        AllegatoFirmatoDigitalmente allegatoFirmato = null;
        AllegatoFirmatoDigitalmente allegatoFirmatoRiepilogo = null;
        AllegatononFirmato allegatoNonFirmato = null;
        SceltaRiepilogoRichiesta riepilogo = richiesta
                .addNewRiepilogoRichiesta();
		// XXX occorre decodificare l'array di byte che viene da people
        // perchè xmlbeans effettua gà la propria codifica per gli elementi
        // di tipo base64Binary, pertanto senza questa decodifica il
        // contenuto dell'allegato sarebbe codificato due volte.
        // Segnalazione telefonica di Massimiliano Pianciamore del
        // 19/10/2006
        byte[] decoded = null;
        while (iter.hasNext()) {
            Attachment temp = (Attachment) iter.next();
            // riepilogo firmato: uno per richiesta
            if (temp instanceof SignedAttachment) {
                if (temp instanceof SignedSummaryAttachment) {
                    allegatoFirmatoRiepilogo = AllegatoFirmatoDigitalmente.Factory
                            .newInstance();
                    decoded = Base64.decode(((SignedSummaryAttachment) temp)
                            .getData().getBytes());
                    byte[] d = ((SignedSummaryAttachment) temp).getData()
                            .getBytes();
                    if (decoded != null) {
                        allegatoFirmatoRiepilogo.setContenuto(decoded);
                    } else {
                        allegatoFirmatoRiepilogo.setContenuto(d);
                    }
                    // allegatoFirmatoRiepilogo.setContenuto(decoded);
                    allegatoFirmatoRiepilogo
                            .setDescrizione(((SignedSummaryAttachment) temp)
                                    .getDescrizione());
                    allegatoFirmatoRiepilogo
                            .setNomeFile(((SignedSummaryAttachment) temp)
                                    .getName());
                    riepilogo
                            .setAllegatoFirmatoDigitalmente(allegatoFirmatoRiepilogo);
                    logger.debug("inserito il riepilogo firmato");

                } else {// allegato firmato: non riepilogo
                    allegatoFirmato = richiesta
                            .addNewAllegatoFirmatoDigitalmente();
                    decoded = Base64.decode(((SignedAttachment) temp).getData()
                            .getBytes());
                    if (decoded != null) {
                        allegatoFirmato.setContenuto(decoded);
                    } else {
                        allegatoFirmato.setContenuto(((SignedAttachment) temp)
                                .getData().getBytes());
                    }
                    // allegatoFirmato.setContenuto(decoded);
                    allegatoFirmato.setDescrizione(((SignedAttachment) temp)
                            .getDescrizione());
                    allegatoFirmato.setNomeFile(((SignedAttachment) temp)
                            .getName());
                    logger.debug("inserito un allegato firmato");
                }
                // allegatoNonFirmato: riepilogo
            } else if (((Attachment) temp).getDescrizione().indexOf(
                    "riepilogo.pdf") != -1) {
                allegatoNonFirmato = riepilogo.addNewAllegatononFirmato();
				// allegatoNonFirmato =
                // AllegatononFirmato.Factory.newInstance();
                String data = ((Attachment) temp).getData();
                decoded = Base64.decode(data.getBytes());
                if (decoded != null) {
                    allegatoNonFirmato.setContenuto(decoded);
                } else {
                    allegatoNonFirmato.setContenuto(data.getBytes());
                }
                allegatoNonFirmato.setDescrizione(((Attachment) temp)
                        .getDescrizione());
                allegatoNonFirmato.setNomeFile(((Attachment) temp).getName());
                // riepilogo.setAllegatononFirmato(allegatoNonFirmato);
                logger.debug("inserito il riepilogo non firmato");
                // allegatoNonFirmato:
            } else {
                allegatoNonFirmato = richiesta.addNewAllegatononFirmato();
                decoded = Base64.decode(((Attachment) temp).getData()
                        .getBytes());
                if (decoded != null) {
                    allegatoNonFirmato.setContenuto(decoded);
                } else {
                    allegatoNonFirmato.setContenuto(((Attachment) temp)
                            .getData().getBytes());
                }
                // allegatoNonFirmato.setContenuto(decoded);
                allegatoNonFirmato.setDescrizione(((Attachment) temp)
                        .getDescrizione());
				// allegatoNonFirmato.setNomeFile(((Attachment)
                // temp).getName());
                allegatoNonFirmato.setNomeFile(((Attachment) temp).getName());
                logger.debug("inserito un allegato non firmato");
            }
        }
        richiesta.setRemoteAttachFile(this.remoteAttachFile);
        // recapito
        Recapito recapito = richiesta.addNewRecapito();

        UtenteAutenticato utenteAutenticato = getRichiedente()
                .getUtenteAutenticato();
        List recapiti = utenteAutenticato.getRecapito();
        // ora recupero solo l'indirizzo email (sufficiente per Connects)
        if (recapiti == null || recapiti.size() == 0 || recapiti.get(0) == null) {
			// TODO
            // recapito.setIndirizzoemail(this.anagrafica.getDichiarante().getResidenza().getEmail());
            recapito.setIndirizzoemail(getCampoDinamicoAnagrafica(this
                    .getAnagrafica().getListaCampi(), "ANAG_EMAIL_DICHIARANTE"));
        } else {
            recapito.setIndirizzoemail((String) recapiti.get(0));
        }
        recapito.setReferente("test");
        recapito.setPriorita(Enum.forInt(1));

        logger.debug("inserito il recapito");

        SimpleDateFormat sfd = new SimpleDateFormat("dd/MM/yyyy");
        sfd.format(new Date());
        // identificatore richiesta
        Calendar today = sfd.getCalendar();

        IdentificatorediRichiesta identificatoreDiRichiesta = richiesta
                .addNewIdentificatorediRichiesta();

        IdentificatoreUnivoco idUnivoco = identificatoreDiRichiesta
                .addNewIdentificatoreUnivoco();
        it.people.util.IdentificatoreUnivoco idUnivocoTemp = this
                .getIdentificatoreUnivoco();
        CodiceSistema codiceSistema = idUnivocoTemp.getCodiceSistema();
        idUnivoco.setCodiceIdentificativoOperazione(this
                .getIdentificatorePeople().getIdentificatoreProcedimento()
                + "/" + sportello.getIdx());
        idUnivoco.setCodiceProgetto(idUnivocoTemp.getCodiceProgetto());
        idUnivoco.addNewCodiceSistema();
        idUnivoco.getCodiceSistema().setCodiceAmministrazione(
                idUnivocoTemp.getMarshallingCodiceAmministrazione());
        idUnivoco.getCodiceSistema().setNomeServer(
                codiceSistema.getNomeServer());
        idUnivoco.setDatadiRegistrazione(today);
        IdentificatorediProtocollo idProt = identificatoreDiRichiesta
                .addNewIdentificatorediProtocollo();
        idProt.setCodiceAmministrazione("");
        idProt.setCodiceAOO("");
        idProt.setDatadiRegistrazione(new GregorianCalendar());
        idProt.setNumerodiRegistrazione(new BigInteger("0"));

        logger.debug("inserito l'identificatore di richiesta");
        // richiedente
        CredenzialiUtenteCertificate credenzialiRichiedente = richiesta
                .addNewRichiedente();

        // imposto i dati del richiedente da inviare al BE
        credenzialiRichiedente.setCodiceFiscale(getCampoDinamicoAnagrafica(this
                .getAnagrafica().getListaCampi(), "ANAG_CODFISC_DICHIARANTE"));

        AllegatoFirmatoDigitalmente credenziali = credenzialiRichiedente
                .addNewCredenzialiFirmate();
        String p = (String) pd.getAttribute("processDataParam");
        XPathReader xpr = new XPathReader(p);

        try {
			// String contenuto =
            // xpr.readElementString("/env:RichiestadiInvio/env:TipoComunicazione/ogg:RichiestaC2G/ogg:Mittente/ogg:CredenzialiFirmate/ogg:Contenuto");
            credenziali
                    .setContenuto(xpr
                            .readElementString(
                                    "/env:RichiestadiInvio/env:TipoComunicazione/ogg:RichiestaC2G/ogg:Mittente/ogg:CredenzialiFirmate/ogg:Contenuto")
                            .getBytes());
            credenziali
                    .setDescrizione(xpr
                            .readElementString("/env:RichiestadiInvio/env:TipoComunicazione/ogg:RichiestaC2G/ogg:Mittente/ogg:CredenzialiFirmate/ogg:Descrizione"));
            credenziali
                    .setNomeFile(xpr
                            .readElementString("/env:RichiestadiInvio/env:TipoComunicazione/ogg:RichiestaC2G/ogg:Mittente/ogg:CredenzialiFirmate/ogg:NomeFile"));
            logger.debug("inserito il richiedente");
        } catch (Exception e) {
        }
        try {
            SceltaTitolare sceltaTitolare = richiesta.addNewTitolare();
            if (super.getTitolare() != null
                    && super.getTitolare().getPersonaFisica() != null) {
                PersonaFisica personaFisicaTitolare = sceltaTitolare
                        .addNewPersonaFisica();
                personaFisicaTitolare.setCodiceFiscale(super.getTitolare()
                        .getPersonaFisica().getCodiceFiscale());
                personaFisicaTitolare.setCognome(super.getTitolare()
                        .getPersonaFisica().getCognome());
                personaFisicaTitolare.setNome(super.getTitolare()
                        .getPersonaFisica().getNome());
                personaFisicaTitolare.setDatadiNascita(super.getTitolare()
                        .getPersonaFisica().getDatadiNascita().getCalendar());
                SceltaLuogoEsteso luogoNascita = personaFisicaTitolare
                        .addNewLuogodiNascita();
                ComuneEsteso comune = luogoNascita.addNewComuneEsteso();
                if (super.getTitolare().getPersonaFisica().getLuogodiNascita() != null
                        && super.getTitolare().getPersonaFisica()
                        .getLuogodiNascita().getComune() != null
                        && super.getTitolare().getPersonaFisica()
                        .getLuogodiNascita().getComune().getNome() != null) {
                    comune.setNome(super.getTitolare().getPersonaFisica()
                            .getLuogodiNascita().getComune().getNome());
                }
                if (super.getTitolare().getPersonaFisica().getSesso() != null) {
                    if (super.getTitolare().getPersonaFisica().getSesso()
                            .equalsIgnoreCase("M")
                            || super.getTitolare().getPersonaFisica()
                            .getSesso().equalsIgnoreCase("maschio")) {
                        personaFisicaTitolare.setSesso(Sesso.MASCHIO);
                    } else {
                        personaFisicaTitolare.setSesso(Sesso.FEMMINA);
                    }
                }
                SceltaResidenza sceltaResidenza = personaFisicaTitolare
                        .addNewResidenza();
                IndirizzoStrutturatoCompleto indirizzo = sceltaResidenza
                        .addNewIndirizzoStrutturatoCompleto();
                if (super.getTitolare().getPersonaFisica().getResidenza() != null) {
                    indirizzo.setCAP(new BigInteger(super.getTitolare()
                            .getPersonaFisica().getResidenza().getCAP()));
                }
                Comune comuneRes = indirizzo.addNewComune();
                if (super.getTitolare().getPersonaFisica().getResidenza() != null
                        && super.getTitolare().getPersonaFisica()
                        .getResidenza().getLuogo() != null
                        && super.getTitolare().getPersonaFisica()
                        .getResidenza().getLuogo().getComune() != null) {
                    comuneRes.setNome(super.getTitolare().getPersonaFisica()
                            .getResidenza().getLuogo().getComune().getNome());
                }
            } else if (super.getTitolare() != null
                    && super.getTitolare().getPersonaGiuridica() != null) {
                PersonaGiuridica personaGiuridicaTitolare = sceltaTitolare
                        .addNewPersonaGiuridica();
                personaGiuridicaTitolare.setCodiceFiscalePersonaGiuridica(Long
                        .parseLong(super.getTitolare().getPersonaGiuridica()
                                .getPartitaIVA()));
                personaGiuridicaTitolare
                        .setDenominazioneoRagioneSociale(super.getTitolare()
                                .getPersonaGiuridica().getDenominazione());
                RappresentanteLegale rapp = personaGiuridicaTitolare
                        .addNewRappresentanteLegale();
                rapp.setCodiceFiscale(super.getTitolare().getPersonaGiuridica()
                        .getRappresentanteLegale().getCodiceFiscale());
                rapp.setCognome(super.getTitolare().getPersonaGiuridica()
                        .getRappresentanteLegale().getCognome());
                rapp.setNome(super.getTitolare().getPersonaGiuridica()
                        .getRappresentanteLegale().getNome());
                Sede sed = personaGiuridicaTitolare.addNewSedeLegale();
                sed.setIndirizzoTestuale(super.getTitolare()
                        .getPersonaGiuridica().getSedeLegale()
                        .getIndirizzoTestuale());
            }
        } catch (Exception e) {
            logger.error("Errore nell'inserimento del titolare");
        }

        richiesta.setInvioDiCortesia(this.getTipoBookmark().equalsIgnoreCase(
                Costant.bookmarkTypeCortesiaLabel) ? true : false);
        if (isComunica(this.getAnagrafica())) {
            richiesta.setInvioDiCortesia(true);
            this.setTipoBookmark(Costant.bookmarkTypeCortesiaLabel);
        }
		// MAPPAGGIO DATI
        // if (this.getTitolare()!=null &&
        // this.getTitolare().getPersonaFisica()!=null &&
        // this.getTitolare().getPersonaFisica().getNome().equalsIgnoreCase("Anonimo")
        // &&
        // this.getTitolare().getPersonaFisica().getCognome().equalsIgnoreCase("Anonimo")
        // &&
        // this.getTitolare().getPersonaFisica().getCodiceFiscale().equalsIgnoreCase("NNMNNM70A01H536W"))
        // {
        if (this.getTipoBookmark().equalsIgnoreCase(
                Costant.bookmarkTypeCortesiaLabel)) {
            richiesta.setPraticaAnonima(true);
        } else {
            richiesta.setPraticaAnonima(false);
        }
        richiesta.setDataOraPresentazione(today);
        EnteType ente = richiesta.addNewEnte();

        ente.setCodEnte(this.comuneSelezionato.getCodEnte());
        ente.setDesEnte(this.comuneSelezionato.getDescrizione());
        ente.setCap(Utilities.NVL(this.comuneSelezionato.getCap(), ""));
        ente.setCitta(Utilities.NVL(this.comuneSelezionato.getCitta(), ""));
        ente.setCodAoo(Utilities.NVL(this.comuneSelezionato.getAoo(), ""));
        ente.setCodBf(Utilities.NVL(this.comuneSelezionato.getCodBf(), ""));
        ente.setCodiceClasseEnte(Utilities.NVL(
                this.comuneSelezionato.getCodClasseEnte(), ""));
        ente.setCodIstat(Utilities.NVL(this.comuneSelezionato.getCodIstat(), ""));
        ente.setEmail(Utilities.NVL(this.comuneSelezionato.getEmail(), ""));
        ente.setFax(Utilities.NVL(this.comuneSelezionato.getFax(), ""));
        ente.setIndirizzo(Utilities.NVL(this.comuneSelezionato.getVia(), ""));
        ente.setProvincia(Utilities.NVL(this.comuneSelezionato.getProvincia(),
                ""));
        ente.setTelefono(Utilities.NVL(this.comuneSelezionato.getTelefono(), ""));

        SportelloType sportelloDestinazione = richiesta
                .addNewSportelloDestinatario();
        sportelloDestinazione.setCap(sportello.getCap());
        sportelloDestinazione.setCitta(sportello.getCitta());
        sportelloDestinazione
                .setCodiceSportello(sportello.getCodiceSportello());
        sportelloDestinazione.setDescrizioneSportello(sportello
                .getDescrizioneSportello());
        sportelloDestinazione.setEmail(sportello.getEmail());
        sportelloDestinazione.setFax(Utilities.NVL(sportello.getFax(), ""));
        sportelloDestinazione.setFlgPu(sportello.getFlgPu());
        sportelloDestinazione.setFlgSu(sportello.getFlgSu());
        sportelloDestinazione.setIndirizzo(sportello.getIndirizzo());
        sportelloDestinazione.setPec(Utilities.NVL(sportello.getPec(), ""));
        sportelloDestinazione.setProvincia(sportello.getProvincia());
        sportelloDestinazione.setRup(sportello.getRup());
        sportelloDestinazione.setTelefono(Utilities.NVL(
                sportello.getTelefono(), ""));
        sportelloDestinazione.setIdMailServer(Utilities.NVL(
                sportello.getId_mail_server(), ""));
        sportelloDestinazione.setIdProtocollo(Utilities.NVL(
                sportello.getId_protocollo(), ""));
        sportelloDestinazione.setIdBackoffice(Utilities.NVL(
                sportello.getId_backoffice(), ""));
		// <RF - Aggiunta modellazione spostamento paraemtri PEC e nuova
        // gestione Connects
        sportelloDestinazione.setTemplateOggettoRicevuta(Utilities.NVL(
                sportello.getTemplate_oggetto_ricevuta(), "").getBytes());
        sportelloDestinazione.setTemplateCorpoRicevuta(Utilities.NVL(
                sportello.getTemplate_corpo_ricevuta(), "").getBytes());
        sportelloDestinazione.setTemplateNomeFileZip(Utilities.NVL(
                sportello.getTemplate_nome_file_zip(), "").getBytes());
        sportelloDestinazione.setSendZipFile(Boolean.parseBoolean(sportello
                .getSend_zip_file()));
        sportelloDestinazione.setSendSingleFiles(Boolean.parseBoolean(sportello
                .getSend_single_files()));
        sportelloDestinazione.setSendXml(Boolean.parseBoolean(sportello
                .getSend_xml()));
        sportelloDestinazione.setSendSignature(Boolean.parseBoolean(sportello
                .getSend_signature()));
        sportelloDestinazione.setSendProtocolloParam(Boolean
                .parseBoolean(sportello.getSend_protocollo()));
        sportelloDestinazione.setFlgOggettoRicevuta(sportello
                .isFlgOggettoRicevuta());
        sportelloDestinazione.setTemplateOggettoMailSuap(Utilities.NVL(
                sportello.getTemplate_oggetto_mail_suap(), "").getBytes());
        sportelloDestinazione
                .setSendRicevutaDopoProtocollazione(Boolean
                        .parseBoolean(sportello
                                .getSend_ricevuta_dopo_protocollazione()));
        sportelloDestinazione.setSendRicevutaDopoInvioBo(Boolean
                .parseBoolean(sportello.getSend_ricevuta_dopo_invio_bo()));
		// RF - Aggiunta modellazione spostamento paraemtri PEC e nuova gestione
        // Connects>
        SettoreAttivitaType settore = richiesta.addNewSettoreAttivita();
        settore.setCodiceSettore(this.getSettoreScelto().getCodice());
        settore.setDescrizioneSettore(this.getSettoreScelto().getDescrizione());

        try {
            if (!sportello.getDatiSegnaturaCittadino().isEmpty()) {
                DatiSegnaturaCittadino datiSegnaturaCittadino = sportelloDestinazione
                        .addNewDatiSegnaturaCittadino();
                Iterator keySetIterator = sportello.getDatiSegnaturaCittadino()
                        .keySet().iterator();
                while (keySetIterator.hasNext()) {
                    String chiave = (String) keySetIterator.next();
                    String valore = (String) sportello
                            .getDatiSegnaturaCittadino().get(chiave);
                    DatoType dato = datiSegnaturaCittadino.addNewDato();
                    dato.setChiave(chiave);
                    dato.setValore(valore);
                }
            }
        } catch (Exception e) {

        }
        try {
            if (!sportello.getDatiProtocollo().isEmpty()) {
                DatiProtocollo datiProtocollo = sportelloDestinazione
                        .addNewDatiProtocollo();
                Iterator keySetIterator = sportello.getDatiProtocollo()
                        .keySet().iterator();
                while (keySetIterator.hasNext()) {
                    String chiave = (String) keySetIterator.next();
                    String valore = (String) sportello.getDatiProtocollo().get(
                            chiave);
                    DatoType dato = datiProtocollo.addNewDato();
                    dato.setChiave(chiave);
                    dato.setValore(valore);
                }
            }
        } catch (Exception e) {

        }
        OperazioniAttivitaType listaOperazioni = richiesta
                .addNewOperazioniAttivita();
        int profonditaMassima = maxProfAlberoOperazioni(this
                .getAlberoOperazioni());
        for (int i = 1; i <= profonditaMassima; i++) {
            for (Iterator iterator = this.getAlberoOperazioni().iterator(); iterator
                    .hasNext();) {
                OperazioneBean operazione = (OperazioneBean) iterator.next();
                if (operazione.isSelezionato()
                        && operazione.getProfondita() == i/*
                         * && (operazione.
                         * getListaCodiciFigli
                         * ()==null ||
                         * operazione
                         * .getListaCodiciFigli
                         * ().size()==0)
                         */) {
                    OperazioneType op = listaOperazioni.addNewOperazione();
                    op.setCodOperazione(operazione.getCodiceOperazione());
                    op.setDesOperazione(operazione.getDescrizioneOperazione());
                    if (operazione.getCodice() != null) {
                        op.setCodice(operazione.getCodice());
                    }
                    if (operazione.getListaCodiciFigli() == null
                            || operazione.getListaCodiciFigli().size() == 0) {
                        op.setIsFoglia(true);
                    } else {
                        op.setIsFoglia(false);
                    }
                }
            }
        }

        richiesta.setIdBookmark(Utilities.NVL(this.getIdBookmark(), ""));
        richiesta.setDescrizioneBookmark(Utilities.NVL(this.getDescBookmark(),
                ""));

        AnagraficaType an = richiesta.addNewAnagrafica();

        for (Iterator iterator = this.getAnagrafica().getListaCampi()
                .iterator(); iterator.hasNext();) {
            HrefCampiBean campoAnag = (HrefCampiBean) iterator.next();
            CampoAnagraficaType campoAnagrafica = an.addNewCampoAnagrafica();
            campoAnagrafica.setCampoXmlMod(Utilities.NVL(
                    campoAnag.getCampo_xml_mod(), ""));
            campoAnagrafica.setContatore(campoAnag.getContatore());
            campoAnagrafica.setControllo(Utilities.NVL(
                    campoAnag.getControllo(), ""));
            campoAnagrafica
                    .setDecimali(String.valueOf(campoAnag.getDecimali()));
            campoAnagrafica.setDescrizione(campoAnag.getDescrizione());
            campoAnagrafica.setEdit(campoAnag.getEdit());
            campoAnagrafica.setLivello(String.valueOf(campoAnag.getLivello()));
            campoAnagrafica.setLunghezza(String.valueOf(campoAnag
                    .getLunghezza()));
            campoAnagrafica.setMolteplicita(String.valueOf(campoAnag
                    .getMolteplicita()));
            campoAnagrafica.setNome(campoAnag.getNome());
            campoAnagrafica.setPosizione(String.valueOf(campoAnag
                    .getPosizione()));
            campoAnagrafica.setRiga(String.valueOf(campoAnag.getRiga()));
            campoAnagrafica.setTipo(campoAnag.getTipo());
            campoAnagrafica.setValore(Utilities.NVL(campoAnag.getValore(), ""));
            campoAnagrafica.setValoreUtente(campoAnag.getValoreUtente());
            campoAnagrafica.setRaggruppamentoCheck(Utilities.NVL(
                    campoAnag.getRaggruppamento_check(), ""));
            campoAnagrafica.setCampoCollegato(Utilities.NVL(
                    campoAnag.getCampo_collegato(), ""));
            campoAnagrafica.setValCampoCollegato(Utilities.NVL(
                    campoAnag.getVal_campo_collegato(), ""));
            if (campoAnag.getOpzioniCombo() != null
                    && campoAnag.getOpzioniCombo().size() > 0) {
                OpzioniComboType listaOpz = campoAnagrafica
                        .addNewOpzioniCombo();
                for (Iterator iterator2 = campoAnag.getOpzioniCombo()
                        .iterator(); iterator2.hasNext();) {
                    BaseBean bb = (BaseBean) iterator2.next();
                    OpzioneComboType opz = listaOpz.addNewOpzioneCombo();
                    opz.setCodice(bb.getCodice());
                    opz.setEtichetta(bb.getDescrizione());
                }
            }

        }

        // altri dichiaranti
        for (Iterator iterator2 = this.getAltriRichiedenti().iterator(); iterator2
                .hasNext();) {
            AnagraficaBean anagraficaAltriRichiedenti = (AnagraficaBean) iterator2
                    .next();
            AnagraficaType a = richiesta.addNewAnagraficaAltriDichiaranti();
            for (Iterator iterator = anagraficaAltriRichiedenti.getListaCampi()
                    .iterator(); iterator.hasNext();) {
                HrefCampiBean campoAnag = (HrefCampiBean) iterator.next();
                CampoAnagraficaType campoAnagrafica = a.addNewCampoAnagrafica();
                campoAnagrafica.setCampoXmlMod(Utilities.NVL(
                        campoAnag.getCampo_xml_mod(), ""));
                campoAnagrafica.setContatore(campoAnag.getContatore());
                campoAnagrafica.setControllo(Utilities.NVL(
                        campoAnag.getControllo(), ""));
                campoAnagrafica.setDecimali(String.valueOf(campoAnag
                        .getDecimali()));
                campoAnagrafica.setDescrizione(campoAnag.getDescrizione());
                campoAnagrafica.setEdit(campoAnag.getEdit());
                campoAnagrafica.setLivello(String.valueOf(campoAnag
                        .getLivello()));
                campoAnagrafica.setLunghezza(String.valueOf(campoAnag
                        .getLunghezza()));
                campoAnagrafica.setMolteplicita(String.valueOf(campoAnag
                        .getMolteplicita()));
                campoAnagrafica.setNome(campoAnag.getNome());
                campoAnagrafica.setPosizione(String.valueOf(campoAnag
                        .getPosizione()));
                campoAnagrafica.setRiga(String.valueOf(campoAnag.getRiga()));
                campoAnagrafica.setTipo(campoAnag.getTipo());
                campoAnagrafica.setValore(Utilities.NVL(campoAnag.getValore(),
                        ""));
                campoAnagrafica.setValoreUtente(campoAnag.getValoreUtente());
                campoAnagrafica.setRaggruppamentoCheck(Utilities.NVL(
                        campoAnag.getRaggruppamento_check(), ""));
                campoAnagrafica.setCampoCollegato(Utilities.NVL(
                        campoAnag.getCampo_collegato(), ""));
                campoAnagrafica.setValCampoCollegato(Utilities.NVL(
                        campoAnag.getVal_campo_collegato(), ""));
                if (campoAnag.getOpzioniCombo() != null
                        && campoAnag.getOpzioniCombo().size() > 0) {
                    OpzioniComboType listaOpz = campoAnagrafica
                            .addNewOpzioniCombo();
                    for (Iterator iterator3 = campoAnag.getOpzioniCombo()
                            .iterator(); iterator3.hasNext();) {
                        BaseBean bb = (BaseBean) iterator3.next();
                        OpzioneComboType opz = listaOpz.addNewOpzioneCombo();
                        opz.setCodice(bb.getCodice());
                        opz.setEtichetta(bb.getDescrizione());
                    }
                }
            }
        }

        // OGGETTO PRATICA
        if (this.getOggettoIstanza() != null) {
            DichiarazioneDinamicaType oggetto = richiesta
                    .addNewOggettoPratica();
            oggetto.setHref(Utilities.NVL(this.getOggettoIstanza().getHref(),
                    ""));
            oggetto.setPiedeHref(Utilities.NVL(this.getOggettoIstanza()
                    .getPiedeHref(), ""));
            oggetto.setTitolo(Utilities.NVL(this.getOggettoIstanza()
                    .getTitolo(), ""));
            CampiHrefType listaCampi = oggetto.addNewCampiHref();
            for (Iterator iterator2 = this.getOggettoIstanza().getCampi()
                    .iterator(); iterator2.hasNext();) {
                HrefCampiBean campo = (HrefCampiBean) iterator2.next();
                CampoHrefType cht = listaCampi.addNewCampoHref();
                cht.setCampoXmlMod(campo.getCampo_xml_mod());
                cht.setContatore(campo.getContatore());
                cht.setControllo(campo.getControllo());
                cht.setDecimali(String.valueOf(campo.getDecimali()));
                cht.setDescrizione(Utilities.NVL(campo.getDescrizione(), ""));
                cht.setEdit(campo.getEdit());
                cht.setLunghezza(String.valueOf(campo.getLunghezza()));
                // cht.setMolteplicita(String.valueOf(campo.getMolteplicita()));

                cht.setMolteplicita(campo.getNumCampo() == 0 ? String
                        .valueOf(campo.getNumCampo()) : String.valueOf(campo
                                .getMolteplicita()));

                cht.setNome(Utilities.NVL(campo.getNome(), ""));
                cht.setPosizione(String.valueOf(campo.getPosizione()));
                cht.setRiga(String.valueOf(campo.getRiga()));
                String tipo = campo.getTipo();
                cht.setTipo((tipo.equalsIgnoreCase("A") ? "I" : tipo));
                cht.setValore(Utilities.NVL(campo.getValore(), ""));
                cht.setValoreUtente(Utilities.NVL(campo.getValoreUtente(), ""));
                if (campo.getOpzioniCombo() != null
                        && campo.getOpzioniCombo().size() > 0) {
                    OpzioniComboType opzCombo = cht.addNewOpzioniCombo();
                    for (Iterator iterator3 = campo.getOpzioniCombo()
                            .iterator(); iterator3.hasNext();) {
                        BaseBean bb = (BaseBean) iterator3.next();
                        OpzioneComboType campoCombo = opzCombo
                                .addNewOpzioneCombo();
                        campoCombo.setCodice(bb.getCodice());
                        campoCombo.setEtichetta(bb.getDescrizione());
                    }
                }
            }
        }

        HashMap listaCodiciNormative = new HashMap();
        HashMap listaCodiciDichiarazioniStatiche = new HashMap();
        HashMap listaCodiciDichiarazioniDinamiche = new HashMap();
        HashMap listaCodiciModulistica = new HashMap();
        HashMap listaCodiciDocumenti = new HashMap();
        HashMap listaCodiciDestinatari = new HashMap();
        HashMap listaCuds = new HashMap();
        HashMap listaClassiEnte = new HashMap();

        // RIEPILOGO DOMANDA
        RiepilogoDomandaType riep = richiesta.addNewRiepilogoDomanda();
        ProcedimentiType listaProcedimenti = riep.addNewProcedimenti();
        for (Iterator iterator = sportello.getCodProcedimenti().iterator(); iterator
                .hasNext();) {
            String codiceProcedimento = (String) iterator.next();
            ProcedimentoBean procedimentoPD = (ProcedimentoBean) this
                    .getListaProcedimenti().get(codiceProcedimento);
            ProcedimentoType procedimento = listaProcedimenti
                    .addNewProcedimento();
            procedimento.setCodiceCud(procedimentoPD.getCodiceCud());
            procedimento.setCodiceProcedimento(procedimentoPD
                    .getCodiceProcedimento());
            procedimento.setFlagComune(procedimentoPD.getFlagComune());
            procedimento.setFlagProcedimento(procedimentoPD
                    .getFlagProcedimento());
            procedimento.setFlagPu(procedimentoPD.getFlagPu());
            procedimento.setFlgBollo(procedimentoPD.getFlg_bollo());
            procedimento.setNome(procedimentoPD.getNome());
            procedimento
                    .setTerminiEvasione(procedimentoPD.getTerminiEvasione());
            procedimento.setTipo(String.valueOf(procedimentoPD.getTipo()));
            procedimento.setCodiceDestinatario(procedimentoPD.getCodDest());
            procedimento.setCodiceEnte(procedimentoPD.getCodiceEnte());
            procedimento.setCodiceClasseEnte(procedimentoPD
                    .getCodiceClasseEnte());
            if (procedimentoPD.getCodDest() != null
                    && !procedimentoPD.getCodDest().equalsIgnoreCase("")
                    && !listaCodiciDestinatari.containsKey(procedimentoPD
                            .getCodDest())) {
                listaCodiciDestinatari.put(procedimentoPD.getCodDest(), "");
            }
            if (procedimentoPD.getCodiceCud() != null
                    && !procedimentoPD.getCodiceCud().equalsIgnoreCase("")
                    && !listaCuds.containsKey(procedimentoPD.getCodiceCud())) {
                listaCuds.put(procedimentoPD.getCodiceCud(),
                        procedimentoPD.getDescrizioneCud());
            }
            if (procedimentoPD.getCodiceClasseEnte() != null
                    && !procedimentoPD.getCodiceClasseEnte().equalsIgnoreCase(
                            "")
                    && !listaClassiEnte.containsKey(procedimentoPD
                            .getCodiceClasseEnte())) {
                BaseBean bb = new BaseBean(procedimentoPD.getFlagComune(),
                        procedimentoPD.getDescrizioneClasseEnte());
                listaClassiEnte.put(procedimentoPD.getCodiceClasseEnte(), bb);
            }
            double importoOneriProc = 0;
            if (procedimentoPD.getCodInterventi() != null
                    && procedimentoPD.getCodInterventi().size() > 0) {
                InterventiType listaInterventi = procedimento
                        .addNewInterventi();
                for (Iterator iterator2 = procedimentoPD.getCodInterventi()
                        .iterator(); iterator2.hasNext();) {
                    String codiceIntervento = (String) iterator2.next();
                    InterventoBean interventoPD = recuperaIntervento(codiceIntervento);
                    if (interventoPD != null) {
                        InterventoType intervento = listaInterventi
                                .addNewIntervento();
                        intervento.setCodice(interventoPD.getCodice());
                        intervento
                                .setDescrizione(interventoPD.getDescrizione());
                        if (interventoPD.getListaCodiciNormative() != null
                                && interventoPD.getListaCodiciNormative()
                                .size() > 0) {
                            CodiciNormativeType listaCodNormative = intervento
                                    .addNewCodiciNormative();
                            for (Iterator iterator3 = interventoPD
                                    .getListaCodiciNormative().iterator(); iterator3
                                    .hasNext();) {
                                String codNormativaPD = (String) iterator3
                                        .next();
                                CodiceType cod = listaCodNormative
                                        .addNewCodice();
                                cod.setCodice(codNormativaPD);
                                listaCodiciNormative.put(codNormativaPD,
                                        codNormativaPD);
                            }
                        }
                        if (interventoPD.getListaCodiciAllegati() != null
                                && interventoPD.getListaCodiciAllegati().size() > 0) {
                            CodiciDichiarazioniDinamicheType listaCodDichiarazioniDinamiche = intervento
                                    .addNewCodiciDichiarazioniDinamiche();
                            CodiciDichiarazioniStaticheType listaCodDichiarazioniStatiche = intervento
                                    .addNewCodiciDichiarazioniStatiche();
                            CodiciDocumentiType listaCodDocumenti = intervento
                                    .addNewCodiciDocumenti();
                            CodiciModulisticaType listaCodModulistica = intervento
                                    .addNewCodiciModulistica();
                            for (Iterator iterator3 = interventoPD
                                    .getListaCodiciAllegati().iterator(); iterator3
                                    .hasNext();) {
                                String codAllegato = (String) iterator3.next();
                                AllegatoBean all = (AllegatoBean) this
                                        .getListaAllegati().get(codAllegato);
                                if (all.getHref() != null
                                        && !all.getHref().equalsIgnoreCase("")) {
                                    // questo allegato è un HREF
                                    CodiceType cod = listaCodDichiarazioniDinamiche
                                            .addNewCodice();
                                    cod.setCodice(all.getHref());
                                    listaCodiciDichiarazioniDinamiche.put(
                                            all.getHref(), all.getHref());
                                }
                                if (all.getNomeFile() == null
                                        && all.getFlagDic() != null
                                        && all.getFlagDic().equalsIgnoreCase(
                                                "S") && all.getHref() == null) {
									// questo allegato è una dichiarazione
                                    // Statica
                                    CodiceType cod = listaCodDichiarazioniStatiche
                                            .addNewCodice();
                                    cod.setCodice(codAllegato);
                                    listaCodiciDichiarazioniStatiche.put(
                                            codAllegato, codAllegato);
                                }
                                if (all.getNomeFile() != null
                                        && all.getFlagDic() != null
                                        && all.getFlagDic().equalsIgnoreCase(
                                                "N") && all.getHref() == null) {
                                    // questo allegato è una modulistica
                                    CodiceType cod = listaCodModulistica
                                            .addNewCodice();
                                    cod.setCodice(codAllegato);
                                    listaCodiciModulistica.put(codAllegato,
                                            codAllegato);
                                }
                                if (all.getNomeFile() == null
                                        && all.getFlagDic() != null
                                        && all.getFlagDic().equalsIgnoreCase(
                                                "N") && all.getHref() == null) {
                                    // questo allegato è un documento
                                    CodiceType cod = listaCodDocumenti
                                            .addNewCodice();
                                    cod.setCodice(codAllegato);
                                    listaCodiciDocumenti.put(codAllegato,
                                            codAllegato);
                                }
                            }
                        }
                        OneriDettaglioType oneriDett = intervento
                                .addNewOneriDettaglio();
                        if (this.getOneriAnticipati() != null) {
                            for (Iterator iterator3 = this.getOneriAnticipati()
                                    .iterator(); iterator3.hasNext();) {
                                OneriBean onerePD = (OneriBean) iterator3
                                        .next();
                                if (onerePD.getCod_int() != null
                                        && onerePD.getCod_int()
                                        .equalsIgnoreCase(
                                                interventoPD
                                                .getCodice())) {
                                    OnereDettaglioType onere = oneriDett
                                            .addNewOnereDettaglio();
                                    onere.setCodice(onerePD.getCodice());
                                    onere.setCodiceCud("");
                                    onere.setCodiceDestinatario(onerePD
                                            .getCodDestinatario());
                                    onere.setDescrizione(onerePD
                                            .getDescrizione());
                                    onere.setImporto(String
                                            .valueOf((int) (onerePD
                                                    .getImporto() * 100)));
                                    onere.setNote(Utilities.NVL(
                                            onerePD.getNota(), ""));
                                    importoOneriProc += onerePD.getImporto();
                                }
                            }
                        }
                        if (this.getOneriPosticipati() != null) {
                            for (Iterator iterator3 = this
                                    .getOneriPosticipati().iterator(); iterator3
                                    .hasNext();) {
                                OneriBean onerePD = (OneriBean) iterator3
                                        .next();
                                if (onerePD.getCod_int() != null
                                        && onerePD.getCod_int()
                                        .equalsIgnoreCase(
                                                interventoPD
                                                .getCodice())) {
                                    OnereDettaglioType onere = oneriDett
                                            .addNewOnereDettaglio();
                                    onere.setCodice(onerePD.getCodice());
                                    onere.setCodiceCud("");
                                    onere.setCodiceDestinatario(onerePD
                                            .getCodDestinatario());
                                    onere.setDescrizione(onerePD
                                            .getDescrizione());
                                    onere.setImporto("0");
                                    onere.setNote(Utilities.NVL(
                                            onerePD.getNota(), ""));
                                }
                            }
                        }
                    }
                }
            }
            procedimento.setTotaleOneri((int) (importoOneriProc * 100));
            /*
             * if (procedimentoPD.getListaCodiciOneri()!=null &&
             * procedimentoPD.getListaCodiciOneri().size()>0) {
             * OneriDettaglioType listaOneri =
             * procedimento.addNewOneriDettaglio(); for (Iterator iterator2 =
             * procedimentoPD.getListaCodiciOneri().iterator();
             * iterator2.hasNext();) { String codOnere = (String)
             * iterator2.next(); Iterator itOneri =
             * this.getOneriAnticipati().iterator(); boolean
             * trovatoOnereAnticipato = false; while (!trovatoOnereAnticipato &&
             * itOneri.hasNext()) { OneriBean onerePD =
             * (OneriBean)itOneri.next(); if (onerePD!=null &&
             * onerePD.getCodice().equalsIgnoreCase(codOnere)){
             * trovatoOnereAnticipato = true; OnereDettaglioType onere =
             * listaOneri.addNewOnereDettaglio(); onere.setCodice(codOnere);
             * onere.setDescrizione(onerePD.getDescrizione());
             * onere.setImporto(String.valueOf((int)
             * (onerePD.getImporto()*100)));
             * onere.setNote(Utilities.NVL(onerePD.getNota(),""));
             * importoOneriProc += onerePD.getImporto(); } } if
             * (!trovatoOnereAnticipato){ itOneri =
             * this.getOneriPosticipati().iterator(); boolean
             * trovatoOnerePosticipato = false; while (!trovatoOnerePosticipato
             * && itOneri.hasNext()) { OneriBean onerePD =
             * (OneriBean)itOneri.next(); if (onerePD!=null &&
             * onerePD.getCodice().equalsIgnoreCase(codOnere)){
             * trovatoOnereAnticipato = true; OnereDettaglioType onere =
             * listaOneri.addNewOnereDettaglio(); onere.setCodice(codOnere);
             * onere.setDescrizione(onerePD.getDescrizione());
             * onere.setImporto("0");
             * onere.setNote("(Onere Posticipato) "+onerePD.getNota());
             * importoOneriProc +=
             * Double.parseDouble(Utilities.NVL(onere.getImporto(),"0")); } } }
             * } }
             */

        }
		// // riepilogo in formato PDF
        // boolean trovatoRiepPdf = false;
        // Iterator itAll = sportello.getListaAllegati().iterator();
        // while (itAll.hasNext() && !trovatoRiepPdf){
        // Attachment attach = (Attachment) itAll.next();
        // if (attach.getName().equalsIgnoreCase("riepilogo.pdf") &&
        // attach.getDescrizione().equalsIgnoreCase("riepilogo_"+this.getIdentificatorePeople().getIdentificatoreProcedimento()+"_"+sportello.getIdx()+".pdf")){
        // richiesta.setPdfIstanza(attach.getData());
        // }
        //
        // }

        if (listaCodiciNormative.size() > 0) {
            Set setKeyNormative = listaCodiciNormative.keySet();
            NormativeType listaNormative = richiesta.addNewNormative();
            for (Iterator iterator = setKeyNormative.iterator(); iterator
                    .hasNext();) {
                String keyNorm = (String) iterator.next();
                NormativaBean normativaPD = (NormativaBean) this
                        .getListaNormative().get(keyNorm);
                if (normativaPD != null) {
                    NormativaType norm = listaNormative.addNewNormativa();
                    norm.setCodRif(normativaPD.getCodRif());
                    norm.setNomeFile(normativaPD.getNomeFile());
                    norm.setNomeRiferimento(normativaPD.getNomeRiferimento());
                    norm.setTitoloRiferimento(normativaPD
                            .getTitoloRiferimento());
                }
            }
        }

        if (listaCodiciDichiarazioniDinamiche.size() > 0) { // DICHIARAZIONI
            // DINAMICHE
            Set setKeyDichiarazioniDinamiche = listaCodiciDichiarazioniDinamiche
                    .keySet();
            DichiarazioniDinamicheType listaDichiarazioniDinamiche = richiesta
                    .addNewDichiarazioniDinamiche();
            for (Iterator iterator = setKeyDichiarazioniDinamiche.iterator(); iterator
                    .hasNext();) {
                String keyDichirazioniDinamiche = (String) iterator.next();
                SezioneCompilabileBean href = (SezioneCompilabileBean) this
                        .getListaHref().get(keyDichirazioniDinamiche);
                if (href != null) {
                    DichiarazioneDinamicaType dd = listaDichiarazioniDinamiche
                            .addNewDichiarazioneDinamica();
                    dd.setHref(href.getHref());
                    dd.setTitolo(href.getTitolo());
                    dd.setPiedeHref(href.getPiedeHref());
                    CampiHrefType listaCampi = dd.addNewCampiHref();
                    for (Iterator iterator2 = href.getCampi().iterator(); iterator2
                            .hasNext();) {
                        HrefCampiBean campo = (HrefCampiBean) iterator2.next();
                        CampoHrefType cht = listaCampi.addNewCampoHref();
                        cht.setCampoXmlMod(campo.getCampo_xml_mod());
                        cht.setContatore(campo.getContatore());
                        cht.setControllo(campo.getControllo());
                        cht.setDecimali(String.valueOf(campo.getDecimali()));
                        cht.setDescrizione(Utilities.NVL(
                                campo.getDescrizione(), ""));
                        cht.setEdit(campo.getEdit());
                        cht.setLunghezza(String.valueOf(campo.getLunghezza()));
                        // cht.setMolteplicita(String.valueOf(campo.getMolteplicita()));

                        cht.setMolteplicita(campo.getNumCampo() == 0 ? String
                                .valueOf(campo.getNumCampo()) : String
                                .valueOf(campo.getMolteplicita()));

                        cht.setNome(Utilities.NVL(campo.getNome(), ""));
                        cht.setPosizione(String.valueOf(campo.getPosizione()));
                        cht.setRiga(String.valueOf(campo.getRiga()));
                        String tipo = campo.getTipo();
                        cht.setTipo((tipo.equalsIgnoreCase("A") ? "I" : tipo));
                        cht.setValore(Utilities.NVL(campo.getValore(), ""));
                        cht.setValoreUtente(Utilities.NVL(
                                campo.getValoreUtente(), ""));
                        if (campo.getOpzioniCombo() != null
                                && campo.getOpzioniCombo().size() > 0) {
                            OpzioniComboType opzCombo = cht
                                    .addNewOpzioniCombo();
                            for (Iterator iterator3 = campo.getOpzioniCombo()
                                    .iterator(); iterator3.hasNext();) {
                                BaseBean bb = (BaseBean) iterator3.next();
                                OpzioneComboType campoCombo = opzCombo
                                        .addNewOpzioneCombo();
                                campoCombo.setCodice(bb.getCodice());
                                campoCombo.setEtichetta(bb.getDescrizione());
                            }
                        }
                    }
                }
            }
        }

        // DICHIARAZIONI STATICHE
        if (listaCodiciDichiarazioniStatiche.size() > 0) {
            Set setKeyDichiarazioniStatiche = listaCodiciDichiarazioniStatiche
                    .keySet();
            DichiarazioniStaticheType listaDichiarazioniStatiche = richiesta
                    .addNewDichiarazioniStatiche();
            for (Iterator iterator = setKeyDichiarazioniStatiche.iterator(); iterator
                    .hasNext();) {
                String keyDichiarazioneStatica = (String) iterator.next();
                DichiarazioniStaticheBean dicStaticaPD = (DichiarazioniStaticheBean) this
                        .getListaDichiarazioniStatiche().get(
                                keyDichiarazioneStatica);
                if (dicStaticaPD != null) {
                    DichiarazioneStaticaType dichiarazioneS = listaDichiarazioniStatiche
                            .addNewDichiarazioneStatica();
                    dichiarazioneS.setChiave(dicStaticaPD.getCodice());
                    dichiarazioneS.setTitolo(dicStaticaPD.getTitolo());
                    dichiarazioneS.setValore(dicStaticaPD.getDescrizione());
                }
            }
        }
        if (listaCodiciModulistica.size() > 0) {
            Set setKeyModulistica = listaCodiciModulistica.keySet();
            ModulisticaType listaModulistica = richiesta.addNewModulistica();
            for (Iterator iterator = setKeyModulistica.iterator(); iterator
                    .hasNext();) {
                String keyModulistica = (String) iterator.next();
                ModulisticaBean modulisticaPD = (ModulisticaBean) this
                        .getListaModulistica().get(keyModulistica);
                if (modulisticaPD != null) {
                    ModuloType modulo = listaModulistica.addNewModulo();
                    modulo.setCodiceDoc(modulisticaPD.getCodiceDoc());
                    modulo.setNomeFile(modulisticaPD.getNomeFile());
                    modulo.setTipDoc(modulisticaPD.getTip_doc());
                    modulo.setTitolo(modulisticaPD.getTitolo());
                }
            }
        }
        if (listaCodiciDocumenti.size() > 0) {
            Set setKeyDocumenti = listaCodiciDocumenti.keySet();
            DocumentiType listaDocumenti = richiesta.addNewDocumenti();
            for (Iterator iterator = setKeyDocumenti.iterator(); iterator
                    .hasNext();) {
                String keyDocumento = (String) iterator.next();
                DocumentoBean documentoPD = (DocumentoBean) this
                        .getListaDocRichiesti().get(keyDocumento);
                if (documentoPD != null) {
                    DocumentoType documento = listaDocumenti.addNewDocumento();
                    documento.setCodice(documentoPD.getCodiceDoc());
                    documento.setCopie(((AllegatoBean) this.getListaAllegati()
                            .get(keyDocumento)).getCopie());
                    documento.setDescrizione(documentoPD.getTitolo());
                }
            }
        }

        if (getEsitoPagamento() != null
                && getEsitoPagamento().getEsito() != null
                && getEsitoPagamento().getEsito().equalsIgnoreCase("OK")
                && this.getRiepilogoOneriPagati() != null && this.getRiepilogoOneriPagati().getTotale() > 0) {
            OneriRiepilogoType oneri = richiesta.addNewOneriRiepilogo();
            oneri.setTotaleOneri(getDatiTemporanei().getTotalePagato());
            EsitoPagamentoType esitoPagamento = oneri.addNewEsitoPagamento();
            esitoPagamento.setAutorizzazione(getEsitoPagamento()
                    .getNumeroAutorizzazione());
            esitoPagamento.setCircuitoAutorizzativo(getEsitoPagamento()
                    .getCircuitoAutorizzativo());
            esitoPagamento.setCircuitoAutorizzativoD(getEsitoPagamento()
                    .getDescrizioneCircuitoAutorizzativo());
            esitoPagamento.setCircuitoSelezionato(getEsitoPagamento()
                    .getCircuitoSelezionato());
            esitoPagamento.setCircuitoSelezionatoD(getEsitoPagamento()
                    .getDescrizioneCircuitoSelezionato());
            GregorianCalendar cal = new GregorianCalendar();
            if (getEsitoPagamento().getDataAutorizzazione() != null) {
                cal.setTime(getEsitoPagamento().getDataAutorizzazione());
                esitoPagamento.setDataOra(Utilities.Cal2String(cal,
                        "dd/MM/yyyy HH:mm:ss"));
            } else {
                esitoPagamento.setDataOra("");
            }
            if (getEsitoPagamento().getDataOrdine() != null) {
                cal.setTime(getEsitoPagamento().getDataOrdine());
                esitoPagamento.setDataOraOrdine(Utilities.Cal2String(cal,
                        "dd/MM/yyyy HH:mm:ss"));
            } else {
                esitoPagamento.setDataOraOrdine("");
            }
            if (getEsitoPagamento().getDataTransazione() != null) {
                cal.setTime(getEsitoPagamento().getDataTransazione());
                esitoPagamento.setDataOraTransazione(Utilities.Cal2String(cal,
                        "dd/MM/yyyy HH:mm:ss"));
            } else {
                esitoPagamento.setDataOraTransazione("");
            }
            esitoPagamento.setDatiSpecifici(getEsitoPagamento()
                    .getDatiSpecifici());
            esitoPagamento.setEsito(getEsitoPagamento().getEsito());
            esitoPagamento.setEsitoD(getEsitoPagamento().getDescrizioneEsito());
            esitoPagamento.setIDOrdine(getEsitoPagamento().getIDOrdine());
            esitoPagamento.setIDTransazione(getEsitoPagamento()
                    .getIDTransazione());
            esitoPagamento.setImportoAutorizzato(String
                    .valueOf(getEsitoPagamento().getImportoPagato()));
            esitoPagamento.setImportoCommissioni(String
                    .valueOf(getEsitoPagamento().getImportoCommissioni()));
            esitoPagamento.setImportoTransato(String
                    .valueOf(getEsitoPagamento().getImportoPagato()));
            esitoPagamento.setNumeroOperazione(getEsitoPagamento()
                    .getNumeroOperazione());
            esitoPagamento.setPortaleID("");
            esitoPagamento.setSistemaPagamento(getEsitoPagamento()
                    .getSistemaPagamento());
            esitoPagamento.setSistemaPagamentoD(getEsitoPagamento()
                    .getDescrizioneSistemaPagamento());
            oneri.setPagamentoEffettuato(true);
            oneri.setModalitaPagamento(getDatiTemporanei()
                    .getModalitaPagamento());
            if (getRiepilogoOneriPagati() != null) {
                it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.OneriRiepilogoType.OneriPagati oneriPagati = oneri
                        .addNewOneriPagati();
                oneriPagati.setTotale(getRiepilogoOneriPagati().getTotale());
                if (getRiepilogoOneriPagati().getOneri() != null
                        && !getRiepilogoOneriPagati().getOneri().isEmpty()) {
                    it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.OneriRiepilogoType.OneriPagati.Onere onere;
                    OnereBean onereBean;
                    for (Iterator oneriIterator = getRiepilogoOneriPagati()
                            .getOneri().iterator(); oneriIterator.hasNext(); onere
                            .setRiversamentoAutomatico(onereBean
                                    .isRiversamentoAutomatico())) {
                        onere = oneriPagati.addNewOnere();
                        onereBean = (OnereBean) oneriIterator.next();
                        if (onereBean.getCodice() != null) {
                            onere.setCodice(onereBean.getCodice());
                        }
                        if (onereBean.getAeCodiceUtente() != null) {
                            onere.setAeCodiceUtente(onereBean.getAeCodiceUtente());
                        }
                        if (onereBean.getAeCodiceEnte() != null) {
                            onere.setAeCodiceEnte(onereBean.getAeCodiceEnte());
                        }
                        if (onereBean.getAeCodiceUfficio() != null) {
                            onere.setAeCodiceUfficio(onereBean
                                    .getAeCodiceUfficio());
                        }
                        if (onereBean.getAeTipoUfficio() != null) {
                            onere.setAeTipoUfficio(onereBean.getAeTipoUfficio());
                        }
                        if (onereBean.getCodiceDestinatario() != null) {
                            onere.setCodiceDestinatario(onereBean
                                    .getCodiceDestinatario());
                        }
                        if (onereBean.getDescrizione() != null) {
                            onere.setDescrizione(onereBean.getDescrizione());
                        } else {
                            onere.setDescrizione("");
                        }
                        if (onereBean.getDescrizioneDestinatario() != null) {
                            onere.setDescrizioneDestinatario(onereBean
                                    .getDescrizioneDestinatario());
                        }
                        onere.setImporto(onereBean.getImporto());
                    }

                }
            }
        }
        if (isAttestatoPagamentoObbligatorio()) {
            OneriRiepilogoType oneri = richiesta.addNewOneriRiepilogo();
            oneri.setPagamentoEffettuato(true);
            oneri.setModalitaPagamento(getDatiTemporanei()
                    .getModalitaPagamento());
            if (getRiepilogoOneriPagati() != null) {
                it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.OneriRiepilogoType.OneriPagati oneriPagati = oneri
                        .addNewOneriPagati();
                oneriPagati.setTotale(getRiepilogoOneriPagati().getTotale());
                if (getRiepilogoOneriPagati().getOneri() != null
                        && !getRiepilogoOneriPagati().getOneri().isEmpty()) {
                    it.gruppoinit.b110.concessioniEAutorizzazioni.procedimentoUnico.OneriRiepilogoType.OneriPagati.Onere onere;
                    OnereBean onereBean;
                    for (Iterator oneriIterator = getRiepilogoOneriPagati()
                            .getOneri().iterator(); oneriIterator.hasNext(); onere
                            .setRiversamentoAutomatico(onereBean
                                    .isRiversamentoAutomatico())) {
                        onere = oneriPagati.addNewOnere();
                        onereBean = (OnereBean) oneriIterator.next();
                        if (onereBean.getCodice() != null) {
                            onere.setCodice(onereBean.getCodice());
                        }
                        if (onereBean.getAeCodiceUtente() != null) {
                            onere.setAeCodiceUtente(onereBean.getAeCodiceUtente());
                        }
                        if (onereBean.getAeCodiceEnte() != null) {
                            onere.setAeCodiceEnte(onereBean.getAeCodiceEnte());
                        }
                        if (onereBean.getAeCodiceUfficio() != null) {
                            onere.setAeCodiceUfficio(onereBean
                                    .getAeCodiceUfficio());
                        }
                        if (onereBean.getAeTipoUfficio() != null) {
                            onere.setAeTipoUfficio(onereBean.getAeTipoUfficio());
                        }
                        if (onereBean.getCodiceDestinatario() != null) {
                            onere.setCodiceDestinatario(onereBean
                                    .getCodiceDestinatario());
                        }
                        if (onereBean.getDescrizione() != null) {
                            onere.setDescrizione(onereBean.getDescrizione());
                        } else {
                            onere.setDescrizione("");
                        }
                        if (onereBean.getDescrizioneDestinatario() != null) {
                            onere.setDescrizioneDestinatario(onereBean
                                    .getDescrizioneDestinatario());
                        }
                        onere.setImporto(onereBean.getImporto());
                    }

                }
            }
        }

        if (listaCodiciDestinatari.size() > 0) {
            DestinatariType destinatari = richiesta.addNewDestinatari();
            Set set = listaCodiciDestinatari.keySet();
            Iterator iterator = set.iterator();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                DestinatarioBean destinatarioPD = (DestinatarioBean) this
                        .getListaDestinatari().get(key);
                if (destinatarioPD != null) {
                    DestinatarioType destinatario = destinatari
                            .addNewDestinatario();
                    destinatario.setCodiceDestinatario(key);
                    destinatario.setCap(destinatarioPD.getCap());
                    destinatario.setCitta(destinatarioPD.getCitta());
                    destinatario.setCodiceEnte(destinatarioPD.getCod_ente());
                    destinatario.setEmail(destinatarioPD.getEmail());
                    destinatario.setFax(destinatarioPD.getFax());
                    destinatario.setIndirizzo(destinatarioPD.getVia());
                    destinatario.setIntestazione(destinatarioPD
                            .getIntestazione());
                    destinatario.setNome(destinatarioPD.getNome_dest());
                    destinatario.setProvincia(destinatarioPD.getProvincia());
                    destinatario.setTelefono(destinatarioPD.getTelefono());
                }
            }
        }

        if (listaCuds.size() > 0) {
            CudsType cuds = richiesta.addNewCuds();
            Set set = listaCuds.keySet();
            Iterator iterator = set.iterator();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                String desc = (String) listaCuds.get(key);
                CudType cud = cuds.addNewCud();
                cud.setCodice(key);
                cud.setDescrizione(desc);
            }
        }

        if (listaClassiEnte.size() > 0) {
            ClassiEnteType classiEnti = richiesta.addNewClassiEnte();
            Set set = listaClassiEnte.keySet();
            Iterator iterator = set.iterator();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                BaseBean bb = (BaseBean) listaClassiEnte.get(key);
                ClasseEnteType classeEnte = classiEnti.addNewClasseEnte();
                classeEnte.setCodice(key);
                classeEnte.setDescrizione(bb.getDescrizione());
                classeEnte.setFlgComune(bb.getCodice());
            }
        }

        pd.setAttribute(PipelineDataImpl.XML_PROCESSDATA_PARAMNAME,
                generateXml(richiestaDocument));
    }

    public boolean isComunica(AnagraficaBean anagrafica2) {
        String val = getCampoDinamicoAnagrafica(anagrafica2.getListaCampi(),
                "ANAG_SCELTA_COMUNICA"); // test ANAG_RAPPSOC_MOTIVAZIONE
        if (val != null && val.equalsIgnoreCase("2")) { // 2=SI
            return true;
        }
        return false;
    }

    private boolean isFormatRemoteFile(String data) {
        String[] tmp = data.split("\\|\\|");
        if (tmp != null && tmp.length == 5) {
            return true;
        }
        return false;
    }

    private int maxProfAlberoOperazioni(ArrayList treeOp) {
        int max = 0;
        for (Iterator iterator = treeOp.iterator(); iterator.hasNext();) {
            OperazioneBean op = (OperazioneBean) iterator.next();
            if (max < op.getProfondita()) {
                max = op.getProfondita();
            }
        }
        return max;
    }

    public static String generateXml(XmlObject object) {
        String ret = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n";
        ret += object.toString();
        return ret;
    }

    public boolean isFineSceltaOp() {
        return fineSceltaOp;
    }

    public void setFineSceltaOp(boolean fineSceltaOp) {
        this.fineSceltaOp = fineSceltaOp;
    }

    public ArrayList getInterventi() {
        return interventi;
    }

    public void setInterventi(ArrayList interventi) {
        this.interventi = interventi;
    }

    public void addInterventi(InterventoBean intervento) {
        this.interventi.add(intervento);
    }

    public ArrayList getInterventiFacoltativi() {
        return interventiFacoltativi;
    }

    public void setInterventiFacoltativi(ArrayList interventiFacoltativi) {
        this.interventiFacoltativi = interventiFacoltativi;
    }

    public void addInterventiFacoltativi(InterventoBean interventoFacoltativo) {
        this.interventiFacoltativi.add(interventoFacoltativo);
    }

    public ErrorBean getErrore() {
        return errore;
    }

    public void setErrore(ErrorBean errore) {
        this.errore = errore;
    }

    public boolean isInternalError() {
        return internalError;
    }

    public void setInternalError(boolean internalError) {
        this.internalError = internalError;
    }

    public int getLivelloSceltaOp() {
        return livelloSceltaOp;
    }

    public void setLivelloSceltaOp(int livelloSceltaOp) {
        this.livelloSceltaOp = livelloSceltaOp;
    }

    public LinkedHashMap getListaAllegati() {
        return listaAllegati;
    }

    public void setListaAllegati(LinkedHashMap listaAllegati) {
        this.listaAllegati = listaAllegati;
    }

    public void addListaAllegati(String key, AllegatoBean allegato) {
        this.listaAllegati.put(key, allegato);
    }

	// public void addListaAllegati(AllegatoBean allegato) {
    // this.listaAllegati.put(allegato.getCodice(), allegato);
    // }
    public HashMap getListaProcedimenti() {
        return listaProcedimenti;
    }

    public void setListaProcedimenti(HashMap listaProcedimenti) {
        this.listaProcedimenti = listaProcedimenti;
    }

    public void addListaProcedimenti(String key, ProcedimentoBean allegato) {
        this.listaProcedimenti.put(key, allegato);
    }

    public HashMap getListaSportelli() {
        return listaSportelli;
    }

    public void setListaSportelli(HashMap listaSportelli) {
        this.listaSportelli = listaSportelli;
    }

    public void addListaSportelli(String key, SportelloBean sportello) {
        this.listaSportelli.put(key, sportello);
    }

    public HashMap getListaNormative() {
        return listaNormative;
    }

    public void setListaNormative(HashMap listaNormative) {
        this.listaNormative = listaNormative;
    }

    public void addListaNormative(String key, NormativaBean normativa) {
        this.listaNormative.put(key, normativa);
    }

	// PC - ordinamento dichiarazioni
    public ArrayList getListaHrefOrdered() {
        return listaHrefOrdered;
    }

    public void setListaHrefOrdered(ArrayList listaHrefOrdered) {
        this.listaHrefOrdered = listaHrefOrdered;
    }

    public void addListaHrefOrdered(String key) {
        this.listaHrefOrdered.add(key);
    }

	// PC - ordinamento dichiarazioni
    public HashMap getListaHref() {
        return listaHref;
    }

    public void setListaHref(HashMap listaHref) {
        this.listaHref = listaHref;
    }

    public void addListaHref(String key, SezioneCompilabileBean href) {
        this.listaHref.put(key, href);
    }

    public LinkedHashMap getListaDichiarazioniStatiche() {
        return listaDichiarazioniStatiche;
    }

    public void setListaDichiarazioniStatiche(LinkedHashMap listaDichiarazioniStatiche) {
        this.listaDichiarazioniStatiche = listaDichiarazioniStatiche;
    }

    public void addListaDichiarazioniStatiche(String key,
            DichiarazioniStaticheBean dichiarazioneStatica) {
        this.listaDichiarazioniStatiche.put(key, dichiarazioneStatica);
    }

    public LinkedHashMap getListaModulistica() {
        return listaModulistica;
    }

    public void setListaModulistica(LinkedHashMap listaModulistica) {
        this.listaModulistica = listaModulistica;
    }

    public void addListaModulistica(String key, ModulisticaBean mod) {
        this.listaModulistica.put(key, mod);
    }

    public LinkedHashMap getListaDocRichiesti() {
        return listaDocRichiesti;
    }

    public void setListaDocRichiesti(LinkedHashMap listaDocRichiesti) {
        this.listaDocRichiesti = listaDocRichiesti;
    }

    public void addListaDocRichiesti(String key, DocumentoBean doc) {
        this.listaDocRichiesti.put(key, doc);
    }

    public ArrayList getListaUpload() {
        return listaUpload;
    }

    public void setListaUpload(ArrayList listaUpload) {
        this.listaUpload = listaUpload;
    }

    public void addListaUpload(ArrayList listaUpload) {
        this.listaUpload = listaUpload;
    }

    public FormFile getUploadFile() {
        return uploadFile;
    }

    public void setUploadFile(FormFile uploadFile) {
        this.uploadFile = uploadFile;
    }

    public DatiTemporaneiBean getDatiTemporanei() {
        return datiTemporanei;
    }

    public void setDatiTemporanei(DatiTemporaneiBean datiTemporanei) {
        this.datiTemporanei = datiTemporanei;
    }

    public String getDescBookmark() {
        return descBookmark;
    }

    public void setDescBookmark(String descBookmark) {
        this.descBookmark = descBookmark;
    }

    public String getIdBookmark() {
        return idBookmark;
    }

    public void setIdBookmark(String idBookmark) {
        this.idBookmark = idBookmark;
    }

    public String getNomeBookmark() {
        return nomeBookmark;
    }

    public void setNomeBookmark(String nomeBookmark) {
        this.nomeBookmark = nomeBookmark;
    }

    public String getLastStepId() {
        return lastStepId;
    }

    public void setLastStepId(String lastStepId) {
        this.lastStepId = lastStepId;
    }

    public String getLastActivityId() {
        return lastActivityId;
    }

    public void setLastActivityId(String lastActivityId) {
        this.lastActivityId = lastActivityId;
    }

    public boolean isAttivaPagamenti() {
        return attivaPagamenti;
    }

    public void setAttivaPagamenti(boolean attivaPagamenti) {
        this.attivaPagamenti = attivaPagamenti;
    }

	// public boolean isForzaPagamento() {
    // return forzaPagamento;
    // }
    //
    // public void setforzaPagamento(boolean forzaPagamento) {
    // this.forzaPagamento = forzaPagamento;
    // }
    /**
     * @return the modalitaPagamentoSoloOnLine
     */
    public final boolean isModalitaPagamentoSoloOnLine() {
        return this.modalitaPagamentoSoloOnLine;
    }

    /**
     * @return the tipoAttivazioneOneri
     */
    public final String getTipoAttivazioneOneri() {
        return this.tipoAttivazioneOneri;
    }

    /**
     * @param tipoAttivazioneOneri the tipoAttivazioneOneri to set
     */
    public final void setTipoAttivazioneOneri(String tipoAttivazioneOneri) {
        this.tipoAttivazioneOneri = tipoAttivazioneOneri;
    }

    /**
     * @param modalitaPagamentoSoloOnLine the modalitaPagamentoSoloOnLine to set
     */
    public final void setModalitaPagamentoSoloOnLine(
            boolean modalitaPagamentoSoloOnLine) {
        this.modalitaPagamentoSoloOnLine = modalitaPagamentoSoloOnLine;
    }

    /**
     * @return the modalitaPagamentoOpzionaleSoloOnLine
     */
    public final boolean isModalitaPagamentoOpzionaleSoloOnLine() {
        return this.modalitaPagamentoOpzionaleSoloOnLine;
    }

    /**
     * @param modalitaPagamentoOpzionaleSoloOnLine the
     * modalitaPagamentoOpzionaleSoloOnLine to set
     */
    public final void setModalitaPagamentoOpzionaleSoloOnLine(
            boolean modalitaPagamentoOpzionaleSoloOnLine) {
        this.modalitaPagamentoOpzionaleSoloOnLine = modalitaPagamentoOpzionaleSoloOnLine;
    }

    /**
     * @return the attestatoPagamentoObbligatorio
     */
    public final boolean isAttestatoPagamentoObbligatorio() {
        return this.attestatoPagamentoObbligatorio;
    }

    /**
     * @param attestatoPagamentoObbligatorio the attestatoPagamentoObbligatorio
     * to set
     */
    public final void setAttestatoPagamentoObbligatorio(
            boolean attestatoPagamentoObbligatorio) {
        this.attestatoPagamentoObbligatorio = attestatoPagamentoObbligatorio;
    }

    public Set getOneriAnticipati() {
        return oneriAnticipati;
    }

    public void setOneriAnticipati(Set oneriAnticipati) {
        this.oneriAnticipati = oneriAnticipati;
    }

    public void addOneriAnticipati(OneriBean bean) {
        logger.debug("inside addOneriAnticipati: bean=" + bean);
        try {
            oneriAnticipati.add(bean);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    public Set getOneriPosticipati() {
        return oneriPosticipati;
    }

    public void setOneriPosticipati(Set oneriPosticipati) {
        this.oneriPosticipati = oneriPosticipati;
    }

    public void addOneriPosticipati(OneriBean bean) {
        logger.debug("inside addOneriAnticipati: bean=" + bean);
        try {
            oneriPosticipati.add(bean);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    public ArrayList getListaAlberoOneri() {
        return listaAlberoOneri;
    }

    public void setListaAlberoOneri(ArrayList listaAlberoOneri) {
        this.listaAlberoOneri = listaAlberoOneri;
    }

    public void addListaAlberoOneri(OneriBean onereBean) {
        this.listaAlberoOneri.add(onereBean);
    }

	// public void setForzaPagamento(boolean forzaPagamento) {
    // this.forzaPagamento = forzaPagamento;
    // }
    public String[] getOneriVec() {
        return oneriVec;
    }

    public void setOneriVec(String[] oneriVec) {
        this.oneriVec = oneriVec;
    }

    public void addOneriVec(String str) {
        logger.debug("inside addOneriVec: str=" + str);

        int size = oneriVec.length + 1;
        String[] temp = new String[size];
        logger.debug("inside addOneriVec: size=" + size);
        logger.debug("inside addOneriVec: oneriVec.length=" + oneriVec.length);

        try {
            for (int i = 0; i < oneriVec.length; i++) {
                temp[i] = oneriVec[i];
            }
            temp[size - 1] = str;
            oneriVec = temp;
        } catch (Exception e) {
            logger.error(e);
        }
    }

    public RiepilogoOneri getRiepilogoOneri() {
        return riepilogoOneri;
    }

    public void setRiepilogoOneri(RiepilogoOneri riepilogoOneri) {
        this.riepilogoOneri = riepilogoOneri;
    }

    public boolean isOneriAnticipatiPresent() {
        return oneriAnticipatiPresent;
    }

    public void setOneriAnticipatiPresent(boolean oneriAnticipatiPresent) {
        this.oneriAnticipatiPresent = oneriAnticipatiPresent;
    }

    public boolean isOneriCalcolatiPresent() {
        return oneriCalcolatiPresent;
    }

    public void setOneriCalcolatiPresent(boolean oneriCalcolatiPresent) {
        this.oneriCalcolatiPresent = oneriCalcolatiPresent;
    }

    public AnagraficaBean getAnagrafica() {
        return anagrafica;
    }

    public void setAnagrafica(AnagraficaBean anagrafica) {
        this.anagrafica = anagrafica;
    }

    public EsitoPagamento getEsitoPagamento() {
        return esitoPagamento;
    }

    public void setEsitoPagamento(EsitoPagamento esitoPagamento) {
        this.esitoPagamento = esitoPagamento;
    }

	// public SignedInfo[] getSInfo() {
    // return sInfo;
    // }
    //
    // public void setSInfo(SignedInfo[] info) {
    // sInfo = info;
    // }
    public String getCredenzialiBase64() {
        return credenzialiBase64;
    }

    public void setCredenzialiBase64(String credenzialiBase64) {
        this.credenzialiBase64 = credenzialiBase64;
    }

    public String getCampoDinamicoAnagrafica(ArrayList listaCampi,
            String campoXmlMod) {
        boolean trovato = false;
        String ret = "";
        Iterator it = listaCampi.iterator();
        while (it.hasNext() && !trovato) {
            HrefCampiBean campoAnagrafica = (HrefCampiBean) it.next();
            if (campoAnagrafica.getCampo_xml_mod() != null
                    && campoAnagrafica.getCampo_xml_mod().equalsIgnoreCase(
                            campoXmlMod)) {
                trovato = true;
                ret = campoAnagrafica.getValoreUtente();
            }
        }
        return ret;
    }

    public InterventoBean recuperaIntervento(String codiceIntervento) {
        InterventoBean intervento = null;
        boolean trovato = false;
        Iterator it = this.getInterventi().iterator();
        while (it.hasNext() && !trovato) {
            InterventoBean in = (InterventoBean) it.next();
            if (in.getCodice() != null
                    && in.getCodice().equalsIgnoreCase(codiceIntervento)) {
                trovato = true;
                intervento = in;
            }
        }
        if (!trovato) {
            Iterator it2 = this.getInterventiFacoltativi().iterator();
            while (it2.hasNext() && !trovato) {
                InterventoBean in = (InterventoBean) it2.next();
                if (in.getCodice() != null
                        && in.getCodice().equalsIgnoreCase(codiceIntervento)) {
                    trovato = true;
                    intervento = in;
                }
            }
        }
        if (trovato) {
            return intervento;
        } else {
            return null;
        }
    }

    public List getErroreSuHref() {
        return erroreSuHref;
    }

    public void setErroreSuHref(List erroreSuHref) {
        this.erroreSuHref = erroreSuHref;
    }

    public void addErroreSuHref(String erroreSuHref) {
        this.erroreSuHref.add(erroreSuHref);
    }

    public String getTipoBookmark() {
        return tipoBookmark;
    }

    public void setTipoBookmark(String tipoBookmark) {
        this.tipoBookmark = tipoBookmark;
    }

    public String getTipoPagamentoBookmark() {
        return tipoPagamentoBookmark;
    }

    public void setTipoPagamentoBookmark(String tipoPagamentoBookmark) {
        this.tipoPagamentoBookmark = tipoPagamentoBookmark;
    }

    /**
     * @return the modalitaPagamentoBookmarkSoloOnLine
     */
    public final boolean isModalitaPagamentoBookmarkSoloOnLine() {
        return this.modalitaPagamentoBookmarkSoloOnLine;
    }

    /**
     * @param modalitaPagamentoBookmarkSoloOnLine the
     * modalitaPagamentoBookmarkSoloOnLine to set
     */
    public final void setModalitaPagamentoBookmarkSoloOnLine(
            boolean modalitaPagamentoBookmarkSoloOnLine) {
        this.modalitaPagamentoBookmarkSoloOnLine = modalitaPagamentoBookmarkSoloOnLine;
    }

    /**
     * @return the modalitaPagamentoOpzionaleBookmarkSoloOnLine
     */
    public final boolean isModalitaPagamentoOpzionaleBookmarkSoloOnLine() {
        return this.modalitaPagamentoOpzionaleBookmarkSoloOnLine;
    }

    /**
     * @param modalitaPagamentoOpzionaleBookmarkSoloOnLine the
     * modalitaPagamentoOpzionaleBookmarkSoloOnLine to set
     */
    public final void setModalitaPagamentoOpzionaleBookmarkSoloOnLine(
            boolean modalitaPagamentoOpzionaleBookmarkSoloOnLine) {
        this.modalitaPagamentoOpzionaleBookmarkSoloOnLine = modalitaPagamentoOpzionaleBookmarkSoloOnLine;
    }

    public String getFirmaBookmark() {
        return firmaBookmark;
    }

    public void setFirmaBookmark(String firmaBookmark) {
        this.firmaBookmark = firmaBookmark;
    }

    public int getLivelloSceltaMinOp() {
        return livelloSceltaMinOp;
    }

    public void setLivelloSceltaMinOp(int livelloSceltaMinOp) {
        this.livelloSceltaMinOp = livelloSceltaMinOp;
    }

    public String getNteldic() {
        return nteldic;
    }

    public void setNteldic(String nteldic) {
        this.nteldic = nteldic;
    }

    public IntermediariBean getIntermediari() {
        return intermediari;
    }

    public void setIntermediari(IntermediariBean intermediari) {
        this.intermediari = intermediari;
    }

    public boolean isBandiAttivi() {
        return bandiAttivi;
    }

    public void setBandiAttivi(boolean bandiAttivi) {
        this.bandiAttivi = bandiAttivi;
    }

    public int getLivelloSceltaSettore() {
        return livelloSceltaSettore;
    }

    public void setLivelloSceltaSettore(int livelloSceltaSettore) {
        this.livelloSceltaSettore = livelloSceltaSettore;
    }

    public int getLivelloSceltaMinSettore() {
        return livelloSceltaMinSettore;
    }

    public void setLivelloSceltaMinSettore(int livelloSceltaMinSettore) {
        this.livelloSceltaMinSettore = livelloSceltaMinSettore;
    }

    public ArrayList getAltriRichiedenti() {
        return altriRichiedenti;
    }

    public void setAltriRichiedenti(ArrayList altriRichiedenti) {
        this.altriRichiedenti = altriRichiedenti;
    }

    public void addAltriRichiedenti(AnagraficaBean altroRichiedente) {
        this.altriRichiedenti.add(altroRichiedente);
    }

    public ArrayList getListaProcuratori() {
        return listaProcuratori;
    }

    public void setListaProcuratori(ArrayList listaProcuratori) {
        this.listaProcuratori = listaProcuratori;
    }

    public void addListaProcuratori(BaseBean procuratore) {
        if (this.listaProcuratori == null) {
            this.listaProcuratori = new ArrayList();
        }
        this.listaProcuratori.add(procuratore);
    }

    public SezioneCompilabileBean getOggettoIstanza() {
        return oggettoIstanza;
    }

    public void setOggettoIstanza(SezioneCompilabileBean oggettoIstanza) {
        this.oggettoIstanza = oggettoIstanza;
    }

    public String getRemoteAttachFile() {
        return remoteAttachFile;
    }

    public void setRemoteAttachFile(String remoteAttachFile) {
        this.remoteAttachFile = remoteAttachFile;
    }

    public HashMap getListaDestinatari() {
        return listaDestinatari;
    }

    public void setListaDestinatari(HashMap listaDestinatari) {
        this.listaDestinatari = listaDestinatari;
    }

    public void addListaDestinatari(String key, DestinatarioBean destinatario) {
        this.listaDestinatari.put(key, destinatario);
    }

    /**
     * @return the entePeopleKey
     */
    public final String getEntePeopleKey() {
        return this.entePeopleKey;
    }

    /**
     * @param entePeopleKey the entePeopleKey to set
     */
    public final void setEntePeopleKey(String entePeopleKey) {
        this.entePeopleKey = entePeopleKey;
    }

    /**
     * @return the uploadedSize
     */
    public final int getUploadedSize() {
        return this.uploadedSize;
    }

    /**
     * @param uploadedSize the uploadedSize to set
     */
    public final void setUploadedSize(int uploadedSize) {
        this.uploadedSize = uploadedSize;
    }

    public Object getCustomObject() {
        return customObject;
    }

    public void setCustomObject(HashMap customObject) {
        this.customObject = customObject;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public final RiepilogoOneriPagati getRiepilogoOneriPagati() {
        return riepilogoOneriPagati;
    }

    public final void setRiepilogoOneriPagati(
            RiepilogoOneriPagati riepilogoOneriPagati) {
        this.riepilogoOneriPagati = riepilogoOneriPagati;
    }

    public final Vector getRegistryRequestSavedFields() {
        return registryRequestSavedFields;
    }

    public final void setRegistryRequestSavedFields(Vector registryRequestSavedFields) {
        this.registryRequestSavedFields = registryRequestSavedFields;
    }

    public final ProfiloPersonaFisica getTitolarePagamento() {
        return titolarePagamento;
    }

    public final void setTitolarePagamento(ProfiloPersonaFisica titolarePagamento) {
        this.titolarePagamento = titolarePagamento;
    }
// PC - tieni traccia delle scelte su allegati facoltativi - inizio

    public HashMap getListaAllegatiFacoltativi() {
        return listaAllegatiFacoltativi;
    }

    public void setListaAllegatiFacoltativi(HashMap listaAllegatiFacoltativi) {
        this.listaAllegatiFacoltativi = listaAllegatiFacoltativi;
    }

    public void addListaAllegatiFacoltativi(String key, AllegatoBean allegato) {
        this.listaAllegatiFacoltativi.put(key, allegato);
    }
// PC - tieni traccia delle scelte su allegati facoltativi - fine	
}
