/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/

package com.jaspersoft.jasperserver.api.engine.scheduling.domain.jaxb;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;

/**
 * @author Yaroslav.Kovalchyk
 * @version $Id$
 */
@XmlRootElement(name = "parameters")
public class ReportParametersMapWrapper {
    private HashMap<String, Object> parameterValues;

    public ReportParametersMapWrapper(){}
    public ReportParametersMapWrapper(HashMap<String, Object> parameterValues){
        this.parameterValues = parameterValues;
    }

    public HashMap<String, Object> getParameterValues() {
        return parameterValues;
    }

    public void setParameterValues(HashMap<String, Object> parameterValues) {
        this.parameterValues = parameterValues;
    }
}
