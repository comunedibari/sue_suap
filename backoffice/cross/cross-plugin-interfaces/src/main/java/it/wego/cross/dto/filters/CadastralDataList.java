package it.wego.cross.dto.filters;

import java.util.List;

import it.wego.cross.dto.enums.LogicalOperator;

public class CadastralDataList {

	private LogicalOperator logicalOperator = LogicalOperator.AND;
	private List<CadastralData> cadastralDataList;

	public CadastralDataList(LogicalOperator logicalOperator, List<CadastralData> cadastralDataList) {
		if(logicalOperator != LogicalOperator.NONE)
			this.logicalOperator = logicalOperator;
		this.cadastralDataList = cadastralDataList;
	}
	
	public LogicalOperator getLogicalOperator() {
		return logicalOperator;
	}
	public void setLogicalOperator(LogicalOperator logicalOperator) {
		this.logicalOperator = logicalOperator;
	}

	public List<CadastralData> getList() {
		return cadastralDataList;
	}

	public void setList(List<CadastralData> cadastralDataList) {
		this.cadastralDataList = cadastralDataList;
	}

}
