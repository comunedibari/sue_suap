/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import it.gov.impresainungiorno.schema.suap.pratica.RiepilogoPraticaSUAP;
import it.wego.cross.dto.ComboDTO;
import it.wego.cross.dto.comunica.ComuneRiferimentoDTO;
import it.wego.cross.dto.comunica.SportelloDTO;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.LkFormeGiuridiche;
import it.wego.cross.entity.LkNazionalita;
import it.wego.cross.entity.LkProvincie;
import it.wego.cross.entity.view.ProcedimentiLocalizzatiView;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author GabrieleM
 */
@Service
public interface ComunicaService {

    public List<ComboDTO> serializeProvincie(List<LkProvincie> provincie);

    public List<ComboDTO> serializeFormeGiuridiche(List<LkFormeGiuridiche> formeGiuridiche);

    public List<ComboDTO> serializeNazionalita(List<LkNazionalita> nazionalita);

    public List<ComboDTO> serializeEnti(List<Enti> enti);

    public List<ComboDTO> serializeProcedimenti(List<ProcedimentiLocalizzatiView> procedimentiSportello);

    public ComuneRiferimentoDTO getComuneRiferimento(RiepilogoPraticaSUAP riepilogoPraticaSuap) throws Exception;

    public SportelloDTO getSportelloDestinazione(RiepilogoPraticaSUAP riepilogoPraticaSuap) throws Exception;
}
