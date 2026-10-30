package it.wego.cross.beans.pratica;

import it.wego.cross.beans.AzioneUtente;
import it.wego.cross.beans.Oggetto;
import it.wego.cross.beans.Referenti;
import it.wego.cross.beans.TypeMetadata;
import it.wego.cross.beans.Ubicazione;
import it.wego.cross.cross_beans.AZIONEUTENTE;
import it.wego.cross.cross_dms.sql.Dao;
import it.wego.cross.cross_dms.sql.PoolConnectionDB;
import it.wego.cross.cross_dms.sql.Query;
import it.wego.cross.cross_dms.utilities.myUtility;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper; 
import com.fasterxml.jackson.databind.ObjectWriter; 

public class AddPraticaDms {
	@JsonProperty("owner")
	private String owner;
	
	@JsonProperty("public")
	private boolean _public;
	
	@JsonProperty("name")
	private String name;
	
	private TypeMetadata typeMetadata;
	
	@JsonProperty("typeId")
	private long typeID;

	@JsonProperty("id")
	private String id;
	
	@JsonProperty("versione")
	private long version;

	public AddPraticaDms() {
		super();
		this.owner = "";
		this._public = false;
		this.name = "";
		this.typeID = 0;
		this.id = "";
		this.version = 0;
	}
	public String getOwner() { return owner; }
	public void setOwner(String value) { this.owner = value; }

	public boolean get_public() { return _public; }
	public void set_public(boolean value) { this._public = value; }

	public String getName() { return name; }
	public void setName(String value) { this.name = value; }

	public TypeMetadata getTypeMetadata() { return typeMetadata; }
	public void setTypeMetadata(TypeMetadata value) { this.typeMetadata = value; }

	public long getTypeID() { return typeID; }
	public void setTypeID(long value) { this.typeID = value; }

	public String getID() { return id; }
	public void setID(String value) { this.id = value; }

	public long getVersion() { return version; }
	public void setVersion(long value) { this.version = value; }
	
