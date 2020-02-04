/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.webservices.mypage.mypageclient;

import it.wego.cross.webservices.mypage.mypageclient.stubs.EventoBean;
import it.wego.cross.webservices.mypage.mypageclient.stubs.ReceivedProcessImplWS;
import it.wego.cross.webservices.mypage.mypageclient.stubs.ReceivedProcessImplWSService;
import it.wego.cross.webservices.mypage.mypageclient.stubs.ReceivedProcessImplWSServiceLocator;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import javax.xml.rpc.ServiceException;

/**
 *
 * @author giuseppe
 */
public class MyPageClient {

    private String URL;

    public MyPageClient(String url) {
        this.URL = url;
    }

    public String callMyPage(EventoBean riepilogo) throws RemoteException, ServiceException, MalformedURLException {
//        ReceivedProcessImplWSService receivedProcessImplWSService = new ReceivedProcessImplWSService_Impl();
//        ReceivedProcessImplWS client = receivedProcessImplWSService.getSetEventService();
        ReceivedProcessImplWSService service = new ReceivedProcessImplWSServiceLocator(); 
        ReceivedProcessImplWS client = service.getsetEventService(new URL(URL));
//        ((ReceivedProcessImplWS_Stub) client)._setProperty(Stub.ENDPOINT_ADDRESS_PROPERTY, URL);
        String response = client.setInfoProcess(riepilogo);
        return response;
    }
}
