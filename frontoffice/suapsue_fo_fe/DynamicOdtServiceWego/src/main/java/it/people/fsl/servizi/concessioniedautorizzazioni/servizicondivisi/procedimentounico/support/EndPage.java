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

import com.itextpdf.text.Document;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

//import com.lowagie.text.Document;
//import com.lowagie.text.ExceptionConverter;
//import com.lowagie.text.Image;
//import com.lowagie.text.Rectangle;
//import com.lowagie.text.pdf.PdfPCell;
//import com.lowagie.text.pdf.PdfPTable;
//import com.lowagie.text.pdf.PdfPageEventHelper;
//import com.lowagie.text.pdf.PdfWriter;

public class EndPage extends PdfPageEventHelper {
    
    public void onEndPage(PdfWriter writer, Document document) {
        try {
//            Rectangle page = document.getPageSize();
//            PdfPTable head = new PdfPTable(1);
//            Image jpg = Image.getInstance("c:\\temp\\header.JPG");
//            PdfPCell pdcell = new PdfPCell(jpg);
//            pdcell.cloneNonPositionParameters(getBordoInvisibile());
//            head.addCell(pdcell);
//            head.setTotalWidth(page.width() - document.leftMargin() - document.rightMargin());
//            head.writeSelectedRows(0, -1, document.leftMargin(), page.height() - document.topMargin() + head.getTotalHeight(),
//                writer.getDirectContent());
            // Codice per eventuale piede
//            PdfPTable foot = new PdfPTable(1);
//            foot.addCell("Mettere piede");
//            foot.setTotalWidth(page.width() - document.leftMargin() - document.rightMargin());
//            foot.writeSelectedRows(0, -1, document.leftMargin(), document.bottomMargin(),
//                writer.getDirectContent());
        }
        catch (Exception e) {
            throw new ExceptionConverter(e);
        }
    }

    private Rectangle getBordoInvisibile(){
        Rectangle bordoInvisibile = new Rectangle(0f, 0f);
        bordoInvisibile.setBorderWidthLeft(0f);
        bordoInvisibile.setBorderWidthBottom(0f);
        bordoInvisibile.setBorderWidthRight(0f);
        bordoInvisibile.setBorderWidthTop(0f);
        return bordoInvisibile;
    }
}
