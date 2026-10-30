package it.wego.cross.beans;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.OffsetDateTime;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import it.wego.cross.cross_dms.sql.Root;
import it.wego.cross.cross_dms.utilities.MyJSONObject;
import it.wego.cross.cross_dms.utilities.ParserJson;

public class DettaglioPratica {

	private String json;
	private String owner;
	private long version;
	private Object size;
	private TypeMetadata typeMetadata;
	private UUID id;
	private OffsetDateTime createdAt;
	private Object updatedAt;
	private String name;
	private long typeID;
	private boolean welcome1Private;

	public String getOwner() { return owner; }
	public void setOwner(String value) { this.owner = value; }

	public long getVersion() { return version; }
	public void setVersion(long value) { this.version = value; }

	public Object getSize() { return size; }
	public void setSize(Object value) { this.size = value; }

	public TypeMetadata getTypeMetadata() { return typeMetadata; }
	public void setTypeMetadata(TypeMetadata value) { this.typeMetadata = value; }

	public UUID getID() { return id; }
	public void setID(UUID value) { this.id = value; }

	public OffsetDateTime getCreatedAt() { return createdAt; }
	public void setCreatedAt(OffsetDateTime value) { this.createdAt = value; }

	public Object getUpdatedAt() { return updatedAt; }
	public void setUpdatedAt(Object value) { this.updatedAt = value; }

	public String getName() { return name; }
	public void setName(String value) { this.name = value; }

	public long getTypeID() { return typeID; }
	public void setTypeID(long value) { this.typeID = value; }

	public boolean getWelcome1Private() { return welcome1Private; }
	public void setWelcome1Private(boolean value) { this.welcome1Private = value; }

	public void parser_json(String json) throws ParseException {
		this.json = json;

		JSONParser parser = new JSONParser();
		
		MyJSONObject myj = new MyJSONObject();
		JSONArray array = (JSONArray) parser.parse(json);
		String value="", key="";
		
		for (int i=0; i < array.size(); i++) {
			JSONObject rootElement = (JSONObject) array.get(i);
			
			try{  value = "";  value =  (String)rootElement.get("id").toString().replaceAll("[{}]","");} catch(Exception e) {}
			this.setID(UUID.fromString(value));

			try{  value = "";  value =  (String)rootElement.get("name").toString().replaceAll("[{}]","");} catch(Exception e) {}
			this.setName(value);

			try{  value = "";  value =  (String)rootElement.get("typeId").toString().replaceAll("[{}]","");} catch(Exception e) {}
			this.setTypeID(Long.parseLong(value));

			JSONObject jTypeMeta = myj.getChildJSONObjectByName((JSONObject) array.get(i), "typeMetadata");

			TypeMetadata typemeta = new TypeMetadata();
			
			if(jTypeMeta.containsKey("REFERENTI")) {

				JSONArray jArrayReferenti = ParserJson.getChildJSONObjectArrayByName(jTypeMeta,  "REFERENTI");

				Referenti[] refarray = new Referenti[jArrayReferenti.size()];
				
				for(int n=0; n<jArrayReferenti.size(); n++ ) { 
					Referenti ref = new Referenti();
					
					JSONObject referenteObj = (JSONObject)jArrayReferenti.get(n); 

					try{  value = "";  value =  (String)referenteObj.get("CODICE FISCALE").toString().replaceAll("[{}]","");} catch(Exception e) {}								
					ref.setCodiceFiscale(value);

					try{  value = "";  value =  (String)referenteObj.get("PARTITA IVA").toString().replaceAll("[{}]","");} catch(Exception e) {}								
					ref.setPartitaIva(value);

					try{  value = "";  value =  (String)referenteObj.get("COGNOME_DENOMINAZIONE").toString().replaceAll("[{}]","");} catch(Exception e) {}								
					ref.setCognomeDenominazione(value);

					try{  value = "";  value =  (String)referenteObj.get("NOME").toString().replaceAll("[{}]","");} catch(Exception e) {}								
					ref.setNome(value);

					try{  value = "";  value =  (String)referenteObj.get("RUOLO").toString().replaceAll("[{}]","");} catch(Exception e) {}								
					ref.setRuolo(value);  

					refarray[n] = ref;
				}
				typemeta.setReferenti(refarray);
			}
			if(jTypeMeta.containsKey("UBICAZIONE")) {
				
				JSONArray jArrayIndirizzoIntervento = myj.getChildJSONObjectArrayByName(jTypeMeta, "UBICAZIONE");								
				
				int sizeArray = jArrayIndirizzoIntervento.size();
				
				Ubicazione[] ubiArray = new Ubicazione[sizeArray];

				for(int x=0; x < sizeArray; x++ ) { 
					Ubicazione ubi = new Ubicazione();
					
					JSONObject jUbicazione = (JSONObject)jArrayIndirizzoIntervento.get(x); 
					
					try{  value = "";  value =  (String)jUbicazione.get("INDIRIZZO DELLA PRATICA").toString().replaceAll("[{}]","");} catch(Exception e) {}
					ubi.setIndirizzoDellaPratica(value);
					
					try{  value = "";  value =  (String)jUbicazione.get("NUMERO CIVICO").toString().replaceAll("[{}]","");} catch(Exception e) {}
					ubi.setNumeroCivico(value);

					try{  value = "";  value =  (String)jUbicazione.get("PIANO").toString().replaceAll("[{}]","");} catch(Exception e) {}
					ubi.setPiano(value);
					
					try{  value = "";  value =  (String)jUbicazione.get("SCALA").toString().replaceAll("[{}]","");} catch(Exception e) {}
					ubi.setScala(value);

					try{  value = "";  value =  (String)jUbicazione.get("INTERNO").toString().replaceAll("[{}]","");} catch(Exception e) {}
					ubi.setInterno(value);

					try{  value = "";  value =  (String)jUbicazione.get("FRAZIONE_LOCALITÀ").toString().replaceAll("[{}]","");} catch(Exception e) {}
					ubi.setFrazioneLocalita(value);

					try{  value = "";  value =  (String)jUbicazione.get("LATITUDINE").toString().replaceAll("[{}]","");} catch(Exception e) {}
					ubi.setLatitudine(value);

					try{  value = "";  value =  (String)jUbicazione.get("LONGITUDINE").toString().replaceAll("[{}]","");} catch(Exception e) {}
					ubi.setLongitudine(value);

					ubiArray[x] = ubi;
				}
				typemeta.setUbicazione(ubiArray);

			}
			
			this.setTypeMetadata(typemeta);
			break;
		}

	}

	public static void main(String[] args) {
		
		DettaglioPratica dtp = new DettaglioPratica();
		
		ObjectMapper om = JsonMapper.builder()
				.enable(JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER)
				.build();


		//Root rootJsonDms = om.readValue(jsonResult, Root.class);
		try {  
			// Open the file
			FileInputStream fstream = new FileInputStream("json/DIA-4001-2002.json");
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			StringBuilder sbr = new StringBuilder();

			String strResponseJsonDMs;

			while ((strResponseJsonDMs = br.readLine()) != null)   {
				System.out.println (strResponseJsonDMs);
				sbr.append(strResponseJsonDMs);
			}

			fstream.close();

			strResponseJsonDMs = sbr.toString();
			
			dtp.parser_json(strResponseJsonDMs);
			//Object obj = parser.parse(strResponseJsonDMs);
			//JSONObject jsonObject = (JSONObject) obj;
			//ParserJson.parseJson(jsonObject);

		} catch (Exception ex) {  
			System.out.println(ex.getMessage());  
		}  
	}
}