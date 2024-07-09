/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.eclipse.util;

import java.util.Map;

/**
 * A generic entry to bound a name to a value, a general pair
 * class that can be used in many case to do association of two 
 * element
 * 
 * @author Orlandin Marco
 *
 */
public class Pair<K, V> implements Map.Entry<K, V> {
    
	private final K key;
    
	private V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public V setValue(V value) {
        V old = this.value;
        this.value = value;
        return old;
    }
    
    @Override
    public boolean equals(Object obj) {
    	if (obj instanceof Pair){
    		Pair<?, ?> def = (Pair<?, ?>)obj;
    		return safeEquals(key, def.getKey()) &&
    				safeEquals(value, def.getValue());
    	}
    	return false;
    }
    
    @Override
    protected Object clone() throws CloneNotSupportedException {
    	return new Pair<K, V>(key, value);
    }
    
	/**
	 * Determines whether two objects are equal, including <code>null</code> values.
	 * 
	 * @param o1
	 * @param o2
	 * @return whether the two objects are equal
	 */
	public static boolean safeEquals(Object o1, Object o2) {
		return (o1 == null) ? (o2 == null) : (o2 != null && o1.equals(o2));
	}
}
