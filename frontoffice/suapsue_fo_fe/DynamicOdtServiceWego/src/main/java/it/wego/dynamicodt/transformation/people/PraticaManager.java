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
package it.wego.dynamicodt.transformation.people;

import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.HrefCampiBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.SezioneCompilabileBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.support.HtmlRenderer;
import it.wego.maid.domain.xmlbean.DocumentRootDocument;
import java.util.ArrayList;
import java.util.List;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xmlbeans.XmlOptions;

import it.wego.dynamicodt.transformation.util.ConfigUtil;

/**
 *
 * @author marcob
 */
public class PraticaManager {

    private static Log log = LogFactory.getLog(PraticaManager.class);
    private static final String separator = "|@|";
    private static final String emptyRow = "________";

    /**
     * Restituisce una lista di SezioneCompilabileBean a partire dall'oggetto DocumentRootDocument relativo all'xml people
     * @param docRoot
     * @return
     * @throws java.lang.Exception
     */
    public static List<SezioneCompilabileBean> getSezioniCompilabiliBeanList(DocumentRootDocument drd) throws Exception {


        List<SezioneCompilabileBean> scbs = new ArrayList<SezioneCompilabileBean>();

        log.debug("getSezioniCompilabiliBeanList...");

        DocumentRootDocument.DocumentRoot.DichiarazioniDinamiche dinamiche = drd.getDocumentRoot().getDichiarazioniDinamiche();

        if (dinamiche != null) {

            log.debug("Ho l'oggetto DichiarazioniDinamiche..");

            // booleano che serve a sapere se un SezioneCompilabileBean e' stato saltato perche'
            // di tipo anagrafica (cittadino o ditta)
            //boolean skipped = false;

            for (int i = 0; i < dinamiche.sizeOfDichiarazioneDinamicaArray(); i++) {


                SezioneCompilabileBean scb = new SezioneCompilabileBean();
                DocumentRootDocument.DocumentRoot.DichiarazioniDinamiche.DichiarazioneDinamica dinamica = dinamiche.getDichiarazioneDinamicaArray(i);



                scb.setHtml(dinamica.getHtml());
                scb.setPiedeHref(dinamica.getPiedeHref());
                scb.setTitolo(dinamica.getTitolo());
                scb.setDescrizione(dinamica.getDescrizione());
                scb.setHref(dinamica.getHref());
                scb.setCodice(dinamica.getCodiceDocumento());
                //scb.setRowCount(Integer.parseInt(dinamica.getRowCount()));
                //scb.setTdCount(2);
                int posizione = 0;
                int riga = 0;

                List<HrefCampiBean> campi = new ArrayList<HrefCampiBean>();


                for (int j = 0; j < dinamica.getCampi().sizeOfCampoArray(); j++) {


                    // Campo (se e' di tipo anagrafica (cittadino o ditta devo saltarlo!)
                    DocumentRootDocument.DocumentRoot.DichiarazioniDinamiche.DichiarazioneDinamica.Campi.Campo campoDinamica = dinamica.getCampi().getCampoArray(j);




                    HrefCampiBean hcb = new HrefCampiBean();

                    hcb.setNome(campoDinamica.getNome());

                    hcb.setContatore(campoDinamica.getContatore());
                    hcb.setControllo(campoDinamica.getControllo());
                    hcb.setDecimali(Integer.parseInt(campoDinamica.getDecimali()));
                    hcb.setDescrizione(campoDinamica.getDescrizione());
                    hcb.setEdit(campoDinamica.getEdit());
                    hcb.setLunghezza(Integer.parseInt(campoDinamica.getLunghezza()));
                    hcb.setNumCampo(Integer.parseInt(campoDinamica.getNumCampo()));
                    //hcb.setOpzioniCombo( campoDinamica.getOpzioniCombo().);
                    //log.debug("campoDinamica.getPosizione() " + campoDinamica.getPosizione());
                    int posizioneCur = Integer.parseInt(campoDinamica.getPosizione());
                    if (posizioneCur > posizione) {
                        posizione = posizioneCur;
                    }
                    hcb.setPosizione(posizioneCur);

                    int rigaCur = Integer.parseInt(campoDinamica.getRiga());
                    if (rigaCur > riga) {
                        riga = rigaCur;
                    }
                    hcb.setRiga(rigaCur);
                    hcb.setTipo(campoDinamica.getTipo());
                    hcb.setTp_controllo(campoDinamica.getTpControllo());
                    hcb.setValore(campoDinamica.getValore());
                    hcb.setValoreUtente(campoDinamica.getValoreUtente());
                    hcb.setCampo_collegato(campoDinamica.getCampoCollegato());
                    hcb.setCampo_dati(campoDinamica.getCampoDati());
                    hcb.setCampo_key(campoDinamica.getCampoKey());
                    hcb.setCampo_xml_mod(campoDinamica.getCampoXmlMod());
                    hcb.setRaggruppamento_check(campoDinamica.getRaggruppamentoCheck());
                    hcb.setVal_campo_collegato(campoDinamica.getCampoCollegato());
                    hcb.setWeb_serv(campoDinamica.getWebServ());

                    campi.add(hcb);

                }

                log.debug("setRowCount " + riga);
                log.debug("setTdCount " + posizione);

                scb.setRowCount(riga);
                scb.setTdCount(posizione);

                /*
                // se questa sezionecompbean e' da saltare passo alla prossima
                if (skipped) {
                continue;
                // Aggiungo i campi alla SezioneCompilabileBean corrente
                }
                 */
                scb.setCampi(campi);

                // Aggiungo il SezioneCompilabileBean
                scbs.add(scb);
            }

        }

        return scbs;


    }

