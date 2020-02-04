/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.upgradenewversion;

import it.wego.cross.service.*;
import it.wego.cross.dao.StagingDao;
import it.wego.cross.entity.Staging;
import java.util.HashMap;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author piergiorgio
 */
@Service
public class UpgradeNewVersionServiceImpl implements UpgradeNewVersionService {
    
    @Autowired
    private StagingDao stagingDao;
    
    @Override
    public List<Staging> getAllStaging() {
        List<Staging> stagings = stagingDao.findAll();
        return stagings;
    }

    @Override
    public void merge(Staging staging) throws Exception {
        stagingDao.merge(staging);
    }

    @Override
    public List<Integer> findForConversion() throws Exception {
        List<Integer> record = stagingDao.findForConversion();
        return record;
    }

}
