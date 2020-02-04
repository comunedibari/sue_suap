package it.eng.tz.area.vasta.suap.sue.download.manuali.enumeration;

public enum ManualiSuapSueEnum {
	ME("02 - Modulistica edilizia.pdf"),
	AAME("Allegato_A_modulistica_edilizia.doc"),
	MUPCE("Modulo unificato Permesso di costruire edilizia.pdf");
	
	private String fileName;
	private ManualiSuapSueEnum(String fileName)
	{
		this.fileName = fileName;
	}
	public String getFileName() {
		return fileName;
	}	
}
