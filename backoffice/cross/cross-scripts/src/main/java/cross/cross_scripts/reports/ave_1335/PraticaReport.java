package cross.cross_scripts.reports.ave_1335;

import java.util.ArrayList;
import java.util.List;

public class PraticaReport {
	int idPratica; 
	int numero; 
	int anno ;
	String xml ;
	String tipoPratica; 
	String descrizioneIntervento;
	
	List<String> indirizzi = new ArrayList<>() ;
	List<String> piani = new ArrayList<>() ;
	
	public int getIdPratica() {
		return idPratica;
	}
	public void setIdPratica(int idPratica) {
		this.idPratica = idPratica;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public int getAnno() {
		return anno;
	}
	public void setAnno(int anno) {
		this.anno = anno;
	}
	public String getXml() {
		return xml;
	}
	public void setXml(String xml) {
		this.xml = xml;
	}
	public String getTipoPratica() {
		return tipoPratica;
	}
	public void setTipoPratica(String tipoPratica) {
		this.tipoPratica = tipoPratica;
	}
	public void addIndirizzo(String indirizzo) {
		this.indirizzi.add(indirizzo);
	}
	
	public List<String> getIndirizzi(){
		return this.indirizzi;
	}
	
	
	public String getDescrizioneIntervento() {
		return descrizioneIntervento;
	}
	public void setDescrizioneIntervento(String descrizioneIntervento) {
		this.descrizioneIntervento = descrizioneIntervento;
	}
	public void addPiano(String piano) {
		this.piani.add(piano);
	}
	
	public List<String> getPiani(){
		return this.piani;
	}
}
