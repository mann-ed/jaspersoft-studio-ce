/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/

package com.jaspersoft.jasperserver.api.metadata.user.domain.client;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.jaspersoft.jasperserver.api.metadata.user.domain.ProfileAttribute;

/**
 * @author sbirney
 */

@XmlRootElement(name = "profileAttribute")
public class ProfileAttributeImpl implements ProfileAttribute, Serializable {

    private String attrName;
    private String attrValue;

    @XmlTransient
    private Object principal;

    public String getAttrName() {
	return attrName;
    }
    public void setAttrName(String s) {
	this.attrName = s;
    }

    public String getAttrValue() {
	return attrValue;
    }
    public void setAttrValue(String s) {
	this.attrValue = s;
    }

    @XmlTransient
    public Object getPrincipal() {
	return principal;
    }


    public void setPrincipal(Object o) {
	this.principal = o;
    }

    public String toString() {
	return new ToStringBuilder(this)
	    .append("attrName", getAttrName())
	    .append("attrValue", getAttrValue())
	    .append("principal", getPrincipal())
	    .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ProfileAttributeImpl) ) return false;
        ProfileAttributeImpl castOther = (ProfileAttributeImpl) other;
        return new EqualsBuilder()
            .append(this.getAttrName(), castOther.getAttrName())
            .append(this.getPrincipal(), castOther.getPrincipal())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getAttrName())
            .append(getPrincipal())
            .toHashCode();
    }
}
