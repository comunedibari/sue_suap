/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.controller.tag;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateHashModel;
import it.wego.cross.service.FormService;
import it.wego.cross.utils.FTLUtils;
import it.wego.utils.wegoforms.FormContext;
import it.wego.utils.wegoforms.FormEngine;
import it.wego.utils.wegoforms.object.Component;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import static javax.servlet.jsp.tagext.Tag.SKIP_BODY;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.MapType;
//import org.codehaus.jackson.map.ObjectMapper;
//import org.codehaus.jackson.map.type.MapType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

/**
 *
 * @author Gabriele
 */
public class FormTag extends RequestContextAwareTag {

    public static final String FORM_NOT_FOUND_COMPONENT_ID = "FormNotFoundFieldSet";
    final ObjectMapper mapper = new ObjectMapper();
    final MapType type = mapper.getTypeFactory().constructMapType(Map.class, String.class, Object.class);

    @Autowired
    protected FormContext formContext;
    @Autowired
    protected FormService formService;

    @Autowired
    public void setFormContext(FormContext formContext) {
        this.formContext = formContext;
    }

    private String formId;
    private String config;

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public void init() {
        if (formContext == null) {
            WebApplicationContext wac = getRequestContext().getWebApplicationContext();
            AutowireCapableBeanFactory acbf = wac.getAutowireCapableBeanFactory();
            acbf.autowireBean(this);
        }
    }

    @Override
    protected int doStartTagInternal() throws Exception {
        try {
            long begin = System.currentTimeMillis();

            init();
//            FormEngine formEngine = formContext.newFormEngine();

            JspWriter out = pageContext.getOut();
//
//            Component component = formContext.getComponentRepository().findComponentById(formId);
//            if (component == null) {
//                formId = FORM_NOT_FOUND_COMPONENT_ID;
//            }
//
            Map<String, Object> attributes = Maps.asMap(Sets.newHashSet(Iterators.concat(Iterables.transform(Arrays.asList(PageContext.APPLICATION_SCOPE, PageContext.SESSION_SCOPE, PageContext.REQUEST_SCOPE, PageContext.PAGE_SCOPE), new Function<Integer, Iterator<String>>() {
                @Override
                public Iterator<String> apply(Integer input) {
                    return Iterators.forEnumeration(pageContext.getAttributeNamesInScope(input));
                }
            }).iterator())), new Function<String, Object>() {
                @Override
                public Object apply(String input) {
                    return pageContext.findAttribute(input);
                }
            });
////            attributes.put("ns", Strings.nullToEmpty(formContext.getRenderingNamespace()));
//            BeansWrapper wrapper = BeansWrapper.getDefaultInstance();
//            TemplateHashModel staticModels = wrapper.getStaticModels();
//            TemplateHashModel ftlUtilsStatics = (TemplateHashModel) staticModels.get("it.wego.cross.utils.FTLUtils");
//
//            Map instanceDataMap;
//            if (StringUtils.isEmpty(config)) {
//                instanceDataMap = ImmutableMap.builder().putAll(attributes).put("FtlUtils", ftlUtilsStatics).build();
//            } else {
//                Map<String, Object> data = mapper.readValue(config, type);
//                instanceDataMap = ImmutableMap.builder().putAll(attributes).put("FtlUtils", ftlUtilsStatics).put("config", data).build();
//            }
//
//
//            formEngine.setInstanceData(instanceDataMap);
            
            Map<String, Object> data = new HashMap<String, Object>();
            if (!StringUtils.isEmpty(config)) {
                data = mapper.readValue(config, type);
            }
            data.putAll(attributes);

            FormEngine formEngine = formService.getFormEngine(formId, pageContext.getRequest(), null, data);
            
            String result = formEngine.withRequest(pageContext.getRequest()).withComponentId(formId).getResult();

            long end = System.currentTimeMillis();
            System.out.println("TEMPO RENDERING FORM: " + ((end - begin) / 1000.0));

            out.print(result);

        } catch (IOException ioe) {
            throw new JspException("Error: " + ioe.getMessage());
        }
        return SKIP_BODY;
    }

}
