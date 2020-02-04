/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.utils;

import java.util.Map;

/**
 *
 * @author Gabriele
 */
public class FTLUtils {

    public static void updateMap(Map<String, Object> mapToUpdate, Map<String, Object> referenceMap) {
        if (referenceMap != null) {
            for (Map.Entry<String, Object> entry : referenceMap.entrySet()) {
                String key = entry.getKey();
                if (entry.getValue() != null && mapToUpdate.get(key) == null) {
                    mapToUpdate.put(key, entry.getValue());
                }

            }
        }
    }

    public static Object getValue(String key, Map<String, Object> instanceMap, Map<String, Object> referenceMap, Object defaultValue) {
        if (instanceMap != null && instanceMap.get(key) != null) {
            return instanceMap.get(key);
        }

        if (referenceMap != null && referenceMap.get(key) != null) {
            return referenceMap.get(key);
        }

        return defaultValue;
    }
}
