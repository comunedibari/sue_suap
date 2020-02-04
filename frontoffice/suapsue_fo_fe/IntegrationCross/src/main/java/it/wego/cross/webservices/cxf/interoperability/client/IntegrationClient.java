package it.wego.cross.webservices.cxf.interoperability.client;

import java.net.URL;
import java.rmi.RemoteException;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import it.wego.cross.webservices.cxf.interoperability.CrossServices;
import it.wego.cross.webservices.cxf.interoperability.CrossServicesException;
import it.wego.cross.webservices.cxf.interoperability.CrossServicesImplService;
import it.wego.cross.webservices.cxf.interoperability.CrossServicesImplServiceLocator;
import it.wego.cross.webservices.cxf.interoperability.CrossServicesProxy;
import it.wego.cross.webservices.cxf.interoperability.Evento;

public class IntegrationClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
		
		CrossServicesImplService service = new CrossServicesImplServiceLocator();
		
		
		/*URL portAddress;
		try {
//			portAddress = new URL("http://192.168.100.100:8082/cross-ws/services/CrossServices");
			try {
				service.getCrossServicesImplPort(portAddress);
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (MalformedURLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}*/
		
//		BindingProvider bp = (BindingProvider)port;
//        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointURL);


		
		CrossServices stub;
		try {
			stub = service.getCrossServicesImplPort();
			org.apache.axis.client.Stub axisStub = (org.apache.axis.client.Stub) stub;
			Evento[] e = stub.getListaEventi(0, "PRZPQL71P29F839L-060605-0597717", "");
			System.out.println("Numero eventi" + e.length);
		} catch (ServiceException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (CrossServicesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
