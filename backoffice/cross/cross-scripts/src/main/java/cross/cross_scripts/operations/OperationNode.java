package cross.cross_scripts.operations;

class OperationNode{
	private String code;
	private String opCode;
	private String des;
	
	
	public OperationNode(String opCode, String code, String des) {
		super();
		this.opCode = opCode;
		this.des = des;
		this.code = code;  
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getOpCode() {
		return opCode;
	}
	
	public void setOpCode(String opCode) {
		this.opCode = opCode;
	}

	public String getDes() {
		return des;
	}
	
	public void setDes(String des) {
		this.des = des;
	}

	@Override
	public String toString() {
		return "OperationNode [code=" + code + ", opCode=" + opCode + ", des=" + des + "]";
	}
}