package it.wego.cross.entity;

import java.io.Serializable;
import javax.persistence.*;


@Entity
@NamedQueries({
    @NamedQuery(name = "Operazione.findAll", query = "SELECT p FROM Operazione p"),
    @NamedQuery(name = "Operazione.findByOpCode", query = "SELECT p FROM Operazione p WHERE p.codiceOperazione = :codiceOperazione"),
})
public class Operazione implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "codice_operazione")
	private String codiceOperazione;

	@Column(name = "codice")
	private String codice;

	@Lob
	@Column(name = "descrizione")
	private String descrizione;

	public Operazione() {}
	
	public Operazione(String codiceOperazione, String codice, String descrizione) {
		this.codice = codice;
		this.descrizione = descrizione;
		this.codiceOperazione = codiceOperazione;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodiceOperazione() {
		return codiceOperazione;
	}

	public void setCodiceOperazione(String codiceOperazione) {
		this.codiceOperazione = codiceOperazione;
	}
	
	public String getCodice() {
		return codice;
	}
	
	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
}