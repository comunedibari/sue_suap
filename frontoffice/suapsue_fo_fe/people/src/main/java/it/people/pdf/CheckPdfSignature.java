package it.people.pdf;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
//import java.nio.file.Files;
//LOR import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
/*
 * LOR
 import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
*/

import java.util.Collection;
import java.util.List;
//LOR import java.util.Optional;
import java.util.concurrent.Callable;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckPdfSignature {

    File pdfFile;

    Logger logApp=LoggerFactory.getLogger(CheckPdfSignature.class);
    public final Log logger = LogFactory.getLog(this.getClass());


    private  byte[] getByteRangeData(ByteArrayInputStream bis,int[] byteRange)    {
        int length1=byteRange[1]+byteRange[3];
        byte[] contentSigned=new byte[length1];
        bis.skip(byteRange[0]);
        bis.read(contentSigned, 0, byteRange[1]);
        bis.skip(byteRange[2]-byteRange[1]-byteRange[0]);
        bis.read(contentSigned, byteRange[1], byteRange[3]);
        bis.reset();
        return contentSigned;

    }

    public static ByteArrayInputStream retrieveByteArrayInputStream(File file) throws IOException {
        return new ByteArrayInputStream(FileUtils.readFileToByteArray(file));
    }
    
    public boolean verifyIsSigned(File pdfFile) throws Exception {
    	boolean isSigned = false;
    	String firma = "";
    	
        PDDocument pdfDoc=null;
        logger.info("PDF verifyIsSigned ENTRY POINT");
        System.out.println("PDF verifyIsSigned ENTRY POINT");

        try {
        	        	
        	File file = new File(pdfFile.getAbsolutePath());
        	ByteArrayInputStream pdfBytes = retrieveByteArrayInputStream(file);
        	
            logger.info("PDF verifyIsSigned:" +  pdfFile.getAbsolutePath());
            System.out.println("PDF verifyIsSigned:" +  pdfFile.getAbsolutePath());
            
            pdfDoc = PDDocument.load(pdfFile);
            
            logger.info("PDF verifyIsSigned PDDocument loaded");
            System.out.println("PDF verifyIsSigned PDDocument loaded");

            for (PDSignature a : pdfDoc.getSignatureDictionaries()) {
            	firma = myProcessSignature(a, pdfBytes); 
                if(firma != null && !firma.isEmpty()) {
                	isSigned = true;
                }
            }

            logger.info("PDF verified signature");
            System.out.println("PDF verified signature");

            pdfBytes.close();
            pdfDoc.close();
            return isSigned;
        }
        finally {
            logger.info("PDF verifyIsSigned EXIT");
            System.out.println("PDF verifyIsSigned EXIT");
        }
    }
     
    private String myProcessSignature(PDSignature signature,
            ByteArrayInputStream pdfBytes) throws Exception {

    	String result = "";
    	
    	byte[] contentToSigned=getByteRangeData(pdfBytes, signature.getByteRange());
        String filter=signature.getFilter();
        String subFilter=signature.getSubFilter();
/* 
        String contactInfo=Optional.ofNullable(signature.getContactInfo()).orElse("N/A");
        String reason=Optional.ofNullable(signature.getReason()).orElse("N/A");
*/        
        System.out.println("FIRMA documento:" + signature.getName());

        logApp.info("Signature {} Filter:{}",signature.getName(),filter);
        logApp.info("Signature {} SubFilter:{}",signature.getName(),subFilter);
/*       
 * 		logApp.info("Signature {} ContactInfo:{}",signature.getName(),contactInfo);
        logApp.info("Signature {} Reason:{}",signature.getName(),reason);
*/
        result = signature.getName();

        return result;
    }
    
    public static void main(String[] args)  {
    	
    	
    	String filename = "C:/SUESUAP/GIT/area-vasta/frontoffice/suapsue_fo_fe/people/documenti_firmati/prova.pdf";
		CheckPdfSignature pdfcheck = new CheckPdfSignature();
		try {
			boolean tmp1 = false;
			tmp1 = pdfcheck.verifyIsSigned(new File(filename));
			if(!tmp1) {System.out.println("DOCUMENTO non firmato");}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

               
    }

}
