package cross.cross_scripts.ave_151;

public class Paperwork {
	private String town;
	private int id;
	public String getTown() {
		return town;
	}
	public void setTown(String town) {
		this.town = town;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "Paperwork [town=" + town + ", id=" + id + "]";
	}
	
	
}
