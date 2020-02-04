
package it.wego.cross.beans.grid;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class GridBeanExclusionStrategy  implements ExclusionStrategy {

    @Override
    public boolean shouldSkipField(FieldAttributes fa) {
        if (fa.getName().equals("idEntePadre")){
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean shouldSkipClass(Class<?> type) {
        return false;
    }

}
