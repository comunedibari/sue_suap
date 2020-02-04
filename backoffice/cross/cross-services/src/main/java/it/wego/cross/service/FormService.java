/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import it.wego.utils.wegoforms.FormEngine;
import java.util.Map;
import javax.servlet.ServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public interface FormService {

    public void initializeFormsConfiguration() throws Exception;

    public String getFormContent(String idForm,
            ServletRequest request,
            Model model,
            Map<String, Object> attributes,
            Boolean ajaxForm) throws Exception;
    
    public String getFormContent(String idForm,
            ServletRequest request,
            Model model,
            Map<String, Object> attributes,
            Boolean ajaxForm,
            Boolean oldForms) throws Exception;
    
    public FormEngine getFormEngine(String idForm,
            ServletRequest request,
            Model model,
            Map<String, Object> attributes) throws Exception;

    public String extendWithButtons(String idForm, String formContent, String namespace,  Model model, Boolean ajaxForm) throws Exception;
    
    public String extendWithButtons(String idForm, String formContent, String namespace,  Model model, Boolean ajaxForm, Boolean oldForms) throws Exception;

    public Object executeAction(
            FormEngine formEngine,
            ServletRequest request,
            Model model,
            String idForm,
            String actionId) throws Exception;

}
