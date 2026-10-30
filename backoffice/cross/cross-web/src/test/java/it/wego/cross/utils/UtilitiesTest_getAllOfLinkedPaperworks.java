package it.wego.cross.utils;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.hamcrest.core.Is;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import it.wego.cross.dto.dozer.PraticaDTO;

@RunWith(Parameterized.class)
public class UtilitiesTest_getAllOfLinkedPaperworks {

	private static int upperbound = 10000;
	private static int counter = 1;

	@Parameters(name = "{0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {

			{ "Test", 
				preparePaperwork(),
				1,
				Arrays.asList(Arrays.asList(10001))
			},
			{ "Test 2", 
				preparePaperwork2(),
				4,
				Arrays.asList(
						Arrays.asList(10001),
						Arrays.asList(10001, 10005, 10004),
						Arrays.asList(10003, 10001),
						Arrays.asList(10003))
			},
			{ "Test 3", 
				preparePaperwork3(),
				3,
				Arrays.asList(
						Arrays.asList(10001 ,10004),
						Arrays.asList(10001, 10004),
						Arrays.asList(10002, 10003))
			},
			{ "Test 4", 
				preparePaperwork4(),
				5,
				Arrays.asList(
						Arrays.asList(10001, 10004 , 10005, 10006),
						Arrays.asList(10001, 10005),
						Arrays.asList(10002),
						Arrays.asList(10002, 10003),
						Arrays.asList(10002))
			},
			{ "Test 5", 
				preparePaperwork5(),
				3,
				Arrays.asList(
						Arrays.asList(10001,10004),
						Arrays.asList(10001,10004),
						Arrays.asList(10002,10003, 10001))
			},
			{ "Test 6", 
				preparePaperwork6(),
				3,
				Arrays.asList(
						Arrays.asList(10001,10003),
						Arrays.asList(10002,10001,10004),
						Arrays.asList(10003,10001))
			},
			{ "Test 7", 
				preparePaperwork7(),
				2,
				Arrays.asList(
						Arrays.asList(10001, 10003),
						Arrays.asList(10002, 10001))
			},
		});
	}

	private PraticaDTO pratica;
	private Integer expectedSize;
	private List<List<Integer>> expectedParents;

	public UtilitiesTest_getAllOfLinkedPaperworks(String name, PraticaDTO pratica, Integer expectedSize, List<List<Integer>> expectedParents) {
		super();
		this.pratica = pratica;
		this.expectedSize = expectedSize;
		this.expectedParents = expectedParents;
	}

	@Test
	public void test() {
		List<PraticaDTO> currentResult = Utilities.getAllOfLinkedPaperworks(pratica);
		assertThat(currentResult.size(), Is.is(expectedSize));
		int i = 0;
		for(PraticaDTO praticaDto: currentResult) {
		System.out.println(praticaDto.getIdPratica()+ "->"+praticaDto.getLinkedToList());
			assertThat(praticaDto.getLinkedToList().stream().map(p->p.getIdPratica()).collect(Collectors.toList()), 
					equalTo(expectedParents.get(i++)));
		}
	}

	private static PraticaDTO preparePaperwork() {
		counter = 1;
		PraticaDTO paperwork1 = generate();
		PraticaDTO paperwork2 = generate();

		paperwork1.setPraticheAssociate(Arrays.asList(paperwork2));
		paperwork2.setPraticheAssociate(Arrays.asList(paperwork1));

		return paperwork1;
	}

	private static PraticaDTO preparePaperwork2() {
		counter = 1;
		PraticaDTO paperwork1 = generate();
		PraticaDTO paperwork2 = generate();
		PraticaDTO paperwork3 = generate();
		PraticaDTO paperwork4 = generate();
		PraticaDTO paperwork5 = generate();
		paperwork1.setPraticheAssociate(Arrays.asList(paperwork2,paperwork3,paperwork5));
		paperwork2.setPraticheAssociate(Arrays.asList(paperwork1));

		paperwork3.setPraticheAssociate(Arrays.asList(paperwork4,paperwork5,paperwork1));
		paperwork4.setPraticheAssociate(Arrays.asList(paperwork3));
		paperwork5.setPraticheAssociate(Arrays.asList(paperwork3 , paperwork1));

		return paperwork1;
	}

	private static PraticaDTO preparePaperwork3() {
		counter = 1;
		PraticaDTO paperwork1 = generate();
		PraticaDTO paperwork2 = generate();
		PraticaDTO paperwork3 = generate();
		PraticaDTO paperwork4 = generate();
		paperwork1.setPraticheAssociate(Arrays.asList(paperwork2, paperwork3));
		paperwork2.setPraticheAssociate(Arrays.asList(paperwork1, paperwork4));
		paperwork3.setPraticheAssociate(Arrays.asList(paperwork4, paperwork1));
		paperwork4.setPraticheAssociate(Arrays.asList(paperwork3, paperwork2));
		return paperwork1;
	}

	private static PraticaDTO preparePaperwork4() {
		counter = 1;
		PraticaDTO paperwork1 = generate();
		PraticaDTO paperwork2 = generate();
		PraticaDTO paperwork3 = generate();
		PraticaDTO paperwork4 = generate();
		PraticaDTO paperwork5 = generate();
		PraticaDTO paperwork6 = generate();
		paperwork1.setPraticheAssociate(Arrays.asList(paperwork2, paperwork3));
		paperwork2.setPraticheAssociate(Arrays.asList(paperwork1, paperwork4, paperwork5, paperwork6));
		paperwork3.setPraticheAssociate(Arrays.asList(paperwork5, paperwork1));
		paperwork4.setPraticheAssociate(Arrays.asList(paperwork2));
		paperwork5.setPraticheAssociate(Arrays.asList(paperwork3, paperwork2));
		paperwork6.setPraticheAssociate(Arrays.asList(paperwork2));
		return paperwork1;
	}

	private static PraticaDTO preparePaperwork5() {
		counter = 1;
		PraticaDTO paperwork1 = generate();
		PraticaDTO paperwork2 = generate();
		PraticaDTO paperwork3 = generate();
		PraticaDTO paperwork4 = generate();
		paperwork1.setPraticheAssociate(Arrays.asList(paperwork2, paperwork3, paperwork4));
		paperwork2.setPraticheAssociate(Arrays.asList(paperwork1, paperwork4));
		paperwork3.setPraticheAssociate(Arrays.asList(paperwork1, paperwork4));
		paperwork4.setPraticheAssociate(Arrays.asList(paperwork1, paperwork2, paperwork3));
		return paperwork1;
	}
	private static PraticaDTO preparePaperwork6() {
		counter = 1;
		PraticaDTO paperwork1 = generate();
		PraticaDTO paperwork2 = generate();
		PraticaDTO paperwork3 = generate();
		PraticaDTO paperwork4 = generate();
		paperwork1.setPraticheAssociate(Arrays.asList(paperwork2, paperwork3, paperwork4));
		paperwork2.setPraticheAssociate(Arrays.asList(paperwork1, paperwork3));
		paperwork3.setPraticheAssociate(Arrays.asList(paperwork1, paperwork2, paperwork4));
		paperwork4.setPraticheAssociate(Arrays.asList(paperwork1, paperwork3));
		return paperwork1;
	}

	private static PraticaDTO preparePaperwork7() {
		counter = 1;
		PraticaDTO paperwork1 = generate();
		PraticaDTO paperwork2 = generate();
		PraticaDTO paperwork3 = generate();
		paperwork1.setPraticheAssociate(Arrays.asList(paperwork2, paperwork3));
		paperwork2.setPraticheAssociate(Arrays.asList(paperwork1, paperwork3));
		paperwork3.setPraticheAssociate(Arrays.asList(paperwork1, paperwork2));
		return paperwork1;
	}

	private static PraticaDTO generate() {
		PraticaDTO paperwork = new PraticaDTO();
		paperwork.setIdPratica(upperbound+counter++);
		return paperwork;
	}

}
