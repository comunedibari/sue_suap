package it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RiepilogoOneriPagati implements Serializable {

	public RiepilogoOneriPagati() {
		setOneri(new ArrayList());
	}

	public final double getTotale() {
		return totale;
	}

	public final void setTotale(double totale) {
		this.totale = totale;
	}

	public final List getOneri() {
		return oneri;
	}

	public final void setOneri(List oneri) {
		this.oneri = oneri;
	}

	public final void addOneri(OnereBean onere) {
		getOneri().add(onere);
	}

	private static final long serialVersionUID = 0x2a6a609eL;
	private double totale;
	private List oneri;
}
