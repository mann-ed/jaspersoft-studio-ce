/*
 * JasperReports - Free Java Reporting Library. Copyright (C) 2001 - 2009 Jaspersoft Corporation. All rights reserved.
 * http://www.jaspersoft.com
 * 
 * Unless you have purchased a commercial license agreement from Jaspersoft, the following license terms apply:
 * 
 * This program is part of JasperReports.
 * 
 * JasperReports is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General
 * Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * JasperReports is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with JasperReports. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package com.jaspersoft.studio.model.parameter.command;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignParameter;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.widgets.Display;

import com.jaspersoft.studio.messages.Messages;
import com.jaspersoft.studio.model.parameter.MParameter;
import com.jaspersoft.studio.model.parameter.MParameters;
import com.jaspersoft.studio.utils.ModelUtils;

/*
 * link nodes & together.
 * 
 * @author Chicu Veaceslav
 */
public class CreateParameterCommand extends Command {

	/** The jr parameter. */
	private JRDesignParameter jrParameter;

	/** The jr dataset. */
	private JRDesignDataset jrDataset;

	/** The index. */
	private int index;

	/**
	 * Instantiates a new creates the parameter command.
	 * 
	 * @param destNode
	 *          the dest node
	 * @param srcNode
	 *          the src node
	 * @param position
	 *          the position
	 * @param index
	 *          the index
	 */
	public CreateParameterCommand(MParameters destNode, MParameter srcNode, int index) {
		super();
		this.jrDataset = (JRDesignDataset) destNode.getValue();
		this.index = index;
		if (srcNode != null && srcNode.getValue() != null)
			this.jrParameter = (JRDesignParameter) srcNode.getValue();
	}

	public CreateParameterCommand(JRDesignDataset jDataset, JRParameter jParam, int index) {
		super();
		this.jrDataset = jDataset;
		this.index = index;
		this.jrParameter = (JRDesignParameter) jParam;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		if (jrParameter == null) {
			this.jrParameter = MParameter.createJRParameter(jrDataset);
		}
		if (jrParameter != null) {
			try {
				if (index < 0 || index > jrDataset.getParametersList().size())
					jrDataset.addParameter(jrParameter);
				else
					jrDataset.addParameter(index, jrParameter);
			} catch (JRException e) {
				e.printStackTrace();
				if (e.getMessage().startsWith("Duplicate declaration")) { //$NON-NLS-1$
					String defaultName = ModelUtils.getDefaultName(jrDataset.getParametersMap(), "CopyOFParameter_"); //$NON-NLS-1$
					InputDialog dlg = new InputDialog(Display.getCurrent().getActiveShell(),
							Messages.CreateParameterCommand_parameter_name,
							Messages.CreateParameterCommand_parameter_name_dialog_text, defaultName, null);
					if (dlg.open() == InputDialog.OK) {
						jrParameter.setName(dlg.getValue());
						execute();
					}
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#canUndo()
	 */
	@Override
	public boolean canUndo() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		jrDataset.removeParameter(jrParameter);
	}
}
