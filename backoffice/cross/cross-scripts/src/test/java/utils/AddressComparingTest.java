package utils;


import static org.hamcrest.MatcherAssert.assertThat;

import java.util.stream.Stream;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;


class AddressComparingTest {
	static AddressesComparator ac;
	
	@BeforeAll
	public static void init() {
		ac = new AddressesComparator();
	}
	
	@ParameterizedTest
	@MethodSource("provideHappyCases")
	void shouldBeConsideredEqual(String addressDb, String addressXml) throws Exception {
		assertThat(ac.compare(addressDb, addressXml),Is.is(true));
	}

	private static Stream<Arguments> provideHappyCases() {

		return Stream.of(
				Arguments.of(
						"Via prova",
						"Via prova"
						),
				Arguments.of(
						"Via prova",
						"prova"
						),
				Arguments.of(
						"Via prova",
						"Via ProVa"
						),
				Arguments.of(
						"VIA ROSARIO LIVATINO",
						"VIA R. LIVATINO"
						),
				Arguments.of(
						"C.so Alcide De Gasperi",
						"Corso Alcide De Gasperi"
						),
				Arguments.of(
						"VIA GIACOMO PUCCINI",
						"VIA PUCCINI"
						),
				Arguments.of(
						"TRAV. AL N.45 DI PIETRO NENNI",
						"TRAV. AL N°45 DI PIETRO NENNI"
						),
				Arguments.of(
						"V.N. CERROTTI",
						"VIA VINCENZO NUNZIO CERROTTI"
						),
				Arguments.of(
						"STRADA PROV.LE 60 PONTE SAN GIORGIO",
						"S.P. 60 PONTE SAN GIORGIO"
						),
				Arguments.of(
						"VIA O.FLACCO",
						"VIALE ORAZIO FLACCO 26/A"
						),
				Arguments.of(
						"VIA O.FLACCO",
						"VIALE ORAZIO FLACCO 26\\A"
						),
				Arguments.of(
						"VIA CARLO COLLODI",
						"VIA CARLO COLLODI 33"
						),
				Arguments.of(
						"VIA VICINALE VECCHIA MATERA",
						"via vic.le vecchia matera"
						),
				Arguments.of(
						"via Primo Maggio",
						"I MAGGIO"),
				Arguments.of(
						"PARCO DEGLI ULIVI",
						"PARCO DEGLI ULIVI, AURICARRO"
						),
				Arguments.of(
						"S.S. 16 KM 788,00",
						"S.S. 16 km 788,00 (Residence Macina)"
						),
				Arguments.of(
						"via Ten. Col. Fiore",
						"VIA TENENTE COLONNELLO FIORE"
						),
				Arguments.of(
						"S.STATALE N.16",
						"S.S.16 - STRADA VICINALE \"TORR"
						),
				Arguments.of(
						"V. prova",
						"via prova"
						),
				Arguments.of(
						"Gentile",
						"Via"
						)
				);
	}


	@ParameterizedTest
	@MethodSource("provideErrorCases")
	void shouldntBeConsideredEqual(String addressDb, String addressXml) throws Exception {
		assertThat(ac.compare(addressDb, addressXml),Is.is(false));
	}

	private static Stream<Arguments> provideErrorCases() {
		return Stream.of(
				Arguments.of(
						"Via provare",
						"Via prova"
						),
				Arguments.of(
						"Via Torricella",
						"Via Giosuè Carducci"
						),
				Arguments.of(
						"Via F. Torricella",
						"Via Torricella Giosuè Carducci"
						),
				Arguments.of(
						"VICO TENENTE MARIO CAPUTI",
						"VICO LERARIO"
						),
				Arguments.of(
						"Via prova test",
						"Via test prova"
						),
				Arguments.of(
						"Via test prova",
						"Via prova test"
						),
				Arguments.of(
						"Via Vulture",
						"Via Gaetano Filangieri"
						),
				Arguments.of(
						"Via",
						"Gentile"
						)
				);
	}
}
