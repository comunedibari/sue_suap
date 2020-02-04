/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.utils;

import com.google.common.collect.Maps;
import com.itextpdf.text.pdf.PRStream;
import com.itextpdf.text.pdf.PdfArray;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfString;
import it.wego.cross.exception.CrossException;
import java.util.Map;
import org.bouncycastle.cms.CMSSignedData;

/**
 *
 * @author aleph
 */
public class PdfAttachmentUtils {

    public static byte[] getPdfFromP7m(byte[] p7mFileData) throws CrossException {
        try {
            CMSSignedData cms = new CMSSignedData(p7mFileData);
            return (byte[]) cms.getSignedContent().getContent();
        } catch (Exception e) {
            return p7mFileData;
        }
    }

    public static Map<String, byte[]> getPdfAttachmentsFromPdfFileData(byte[] pdfData) throws Exception {
        try {
            Map<String, byte[]> map = Maps.newLinkedHashMap();
            PdfReader reader = new PdfReader(pdfData);
            PdfDictionary root = reader.getCatalog();
            PdfDictionary names = root.getAsDict(PdfName.NAMES);
            PdfDictionary embedded = names.getAsDict(PdfName.EMBEDDEDFILES);
            PdfArray filespecs = embedded.getAsArray(PdfName.NAMES);
            for (int i = 0; i < filespecs.size();) {
                PdfString name = filespecs.getAsString(i++);
                PdfDictionary filespec = filespecs.getAsDict(i++);
                PdfDictionary refs = filespec.getAsDict(PdfName.EF);
                for (PdfName key : refs.getKeys()) {
                    PRStream stream = (PRStream) PdfReader.getPdfObject(refs.getAsIndirectObject(key));
                    String attachmentFileName = filespec.getAsString(key).toString();
                    byte[] attachmentFileData = PdfReader.getStreamBytes(stream);
                    map.put(attachmentFileName, attachmentFileData);
                }
            }
            return map;
        } catch (Throwable e) {
            Log.APP.error("",e);
            throw new CrossException("Impossibile estrarre degli allegati dal file caricato.");
        }
    }

    public static Map<String, byte[]> getPdfAttachmentsFromP7mFileData(byte[] p7mData) throws Exception {
        return getPdfAttachmentsFromPdfFileData(getPdfFromP7m(p7mData));
    }

}
