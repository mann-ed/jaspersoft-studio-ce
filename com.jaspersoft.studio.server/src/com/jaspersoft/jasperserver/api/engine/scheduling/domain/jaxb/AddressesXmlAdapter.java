/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/

package com.jaspersoft.jasperserver.api.engine.scheduling.domain.jaxb;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.List;

/**
 * @author Yaroslav.Kovalchyk
 * @version $Id: AddressesXmlAdapter.java 22756 2012-03-23 10:39:15Z sergey.prilukin $
 */
public class AddressesXmlAdapter extends XmlAdapter<AddressesListWrapper, List<String>> {
    @Override
    public List<String> unmarshal(AddressesListWrapper v) throws Exception {
        return v != null ? v.getAddresses() : null;
    }

    @Override
    public AddressesListWrapper marshal(List<String> v) throws Exception {
        return v != null ? new AddressesListWrapper(v) : null;
    }
}
