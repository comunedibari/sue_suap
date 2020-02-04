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
package it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.steps;

import it.gruppoinit.b1.concessioniEAutorizzazioni.precompilazione.PrecompilazioneBeanDocument;
import it.people.IValidationErrors;
import it.people.core.exception.ServiceException;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.dao.ProcedimentoUnicoDAO;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.AllegatiBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.AnagraficaBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.BaseBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.CampoPrecompilazioneBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ComuneBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.DichiarazioniStaticheBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.HrefCampiBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.InterventoBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.Output;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.PrecompilazioneBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.Procedimento;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ProcedimentoBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ProcedimentoSempliceBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.SezioneCompilabileBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.Sportello;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.support.CreatePdf;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.support.CreateSinglePdfWhite;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.support.HtmlRenderer;
import it.people.fsl.servizi.oggetticondivisi.PersonaFisica;
import it.people.fsl.servizi.oggetticondivisi.UtenteAutenticato;
import it.people.fsl.servizi.oggetticondivisi.luogo.Indirizzo;
import it.people.fsl.servizi.oggetticondivisi.personagiuridica.PersonaGiuridica;
import it.people.fsl.servizi.oggetticondivisi.personagiuridica.Sede;
import it.people.fsl.servizi.oggetticondivisi.tipibase.Data;
import it.people.parser.exception.ParserException;
import it.people.process.AbstractPplProcess;
import it.people.process.SurfDirection;
import it.people.sirac.accr.beans.AbstractProfile;
import it.people.sirac.accr.beans.ProfiloPersonaFisica;
import it.people.sirac.accr.beans.ProfiloPersonaGiuridica;
import it.people.sirac.core.SiracConstants;
import it.people.sirac.core.SiracHelper;
import it.people.util.ServiceParameters;
import it.people.util.XmlObjectWrapper;
import it.people.vsl.exception.SendException;
import it.people.wrappers.IRequestWrapper;

import java.beans.IntrospectionException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

import javax.servlet.ServletException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.betwixt.io.BeanReader;
import org.apache.commons.betwixt.io.BeanWriter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.xml.sax.SAXException;

// import com.lowagie.text.*;
// import com.lowagie.text.pdf.PdfContentByte;
// import com.lowagie.text.pdf.PdfWriter;
// import com.lowagie.text.pdf.BaseFont;

/**
 * E' la classe relativa alla pagina di presentazione del modello unico. <br/>
 * Prima della visualizzazione della domanda vengono inizializzate le
 * dichiarazioni dinamice e statiche. <br/> Dopo la visualizzazione: <br/> -
 * Viene controllato che la domanda sia stata compilata in ogni sua parte <br/> -
 * Viene effettuata la scompattazione del modello unico per gli eventuali
 * destinatari <br/> Il meetodo loopback gestisce l'apertura ed il salvataggio
 * dei dati di varie pagine non di iter, in particolare: <br/> - Compilazione
 * delle dichiarazioni dinamiche (compresa la precompilazione e la validazione
 * dei campi) <br/> - Allegati <br/> - Normative <br/> - Stampa di qualit� <br/>
 * 
 * @author InIT http://www.gruppoinit.it
 * 
 * 16-giu-2006
 */
public class ModelloUnicoStep extends BaseStep {
    /*
     * (non-Javadoc)
     * 
     * @see it.people.IStep#service(it.people.process.AbstractPplProcess,
     *      it.people.wrappers.IRequestWrapper)
     */
    private Log log = LogFactory.getLog(this.getClass());

    public void service(AbstractPplProcess process, IRequestWrapper request) throws IOException, ServletException {

        if (initialise(request)) {
            if (saveHistory(process, request))
                return;
            logStep(process);
            enableBottomNavigationBar(process, true);
            
            ProcedimentoUnicoDAO delegate = new ProcedimentoUnicoDAO(db, language);

            // Recupero se � un servizo di livello inferiore al 3
            session.removeAttribute("stop");
            
            /*String serviceName = "it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico";
            String idbookmark = (String) session.getAttribute("idbookmark");
            if (idbookmark != null && serviceName != null) {
                PeopleServiceProfile serviceProfile = null;
                try {
                    serviceProfile = delegate.caricaServiceProfile(serviceName, ""
                                                                                + idbookmark);
                } catch (XmlException e) {
                } catch (peopleException e) {
                }
                if (serviceProfile != null) {
                    Service service = serviceProfile.getService();
                    boolean autenticazioneForte = service.getSecurityProfile().getStrongAuthentication();
                    boolean autenticazioneDebole = service.getSecurityProfile().getWeakAuthentication();
                    if (!autenticazioneForte && !autenticazioneDebole) {
                        session.setAttribute("stop", "true");
                    }
                }
            }*/
            ProcessData dataForm = (ProcessData) process.getData();
            
            // Soluzione 1
            String userID = (String)session.getAttribute(SiracConstants.SIRAC_AUTHENTICATED_USER);
            boolean isAnonymous = SiracHelper.isAnonymousUser(userID);
            
            // Soluzione 2
            /*
             * Modifica init 16/04/2007 (1.3.1)
             * Modificato il metodo per il recupero dello stato dell'autenticazione dell'utente in accordo con le indicazioni di Pianciamore
             */
            /*PplUser pplUser = process.getContext().getUser ();
            boolean isAnonymous = pplUser.isAnonymous();*/
            if (isAnonymous) {
                session.setAttribute("stop", "true");
                dataForm.getIdentificatorePeople().setIdentificatoreProcedimento("");
            }
            if(isServizioLivelloDue()){
                session.setAttribute("stop", "true");
            }

            if(dataForm.getDichiarazioniStatiche() == null){
                dataForm.setDichiarazioniStatiche(new ArrayList());
            }
            /*
             * Modifica init 16/04/2007 (1.3.1)
             * Modifica per risolvere bug evidenzito da Comune di Firenze: per evitare che le dichiarazioni statiche siano doppiate se l'utente va allo step precedente
             * e poi a quello successivo ne viene forzato il ricalcolo ad ogni accesso allo step ModelloUnico.java
             * Il bug si era creato a seguito della modifica [Modifica Ferretti 09/03/2007 (versione 1.2.3)] su BaseStep.java
             */
            else{
                dataForm.getDichiarazioniStatiche().clear();
            }
            boolean sezioniCompilabiliGiaCreate = true;
            if (dataForm.getSezioniCompilabiliBean() == null
                || dataForm.getSezioniCompilabiliBean().isEmpty()) {
                sezioniCompilabiliGiaCreate = false;
                dataForm.setSezioniCompilabiliBean(new HashMap());
            }
            
            // Abilitazione form dati associazione di categoria
            //dataForm.setDatiAssociazioneDiCategoria(new AssociazioneDiCategoriaBean());
            boolean abilitaFormAssociazioneCategoria = isAssociazioneCategoriaAbilitato(dataForm);
            dataForm.getDatiAssociazioneDiCategoria().setAbilitato(abilitaFormAssociazioneCategoria);

            // Recupero i dati del titolare
            AbstractProfile profiloTitolare = (AbstractProfile) session.getAttribute(it.people.sirac.core.SiracConstants.SIRAC_ACCR_TITOLARE);
            if (profiloTitolare != null) {
                dataForm.setProfiloTitolareNew(profiloTitolare);
                if (profiloTitolare instanceof ProfiloPersonaFisica) {
                    logger.debug("Il titolare � una persona fisica");
                    PersonaFisica pf = new PersonaFisica();
                    ProfiloPersonaFisica pt = (ProfiloPersonaFisica) profiloTitolare;
                    pf.setCodiceFiscale(pt.getCodiceFiscale() == null ? "FRRFRC70D01H199K" : pt.getCodiceFiscale());
                    
                    Data data = new Data();
                    Date dataNascita = pt.getDataNascita();
                    if (dataNascita != null) {
                        data.setData(dataNascita);
                    } else {
                        data.setData(new Date());
                    }
                    pf.setSesso(pt.getSesso());
                    pf.setDatadiNascita(data);
                    pf.getLuogodiNascita().getComune().setNome(pt.getLuogoNascita() == null ? "" : pt.getLuogoNascita());
                    pf.setCognome(pt.getCognome() == null ? "" : pt.getCognome());
                    pf.setNome(pt.getNome() == null ? "" : pt.getNome());
                    Indirizzo indirizzo = new Indirizzo();
                    indirizzo.setVia(pt.getIndirizzoResidenza() == null ? "" : pt.getIndirizzoResidenza());
                    indirizzo.getLuogo().getComune().setNome(dataForm.getComune().getCitta());
                    indirizzo.getLuogo().getComune().getProvincia().setNome(dataForm.getComune().getProvincia());
                    pf.setResidenza(indirizzo);
                    pf.setDomicilio(indirizzo);
                    pf.setSesso(pt.getSesso());
                    dataForm.getTitolare().setPersonaFisica(pf);
                } else/*
                         * if (profiloTitolare instanceof
                         * ProfiloPersonaGiuridica)
                         */{
                    logger.debug("Il titolare � una persona giuridica");
                    PersonaGiuridica pg = new PersonaGiuridica();
                    ProfiloPersonaGiuridica pt = (ProfiloPersonaGiuridica) profiloTitolare;
                    pg.setCodiceFiscale(pt.getCodiceFiscale() == null ? "FRRFRC70D01H199K" : pt.getCodiceFiscale());
                    pg.setPartitaIVA(pt.getPartitaIva() == null ? "12345678901" : pt.getPartitaIva());
                    pg.setDenominazione(pt.getDenominazione() == null ? "" : pt.getDenominazione());
                    pg.setRagioneSociale(pt.getDenominazione() == null ? "" : pt.getDenominazione());
                    PersonaFisica rappresentanteLegale = new PersonaFisica();
                    ProfiloPersonaFisica profiloRappresentante = pt.getRappresentanteLegale();
                    // TODO copiare le propriet� comuni da profiloRappresentante
                    // a
                    // rappresentanteLegale
                    pg.setRappresentanteLegale(rappresentanteLegale);
                    // controllo per evitare NullPointerException
                    Sede sede = pg.getSedeLegale();
                    if (sede == null) {
                        sede = pg.getSedeOperativa();
                        if (sede == null) {
                            sede = new Sede();
                        }
                    }
                    Indirizzo indirizzo = new Indirizzo();
                    indirizzo.getLuogo().getComune().setNome(dataForm.getComune().getCitta());
                    indirizzo.getLuogo().getComune().getProvincia().setNome(dataForm.getComune().getProvincia());
                    indirizzo.setVia(pt.getSedeLegale() == null ? "" : pt.getSedeLegale());
                    sede.setIndirizzoStrutturato(indirizzo);
                    pg.setSedeLegale(sede);
                    pg.setSedeOperativa(sede);
                    dataForm.getTitolare().setPersonaGiuridica(pg);
                }
            }

            // Precompilo i dati relativi al richiedente
            if (getSurfDirection(process) == SurfDirection.forward && session.getAttribute("muincompleto") == null) {
                AnagraficaBean datiAnagrafici = dataForm.getAnagrafica();
                if (datiAnagrafici != null) {
                    if (dataForm.getRichiedente().getUtenteAutenticato() != null) {
                        UtenteAutenticato utenteAut = dataForm.getRichiedente().getUtenteAutenticato();
                        if(!isAnonymous){
                            datiAnagrafici.getDichiarante().setNome(utenteAut.getNome());
                            datiAnagrafici.getDichiarante().setCognome(utenteAut.getCognome());
                            datiAnagrafici.getDichiarante().setLuogoNascita(utenteAut.getLuogodiNascita().getComune().getNome());
                            datiAnagrafici.getDichiarante().setProvinciaNascita(utenteAut.getLuogodiNascita().getComune().getProvincia().getNome());
                            datiAnagrafici.getDichiarante().setDataNascita(""
                                                                           + utenteAut.getDatadiNascita());
                            datiAnagrafici.getDichiarante().setSesso(utenteAut.getSesso());
                            datiAnagrafici.getDichiarante().setCodiceFiscale(utenteAut.getCodiceFiscale());
                            datiAnagrafici.getDichiarante().getResidenza().setCitta(utenteAut.getResidenza().getLuogo().getComune().getNome());
                            datiAnagrafici.getDichiarante().getResidenza().setVia(utenteAut.getResidenza().getVia());
                            datiAnagrafici.getDichiarante().getResidenza().setNumero(utenteAut.getResidenza().getNumeroCivico());
                            datiAnagrafici.getDichiarante().getResidenza().setCap(utenteAut.getResidenza().getCAP());
                            datiAnagrafici.getDichiarante().getResidenza().setProvincia(utenteAut.getResidenza().getLuogo().getComune().getProvincia().getNome());
                        }
                        
                        List recapiti = utenteAut.getRecapito();
                        if(recapiti != null){
                            datiAnagrafici.getDichiarante().getResidenza().setEmail(recapiti.get(0) != null ? (String)recapiti.get(0) : "");
                            datiAnagrafici.getDichiarante().getResidenza().setTelefono(recapiti.get(1) != null ? (String)recapiti.get(1) : "");
                        }
                    }
                    if (dataForm.getTitolare().getPersonaGiuridica() != null) {
                        PersonaGiuridica personaGiuridica = dataForm.getTitolare().getPersonaGiuridica();
                        if(!isAnonymous){
                            datiAnagrafici.getAttivita().setDenominazione(personaGiuridica.getRagioneSociale());
                            datiAnagrafici.getAttivita().getSede().setCitta(personaGiuridica.getSedeLegale().getIndirizzoStrutturato().getLuogo().getComune().getNome());
                            datiAnagrafici.getAttivita().getSede().setProvincia(personaGiuridica.getSedeLegale().getIndirizzoStrutturato().getLuogo().getComune().getProvincia().getNome());
                            datiAnagrafici.getAttivita().getSede().setCap(personaGiuridica.getSedeLegale().getIndirizzoStrutturato().getCAP());
                            datiAnagrafici.getAttivita().getSede().setVia(personaGiuridica.getSedeLegale().getIndirizzoStrutturato().getVia());
                            datiAnagrafici.getAttivita().getSede().setNumero(personaGiuridica.getSedeLegale().getIndirizzoStrutturato().getNumeroCivico());
                            datiAnagrafici.getAttivita().setCodiceFiscale(personaGiuridica.getCodiceFiscale());
                            datiAnagrafici.getAttivita().setPartitaIva(personaGiuridica.getPartitaIVA());
                        }
                        datiAnagrafici.getAttivita().setInQualitaDi("2");

                        dataForm.getAnagrafica().getDichiarante().setAgiscePerContoDi("0");
                    } else if (dataForm.getTitolare().getPersonaFisica() != null) {
                        PersonaFisica personaFisica = dataForm.getTitolare().getPersonaFisica();
                        if (!personaFisica.getCodiceFiscale().equalsIgnoreCase(dataForm.getRichiedente().getUtenteAutenticato().getCodiceFiscale())) {
                            if(!isAnonymous){
                                datiAnagrafici.getDatiPersonaDeleganteBean().setNome(personaFisica.getNome());
                                datiAnagrafici.getDatiPersonaDeleganteBean().setCognome(personaFisica.getCognome());
                                datiAnagrafici.getDatiPersonaDeleganteBean().setLuogoNascita(personaFisica.getLuogodiNascita().getComune().getNome());
                                datiAnagrafici.getDatiPersonaDeleganteBean().setDataNascita(""
                                                                                            + personaFisica.getDatadiNascita());
                                datiAnagrafici.getDatiPersonaDeleganteBean().setCodiceFiscale(personaFisica.getCodiceFiscale());
                                datiAnagrafici.getDatiPersonaDeleganteBean().getResidenza().setCitta(personaFisica.getResidenza().getLuogo().getComune().getNome());
                                datiAnagrafici.getDatiPersonaDeleganteBean().getResidenza().setProvincia(personaFisica.getResidenza().getLuogo().getComune().getProvincia().getNome());
                                datiAnagrafici.getDatiPersonaDeleganteBean().getResidenza().setCap(personaFisica.getResidenza().getCAP());
                                datiAnagrafici.getDatiPersonaDeleganteBean().getResidenza().setVia(personaFisica.getResidenza().getVia());
                                datiAnagrafici.getDatiPersonaDeleganteBean().getResidenza().setNumero(personaFisica.getResidenza().getNumeroCivico());
                            }
                            datiAnagrafici.getAttivita().setInQualitaDiPersonaFisica("2");

                            dataForm.getAnagrafica().getDichiarante().setAgiscePerContoDi("2");
                        } else {
                            // 31/01/2008 INIT: tolta la preselezione del radiobutton per obbligare l'utente ad una scelta
                            dataForm.getAnagrafica().getDichiarante().setAgiscePerContoDi("");
                        }
                    } else {
                        // 31/01/2008 INIT: tolta la preselezione del radiobutton per obbligare l'utente ad una scelta
                        dataForm.getAnagrafica().getDichiarante().setAgiscePerContoDi("");
                    }
                }
            }
            session.removeAttribute("muincompleto");
            try {
                // Genero la stringa html rappresentativa dei vari href
                Set allegatiSel = dataForm.getAllegatiSelezionati();
                // List allegatiSel = dataForm.getAllegati();
                Iterator it = allegatiSel.iterator();
                String href = null;
                while (it.hasNext()) {
                    AllegatiBean allegatoBean = (AllegatiBean) it.next();
                    href = allegatoBean.getHref();
                    if (href != null) {
                        // Recupero le sezioni compilabili
                        if (!sezioniCompilabiliGiaCreate) {
                            if (dataForm.getSezioniCompilabiliBean().get(href) == null) {
                                SezioneCompilabileBean sezComp = delegate.getHrefDefinitions(href, dataForm, (allegatoBean.getDescrizione() == null || allegatoBean.getDescrizione().equalsIgnoreCase("")) ? allegatoBean.getTitolo() : allegatoBean.getDescrizione(), (allegatoBean.getTitolo() == null || allegatoBean.getTitolo().equalsIgnoreCase("")) ? allegatoBean.getDescrizione() : allegatoBean.getTitolo(), allegatoBean.getCodiceIntervento(), isAnonymous);
                                int liv = livelloMaxCampiMultipli(sezComp);
                                String html = HtmlRenderer.costruisciStringaHtml(sezComp, false, "SectionText", null, null, null, null, null, null, liv, dataForm);
                                sezComp.setHtml(html);
                                html = HtmlRenderer.costruisciStringaHtml(sezComp, true, "SectionText", "TableRow0", "96%", "0", "6", null, "25", liv, dataForm);
                                sezComp.setHtmlEditable(html);
                                settaCompletamento(sezComp);
                                dataForm.getSezioniCompilabiliBean().put(href, sezComp);
                            } else {
                                SezioneCompilabileBean sezComp = (SezioneCompilabileBean) dataForm.getSezioniCompilabiliBean().get(href);
                                String srtInt = sezComp.getIntervento();
                                sezComp.setIntervento(srtInt
                                                      + "|"
                                                      + allegatoBean.getCodiceIntervento());
                            }
                        }
                    } else if (allegatoBean.getFlagDic().equalsIgnoreCase("S")) {
                        // Recupero le dichiarazioni statiche
                        DichiarazioniStaticheBean dicStat = new DichiarazioniStaticheBean();
                        dicStat.setDescrizione(allegatoBean.getDescrizione());
                        dicStat.setTitolo(allegatoBean.getTitolo());
                        dicStat.setIntervento(allegatoBean.getCodiceIntervento());
                        dataForm.getDichiarazioniStatiche().add(dicStat);
                    }
                }
                // Aggiungo gli href controllati da anagrafica
                gestioneHrefAnagrafica(dataForm, delegate);
                
            } catch (SQLException e) {
                logger.error(e);
            }
            // ricalcolaSezioniCompilabili(process, request, false);
        }
    }
    
