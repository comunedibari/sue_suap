/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import it.gov.impresainungiorno.schema.base.CodiceREA;
import it.gov.impresainungiorno.schema.base.Comune;
import it.gov.impresainungiorno.schema.base.IndirizzoConRecapiti;
import it.gov.impresainungiorno.schema.base.Provincia;
import it.gov.impresainungiorno.schema.base.Stato;
import it.gov.impresainungiorno.schema.suap.ente.AllegatoCooperazione;
import it.gov.impresainungiorno.schema.suap.ente.CooperazioneEnteSUAP;
import it.gov.impresainungiorno.schema.suap.ente.OggettoCooperazione;
import it.gov.impresainungiorno.schema.suap.ente.CooperazioneEnteSUAP.InfoSchema;
import it.gov.impresainungiorno.schema.suap.ente.CooperazioneEnteSUAP.Intestazione;
import it.gov.impresainungiorno.schema.suap.pratica.AnagraficaImpresa;
import it.gov.impresainungiorno.schema.suap.pratica.AnagraficaRappresentante;
import it.gov.impresainungiorno.schema.suap.pratica.Carica;
import it.gov.impresainungiorno.schema.suap.pratica.EstremiEnte;
import it.gov.impresainungiorno.schema.suap.pratica.EstremiSuap;
import it.gov.impresainungiorno.schema.suap.pratica.FormaGiuridica;
import it.gov.impresainungiorno.schema.suap.pratica.OggettoComunicazione;
import it.gov.impresainungiorno.schema.suap.pratica.TipoIntervento;
import it.gov.impresainungiorno.schema.suap.ri.ProtocolloSUAP;
import it.gov.impresainungiorno.schema.suap.ri.RichiestaIscrizioneImpresaRiSPC;
import it.gov.impresainungiorno.schema.suap.ri.ServizioIntegrazioneSuapRi;
import it.gov.impresainungiorno.schema.suap.ri.spc.IscrizioneImpresaRiSpcRequest;
import it.gov.impresainungiorno.schema.suap.ri.spc.IscrizioneImpresaRiSpcResponse;
import it.wego.cross.constants.Constants;
import it.wego.cross.constants.SessionConstants;
import it.wego.cross.dao.*;
import it.wego.cross.dto.AllegatoDTO;
import it.wego.cross.dto.dozer.forms.ComunicazioneDTO;
import it.wego.cross.entity.Anagrafica;
import it.wego.cross.entity.AnagraficaRecapiti;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.LkTipoRuolo;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticaAnagrafica;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.entity.Procedimenti;
import it.wego.cross.entity.ProcedimentiEnti;
import it.wego.cross.entity.Recapiti;
import it.wego.cross.serializer.AllegatiSerializer;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.Utils;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.BindingProvider;
import org.apache.commons.lang.StringUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Gabri
 */
@Service
public class RegistroImpreseServiceImpl implements RegistroImpreseService {

    @Autowired
//    private ConfigurationDao configurationDao;
    private ConfigurationService configurationService;
    @Autowired
    private PraticheService praticheService;
    
    @Autowired
    private EntiService entiService;
    
    @Autowired
    private PraticaDao praticaDao;
    
    @Autowired
    private Mapper mapper;

    @Override
    public IscrizioneImpresaRiSpcResponse richiestaIscrizioneImpresa(String codiceFiscale) throws Exception {
        RichiestaIscrizioneImpresaRiSPC richiestaIscrizioneImpresaPortType = getRichiestaIscrizioneImpresaPortType();

        IscrizioneImpresaRiSpcRequest richiestaIntegrazioneRequest = new IscrizioneImpresaRiSpcRequest();
        richiestaIntegrazioneRequest.setCodiceFiscale(codiceFiscale);

        IscrizioneImpresaRiSpcResponse richiestaIntegrazioneResponse = richiestaIscrizioneImpresaPortType.iscrizioneImpresaRiSpc(richiestaIntegrazioneRequest);

        return richiestaIntegrazioneResponse;

    }

