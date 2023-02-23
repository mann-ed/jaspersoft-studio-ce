/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/

package com.jaspersoft.jasperserver.remote.services.impl;

/**
 * @author Yaroslav.Kovalchyk
 * @version $Id: AutoincrementalIntegerBidirectionalMapping.java 23005 2012-04-05 12:47:10Z ykovalchyk $
 */
public interface AutoincrementalIntegerBidirectionalMapping<V> extends BidirectionalMapping<Integer,V> {
    Integer put(V value);
}
