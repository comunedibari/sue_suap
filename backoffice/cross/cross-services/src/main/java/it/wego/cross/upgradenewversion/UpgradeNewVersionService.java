/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.upgradenewversion;

import it.wego.cross.service.*;
import it.wego.cross.entity.Staging;
import java.util.HashMap;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Service;

/**
 *
 * @author piergiorgio
 */
@Service
public interface UpgradeNewVersionService {

    public List<Staging> getAllStaging();

    public void merge(Staging staging) throws Exception;

    public List<Integer> findForConversion() throws Exception;

}
