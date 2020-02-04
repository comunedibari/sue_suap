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
package it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.steps;

import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import javax.mail.internet.InternetAddress;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.sun.star.i18n.Calendar;

import it.gruppoinit.commons.Utilities;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.dao.ProcedimentoUnicoDAO;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.OnereBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.OneriBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ProcedimentoUnicoException;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.RiepilogoOneri;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.RiepilogoOneriPagati;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.SportelloBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.Bean2XML;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.UtilProperties;
import it.people.fsl.servizi.oggetticondivisi.profili.ProfiloPersonaFisica;
import it.people.process.AbstractPplProcess;
import it.people.util.MessageBundleHelper;
import it.people.util.ServiceParameters;
import it.people.util.payment.IStartPayment;
import it.people.util.payment.request.EnteDestinatario;
import it.people.util.payment.request.ImportoContabile;
import it.people.util.payment.request.PaymentRequestParameter;
import it.people.wrappers.IRequestWrapper;
import java.util.HashMap;
import java.util.Map;

public class RiepilogoImportiStep extends BaseStep implements IStartPayment {

    private String SERVICE_ID = "";
    private Long importo;
    private InternetAddress email;
    private String datiSpecifici;
    private Set oneriAnticipati;
    private SportelloBean sportello;
    private String numeroDocumento;
    private String encoding;
    private String annoDocumento;
    private ArrayList listaAlberoOneri;
    private boolean oneriCalcolatiPresent;
    private ProfiloPersonaFisica titolarePagamento;

