/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/

package com.jaspersoft.jasperserver.remote.services.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Yaroslav.Kovalchyk
 * @version $Id: HashMapBasedBidirectionalMapping.java 23005 2012-04-05 12:47:10Z ykovalchyk $
 */
public class HashMapBasedBidirectionalMapping<K, V> implements BidirectionalMapping<K,V> {

    protected final Map<K, V> keyValueMap = Collections.synchronizedMap(new HashMap<K, V>());
    protected final Map<V, K> valueKeyMap = Collections.synchronizedMap(new HashMap<V, K>());

    @Override
    public K getKey(V value) {
        return valueKeyMap.get(value);
    }

    @Override
    public V getValue(K key) {
        return keyValueMap.get(key);
    }

    @Override
    public void put(K key, V value) {
        synchronized (keyValueMap) {
            V oldValue = keyValueMap.get(key);
            if(oldValue != null)
                valueKeyMap.remove(oldValue);
            K oldKey = valueKeyMap.get(value);
            if(oldKey != null)
                keyValueMap.remove(oldKey);
            keyValueMap.put(key, value);
            valueKeyMap.put(value, key);
        }
    }

    @Override
    public void clear() {
        synchronized (keyValueMap) {
            keyValueMap.clear();
            valueKeyMap.clear();
        }
    }

    @Override
    public int size() {
        return keyValueMap.size();
    }

}