    public static DocumentRootDocument extractXmlPeopleDocumentRootDocument(String xmlPeople, String encoding) throws Exception {

        // Utilizzando xmlbeans di apache estraggo gli href dall'xml
        DocumentRootDocument poDoc = null;
        XmlOptions xmlOptions = new XmlOptions();
        xmlOptions.setCharacterEncoding(encoding);
        poDoc = DocumentRootDocument.Factory.parse(xmlPeople, xmlOptions);

        return poDoc;

    }

    public static String cleanHtmlFragment(String xmlData, String encoding) throws Exception {

//    	System.out.println(xmlData);
    	
        //leggo l'xml di input ed ottengo le dichiarazioni dinamiche
        DocumentRootDocument drd = PraticaManager.extractXmlPeopleDocumentRootDocument(xmlData, encoding);

//        System.out.println(drd.toString());
        
        String moduloBianco = drd.getDocumentRoot().getModuloBianco().toString();

        //se non voglio il mudolu bianco non devo fare nulla.
        if (!"S".equals(moduloBianco)) {
            return drd.toString();
        }

        ConfigUtil cu = new ConfigUtil();
        boolean demo = false;
        String demoValue = "Rosso|@|Nero";

        try {
            demo = Boolean.parseBoolean(cu.getInstance().getProperty("htmlParser.demo"));
            demoValue = cu.getInstance().getProperty("htmlParser.demo.valoreUtente");
        } catch (Exception e) {
        }


        int iterateNum = 5;
        try {
            iterateNum = Integer.parseInt(cu.getInstance().getProperty("htmlParser.campiMultipli"));
        } catch (Exception e) {
        }



        DocumentRootDocument.DocumentRoot.DichiarazioniDinamiche dinamiche = drd.getDocumentRoot().getDichiarazioniDinamiche();

        //sbianco tutti i valore utente di tutte le dichiarazioni
        for (int i = 0; i < dinamiche.sizeOfDichiarazioneDinamicaArray(); i++) {
            DocumentRootDocument.DocumentRoot.DichiarazioniDinamiche.DichiarazioneDinamica dinamicaS = dinamiche.getDichiarazioneDinamicaArray(i);
            for (DocumentRootDocument.DocumentRoot.DichiarazioniDinamiche.DichiarazioneDinamica.Campi.Campo campo : dinamicaS.getCampi().getCampoArray()) {
                if ("I".equals(campo.getTipo()) && "1".equals(campo.getNumCampo())) {

                    String valoreUtente = "";

                    for (int ii = 1; ii <= iterateNum; ii++) {
                        valoreUtente += emptyRow;
                        if (ii < iterateNum) {
                            valoreUtente += separator;
                        }
                    }

System.out.println(valoreUtente);

                    campo.setValoreUtente(valoreUtente);
                } else if ("L".equals(campo.getTipo())) {
                    if ("1".equals(campo.getNumCampo())) {

                        String valoreUtente = "";

                        for (int ii = 1; ii <= iterateNum; ii++) {
                            if (!demo) {
                                valoreUtente += campo.getOpzioniCombo();
                            } else {
                                valoreUtente += demoValue;
                            }
                            if (ii < iterateNum) {
                                valoreUtente += "<br/>" + separator;
                            }
                        }

                        campo.setValoreUtente(valoreUtente);

                    } else {
                        String valoreUtente = campo.getOpzioniCombo();
                        if (!demo) {
                            campo.setValoreUtente(valoreUtente);
                        } else {
                            campo.setValoreUtente(demoValue);
                        }
                    }
                } else {
                    campo.setValoreUtente("&nbsp;");
                }
            }
        }

        //converto le dichiarazioni dinamiche in SezioneCompilabileBean richieste da HtmlRenderer
        List<SezioneCompilabileBean> scbs = PraticaManager.getSezioniCompilabiliBeanList(drd);

        for (int i = 0; i < scbs.size(); i++) {
            //ottengo il frammento di XML generato
            String htmlString = HtmlRenderer.costruisciStringaHtml((SezioneCompilabileBean) scbs.get(i), false, "SectionText", null, null, null, null, null, null);

            //e lo setto al posto di quello proveniente dall'input
            DocumentRootDocument.DocumentRoot.DichiarazioniDinamiche.DichiarazioneDinamica dinamica = dinamiche.getDichiarazioneDinamicaArray(i);
            dinamica.setHtml(htmlString);

            log.debug("frammento html: " + htmlString);

        }

        //log.debug("XML data modificato " + drd.toString());

        return drd.toString();


    }
}
