/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.editor.preview.datasnapshot;

import java.util.Date;

import net.sf.jasperreports.data.cache.ColumnDataCacheHandler;
import net.sf.jasperreports.data.cache.DataRecorder;
import net.sf.jasperreports.data.cache.DataSnapshot;

public class JSSColumnDataCacheHandler extends ColumnDataCacheHandler {
	private Date creationTimestamp;

	public Date getCreationTimestamp() {
		return creationTimestamp;
	}

	public JSSColumnDataCacheHandler() {
		super();
	}

	public JSSColumnDataCacheHandler(DataSnapshot snapshot, Date creationTimestamp) {
		super();
		setDataSnapshot(snapshot);
		this.creationTimestamp = creationTimestamp;
	}

	@Override
	public DataRecorder createDataRecorder() {
		creationTimestamp = new Date();

		return super.createDataRecorder();
	}
}
