package it.wego.cross.people.utils;

import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.RollingFileAppender;

public class LogClass {

    private static Logger log =  Logger.getLogger(LogClass.class.getName());
    private static boolean initializationFlag = false;
    private static String fileName;
    private static Properties properties;
    
    private static void intializeLogger(){
        log.setLevel(Level.DEBUG);

		try {
			InputStream input = LogClass.class.getClassLoader().getResourceAsStream(myUtility.PROP_FILE_NAME);
            Properties prop = new Properties();

            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                return;
            }

            prop.load(input);

            fileName = prop.getProperty("logfile");
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
			
	
		
        RollingFileAppender appender = new RollingFileAppender();
        appender.setAppend(true);
        appender.setMaxFileSize("10MB");
        appender.setMaxBackupIndex(1);
        appender.setFile(fileName + ".log");
        appender.activateOptions();

        PatternLayout layOut = new PatternLayout();
        layOut.setConversionPattern("%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n");
        appender.setLayout(layOut);

        log.addAppender(appender);
    }

    public static Logger getLogger(){
        if(initializationFlag == false){
            intializeLogger();
            initializationFlag = true;
            return LogClass.log;
        }
        else{
            return LogClass.log;
        }
    }

    public static void trace(String message){
    	LogClass.getLogger().debug(message);
    }

    public static void debug(String message){
    	LogClass.getLogger().debug(message);
    }

    public static void error(String message){
    	LogClass.getLogger().debug(message);
    }

    public static void info(String message){
    	LogClass.getLogger().debug(message);
    }

    public static void setFileName(String fileName){
        LogClass.fileName = fileName;
    }
}