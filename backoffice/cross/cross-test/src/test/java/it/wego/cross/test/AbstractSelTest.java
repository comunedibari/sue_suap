/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.test;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import freemarker.template.Configuration;
import freemarker.template.Template;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import java.util.zip.GZIPInputStream;
import org.apache.commons.dbutils.DbUtils;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.xml.XmlConfiguration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author aleph
 */
public abstract class AbstractSelTest {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public static class SelTestUtils {

        private final Logger logger = LoggerFactory.getLogger(getClass());

        private Server server;
        private DesiredCapabilities desiredCapabilities;
        private WebDriver webDriver;
        private int port = 1380;
        private String dbCross, dbActiviti, passwd;

        private Connection getMysqlRootConnection() throws ClassNotFoundException, SQLException {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://oss:3306", "root", "allamantellotto");
            return connection;
        }

        public void prepareDatabase() throws ClassNotFoundException, SQLException, IOException {
            Connection connection = getMysqlRootConnection();
            try {
                passwd = UUID.randomUUID().toString();
                logger.info("db passwd = {}", passwd);
                {
                    dbActiviti = "activiti" + UUID.randomUUID().toString().substring(0, 4);
                    connection.createStatement().executeUpdate("CREATE DATABASE " + dbActiviti + " DEFAULT CHARSET 'UTF8';");
                    connection.createStatement().executeUpdate("GRANT ALL ON " + dbActiviti + ".* TO '" + dbActiviti + "'@'%' IDENTIFIED BY '" + passwd + "';");
                    connection.createStatement().executeUpdate("GRANT CREATE VIEW ON " + dbActiviti + ".* TO '" + dbActiviti + "'@'%';");
                    connection.createStatement().executeUpdate("GRANT ALTER ROUTINE, CREATE ROUTINE, EXECUTE ON " + dbActiviti + ".* TO '" + dbActiviti + "'@'%';");
                    logger.info("loading db {}", dbActiviti);
                    Connection dbConnection = DriverManager.getConnection("jdbc:mysql://oss:3306/" + dbActiviti, dbActiviti, passwd);
                    try {
                        ScriptRunner scriptRunner = new ScriptRunner(dbConnection);
                        scriptRunner.setLogWriter(null);
                        scriptRunner.runScript(new InputStreamReader(new GZIPInputStream(getClass().getResourceAsStream("/activitiDb.sql.gz"))));
                        scriptRunner.closeConnection();
                    } finally {
                        DbUtils.closeQuietly(dbConnection);
                    }
                    logger.info("loaded db {}", dbActiviti);
                }

                {
                    dbCross = "cross" + UUID.randomUUID().toString().substring(0, 4);
                    connection.createStatement().executeUpdate("CREATE DATABASE " + dbCross + " DEFAULT CHARSET 'UTF8';");
                    connection.createStatement().executeUpdate("GRANT ALL ON " + dbCross + ".* TO '" + dbCross + "'@'%' IDENTIFIED BY '" + passwd + "';");
                    connection.createStatement().executeUpdate("GRANT CREATE VIEW ON " + dbCross + ".* TO '" + dbCross + "'@'%';");
                    connection.createStatement().executeUpdate("GRANT ALTER ROUTINE, CREATE ROUTINE, EXECUTE ON " + dbCross + ".* TO '" + dbCross + "'@'%';");

                    logger.info("loading db {}", dbCross);
                    Connection dbConnection = DriverManager.getConnection("jdbc:mysql://oss:3306/" + dbCross, dbCross, passwd);
                    try {
                        ScriptRunner scriptRunner = new ScriptRunner(dbConnection);
                        scriptRunner.setLogWriter(null);
//                        scriptRunner.setAutoCommit(true);
//                        scriptRunner.setSendFullScript(true);
//                        scriptRunner.setStopOnError(false);

                        scriptRunner.runScript(new InputStreamReader(new GZIPInputStream(getClass().getResourceAsStream("/crossDb.sql.gz"))));
                        scriptRunner.closeConnection();
                    } finally {
                        DbUtils.closeQuietly(dbConnection);
                    }
                    logger.info("loaded db {}", dbCross);
                }
            } finally {
                DbUtils.closeQuietly(connection);
            }
        }

        public void cleanDatabase() throws ClassNotFoundException, SQLException {
            Connection connection = getMysqlRootConnection();
            try {
                logger.info("dropping mysql dbs {} and {}", dbCross, dbActiviti);
                connection.createStatement().executeUpdate("DROP DATABASE " + dbCross + ";");
                dbCross = null;
                connection.createStatement().executeUpdate("DROP DATABASE " + dbActiviti + ";");
                dbActiviti = null;
            } finally {
                DbUtils.closeQuietly(connection);
            }
        }

