/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import java.io.File;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.wego.cross.plugins.GestioneProtocolloBari;
import it.wego.cross.plugins.GestioneProtocolloRuvo;
import it.wego.cross.plugins.commons.beans.Allegato;
import it.wego.cross.utils.Log;
import utilities.myUtility;

@Service
public class ProtocolloServiceImpl implements ProtocolloService{

	@Autowired
	private GestioneProtocolloBari protocolloBari;

	@Autowired
	private GestioneProtocolloRuvo protocolloRuvo;

	@Override
	public Allegato getStampedDocument(String protocolId, String attachmentName) throws Exception{

		Log.APP.info("numero protocollo: " + protocolId);
		Log.APP.info("Nome file: " + attachmentName);

       	String fileBase64 = protocolloBari.getFileProtocollatoTimbrato(protocolId, attachmentName);   
		byte[] body =  Base64.getDecoder().decode(fileBase64);
		Allegato ret = new Allegato();		
		ret.setFile(body);
		return ret;
	}

	@Override
	public byte[] getDocumentiProtocollatiRuvo(String protocolId, String nomeFileZippato) throws Exception{

		byte[] result = null;
		
		Log.APP.info("getDocumentiProtocollatiRuvo numero protocollo: " + protocolId);
		System.out.println("getDocumentiProtocollatiRuvo numero protocollo: " + protocolId);

//       	String nomeFileZippato = protocolloRuvo.getAllegatiProtocollatiTimbratiRuvo(protocolId);   
       	protocolloRuvo.getAllegatiProtocollatiTimbratiRuvo(protocolId, nomeFileZippato);   
       	
       	System.out.println("getDocumentiProtocollatiRuvo file recuperato: " + nomeFileZippato);
       	Log.APP.info("getDocumentiProtocollatiRuvo file recuperato: " + nomeFileZippato);

       	File fileTmp = new File(nomeFileZippato);
       	
       	result = protocolloRuvo.getFileZippato(fileTmp);
       	
		return result;
		
	}

	@Override
	public byte[] getFileProtocollatoTimbratoRuvo(String protocolId, String nomeFile) throws Exception {
		
		Log.APP.info("ProtocolloServiceImpl::getFileProtocollatoTimbratoRuvo numero protocollo: " + protocolId);
		Log.APP.info("ProtocolloServiceImpl::getFileProtocollatoTimbratoRuvo nomeFile: " + nomeFile);
		System.out.println("getDocumentiProtocollatiRuvo numero protocollo: " + protocolId);
		
		String sfileBase64 = protocolloRuvo.getFileProtocollatoTimbratoRuvo(protocolId, nomeFile);
		byte[] decodedString = Base64.getDecoder().decode(new String(sfileBase64).getBytes("UTF-8"));
				
		return decodedString;
	}

	@Override
	public String getNomeFileZippato(Integer idPratica, Integer idPraticaEvento) {

		return protocolloRuvo.getNomeFileZippato(idPratica, idPraticaEvento);
	}

	@Override
	public byte[] recuperaZipfileTimbrati(String nomefileZippato) {
		
		return protocolloRuvo.recuperaZipfileTimbrati(nomefileZippato);
	}	
}
