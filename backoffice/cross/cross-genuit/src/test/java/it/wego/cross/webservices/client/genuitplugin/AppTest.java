package it.wego.cross.webservices.client.genuitplugin;

import it.asimantova.genuit.webservice.protocollo.PRIGetSidResponse;
import it.asimantova.genuit.webservice.protocollo.ProtocolloServicePortTypeServiceLocator;
import it.asimantova.genuit.webservice.protocollo.ProtocolloServiceSoapBindingStub;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.Ignore;

/**
 * Unit test for simple App.
 */
@Ignore
public class AppTest
        extends TestCase {

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(AppTest.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() throws Exception {
        assertTrue(true);
//        AuthenticationHandler authenticationHandler = new AuthenticationHandler();
//        ProtocolloServicePortType_Stub protocolloServicePortType_Stub = new ProtocolloServicePortType_Stub(null);
//        
//        protocolloServicePortType_Stub.PRIGetSid("", "");

//        ProtocolloServicePortTypeService_Impl s = new ProtocolloServicePortTypeService_Impl();
//        ProtocolloServicePortType protocolloServicePortType = s.getProtocolloService();
//        
//        PRIGetSidResponse PRIGetSid = protocolloServicePortType.PRIGetSid("CROSS", "12345");

//        String pippo = "";
        //        ProtocolloServicePortType port =
        //
        //        Reporter service = new Reporter();
        //        ReporterPortType port = service.getReporterSOAP11PortHttp();
        //        String reporterClientUrl = configurationDao.getCachedConfiguration(Constants.REPORTER_CLIENT_URL);
        //        if (StringUtils.isEmpty(reporterClientUrl)) {
        //            throw new Exception("Chiave '" + Constants.REPORTER_CLIENT_URL + "' non trovata nella tabella Configurations.");
        //        }
        //        ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, reporterClientUrl);
        //
        //        return port.generateDocument(decoder.decode(templateByteArray), new byte[0], xmlData.getBytes(), new byte[0], documentStringOutput.getBytes());


        try {
//            Stub stub = (Stub) (new ProtocolloServicePortTypeService_Impl().getProtocolloService());
////            Stub stub = (Stub) (new CustomerServiceSoapService_Impl().getPlugin_customer_CustomerService());
//            stub._setProperty(Stub.ENDPOINT_ADDRESS_PROPERTY, "http://localhost:8090/pri/gnu4300i/services/ProtocolloService");
//            ProtocolloServicePortType protocolloServicePortType = (ProtocolloServicePortType) stub;
//            PRIGetSidResponse PRIGetSid = protocolloServicePortType.PRIGetSid("CROSS", "12345");
//            System.out.println("Customer List Size :: ");

            String endPoint = "http://suictest.invallee.it/pri/gnu4300i/services/ProtocolloService";
            ProtocolloServicePortTypeServiceLocator proto = new ProtocolloServicePortTypeServiceLocator();
            proto.setProtocolloServiceEndpointAddress(endPoint);
            ProtocolloServiceSoapBindingStub binding = (ProtocolloServiceSoapBindingStub) proto.getProtocolloService();
            binding.setTimeout(60000);
            PRIGetSidResponse priGetSid = binding.PRIGetSid("CROSS", "12345");

            System.out.println(priGetSid.getSid());

            String pippo = "";


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
