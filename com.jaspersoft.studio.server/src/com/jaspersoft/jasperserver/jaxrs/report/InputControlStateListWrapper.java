/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/

package com.jaspersoft.jasperserver.jaxrs.report;

import com.jaspersoft.jasperserver.dto.reports.inputcontrols.InputControlState;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author akasych
 * @version $Id$
 */
@XmlRootElement(name = "inputControlStateList")
public class InputControlStateListWrapper {

    private List<InputControlState> inputControlStateList;

    public InputControlStateListWrapper(){}
    public InputControlStateListWrapper(List<InputControlState> inputControlStateList){
        this.inputControlStateList = inputControlStateList;
    }
    @XmlElement(name = "inputControlState")
    public List<InputControlState> getInputControlStateList() {
        return inputControlStateList;
    }

    public void setInputControlStateList(List<InputControlState> inputControlStateList) {
        this.inputControlStateList = inputControlStateList;
    }

}
