/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.webservices.cxf;

/**
 *
 * @author piergiorgio
 */
import it.wego.cross.actions.ErroriAction;
import it.wego.cross.actions.PraticheAction;
import it.wego.cross.constants.Constants;
import it.wego.cross.constants.Error;
import it.wego.cross.dao.DatiCatastaliDao;
import it.wego.cross.dao.LookupDao;
import it.wego.cross.dao.ProcedimentiDao;
import it.wego.cross.dao.ProcessiDao;
import it.wego.cross.dao.TemplateDao;
import it.wego.cross.dto.AllegatoRicezioneDTO;
import it.wego.cross.dto.ErroreDTO;
import it.wego.cross.entity.Allegati;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.EventiTemplate;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.LkTipoSistemaCatastale;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.Procedimenti;
import it.wego.cross.entity.ProcedimentiEnti;
import it.wego.cross.entity.Processi;
import it.wego.cross.entity.ProcessiEventi;
import it.wego.cross.entity.Staging;
import it.wego.cross.plugins.aec.AeCGestionePratica;
import it.wego.cross.plugins.aec.ConcessioniAutorizzazioniNamespaceContext;
import it.wego.cross.plugins.pratica.GestionePratica;
import it.wego.cross.serializer.AllegatiSerializer;
import it.wego.cross.service.EntiService;
import it.wego.cross.service.PluginService;
import it.wego.cross.service.ProcedimentiService;
import it.wego.cross.service.WorkFlowService;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.PraticaUtils;
import it.wego.cross.utils.Utils;
import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.jws.WebService;
import javax.xml.namespace.NamespaceContext;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.InputSource;

/**
 * This class was generated by Apache CXF 2.5.2 2012-11-15T16:48:22.619+01:00
 * Generated source version: 2.5.2
 *
 */
@Component("peoplePraticheService")
@WebService(serviceName = "PeoplePraticheService",
portName = "PeoplePraticheService",
targetNamespace = "http://webservice.backend.people.it/",
wsdlLocation = "PeoplePraticheService.wsdl",
endpointInterface = "it.wego.cross.webservices.cxf.PeoplePraticheService")
public class PeoplePraticheServiceImpl implements PeoplePraticheService {

    @Override
    public java.lang.String process(java.lang.String data) {

        return "success";

    }
}
