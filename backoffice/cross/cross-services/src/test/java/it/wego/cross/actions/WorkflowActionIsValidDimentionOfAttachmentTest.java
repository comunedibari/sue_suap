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
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.Arguments;
//import org.junit.jupiter.params.provider.MethodSource;
//import org.mockito.invocation.InvocationOnMock;
//import org.mockito.stubbing.Answer;
//
//import it.wego.cross.dto.ErroreDTO;
//import it.wego.cross.entity.Allegati;
//import it.wego.cross.entity.Configuration;
//import it.wego.cross.entity.PraticheEventiAllegati;
//import it.wego.cross.exception.WorkflowException;
//import it.wego.cross.service.ConfigurationService;
//
//
//class WorkflowActionisValidDimentionOfAttachmentsTest {
//
//	WorkflowAction  wa;
//	ConfigurationService configuration;
//	@BeforeEach
//	public void init() {
//		ErroriAction erroriAction = mock(ErroriAction.class);
//		configuration = mock(ConfigurationService.class);
//		wa = new WorkflowAction(erroriAction, configuration);
//		when(erroriAction.getError(anyString(), anyString(), anyInt())).thenReturn(new ErroreDTO());
//	}
//
//	@ParameterizedTest
//	@MethodSource("provideAttachmentsFilePathAttribute")
//	void testAttachmentsWithFilePath(List<PraticheEventiAllegati> attachments, Long inputMaxSize, boolean expectedResult) throws WorkflowException {
//
//		boolean ret = wa.isValidDimentionOfAttachments(attachments, inputMaxSize);
//		assertThat(ret, Is.is(expectedResult));
//	}
//
//	@ParameterizedTest
//	@MethodSource("provideAttachmentsWithFileAttribute")
//	void testAttachmentsWithFileAttribute(List<PraticheEventiAllegati> attachments, Long inputMaxSize, boolean expectedResult) throws WorkflowException {
//		
//		boolean ret = wa.isValidDimentionOfAttachments(attachments, inputMaxSize);
//		assertThat(ret, Is.is(expectedResult));
//	}
//	
//	@ParameterizedTest
//	@MethodSource("provideAttachmentsWithDifferentFileTypes")
//	void testAttachmentsWithDifferentFileTypes(List<PraticheEventiAllegati> attachments, Long inputMaxSize, boolean expectedResult) throws WorkflowException {
//		boolean ret = wa.isValidDimentionOfAttachments(attachments, inputMaxSize);
//		assertThat(ret, Is.is(expectedResult));
//	}
//
//	@ParameterizedTest
//	@MethodSource("provideZipAttachmentWithFileAttribute")
//	void testZipAttachmentWithFileAttribute(List<PraticheEventiAllegati> attachments, Long inputMaxSize, boolean expectedResult) throws WorkflowException {
//		boolean ret = wa.isValidDimentionOfAttachments(attachments, inputMaxSize);
//		assertThat(ret, Is.is(expectedResult));
//	}
//
//	
//	/*****************************
//	 * data methods
//	 * **************************/
//	private static Stream<Arguments> provideAttachmentsFilePathAttribute() {
//	    return Stream.of(
//	      Arguments.of(prepareAttachmentsFilePathAttribute(1), 30000l, true),
//	      Arguments.of(prepareAttachmentsFilePathAttribute(3), 60000l, false)
//	    );
//	}
//	
//	private static List<PraticheEventiAllegati> prepareAttachmentsFilePathAttribute(int dim) {
//	
//		List<PraticheEventiAllegati>  list = new LinkedList<>();
//		for(int i = 0; i < dim; i++) {
//			PraticheEventiAllegati pea = new PraticheEventiAllegati();
//			Allegati attachment = new Allegati();
//			pea.setAllegati(attachment);
//			String path = Paths.get("src","test","resources","Examples", "Prova"+(i+1)+".pdf").toFile().getAbsolutePath();
//			attachment.setPathFile(path);
//			attachment.setNomeFile("Prova"+(i+1)+".pdf");
//			list.add(pea);
//		}
//		return list;
//	}
//	
//	//---
//
//	private static Stream<Arguments> provideAttachmentsWithFileAttribute() throws IOException {
//	    return Stream.of(
//	      Arguments.of(prepareAttachmentsWithFileAttribute(1), 30000l, true),
//	      Arguments.of(prepareAttachmentsWithFileAttribute(3), 70000l, false)
//	    );
//	}
//	
//	private static List<PraticheEventiAllegati> prepareAttachmentsWithFileAttribute(int dim) throws IOException {
//	
//		List<PraticheEventiAllegati> list = new LinkedList<>();
//		for(int i = 1; i <= dim; i++) {
//			PraticheEventiAllegati pea = new PraticheEventiAllegati();
//			Allegati attachment = new Allegati();
//			pea.setAllegati(attachment);
//			Path path = Paths.get("src","test","resources","Examples", "Prova"+i+".pdf");
//
//			byte[] data = Files.readAllBytes(path);
//			attachment.setFile(data);
//			list.add(pea);
//		}
//		
//		return list;
//	}
//	
//	//---
//	
//	private static Stream<Arguments> provideAttachmentsWithDifferentFileTypes() throws IOException {
//	    return Stream.of(
//	      Arguments.of(prepareAttachmentsWithDifferentFileTypes(), 1000000l, true),
//	      Arguments.of(prepareAttachmentsWithDifferentFileTypes(), 100000l, false)
//	    );
//	}
//	
//	private static List<PraticheEventiAllegati> prepareAttachmentsWithDifferentFileTypes() throws IOException {
//		List<PraticheEventiAllegati> list = new LinkedList<>();
//		
//		PraticheEventiAllegati pea = new PraticheEventiAllegati();
//		Allegati attachment = new Allegati();
//		pea.setAllegati(attachment);
//		Path bytes = Paths.get("src","test","resources","Examples", "esempio.doc");
//		byte[] data = Files.readAllBytes(bytes);
//		attachment.setFile(data);
//		attachment.setNomeFile("esempio.doc");
//		list.add(pea);
//		
//		pea = new PraticheEventiAllegati();
//		attachment = new Allegati();
//		pea.setAllegati(attachment);
//		bytes = Paths.get("src","test","resources","Examples", "esempio.docx.pdf");
//		data = Files.readAllBytes(bytes);
//		attachment.setFile(data);
//		attachment.setNomeFile("esempio.docx.pdf");
//		list.add(pea);
//		
//		pea = new PraticheEventiAllegati();
//		attachment = new Allegati();
//		pea.setAllegati(attachment);
//		bytes = Paths.get("src","test","resources","Examples", "esempio.pdf.p7m");
//		data = Files.readAllBytes(bytes);
//		attachment.setFile(data);
//		attachment.setNomeFile("esempio.pdf.p7m");
//		list.add(pea);
//
//		pea = new PraticheEventiAllegati();
//		attachment = new Allegati();
//		pea.setAllegati(attachment);
//		String path = Paths.get("src","test","resources","Examples", "Prova1.pdf").toFile().getAbsolutePath();
//		attachment.setPathFile(path);
//		attachment.setNomeFile("Prova1.pdf");
//		list.add(pea);
//		
//		return list;
//	}
//
//	//---
//
//	private static Stream<Arguments> provideZipAttachmentWithFileAttribute() throws IOException {
//	    return Stream.of(
//	    		Arguments.of(prepareZipWithFileAttribute(), 1000000l, true)
//	    );
//	}
//
//	private static List<PraticheEventiAllegati> prepareZipWithFileAttribute() throws IOException {
//		List<PraticheEventiAllegati> list = new LinkedList<>();
//		
//		PraticheEventiAllegati pea = new PraticheEventiAllegati();
//		Allegati attachment = new Allegati();
//		pea.setAllegati(attachment);
//		Path bytes = Paths.get("src","test","resources","Examples", "zip.zip");
//		byte[] data = Files.readAllBytes(bytes);
//		attachment.setFile(data);
//		attachment.setTipoFile("application/zip");
//		list.add(pea);
//
//		
//		return list;
//	}
//
//}
