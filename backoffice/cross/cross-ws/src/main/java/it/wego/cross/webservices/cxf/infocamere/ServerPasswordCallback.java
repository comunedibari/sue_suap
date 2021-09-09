package it.wego.cross.webservices.cxf.infocamere;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSPasswordCallback;

public class ServerPasswordCallback implements CallbackHandler {
 
	
	   public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException{
		   WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];
		   Properties properties = new Properties();
		   InputStream is = getClass().getClassLoader().getResourceAsStream("signprop.properties");
		   
		   properties.load(is);
		   String username = properties.getProperty("org.apache.ws.security.crypto.merlin.keystore.alias");
		   String password = properties.getProperty("org.apache.ws.security.crypto.merlin.keystore.password");
	        if (pc.getIdentifier().equals(username)) {
	            // set the password on the callback. This will be compared to the
	            // password which was sent from the client.
	            pc.setPassword(password);
	        }
	   }
	
	
    
 
}