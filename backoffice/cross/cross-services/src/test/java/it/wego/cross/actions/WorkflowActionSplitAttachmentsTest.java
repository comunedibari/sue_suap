///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package it.wego.cross.actions;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.Arrays;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.stream.Stream;
//
//import org.hamcrest.core.Is;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.Arguments;
//import org.junit.jupiter.params.provider.MethodSource;
//
//import it.wego.cross.dto.ErroreDTO;
//import it.wego.cross.entity.Allegati;
//import it.wego.cross.exception.WorkflowException;
//import it.wego.cross.service.ConfigurationService;
//
//class WorkflowActionSplitAttachmentsTest {
//	
//	private static int ID_PRATICA = 1;
//	private static int ID_ENTE = 1;
//	private static int ID_COMUNE = 1;
//	
//	WorkflowAction  wa;
//	
//	@BeforeEach
//	public void init() {
//		ErroriAction erroriAction = mock(ErroriAction.class);
//		ConfigurationService configuration = mock(ConfigurationService.class);
//		wa = new WorkflowAction(erroriAction, configuration);
//		when(erroriAction.getError(anyString(), anyString(), anyInt())).thenReturn(new ErroreDTO());
//		when(configuration.findByName(anyString(), anyInt(), anyInt())).thenReturn(null);
//	}
//
//	@ParameterizedTest
//	@MethodSource("provideTestWithNumberParameter")
//	void testAttachmentsWithNumberParameter(List<Allegati> attachments, Integer inputMaxNumber, List<Integer> sizePerListExpected) throws WorkflowException {
//		List<List<Allegati>> listOfAttachments = wa.composeListOfAttachments(ID_PRATICA, attachments, ID_ENTE, ID_COMUNE, null, new Long(inputMaxNumber));
//		assertThat(listOfAttachments.size(), Is.is(sizePerListExpected.size()));
//		for(int i = 0; i < listOfAttachments.size(); i++)
//			assertThat(listOfAttachments.get(i).size(), Is.is(sizePerListExpected.get(i)));
//	}
//
//	@ParameterizedTest
//	@MethodSource("provideAttachmentsFilePathAttribute")
//	void testAttachmentsWithFilePath(List<Allegati> attachments, Long inputMaxSize, List<Integer> sizePerListExpected) throws WorkflowException {
//		List<List<Allegati>> ret = wa.composeListOfAttachments(ID_PRATICA, attachments, ID_ENTE, ID_COMUNE, inputMaxSize, null);
//		assertThat(ret.size(), Is.is(sizePerListExpected.size()));
//		for(int i = 0; i < ret.size(); i++) {
//			assertThat(ret.get(i).size(), Is.is(sizePerListExpected.get(i)));
//		}
//	}
//
//	@ParameterizedTest
//	@MethodSource("provideAttachmentsWithFileAttribute")
//	void testAttachmentsWithFile(List<Allegati> attachments, Long inputMaxSize, List<Integer> sizePerListExpected) throws WorkflowException {
//		List<List<Allegati>> ret = wa.composeListOfAttachments(ID_PRATICA, attachments, ID_ENTE, ID_COMUNE, inputMaxSize, null);
//		assertThat(ret.size(), Is.is(sizePerListExpected.size()));
//		for(int i = 0; i < ret.size(); i++) {
//			assertThat(ret.get(i).size(), Is.is(sizePerListExpected.get(i)));
//		}
//	}
//	
//	@ParameterizedTest
//	@MethodSource("provideAttachmentsWithDifferentFileTypes")
//	void testAttachmentsWithDifferentFileTypes(List<Allegati> attachments, Long inputMaxSize, List<Integer> sizePerListExpected) throws WorkflowException {
//		List<List<Allegati>> ret = wa.composeListOfAttachments(ID_PRATICA, attachments, ID_ENTE, ID_COMUNE, inputMaxSize, null);
//		assertThat(ret.size(), Is.is(sizePerListExpected.size()));
//		for(int i = 0; i < ret.size(); i++) {
//			assertThat(ret.get(i).size(), Is.is(sizePerListExpected.get(i)));
//		}
//	}
//
//	@ParameterizedTest
//	@MethodSource("provideZipAttachmentWithFileAttribute")
//	void testZipAttachmentWithFileAttribute(List<Allegati> attachments, Long inputMaxSize, List<Integer> sizePerListExpected) throws WorkflowException {
//		List<List<Allegati>> ret = wa.composeListOfAttachments(ID_PRATICA, attachments, ID_ENTE, ID_COMUNE, inputMaxSize, null);
//		assertThat(ret.size(), Is.is(sizePerListExpected.size()));
//		for(int i = 0; i < ret.size(); i++) {
//			assertThat(ret.get(i).size(), Is.is(sizePerListExpected.get(i)));
//		}
//	}
//
//	@ParameterizedTest
//	@MethodSource("provideZipWithFilePathAttribute")
//	void testZipWithFilePathAttribute(List<Allegati> attachments, Long inputMaxSize, List<Integer> sizePerListExpected) throws WorkflowException {
//		List<List<Allegati>> ret = wa.composeListOfAttachments(ID_PRATICA, attachments, ID_ENTE, ID_COMUNE, inputMaxSize, null);
//		assertThat(ret.size(), Is.is(sizePerListExpected.size()));
//		for(int i = 0; i < ret.size(); i++) {
//			assertThat(ret.get(i).size(), Is.is(sizePerListExpected.get(i)));
//		}
//	}
//
//	@Test
//	void testWithAllegatiObjects() throws IOException, WorkflowException {
//		List<Allegati> attachments = prepareAttachmentsWithDifferentFileTypes();
//		List<List<Allegati>> ret = wa.composeListOfAttachments(attachments, ID_PRATICA, ID_ENTE, ID_COMUNE);
//		assertThat(ret.size(), Is.is(1));
//		assertThat(ret.get(0).size(), Is.is(attachments.size()));
//		for(int i = 0; i < ret.size();i++) {
//			assertThat(ret.get(0).get(i) instanceof Allegati, Is.is(true));
//			assertThat(ret.get(0).get(i).getNomeFile(), Is.is(attachments.get(i).getNomeFile()));			
//		}
//	}
//
//	/*****************************
//	 * data methods
//	 * **************************/
//	private static Stream<Arguments> provideTestWithNumberParameter() {
//	    return Stream.of(
//	      Arguments.of(prepareAttachments(1), 1, Arrays.asList(1)),
//	      Arguments.of(prepareAttachments(5), 3, Arrays.asList(3,2)),
//	      Arguments.of(prepareAttachments(5), 6, Arrays.asList(5))
//	    );
//	}
//
//	private static List<Allegati> prepareAttachments(int dim) {
//	
//		List<Allegati> list = new LinkedList<>();
//		for(int i = 0; i < dim; i++) {
//			Allegati attachment = new Allegati();
//			String path = Paths.get("src","test","resources","Examples", "Prova1.pdf").toFile().getAbsolutePath();
//			attachment.setPathFile(path);
//			list.add(attachment);
//		}
//		
//		return list;
//	}
//
//	//---
//	
//	private static Stream<Arguments> provideAttachmentsFilePathAttribute() {
//	    return Stream.of(
//	      Arguments.of(prepareAttachmentsFilePathAttribute(1), 30000l, Arrays.asList(1)),
//	      Arguments.of(prepareAttachmentsFilePathAttribute(3), 60000l, Arrays.asList(2,1)),
//	      Arguments.of(prepareAttachmentsFilePathAttribute(3), 31000l, Arrays.asList(1,1,1))
//	    );
//	}
//	
//	private static List<Allegati> prepareAttachmentsFilePathAttribute(int dim) {
//	
//		List<Allegati>  list = new LinkedList<>();
//		for(int i = 0; i < dim; i++) {
//			Allegati attachment = new Allegati();
//			String path = Paths.get("src","test","resources","Examples", "Prova"+(i+1)+".pdf").toFile().getAbsolutePath();
//			attachment.setPathFile(path);
//			attachment.setNomeFile("Prova"+(i+1)+".pdf");
//			list.add(attachment);
//		}
//		return list;
//	}
//	
//	//---
//
//	private static Stream<Arguments> provideAttachmentsWithFileAttribute() throws IOException {
//	    return Stream.of(
//	      Arguments.of(prepareAttachmentsWithFileAttribute(1), 30000l, Arrays.asList(1)),
//	      Arguments.of(prepareAttachmentsWithFileAttribute(3), 70000l, Arrays.asList(2, 1))
//	    );
//	}
//	
//	private static List<Allegati> prepareAttachmentsWithFileAttribute(int dim) throws IOException {
//	
//		List<Allegati> list = new LinkedList<>();
//		for(int i = 1; i <= dim; i++) {
//			Allegati attachment = new Allegati();
//			Path path = Paths.get("src","test","resources","Examples", "Prova"+i+".pdf");
//
//			byte[] data = Files.readAllBytes(path);
//			attachment.setFile(data);
//			list.add(attachment);
//		}
//		
//		return list;
//	}
//	
//	//---
//	
//	private static Stream<Arguments> provideAttachmentsWithDifferentFileTypes() throws IOException {
//	    return Stream.of(
//	      Arguments.of(prepareAttachmentsWithDifferentFileTypes(), 1000000l, Arrays.asList(4)),
//	      Arguments.of(prepareAttachmentsWithDifferentFileTypes(), 100000l, Arrays.asList(2,1,1))
//	    );
//	}
//	
//	private static List<Allegati> prepareAttachmentsWithDifferentFileTypes() throws IOException {
//		List<Allegati> list = new LinkedList<>();
//		
//		Allegati attachment = new Allegati();
//		Path bytes = Paths.get("src","test","resources","Examples", "esempio.doc");
//		byte[] data = Files.readAllBytes(bytes);
//		attachment.setFile(data);
//		attachment.setNomeFile("esempio.doc");
//		list.add(attachment);
//		
//		attachment = new Allegati();
//		bytes = Paths.get("src","test","resources","Examples", "esempio.docx.pdf");
//		data = Files.readAllBytes(bytes);
//		attachment.setFile(data);
//		attachment.setNomeFile("esempio.docx.pdf");
//		list.add(attachment);
//		
//		attachment = new Allegati();
//		bytes = Paths.get("src","test","resources","Examples", "esempio.pdf.p7m");
//		data = Files.readAllBytes(bytes);
//		attachment.setFile(data);
//		attachment.setNomeFile("esempio.pdf.p7m");
//		list.add(attachment);
//
//		attachment = new Allegati();
//		String path = Paths.get("src","test","resources","Examples", "Prova1.pdf").toFile().getAbsolutePath();
//		attachment.setPathFile(path);
//		attachment.setNomeFile("Prova1.pdf");
//		list.add(attachment);
//		
//		return list;
//	}
//	
//	//---
//
//	private static Stream<Arguments> provideZipAttachmentWithFileAttribute() throws IOException {
//	    return Stream.of(
//	    		Arguments.of(prepareZipWithFileAttribute(), 1000000l, Arrays.asList(1))
//	    );
//	}
//
//	private static List<Allegati> prepareZipWithFileAttribute() throws IOException {
//		List<Allegati> list = new LinkedList<>();
//		
//		Allegati attachment = new Allegati();
//		Path bytes = Paths.get("src","test","resources","Examples", "zip.zip");
//		byte[] data = Files.readAllBytes(bytes);
//		attachment.setFile(data);
//		attachment.setTipoFile("application/zip");
//		list.add(attachment);
//
//		
//		return list;
//	}
//
//	//---
//	
//	private static Stream<Arguments> provideZipWithFilePathAttribute() throws IOException {
//		return Stream.of(
//				Arguments.of(prepareZipWithFilePathAttribute(), 1000000l, Arrays.asList(1))
//		);
//	}
//
//	private static List<Allegati> prepareZipWithFilePathAttribute() throws IOException {
//		List<Allegati> list = new LinkedList<>();
//		
//		Allegati attachment = new Allegati();
//		String path= Paths.get("src","test","resources","Examples", "zip.zip").toFile().getAbsolutePath();;
//		attachment.setPathFile(path);
//		attachment.setTipoFile("application/zip");
//		list.add(attachment);
//		
//		return list;
//	}
//}
