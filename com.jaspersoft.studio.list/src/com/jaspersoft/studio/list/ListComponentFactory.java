/*
 * Jaspersoft Open Studio - Eclipse-based JasperReports Designer.
 * Copyright (C) 2005 - 2010 Jaspersoft Corporation. All rights reserved.
 * http://www.jaspersoft.com
 *
 * Unless you have purchased a commercial license agreement from Jaspersoft,
 * the following license terms apply:
 *
 * This program is part of iReport.
 *
 * iReport is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * iReport is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with iReport. If not, see <http://www.gnu.org/licenses/>.
 */
package com.jaspersoft.studio.list;

import java.util.ArrayList;
import java.util.List;

import net.sf.jasperreports.components.list.ListContents;
import net.sf.jasperreports.components.list.StandardListComponent;
import net.sf.jasperreports.engine.JRComponentElement;
import net.sf.jasperreports.engine.design.JRDesignComponentElement;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;

import com.jaspersoft.studio.IComponentFactory;
import com.jaspersoft.studio.list.figure.ListFigure;
import com.jaspersoft.studio.list.model.MList;
import com.jaspersoft.studio.model.ANode;

public class ListComponentFactory implements IComponentFactory {

	public ANode createNode(ANode parent, Object jrObject, int newIndex) {
		if (jrObject instanceof JRDesignComponentElement
				&& ((JRDesignComponentElement) jrObject).getComponent() instanceof StandardListComponent)
			return new MList(parent, (JRDesignComponentElement) jrObject, newIndex);
		return null;
	}

	public IFigure createFigure(ANode node) {
		if (node instanceof MList)
			return new ListFigure();
		return null;
	}

	public List<?> getChildren4Element(Object jrObject) {
		if (jrObject instanceof JRComponentElement
				&& ((JRDesignComponentElement) jrObject).getComponent() instanceof StandardListComponent) {
			StandardListComponent slc = (StandardListComponent) ((JRDesignComponentElement) jrObject).getComponent();
			ListContents lc = slc.getContents();
			if (lc != null)
				return lc.getChildren();
		}
		return null;
	}

	public List<Class<?>> getPaletteEntries() {
		List<Class<?>> list = new ArrayList<Class<?>>();
		list.add(MList.class);
		return list;
	}

	public Command getCreateCommand(ANode parent, ANode child, Point location, int newIndex) {
		return null;
	}

}
