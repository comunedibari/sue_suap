/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dto;

import com.google.gson.Gson;
import java.io.Serializable;
import org.apache.commons.beanutils.PropertyUtils;

/**
 *
 * @author CS
 */
public class AbstractDTO {

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
   
}
