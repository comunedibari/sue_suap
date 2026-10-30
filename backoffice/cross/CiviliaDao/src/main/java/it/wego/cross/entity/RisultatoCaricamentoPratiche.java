package it.wego.cross.entity;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.Date;


/**
 * The persistent class for the risultato_caricamento_pratiche database table.
 * 
 */
@Entity
@Table(name="risultato_caricamento_pratiche")
@XmlRootElement
@NamedQueries({
@NamedQuery(name="RisultatoCaricamentoPratiche.findAll", query="SELECT r FROM RisultatoCaricamentoPratiche r"),
@NamedQuery(name="RisultatoCaricamentoPratiche.findByIdRisultatoCaricamento", query="SELECT r FROM RisultatoCaricamentoPratiche r where r.idRisultatoCaricamento = :idRisultatoCaricamento"),
@NamedQuery(name="RisultatoCaricamentoPratiche.findByIdentificativoPratiche", query="SELECT r FROM RisultatoCaricamentoPratiche r where r.identificativoPratica = :identificativoPratica")})
public class RisultatoCaricamentoPratiche implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
	@Column(name="id_risultato_caricamento")
	private int idRisultatoCaricamento;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_caricamento")
	private Date dataCaricamento;

	@Column(name="descrizione_errore")
	private String descrizioneErrore;

	@Column(name="esito_caricamento")
	private String esitoCaricamento;

	@JoinColumn(name = "id_utente", referencedColumnName = "id_utente")
    @ManyToOne
    private Utente idUtente;
    @JoinColumn(name = "id_pratica", referencedColumnName = "id_pratica")
    @ManyToOne
    private Pratica idPratica;
    @Column(name="nome_file_caricato")
    private String nomeFileCaricato;
    
	@Column(name="identificativo_pratica")
	private String identificativoPratica;

	public RisultatoCaricamentoPratiche() {
	}

	public int getIdRisultatoCaricamento() {
		return this.idRisultatoCaricamento;
	}

	public void setIdRisultatoCaricamento(int idRisultatoCaricamento) {
		this.idRisultatoCaricamento = idRisultatoCaricamento;
	}

	public Date getDataCaricamento() {
		return this.dataCaricamento;
	}

	public void setDataCaricamento(Date dataCaricamento) {
		this.dataCaricamento = dataCaricamento;
	}

	public String getDescrizioneErrore() {
		return this.descrizioneErrore;
	}

	public void setDescrizioneErrore(String descrizioneErrore) {
		this.descrizioneErrore = descrizioneErrore;
	}

	public String getEsitoCaricamento() {
		return this.esitoCaricamento;
	}

	public void setEsitoCaricamento(String esitoCaricamento) {
		this.esitoCaricamento = esitoCaricamento;
	}

	
	public String getIdentificativoPratica() {
		return this.identificativoPratica;
	}

	public void setIdentificativoPratica(String identificativoPratica) {
		this.identificativoPratica = identificativoPratica;
	}
	public Utente getIdUtente() {
	        return idUtente;
	}

	public void setIdUtente(Utente idUtente) {
	        this.idUtente = idUtente;
	}

	public Pratica getIdPratica() {
	        return idPratica;
	}

	public void setIdPratica(Pratica idPratica) {
	        this.idPratica = idPratica;
	}
	public String getNomeFileCaricato() {
		return this.nomeFileCaricato;
	}

	public void setNomeFileCaricato(String nomeFileCaricato) {
		this.nomeFileCaricato = nomeFileCaricato;
	}



}