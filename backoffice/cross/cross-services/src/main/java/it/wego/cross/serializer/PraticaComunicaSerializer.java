/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.serializer;

import it.gov.impresainungiorno.schema.suap.pratica.AnagraficaImpresa;
import it.gov.impresainungiorno.schema.suap.pratica.AnagraficaRappresentante;
import it.gov.impresainungiorno.schema.suap.pratica.EstremiDichiarante;
import it.gov.impresainungiorno.schema.suap.pratica.EstremiSuap;
import it.gov.impresainungiorno.schema.suap.pratica.RiepilogoPraticaSUAP;
import it.wego.cross.dto.comunica.DichiaranteDTO;
import it.wego.cross.dto.comunica.ImpresaDTO;
import it.wego.cross.dto.comunica.LegaleRappresentanteDTO;
import it.wego.cross.dto.comunica.PraticaComunicaDTO;
import it.wego.cross.dto.comunica.UfficioDTO;
import it.wego.cross.utils.Utils;
import java.util.Date;

/**
 *
 * @author giuseppe
 */
public class PraticaComunicaSerializer {

    public static PraticaComunicaDTO serialize(RiepilogoPraticaSUAP riepilogoPraticaSuap) {
        PraticaComunicaDTO praticaComunica = new PraticaComunicaDTO();
        String pec = riepilogoPraticaSuap.getIntestazione().getDomicilioElettronico();
        if (riepilogoPraticaSuap.getIntestazione().getUfficioDestinatario() != null) {
            UfficioDTO ufficio = serializeUfficio(riepilogoPraticaSuap.getIntestazione().getUfficioDestinatario());
            praticaComunica.setUfficio(ufficio);
        }
        if (riepilogoPraticaSuap.getIntestazione().getImpresa() != null) {
            ImpresaDTO impresa = serializeImpresa(riepilogoPraticaSuap.getIntestazione().getImpresa());
            impresa.setPec(pec);
            praticaComunica.setImpresa(impresa);
            if (riepilogoPraticaSuap.getIntestazione().getImpresa().getLegaleRappresentante() != null) {
                LegaleRappresentanteDTO legaleRappresentante = serializeLegaleRappresentante(riepilogoPraticaSuap.getIntestazione().getImpresa().getLegaleRappresentante());
                legaleRappresentante.setPec(pec);
                praticaComunica.setLegaleRappresentante(legaleRappresentante);
            }
        }
        if (riepilogoPraticaSuap.getIntestazione().getDichiarante() != null) {
            DichiaranteDTO dichiarante = serializeDichiarante(riepilogoPraticaSuap.getIntestazione().getDichiarante());
            praticaComunica.setDichiarante(dichiarante);
            if (riepilogoPraticaSuap.getIntestazione().getOggettoComunicazione() != null) {
                praticaComunica.setOggetto(riepilogoPraticaSuap.getIntestazione().getOggettoComunicazione().getValue());
                praticaComunica.setTipoProcedimento(riepilogoPraticaSuap.getIntestazione().getOggettoComunicazione().getTipoProcedimento());
                if (riepilogoPraticaSuap.getIntestazione().getOggettoComunicazione().getTipoIntervento() != null) {
                    praticaComunica.setInterventoOrigine(riepilogoPraticaSuap.getIntestazione().getOggettoComunicazione().getTipoIntervento().value());
                }
            }
        }
        return praticaComunica;
    }

    private static UfficioDTO serializeUfficio(EstremiSuap ufficioDestinatario) {
        UfficioDTO ufficio = new UfficioDTO();
        ufficio.setCodiceAmministrazione(ufficioDestinatario.getCodiceAmministrazione());
        ufficio.setCodiceAoo(ufficioDestinatario.getCodiceAoo());
        if (ufficioDestinatario.getIdentificativoSuap() != null) {
            ufficio.setIdentificativoSuap(ufficioDestinatario.getIdentificativoSuap().toString());
        }
        ufficio.setDescrizione(ufficioDestinatario.getValue());
        return ufficio;
    }

