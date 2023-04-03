/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.components.chart.property.descriptor.seriescolor.dialog;

import java.awt.Color;

/*
 * @author Chicu Veaceslav
 * 
 */
public class SeriesColorDTO {

	public SeriesColorDTO(Color value) {
		super();
		this.value = value;
	}

	public Color getValue() {
		return value;
	}

	public void setValue(Color value) {
		this.value = value;
	}

	private Color value;

}
