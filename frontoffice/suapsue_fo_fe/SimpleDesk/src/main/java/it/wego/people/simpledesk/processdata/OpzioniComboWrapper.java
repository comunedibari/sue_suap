package it.wego.people.simpledesk.processdata;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "opzioniCombo")
public class OpzioniComboWrapper {

    @XmlElement(name = "opzioniCombo")
    protected List<OpzioniCombo> opzioniCombo;

    public List<OpzioniCombo> getOpzioniCombo() {
        if (opzioniCombo == null) {
            opzioniCombo = new ArrayList<OpzioniCombo>();
        }
        return this.opzioniCombo;
    }
    private transient Map<String, String> comboMap;

    public Map<String, String> getComboAsMap() {
        if (comboMap == null) {
            comboMap = Maps.newLinkedHashMap(); // linked hashmap maintain items ordering
            for (OpzioniCombo opzioneCombo : getOpzioniCombo()) {
                comboMap.put(opzioneCombo.getCodice(), opzioneCombo.getDescrizione());
            }
        }
        return comboMap;
    }

    public Map<String, String> getComboAsReverseMap() {
        return ImmutableBiMap.copyOf(getComboAsMap()).inverse();
    }
    private transient List<String> comboList;

    public List<String> getComboAsList() {
        if (comboList == null) {
            comboList = Lists.newArrayList(getComboAsMap().values());
        }
        return comboList;
    }
}
