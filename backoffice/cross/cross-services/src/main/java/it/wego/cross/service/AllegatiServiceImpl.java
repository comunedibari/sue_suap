/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.PropertyException;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import it.gov.impresainungiorno.schema.base.Comune;
import it.gov.impresainungiorno.schema.base.IndirizzoConRecapiti;
import it.gov.impresainungiorno.schema.base.Provincia;
import it.gov.impresainungiorno.schema.base.Stato;
import it.gov.impresainungiorno.schema.suap.ente.AllegatoCooperazione;
import it.gov.impresainungiorno.schema.suap.ente.CooperazioneSUAPEnte;
import it.gov.impresainungiorno.schema.suap.ente.OggettoCooperazione;
import it.gov.impresainungiorno.schema.suap.pratica.AnagraficaImpresa;
import it.gov.impresainungiorno.schema.suap.pratica.AnagraficaRappresentante;
import it.gov.impresainungiorno.schema.suap.pratica.Carica;
import it.gov.impresainungiorno.schema.suap.pratica.EstremiEnte;
import it.gov.impresainungiorno.schema.suap.pratica.EstremiSuap;
import it.gov.impresainungiorno.schema.suap.pratica.FormaGiuridica;
import it.gov.impresainungiorno.schema.suap.pratica.OggettoComunicazione;
import it.gov.impresainungiorno.schema.suap.pratica.ProtocolloSUAP;
import it.gov.impresainungiorno.schema.suap.pratica.TipoIntervento;
import it.wego.cross.dao.AllegatiDao;
import it.wego.cross.dao.PraticaDao;
import it.wego.cross.dto.AllegatoDTO;
import it.wego.cross.dto.dozer.forms.ComunicazioneDTO;
import it.wego.cross.entity.Allegati;
import it.wego.cross.entity.Anagrafica;
import it.wego.cross.entity.AnagraficaRecapiti;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.LkTipoRuolo;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticaAnagrafica;
import it.wego.cross.entity.Procedimenti;
import it.wego.cross.entity.ProcedimentiEnti;
import it.wego.cross.entity.Recapiti;
import it.wego.cross.plugins.commons.beans.Allegato;
import it.wego.cross.plugins.documenti.GestioneAllegati;
import it.wego.cross.plugins.documenti.GestioneAllegatiDB;
import it.wego.cross.plugins.documenti.GestioneAllegatiFS;
import it.wego.cross.serializer.AllegatiSerializer;
import it.wego.cross.utils.Utils;

/**
 *
 * @author giuseppe
 */
@Service
public class AllegatiServiceImpl implements AllegatiService {

    @Autowired
    private AllegatiDao allegatiDao;
    @Autowired
    private PluginService pluginService;
    @Autowired
    private EntiService entiService;
    private final Map<String, String> temporaryPathCodes = new HashMap<String, String>();
    @Autowired
    private GestioneAllegatiFS gestioneAllegatiFS;
    @Autowired
    private GestioneAllegatiDB gestioneAllegatiDB;
    @Autowired
    private PraticaDao praticaDao;
    @Autowired
    private Mapper mapper;

    @Override
    public String putFile(String path) {
        String code = UUID.randomUUID().toString();
        temporaryPathCodes.put(code, path);
        return code;
    }

    @Override
    public String getFile(String code) {
        return temporaryPathCodes.get(code);
    }

    @Override
    public void deleteFileMap() {
        temporaryPathCodes.clear();
    }

    @Override
    public AllegatoDTO findAllegatoByIdNullSafe(Integer idAllegato, Enti ente) throws Exception {
        Integer idEnte = ente != null ? ente.getIdEnte() : null;
        AllegatoDTO allegato;
        allegato = getAllegato(idAllegato, idEnte);
        return allegato;
    }

