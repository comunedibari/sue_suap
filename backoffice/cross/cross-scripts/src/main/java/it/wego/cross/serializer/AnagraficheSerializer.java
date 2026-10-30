package it.wego.cross.serializer;

import java.util.ArrayList;
import java.util.List;

import it.wego.cross.dto.AnagraficaDTO;
import it.wego.cross.dto.ComuneDTO;
import it.wego.cross.dto.ProvinciaDTO;
import it.wego.cross.dto.RecapitoDTO;
import it.wego.cross.entity.Anagrafica;
import it.wego.cross.entity.AnagraficaRecapiti;
import it.wego.cross.utils.Utils;

public class AnagraficheSerializer {

	
	public static AnagraficaDTO serializeAnagrafica(Anagrafica anagraficaEntity) {
        AnagraficaDTO anagraficaDTO = new AnagraficaDTO();
        anagraficaDTO.setIdAnagrafica(anagraficaEntity.getIdAnagrafica());
        anagraficaDTO.setTipoAnagrafica(String.valueOf(anagraficaEntity.getTipoAnagrafica()));
        anagraficaDTO.setIdTipoPersona(String.valueOf(anagraficaEntity.getTipoAnagrafica()));
        //^^cs aggiunta
        //anagraficaDTO.setVarianteAnagrafica(anagraficaEntity.getVarianteAnagrafica());
        anagraficaDTO.setVarianteAnagrafica(anagraficaEntity.getVarianteAnagrafica());
        anagraficaDTO.setNome(anagraficaEntity.getNome());
        anagraficaDTO.setCognome(anagraficaEntity.getCognome());
        anagraficaDTO.setCodiceFiscale(anagraficaEntity.getCodiceFiscale());
        anagraficaDTO.setDenominazione(anagraficaEntity.getDenominazione());
        anagraficaDTO.setPartitaIva(anagraficaEntity.getPartitaIva());
        anagraficaDTO.setFlgIndividuale(String.valueOf(anagraficaEntity.getFlgIndividuale()));
        if (anagraficaEntity.getIdFormaGiuridica() != null) {
            anagraficaDTO.setIdFormaGiuridica(anagraficaEntity.getIdFormaGiuridica().getIdFormeGiuridiche());
            anagraficaDTO.setDesFormaGiuridica(anagraficaEntity.getIdFormaGiuridica().getDescrizione());
        }
        anagraficaDTO.setDataNascita(anagraficaEntity.getDataNascita());
        if (anagraficaEntity.getIdCittadinanza() != null) {
            anagraficaDTO.setIdCittadinanza(anagraficaEntity.getIdCittadinanza().getIdCittadinanza());
            anagraficaDTO.setDesCittadinanza(anagraficaEntity.getIdCittadinanza().getDescrizione());
        }
        if (anagraficaEntity.getIdNazionalita() != null) {
            anagraficaDTO.setIdNazionalita(anagraficaEntity.getIdNazionalita().getIdNazionalita());
            anagraficaDTO.setDesNazionalita(anagraficaEntity.getIdNazionalita().getDescrizione());
        }
        if (anagraficaEntity.getIdComuneNascita() != null) {
            ComuneDTO comune = LuoghiSerializer.serialize(anagraficaEntity.getIdComuneNascita());
            anagraficaDTO.setComuneNascita(comune);
//            anagraficaDTO.getComuneNascita().setDescrizione(anagraficaEntity.getIdComuneNascita().getDescrizione());
        }
        anagraficaDTO.setLocalitaNascita(anagraficaEntity.getLocalitaNascita());
        if (anagraficaEntity.getSesso() != null) {
            anagraficaDTO.setSesso(Utils.Char(anagraficaEntity.getSesso()));
        }
        if (anagraficaEntity.getIdTipoCollegio() != null) {
            anagraficaDTO.setCodTipoCollegio(anagraficaEntity.getIdTipoCollegio().getIdTipoCollegio());
            anagraficaDTO.setDesTipoCollegio(anagraficaEntity.getIdTipoCollegio().getDescrizione());
        }
        anagraficaDTO.setNumeroIscrizione(anagraficaEntity.getNumeroIscrizione());
        anagraficaDTO.setDataIscrizione(anagraficaEntity.getDataIscrizione());
        if (anagraficaEntity.getIdProvinciaIscrizione() != null) {
//            if (anagraficaDTO.getProvinciaIscrizione() == null) {
//                anagraficaDTO.setProvinciaIscrizione(new ProvinciaDTO());
//            } else {
            ProvinciaDTO provincia = LuoghiSerializer.serialize(anagraficaEntity.getIdProvinciaIscrizione());
            anagraficaDTO.setProvinciaIscrizione(provincia);
//            }
//            anagraficaDTO.getProvinciaIscrizione().setIdProvincie(anagraficaEntity.getIdProvinciaIscrizione().getIdProvincie());
//            anagraficaDTO.getProvinciaIscrizione().setDescrizione(anagraficaEntity.getIdProvinciaIscrizione().getDescrizione());
//            anagraficaDTO.getProvinciaIscrizione().setCodCatastale(anagraficaEntity.getIdProvinciaIscrizione().getCodCatastale());
//            anagraficaDTO.getProvinciaIscrizione().setFlgInfocamere(Utils.flag(anagraficaEntity.getIdProvinciaIscrizione().getFlgInfocamere()));
        }
        if (anagraficaEntity.getIdProvinciaCciaa() != null) {
            ProvinciaDTO provincia = LuoghiSerializer.serialize(anagraficaEntity.getIdProvinciaCciaa());
            anagraficaDTO.setProvinciaCciaa(provincia);
        }
        if (anagraficaEntity.getFlgAttesaIscrizioneRi() != null) {
            anagraficaDTO.setFlgAttesaIscrizioneRi(String.valueOf(anagraficaEntity.getFlgAttesaIscrizioneRi()));
        }
        anagraficaDTO.setDataIscrizioneRi(anagraficaEntity.getDataIscrizioneRi());
        anagraficaDTO.setnIscrizioneRi(anagraficaEntity.getNIscrizioneRi());
        if (anagraficaEntity.getFlgAttesaIscrizioneRea() != null) {
            anagraficaDTO.setFlgAttesaIscrizioneRea(String.valueOf(anagraficaEntity.getFlgAttesaIscrizioneRea()));
        }
        anagraficaDTO.setDataIscrizioneRea(anagraficaEntity.getDataIscrizioneRea());
        anagraficaDTO.setnIscrizioneRea(anagraficaEntity.getNIscrizioneRea());
        if (anagraficaEntity.getIdFormaGiuridica() != null) {
            anagraficaDTO.setDesFormaGiuridica(anagraficaEntity.getIdFormaGiuridica().getDescrizione());
        }

        List<RecapitoDTO> recapitiDto = new ArrayList<RecapitoDTO>();
        if (anagraficaEntity.getAnagraficaRecapitiList() != null && anagraficaEntity.getAnagraficaRecapitiList().size() > 0) {
            for (AnagraficaRecapiti r : anagraficaEntity.getAnagraficaRecapitiList()) {
                RecapitoDTO recapito = RecapitiSerializer.serialize(r.getIdRecapito(), r.getIdTipoIndirizzo());
                recapitiDto.add(recapito);
            }
        }
        anagraficaDTO.setRecapiti(recapitiDto);

        return anagraficaDTO;
    }

}
