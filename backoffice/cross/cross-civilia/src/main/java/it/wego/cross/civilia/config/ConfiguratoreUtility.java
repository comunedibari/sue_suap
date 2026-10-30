package it.wego.cross.civilia.config;

import java.util.Properties;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class ConfiguratoreUtility {
	
    private static final Logger logger = Logger.getLogger(ConfiguratoreUtility.class.getName());

	public static String APPSADGIO = "APPSERVIZIADOMANDAGIO";

    private static String file_reference = "/civilia.properties";

    private static Properties properties;

    private static final String Url = "proxySet";

    private static final String user_db = "userDB";

    private static final String password_db = "passwordDB";

    private static final String log_properties_file = "logPropertiesFile";

    private static final String utente = "utente";

    private static final String password = "password";

    private static ConfiguratoreUtility instance;

    public static synchronized ConfiguratoreUtility getInstance() throws ConfigException {
        if (instance == null)
            instance = new ConfiguratoreUtility();
        return instance;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setUtente(String utente) throws ConfigException {
        properties.setProperty("utente", utente);
    }

    public void setUrl(String url) {
        properties.setProperty("Url", url);
    }

    public void setLogPropertiesFile(String log_properties_file) {
        properties.setProperty("logPropertiesFile",
                log_properties_file);
    }

    public void setPasswordDb(String password_db) {
        properties.setProperty("passwordDB",
                password_db);
    }


    public ConfiguratoreUtility() throws ConfigException { }

    private void acquireProperties() {
        properties = new Properties();
        try {
            if (System.getProperty("config") != null)
                file_reference = "/" + System.getProperty("config");
            InputStream propertiesAsStream = ConfiguratoreUtility.class.getResourceAsStream(file_reference);
            if (propertiesAsStream != null) {
                properties.load(propertiesAsStream);
            } else {
                throw new FileNotFoundException(String.valueOf(file_reference) + "non trovato...tutti i parametri sono da configurare");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clearAllProperties() {
        properties = new Properties();
    }

    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    public String getUtente() {
        return getPropertyValue("utente");
    }

    public String getJdbcUrl() {
        return getPropertyValue("jdbcUrl");
    }


    public String getLogPropertiesFile() {
        return getPropertyValue("logPropertiesFile");
    }

    public String getPasswordDb() {
        return getPropertyValue("passwordDB");
    }


    public String getTempDir() {
        return getPropertyValue("tempDir");
    }


    public String getPropertyValue(String propertyKey) {
        return properties.getProperty(propertyKey);
    }

    public String getUserDb() {
        return getPropertyValue("userDB");
    }

    public void printPropertiesLoaded() {
        try {
            logger.debug("Properties caricate from " + file_reference);
            Enumeration<?> e = properties.propertyNames();
            while (e.hasMoreElements()) {
                String key = (String)e.nextElement();
                if (key.contains("password") ) {
                    logger.debug(String.valueOf(key) + "=<************>");
                    continue;
                }
                logger.debug(String.valueOf(key) + "=<" + properties.getProperty(key) + ">");
            }
            ClassLoader cl = ClassLoader.getSystemClassLoader();
            URL[] urls = ((URLClassLoader)cl).getURLs();
            byte b;
            int i;
            URL[] arrayOfURL1;
            for (i = (arrayOfURL1 = urls).length, b = 0; b < i; ) {
                URL url = arrayOfURL1[b];
                logger.debug(url.getFile());
                b++;
            }
        } catch (Exception e) {
            logger.error(
                    "Errore nella lettura dei parametri di inizializzazione:",
                    e);
        }
    }

    private void setLogProperties() throws ConfigException {
        try {
            String logPropertiesFile = (getLogPropertiesFile() == null) ? "/log4j.properties" :
                    getLogPropertiesFile();
            URL log4jConfig = ConfiguratoreUtility.class.getResource(logPropertiesFile);
            PropertyConfigurator.configure(log4jConfig);
        } catch (Exception e) {
            throw new ConfigException("Errore in configurazione del log", e);
        }
    }

    public String getUser() {
        return getPropertyValue("mail.user");
    }

    public String getPassword() {
        return getPropertyValue("mail.password");
    }
}

