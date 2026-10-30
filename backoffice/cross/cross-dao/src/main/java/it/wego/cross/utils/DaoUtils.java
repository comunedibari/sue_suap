package it.wego.cross.utils;

public abstract class DaoUtils {
	public static synchronized String setWildStringParameter(String string) {
		return string.trim().replaceAll("\\s+", "%") + "%";
	}

}
