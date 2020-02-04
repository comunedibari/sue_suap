package it.wego.cross.controller;


import it.wego.cross.beans.layout.Message;
import it.wego.cross.service.ConfigurationService;
import it.wego.cross.service.FormService;
import it.wego.utils.wegoforms.repository.AbstractFileComponentRepository;
import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author CS
 * @original_author : Gabriele Muscas [gabriele.muscas@wego.it]
 */
@Controller
public class ResetConfigurazione extends AbstractController {

    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private AbstractFileComponentRepository commonFormRepository;
    @Autowired
    private FormService formService;

    @RequestMapping("/resetConfigurazione")
    public String resetConfigurazione(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        configurationService.resetCachedConfiguration();
        formService.initializeFormsConfiguration();
        commonFormRepository.reload();
        model.addAttribute("message", new Message(Boolean.FALSE, Arrays.asList("Le configurazioni sono state ricaricate.")));
        return "cross_index";
    }
}
