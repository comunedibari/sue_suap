package it.wego.cross.utils;

import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.Collection;

import org.hamcrest.core.Is;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;


@RunWith(Parameterized.class)
public class PraticaUtils_SplitFullNameTest {

	@Parameters(name="{0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
			{ "No Name", "Caffetteria" , new Identification("Caffetteria")},
			{ "Simple Name", "Mario Rossi" , new Identification("Mario Rossi", "Mario", "Rossi")},
			{ "Simple Name with spaces", "Mario   Rossi" , new Identification( "Mario Rossi", "Mario", "Rossi")},
			{ "Surname with special character", "Mario Dell'aera" , new Identification("Mario Dell'aera", "Mario", "Dell'aera")},
			{ "Full name with special characters", "Dell'aera Dell'aera" ,  new Identification("Dell'aera Dell'aera", "Dell'aera", "Dell'aera")},
			{ "Name of two words", "Mario Rossi Rossi" , new Identification("Mario Rossi Rossi", "Mario", "Rossi Rossi")},
			{ "Name with no trimmed", "   Mario Rossi   " , new Identification("Mario Rossi", "Mario", "Rossi")},
		});
	}

	private final String input;
	private final Identification expectedOutput;

	public PraticaUtils_SplitFullNameTest(String name, String input, Identification expectedOutput) {
		this.input = input;
		this.expectedOutput = expectedOutput;
	}

	
	@Test
	public void shouldBeSplitted() {
		Identification splittedFullName = PraticaUtils.splitFullName(this.input);

		assertThat(splittedFullName.getFullName(),Is.is(this.expectedOutput.getFullName()));
		assertThat(splittedFullName.hasSplitted(),Is.is(this.expectedOutput.hasSplitted()));
		if(splittedFullName.hasSplitted()) {
			assertThat(splittedFullName.getName(),Is.is(this.expectedOutput.getName()));
			assertThat(splittedFullName.getSurname(),Is.is(this.expectedOutput.getSurname()));
		}
	}
}