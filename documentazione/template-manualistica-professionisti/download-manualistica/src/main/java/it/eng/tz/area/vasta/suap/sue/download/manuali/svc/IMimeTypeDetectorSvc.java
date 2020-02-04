package it.eng.tz.area.vasta.suap.sue.download.manuali.svc;
import java.io.File;
import java.io.InputStream;

public interface IMimeTypeDetectorSvc
{
	String getMimeType(String fileName) throws Exception;
	String getMimeType(String fileName, byte[] fileContent) throws Exception;
	String getMimeType(String fileName, InputStream fileContent) throws Exception;
	String getMimeType(String fileName, File file) throws Exception;
}
