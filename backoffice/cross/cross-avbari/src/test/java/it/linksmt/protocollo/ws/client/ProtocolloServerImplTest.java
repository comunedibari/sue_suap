package it.linksmt.protocollo.ws.client;

import static org.junit.Assert.*;
import it.wego.cross.avbari.linksmt.protocollo.AddAllegatura;
import it.wego.cross.avbari.linksmt.protocollo.AddAllegaturaResponse.Return;
import it.wego.cross.avbari.linksmt.protocollo.Allegato;
import it.wego.cross.avbari.linksmt.protocollo.CompletamentoProtocolloProvvisorio.ProtocolloDaCompletareRequest;
import it.wego.cross.avbari.linksmt.protocollo.CompletamentoProtocolloProvvisorioResponse;
import it.wego.cross.avbari.linksmt.protocollo.ContattoDestinatario;
import it.wego.cross.avbari.linksmt.protocollo.DatiEmail;
import it.wego.cross.avbari.linksmt.protocollo.Destinatario;
import it.wego.cross.avbari.linksmt.protocollo.Documento;
import it.wego.cross.avbari.linksmt.protocollo.GetAllegato.DettaglioAllegatoRequest;
import it.wego.cross.avbari.linksmt.protocollo.GetDestinatariProtocollo.DestinatarioProtocolloRequest;
import it.wego.cross.avbari.linksmt.protocollo.GetMittenteProtocollo.MittenteProtocolloRequest;
import it.wego.cross.avbari.linksmt.protocollo.GetProtocollo.ProtocolloInformazioniRequest;
import it.wego.cross.avbari.linksmt.protocollo.Mittente;
import it.wego.cross.avbari.linksmt.protocollo.MittenteProtUscita;
import it.wego.cross.avbari.linksmt.protocollo.PersonaFisica;
import it.wego.cross.avbari.linksmt.protocollo.ProtocolloServer;
import it.wego.cross.avbari.linksmt.protocollo.ProtocolloServerImplService;
import it.wego.cross.avbari.linksmt.protocollo.ProtocolloUscitaRequest;
import it.wego.cross.avbari.linksmt.protocollo.RichiestaProtocollo;
import it.wego.cross.avbari.linksmt.protocollo.RichiestaProtocolloFasc;
import it.wego.cross.avbari.linksmt.protocollo.RichiestaProtocolloFascResponse;
import it.wego.cross.avbari.linksmt.protocollo.RichiestaProtocolloProvvisorio;
import it.wego.cross.avbari.linksmt.protocollo.RichiestaProtocolloProvvisorioResponse;
import it.wego.cross.avbari.linksmt.protocollo.RichiestaProtocolloResponse;
import it.wego.cross.avbari.linksmt.protocollo.RichiestaProtocolloUscita;
import it.wego.cross.avbari.linksmt.protocollo.RichiestaProtocolloUscitaResponse;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;

