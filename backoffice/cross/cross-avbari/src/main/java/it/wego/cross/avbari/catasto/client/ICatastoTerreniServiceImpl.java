
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package it.wego.cross.avbari.catasto.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class was generated by Apache CXF 2.3.9
 * 2015-04-09T12:04:09.994+02:00
 * Generated source version: 2.3.9
 * 
 */

@javax.jws.WebService(
                      serviceName = "CatastoTerreniService",
                      portName = "BasicHttpBinding_ICatastoTerreniService",
                      targetNamespace = "http://tempuri.org/",
                      endpointInterface = "it.wego.cross.avbari.catasto.client.ICatastoTerreniService")
                      
public class ICatastoTerreniServiceImpl implements ICatastoTerreniService {

    private static final Logger LOG = LoggerFactory.getLogger(ICatastoTerreniServiceImpl.class.getName());

    /* (non-Javadoc)
     * @see it.wego.cross.avbari.catasto.client.ICatastoTerreniService#process(java.lang.String  data )*
     */
    public java.lang.String process(java.lang.String data) { 

        try {
            java.lang.String _return = "";
            if( LOG.isInfoEnabled() )
            {
            	LOG.info("ESECUZIONE METODO [{}] CON DATA [{}] RISULTATO [{}]", "process", data, _return);
            }            
            return _return;
        } catch (java.lang.Exception ex) {
            if( LOG.isInfoEnabled() )
            {
            	LOG.info("ECCEZIONE ESECUZIONE METODO [{}] CON DATA [{}]", "process", data, ex);
            }
            throw new RuntimeException(ex);
        }
    }

}
