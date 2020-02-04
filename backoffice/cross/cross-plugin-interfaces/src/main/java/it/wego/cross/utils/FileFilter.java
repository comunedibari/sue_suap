package it.wego.cross.utils;

import java.io.File;
import java.io.FilenameFilter;

public class FileFilter implements FilenameFilter {

    private String[] allowedExtensions = null;

    public FileFilter(String[] allowedExtensions) {
        this.allowedExtensions = allowedExtensions.clone();
    }

    @Override
    public boolean accept(File dir, String name) {

        String fileExt = name.substring(name.lastIndexOf(".") + 1);
        for (String imgExt : allowedExtensions) {
            if (imgExt.compareToIgnoreCase(fileExt) == 0)
                return true;
        }
        return false;
    }
}