    @Override
    public AllegatoDTO getAllegato(Integer idAllegato, Integer idEnte) throws Exception {
        Enti ente = entiService.findByIdEnte(idEnte);
        Allegati allegato = allegatiDao.getAllegato(idAllegato);
        AllegatoDTO a = new AllegatoDTO();
		if (allegato != null) {
			a.setDescrizione(allegato.getDescrizione());
			a.setIdAllegato(allegato.getId());
			a.setNomeFile(allegato.getNomeFile());
			a.setTipoFile(allegato.getTipoFile());

			if (allegato.getFile() != null) {
				a.setFileContent(allegato.getFile());
			} else if (!Utils.e(allegato.getPathFile())) {
				a.setFileContent(gestioneAllegatiFS.getFileContent(allegato, ente, null));
			} else {
				GestioneAllegati ga = pluginService.getGestioneAllegati(idEnte, null);
				a.setFileContent(ga.getFileContent(allegato, ente, null));
			}

			return a;
		}
		else {
			return null;
		}
    }

    @Override
    public Allegati findAllegatoById(Integer idAllegato) throws Exception {
        Allegati allegato = allegatiDao.getAllegato(idAllegato);
        return allegato;
    }

    @Override
    public boolean checkAllegato(Pratica pratica, Integer idFile) {
        boolean isAllegatoPratica = allegatiDao.checkPraticaAllegato(pratica, idFile);
        return isAllegatoPratica;
    }

    @Override
    public void aggiornaAllegato(Allegati allegato) throws Exception {
        allegatiDao.mergeAllegato(allegato);
    }

    @Override
    public Allegati findAllegatoByIdFileEsterno(String idFileEsterno) throws Exception {
        Allegati allegato = allegatiDao.findByIdFileEsterno(idFileEsterno);
        return allegato;
    }

//    @Override
//    public void salvaAllegato(Allegati allegato) throws Exception {
//        allegatiDao.insertAllegato(allegato);
//    }
    @Override
    public Allegati salvaAllegatoFS(AllegatoDTO allegato, Enti ente, LkComuni comune) throws Exception {
        Integer idEnte = ente != null ? ente.getIdEnte() : null;
        Integer idComune = comune != null ? comune.getIdComune() : null;

        if (allegato.getFile() != null) {
            allegato.setFileContent(allegato.getFile().getBytes());
            allegato.setTipoFile(allegato.getFile().getContentType());
            allegato.setNomeFile(allegato.getFile().getOriginalFilename());
        }
        if (!Utils.e(allegato.getIdFileEsterno())) {
            GestioneAllegati gestioneAllegati = pluginService.getGestioneAllegati(idEnte, idComune);
            Allegato file = gestioneAllegati.getFile(allegato.getIdFileEsterno(), ente, comune);
            allegato.setFileContent(file.getFile());
        }

        Allegati a = AllegatiSerializer.serialize(allegato);
        if (Utils.e(allegato.getPathFile())) {
            gestioneAllegatiFS.add(a, ente, comune);
            allegato.setPathFile(a.getPathFile());
        }
        return a;
    }
    
    @Override
    public Allegati salvaAllegatoDB(AllegatoDTO allegato, Enti ente, LkComuni comune) throws Exception {
        Integer idEnte = ente != null ? ente.getIdEnte() : null;
        Integer idComune = comune != null ? comune.getIdComune() : null;

        if (allegato.getFile() != null) {
            allegato.setFileContent(allegato.getFile().getBytes());
            allegato.setTipoFile(allegato.getFile().getContentType());
            allegato.setNomeFile(allegato.getFile().getOriginalFilename());
        }
        if (!Utils.e(allegato.getIdFileEsterno())) {
            GestioneAllegati gestioneAllegati = pluginService.getGestioneAllegati(idEnte, idComune);
            Allegato file = gestioneAllegati.getFile(allegato.getIdFileEsterno(), ente, comune);
            allegato.setFileContent(file.getFile());
        }

        Allegati a = AllegatiSerializer.serialize(allegato);
        return a;
    }

    @Override
    public it.wego.cross.plugins.commons.beans.Allegato getAllegatoDaProtocollo(String idFile, Enti ente) throws Exception {
        GestioneAllegati gestioneAllegati;
        gestioneAllegati = pluginService.getGestioneAllegati(ente == null ? null : ente.getIdEnte(), null);

        //TODO: passare anche LkComune?
        it.wego.cross.plugins.commons.beans.Allegato allegatoProtocollo = gestioneAllegati.getFile(idFile, ente, null);
        return allegatoProtocollo;
    }

