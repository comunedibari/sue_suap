package it.wego.cross.utils;

import java.util.HashSet;
import java.util.Set;

public class JspSet {
	private Set<String> set = new HashSet<>();
	
	public String get() {
		return set.toString();
	}
	
	public void add(String... values) {
		set.add(getString(values));
	}
	
	public boolean contains(String... values) {
		return set.contains(getString(values));
	}
	
	public void clean() {
		set.clear();
	}
	
	private String getString(String[] values) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < values.length; i++) {
			if(values[i] != null) {
				sb.append(values[i]);
			}
		}
		
		return sb.toString();
	}
	
	
}
