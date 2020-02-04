/**
 * 
 */
package it.wego.dynamicodt.transformation.util;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

import com.itextpdf.text.pdf.ICC_Profile;
import com.itextpdf.text.pdf.PdfArray;
import com.itextpdf.text.pdf.PdfBoolean;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfCopyFields;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfICCBased;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfString;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * @author Riccardo Forafò - Engineering Ingegneria Informatica - Genova
 * 18/nov/2012 23:37:08
 */
public class PdfUtils {

	/**
	 * 
	 */
	public PdfUtils() {
		
	}
	
	/**
	 * @param pdfIn
	 * @return
	 */
	public byte[] convertToPDFAFormatAndProtect(byte[] pdfIn) {
		
		byte[] result = pdfIn;
		
		String password = UUID.randomUUID().toString();
		
        try {

	        ByteArrayOutputStream pdfOut = new ByteArrayOutputStream();
	        PdfReader reader = new PdfReader(pdfIn);
	
	        PdfCopyFields copy = new PdfCopyFields(pdfOut);
	        PdfWriter writer = copy.getWriter();
	        writer.setPDFXConformance(PdfCopy.PDFA1A);
	        writer.setEncryption(null, password.getBytes(),
	                PdfWriter.ALLOW_PRINTING | PdfWriter.ALLOW_COPY, PdfWriter.ENCRYPTION_AES_128);

	        copy.addDocument(reader);
	
	        PdfDictionary outi = new PdfDictionary(PdfName.OUTPUTINTENT);
	        outi.put(PdfName.OUTPUTCONDITIONIDENTIFIER, new PdfString("sRGB IEC61966-2.1"));
	        outi.put(PdfName.INFO, new PdfString("sRGB IEC61966-2.1"));
	        outi.put(PdfName.S, new PdfName("GTS_PDFA1"));
	        
	        ICC_Profile icc = ICC_Profile.getInstance(this.getClass().getResourceAsStream("/resources/srgb.profile"));
	        PdfICCBased ib = new PdfICCBased(icc);
	        ib.remove(PdfName.ALTERNATE);
	        outi.put(PdfName.DESTOUTPUTPROFILE, writer.addToBody(ib).getIndirectReference());
	        writer.getExtraCatalog().put(PdfName.OUTPUTINTENTS, new PdfArray(outi));
	
	        PdfDictionary structureTreeRoot = new PdfDictionary();
	        structureTreeRoot.put(PdfName.TYPE, PdfName.STRUCTTREEROOT);
	        writer.getExtraCatalog().put(PdfName.STRUCTTREEROOT, structureTreeRoot);

	        PdfDictionary markInfo = new PdfDictionary();
	        markInfo.put(PdfName.MARKED, new PdfBoolean(true));
	        writer.getExtraCatalog().put(PdfName.MARKINFO, markInfo);
	
	        writer.createXmpMetadata();
	        writer.close();
	        copy.close();
	        reader.close();
	        
	        result = pdfOut.toByteArray();
	        
        } catch(Exception ignore) {
        	
        }
		
        return result;
        
	}
	
}
