/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.controller;

import it.wego.cross.dto.filters.Filter;
import it.wego.cross.entity.Email;
import it.wego.cross.service.MailService;
import it.wego.cross.upgradenewversion.Upgrade;
import it.wego.cross.utils.Log;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author piergiorgio
 *
 */
@Controller
public class UpgradeNewVersion extends AbstractController {
    
    @Autowired
    private Upgrade upgrade;
    @Autowired
    private MailService mailService;
    
    @RequestMapping("/UpgradeNewVersion")
    public void UpgradeNewVersion(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Log.APP.info("Avvio procedura di allineamento dati dalla precedente versione di CROSS ...");
        upgrade.upgradeNewVersion();
        Log.APP.info("Allineamento terminato");
    }
    
    @RequestMapping("/upgrade/email")
    public void createEmailProcess(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Log.APP.info("Avvio procedura di allineamento email");
        Log.APP.info("Per tutte le email con stato S (da spedire) avvio il flusso di invio email");
        Filter filter = new Filter();
        filter.setOrderColumn("idEmail");
        filter.setOrderDirection("asc");
        List<Email> emails = mailService.getEmailNonInviate();
        Log.APP.info("Trovate " + emails.size() + " da inviare");
        for (Email email : emails) {
            Log.APP.info("Preparo il flusso per l'invio della email con id " + email.getIdEmail());
            upgrade.startEmailProcess(email);
        }
        Log.APP.info("Allineamento terminato");
    }
}
