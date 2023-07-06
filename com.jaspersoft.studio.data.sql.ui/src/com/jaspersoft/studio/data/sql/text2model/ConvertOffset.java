/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.data.sql.text2model;

import com.jaspersoft.studio.data.sql.Offset;
import com.jaspersoft.studio.data.sql.SQLQueryDesigner;
import com.jaspersoft.studio.data.sql.impl.OffsetImpl;
import com.jaspersoft.studio.data.sql.model.MSQLRoot;
import com.jaspersoft.studio.data.sql.model.query.AMKeyword;

public class ConvertOffset {
	public static void convertOffset(SQLQueryDesigner designer, Offset cols) {
		if (cols == null)
			return;
		if (cols instanceof OffsetImpl) {
			String lim = "OFFSET " + cols.getOffset();
			MSQLRoot mroot = designer.getRoot();
			new AMKeyword(mroot, lim, null);
		}
	}

}
