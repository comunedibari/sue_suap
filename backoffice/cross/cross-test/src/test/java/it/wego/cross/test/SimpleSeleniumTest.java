/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.test;

import java.io.IOException;
import java.net.SocketException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author aleph
 */
public class SimpleSeleniumTest extends AbstractSelTest {

    @BeforeClass
    public static void prepare() throws Exception {
        getSelTestUtils().startEverything();
    }

    @AfterClass
    public static void cleanup() throws Exception {
        getSelTestUtils().stopEverything();
    }

    @Test
    public void simpleTest() throws InterruptedException, SocketException, IOException {
        logger.info("simpleTest");
        getSelTestUtils().getWebDriver().get(getSelTestUtils().getJettyBaseUrl() + "/cross");
        Thread.sleep(20000);
        logger.info("simpleTest end");
    }
}
