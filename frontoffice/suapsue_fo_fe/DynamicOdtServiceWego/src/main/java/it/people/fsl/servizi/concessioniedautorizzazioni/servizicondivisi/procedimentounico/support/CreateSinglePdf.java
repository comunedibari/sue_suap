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
package it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.support;

import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.AllegatiBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.DichiarazioniStaticheBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.InterventoBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.NormativaBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ProcedimentoBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ProcedimentoSempliceBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.SezioneCompilabileBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.steps.BaseStep;
import it.people.process.AbstractPplProcess;
import it.people.util.ServiceParameters;
import it.people.wrappers.IRequestWrapper;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.Properties;

import javax.servlet.http.HttpSession;
import javax.xml.parsers.FactoryConfigurationError;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

//import com.lowagie.text.Document;
//import com.lowagie.text.DocumentException;
//import com.lowagie.text.Element;
//import com.lowagie.text.Font;
//import com.lowagie.text.Image;
//import com.lowagie.text.List;
//import com.lowagie.text.ListItem;
//import com.lowagie.text.PageSize;
//import com.lowagie.text.Paragraph;
//import com.lowagie.text.Rectangle;
//import com.lowagie.text.pdf.PdfPCell;
//import com.lowagie.text.pdf.PdfPTable;
//import com.lowagie.text.pdf.PdfWriter;

/**
 * Classe che gestisce la creazione del pdf per la stampa di qualit�
 *
 * @author InIT http://www.gruppoinit.it
 *
 * 16-giu-2006
 */
public class CreateSinglePdf extends BaseStep {
    
    private static Color headerColor = Color.LIGHT_GRAY;
    public final Log logger = LogFactory.getLog(this.getClass());
    
    public byte[] stampaPdf(AbstractPplProcess process, IRequestWrapper request){
        if(request == null){
            return null;
        }
        HttpSession session = request.getUnwrappedRequest().getSession();
        ProcessData dataForm = (ProcessData) process.getData();
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ProcessData dataFormperDestinatario = (ProcessData) session.getAttribute("dataPerDestinatario");
        try{
            //PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(m_pdfFile));
            PdfWriter pdfWriter = PdfWriter.getInstance(document,baos);
            pdfWriter.setPageEvent(new PageNumbers());
            pdfWriter.setPageEvent(new EndPage());
            
            // apro il document
            document.open();
            
            
            Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, Font.NORMAL, 10);
            Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, Font.BOLD, 10);
            Font boldFontGrande = new Font(Font.FontFamily.TIMES_ROMAN, Font.BOLD, 12);
            
