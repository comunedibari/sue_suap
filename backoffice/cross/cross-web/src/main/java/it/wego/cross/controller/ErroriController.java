package it.wego.cross.controller;

import it.wego.cross.beans.grid.GridTasklistBean;
import it.wego.cross.beans.layout.JqgridPaginator;
import it.wego.cross.dto.ErroreDTO;
import it.wego.cross.dto.TaskDTO;
import it.wego.cross.service.ErroriService;
import it.wego.cross.utils.Log;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ErroriController extends AbstractController {

    @Autowired
    private ErroriService erroriService;

    public static final String INTERNAL_SERVER_ERRROR = "internal_server_error";
    private static final String AJAX_PAGE = "ajax";

    @RequestMapping("/errore")
    public String error(Model model, HttpServletRequest request, HttpServletResponse response) {
        return INTERNAL_SERVER_ERRROR;
    }

    @RequestMapping("/error/view")
    public String dettaglioErrore(Model model, @RequestParam(value = "errorId", required = true) Integer errorId) {
        try {
            ErroreDTO errore = erroriService.findErroreById(errorId);
            
            model.addAttribute("json", errore.getTrace().replaceAll("(\\r\\n|\\n)", "<br />"));
        } catch (Exception e) {
            String msg = "Impossibile trovare l'errore.";
            model.addAttribute("json", msg);
            Log.APP.error(msg, e);
        }
        return AJAX_PAGE;
    }
}
