package operazioni;

import java.util.ArrayList;

public class NewUD {
	RegistrazioneDaDare RegistrazioneDaDareObject;
	private String OggettoUD;
	VersioneElettronica VersioneElettronicaObject;
	private String TipoProvenienza;
	DatiEntrata DatiEntrataObject;
	AssegnazioneInterna AssegnazioneInternaObject;


	// Getter Methods 

	public RegistrazioneDaDare getRegistrazioneDaDare() {
		return RegistrazioneDaDareObject;
	}

	public String getOggettoUD() {
		return OggettoUD;
	}

	public VersioneElettronica getVersioneElettronica() {
		return VersioneElettronicaObject;
	}

	public String getTipoProvenienza() {
		return TipoProvenienza;
	}

	public DatiEntrata getDatiEntrata() {
		return DatiEntrataObject;
	}

	public AssegnazioneInterna getAssegnazioneInterna() {
		return AssegnazioneInternaObject;
	}

	// Setter Methods 

	public void setRegistrazioneDaDare(RegistrazioneDaDare RegistrazioneDaDareObject) {
		this.RegistrazioneDaDareObject = RegistrazioneDaDareObject;
	}

	public void setOggettoUD(String OggettoUD) {
		this.OggettoUD = OggettoUD;
	}

	public void setVersioneElettronica(VersioneElettronica VersioneElettronicaObject) {
		this.VersioneElettronicaObject = VersioneElettronicaObject;
	}

	public void setTipoProvenienza(String TipoProvenienza) {
		this.TipoProvenienza = TipoProvenienza;
	}

	public void setDatiEntrata(DatiEntrata DatiEntrataObject) {
		this.DatiEntrataObject = DatiEntrataObject;
	}

	public void setAssegnazioneInterna(AssegnazioneInterna AssegnazioneInternaObject) {
		this.AssegnazioneInternaObject = AssegnazioneInternaObject;
	}
}
