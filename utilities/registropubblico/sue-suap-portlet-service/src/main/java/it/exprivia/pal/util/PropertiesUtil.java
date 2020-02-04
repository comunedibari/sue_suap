package it.exprivia.pal.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertiesUtil {
	
	public static Properties getProperties() throws IOException {
		InputStream is = null;		
		try {
			is = PropertiesUtil.class.getClassLoader().getResourceAsStream("META-INF/service.properties");
			
			if (is != null) {
				Properties prop = new Properties();
				prop.load(is);
				
				return prop;
			} else
				throw new FileNotFoundException("service.properties non trovato");
		} finally {
			if (is != null)
				try {
					is.close();
				} catch (IOException e) {
				}
		}
	}
}