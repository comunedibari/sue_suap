package it.wego.cross.utils;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.wego.cross.dao.ConfigurationDao;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.Pratica;

@Component
public class ComponentUtils {
	
	@Autowired
	private ConfigurationDao configurationDao;

	public String generateFilePath(String genericPath, LkComuni town, Integer year) {
//		Configuration placeholder = configurationDao.getConfiguration(SessionConstants.ATTACHMENTS_PLACEHOLDER, null, null);		
//		return Utils.generateFilePath(genericPath, placeholder.getValue(), town.getDescrizione(), year != null? year: Calendar.getInstance().get(Calendar.YEAR));
		return genericPath;
	}	

	public String generateFilePath(String genericPath, LkComuni town, Date date) {
//		Configuration placeholder = configurationDao.getConfiguration(SessionConstants.ATTACHMENTS_PLACEHOLDER, null, null);
//		Integer year = null;
//		if(date != null) {
//			Calendar cal = Calendar.getInstance();
//			cal.setTime(date);
//			year = cal.get(Calendar.YEAR);
//		}
//		return Utils.generateFilePath(genericPath, placeholder.getValue(), town.getDescrizione(), year != null? year: Calendar.getInstance().get(Calendar.YEAR));
		return genericPath;
	}	
	
	public String generateFilePath(String genericPath, LkComuni town) {
//		Configuration placeholder = configurationDao.getConfiguration(SessionConstants.ATTACHMENTS_PLACEHOLDER, null, null);		
//		return Utils.generateFilePath(genericPath, placeholder.getValue(), town.getDescrizione(), Calendar.getInstance().get(Calendar.YEAR));
		return genericPath;
	}	
	/**
	 * get path using paperwork and current year
	 * @param currentPath
	 * @param configPathName
	 * @param ente
	 * @param comune
	 * @return
	 * @throws IOException
	 */
	public String generateFilePath(String genericPath, Pratica paperwork) {
//		Configuration placeholder = configurationDao.getConfiguration(SessionConstants.ATTACHMENTS_PLACEHOLDER, null, null);		
//		return Utils.generateFilePath(genericPath, placeholder.getValue(), paperwork.getIdComune().getDescrizione(), Calendar.getInstance().get(Calendar.YEAR));
		return genericPath;
	}	

	
}