    private static ImpresaDTO serializeImpresa(AnagraficaImpresa impresa) {
        ImpresaDTO dto = new ImpresaDTO();
        if (impresa.getFormaGiuridica() != null) {
            dto.setCodFormaGiuridica(impresa.getFormaGiuridica().getCodice());
            dto.setDesFormaGiuridica(impresa.getFormaGiuridica().getValue());
            dto.setFormaGiuridicaOrigine(impresa.getFormaGiuridica().getValue());
        }
        dto.setRagioneSociale(impresa.getRagioneSociale());
        dto.setCodiceFiscale(impresa.getCodiceFiscale());
        dto.setPartitaIva(impresa.getPartitaIva());

        if (impresa.getCodiceREA() != null) {
            dto.setProvinciaRea(impresa.getCodiceREA().getProvincia());
            dto.setProvinciaReaOrigine(impresa.getCodiceREA().getProvincia());
            if (impresa.getCodiceREA().getDataIscrizione() != null) {
                Date data = Utils.xmlGregorianCalendarToDate(impresa.getCodiceREA().getDataIscrizione());
                String dataIscrizione = Utils.convertDataToString(data);
                dto.setDataIscrizione(dataIscrizione);
            }
            dto.setNumeroRea(impresa.getCodiceREA().getValue());
        }

        if (impresa.getIndirizzo() != null) {
            if (impresa.getIndirizzo().getProvincia() != null) {
                dto.setDesProvinciaSede(impresa.getIndirizzo().getProvincia().getValue());
                dto.setCodCatastaleProvinciaSede(impresa.getIndirizzo().getProvincia().getCodiceIstat());
            }
            if (impresa.getIndirizzo().getComune() != null) {
                dto.setDesComuneSede(impresa.getIndirizzo().getComune().getValue());
                dto.setCodCatastaleComuneSede(impresa.getIndirizzo().getComune().getCodiceCatastale());
            }
            dto.setCapSede(impresa.getIndirizzo().getCap());
            dto.setIndirizzoSede(impresa.getIndirizzo().getToponimo() + " "+impresa.getIndirizzo().getDenominazioneStradale());
            dto.setCivicoSede(impresa.getIndirizzo().getNumeroCivico());
            if (impresa.getIndirizzo().getTelefono() != null && !impresa.getIndirizzo().getTelefono().isEmpty()) {
                dto.setTelefono(impresa.getIndirizzo().getTelefono().get(0).getValue());
            }
        }

        return dto;
    }

    private static LegaleRappresentanteDTO serializeLegaleRappresentante(AnagraficaRappresentante legaleRappresentante) {
        LegaleRappresentanteDTO rappresentante = new LegaleRappresentanteDTO();
        rappresentante.setCognome(legaleRappresentante.getCognome());
        rappresentante.setNome(legaleRappresentante.getNome());
        rappresentante.setCodiceFiscale(legaleRappresentante.getCodiceFiscale());
        rappresentante.setPartitaIva(legaleRappresentante.getPartitaIva());
        if (legaleRappresentante.getNazionalita() != null) {
            rappresentante.setNazionalita(legaleRappresentante.getNazionalita().getValue());
            rappresentante.setNazionalitaOrigine(legaleRappresentante.getNazionalita().getValue());
        }
        return rappresentante;
    }

    private static DichiaranteDTO serializeDichiarante(EstremiDichiarante dichiarante) {
        DichiaranteDTO dto = new DichiaranteDTO();
        dto.setCognome(dichiarante.getCognome());
        dto.setNome(dichiarante.getNome());
        dto.setCodiceFiscale(dichiarante.getCodiceFiscale());
        dto.setPec(dichiarante.getPec());
        dto.setTelefono(dichiarante.getTelefono());
        if (dichiarante.getNazionalita() != null) {
            dto.setNazionalita(dichiarante.getNazionalita().getValue());
            dto.setNazionalitaOrigine(dichiarante.getNazionalita().getValue());
        }
        dto.setPartitaIva(dichiarante.getPartitaIva());
        return dto;
    }
}