            Properties p = new Properties();
            ServiceParameters params = (ServiceParameters) session.getAttribute("serviceParameters");
            String absPathToService = params.get("absPathToService");
            if(absPathToService == null){
                return null;
            }
            // Da cambiare per debug
            //p.load(new FileInputStream(absPathToService+"\\risorse\\messaggi.properties"));
            p.load(new FileInputStream(absPathToService
                                        + System.getProperty("file.separator")
                                        + "risorse"
                                        + System.getProperty("file.separator")
                                        + "messaggi.properties"));
            //p.load(new FileInputStream("E:\\eclipse\\eclipse3.0\\workspace\\people-1.2.2\\src\\it\\people\\fsl\\servizi\\concessioniedautorizzazioni\\servizicondivisi\\procedimentounico\\risorse\\messaggi.properties"));
            // Parte in alto a destra
            Paragraph[] inizio = new Paragraph[1];
            if(dataFormperDestinatario.isVisualizzaBollo()){
                inizio[0] = new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("label.marcabollo")), normalFont);
            }
            else{
                inizio[0] = new Paragraph(" ");
            }
            for (int i = 0; i < 1; i++) {
                inizio[i].setAlignment(Element.ALIGN_RIGHT);
                document.add(inizio[i]);
            }
            
            inizio = new Paragraph[1];
            if(p.getProperty("label.istruzioni.marcabolloPdf") != null && !p.getProperty("label.istruzioni.marcabolloPdf").equalsIgnoreCase("")){
                inizio[0] = new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("label.istruzioni.marcabolloPdf")), normalFont);
            }
            else{
                inizio[0] = new Paragraph(" ");
            }
            for (int i = 0; i < 1; i++) {
                inizio[i].setAlignment(Element.ALIGN_LEFT);
                document.add(inizio[i]);
            }

            inizio = new Paragraph[5];
            inizio[0] = new Paragraph(" ");
            inizio[1] = new Paragraph(dataFormperDestinatario.getComune().getSportello().getDescrizioneSportello(), boldFont);
            inizio[2] = new Paragraph(dataFormperDestinatario.getComune().getSportello().getIndirizzo(), normalFont);
            inizio[3] = new Paragraph(dataFormperDestinatario.getComune().getSportello().getCap()+" "+dataFormperDestinatario.getComune().getSportello().getCitta(), normalFont);
            
            /*
             * Modifica Init 04/04/2007
             * tolta, in visualizzazione, la parte dell'email dopo il ; (usato per il proxyMail)
             */
            String email = dataFormperDestinatario.getComune().getSportello().getEmail();
            String emailNew = "";
            if(email != null){
                int pos = email.indexOf(';');
                emailNew = email;
                if(pos != -1){
                    emailNew = email.substring(0, pos);
                }
            }
            inizio[4] = new Paragraph(emailNew, normalFont);

            for (int i = 0; i < 5; i++) {
                inizio[i].setAlignment(Element.ALIGN_RIGHT);
                document.add(inizio[i]);
            }
            
            // Oggetto
            Paragraph[] oggetto = new Paragraph[4];
            
            oggetto[0] = new Paragraph(" ");
            oggetto[1] = new Paragraph("Oggetto:", normalFont);
            String sportUnico = StringEscapeUtils.unescapeHtml(p.getProperty("mu.oggettoDesc"));
            oggetto[2] = new Paragraph(sportUnico.replaceAll("<br/>", "\n"), normalFont);
            oggetto[3] = new Paragraph(" ");
            
            for (int i = 0; i < 4; i++) {
                oggetto[i].setAlignment(Element.ALIGN_LEFT);
                document.add(oggetto[i]);
            }

            // Interventi selezionati
            Paragraph interventiSel = new Paragraph();
            interventiSel.setAlignment(Element.ALIGN_CENTER);
            List list = new List(false, 20);
            Iterator itIntSel = dataFormperDestinatario.getInterventiSelezionati().iterator();
            while(itIntSel.hasNext()){
                ListItem listItem = new ListItem(((InterventoBean)itIntSel.next()).getDescrizione(), normalFont);
                list.add(listItem);
            }
            interventiSel.add(list);
            document.add(interventiSel);
            
            aggiungiSpazi(document, 1);
            
            // Codice domanda
            PdfPTable table = new PdfPTable(2);
            PdfPCell cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.codice.domanda")), normalFont));
            cell.setGrayFill(0.90f);
            table.addCell(cell);
            table.addCell(new Paragraph(dataFormperDestinatario.getIdentificatoreProcedimentoPraticaSpacchettata(), boldFont));
            table.setWidthPercentage(100);
            document.add(table);
            
            aggiungiSpazi(document, 1);
            
            // Spazio ufficio
            table = new PdfPTable(1);
            table.addCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.spazio.ufficioPdf")), boldFont));
            table.setWidthPercentage(100);
            document.add(table);
            
            aggiungiSpazi(document, 1);
            
            // Dati richiedente
            table = new PdfPTable(8);
            table.setWidthPercentage(100);
            
            cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.sottoscritto")), normalFont));
            cell.setColspan(4);
            cell.setGrayFill(0.90f);
            table.addCell(cell);
            String nome = dataForm.getAnagrafica().getDichiarante().getNome()==null ? "" : dataForm.getAnagrafica().getDichiarante().getNome();
            String cognome = dataForm.getAnagrafica().getDichiarante().getCognome()==null ? "" : dataForm.getAnagrafica().getDichiarante().getCognome();
            cell = new PdfPCell(new Paragraph(nome+" "+cognome, boldFont));
            cell.setColspan(4);
            table.addCell(cell);
            
            cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.natoa")), normalFont));
            cell.setColspan(4);
            cell.setGrayFill(0.90f);
            table.addCell(cell);
            String dato = dataForm.getAnagrafica().getDichiarante().getLuogoNascita()==null ? "" : dataForm.getAnagrafica().getDichiarante().getLuogoNascita();
            /*
             * Modifica Init 08/05/2007 (1.3.2)
             * Aggiunta la visualizzazione della provincia
             */
            if(dataForm.getAnagrafica().getDichiarante().getProvinciaNascita()!=null && !dataForm.getAnagrafica().getDichiarante().getProvinciaNascita().equalsIgnoreCase(""))
                dato += " ("+dataForm.getAnagrafica().getDichiarante().getProvinciaNascita()+")";
            cell = new PdfPCell(new Paragraph(dato, boldFont));
            cell.setColspan(4);
            table.addCell(cell);
            
            cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.nato.il")), normalFont));
            cell.setColspan(2);
            cell.setGrayFill(0.90f);
            table.addCell(cell);
            dato = dataForm.getAnagrafica().getDichiarante().getDataNascita()==null ? "" : dataForm.getAnagrafica().getDichiarante().getDataNascita();
            cell = new PdfPCell(new Paragraph(dato, boldFont));
            cell.setColspan(2);
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.cf")), normalFont));
            cell.setColspan(2);
            cell.setGrayFill(0.90f);
            table.addCell(cell);
            dato = dataForm.getAnagrafica().getDichiarante().getCodiceFiscale()==null ? "" : dataForm.getAnagrafica().getDichiarante().getCodiceFiscale();
            cell = new PdfPCell(new Paragraph(dato, boldFont));
            cell.setColspan(2);
            table.addCell(cell);
            
            cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.residente")), normalFont));
            cell.setColspan(3);
            cell.setGrayFill(0.90f);
            table.addCell(cell);
            dato = dataForm.getAnagrafica().getDichiarante().getResidenza().getCitta()==null ? "" : dataForm.getAnagrafica().getDichiarante().getResidenza().getCitta();
            /*
             * Modifica Init 08/05/2007 (1.3.2)
             * Aggiunta la visualizzazione della provincia
             */
            if(dataForm.getAnagrafica().getDichiarante().getResidenza().getProvincia()!=null && !dataForm.getAnagrafica().getDichiarante().getResidenza().getProvincia().equalsIgnoreCase(""))
                dato += " ("+dataForm.getAnagrafica().getDichiarante().getResidenza().getProvincia()+")";
            cell = new PdfPCell(new Paragraph(dato, boldFont));
            cell.setColspan(3);
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.cap")), normalFont));
            cell.setColspan(1);
            cell.setGrayFill(0.90f);
            table.addCell(cell);
            dato = dataForm.getAnagrafica().getDichiarante().getResidenza().getCap()==null ? "" : dataForm.getAnagrafica().getDichiarante().getResidenza().getCap();
            cell = new PdfPCell(new Paragraph(dato, boldFont));
            cell.setColspan(1);
            table.addCell(cell);
            
            cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.residente.via")), normalFont));
            cell.setColspan(4);
            cell.setGrayFill(0.90f);
            table.addCell(cell);
            dato = dataForm.getAnagrafica().getDichiarante().getResidenza().getVia()==null ? "" : dataForm.getAnagrafica().getDichiarante().getResidenza().getVia();
            cell = new PdfPCell(new Paragraph(dato, boldFont));
            cell.setColspan(4);
            table.addCell(cell);
            
            /*
             * Modifica Ferretti 21/03/2007
             * Tolto il campo numero civico
             */
            /*cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.residente.n")), normalFont));
            cell.setColspan(1);
            cell.setGrayFill(0.90f);
            table.addCell(cell);
            dato = dataForm.getAnagrafica().getDichiarante().getResidenza().getNumero()==null ? "" : dataForm.getAnagrafica().getDichiarante().getResidenza().getNumero();
            cell = new PdfPCell(new Paragraph(dato, boldFont));
            cell.setColspan(1);
            table.addCell(cell);*/
            
            cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.telefono")), normalFont));
            cell.setColspan(2);
            cell.setGrayFill(0.90f);
            table.addCell(cell);
            dato = dataForm.getAnagrafica().getDichiarante().getResidenza().getTelefono()==null ? "" : dataForm.getAnagrafica().getDichiarante().getResidenza().getTelefono();
            cell = new PdfPCell(new Paragraph(dato, boldFont));
            cell.setColspan(2);
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.fax")), normalFont));
            cell.setColspan(2);
            cell.setGrayFill(0.90f);
            table.addCell(cell);
            dato = dataForm.getAnagrafica().getDichiarante().getResidenza().getFax()==null ? "" : dataForm.getAnagrafica().getDichiarante().getResidenza().getFax();
            cell = new PdfPCell(new Paragraph(dato, boldFont));
            cell.setColspan(2);
            table.addCell(cell);
            
            cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.email")), normalFont));
            cell.setColspan(2);
            cell.setGrayFill(0.90f);
            table.addCell(cell);
            dato = dataForm.getAnagrafica().getDichiarante().getResidenza().getEmail()==null ? "" : dataForm.getAnagrafica().getDichiarante().getResidenza().getEmail();
            cell = new PdfPCell(new Paragraph(dato, boldFont));
            cell.setColspan(6);
            table.addCell(cell);
            
            cell = new PdfPCell(new Paragraph("", boldFont));
            cell.setColspan(8);
            table.addCell(cell);
            
            if(dataForm.getAnagrafica().getDichiarante().getAgiscePerContoDi() != null){
                if(dataForm.getAnagrafica().getDichiarante().getAgiscePerContoDi().equalsIgnoreCase("0")){
                    cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("label.anagrafica.motivazione")), normalFont));
                    cell.setColspan(4);
                    cell.setGrayFill(0.90f);
                    table.addCell(cell);
                    if(dataForm.getAnagrafica().getAttivita().getCodiceMotivazioneRappresentanza()!=null){ 
                        if(dataForm.getAnagrafica().getAttivita().getCodiceMotivazioneRappresentanza().equalsIgnoreCase("ALTRO")){
                            dato = dataForm.getAnagrafica().getAttivita().getAltraMotivazione();
                        }
                        else{
                            dato = dataForm.getAnagrafica().getAttivita().getDescrizioneMotivazioneRappresentanza();                            
                        }
                    }
                    else{
                        dato = "";
                    }
                    cell = new PdfPCell(new Paragraph(dato, boldFont));
                    cell.setColspan(4);
                    table.addCell(cell);
                    
                    cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.impresa")), normalFont));
                    cell.setColspan(4);
                    cell.setGrayFill(0.90f);
                    table.addCell(cell);
                    dato = dataForm.getAnagrafica().getAttivita().getDenominazione()==null ? "" : dataForm.getAnagrafica().getAttivita().getDenominazione();
                    cell = new PdfPCell(new Paragraph(dato, boldFont));
                    cell.setColspan(4);
                    table.addCell(cell);
                    
                    cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.sede.legale")), normalFont));
                    cell.setColspan(2);
                    cell.setGrayFill(0.90f);
                    table.addCell(cell);
                    dato = dataForm.getAnagrafica().getAttivita().getSede().getCitta()==null ? "" : dataForm.getAnagrafica().getAttivita().getSede().getCitta();
                    if(dataForm.getAnagrafica().getAttivita().getSede().getProvincia()!=null){
                        dato+=" ("+dataForm.getAnagrafica().getAttivita().getSede().getProvincia()+")";
                    }
                    cell = new PdfPCell(new Paragraph(dato, boldFont));
                    cell.setColspan(2);
                    table.addCell(cell);
                    cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.cap")), normalFont));
                    cell.setColspan(2);
                    cell.setGrayFill(0.90f);
                    table.addCell(cell);
                    dato = dataForm.getAnagrafica().getAttivita().getSede().getCap()==null ? "" : dataForm.getAnagrafica().getAttivita().getSede().getCap();
                    cell = new PdfPCell(new Paragraph(dato, boldFont));
                    cell.setColspan(2);
                    table.addCell(cell);
                    
                    cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.via")), normalFont));
                    cell.setColspan(4);
                    cell.setGrayFill(0.90f);
                    table.addCell(cell);
                    dato = dataForm.getAnagrafica().getAttivita().getSede().getVia()==null ? "" : dataForm.getAnagrafica().getAttivita().getSede().getVia();
                    cell = new PdfPCell(new Paragraph(dato, boldFont));
                    cell.setColspan(4);
                    table.addCell(cell);
                    /*
                     * Modifica Ferretti 21/03/2007
                     * Tolto il campo numero civico
                     */
                    /*cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.numero")), normalFont));
                    cell.setColspan(1);
                    cell.setGrayFill(0.90f);
                    table.addCell(cell);
                    dato = dataForm.getAnagrafica().getAttivita().getSede().getNumero()==null ? "" : dataForm.getAnagrafica().getAttivita().getSede().getNumero();
                    cell = new PdfPCell(new Paragraph(dato, boldFont));
                    cell.setColspan(1);
                    table.addCell(cell);*/
        
                    cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.tel")), normalFont));
                    cell.setColspan(2);
                    cell.setGrayFill(0.90f);
                    table.addCell(cell);
                    dato = dataForm.getAnagrafica().getAttivita().getSede().getTelefono()==null ? "" : dataForm.getAnagrafica().getAttivita().getSede().getTelefono();
                    cell = new PdfPCell(new Paragraph(dato, boldFont));
                    cell.setColspan(2);
                    table.addCell(cell);
                    cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.fax")), normalFont));
                    cell.setColspan(2);
                    cell.setGrayFill(0.90f);
                    table.addCell(cell);
                    dato = dataForm.getAnagrafica().getAttivita().getSede().getFax()==null ? "" : dataForm.getAnagrafica().getAttivita().getSede().getFax();
                    cell = new PdfPCell(new Paragraph(dato, boldFont));
                    cell.setColspan(2);
                    table.addCell(cell);
        
                    cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.email")), normalFont));
                    cell.setColspan(4);
                    cell.setGrayFill(0.90f);
                    table.addCell(cell);
                    dato = dataForm.getAnagrafica().getAttivita().getSede().getEmail()==null ? "" : dataForm.getAnagrafica().getAttivita().getSede().getEmail();
                    cell = new PdfPCell(new Paragraph(dato, boldFont));
                    cell.setColspan(4);
                    table.addCell(cell);
        
                    cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.cf")), normalFont));
                    cell.setColspan(2);
                    cell.setGrayFill(0.90f);
                    table.addCell(cell);
                    dato = dataForm.getAnagrafica().getAttivita().getCodiceFiscale()==null ? "" : dataForm.getAnagrafica().getAttivita().getCodiceFiscale();
                    cell = new PdfPCell(new Paragraph(dato, boldFont));
                    cell.setColspan(2);
                    table.addCell(cell);
                    cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.piva")), normalFont));
                    cell.setColspan(2);
                    cell.setGrayFill(0.90f);
                    table.addCell(cell);
                    dato = dataForm.getAnagrafica().getAttivita().getPartitaIva()==null ? "" : dataForm.getAnagrafica().getAttivita().getPartitaIva();
                    cell = new PdfPCell(new Paragraph(dato, boldFont));
                    cell.setColspan(2);
                    table.addCell(cell);
        
                    cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.tribunale")), normalFont));
                    cell.setColspan(3);
                    cell.setGrayFill(0.90f);
                    table.addCell(cell);
                    dato = dataForm.getAnagrafica().getTribunale().getLuogo()==null ? "" : dataForm.getAnagrafica().getTribunale().getLuogo();
                    cell = new PdfPCell(new Paragraph(dato, boldFont));
                    cell.setColspan(3);
                    table.addCell(cell);
                    cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.numero")), normalFont));
                    cell.setColspan(1);
                    cell.setGrayFill(0.90f);
                    table.addCell(cell);
                    dato = dataForm.getAnagrafica().getTribunale().getNumero()==null ? "" : dataForm.getAnagrafica().getTribunale().getNumero();
                    cell = new PdfPCell(new Paragraph(dato, boldFont));
                    cell.setColspan(1);
                    table.addCell(cell);
        
                    cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.cciaa")), normalFont));
                    cell.setColspan(3);
                    cell.setGrayFill(0.90f);
                    table.addCell(cell);
                    dato = dataForm.getAnagrafica().getCameraCommercio().getLuogo()==null ? "" : dataForm.getAnagrafica().getCameraCommercio().getLuogo();
                    cell = new PdfPCell(new Paragraph(dato, boldFont));
                    cell.setColspan(3);
                    table.addCell(cell);
                    cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.numero")), normalFont));
                    cell.setColspan(1);
                    cell.setGrayFill(0.90f);
                    table.addCell(cell);
                    dato = dataForm.getAnagrafica().getCameraCommercio().getNumero()==null ? "" : dataForm.getAnagrafica().getCameraCommercio().getNumero();
                    cell = new PdfPCell(new Paragraph(dato, boldFont));
                    cell.setColspan(1);
                    table.addCell(cell);
                }
                else if(dataForm.getAnagrafica().getDichiarante().getAgiscePerContoDi().equalsIgnoreCase("2")){
                    cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("label.anagrafica.motivazione")), normalFont));
                    cell.setColspan(4);
                    cell.setGrayFill(0.90f);
                    table.addCell(cell);
                    if(dataForm.getAnagrafica().getDatiPersonaDeleganteBean().getCodiceMotivazioneRappresentanza()!=null){ 
                        if(dataForm.getAnagrafica().getDatiPersonaDeleganteBean().getCodiceMotivazioneRappresentanza().equalsIgnoreCase("ALTRO")){
                            dato = dataForm.getAnagrafica().getDatiPersonaDeleganteBean().getAltraMotivazione();
                        }
                        else{
                            dato = dataForm.getAnagrafica().getDatiPersonaDeleganteBean().getDescrizioneMotivazioneRappresentanza();                            
                        }
                    }
                    else{
                        dato = "";
                    }
                    cell = new PdfPCell(new Paragraph(dato, boldFont));
                    cell.setColspan(4);
                    table.addCell(cell);
                    
                    cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.signore")), normalFont));
                    cell.setColspan(4);
                    cell.setGrayFill(0.90f);
                    table.addCell(cell);
                    dato = (dataForm.getAnagrafica().getDatiPersonaDeleganteBean().getCognome()==null || dataForm.getAnagrafica().getDatiPersonaDeleganteBean().getNome()==null) ? "" : dataForm.getAnagrafica().getDatiPersonaDeleganteBean().getNome()+" "+dataForm.getAnagrafica().getDatiPersonaDeleganteBean().getCognome();
                    cell = new PdfPCell(new Paragraph(dato, boldFont));
                    cell.setColspan(4);
                    table.addCell(cell);
                    
                    cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.natoa")), normalFont));
                    cell.setColspan(4);
                    cell.setGrayFill(0.90f);
                    table.addCell(cell);
                    dato = dataForm.getAnagrafica().getDatiPersonaDeleganteBean().getLuogoNascita()==null ? "" : dataForm.getAnagrafica().getDatiPersonaDeleganteBean().getLuogoNascita();
                    
                    if(dataForm.getAnagrafica().getDatiPersonaDeleganteBean().getProvinciaNascita()!=null && !dataForm.getAnagrafica().getDatiPersonaDeleganteBean().getProvinciaNascita().equalsIgnoreCase(""))
                        dato += " ("+dataForm.getAnagrafica().getDatiPersonaDeleganteBean().getProvinciaNascita()+")";
                    
                    cell = new PdfPCell(new Paragraph(dato, boldFont));
                    cell.setColspan(4);
                    table.addCell(cell);
                    
                    
                    cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.il")), normalFont));
                    cell.setColspan(2);
                    cell.setGrayFill(0.90f);
                    table.addCell(cell);
                    dato = dataForm.getAnagrafica().getDatiPersonaDeleganteBean().getDataNascita()==null ? "" : dataForm.getAnagrafica().getDatiPersonaDeleganteBean().getDataNascita();
                    cell = new PdfPCell(new Paragraph(dato, boldFont));
                    cell.setColspan(2);
                    table.addCell(cell);
                    cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.cf")), normalFont));
                    cell.setColspan(2);
                    cell.setGrayFill(0.90f);
                    table.addCell(cell);
                    dato = dataForm.getAnagrafica().getDatiPersonaDeleganteBean().getCodiceFiscale()==null ? "" : dataForm.getAnagrafica().getDatiPersonaDeleganteBean().getCodiceFiscale();
                    cell = new PdfPCell(new Paragraph(dato, boldFont));
                    cell.setColspan(2);
                    table.addCell(cell);
                    
                    cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.residente")), normalFont));
                    cell.setColspan(2);
                    cell.setGrayFill(0.90f);
                    table.addCell(cell);
                    dato = dataForm.getAnagrafica().getDatiPersonaDeleganteBean().getResidenza().getCitta()==null ? "" : dataForm.getAnagrafica().getDatiPersonaDeleganteBean().getResidenza().getCitta();
                    
                    if(dataForm.getAnagrafica().getDatiPersonaDeleganteBean().getResidenza().getProvincia()!=null && !dataForm.getAnagrafica().getDatiPersonaDeleganteBean().getResidenza().getProvincia().equalsIgnoreCase(""))
                        dato += " ("+dataForm.getAnagrafica().getDatiPersonaDeleganteBean().getResidenza().getProvincia()+")";
                    
                    cell = new PdfPCell(new Paragraph(dato, boldFont));
                    cell.setColspan(2);
                    table.addCell(cell);
                    
                    /*cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.provincia")), normalFont));
                    cell.setColspan(1);
                    cell.setGrayFill(0.90f);
                    table.addCell(cell);
                    dato = dataForm.getAnagrafica().getDatiPersonaDeleganteBean().getResidenza().getProvincia()==null ? "" : dataForm.getAnagrafica().getDatiPersonaDeleganteBean().getResidenza().getProvincia();
                    cell = new PdfPCell(new Paragraph(dato, boldFont));
                    cell.setColspan(1);
                    table.addCell(cell);*/
                    
                    cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.cap")), normalFont));
                    cell.setColspan(2);
                    cell.setGrayFill(0.90f);
                    table.addCell(cell);
                    dato = dataForm.getAnagrafica().getDatiPersonaDeleganteBean().getResidenza().getCap()==null ? "" : dataForm.getAnagrafica().getDatiPersonaDeleganteBean().getResidenza().getCap();
                    cell = new PdfPCell(new Paragraph(dato, boldFont));
                    cell.setColspan(2);
                    table.addCell(cell);
                    
                    cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.via")), normalFont));
                    cell.setColspan(2);
                    cell.setGrayFill(0.90f);
                    table.addCell(cell);
                    dato = dataForm.getAnagrafica().getDatiPersonaDeleganteBean().getResidenza().getVia()==null ? "" : dataForm.getAnagrafica().getDatiPersonaDeleganteBean().getResidenza().getVia();
                    cell = new PdfPCell(new Paragraph(dato, boldFont));
                    cell.setColspan(6);
                    table.addCell(cell);
                    /*
                     * Modifica Ferretti 21/03/2007
                     * Tolto il campo numero civico
                     */
                    /*cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.numero")), normalFont));
                    cell.setColspan(2);
                    cell.setGrayFill(0.90f);
                    table.addCell(cell);
                    dato = dataForm.getAnagrafica().getDatiPersonaDeleganteBean().getResidenza().getNumero()==null ? "" : dataForm.getAnagrafica().getDatiPersonaDeleganteBean().getResidenza().getNumero();
                    cell = new PdfPCell(new Paragraph(dato, boldFont));
                    cell.setColspan(2);
                    table.addCell(cell);*/
                }
                else if(dataForm.getAnagrafica().getDichiarante().getAgiscePerContoDi().equalsIgnoreCase("3")){
                    cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("label.anagrafica.motivazione")), normalFont));
                    cell.setColspan(4);
                    cell.setGrayFill(0.90f);
                    table.addCell(cell);
                    if(dataForm.getAnagrafica().getDatiAziendaNoProfitDeleganteBean().getCodiceMotivazioneRappresentanza()!=null){ 
                        if(dataForm.getAnagrafica().getDatiAziendaNoProfitDeleganteBean().getCodiceMotivazioneRappresentanza().equalsIgnoreCase("ALTRO")){
                            dato = dataForm.getAnagrafica().getDatiAziendaNoProfitDeleganteBean().getAltraMotivazione();
                        }
                        else{
                            dato = dataForm.getAnagrafica().getDatiAziendaNoProfitDeleganteBean().getDescrizioneMotivazioneRappresentanza();                            
                        }
                    }
                    else{
                        dato = "";
                    }
                    cell = new PdfPCell(new Paragraph(dato, boldFont));
                    cell.setColspan(4);
                    table.addCell(cell);
                
                    cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.impresa")), normalFont));
                    cell.setColspan(2);
                    cell.setGrayFill(0.90f);
                    table.addCell(cell);
                    dato = dataForm.getAnagrafica().getDatiAziendaNoProfitDeleganteBean().getDenominazione()==null ? "" : dataForm.getAnagrafica().getDatiAziendaNoProfitDeleganteBean().getDenominazione();
                    cell = new PdfPCell(new Paragraph(dato, boldFont));
                    cell.setColspan(6);
                    table.addCell(cell);
                    
                    cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.sedelegalein")), normalFont));
                    cell.setColspan(2);
                    cell.setGrayFill(0.90f);
                    table.addCell(cell);
                    dato = dataForm.getAnagrafica().getDatiAziendaNoProfitDeleganteBean().getSede().getCitta()==null ? "" : dataForm.getAnagrafica().getDatiAziendaNoProfitDeleganteBean().getSede().getCitta();
                    if(dataForm.getAnagrafica().getDatiAziendaNoProfitDeleganteBean().getSede().getProvincia()!=null){
                        dato+=" ("+dataForm.getAnagrafica().getDatiAziendaNoProfitDeleganteBean().getSede().getProvincia()+")";
                    }
                    cell = new PdfPCell(new Paragraph(dato, boldFont));
                    cell.setColspan(2);
                    table.addCell(cell);
                    cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.cap")), normalFont));
                    cell.setColspan(2);
                    cell.setGrayFill(0.90f);
                    table.addCell(cell);
                    dato = dataForm.getAnagrafica().getDatiAziendaNoProfitDeleganteBean().getSede().getCap()==null ? "" : dataForm.getAnagrafica().getDatiAziendaNoProfitDeleganteBean().getSede().getCap();
                    cell = new PdfPCell(new Paragraph(dato, boldFont));
                    cell.setColspan(2);
                    table.addCell(cell);
                    
                    cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.via")), normalFont));
                    cell.setColspan(2);
                    cell.setGrayFill(0.90f);
                    table.addCell(cell);
                    dato = dataForm.getAnagrafica().getDatiAziendaNoProfitDeleganteBean().getSede().getVia()==null ? "" : dataForm.getAnagrafica().getDatiAziendaNoProfitDeleganteBean().getSede().getVia();
                    cell = new PdfPCell(new Paragraph(dato, boldFont));
                    cell.setColspan(6);
                    table.addCell(cell);
                    /*
                     * Modifica Ferretti 21/03/2007
                     * Tolto il campo numero civico
                     */
                    /*cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.numero")), normalFont));
                    cell.setColspan(2);
                    cell.setGrayFill(0.90f);
                    table.addCell(cell);
                    dato = dataForm.getAnagrafica().getDatiAziendaNoProfitDeleganteBean().getSede().getNumero()==null ? "" : dataForm.getAnagrafica().getDatiAziendaNoProfitDeleganteBean().getSede().getNumero();
                    cell = new PdfPCell(new Paragraph(dato, boldFont));
                    cell.setColspan(2);
                    table.addCell(cell);*/
                    
                    cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.tel")), normalFont));
                    cell.setColspan(2);
                    cell.setGrayFill(0.90f);
                    table.addCell(cell);
                    dato = dataForm.getAnagrafica().getDatiAziendaNoProfitDeleganteBean().getSede().getTelefono()==null ? "" : dataForm.getAnagrafica().getDatiAziendaNoProfitDeleganteBean().getSede().getTelefono();
                    cell = new PdfPCell(new Paragraph(dato, boldFont));
                    cell.setColspan(2);
                    table.addCell(cell);
                    cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.fax")), normalFont));
                    cell.setColspan(2);
                    cell.setGrayFill(0.90f);
                    table.addCell(cell);
                    dato = dataForm.getAnagrafica().getDatiAziendaNoProfitDeleganteBean().getSede().getFax()==null ? "" : dataForm.getAnagrafica().getDatiAziendaNoProfitDeleganteBean().getSede().getFax();
                    cell = new PdfPCell(new Paragraph(dato, boldFont));
                    cell.setColspan(2);
                    table.addCell(cell);
    
                    cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.email")), normalFont));
                    cell.setColspan(2);
                    cell.setGrayFill(0.90f);
                    table.addCell(cell);
                    dato = dataForm.getAnagrafica().getDatiAziendaNoProfitDeleganteBean().getSede().getEmail()==null ? "" : dataForm.getAnagrafica().getDatiAziendaNoProfitDeleganteBean().getSede().getEmail();
                    cell = new PdfPCell(new Paragraph(dato, boldFont));
                    cell.setColspan(6);
                    table.addCell(cell);
    
                    cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.cf")), normalFont));
                    cell.setColspan(2);
                    cell.setGrayFill(0.90f);
                    table.addCell(cell);
                    dato = dataForm.getAnagrafica().getDatiAziendaNoProfitDeleganteBean().getSede().getCodFiscale()==null ? "" : dataForm.getAnagrafica().getDatiAziendaNoProfitDeleganteBean().getSede().getCodFiscale();
                    cell = new PdfPCell(new Paragraph(dato, boldFont));
                    cell.setColspan(2);
                    table.addCell(cell);
                    cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.piva")), normalFont));
                    cell.setColspan(2);
                    cell.setGrayFill(0.90f);
                    table.addCell(cell);
                    dato = dataForm.getAnagrafica().getDatiAziendaNoProfitDeleganteBean().getSede().getPartitaIva()==null ? "" : dataForm.getAnagrafica().getDatiAziendaNoProfitDeleganteBean().getSede().getPartitaIva();
                    cell = new PdfPCell(new Paragraph(dato, boldFont));
                    cell.setColspan(2);
                    table.addCell(cell);
                    
                    cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("label.informazioniregistro")+":"), boldFont));
                    cell.setColspan(8);
                    cell.setGrayFill(0.90f);
                    table.addCell(cell);
                    
                    cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("label.regioneregistro")), normalFont));
                    cell.setColspan(2);
                    cell.setGrayFill(0.90f);
                    table.addCell(cell);
                    dato = dataForm.getAnagrafica().getDatiAziendaNoProfitDeleganteBean().getRegioneRegistro()==null ? "" : dataForm.getAnagrafica().getDatiAziendaNoProfitDeleganteBean().getRegioneRegistro();
                    cell = new PdfPCell(new Paragraph(dato, boldFont));
                    cell.setColspan(2);
                    table.addCell(cell);
                    cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("label.numeroregistro")), normalFont));
                    cell.setColspan(2);
                    cell.setGrayFill(0.90f);
                    table.addCell(cell);
                    dato = dataForm.getAnagrafica().getDatiAziendaNoProfitDeleganteBean().getNumeroRegistro()==null ? "" : dataForm.getAnagrafica().getDatiAziendaNoProfitDeleganteBean().getNumeroRegistro();
                    cell = new PdfPCell(new Paragraph(dato, boldFont));
                    cell.setColspan(2);
                    table.addCell(cell);
                }
                else if(dataForm.getAnagrafica().getDichiarante().getAgiscePerContoDi().equalsIgnoreCase("4")){
                    cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("label.anagrafica.tipoprofessione")), normalFont));
                    cell.setColspan(4);
                    cell.setGrayFill(0.90f);
                    table.addCell(cell);
                    if(dataForm.getAnagrafica().getDatiProfessionistaBean().getCodiceMotivazioneRappresentanza()!=null){ 
                        if(dataForm.getAnagrafica().getDatiProfessionistaBean().getCodiceMotivazioneRappresentanza().equalsIgnoreCase("ALTRO")){
                            dato = dataForm.getAnagrafica().getDatiProfessionistaBean().getAltraMotivazione();
                        }
                        else{
                            dato = dataForm.getAnagrafica().getDatiProfessionistaBean().getDescrizioneMotivazioneRappresentanza();                            
                        }
                    }
                    else{
                        dato = "";
                    }
                    cell = new PdfPCell(new Paragraph(dato, boldFont));
                    cell.setColspan(4);
                    table.addCell(cell);
                    
                    cell = new PdfPCell(new Paragraph("", boldFont));
                    cell.setColspan(8);
                    table.addCell(cell);
                    
                    cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("label.iscrizionealbo")), normalFont));
                    cell.setColspan(4);
                    cell.setGrayFill(0.90f);
                    table.addCell(cell);
                    if(dataForm.getAnagrafica().getDatiProfessionistaBean().getCodiceAlbo()!=null){ 
                        if(dataForm.getAnagrafica().getDatiProfessionistaBean().getCodiceAlbo().equalsIgnoreCase("ALTRO")){
                            dato = dataForm.getAnagrafica().getDatiProfessionistaBean().getAltroAlbo();
                        }
                        else{
                            dato = dataForm.getAnagrafica().getDatiProfessionistaBean().getDescrizioneAlbo();                            
                        }
                    }
                    else{
                        dato = "";
                    }
                    cell = new PdfPCell(new Paragraph(dato, boldFont));
                    cell.setColspan(4);
                    table.addCell(cell);
                    
                    cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("label.professionista.numeroiscrizionealbo")), normalFont));
                    cell.setColspan(2);
                    cell.setGrayFill(0.90f);
                    table.addCell(cell);
                    dato = dataForm.getAnagrafica().getDatiProfessionistaBean().getNumeroIscrizioneAlbo()==null ? "" : dataForm.getAnagrafica().getDatiProfessionistaBean().getNumeroIscrizioneAlbo();
                    cell = new PdfPCell(new Paragraph(dato, boldFont));
                    cell.setColspan(1);
                    table.addCell(cell);
                    cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("label.professionista.provinciaiscrizionealbo")), normalFont));
                    cell.setColspan(2);
                    cell.setGrayFill(0.90f);
                    table.addCell(cell);
                    dato = dataForm.getAnagrafica().getDatiProfessionistaBean().getDataIscrizioneAlbo()==null ? "" : dataForm.getAnagrafica().getDatiProfessionistaBean().getDataIscrizioneAlbo();
                    cell = new PdfPCell(new Paragraph(dato, boldFont));
                    cell.setColspan(1);
                    table.addCell(cell);
                    cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("label.professionista.dataiscrizionealbo")), normalFont));
                    cell.setColspan(1);
                    cell.setGrayFill(0.90f);
                    table.addCell(cell);
                    dato = dataForm.getAnagrafica().getDatiProfessionistaBean().getProvinciaIscrizioneAlbo()==null ? "" : dataForm.getAnagrafica().getDatiProfessionistaBean().getProvinciaIscrizioneAlbo();
                    cell = new PdfPCell(new Paragraph(dato, boldFont));
                    cell.setColspan(1);
                    table.addCell(cell);
                    
                    cell = new PdfPCell(new Paragraph("", boldFont));
                    cell.setColspan(8);
                    table.addCell(cell);
                    
                    if(dataForm.getAnagrafica().getDatiProfessionistaBean().getDelega().getCodiceCausaleDelega()!=null){ 
                        if(dataForm.getAnagrafica().getDatiProfessionistaBean().getDelega().getCodiceCausaleDelega().equalsIgnoreCase("ALTRO")){
                            dato = dataForm.getAnagrafica().getDatiProfessionistaBean().getDelega().getAltraCausaleDelega();
                        }
                        else{
                            dato = dataForm.getAnagrafica().getDatiProfessionistaBean().getDelega().getDescrizioneCausaleDelega();                            
                        }
                    }
                    else{
                        dato = "";
                    }
                    cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("label.dichiarazione.delega")+" "+p.getProperty("label.dellapresentazione")+" "+dato), normalFont));
                    cell.setColspan(8);
                    cell.setGrayFill(0.90f);
                    table.addCell(cell);
                    
                    cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("label.tramite")), normalFont));
                    cell.setColspan(2);
                    cell.setGrayFill(0.90f);
                    table.addCell(cell);
                    if(dataForm.getAnagrafica().getDatiProfessionistaBean().getDelega().getCodiceTramiteDelega()!=null){ 
                        if(dataForm.getAnagrafica().getDatiProfessionistaBean().getDelega().getCodiceTramiteDelega().equalsIgnoreCase("ALTRO")){
                            dato = dataForm.getAnagrafica().getDatiProfessionistaBean().getDelega().getAltroTramiteDelega();
                        }
                        else{
                            dato = dataForm.getAnagrafica().getDatiProfessionistaBean().getDelega().getDescrizioneTramitedelega();                            
                        }
                    }
                    else{
                        dato = "";
                    }
                    cell = new PdfPCell(new Paragraph(dato, boldFont));
                    cell.setColspan(1);
                    table.addCell(cell);
                    cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("label.sottoscritta")), normalFont));
                    cell.setColspan(2);
                    cell.setGrayFill(0.90f);
                    table.addCell(cell);
                    dato = dataForm.getAnagrafica().getDatiProfessionistaBean().getDelega().getDataSottoscrizioneDelega()==null ? "" : dataForm.getAnagrafica().getDatiProfessionistaBean().getDelega().getDataSottoscrizioneDelega();
                    cell = new PdfPCell(new Paragraph(dato, boldFont));
                    cell.setColspan(1);
                    table.addCell(cell);
                    cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("label.custoditapresso")), normalFont));
                    cell.setColspan(1);
                    cell.setGrayFill(0.90f);
                    table.addCell(cell);
                    dato = dataForm.getAnagrafica().getDatiProfessionistaBean().getDelega().getLuogoOriginaleDelega()==null ? "" : dataForm.getAnagrafica().getDatiProfessionistaBean().getDelega().getLuogoOriginaleDelega();
                    cell = new PdfPCell(new Paragraph(dato, boldFont));
                    cell.setColspan(1);
                    table.addCell(cell);
                    
                    cell = new PdfPCell(new Paragraph("", boldFont));
                    cell.setColspan(8);
                    table.addCell(cell);
                    
                    cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("label.profilotitolare")+":"), normalFont));
                    cell.setColspan(8);
                    cell.setGrayFill(0.90f);
                    table.addCell(cell);
                    
                    if(dataForm.getAnagrafica().getDatiProfessionistaBean().getAgiscePerContoDi().equalsIgnoreCase("PF")){
                        cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("label.nomecognome")), normalFont));
                        cell.setColspan(4);
                        cell.setGrayFill(0.90f);
                        table.addCell(cell);
                        dato = (dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaFisica().getCognome()==null || dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaFisica().getNome()==null) ? "" : dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaFisica().getNome()+" "+dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaFisica().getCognome();
                        cell = new PdfPCell(new Paragraph(dato, boldFont));
                        cell.setColspan(4);
                        table.addCell(cell);
                        
                        cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.natoa")), normalFont));
                        cell.setColspan(4);
                        cell.setGrayFill(0.90f);
                        table.addCell(cell);
                        dato = dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaFisica().getLuogoNascita()==null ? "" : dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaFisica().getLuogoNascita();
                        
                        if(dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaFisica().getProvinciaNascita()!=null && !dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaFisica().getProvinciaNascita().equalsIgnoreCase(""))
                            dato += " ("+dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaFisica().getProvinciaNascita()+")";
                        
                        cell = new PdfPCell(new Paragraph(dato, boldFont));
                        cell.setColspan(4);
                        table.addCell(cell);
                        
                        
                        cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.il")), normalFont));
                        cell.setColspan(2);
                        cell.setGrayFill(0.90f);
                        table.addCell(cell);
                        dato = dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaFisica().getDataNascita()==null ? "" : dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaFisica().getDataNascita();
                        cell = new PdfPCell(new Paragraph(dato, boldFont));
                        cell.setColspan(2);
                        table.addCell(cell);
                        cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.cf")), normalFont));
                        cell.setColspan(2);
                        cell.setGrayFill(0.90f);
                        table.addCell(cell);
                        dato = dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaFisica().getCodiceFiscale()==null ? "" : dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaFisica().getCodiceFiscale();
                        cell = new PdfPCell(new Paragraph(dato, boldFont));
                        cell.setColspan(2);
                        table.addCell(cell);
                        
                        cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.residente")), normalFont));
                        cell.setColspan(2);
                        cell.setGrayFill(0.90f);
                        table.addCell(cell);
                        dato = dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaFisica().getResidenza().getCitta()==null ? "" : dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaFisica().getResidenza().getCitta();
                        
                        if(dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaFisica().getResidenza().getProvincia()!=null && !dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaFisica().getResidenza().getProvincia().equalsIgnoreCase(""))
                            dato += " ("+dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaFisica().getResidenza().getProvincia()+")";
                        
                        cell = new PdfPCell(new Paragraph(dato, boldFont));
                        cell.setColspan(2);
                        table.addCell(cell);
                        
                        cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.cap")), normalFont));
                        cell.setColspan(2);
                        cell.setGrayFill(0.90f);
                        table.addCell(cell);
                        dato = dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaFisica().getResidenza().getCap()==null ? "" : dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaFisica().getResidenza().getCap();
                        cell = new PdfPCell(new Paragraph(dato, boldFont));
                        cell.setColspan(2);
                        table.addCell(cell);
                        
                        cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.via")), normalFont));
                        cell.setColspan(2);
                        cell.setGrayFill(0.90f);
                        table.addCell(cell);
                        dato = dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaFisica().getResidenza().getVia()==null ? "" : dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaFisica().getResidenza().getVia();
                        cell = new PdfPCell(new Paragraph(dato, boldFont));
                        cell.setColspan(6);
                        table.addCell(cell);
                    }
                    else if(dataForm.getAnagrafica().getDatiProfessionistaBean().getAgiscePerContoDi().equalsIgnoreCase("PG")){
                        cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("label.anagrafica.denominazione")), normalFont));
                        cell.setColspan(4);
                        cell.setGrayFill(0.90f);
                        table.addCell(cell);
                        dato = dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaGiuridica().getDenominazione()==null ? "" : dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaGiuridica().getDenominazione();
                        cell = new PdfPCell(new Paragraph(dato, boldFont));
                        cell.setColspan(4);
                        table.addCell(cell);
                        
                        cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.sede.legale")), normalFont));
                        cell.setColspan(2);
                        cell.setGrayFill(0.90f);
                        table.addCell(cell);
                        dato = dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaGiuridica().getSede().getCitta()==null ? "" : dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaGiuridica().getSede().getCitta();
                        if(dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaGiuridica().getSede().getProvincia() != null){
                            dato += " ("+dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaGiuridica().getSede().getProvincia()+")";
                        }
                        cell = new PdfPCell(new Paragraph(dato, boldFont));
                        cell.setColspan(2);
                        table.addCell(cell);
                        cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.cap")), normalFont));
                        cell.setColspan(2);
                        cell.setGrayFill(0.90f);
                        table.addCell(cell);
                        dato = dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaGiuridica().getSede().getCap()==null ? "" : dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaGiuridica().getSede().getCap();
                        cell = new PdfPCell(new Paragraph(dato, boldFont));
                        cell.setColspan(2);
                        table.addCell(cell);
                        
                        cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.via")), normalFont));
                        cell.setColspan(4);
                        cell.setGrayFill(0.90f);
                        table.addCell(cell);
                        dato = dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaGiuridica().getSede().getVia()==null ? "" : dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaGiuridica().getSede().getVia();
                        cell = new PdfPCell(new Paragraph(dato, boldFont));
                        cell.setColspan(4);
                        table.addCell(cell);
                        
                        cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.tel")), normalFont));
                        cell.setColspan(2);
                        cell.setGrayFill(0.90f);
                        table.addCell(cell);
                        dato = dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaGiuridica().getSede().getTelefono()==null ? "" : dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaGiuridica().getSede().getTelefono();
                        cell = new PdfPCell(new Paragraph(dato, boldFont));
                        cell.setColspan(2);
                        table.addCell(cell);
                        cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.fax")), normalFont));
                        cell.setColspan(2);
                        cell.setGrayFill(0.90f);
                        table.addCell(cell);
                        dato = dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaGiuridica().getSede().getFax()==null ? "" : dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaGiuridica().getSede().getFax();
                        cell = new PdfPCell(new Paragraph(dato, boldFont));
                        cell.setColspan(2);
                        table.addCell(cell);
            
                        cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.email")), normalFont));
                        cell.setColspan(4);
                        cell.setGrayFill(0.90f);
                        table.addCell(cell);
                        dato = dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaGiuridica().getSede().getEmail()==null ? "" : dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaGiuridica().getSede().getEmail();
                        cell = new PdfPCell(new Paragraph(dato, boldFont));
                        cell.setColspan(4);
                        table.addCell(cell);
            
                        cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.cf")), normalFont));
                        cell.setColspan(2);
                        cell.setGrayFill(0.90f);
                        table.addCell(cell);
                        dato = dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaGiuridica().getCodiceFiscale()==null ? "" : dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaGiuridica().getCodiceFiscale();
                        cell = new PdfPCell(new Paragraph(dato, boldFont));
                        cell.setColspan(2);
                        table.addCell(cell);
                        cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.piva")), normalFont));
                        cell.setColspan(2);
                        cell.setGrayFill(0.90f);
                        table.addCell(cell);
                        dato = dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaGiuridica().getPartitaIva()==null ? "" : dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaGiuridica().getPartitaIva();
                        cell = new PdfPCell(new Paragraph(dato, boldFont));
                        cell.setColspan(2);
                        table.addCell(cell);
            
                        cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.tribunale")), normalFont));
                        cell.setColspan(3);
                        cell.setGrayFill(0.90f);
                        table.addCell(cell);
                        dato = dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaGiuridica().getTribunale().getLuogo()==null ? "" : dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaGiuridica().getTribunale().getLuogo();
                        cell = new PdfPCell(new Paragraph(dato, boldFont));
                        cell.setColspan(3);
                        table.addCell(cell);
                        cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.numero")), normalFont));
                        cell.setColspan(1);
                        cell.setGrayFill(0.90f);
                        table.addCell(cell);
                        dato = dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaGiuridica().getTribunale().getNumero()==null ? "" : dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaGiuridica().getTribunale().getNumero();
                        cell = new PdfPCell(new Paragraph(dato, boldFont));
                        cell.setColspan(1);
                        table.addCell(cell);
            
                        cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.cciaa")), normalFont));
                        cell.setColspan(3);
                        cell.setGrayFill(0.90f);
                        table.addCell(cell);
                        dato = dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaGiuridica().getCameraCommercio().getLuogo()==null ? "" : dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaGiuridica().getCameraCommercio().getLuogo();
                        cell = new PdfPCell(new Paragraph(dato, boldFont));
                        cell.setColspan(3);
                        table.addCell(cell);
                        cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.numero")), normalFont));
                        cell.setColspan(1);
                        cell.setGrayFill(0.90f);
                        table.addCell(cell);
                        dato = dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaGiuridica().getCameraCommercio().getNumero()==null ? "" : dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaGiuridica().getCameraCommercio().getNumero();
                        cell = new PdfPCell(new Paragraph(dato, boldFont));
                        cell.setColspan(1);
                        table.addCell(cell);
                        
                        cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("label.rappresentanteLegale")+":"), normalFont));
                        cell.setColspan(8);
                        cell.setGrayFill(0.90f);
                        table.addCell(cell);
                        
                        cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("label.nomecognome")), normalFont));
                        cell.setColspan(2);
                        cell.setGrayFill(0.90f);
                        table.addCell(cell);
                        dato = (dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaGiuridica().getRappresentanteLegale().getNome()==null && dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaGiuridica().getRappresentanteLegale().getCognome()==null) ? "" : dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaGiuridica().getRappresentanteLegale().getNome()+" "+dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaGiuridica().getRappresentanteLegale().getCognome();
                        cell = new PdfPCell(new Paragraph(dato, boldFont));
                        cell.setColspan(2);
                        table.addCell(cell);
                        cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("label.anagrafica.codFiscale")), normalFont));
                        cell.setColspan(2);
                        cell.setGrayFill(0.90f);
                        table.addCell(cell);
                        dato = dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaGiuridica().getRappresentanteLegale().getCodiceFiscale()==null ? "" : dataForm.getAnagrafica().getDatiProfessionistaBean().getPersonaGiuridica().getRappresentanteLegale().getCodiceFiscale();
                        cell = new PdfPCell(new Paragraph(dato, boldFont));
                        cell.setColspan(2);
                        table.addCell(cell);
                    }
                }
            }
            document.add(table);
            aggiungiSpazi(document, 1);
            
            // Settore attivit�
            /*table = new PdfPTable(2);
            
            cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.settore.attivita")), normalFont));
            cell.setGrayFill(0.90f);
            table.addCell(cell);
            table.addCell(new Paragraph(dataForm.getDescrizioneSettore(), boldFont));
            cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.comune.ente")), normalFont));
            cell.setGrayFill(0.90f);
            table.addCell(cell);
            table.addCell(new Paragraph(dataForm.getComune().getCitta(), boldFont));
            table.setWidthPercentage(100);
            document.add(table);
            
            aggiungiSpazi(document, 1);*/
            
            
            
            java.util.List listaProc = dataFormperDestinatario.getProcedimenti();
            boolean ciSonoProcedimentiSemplici = false;
            boolean ciSonoProcedimentiComunicazioni = false;
            Iterator itListaProc = listaProc.iterator();
            while(itListaProc.hasNext()){
                ProcedimentoSempliceBean procSempl = ((ProcedimentoBean)itListaProc.next()).getProcedimento();
                if(procSempl.getTipo()==0 || procSempl.getTipo()==1)
                    ciSonoProcedimentiSemplici = true;
                if(procSempl.getTipo()==2)
                    ciSonoProcedimentiComunicazioni = true;
            }
            if(ciSonoProcedimentiSemplici){            
//-------------------------------------------------------            
                // Chiede
//                if(dataForm.getTipoProcedura() == 0){
//                    dato = p.getProperty("mu.chiede");
//                }
//                else if(dataForm.getTipoProcedura() == 1){
//                    dato = p.getProperty("mu.chiede");
//                }
                dato = p.getProperty("mu.chiede");
                
                Paragraph chiede = new Paragraph(dato, boldFontGrande);
                chiede.setAlignment(Element.ALIGN_CENTER);
                document.add(chiede);
                
                aggiungiSpazi(document, 1);
                
                // Tipo procedimento
//                if(dataForm.getTipoProcedura() == 0){
//                    dato = StringEscapeUtils.unescapeHtml(p.getProperty("mu.avvio.procedimento.semplificato"));
//                }
//                else{
//                    dato = StringEscapeUtils.unescapeHtml(p.getProperty("mu.avvio.autocertificazione"));
//                }
                dato = StringEscapeUtils.unescapeHtml(p.getProperty("mu.avvio.procedimento.semplificato"));

                Paragraph proc = new Paragraph(new Paragraph(dato, normalFont));
                document.add(proc);
                
                aggiungiSpazi(document, 1);
                
                // Lista procedimenti
    //            float[] widths = {20f, 27f, 13f, 27f, 13f};
                float[] widths = {23f, 30f, 16f, 31f};
                table = new PdfPTable(widths);
                table.setWidthPercentage(100);
                
                cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.procedimenti.semplici")), boldFont));
                cell.setColspan(5);
                cell.setGrayFill(0.75f);
                table.addCell(cell);

                cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.procedimenti.int.proc")), boldFont));
                cell.setGrayFill(0.75f);
                table.addCell(cell);

                cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.procedimenti.int.intervento")), boldFont));
                cell.setGrayFill(0.75f);
                table.addCell(cell);
                cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.procedimenti.int.ente")), boldFont));
                cell.setGrayFill(0.75f);
                table.addCell(cell);
                cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.procedimenti.int.norme")), boldFont));
                cell.setGrayFill(0.75f);
                table.addCell(cell);
//            cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.procedimenti.int.procedura")), boldFont));
//            cell.setGrayFill(0.75f);
//            table.addCell(cell);
                
                table.setHeaderRows(2);
                
                Iterator itProc = dataFormperDestinatario.getProcedimenti().iterator();
                while(itProc.hasNext()){
                    ProcedimentoBean procBean = (ProcedimentoBean)itProc.next();
                    if(procBean.getProcedimento().getTipo()==0 || procBean.getProcedimento().getTipo()==1){
                        table.addCell(new PdfPCell(new Paragraph(procBean.getProcedimento().getNome(), normalFont)));
                        
                        // Interventi
                        Iterator itInt = procBean.getProcedimento().getInterventi().iterator();
                        PdfPTable innerTable = new PdfPTable(1);
                        while(itInt.hasNext()){
                            InterventoBean intBean = (InterventoBean)itInt.next();
                            PdfPCell pdcell = new PdfPCell(new Paragraph("- "+intBean.getDescrizione(), normalFont));
                            pdcell.cloneNonPositionParameters(getBordoInvisibile());
                            innerTable.addCell(pdcell);
                        }
                        table.addCell(innerTable);
                        
                        table.addCell(new PdfPCell(new Paragraph(procBean.getProcedimento().getEnte(), normalFont)));
                        
                        // Normative
                        Iterator itNorm = procBean.getNormative().iterator();
                        innerTable = new PdfPTable(1);
                        while(itNorm.hasNext()){
                            NormativaBean normBean = (NormativaBean)itNorm.next();
                            PdfPCell pdcell = new PdfPCell(new Paragraph("- "+normBean.getNomeRiferimento()+" "+normBean.getTitoloRiferimento(), normalFont));
                            pdcell.cloneNonPositionParameters(getBordoInvisibile());
                            innerTable.addCell(pdcell);
                        }
                        table.addCell(innerTable);   
                    }
                }
                document.add(table);
                aggiungiSpazi(document, 1);
            }
