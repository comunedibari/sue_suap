/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.utils;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.commons.lang.Validate;
import org.slf4j.LoggerFactory;

/**
 *
 * @author aleph
 */
public class TomcatUtils {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    private Tomcat tomcat;
    private File directory;

    public void start() throws LifecycleException, UnknownHostException {
        logger.info("starting tomcat");
        directory = new File(System.getProperty("java.io.tmpdir"));
        Validate.isTrue(directory.exists() && directory.isDirectory() && directory.canRead());

        tomcat = new Tomcat();
//        tomcat.setPort(0); // random port
        tomcat.setPort(8080);
        tomcat.setBaseDir(directory.getAbsolutePath());
        tomcat.getHost().setAppBase(directory.getAbsolutePath());
        tomcat.getHost().setAutoDeploy(true);
        tomcat.getHost().setDeployOnStartup(true);
        tomcat.enableNaming();

//        String contextPath = "/" + getApplicationId();
//        File webApp = new File(mWorkingDir, getApplicationId());
//        File oldWebApp = new File(webApp.getAbsolutePath());
//        FileUtils.deleteDirectory(oldWebApp);
//        new ZipExporterImpl(createWebArchive()).exportTo(new File(mWorkingDir + "/" + getApplicationId() + ".war"),
//                true);
        File webappDirectory = new File("target/cross-web-1.0-SNAPSHOT/");
        Validate.isTrue(webappDirectory.exists() && webappDirectory.isDirectory() && webappDirectory.canRead());
        Context context = tomcat.addWebapp(tomcat.getHost(), "/cross", webappDirectory.getAbsolutePath());
        context.setConfigFile(getClass().getResource("/testContext.xml"));

        tomcat.start();
        logger.info("started tomcat, url = {}", getTomcatUrl());
    }

    public int getTomcatPort() {
        return tomcat.getConnector().getLocalPort();
    }

    public String getTomcatUrl() throws UnknownHostException {
        return "http://" + InetAddress.getLocalHost().getHostName() + ":" + getTomcatPort();
    }

    public void cleanup() {
        logger.info("stopping tomcat");
        try {
            tomcat.stop();
        } catch (Exception ex) {
            logger.warn("error stopping tomcat", ex);
        }
        try {
            tomcat.destroy();
        } catch (Exception ex) {
            logger.warn("error destroying tomcat", ex);
        }
        tomcat = null;
        directory = null;
        logger.info("stopped tomcat");
    }
}