	public void estrae_dati_pratica(String identificativo_pratica) throws Exception {
		String query = "";
		Dao dao = new Dao();dao.openConnection(PoolConnectionDB.DB_CROSS);
		myUtility utl = new myUtility();
		
		// DATI PRATICA
		this.setOwner("admin");
		this.set_public(false);
		this.setName(identificativo_pratica);
		this.setTypeID(1480);
		this.setID("_new");
		this.setVersion(0);
		
		java.sql.Connection connection = dao.getConnection();
		query = Query.addpratica_dati_pratica;
	    query = myUtility.setString(query,  "?", String.valueOf(identificativo_pratica));
		PreparedStatement ps = connection.prepareStatement(query);

	    ResultSet rs = ps.executeQuery();
		
		TypeMetadata metadati = new TypeMetadata();
		Object obj = null;
//		metadati.setTornata(myUtility.getLocalTime2()+"Z");
		metadati.setSettore("PRATICHE EDILIZIE");
		int id_pratica = 0;
		while (rs.next()) {
			
			metadati.setID((String)Integer.toString(rs.getInt("id_pratica")).toString());
			String sAnno = rs.getString("anno");
			if(sAnno != null) {
				long lAnno = (long)Integer.valueOf(rs.getString("anno"));
				metadati.setAnno(lAnno);
			}			

			String sId_tipo_pratica = rs.getString("id_tipo_pratica");
			
			if(sId_tipo_pratica != null) {
				String query_tipo_pratica = "SELECT DISTINCT "+
				"`tipo_pratica`, `descrizione` FROM `opencross`.`lk_tipo_pratica` WHERE id_tipo_pratica = ?"; 
				query_tipo_pratica = myUtility.setString(query_tipo_pratica,  "?", String.valueOf(sId_tipo_pratica));
				String tipo_pratica = dao.queryString(query_tipo_pratica, "tipo_pratica");
				metadati.setTipoPratica((String)tipo_pratica.toString());
			}			
			
			id_pratica = rs.getInt("id_pratica");
			
			metadati.setNumero((String)Integer.toString(id_pratica).toString());
			
			Oggetto[] oggetto = new Oggetto[1];
			Oggetto oggetto_pratica = new Oggetto();
			oggetto_pratica.setDescrizioneIntervento(rs.getString("oggetto_pratica") != null? (String)rs.getString("oggetto_pratica").toString() : "");			
			oggetto_pratica.setDataDiPresentazione(myUtility.getLocalTimeZone().toString());
			oggetto[0] = oggetto_pratica;
			metadati.setOggetto(oggetto);
			
			AzioneUtente[] array_azione_utente = new AzioneUtente[1];
			AzioneUtente azione_utente = new AzioneUtente(); 
			azione_utente.setAzione("caricato");
			
			//LocalDateTime ldate = LocalDateTime.parse(myUtility.getLocalTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"));
			
			String sDataAzione = myUtility.getLocalTime2()+"Z";
//			myUtility.getOffsetLocalTimeZone(offsetTime);
			azione_utente.setDataAzione(sDataAzione);
			array_azione_utente[0] = azione_utente;
			
			metadati.setAzioneUtente(array_azione_utente);
			break;
		}
		ps.close();

		//REFERENTI PRATICA
		query = Query.addpratica_referenti_pratica;
	    query = myUtility.setString(query,  "?", String.valueOf(identificativo_pratica));
	    
	    int rowsCount = dao.getNumberRows(query);
	    Referenti[] array_referenti = new Referenti[rowsCount];

	    ps = connection.prepareStatement(query);		
	    rs = ps.executeQuery();
	    int refcount = 0;
		while (rs.next()) {
			Referenti referenti = new Referenti();
			
			String tipo_anagrafica_cross = (String)rs.getString("tipo_anagrafica").toString();
			if(tipo_anagrafica_cross != null) {
				if(tipo_anagrafica_cross.equals("G")) {
					referenti.setCognomeDenominazione(rs.getString("denominazione") != null? (String)rs.getString("denominazione").toString() : "");
					referenti.setPartitaIva(rs.getString("partita_iva") != null? (String)rs.getString("partita_iva").toString() : "");
					referenti.setCodiceFiscale("");
				} else {
					referenti.setCognomeDenominazione(rs.getString("cognome") != null? (String)rs.getString("cognome").toString() : "");
					referenti.setCodiceFiscale(rs.getString("codice_fiscale") != null? (String)rs.getString("codice_fiscale").toString() : "");
					referenti.setPartitaIva("");
				}
			}
			
			referenti.setNome(rs.getString("nome") != null? (String)rs.getString("nome").toString() : "");
			referenti.setRuolo(rs.getString("ruolo") != null? (String)rs.getString("ruolo").toString() : "");
			array_referenti[refcount] = referenti; 
			
			refcount = refcount +1;
		}
		metadati.setReferenti(array_referenti);
				
		ps.close();
						
		
		//INDIRIZZO_INTERVENTO
		
		query = Query.addpratica_indirizzi_intervento;
	    query = myUtility.setString(query,  "?", String.valueOf(id_pratica));
	    
	    rowsCount = dao.getNumberRows(query);
	    Ubicazione[] array_ind_intervento = new Ubicazione[rowsCount];

	    ps = connection.prepareStatement(query);		
	    rs = ps.executeQuery();
	    int ind_intervento_count = 0;
		while (rs.next()) {
			Ubicazione ubicazione = new Ubicazione();
			
			ubicazione.setIndirizzoDellaPratica(rs.getString("indirizzo") != null? (String)rs.getString("indirizzo").toString() : "");
			ubicazione.setNumeroCivico(rs.getString("civico") != null? (String)rs.getString("civico").toString() : "");

			ubicazione.setScala(rs.getString("interno_scala") != null? (String)rs.getString("interno_scala").toString() : "");
			ubicazione.setPiano(rs.getString("piano") != null? (String)rs.getString("piano").toString() : "");
			ubicazione.setFrazioneLocalita(rs.getString("localita") != null? (String)rs.getString("localita").toString() : "");
			ubicazione.setLongitudine(rs.getString("longitudine") != null? (String)rs.getString("longitudine").toString() : "");
			ubicazione.setLatitudine(rs.getString("latitudine") != null? (String)rs.getString("latitudine").toString() : "");

			array_ind_intervento[ind_intervento_count] = ubicazione; 
			ind_intervento_count = ind_intervento_count +1;
		}
				
		ps.close();
		
		metadati.setUbicazione(array_ind_intervento);
		
		this.setTypeMetadata(metadati);
		
		dao.closeConnection();
	}
	public String toStringJson()
	{
		String json = "";
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		try {
			json = ow.writeValueAsString(this);			
			json = json.replaceAll("null,", "\"\",");
			json = json.replaceAll("null", "");
			
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
	
	public static void main(String[] args) {
		String ID = "";
		int idPratica = 6356546;
		ID= (String)Integer.toString(idPratica);

		
		AddPraticaDms add_pratica = new AddPraticaDms();
		
		try {
			add_pratica.estrae_dati_pratica("CRTNCL71M07A662O-060582-5781815");
			String json_add_pratica = add_pratica.toStringJson();
			System.out.println(json_add_pratica);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

