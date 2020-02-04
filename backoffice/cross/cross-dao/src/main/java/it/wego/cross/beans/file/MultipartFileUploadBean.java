/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.beans.file;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author giuseppe
 */
public class MultipartFileUploadBean {

    private List<MultipartFile> files;

    public void setFiles(List<MultipartFile> files) {
        this.files = files;
    }

    public List<MultipartFile> getFiles() {
        return files;
    }
}