//          -------------------------------------------------------            
//          -------------------------------------------------------            
            if(ciSonoProcedimentiComunicazioni){
                // Chiede
                dato = p.getProperty("mu.comunica");
                
                Paragraph chiede = new Paragraph(dato, boldFontGrande);
                chiede.setAlignment(Element.ALIGN_CENTER);
                document.add(chiede);
                
                aggiungiSpazi(document, 1);
                
                // Tipo procedimento
                dato = StringEscapeUtils.unescapeHtml(p.getProperty("mu.avvio.generico"));

                Paragraph proc = new Paragraph(new Paragraph(dato, normalFont));
                document.add(proc);
                
                aggiungiSpazi(document, 1);
                
                // Lista procedimenti
//                float[] widths = {20f, 27f, 13f, 27f, 13f};
                float[] widths2 = {23f, 30f, 16f, 31f};
                table = new PdfPTable(widths2);
                table.setWidthPercentage(100);

                cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.procedimenti.dia")), boldFont));
                cell.setColspan(5);
                cell.setGrayFill(0.75f);
                table.addCell(cell);
                
                cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.procedimenti.dia")), boldFont));
                cell.setGrayFill(0.75f);
                table.addCell(cell);
                
                cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.procedimenti.int.intervento")), boldFont));
                cell.setGrayFill(0.75f);
                table.addCell(cell);
                cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.procedimenti.int.ente")), boldFont));
                cell.setGrayFill(0.75f);
                table.addCell(cell);
                cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.procedimenti.int.norme")), boldFont));
                cell.setGrayFill(0.75f);
                table.addCell(cell);
    //            cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.procedimenti.int.procedura")), boldFont));
    //            cell.setGrayFill(0.75f);
    //            table.addCell(cell);
                
                table.setHeaderRows(2);
                
                Iterator itProc = dataFormperDestinatario.getProcedimenti().iterator();
                while(itProc.hasNext()){
                    ProcedimentoBean procBean = (ProcedimentoBean)itProc.next();
                    if(procBean.getProcedimento().getTipo()==2){ 
                        table.addCell(new PdfPCell(new Paragraph(procBean.getProcedimento().getNome(), normalFont)));
                        
                        // Interventi
                        Iterator itInt = procBean.getProcedimento().getInterventi().iterator();
                        PdfPTable innerTable = new PdfPTable(1);
                        while(itInt.hasNext()){
                            InterventoBean intBean = (InterventoBean)itInt.next();
                            PdfPCell pdcell = new PdfPCell(new Paragraph("- "+intBean.getDescrizione(), normalFont));
                            pdcell.cloneNonPositionParameters(getBordoInvisibile());
                            innerTable.addCell(pdcell);
                        }
                        table.addCell(innerTable);
                        
                        table.addCell(new PdfPCell(new Paragraph(procBean.getProcedimento().getEnte(), normalFont)));
                        
                        // Normative
                        Iterator itNorm = procBean.getNormative().iterator();
                        innerTable = new PdfPTable(1);
                        while(itNorm.hasNext()){
                            NormativaBean normBean = (NormativaBean)itNorm.next();
                            PdfPCell pdcell = new PdfPCell(new Paragraph("- "+normBean.getNomeRiferimento()+" "+normBean.getTitoloRiferimento(), normalFont));
                            pdcell.cloneNonPositionParameters(getBordoInvisibile());
                            innerTable.addCell(pdcell);
                        }
                        table.addCell(innerTable);
                    }
                }
                document.add(table);
                aggiungiSpazi(document, 1);
            }
            // -------------------------------------------------------                
            dato = StringEscapeUtils.unescapeHtml(p.getProperty("mu.dichiarazioni.intPdf"));
            Paragraph proc = new Paragraph(new Paragraph(dato, normalFont));
            document.add(proc);
            
            aggiungiSpazi(document, 1);
            
            // Dichiara
            Paragraph dichiara = new Paragraph("DICHIARA", boldFontGrande);
            dichiara.setAlignment(Element.ALIGN_CENTER);
            document.add(dichiara);
            
            aggiungiSpazi(document, 1);
            
            // Dichiarazioni dinamiche
            Iterator itKey = dataFormperDestinatario.getSezioniCompilabiliBean().keySet().iterator();
            while(itKey.hasNext()){
                SezioneCompilabileBean sezComp = (SezioneCompilabileBean)dataFormperDestinatario.getSezioniCompilabiliBean().get(itKey.next());
                Object[][] datiHref = HtmlRenderer.getDatiHref(sezComp);
                if(datiHref.length>0){
                    // Titolo
                    table = new PdfPTable(1);
                    table.setWidthPercentage(100);
                    cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(sezComp.getTitolo()), boldFont));
                    cell.setGrayFill(0.75f);
                    table.addCell(cell);
                    document.add(table);
