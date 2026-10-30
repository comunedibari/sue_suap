/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.utils;

import static org.hamcrest.MatcherAssert.assertThat;

import org.hamcrest.core.Is;
import org.junit.Test;

/**
 *
 * @author aleph
 */
public class FileUtilitiesTest {

	@Test
	public void testing() {
		String path = "C:\\Programs\\apache-tomcat-8.0.48_8081_bo\\cross_files\\attachments\\esempio.pdf.p7m";
		String ret = FileUtilities.getParent(path);
		assertThat(ret,Is.is("C:\\Programs\\apache-tomcat-8.0.48_8081_bo\\cross_files\\attachments"));
	}
}
