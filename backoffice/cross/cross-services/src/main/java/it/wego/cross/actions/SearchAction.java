package it.wego.cross.actions;

import it.wego.cross.dto.CittadinanzaDTO;
import it.wego.cross.dto.ComuneDTO;
import it.wego.cross.dto.EnteDTO;
import it.wego.cross.dto.EventoDTO;
import it.wego.cross.dto.ProvinciaDTO;
import it.wego.cross.dto.UtenteMinifiedDTO;
import it.wego.cross.dto.search.DestinatariDTO;
import it.wego.cross.dto.search.FormaGiuridicaDTO;
import it.wego.cross.dto.search.TipoCollegioDTO;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.Pratica;
import it.wego.cross.serializer.EntiSerializer;
import it.wego.cross.service.ComuniService;
import it.wego.cross.service.EntiService;
import it.wego.cross.service.PraticheService;
import it.wego.cross.service.ProcedimentiService;
import it.wego.cross.service.SearchService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author CS
 */
@Component
public class SearchAction {

    @Autowired
    private ComuniService comuniService;
    @Autowired
    private ProcedimentiService procedimentiService;
    @Autowired
    private EntiService entiService;
    @Autowired
    private PraticheService praticheService;
    @Autowired
    private SearchService searchService;

    



}
