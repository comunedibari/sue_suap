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
package it.people.dbm.model;

import java.util.List;

import org.slf4j.Logger;

import it.people.dbm.dao.StampaSchedaDao;
import it.people.dbm.model.StampaSchede.AllegatoStampaSchede;
import it.people.dbm.model.StampaSchede.DocumentoStampaSchede;
import it.people.dbm.model.StampaSchede.InterventoStampaSchede;
import it.people.dbm.model.StampaSchede.NormativaStampaSchede;
import it.people.dbm.model.StampaSchede.OnereStampaSchede;
import it.people.dbm.model.StampaSchede.ProcedimentoStampaSchede;
import it.people.dbm.model.StampaSchede.RelazioneEnteStampaSchede;
import org.slf4j.LoggerFactory;
public class DocumentoRtf {

    private InterventoStampaSchede intervento;
    private ProcedimentoStampaSchede procedimento;
    private RelazioneEnteStampaSchede relazioneEnte;
    private List<DocumentoStampaSchede> documenti;
    private List<AllegatoStampaSchede> allegati;
    private List<NormativaStampaSchede> normative;
    private List<OnereStampaSchede> oneri;
    private List<InterventoStampaSchede> interventiCollegati;
    private static Logger log = LoggerFactory.getLogger(DocumentoRtf.class);

    public DocumentoRtf() {}

    public List<AllegatoStampaSchede> getAllegati() {
        return allegati;
    }

    public void setAllegati(List<AllegatoStampaSchede> allegati) {
        this.allegati = allegati;
    }

    public List<DocumentoStampaSchede> getDocumenti() {
        return documenti;
    }

    public void setDocumenti(List<DocumentoStampaSchede> documenti) {
        aggiornaListaHref(documenti);
        this.documenti = documenti;
    }

    public List<InterventoStampaSchede> getInterventiCollegati() {
        return interventiCollegati;
    }

    public void setInterventiCollegati(List<InterventoStampaSchede> interventiCollegati) {
        this.interventiCollegati = interventiCollegati;
    }

    public InterventoStampaSchede getIntervento() {
        return intervento;
    }

    public void setIntervento(InterventoStampaSchede intervento) {
        this.intervento = intervento;
    }

    public List<NormativaStampaSchede> getNormative() {
        return normative;
    }

    public void setNormative(List<NormativaStampaSchede> normative) {
        this.normative = normative;
    }

    public List<OnereStampaSchede> getOneri() {
        return oneri;
    }

    public void setOneri(List<OnereStampaSchede> oneri) {
        this.oneri = oneri;
    }

    public ProcedimentoStampaSchede getProcedimento() {
        return procedimento;
    }

    public void setProcedimento(ProcedimentoStampaSchede procedimento) {
        this.procedimento = procedimento;
    }

    public RelazioneEnteStampaSchede getRelazioneEnte() {
        return relazioneEnte;
    }

    public void setRelazioneEnte(RelazioneEnteStampaSchede relazioneEnte) {
        this.relazioneEnte = relazioneEnte;
    }

    private void aggiornaListaHref(List<DocumentoStampaSchede> documenti) {
        for (DocumentoStampaSchede doc : documenti) {
            if (!"".equals(doc.getHref())){
                try {
                    doc.setMatricehref(preparaMatriceHref(doc.getHref()));
                } catch (Exception ex) {
                    log.error("Errore nella preparazione della matrice di Href", ex);
                }
            }
        }
    }

    private HrefStampaSchede[][] preparaMatriceHref(String codiceHref) throws Exception {
        StampaSchedaDao dao = new StampaSchedaDao();
        int posMax = dao.selectMaxHrefPosizione(codiceHref);
//        int posMin = dao.selectMinHrefPosizione(codiceHref);
        int rigaMax = dao.selectMaxHrefRiga(codiceHref);
//        int rigaMin = dao.selectMinHrefRiga(codiceHref);

//        int numeroRighe = rigaMax - rigaMin + 1;
//        int numeroColonne = posMax - posMin + 1;

        HrefStampaSchede hrefMatrix[][] = new HrefStampaSchede[rigaMax][posMax];

        for (HrefStampaSchede href : dao.selectHrefCampi(codiceHref)) {
            hrefMatrix[href.getRiga()-1][href.getPosizione()-1] = href;
        }
        return hrefMatrix;
    }
    
}
