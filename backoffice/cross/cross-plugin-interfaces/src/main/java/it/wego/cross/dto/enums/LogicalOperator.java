package it.wego.cross.dto.enums;

public enum LogicalOperator {
	NONE(""),
	AND("0"),
	OR("1");

	private String value;
	
	LogicalOperator(String value){
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public static LogicalOperator fromString(String text) {
		for (LogicalOperator b : LogicalOperator.values()) {
			if (b.value.equalsIgnoreCase(text)) {
				return b;
			}
		}
		return NONE;
	}
}
