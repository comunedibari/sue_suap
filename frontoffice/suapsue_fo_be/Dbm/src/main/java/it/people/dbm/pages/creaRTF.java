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
package it.people.dbm.pages;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Table;
import com.lowagie.text.rtf.RtfWriter2;
import com.lowagie.text.rtf.table.RtfCell;

import it.people.dbm.dao.StampaSchedaDao;
import it.people.dbm.model.DocumentoRtf;
import it.people.dbm.model.HrefStampaSchede;
import it.people.dbm.model.StampaSchede.AllegatoStampaSchede;
import it.people.dbm.model.StampaSchede.DocumentoStampaSchede;
import it.people.dbm.model.StampaSchede.InterventoStampaSchede;
import it.people.dbm.model.StampaSchede.NormativaStampaSchede;
import it.people.dbm.model.StampaSchede.OnereStampaSchede;
import it.people.dbm.model.StampaSchede.ProcedimentoStampaSchede;
import org.slf4j.LoggerFactory;
public class creaRTF extends HttpServlet {

    private static Logger log = LoggerFactory.getLogger(creaRTF.class);
    private HashMap<String, String> testiPortale = new HashMap<String, String>();
    private Font font10 = FontFactory.getFont(FontFactory.HELVETICA, 10);
    private Font font10b = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
    private Font font10i = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 10);
    private Font font14 = FontFactory.getFont(FontFactory.HELVETICA, 14);
    private Font fontHead = FontFactory.getFont(FontFactory.HELVETICA, 16);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int codiceIntervento = Integer.parseInt(request.getParameter("cod_int"));
        String codiceComune = request.getParameter("cod_com");
        try {
            HttpSession session = request.getSession();
            testiPortale = (HashMap<String, String>) session.getAttribute("testiPortale");
            DocumentoRtf documento = getDocumentoRtf(codiceIntervento, codiceComune);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Document document = new Document(PageSize.A4, 50, 30, 70, 70);
            RtfWriter2.getInstance(document, baos);
            document.open();
            creaDocumento(documento, document);
            document.close();
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            response.setContentType("application/rtf");
            response.setHeader("content-disposition", "attachment;filename=" + codiceIntervento + ".rtf");
            response.setContentLength(baos.size());
            ServletOutputStream out = response.getOutputStream();
            baos.writeTo(out);
            out.flush();
            out.close();

        } catch (Exception ex) {
            log.error("Error creating document", ex);
        }
    }

    private DocumentoRtf getDocumentoRtf(int codiceIntervento, String codiceComune) throws Exception {
        StampaSchedaDao dao = new StampaSchedaDao();
        DocumentoRtf documento = new DocumentoRtf();
        InterventoStampaSchede intervento = dao.selectIntervento(codiceIntervento);
        addTestiIntervento(intervento);
        documento.setIntervento(intervento);
        ProcedimentoStampaSchede procedimento = dao.selectProcedimento(intervento.getCodiceProcedimento());
        addTestiProcedimento(procedimento);
        documento.setProcedimento(procedimento);
        documento.setRelazioneEnte(dao.selectRelazioniEnti(procedimento.getCodiceCud(), codiceComune));
        documento.setDocumenti(dao.selectDocumenti(codiceComune, codiceIntervento));
        documento.setAllegati(dao.selectAllegati(codiceComune, codiceIntervento));
        documento.setNormative(dao.selectNormative(codiceComune, codiceIntervento));
        documento.setOneri(dao.selectOneri(intervento.getCodiceIntervento(), codiceComune));
        documento.setInterventiCollegati(dao.selectInterventiCollegati(codiceIntervento));
        return documento;
    }

    private void creaDocumento(DocumentoRtf documento, Document document) {
        try {
            Paragraph paragraph = new Paragraph("SCHEDA DI RILEVAMENTO DEI PROCEDIMENTI AMMINISTRATIVI", fontHead);
            paragraph.setSpacingAfter(20);
            document.add(paragraph);

            addIntervento(documento, document);
            addProcedimento(documento, document);
            addEnte(documento, document);
            addDichiarazioni(documento, document);
            addAllegati(documento, document);
            addNormative(documento, document);
            addOneri(documento, document);
            addInterventiCollegati(documento, document);
            addNote(document);
        } catch (DocumentException ex) {
            log.error("Errore in fase di creazione del documento", ex);
        }
    }

    private void addIntervento(DocumentoRtf documento, Document document) throws DocumentException {
        addParagraph(document, "Intervento", true);
        Chunk titolo = new Chunk(documento.getIntervento().getTitoloIntervento() + " (", font10);
        titolo.getFont().setStyle(Font.NORMAL);
        Chunk chiudi = new Chunk(") ", font10);
        chiudi.getFont().setStyle(Font.NORMAL);
        Chunk code = new Chunk(String.valueOf(documento.getIntervento().getCodiceIntervento()));
        code.setFont(font10i);
        Phrase p = new Phrase();
        p.add(titolo);
        p.add(code);
        p.add(chiudi);
        Paragraph paragraph = new Paragraph();
        paragraph.add(p);
        document.add(paragraph);
    }

    private void addProcedimento(DocumentoRtf documento, Document document) throws DocumentException {

        addParagraph(document, "Titolo del Procedimento:", true);
        addParagraph(document, documento.getProcedimento().getTitoloProcedimento() + " (" + String.valueOf(documento.getProcedimento().getCodiceProcedimento()) + ")", false);

        Table table = new Table(3);
        table.setWidth(95);
        RtfCell cell = new RtfCell(getPhrase("Tipologia", true));
        table.addCell(cell);
        cell = new RtfCell(getPhrase("Obbligatorietà", true));
        table.addCell(cell);
        cell = new RtfCell(getPhrase("Istanza in bollo", true));
        table.addCell(cell);

        ProcedimentoStampaSchede procedimento = documento.getProcedimento();
        InterventoStampaSchede intervento = documento.getIntervento();
        RtfCell rtfCell = new RtfCell(getPhrase(procedimento.getTestoTipoProcedimento() + " (" + procedimento.getTipoProcedimento() + ")", false));
        table.addCell(rtfCell);
        rtfCell = new RtfCell(getPhrase(intervento.getTestoObbligatorio() + " (" + intervento.getObbligatorio() + ")", false));
        table.addCell(rtfCell);
        rtfCell = new RtfCell(getPhrase(procedimento.getTestoBollo() + " (" + procedimento.getBollo() + ")", false));
        table.addCell(rtfCell);
        document.add(table);
    }

    private void addEnte(DocumentoRtf documento, Document document) throws DocumentException {
        if (documento.getRelazioneEnte() != null) {
            Paragraph p = addParagraphWithTitle("Ente compentente: ", documento.getRelazioneEnte().getDescrizioneEnte() + " (" + documento.getRelazioneEnte().getCodiceEnte() + ")");
            p.setSpacingBefore(20);
            document.add(p);

            StringBuilder builder = new StringBuilder();
            builder.append(documento.getRelazioneEnte().getIntestazione()).append(" - ").append(documento.getRelazioneEnte().getDescrizioneEnte()).append(" (").append(documento.getRelazioneEnte().getCodiceDestinatario()).append(")");
            document.add(addParagraphWithTitle("Responsabile del procedimento: ", builder.toString()));

            builder = new StringBuilder();
            builder.append(documento.getRelazioneEnte().getDescrizioneSportello()).append(" (").append(documento.getRelazioneEnte().getCodiceSportello()).append(")");
            document.add(addParagraphWithTitle("Ufficio/Unità Organizzativa presentazione istanza: ", builder.toString()));

            builder = new StringBuilder();
            builder.append(documento.getRelazioneEnte().getIntestazione()).append(" (").append(documento.getRelazioneEnte().getCodiceDestinatario()).append(")");
            document.add(addParagraphWithTitle("Ufficio/Unità Organizzativa responsabile istruttoria: ", builder.toString()));

            document.add(addParagraphWithTitle("Termini rilascio del Provvedimento finale: ", documento.getProcedimento().getTerminiEvasione() + " giorni"));
        } else {
            addParagraphWithTitle("Ente compentente: ", "Dato non presente");
            addParagraphWithTitle("Responsabile del procedimento: ", "Dato non presente");
            addParagraphWithTitle("Ufficio/Unità Organizzativa presentazione istanza: ", "Dato non presente");
            addParagraphWithTitle("Ufficio/Unità Organizzativa responsabile istruttoria: ", "Dato non presente");
            addParagraphWithTitle("Termini rilascio del Provvedimento finale: ", String.valueOf(documento.getProcedimento().getTerminiEvasione() + " giorni"));
        }
    }

    private void addDichiarazioni(DocumentoRtf documento, Document document) throws DocumentException {
        if (documento.getDocumenti() != null && documento.getDocumenti().size() > 0) {
            Paragraph paragraph = new Paragraph();
            paragraph.setAlignment(Paragraph.ALIGN_CENTER);
            paragraph.add(new Phrase("DICHIARAZIONI", font14));
            paragraph.getFont().setStyle(Font.BOLD);
            paragraph.setSpacingBefore(20);
            document.add(paragraph);

            paragraph = new Paragraph();
            for (DocumentoStampaSchede doc : documento.getDocumenti()) {
                Table table = new Table(2);
                table.setWidths(new float[]{.5f, 2f});
                table.setWidth(95);
                RtfCell rtfCell = new RtfCell(getPhrase("Dichiarazione", true));
                table.addCell(rtfCell);

                rtfCell = new RtfCell(getPhrase(doc.getTitoloDocumento() + " (" + doc.getCodiceDocumento() + ", " + doc.getHref() + ")", true));
                table.addCell(rtfCell);
                rtfCell = new RtfCell(getPhrase("Descrizione", true));
                table.addCell(rtfCell);
                rtfCell = new RtfCell(getPhrase(doc.getDescrizioneDocumento(), false));
                table.addCell(rtfCell);
                if (!"".equals(doc.getCodiceCondizione())) {
                    rtfCell = new RtfCell(getPhrase("Condizione: ", true));
                    table.addCell(rtfCell);
                    rtfCell = new RtfCell(getPhrase(doc.getTestoCondizione() + " (" + doc.getCodiceCondizione() + ")", false));
                    table.addCell(rtfCell);
                }
                document.add(table);

                if (!"".equals(doc.getHref())) {
                    Table hrefTable = preparaTabellaHref(doc);
                    if (hrefTable != null) {
                        paragraph = new Paragraph();
                        rtfCell = new RtfCell(hrefTable);
                        rtfCell.setRowspan(2);
                        table.addCell(rtfCell);
                        paragraph.add(hrefTable);
                        paragraph.setSpacingBefore(-20);
                        document.add(paragraph);
                    }
                }

                if (!"".equals(doc.getPiedeHref())) {
                    paragraph = new Paragraph();
                    Table piede = new Table(1);
                    piede.setWidth(95);
                    Phrase phrase = new Phrase(doc.getPiedeHref());
                    phrase.getFont().setFamily(FontFactory.HELVETICA);
                    phrase.getFont().setSize(8);
                    rtfCell = new RtfCell(phrase);
                    table.addCell(rtfCell);
                    RtfCell piedeCell = new RtfCell(phrase);
                    piede.addCell(piedeCell);
                    paragraph.add(piede);
                    paragraph.setSpacingBefore(-20);
                    document.add(paragraph);
                }
            }
        }
    }

    private void addAllegati(DocumentoRtf documento, Document document) throws DocumentException {
        if (documento.getAllegati() != null && documento.getAllegati().size() > 0) {
            Paragraph paragraph = new Paragraph();
            paragraph.setAlignment(Paragraph.ALIGN_CENTER);
            paragraph.add(new Phrase("ALLEGATI", font14));
            paragraph.getFont().setStyle(Font.BOLD);
            paragraph.setSpacingBefore(10);
            document.add(paragraph);
            for (AllegatoStampaSchede doc : documento.getAllegati()) {
                Table table = new Table(2);
                table.setWidth(95);
                table.setWidths(new float[]{.5f, 2f});
                RtfCell rtfCell = new RtfCell(getPhrase("Descrizione:", true));
                table.addCell(rtfCell);
                rtfCell = new RtfCell(getPhrase(doc.getTitoloDocumento() + " (" + doc.getCodiceDocumento() + ")", false));
                table.addCell(rtfCell);
                rtfCell = new RtfCell(getPhrase("Dichiarazione:", true));
                table.addCell(rtfCell);
                rtfCell = new RtfCell(getPhrase(doc.getDescrizioneDocumento(), false));
                table.addCell(rtfCell);
                if (!"".equals(doc.getNomeFile())) {
                    rtfCell = new RtfCell(getPhrase("Documento: ", true));
                    table.addCell(rtfCell);
                    rtfCell = new RtfCell(getPhrase(doc.getNomeFile(), false));
                    table.addCell(rtfCell);
                }
                rtfCell = new RtfCell(getPhrase("Copie: ", true));
                table.addCell(rtfCell);
                rtfCell = new RtfCell(getPhrase(String.valueOf(doc.getCopie()), false));
                table.addCell(rtfCell);
                if (!"".equals(doc.getCodiceCondizione())) {
                    rtfCell = new RtfCell(getPhrase("Condizione: ", true));
                    table.addCell(rtfCell);
                    rtfCell = new RtfCell(getPhrase(doc.getTestoCondizione() + " (" + doc.getCodiceCondizione() + ")", false));
                    table.addCell(rtfCell);
                }
                document.add(table);
            }
        }
    }

    private void addNormative(DocumentoRtf documento, Document document) throws DocumentException {
        if (documento.getNormative() != null && documento.getNormative().size() > 0) {
            Paragraph paragraph = new Paragraph();
            paragraph.setAlignment(Paragraph.ALIGN_CENTER);
            paragraph.add(new Phrase("NORMATIVE DI RIFERIMENTO", font14));
            paragraph.getFont().setStyle(Font.BOLD);
            paragraph.setSpacingBefore(10);
            document.add(paragraph);

            for (NormativaStampaSchede doc : documento.getNormative()) {
                Table table = new Table(2);
                table.setWidth(95);
                table.setWidths(new float[]{.5f, 2f});
                RtfCell rtfCell = new RtfCell(getPhrase("Riferimento:", true));
                table.addCell(rtfCell);
                rtfCell = new RtfCell(getPhrase(doc.getNomeRiferimento() + " (" + doc.getCodiceRiferimento() + ")", false));
                table.addCell(rtfCell);
                rtfCell = new RtfCell(getPhrase("Descrizione: ", true));
                table.addCell(rtfCell);
                rtfCell = new RtfCell(getPhrase(doc.getTitoloRiferimento(), false));
                table.addCell(rtfCell);
                rtfCell = new RtfCell(getPhrase("Articolo: ", true));
                table.addCell(rtfCell);
                rtfCell = new RtfCell(getPhrase(doc.getArticoloRiferimento(), false));
                table.addCell(rtfCell);
                rtfCell = new RtfCell(getPhrase("Tipologia: ", true));
                table.addCell(rtfCell);
                rtfCell = new RtfCell(getPhrase(doc.getTipoRiferimento() + " (" + doc.getCodiceTipoRiferimento() + ")", false));
                table.addCell(rtfCell);
                rtfCell = new RtfCell(getPhrase("Documento: ", true));
                table.addCell(rtfCell);
                rtfCell = new RtfCell(getPhrase(doc.getNomeFile(), false));
                table.addCell(rtfCell);
                document.add(table);
            }
        }
    }

    private void addOneri(DocumentoRtf documento, Document document) throws BadElementException, DocumentException {
        if (documento.getOneri() != null && documento.getOneri().size() > 0) {
            Paragraph paragraph = new Paragraph();
            paragraph.setAlignment(Paragraph.ALIGN_CENTER);
            paragraph.add(new Phrase("ONERI", font10));
            paragraph.getFont().setStyle(Font.BOLD);
            paragraph.setSpacingBefore(10);
            document.add(paragraph);

            for (OnereStampaSchede doc : documento.getOneri()) {
                Table table = new Table(2);
                table.setWidth(95);
                table.setWidths(new float[]{.5f, 2f});
                RtfCell rtfCell = new RtfCell(getPhrase("Descrizione:", true));
                table.addCell(rtfCell);
                rtfCell = new RtfCell(getPhrase(doc.getDescrizioneOneri() + " (" + doc.getCodiceOneri() + ")", false));
                table.addCell(rtfCell);
                rtfCell = new RtfCell(getPhrase("Tipologia:", true));
                table.addCell(rtfCell);
                rtfCell = new RtfCell(getPhrase(doc.getNote(), false));
                table.addCell(rtfCell);
                rtfCell = new RtfCell(getPhrase("Importo:", true));
                table.addCell(rtfCell);
                if (doc.getImportoAcconto() != null && doc.getImportoAcconto().intValue() != 0) {
                    rtfCell = new RtfCell(getPhrase(doc.getImportoAcconto().toString(), false));
                } else {
                    rtfCell = new RtfCell(getPhrase("Onere posticipato", false));
                }

                table.addCell(rtfCell);
                if (!"".equals(doc.getCodiceDocumentoOnere())) {
                    rtfCell = new RtfCell(getPhrase("Tariffario:", true));
                    table.addCell(rtfCell);
                    rtfCell = new RtfCell(getPhrase(doc.getDescrizioneDocumentoOnere() + "( " + doc.getCodiceDocumentoOnere() + ")", false));
                    table.addCell(rtfCell);
                    if (!"".equals(doc.getNomeFile())) {
                        rtfCell = new RtfCell(getPhrase("Documento:", true));
                        table.addCell(rtfCell);
                        rtfCell = new RtfCell(getPhrase(doc.getNomeFile(), false));
                        table.addCell(rtfCell);
                    }
                }
                rtfCell = new RtfCell(getPhrase("Ente destinatario:", true));
                table.addCell(rtfCell);
                rtfCell = new RtfCell(getPhrase(doc.getDescrizioneEnte() + " (" + doc.getCodiceEnte() + ")", false));
                table.addCell(rtfCell);
                document.add(table);
            }
        }
    }

    private void addInterventiCollegati(DocumentoRtf documento, Document document) throws BadElementException, DocumentException {
        if (documento.getInterventiCollegati() != null && documento.getInterventiCollegati().size() > 0) {
            Paragraph paragraph = new Paragraph();
            paragraph.setAlignment(Paragraph.ALIGN_CENTER);
            paragraph.add(new Phrase("PROCEDIMENTI COLLEGATI", font14));
            paragraph.setSpacingBefore(10);
            document.add(paragraph);

            for (InterventoStampaSchede doc : documento.getInterventiCollegati()) {
                Table table = new Table(2);
                table.setWidth(95);
                table.setWidths(new float[]{.5f, 2f});
                RtfCell rtfCell = new RtfCell(getPhrase("Esigenza:", true));
                table.addCell(rtfCell);
                rtfCell = new RtfCell(getPhrase(doc.getTitoloIntervento() + " (" + doc.getCodiceIntervento() + ")", false));
                table.addCell(rtfCell);
                rtfCell = new RtfCell(getPhrase("Procedimento:", true));
                table.addCell(rtfCell);
                rtfCell = new RtfCell(getPhrase(doc.getTitoloProcedimento() + " (" + doc.getCodiceProcedimento() + ")", false));
                table.addCell(rtfCell);
                if (!"".equals(doc.getCodiceCondizione())) {
                    rtfCell = new RtfCell(getPhrase("Condizione:", true));
                    table.addCell(rtfCell);
                    rtfCell = new RtfCell(getPhrase(doc.getTestoCondizione() + " (" + doc.getCodiceCondizione() + ")", false));
                    table.addCell(rtfCell);
                }
                document.add(table);
            }
        }
    }

    private void addTestiIntervento(InterventoStampaSchede intervento) {
        String obbligatorio = intervento.getObbligatorio();
        if ("O".equalsIgnoreCase(obbligatorio)) {
            intervento.setTestoObbligatorio(testiPortale.get("Interventi_field_form_combo_flg_obb_O"));
        } else if ("F".equalsIgnoreCase(obbligatorio)) {
            intervento.setTestoObbligatorio(testiPortale.get("Interventi_field_form_combo_flg_obb_F"));
        } else if ("N".equalsIgnoreCase(obbligatorio)) {
            intervento.setTestoObbligatorio(testiPortale.get("Interventi_field_form_combo_flg_obb_N"));
        }
    }

    private void addTestiProcedimento(ProcedimentoStampaSchede procedimento) {
        int tipoProcedimento = procedimento.getTipoProcedimento();
        if (tipoProcedimento == 0) {
            procedimento.setTestoTipoProcedimento(testiPortale.get("Procedimenti_field_form_combo_flg_tipo_proc_0"));
        } else if (tipoProcedimento == 1) {
            procedimento.setTestoTipoProcedimento(testiPortale.get("Procedimenti_field_form_combo_flg_tipo_proc_1"));
        } else if (tipoProcedimento == 2) {
            procedimento.setTestoTipoProcedimento(testiPortale.get("Procedimenti_field_form_combo_flg_tipo_proc_2"));
        }
        String bollo = procedimento.getBollo();
        if ("S".equalsIgnoreCase(bollo)) {
            procedimento.setTestoBollo(testiPortale.get("Procedimenti_field_form_combo_flg_bollo_S"));
        } else if ("N".equalsIgnoreCase(bollo)) {
            procedimento.setTestoBollo(testiPortale.get("Procedimenti_field_form_combo_flg_bollo_N"));
        }
    }

    private Table preparaTabellaHref(DocumentoStampaSchede doc) throws BadElementException {
        HrefStampaSchede[][] matrix = doc.getMatricehref();
        Table table = null;
        if (matrix != null && matrix.length > 0) {
            int matrixHeigth = matrix.length;
            int matrixSize = matrix[0].length;
            table = new Table(matrixSize);
            table.setWidth(95);
            for (int i = 0; i < matrixHeigth; i++) {
                for (int j = 0; j < matrixSize; j++) {
                    Paragraph paragraph = new Paragraph();
                    HrefStampaSchede href = matrix[i][j];
                    if (href != null) {
                        String tipo = href.getTipo();
                        StringBuilder text = new StringBuilder();
                        if ("L".equalsIgnoreCase(tipo) || "I".equalsIgnoreCase(tipo)) {
                            text.append(getDescrizione(href));
                            if (href.isObbligatorio()) {
                                text.append(" (*) ");
                            }
                            text.append("_________________");
                        } else if ("R".equalsIgnoreCase(tipo)) {
                            text.append("( ) ");
                            text.append(getDescrizione(href));
                            if (href.isObbligatorio()) {
                                text.append("(*)");
                            }
                        } else if ("C".equalsIgnoreCase(tipo)) {
                            text.append("[ ] ");
                            text.append(getDescrizione(href));
                            if (href.isObbligatorio()) {
                                text.append("(*)");
                            }
                        } else if ("T".equalsIgnoreCase(tipo)) {
                            text.append(getDescrizione(href));
                        }
                        text.append("     ");
                        paragraph.add(new Phrase(text.toString(), font10));
                        RtfCell rtfcell = new RtfCell(paragraph);
                        table.addCell(rtfcell);
                    } else {
                        paragraph.add(new Phrase("      ", font10));
                        RtfCell rtfcell = new RtfCell(paragraph);
                        table.addCell(rtfcell);
                    }
                }
            }
        }
        return table;
    }

    private String getDescrizione(HrefStampaSchede href) {
        StringBuilder result = new StringBuilder();
        if (href.getTipoRiga() > 0) {
            result.append(" (+) ");
        }
        result.append(href.getDescrizione());
        return result.toString();
    }

    public void addParagraph(Document document, String text, boolean bold) throws DocumentException {
        Paragraph paragraph = new Paragraph();
        Phrase p = new Phrase(text, font10);
        if (bold) {
            p.getFont().setStyle(Font.BOLD);
        } else {
            p.getFont().setStyle(Font.NORMAL);
        }
        paragraph.add(p);
        document.add(paragraph);
    }

    public Paragraph addParagraphWithTitle(String title, String text) throws DocumentException {
        Paragraph paragraph = new Paragraph();

        Chunk titleSection = new Chunk(title);
        titleSection.setFont(font10b);
        titleSection.getFont().setStyle(Font.BOLD);
        paragraph.add(titleSection);
        Phrase textSection = new Phrase(text, font10);
        textSection.getFont().setStyle(Font.NORMAL);
        paragraph.add(textSection);
        return paragraph;
    }

    public Phrase getPhrase(String text, boolean bold) {
        Phrase phrase = new Phrase(text);
        phrase.getFont().setFamily(FontFactory.HELVETICA);
        phrase.getFont().setSize(10);
        if (bold) {
            phrase.getFont().setStyle(Font.BOLD);
        } else {
            phrase.getFont().setStyle(Font.NORMAL);
        }
        return phrase;
    }

    private void addNote(Document document) throws DocumentException {
        Paragraph paragraph = new Paragraph();
        paragraph.setSpacingBefore(2f);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);
        Phrase p = new Phrase("NOTE", font14);
        p.getFont().setStyle(Font.BOLD);
        paragraph.add(p);
        document.add(paragraph);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
