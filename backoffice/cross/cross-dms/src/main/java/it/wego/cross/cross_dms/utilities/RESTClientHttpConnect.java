package it.wego.cross.cross_dms.utilities;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class RESTClientHttpConnect {

	public RESTClientHttpConnect() {
		// TODO Auto-generated constructor stub
	}

	public String parse(String jsonLine) {
	    JsonElement jelement = new JsonParser().parse(jsonLine);
	    JsonObject  jobject = jelement.getAsJsonObject();
	    jobject = jobject.getAsJsonObject("data");
	    JsonArray jarray = jobject.getAsJsonArray("translations");
	    jobject = jarray.get(0).getAsJsonObject();
	    String result = jobject.get("translatedText").getAsString();
	    return result;
	}
	
	public String parseArray(String json) {
		return json;
/*	
		JsonParser parser = new JsonParser();
	    String data = json;
		JsonObject jo = (JsonObject) parser.parse(data );
	    JsonElement je = jo.get("some_array");
	
	    //Parsing back the string as Array
	    JsonArray ja = (JsonArray) parser.parse(o.get("some_array").getAsString());
	    for (JsonElement jo : ja) {
	    JsonObject j = (JsonObject) jo;
	        // Your Code, Access json elements as j.get("some_element")
	    }
	    return data;
*/	    
	}		
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		URL url;
		try {
			url = new URL("http://example.com");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
