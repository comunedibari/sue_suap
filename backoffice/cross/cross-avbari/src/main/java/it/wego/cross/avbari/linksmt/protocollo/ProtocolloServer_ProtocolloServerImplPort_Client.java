
package it.wego.cross.avbari.linksmt.protocollo;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.3.9
 * 2015-03-19T15:16:32.193+01:00
 * Generated source version: 2.3.9
 * 
 */
public final class ProtocolloServer_ProtocolloServerImplPort_Client {

    private static final QName SERVICE_NAME = new QName("http://impl.server.ws.protocollo.linksmt.it/", "ProtocolloServerImplService");

    private ProtocolloServer_ProtocolloServerImplPort_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = ProtocolloServerImplService.WSDL_LOCATION;
        if (args.length > 0) { 
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
      
        ProtocolloServerImplService ss = new ProtocolloServerImplService(wsdlURL, SERVICE_NAME);
        ProtocolloServer port = ss.getProtocolloServerImplPort();  
        
        {
        System.out.println("Invoking inoltraProtocollo...");
        it.wego.cross.avbari.linksmt.protocollo.InoltraProtocollo.InoltroProtocolloRequest _inoltraProtocollo_inoltroProtocolloRequest = null;
        it.wego.cross.avbari.linksmt.protocollo.InoltraProtocolloResponse.Return _inoltraProtocollo__return = port.inoltraProtocollo(_inoltraProtocollo_inoltroProtocolloRequest);
        System.out.println("inoltraProtocollo.result=" + _inoltraProtocollo__return);


        }
        {
        System.out.println("Invoking completamentoProtocolloProvvisorio...");
        it.wego.cross.avbari.linksmt.protocollo.CompletamentoProtocolloProvvisorio.ProtocolloDaCompletareRequest _completamentoProtocolloProvvisorio_protocolloDaCompletareRequest = null;
        it.wego.cross.avbari.linksmt.protocollo.CompletamentoProtocolloProvvisorioResponse.Return _completamentoProtocolloProvvisorio__return = port.completamentoProtocolloProvvisorio(_completamentoProtocolloProvvisorio_protocolloDaCompletareRequest);
        System.out.println("completamentoProtocolloProvvisorio.result=" + _completamentoProtocolloProvvisorio__return);


        }
        {
        System.out.println("Invoking richiestaProtocolloUscitaFasc...");
        it.wego.cross.avbari.linksmt.protocollo.RichiestaProtocolloUscitaFasc.ProtocolloUscitaFascRequest _richiestaProtocolloUscitaFasc_protocolloUscitaFascRequest = null;
        it.wego.cross.avbari.linksmt.protocollo.RichiestaProtocolloUscitaFascResponse.Return _richiestaProtocolloUscitaFasc__return = port.richiestaProtocolloUscitaFasc(_richiestaProtocolloUscitaFasc_protocolloUscitaFascRequest);
        System.out.println("richiestaProtocolloUscitaFasc.result=" + _richiestaProtocolloUscitaFasc__return);


        }
        {
        System.out.println("Invoking richiestaProtocolloProvvisorio...");
        it.wego.cross.avbari.linksmt.protocollo.RichiestaProtocolloProvvisorio.ProtocolloProvvisorioRequest _richiestaProtocolloProvvisorio_protocolloProvvisorioRequest = null;
        it.wego.cross.avbari.linksmt.protocollo.RichiestaProtocolloProvvisorioResponse.Return _richiestaProtocolloProvvisorio__return = port.richiestaProtocolloProvvisorio(_richiestaProtocolloProvvisorio_protocolloProvvisorioRequest);
        System.out.println("richiestaProtocolloProvvisorio.result=" + _richiestaProtocolloProvvisorio__return);


        }
        {
        System.out.println("Invoking richiestaProtocolloUscita...");
        it.wego.cross.avbari.linksmt.protocollo.RichiestaProtocolloUscita.ProtocolloUscitaRequest _richiestaProtocolloUscita_protocolloUscitaRequest = null;
        it.wego.cross.avbari.linksmt.protocollo.RichiestaProtocolloUscitaResponse.Return _richiestaProtocolloUscita__return = port.richiestaProtocolloUscita(_richiestaProtocolloUscita_protocolloUscitaRequest);
        System.out.println("richiestaProtocolloUscita.result=" + _richiestaProtocolloUscita__return);


        }
        {
        System.out.println("Invoking richiestaProtocolloFasc...");
        it.wego.cross.avbari.linksmt.protocollo.RichiestaProtocolloFasc.ProtocolloFascRequest _richiestaProtocolloFasc_protocolloFascRequest = null;
        it.wego.cross.avbari.linksmt.protocollo.RichiestaProtocolloFascResponse.Return _richiestaProtocolloFasc__return = port.richiestaProtocolloFasc(_richiestaProtocolloFasc_protocolloFascRequest);
        System.out.println("richiestaProtocolloFasc.result=" + _richiestaProtocolloFasc__return);


        }
        {
        System.out.println("Invoking getAllegato...");
        it.wego.cross.avbari.linksmt.protocollo.GetAllegato.DettaglioAllegatoRequest _getAllegato_dettaglioAllegatoRequest = null;
        it.wego.cross.avbari.linksmt.protocollo.GetAllegatoResponse.Return _getAllegato__return = port.getAllegato(_getAllegato_dettaglioAllegatoRequest);
        System.out.println("getAllegato.result=" + _getAllegato__return);


        }
        {
        System.out.println("Invoking addAllegatura...");
        it.wego.cross.avbari.linksmt.protocollo.AddAllegatura.AllegaturaRequest _addAllegatura_allegaturaRequest = null;
        it.wego.cross.avbari.linksmt.protocollo.AddAllegaturaResponse.Return _addAllegatura__return = port.addAllegatura(_addAllegatura_allegaturaRequest);
        System.out.println("addAllegatura.result=" + _addAllegatura__return);


        }
        {
        System.out.println("Invoking getDestinatariProtocollo...");
        it.wego.cross.avbari.linksmt.protocollo.GetDestinatariProtocollo.DestinatarioProtocolloRequest _getDestinatariProtocollo_destinatarioProtocolloRequest = null;
        it.wego.cross.avbari.linksmt.protocollo.GetDestinatariProtocolloResponse.Return _getDestinatariProtocollo__return = port.getDestinatariProtocollo(_getDestinatariProtocollo_destinatarioProtocolloRequest);
        System.out.println("getDestinatariProtocollo.result=" + _getDestinatariProtocollo__return);


        }
        {
        System.out.println("Invoking richiestaProtocollo...");
        it.wego.cross.avbari.linksmt.protocollo.RichiestaProtocollo.ProtocolloRequest _richiestaProtocollo_protocolloRequest = null;
        it.wego.cross.avbari.linksmt.protocollo.RichiestaProtocolloResponse.Return _richiestaProtocollo__return = port.richiestaProtocollo(_richiestaProtocollo_protocolloRequest);
        System.out.println("richiestaProtocollo.result=" + _richiestaProtocollo__return);


        }
        {
        System.out.println("Invoking getProtocollo...");
        it.wego.cross.avbari.linksmt.protocollo.GetProtocollo.ProtocolloInformazioniRequest _getProtocollo_protocolloInformazioniRequest = null;
        it.wego.cross.avbari.linksmt.protocollo.GetProtocolloResponse.Return _getProtocollo__return = port.getProtocollo(_getProtocollo_protocolloInformazioniRequest);
        System.out.println("getProtocollo.result=" + _getProtocollo__return);


        }
        {
        System.out.println("Invoking getMittenteProtocollo...");
        it.wego.cross.avbari.linksmt.protocollo.GetMittenteProtocollo.MittenteProtocolloRequest _getMittenteProtocollo_mittenteProtocolloRequest = null;
        it.wego.cross.avbari.linksmt.protocollo.GetMittenteProtocolloResponse.Return _getMittenteProtocollo__return = port.getMittenteProtocollo(_getMittenteProtocollo_mittenteProtocolloRequest);
        System.out.println("getMittenteProtocollo.result=" + _getMittenteProtocollo__return);


        }

        System.exit(0);
    }

}
