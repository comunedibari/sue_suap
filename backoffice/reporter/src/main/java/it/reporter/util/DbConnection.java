/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.reporter.util;

import it.reporter.xsd.connections.ConnectionsRoot;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import it.reporter.xsd.data.DocumentRoot.Connections;
import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author Piergiorgio
 */
public class DbConnection {

    Connection connection = null;
    String driver;
    String url;
    String utente;
    String password;

    public Connection getConnection(Connections connectionParam) throws Exception {

        if (connection == null) {
            if (connectionParam != null) {
                if (connectionParam.getConnectionString() != null) {
                    driver = connectionParam.getConnectionString().getDriver().trim();
                    url = connectionParam.getConnectionString().getConnection().trim();
                    utente = connectionParam.getConnectionString().getUser().trim();
                    password = connectionParam.getConnectionString().getPassword().trim();
                    Class.forName(driver);
                    connection = DriverManager.getConnection(url, utente, password);

                } else {
                    if (connectionParam.getConnectionJNDI() != null) {
                        Context initContext = new InitialContext();
                        DataSource dataSource = (DataSource) initContext.lookup(connectionParam.getConnectionJNDI().trim());
                        connection = dataSource.getConnection();

                    } else {
                        if (connectionParam.getConnectionFileExt() != null) {
                            File f2 = new File(connectionParam.getConnectionFileExt());
                            JAXBContext jaxbCtx = JAXBContext.newInstance(ConnectionsRoot.class.getPackage().getName());
                            Unmarshaller unmarshaller = jaxbCtx.createUnmarshaller();
                            ConnectionsRoot cr = (ConnectionsRoot) unmarshaller.unmarshal(f2);
                            if (cr.getConnectionString() != null) {
                                driver = cr.getConnectionString().getDriver().trim();
                                url = cr.getConnectionString().getConnection().trim();
                                utente = cr.getConnectionString().getUser().trim();
                                password = cr.getConnectionString().getPassword().trim();
                                Class.forName(driver);
                                connection = DriverManager.getConnection(url, utente, password);
                            } else {
                                if (cr.getConnectionJNDI() != null) {
                                    Context initContext = new InitialContext();
                                    DataSource dataSource = (DataSource) initContext.lookup(connectionParam.getConnectionJNDI().trim());
                                    connection = dataSource.getConnection();
                                }
                            }
                        }
                    }
                }
            }
        }
        return connection;
    }
}