    private ServizioIntegrazioneSuapRi getServizioRegistroImprese() throws Exception {
        URL wsdlUrl = RegistroImpreseServiceImpl.class.getResource("/wsdl/registroimprese/ws/comunicazioniRea_implementazione_erogatore.wsdl");
        if (wsdlUrl == null) {
            throw new Exception("Impossibile individualre il WSDL del servizio di integrazione di Registro Imprese.");
        }

        ServizioIntegrazioneSuapRi registroImprese = new ServizioIntegrazioneSuapRi(wsdlUrl);
        return registroImprese;
    }

    @Override
    public RichiestaIscrizioneImpresaRiSPC getRichiestaIscrizioneImpresaPortType() throws Exception {
        RichiestaIscrizioneImpresaRiSPC richiestaIscrizioneImpresaPortType = getServizioRegistroImprese().getRichiestaIscrizioneImpresaRiSPC();
        String registroImpreseRichiestaIscrizioneImpresaClientUrl = configurationService.getCachedConfiguration(SessionConstants.REGISTRO_IMPRESE_RICHIESTA_ISCRIZIONE_URL,null,null);
        if (StringUtils.isEmpty(registroImpreseRichiestaIscrizioneImpresaClientUrl)) {
            throw new Exception("Chiave '" + SessionConstants.REGISTRO_IMPRESE_RICHIESTA_ISCRIZIONE_URL + "' non trovata nella tabella Configurations.");
        }
        ((BindingProvider) richiestaIscrizioneImpresaPortType).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, registroImpreseRichiestaIscrizioneImpresaClientUrl);
        return richiestaIscrizioneImpresaPortType;
    }

    @Override
    public ProtocolloSUAP getComunicazioneSuapPortType() throws Exception {
        ProtocolloSUAP comunicazioneSuapPortType = getServizioRegistroImprese().getProtocolloSUAP();
        String registroImpreseComunicazioneSuapImpresaClientUrl = configurationService.getCachedConfiguration(SessionConstants.REGISTRO_IMPRESE_COMUNICAZIONE_SUAP_URL,null,null);
        if (StringUtils.isEmpty(registroImpreseComunicazioneSuapImpresaClientUrl)) {
            throw new Exception("Chiave '" + SessionConstants.REGISTRO_IMPRESE_COMUNICAZIONE_SUAP_URL + "' non trovata nella tabella Configurations.");
        }
        ((BindingProvider) comunicazioneSuapPortType).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, registroImpreseComunicazioneSuapImpresaClientUrl);
        return comunicazioneSuapPortType;
    }
    
    public CooperazioneEnteSUAP creaCES(Pratica pratica, ComunicazioneDTO comunicazione) throws Exception {
		AllegatoDTO suapEnte = new AllegatoDTO();
    	String xml=null;
    	CooperazioneEnteSUAP comunicazioneXml = new CooperazioneEnteSUAP();
    	Log.APP.info("dentro metodo creaCES");
    	//Composizione file ENTESUAP.xml per comunicazione tra ENTE e SUAP
    	
    		//Info-Schema
    	CooperazioneEnteSUAP.InfoSchema is = new CooperazioneEnteSUAP.InfoSchema();
    	is.setVersione("1.1.0");
		is.setData(Utils.dateToXmlGregorianCalendar(pratica.getDataRicezione()));
//    	is.setData(pratica.getDataRicezione());
    	comunicazioneXml.setInfoSchema(is);
    	
    		//Intestazione
    	CooperazioneEnteSUAP.Intestazione intestazione = new CooperazioneEnteSUAP.Intestazione();
    	intestazione.setProgressivo(1);
    	intestazione.setTotale(1);
    	
    	intestazione.setCodicePratica(pratica.getIdentificativoPratica());
		
    	
    	ProcedimentiEnti idProcEnte = pratica.getIdProcEnte();
    	Enti ente = entiService.findByIdEnte(idProcEnte.getIdEnte().getIdEnte());
    	EstremiEnte em = new EstremiEnte();
		em.setCodiceAmministrazione(ente.getCodiceAmministrazione());
		em.setCodiceAoo(ente.getCodiceAoo());
		em.setPec(ente.getPec());
		em.setValue(ente.getDescrizione());
		intestazione.setEnteMittente(em);
    	// da dove prendo i dati dati suap forse da ente?
		EstremiSuap estremiSuap = new EstremiSuap();
		Enti enteSuap = new Enti();
    	estremiSuap.setCodiceAmministrazione(enteSuap.getCodiceAmministrazione());
    	estremiSuap.setCodiceAoo(enteSuap.getCodiceAoo());
    	BigInteger identifSuap = null;
    	if(enteSuap.getIdentificativoSuap() != null && enteSuap.getIdentificativoSuap().trim().length() != 0)
    			identifSuap = new BigInteger(enteSuap.getIdentificativoSuap());
    	estremiSuap.setIdentificativoSuap(identifSuap);
    	estremiSuap.setValue(enteSuap.getDescrizione());
		intestazione.setSuapCompetente(estremiSuap);
		
		
//		List<String> destinatariIds = comunicazione.getDestinatariIds();
//		List<EstremiEnte> listaEnti = new ArrayList<EstremiEnte>();
//		for (String d : destinatariIds) {
//			Integer idEnte = Integer.valueOf(d.split("\\|")[1]);
//			Enti ente = entiService.findByIdEnte(idEnte);
//			EstremiEnte ee = new EstremiEnte();
//			ee.setValue(ente.getDescrizione());
//			ee.setCodiceAmministrazione(ente.getCodiceAmministrazione());
//			ee.setCodiceAoo(ente.getCodiceAoo());
//			ee.setPec(ente.getPec());
//			listaEnti.add(ee);
//			
//		}
//		intestazione.getEnteDestinatario().addAll(listaEnti);
//		settipoCooperaizione = ALTRO O RILASCIO PROVVEDIMENTO
		
		OggettoComunicazione oc = new OggettoComunicazione();	
		// prendere dalla pratica
		oc.setTipoIntervento(TipoIntervento.ALTRO);
		Procedimenti proc = idProcEnte.getIdProc();
		// prendere dalla pratica
		if(proc.getTipoProc().contains("O"))
			oc.setTipoProcedimento("Ordinario");
		else
			oc.setTipoProcedimento("Automatizzato");
		oc.setValue(pratica.getOggettoPratica());
		
		intestazione.setOggettoPratica(oc);
		// bisogna mettere la parte protocollo?
		it.gov.impresainungiorno.schema.suap.pratica.ProtocolloSUAP protocolloPraticaSuap = new it.gov.impresainungiorno.schema.suap.pratica.ProtocolloSUAP();
		protocolloPraticaSuap.setCodiceAmministrazione(enteSuap.getCodiceAmministrazione());
		protocolloPraticaSuap.setCodiceAoo(enteSuap.getCodiceAoo());
//		protocolloPraticaSuap.setDataRegistrazione(Utils.dateToXmlGregorianCalendar(pratica.getData_prot_suap()));
		protocolloPraticaSuap.setDataRegistrazione(pratica.getData_prot_suap());
		protocolloPraticaSuap.setNumeroRegistrazione(pratica.getProt_suap());
		
		intestazione.setProtocolloPraticaSuap(protocolloPraticaSuap);
		
		AnagraficaImpresa impresa = new AnagraficaImpresa();
		// prendere dalla pratica
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
			pr.setSigla(recapiti.getIdComune() != null ?
				recapiti.getIdComune().getIdProvincia().getCodCatastale() : "");
			pr.setValue(recapiti.getIdComune() != null ?
					recapiti.getIdComune().getIdProvincia().getDescrizione() : ""	);
			indirizzo.setProvincia(pr);
			Comune c = new Comune();
			c.setCodiceCatastale(recapiti.getIdComune() != null ?
					recapiti.getIdComune().getCodCatastale() : "");
			c.setValue(recapiti.getIdComune() != null ?
					recapiti.getIdComune().getDescrizione() : "");
			indirizzo.setComune(c);
			indirizzo.setCap(recapiti.getCap());
			indirizzo.setDenominazioneStradale(recapiti.getIndirizzo() != null ?
					recapiti.getIndirizzo() : "");
			indirizzo.setNumeroCivico(recapiti.getNCivico() != null ?
					recapiti.getNCivico() : "");
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
		// da dove ricavare i dati della comunicazione?
		OggettoCooperazione ocomunicazione = new OggettoCooperazione();
		ocomunicazione.setTipoCooperazione("OINOLTRO");
		ocomunicazione.setValue("Trasmissione pratica n."+ pratica.getIdentificativoPratica());
		intestazione.setOggettoComunicazione(ocomunicazione);
		//prendere dalla pratica
		intestazione.setTestoComunicazione(comunicazione.getContenuto());
		
		it.gov.impresainungiorno.schema.suap.pratica.ProtocolloSUAP protocollo = new it.gov.impresainungiorno.schema.suap.pratica.ProtocolloSUAP();
		protocollo.setCodiceAmministrazione(enteSuap.getCodiceAmministrazione());
		protocollo.setCodiceAoo(enteSuap.getCodiceAoo());
		if (comunicazione.getDataDiProtocollo() != null)
//			protocollo.setDataRegistrazione(Utils.dateToXmlGregorianCalendar(comunicazione.getDataDiProtocollo()));
			protocollo.setDataRegistrazione(comunicazione.getDataDiProtocollo());
		else
			protocollo.setDataRegistrazione(null);
		if (comunicazione.getNumeroDiProtocollo() != null)
			protocollo.setNumeroRegistrazione(comunicazione.getNumeroDiProtocollo());
		else
			protocollo.setNumeroRegistrazione("");
		
		intestazione.setProtocollo(protocollo);
		comunicazioneXml.setIntestazione(intestazione);
    	
		
    		//Allegati
//		it.wego.cross.dto.dozer.PraticaDTO praticaDTO = mapper.map(pratica, it.wego.cross.dto.dozer.PraticaDTO.class);
//		List<AllegatoCooperazione> temp = new ArrayList<AllegatoCooperazione>();
//		List<it.wego.cross.dto.dozer.AllegatoDTO> allegatiPratica = praticaDTO.getAllegati();
//		for (it.wego.cross.dto.dozer.AllegatoDTO allegato : allegatiPratica) {
//    		AllegatoCooperazione e = AllegatiSerializer.serializeAllegatoCooperazione(allegato);
//    		temp.add(e);
//        }
//    	comunicazioneXml.getAllegato().addAll(temp);
    	
    	//Fine creazione xml
    	
		return comunicazioneXml;
	}
    
    
    public   CooperazioneEnteSUAP creaCES2(Pratica pratica) throws ParseException, IOException {
		CooperazioneEnteSUAP ces = new CooperazioneEnteSUAP();
		InfoSchema value = new InfoSchema();
		value.setVersione("1.1.0");
		Date data = new Date();
		String myDateStr = "2015-05-18";
		    
		     SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		     Date myDate = simpleDateFormat.parse(myDateStr);
		
		XMLGregorianCalendar date1 = null;
		XMLGregorianCalendar date2 = null;
		try {
			date1 = DatatypeFactory.newInstance().newXMLGregorianCalendar("2018-04-23+02:00");
			date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar("2018-04-20+02:00");
		} catch (DatatypeConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		value.setData(date1);
		System.out.println("Date1: "+date1);
		System.out.println("Date2: "+date2);

		ces.setInfoSchema(value);

		Intestazione intestaz = new Intestazione();
		EstremiEnte em = new EstremiEnte();
		em.setCodiceAmministrazione("CCIAA_MI");
		em.setCodiceAoo("MI-SUPRO");
		em.setPec("recapitoPDD-milano@prova.it");
		em.setValue("xxx@prova.it");
		intestaz.setEnteMittente(em);
		EstremiSuap es = new EstremiSuap();
		es.setCodiceAmministrazione("c_f205");
		es.setCodiceAoo("AMM");
		es.setCodiceCatastale("");
		es.setIdentificativoSuap(BigInteger.valueOf(291));
		es.setValue("SUAP Comune di Milano");
		intestaz.setSuapCompetente(es);
		intestaz.setCodicePratica("DNGFNC62H13A429G-06032015-0902");
		AnagraficaImpresa impresa = new AnagraficaImpresa();
		FormaGiuridica fg = new FormaGiuridica();
//		fg.setCategoria("");
		fg.setCodice("DI");
		fg.setValue("IMPRESA INDIVIDUALE");
		impresa.setFormaGiuridica(fg);
		impresa.setRagioneSociale("DITTA DI PROVA SCIA 6 MARZO -1");
		impresa.setCodiceFiscale("DNGFNC62H13A429G");
//		impresa.setPartitaIva("");
		CodiceREA cr = new CodiceREA();
		cr.setProvincia("");
		cr.setValue("");
//		impresa.setCodiceREA(cr);
		IndirizzoConRecapiti indirizzo = new IndirizzoConRecapiti();
		Stato s = new Stato();
		s.setCodice("I");
		s.setValue("Italia");
		indirizzo.setStato(s);
		Provincia prov = new Provincia();
		prov.setSigla("MI");
		prov.setValue("MILANO");
		indirizzo.setProvincia(prov);
		Comune com = new Comune();
		com.setValue("MILANO");
		com.setCodiceCatastale("F205");
		indirizzo.setComune(com);
		indirizzo.setCap("20100");
		indirizzo.setToponimo("via");
		indirizzo.setDenominazioneStradale("ROMA");
		indirizzo.setNumeroCivico("22");
		impresa.setIndirizzo(indirizzo);
		AnagraficaRappresentante lr = new AnagraficaRappresentante();
		lr.setCognome("DE NIGRIS");
		lr.setNome("FRANCESCO");
		lr.setCodiceFiscale("DNGFNC62H13A429G");
		Carica carica = new Carica();
		carica.setCodice("TI");
		carica.setValue("TITOLARE");
		lr.setCarica(carica);
		impresa.setLegaleRappresentante(lr);
		intestaz.setImpresa(impresa);
		

		OggettoComunicazione op = new OggettoComunicazione();
		op.setTipoIntervento(TipoIntervento.ALTRO);
		op.setTipoProcedimento("SCIA");
		op.setValue(
				"Comunicazione SUAP pratica n.DNGFNC62H13A429G-06032015-0902 - SUAP 291 - DNGFNC62H13A429G DITTA DI PROVA SCIA 6 MARZO -1");
		intestaz.setOggettoPratica(op);
		it.gov.impresainungiorno.schema.suap.pratica.ProtocolloSUAP pps = new it.gov.impresainungiorno.schema.suap.pratica.ProtocolloSUAP();
		pps.setCodiceAmministrazione("REP_PROV_LE");
		pps.setCodiceAoo("LE-SUPRO");
		pps.setNumeroRegistrazione("0026173");
		//XMLGregorianCalendar dataRegistrazione = dateToXmlGregorianCalendar(new Date());
//		pps.setDataRegistrazione(new Date());
		intestaz.setProtocolloPraticaSuap(pps);
		OggettoCooperazione oc = new OggettoCooperazione();
		oc.setTipoCooperazione("altro");
		oc.setValue("Si comunica a codesto SUAP che la SCIA � stata ricevuta correttamente�");
		intestaz.setOggettoComunicazione(oc);
		intestaz.setTestoComunicazione("Test");

		it.gov.impresainungiorno.schema.suap.pratica.ProtocolloSUAP prot = new it.gov.impresainungiorno.schema.suap.pratica.ProtocolloSUAP();
		prot.setCodiceAmministrazione("CCIAA_MI");
		prot.setCodiceAoo("MI-SUPRO");
		prot.setNumeroRegistrazione("0000177");
		
		
		//prot.setDataRegistrazione(dataRegistrazione);
//		prot.setDataRegistrazione(new Date());
		intestaz.setProtocollo(prot);
		ces.setIntestazione(intestaz);
		
		it.wego.cross.dto.dozer.PraticaDTO praticaDTO = mapper.map(pratica, it.wego.cross.dto.dozer.PraticaDTO.class);
		List<AllegatoCooperazione> temp = new ArrayList<AllegatoCooperazione>();
		List<it.wego.cross.dto.dozer.AllegatoDTO> allegatiPratica = praticaDTO.getAllegati();
		for (it.wego.cross.dto.dozer.AllegatoDTO allegato : allegatiPratica) {
    		AllegatoCooperazione e = AllegatiSerializer.serializeAllegatoCooperazione(allegato);
    		temp.add(e);
        }
    	ces.getAllegato().addAll(temp);
		
		
		
		return ces;
	}
    
    public   CooperazioneEnteSUAP creaCES3(Pratica pratica,ComunicazioneDTO comunicazione) throws ParseException, IOException, DatatypeConfigurationException {
    	CooperazioneEnteSUAP comunicazioneXml = new CooperazioneEnteSUAP();
    	Log.APP.info("dentro metodo creaCES");
    	//Composizione file ENTESUAP.xml per comunicazione tra ENTE e SUAP
    	
    		//Info-Schema
    	InfoSchema is = new InfoSchema();
    	is.setVersione("1.1.0");
		is.setData(Utils.dateToXmlGregorianCalendar(pratica.getDataRicezione()));
//    	is.setData(pratica.getDataRicezione());
    	comunicazioneXml.setInfoSchema(is);
		
    	 it.wego.cross.dto.dozer.PraticaDTO praticaDTO = mapper.map(pratica, it.wego.cross.dto.dozer.PraticaDTO.class);
    	
		Intestazione intestazione = new Intestazione();
		ProcedimentiEnti idProcEnte = pratica.getIdProcEnte();
		Enti ente = entiService.findByIdEnte(idProcEnte.getIdEnte().getIdEnte());
    	EstremiEnte em = new EstremiEnte();
		em.setCodiceAmministrazione( praticaDTO.getEnte().getCodiceAmministrazione());
		em.setCodiceAoo( praticaDTO.getEnte().getCodiceAoo());
		em.setPec( praticaDTO.getEnte().getPec());
		em.setValue( praticaDTO.getEnte().getDescrizione());
		intestazione.setEnteMittente(em);
//		EstremiSuap estremiSuap = new EstremiSuap();
//		pratica.getidp
		Enti enteSuap = new Enti();
//    	estremiSuap.setCodiceAmministrazione(enteSuap.getCodiceAmministrazione());
//    	estremiSuap.setCodiceAoo(enteSuap.getCodiceAoo());
//    	BigInteger identifSuap = null;
//    	if(enteSuap.getIdentificativoSuap() != null && enteSuap.getIdentificativoSuap().trim().length() != 0)
//    			identifSuap = new BigInteger(enteSuap.getIdentificativoSuap());
//    	estremiSuap.setIdentificativoSuap(identifSuap);
//    	estremiSuap.setValue(enteSuap.getDescrizione());
//		intestazione.setSuapCompetente(estremiSuap);
		
		

		
		EstremiSuap estremiSuap = new EstremiSuap();
		estremiSuap.setCodiceAmministrazione(ente.getCodiceAmministrazione());
		estremiSuap.setCodiceAoo(ente.getCodiceAoo());
		estremiSuap.setCodiceCatastale(ente.getCodiceCatastale());
		estremiSuap.setIdentificativoSuap(new BigInteger(praticaDTO.getEnte().getIdentificativoSuap())); //capire dove sta l identificativo suap
		estremiSuap.setValue(ente.getDescrizione());
		intestazione.setSuapCompetente(estremiSuap);
		
    	intestazione.setProgressivo(1);
    	intestazione.setTotale(1);
    	
    	intestazione.setCodicePratica(pratica.getIdentificativoPratica());
		
		
		//inizio
		AnagraficaImpresa impresa = new AnagraficaImpresa();
		// prendere dalla pratica
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
			pr.setSigla(recapiti.getIdComune() != null ?
				recapiti.getIdComune().getIdProvincia().getCodCatastale() : "");
			pr.setValue(recapiti.getIdComune() != null ?
					recapiti.getIdComune().getIdProvincia().getDescrizione() : ""	);
			indirizzo.setProvincia(pr);
			Comune c = new Comune();
			c.setCodiceCatastale(recapiti.getIdComune() != null ?
					recapiti.getIdComune().getCodCatastale() : "");
			c.setValue(recapiti.getIdComune() != null ?
					recapiti.getIdComune().getDescrizione() : "");
			indirizzo.setComune(c);
			indirizzo.setCap(recapiti.getCap());
			indirizzo.setDenominazioneStradale(recapiti.getIndirizzo() != null ?
					recapiti.getIndirizzo() : "");
			indirizzo.setNumeroCivico(recapiti.getNCivico() != null ?
					recapiti.getNCivico() : "");
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
		
		
		OggettoComunicazione oc = new OggettoComunicazione();	
		// prendere dalla pratica
		oc.setTipoIntervento(TipoIntervento.ALTRO);
		Procedimenti proc = idProcEnte.getIdProc();
		// prendere dalla pratica
		if(proc.getTipoProc().contains("O"))
			oc.setTipoProcedimento("Ordinario");
		else
			oc.setTipoProcedimento("Automatizzato");
		oc.setValue(pratica.getOggettoPratica());
		intestazione.setOggettoPratica(oc);
		it.gov.impresainungiorno.schema.suap.pratica.ProtocolloSUAP protocolloPraticaSuap = new it.gov.impresainungiorno.schema.suap.pratica.ProtocolloSUAP();
		protocolloPraticaSuap.setCodiceAmministrazione(enteSuap.getCodiceAmministrazione());
		protocolloPraticaSuap.setCodiceAoo(enteSuap.getCodiceAoo());
//		protocolloPraticaSuap.setDataRegistrazione(Utils.dateToXmlGregorianCalendar(pratica.getData_prot_suap()));
//		protocolloPraticaSuap.setDataRegistrazione(pratica.getData_prot_suap());
		protocolloPraticaSuap.setNumeroRegistrazione(pratica.getProt_suap());
		
		intestazione.setProtocolloPraticaSuap(protocolloPraticaSuap);
		
		//fine
		
		OggettoCooperazione ocomunicazione = new OggettoCooperazione();
		ocomunicazione.setTipoCooperazione("OINOLTRO");
		ocomunicazione.setValue("Trasmissione pratica n."+ pratica.getIdentificativoPratica());
		intestazione.setOggettoComunicazione(ocomunicazione);
		//prendere dalla pratica
		intestazione.setTestoComunicazione(comunicazione.getContenuto());
		
		it.gov.impresainungiorno.schema.suap.pratica.ProtocolloSUAP protocollo = new it.gov.impresainungiorno.schema.suap.pratica.ProtocolloSUAP();
		protocollo.setCodiceAmministrazione(enteSuap.getCodiceAmministrazione());
		protocollo.setCodiceAoo(enteSuap.getCodiceAoo());
		if (comunicazione.getDataDiProtocollo() != null)
//			protocollo.setDataRegistrazione(Utils.dateToXmlGregorianCalendar(comunicazione.getDataDiProtocollo()));
			protocollo.setDataRegistrazione(comunicazione.getDataDiProtocollo());
		else
			protocollo.setDataRegistrazione(null);
		if (comunicazione.getNumeroDiProtocollo() != null)
			protocollo.setNumeroRegistrazione(comunicazione.getNumeroDiProtocollo());
		else
			protocollo.setNumeroRegistrazione("");
		
		intestazione.setProtocollo(protocollo);
		comunicazioneXml.setIntestazione(intestazione);
		
		//fine
		
		
		
		return comunicazioneXml;
	}
}
