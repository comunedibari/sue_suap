package it.wego.cross.dto.filters;

import java.util.List;

import it.wego.cross.dto.enums.LogicalOperator;

public class CadastralAddresses {

	private LogicalOperator logicalOperator = LogicalOperator.AND;
	private List<CadastralAddress> addresses;

	public CadastralAddresses(LogicalOperator logicalOperator, List<CadastralAddress> addresses) {
		if(logicalOperator != LogicalOperator.NONE)
			this.logicalOperator = logicalOperator;
		this.addresses = addresses;
	}
	
	public LogicalOperator getLogicalOperator() {
		return logicalOperator;
	}
	public void setLogicalOperator(LogicalOperator logicalOperator) {
		this.logicalOperator = logicalOperator;
	}
	public List<CadastralAddress> getAddresses() {
		return addresses;
	}
	public void setAddresses(List<CadastralAddress> addresses) {
		this.addresses = addresses;
	}
	
	
}