    /*
     * Modifica Init 08/05/2007 (1.3.2)
     * Aggiunta la gestione dei servizi di livello 2
     */
    private boolean isServizioLivelloDue(){
        try {
            Properties p = new Properties();
            ServiceParameters params = (ServiceParameters) session.getAttribute("serviceParameters");
            String absPathToService = params.get("absPathToService");
            // Da cambiare per debug
            p.load(new FileInputStream(absPathToService
                                       + System.getProperty("file.separator")
                                       + "risorse"
                                       + System.getProperty("file.separator")
                                       + "messaggi.properties"));
            String labelOggetto = p.getProperty("AC.livellodue"+session.getAttribute("idbookmark"));
            if(labelOggetto != null && labelOggetto.equalsIgnoreCase("Y")){
                return true;
            }
            return false;
        } catch (IOException e) {
            logger.error(e);
            return false;
        }
    }
    
    private boolean isCodiceAssociazioneCategoriaValido(ProcessData dataForm, String codiceControllo){
        try {
            Properties p = new Properties();
            ServiceParameters params = (ServiceParameters) session.getAttribute("serviceParameters");
            String absPathToService = params.get("absPathToService");
            // Da cambiare per debug
            try{
                p.load(new FileInputStream(absPathToService
                                            + System.getProperty("file.separator")
                                            + "risorse"
                                            + System.getProperty("file.separator")
                                            + "associazioneCategoria.properties"));
            }
            catch(Exception ex){
                p.load(new FileInputStream("E:\\eclipse\\eclipse3.0\\workspace\\People-2.0\\web\\WEB-INF\\classes\\it\\people\\fsl\\servizi\\concessioniedautorizzazioni\\servizicondivisi\\procedimentounico"
                                            + System.getProperty("file.separator")
                                            + "risorse"
                                            + System.getProperty("file.separator")
                                            + "associazioneCategoria.properties"));
            }
            String codiciDiControllo = p.getProperty("codiceControllo"+session.getAttribute("idbookmark"));
            if(codiciDiControllo == null || codiciDiControllo.equalsIgnoreCase("")){
                return false;
            }
            if(codiceControllo.equalsIgnoreCase("")){
                return true;
            }
            List listaCodiciControllo = parseCodiciControllo(codiciDiControllo);
            Iterator it = listaCodiciControllo.iterator();
            while(it.hasNext()){
                String codice = (String)it.next();
                if(codice.equalsIgnoreCase(codiceControllo))
                    return true;
            }
            return false;
        } catch (IOException e) {
            logger.error(e);
            /*ArrayList listaErrori = new ArrayList();
            listaErrori.add(e.toString());
            dataForm.setErroreSuHref(listaErrori);*/
            return false;
        }
    }
    
    private boolean isAssociazioneCategoriaAbilitato(ProcessData dataForm){
        try {
            Properties p = new Properties();
            ServiceParameters params = (ServiceParameters) session.getAttribute("serviceParameters");
            String absPathToService = params.get("absPathToService");
            try{
                p.load(new FileInputStream(absPathToService
                                           + System.getProperty("file.separator")
                                           + "risorse"
                                           + System.getProperty("file.separator")
                                           + "associazioneCategoria.properties"));
            }
            catch(Exception ex){
                p.load(new FileInputStream("E:\\eclipse\\eclipse3.0\\workspace\\People-2.0\\web\\WEB-INF\\classes\\it\\people\\fsl\\servizi\\concessioniedautorizzazioni\\servizicondivisi\\procedimentounico"
                                            + System.getProperty("file.separator")
                                            + "risorse"
                                            + System.getProperty("file.separator")
                                            + "associazioneCategoria.properties"));
            }
            String codiciDiControllo = p.getProperty("codiceControllo"+session.getAttribute("idbookmark"));
            if(codiciDiControllo == null){
                return false;
            }
            return true;
        } catch (IOException e) {
            logger.error(e);
            /*ArrayList listaErrori = new ArrayList();
            listaErrori.add(e.toString());
            dataForm.setErroreSuHref(listaErrori);*/
            return false;
        }
    }
    
    private boolean isUtenteAmministratoreStampe(ProcessData dataForm){
        try {
            Properties p = new Properties();
            ServiceParameters params = (ServiceParameters) session.getAttribute("serviceParameters");
            String absPathToService = params.get("absPathToService");
            try{
                p.load(new FileInputStream(absPathToService
                                           + System.getProperty("file.separator")
                                           + "risorse"
                                           + System.getProperty("file.separator")
                                           + "utenteAmministratoreStampe.properties"));
            }
            catch(Exception ex){
                p.load(new FileInputStream("E:\\eclipse\\eclipse3.0\\workspace\\People-2.0\\web\\WEB-INF\\classes\\it\\people\\fsl\\servizi\\concessioniedautorizzazioni\\servizicondivisi\\procedimentounico"
                                            + System.getProperty("file.separator")
                                            + "risorse"
                                            + System.getProperty("file.separator")
                                            + "utenteAmministratoreStampe.properties"));
            }
            String CFutenteAmministratore = p.getProperty("codiceFiscaleAmministratoreStampe");
            if(CFutenteAmministratore == null){
                return false;
            }
            else{
                if(dataForm.getRichiedente().getUtenteAutenticato().getCodiceFiscale().equalsIgnoreCase(CFutenteAmministratore)){
                    return true;
                }
            }
            return false;
        } catch (IOException e) {
            logger.error(e);
            /*ArrayList listaErrori = new ArrayList();
            listaErrori.add(e.toString());
            dataForm.setErroreSuHref(listaErrori);*/
            return true;
        }
    }
    
    private static List parseCodiciControllo(String valore) {
        List retVal = new ArrayList();
        if (valore != null && !valore.equalsIgnoreCase("")) {
            boolean continua = valore.indexOf(",") >= 0;
            if (!continua) {
                retVal.add(valore);
            }
            while (continua) {
                retVal.add(valore.substring(0, valore.indexOf(",")));
                valore = valore.substring(valore.indexOf(",") + 1);
                continua = valore.indexOf(",") >= 0;
                if (!continua) {
                    retVal.add(valore);
                }
            }
        }
        return retVal;
    }
    
    /*
     * Modifica Init 18/05/2007 (1.3.3)
     * Parametrizzato (nel messaggi.properties) il blocco del servizio nel caso che non siano state compilate tutte le parti obbligatorie.
     * Il metodo restituisce true se il blocco viene disattivato
     */
    private boolean disabilitaControlloCompletamento(ProcessData dataForm){
        if(isUtenteAmministratoreStampe(dataForm)){
            return true;
        }
        try {
            Properties p = new Properties();
            ServiceParameters params = (ServiceParameters) session.getAttribute("serviceParameters");
            String absPathToService = params.get("absPathToService");
            // Da cambiare per debug
            p.load(new FileInputStream(absPathToService
                                       + System.getProperty("file.separator")
                                       + "risorse"
                                       + System.getProperty("file.separator")
                                       + "messaggi.properties"));
            String labelOggetto = p.getProperty("AC.bloccoMU"+session.getAttribute("idbookmark"));
            if(labelOggetto != null && labelOggetto.equalsIgnoreCase("N")){
                return true;
            }
            return false;
        } catch (IOException e) {
            logger.error(e);
            return false;
        }
    }
    
