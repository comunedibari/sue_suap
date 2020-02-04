/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.wego.cross.freemarker;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import it.wego.cross.utils.Log;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

/**
 *
 * @author Giuseppe
 */
public class TestTemplate {
    private static final String template = "La data ${data} aumentata di 10 gg <@addDays date=data days=10 input_format=\"dd/MM/yyyy\" output_format=\"dd-MM-yyyy HH:mm:ss\"/>. Number Format ${numero?replace(',','.')?number}";

    @Test
    public void executeTemplateWithExtensions() throws Exception {
        Map params = new HashMap();
        params.put("data", "28/10/2014");
        params.put("numero", "12611,82");
        String result = getContenutoDaTemplate(params);
        System.out.println(result);
    }

    private String getContenutoDaTemplate(Map params) throws IOException, TemplateException {
        freemarker.template.Configuration freeMarkerConfig = new freemarker.template.Configuration();
        freeMarkerConfig.setSharedVariable("addDays", new DateDirective());
        InputStream freeMarkerInputStream = new ByteArrayInputStream(template.getBytes("UTF-8"));
        try {
            Template freeMarkerTemplate = new Template("mailTemplate", new InputStreamReader(freeMarkerInputStream, Charset.forName("UTF-8")), freeMarkerConfig);
            ByteArrayOutputStream freeMarkerOutputStream = new ByteArrayOutputStream();
            freeMarkerTemplate.process(params, new OutputStreamWriter(freeMarkerOutputStream, Charset.forName("UTF-8")));
            String content = freeMarkerOutputStream.toString("UTF-8");
            return content;
        } catch (Exception e) {
            Log.APP.error("Si è verificato un errore eseguendo il parsing del template in freemarker.", e);
            return template + "#ERRORE#";
        }

    }
}
