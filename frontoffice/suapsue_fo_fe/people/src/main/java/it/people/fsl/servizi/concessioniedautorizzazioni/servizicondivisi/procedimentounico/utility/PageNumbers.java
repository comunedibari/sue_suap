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
package it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility;

import com.lowagie.text.Document;
import com.lowagie.text.ExceptionConverter;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;

/**
 * Making a document with a footer containing 'Page x of y'
 * 
 * @author Mario Casola
 */
public class PageNumbers extends PdfPageEventHelper {

	/** A template that will hold the total number of pages. */
	public PdfTemplate tpl;

	/** The font that will be used. */
	public BaseFont helv;

	/**
	 * The onOpenDocument method is called just after the document is opened and
	 * after the first page has been initialized. It is used to initialize some
	 * parameters.
	 * 
	 * @param writer
	 * @param document
	 * @see com.lowagie.text.pdf.PdfPageEventHelper#onOpenDocument(com.lowagie.text.pdf.PdfWriter,
	 *      com.lowagie.text.Document)
	 */
	public void onOpenDocument(PdfWriter writer, Document document) {
		try {
			// initialization of the template
			tpl = writer.getDirectContent().createTemplate(100, 100);
			// initialization of the font
			helv = BaseFont.createFont("Helvetica", BaseFont.WINANSI, false);
		} catch (Exception e) {
			throw new ExceptionConverter(e);
		}
	}

	/**
	 * The onEndPage method is called just before a new page is started and
	 * initialized. It is used to add footer.
	 * 
	 * @param writer
	 * @param document
	 * @see com.lowagie.text.pdf.PdfPageEventHelper#onEndPage(com.lowagie.text.pdf.PdfWriter,
	 *      com.lowagie.text.Document)
	 */
	public void onEndPage(PdfWriter writer, Document document) {
		PdfContentByte cb = writer.getDirectContent();
		cb.saveState();
		// compose the footer
		String text = "Pagina " + writer.getPageNumber() + " di ";
		float textSize = helv.getWidthPoint(text, 10);
		float textBase = document.bottom() - 20;
		cb.beginText();
		cb.setFontAndSize(helv, 10);
		//
		cb.setTextMatrix((document.getPageSize().width() / 2)
				- (textSize / 2), textBase);
		cb.showText(text);
		cb.endText();
		cb.addTemplate(tpl, (document.getPageSize().width() / 2)
				+ (textSize / 2), textBase);
		//
		cb.saveState();
		cb.restoreState();
	}

	/**
	 * The onCloseDocument method is called just before all streams are closed.
	 * It is used to complete the template containing the total number of pages.
	 * 
	 * @param writer
	 * @param document
	 * @see com.lowagie.text.pdf.PdfPageEventHelper#onCloseDocument(com.lowagie.text.pdf.PdfWriter,
	 *      com.lowagie.text.Document)
	 */
	public void onCloseDocument(PdfWriter writer, Document document) {
		tpl.beginText();
		tpl.setFontAndSize(helv, 10);
		tpl.setTextMatrix(0, 0);
		tpl.showText("" + (writer.getPageNumber() - 1));
		tpl.endText();
	}
}
