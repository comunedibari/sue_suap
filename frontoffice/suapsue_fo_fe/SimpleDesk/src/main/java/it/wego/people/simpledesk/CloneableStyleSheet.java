package it.wego.people.simpledesk;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.itextpdf.text.html.simpleparser.StyleSheet;
import java.util.Map;

/**
 *
 * @author aleph
 */
public class CloneableStyleSheet extends StyleSheet implements Cloneable {

    private static final Function<Map<String, String>, Map<String, String>> mapClonerFunc = new Function<Map<String, String>, Map<String, String>>() {

        public Map<String, String> apply(Map<String, String> input) {
            return Maps.newHashMap(input);
        }
    };

    public CloneableStyleSheet() {
    }

    public CloneableStyleSheet(CloneableStyleSheet styleSheet) {
        this.classMap = Maps.newHashMap(Maps.transformValues(styleSheet.classMap, mapClonerFunc));
        this.tagMap = Maps.newHashMap(Maps.transformValues(styleSheet.tagMap, mapClonerFunc));
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return getClone();
    }

    public CloneableStyleSheet getClone() {
        return new CloneableStyleSheet(this);
    }
}
