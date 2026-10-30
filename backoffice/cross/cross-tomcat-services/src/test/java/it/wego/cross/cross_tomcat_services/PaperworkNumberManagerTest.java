package it.wego.cross.cross_tomcat_services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.hamcrest.core.Is;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import it.wego.cross.cross_tomcat_services.db.DbReader;
import it.wego.cross.cross_tomcat_services.utils.PairKey;
import it.wego.cross.cross_tomcat_services.utils.PaperworkNumberException;

@RunWith(PowerMockRunner.class)
class PaperworkNumberManagerTest {

	@ParameterizedTest
	@MethodSource("provideInputData")
	void shouldGetNewNumber(List<Integer> paperworks, List<Integer> institutions, Map<PairKey<Integer,Integer>,Integer> map) throws Exception {
		PaperworkNumberManager manager = prepareManager(paperworks, institutions, map);
		assertThat(manager.getNumber(100,200),Is.is(5));
		assertThat(manager.getNewNumber(100,200),Is.is(6));
		assertThat(manager.getNumber(106,206),Is.is(0));
		assertThat(manager.getNewNumber(106,206),Is.is(1));
	}

	@ParameterizedTest
	@MethodSource("provideInputData")
	void shouldntGetNewNumber(List<Integer> paperworks, List<Integer> institutions, Map<PairKey<Integer,Integer>,Integer> map) throws Exception {
		PaperworkNumberManager manager = prepareManager(paperworks, institutions, map);
		
		Exception exception = assertThrows(PaperworkNumberException.class, () -> {
			manager.getNewNumber(107,200);
	    });

	    String expectedMessage = "Error unknown key:PairKey [key1=107, key2=200]";
	    String actualMessage = exception.getMessage();

	    assertThat(actualMessage, Is.is(expectedMessage));
	}
	
	private static Stream<Arguments> provideInputData() {
		Map<PairKey<Integer,Integer>,Integer> map = new HashMap<>();
		map.put(new PairKey<>(100,200), 5);
	    return Stream.of(
	      Arguments.of(
	    		  Arrays.asList(100,101,102,103,104,105,106), 
	    		  Arrays.asList(200,201,202,203,204,205,206), 
	    		  map)
	    );
	}

	private PaperworkNumberManager prepareManager(List<Integer> institutions, List<Integer> paperworks, Map<PairKey<Integer,Integer>,Integer> map) throws Exception {
		DbReader dbReaderMock = mock(DbReader.class);
		when(dbReaderMock.findAllPaperworkTypes()).thenReturn(paperworks);
		when(dbReaderMock.findAllInstitutionId()).thenReturn(institutions);
		when(dbReaderMock.findDataForCurrentYear()).thenReturn(map);
		return new PaperworkNumberManager(dbReaderMock);
	}	
}
