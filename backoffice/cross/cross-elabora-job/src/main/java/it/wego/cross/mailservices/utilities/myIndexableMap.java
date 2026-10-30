package it.wego.cross.mailservices.utilities;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class myIndexableMap<K, V> extends HashMap<K, V> {

    private LinkedList<K> keyList = new LinkedList<>();
    
	@Override
    public V put(K key, V value) {
        if (!keyList.contains(key))
            keyList.add(key);
        return super.put(key, value);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Entry<? extends K, ? extends V> entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        keyList.clear();
        super.clear();
    }

    public List<K> getKeys() {
        return keyList;
    }

    public int getKeyIndex(K key) {
        return keyList.indexOf(key);
    }

    public K getKeyAt(int index) {
        if (keyList.size() > index)
            return keyList.get(index);
        return null;
    }

    public V getValueAt(int index) {
        K key = getKeyAt(index);
        if (key != null)
            return get(key);
        return null;
    }
    
    public int getKeyListSize() {
		return keyList.size();
	}
    
    public static void main(String[] args) {
    	Map<String, String> myMap = new myIndexableMap<String, String>();

    	myMap.put("field1", "valore 1");
    	myMap.put("field2", "valore 2");
    	
    	String key = ((myIndexableMap<String, String>) myMap).getKeyAt(1);
    	String value =  ((myIndexableMap<String, String>) myMap).getValueAt(1);
    	
    }
}