//                    table.setHeaderRows(1);
                    
                    // Dati
                    table = new PdfPTable(datiHref[0].length);
                    table.setWidthPercentage(100);
                    for(int i=0; i<datiHref.length; i++){
                        Object[] riga = datiHref[i];
                        int ultimaColonnaPiena = ultimaColonnaPiena(riga);
                        for(int n=0; n<riga.length; n++){
                            if(riga[n] != null){
                                String[] cella = (String[])riga[n];
                                if(cella[2].equalsIgnoreCase("I") || cella[2].equalsIgnoreCase("L") || cella[2].equalsIgnoreCase("T") || cella[3] != null){
                                    PdfPTable innerTable = new PdfPTable(cella[3] == null ? 2 : 1);
                                    
                                    PdfPCell pdcell = null;
                                    if(cella[3] == null){
                                        pdcell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(cella[0]==null?"":cella[0]), normalFont));
                                        if(n!=ultimaColonnaPiena)
                                            pdcell.cloneNonPositionParameters(getBordoInvisibile());
                                        pdcell.setGrayFill(0.90f);
                                        innerTable.addCell(pdcell);
                                    }   
                                    pdcell = new PdfPCell(new Paragraph(cella[1]==null?"":cella[1], boldFont));
                                    if(n!=ultimaColonnaPiena)
                                        pdcell.cloneNonPositionParameters(getBordoInvisibile());
                                    innerTable.addCell(pdcell);
                                    
                                    if(n==ultimaColonnaPiena){
                                        PdfPCell cellaConInnerTable = new PdfPCell(innerTable);
                                        cellaConInnerTable.setColspan(riga.length-ultimaColonnaPiena);
//                                        cellaConInnerTable.cloneNonPositionParameters(getBordoInvisibile());
                                        table.addCell(cellaConInnerTable);
                                        break;
                                    }
                                    else{
                                        table.addCell(innerTable);
                                    }
                                }
                                else{
                                    float[] widthsInner = {10f, 90f};
                                    PdfPTable innerTable = new PdfPTable(widthsInner);
                                    
                                    PdfPCell pdcell = null;
                                    if(cella[1] != null && !cella[1].equalsIgnoreCase("")){
                                        // Da cambiare per debug
                                        Image jpg = Image.getInstance(absPathToService
                                                                        + System.getProperty("file.separator")
                                                                        + System.getProperty("file.separator")
                                                                        + "risorse"
                                                                        + System.getProperty("file.separator")
                                                                        + "img"
                                                                        + System.getProperty("file.separator")
                                                                        + "check_flag.JPG");
                                        //Image jpg = Image.getInstance("e:\\temp\\check_flag.JPG");
                                        pdcell = new PdfPCell(jpg);
                                    }
                                    else{
//                                        Image jpg = Image.getInstance(absPathToService+"\\risorse\\img\\check.JPG");
                                        Image jpg = Image.getInstance(absPathToService
                                                                        + System.getProperty("file.separator")
                                                                        + "risorse"
                                                                        + System.getProperty("file.separator")
                                                                        + "img"
                                                                        + System.getProperty("file.separator")
                                                                        + "check.JPG");
                                        //Image jpg = Image.getInstance("e:\\temp\\check.JPG");
                                        pdcell = new PdfPCell(jpg);
                                    }
                                    if(n!=ultimaColonnaPiena)
                                        pdcell.cloneNonPositionParameters(getBordoInvisibile());
                                    innerTable.addCell(pdcell);
                                    
                                    pdcell = new PdfPCell(new Paragraph(cella[0]==null?"":(cella[0]), normalFont));
                                    if(n!=ultimaColonnaPiena)
                                        pdcell.cloneNonPositionParameters(getBordoInvisibile());
                                    innerTable.addCell(pdcell);
                                    
                                    if(n==ultimaColonnaPiena){
                                        PdfPCell cellaConInnerTable = new PdfPCell(innerTable);
                                        cellaConInnerTable.setColspan(riga.length-ultimaColonnaPiena);
                                        table.addCell(cellaConInnerTable);
                                        break;
                                    }
                                    else{
                                        table.addCell(innerTable);
                                    }
                                }
                            }
                            else{
                                cell = new PdfPCell(new Paragraph(" ", normalFont));
                                table.addCell(cell);
                            }
                        }
                    }
                    document.add(table);
                    
                    // Piede
                    if(sezComp.getPiedeHref() != null && !sezComp.getPiedeHref().equalsIgnoreCase("")){
                        table = new PdfPTable(1);
                        table.setWidthPercentage(100);
                        cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(sezComp.getPiedeHref()), boldFont));
