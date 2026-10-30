package it.wego.cross.cross_dms.sql;

import java.util.ArrayList;

public class dmsALLEGATI {
    private ArrayList<Allegato> allegato;

	public ArrayList<Allegato> getAllegato() {
		return allegato;
	}

	public void setAllegato(ArrayList<Allegato> allegato) {
		this.allegato = allegato;
	}
}


class Allegato{
	private int id;
    public Object categoria;
    private boolean depositato;
    private String descrizione;
    private String data_arrivo;
    private String nome_file;

    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Object getCategoria() {
		return categoria;
	}
	public void setCategoria(Object categoria) {
		this.categoria = categoria;
	}
	public boolean isDepositato() {
		return depositato;
	}
	public void setDepositato(boolean depositato) {
		this.depositato = depositato;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getData_arrivo() {
		return data_arrivo;
	}
	public void setData_arrivo(String data_arrivo) {
		this.data_arrivo = data_arrivo;
	}
	public String getNome_file() {
		return nome_file;
	}
	public void setNome_file(String nome_file) {
		this.nome_file = nome_file;
	}
}