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
 * http://joinup.ec.europa.eu/software/page/eupl
 * 
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * 
 * This product includes software developed by Yale University
 * 
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
**/
package tools;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfAnnotation;
import com.itextpdf.text.pdf.PdfFormField;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfString;
import com.itextpdf.text.pdf.PdfTemplate;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/** Utility per inserire un campo firma in un file pdf
 *
 * @author Giuseppe
 */
public class PdfSignField {

private String page;
private float width;
private float heigth;
private float paddingWidth;
private float paddingHeigth;
private String signatureName;
//private boolean hiddenSignature;


    /** Crea un oggetto "PdfSignField" che rappresenta il campo firma da inserire
     *
     * @param page pagina in cui il campo firam andrà inserito
     * @param width larghezza del campo firma
     * @param heigth altezza del campo firma
     * @param paddingWidth distanza dal margine destro
     * @param paddingHeigth distanza dal margine superiore
     * @param signatureName nome del campo firma
     */
    public PdfSignField(String page, float width, float heigth, float paddingWidth, float paddingHeigth, String signatureName) {
        this.page = page;
        this.width = width;
        this.heigth = heigth;
        this.paddingWidth = paddingWidth;
        this.paddingHeigth = paddingHeigth;
        this.signatureName = signatureName;
//        this.hiddenSignature = hidden;
    }

    /** Ritorna l'altezza del campo firma
     *
     * @return altezza del campo firma
     */
    public float getHeigth() {
        return heigth;
    }

    /** Imposta l'altezza del campo firma
     *
     * @param heigth altezza del campo firma
     */
    public void setHeigth(float heigth) {
        this.heigth = heigth;
    }

    /** Ritorna la distanza dal margine superiore del campo firma
     *
     * @return distanza dal margine superiore del campo firma
     */
    public float getPaddingHeigth() {
        return paddingHeigth;
    }

    /** Imposta la distanza dal margine superiore del campo firma
     *
     * @param paddingHeigth distanza dal margine superiore del campo firma
     */
    public void setPaddingHeigth(float paddingHeigth) {
        this.paddingHeigth = paddingHeigth;
    }

    /** Ritorna la distanza dal margine destro del campo firma
     *
     * @return distanza dal margine destro del campo firma
     */
    public float getPaddingWidth() {
        return paddingWidth;
    }

    /** Imposta la distanza dal margine destro del campo firma
     *
     * @param paddingWidth distanza dal margine destro del campo firma
     */
    public void setPaddingWidth(float paddingWidth) {
        this.paddingWidth = paddingWidth;
    }

    /** Ritorna la larghezza del campo firma
     *
     * @return larghezza del campo firma
     */
    public float getWidth() {
        return width;
    }

    /** Imposta la larghezza del campo firma
     *
     * @param width larghezza del campo firma
     */
    public void setWidth(float width) {
        this.width = width;
    }

    /** Imposta larghezza, altezza, distanza dal margine destro, distanza dal margine superiore del campo firma
     *
     * @param width larghezza del campo firma
     * @param heigth altezza del campo firma
     * @param paddingWidth distanza dal margine destro del campo firma
     * @param paddingHeigth distanza dal margine superiore del campo firma
     */
    public void setCoord(float width, float heigth, float paddingWidth, float paddingHeigth) {
        this.width = width;
        this.heigth = heigth;
        this.paddingWidth = paddingWidth;
        this.paddingHeigth = paddingHeigth;
    }

    /** Ritorna la pagina in cui il campo firma andrà inserito
     *
     * @return la pagina in cui il campo firma andrà inserito
     */
    public String getPage() {
        return page;
    }

    /** Imposta la pagina in cui il campo firma andrà inserito
     *
     * @param page la pagina in cui il campo firma andrà inserito
     */
    public void setPage(String page) {
        this.page = page;
    }

     /** Ritorna il nome del campo firma
     *
     * @return il nome del campo firma
     */
    public String getSignatureName() {
        return signatureName;
    }

    /** Imposta il nome del campo firma
     *
     * @param signatureName il nome del campo firma
     */
    public void setSignatureName(String signatureName) {
        this.signatureName = signatureName;
    }

    /** Inserisce il campo firma in un file pdf
     *
     * @param pdfFile il file pdf in cui inserire il campo firma
     * @throws IOException
     * @throws DocumentException
     */
    public void insertIntoPdf(File pdfFile) throws IOException, DocumentException {
        File tempFile = null;
        FileOutputStream tempOutStream = null;

        try {
            String pdfFileName = pdfFile.getAbsolutePath();
            PdfReader pdf = new PdfReader(pdfFileName);
            tempFile = new File(pdfFileName.substring(0, pdfFileName.lastIndexOf(File.separator)) + "/_pdfSignFieldtemp_" + pdfFile.getName());
            tempOutStream = new FileOutputStream(tempFile.getAbsolutePath());
            PdfStamper stp = new PdfStamper(pdf, tempOutStream);

            PdfFormField sig = PdfFormField.createSignature(stp.getWriter());

            int intpage = 0;
            if (page.equalsIgnoreCase("n"))
                intpage = pdf.getNumberOfPages();
            else {
                try {
                    intpage = Integer.parseInt(page);
                }
                catch (NumberFormatException ex) {
                    throw new NumberFormatException("Numero di pagina errato.");
                }
            }

            float pageWidth = pdf.getPageSize(intpage).getWidth();
            float pageHeight = pdf.getPageSize(intpage).getHeight();

            // la pagina è orientata orizzontalmente quindi inverto pageWidth con pageHeight
            if (pdf.getPageRotation(intpage) % 180 == 90) {
                float swap = pageHeight;
                pageHeight = pageWidth;
                pageWidth = swap;
            }

            float llx = pageWidth - width - paddingWidth;
            float lly = pageHeight - heigth - paddingHeigth;
            float urx = llx + width;
            float ury = lly + heigth;

            sig.setWidget(new Rectangle(llx, lly, urx, ury), null);
//            if (hiddenSignature)
//                sig.setFlags(PdfAnnotation.FLAGS_HIDDEN);

            sig.setAppearance(PdfAnnotation.APPEARANCE_NORMAL, "", PdfTemplate.createTemplate(stp.getWriter(), 0, 0));
            sig.setFlags(PdfAnnotation.FLAGS_PRINT);

            sig.put(PdfName.DA, new PdfString("/Arial 0 Tf 0 g"));

            sig.setFieldName(signatureName);

//            sig.setPage(intpage);
//            stp.addAnnotation(sig, intpage);
            stp.addAnnotation(sig, intpage);
            pdf.close();
            stp.close();

            pdfFile.delete();
            File pdfWithField = new File(pdfFileName.substring(0, pdfFileName.lastIndexOf(File.separator)) + "/_pdfSignFieldtemp_" + pdfFile.getName());
            pdfWithField.renameTo(new File(pdfFileName));
        }
        finally {
            tempOutStream.close();
            tempFile.delete();
        }
    }

    public String toString() {
        return "Signature Name: " + signatureName  + "; page: " + page + "; coords: " + width + ";" + heigth + ";" + paddingWidth + ";" + paddingHeigth;
//        return "Signature Name: " + signatureName + (hiddenSignature ? "hidden" : "visible") + "page: " + page + "; coords: " + coord.getLeft() + ";" + coord.getBottom() + ";" + coord.getRight() + ";" + coord.getTop();
    }
}
