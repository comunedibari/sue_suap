/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.test;

import it.wego.cross.dao.PraticaDao;
import it.wego.cross.dao.ProcedimentiDao;
import it.wego.cross.service.ClearService;
import it.wego.cross.service.EntiService;
import it.wego.cross.service.PraticheService;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Gabriele
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/test/application-context-test.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class EProtTest {

    @Autowired
    ClearService clearService;
    @Autowired
    ProcedimentiDao procedimentiDao;
    @Autowired
    PraticaDao praticaDao;
    @Autowired
    PraticheService praticheService;
    @Autowired
    EntiService entiService;

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
//    @Transactional
//    @Ignore
    @Ignore
    public void eProtTest() throws Exception {
        RestTemplate rest = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

//        HttpEntity<String> entity = new HttpEntity<String>("pippo", headers);
//        rest.put("http://eprot.flosslab.it/eprot_api/rest/protocolloservice/richiesta_protocollazione_ingresso", entity);


        List<HttpMessageConverter<?>> messageConverters = rest.getMessageConverters();
        List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>(messageConverters);


        HttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();
        HttpMessageConverter stringHttpMessageConverternew = new StringHttpMessageConverter();

        converters.add(formHttpMessageConverter);
        converters.add(stringHttpMessageConverternew);
        converters.add(new MappingJacksonHttpMessageConverter());

        rest.setMessageConverters(converters);



        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("UTENTE_PROT", "admin");
        map.add("TIPO_ASSEGNATARIO", "UTENTE");
        map.add("UTENTE_ASSEGNATARIO", "protocollatore");
        map.add("UFFICIO_ASSEGNATARIO", "");
        map.add("TIPO_MITTENTE", "F");
        map.add("NOME", "Gabriele");
        map.add("COGNOME", "Muscas");
        map.add("RAGIONE_SOCIALE", "");
        map.add("OGGETTO", "Oggetto protocollo");
        map.add("TIPO_DOCUMENTO", "2");
        map.add("TITOLARIO", "2");
        map.add("CODICE_UTENTE", "gdsfrc74h26l483f");
        
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        
        String result = rest.postForObject("http://eprot.flosslab.it/eprot_api/rest/protocolloservice/richiesta_protocollazione_ingresso", request, String.class);
        System.out.println(result);


    }
}
