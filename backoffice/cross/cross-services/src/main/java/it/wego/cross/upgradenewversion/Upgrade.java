/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.upgradenewversion;

import it.wego.cross.actions.UpdateNewVersionAction;
import it.wego.cross.entity.Email;
import it.wego.cross.utils.Log;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author piergiorgio
 */
@Service
public class Upgrade {

    @Autowired
    private UpdateNewVersionAction updateNewVersionAction;
    @Autowired
    private UpgradeNewVersionService serviceDao;

    public void upgradeNewVersion() throws Exception {
        Log.APP.info("Recupero l'elenco degli xml di staging da aggiornare");
        List<Integer> map = serviceDao.findForConversion();
        Log.APP.info("Devo aggiornare " + map.size() + " XML di staging");
        for (Integer key : map) {
            Log.APP.info("Aggiorno l'XML con id " + key);
            updateNewVersionAction.upgradeNewVersionRow(key);
        }
    }

    public void startEmailProcess(Email email) throws Exception {
        updateNewVersionAction.startEmailProcess(email);
    }

}
