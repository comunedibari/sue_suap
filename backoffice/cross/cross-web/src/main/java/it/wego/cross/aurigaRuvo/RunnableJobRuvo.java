package it.wego.cross.aurigaRuvo;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;

import it.wego.cross.plugins.GestioneProtocolloRuvo;
import it.wego.cross.service.ProtocolloService;
import it.wego.cross.utils.Log;

public class RunnableJobRuvo implements Runnable {
    private final String protocollo;
    private final String nomeFileZippato;
    
	@Autowired 
	ProtocolloService protocolloService;

	private byte[] stampedDocument;

    
    /**
	 * @return the stampedDocument
	 */
	public byte[] getStampedDocument() {
		return stampedDocument;
	}

	/**
	 * @param stampedDocument the stampedDocument to set
	 */
	public void setStampedDocument(byte[] stampedDocument) {
		this.stampedDocument = stampedDocument;
	}

	public RunnableJobRuvo(String protocollo, String nomeFileZippato) {
        this.protocollo = protocollo;
        this.nomeFileZippato = nomeFileZippato;
    }

    @Override
    public void run() {
    	try {
			//stampedDocument = protocolloService.getDocumentiProtocollatiRuvo(protocollo, nomeFileZippato);
    		GestioneProtocolloRuvo protocolloService = new GestioneProtocolloRuvo();
    		
    		//protocolloService.getDocumentiProtocollatiRuvo(protocollo, nomeFileZippato);
    		    		
    		Log.APP.info("Run Job Ruvo numero protocollo: " + protocollo + " nomefile:" + nomeFileZippato);
    		System.out.println("Run Job Ruvo numero protocollo: " + protocollo + " nomefile:" + nomeFileZippato);

//           	String nomeFileZippato = protocolloRuvo.getAllegatiProtocollatiTimbratiRuvo(protocolId);   
    		protocolloService.getAllegatiProtocollatiTimbratiRuvo(protocollo, nomeFileZippato);   
           	
           	System.out.println("Run Job Ruvo file recuperato: " + nomeFileZippato);
           	Log.APP.info("Run Job Ruvo file recuperato: " + nomeFileZippato);

           	File fileTmp = new File(nomeFileZippato);
           	
           	stampedDocument = protocolloService.getFileZippato(fileTmp);           	    		
    		
		} catch (Exception e) {
			Log.APP.error("Run Job Ruvo Non è stato possibile scaricare i documenti", e);
		}
    }
 } 