/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.utils;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author GabrieleM
 */
@JsonIgnoreProperties({"file"})
public class MultiPartFileTempPath {
    MultipartFile file;
    String size;
    String name;
    String tempPath;
    String type;
    String typeCode;

    
    
    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public MultiPartFileTempPath() {
    }

    public MultipartFile getFile() {
        return file;
    }

    public String getTempPath() {
        return tempPath;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public void setTempPath(String tempPath) {
        this.tempPath = tempPath;
    }

    public String getSize() {
        return size;
    }

    public String getName() {
        return name;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
}
