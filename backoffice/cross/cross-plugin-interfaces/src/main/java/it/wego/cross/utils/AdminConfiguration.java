/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author giuseppe
 */
public class AdminConfiguration {

    private static AdminConfiguration instance = null;
    private Properties props;

    private AdminConfiguration() throws IOException {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("admin.properties");
        props = new Properties();
        props.load(inputStream);
    }

    public static synchronized AdminConfiguration getInstance() throws IOException {
        if (instance == null) {
            instance = new AdminConfiguration();
        }
        return instance;
    }

    public String getHost() {
        String host = props.getProperty("admin.mail.host");
        return host;
    }

    public String getPort() {
        String port = props.getProperty("admin.mail.port");
        return port;
    }

    public boolean isAutenticated() {
        Boolean isAuthenticated = Boolean.valueOf(props.getProperty("admin.mail.auth"));
        return isAuthenticated;
    }

    public String getUsername() {
        String username = props.getProperty("admin.mail.username");
        return username;
    }

    public String getPassword() {
        String password = props.getProperty("admin.mail.password");
        return password;
    }

    public boolean useSSL() {
        Boolean useSSL = Boolean.valueOf(props.getProperty("admin.mail.usessl"));
        return useSSL;
    }

    public boolean useTLS() {
        Boolean useTLS = Boolean.valueOf(props.getProperty("admin.mail.usetls"));
        return useTLS;
    }

    public String getSender() {
        String sender = props.getProperty("admin.mail.sender");
        return sender;
    }

    public String getReceiver1() {
        String receiver = props.getProperty("admin.mail.receiver1");
        return receiver;
    }

    public String getReceiver2() {
        String receiver = props.getProperty("admin.mail.receiver2");
        return receiver;
    }

    public String getReceiver3() {
        String receiver = props.getProperty("admin.mail.receiver3");
        return receiver;
    }

    public String getTempFolder() {
        String folder = props.getProperty("admin.tmp.folder") + File.separator + "cross_pratiche_tmp";
        return folder;
    }
}
