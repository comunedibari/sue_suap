/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.selenium;

/**
 *
 * @author GabrieleM
 */
 
import org.apache.http.client.methods.*;
 
public enum RequestMethod {
    OPTIONS(new HttpOptions()),
    GET(new HttpGet()),
    HEAD(new HttpHead()),
    POST(new HttpPost()),
    PUT(new HttpPut()),
    DELETE(new HttpDelete()),
    TRACE(new HttpTrace());
 
    private final HttpRequestBase requestMethod;
 
    RequestMethod(HttpRequestBase requestMethod) {
        this.requestMethod = requestMethod;
    }
 
    public HttpRequestBase getRequestMethod() {
        return this.requestMethod;
    }
}
