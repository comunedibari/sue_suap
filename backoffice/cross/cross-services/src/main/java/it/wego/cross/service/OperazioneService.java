package it.wego.cross.service;

import it.wego.cross.entity.Operazione;

public interface OperazioneService {

	public void insert(String opCode, String code, String description) throws Exception;

	public Operazione readByCode(String opCode) throws Exception;


}
