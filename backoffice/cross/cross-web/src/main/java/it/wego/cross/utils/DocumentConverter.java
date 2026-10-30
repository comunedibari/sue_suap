package it.wego.cross.utils;


import java.io.File;

import org.springframework.stereotype.Service;

@Service
public interface DocumentConverter {

	public File convert(File inputFile,File outputFile) throws Exception;
	
	public File convert(byte[] inputFile,File outputFile) throws Exception;
}