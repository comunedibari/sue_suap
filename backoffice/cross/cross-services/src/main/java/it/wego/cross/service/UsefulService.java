/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author giuseppe
 */
@Service
public interface UsefulService {

    public void flush() throws Exception;

    public void update(Object entity) throws Exception;
    
    public void refresh(Object entity) throws Exception;

    public <T, U> List<U> map(List<T> source, final Class<U> destType);
    
}