    public PaymentRequestParameter getPaymentRequestParameter() {

        PaymentRequestParameter param = new PaymentRequestParameter();

        param.setTitolarePagamento(titolarePagamento);

        /* Indirizzo di e-mail a cui il sistema di pagamento far�
         * riferimento per l'invio dell'email di notifica di avvenuto pagamento.
         * Per utilizzare l'indirizzo dell'utente riportato dal sirac ritornare 
         * la stringa vuota.
         */
        try {
            param.getUserData().setEmailUtente(email);
        } catch (Exception ex) {
        }

        // Identificativo del servizio utilizzato dal MIP
        param.getServiceData().setIdServizio(SERVICE_ID);

        /* Dati specifici che saranno registrati nei flussi di 
         * rendicontazione e serviranno ad identificare il servizio.
         * Pu� essere usato un qualunque testo anche in formato XML, un
         * esempio di dati specifici potrebbe essere la serializzazione della
         * ProcessData del servizio stesso, pu� essere anche un sottinsieme 
         * pi� limitato di informazioni.
         */
        param.getServiceData().setDatiSpecifici(this.datiSpecifici);

        param.getServiceData().setAnnoDocumento(annoDocumento);
        param.getServiceData().setNumeroDocumento(numeroDocumento);


        // Importo da pagare da esprimere in centesimi di euro
        param.getPaymentData().setImporto(this.importo);

// PC - Oneri MIP - inizio         
        Map<String, Long> mappaImportiContabili = new HashMap<String, Long>();
        Iterator oneriAnticipatiIter = oneriAnticipati.iterator();
        while (oneriAnticipatiIter.hasNext()) {
            OneriBean oneriBean = (OneriBean) oneriAnticipatiIter.next();
            if (oneriBean.getIdentificativoContabile() != null && !oneriBean.getIdentificativoContabile().trim().equals("")) {
                double bufferImporto = Math.round(oneriBean.getImporto() * 100);
                int importoInt = new Double(bufferImporto).intValue();
                Long valore = new Long((long) importoInt);

                if (mappaImportiContabili.containsKey(oneriBean.getIdentificativoContabile())) {
                    mappaImportiContabili.put(oneriBean.getIdentificativoContabile(), mappaImportiContabili.get(oneriBean.getIdentificativoContabile()) + valore);
                } else {
                    mappaImportiContabili.put(oneriBean.getIdentificativoContabile(), valore);
                }
            }
        }

        ArrayList importiContabili = param.getAccountingData().getImportiContabili();
        for (Map.Entry entry : mappaImportiContabili.entrySet()) {
            String key = String.valueOf(entry.getKey());
            Long value = (Long) entry.getValue();
            importiContabili.add(new ImportoContabile(key, value));
        }

// PC - Oneri MIP - fine  

        if (!oneriAnticipati.isEmpty() && !StringUtils.isEmpty(sportello.getAe_codice_utente())) {

            RiepilogoOneriPagati riepilogoOneriPagati = new RiepilogoOneriPagati();
            double totaleOneri = (new Double(0.0D)).doubleValue();

            Iterator oneriAnticipatiIterator = oneriAnticipati.iterator();
            while (oneriAnticipatiIterator.hasNext()) {
                OneriBean oneriBean = (OneriBean) oneriAnticipatiIterator.next();

                totaleOneri += oneriBean.getImporto();
                double importoOnere = oneriBean.getImporto();
                double bufferImporto = Math.round(oneriBean.getImporto() * 100);
                int importoInt = new Double(bufferImporto).intValue();
                Long valore = new Long((long) importoInt);

                String codiceOnere = oneriBean.getCodice();
                String codDestinatario = oneriBean.getCodDestinatario();
                String desDestinatario = oneriBean.getDesDestinatario();
                String desOnere = oneriBean.getDescrizione();

                String aeCodiceUtente = oneriBean.getAe_codice_utente();
                String aeCodiceEnte = oneriBean.getAe_codice_ente();
                String aeTipoUfficio = oneriBean.getAe_tipo_ufficio();
                String aeCodiceUfficio = oneriBean.getAe_codice_ufficio();
// PC - Oneri MIP - inizio                 
                String identificativoContabileOnere = oneriBean.getIdentificativoContabile();
// PC - Oneri MIP - fine

                boolean riversamentoAutomaticoOnere = oneriBean.isRiversamentoAutomatico();

                if (this.oneriCalcolatiPresent && (oneriBean.getCampoFormula() != null && !oneriBean.getCampoFormula().equalsIgnoreCase(""))
                        && codDestinatario == null && desDestinatario == null) {

                    Iterator listaAlberoOneriIterator = this.listaAlberoOneri.iterator();
                    while (listaAlberoOneriIterator.hasNext()) {
                        OneriBean oneriBeanLista = (OneriBean) listaAlberoOneriIterator.next();
                        if (oneriBeanLista.getCodiceAntenato().equalsIgnoreCase(oneriBean.getCodiceAntenato())) {
                            codiceOnere = oneriBeanLista.getCodice();
                            codDestinatario = oneriBeanLista.getCodDestinatario();
                            desDestinatario = oneriBeanLista.getDesDestinatario();
                            desOnere = oneriBeanLista.getDescrizione();
                            aeCodiceUtente = oneriBeanLista.getAe_codice_utente();
                            aeCodiceEnte = oneriBeanLista.getAe_codice_ente();
                            aeTipoUfficio = oneriBeanLista.getAe_tipo_ufficio();
                            aeCodiceUfficio = oneriBeanLista.getAe_codice_ufficio();
                            riversamentoAutomaticoOnere = oneriBeanLista.isRiversamentoAutomatico();
// PC - Oneri MIP - inizio                             
                            identificativoContabileOnere = oneriBeanLista.getIdentificativoContabile();
// PC - Oneri MIP - fine  
                            break;
                        }
                    }

                }

                if (!riversamentoAutomaticoOnere) {
                    aeCodiceUtente = "";
                    aeCodiceEnte = "";
                    aeTipoUfficio = "";
                    aeCodiceUfficio = "";
                }

                ImportoContabile importoContabile = new ImportoContabile();
                importoContabile.setIdentificativo(codDestinatario);
                importoContabile.setValore(valore);

                EnteDestinatario enteDestinatario = new EnteDestinatario();
                //PayER vuole sia l'identificativo sia la descrizione dell'ente, ma 
                //non vi è un campo specifico per la descrizione, per cui è necessario 
                //passare all'interno del campo identificativo sia il codice sia la 
                //descrizione separate da "##|##"
                enteDestinatario.setIdentificativo(emptyStringToNull(codDestinatario) + "##|##" + emptyStringToNull(desDestinatario)
                        + "##|##" + emptyStringToNull(aeCodiceUtente)
                        + "##|##" + emptyStringToNull(aeCodiceEnte) + "##|##" + emptyStringToNull(aeTipoUfficio) + "##|##" + emptyStringToNull(aeCodiceUfficio)
                        + "##|##" + riversamentoAutomaticoOnere);
                enteDestinatario.setCausale(desOnere);
                enteDestinatario.setValore(valore);

                OnereBean onereBean = new OnereBean();
                onereBean.setCodice(codiceOnere);
                onereBean.setAeCodiceEnte(aeCodiceEnte);
                onereBean.setAeCodiceUfficio(aeCodiceUfficio);
                onereBean.setAeTipoUfficio(aeTipoUfficio);
                onereBean.setCodiceDestinatario(codDestinatario);
                onereBean.setDescrizione(desOnere);
                onereBean.setDescrizioneDestinatario(desDestinatario);
                onereBean.setImporto(importoOnere);
                onereBean.setRiversamentoAutomatico(riversamentoAutomaticoOnere);
// PC - Oneri MIP - inizio                 
                onereBean.setIdentificativoContabile(identificativoContabileOnere);
// PC - Oneri MIP - fine  
                riepilogoOneriPagati.addOneri(onereBean);

                param.getAccountingData().getImportiContabili().add(importoContabile);
                param.getAccountingData().getEntiDestinatari().add(enteDestinatario);
            }

            riepilogoOneriPagati.setTotale(totaleOneri);
            param.getServiceData().setDatiSpecifici(Bean2XML.marshallObject(riepilogoOneriPagati, this.encoding));

        }

        param.getPaymentManagerSpecificData().put("RiversamentoAutomatico", String.valueOf(sportello.isRiversamentoAutomatico()));
        param.getPaymentManagerSpecificData().put("CodiceUtente", sportello.getAe_codice_utente());
        param.getPaymentManagerSpecificData().put("CodiceEnte", sportello.getAe_codice_ente());
        param.getPaymentManagerSpecificData().put("TipoUfficio", sportello.getAe_tipo_ufficio());
        param.getPaymentManagerSpecificData().put("CodiceUfficio", sportello.getAe_codice_ufficio());
        param.getPaymentManagerSpecificData().put("TipologiaServizio", sportello.getAe_tipologia_servizio());

        return param;
    }