    private void gestioneHrefAnagrafica(ProcessData dataForm, ProcedimentoUnicoDAO delegate){
        String label_imp_legale = "";
        String label_imp_procuratore = "";
        String label_imp_delegato = "";
        
        String label_pers_legale = "";
        String label_pers_procuratore = "";
        String label_pers_delegato = "";
        
        String label_noprof_legale = "";
        String label_noprof_procuratore = "";
        String label_noprof_delegato = "";
        
        String titolo_imp_legale = "";
        String desc_imp_legale = "";
        String titolo_imp_procuratore = "";
        String desc_imp_procuratore = "";
        String titolo_imp_delegato = "";
        String desc_imp_delegato = "";
        
        String titolo_pers_legale = "";
        String desc_pers_legale = "";
        String titolo_pers_procuratore = "";
        String desc_pers_procuratore = "";
        String titolo_pers_delegato = "";
        String desc_pers_delegato = "";
        
        String titolo_noprof_legale = "";
        String desc_noprof_legale = "";
        String titolo_noprof_procuratore = "";
        String desc_noprof_procuratore = "";
        String titolo_noprof_delegato = "";
        String desc_noprof_delegato = "";
        try {
            Properties p = new Properties();
            ServiceParameters params = (ServiceParameters) session.getAttribute("serviceParameters");
            String absPathToService = params.get("absPathToService");
            // Da cambiare per debug
            p.load(new FileInputStream(absPathToService
                                       + System.getProperty("file.separator")
                                       + "risorse"
                                       + System.getProperty("file.separator")
                                       + "messaggi.properties"));
            label_imp_legale = p.getProperty("AC.impresa.legale.dinamico");
            label_imp_procuratore = p.getProperty("AC.impresa.procuratore.dinamico");
            label_imp_delegato = p.getProperty("AC.impresa.delegato.dinamico");
            
            label_pers_legale = p.getProperty("AC.persona.legale.dinamico");
            label_pers_procuratore = p.getProperty("AC.persona.procuratore.dinamico");
            label_pers_delegato = p.getProperty("AC.persona.delegato.dinamico");
            
            label_noprof_legale = p.getProperty("AC.noprofit.legale.dinamico");
            label_noprof_procuratore = p.getProperty("AC.noprofit.procuratore.dinamico");
            label_noprof_delegato = p.getProperty("AC.noprofit.delegato.dinamico");

            titolo_imp_legale = p.getProperty("AC.impresa.legale.statico.titolo");
            desc_imp_legale = p.getProperty("AC.impresa.legale.statico.descrizione");
            titolo_imp_procuratore = p.getProperty("AC.impresa.procuratore.statico.titolo");
            desc_imp_procuratore = p.getProperty("AC.impresa.procuratore.statico.descrizione");
            titolo_imp_delegato = p.getProperty("AC.impresa.delegato.statico.titolo");
            desc_imp_delegato = p.getProperty("AC.impresa.delegato.statico.descrizione");
            
            titolo_pers_legale = p.getProperty("AC.persona.legale.statico.titolo");
            desc_pers_legale = p.getProperty("AC.persona.legale.statico.descrizione");
            titolo_pers_procuratore = p.getProperty("AC.persona.procuratore.statico.titolo");
            desc_pers_procuratore = p.getProperty("AC.persona.procuratore.statico.descrizione");
            titolo_pers_delegato = p.getProperty("AC.persona.delegato.statico.titolo");
            desc_pers_delegato = p.getProperty("AC.persona.delegato.statico.descrizione");
            
            titolo_noprof_legale = p.getProperty("AC.noprofit.legale.statico.titolo");
            desc_noprof_legale = p.getProperty("AC.noprofit.legale.statico.descrizione");
            titolo_noprof_procuratore = p.getProperty("AC.noprofit.procuratore.statico.titolo");
            desc_noprof_procuratore = p.getProperty("AC.noprofit.procuratore.statico.descrizione");
            titolo_noprof_delegato = p.getProperty("AC.noprofit.delegato.statico.titolo");
            desc_noprof_delegato = p.getProperty("AC.noprofit.delegato.statico.descrizione");
            
            if(label_imp_legale == null)
                label_imp_legale = "";
            if(label_imp_procuratore == null)
                label_imp_procuratore = "";
            if(label_imp_delegato == null)
                label_imp_delegato = "";
            if(label_pers_legale == null)
                label_pers_legale = "";
            if(label_pers_procuratore == null)
                label_pers_procuratore = "";
            if(label_pers_delegato == null)
                label_pers_delegato = "";
            if(label_noprof_legale == null)
                label_noprof_legale = "";
            if(label_noprof_procuratore == null)
                label_noprof_procuratore = "";
            if(label_noprof_delegato == null)
                label_noprof_delegato = "";
        } catch (IOException e) {
            logger.error(e);
            label_imp_legale = "A6000";
            label_imp_procuratore = "A6000";
            label_imp_delegato = "A6000";
            
            label_pers_legale = "href_pers";
            label_pers_procuratore = "href_pers";
            label_pers_delegato = "href_pers";
            
            label_noprof_legale = "href_noprof";
            label_noprof_procuratore = "href_noprof";
            label_noprof_delegato = "href_noprof";
        }
        try{
            List listaDicStatiche = dataForm.getDichiarazioniStatiche();
            List listaDicStaticheDaTogliere = new ArrayList();
            Iterator it = listaDicStatiche.iterator();
            while(it.hasNext()){
                DichiarazioniStaticheBean ds = (DichiarazioniStaticheBean) it.next();
                if(ds.getDescrizione().equalsIgnoreCase(desc_imp_legale) || 
                        ds.getDescrizione().equalsIgnoreCase(desc_imp_procuratore) || 
                        ds.getDescrizione().equalsIgnoreCase(desc_imp_delegato) || 
                        ds.getDescrizione().equalsIgnoreCase(desc_pers_legale) || 
                        ds.getDescrizione().equalsIgnoreCase(desc_pers_procuratore) || 
                        ds.getDescrizione().equalsIgnoreCase(desc_pers_delegato) || 
                        ds.getDescrizione().equalsIgnoreCase(desc_noprof_legale) ||
                        ds.getDescrizione().equalsIgnoreCase(desc_noprof_procuratore) ||
                        ds.getDescrizione().equalsIgnoreCase(desc_noprof_delegato)){
                    listaDicStaticheDaTogliere.add(ds);
                }
            }
            Iterator itDaTogliere = listaDicStaticheDaTogliere.iterator();
            while(itDaTogliere.hasNext()){
                listaDicStatiche.remove((DichiarazioniStaticheBean)itDaTogliere.next());
            }
            
            // Da togliere: messo per non avere pi� il menu a tendina
            dataForm.getAnagrafica().getAttivita().setInQualitaDi("0");
            dataForm.getAnagrafica().getAttivita().setInQualitaDiAziendaNoProfit("0");
            dataForm.getAnagrafica().getAttivita().setInQualitaDiPersonaFisica("0");
            
            if(dataForm.getAnagrafica().getDichiarante().getAgiscePerContoDi().equalsIgnoreCase("0") &&
                    dataForm.getAnagrafica().getAttivita().getInQualitaDi().equalsIgnoreCase("0")){
                
                if(!label_imp_procuratore.equalsIgnoreCase(label_imp_legale))
                    dataForm.getSezioniCompilabiliBean().remove(label_imp_procuratore);
                if(!label_imp_delegato.equalsIgnoreCase(label_imp_legale))
                    dataForm.getSezioniCompilabiliBean().remove(label_imp_delegato);
                if(!label_pers_legale.equalsIgnoreCase(label_imp_legale))
                    dataForm.getSezioniCompilabiliBean().remove(label_pers_legale);
                if(!label_pers_procuratore.equalsIgnoreCase(label_imp_legale))
                    dataForm.getSezioniCompilabiliBean().remove(label_pers_procuratore);
                if(!label_pers_delegato.equalsIgnoreCase(label_imp_legale))
                    dataForm.getSezioniCompilabiliBean().remove(label_pers_delegato);
                if(!label_noprof_legale.equalsIgnoreCase(label_imp_legale))
                    dataForm.getSezioniCompilabiliBean().remove(label_noprof_legale);
                if(!label_noprof_procuratore.equalsIgnoreCase(label_imp_legale))
                    dataForm.getSezioniCompilabiliBean().remove(label_noprof_procuratore);
                if(!label_noprof_delegato.equalsIgnoreCase(label_imp_legale))
                    dataForm.getSezioniCompilabiliBean().remove(label_noprof_delegato);
                
                if(dataForm.getSezioniCompilabiliBean().get(label_imp_legale) == null){
                    SezioneCompilabileBean sezComp = delegate.getHrefDefinitions(label_imp_legale, dataForm, null, null, "FORCED");
                    if(sezComp.getDescrizione() != null && !sezComp.getDescrizione().equalsIgnoreCase("")){
                        int liv = livelloMaxCampiMultipli(sezComp);
                        String html = HtmlRenderer.costruisciStringaHtml(sezComp, false, "SectionText", null, null, null, null, null, null, liv, dataForm);
                        sezComp.setHtml(html);
                        html = HtmlRenderer.costruisciStringaHtml(sezComp, true, "SectionText", "TableRow0", "96%", "0", "6", null, "25", liv, dataForm);
                        sezComp.setHtmlEditable(html);
                        settaCompletamento(sezComp);
                        dataForm.getSezioniCompilabiliBean().put(label_imp_legale, sezComp);
                    }
                }
                if(titolo_imp_legale != null && desc_imp_legale != null && !titolo_imp_legale.equalsIgnoreCase("") && !desc_imp_legale.equalsIgnoreCase("")){
                    DichiarazioniStaticheBean dicStat = new DichiarazioniStaticheBean();
                    dicStat.setDescrizione(desc_imp_legale);
                    dicStat.setTitolo(titolo_imp_legale);
                    dicStat.setIntervento("FORCED");
                    dataForm.getDichiarazioniStatiche().add(dicStat);
                }
            }
            else if(dataForm.getAnagrafica().getDichiarante().getAgiscePerContoDi().equalsIgnoreCase("0") &&
                    dataForm.getAnagrafica().getAttivita().getInQualitaDi().equalsIgnoreCase("1")){
                
                if(!label_imp_legale.equalsIgnoreCase(label_imp_procuratore))
                    dataForm.getSezioniCompilabiliBean().remove(label_imp_legale);
                if(!label_imp_delegato.equalsIgnoreCase(label_imp_procuratore))
                    dataForm.getSezioniCompilabiliBean().remove(label_imp_delegato);
                if(!label_pers_legale.equalsIgnoreCase(label_imp_procuratore))
                    dataForm.getSezioniCompilabiliBean().remove(label_pers_legale);
                if(!label_pers_procuratore.equalsIgnoreCase(label_imp_procuratore))
                    dataForm.getSezioniCompilabiliBean().remove(label_pers_procuratore);
                if(!label_pers_delegato.equalsIgnoreCase(label_imp_procuratore))
                    dataForm.getSezioniCompilabiliBean().remove(label_pers_delegato);
                if(!label_noprof_legale.equalsIgnoreCase(label_imp_procuratore))
                    dataForm.getSezioniCompilabiliBean().remove(label_noprof_legale);
                if(!label_noprof_procuratore.equalsIgnoreCase(label_imp_procuratore))
                    dataForm.getSezioniCompilabiliBean().remove(label_noprof_procuratore);
                if(!label_noprof_delegato.equalsIgnoreCase(label_imp_procuratore))
                    dataForm.getSezioniCompilabiliBean().remove(label_noprof_delegato);
                
                if(dataForm.getSezioniCompilabiliBean().get(label_imp_procuratore) == null){
                    SezioneCompilabileBean sezComp = delegate.getHrefDefinitions(label_imp_procuratore, dataForm, null, null, "FORCED");
                    if(sezComp.getDescrizione() != null && !sezComp.getDescrizione().equalsIgnoreCase("")){
                        int liv = livelloMaxCampiMultipli(sezComp);
                        String html = HtmlRenderer.costruisciStringaHtml(sezComp, false, "SectionText", null, null, null, null, null, null, liv, dataForm);
                        sezComp.setHtml(html);
                        html = HtmlRenderer.costruisciStringaHtml(sezComp, true, "SectionText", "TableRow0", "96%", "0", "6", null, "25", liv, dataForm);
                        sezComp.setHtmlEditable(html);
                        settaCompletamento(sezComp);
                        dataForm.getSezioniCompilabiliBean().put(label_imp_procuratore, sezComp);
                    }
                }
                if(titolo_imp_procuratore != null && desc_imp_procuratore != null && !titolo_imp_procuratore.equalsIgnoreCase("") && !desc_imp_procuratore.equalsIgnoreCase("")){
                    DichiarazioniStaticheBean dicStat = new DichiarazioniStaticheBean();
                    dicStat.setDescrizione(desc_imp_procuratore);
                    dicStat.setTitolo(titolo_imp_procuratore);
                    dicStat.setIntervento("FORCED");
                    dataForm.getDichiarazioniStatiche().add(dicStat);
                }
            }else if(dataForm.getAnagrafica().getDichiarante().getAgiscePerContoDi().equalsIgnoreCase("0") &&
                    dataForm.getAnagrafica().getAttivita().getInQualitaDi().equalsIgnoreCase("2")){
                
                if(!label_imp_legale.equalsIgnoreCase(label_imp_delegato))
                    dataForm.getSezioniCompilabiliBean().remove(label_imp_legale);
                if(!label_imp_procuratore.equalsIgnoreCase(label_imp_delegato))
                    dataForm.getSezioniCompilabiliBean().remove(label_imp_procuratore);
                if(!label_pers_legale.equalsIgnoreCase(label_imp_delegato))
                    dataForm.getSezioniCompilabiliBean().remove(label_pers_legale);
                if(!label_pers_procuratore.equalsIgnoreCase(label_imp_delegato))
                    dataForm.getSezioniCompilabiliBean().remove(label_pers_procuratore);
                if(!label_pers_delegato.equalsIgnoreCase(label_imp_delegato))
                    dataForm.getSezioniCompilabiliBean().remove(label_pers_delegato);
                if(!label_noprof_legale.equalsIgnoreCase(label_imp_delegato))
                    dataForm.getSezioniCompilabiliBean().remove(label_noprof_legale);
                if(!label_noprof_procuratore.equalsIgnoreCase(label_imp_delegato))
                    dataForm.getSezioniCompilabiliBean().remove(label_noprof_procuratore);
                if(!label_noprof_delegato.equalsIgnoreCase(label_imp_delegato))
                    dataForm.getSezioniCompilabiliBean().remove(label_noprof_delegato);
                
                if(dataForm.getSezioniCompilabiliBean().get(label_imp_delegato) == null){
                    SezioneCompilabileBean sezComp = delegate.getHrefDefinitions(label_imp_delegato, dataForm, null, null, "FORCED");
                    if(sezComp.getDescrizione() != null && !sezComp.getDescrizione().equalsIgnoreCase("")){
                        int liv = livelloMaxCampiMultipli(sezComp);
                        String html = HtmlRenderer.costruisciStringaHtml(sezComp, false, "SectionText", null, null, null, null, null, null, liv, dataForm);
                        sezComp.setHtml(html);
                        html = HtmlRenderer.costruisciStringaHtml(sezComp, true, "SectionText", "TableRow0", "96%", "0", "6", null, "25", liv, dataForm);
                        sezComp.setHtmlEditable(html);
                        settaCompletamento(sezComp);
                        dataForm.getSezioniCompilabiliBean().put(label_imp_delegato, sezComp);
                    }
                }
                if(titolo_imp_delegato != null && desc_imp_delegato != null && !titolo_imp_delegato.equalsIgnoreCase("") && !desc_imp_delegato.equalsIgnoreCase("")){
                    DichiarazioniStaticheBean dicStat = new DichiarazioniStaticheBean();
                    dicStat.setDescrizione(desc_imp_delegato);
                    dicStat.setTitolo(titolo_imp_delegato);
                    dicStat.setIntervento("FORCED");
                    dataForm.getDichiarazioniStatiche().add(dicStat);
                }
            }
            
            
            
            else if(dataForm.getAnagrafica().getDichiarante().getAgiscePerContoDi().equalsIgnoreCase("2") &&
                    dataForm.getAnagrafica().getAttivita().getInQualitaDi().equalsIgnoreCase("0")){
                
                if(!label_imp_legale.equalsIgnoreCase(label_pers_legale))
                    dataForm.getSezioniCompilabiliBean().remove(label_imp_legale);
                if(!label_imp_procuratore.equalsIgnoreCase(label_pers_legale))
                    dataForm.getSezioniCompilabiliBean().remove(label_imp_procuratore);
                if(!label_imp_delegato.equalsIgnoreCase(label_pers_legale))
                    dataForm.getSezioniCompilabiliBean().remove(label_imp_delegato);
                if(!label_pers_procuratore.equalsIgnoreCase(label_pers_legale))
                    dataForm.getSezioniCompilabiliBean().remove(label_pers_procuratore);
                if(!label_pers_delegato.equalsIgnoreCase(label_pers_legale))
                    dataForm.getSezioniCompilabiliBean().remove(label_pers_delegato);
                if(!label_noprof_legale.equalsIgnoreCase(label_pers_legale))
                    dataForm.getSezioniCompilabiliBean().remove(label_noprof_legale);
                if(!label_noprof_procuratore.equalsIgnoreCase(label_pers_legale))
                    dataForm.getSezioniCompilabiliBean().remove(label_noprof_procuratore);
                if(!label_noprof_delegato.equalsIgnoreCase(label_pers_legale))
                    dataForm.getSezioniCompilabiliBean().remove(label_noprof_delegato);
                
                if(dataForm.getSezioniCompilabiliBean().get(label_pers_legale) == null){
                    SezioneCompilabileBean sezComp = delegate.getHrefDefinitions(label_pers_legale, dataForm, null, null, "FORCED");
                    if(sezComp.getDescrizione() != null && !sezComp.getDescrizione().equalsIgnoreCase("")){
                        int liv = livelloMaxCampiMultipli(sezComp);
                        String html = HtmlRenderer.costruisciStringaHtml(sezComp, false, "SectionText", null, null, null, null, null, null, liv, dataForm);
                        sezComp.setHtml(html);
                        html = HtmlRenderer.costruisciStringaHtml(sezComp, true, "SectionText", "TableRow0", "96%", "0", "6", null, "25", liv, dataForm);
                        sezComp.setHtmlEditable(html);
                        settaCompletamento(sezComp);
                        dataForm.getSezioniCompilabiliBean().put(label_pers_legale, sezComp);
                    }
                }
                if(titolo_pers_legale != null && desc_pers_legale != null && !titolo_pers_legale.equalsIgnoreCase("") && !desc_pers_legale.equalsIgnoreCase("")){
                    DichiarazioniStaticheBean dicStat = new DichiarazioniStaticheBean();
                    dicStat.setDescrizione(desc_pers_legale);
                    dicStat.setTitolo(titolo_pers_legale);
                    dicStat.setIntervento("FORCED");
                    dataForm.getDichiarazioniStatiche().add(dicStat);
                    
                }
            }
            else if(dataForm.getAnagrafica().getDichiarante().getAgiscePerContoDi().equalsIgnoreCase("2") &&
                    dataForm.getAnagrafica().getAttivita().getInQualitaDi().equalsIgnoreCase("1")){
                
                if(!label_imp_legale.equalsIgnoreCase(label_pers_procuratore))
                    dataForm.getSezioniCompilabiliBean().remove(label_imp_legale);
                if(!label_imp_procuratore.equalsIgnoreCase(label_pers_procuratore))
                    dataForm.getSezioniCompilabiliBean().remove(label_imp_procuratore);
                if(!label_imp_delegato.equalsIgnoreCase(label_pers_procuratore))
                    dataForm.getSezioniCompilabiliBean().remove(label_imp_delegato);
                if(!label_pers_legale.equalsIgnoreCase(label_pers_procuratore))
                    dataForm.getSezioniCompilabiliBean().remove(label_pers_legale);
                if(!label_pers_delegato.equalsIgnoreCase(label_pers_procuratore))
                    dataForm.getSezioniCompilabiliBean().remove(label_pers_delegato);
                if(!label_noprof_legale.equalsIgnoreCase(label_pers_procuratore))
                    dataForm.getSezioniCompilabiliBean().remove(label_noprof_legale);
                if(!label_noprof_procuratore.equalsIgnoreCase(label_pers_procuratore))
                    dataForm.getSezioniCompilabiliBean().remove(label_noprof_procuratore);
                if(!label_noprof_delegato.equalsIgnoreCase(label_pers_procuratore))
                    dataForm.getSezioniCompilabiliBean().remove(label_noprof_delegato);
                
                if(dataForm.getSezioniCompilabiliBean().get(label_pers_procuratore) == null){
                    SezioneCompilabileBean sezComp = delegate.getHrefDefinitions(label_pers_procuratore, dataForm, null, null, "FORCED");
                    if(sezComp.getDescrizione() != null && !sezComp.getDescrizione().equalsIgnoreCase("")){
                        int liv = livelloMaxCampiMultipli(sezComp);
                        String html = HtmlRenderer.costruisciStringaHtml(sezComp, false, "SectionText", null, null, null, null, null, null, liv, dataForm);
                        sezComp.setHtml(html);
                        html = HtmlRenderer.costruisciStringaHtml(sezComp, true, "SectionText", "TableRow0", "96%", "0", "6", null, "25", liv, dataForm);
                        sezComp.setHtmlEditable(html);
                        settaCompletamento(sezComp);
                        dataForm.getSezioniCompilabiliBean().put(label_pers_procuratore, sezComp);
                    }
                }
                if(titolo_pers_procuratore != null && desc_pers_procuratore != null && !titolo_pers_procuratore.equalsIgnoreCase("") && !desc_pers_procuratore.equalsIgnoreCase("")){
                    DichiarazioniStaticheBean dicStat = new DichiarazioniStaticheBean();
                    dicStat.setDescrizione(desc_pers_procuratore);
                    dicStat.setTitolo(titolo_pers_procuratore);
                    dicStat.setIntervento("FORCED");
                    dataForm.getDichiarazioniStatiche().add(dicStat);
                    
                }
            }
            else if(dataForm.getAnagrafica().getDichiarante().getAgiscePerContoDi().equalsIgnoreCase("2") &&
                    dataForm.getAnagrafica().getAttivita().getInQualitaDi().equalsIgnoreCase("2")){
                
                if(!label_imp_legale.equalsIgnoreCase(label_pers_delegato))
                    dataForm.getSezioniCompilabiliBean().remove(label_imp_legale);
                if(!label_imp_procuratore.equalsIgnoreCase(label_pers_delegato))
                    dataForm.getSezioniCompilabiliBean().remove(label_imp_procuratore);
                if(!label_imp_delegato.equalsIgnoreCase(label_pers_delegato))
                    dataForm.getSezioniCompilabiliBean().remove(label_imp_delegato);
                if(!label_pers_legale.equalsIgnoreCase(label_pers_delegato))
                    dataForm.getSezioniCompilabiliBean().remove(label_pers_legale);
                if(!label_pers_procuratore.equalsIgnoreCase(label_pers_delegato))
                    dataForm.getSezioniCompilabiliBean().remove(label_pers_procuratore);
                if(!label_noprof_legale.equalsIgnoreCase(label_pers_delegato))
                    dataForm.getSezioniCompilabiliBean().remove(label_noprof_legale);
                if(!label_noprof_procuratore.equalsIgnoreCase(label_pers_delegato))
                    dataForm.getSezioniCompilabiliBean().remove(label_noprof_procuratore);
                if(!label_noprof_delegato.equalsIgnoreCase(label_pers_delegato))
                    dataForm.getSezioniCompilabiliBean().remove(label_noprof_delegato);
                
                if(dataForm.getSezioniCompilabiliBean().get(label_pers_delegato) == null){
                    SezioneCompilabileBean sezComp = delegate.getHrefDefinitions(label_pers_delegato, dataForm, null, null, "FORCED");
                    if(sezComp.getDescrizione() != null && !sezComp.getDescrizione().equalsIgnoreCase("")){
                        int liv = livelloMaxCampiMultipli(sezComp);
                        String html = HtmlRenderer.costruisciStringaHtml(sezComp, false, "SectionText", null, null, null, null, null, null, liv, dataForm);
                        sezComp.setHtml(html);
                        html = HtmlRenderer.costruisciStringaHtml(sezComp, true, "SectionText", "TableRow0", "96%", "0", "6", null, "25", liv, dataForm);
                        sezComp.setHtmlEditable(html);
                        settaCompletamento(sezComp);
                        dataForm.getSezioniCompilabiliBean().put(label_pers_delegato, sezComp);
                    }
                }
                if(titolo_pers_delegato != null && desc_pers_delegato != null && !titolo_pers_delegato.equalsIgnoreCase("") && !desc_pers_delegato.equalsIgnoreCase("")){
                    DichiarazioniStaticheBean dicStat = new DichiarazioniStaticheBean();
                    dicStat.setDescrizione(desc_pers_delegato);
                    dicStat.setTitolo(titolo_pers_delegato);
                    dicStat.setIntervento("FORCED");
                    dataForm.getDichiarazioniStatiche().add(dicStat);
                    
                }
            }
            
            
            
            else if(dataForm.getAnagrafica().getDichiarante().getAgiscePerContoDi().equalsIgnoreCase("3") &&
                    dataForm.getAnagrafica().getAttivita().getInQualitaDi().equalsIgnoreCase("0")){
                
                if(!label_imp_legale.equalsIgnoreCase(label_noprof_legale))
                    dataForm.getSezioniCompilabiliBean().remove(label_imp_legale);
                if(!label_imp_procuratore.equalsIgnoreCase(label_noprof_legale))
                    dataForm.getSezioniCompilabiliBean().remove(label_imp_procuratore);
                if(!label_imp_delegato.equalsIgnoreCase(label_noprof_legale))
                    dataForm.getSezioniCompilabiliBean().remove(label_imp_delegato);
                if(!label_pers_legale.equalsIgnoreCase(label_noprof_legale))
                    dataForm.getSezioniCompilabiliBean().remove(label_pers_legale);
                if(!label_pers_procuratore.equalsIgnoreCase(label_noprof_legale))
                    dataForm.getSezioniCompilabiliBean().remove(label_pers_procuratore);
                if(!label_pers_delegato.equalsIgnoreCase(label_noprof_legale))
                    dataForm.getSezioniCompilabiliBean().remove(label_pers_delegato);
                if(!label_noprof_procuratore.equalsIgnoreCase(label_noprof_legale))
                    dataForm.getSezioniCompilabiliBean().remove(label_noprof_procuratore);
                if(!label_noprof_delegato.equalsIgnoreCase(label_noprof_legale))
                    dataForm.getSezioniCompilabiliBean().remove(label_noprof_delegato);
                
                if(dataForm.getSezioniCompilabiliBean().get(label_noprof_legale) == null){
                    SezioneCompilabileBean sezComp = delegate.getHrefDefinitions(label_noprof_legale, dataForm, null, null, "FORCED");
                    if(sezComp.getDescrizione() != null && !sezComp.getDescrizione().equalsIgnoreCase("")){
                        int liv = livelloMaxCampiMultipli(sezComp);
                        String html = HtmlRenderer.costruisciStringaHtml(sezComp, false, "SectionText", null, null, null, null, null, null, liv, dataForm);
                        sezComp.setHtml(html);
                        html = HtmlRenderer.costruisciStringaHtml(sezComp, true, "SectionText", "TableRow0", "96%", "0", "6", null, "25", liv, dataForm);
                        sezComp.setHtmlEditable(html);
                        settaCompletamento(sezComp);
                        dataForm.getSezioniCompilabiliBean().put(label_noprof_legale, sezComp);
                    }
                }
                if(titolo_noprof_legale != null && desc_noprof_legale != null && !titolo_noprof_legale.equalsIgnoreCase("") && !desc_noprof_legale.equalsIgnoreCase("")){
                    DichiarazioniStaticheBean dicStat = new DichiarazioniStaticheBean();
                    dicStat.setDescrizione(desc_noprof_legale);
                    dicStat.setTitolo(titolo_noprof_legale);
                    dicStat.setIntervento("FORCED");
                    dataForm.getDichiarazioniStatiche().add(dicStat);
                    
                }
            }    
            else if(dataForm.getAnagrafica().getDichiarante().getAgiscePerContoDi().equalsIgnoreCase("3") &&
                    dataForm.getAnagrafica().getAttivita().getInQualitaDi().equalsIgnoreCase("1")){
                
                if(!label_imp_legale.equalsIgnoreCase(label_noprof_procuratore))
                    dataForm.getSezioniCompilabiliBean().remove(label_imp_legale);
                if(!label_imp_procuratore.equalsIgnoreCase(label_noprof_procuratore))
                    dataForm.getSezioniCompilabiliBean().remove(label_imp_procuratore);
                if(!label_imp_delegato.equalsIgnoreCase(label_noprof_procuratore))
                    dataForm.getSezioniCompilabiliBean().remove(label_imp_delegato);
                if(!label_pers_legale.equalsIgnoreCase(label_noprof_procuratore))
                    dataForm.getSezioniCompilabiliBean().remove(label_pers_legale);
                if(!label_pers_procuratore.equalsIgnoreCase(label_noprof_procuratore))
                    dataForm.getSezioniCompilabiliBean().remove(label_pers_procuratore);
                if(!label_pers_delegato.equalsIgnoreCase(label_noprof_procuratore))
                    dataForm.getSezioniCompilabiliBean().remove(label_pers_delegato);
                if(!label_noprof_legale.equalsIgnoreCase(label_noprof_procuratore))
                    dataForm.getSezioniCompilabiliBean().remove(label_noprof_legale);
                if(!label_noprof_delegato.equalsIgnoreCase(label_noprof_procuratore))
                    dataForm.getSezioniCompilabiliBean().remove(label_noprof_delegato);
                
                if(dataForm.getSezioniCompilabiliBean().get(label_noprof_procuratore) == null){
                    SezioneCompilabileBean sezComp = delegate.getHrefDefinitions(label_noprof_procuratore, dataForm, null, null, "FORCED");
                    if(sezComp.getDescrizione() != null && !sezComp.getDescrizione().equalsIgnoreCase("")){
                        int liv = livelloMaxCampiMultipli(sezComp);
                        String html = HtmlRenderer.costruisciStringaHtml(sezComp, false, "SectionText", null, null, null, null, null, null, liv, dataForm);
                        sezComp.setHtml(html);
                        html = HtmlRenderer.costruisciStringaHtml(sezComp, true, "SectionText", "TableRow0", "96%", "0", "6", null, "25", liv, dataForm);
                        sezComp.setHtmlEditable(html);
                        settaCompletamento(sezComp);
                        dataForm.getSezioniCompilabiliBean().put(label_noprof_procuratore, sezComp);
                    }
                }
                if(titolo_noprof_procuratore != null && desc_noprof_procuratore != null && !titolo_noprof_procuratore.equalsIgnoreCase("") && !desc_noprof_procuratore.equalsIgnoreCase("")){
                    DichiarazioniStaticheBean dicStat = new DichiarazioniStaticheBean();
                    dicStat.setDescrizione(desc_noprof_procuratore);
                    dicStat.setTitolo(titolo_noprof_procuratore);
                    dicStat.setIntervento("FORCED");
                    dataForm.getDichiarazioniStatiche().add(dicStat);
                    
                }
            }    
            else if(dataForm.getAnagrafica().getDichiarante().getAgiscePerContoDi().equalsIgnoreCase("3") &&
                    dataForm.getAnagrafica().getAttivita().getInQualitaDi().equalsIgnoreCase("2")){
                
                if(!label_imp_legale.equalsIgnoreCase(label_noprof_delegato))
                    dataForm.getSezioniCompilabiliBean().remove(label_imp_legale);
                if(!label_imp_procuratore.equalsIgnoreCase(label_noprof_delegato))
                    dataForm.getSezioniCompilabiliBean().remove(label_imp_procuratore);
                if(!label_imp_delegato.equalsIgnoreCase(label_noprof_delegato))
                    dataForm.getSezioniCompilabiliBean().remove(label_imp_delegato);
                if(!label_pers_legale.equalsIgnoreCase(label_noprof_delegato))
                    dataForm.getSezioniCompilabiliBean().remove(label_pers_legale);
                if(!label_pers_procuratore.equalsIgnoreCase(label_noprof_delegato))
                    dataForm.getSezioniCompilabiliBean().remove(label_pers_procuratore);
                if(!label_pers_delegato.equalsIgnoreCase(label_noprof_delegato))
                    dataForm.getSezioniCompilabiliBean().remove(label_pers_delegato);
                if(!label_noprof_legale.equalsIgnoreCase(label_noprof_delegato))
                    dataForm.getSezioniCompilabiliBean().remove(label_noprof_legale);
                if(!label_noprof_procuratore.equalsIgnoreCase(label_noprof_delegato))
                    dataForm.getSezioniCompilabiliBean().remove(label_noprof_procuratore);
                
                if(dataForm.getSezioniCompilabiliBean().get(label_noprof_delegato) == null){
                    SezioneCompilabileBean sezComp = delegate.getHrefDefinitions(label_noprof_delegato, dataForm, null, null, "FORCED");
                    if(sezComp.getDescrizione() != null && !sezComp.getDescrizione().equalsIgnoreCase("")){
                        int liv = livelloMaxCampiMultipli(sezComp);
                        String html = HtmlRenderer.costruisciStringaHtml(sezComp, false, "SectionText", null, null, null, null, null, null, liv, dataForm);
                        sezComp.setHtml(html);
                        html = HtmlRenderer.costruisciStringaHtml(sezComp, true, "SectionText", "TableRow0", "96%", "0", "6", null, "25", liv, dataForm);
                        sezComp.setHtmlEditable(html);
                        settaCompletamento(sezComp);
                        dataForm.getSezioniCompilabiliBean().put(label_noprof_delegato, sezComp);
                    }
                }
                if(titolo_noprof_delegato != null && desc_noprof_delegato != null && !titolo_noprof_delegato.equalsIgnoreCase("") && !desc_noprof_delegato.equalsIgnoreCase("")){
                    DichiarazioniStaticheBean dicStat = new DichiarazioniStaticheBean();
                    dicStat.setDescrizione(desc_noprof_delegato);
                    dicStat.setTitolo(titolo_noprof_delegato);
                    dicStat.setIntervento("FORCED");
                    dataForm.getDichiarazioniStatiche().add(dicStat);
                    
                }
            }    
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    private void settaCompletamento(SezioneCompilabileBean sezComp) {
        List campi = sezComp.getCampi();
        Iterator it = campi.iterator();
        while (it.hasNext()) {
            HrefCampiBean hrefCB = (HrefCampiBean) it.next();
            if (hrefCB.getControllo() != null
                && !hrefCB.getControllo().equalsIgnoreCase("")) {
                sezComp.setComplete(false);
                return;
            }
        }
        sezComp.setComplete(true);
    }
    
    private void setDescrizioniMotivazioniRappresentanza(ProcessData dataForm){
        if(dataForm.getAnagrafica().getAttivita().getCodiceMotivazioneRappresentanza() != null && 
                !dataForm.getAnagrafica().getAttivita().getCodiceMotivazioneRappresentanza().equalsIgnoreCase("")){
            List datiCombo = (List)session.getAttribute("comboImpresa");
            Iterator it = datiCombo.iterator();
            while(it.hasNext()){
                BaseBean bb = (BaseBean)it.next();
                if(bb.getCodice().equalsIgnoreCase(dataForm.getAnagrafica().getAttivita().getCodiceMotivazioneRappresentanza())){
                    dataForm.getAnagrafica().getAttivita().setDescrizioneMotivazioneRappresentanza(bb.getDescrizione());
                    break;
                }
            }
        }
        if(dataForm.getAnagrafica().getDatiPersonaDeleganteBean().getCodiceMotivazioneRappresentanza() != null && 
                !dataForm.getAnagrafica().getDatiPersonaDeleganteBean().getCodiceMotivazioneRappresentanza().equalsIgnoreCase("")){
            List datiCombo = (List)session.getAttribute("comboPrivato");
            Iterator it = datiCombo.iterator();
            while(it.hasNext()){
                BaseBean bb = (BaseBean)it.next();
                if(bb.getCodice().equalsIgnoreCase(dataForm.getAnagrafica().getDatiPersonaDeleganteBean().getCodiceMotivazioneRappresentanza())){
                    dataForm.getAnagrafica().getDatiPersonaDeleganteBean().setDescrizioneMotivazioneRappresentanza(bb.getDescrizione());
                    break;
                }
            }
        }
        if(dataForm.getAnagrafica().getDatiAziendaNoProfitDeleganteBean().getCodiceMotivazioneRappresentanza() != null && 
                !dataForm.getAnagrafica().getDatiAziendaNoProfitDeleganteBean().getCodiceMotivazioneRappresentanza().equalsIgnoreCase("")){
            List datiCombo = (List)session.getAttribute("comboAltroEnte");
            Iterator it = datiCombo.iterator();
            while(it.hasNext()){
                BaseBean bb = (BaseBean)it.next();
                if(bb.getCodice().equalsIgnoreCase(dataForm.getAnagrafica().getDatiAziendaNoProfitDeleganteBean().getCodiceMotivazioneRappresentanza())){
                    dataForm.getAnagrafica().getDatiAziendaNoProfitDeleganteBean().setDescrizioneMotivazioneRappresentanza(bb.getDescrizione());
                    break;
                }
            }
        }
        if(dataForm.getAnagrafica().getDatiProfessionistaBean().getCodiceMotivazioneRappresentanza() != null && 
                !dataForm.getAnagrafica().getDatiProfessionistaBean().getCodiceMotivazioneRappresentanza().equalsIgnoreCase("")){
            List datiCombo = (List)session.getAttribute("comboProfessionista");
            Iterator it = datiCombo.iterator();
            while(it.hasNext()){
                BaseBean bb = (BaseBean)it.next();
                if(bb.getCodice().equalsIgnoreCase(dataForm.getAnagrafica().getDatiProfessionistaBean().getCodiceMotivazioneRappresentanza())){
                    dataForm.getAnagrafica().getDatiProfessionistaBean().setDescrizioneMotivazioneRappresentanza(bb.getDescrizione());
                    break;
                }
            }
        }
        if(dataForm.getAnagrafica().getDatiProfessionistaBean().getCodiceAlbo() != null && 
                !dataForm.getAnagrafica().getDatiProfessionistaBean().getCodiceAlbo().equalsIgnoreCase("")){
            List datiCombo = (List)session.getAttribute("comboAlbo");
            Iterator it = datiCombo.iterator();
            while(it.hasNext()){
                BaseBean bb = (BaseBean)it.next();
                if(bb.getCodice().equalsIgnoreCase(dataForm.getAnagrafica().getDatiProfessionistaBean().getCodiceAlbo())){
                    dataForm.getAnagrafica().getDatiProfessionistaBean().setDescrizioneAlbo(bb.getDescrizione());
                    break;
                }
            }
        }
        if(dataForm.getAnagrafica().getDatiProfessionistaBean().getDelega().getCodiceCausaleDelega() != null && 
                !dataForm.getAnagrafica().getDatiProfessionistaBean().getDelega().getCodiceCausaleDelega().equalsIgnoreCase("")){
            List datiCombo = (List)session.getAttribute("comboCausaleDelega");
            Iterator it = datiCombo.iterator();
            while(it.hasNext()){
                BaseBean bb = (BaseBean)it.next();
                if(bb.getCodice().equalsIgnoreCase(dataForm.getAnagrafica().getDatiProfessionistaBean().getDelega().getCodiceCausaleDelega())){
                    dataForm.getAnagrafica().getDatiProfessionistaBean().getDelega().setDescrizioneCausaleDelega(bb.getDescrizione());
                    break;
                }
            }
        }
        if(dataForm.getAnagrafica().getDatiProfessionistaBean().getDelega().getCodiceTramiteDelega() != null && 
                !dataForm.getAnagrafica().getDatiProfessionistaBean().getDelega().getCodiceTramiteDelega().equalsIgnoreCase("")){
            List datiCombo = (List)session.getAttribute("comboTramiteDelega");
            Iterator it = datiCombo.iterator();
            while(it.hasNext()){
                BaseBean bb = (BaseBean)it.next();
                if(bb.getCodice().equalsIgnoreCase(dataForm.getAnagrafica().getDatiProfessionistaBean().getDelega().getCodiceTramiteDelega())){
                    dataForm.getAnagrafica().getDatiProfessionistaBean().getDelega().setDescrizioneTramitedelega(bb.getDescrizione());
                    break;
                }
            }
        }
    }

    private void settaCompletamentoAnagrafica(ProcessData dataForm) {
        dataForm.getDescrizioneCampiNonCompilati().add("Anagrafica non compilata in tutte le parti obbligatorie");
        if (dataForm.getAnagrafica().getDichiarante().getCognome() == null
            || dataForm.getAnagrafica().getDichiarante().getCognome().equalsIgnoreCase("")) {
            dataForm.getAnagrafica().setComplete(false);
            return;
        }
        if (dataForm.getAnagrafica().getDichiarante().getNome() == null
            || dataForm.getAnagrafica().getDichiarante().getNome().equalsIgnoreCase("")) {
            dataForm.getAnagrafica().setComplete(false);
            return;
        }
        if (dataForm.getAnagrafica().getDichiarante().getLuogoNascita() == null
            || dataForm.getAnagrafica().getDichiarante().getLuogoNascita().equalsIgnoreCase("")) {
            dataForm.getAnagrafica().setComplete(false);
            return;
        }
        if (dataForm.getAnagrafica().getDichiarante().getProvinciaNascita() == null
            || dataForm.getAnagrafica().getDichiarante().getProvinciaNascita().equalsIgnoreCase("")) {
            dataForm.getAnagrafica().setComplete(false);
            return;
        }
        if (dataForm.getAnagrafica().getDichiarante().getDataNascita() == null
            || dataForm.getAnagrafica().getDichiarante().getDataNascita().equalsIgnoreCase("")) {
            dataForm.getAnagrafica().setComplete(false);
            return;
        }
        if (dataForm.getAnagrafica().getDichiarante().getCodiceFiscale() == null
            || dataForm.getAnagrafica().getDichiarante().getCodiceFiscale().equalsIgnoreCase("")) {
            dataForm.getAnagrafica().setComplete(false);
            return;
        }
        if (dataForm.getAnagrafica().getDichiarante().getResidenza().getCitta() == null
            || dataForm.getAnagrafica().getDichiarante().getResidenza().getCitta().equalsIgnoreCase("")) {
            dataForm.getAnagrafica().setComplete(false);
            return;
        }
        if (dataForm.getAnagrafica().getDichiarante().getResidenza().getProvincia() == null
            || dataForm.getAnagrafica().getDichiarante().getResidenza().getProvincia().equalsIgnoreCase("")) {
            dataForm.getAnagrafica().setComplete(false);
            return;
        }
        if (dataForm.getAnagrafica().getDichiarante().getResidenza().getCap() == null
            || dataForm.getAnagrafica().getDichiarante().getResidenza().getCap().equalsIgnoreCase("")) {
            dataForm.getAnagrafica().setComplete(false);
            return;
        }
        if (dataForm.getAnagrafica().getDichiarante().getResidenza().getVia() == null
            || dataForm.getAnagrafica().getDichiarante().getResidenza().getVia().equalsIgnoreCase("")) {
            dataForm.getAnagrafica().setComplete(false);
            return;
        }
        /*
         * Modifica Ferretti 13/03/2007 (versione 1.2.3): tolto il campo civico (e la relativa obbligaatoriet�)
         */
        /*if (dataForm.getAnagrafica().getDichiarante().getResidenza().getNumero() == null
            || dataForm.getAnagrafica().getDichiarante().getResidenza().getNumero().equalsIgnoreCase("")) {
            dataForm.getAnagrafica().setComplete(false);
            return;
        }*/
        if (dataForm.getAnagrafica().getDichiarante().getAgiscePerContoDi() == null
            || dataForm.getAnagrafica().getDichiarante().getAgiscePerContoDi().equalsIgnoreCase("")) {
            dataForm.getAnagrafica().setComplete(false);
            return;
        }
        if (dataForm.getAnagrafica().getDichiarante().getAgiscePerContoDi() != null) {
            // Anagrafica impresa
            if (dataForm.getAnagrafica().getDichiarante().getAgiscePerContoDi().equalsIgnoreCase("0")) {
                if (dataForm.getAnagrafica().getAttivita().getDenominazione() == null
                    || dataForm.getAnagrafica().getAttivita().getDenominazione().equalsIgnoreCase("")) {
                    dataForm.getAnagrafica().setComplete(false);
                    return;
                }
                if (dataForm.getAnagrafica().getAttivita().getSede().getCitta() == null
                    || dataForm.getAnagrafica().getAttivita().getSede().getCitta().equalsIgnoreCase("")) {
                    dataForm.getAnagrafica().setComplete(false);
                    return;
                }
                if (dataForm.getAnagrafica().getAttivita().getSede().getProvincia() == null
                    || dataForm.getAnagrafica().getAttivita().getSede().getProvincia().equalsIgnoreCase("")) {
                    dataForm.getAnagrafica().setComplete(false);
                    return;
                }
                if (dataForm.getAnagrafica().getAttivita().getSede().getCap() == null
                    || dataForm.getAnagrafica().getAttivita().getSede().getCap().equalsIgnoreCase("")) {
                    dataForm.getAnagrafica().setComplete(false);
                    return;
                }
                if (dataForm.getAnagrafica().getAttivita().getSede().getVia() == null
                    || dataForm.getAnagrafica().getAttivita().getSede().getVia().equalsIgnoreCase("")) {
                    dataForm.getAnagrafica().setComplete(false);
                    return;
                }
                /*
                 * Modifica Ferretti 13/03/2007 (versione 1.2.3): tolto il campo civico (e la relativa obbligaatoriet�)
                 */
                /*if (dataForm.getAnagrafica().getAttivita().getSede().getNumero() == null
                    || dataForm.getAnagrafica().getAttivita().getSede().getNumero().equalsIgnoreCase("")) {
                    dataForm.getAnagrafica().setComplete(false);
                    return;
                }*/
                if (dataForm.getAnagrafica().getAttivita().getCodiceFiscale() == null
                    || dataForm.getAnagrafica().getAttivita().getCodiceFiscale().equalsIgnoreCase("")) {
                    dataForm.getAnagrafica().setComplete(false);
                    return;
                }
                if (dataForm.getAnagrafica().getAttivita().getPartitaIva() == null
                    || dataForm.getAnagrafica().getAttivita().getPartitaIva().equalsIgnoreCase("")) {
                    dataForm.getAnagrafica().setComplete(false);
                    return;
                }
                if(dataForm.getAnagrafica().getAttivita().getCodiceMotivazioneRappresentanza() != null 
                    && dataForm.getAnagrafica().getAttivita().getCodiceMotivazioneRappresentanza().equalsIgnoreCase("ALTRO")
                    && (dataForm.getAnagrafica().getAttivita().getAltraMotivazione() == null || dataForm.getAnagrafica().getAttivita().getAltraMotivazione().equalsIgnoreCase(""))){
                    dataForm.getAnagrafica().setComplete(false);
                    return;
                }
            }
            // Anagrafica persona fisica
            else if (dataForm.getAnagrafica().getDichiarante().getAgiscePerContoDi().equalsIgnoreCase("2")) {
                if (dataForm.getAnagrafica().getDatiPersonaDeleganteBean().getCognome() == null
                    || dataForm.getAnagrafica().getDatiPersonaDeleganteBean().getCognome().equalsIgnoreCase("")) {
                    dataForm.getAnagrafica().setComplete(false);
                    return;
                }
                if (dataForm.getAnagrafica().getDatiPersonaDeleganteBean().getNome() == null
                    || dataForm.getAnagrafica().getDatiPersonaDeleganteBean().getNome().equalsIgnoreCase("")) {
                    dataForm.getAnagrafica().setComplete(false);
                    return;
                }
                if (dataForm.getAnagrafica().getDatiPersonaDeleganteBean().getLuogoNascita() == null
                    || dataForm.getAnagrafica().getDatiPersonaDeleganteBean().getLuogoNascita().equalsIgnoreCase("")) {
                    dataForm.getAnagrafica().setComplete(false);
                    return;
                }
                if (dataForm.getAnagrafica().getDatiPersonaDeleganteBean().getProvinciaNascita() == null
                    || dataForm.getAnagrafica().getDatiPersonaDeleganteBean().getProvinciaNascita().equalsIgnoreCase("")) {
                    dataForm.getAnagrafica().setComplete(false);
                    return;
                }
                if (dataForm.getAnagrafica().getDatiPersonaDeleganteBean().getDataNascita() == null
                    || dataForm.getAnagrafica().getDatiPersonaDeleganteBean().getDataNascita().equalsIgnoreCase("")) {
                    dataForm.getAnagrafica().setComplete(false);
                    return;
                }
                if (dataForm.getAnagrafica().getDatiPersonaDeleganteBean().getCodiceFiscale() == null
                    || dataForm.getAnagrafica().getDatiPersonaDeleganteBean().getCodiceFiscale().equalsIgnoreCase("")) {
                    dataForm.getAnagrafica().setComplete(false);
                    return;
                }
                if (dataForm.getAnagrafica().getDatiPersonaDeleganteBean().getResidenza().getCitta() == null
                    || dataForm.getAnagrafica().getDatiPersonaDeleganteBean().getResidenza().getCitta().equalsIgnoreCase("")) {
                    dataForm.getAnagrafica().setComplete(false);
                    return;
                }
                if (dataForm.getAnagrafica().getDatiPersonaDeleganteBean().getResidenza().getProvincia() == null
                    || dataForm.getAnagrafica().getDatiPersonaDeleganteBean().getResidenza().getProvincia().equalsIgnoreCase("")) {
                    dataForm.getAnagrafica().setComplete(false);
                    return;
                }
                if (dataForm.getAnagrafica().getDatiPersonaDeleganteBean().getResidenza().getCap() == null
                    || dataForm.getAnagrafica().getDatiPersonaDeleganteBean().getResidenza().getCap().equalsIgnoreCase("")) {
                    dataForm.getAnagrafica().setComplete(false);
                    return;
                }
                if (dataForm.getAnagrafica().getDatiPersonaDeleganteBean().getResidenza().getVia() == null
                    || dataForm.getAnagrafica().getDatiPersonaDeleganteBean().getResidenza().getVia().equalsIgnoreCase("")) {
                    dataForm.getAnagrafica().setComplete(false);
                    return;
                }
                if(dataForm.getAnagrafica().getDatiPersonaDeleganteBean().getCodiceMotivazioneRappresentanza() != null 
                    && dataForm.getAnagrafica().getDatiPersonaDeleganteBean().getCodiceMotivazioneRappresentanza().equalsIgnoreCase("ALTRO")
                    && (dataForm.getAnagrafica().getDatiPersonaDeleganteBean().getAltraMotivazione() == null || dataForm.getAnagrafica().getDatiPersonaDeleganteBean().getAltraMotivazione().equalsIgnoreCase(""))){
                    dataForm.getAnagrafica().setComplete(false);
                    return;
                }
                /*
                 * Modifica Ferretti 13/03/2007 (versione 1.2.3): tolto il campo civico (e la relativa obbligaatoriet�)
                 */
                /*if (dataForm.getAnagrafica().getDatiPersonaDeleganteBean().getResidenza().getNumero() == null
                    || dataForm.getAnagrafica().getDatiPersonaDeleganteBean().getResidenza().getNumero().equalsIgnoreCase("")) {
                    dataForm.getAnagrafica().setComplete(false);
                    return;
                }*/
            }
            // Anagrafica impresa no profit
            else if (dataForm.getAnagrafica().getDichiarante().getAgiscePerContoDi().equalsIgnoreCase("3")) {
                if (dataForm.getAnagrafica().getDatiAziendaNoProfitDeleganteBean().getDenominazione() == null
                    || dataForm.getAnagrafica().getDatiAziendaNoProfitDeleganteBean().getDenominazione().equalsIgnoreCase("")) {
                    dataForm.getAnagrafica().setComplete(false);
                    return;
                }
                if (dataForm.getAnagrafica().getDatiAziendaNoProfitDeleganteBean().getSede().getCitta() == null
                    || dataForm.getAnagrafica().getDatiAziendaNoProfitDeleganteBean().getSede().getCitta().equalsIgnoreCase("")) {
                    dataForm.getAnagrafica().setComplete(false);
                    return;
                }
                if (dataForm.getAnagrafica().getDatiAziendaNoProfitDeleganteBean().getSede().getCap() == null
                    || dataForm.getAnagrafica().getDatiAziendaNoProfitDeleganteBean().getSede().getCap().equalsIgnoreCase("")) {
                    dataForm.getAnagrafica().setComplete(false);
                    return;
                }
                if (dataForm.getAnagrafica().getDatiAziendaNoProfitDeleganteBean().getSede().getProvincia() == null
                    || dataForm.getAnagrafica().getDatiAziendaNoProfitDeleganteBean().getSede().getProvincia().equalsIgnoreCase("")) {
                    dataForm.getAnagrafica().setComplete(false);
                    return;
                }
                if (dataForm.getAnagrafica().getDatiAziendaNoProfitDeleganteBean().getSede().getVia() == null
                    || dataForm.getAnagrafica().getDatiAziendaNoProfitDeleganteBean().getSede().getVia().equalsIgnoreCase("")) {
                    dataForm.getAnagrafica().setComplete(false);
                    return;
                }
                /*
                 * Modifica Ferretti 13/03/2007 (versione 1.2.3): tolto il campo civico (e la relativa obbligaatoriet�)
                 */
                /*if (dataForm.getAnagrafica().getDatiAziendaNoProfitDeleganteBean().getSede().getNumero() == null
                    || dataForm.getAnagrafica().getDatiAziendaNoProfitDeleganteBean().getSede().getNumero().equalsIgnoreCase("")) {
                    dataForm.getAnagrafica().setComplete(false);
                    return;
                }*/
                if (dataForm.getAnagrafica().getDatiAziendaNoProfitDeleganteBean().getSede().getCodFiscale() == null
                    || dataForm.getAnagrafica().getDatiAziendaNoProfitDeleganteBean().getSede().getCodFiscale().equalsIgnoreCase("")) {
                    dataForm.getAnagrafica().setComplete(false);
                    return;
                }
                if (dataForm.getAnagrafica().getDatiAziendaNoProfitDeleganteBean().getSede().getPartitaIva() == null
                    || dataForm.getAnagrafica().getDatiAziendaNoProfitDeleganteBean().getSede().getPartitaIva().equalsIgnoreCase("")) {
                    dataForm.getAnagrafica().setComplete(false);
                    return;
                }
                if(dataForm.getAnagrafica().getDatiAziendaNoProfitDeleganteBean().getCodiceMotivazioneRappresentanza() != null 
                    && dataForm.getAnagrafica().getDatiAziendaNoProfitDeleganteBean().getCodiceMotivazioneRappresentanza().equalsIgnoreCase("ALTRO")
                    && (dataForm.getAnagrafica().getDatiAziendaNoProfitDeleganteBean().getAltraMotivazione() == null || dataForm.getAnagrafica().getDatiAziendaNoProfitDeleganteBean().getAltraMotivazione().equalsIgnoreCase(""))){
                    dataForm.getAnagrafica().setComplete(false);
                    return;
                }
            }
            // Professionista con delega
            else if (dataForm.getAnagrafica().getDichiarante().getAgiscePerContoDi().equalsIgnoreCase("4")) {
                if(dataForm.getAnagrafica().getDatiProfessionistaBean().getCodiceMotivazioneRappresentanza() != null 
                    && dataForm.getAnagrafica().getDatiProfessionistaBean().getCodiceMotivazioneRappresentanza().equalsIgnoreCase("ALTRO")
                    && (dataForm.getAnagrafica().getDatiProfessionistaBean().getAltraMotivazione() == null || dataForm.getAnagrafica().getDatiProfessionistaBean().getAltraMotivazione().equalsIgnoreCase(""))){
                    dataForm.getAnagrafica().setComplete(false);
                    return;
                }
                if(dataForm.getAnagrafica().getDatiProfessionistaBean().getCodiceAlbo() != null 
                    && dataForm.getAnagrafica().getDatiProfessionistaBean().getCodiceAlbo().equalsIgnoreCase("ALTRO")
                    && (dataForm.getAnagrafica().getDatiProfessionistaBean().getAltroAlbo() == null || dataForm.getAnagrafica().getDatiProfessionistaBean().getAltroAlbo().equalsIgnoreCase(""))){
                    dataForm.getAnagrafica().setComplete(false);
                    return;
                }
                if (dataForm.getAnagrafica().getDatiProfessionistaBean().getNumeroIscrizioneAlbo() == null
                    || dataForm.getAnagrafica().getDatiProfessionistaBean().getNumeroIscrizioneAlbo().equalsIgnoreCase("")) {
                    dataForm.getAnagrafica().setComplete(false);
                    return;
                }
                /*if (dataForm.getAnagrafica().getDatiProfessionistaBean().getDataIscrizioneAlbo() == null
                    || dataForm.getAnagrafica().getDatiProfessionistaBean().getDataIscrizioneAlbo().equalsIgnoreCase("")) {
                    dataForm.getAnagrafica().setComplete(false);
                    return;
                }*/
                if (dataForm.getAnagrafica().getDatiProfessionistaBean().getProvinciaIscrizioneAlbo() == null
                    || dataForm.getAnagrafica().getDatiProfessionistaBean().getProvinciaIscrizioneAlbo().equalsIgnoreCase("")) {
                    dataForm.getAnagrafica().setComplete(false);
                    return;
                }
                if(dataForm.getAnagrafica().getDatiProfessionistaBean().getDelega().getCodiceCausaleDelega() != null 
                    && dataForm.getAnagrafica().getDatiProfessionistaBean().getDelega().getCodiceCausaleDelega().equalsIgnoreCase("ALTRO")
                    && (dataForm.getAnagrafica().getDatiProfessionistaBean().getDelega().getAltraCausaleDelega() == null || dataForm.getAnagrafica().getDatiProfessionistaBean().getDelega().getAltraCausaleDelega().equalsIgnoreCase(""))){
                    dataForm.getAnagrafica().setComplete(false);
                    return;
                }
                if(dataForm.getAnagrafica().getDatiProfessionistaBean().getDelega().getCodiceTramiteDelega() != null 
                    && dataForm.getAnagrafica().getDatiProfessionistaBean().getDelega().getCodiceTramiteDelega().equalsIgnoreCase("ALTRO")
                    && (dataForm.getAnagrafica().getDatiProfessionistaBean().getDelega().getAltroTramiteDelega() == null || dataForm.getAnagrafica().getDatiProfessionistaBean().getDelega().getAltroTramiteDelega().equalsIgnoreCase(""))){
                    dataForm.getAnagrafica().setComplete(false);
                    return;
                }
                if (dataForm.getAnagrafica().getDatiProfessionistaBean().getDelega().getDataSottoscrizioneDelega() == null
                    || dataForm.getAnagrafica().getDatiProfessionistaBean().getDelega().getDataSottoscrizioneDelega().equalsIgnoreCase("")) {
                    dataForm.getAnagrafica().setComplete(false);
                    return;
                }
                if (dataForm.getAnagrafica().getDatiProfessionistaBean().getDelega().getLuogoOriginaleDelega() == null
                    || dataForm.getAnagrafica().getDatiProfessionistaBean().getDelega().getLuogoOriginaleDelega().equalsIgnoreCase("")) {
                    dataForm.getAnagrafica().setComplete(false);
                    return;
                }
                if((dataForm.getAnagrafica().getDatiProfessionistaBean().getAgiscePerContoDi() == null) || (dataForm.getAnagrafica().getDatiProfessionistaBean().getAgiscePerContoDi().equalsIgnoreCase("")) ){
                    dataForm.getAnagrafica().setComplete(false);
                    return;
                }
                if(dataForm.getAnagrafica().getDatiProfessionistaBean().getAgiscePerContoDi() != null &&
                        dataForm.getAnagrafica().getDatiProfessionistaBean().getAgiscePerContoDi().equalsIgnoreCase("PF")){
                    if(dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaFisica().getNome() == null
                       || dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaFisica().getNome().equalsIgnoreCase("")){
                        dataForm.getAnagrafica().setComplete(false);
                        return;
                    }
                    if(dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaFisica().getCognome() == null
                        || dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaFisica().getCognome().equalsIgnoreCase("")){
                        dataForm.getAnagrafica().setComplete(false);
                        return;
                    }
                    if(dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaFisica().getLuogoNascita() == null
                        || dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaFisica().getLuogoNascita().equalsIgnoreCase("")){
                        dataForm.getAnagrafica().setComplete(false);
                        return;
                    }
                    if(dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaFisica().getProvinciaNascita() == null
                        || dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaFisica().getProvinciaNascita().equalsIgnoreCase("")){
                        dataForm.getAnagrafica().setComplete(false);
                        return;
                    }
                    if(dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaFisica().getDataNascita() == null
                        || dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaFisica().getDataNascita().equalsIgnoreCase("")){
                        dataForm.getAnagrafica().setComplete(false);
                        return;
                    }
                    if(dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaFisica().getCodiceFiscale() == null
                        || dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaFisica().getCodiceFiscale().equalsIgnoreCase("")){
                        dataForm.getAnagrafica().setComplete(false);
                        return;
                    }
                    if(dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaFisica().getResidenza().getCitta() == null
                        || dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaFisica().getResidenza().getCitta().equalsIgnoreCase("")){
                        dataForm.getAnagrafica().setComplete(false);
                        return;
                    }
                    if(dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaFisica().getResidenza().getProvincia() == null
                        || dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaFisica().getResidenza().getProvincia().equalsIgnoreCase("")){
                        dataForm.getAnagrafica().setComplete(false);
                        return;
                    }
                    if(dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaFisica().getResidenza().getCap() == null
                        || dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaFisica().getResidenza().getCap().equalsIgnoreCase("")){
                        dataForm.getAnagrafica().setComplete(false);
                        return;
                    }
                    if(dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaFisica().getResidenza().getVia() == null
                        || dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaFisica().getResidenza().getVia().equalsIgnoreCase("")){
                        dataForm.getAnagrafica().setComplete(false);
                        return;
                    }
                }
                else if(dataForm.getAnagrafica().getDatiProfessionistaBean().getAgiscePerContoDi() != null &&
                        dataForm.getAnagrafica().getDatiProfessionistaBean().getAgiscePerContoDi().equalsIgnoreCase("PG")){
                    if(dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaGiuridica().getDenominazione() == null
                        || dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaGiuridica().getDenominazione().equalsIgnoreCase("")){
                        dataForm.getAnagrafica().setComplete(false);
                        return;
                    }
                    if(dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaGiuridica().getSede().getCitta() == null
                        || dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaGiuridica().getSede().getCitta().equalsIgnoreCase("")){
                        dataForm.getAnagrafica().setComplete(false);
                        return;
                    }
                    if(dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaGiuridica().getSede().getProvincia() == null
                        || dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaGiuridica().getSede().getProvincia().equalsIgnoreCase("")){
                        dataForm.getAnagrafica().setComplete(false);
                        return;
                    }
                    if(dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaGiuridica().getSede().getCap() == null
                        || dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaGiuridica().getSede().getCap().equalsIgnoreCase("")){
                        dataForm.getAnagrafica().setComplete(false);
                        return;
                    }
                    if(dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaGiuridica().getSede().getVia() == null
                        || dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaGiuridica().getSede().getVia().equalsIgnoreCase("")){
                        dataForm.getAnagrafica().setComplete(false);
                        return;
                    }
                    if(dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaGiuridica().getCodiceFiscale() == null
                        || dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaGiuridica().getCodiceFiscale().equalsIgnoreCase("")){
                        dataForm.getAnagrafica().setComplete(false);
                        return;
                    }
                    if(dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaGiuridica().getRappresentanteLegale().getNome() == null
                        || dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaGiuridica().getRappresentanteLegale().getNome().equalsIgnoreCase("")){
                        dataForm.getAnagrafica().setComplete(false);
                        return;
                    }
                    if(dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaGiuridica().getRappresentanteLegale().getCognome() == null
                        || dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaGiuridica().getRappresentanteLegale().getCognome().equalsIgnoreCase("")){
                        dataForm.getAnagrafica().setComplete(false);
                        return;
                    }
                    if(dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaGiuridica().getRappresentanteLegale().getCodiceFiscale() == null
                        || dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaGiuridica().getRappresentanteLegale().getCodiceFiscale().equalsIgnoreCase("")){
                        dataForm.getAnagrafica().setComplete(false);
                        return;
                    }
                }
            }
        }
        if(dataForm.getDescrizioneCampiNonCompilati().size()>0)
            dataForm.getDescrizioneCampiNonCompilati().remove(dataForm.getDescrizioneCampiNonCompilati().size()-1);
        dataForm.getAnagrafica().setComplete(true);
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.people.IStep#logicalValidate(it.people.process.AbstractPplProcess,
     *      it.people.wrappers.IRequestWrapper, it.people.IValidationErrors)
     */
    public boolean logicalValidate(AbstractPplProcess process, IRequestWrapper request, IValidationErrors errors) throws ParserException {
        String param = request.getUnwrappedRequest().getParameter("propertyName");
        if (param != null) {
            if (param.equalsIgnoreCase("listaDichiarazioni.jsp")
                || param.equalsIgnoreCase("modelloUnico.jsp")
                || param.equalsIgnoreCase("anagraficaImpresa.jsp")
                || param.equalsIgnoreCase("renderHref.jsp")
                || param.equalsIgnoreCase("documenti.jsp")) {
                // salta la convalida dei dati
                return true;
            }
        }
        /* Da cancellare - messo per debug step pagamento */
        // else
        // return true;
        ProcessData dataForm = (ProcessData) process.getData();
        logger.debug("logicalValidate ModelloUnicoStep");

        HashMap hma = (HashMap) dataForm.getSezioniCompilabiliBean();
        Set lisKey = hma.keySet();
        Iterator itk = lisKey.iterator();
        dataForm.setModelloUnicoComplete(true);
        while (itk.hasNext()) {
            SezioneCompilabileBean sezComp = (SezioneCompilabileBean) hma.get(itk.next());
            if (!sezComp.isComplete()) {
                dataForm.setModelloUnicoComplete(false);
                break;
            }
        }

        if(!disabilitaControlloCompletamento(dataForm)){
            if (!dataForm.isModelloUnicoComplete() || !dataForm.getAnagrafica().isComplete()) {
                //errors.add("error.generic", "modello unico incompleto");
                Iterator it = dataForm.getDescrizioneCampiNonCompilati().iterator();
                while(it.hasNext()){
                    errors.add("error.generic", it.next());
                }
                
                Map hm = dataForm.getSezioniCompilabiliBean();
                Set key = hm.keySet();
                Iterator itKey = key.iterator();
                while(itKey.hasNext()){
                    String keySezComp = (String) itKey.next();
                    SezioneCompilabileBean sezComp = (SezioneCompilabileBean)hm.get(keySezComp);
                    if(!sezComp.isComplete()){
                        errors.add("error.generic", "La dichiarazione \""+sezComp.getDescrizione()+"\" non risulta compilata in tutte le parti obbligatorie");
                    }
                }
                
                errors.add("error.solo", "Selezionare COMPILA/MODIFICA e completare le sezioni che non appaiono in verde");
                errors.add("error.solo", "I campi obbligatori di ciascuna sezione sono quelli contrassegnato dall'asterisco (*)");
                session.setAttribute("muincompleto", "TRUE");
                //removeLastHistory(request);
                try {
                    service(process, request);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    logger.error(e);
                } catch (ServletException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    logger.error(e);
                }
                return true;
            }
        }
         
        ProcedimentoUnicoDAO delegate = new ProcedimentoUnicoDAO(db, language);

        // Inizio gestione procedimenti plurimi
        try {
            Output output = dataForm.getOutput();

            // TODO da decidere se tenerlo cos�
            // creo un'hashmap con coppie procedimento/sportello (cod+desc)
            Iterator iter = output.getSportelli().iterator();

            // Creo un Hashmap sportelli/set di procedimenti
            iter = output.getSportelli().iterator();
            HashMap hm2 = new HashMap();
            while (iter.hasNext()) {
                Sportello sport = (Sportello) iter.next();
                String codSportello = sport.getCodiceSportello();
                Iterator iter2 = sport.getProcedimenti().iterator();
                Set setProc = new TreeSet();
                while (iter2.hasNext()) {
                    Procedimento proc = (Procedimento) iter2.next();
                    setProc.add(proc.getCodiceProcedimento());
                }
                if (hm2.get(codSportello) == null) {
                    hm2.put(codSportello, setProc);
                } else {
                    TreeSet setTmp = (TreeSet) hm2.get(codSportello);
                    Iterator it = setProc.iterator();
                    while (it.hasNext()) {
                        setTmp.add((String) it.next());
                    }
                    hm2.remove(codSportello);
                    hm2.put(codSportello, setTmp);
                }
            }

            // Creao una lista di ProcessData per quanti sono i destinatari
            dataForm.setProcessDataPerDestinatario(new ArrayList());
            Set chiavi = hm2.keySet();
            Iterator itChiavi = chiavi.iterator();
            while (itChiavi.hasNext()) {
                boolean visualizzaBollo = false;
                ProcessData pd = new ProcessData();
                String chiave = (String) itChiavi.next();
                pd.setCodSportello(chiave);
                pd.setDescSportello(delegate.getDescrizioneSportello(chiave));
                pd.setDescrizioneSettore(dataForm.getDescrizioneSettore());
                pd.setComune(new ComuneBean());
                BeanUtils.copyProperties(pd.getComune(), dataForm.getComune());
                pd.getComune().setSportello(delegate.getSportello(chiave, dataForm.getComune().getCodEnte()));
                pd.setCredenzialiBase64(dataForm.getCredenzialiBase64());
                pd.setProfiloTitolareNew(dataForm.getProfiloTitolareNew());
                pd.setTipoAccreditamento(dataForm.getTipoAccreditamento());
                pd.setDescBookmark(dataForm.getDescBookmark());

                pd.setAnagrafica(dataForm.getAnagrafica());
                
                Iterator itTuttiIProcedimenti = dataForm.getProcedimenti().iterator();
                while (itTuttiIProcedimenti.hasNext()) {
                    ProcedimentoBean procBean = (ProcedimentoBean) itTuttiIProcedimenti.next();
                    ProcedimentoSempliceBean procSemplice = procBean.getProcedimento();
                    if (procSemplice.getCodSportello() != null
                        && procSemplice.getCodSportello().equalsIgnoreCase(chiave)) {
                        ProcedimentoBean proc = new ProcedimentoBean();
                        if (procSemplice.getFlg_bollo() == null
                            || procSemplice.getFlg_bollo().equalsIgnoreCase("0")
                            || procSemplice.getFlg_bollo().equalsIgnoreCase("S")
                            || procSemplice.getFlg_bollo().equalsIgnoreCase("Y"))
                            visualizzaBollo = true;
                        proc.setProcedimento(procSemplice);
                        proc.setAllegati(procBean.getAllegati());
                        proc.setNormative(procBean.getNormative());
                        pd.getProcedimenti().add(proc);

                        // Inserisco gli interventiSelezionati relativi a
                        // ciascun procedimento
                        Set interventiSelezionati = dataForm.getInterventiSelezionati();
                        Iterator itInterventiSel = interventiSelezionati.iterator();
                        while (itInterventiSel.hasNext()) {
                            InterventoBean interventoSelezionato = (InterventoBean) itInterventiSel.next();
                            List listaInterventiPerProc = procBean.getProcedimento().getInterventi();
                            Iterator itInterventiPerProc = listaInterventiPerProc.iterator();
                            while (itInterventiPerProc.hasNext()) {
                                InterventoBean interventoPerProc = (InterventoBean) itInterventiPerProc.next();
                                if (interventoSelezionato.getCodice().equalsIgnoreCase(interventoPerProc.getCodice())) {
                                    pd.getInterventiSelezionati().add(interventoPerProc);

                                    // Inserisco le sezioniCompilabili relative
                                    // a ciascun procedimento
                                    Map hmSezComp = dataForm.getSezioniCompilabiliBean();
                                    Set key = hmSezComp.keySet();
                                    Iterator itHmKey = key.iterator();
                                    while (itHmKey.hasNext()) {
                                        String keySezComp = (String) itHmKey.next();
                                        /*
                                         * Modifica Ferretti 23/05/2007 (1.3.3)
                                         * Risolto bug che non visualizzava alcune dichiarazioni dinamiche nella pagina di firma.
                                         * Aggiunta la condizione: || sezComp.getIntervento().indexOf(interventoPerProc.getCodice()) != -1
                                         * per fare un controllo incrociato fra i codici degli interventi
                                         */
                                        SezioneCompilabileBean sezComp = (SezioneCompilabileBean) hmSezComp.get(keySezComp);
                                        if (interventoPerProc.getCodice().indexOf(sezComp.getIntervento()) != -1
                                                || sezComp.getIntervento().indexOf(interventoPerProc.getCodice()) != -1
                                                || sezComp.getIntervento().equalsIgnoreCase("FORCED")) {
                                            pd.getSezioniCompilabiliBean().put(keySezComp, sezComp);
                                        }
                                    }

                                    // Inserisco le dichiarazioneStatiche
                                    // relative a ciascun procedimento
                                    List dicStatiche = dataForm.getDichiarazioniStatiche();
                                    Iterator itDicStatiche = dicStatiche.iterator();
                                    while (itDicStatiche.hasNext()) {
                                        DichiarazioniStaticheBean dicStatBean = (DichiarazioniStaticheBean) itDicStatiche.next();
                                        if (dicStatBean.getIntervento().equalsIgnoreCase(interventoPerProc.getCodice()) || dicStatBean.getIntervento().equalsIgnoreCase("FORCED")) {
                                            pd.getDichiarazioniStatiche().add(dicStatBean);
                                        }
                                    }

                                    break;
                                }
                            }
                        }
                    }
                }
                pd.setIdentificatorediProtocollo(dataForm.getIdentificatorediProtocollo());
                pd.setIdentificatorePeople(dataForm.getIdentificatorePeople());
                pd.setIdentificatoreUnivoco(dataForm.getIdentificatoreUnivoco());
                pd.setRichiedente(dataForm.getRichiedente());
                pd.setTitolare(dataForm.getTitolare());
                pd.setVisualizzaBollo(visualizzaBollo);

                // Definisco il tipo di procedimento
                List procedimenti = pd.getProcedimenti();
                int tipoProcedura = delegate.getTipoProcedura(procedimenti);
                if (tipoProcedura == ProcessData.SEMPLICE) {
                    pd.setTipoProcedura(ProcessData.SEMPLICE);
                } else if (tipoProcedura == ProcessData.AUTOCERTIFICABILE) {
                    pd.setTipoProcedura(ProcessData.AUTOCERTIFICABILE);
                } else {
                    pd.setTipoProcedura(ProcessData.DIA);
                }

                dataForm.getProcessDataPerDestinatario().add(pd);
                // resettaListaProcessData(dataForm.getProcessDataPerDestinatario());

                /*
                 * A questo punto, poich� ho a disposizione tutti i dati dello
                 * "spacchettamento" del modello unico, modifico l'attivit� di
                 * riepilogo e firma per i modelli plurimi
                 */
                // Activity[] activities = process.getView().getActivities();
                // //Si tratta dell'ultima attivit�
                // Activity activity = activities[activities.length-1];
                // Step step = activity.getStep(0);
                // activity.setStepList(new ArrayList());
                // Iterator iterator =
                // dataForm.getProcessDataPerDestinatario().iterator();
                // while (iterator.hasNext()) {
                // //salta lo step di informazioni sul servizio
                // //che deve sempre essere presente
                // ProcessData data = (ProcessData)iterator.next();
                //                    
                // }
                //
                // int stepIndex = ((ProcessData)
                // process.getData()).getBookmarkStepIndex();
                // String[] stepOrder = activity.getStepOrder();
                // int lastStepId = Integer.parseInt(stepOrder[stepOrder.length
                // - 1]);
                // String newStepOrder = "";
                // for (; stepIndex <= lastStepId; stepIndex++) {
                // if (stepIndex == lastStepId) {
                // newStepOrder += stepIndex;
                // } else {
                // newStepOrder += stepIndex + ",";
                // }
                // }
                //
                // activity.setStepOrder(newStepOrder);
                // LayoutMenu menu = (LayoutMenu)
                // session.getAttribute("menuObject");
                // NavigationBar navBar = (NavigationBar)
                // session.getAttribute("navBarObject");
                // if (menu != null && navBar != null) {
                // menu.update(process.getView());
                // navBar.update(process.getView());
                // }
            }
            session.setAttribute("pdpd", dataForm.getProcessDataPerDestinatario());
            session.setAttribute("titolare", dataForm.getTitolare());
            session.setAttribute("richiedente", dataForm.getRichiedente());
        } catch (SQLException e) {
            logger.error(e);
            gestioneEccezioni(process, e);
        } catch (IllegalAccessException e) {
            logger.error(e);
            gestioneEccezioni(process, e);
        } catch (InvocationTargetException e) {
            logger.error(e);
            gestioneEccezioni(process, e);
        }

        // Fine gestione procedimenti plurimi

        // il modulo � completo, riabilito il menu di navigazione
        enableBottomNavigationBar(process, true);
        //enableBottomSaveBar(process, true);
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.people.IStep#loopBack(it.people.process.AbstractPplProcess,
     *      it.people.wrappers.IRequestWrapper, java.lang.String, int)
     */
    public void loopBack(AbstractPplProcess process, IRequestWrapper request, String propertyName, int index) throws IOException, ServletException {
        if (initialise(request)) {
            if(propertyName == null)
                propertyName = request.getParameter("pagina");
            ProcessData dataForm = (ProcessData) process.getData();
            logger.debug("loopBack ModelloUnicoStep");
            logger.debug("propertyName=" + propertyName);
            logStep(process);
            try {
                // apro il delegate ProcedimentoUnicoDAO
                ProcedimentoUnicoDAO delegate = new ProcedimentoUnicoDAO(db, language);
                // visualizza la lista degli href
                if (propertyName.equalsIgnoreCase("listaDichiarazioni.jsp")) {
                    enableBottomNavigationBar(process, false);
                    enableBottomSaveBar(process, false);
                    logger.debug(process.getView().getCurrentActivity().getCurrentStep().getJspPath());
                    // Aggiungo gli href controllati da anagrafica
                    gestioneHrefAnagrafica(dataForm, delegate);
                    creaSezioniCompilabili(process, request);
                    salvaDatiHref(request, dataForm);
                    settaCompletamentoAnagrafica(dataForm);
                    setDescrizioniMotivazioniRappresentanza(dataForm);
                    gestisciToolBarSinistra(process, false);
                    if(request.getParameter("associazioneCategoria") != null){
                        if(isCodiceAssociazioneCategoriaValido(dataForm, dataForm.getDatiAssociazioneDiCategoria().getCodiceControllo())){
                            if((dataForm.getDatiAssociazioneDiCategoria().getCodiceSportello() == null || dataForm.getDatiAssociazioneDiCategoria().getCodiceSportello().equalsIgnoreCase("") ||
                                    dataForm.getDatiAssociazioneDiCategoria().getDenominazione() == null || dataForm.getDatiAssociazioneDiCategoria().getDenominazione().equalsIgnoreCase("") ||
                                    dataForm.getDatiAssociazioneDiCategoria().getEmail() == null || dataForm.getDatiAssociazioneDiCategoria().getEmail().equalsIgnoreCase("")) &&
                                    !dataForm.getDatiAssociazioneDiCategoria().getCodiceControllo().equalsIgnoreCase("")){
                                ArrayList listaErrori = new ArrayList();
                                listaErrori.add("Codice di controllo valido: per continuare � necessario inserire anche gli altri dati.");
                                dataForm.setErroreSuHref(listaErrori);
                            }
                            else{
                                showJsp(process, propertyName, false);
                            }
                        }
                        else{
                            ArrayList listaErrori = new ArrayList();
                            listaErrori.add("Codice di controllo non valido: provare ad inserire il codice nuovamente.");
                            listaErrori.add("Se non si � a conoscenza del codice cancellare il valore immesso e premere il pulsante \"SALVE E TORNA ALLE DICHIARAZIONI\": gli altri dati inseriti saranno cos� ignorati");
                            dataForm.setErroreSuHref(listaErrori);
                        }
                    }
                    else{
                        showJsp(process, propertyName, false);
                    }
                } else if (propertyName.equalsIgnoreCase("anagrafica.jsp")) {
                    logger.debug(process.getView().getCurrentActivity().getCurrentStep().getJspPath());
                    // Recupero il contenuto delle combo delle motivazioni delle rappresentanze
                    if(session.getAttribute("comboImpresa") == null){
                        dataForm.getDatiTemporanei().setComboAnagrafica(CreateSinglePdfWhite.getContenutocomboRappresentanze(session));
                    }
                    showJsp(process, propertyName, false);
                } else if(propertyName.equalsIgnoreCase("associazioneDiCategoria.jsp")){
                    logger.debug(process.getView().getCurrentActivity().getCurrentStep().getJspPath());
                    showJsp(process, propertyName, false);
                } else if (propertyName.equalsIgnoreCase("modelloUnico.jsp")) {
                    enableBottomNavigationBar(process, true);
                    enableBottomSaveBar(process, true);
                    gestisciToolBarSinistra(process, true);
                    logger.debug(process.getView().getCurrentActivity().getCurrentStep().getJspPath());
                    // TODO put here your code
                    showJsp(process, propertyName, false);
                } else if (propertyName.equalsIgnoreCase("documenti.jsp")) {
                    String tipoDoc = request.getParameter("tipoDoc");
                    enableBottomNavigationBar(process, false);
                    enableBottomSaveBar(process, false);
                    gestisciToolBarSinistra(process, false);
                    if (tipoDoc.equalsIgnoreCase("allegati")) {
                        String codDoc = request.getParameter("codDoc");
                        request.setAttribute("listaDocumenti", delegate.getDocumentiAllegati(codDoc));
                        request.setAttribute("MU", "true");
                        showJsp(process, propertyName, false);
                    } else if (tipoDoc.equalsIgnoreCase("normative")) {
                        logger.debug(process.getView().getCurrentActivity().getCurrentStep().getJspPath());
                        String codRif = request.getParameter("codRif");
                        request.setAttribute("listaDocumenti", delegate.getDocumentiNormative(codRif));
                        request.setAttribute("MU", "true");
                        showJsp(process, propertyName, false);
                    }
                } else if (propertyName.equalsIgnoreCase("stampaPdf")) {
                    CreatePdf cPdf = new CreatePdf();
                    byte[] b = cPdf.stampaPdf(process, request);
                    session.setAttribute("pdfByteArray", b);

                } else if (propertyName.indexOf("renderHref.jsp") != 1) {
                    logger.debug(process.getView().getCurrentActivity().getCurrentStep().getJspPath());
                    String href = request.getParameter("href");
                    if(href == null){
                        href = request.getParameter("href");
                    }
                    if (request.getParameter("accedi") != null) {
                        salvaDatiHref(request, dataForm);
                        chiamaWebService(process, request, dataForm);
                    }
                    if (request.getParameter("aggiungi") != null) {
                        salvaDatiHref(request, dataForm);
                    }
                    if (request.getParameter("togli") != null) {
                        salvaDatiHref(request, dataForm, request.getParameter("togli"));
                    }
                    SezioneCompilabileBean dataBean = (SezioneCompilabileBean) dataForm.getSezioniCompilabiliBean().get(href);
                    session.setAttribute("sezioneBean", dataBean);
                    showJsp(process, propertyName, false);
                }
            } catch (SQLException e) {
                logger.error(e);
                gestioneEccezioni(process, e);
            } catch (Exception e) {
                logger.error(e);
                gestioneEccezioni(process, e);
            }
        }
    }
    
/*    private HashMap getContenutocomboRappresentanze() throws IOException, ServletException{
        HashMap retVal = new HashMap();
        Properties p = new Properties();
        ServiceParameters params = (ServiceParameters) session.getAttribute("serviceParameters");
        String absPathToService = params.get("absPathToService");
        try{
            p.load(new FileInputStream(absPathToService
                                       + System.getProperty("file.separator")
                                       + "risorse"
                                       + System.getProperty("file.separator")
                                       + "messaggi.properties"));
        }
        catch(Exception e){
            p.load(new FileInputStream("D:\\eclipseworkspace\\people-2.x\\web\\WEB-INF\\classes\\it\\people\\fsl\\servizi\\concessioniedautorizzazioni\\servizicondivisi\\procedimentounico"
                    + System.getProperty("file.separator")
                    + "risorse"
                    + System.getProperty("file.separator")
                    + "messaggi.properties"));
        }
        
        boolean continua = true;
        int i=1;
        List listaCombo = new LinkedList();
        // Combo Impresa
        while(continua){
            String elementoCombo = p.getProperty("impresa_combo"+i);
            if(elementoCombo == null){
                continua = false;
            }
            else{
                BaseBean bb = new BaseBean();
                String[] elem = splitCodiceEDescrizioneCombo(elementoCombo);
                bb.setCodice(elem[0]);
                bb.setDescrizione(elem[1]);
                listaCombo.add(bb);
            }
            i++;
        }
        session.setAttribute("comboImpresa", listaCombo);
        retVal.put("comboImpresa", listaCombo);
        
        i=1;
        continua = true;
        List listaCombo2 = new LinkedList();
        // Combo Privato
        while(continua){
            String elementoCombo = p.getProperty("privato_combo"+i);
            if(elementoCombo == null){
                continua = false;
            }
            else{
                BaseBean bb = new BaseBean();
                String[] elem = splitCodiceEDescrizioneCombo(elementoCombo);
                bb.setCodice(elem[0]);
                bb.setDescrizione(elem[1]);
                listaCombo2.add(bb);
            }
            i++;
        }
        session.setAttribute("comboPrivato", listaCombo2);
        retVal.put("comboPrivato", listaCombo2);

        i=1;
        continua = true;
        List listaCombo3 = new LinkedList();
        // Combo AltroEnte
        while(continua){
            String elementoCombo = p.getProperty("altroente_combo"+i);
            if(elementoCombo == null){
                continua = false;
            }
            else{
                BaseBean bb = new BaseBean();
                String[] elem = splitCodiceEDescrizioneCombo(elementoCombo);
                bb.setCodice(elem[0]);
                bb.setDescrizione(elem[1]);
                listaCombo3.add(bb);
            }
            i++;
        }
        session.setAttribute("comboAltroEnte", listaCombo3);
        retVal.put("comboAltroEnte", listaCombo3);

        i=1;
        continua = true;
        List listaCombo4 = new LinkedList();
        // Combo AltroEnte
        while(continua){
            String elementoCombo = p.getProperty("professionista_combo"+i);
            if(elementoCombo == null){
                continua = false;
            }
            else{
                BaseBean bb = new BaseBean();
                String[] elem = splitCodiceEDescrizioneCombo(elementoCombo);
                bb.setCodice(elem[0]);
                bb.setDescrizione(elem[1]);
                listaCombo4.add(bb);
            }
            i++;
        }
        session.setAttribute("comboProfessionista", listaCombo4);
        retVal.put("comboProfessionista", listaCombo4);

        i=1;
        continua = true;
        List listaCombo5 = new LinkedList();
        // Combo AltroEnte
        while(continua){
            String elementoCombo = p.getProperty("albo_combo"+i);
            if(elementoCombo == null){
                continua = false;
            }
            else{
                BaseBean bb = new BaseBean();
                String[] elem = splitCodiceEDescrizioneCombo(elementoCombo);
                bb.setCodice(elem[0]);
                bb.setDescrizione(elem[1]);
                listaCombo5.add(bb);
            }
            i++;
        }
        session.setAttribute("comboAlbo", listaCombo5);
        retVal.put("comboAlbo", listaCombo5);

        i=1;
        continua = true;
        List listaCombo6 = new LinkedList();
        // Combo AltroEnte
        while(continua){
            String elementoCombo = p.getProperty("causaledelega_combo"+i);
            if(elementoCombo == null){
                continua = false;
            }
            else{
                BaseBean bb = new BaseBean();
                String[] elem = splitCodiceEDescrizioneCombo(elementoCombo);
                bb.setCodice(elem[0]);
                bb.setDescrizione(elem[1]);
                listaCombo6.add(bb);
            }
            i++;
        }
        session.setAttribute("comboCausaleDelega", listaCombo6);
        retVal.put("comboCausaleDelega", listaCombo6);

        i=1;
        continua = true;
        List listaCombo7 = new LinkedList();
        // Combo AltroEnte
        while(continua){
            String elementoCombo = p.getProperty("tramitedelega_combo"+i);
            if(elementoCombo == null){
                continua = false;
            }
            else{
                BaseBean bb = new BaseBean();
                String[] elem = splitCodiceEDescrizioneCombo(elementoCombo);
                bb.setCodice(elem[0]);
                bb.setDescrizione(elem[1]);
                listaCombo7.add(bb);
            }
            i++;
        }
        session.setAttribute("comboTramiteDelega", listaCombo7);
        retVal.put("comboTramiteDelega", listaCombo7);

        return retVal;

    }
    
    private String[] splitCodiceEDescrizioneCombo(String codice){
        String[] retVal = new String[2];
        retVal[0] = codice.indexOf("|") != -1 ? codice.substring(0, codice.indexOf("|")) : null;
        retVal[1] = (codice.indexOf("|") != -1 && codice.indexOf("|") < codice.length()) ? codice.substring(codice.indexOf("|")+1, codice.length()) : null;
        return retVal;
    }*/

    /**
     * Costruisce la lista delle sezioni compilabili
     * 
     * @param process
     * @param request
     */
    private void creaSezioniCompilabili(AbstractPplProcess process, IRequestWrapper request) {
        if (initialise(request)) {
            logger.debug("inside creaSezioniCompilabili");
            ProcessData dataForm = (ProcessData) process.getData();
            // apro il delegate ProcedimentoUnicoDAO
            boolean tieniAllegato = false;
            ProcedimentoBean procedimento = null;
            ProcedimentoSempliceBean procedimentoSempliceBean = null;
            // List allegati = null;
            String titoloHref = "";

            List list = (List) dataForm.getSezioniCompilabili();
            // se la lista delle sezioni compilabili � null o vuota
            // significa che questo metodo � stato chiamato per la prima
            // volta
            // pertanto creo le sezioni compilabili
            // if (list == null || list.isEmpty()) {
            logger.debug("Creo le sezioni compilabili");
            // DA QUI - inizio vaglio degli href come da analisi wego, poi
            // smentita da Vidmar
            // Iterator procedimentiIter =
            // dataForm.getProcedimenti().iterator();
            // HashMap hm = new HashMap();
            // while (procedimentiIter.hasNext()) {
            // procedimento = (ProcedimentoBean) procedimentiIter.next();
            // allegati = procedimento.getAllegati();
            // if (allegati == null) {
            // continue;
            // }
            //
            // Iterator allegatiIter = allegati.iterator();
            // while (allegatiIter.hasNext()) {
            // AllegatiBean allegato = (AllegatiBean) allegatiIter.next();
            // if (allegato.getFlagAutocertificazione().equalsIgnoreCase("0")) {
            // tieniAllegato = true;
            // } else {
            // procedimentoSempliceBean = procedimento.getProcedimento();
            // //il procedimento � autocertificato
            // if (procedimentoSempliceBean.getTipo() ==
            // ProcedimentoSempliceBean.PROCEDIMENTO_AUTOCERTIFICABILE) {
            // tieniAllegato = true;
            // } else {
            // tieniAllegato = false;
            // }
            // }
            // boolean salvaHref = true;
            // if (tieniAllegato) {
            // if (allegato.getFlagDic().equalsIgnoreCase("S")) {
            // // recupera titolo href
            // if (allegato.getHref() != null
            // && hm.get(allegato.getHref()) == null) {
            // titoloHref =
            // allegato.getTitolo()/*delegate.getTitoloHref(allegato.getHref())*/;
            // hm.put(allegato.getHref(), "1");
            // } else {
            // salvaHref = false;
            // }
            // } else {
            // tieniAllegato = false;
            // // C�� qualcosa che non va: secondo le
            // // specifiche qui ci dovrebbe essere un
            // // break ma in questo modo non fa vedere
            // // nessuna sez.compilabile
            // salvaHref = false;
            // //break;
            // }
            // } else { //tieniAllegato � false
            // break;
            // }
            //
            // if (salvaHref && titoloHref != null) {
            // SezioneCompilabileBean sezioneCompilabile = new
            // SezioneCompilabileBean();
            // sezioneCompilabile.setTitolo(titoloHref);
            // sezioneCompilabile.setHref(allegato.getHref());
            // //sezioneCompilabile.setLink(getLinkSottofunzione());
            //
            // // TODO Da migliorare (Massimo)
            // Map map = dataForm.getSezioniCompilabiliBean();
            // SezioneCompilabileBean sezComp = (SezioneCompilabileBean)
            // map.get(allegato.getHref());
            // sezioneCompilabile.setComplete(sezComp.isComplete());
            //
            // dataForm.getSezioniCompilabili().add(sezioneCompilabile);
            //
            // }
            // //reset
            // tieniAllegato = false;
            // }
            // }
            // A QUI - inizio vaglio degli href come da analisi wego, poi
            // smentita da Vidmar

            // DA QUI - nuova versione: faccio modificare tutti gli href che si
            // vedono nel procedimento unico
            Map mapSezComp = dataForm.getSezioniCompilabiliBean();
            Set chiavi = mapSezComp.keySet();
            dataForm.getSezioniCompilabili().clear();

            Iterator chiaviIter = chiavi.iterator();
            while (chiaviIter.hasNext()) {
                SezioneCompilabileBean sezioneCompilabileMax = (SezioneCompilabileBean) (mapSezComp.get(chiaviIter.next()));
                SezioneCompilabileBean sezioneCompilabile = new SezioneCompilabileBean();
                sezioneCompilabile.setTitolo(sezioneCompilabileMax.getDescrizione());
                sezioneCompilabile.setHref(sezioneCompilabileMax.getHref());
                sezioneCompilabile.setComplete(sezioneCompilabileMax.isComplete());

                // Controlo se ci sono campi obbligatori

                dataForm.getSezioniCompilabili().add(sezioneCompilabile);
            }
            // A QUI - nuova versione: faccio modificare tutti gli href che si
            // vedono nel procedimento unico
            // }
        }
    }

    // Salva i dati della sezione compilabile appena chiusa
    private void salvaDatiHref(IRequestWrapper request, ProcessData dataForm) {
        salvaDatiHref(request, dataForm, null);
    }

    private void salvaDatiHref(IRequestWrapper request, ProcessData dataForm, String togli) {
        String href = request.getParameter("href");
        if(href == null){
            href = request.getParameter("href");
        }
        dataForm.getDescrizioneCampiNonCompilati().clear();
        HashMap hmCheckRaggruppati = new HashMap();
        Map hm = dataForm.getSezioniCompilabiliBean();
        SezioneCompilabileBean sezComp = (SezioneCompilabileBean) hm.get(href);
        boolean complete = true;
        if (sezComp != null) {
            List listaHrefCampiBean = sezComp.getCampi();
            Iterator it = listaHrefCampiBean.iterator();

            while (it.hasNext()) {
                HrefCampiBean hrefCampi = (HrefCampiBean) it.next();
                String nomeCampo = hrefCampi.getNome();
                if (!hrefCampi.getTipo().equalsIgnoreCase("R")) {
                    String valore = request.getParameter(nomeCampo);
                    boolean continua = true;
                    // Gestione recupero valori campi multipli
                    if(hrefCampi.getNumCampo() == 1){
                        if (!hrefCampi.getTipo().equalsIgnoreCase("C")) {
                            if (request.getParameter(nomeCampo + "#1") != null) {
                                int i = 1;
                                while (continua) {
                                    if (request.getParameter(nomeCampo + "#" + i) != null) {
                                        valore += "|@|"
                                                  + request.getParameter(nomeCampo
                                                                         + "#" + i);
                                    }
                                    i++;
                                    if (request.getParameter(nomeCampo + "#" + i) == null) {
                                        continua = false;
                                    }
                                }
                            }
                        } else {
                            // E' un checkbox: non posso far affidamento sul valore
                            // di ritorno (� null se il check non � selezionato)
                            int i = 1;
                            int liv = livelloMaxCampiMultipli(sezComp);
                            if (i < liv) {
                                while (continua) {
                                    if (togli == null)
                                        valore += "|@|"
                                                  + request.getParameter(nomeCampo
                                                                         + "#" + i);
                                    i++;
                                    if (i >= liv) {
                                        continua = false;
                                    }
                                }
                            }
                        }
                    }
                    hrefCampi.setValoreUtente(valore);
                    if ((valore == null || valore.equalsIgnoreCase("") || !controlloValoreCampoMultiplo(valore))
                        && hrefCampi.getControllo() != null
                        && !hrefCampi.getControllo().equalsIgnoreCase("")) {
                        if(hrefCampi.getTipo().equalsIgnoreCase("C") && hrefCampi.getRaggruppamento_check() != null && !hrefCampi.getRaggruppamento_check().equalsIgnoreCase("")){
                            if(hmCheckRaggruppati.get(hrefCampi.getRaggruppamento_check()) == null){
                                hmCheckRaggruppati.put(hrefCampi.getRaggruppamento_check(), "N");
                            }
                            //complete = true;
                        }
                        else if(hrefCampi.getTipo().equalsIgnoreCase("I") && 
                                hrefCampi.getCampo_collegato() != null && !hrefCampi.getCampo_collegato().equalsIgnoreCase("") &&
                                hrefCampi.getVal_campo_collegato() != null && !hrefCampi.getVal_campo_collegato().equalsIgnoreCase("")){
                            String valCampoCollegato = request.getParameter(hrefCampi.getCampo_collegato());
                            if(valCampoCollegato != null && valCampoCollegato.equalsIgnoreCase(hrefCampi.getVal_campo_collegato())){
                                complete = false;
                            }
                        }
                        else{
                            complete = false;
                        }
                    }
                    else{
                        if(hrefCampi.getTipo().equalsIgnoreCase("C") && hrefCampi.getRaggruppamento_check() != null && !hrefCampi.getRaggruppamento_check().equalsIgnoreCase("")){
                            hmCheckRaggruppati.put(hrefCampi.getRaggruppamento_check(), "Y");
                        }
                    }
                } else {
                    if (hrefCampi.getNumCampo() == 0) {
                        String radioSelezionato = request.getParameter(nomeCampo);
                        if (radioSelezionato != null
                            && radioSelezionato.equalsIgnoreCase(hrefCampi.getValore()))
                            hrefCampi.setValoreUtente(radioSelezionato);
                        else
                            hrefCampi.setValoreUtente(null);
                        if (radioSelezionato == null
                            && hrefCampi.getControllo() != null
                            && !hrefCampi.getControllo().equalsIgnoreCase("")) {
                            complete = false;
                        }
                    } else {
                        // Gestione posticcia dei radiobutton multipli
                        boolean continua = true;
                        int i = 1;
                        int liv = livelloMaxCampiMultipli(sezComp);

                        String radioSelezionato = request.getParameter(nomeCampo);
                        if (radioSelezionato == null
                            || !radioSelezionato.equalsIgnoreCase(hrefCampi.getValore()))
                            radioSelezionato = null;

                        while (continua) {
                            if (togli == null) {
                                String radioSelTmp = request.getParameter(nomeCampo
                                                                          + "#"
                                                                          + i);
                                if (radioSelTmp != null
                                    && radioSelTmp.equalsIgnoreCase(hrefCampi.getValore()))
                                    radioSelezionato += "|@|"
                                                        + request.getParameter(nomeCampo
                                                                               + "#"
                                                                               + i);
                                else
                                    radioSelezionato += "|@|null";
                            }
                            i++;
                            if (i >= liv) {
                                continua = false;
                            }
                        }
                        hrefCampi.setValoreUtente(radioSelezionato);
                        if (radioSelezionato == null
                            && hrefCampi.getControllo() != null
                            && !hrefCampi.getControllo().equalsIgnoreCase("")) {
                            complete = false;
                        }
                    }
                }
            }
            int liv = livelloMaxCampiMultipli(sezComp);
            if (togli != null) {
                if (liv > 1) {
                    liv--;
                }
            } else if (request.getParameter("aggiungi") != null) {
                liv++;
            }
            sezComp.setHtml(HtmlRenderer.costruisciStringaHtml(sezComp, false, "SectionText", null, null, null, null, null, null, liv, dataForm));
            sezComp.setHtmlEditable(HtmlRenderer.costruisciStringaHtml(sezComp, true, "SectionText", "TableRow0", "96%", "0", "6", null, "25", liv, dataForm));
            sezComp.setComplete(complete);
            // Gestione check raggruppati
            if(hmCheckRaggruppati.size()>0){
                Set key = hmCheckRaggruppati.keySet();
                Iterator itKey = key.iterator();
                while(itKey.hasNext()){
                    String value = (String) hmCheckRaggruppati.get((String)itKey.next());
                    if(value != null && value.equalsIgnoreCase("N")){
                        complete = false;
                    }
                }
            }
            
            List lis = dataForm.getSezioniCompilabili();
            Iterator i = lis.iterator();
            while (i.hasNext()) {
                SezioneCompilabileBean sez = (SezioneCompilabileBean) i.next();
                if (sez.getHref().equalsIgnoreCase(href))
                    sez.setComplete(complete);
            }
        }
    }
    
    private boolean controlloValoreCampoMultiplo(String valore){
        List listaValoriMultipli = parseValoriCampiMultipli(valore);
        Iterator it = listaValoriMultipli.iterator();
        while(it.hasNext()){
            String nVal = (String)it.next();
            if(nVal.equalsIgnoreCase("")){
                return false;
            }
        }
        return true;
    }
    
    private static List parseValoriCampiMultipli(String valore) {
        List retVal = new ArrayList();
        if (valore != null && !valore.equalsIgnoreCase("")) {
            boolean continua = valore.indexOf("|@|") >= 0;
            if (!continua) {
                retVal.add(valore);
            }
            while (continua) {
                retVal.add(valore.substring(0, valore.indexOf("|@|")));
                valore = valore.substring(valore.indexOf("|@|") + 3);
                continua = valore.indexOf("|@|") >= 0;
                if (!continua) {
                    retVal.add(valore);
                }
            }
        }
        return retVal;
    }

    private void chiamaWebService(AbstractPplProcess process, IRequestWrapper request, ProcessData dataForm) {
        String href = request.getParameter("href");
        if(href == null){
            href = request.getParameter("href");
        }
        Map hm = dataForm.getSezioniCompilabiliBean();
        SezioneCompilabileBean sezComp = (SezioneCompilabileBean) hm.get(href);
        String urlWebService = "";
        String nomeWebService = "";
        String metodoWebService = "";
        if (sezComp != null) {
            List listaHrefCampiBean = sezComp.getCampi();
            Iterator it = listaHrefCampiBean.iterator();
            // Controllo i campi chiave
            PrecompilazioneBean precompilazioneBean = new PrecompilazioneBean();

            // String valoreCampoChiave = "";
            while (it.hasNext()) {
                HrefCampiBean hrefCampi = (HrefCampiBean) it.next();
                if (hrefCampi.getCampo_key() != null) {
                    CampoPrecompilazioneBean campoPrecBean = new CampoPrecompilazioneBean();
                    campoPrecBean.setCodice(hrefCampi.getCampo_dati());
                    campoPrecBean.getDescrizione().add(hrefCampi.getValoreUtente());
                    if (hrefCampi.getWeb_serv() != null
                        && !"".equalsIgnoreCase(hrefCampi.getWeb_serv()))
                        urlWebService = hrefCampi.getWeb_serv();
                    if (hrefCampi.getNome_xsd() != null
                        && !"".equalsIgnoreCase(hrefCampi.getNome_xsd())) {
                        String tmp = hrefCampi.getNome_xsd();
                        nomeWebService = tmp.substring(0, tmp.indexOf("|") > 0 ? tmp.indexOf("|") : tmp.length());
                        if (tmp.equalsIgnoreCase(nomeWebService))
                            metodoWebService = "process";
                        else
                            metodoWebService = tmp.substring(tmp.indexOf("|") + 1, tmp.length());
                    }
                    precompilazioneBean.getInput().add(campoPrecBean);
                }
            }
            if ("".equalsIgnoreCase(urlWebService)
                || "".equalsIgnoreCase(nomeWebService)) {
                gestioneEccezioni(process, new Exception("Non sono stati settati i parametri di connessone al web service"));
                dataForm.getErroreSuHref().add(new String("Non sono stati settati i parametri di connessone al web service"));
                return;
            }
            String richiestaWS = getXmlRixhiestaPrecompilazione(precompilazioneBean, dataForm);
            System.out.println("--------- INIZIO XML INVIATO PER PRECOMPILAZIONE ---------");
            System.out.println(richiestaWS);
            System.out.println("--------- FINE XML INVIATO PER PRECOMPILAZIONE ---------");
            // String
            // richiestaWS=marshallPrecompilazioneBean(precompilazioneBean);
            String messaggio = "";

            try {
                String sVisura = urlWebService;
                messaggio = process.callService(sVisura, richiestaWS);
            } catch (ServiceException e) {
                dataForm.getErroreSuHref().add(new String(e.toString()));
                e.printStackTrace();
            } catch (SendException e) {
                dataForm.getErroreSuHref().add(new String(e.toString()));
                e.printStackTrace();
            }
            
            System.out.println("--------- INIZIO XML RICEVUTO PER PRECOMPILAZIONE ---------");
            System.out.println(messaggio);
            System.out.println("--------- FINE XML RICEVUTO PER PRECOMPILAZIONE ---------");

/*messaggio = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
    + "<PrecompilazioneBean xmlns=\"http://gruppoinit.it/b1/ConcessioniEAutorizzazioni/precompilazione\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
    + "  <Input xmlns=\"\">"
    + "    <CampoPrecompilazioneBean>"
    + "      <Codice>radio1</Codice>"
    + "      <Descrizione xsi:nil=\"true\"/>"
    + "    </CampoPrecompilazioneBean>"
    + "    <CampoPrecompilazioneBean>"
    + "      <Codice>radio2</Codice>"
    + "      <Descrizione xsi:nil=\"true\"/>"
    + "    </CampoPrecompilazioneBean>"
    + "    <CampoPrecompilazioneBean>"
    + "      <Codice>non_radio</Codice>"
    + "      <Descrizione/>"
    + "    </CampoPrecompilazioneBean>"
    + "  </Input>"
    + "  <Output xmlns=\"\">"
    + "    <CampoPrecompilazioneBean>"
    + "      <Codice>radio1</Codice>"
    + "      <Descrizione>1</Descrizione>"
    + "      <Descrizione>2</Descrizione>"
    + "    </CampoPrecompilazioneBean>"
    + "    <CampoPrecompilazioneBean>"
    + "      <Codice>radio2</Codice>"
    + "      <Descrizione>1</Descrizione>"
    + "      <Descrizione>2</Descrizione>"
    + "    </CampoPrecompilazioneBean>"
    + "    <CampoPrecompilazioneBean>"
    + "      <Codice>non_radio</Codice>"
    + "      <Descrizione>Valore 1</Descrizione>"
    + "      <Descrizione>Valore 2</Descrizione>"
    + "    </CampoPrecompilazioneBean>"
    + "    <CampoPrecompilazioneBean>"
    + "      <Codice>non_radio2</Codice>"
    + "      <Descrizione>Testo 1</Descrizione>"
    + "      <Descrizione>Testo 2</Descrizione>"
    + "    </CampoPrecompilazioneBean>"
    + "    <CampoPrecompilazioneBean>"
    + "      <Codice>combo1</Codice>"
    + "      <Descrizione>Val combo 1</Descrizione>"
    + "      <Descrizione>Val combo 2</Descrizione>"
    + "    </CampoPrecompilazioneBean>"
    + "    <CampoPrecompilazioneBean>"
    + "      <Codice>check1</Codice>"
    + "      <Descrizione>Val check 1</Descrizione>"
    + "      <Descrizione>Val check 2</Descrizione>"
    + "    </CampoPrecompilazioneBean>"
    + "  </Output>"
    + "  <CodEnte xmlns=\"\">015146</CodEnte>"
    + "</PrecompilazioneBean>";*/

            // precompilazioneBean = unmarshallPrecompilazioneBean(messaggio);
            precompilazioneBean = leggiXml(messaggio, dataForm);

            if (precompilazioneBean != null) {
                List output = precompilazioneBean.getOutput();
                if (output.size() > 0
                    && ((CampoPrecompilazioneBean) output.get(0)).getDescrizione().size() == 1) {
                    it = listaHrefCampiBean.iterator();
                    while (it.hasNext()) {
                        HrefCampiBean hrefCampi = (HrefCampiBean) it.next();
                        Iterator itCampiWebService = output.iterator();
                        while (itCampiWebService.hasNext()) {
                            CampoPrecompilazioneBean campoPrecomp = (CampoPrecompilazioneBean) itCampiWebService.next();
                            if (campoPrecomp.getCodice().equalsIgnoreCase(hrefCampi.getCampo_dati())) {
                                List descrizioni = campoPrecomp.getDescrizione();
                                if (descrizioni.size() == 1) {
                                    hrefCampi.setValoreUtente((String) descrizioni.get(0));
                                }
                            }
                        }
                    }
                } else if (output.size() > 0
                           && ((CampoPrecompilazioneBean) output.get(0)).getDescrizione().size() > 1) {
                    List attivaRicerca = new LinkedList();
                    HashMap hmCampi = hrefCampiPerNome(listaHrefCampiBean);
                    Vector vec = null;
                    int numeroDiDesc = ((CampoPrecompilazioneBean) output.get(0)).getDescrizione().size();
                    // Intestazione
                    vec = new Vector();
                    for (int i = 0; i < output.size(); i++) {
                        CampoPrecompilazioneBean campoPrecomp = (CampoPrecompilazioneBean) output.get(i);
                        HrefCampiBean hrefCampi = (HrefCampiBean) hmCampi.get(campoPrecomp.getCodice());
                        if (hrefCampi != null && hrefCampi.getTipo().equalsIgnoreCase("I")) {
                            vec.add(hrefCampi.getDescrizione());
                        }
                    }
                    attivaRicerca.add(vec);
                    // Dati
                    for (int j = 0; j < numeroDiDesc; j++) {
                        vec = new Vector();
                        for (int i = 0; i < output.size(); i++) {
                            CampoPrecompilazioneBean campoPrecomp = (CampoPrecompilazioneBean) output.get(i);
                            HrefCampiBean hrefCampi = (HrefCampiBean) hmCampi.get(campoPrecomp.getCodice());
                            if (hrefCampi != null && hrefCampi.getTipo().equalsIgnoreCase("I")) {
                                vec.add(campoPrecomp.getDescrizione().get(j)
                                        + "|" + hrefCampi.getNome());
                            }
                        }
                        attivaRicerca.add(vec);
                    }
                    request.setAttribute("attivaRicerca", attivaRicerca);
                }
            }
            int liv = livelloMaxCampiMultipli(sezComp);
            sezComp.setHtml(HtmlRenderer.costruisciStringaHtml(sezComp, false, "SectionText", null, null, null, null, null, null, liv, dataForm));
            sezComp.setHtmlEditable(HtmlRenderer.costruisciStringaHtml(sezComp, true, "SectionText", "TableRow0", "96%", "0", "6", null, "25", liv, dataForm));
        }
    }

    private HashMap hrefCampiPerNome(List listaHrefCampiBean) {
        HashMap hm = new HashMap();
        Iterator it = listaHrefCampiBean.iterator();
        while (it.hasNext()) {
            HrefCampiBean hrefCampi = (HrefCampiBean) it.next();
            hm.put(hrefCampi.getCampo_dati(), hrefCampi);
        }
        return hm;
    }

    private String getXmlRixhiestaPrecompilazione(PrecompilazioneBean precBean, ProcessData dataForm) {
        PrecompilazioneBeanDocument precomompilazioneBeanDoc = PrecompilazioneBeanDocument.Factory.newInstance();
        PrecompilazioneBeanDocument.PrecompilazioneBean precompilazioneBean = precomompilazioneBeanDoc.addNewPrecompilazioneBean();
        PrecompilazioneBeanDocument.PrecompilazioneBean.Input input = precompilazioneBean.addNewInput();
        // dataForm.getComune().get
        precompilazioneBean.setCodEnte(dataForm.getComune().getCodIstat() != null ? dataForm.getComune().getCodIstat() : "054024");

        List inputDati = precBean.getInput();
        Iterator it = inputDati.iterator();
        
        HashMap hm = new HashMap();
        while (it.hasNext()) {
            CampoPrecompilazioneBean campoPB = (CampoPrecompilazioneBean) it.next();
            if(hm.get(campoPB.getCodice()) == null){
                it.gruppoinit.b1.concessioniEAutorizzazioni.precompilazione.CampoPrecompilazioneBean campoPre = input.addNewCampoPrecompilazioneBean();
                campoPre.setCodice(campoPB.getCodice());
                String descRadio = getValoreRadioButton(campoPB.getCodice(), inputDati);
                if(descRadio != null){
                    campoPre.addDescrizione(descRadio);
                }
                else{
                    Iterator it2 = campoPB.getDescrizione().iterator();
                    while (it2.hasNext()) {
                        campoPre.addDescrizione((String) it2.next());
                    }
                }
            }
            hm.put(campoPB.getCodice(), "Y");
        }
        String xml = XmlObjectWrapper.generateXml(precomompilazioneBeanDoc);
        return xml;
    }
    
    private String getValoreRadioButton(String codice, List inputDati){
        Iterator it = inputDati.iterator();
        String retVal= "";
        while(it.hasNext()){
            CampoPrecompilazioneBean campoPB = (CampoPrecompilazioneBean) it.next();
            if(campoPB.getCodice().equalsIgnoreCase(codice) && campoPB.getDescrizione() != null && campoPB.getDescrizione().get(0) != null && !campoPB.getDescrizione().get(0).equals("")){
                retVal = campoPB.getDescrizione().get(0).toString();
            }
        }
        return retVal;
    }

    private String marshallPrecompilazioneBean(PrecompilazioneBean obj) {
        StringWriter sw = new StringWriter();
        BeanWriter bw = new BeanWriter(sw);

        try {
            bw.getBindingConfiguration().setMapIDs(false);
            // bw.setWriteIDs(false);
            bw.getXMLIntrospector().getConfiguration().setAttributesForPrimitives(false);
            bw.setWriteEmptyElements(true);
            bw.enablePrettyPrint();
            bw.writeXmlDeclaration("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            bw.write(obj);
            bw.flush();
            bw.close();
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
        // logger.debug(sw.toString());
        return sw.toString();
    }

    private PrecompilazioneBean leggiXml(String sXml, ProcessData dataForm) {
        Document xml = null;
        List pratichePerUtente = new ArrayList();
        try {
            xml = DocumentHelper.parseText(sXml);
        } catch (DocumentException e) {
            dataForm.getErroreSuHref().add(new String(e.toString()));
            e.printStackTrace();
        }
        List nodoMultiplo = null;
        Node nodoSingolo = null;

        PrecompilazioneBean precBean = new PrecompilazioneBean();
        try {
            // nodoSingolo = xml.selectSingleNode("//Output");

            // nodoMultiplo =
            // nodoSingolo.selectNodes("//CampoPrecompilazioneBean");
            nodoMultiplo = xml.selectNodes("//Output/CampoPrecompilazioneBean");

            if (nodoMultiplo != null && nodoMultiplo.size() > 0) {
                Iterator itDati = nodoMultiplo.iterator();
                while (itDati.hasNext()) {
                    Node nodoCampoPrecBean = (Node) itDati.next();
                    CampoPrecompilazioneBean campoPrecBean = new CampoPrecompilazioneBean();
                    List output = precBean.getOutput();

                    String codice = nodoCampoPrecBean.valueOf("Codice");
                    campoPrecBean.setCodice(codice.equalsIgnoreCase("") ? "" : codice);
                    List descrizioni = new ArrayList();
                    // List nodoMultiploDesc =
                    // nodoCampoPrecBean.selectNodes("//Descrizione");
                    //                    
                    // if(nodoMultiploDesc != null &&
                    // nodoMultiploDesc.size()>0){
                    // Iterator itDatiDesc = nodoMultiploDesc.iterator();
                    // while(itDatiDesc.hasNext()){
                    // Node nodoDesc = (Node) itDatiDesc.next();
                    // descrizioni.add(nodoDesc.valueOf("Descrizione").equalsIgnoreCase("")
                    // ? "" : nodoDesc.valueOf("Descrizione"));
                    // }
                    // }
                    String sCount = nodoCampoPrecBean.valueOf("count(Descrizione)");
                    int count = 0;
                    try {
                        count = Integer.parseInt(sCount);
                    } catch (Exception e) {
                    }

                    for (int i = 1; i <= count; i++) {
                        String desc = nodoCampoPrecBean.valueOf("Descrizione["
                                                                + i + "]");
                        String descMod = desc.replace('\'',  '`');
                        descrizioni.add(descMod);
                    }

                    campoPrecBean.setDescrizione(descrizioni);
                    output.add(campoPrecBean);

                    precBean.setOutput(output);
                }
            }
        } catch (Exception e) {
            // An error occurred parsing or executing the XPath
            dataForm.getErroreSuHref().add(new String(e.toString()));
        }
        return precBean;
    }

    private PrecompilazioneBean unmarshallPrecompilazioneBean(String xml) {
        PrecompilazioneBean newData = null;
        try {
            BeanReader br = new BeanReader();
            // br.setMatchIDs(false);
            br.getBindingConfiguration().setMapIDs(false);
            br.registerBeanClass(PrecompilazioneBean.class);
            newData = (PrecompilazioneBean) br.parse(new StringReader(xml));
        } catch (IOException e) {
            log.error(e);
        } catch (IntrospectionException e) {
            log.error(e);
        } catch (SAXException e) {
            log.error(e);
        }
        return newData;
    }

    private int livelloMaxCampiMultipli(SezioneCompilabileBean sezComp) {
        Iterator it = sezComp.getCampi().iterator();
        int tmpMaxLivello = 0;
        while (it.hasNext()) {
            HrefCampiBean hrefCampi = (HrefCampiBean) it.next();
            if (!hrefCampi.getTipo().equalsIgnoreCase("R")
                && !hrefCampi.getTipo().equalsIgnoreCase("C")) {
                if (hrefCampi.getValoreUtente() != null
                    && hrefCampi.getValoreUtente().indexOf("|@|") > 0) {
                    int tmp = contaLivelli(hrefCampi.getValoreUtente());
                    if (tmp > tmpMaxLivello) {
                        tmpMaxLivello = tmp;
                    }
                }
            }
        }
        return tmpMaxLivello == 0 ? 1 : tmpMaxLivello;
    }

    private int contaLivelli(String valoreUtente) {
        int livelloTmp = 0;
        String valoreTmp = valoreUtente;
        if (valoreTmp != null && !valoreTmp.equalsIgnoreCase("")) {
            boolean continua = valoreTmp.indexOf("|@|") >= 0;
            if (!continua) {
                livelloTmp++;
            }
            while (continua) {
                livelloTmp++;
                valoreTmp = valoreTmp.substring(valoreTmp.indexOf("|@|") + 3);
                continua = valoreTmp.indexOf("|@|") >= 0;
                if (!continua) {
                    livelloTmp++;
                }
            }
        }
        return livelloTmp;
    }
    
    private void gestisciToolBarSinistra(AbstractPplProcess process, boolean abilita){
        if(abilita){
            process.getView().getActivityById("0").setState(it.people.ActivityState.ACTIVE);
            process.getView().getActivityById("1").setState(it.people.ActivityState.ACTIVE);
        }
        else{
            process.getView().getActivityById("0").setState(it.people.ActivityState.INACTIVE);
            process.getView().getActivityById("1").setState(it.people.ActivityState.INACTIVE);
        }
    }
}
