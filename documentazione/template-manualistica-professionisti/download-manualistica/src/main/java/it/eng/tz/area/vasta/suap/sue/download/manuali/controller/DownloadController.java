package it.eng.tz.area.vasta.suap.sue.download.manuali.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.eng.tz.area.vasta.suap.sue.download.manuali.enumeration.ManualiSuapSueEnum;
import it.eng.tz.area.vasta.suap.sue.download.manuali.svc.IMimeTypeDetectorSvc;

@RestController
@RequestMapping("/download/")
public class DownloadController {
	private static final Logger logger = LoggerFactory.getLogger(DownloadController.class.getName());
	@Autowired
	private HttpServletRequest req;
	@Autowired
	private IMimeTypeDetectorSvc mimeSvc;
	@Autowired
	private ServletContext sc;
	 
	@RequestMapping(value="/suap_sue/{manuale}", method= {RequestMethod.GET})
	public ResponseEntity<InputStreamResource> download(@PathVariable(name="manuale", required=true) String manuale)
	{
		ManualiSuapSueEnum man = ManualiSuapSueEnum.valueOf(manuale);
		String fileName = man.getFileName();
		if( logger.isDebugEnabled() )
		{
			logger.debug("IP RICHIESTA {} MANUALE RICHIESTO {} FILE EROGATO {}",req.getRemoteAddr(), manuale, fileName);
		}
		try {
			File f = new File(sc.getResource("manuali/").getFile(), fileName);
			//File f = new File("/home/angelo/Scrivania/ModuliSuapSue/02 - Modulistica edilizia.pdf");
			int dimensione = (int)f.length();
			InputStream is = new FileInputStream(f);//sc.getResourceAsStream("manuali/"+fileName);
			HttpHeaders respHeaders = new HttpHeaders();
			String mi = mimeSvc.getMimeType(fileName, is);
			MediaType mime = MediaType.parseMediaType(mi);//new MediaType(mimes[0], mimes[1]);
		    respHeaders.setContentType(mime);
		    respHeaders.setContentDispositionFormData("attachment", fileName);
		    respHeaders.setContentLength(dimensione);
		    InputStreamResource result = new InputStreamResource(new FileInputStream(f));
			return new ResponseEntity<InputStreamResource>(result, respHeaders, HttpStatus.OK);	
		} catch (Exception e) {
			
			if( logger.isErrorEnabled() )
			{
				logger.error("Errore nel download del manuale", e);
			}
			return new ResponseEntity<InputStreamResource>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
