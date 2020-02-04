/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import it.wego.cross.dao.UsefulDao;
import java.util.ArrayList;
import java.util.List;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author giuseppe
 */
@Service
public class UsefulServiceImpl implements UsefulService {

    @Autowired
    private UsefulDao userfulDao;
    @Autowired
    private Mapper mapper;

    @Override
    public void flush() throws Exception {
        userfulDao.flush();
    }

    @Override
    public void update(Object entity) throws Exception {
        userfulDao.update(entity);
    }
    
    @Override
    public void refresh(Object entity) throws Exception {
        userfulDao.refresh(entity);
    }

    @Override
    public <T, U> List<U> map(List<T> source, final Class<U> destType) {
        final List<U> dest = new ArrayList();
        for (T element : source) {
            dest.add(mapper.map(element, destType));
        }
        return dest;
    }

}
