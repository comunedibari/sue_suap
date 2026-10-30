package it.wego.cross.dto.filters;

import java.util.List;

import it.wego.cross.dto.enums.LogicalOperator;

public class Identifications {

	private LogicalOperator logicalOperator = LogicalOperator.AND;
	private List<Identification> identifications;

	public Identifications(LogicalOperator logicalOperator, List<Identification> identifications) {
		if(logicalOperator != LogicalOperator.NONE)
			this.logicalOperator = logicalOperator;
		this.identifications = identifications;
	}
	
	public LogicalOperator getLogicalOperator() {
		return logicalOperator;
	}
	public void setLogicalOperator(LogicalOperator logicalOperator) {
		this.logicalOperator = logicalOperator;
	}

	public List<Identification> getList() {
		return identifications;
	}

	public void setList(List<Identification> IdentificationList) {
		this.identifications = IdentificationList;
	}

}
