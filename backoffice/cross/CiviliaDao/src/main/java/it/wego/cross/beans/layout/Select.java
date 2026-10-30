/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.beans.layout;

/**
 *
 * @author Gabriele
 */
public class Select implements Comparable<Select>{

    private String itemValue;
    private String itemLabel;

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }

    public String getItemLabel() {
        return itemLabel;
    }

    public void setItemLabel(String itemLabel) {
        this.itemLabel = itemLabel;
    }

    @Override
    public int compareTo(Select o) {
        return itemValue.compareTo(o.itemValue);
    }
}
