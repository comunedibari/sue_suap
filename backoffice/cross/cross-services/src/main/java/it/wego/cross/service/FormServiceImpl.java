/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateHashModel;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import it.wego.cross.utils.Utils;
import it.wego.utils.wegoforms.FormContext;
import it.wego.utils.wegoforms.FormEngine;
import it.wego.utils.wegoforms.object.Component;
import it.wego.utils.wegoforms.parsing.WgfValue;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.cxf.common.util.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.MapType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.ui.Model;

/**
 *
 * @author Gabriele
 */
@Service
public class FormServiceImpl implements FormService {

    public static final String FORM_NOT_FOUND_COMPONENT_ID = "FormNotFoundFieldSet";
    @Autowired
    private FormContext formContext;
    @Autowired
    private ApplicationContext appContext;
    private final ObjectMapper jsonMapper = new ObjectMapper();
    private final MapType mapType = jsonMapper.getTypeFactory().constructMapType(Map.class, String.class, Object.class);
    private Map<String, Object> formsMap = null;
    private static final String INIT_METHOD = "init";

    @Override
    public void initializeFormsConfiguration() throws Exception {
        InputStream formActions = FormService.class.getClassLoader().getResourceAsStream("it/wego/cross/forms/form.actions.json");
        if (formActions != null) {
            formsMap = jsonMapper.readValue(formActions, mapType);
            formActions.close();
        }
    }
    
    @Override
    public FormEngine getFormEngine(String idForm,
            ServletRequest request,
            Model model,
            Map<String, Object> attributes) throws Exception {
        Component component = formContext.getComponentRepository().findComponentById(idForm);
        if (component == null) {
            idForm = FORM_NOT_FOUND_COMPONENT_ID;
        }
        FormEngine formEngine = formContext.newFormEngine();

        Map<String, Object> attributeParameters = new HashMap<String, Object>();
        if (model != null) {
            attributeParameters.putAll(model.asMap());
        }
        if (request != null) {
            for (Object key : Collections.list(request.getParameterNames())) {
                attributeParameters.put((String) key, request.getParameter((String) key));
            }
        }
        if (attributes != null) {
            attributeParameters.putAll(attributes);
        }

        BeansWrapper wrapper = BeansWrapper.getDefaultInstance();
        TemplateHashModel staticModels = wrapper.getStaticModels();
        TemplateHashModel ftlUtilsStatics = (TemplateHashModel) staticModels.get("it.wego.cross.utils.FTLUtils");
        attributeParameters.put("FtlUtils", ftlUtilsStatics);

        if (formsMap == null) {
            initializeFormsConfiguration();
        }

        formEngine.setInstanceData(attributeParameters);
        executeAction(formEngine, request, model, idForm, INIT_METHOD);

        return formEngine;
    }

    @Override
    public String extendWithButtons(String idForm, String formContent, String formNs, Model model, Boolean ajaxForm) throws Exception {
        return extendWithButtons(idForm, formContent, formNs, model, ajaxForm, Boolean.FALSE);
    }
    
    @Override
    public String extendWithButtons(String idForm, String formContent, String formNs, Model model, Boolean ajaxForm, Boolean oldForms) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(formContent);
        Map<String, Object> formConfigurations = (Map<String, Object>) formsMap.get(idForm);
        if (formConfigurations != null) {
            Map<String, Object> formActions = (Map<String, Object>) formConfigurations.get("actions");
            if (formActions != null && formActions.get("submit") != null) {
                String formSubmitUrl = (String) formConfigurations.get("submitUrl");
                if (StringUtils.isEmpty(formSubmitUrl)) {
                    formSubmitUrl = "/form/ajax/submit.htm";
                }

                sb.append("<script type='text/javascript'>")
                        .append("$(function() {")
                        .append("   var oldForm=").append(oldForms.toString()).append(";")
                        .append("   var notificationMode=oldForm?'dialog':'none';")
                        .append("   wgf.utils.configureForm('.").append(formNs).append("wgf-form', '").append(model.asMap().get("path")).append(formSubmitUrl).append("', {")
                        .append("	notification: function(config) {")
                        .append("           config.mode = notificationMode;")
                        .append("       }")
                        .append("   });")
                        .append("   $('.").append(formNs).append("wgf-form').prepend(\"<input type='hidden' name='__idForm' value='").append(idForm).append("'/>\");")
                        .append("   $('.").append(formNs).append("wgf-form').prepend(\"<input type='hidden' name='__action' class='").append(formNs).append("action-id'/>\");")
                        .append("   $('.").append(formNs).append("wgf-form').on('wgf-beforeSubmit', function() {")
                        .append("       $('.cui-form-btn').prop('disabled', true);")
                        .append("   });")
                        .append("   $('.").append(formNs).append("wgf-form').on('wgf-onSubmitSuccess', function(event, response) {")
                        .append("       $('.cui-form-btn').prop('disabled', false);")
                        .append("       if(!oldForm){")
                        .append(StringUtils.isEmpty((String) formConfigurations.get("submitTrigger")) ? "" : "$(EventManager).trigger('").append(formConfigurations.get("submitTrigger")).append("');")
                        .append("           var message=response.message;")
                        .append(ajaxForm ? "       $(this).closest('.ui-dialog-content').dialog('destroy').remove();" : "")
                        .append("           $.gritter.add({")
                        .append("               title: 'Notifica',")
                        .append("               text: message,")
                        .append("               class_name: 'gritter-success'")
                        .append("           });")
                        .append("       }")
                        .append("   });")
                        .append("   $('.").append(formNs).append("wgf-form').on('wgf-onSubmitError', function(event, response) {\n")
                        .append("       $('.cui-form-btn').prop('disabled', false);\n")
                        .append("       if(!oldForm){")
                        .append(StringUtils.isEmpty((String) formConfigurations.get("submitTrigger")) ? "" : "$(EventManager).trigger('").append(formConfigurations.get("submitTrigger")).append("');")
                        .append("           var message=response.message;\n")
                        .append("           $.gritter.add({")
                        .append("               title: 'Errore',")
                        .append("               text: message,")
                        .append("               sticky: true,")
                        .append("               class_name: 'gritter-error'")
                        .append("           });")
                        .append("       }")
                        .append("   });")
                        .append("});")
                        .append("</script>");
            }

            Map<String, Object> formButtons = (Map<String, Object>) formConfigurations.get("buttons");
            if (formButtons != null && !formButtons.isEmpty()) {

                StringBuilder buttonsLayout = new StringBuilder();
                StringBuilder buttonsScript = new StringBuilder();
                buttonsScript.append("$(function() { ");
                for (Entry<String, Object> button : formButtons.entrySet()) {
//                    String buttonNs = UUID.randomUUID().toString();
                    String buttonNs = RandomStringUtils.randomAlphabetic(8);
//                    String buttonRendered = renderSingleButton((Map<String, Object>) button.getValue(), formNs);

                    buttonsLayout
                            .append("<button class='btn btn-info cui-form-btn' id='").append(buttonNs).append("' type='button'>")
                            .append("   <i class='ace-icon fa fa-check bigger-110'></i>")
                            .append(((Map<String, Object>) button.getValue()).get("label"))
                            .append("</button>");

                    buttonsScript
                            .append("   $('#").append(buttonNs).append("').on('click', function() {")
                            .append("       $('.").append(formNs).append("action-id').val('").append(((Map<String, Object>) button.getValue()).get("action")).append("');")
                            .append("       $('.").append(formNs).append("wgf-form').trigger('submit');")
                            .append("   });");
                }
                buttonsScript.append("});");

                if (!ajaxForm) {
                    sb.append("<div class='clearfix form-actions'>")
                            .append("<div class='pull-right'>")
                            .append(buttonsLayout)
                            .append("</div>")
                            .append("<script type='text/javascript'>")
                            .append(buttonsScript)
                            .append("</script>")
                            .append("</div>");
                } else {
                    sb.append("<script type='text/javascript'>")
                            .append("$('#" + model.asMap().get("dialogId") + "').parent().find('.ui-dialog-buttonset>button:last-child').after(\"").append(buttonsLayout).append("\");")
                            .append(buttonsScript)
                            .append("</script>");
                }
            }
        }