    public void service(AbstractPplProcess process, IRequestWrapper request) throws IOException, ServletException {
        try {
            if (initialise(process, request)) {
                logger.debug("RiepilogoImportiStep - service method");
                ProcessData dataForm = (ProcessData) process.getData();

                titolarePagamento = dataForm.getTitolarePagamento();
                this.oneriAnticipati = dataForm.getOneriAnticipati();

                this.listaAlberoOneri = dataForm.getListaAlberoOneri();

                this.oneriCalcolatiPresent = dataForm.isOneriCalcolatiPresent();

                this.numeroDocumento = process.getIdentificatoreProcedimento();
                java.util.Calendar calendar = java.util.Calendar.getInstance(process.getContext().getLocale());
                calendar.setTime(process.getCreationTime());
                this.annoDocumento = String.valueOf(calendar.get(java.util.Calendar.YEAR));

                this.encoding = request.getUnwrappedRequest().getCharacterEncoding();

//		        Set chiaviSettore = dataForm.getListaSportelli().keySet();
//		        boolean trovato=false;
//		        Iterator it = chiaviSettore.iterator();
//		        while (it.hasNext() && !trovato){
//					String chiaveSettore = (String) it.next();
//					sportello = (SportelloBean) dataForm.getListaSportelli().get(chiaveSettore);
//					if (sportello.getIdx() == dataForm.getDatiTemporanei().getIndiceSportello()) {
//						trovato=true;
//					}
//				}

                Set chiaviSettore = dataForm.getListaSportelli().keySet();
                boolean trovato = sportello != null;
                Iterator chiaviSettoreIterator = chiaviSettore.iterator();
                while (chiaviSettoreIterator.hasNext() && !trovato) {
                    String chiaveSettore = (String) chiaviSettoreIterator.next();
                    if (sportello == null) {
                        sportello = (SportelloBean) dataForm.getListaSportelli().get(chiaveSettore);
                        trovato = true;
                    }
                }


                if (trovato && entePayERAttivo(sportello) && !oneriValidi()) {

                    String messaggioErrore = MessageBundleHelper.message("error.pagamentoDatiOneri", null, process.getProcessName(), process.getCommune().getKey(),
                            process.getContext().getLocale());

                    process.addServiceError(messaggioErrore);

                    return;
                }
// PC - Oneri MIP - inizio                 
                if (checkCausaliContabili()) {
                    String messaggioErrore = MessageBundleHelper.message("error.pagamentoDatiOneri", null, process.getProcessName(), process.getCommune().getKey(),
                            process.getContext().getLocale());

                    process.addServiceError(messaggioErrore);

                    return;
                }
// PC - Oneri MIP - fine 

                resetError(dataForm);

                RiepilogoOneri riepilogoOneri = dataForm.getRiepilogoOneri();
                double totaleOneri = riepilogoOneri.getTotale();
                String service_id = "";
//	            ServiceParameters params = (ServiceParameters) session.getAttribute("serviceParameters");
//	            String absPathToService = params.get("absPathToService");
//	            String resourcePath = absPathToService+System.getProperty("file.separator")+"risorse"+System.getProperty("file.separator");
//	            Properties props[] = UtilProperties.getProperties(resourcePath,"parametri", process.getCommune().getOid());
//	            service_id = UtilProperties.getPropertyKey(props[0], props[1], props[2], "AC.idserviziopag"+Utilities.NVL(dataForm.getIdBookmark(),""));
//	            if(service_id==null){
//	            	service_id = UtilProperties.getPropertyKey(props[0], props[1], props[2], "AC.idserviziopag");
//	            }

                ProcedimentoUnicoDAO delegate = new ProcedimentoUnicoDAO(db, language);
                service_id = delegate.getParametroConfigurazione(process.getCommune().getOid(), "AC.idserviziopag" + Utilities.NVL(dataForm.getIdBookmark(), ""));
                if (!Utilities.isset(service_id)) {
                    service_id = delegate.getParametroConfigurazione(process.getCommune().getOid(), "AC.idserviziopag");
                }

                dataForm.getDatiTemporanei().setServiceIdPagamenti(service_id);


                SERVICE_ID = service_id;
                if (SERVICE_ID == null || SERVICE_ID.equalsIgnoreCase("")) {
                    SERVICE_ID = "PTEST";
                    dataForm.getDatiTemporanei().setServiceIdPagamenti(SERVICE_ID);
                    logger.warn("SERVICE_ID non settato");
//					System.out.println("SERVICE_ID non settato");
                }

                totaleOneri = Math.round(totaleOneri * 100);
                int tempOneri = new Double(totaleOneri).intValue();
                // PER TEST - DA CANCELLARE
                this.importo = new Long((long) tempOneri);
                InternetAddress ia = new InternetAddress();
                try {
                    String mailNotifica = getCampoDinamicoAnagrafica(dataForm.getAnagrafica().getListaCampi(), "ANAG_EMAIL_DICHIARANTE");
                    // this.email = new InternetAddress("mirko.calandrini@gruppoinit.it"/*dataForm.getAnagrafica().getDichiarante().getResidenza().getEmail()*/);
                    //this.email = new InternetAddress(mailNotifica);
                    this.email = new InternetAddress(getEMailTitolare(process, mailNotifica));
                } catch (Exception ex) {
                }

//				RiepilogoImporti oRiepilogoImporti = new RiepilogoImporti();
//				// oRiepilogoImporti.setImportoBollo((long)totaleOneri);
//				oRiepilogoImporti.setImportoTotale((long) totaleOneri);
//				request.setAttribute("riepilogoimporti", oRiepilogoImporti);
                // request.setAttribute("urlparmas", urlparmas);
            } else {
                throw new ProcedimentoUnicoException("Sessione scaduta");
            }
            logger.debug("RiepilogoImportiStep - service method END");
        } catch (Exception e) {
            gestioneEccezioni(process, 5, e);
        }



    }

