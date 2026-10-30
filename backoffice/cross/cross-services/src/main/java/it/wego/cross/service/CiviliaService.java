package it.wego.cross.service;

import java.util.List;

import org.springframework.stereotype.Component;

import it.wego.cross.dto.AllegatoDTO;
import it.wego.cross.entity.DatiCatastali;
import it.wego.cross.entity.Pratica;

@Component
public interface CiviliaService {
	
	public boolean isCivilia(Pratica pratica);
	
	public List<AllegatoDTO> filterAttachmentsByDescription(List<AllegatoDTO> allegatiCivilia, List<AllegatoDTO> allegatiCross, List<AllegatoDTO> allegatiDMS);
	
	public List<DatiCatastali> filterCadastralData(List<DatiCatastali> civilia, List<DatiCatastali> cross, List<DatiCatastali> dms);
	
}