/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/

package com.jaspersoft.jasperserver.remote.services.impl;

/**
 * @author Yaroslav.Kovalchyk
 * @version $Id: BidirectionalMapping.java 23005 2012-04-05 12:47:10Z ykovalchyk $
 */
public interface BidirectionalMapping<K, V> {
    K getKey(V value);

    V getValue(K key);

    void put(K key, V value);

    void clear();

    int size();
}