	@Override
	public AllegatoDTO creaFileSuapEnte(Pratica pratica, ComunicazioneDTO comunicazione) throws Exception {
		AllegatoDTO suapEnte = new AllegatoDTO();
    	String xml=null;
    	CooperazioneSUAPEnte comunicazioneXml = new CooperazioneSUAPEnte();
    	
    	//Composizione file SUAPENTE.xml per comunicazione tra suap e ente
    	
    		//Info-Schema
    	CooperazioneSUAPEnte.InfoSchema is = new CooperazioneSUAPEnte.InfoSchema();
    	is.setVersione("1.1.0");
		is.setData(Utils.dateToXmlGregorianCalendar(pratica.getDataRicezione()));
    	comunicazioneXml.setInfoSchema(is);
    	
    		//Intestazione
    	CooperazioneSUAPEnte.Intestazione intestazione = new CooperazioneSUAPEnte.Intestazione();
    	intestazione.setProgressivo(1);
    	intestazione.setTotale(1);
    	
    	intestazione.setCodicePratica(pratica.getIdentificativoPratica());
    	
    	EstremiSuap estremiSuap = new EstremiSuap();
    	ProcedimentiEnti idProcEnte = pratica.getIdProcEnte();
    	Enti enteSuap = entiService.findByIdEnte(idProcEnte.getIdEnte().getIdEnte());
    	estremiSuap.setCodiceAmministrazione(enteSuap.getCodiceAmministrazione());
    	estremiSuap.setCodiceAoo(enteSuap.getCodiceAoo());
    	BigInteger identifSuap = null;
    	if(enteSuap.getIdentificativoSuap() != null && enteSuap.getIdentificativoSuap().trim().length() != 0)
    			identifSuap = new BigInteger(enteSuap.getIdentificativoSuap());
    	estremiSuap.setIdentificativoSuap(identifSuap);
    	estremiSuap.setValue(enteSuap.getDescrizione());
		intestazione.setSuapCompetente(estremiSuap);
		
		
		List<String> destinatariIds = comunicazione.getDestinatariIds();
		List<EstremiEnte> listaEnti = new ArrayList<EstremiEnte>();
		for (String d : destinatariIds) {
			Integer idEnte = Integer.valueOf(d.split("\\|")[1]);
			Enti ente = entiService.findByIdEnte(idEnte);
			EstremiEnte ee = new EstremiEnte();
			ee.setValue(ente.getDescrizione());
			ee.setCodiceAmministrazione(ente.getCodiceAmministrazione());
			ee.setCodiceAoo(ente.getCodiceAoo());
			ee.setPec(ente.getPec());
			listaEnti.add(ee);
			
		}
		intestazione.getEnteDestinatario().addAll(listaEnti);
		
		OggettoComunicazione oc = new OggettoComunicazione();	
		oc.setTipoIntervento(TipoIntervento.ALTRO);
		Procedimenti proc = idProcEnte.getIdProc();
		if(proc.getTipoProc().contains("O"))
			oc.setTipoProcedimento("Ordinario");
		else
			oc.setTipoProcedimento("Automatizzato");
		oc.setValue(pratica.getOggettoPratica());
		
		intestazione.setOggettoPratica(oc);
		
		it.gov.impresainungiorno.schema.suap.pratica.ProtocolloSUAP protocolloPraticaSuap = new ProtocolloSUAP();
		protocolloPraticaSuap.setCodiceAmministrazione(enteSuap.getCodiceAmministrazione());
		protocolloPraticaSuap.setCodiceAoo(enteSuap.getCodiceAoo());
		protocolloPraticaSuap.setDataRegistrazione(Utils.dateToXmlGregorianCalendar(pratica.getDataProtocollazione()));
		protocolloPraticaSuap.setNumeroRegistrazione(pratica.getProtocollo());
		
		intestazione.setProtocolloPraticaSuap(protocolloPraticaSuap);
		
		AnagraficaImpresa impresa = new AnagraficaImpresa();
		
		LkTipoRuolo tipoRuolo = new LkTipoRuolo(2); //Beneficiario
		List<PraticaAnagrafica> pa = praticaDao.findPraticaAnagraficaByTipoRuolo(pratica, tipoRuolo);
		if (!pa.isEmpty()) {
			Anagrafica anagrafica = pa.get(0).getAnagrafica();//Anagrafica del beneficiario
			List<AnagraficaRecapiti> anagraficaRecapitiList = anagrafica.getAnagraficaRecapitiList();
			Recapiti recapiti = anagraficaRecapitiList.get(0).getIdRecapito();
			FormaGiuridica fg = new FormaGiuridica();
			if(anagrafica.getIdFormaGiuridica() != null) {
				fg.setCategoria(anagrafica.getIdFormaGiuridica().getCodFormaGiuridica());
				fg.setValue(anagrafica.getIdFormaGiuridica().getDescrizione());
			}

			impresa.setFormaGiuridica(fg);
			impresa.setRagioneSociale(anagrafica.getDenominazione());
			impresa.setCodiceFiscale(anagrafica.getCodiceFiscale());
			impresa.setPartitaIva(anagrafica.getPartitaIva());
			IndirizzoConRecapiti indirizzo = new IndirizzoConRecapiti();
			Stato stato = new Stato();
			stato.setCodice("I");
			stato.setValue("Italia");

			indirizzo.setStato(stato);
			Provincia pr = new Provincia();
			pr.setSigla(recapiti.getIdComune().getIdProvincia().getCodCatastale());
			pr.setValue(recapiti.getIdComune().getIdProvincia().getDescrizione());
			indirizzo.setProvincia(pr);
			Comune c = new Comune();
			c.setCodiceCatastale(recapiti.getIdComune().getCodCatastale());
			c.setValue(recapiti.getIdComune().getDescrizione());
			indirizzo.setComune(c);
			indirizzo.setCap(recapiti.getCap());
			indirizzo.setDenominazioneStradale(recapiti.getIndirizzo());
			indirizzo.setNumeroCivico(recapiti.getNCivico());
			impresa.setIndirizzo(indirizzo);

			AnagraficaRappresentante lr = new AnagraficaRappresentante();
			lr.setNome(anagrafica.getNome());
			lr.setCognome(anagrafica.getCognome());
			lr.setCodiceFiscale(anagrafica.getCodiceFiscale());
			Carica carica = new Carica();
			carica.setCodice("AMM");
			carica.setValue("Amministratore");
			lr.setCarica(carica );
			impresa.setLegaleRappresentante(lr);

			intestazione.setImpresa(impresa);
		}
		else {
			FormaGiuridica fg = new FormaGiuridica();
			impresa.setFormaGiuridica(fg);
			IndirizzoConRecapiti indirizzo = new IndirizzoConRecapiti();
			impresa.setIndirizzo(indirizzo);
			AnagraficaRappresentante lr = new AnagraficaRappresentante();
			impresa.setLegaleRappresentante(lr);
			intestazione.setImpresa(impresa);
		}
		OggettoCooperazione ocomunicazione = new OggettoCooperazione();
		ocomunicazione.setTipoCooperazione("OINOLTRO");
		ocomunicazione.setValue("Trasmissione pratica n."+ pratica.getIdentificativoPratica());
		intestazione.setOggettoComunicazione(ocomunicazione);
		
		intestazione.setTestoComunicazione("Si trasmette, per competenza, la pratica"+ pratica.getIdentificativoPratica() +" presa in carico da " + enteSuap.getDescrizione() + " .\r\n" + 
				"SUAP mittente: "+ enteSuap.getDescrizione() +" \r\n" + 
				"Pratica: "+ pratica.getIdentificativoPratica() +"\r\n" + 
				"Impresa: "+ impresa.getRagioneSociale() + "\r\n" + 
				"Protocollo Registro Imprese: \r\n" + 
				"Protocollo pratica: "+ pratica.getProtocollo() +"\r\n" + 
				"Protocollo della comunicazione: " + (comunicazione.getNumeroDiProtocollo() != null ? comunicazione.getNumeroDiProtocollo() : "")  +  
				"\r\n" + 
				"Adempimenti presenti nella pratica:\r\n" + 
				"- Domanda preventiva di parere e/o atto di assenso ad Ente\r\n" + 
				"\r\n" + 
				"Si allega alla presente anche la ricevuta rilasciata all'impresa dal SUAP, ai sensi del d.P.R. 160/2010.\r\n" + 
				"\r\n" + 
				"Si chiede al destinatario della presente, di trasmettere l'eventuale risposta utilizzando la funzione \"rispondi\" del proprio sistema di Posta Elettronica Certificata, lasciando invariati l'oggetto della comunicazione ed il destinatario della stessa; cio' al fine di garantire il tempestivo ricevimento della risposta da parte del SUAP.                       \r\n" + 
				"Si ricorda inoltre che i formati ammessi per gli allegati alle pratiche SUAP sono i seguenti:\r\n" + 
				"pdf; pdf.p7m; xml; dwf; dwf.p7m; svg; svg.p7m; jpg; jpg.p7m\r\n" + 
				"Pertanto sia i documenti che gli uffici SUAP allegano a comunicazioni effettuate tramite la Scrivania Virtuale, sia i documenti trasmessi da imprese, intermediari ed enti terzi ai SUAP tramite PEC, devono rispettare tali formati.");
    	
		
		it.gov.impresainungiorno.schema.suap.pratica.ProtocolloSUAP protocollo = new ProtocolloSUAP();
		protocollo.setCodiceAmministrazione(enteSuap.getCodiceAmministrazione());
		protocollo.setCodiceAoo(enteSuap.getCodiceAoo());
		if (comunicazione.getDataDiProtocollo() != null)
			protocollo.setDataRegistrazione(Utils.dateToXmlGregorianCalendar(comunicazione.getDataDiProtocollo()));
		else
			protocollo.setDataRegistrazione(null);
		if (comunicazione.getNumeroDiProtocollo() != null)
			protocollo.setNumeroRegistrazione(comunicazione.getNumeroDiProtocollo());
		else
			protocollo.setNumeroRegistrazione("");
		
		intestazione.setProtocollo(protocollo);
		comunicazioneXml.setIntestazione(intestazione);
    	
		
    		//Allegati
		it.wego.cross.dto.dozer.PraticaDTO praticaDTO = mapper.map(pratica, it.wego.cross.dto.dozer.PraticaDTO.class);
		List<AllegatoCooperazione> temp = new ArrayList<AllegatoCooperazione>();
		List<it.wego.cross.dto.dozer.AllegatoDTO> allegatiPratica = praticaDTO.getAllegati();
		for (it.wego.cross.dto.dozer.AllegatoDTO allegato : allegatiPratica) {
    		AllegatoCooperazione e = AllegatiSerializer.serializeAllegatoCooperazione(allegato);
    		temp.add(e);
        }
    	comunicazioneXml.getAllegato().addAll(temp);
    	
    	//Fine creazione xml
    	
    	//Creazione file fisico

    	try {
    		it.gov.impresainungiorno.schema.suap.ente.ObjectFactory of = new it.gov.impresainungiorno.schema.suap.ente.ObjectFactory();
            JAXBElement<CooperazioneSUAPEnte> root = of.createCooperazioneSuapEnte(comunicazioneXml);
            xml = Utils.marshall(root, CooperazioneSUAPEnte.class); 
		} catch (PropertyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	String createTempFile = gestioneAllegatiFS.createTempFile("SUAPENTE.xml", xml.getBytes(), enteSuap, null);
    	MultipartFile filea = new MockMultipartFile("SUAPENTE.xml", "SUAPENTE.xml", "application/octet-stream", xml.getBytes());
		suapEnte.setDescrizione("Comunicazione Suap Ente_" + Utils.convertDataToString(new java.util.Date()));
		suapEnte.setNomeFile("SUAPENTE.xml");
		suapEnte.setTipoFile("application/octet-stream");
		suapEnte.setPathFile(createTempFile);
		suapEnte.setFile(filea);
		suapEnte.setAllegaAllaMail("true");
		return suapEnte;
	}
    
}