//                        cell.cloneNonPositionParameters(getBordoInvisibile());
                        cell.setGrayFill(0.75f);
                        table.addCell(cell);
                        document.add(table);
                    }
                }
                aggiungiSpazi(document, 1);
            }
            
            // Dichiarazoni statiche
            Iterator it = dataFormperDestinatario.getDichiarazioniStatiche().iterator();
            while(it.hasNext()){
                DichiarazioniStaticheBean dicStat = (DichiarazioniStaticheBean)it.next();
                table = new PdfPTable(1);
                table.setWidthPercentage(100);
                cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(dicStat.getDescrizione()), boldFont));
                table.addCell(cell);
                document.add(table);
                aggiungiSpazi(document, 1);
            }
            
            
            // Allega
            Paragraph allega = new Paragraph("ALLEGA", boldFontGrande);
            allega.setAlignment(Element.ALIGN_CENTER);
            document.add(allega);
            
            aggiungiSpazi(document, 1);
            
            // Prima zona allegati
            /*table = new PdfPTable(1);
            table.setWidthPercentage(100);
            cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.allegati.int")), boldFont));
            cell.setGrayFill(0.75f);
            table.addCell(cell);
            document.add(table);*/
            
            float[] widths3 = {16f, 42f, 42f};
            table = new PdfPTable(widths3);
            table.setWidthPercentage(100);
            Iterator itProc2 = dataFormperDestinatario.getProcedimenti().iterator();
            boolean scriviTestata = true;
            while(itProc2.hasNext()){
                ProcedimentoBean procBean = (ProcedimentoBean)itProc2.next();
            
                if(scriviTestata){
                    cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.allegati.int.ente")), boldFont));
                    cell.setGrayFill(0.75f);
                    table.addCell(cell);
                    cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.allegati.int.procedimento")), boldFont));
                    cell.setGrayFill(0.75f);
                    table.addCell(cell);
                    cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.allegati.int.intervento")), boldFont));
                    cell.setGrayFill(0.75f);
                    table.addCell(cell);
                }
                scriviTestata = false;
                table.addCell(new PdfPCell(new Paragraph(procBean.getProcedimento().getEnte(), normalFont)));
                table.addCell(new PdfPCell(new Paragraph(procBean.getProcedimento().getNome(), normalFont)));
                Iterator itInt = procBean.getProcedimento().getInterventi().iterator();
                PdfPTable innerTable = new PdfPTable(1);
                while(itInt.hasNext()){
                    InterventoBean intBean = (InterventoBean)itInt.next();
                    PdfPCell pdcell = new PdfPCell(new Paragraph(intBean.getDescrizione(), normalFont));
                    pdcell.cloneNonPositionParameters(getBordoInvisibile());
                    innerTable.addCell(pdcell);
                }
                table.addCell(innerTable);
                
                cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.allegati.int.copie")), boldFont));
                cell.setGrayFill(0.75f);
                table.addCell(cell);
                cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.allegati.int.documento")), boldFont));
                cell.setColspan(2);
                cell.setGrayFill(0.75f);
                table.addCell(cell);
                
                Iterator itAll = procBean.getAllegati().iterator();
                while(itAll.hasNext()){
                    AllegatiBean allBean = (AllegatiBean)itAll.next();
                    // Modifica Init 13/04/2007 (1.3) - Tolte le righe con dicitura "Inclusa nella presente domanda"
                    if(allBean.getFlagDic() != null && allBean.getFlagDic().equalsIgnoreCase("S")){
                        //cell = new PdfPCell(new Paragraph("Inclusa nella presente domanda", boldFont));
                    }else{
                        cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(allBean.getCopie()), boldFont));
                        table.addCell(cell);
                        cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(allBean.getTitolo()), boldFont));
                        cell.setColspan(2);
                        table.addCell(cell);
                    }
                }
                cell = new PdfPCell(new Paragraph(" ", boldFont));
                cell.setColspan(3);
                table.addCell(cell);
            }
            document.add(table);
            
            aggiungiSpazi(document, 1);
            
            // Riepilogo allegati
            float[] widths4 = {85f, 15f};
            table = new PdfPTable(widths4);
            table.setWidthPercentage(100);
            
            cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.allegati.riepilogo")), boldFont));
            cell.setColspan(2);
            cell.setGrayFill(0.75f);
            table.addCell(cell);
            
            cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.allegati.int.documento")), boldFont));
            cell.setGrayFill(0.75f);
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("mu.allegati.int.copie")), boldFont));
            cell.setGrayFill(0.75f);
            table.addCell(cell);
            
            table.setHeaderRows(2);
            
            Iterator itAllSel = dataFormperDestinatario.getAllegatiSelezionati().iterator();
            while(itAllSel.hasNext()){
                AllegatiBean allBean = (AllegatiBean)itAllSel.next();
                if(!allBean.getCopie().equalsIgnoreCase("0")){
                    cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(allBean.getTitolo()), boldFont));
                    table.addCell(cell);
                    cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(allBean.getCopie()), boldFont));
                    table.addCell(cell);
                }
            }
            document.add(table);
            
            aggiungiSpazi(document, 1);
            
            // Dati associazione di categoria
            if(dataForm.getDatiAssociazioneDiCategoria().getCodiceControllo() != null && !dataForm.getDatiAssociazioneDiCategoria().getCodiceControllo().equalsIgnoreCase("")){
                table = new PdfPTable(1);
                table.setWidthPercentage(100);
                cell = new PdfPCell(new Paragraph("Dati associazione di categoria", boldFont));
                cell.setGrayFill(0.75f);
                table.addCell(cell);
                document.add(table);
                
                table = new PdfPTable(8);
                table.setWidthPercentage(100);
                
                cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("associazioneCategoria.denominazione")), normalFont));
                cell.setColspan(4);
                cell.setGrayFill(0.90f);
                table.addCell(cell);
                dato = dataForm.getDatiAssociazioneDiCategoria().getDenominazione();
                cell = new PdfPCell(new Paragraph(dato, boldFont));
                cell.setColspan(4);
                table.addCell(cell);
                cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("associazioneCategoria.codicesportello")), normalFont));
                cell.setColspan(2);
                cell.setGrayFill(0.90f);
                table.addCell(cell);
                dato = dataForm.getDatiAssociazioneDiCategoria().getCodiceSportello();
                cell = new PdfPCell(new Paragraph(dato, boldFont));
                cell.setColspan(2);
                table.addCell(cell);
                cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("associazioneCategoria.email")), normalFont));
                cell.setColspan(2);
                cell.setGrayFill(0.90f);
                table.addCell(cell);
                dato = dataForm.getDatiAssociazioneDiCategoria().getEmail();
                cell = new PdfPCell(new Paragraph(dato, boldFont));
                cell.setColspan(2);
                table.addCell(cell);
                document.add(table);
                
                aggiungiSpazi(document, 1);
            }
            
            // Data e firma
            table = new PdfPTable(1);
            table.setWidthPercentage(100);
            cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml("Data presentazione: ___/___/_______"), boldFont));
            cell.cloneNonPositionParameters(getBordoInvisibile());
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml("Il richiedente: _______________________"), boldFont));
            cell.cloneNonPositionParameters(getBordoInvisibile());
            table.addCell(cell);
            document.add(table);
            
            aggiungiSpazi(document, 1);
            
            if(p.getProperty("label.istruzioni.firmaPdf") != null && !p.getProperty("label.istruzioni.firmaPdf").equalsIgnoreCase("")){
                table = new PdfPTable(1);
                table.setWidthPercentage(100);
                cell = new PdfPCell(new Paragraph(StringEscapeUtils.unescapeHtml(p.getProperty("label.istruzioni.firmaPdf")), boldFont));
                cell.cloneNonPositionParameters(getBordoInvisibile());
                table.addCell(cell);
                document.add(table);
            }
            
            document.close();
            
            //apriPDF(m_pdfFile.getAbsolutePath());
	    }
	    catch(DocumentException de){
	        de.printStackTrace();
            logger.error("Errore su stampa PDF: "+de);
            gestioneEccezioni(process, de);
	    }
