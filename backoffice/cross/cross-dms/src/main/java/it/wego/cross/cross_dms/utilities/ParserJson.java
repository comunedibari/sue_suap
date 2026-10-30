// Source code is decompiled from a .class file using FernFlower decompiler.
package it.wego.cross.cross_dms.utilities;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ParserJson {
	private JsonParser parser = new JsonParser();
	private JsonElement rootNode;
	private String value;

	public ParserJson(String json) {
		this.rootNode = (new JsonParser()).parse(json);
	}

	public JsonElement getRootNode() {
		return this.rootNode;
	}

	public void setRootNode(JsonElement rootNode) {
		this.rootNode = rootNode;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void readgson(String file) {
		try {
			System.out.println("Reading JSON file from Java program");
			FileReader fileReader = new FileReader(file);
			JsonObject object = (JsonObject)this.parser.parse(fileReader);
			Set<Map.Entry<String, JsonElement>> keys = object.entrySet();
			if (keys.isEmpty()) {
				System.out.println("Empty JSON Object");
			} else {
				Map<String, Object> map = this.json_UnKnown_Format(keys);
				System.out.println("Json 2 Map : " + map);
			}
		} catch (IOException var6) {
			System.out.println("Input File Does not Exists.");
		}

	}

	public Map<String, Object> json_UnKnown_Format(Set<Map.Entry<String, JsonElement>> keys) {
		Map<String, Object> jsonMap = new HashMap();
		Iterator var4 = keys.iterator();

		while(true) {
			while(var4.hasNext()) {
				Map.Entry<String, JsonElement> entry = (Map.Entry)var4.next();
				String keyEntry = (String)entry.getKey();
				System.out.println(keyEntry + " : ");
				JsonElement valuesEntry = (JsonElement)entry.getValue();
				if (valuesEntry.isJsonNull()) {
					System.out.println(valuesEntry);
					jsonMap.put(keyEntry, valuesEntry);
				} else if (valuesEntry.isJsonPrimitive()) {
					System.out.println("P - " + valuesEntry);
					jsonMap.put(keyEntry, valuesEntry);
				} else if (!valuesEntry.isJsonArray()) {
					if (valuesEntry.isJsonObject()) {
						JsonObject obj = (JsonObject)this.parser.parse(valuesEntry.toString());
						Set<Map.Entry<String, JsonElement>> obj_key = obj.entrySet();
						jsonMap.put(keyEntry, this.json_UnKnown_Format(obj_key));
					}
				} else {
					JsonArray array = valuesEntry.getAsJsonArray();
					List<Object> array2List = new ArrayList();
					Iterator var10 = array.iterator();

					while(var10.hasNext()) {
						JsonElement jsonElements = (JsonElement)var10.next();
						System.out.println("A - " + jsonElements);
						array2List.add(jsonElements);
					}

					jsonMap.put(keyEntry, array2List);
				}
			}

			return jsonMap;
		}
	}

	public static String getValueBykeyToString(JSONObject jsonObject, String key) {
		String svalue = "";
		boolean bvalue = false;
		int ivalue = 0;
		long lvalue = 0L;
		String value_type = getTypeOfKeyObject(jsonObject, key);
		if (value_type.equalsIgnoreCase("long")) {
			lvalue = (Long)jsonObject.get(key);
			svalue = Long.toString(lvalue);
		}

		if (value_type.equalsIgnoreCase("integer")) {
			ivalue = (Integer)jsonObject.get(key);
			svalue = Integer.valueOf(ivalue).toString();
		}

		if (value_type.equalsIgnoreCase("boolean")) {
			bvalue = (Boolean)jsonObject.get(key);
			if (!bvalue) {
				svalue = "false";
			} else {
				svalue = "true";
			}
		}

		if (value_type.equalsIgnoreCase("string")) {
			svalue = jsonObject.get(key).toString().replaceAll("[{}]", "");
		}

		if (value_type.equalsIgnoreCase("null")) {
			svalue = "";
		}

		return svalue;
	}

	public static int getKeyValueInt(JsonObject JsonObject, String key) {
		int result = 0;
		JsonElement el = JsonObject.get(key);
		if (el != null && !el.isJsonNull()) {
			result = el.getAsInt();
		}

		return result;
	}

	public static String getKeyValueString(JsonObject JsonObject, String key) {
		String result = "";
		JsonElement el = JsonObject.get(key);
		if (el != null && !el.isJsonNull()) {
			result = el.getAsString();
		}

		return result;
	}

	public static boolean getTypeIntegerOfObject(JSONObject json, String keyValue) {
		boolean tipodato = false;
		Object v = json.get(keyValue);
		return v instanceof Integer ? true : tipodato;
	}

	public static String getTypeOfKeyObject(JSONObject json, String keyValue) {
		String tipodato = "";
		Object v = json.get(keyValue);
		if (v instanceof Integer) {
			tipodato = "integer";
		} else if (v instanceof Boolean) {
			tipodato = "boolean";
		} else if (!(v instanceof Float) && !(v instanceof Double)) {
			if (v instanceof String) {
				tipodato = "string";
			} else if (v instanceof Long) {
				tipodato = "long";
			} else {
				tipodato = "null";
			}
		} else {
			tipodato = "double";
		}

		return tipodato;
	}

	public static void iterateOfKeyObject(JSONObject json, String keyValue) {
		Iterator i = json.keySet().iterator();

		while(i.hasNext()) {
			String key = (String)i.next();
			Object val = json.get(key);
			System.out.println(val);
		}

	}

	public static boolean checkKeyChildJSONObjectByName(JSONObject jsonObject, String childName) {
		try {
			JSONObject extnJsonObj = (JSONObject)jsonObject.get(childName);
			return extnJsonObj != null;
		} catch (Exception var3) {
			return false;
		}
	}

	public static boolean checkChildJSONObjectByName(JSONObject jsonObject, String childName) {
		JSONObject extnJsonObj = null;

		try {
			extnJsonObj = (JSONObject)jsonObject.get(childName);
			return extnJsonObj != null;
		} catch (Exception var4) {
			return false;
		}
	}

	public static JSONObject getChildJSONObjectByName(JSONObject jsonObject, String childName) {
		JSONObject extnJsonObj = (JSONObject)jsonObject.get(childName);
		if (extnJsonObj == null) {
			extnJsonObj = new JSONObject();
		}

		return extnJsonObj;
	}

	public static JSONArray getChildJSONObjectArrayByName_old(JSONObject jsonObject, String childName) {
		JSONArray jsonObjArray = (JSONArray)jsonObject.get(childName);
		if (jsonObjArray == null) {
			jsonObjArray = new JSONArray();
		}

		return jsonObjArray;
	}

	public static JSONArray getChildJSONObjectArrayByName(JSONObject jsonObject, String childName) {
		JSONArray jArray = null;
		JSONObject jObject = null;
		Object obj = jsonObject.get(childName);
		if (obj != null) {
			if (obj instanceof JSONArray) {
				jArray = (JSONArray)obj;
			} else if (obj instanceof JSONObject) {
				jObject = (JSONObject)obj;
				jArray = new JSONArray();
				jArray.add(jObject);
			} else {
				jArray = new JSONArray();
			}
		} else {
			jArray = new JSONArray();
		}

		return jArray;
	}

	public static long GetLongFromJson(JSONObject jsonObject, String key) {
		long ivalue = 0L;
		String json = jsonObject.toJSONString();
		JsonElement rootNode = (new JsonParser()).parse(json);
		if (rootNode.isJsonObject()) {
			JsonObject jObjectKey = rootNode.getAsJsonObject();
			if (CHECKKEYEXISTS(rootNode.getAsJsonObject(), key) && CHECKKEYVALUE(jObjectKey, key)) {
				JsonElement jsonElement = jObjectKey.get(key);
				ivalue = jsonElement.getAsLong();
			}
		}

		return ivalue;
	}

	public static String GetStringFromJson(JSONObject jsonObject, String key) {
		String json = jsonObject.toJSONString();
		JsonElement rootNode = (new JsonParser()).parse(json);
		String value = "";
		if (rootNode.isJsonObject()) {
			JsonObject jObjectKey = rootNode.getAsJsonObject();
			if (CHECKKEYEXISTS(rootNode.getAsJsonObject(), key) && CHECKKEYVALUE(jObjectKey, key)) {
				value = jObjectKey.get(key).getAsString();
			}
		}

		return value;
	}

	public static boolean CHECKKEYEXISTS(JsonObject jsonObject, String key) {
		return jsonObject.get(key).isJsonNull() ? false : jsonObject.has(key);
	}

	public static boolean CHECKKEYVALUE(JsonObject JsonObject, String key) {
		JsonElement el = JsonObject.get(key);
		return el != null && !el.isJsonNull();
	}

	public static boolean CHECKJSONARRAY(JsonObject JsonObject, String key) {
		JsonElement jsonElement = JsonObject.get(key);
		return jsonElement.isJsonArray();
	}

	private String getDate() {
		Date date = Calendar.getInstance().getTime();
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String today = formatter.format(date);
		return today;
	}

	
	/*
	 * 
	 try {

        JSONParser jsonParser = new JSONParser();

        File file = new File("src/main/java/read.json");

        Object object = jsonParser.parse(new FileReader(file));

        jsonObject = (JSONObject) object;

        parseJson(jsonObject);

    } catch (Exception ex) {
        ex.printStackTrace();
    } 
	 
	 * 
	 */
	public static void getArray(Object object2) throws ParseException {

		JSONArray jsonArr = (JSONArray) object2;

		for (int k = 0; k < jsonArr.size(); k++) {

			if (jsonArr.get(k) instanceof JSONObject) {
				parseJson((JSONObject) jsonArr.get(k));
			} else {
				System.out.println(jsonArr.get(k));
			}

		}
	}

	public static void parseJson(JSONObject jsonObject) throws ParseException {

		Set<Object> set = jsonObject.keySet();
		Iterator<Object> iterator = set.iterator();
		while (iterator.hasNext()) {
			Object obj = iterator.next();
			if (jsonObject.get(obj) instanceof JSONArray) {
				System.out.println(obj.toString());
				getArray(jsonObject.get(obj));
			} else {
				if (jsonObject.get(obj) instanceof JSONObject) {
					parseJson((JSONObject) jsonObject.get(obj));
				} else {
					System.out.println(obj.toString() + "\t" + jsonObject.get(obj));
				}
			}
		}
	}


	public static void main(String[] args) {
		String value = "";
		String svalue = "";
		boolean bvalue = false;
		int ivalue = 0;
		long lvalue = 0L;
		String json = "{\"dataslots\":{\"dataslot\":[\r\n{\"name\":\"SUAP_codeProtocollo\",\"value\":39628,\"label\":{}},{\"name\":\"DataRispostaPrediniego\",\"label\":{}},{\"name\":\"SUAP_omissis\",\"value\":false,\"label\":{}}\r\n]}}";
		new ParserJson(json);
		JSONParser parser = new JSONParser();

		try {
			JSONObject jsonObject = (JSONObject)parser.parse(json);
			JSONObject dataslotsJsonObj = getChildJSONObjectByName(jsonObject, "dataslots");
			JSONArray var12 = getChildJSONObjectArrayByName(dataslotsJsonObj, "dataslot");
		} catch (ParseException var13) {
			var13.printStackTrace();
		}

	}
}
