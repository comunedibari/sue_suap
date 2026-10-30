package it.wego.cross.dao;

public abstract class TestUtils {
	public static synchronized String extractFrom(String text, String from) {
		return text.substring(text.indexOf(from));
	}
	
	public static synchronized String replaceAlias(String text, String with) {
		return text.replaceAll("\\bt\\d+", with);
	}
}