        public void startJetty() throws Exception {
            logger.info("starting jetty container on port = {}", port);
//            logger.debug("current path = {}", IOUtils.toString(Runtime.getRuntime().exec("pwd").getInputStream()));
            server = new Server(port);

            {
                Configuration configuration = new Configuration();
                configuration.setClassForTemplateLoading(getClass(), "");
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                configuration.getTemplate("jetty.xml.ftl").process(ImmutableMap.builder()
                        .put("host", "oss")
                        .put("crossDb", dbCross)
                        .put("crossUser", dbCross)
                        .put("crossPsw", passwd)
                        .put("activitiDb", dbActiviti)
                        .put("activitiUser", dbActiviti)
                        .put("activitiPsw", passwd)
                        .build(), new OutputStreamWriter(out));
                XmlConfiguration jettyConfiguration = new XmlConfiguration(out.toString());
                jettyConfiguration.configure(server);
            }

            List<WebAppContext> handlers = Lists.newArrayList();
            {
                WebAppContext context = new WebAppContext();
                context.setServer(server);
                context.setContextPath("/cross");
                context.setWar("../cross-web/target/cross.war");
                handlers.add(context);
            }
            {
                WebAppContext context = new WebAppContext();
                context.setServer(server);
                context.setContextPath("/cross-ws");
                context.setWar("../cross-ws/target/cross-ws.war");
                handlers.add(context);
            }

            {
                HandlerList handlerList = new HandlerList();
                handlerList.setHandlers(handlers.toArray(new Handler[handlers.size()]));
                server.setHandler(handlerList);
            }

//            {
//                MysqlConnectionPoolDataSource dataSource = new MysqlConnectionPoolDataSource();
////                dataSource.setUrl("jdbc:mysql://localhost:3306/activiti?autoReconnect=true&amp;useUnicode=true&amp;characterEncoding=UTF-8");
//                dataSource.setUrl("jdbc:mysql://oss:3306/" + dbCross);
//                dataSource.setUser(dbCross);
//                dataSource.setPassword(passwd);
//                for (WebAppContext webAppContext : handlers) {
//                    new Resource(webAppContext, "java:/comp/env/jdbc/CrossDS", dataSource);
//                }
//                logger.info("adding datasource = {}", dataSource);
//            }
//            {
//                MysqlConnectionPoolDataSource dataSource = new MysqlConnectionPoolDataSource();
//                dataSource.setUrl("jdbc:mysql://oss:3306/" + dbActiviti);
//                dataSource.setUser(dbActiviti);
//                dataSource.setPassword(passwd);
//                for (WebAppContext webAppContext : handlers) {
//                    new Resource(webAppContext, "java:/comp/env/jdbc/ActivitiDS", dataSource);
//                }
//                logger.info("adding datasource = {}", dataSource);
//            }
            server.start();
            logger.debug("started jetty, public url = {}", getJettyBaseUrl());
        }

        public void startSelenium() throws MalformedURLException {
            if (desiredCapabilities == null) {
                desiredCapabilities = DesiredCapabilities.chrome();
            }
            webDriver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), desiredCapabilities);
        }

        public void stopJetty() throws Exception {
            if (server != null) {
                logger.info("stopping jetty");
                try {
                    server.stop();
                } catch (Exception e) {
                    logger.error("error stopping jetty", e);
                }
                server = null;
            }
        }

        public void stopSelenium() {
            if (webDriver != null) {
                try {
                    webDriver.close();
                    webDriver.quit();
                } catch (Exception e) {
                    logger.error("error stopping selenium", e);
                }
            }
            webDriver = null;
        }

        public void startEverything() throws Exception {
            prepareDatabase();
            startJetty();
            startSelenium();
        }

        public void stopEverything() throws Exception {
            stopSelenium();
            stopJetty();
            cleanDatabase();
        }

        public WebDriver getWebDriver() {
            return webDriver;
        }

        private String myIp = null;

        public String getJettyBaseUrl() throws SocketException, IOException {
            if (myIp == null) {
                Socket socket = new Socket("google.com", 80);
                myIp = socket.getLocalAddress().toString();
                socket.close();
            }

//            String ipAddr = NetworkInterface.getNetworkInterfaces().nextElement().getInetAddresses().nextElement().getHostAddress();
            return "http://" + myIp + ":" + ((ServerConnector) server.getConnectors()[0]).getLocalPort();
        }

    }

    protected static SelTestUtils selTestUtils;

    public static SelTestUtils getSelTestUtils() {
        if (selTestUtils == null) {
            selTestUtils = new SelTestUtils();
        }
        return selTestUtils;
    }

}
