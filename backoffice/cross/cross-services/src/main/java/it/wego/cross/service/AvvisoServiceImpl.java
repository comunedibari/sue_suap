package it.wego.cross.service;

import it.wego.cross.dao.AvvisoDao;
import it.wego.cross.entity.Avviso;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AvvisoServiceImpl implements AvvisoService {

	@Autowired
	private AvvisoDao avvisoDao;

	@Override
	@Transactional
	public void creaAvviso(Avviso avviso) throws Exception {
		avvisoDao.insert(avviso);		
	}
	
	@Override
	@Transactional
	public void updateAvviso(Avviso avviso) throws Exception {
		avvisoDao.update(avviso);		
	}
	
	@Override
	@Transactional
	public void deleteAvviso(Avviso avviso) throws Exception {
		avvisoDao.delete(avviso);		
	}
	
	@Override
	public List<Avviso> findAllAvvisi() throws Exception {
		return avvisoDao.findAll();		
	}
	
	@Override
	public List<Avviso> findAvvisiNonScaduti(Date dataAttuale) throws Exception {
		return avvisoDao.findAvvisiNonScaduti(dataAttuale);		
	}
	
	@Override
	public Avviso findbyIdAvvisi(int idAvviso) throws Exception {
		return avvisoDao.findbyIdAvvisi(idAvviso);		
	}

}
