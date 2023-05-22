/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/

package com.jaspersoft.jasperserver.api.engine.scheduling.domain.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Collection;

/**
 * <p></p>
 *
 * @author Yaroslav.Kovalchyk
 * @version $Id$
 */
@XmlType(name = "collection")
public class ValuesCollection {
    private Collection<Object> collection;

    @XmlElement(name = "item")
    public Collection<Object> getCollection() {
        return collection;
    }

    public void setCollection(Collection<Object> collection) {
        this.collection = collection;
    }
}
