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

///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//
//package utils;
//
////import com.itextpdf.text.Document;
////import com.itextpdf.text.DocumentException;
////import com.itextpdf.text.pdf.AcroFields;
////import com.itextpdf.text.pdf.BaseFont;
////import com.itextpdf.text.pdf.ICC_Profile;
////import com.itextpdf.text.pdf.PRAcroForm;
////import com.itextpdf.text.pdf.PdfArray;
////import com.itextpdf.text.pdf.PdfContentByte;
////import com.itextpdf.text.pdf.PdfCopy;
////import com.itextpdf.text.pdf.PdfDictionary;
////import com.itextpdf.text.pdf.PdfICCBased;
////import com.itextpdf.text.pdf.PdfImportedPage;
////import com.itextpdf.text.pdf.PdfName;
////import com.itextpdf.text.pdf.PdfReader;
////import com.itextpdf.text.pdf.PdfString;
////import com.itextpdf.text.pdf.PdfWriter;
////import com.itextpdf.text.pdf.SimpleBookmark;
////import java.io.FileInputStream;
//
//import com.lowagie.text.Document;
//import com.lowagie.text.DocumentException;
//import com.lowagie.text.pdf.AcroFields;
//import com.lowagie.text.pdf.BaseFont;
//import com.lowagie.text.pdf.PRAcroForm;
//import com.lowagie.text.pdf.PdfArray;
//import com.lowagie.text.pdf.PdfContentByte;
//import com.lowagie.text.pdf.PdfCopy;
//import com.lowagie.text.pdf.PdfDictionary;
//import com.lowagie.text.pdf.PdfImportedPage;
//import com.lowagie.text.pdf.PdfName;
//import com.lowagie.text.pdf.PdfReader;
//import com.lowagie.text.pdf.PdfString;
//import com.lowagie.text.pdf.PdfWriter;
//import com.lowagie.text.pdf.SimpleBookmark;
//
//
//
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//public class PdfMerger {
//private BaseFont baseFont;
//private boolean enablePagination = false;
//private List<PdfReader> documents;
//private int totalPages;
//
//    public PdfMerger() {
//        documents = new ArrayList<PdfReader>();
//    }
//
//    public BaseFont getBaseFont() {
//        return baseFont;
//    }
//
//    public void setBaseFont(BaseFont baseFont) {
//        this.baseFont = baseFont;
//    }
//
//    public boolean getEnablePagination() {
//        return enablePagination;
//    }
//
//    public void setEnablePagination(boolean enablePagination) {
//        this.enablePagination = enablePagination;
//        if (enablePagination && baseFont == null)
//        try {
//            baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
//        } catch (DocumentException ex) {
//            Logger.getLogger(PdfMerger.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(PdfMerger.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    public List<PdfReader> getDocuments() {
//        return documents;
//    }
//
//    public void addDocument(String filename) {
//        try {
//            documents.add(new PdfReader(filename));
//        } catch (IOException ex) {
//            Logger.getLogger(PdfMerger.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    public void addDocument(InputStream pdfStream) {
//        try {
//            documents.add(new PdfReader(pdfStream));
//        } catch (IOException ex) {
//            Logger.getLogger(PdfMerger.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    public void AddDocument(byte[] pdfContents) {
//        try {
//            documents.add(new PdfReader(pdfContents));
//        } catch (IOException ex) {
//            Logger.getLogger(PdfMerger.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    public void addDocument(PdfReader pdfDocument) {
//        documents.add(pdfDocument);
//    }
//
//    public void merge(String outputFilename) throws IOException {
//        merge(new FileOutputStream(outputFilename));
//    }
//
//    public void merge(OutputStream outputStream) throws IOException {
//
//        Document newDocument = null;
//        ArrayList master = new ArrayList();
//        PdfCopy pdfWriter = null;
//        int pageOffset = 0;
//        try {
//            newDocument = new Document();
//            pdfWriter = new PdfCopy(newDocument, outputStream);
//            pdfWriter.setPDFXConformance(PdfWriter.PDFA1A);
//            newDocument.open();
//
//
//
//
//
//            PdfContentByte pdfContentByte = pdfWriter.getDirectContent();
//
//            if (enablePagination) {
//                for (PdfReader doc : documents ) {
//                    totalPages += doc.getNumberOfPages();
//                }
//            }
//
//            int currentPage = 1;
//            for (PdfReader pdfReader : documents) {
//                List bookmarks = SimpleBookmark.getBookmark(pdfReader);
//                 if (bookmarks != null) {
//                     if (pageOffset != 0)
//                         SimpleBookmark.shiftPageNumbers(bookmarks, pageOffset, null);
//                     master.addAll(bookmarks);
//                 }
//                 for (int page = 1; page <= pdfReader.getNumberOfPages(); page++) {
//                    newDocument.newPage();
//                    PdfImportedPage importedPage = pdfWriter.getImportedPage(pdfReader, page);
//                    pdfWriter.addPage(importedPage);
//                    pdfContentByte.addTemplate(importedPage, 0, 0);
//
//                    if (enablePagination) {
//                        pdfContentByte.beginText();
//                        pdfContentByte.setFontAndSize(baseFont, 9);
//                        pdfContentByte.showTextAligned(PdfContentByte.ALIGN_CENTER, String.format("{0} de {1}", currentPage++, totalPages), 520, 5, 0);
//                        pdfContentByte.endText();
//                    }
//                }
//                PRAcroForm form = pdfReader.getAcroForm();
//                if (form != null) {
//                    System.out.println("doc: " + pdfReader.getNumberOfPages() + "nellìif1" + form.toString());
//                    pdfWriter.copyAcroForm(pdfReader);
//                }
//                AcroFields field = pdfReader.getAcroFields();
//                if (field != null) {
//                    System.out.println("doc: " + pdfReader.getNumberOfPages() + "nellìif2" + field.getSignatureNames());
//
////                    pdfWriter.pdfReader);
//                }
//            }
//        }
//        catch (DocumentException dex) {
//            Logger.getLogger(PdfMerger.class.getName()).log(Level.SEVERE, null, dex);
//        }
//
//        finally {
//            outputStream.flush();
//            if (newDocument != null)
//                if (!master.isEmpty())
//                    pdfWriter.setOutlines(master);
//
//                // rende il documento PDF/A-1
////                PdfDictionary outi = new PdfDictionary(PdfName.OUTPUTINTENT);
////                outi.put(PdfName.OUTPUTCONDITIONIDENTIFIER, new PdfString("sRGB IEC61966-2.1"));
////                outi.put(PdfName.INFO, new PdfString("sRGB IEC61966-2.1"));
////                outi.put(PdfName.S, new PdfName("GTS_PDFA1"));
////                ICC_Profile icc = ICC_Profile.getInstance(new FileInputStream("modelli/srgb.profile"));
////                PdfICCBased ib = new PdfICCBased(icc);
////                ib.remove(PdfName.ALTERNATE);
////                outi.put(PdfName.DESTOUTPUTPROFILE, pdfWriter.addToBody(ib).getIndirectReference());
////                pdfWriter.getExtraCatalog().put(PdfName.OUTPUTINTENTS, new PdfArray(outi));
//                pdfWriter.createXmpMetadata();
//
//                newDocument.close();
//            outputStream.close();
//        }
//    }
//}
