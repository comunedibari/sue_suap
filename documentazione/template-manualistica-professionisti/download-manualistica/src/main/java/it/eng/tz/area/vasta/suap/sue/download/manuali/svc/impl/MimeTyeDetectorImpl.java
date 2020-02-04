package it.eng.tz.area.vasta.suap.sue.download.manuali.svc.impl;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;

import javax.annotation.PostConstruct;

import org.apache.tika.Tika;
import org.apache.tika.detect.Detector;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import it.eng.tz.area.vasta.suap.sue.download.manuali.svc.IMimeTypeDetectorSvc;

@Service
public class MimeTyeDetectorImpl implements IMimeTypeDetectorSvc
{
	private Detector detector;
	private Tika tika;
	private static final Logger logger = LoggerFactory.getLogger(MimeTyeDetectorImpl.class.getName());
	@Override
	public String getMimeType(String fileName) throws Exception
	{
		if( logger.isDebugEnabled() )
		{
			logger.debug("Cerco il mimetype per il file con nome [{}]",fileName);
		}
		return tika.detect(fileName);
	}

	@Override
	public String getMimeType(String fileName, byte[] fileContent) throws Exception
	{
		if( logger.isDebugEnabled() )
		{
			logger.debug("Cerco il mimetype per il file con nome [{}]",fileName);
		}
		
		return tika.detect(fileContent, fileName);
	}

	@Override
	public String getMimeType(String fileName, InputStream fileContent) throws Exception
	{
		if( logger.isDebugEnabled() )
		{
			logger.debug("Cerco il mimetype per il file con nome [{}]",fileName);
		}
		InputStream bis = new BufferedInputStream(fileContent);
		Metadata md = new Metadata();
		md.add(Metadata.RESOURCE_NAME_KEY, fileName);
		return detector.detect(bis, md).toString();
	}

	@Override
	public String getMimeType(String fileName, File file) throws Exception
	{
		if( logger.isDebugEnabled() )
		{
			logger.debug("Cerco il mimetype per il file con nome [{}]",fileName);
		}
		return null;
	}
	@PostConstruct
	public void init()
	{
		AutoDetectParser parser = new AutoDetectParser();
		detector = parser.getDetector();
		tika = new Tika();
	}
}