/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import org.springframework.stereotype.Service;

import it.wego.cross.dao.UsefulDao;

/**
 *
 * @author giuseppe
 */
@Service
public class UsefulServiceImpl {

    private UsefulDao userfulDao;

    public UsefulServiceImpl(UsefulDao userfulDao) {
		super();
		this.userfulDao = userfulDao;
	}

	public void flush() throws Exception {
        userfulDao.flush();
    }

    public void update(Object entity) throws Exception {
        userfulDao.update(entity);
    }
    
    public void refresh(Object entity) throws Exception {
        userfulDao.refresh(entity);
    }
}
