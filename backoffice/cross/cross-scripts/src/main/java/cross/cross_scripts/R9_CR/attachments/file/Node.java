package cross.cross_scripts.R9_CR.attachments.file;

public class Node {
	private Integer id;
	private String path;
	private Integer dimension;
	
	
	public Node(String path, Integer dimension) {
		this.path = path;
		this.dimension = dimension;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Integer getDimension() {
		return dimension;
	}
	public void setDimension(Integer dimension) {
		this.dimension = dimension;
	}
	
	
}
