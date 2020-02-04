/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import it.wego.cross.dao.StagingDao;
import it.wego.cross.entity.Staging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Giuseppe
 */
@Service
public class StagingServiceImpl implements StagingService {

    @Autowired
    private StagingDao stagingDao;
    
    @Override
    public void update(Staging stg) {
        stagingDao.merge(stg);
    }

    @Override
    public Staging findById(Integer idStaging) {
        return stagingDao.findByCodStaging(idStaging);
    }

}
