package cross.cross_scripts.ave_151;

import java.util.Date;

public class PaperworkEvent {
	private int paperworkEventId;
	private int paperworkId;
	private Date date;
	public int getPaperworkEventId() {
		return paperworkEventId;
	}
	public void setPaperworkEventId(int paperworkEventId) {
		this.paperworkEventId = paperworkEventId;
	}
	public int getPaperworkId() {
		return paperworkId;
	}
	public void setPaperworkId(int paperworkId) {
		this.paperworkId = paperworkId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "PaperworkEvent [paperworkEventId=" + paperworkEventId + ", paperworkId=" + paperworkId + ", date="
				+ date + "]";
	}
	
	
}
