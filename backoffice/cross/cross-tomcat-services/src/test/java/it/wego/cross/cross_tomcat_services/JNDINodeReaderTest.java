package it.wego.cross.cross_tomcat_services;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import it.wego.cross.cross_tomcat_services.utils.JNDINodeReader;

import static org.hamcrest.MatcherAssert.assertThat;

class JNDINodeReaderTest {

	@Test
	void test() throws SAXException, IOException, ParserConfigurationException {
		String path = "src"+File.separator+ "test"+File.separator+ "resources"+File.separator+ "server.test.xml";
		File serverTest = new File(path);
		JNDINodeReader reader = new JNDINodeReader(serverTest);
		assertThat(reader.getDriver(), Is.is("com.mysql.jdbc.Driver"));
		assertThat(reader.getUrl(), Is.is("jdbc:mysql://localhost:3306/opencross?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8"));
		assertThat(reader.getUser(), Is.is("root"));
		assertThat(reader.getPwd(), Is.is("root"));
	}

}
