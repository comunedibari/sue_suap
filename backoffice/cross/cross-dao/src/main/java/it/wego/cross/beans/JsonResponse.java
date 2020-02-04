/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.beans;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Giuseppe
 */
public class JsonResponse {

    private Boolean success;
    private String message;
    private Map<String, String> attributes = new HashMap<String, String>();

    public JsonResponse() {
    }

    public JsonResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static JsonResponse buildJsonResponse(Boolean esito, String messaggio) {
        JsonResponse response = new JsonResponse();
        response.setMessage(messaggio);
        response.setSuccess(esito);
        return response;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public void addAttribute(String key, String value) {
        if (this.attributes == null) {
            this.attributes = new HashMap<String, String>();
        }
        this.attributes.put(key, value);
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
        return gson.toJson(this);
    }
}
