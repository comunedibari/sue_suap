package it.wego.cross.webservices.utils;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.stream.Stream;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import it.wego.cross.dao.LookupDao;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.LkTipoPratica;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.Processi;

class CustomPraticaHandlerTest {

	LookupDao lookupDao;
	CustomPraticaHandler cph;
	@BeforeEach
	public void init() {
		this.lookupDao = mock(LookupDao.class);
		when(this.lookupDao.findLkTipoPraticaByType(Mockito.anyString())).then(new Answer<LkTipoPratica>() {
			@Override 
			public LkTipoPratica answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				LkTipoPratica ret = new LkTipoPratica();
				ret.setTipoPratica((String) args[0]);
				return ret;
			}
		});
		cph = new CustomPraticaHandlerImpl(this.lookupDao);
	}

	@ParameterizedTest
	@MethodSource("shouldFineNewTypeParameters")
	void shouldFineNewType(String code, String subject, Integer processId, String expectedTypeCode) {
		Pratica p = new Pratica();
		LkComuni c = new LkComuni();
		c.setCodCatastale(code);
		p.setOggettoPratica(subject);
		Processi pr = new Processi();
		pr.setIdProcesso(processId);
		p.setIdComune(c);
		p.setIdProcesso(pr);
		LkTipoPratica lk = cph.searchNewType(p);
		assertThat(lk.getTipoPratica(), Is.is(expectedTypeCode));
	}

	private static Stream<Arguments> shouldFineNewTypeParameters() {
		return Stream.of(
				Arguments.of("E038", "Comunicazione di fine lavori, Segnalazione Certificata per Agibilità", 8, "AGIB"),
				Arguments.of("E038", "Comunicazione di fine lavori", 9, "FINLAV"),
				Arguments.of("E038", "Comunicazione Inizio Lavori art. 6, comma 1, lett. e-bis del d.P.R. n. 380/2001 e n. 26 Tabella A, Sez. II del d.lgs  n. 222/2016", 9, "CIL")
				);
	}

	@ParameterizedTest
	@MethodSource("shouldntFineNewTypeParameters")
	void shouldntFineNewType(String code, String subject, Integer processId) {
		Pratica p = new Pratica();
		LkComuni c = new LkComuni();
		c.setCodCatastale(code);
		p.setOggettoPratica(subject);
		Processi pr = new Processi();
		pr.setIdProcesso(processId);
		p.setIdComune(c);
		p.setIdProcesso(pr);
		LkTipoPratica lk = cph.searchNewType(p);
		assertThat(lk, Is.is(nullValue()));
	}

	private static Stream<Arguments> shouldntFineNewTypeParameters() {
		return Stream.of(
				Arguments.of("E038", "Comunicazione di fine lavori, Segnalazione Certificata per Agibilità", 9),
				Arguments.of("E039", "Comunicazione di fine lavori, Segnalazione Certificata per Agibilità", 8),
				Arguments.of("E038", "Comunicazione di fine lavori", 10),
				Arguments.of("E038", "Comunicazione Inizio Lavori art. 6, comma 1, lett. e-bis del d.P.R. n. 380/2001 e n. 26 Tabella A, Sez. II del d.lgs  n. 222/2015", 9)
				);
	}
}
