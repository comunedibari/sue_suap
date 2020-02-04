/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.events.anagrafetributaria.service;

import it.wego.cross.beans.layout.Select;
import it.wego.cross.entity.Anagrafica;
import it.wego.cross.entity.AnagraficaRecapiti;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.LkTipoUnita;
import it.wego.cross.entity.Pratica;
import it.wego.cross.events.anagrafetributaria.bean.AnagraficaDTO;
import it.wego.cross.events.anagrafetributaria.bean.DatiCatastaliDTO;
import it.wego.cross.events.anagrafetributaria.bean.ImpresaDTO;
import it.wego.cross.events.anagrafetributaria.bean.IndirizzoRichiestaDTO;
import it.wego.cross.events.anagrafetributaria.bean.QualificaLookupDTO;
import it.wego.cross.events.anagrafetributaria.entity.AtRecordDettaglio;
import it.wego.cross.xml.anagrafetributaria.commercio.AnagrafeTributariaCommercio;
import it.wego.cross.xml.anagrafetributaria.edilizia.AnagrafeTributariaEdilizia;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Gabriele
 */
@Service
public interface AnagrafeTributariaService {

    public List<QualificaLookupDTO> findLookupQualificaByCondizione(String condizione);

    public List<AnagraficaDTO> findRichiedentiPratica(Pratica pratica);

    public List<AnagraficaDTO> findBeneficiariPratica(Pratica pratica);

    public List<AnagraficaDTO> findProfessionistiPratica(Pratica pratica);

    public List<ImpresaDTO> findImpresePratica(Pratica pratica) throws Exception;

    public List<IndirizzoRichiestaDTO> findIndirizzoRichiesta(Pratica pratica);

    public List<DatiCatastaliDTO> findDatiCatastali(Pratica pratica);

    public List<Select> findAllTipoUnita();

    public List<Select> findAllTipoParticella();

    public List<AnagraficaDTO> findAllAnagraficheByTipologia(Pratica pratica, String tipologia);

    public List<Select> findAllAlbiProfessionali();

    public List<Select> findAllProvincie();

    public List<IndirizzoRichiestaDTO> findIndrizzoSede(List<AnagraficaDTO> imprese);

    public String getDocumentoAnagrafeTributariaEdilizia(AnagrafeTributariaEdilizia anagrafeTributaria) throws Exception;

    public String getDocumentoAnagrafeTributariaCommercio(AnagrafeTributariaCommercio anagrafeTributariaCommercio) throws Exception;

    public void salvaRecordDettaglio(AtRecordDettaglio recordDettaglio) throws Exception;

    public List<AnagraficaRecapiti> findRecapitiByTipologia(Anagrafica anagraficaBeneficiario, String seD);

    public List<Select> findAllEnti();

    public List<Select> findAllComuni();

    public List<AtRecordDettaglio> getRecordDettaglio(String codFornitura, Integer annoRiferimento, Integer idSoggettoObbligato);

    public List<AtRecordDettaglio> findRecordDettaglio(Integer idPratica, String codFornitura);

    public LkTipoUnita findLkTipoUnitaByCod(String tipoUnita);

    public AtRecordDettaglio findRecordDettaglioById(Integer idDettaglio);

    public LkComuni findComuneSede(Integer idSoggetto);

    public Enti findEnteById(Integer idSoggettoObbligato);

    public List<Select> findAllTipoCatasto();
}