//	    catch(IOException ioe){
//	        ioe.printStackTrace();
//            logger.error("Errore su stampa PDF: "+ioe);
//            gestioneEccezioni(process, ioe);
//		} 
        catch (FactoryConfigurationError e) {
            e.printStackTrace();
            logger.error("Errore su stampa PDF: "+e);
            gestioneEccezioni(process, e.getException());
        }
        catch (Exception e){
            e.printStackTrace();
            logger.error("Errore su stampa PDF: "+e);
            gestioneEccezioni(process, e);
        }
        return baos.toByteArray();
    }
    
    
    private Rectangle getBordoInvisibile(){
        Rectangle bordoInvisibile = new Rectangle(0f, 0f);
        bordoInvisibile.setBorderWidthLeft(0f);
        bordoInvisibile.setBorderWidthBottom(0f);
        bordoInvisibile.setBorderWidthRight(0f);
        bordoInvisibile.setBorderWidthTop(0f);
        return bordoInvisibile;
    }
    
    private Document aggiungiSpazi(Document document, int numeroSpazi){
        try{
            for (int i = 0; i <= numeroSpazi; i++) {
                document.add(new Paragraph(" "));
            }
        }
        catch(DocumentException de){
            de.printStackTrace();
        }
        return document;
    }
    
    private static void apriPDF( String percorsoFile)
    {
        Process m_pViewer = null;
        try
        {
//            String path = "iexplore.exe";
        	String path = "C:\\Programmi\\Adobe\\Acrobat 6.0\\Reader\\AcroRd32.exe";
//            String path = "C:\\Program Files\\Adobe\\Acrobat 7.0\\Reader\\AcroRd32.exe";
        	m_pViewer = Runtime.getRuntime().exec("\"" + path + "\" \"" + percorsoFile + "\"");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    private int ultimaColonnaPiena(Object[] riga){
        int retVal = 0;
        boolean trovato = false;
        for(int i=0; i<riga.length; i++){
            if(riga[i]==null && !trovato){
                retVal=i-1;
                trovato = true;
            }
            else if(riga[i]!=null){
                retVal=i;
                trovato=false;
            }
        }
        return retVal>=0?retVal:0;
    }
}
