package CiviliaDao.utilities;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

// JSON2PDF
//import com.itextpdf.text.pdf.hyphenation.TernaryTree.Iterator;
//import com.lowagie.text.DocumentException;
//import org.xhtmlrenderer.pdf.ITextRenderer;


import java.io.*;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ParserJson {

	// JsonElement rootNode = parser.parse(json);

	private JsonParser parser = new JsonParser();

	private JsonElement rootNode; 	

	private String value;

	public ParserJson(String json)
	{		
		rootNode = new JsonParser().parse(json);

	}

	/**
	 * @return the rootNode
	 */
	public JsonElement getRootNode() {
		return rootNode;
	}

	/**
	 * @param rootNode the rootNode to set
	 */
	public void setRootNode(JsonElement rootNode) {
		this.rootNode = rootNode;
	}


	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}


	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}


	public void readgson(String file) {
		try {
			System.out.println( "Reading JSON file from Java program" );


			FileReader fileReader = new FileReader( file );
			com.google.gson.JsonObject object = (JsonObject) parser.parse( fileReader );

			Set <java.util.Map.Entry<String, com.google.gson.JsonElement>> keys = object.entrySet();
			if ( keys.isEmpty() ) {
				System.out.println( "Empty JSON Object" );
			}else {
				Map<String, Object> map = json_UnKnown_Format( keys );
				System.out.println("Json 2 Map : " + map);
			}

		} catch (IOException ex) {
			System.out.println("Input File Does not Exists.");
		}
	}

	public Map<String, Object> json_UnKnown_Format( Set <java.util.Map.Entry<String, com.google.gson.JsonElement>> keys ){

		Map<String, Object> jsonMap = new HashMap<String, Object>();

		for (Entry<String, JsonElement> entry : keys) {
			String keyEntry = entry.getKey();

			System.out.println(keyEntry + " : ");

			JsonElement valuesEntry =  entry.getValue();

			if (valuesEntry.isJsonNull()) {
				System.out.println(valuesEntry);
				jsonMap.put(keyEntry, valuesEntry);
			}else if (valuesEntry.isJsonPrimitive()) {
				System.out.println("P - "+valuesEntry);
				jsonMap.put(keyEntry, valuesEntry);
			}else if (valuesEntry.isJsonArray()) {
				JsonArray array = valuesEntry.getAsJsonArray();
				List<Object> array2List = new ArrayList<Object>();
				for (JsonElement jsonElements : array) {
					System.out.println("A - "+jsonElements);
					array2List.add(jsonElements);
				}
				jsonMap.put(keyEntry, array2List);
			}else if (valuesEntry.isJsonObject()) {             
				com.google.gson.JsonObject obj = (JsonObject) parser.parse(valuesEntry.toString());                    
				Set <java.util.Map.Entry<String, com.google.gson.JsonElement>> obj_key = obj.entrySet();
				jsonMap.put(keyEntry, json_UnKnown_Format(obj_key));
			}
		}
		return jsonMap;
	}

	public static String getValueBykeyToString(JSONObject jsonObject, String key) {
		String  svalue = "";
		boolean bvalue=false;
		int ivalue = 0;
		long lvalue = 0;

		String value_type = ParserJson.getTypeOfKeyObject(jsonObject, key);		
		if(value_type.equalsIgnoreCase("long")) {lvalue = (long)jsonObject.get(key); svalue = Long.toString(lvalue);}
		if(value_type.equalsIgnoreCase("integer")) {ivalue = (int)jsonObject.get(key); svalue = Integer.valueOf(ivalue).toString();}
		if(value_type.equalsIgnoreCase("boolean")) {bvalue = (boolean)jsonObject.get(key); if(bvalue == false) svalue="false";else svalue="true";}
		if(value_type.equalsIgnoreCase("string")) {svalue = (String)jsonObject.get(key).toString().replaceAll("[{}]","");}
		if(value_type.equalsIgnoreCase("null")) {svalue="";}
		
		return svalue;
	}

	public static int getKeyValueInt(JsonObject JsonObject, String key) {
		int result=0;
		JsonElement el = JsonObject.get(key);
		if (el != null && !el.isJsonNull()){
			result = el.getAsInt();
		}
		return result; 
	}

	public static String getKeyValueString(JsonObject JsonObject, String key) {
		String result = "";
		JsonElement el = JsonObject.get(key);
		if (el != null && !el.isJsonNull()){
			result = el.getAsString();
		}
		return result; 
	}

	public static boolean getTypeIntegerOfObject(JSONObject json, String keyValue) {
		boolean tipodato = false;

		Object v = json.get(keyValue);
		if (v instanceof Integer) {
			return true;
		} 
		return tipodato;
	}
	

	public static String getTypeOfKeyObject(JSONObject json, String keyValue) {
		String tipodato = "";

		Object v = json.get(keyValue);
		if (v instanceof Integer) {
			tipodato = "integer";
		} else if (v instanceof Boolean) {
			tipodato = "boolean";
		} else if (v instanceof Float || v instanceof Double) {
			tipodato = "double";
		} else if (v instanceof String) {
			tipodato = "string";
		} else if (v instanceof Long) {
			tipodato = "long";
		} else {
			tipodato = "null";
		}
		return tipodato;
	}

	public static void iterateOfKeyObject(JSONObject json, String keyValue) {

		for (Iterator i = json.keySet().iterator(); i.hasNext ();) {
			String key = (String) i.next ();
			Object val = json.get(key);
			System.out.println (val);
		}
	}	


	public static boolean checkKeyChildJSONObjectByName(JSONObject jsonObject, String childName) {
		try {
			JSONObject extnJsonObj = (JSONObject) jsonObject.get(childName);
			if (extnJsonObj == null) return false;
		} catch(Exception e) {
			return false;
		}
		return true;
	}
	
	public static boolean checkChildJSONObjectByName(JSONObject jsonObject, String childName) {
		JSONObject extnJsonObj = (JSONObject) jsonObject.get(childName);
		if (extnJsonObj == null)
			return false;
		
		return true;
	}
	
	public static JSONObject getChildJSONObjectByName(JSONObject jsonObject, String childName) {
		JSONObject extnJsonObj = (JSONObject) jsonObject.get(childName);

		if (extnJsonObj == null)
			extnJsonObj = new JSONObject();
		return extnJsonObj;
	}
	public static JSONArray getChildJSONObjectArrayByName_old(JSONObject jsonObject, String childName) {
		JSONArray jsonObjArray = (JSONArray) jsonObject.get(childName);

		if (jsonObjArray == null)
			jsonObjArray = new JSONArray();
		return jsonObjArray;
	}

	public static JSONArray getChildJSONObjectArrayByName(JSONObject jsonObject, String childName) {
		JSONArray jArray = null;
		JSONObject jObject = null;

		Object obj = jsonObject.get(childName);
		if(obj != null) {
			if (obj instanceof JSONArray) {
				// It's an array
				jArray = (JSONArray)obj;
			}
			else if (obj instanceof JSONObject) {
				// It's an object
				jObject = (JSONObject)obj;

				jArray = new JSONArray();
				//jArray.add(jObject.toJSONString());				
				jArray.add(jObject);				
			}
			else {
				// It's something else, like a string or number
				jArray = new JSONArray();
			}
		} else {				
			jArray = new JSONArray();
		}
		return jArray;

	}

	public static long GetLongFromJson(JSONObject jsonObject, String key) {

		long ivalue = 0;

		String json = jsonObject.toJSONString();

		JsonElement rootNode = new JsonParser().parse(json); 	

		if(rootNode.isJsonObject()) {

			JsonObject jObjectKey = rootNode.getAsJsonObject();

			if(CHECKKEYEXISTS(	rootNode.getAsJsonObject(), key)){    

				if(CHECKKEYVALUE(jObjectKey, key)) {

					JsonElement jsonElement = jObjectKey.get(key);

					ivalue = (long) jsonElement.getAsLong();
				}
			}
		}
		return ivalue;
	}
	
	public static String GetStringFromJson(JSONObject jsonObject, String key) {
		String json = jsonObject.toJSONString();

		JsonElement rootNode = new JsonParser().parse(json); 	

		String value = "";

		if(rootNode.isJsonObject()) {

			JsonObject jObjectKey = rootNode.getAsJsonObject();

			if(CHECKKEYEXISTS(	rootNode.getAsJsonObject(), key)){    

				if(CHECKKEYVALUE(jObjectKey, key)) {
					value = jObjectKey.get(key).getAsString();
				}
			}
		}
		return value;
	}

	public static boolean CHECKKEYEXISTS(JsonObject jsonObject, String key) {
		if (jsonObject.get(key).isJsonNull()) {
			return false;
		} else {
			return jsonObject.has(key);
		}
	}

	public static boolean CHECKKEYVALUE(JsonObject JsonObject, String key) {
		JsonElement el = JsonObject.get(key);
		if (el != null && !el.isJsonNull()){
			return true;
		} else {
			return false;
		}
	}
	public static boolean CHECKJSONARRAY(JsonObject JsonObject, String key) 
	{

		JsonElement jsonElement = JsonObject.get(key);
		if (jsonElement.isJsonArray()) {
			return true;
		} else {
			return false;
		}
	}

	private String getDate() {
		Date date = Calendar.getInstance().getTime();

		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String today = formatter.format(date);
		return today;
	}

	public static void main(String[] args) {
		String  value = "", svalue="";
		boolean bvalue=false;
		int ivalue = 0;
		long lvalue = 0;
		
		String json = "{\"dataslots\":{\"dataslot\":[\r\n" + 
				"{\"name\":\"SUAP_codeProtocollo\",\"value\":39628,\"label\":{}},"
				+ "{\"name\":\"DataRispostaPrediniego\",\"label\":{}},"
				+ "{\"name\":\"SUAP_omissis\",\"value\":false,\"label\":{}}\r\n" + 
				"]}}";

		ParserJson prJ = new ParserJson(json);
		//DataSlots
		/*
		JSONParser parser = new JSONParser();
		JSONObject jsonObject;
		try {
			jsonObject = (JSONObject) parser.parse(json);
		
			JSONObject dataslotsJsonObj =getChildJSONObjectByName(jsonObject, "dataslots");
			JSONArray dataslotJsonArr = ParserJson.getChildJSONObjectArrayByName(dataslotsJsonObj, "dataslot");
			
			for(int m=0; m<dataslotJsonArr.size(); m++ ) { 
			
				DataSlots dataSlots = new DataSlots();
				JSONObject dataslotJsonObj = (JSONObject)dataslotJsonArr.get(m); 
				
				try{  
					value = getTypeOfKeyObject(dataslotJsonObj, "name");
					if(value.equalsIgnoreCase("long")) {lvalue = (long)dataslotJsonObj.get("name");dataSlots.setName(String.valueOf(lvalue));}
					if(value.equalsIgnoreCase("integer")) {ivalue = (int)dataslotJsonObj.get("name");dataSlots.setName(String.valueOf(ivalue));}
					if(value.equalsIgnoreCase("boolean")) {bvalue = (boolean)dataslotJsonObj.get("name");dataSlots.setName(String.valueOf(bvalue));}
					if(value.equalsIgnoreCase("string")) {svalue = (String)dataslotJsonObj.get("name").toString().replaceAll("[{}]","");dataSlots.setName(svalue);}
					if(value.equalsIgnoreCase("null")) {svalue=""; dataSlots.setName(svalue);}
				} catch(Exception e) {}
				
				try{  
					value = getTypeOfKeyObject(dataslotJsonObj, "value");
					if(value.equalsIgnoreCase("long")) {lvalue = (long)dataslotJsonObj.get("value");dataSlots.setValue(String.valueOf(lvalue));}
					if(value.equalsIgnoreCase("integer")) {ivalue = (int)dataslotJsonObj.get("value");dataSlots.setValue(String.valueOf(ivalue));}
					if(value.equalsIgnoreCase("boolean")) {bvalue = (boolean)dataslotJsonObj.get("value");dataSlots.setValue(String.valueOf(bvalue));}
					if(value.equalsIgnoreCase("string")) {svalue = (String)dataslotJsonObj.get("value").toString().replaceAll("[{}]","");dataSlots.setValue(svalue);}
					if(value.equalsIgnoreCase("null")) {svalue=""; dataSlots.setValue(svalue);}
				} catch(Exception e) {}
								
				try{  
					value = getTypeOfKeyObject(dataslotJsonObj, "label");
					if(value.equalsIgnoreCase("long")) {lvalue = (long)dataslotJsonObj.get("label");dataSlots.setLabel(String.valueOf(lvalue));}
					if(value.equalsIgnoreCase("integer")) {ivalue = (int)dataslotJsonObj.get("label");dataSlots.setLabel(String.valueOf(ivalue));}
					if(value.equalsIgnoreCase("boolean")) {bvalue = (boolean)dataslotJsonObj.get("label");dataSlots.setLabel(String.valueOf(bvalue));}
					if(value.equalsIgnoreCase("string")) {svalue = (String)dataslotJsonObj.get("label").toString().replaceAll("[{}]","");dataSlots.setLabel(svalue);}
					if(value.equalsIgnoreCase("null")) {svalue=""; dataSlots.setLabel(svalue);}
				} catch(Exception e) {}
				
				value = "";
	
			}
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
*/
	}
}
