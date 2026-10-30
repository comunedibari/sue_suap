package it.wego.cross.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;

public class HtmlUtilities {
	
	static Map<String, String> escapeMap = new HashMap<>();
	static Map<String, String> unescapeMap = new HashMap<>();
	
	static {
		
		escapeMap.put("&", "&amp;");
		escapeMap.put("<", "&lt;");
		escapeMap.put(">", "&gt;");
		escapeMap.put("\"", "&quot;");
		escapeMap.put("'", "&#039;");
		
		for(String key: escapeMap.keySet())
			unescapeMap.put(key, escapeMap.get(key));
	}
	
	public static String unescape(String input) {
		StringBuilder sb = new StringBuilder("([");
		for(String key: escapeMap.keySet())
			sb.append(key);
		sb.append("])");
		String ret = input;
		Matcher m = Pattern.compile(sb.toString()).matcher(input);
		while(m.find()) {
			ret = ret.replaceAll(m.group(1), escapeMap.get(m.group(1)));
		}
		return ret;
	}
	
	public static String toJson(Object obj) {
		return (new Gson()).toJson(obj);
	}
}
