/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.test;

import it.wego.cross.utils.TomcatUtils;
import java.net.UnknownHostException;
import org.apache.catalina.LifecycleException;
import org.junit.Test;
import org.slf4j.LoggerFactory;

/**
 *
 * @author aleph
 */
public class TestTomcatStartup {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void testTomcatStartup() throws  InterruptedException, UnknownHostException, LifecycleException {
        TomcatUtils tomcatUtils = new TomcatUtils();
        tomcatUtils.start();
        String crossUrl = tomcatUtils.getTomcatUrl() + "/cross";
        Thread.sleep(1000); // do tests here
        tomcatUtils.cleanup();
    }
}
