package it.wego.cross.webservices.cxf.interoperability.client;

import java.rmi.RemoteException;

import it.wego.cross.webservices.cxf.interoperability.CrossServicesException;
import it.wego.cross.webservices.cxf.interoperability.CrossServicesProxy;
import it.wego.cross.webservices.cxf.interoperability.Evento;

public class ClientCross {

	public static void main(String[] args) {

		CrossServicesProxy proxy1 = new CrossServicesProxy();
	    proxy1.setEndpoint("http://localhost:8083/cross-ws/services/CrossServices");
	    
	    try {
			it.wego.cross.webservices.cxf.interoperability.Evento[]  listaEventi = proxy1.getListaEventi(0, "PRZPQL71P29F839L-060605-0597717", "");
			for (int i=0; i<listaEventi.length; i++) {
				Evento e = listaEventi[i];
				System.out.println("Numero eventi: "+ e);
			}
			
		} catch (CrossServicesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
