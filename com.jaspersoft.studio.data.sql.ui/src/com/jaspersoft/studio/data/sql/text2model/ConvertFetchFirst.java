/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.data.sql.text2model;

import com.jaspersoft.studio.data.sql.FetchFirst;
import com.jaspersoft.studio.data.sql.SQLQueryDesigner;
import com.jaspersoft.studio.data.sql.impl.FetchFirstImpl;
import com.jaspersoft.studio.data.sql.model.MSQLRoot;
import com.jaspersoft.studio.data.sql.model.query.AMKeyword;

public class ConvertFetchFirst {
	public static void convertFetchFirst(SQLQueryDesigner designer,
			FetchFirst cols) {
		if (cols == null)
			return;
		if (cols instanceof FetchFirstImpl) {
			String lim = "FETCH FIRST ";
			if (cols.getFetchFirst() != null)
				lim += cols.getFetchFirst().getInteger() + " ";
			lim += cols.getRow().toUpperCase() + " ONLY ";
			MSQLRoot mroot = designer.getRoot();
			new AMKeyword(mroot, lim, null);
		}
	}

}
