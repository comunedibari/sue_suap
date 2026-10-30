package it.wego.cross.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import it.wego.cross.entity.Allegati;

public class AllegatiUtils {

	public static final Set<String> ZIP_MIMETYPES = new HashSet<>();
	static{
		ZIP_MIMETYPES.add("application/zip");
		ZIP_MIMETYPES.add("application/x-zip-compressed");}

	public static synchronized List<Allegati> unzipFile(String filePath) throws IOException {

		List<Allegati> ret = new ArrayList<>();
		File zip = new File(filePath);
		File directory = new File(zip.getParent() + File.separator + zip.getName().replace(".","")+System.currentTimeMillis());
		directory.mkdirs();
		
		
		try (FileInputStream fis = new FileInputStream(filePath); 
				ZipInputStream zis = new ZipInputStream(fis)){
			ZipEntry ze = zis.getNextEntry();
			while(ze != null){
				ret.add(map(directory, zis, ze.getName()));
				ze = zis.getNextEntry();
			}
			zis.closeEntry();
		}
		deleteDirectory(directory);
		return ret;
	}

	public static synchronized List<Allegati> unzipFile(byte[] bytes) throws IOException {

		File zip = new File("temporary.zip");

		org.apache.commons.io.FileUtils.writeByteArrayToFile(zip, bytes);

		List<Allegati> ret = new ArrayList<>();
		File directory = new File(zip.getName().replace(".","") + System.currentTimeMillis());
		directory.mkdirs();
		
		try (FileInputStream fis = new FileInputStream(zip.getAbsolutePath()); 
				ZipInputStream zis = new ZipInputStream(fis)){
			ZipEntry ze = zis.getNextEntry();
			while(ze != null){
				ret.add(map(directory, zis, ze.getName()));
				ze = zis.getNextEntry();
			}
			zis.closeEntry();
		}
		deleteDirectory(directory);
		zip.delete();
		return ret;
	}

	public static synchronized List<Allegati> unzipFileRuvo(String filePath) throws IOException {

		List<Allegati> ret = new ArrayList<>();
		File zip = new File(filePath);
		File directory = new File(zip.getParent() + File.separator + zip.getName().replace(".","")+System.currentTimeMillis());
		directory.mkdirs();
		
		
		try (FileInputStream fis = new FileInputStream(filePath); 
				ZipInputStream zis = new ZipInputStream(fis)){
			ZipEntry ze = zis.getNextEntry();
			while(ze != null){
				ret.add(mapRuvo(directory, zis, ze.getName()));
				ze = zis.getNextEntry();
			}
			zis.closeEntry();
		}
		deleteDirectory(directory);
		return ret;
	}

	public static synchronized List<Allegati> unzipFileRuvo(byte[] bytes) throws IOException {

		File zip = new File("temporary.zip");

		org.apache.commons.io.FileUtils.writeByteArrayToFile(zip, bytes);

		List<Allegati> ret = new ArrayList<>();
		File directory = new File(zip.getName().replace(".","") + System.currentTimeMillis());
		directory.mkdirs();
		
		try (FileInputStream fis = new FileInputStream(zip.getAbsolutePath()); 
				ZipInputStream zis = new ZipInputStream(fis)){
			ZipEntry ze = zis.getNextEntry();
			while(ze != null){
				ret.add(mapRuvo(directory, zis, ze.getName()));
				ze = zis.getNextEntry();
			}
			zis.closeEntry();
		}
		deleteDirectory(directory);
		zip.delete();
		return ret;
	}

	private static Allegati map(File dir, ZipInputStream zis, String name) throws FileNotFoundException, IOException {
		byte[] buffer = new byte[1024];
		
		File newFile = new File(dir.getAbsolutePath() + File.separator + name);
		
		try(FileOutputStream fosCurFile = new FileOutputStream(newFile);){
			int len;
			while ((len = zis.read(buffer)) > 0) {
				fosCurFile.write(buffer, 0, len);
			}
		}
		
		byte[] byteArray = new byte[(int)newFile.length()]; 
		try(FileInputStream fisCurFile = new FileInputStream(newFile);){
			fisCurFile.read(byteArray);  
		}
		
		Allegati attachment = new Allegati();
		attachment.setFile(byteArray);
		attachment.setNomeFile(newFile.getName());
		
		Path path = newFile.toPath();
	    String mimeType = Files.probeContentType(path);
		attachment.setTipoFile(mimeType);

		zis.closeEntry();

		return attachment;
		
	}
	private static Allegati mapRuvo(File dir, ZipInputStream zis, String name) throws FileNotFoundException, IOException {
		byte[] buffer = new byte[1024];
		
		File newFile = new File(dir.getAbsolutePath() + File.separator + name);
		
		try(FileOutputStream fosCurFile = new FileOutputStream(newFile);){
			int len;
			while ((len = zis.read(buffer)) > 0) {
				fosCurFile.write(buffer, 0, len);
			}
		}
		
		byte[] byteArray = new byte[(int)newFile.length()]; 
		try(FileInputStream fisCurFile = new FileInputStream(newFile);){
			fisCurFile.read(byteArray);  
		}
		
		Allegati attachment = new Allegati();
		attachment.setFile(byteArray);
		attachment.setNomeFile(newFile.getName());
		
		Path path = newFile.toPath();
	    String mimeType = Files.probeContentType(path);
		attachment.setTipoFile(mimeType);

		zis.closeEntry();

		return attachment;
		
	}
	
	private static boolean  deleteDirectory(File directoryToBeDeleted) {
	    File[] allContents = directoryToBeDeleted.listFiles();
	    if (allContents != null) {
	        for (File file : allContents) {
	            deleteDirectory(file);
	        }
	    }
	    return directoryToBeDeleted.delete();
	}
}