import org.apache.commons.io.FileUtils;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class ProtocolloServerImplTest {

    private static final int ID_UTENTE_PROTOCOLLATORE = 17489;
    private static final String USERNAME = "Exprivia2014";
    private static final String PASSWORD = "exprivIa";
    private static final String WSDL_PATH = "http://web99.linksmt.it/adoc/protocollo?wsdl";
    private final String fileName = "C:\\links\\test_doc.txt";

    @Ignore
    @Test
    public void testAllegatura() throws Exception {
        // fail();
        try {
            URL baseUrl = ProtocolloServerImplService.class.getResource(".");
            URL url = new URL(baseUrl, WSDL_PATH);
            QName qName = new QName("http://impl.server.ws.protocollo.linksmt.it/", "ProtocolloServerImplService");

            ProtocolloServerImplService protocolloServerImplService = new ProtocolloServerImplService(url, qName);
            ProtocolloServer protocolloServer = protocolloServerImplService.getProtocolloServerImplPort();
            Map<String, Object> req_ctx = ((BindingProvider) protocolloServer).getRequestContext();
            req_ctx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, WSDL_PATH);
            Map<String, List<String>> headers = new HashMap<String, List<String>>();
            headers.put("Username", Collections.singletonList(USERNAME));
            headers.put("Password", Collections.singletonList(PASSWORD));
            req_ctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
            // ProtocolloServerImplService protocolloServerImplService = new
            // ProtocolloServerImplService();
            AddAllegatura allegatura = new AddAllegatura();
            AddAllegatura.AllegaturaRequest allegaturaRequest = new AddAllegatura.AllegaturaRequest();
            allegaturaRequest.setIdUtente(ID_UTENTE_PROTOCOLLATORE);
            allegaturaRequest.setAmministrazione("e_lmt");
            allegaturaRequest.setAreaOrganizzativaOmogenea("app");
            allegaturaRequest.setAnno("2014");
            allegaturaRequest.setNumeroProtocollo(696);

            File file = new File(fileName);
            byte[] data = FileUtils.readFileToByteArray(file);

            Documento documentoAllegato = new Documento();
            documentoAllegato.setContenuto(data);
            documentoAllegato.setDettaglio("Dettaglio allegato");
            documentoAllegato.setSunto("Sunto allegato");
            documentoAllegato.setTitolo("Titolo allegato");
            documentoAllegato.setNomeFile("test.pdf");

            Allegato allegato = new Allegato();
            allegato.setDocumento(documentoAllegato);
            allegaturaRequest.getAllegati().add(allegato);

            allegatura.setAllegaturaRequest(allegaturaRequest);
            Return response = protocolloServer.addAllegatura(allegaturaRequest);
            System.out.print(response);
            //
            if (response != null && response.getErrore() != null) {
                System.out.print(response.getErrore().getDescrizione());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    @Ignore
    @Test
    public void testRichiestaProtocollo() throws Exception {

        try {
            URL baseUrl = ProtocolloServerImplService.class.getResource(".");
            URL url = new URL(baseUrl, WSDL_PATH);
            QName qName = new QName("http://impl.server.ws.protocollo.linksmt.it/", "ProtocolloServerImplService");
            ProtocolloServerImplService protocolloServerImplService = new ProtocolloServerImplService(url, qName);
            ProtocolloServer protocolloServer = protocolloServerImplService.getProtocolloServerImplPort();
            Map<String, Object> req_ctx = ((BindingProvider) protocolloServer).getRequestContext();
            req_ctx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, WSDL_PATH);
            Map<String, List<String>> headers = new HashMap<String, List<String>>();
            headers.put("Username", Collections.singletonList(USERNAME));
            headers.put("Password", Collections.singletonList(PASSWORD));
            req_ctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
            RichiestaProtocollo.ProtocolloRequest protocolloRequest = new RichiestaProtocollo.ProtocolloRequest();
            protocolloRequest.setAmministrazione("e_lmt");
            protocolloRequest.setAreaOrganizzativaOmogenea("app");
            protocolloRequest.setIdUtente(ID_UTENTE_PROTOCOLLATORE);
            protocolloRequest.setOggetto("Oggetto Protocollo");

            Mittente mittente = new Mittente();
            PersonaFisica persona = new PersonaFisica();
            persona.setCodiceFiscale("QWEQWE34W34W123Q");
            persona.setCognome("Rossi");
            persona.setNome("Mario");
            mittente.setPersonaFisica(persona);
            mittente.setComune("Lecce");
            mittente.setIndirizzo("Via Rocco Scotellaro");
            mittente.setNazione("Italia");
            protocolloRequest.setMittente(mittente);

            Documento documento = new Documento();

            File file = new File(fileName);
            byte[] data = FileUtils.readFileToByteArray(file);
            documento.setContenuto(data);
            documento.setDettaglio("Dettaglio doc principale Jetty");
            documento.setSunto("Sunto doc principale Jetty");
            documento.setTitolo("Titolo doc principale Jetty");
            documento.setNomeFile("JETTY-JAAS-300813-0727-143354.pdf");
            protocolloRequest.setDocumento(documento);

            Documento documentoAllegato = new Documento();
            documentoAllegato.setContenuto(data);
            documentoAllegato.setDettaglio("Dettaglio allegato principale");
            documentoAllegato.setSunto("Sunto allegato principale");
            documentoAllegato.setTitolo("Titolo allegato principale");
            documentoAllegato.setNomeFile("test.pdf");

            Allegato allegato = new Allegato();
            allegato.setDocumento(documentoAllegato);
            protocolloRequest.getAllegati().add(allegato);
            Destinatario destinatario = new Destinatario();
            destinatario.setAmministrazione("e_lmt");
            destinatario.setAreaOrganizzativaOmogenea("app");
            PersonaFisica destinatarioFisico = new PersonaFisica();
            destinatarioFisico.setCodiceFiscale("QWEQWE34W34W123Q");
            destinatarioFisico.setCognome("Pindinelli");
            destinatarioFisico.setNome("Gianluca");
            destinatario.setPersonaFisica(destinatarioFisico);
            protocolloRequest.getDestinatari().add(destinatario);
            RichiestaProtocolloResponse.Return response = protocolloServer.richiestaProtocollo(protocolloRequest);
            System.out.println(response.getNumeroProtocollo());

            if (response != null && response.getErrore() != null) {
                fail(response.getErrore().getDescrizione());
                System.out.println(response.getErrore().getDescrizione());
            }
        } catch (Exception e) {
            fail(e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    @Ignore
    @Test
    public void testRichiestaProtocolloFasc() throws Exception {

        try {
            URL baseUrl = ProtocolloServerImplService.class.getResource(".");
            URL url = new URL(baseUrl, WSDL_PATH);
            QName qName = new QName("http://impl.server.ws.protocollo.linksmt.it/", "ProtocolloServerImplService");
            ProtocolloServerImplService protocolloServerImplService = new ProtocolloServerImplService(url, qName);
            ProtocolloServer protocolloServer = protocolloServerImplService.getProtocolloServerImplPort();
            Map<String, Object> req_ctx = ((BindingProvider) protocolloServer).getRequestContext();
            req_ctx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, WSDL_PATH);
            Map<String, List<String>> headers = new HashMap<String, List<String>>();
            headers.put("Username", Collections.singletonList(USERNAME));
            headers.put("Password", Collections.singletonList(PASSWORD));
            req_ctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);

            RichiestaProtocolloFasc.ProtocolloFascRequest protocolloFascRequest = new RichiestaProtocolloFasc.ProtocolloFascRequest();
            protocolloFascRequest.setAmministrazione("e_lmt");
            protocolloFascRequest.setAreaOrganizzativaOmogenea("app");
            protocolloFascRequest.setIdUtente(ID_UTENTE_PROTOCOLLATORE);
            protocolloFascRequest.setOggetto("Oggetto Protocollo");

            Mittente mittente = new Mittente();
            PersonaFisica persona = new PersonaFisica();
            persona.setCodiceFiscale("QWEQWE34W34W123Q");
            persona.setCognome("Rossi");
            persona.setNome("Mario");
            mittente.setPersonaFisica(persona);
            mittente.setComune("Lecce");
            mittente.setIndirizzo("Via Rocco Scotellaro");
            mittente.setNazione("Italia");
            protocolloFascRequest.setMittente(mittente);

            Documento documento = new Documento();

            File file = new File(fileName);
            byte[] data = FileUtils.readFileToByteArray(file);
            documento.setContenuto(data);
            documento.setDettaglio("Dettaglio doc principale Jetty");
            documento.setSunto("Sunto doc principale Jetty");
            documento.setTitolo("Titolo doc principale Jetty");
            documento.setNomeFile("JETTY-JAAS-300813-0727-143354.pdf");
            protocolloFascRequest.setDocumento(documento);

            Documento documentoAllegato = new Documento();
            documentoAllegato.setContenuto(data);
            documentoAllegato.setDettaglio("Dettaglio allegato principale");
            documentoAllegato.setSunto("Sunto allegato principale");
            documentoAllegato.setTitolo("Titolo allegato principale");
            documentoAllegato.setNomeFile("test.pdf");

            Allegato allegato = new Allegato();
            allegato.setDocumento(documentoAllegato);
            protocolloFascRequest.getAllegati().add(allegato);
            Destinatario destinatario = new Destinatario();
            destinatario.setAmministrazione("e_lmt");
            destinatario.setAreaOrganizzativaOmogenea("app");
            PersonaFisica destinatarioFisico = new PersonaFisica();
            destinatarioFisico.setCodiceFiscale("QWEQWE34W34W123Q");
            destinatarioFisico.setCognome("Bello");
            destinatarioFisico.setNome("Simone");
            destinatario.setPersonaFisica(destinatarioFisico);
            protocolloFascRequest.getDestinatari().add(destinatario);
            protocolloFascRequest.setIdFascicolo("15-F01");
            protocolloFascRequest.setIdTitolario("15");
            RichiestaProtocolloFascResponse.Return response = protocolloServer.richiestaProtocolloFasc(protocolloFascRequest);
            System.out.println(response.getNumeroProtocollo());

            if (response != null && response.getErrore() != null) {
                fail(response.getErrore().getDescrizione());
                System.out.println(response.getErrore().getDescrizione());
            }
        } catch (Exception e) {
            fail(e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    @Ignore
    @Test
    public void testGetAllegato() throws Exception {
        URL baseUrl = ProtocolloServerImplService.class.getResource(".");
        URL url = new URL(baseUrl, WSDL_PATH);
        QName qName = new QName("http://impl.server.ws.protocollo.linksmt.it/", "ProtocolloServerImplService");

        ProtocolloServerImplService protocolloServerImplService = new ProtocolloServerImplService(url, qName);
        ProtocolloServer protocolloServer = protocolloServerImplService.getProtocolloServerImplPort();

        Map<String, Object> req_ctx = ((BindingProvider) protocolloServer).getRequestContext();
        req_ctx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, WSDL_PATH);
        Map<String, List<String>> headers = new HashMap<String, List<String>>();
        headers.put("Username", Collections.singletonList(USERNAME));
        headers.put("Password", Collections.singletonList(PASSWORD));
        req_ctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);

        DettaglioAllegatoRequest dettaglioAllegatoRequest = new DettaglioAllegatoRequest();

        dettaglioAllegatoRequest.setIdentificativo("DOC10008");

        it.wego.cross.avbari.linksmt.protocollo.GetAllegatoResponse.Return mittenteProtocolloResponse = protocolloServer.getAllegato(dettaglioAllegatoRequest);
        assertNotNull(mittenteProtocolloResponse);
        assertNotNull(mittenteProtocolloResponse.getAllegato());
        System.out.println(mittenteProtocolloResponse.getAllegato().getDocumento().getTitolo());
        assertNull(mittenteProtocolloResponse.getErrore());

    }

    /**
     * Richiesta protocollo provvisorio. senza allegati nè documenti
     */
    @Ignore
    @Test
    public void testRichiestaProtocolloProvvisorio() {
        try {
            URL baseUrl = ProtocolloServerImplService.class.getResource(".");
            URL url = new URL(baseUrl, WSDL_PATH);
            QName qName = new QName("http://impl.server.ws.protocollo.linksmt.it/", "ProtocolloServerImplService");
            ProtocolloServerImplService protocolloServerImplService = new ProtocolloServerImplService(url, qName);
            ProtocolloServer protocolloServer = protocolloServerImplService.getProtocolloServerImplPort();
            Map<String, Object> req_ctx = ((BindingProvider) protocolloServer).getRequestContext();
            req_ctx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, WSDL_PATH);
            Map<String, List<String>> headers = new HashMap<String, List<String>>();
            headers.put("Username", Collections.singletonList(USERNAME));
            headers.put("Password", Collections.singletonList(PASSWORD));
            req_ctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
            RichiestaProtocolloProvvisorio.ProtocolloProvvisorioRequest protocolloRequest = new RichiestaProtocolloProvvisorio.ProtocolloProvvisorioRequest();
            protocolloRequest.setAmministrazione("e_lmt");
            protocolloRequest.setAreaOrganizzativaOmogenea("app");
            protocolloRequest.setIdUtente(ID_UTENTE_PROTOCOLLATORE);
            protocolloRequest.setOggetto("Oggetto Protocollo");

            Mittente mittente = new Mittente();
            PersonaFisica persona = new PersonaFisica();
            persona.setCodiceFiscale("QWEQWE34W34W123Q");
            persona.setCognome("Rossi");
            persona.setNome("Mario");
            mittente.setPersonaFisica(persona);
            mittente.setComune("Lecce");
            mittente.setIndirizzo("Via Rocco Scotellaro");
            mittente.setNazione("Italia");
            protocolloRequest.setMittente(mittente);

            Documento documento = new Documento();

            File file = new File(fileName);
            byte[] data = FileUtils.readFileToByteArray(file);
            documento.setContenuto(data);
            documento.setDettaglio("Dettaglio doc principale Jetty");
            documento.setSunto("Sunto doc principale Jetty");
            documento.setTitolo("Titolo doc principale Jetty");
            documento.setNomeFile("JETTY-JAAS-300813-0727-143354.pdf");

            Documento documentoAllegato = new Documento();
            documentoAllegato.setContenuto(data);
            documentoAllegato.setDettaglio("Dettaglio allegato principale");
            documentoAllegato.setSunto("Sunto allegato principale");
            documentoAllegato.setTitolo("Titolo allegato principale");
            documentoAllegato.setNomeFile("test.pdf");

            Allegato allegato = new Allegato();
            allegato.setDocumento(documentoAllegato);
            Destinatario destinatario = new Destinatario();
            destinatario.setAmministrazione("e_lmt");
            destinatario.setAreaOrganizzativaOmogenea("app");
            PersonaFisica destinatarioFisico = new PersonaFisica();
            destinatarioFisico.setCodiceFiscale("QWEQWE34W34W123Q");
            destinatarioFisico.setCognome("Pindinelli");
            destinatarioFisico.setNome("Gianluca");
            destinatario.setPersonaFisica(destinatarioFisico);
            protocolloRequest.getDestinatari().add(destinatario);
            RichiestaProtocolloProvvisorioResponse.Return response = protocolloServer.richiestaProtocolloProvvisorio(protocolloRequest);
            System.out.println(response.getNumeroProtocollo());

            if (response != null && response.getErrore() != null) {
                fail(response.getErrore().getDescrizione());
                System.out.println(response.getErrore().getDescrizione());
            }
        } catch (Exception e) {
            fail(e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    @Ignore
    @Test
    public void testCompletamentoProtocolloProvvisorio() {
        try {
            URL baseUrl = ProtocolloServerImplService.class.getResource(".");
            URL url = new URL(baseUrl, WSDL_PATH);
            QName qName = new QName("http://impl.server.ws.protocollo.linksmt.it/", "ProtocolloServerImplService");
            ProtocolloServerImplService protocolloServerImplService = new ProtocolloServerImplService(url, qName);
            ProtocolloServer protocolloServer = protocolloServerImplService.getProtocolloServerImplPort();
            Map<String, Object> req_ctx = ((BindingProvider) protocolloServer).getRequestContext();
            req_ctx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, WSDL_PATH);
            Map<String, List<String>> headers = new HashMap<String, List<String>>();
            headers.put("Username", Collections.singletonList(USERNAME));
            headers.put("Password", Collections.singletonList(PASSWORD));
            req_ctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
            ProtocolloDaCompletareRequest protocolloRequest = new ProtocolloDaCompletareRequest();
            protocolloRequest.setAmministrazione("e_lmt");
            protocolloRequest.setAnno("2014");
            protocolloRequest.setAreaOrganizzativaOmogenea("app");
            protocolloRequest.setIdUtente(ID_UTENTE_PROTOCOLLATORE);

            Documento documento = new Documento();

            File file = new File(fileName);
            byte[] data = FileUtils.readFileToByteArray(file);
            documento.setContenuto(data);
            documento.setDettaglio("Dettaglio doc principale Jetty");
            documento.setSunto("Sunto doc principale Jetty");
            documento.setTitolo("Titolo doc principale Jetty");
            documento.setNomeFile("JETTY-JAAS-300813-0727-143354.pdf");

            Documento documentoAllegato = new Documento();
            documentoAllegato.setContenuto(data);
            documentoAllegato.setDettaglio("Dettaglio allegato principale");
            documentoAllegato.setSunto("Sunto allegato principale");
            documentoAllegato.setTitolo("Titolo allegato principale");
            documentoAllegato.setNomeFile("test.pdf");
            protocolloRequest.setDocumento(documento);
            Allegato allegato = new Allegato();
            allegato.setDocumento(documentoAllegato);
            Destinatario destinatario = new Destinatario();
            destinatario.setAmministrazione("e_lmt");
            destinatario.setAreaOrganizzativaOmogenea("app");
            PersonaFisica destinatarioFisico = new PersonaFisica();
            destinatarioFisico.setCodiceFiscale("QWEQWE34W34W123Q");
            destinatarioFisico.setCognome("Pindinelli");
            destinatarioFisico.setNome("Gianluca");
            destinatario.setPersonaFisica(destinatarioFisico);
            CompletamentoProtocolloProvvisorioResponse.Return response = protocolloServer.completamentoProtocolloProvvisorio(protocolloRequest);
            if (response != null && response.getErrore() != null) {
                fail(response.getErrore().getDescrizione());
                System.out.println(response.getErrore().getDescrizione());
            }
        } catch (Exception e) {
            fail(e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    @Ignore
    @Test
    public void testGetMittenteProtocollo() {
        try {
            URL baseUrl = ProtocolloServerImplService.class.getResource(".");
            URL url = new URL(baseUrl, WSDL_PATH);
            QName qName = new QName("http://impl.server.ws.protocollo.linksmt.it/", "ProtocolloServerImplService");
            ProtocolloServerImplService protocolloServerImplService = new ProtocolloServerImplService(url, qName);
            ProtocolloServer protocolloServer = protocolloServerImplService.getProtocolloServerImplPort();
            Map<String, Object> req_ctx = ((BindingProvider) protocolloServer).getRequestContext();
            req_ctx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, WSDL_PATH);
            Map<String, List<String>> headers = new HashMap<String, List<String>>();
            headers.put("Username", Collections.singletonList(USERNAME));
            headers.put("Password", Collections.singletonList(PASSWORD));
            req_ctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
            MittenteProtocolloRequest mittenteProtocolloRequest = new MittenteProtocolloRequest();
            mittenteProtocolloRequest.setAnno("2014");
            mittenteProtocolloRequest.setNumeroProtocollo(696);
            it.wego.cross.avbari.linksmt.protocollo.GetMittenteProtocolloResponse.Return mittenteProtocolloResponse = protocolloServer.getMittenteProtocollo(mittenteProtocolloRequest);
            assertNotNull(mittenteProtocolloResponse);
            System.out.println(mittenteProtocolloResponse.getNumeroProtocollo());
        } catch (Exception e) {
            fail(e.getMessage());
            System.out.println(e.getMessage());
        }

    }

    @Ignore
    @Test
    public void testGetDestinatariProtocollo() {
        try {
            URL baseUrl = ProtocolloServerImplService.class.getResource(".");
            URL url = new URL(baseUrl, WSDL_PATH);
            QName qName = new QName("http://impl.server.ws.protocollo.linksmt.it/", "ProtocolloServerImplService");
            ProtocolloServerImplService protocolloServerImplService = new ProtocolloServerImplService(url, qName);
            ProtocolloServer protocolloServer = protocolloServerImplService.getProtocolloServerImplPort();
            Map<String, Object> req_ctx = ((BindingProvider) protocolloServer).getRequestContext();
            req_ctx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, WSDL_PATH);
            Map<String, List<String>> headers = new HashMap<String, List<String>>();
            headers.put("Username", Collections.singletonList(USERNAME));
            headers.put("Password", Collections.singletonList(PASSWORD));
            req_ctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
            DestinatarioProtocolloRequest dRequest = new DestinatarioProtocolloRequest();
            dRequest.setAnno("2014");
            dRequest.setNumeroProtocollo(696);
            it.wego.cross.avbari.linksmt.protocollo.GetDestinatariProtocolloResponse.Return destinatarioProtocolloResponse = protocolloServer.getDestinatariProtocollo(dRequest);
            assertNotNull(destinatarioProtocolloResponse.getAnno());
            assertNotNull(destinatarioProtocolloResponse.getDestinatari());
        } catch (Exception e) {
            fail(e.getMessage());
            System.out.println(e.getMessage());
        }

    }

    @Ignore
    @Test
    public void testGetProtocollo() {
        try {
            URL baseUrl = ProtocolloServerImplService.class.getResource(".");
            URL url = new URL(baseUrl, WSDL_PATH);
            QName qName = new QName("http://impl.server.ws.protocollo.linksmt.it/", "ProtocolloServerImplService");
            ProtocolloServerImplService protocolloServerImplService = new ProtocolloServerImplService(url, qName);
            ProtocolloServer protocolloServer = protocolloServerImplService.getProtocolloServerImplPort();
            Map<String, Object> req_ctx = ((BindingProvider) protocolloServer).getRequestContext();
            req_ctx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, WSDL_PATH);
            Map<String, List<String>> headers = new HashMap<String, List<String>>();
            headers.put("Username", Collections.singletonList(USERNAME));
            headers.put("Password", Collections.singletonList(PASSWORD));
            req_ctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
            ProtocolloInformazioniRequest protocolloInformazioniRequest = new ProtocolloInformazioniRequest();
            protocolloInformazioniRequest.setAnno("2014");
            protocolloInformazioniRequest.setNumeroProtocollo(696);

            it.wego.cross.avbari.linksmt.protocollo.GetProtocolloResponse.Return protocolloResponse = protocolloServer.getProtocollo(protocolloInformazioniRequest);
            assertNotNull(protocolloResponse);
            assertNotNull(protocolloResponse.getNumeroProtocollo());
            System.out.println(protocolloResponse.getNumeroProtocollo());
        } catch (Exception e) {
            fail(e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    @Ignore
    @Test
    public void testProtocolloUscita() {
        try {
            URL baseUrl = ProtocolloServerImplService.class.getResource(".");
            URL url = new URL(baseUrl, WSDL_PATH);
            QName qName = new QName("http://impl.server.ws.protocollo.linksmt.it/", "ProtocolloServerImplService");
            ProtocolloServerImplService protocolloServerImplService = new ProtocolloServerImplService(url, qName);
            ProtocolloServer protocolloServer = protocolloServerImplService.getProtocolloServerImplPort();
            Map<String, Object> req_ctx = ((BindingProvider) protocolloServer).getRequestContext();
            req_ctx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, WSDL_PATH);
            Map<String, List<String>> headers = new HashMap<String, List<String>>();
            headers.put("Username", Collections.singletonList(USERNAME));
            headers.put("Password", Collections.singletonList(PASSWORD));
            req_ctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
            RichiestaProtocolloUscita.ProtocolloUscitaRequest protocolloUscitaRequest = new RichiestaProtocolloUscita.ProtocolloUscitaRequest();
            protocolloUscitaRequest.setAmministrazione("e_lmt");
            protocolloUscitaRequest.setAreaOrganizzativaOmogenea("app");
            protocolloUscitaRequest.setIdUtente(ID_UTENTE_PROTOCOLLATORE);
            protocolloUscitaRequest.setOggetto("Oggetto Protocollo");
            MittenteProtUscita mittente = new MittenteProtUscita();
            mittente.setCognome("Rossi");
            mittente.setNome("Mario");
            mittente.setUo("UO PROVA");
            protocolloUscitaRequest.setMittente(mittente);

            List<ContattoDestinatario> destinatari = new ArrayList<ContattoDestinatario>();
            ContattoDestinatario destinatario = new ContattoDestinatario();
            destinatario.setPecEmail("simone.bello@linksmt.it");
            destinatario.setNome("Simone");
            destinatario.setCognome("Bello");
            //Modalità di spedizione :
            //PECMAIL: 0 | campi obbligatori pecEmail,ragioneSociale o Cognome
            //EMAIL : 1 | campi obbligatori email, ragioneSociale o cognome;
            //CORRIERE : 2 | campi obbligatori ragioneSociale o cognome, nome,indirizzo,comune, nazione;
            //FAX : 3 | campi obbligatori fax, ragioneSociale o cognome;
            //CONSEGNA_MANUALE : 4 | campi obbligatori ragioneSociale o cognome;
            //POSTA_ORDINARIA : 5 | campi obbligatori ragioneSociale o cognome, nome indirizzo, comune;
            //RACCOMANDATA_AR : 6 | campi obbligatori ragioneSociale o cognome, nome, indirizzo, comune, nazione;                        
            destinatario.setModalitaSpedizione(0);
            destinatari.add(destinatario);
            protocolloUscitaRequest.getDestinatari().add(destinatario);

            //se l'invio è impostato a true
            protocolloUscitaRequest.setInvio(true);
//Da valorizzare solo se la lista dei destinatari contiene almeno un contatto di tipo mail o pecmail
            DatiEmail email = new DatiEmail();
            email.setOggetto("Oggetto Mail");
            email.setTesto("Testo Mail");
			//TipoAllegati = 1  o non impostato per Invio Completo;
            //TipoAllegati = 2 per invio del documento originale e l'XML
            //TipoAllegati = 3 per invio del solo PDF	
            email.setTipoAllegati(1);
            protocolloUscitaRequest.setEmail(email);
            Documento documento = new Documento();

            File file = new File(fileName);
            byte[] data = FileUtils.readFileToByteArray(file);
            documento.setContenuto(data);
            documento.setDettaglio("Dettaglio doc principale Simone");
            documento.setSunto("Sunto doc principale Simone");
            documento.setTitolo("Titolo doc principale Simone");
            documento.setNomeFile("Simone123.txt");
            protocolloUscitaRequest.setDocumento(documento);

            RichiestaProtocolloUscitaResponse.Return protocolloResponse = protocolloServer.richiestaProtocolloUscita(protocolloUscitaRequest);
            assertNotNull(protocolloResponse);
            assertNotNull(protocolloResponse.getNumeroProtocollo());
            System.out.println(protocolloResponse.getNumeroProtocollo());
        } catch (Exception e) {
            fail(e.getMessage());
            System.out.println(e.getMessage());
        }
    }
    
}
