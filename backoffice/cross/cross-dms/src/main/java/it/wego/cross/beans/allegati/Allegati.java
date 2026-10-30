package it.wego.cross.beans.allegati;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.time.OffsetDateTime;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import it.wego.cross.beans.DettaglioPratica;
import it.wego.cross.beans.Referenti;
import it.wego.cross.beans.TypeMetadata;
import it.wego.cross.cross_dms.utilities.MyJSONObject;
import it.wego.cross.cross_dms.utilities.ParserJson;

public class Allegati {
    private String owner;
    private long typeID;
    private long version;
    private UUID[] idFolders;
    private Welcome1TypeMetadata typeMetadata;
    private UUID id;
    private OffsetDateTime createdAt;
    private Object updatedAt;
    private String title;
    private boolean welcome1Private;
    private UploadInfo[] uploadInfo;

    public String getOwner() { return owner; }
    public void setOwner(String value) { this.owner = value; }

    public long getTypeID() { return typeID; }
    public void setTypeID(long value) { this.typeID = value; }

    public long getVersion() { return version; }
    public void setVersion(long value) { this.version = value; }

    public UUID[] getIDFolders() { return idFolders; }
    public void setIDFolders(UUID[] value) { this.idFolders = value; }

    public Welcome1TypeMetadata getTypeMetadata() { return typeMetadata; }
    public void setTypeMetadata(Welcome1TypeMetadata value) { this.typeMetadata = value; }

    public UUID getID() { return id; }
    public void setID(UUID value) { this.id = value; }

    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime value) { this.createdAt = value; }

    public Object getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Object value) { this.updatedAt = value; }

    public String getTitle() { return title; }
    public void setTitle(String value) { this.title = value; }

    public boolean getWelcome1Private() { return welcome1Private; }
    public void setWelcome1Private(boolean value) { this.welcome1Private = value; }

    public UploadInfo[] getUploadInfo() { return uploadInfo; }
    public void setUploadInfo(UploadInfo[] value) { this.uploadInfo = value; }
    
    public void parser_json(String json) throws ParseException {
		JSONParser parser = new JSONParser();
		
		MyJSONObject myj = new MyJSONObject();
		JSONArray array = (JSONArray) parser.parse(json);
		String value="", key="";
		
		UploadInfo[] refupload = new UploadInfo[array.size()];

		for (int i=0; i < array.size(); i++) {
			JSONObject rootElement = (JSONObject) array.get(i);

			if(rootElement.containsKey("uploadInfo")) {
				UploadInfo uploadinfo = new UploadInfo();
				
				JSONObject jUploadInfo = myj.getChildJSONObjectByName(rootElement, "uploadInfo");

				try{  value = "";  value =  (String)jUploadInfo.get("uuid").toString().replaceAll("[{}]","");} catch(Exception e) {}
				uploadinfo.setUUID(UUID.fromString(value));

				try{  value = "";  value =  (String)jUploadInfo.get("name").toString().replaceAll("[{}]","");} catch(Exception e) {}
				uploadinfo.setName(value);

				try{  value = "";  value =  (String)jUploadInfo.get("type").toString().replaceAll("[{}]","");} catch(Exception e) {}
				uploadinfo.setType(value);

				refupload[i] = uploadinfo;
			}
		}
		this.setUploadInfo(refupload);
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
