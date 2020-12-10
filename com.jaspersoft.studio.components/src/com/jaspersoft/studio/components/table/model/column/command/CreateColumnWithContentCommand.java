package com.jaspersoft.studio.components.table.model.column.command;

import java.util.List;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gef.commands.CompoundCommand;

import com.jaspersoft.studio.components.table.model.MTable;
import com.jaspersoft.studio.components.table.model.column.MColumn;
import com.jaspersoft.studio.editor.layout.ILayout;
import com.jaspersoft.studio.editor.layout.LayoutCommand;
import com.jaspersoft.studio.editor.layout.LayoutManager;
import com.jaspersoft.studio.editor.layout.VerticalRowLayout;
import com.jaspersoft.studio.model.command.Tag;

import net.sf.jasperreports.components.table.BaseColumn;
import net.sf.jasperreports.components.table.DesignCell;
import net.sf.jasperreports.components.table.StandardBaseColumn;
import net.sf.jasperreports.components.table.StandardColumn;
import net.sf.jasperreports.components.table.StandardTable;
import net.sf.jasperreports.components.table.util.TableUtil;
import net.sf.jasperreports.engine.JRPropertiesMap;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignStaticText;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JasperDesign;

/**
 * Create a table column at the end with an element inside the detail 
 * 
 * @author Orlandin Marco
 *
 */
public class CreateColumnWithContentCommand extends CreateColumnCommand {
	
	private Tag elementToCreate;

	private MTable table;
	
	private CompoundCommand layoutCommands;
	
	public CreateColumnWithContentCommand(MTable destNode, Tag elementToCreate) {
		super(destNode, new MColumn(), -1);
		this.elementToCreate = elementToCreate;
		this.table = destNode;
	}
	
	/**
	 * Create a column that cab be added to the table, take the styles
	 * information for the new cells from the table properties map if available.
	 * If this informations are not available the style informations are
	 * recovered from the siblings columns
	 * 
	 * @param jrDesign
	 *            the JasperDesign of the current report
	 * @param jrTable
	 *            the table where the column is added
	 * @param tableMap
	 *            the properties map of the table
	 * @return the created columns
	 */
	@Override
	public StandardBaseColumn createColumn(JasperDesign jrDesign, StandardTable jrTable, JRPropertiesMap tableMap) {
		boolean createTHeader = true;
		boolean createTFooter = true;
		boolean createCHeader = true;
		boolean createCFooter = true;
		boolean createGHeader = false;
		boolean createGFooter = false;

		List<BaseColumn> columns = TableUtil.getAllColumns(jrTable);
		BaseColumn sibling = null;
		if (columns.size() > 0) {
			sibling = columns.get(columns.size() - 1);
		}

		if (sibling != null) {
			createTHeader = sibling.getTableHeader() != null;
			createTFooter = sibling.getTableFooter() != null;
			createCHeader = sibling.getColumnHeader() != null;
			createCFooter = sibling.getColumnFooter() != null;
			if (sibling.getGroupHeaders().size() > 0)
				createGHeader = sibling.getGroupHeaders().get(0) != null;
			if (sibling.getGroupFooters().size() > 0)
				createGFooter = sibling.getGroupFooters().get(0) != null;
		}

		StandardColumn result = null;
		if (hasStyleProperties(tableMap)) {
			result = addStyledColumnWithElement(jrDesign, jrTable, tableMap, createTHeader, createTFooter,
					createCHeader, createCFooter, createGHeader, createGFooter, sibling);
		} else {
			result = addColumnWithElement(jrDesign, jrTable, createTHeader, createTFooter,
					createCHeader, createCFooter, createGHeader, createGFooter, sibling);
		}

		return result;
	}
	
	@Override
	public void execute() {
		layoutCommands = new CompoundCommand();
		super.execute();
		//no need to undo them since on the undo the element will be deleted
		layoutCommands.execute();
	}
	
	private StandardColumn addStyledColumnWithElement(JasperDesign jrDesign, StandardTable jrTable, JRPropertiesMap tableMap, boolean isTHead, boolean isTFoot, boolean isCHead, boolean isCFoot, boolean isGHead, boolean isGFoot, BaseColumn sibling) {
		StandardColumn baseCol = CreateColumnCommand.addColumnWithStyle(jrDesign, jrTable, tableMap, isTHead, isTFoot, isCHead, isCFoot, isGHead, isGFoot);
		
		if (sibling != null && sibling.getWidth() > 0) {
			baseCol.setWidth(sibling.getWidth());
		}
		
		//add the texfield
		DesignCell detailCell = (DesignCell)baseCol.getDetailCell();
		JRDesignTextField textField = new JRDesignTextField();
		textField.setExpression((new JRDesignExpression(elementToCreate.getExpressionText())));
		detailCell.addElement(textField);

		//add the layout command for the textfield
		Dimension d = new Dimension(baseCol.getWidth(), detailCell.getHeight());
		d = LayoutManager.getPaddedSize(detailCell, d);
		ILayout layout = LayoutManager.getLayout(table.getPropertyHolder(), jrDesign, null, new VerticalRowLayout());
		layoutCommands.add(new LayoutCommand(jrDesign, detailCell, layout, d));
		
		//add the column header
		DesignCell columnHeaderName = (DesignCell)baseCol.getColumnHeader();
		if (columnHeaderName != null) {
			JRDesignStaticText staticTextHeader = new JRDesignStaticText();
			staticTextHeader.setText(elementToCreate.name);
			columnHeaderName.addElement(staticTextHeader);
			Dimension staticTextDimension = new Dimension(baseCol.getWidth(), columnHeaderName.getHeight());
			d = LayoutManager.getPaddedSize(detailCell, d);
			layoutCommands.add(new LayoutCommand(jrDesign, columnHeaderName, layout, staticTextDimension));

		}
		
		return baseCol;
	}
	
	private StandardColumn addColumnWithElement(JasperDesign jrDesign, StandardTable jrTable, boolean isTHead,
			boolean isTFoot, boolean isCHead, boolean isCFoot, boolean isGHead, boolean isGFoot, BaseColumn sibling) {
		StandardColumn baseCol = addColWithSibling(jrDesign, jrTable, isTHead, isTFoot, isCHead, isCFoot, isGHead, isGFoot, sibling);
		DesignCell detailCell = (DesignCell)baseCol.getDetailCell();
		
		//add the texfield
		JRDesignTextField textField = new JRDesignTextField();
		textField.setExpression((new JRDesignExpression(elementToCreate.getExpressionText())));
		detailCell.addElement(textField);

		//add the layout command for the textfield
		Dimension d = new Dimension(baseCol.getWidth(), detailCell.getHeight());
		d = LayoutManager.getPaddedSize(detailCell, d);
		ILayout layout = LayoutManager.getLayout(table.getPropertyHolder(), jrDesign, null, new VerticalRowLayout());
		layoutCommands.add(new LayoutCommand(jrDesign, detailCell, layout, d));
		
		//add the column header
		DesignCell columnHeaderName = (DesignCell)baseCol.getColumnHeader();
		if (columnHeaderName != null) {
			JRDesignStaticText staticTextHeader = new JRDesignStaticText();
			staticTextHeader.setText(elementToCreate.txt);
			columnHeaderName.addElement(staticTextHeader);
			Dimension staticTextDimension = new Dimension(baseCol.getWidth(), columnHeaderName.getHeight());
			d = LayoutManager.getPaddedSize(detailCell, d);
			layoutCommands.add(new LayoutCommand(jrDesign, columnHeaderName, layout, staticTextDimension));

		}
		
		return baseCol;
	}

}