        return sb.toString();
    }

    @Override
    public Object executeAction(
            FormEngine formEngine,
            ServletRequest request,
            Model model,
            String idForm,
            String actionId) throws Exception {
        Map<String, Object> formConfigurations = (Map<String, Object>) formsMap.get(idForm);
        Object resultObject = null;
        if (formConfigurations != null) {
            Map<String, Object> formActions = (Map<String, Object>) formConfigurations.get("actions");
            if (formActions != null) {
                Map<String, Object> action = (Map<String, Object>) formActions.get(actionId);
                String actionType = (String) action.get("type");

                Map formParameters = Maps.newHashMap();
                if (!actionId.equalsIgnoreCase(INIT_METHOD)) {
                    WgfValue componentValue = formContext.newFormEngine()
                            .withComponentId(idForm)
                            .parseSubmit(request.getParameterMap());
                    formParameters = Maps.newHashMap(componentValue.asMap());
                }

                if ("JAVA".equalsIgnoreCase(actionType)) {

                    String clsName = (String) action.get("class");  // use fully qualified name
                    String methodName = (String) action.get("method");
                    Class cls = Class.forName(clsName);
                    Object obj = appContext.getBean(cls);
                    Method method = obj.getClass().getDeclaredMethod(methodName, HttpServletRequest.class, Model.class, FormEngine.class, Map.class);
                    resultObject = method.invoke(obj, request, model, formEngine, formParameters);
                }
                if ("GROOVY".equalsIgnoreCase(actionType)) {
                    String scriptPath = "it/wego/cross/forms/script/" + action.get("script");
                    InputStream anagraficaGroovy = FormService.class.getClassLoader().getResourceAsStream(scriptPath);
                    String scriptAnagraficaGroovy = Utils.convertStreamToString(anagraficaGroovy);
                    GroovyShell gs = new GroovyShell();
                    Script script = gs.parse(scriptAnagraficaGroovy);
                    Binding binding = new Binding();
                    binding.setVariable("log", it.wego.cross.utils.Log.APP);
                    binding.setVariable("ctx", appContext);
                    binding.setVariable("requesto", request);
                    binding.setVariable("model", model);
                    binding.setVariable("formEngine", formEngine);
                    binding.setVariable("parameters", formParameters);
                    resultObject = script.invokeMethod("execute", binding);

                }
            }
        }
        return resultObject;
    }

    @Override
    public String getFormContent(String idForm, ServletRequest request, Model model, Map<String, Object> attributes, Boolean ajaxForm) throws Exception {
        return getFormContent(idForm, request, model, attributes, ajaxForm, Boolean.FALSE);
    }
    
    @Override
    public String getFormContent(String idForm, ServletRequest request, Model model, Map<String, Object> attributes, Boolean ajaxForm, Boolean oldForms) throws Exception {
        FormEngine formEngine = getFormEngine(idForm, request, model, null);

        String namespace = UUID.randomUUID().toString();
        String formContent = formEngine.withNamespace(namespace).withComponentId(idForm).withComponentActionUrl("ajax/fileUpload.htm").getResult();

        formContent = extendWithButtons(idForm, formContent, namespace, model, ajaxForm, oldForms);

        return formContent;
    }
}
