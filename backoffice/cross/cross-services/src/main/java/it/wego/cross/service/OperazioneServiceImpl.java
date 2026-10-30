package it.wego.cross.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.wego.cross.dao.OperazioneDao;
import it.wego.cross.entity.Operazione;

@Service
public class OperazioneServiceImpl implements OperazioneService {

	@Autowired
	private OperazioneDao operazioneDao;

	@Override
	@Transactional
	public void insert(String opCode, String code, String description) throws Exception{
		operazioneDao.insert(opCode, code, description);		
	}
	
	
	@Override
	public Operazione readByCode(String opCode) throws Exception{
		return operazioneDao.readByCode(opCode);	
	}
}
