/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/

package com.jaspersoft.jasperserver.remote.services.impl;

/**
 * @author Yaroslav.Kovalchyk
 * @version $Id: HashMapBasedAutoincrementalIntegerBidirectionalMapping.java 23005 2012-04-05 12:47:10Z ykovalchyk $
 */
public class HashMapBasedAutoincrementalIntegerBidirectionalMapping<V> extends HashMapBasedBidirectionalMapping<Integer, V> implements AutoincrementalIntegerBidirectionalMapping<V> {

    public Integer put(V value) {
        Integer key = getKey(value);
        if (key == null)
            synchronized (keyValueMap) {
                key = getKey(value);
                if (key == null) {
                    key = size(); // size() == latest object index + 1
                    put(key, value);
                }
            }
        return key;
    }
}
