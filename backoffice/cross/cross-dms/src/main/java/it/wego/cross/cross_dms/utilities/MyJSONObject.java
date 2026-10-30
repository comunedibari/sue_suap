package it.wego.cross.cross_dms.utilities;


import org.json.simple.*;

import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * A JSON object. Key value pairs are unordered. JSONObject supports java.util.Map interface.
 * 
 * @author FangYidong<fangyidong@yahoo.com.cn>
 */
public class MyJSONObject extends HashMap implements Map, JSONAware, JSONStreamAware{

	public static JsonParser parser = new JsonParser();
	
	public static boolean CHECKIFKEYEXISTS(String response, String key) {
		JsonParser parser = new JsonParser();
		JsonObject jsonObject = parser.parse(response).getAsJsonObject();
		return jsonObject.has(key);
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
	
	public static boolean checkKeyChildJSONObjectByName(JSONObject jsonObject, String childName) {
		JSONObject extnJsonObj = (JSONObject) jsonObject.get(childName);
		if (extnJsonObj == null) return false;
		else return true;
	}
	public static JSONObject getChildJSONObjectByName(JSONObject jsonObject, String childName) {
		JSONObject extnJsonObj = (JSONObject) jsonObject.get(childName);

		if (extnJsonObj == null)
			extnJsonObj = new JSONObject();
		return extnJsonObj;
	}

	public static JSONArray getChildJSONObjectArrayByName(JSONObject jsonObject, String childName) {
		JSONArray jsonObjArray = (JSONArray) jsonObject.get(childName);

		if (jsonObjArray == null)
			jsonObjArray = new JSONArray();
		return jsonObjArray;
	}

	public static JSONArray convertJSONObjectToJSONArray(JSONObject jsonObject, String childName) {
		Object obj = jsonObject.get(childName);
		JSONArray jArray = null;
		JSONObject jObject = null;

		if (obj instanceof JSONArray) {
		    // It's an array
			jArray = (JSONArray)obj;
		}
		else if (obj instanceof JSONObject) {
		    // It's an object
			jObject = (JSONObject)obj;
			
			jArray = new JSONArray();
			jArray.add(jObject.toJSONString());				
		}
		else {
		    // It's something else, like a string or number
		}
		return jArray;
	}
	public static void readgson(String file) {
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
	public static void readgsonSstring(String json) {
        System.out.println( "Reading JSON file from Java program" );

        com.google.gson.JsonObject object = (JsonObject) parser.parse( json );

        Set <java.util.Map.Entry<String, com.google.gson.JsonElement>> keys = object.entrySet();
        if ( keys.isEmpty() ) {
            System.out.println( "Empty JSON Object" );
        }else {
            Map<String, Object> map = json_UnKnown_Format( keys );
            System.out.println("Json 2 Map : " + map);
        }
	}
	
	public static Map<String, Object> json_UnKnown_Format( Set <java.util.Map.Entry<String, com.google.gson.JsonElement>> keys ){
	    
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
    /**
     * Encode a map into JSON text and write it to out.
     * If this map is also a JSONAware or JSONStreamAware, JSONAware or JSONStreamAware specific behaviours will be ignored at this top level.
     * 
     * @see org.json.simple.JSONValue#writeJSONString(Object, Writer)
     * 
     * @param map
     * @param out
     */
	public static void writeJSONString(Map map, Writer out) throws IOException {
		if(map == null){
			out.write("null");
			return;
		}
		
		boolean first = true;
		Iterator iter=map.entrySet().iterator();
		
        out.write('{');
		while(iter.hasNext()){
            if(first)
                first = false;
            else
                out.write(',');
			Map.Entry entry=(Map.Entry)iter.next();
            out.write('\"');
            out.write(escape(String.valueOf(entry.getKey())));
            out.write('\"');
            out.write(':');
			JSONValue.writeJSONString(entry.getValue(), out);
		}
		out.write('}');
	}

	public void writeJSONString(Writer out) throws IOException{
		writeJSONString(this, out);
	}
	
	/**
	 * Convert a map to JSON text. The result is a JSON object. 
	 * If this map is also a JSONAware, JSONAware specific behaviours will be omitted at this top level.
	 * 
	 * @see org.json.simple.JSONValue#toJSONString(Object)
	 * 
	 * @param map
	 * @return JSON text, or "null" if map is null.
	 */
	public static String toJSONString(Map map){
		if(map == null)
			return "null";
		
        StringBuffer sb = new StringBuffer();
        boolean first = true;
		Iterator iter=map.entrySet().iterator();
		
        sb.append('{');
		while(iter.hasNext()){
            if(first)
                first = false;
            else
                sb.append(',');
            
			Map.Entry entry=(Map.Entry)iter.next();
			toJSONString(String.valueOf(entry.getKey()),entry.getValue(), sb);
		}
        sb.append('}');
		return sb.toString();
	}
	
	public String toJSONString(){
		return toJSONString(this);
	}
	
	private static String toJSONString(String key,Object value, StringBuffer sb){
		sb.append('\"');
        if(key == null)
            sb.append("null");
        else
        	MyJSONValue.escape(key, sb);
		sb.append('\"').append(':');
		
		sb.append(JSONValue.toJSONString(value));
		
		return sb.toString();
	}
	
	public String toString(){
		return toJSONString();
	}

	public static String toString(String key,Object value){
        StringBuffer sb = new StringBuffer();
		toJSONString(key, value, sb);
        return sb.toString();
	}
	
	/**
	 * Escape quotes, \, /, \r, \n, \b, \f, \t and other control characters (U+0000 through U+001F).
	 * It's the same as JSONValue.escape() only for compatibility here.
	 * 
	 * @see org.json.simple.JSONValue#escape(String)
	 * 
	 * @param s
	 * @return
	 */
	public static String escape(String s){
		return JSONValue.escape(s);
	}
}
