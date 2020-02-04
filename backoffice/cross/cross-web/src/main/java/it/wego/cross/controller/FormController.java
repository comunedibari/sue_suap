/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.controller;

import it.wego.cross.service.FormService;
import it.wego.utils.json.JsonResponse;
import it.wego.utils.wegoforms.FormContext;
import it.wego.utils.wegoforms.FormEngine;
import java.util.UUID;
import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Gabriele
 */
@Controller
public class FormController extends AbstractController {

    public static final String FORM_NOT_FOUND_COMPONENT_ID = "FormNotFoundFieldSet";

    @Autowired
    private FormService formService;

    @PreDestroy
    public void cleanup() {
        FormContext.clearThreadLocal();
    }

    @RequestMapping("/form/view")
    public String formView(
            @RequestParam(value = "idForm", required = true) String idForm,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model) throws Exception {

        String formContent = formService.getFormContent(idForm, request, model, null, Boolean.FALSE);
        
        model.addAttribute("formContent", formContent);
        model.addAttribute("pageTitle", "helloWorld");

        return "FORM_VIEW";
    }

    @RequestMapping("/form/ajax/view")
    public void formAjaxView(
            @RequestParam(value = "idForm", required = true) String idForm,
            @RequestParam(value = "dialogId", required = false) String dialogId,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model) throws Exception {
        response.setContentType("text/html");

        model.addAttribute("dialogId", dialogId);
        String formContent = formService.getFormContent(idForm, request, model, null, Boolean.TRUE);
        
        IOUtils.write(formContent, response.getOutputStream());
        response.getOutputStream().flush();
    }

    @RequestMapping("/form/ajax/submit")
    public @ResponseBody
    Object formAjaxSubmit(
            @RequestParam(value = "__idForm", required = true) String idForm,
            @RequestParam(value = "__action", required = true) String actionId,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model) throws Exception {
        JsonResponse jsonResponse = (JsonResponse) formService.executeAction(null, request, model, idForm, actionId);
        return jsonResponse;
    }

    @RequestMapping("/form/ajax/fileUpload")
    public void handleComponentAction(
            HttpServletRequest request,
            HttpServletResponse response,
            Model model) throws Exception {
        FormEngine formEngine = formService.getFormEngine("FAKECOMPONENT", request, model, null);
        formEngine.printActionResult(request, response);
    }

}
