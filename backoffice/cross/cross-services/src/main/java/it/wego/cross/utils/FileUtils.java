/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.utils;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import it.wego.cross.constants.FileTypes;
import it.wego.cross.dto.AllegatoDTO;
import it.wego.cross.service.AllegatiService;
import it.wego.cross.service.ConfigurationService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.apache.tika.Tika;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSSignedData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 *
 * @author Gabriele
 */
@Service
public class FileUtils {

    public static final List<String> P7M_MIMETYPES = Lists.newArrayList("APPLICATION/PKCS7-SIGNATURE", "APPLICATION/PKCS7-MIME");

    /**
     * Dato un file: se il file e' firmato restituisce il file in chiaro se il
     * file non e' firmato restituisce il file stesso
     *
     */
    @Autowired
    AllegatiService allegatiservice;
    @Autowired
    private ConfigurationService configurationService;

    public static byte[] getUnsignedFile(File file) throws Exception {
        byte[] ret = null;
        byte[] fis;
        InputStream is = new FileInputStream(file.getPath());
        if (file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length()).equalsIgnoreCase("p7m")) {
            fis = IOUtils.toByteArray(is);
            ret = getSigned(fis);
        } else {
            throw new Exception("Il file non ha estensione P7M");
        }
        return ret;
    }

    public static byte[] getSigned(byte[] file) throws CMSException {
        byte[] ret = null;
        CMSSignedData cms = new CMSSignedData(file);
        if (cms.getSignedContent() != null) {
            ret = (byte[]) cms.getSignedContent().getContent();
        }
        return ret;
    }

    public static Boolean isSigned(MultipartFile file) throws IOException {
        if (file == null) {
            return false;
        }
        Tika tika = new Tika();
        String fileType = tika.detect(file.getInputStream(), file.getOriginalFilename());
//        return P7M_MIMETYPES.contains(fileType.toUpperCase()) || file.getName().toLowerCase().endsWith("p7m");
        return P7M_MIMETYPES.contains(fileType.toUpperCase());
    }

    public static byte[] getFileContent(File file) throws Exception {
        InputStream is = new FileInputStream(file.getPath());
        byte[] ret = IOUtils.toByteArray(is);
        return ret;
    }

    public static boolean isEmpty(AllegatoDTO allegato) {
        return Utils.e(allegato.getDescrizione()) && (allegato.getFile() == null || allegato.getFile().getSize() == 0);
    }
    //INSERITO DA GAB MON
    //*****************************************************************************************************************

    public LinkedList<MultiPartFileTempPath> saveTempCopy(Integer maxNumberOfFiles, MultipartHttpServletRequest request, Integer id_ente, Integer id_comune) throws Exception {
        MultiPartFileTempPath file = new MultiPartFileTempPath();
        LinkedList<MultiPartFileTempPath> files = new LinkedList<MultiPartFileTempPath>();
        Iterator<String> itr = request.getFileNames();
        MultipartFile mpf;
        //2. get each file
        while (itr.hasNext()) {

            //2.1 get next MultipartFile
            mpf = request.getFile(itr.next());
            file.setFile(mpf);
//            File temp = new File(path, mpf.getOriginalFilename());
            File temp = File.createTempFile("cross_temp_file_", null);
            temp.deleteOnExit();
            String temppath = temp.getCanonicalPath();
            file.setTempPath(allegatiservice.putFile(temppath));
            file.setName(mpf.getOriginalFilename());
            file.setType(mpf.getContentType());
            files.add(file);
            // copy file to local disk (make sure the path "e.g. D:/temp/files" exists)            
            FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(temppath));
            //2.2 if files > 10 remove the first from the list
            if (files.size() > maxNumberOfFiles) {
                files.pop();
            }
        }
        // result will be like this
        // [{"fileName":"app_engine-85x77.png","fileSize":"8 Kb","fileType":"image/png"},...]
        for (MultiPartFileTempPath allegato : files) {
            String code = FileTypes.myMap.get(allegato.getType());
            allegato.setTypeCode(code);
            allegato.setType(allegato.getFile().getContentType());
            if (code == null) {
                allegato.setTypeCode(FileTypes.myMap.get("default"));
            }
        }
        return files;
    }

    public MultipartFile getMultipartFile(String name, String pathcode, String fieldname, String type, Boolean isFormField, Integer sizeThereshold) throws IOException {
        String input = allegatiservice.getFile(pathcode);
        InputStream inputStream = new FileInputStream(input);

        int availableBytes = inputStream.available();
        // Write the inputStream to a FileItem
        File outFile = new File(input); // This is your tmp file, the code stores the file here in order to avoid storing it in memory
        FileItem fileitem = new DiskFileItem(fieldname, type, false, name, availableBytes, outFile); // You link FileItem to the tmp outFile 
        OutputStream outputStream = fileitem.getOutputStream(); // Last step is to get FileItem's output stream, and write your inputStream in it. This is the way to write to your FileItem. 
        int read;
        byte[] bytes = new byte[1024];
        while ((read = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, read);
        }
        // Don't forget to release all the resources when you're done with them, or you may encounter memory/resource leaks.
        inputStream.close();
        outputStream.flush(); // This actually causes the bytes to be written.
        outputStream.close();
        MultipartFile multipart = new CommonsMultipartFile(fileitem);
        return multipart;
    }
//    public void cleanTempDirectory(Integer id_ente, Integer id_comune) throws IOException {
//        String path = configurationService.getCachedConfiguration(TEMP_PATH_PROP, id_ente, id_comune);
//        File directory = new File(path);
//        allegatiservice.deleteFileMap();
//        org.apache.commons.io.FileUtils.cleanDirectory(directory);
//    }
    //*****************************************************************************************************************
}
