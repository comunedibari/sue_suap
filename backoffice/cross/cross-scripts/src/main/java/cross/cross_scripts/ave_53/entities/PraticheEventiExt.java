package cross.cross_scripts.ave_53.entities;

import java.sql.Timestamp;

import it.wego.cross.entity.PraticheEventi;

public class PraticheEventiExt extends PraticheEventi{

	Timestamp eventDate;

	public Timestamp getEventDate() {
		return eventDate;
	}

	public void setEventDate(Timestamp eventDate) {
		this.eventDate = eventDate;
	}
	
	
}
