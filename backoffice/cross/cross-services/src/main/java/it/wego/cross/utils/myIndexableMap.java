package it.wego.cross.utils;

import java.math.BigInteger;
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
    	Map<Integer, BigInteger> myMapFileAllegati = new myIndexableMap<Integer, BigInteger>();

    	myMapFileAllegati.put(Integer.valueOf(893), BigInteger.valueOf(150000));
    	myMapFileAllegati.put(Integer.valueOf(7034), BigInteger.valueOf(2000));
    	myMapFileAllegati.put(Integer.valueOf(10233), BigInteger.valueOf(4500));
    	
    	//myMap.put(Integer.valueOf(10233), BigInteger.valueOf(4500));
    	Integer keyRuvo = 10233;
    	Integer key = 10234;
    	long lValue = 3056677;
    	    	
    	BigInteger value = new BigInteger("0");
    	System.out.println("Key1:" + key);
    	System.out.println("value1:" + value);
    	
    	value = myMapFileAllegati.get(keyRuvo);
    	BigInteger bVvalueLong = BigInteger.valueOf(lValue);
    	
    	BigInteger b3 = value.add(bVvalueLong);
    	System.out.println("Key2:" + keyRuvo);
    	System.out.println("value2:" + b3);

    	myMapFileAllegati.put(keyRuvo, b3);
    	value = myMapFileAllegati.get(keyRuvo);
    	
    	System.out.println("Key3:" + keyRuvo);
    	System.out.println("value3:" + value);

    	
    }
}