package it.wego.cross.utils;


import java.io.ByteArrayInputStream;
import java.io.File;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.jodconverter.core.office.OfficeException;
import org.jodconverter.core.office.OfficeUtils;
import org.jodconverter.local.JodConverter;
import org.jodconverter.local.office.LocalOfficeManager;
import org.springframework.stereotype.Service;

@Service
public class JODConverter implements DocumentConverter {

	final LocalOfficeManager officeManager = LocalOfficeManager.install();
	
	@Override
    public File convert(File inputFile,File outputFile) throws OfficeException {
        JodConverter
        .convert(inputFile)
        .to(outputFile)
        .execute();
        return outputFile;
    }

	@Override
    public File convert(byte[] inputFile,File outputFile) throws OfficeException {
    	JodConverter
    	.convert(new ByteArrayInputStream(inputFile))
    	.to(outputFile)
    	.execute();
    	return outputFile;
    }

    @PostConstruct
    public void start() throws OfficeException {
          officeManager.start();
    }

    @PreDestroy
    public void stop() {
    	OfficeUtils.stopQuietly(officeManager);
    }

}