    private String CreateDatiSpecifici() {
        return "DatiSpecificiDellaPratica";
    }

    private String getEMailTitolare(AbstractPplProcess process, String mailNotifica) {

        String result = mailNotifica;

        ProcessData dataForm = (ProcessData) process.getData();
        if (dataForm.getProfiloTitolare() != null) {

            if (dataForm.getProfiloTitolare().getProfiloTitolarePF() != null) {
                if (!StringUtils.isEmpty(dataForm.getProfiloTitolare().getProfiloTitolarePF().getDomicilioElettronico())) {
                    result = dataForm.getProfiloTitolare().getProfiloTitolarePF().getDomicilioElettronico();
                }
            } else if (dataForm.getProfiloTitolare().getProfiloTitolarePG() != null) {
                if (!StringUtils.isEmpty(dataForm.getProfiloTitolare().getProfiloTitolarePG().getDomicilioElettronico())) {
                    result = dataForm.getProfiloTitolare().getProfiloTitolarePG().getCodiceFiscale();
                } else if (dataForm.getProfiloTitolare().getProfiloTitolarePG().getRappresentanteLegale() != null) {
                    if (!StringUtils.isEmpty(dataForm.getProfiloTitolare().getProfiloTitolarePG().getRappresentanteLegale().getDomicilioElettronico())) {
                        result = dataForm.getProfiloTitolare().getProfiloTitolarePG().getRappresentanteLegale().getDomicilioElettronico();
                    }
                }
            }
        }

        return result;

    }

