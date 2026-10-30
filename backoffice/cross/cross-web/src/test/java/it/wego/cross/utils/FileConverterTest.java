package it.wego.cross.utils;

import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;

import org.hamcrest.core.Is;
import org.jodconverter.core.office.OfficeException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class FileConverterTest {

	private String name;
	private File input;
	private static JODConverter jODConverterTest;

	@BeforeClass
	public static void init() throws OfficeException {
		jODConverterTest = new JODConverter();
		jODConverterTest.start();
	}
	
	@AfterClass
	public static void end() {
		jODConverterTest.stop();
	}
	
	public FileConverterTest(String name, File input) {
		super();
		this.name = name;
		this.input = input;
	}

	@Parameters(name = "{0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {

			{ "From doc",  Paths.get("src","test","resources", "jodconverter","esempio.doc").toFile()},
			{ "From docx",  Paths.get("src","test","resources", "jodconverter","esempio.docx").toFile()},
			{ "From odt",  Paths.get("src","test","resources", "jodconverter","esempio.odt").toFile()},
			{ "From xls",  Paths.get("src","test","resources", "jodconverter","esempio.xls").toFile()},
			{ "From xlsx",  Paths.get("src","test","resources", "jodconverter","esempio.xlsx").toFile()},
			{ "From p7m",  Paths.get("src","test","resources", "jodconverter","esempio.pdf.p7m").toFile()},
		});
	}
	
	@Test
	public void test() throws Exception {
		
		File output = new File(input.getPath().concat(".pdf"));
		if(output.exists())
			output.delete();
		
		assertThat(output.exists(), Is.is(false));
		
		jODConverterTest.convert(input.getName().endsWith("p7m")?FileUtilities.extractTxtFileFromP7M(input):input, output);
		assertThat(output.exists(), Is.is(true));
		
		if(output.exists())
			output.delete();
		
		if(input.getName().endsWith("p7m"))
			new File(input.getParent()+"\\tempp7m").delete();
	}
}
