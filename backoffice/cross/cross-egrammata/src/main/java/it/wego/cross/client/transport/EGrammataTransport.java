/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.client.transport;

import com.sun.xml.rpc.client.http.HttpClientTransport;
import com.sun.xml.rpc.soap.message.SOAPMessageContext;
import java.io.IOException;
import java.net.HttpURLConnection;

/**
 *
 * @author giuseppe
 */
public class EGrammataTransport extends HttpClientTransport {

    private Integer TIMEOUT = 60000;

    public EGrammataTransport() {
    }

    public EGrammataTransport(Integer timeout) {
        this.TIMEOUT = timeout;
    }

    public HttpURLConnection getHttpURLConnection(String endpoint, SOAPMessageContext context) throws IOException {
        return createHttpConnection(endpoint, context);
    }

    @Override
    protected HttpURLConnection createHttpConnection(String endpoint, SOAPMessageContext context) throws IOException {
        HttpURLConnection connection = super.createHttpConnection(endpoint, context);
        connection.setReadTimeout(TIMEOUT);
        connection.setConnectTimeout(TIMEOUT);
        return connection;
    }
}