    private String emptyStringToNull(String value) {
        return (value != null && value.equalsIgnoreCase("")) ? "null" : value;
    }

    private boolean entePayERAttivo(SportelloBean sportello) {

        return sportello.getAe_codice_utente() != null && sportello.getAe_codice_utente().length() > 0 && sportello.getAe_codice_ente() != null && sportello.getAe_codice_ente().length() > 0 && sportello.getAe_tipologia_servizio() != null && sportello.getAe_tipologia_servizio().length() > 0;

    }

    private boolean oneriValidi() {

        boolean result = true;

        if (!oneriAnticipati.isEmpty() && !StringUtils.isEmpty(sportello.getAe_codice_utente())) {
            Iterator oneriAnticipatiIterator = oneriAnticipati.iterator();
            while (oneriAnticipatiIterator.hasNext()) {
                OneriBean oneriBean = (OneriBean) oneriAnticipatiIterator.next();

                String codDestinatario = oneriBean.getCodDestinatario();
                String desDestinatario = oneriBean.getDesDestinatario();

                if (this.oneriCalcolatiPresent && (oneriBean.getCampoFormula() != null && !oneriBean.getCampoFormula().equalsIgnoreCase(""))
                        && codDestinatario == null && desDestinatario == null) {

                    Iterator listaAlberoOneriIterator = this.listaAlberoOneri.iterator();
                    while (listaAlberoOneriIterator.hasNext()) {
                        OneriBean oneriBeanLista = (OneriBean) listaAlberoOneriIterator.next();
                        if (oneriBeanLista.getCodiceAntenato().equalsIgnoreCase(oneriBean.getCodiceAntenato())) {
                            codDestinatario = oneriBeanLista.getCodDestinatario();
                            desDestinatario = oneriBeanLista.getDesDestinatario();
                            break;
                        }
                    }

                }

                if (codDestinatario == null || desDestinatario == null || (codDestinatario != null && codDestinatario.trim().equalsIgnoreCase(""))
                        || (desDestinatario != null && desDestinatario.trim().equalsIgnoreCase(""))) {
                    result = false;
                    break;
                }

            }
        }

        return result;

    }
// PC - Oneri MIP - inizio 

    private boolean checkCausaliContabili() {
        boolean flgPresenzaCausali = false;
        boolean flgCausaliVuote = false;
        if (!oneriAnticipati.isEmpty()) {
            Iterator oneriAnticipatiIterator = oneriAnticipati.iterator();
            while (oneriAnticipatiIterator.hasNext()) {
                OneriBean oneriBean = (OneriBean) oneriAnticipatiIterator.next();
                if (oneriBean.getIdentificativoContabile() != null && !oneriBean.getIdentificativoContabile().trim().equals("")) {
                    flgPresenzaCausali = true;
                } else {
                    flgCausaliVuote = true;
                }
            }
        }
        return !(flgPresenzaCausali ^ flgCausaliVuote);
    }
// PC - Oneri MIP - fine 
}
