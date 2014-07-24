/*******************************************************************************
 * Copyright (C) 2005 - 2014 TIBCO Software Inc. All rights reserved.
 * http://www.jaspersoft.com.
 * 
 * Unless you have purchased  a commercial license agreement from Jaspersoft,
 * the following license terms  apply:
 * 
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package com.jaspersoft.studio.property.descriptor.propexpr.dialog;

import java.util.ArrayList;
import java.util.List;

import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRTextField;
import net.sf.jasperreports.engine.design.JasperDesign;

import com.jaspersoft.studio.messages.Messages;
import com.jaspersoft.studio.property.infoList.ElementDescription;

/**
 * Class that define static methods to get the hint properties specific to some type
 * of elements
 */
public class HintsPropertiesList {
	
  public static final int SCOPE_ELEMENT = 1;
  public static final int SCOPE_REPORT = 2;
  public static final int SCOPE_TEXT_ELEMENT=3;
	
  private static List<ElementDescription> genericHints = null;
  
  private static List<ElementDescription> exporterHints = null;
  
	private static ElementDescription addHint(String propName){
		return new ElementDescription(propName,Messages.getString(propName), true);
	}
	
	
	private static List<ElementDescription> addHints() 
	{
		List<ElementDescription> result = new ArrayList<ElementDescription>();
		result.add(new ElementDescription("net.sf.jasperreports.text.truncate.at.char", Messages.getString("JRPropertyDialog.List.Prop1"), true));
		result.add(new ElementDescription("net.sf.jasperreports.text.truncate.suffix", Messages.getString("JRPropertyDialog.List.Prop2"), true));
		result.add(new ElementDescription("net.sf.jasperreports.print.keep.full.text", Messages.getString("JRPropertyDialog.List.Prop3"), true));
		result.add(new ElementDescription("net.sf.jasperreports.text.measurer.factory",	Messages.getString("JRPropertyDialog.List.Prop4"), true));
		result.add(new ElementDescription("net.sf.jasperreports.chart.theme", Messages.getString("JRPropertyDialog.List.Prop11") + Messages.getString("JRPropertyDialog.List.DefaultNull"), true));
		result.add(new ElementDescription("net.sf.jasperreports.export.pdf.tag.h1", Messages.getString("JRPropertyDialog.List.Prop14") + Messages.getString("JRPropertyDialog.List.DefaultNull"), true));
		result.add(new ElementDescription("net.sf.jasperreports.export.pdf.tag.h2", Messages.getString("JRPropertyDialog.List.Prop15") + Messages.getString("JRPropertyDialog.List.DefaultNull"), true));
		result.add(new ElementDescription("net.sf.jasperreports.export.pdf.tag.h3", Messages.getString("JRPropertyDialog.List.Prop16") + Messages.getString("JRPropertyDialog.List.DefaultNull"), true));
		result.add(new ElementDescription("net.sf.jasperreports.export.pdf.tag.table", Messages.getString("JRPropertyDialog.List.Prop17") + Messages.getString("JRPropertyDialog.List.DefaultNull"), true));
		result.add(new ElementDescription("net.sf.jasperreports.export.pdf.tag.tr", Messages.getString("JRPropertyDialog.List.Prop22") + Messages.getString("JRPropertyDialog.List.DefaultNull"), true));
		result.add(new ElementDescription("net.sf.jasperreports.export.pdf.tag.th", Messages.getString("JRPropertyDialog.List.Prop18") + Messages.getString("JRPropertyDialog.List.DefaultNull"), true));
		result.add(new ElementDescription("net.sf.jasperreports.export.pdf.tag.td", Messages.getString("JRPropertyDialog.List.Prop19") + Messages.getString("JRPropertyDialog.List.DefaultNull"), true));
		result.add(new ElementDescription("net.sf.jasperreports.export.pdf.tag.colspan", Messages.getString("JRPropertyDialog.List.Prop20") + Messages.getString("JRPropertyDialog.List.DefaultNull"), true));
		result.add(new ElementDescription("net.sf.jasperreports.export.pdf.tag.rowspan", Messages.getString("JRPropertyDialog.List.Prop21") + Messages.getString("JRPropertyDialog.List.DefaultNull"), true));
		result.add(new ElementDescription("net.sf.jasperreports.export.html.id", Messages.getString("JRPropertyDialog.List.html.id") + Messages.getString("JRPropertyDialog.List.html.id.value"), true));
		return result;
	}
	
	private static List<ElementDescription> addExporterHints()
	{
		List<ElementDescription> result = new ArrayList<ElementDescription>();
		result.add(new ElementDescription("net.sf.jasperreports.export.character.encoding", "Default: UTF-8",false));
		result.add(new ElementDescription("net.sf.jasperreports.export.graphics2d.min.job.size", Messages.getString("JRPropertyDialog.List.Prop5") + Messages.getString("JRPropertyDialog.List.DefaultTrue"), true));
		result.add(new ElementDescription("net.sf.jasperreports.export.html.frames.as.nested.tables", Messages.getString("JRPropertyDialog.List.Prop6") + Messages.getString("JRPropertyDialog.List.DefaultTrue"), true));
		result.add(new ElementDescription("net.sf.jasperreports.export.html.remove.empty.space.between.rows", Messages.getString("JRPropertyDialog.List.Prop6") + Messages.getString("JRPropertyDialog.List.DefaultFalse"), true));
		result.add(new ElementDescription("net.sf.jasperreports.export.html.white.page.background", Messages.getString("JRPropertyDialog.List.Prop6") + Messages.getString("JRPropertyDialog.List.DefaultTrue"), true));
		result.add(new ElementDescription("net.sf.jasperreports.export.html.wrap.break.word", Messages.getString("JRPropertyDialog.List.Prop6") + Messages.getString("JRPropertyDialog.List.DefaultFalse"), true));
		result.add(new ElementDescription("net.sf.jasperreports.export.html.size.unit", Messages.getString("JRPropertyDialog.List.Prop6") + Messages.getString("JRPropertyDialog.List.DefaultPx"), true));
		result.add(new ElementDescription("net.sf.jasperreports.export.html.using.images.to.align", Messages.getString("JRPropertyDialog.List.Prop6") + Messages.getString("JRPropertyDialog.List.DefaultTrue"), true));
		result.add(new ElementDescription("net.sf.jasperreports.export.pdf.force.svg.shapes", Messages.getString("JRPropertyDialog.List.Prop7") + Messages.getString("JRPropertyDialog.List.DefaultTrue"), true));
		result.add(new ElementDescription("net.sf.jasperreports.export.pdf.force.linebreak.policy", Messages.getString("JRPropertyDialog.List.Prop7") + Messages.getString("JRPropertyDialog.List.DefaultFalse"), true));
		result.add(new ElementDescription("net.sf.jasperreports.export.pdf.create.batch.mode.bookmarks", Messages.getString("JRPropertyDialog.List.Prop7") + Messages.getString("JRPropertyDialog.List.DefaultFalse"), true));
		result.add(new ElementDescription("net.sf.jasperreports.export.pdf.compressed", Messages.getString("JRPropertyDialog.List.Prop7") + Messages.getString("JRPropertyDialog.List.DefaultFalse"), true));
		result.add(new ElementDescription("net.sf.jasperreports.export.pdf.encrypted", Messages.getString("JRPropertyDialog.List.Prop7") + Messages.getString("JRPropertyDialog.List.DefaultFalse"), true));
		result.add(new ElementDescription("net.sf.jasperreports.export.pdf.128.bit.key", Messages.getString("JRPropertyDialog.List.Prop7") + Messages.getString("JRPropertyDialog.List.DefaultFalse"), true));
		result.add(new ElementDescription("net.sf.jasperreports.export.csv.field.delimiter", Messages.getString("JRPropertyDialog.List.Prop10") + Messages.getString("JRPropertyDialog.List.Default"), true));
		result.add(new ElementDescription("net.sf.jasperreports.export.csv.record.delimiter", Messages.getString("JRPropertyDialog.List.Prop10") + Messages.getString("JRPropertyDialog.List.Default1"), true));
		result.add(new ElementDescription("net.sf.jasperreports.export.pdf.tagged", Messages.getString("JRPropertyDialog.List.Prop12") + Messages.getString("JRPropertyDialog.List.DefaultFalse"), true));
		result.add(new ElementDescription("net.sf.jasperreports.export.pdf.tag.language", Messages.getString("JRPropertyDialog.List.Prop13") + Messages.getString("JRPropertyDialog.List.DefaultNull"), true));
		return result;    
	}
	
	private static List<ElementDescription> getGenericHints(){
		if (genericHints == null) genericHints = addHints();
		return genericHints;
	}
	
	private static List<ElementDescription> getExporterHints(){
		if (exporterHints == null) exporterHints = addExporterHints();
		return exporterHints;
	}

	private static List<ElementDescription> addHints(int scope)
	{
		List<ElementDescription> result = new ArrayList<ElementDescription>();
		if (scope == SCOPE_TEXT_ELEMENT) {
			 result.add(addHint("net.sf.jasperreports.export.xls.formula"));
	 	 	 result.add(addHint("net.sf.jasperreports.export.xls.pattern"));
		}
		
		if (scope == SCOPE_ELEMENT || scope == SCOPE_TEXT_ELEMENT) {
			result.add(addHint("net.sf.jasperreports.export.xls.freeze.row.edge"));
			result.add(addHint("net.sf.jasperreports.export.xls.auto.filter"));
			result.add(addHint("net.sf.jasperreports.export.xls.column.name"));
			result.add(addHint("net.sf.jasperreports.export.xls.data"));
			result.add(addHint("net.sf.jasperreports.export.xls.break.before.row"));
			result.add(addHint("net.sf.jasperreports.export.xls.break.after.row"));
			result.add(addHint("net.sf.jasperreports.export.xls.repeat.value"));
			result.add(addHint("net.sf.jasperreports.export.xls.sheet.name"));
			result.add(addHint("net.sf.jasperreports.export.xls.write.header"));
			result.add(addHint("net.sf.jasperreports.export.xls.column.width"));
			result.add(addHint("net.sf.jasperreports.export.xls.row.outline.level.{arbitrary_level}"));
		}
	
		if (scope == SCOPE_REPORT || scope== SCOPE_ELEMENT || scope == SCOPE_TEXT_ELEMENT) {
			result.add(addHint("net.sf.jasperreports.export.xls.column.width.ratio"));
			result.add(addHint("net.sf.jasperreports.export.xls.cell.hidden"));
			result.add(addHint("net.sf.jasperreports.export.xls.cell.locked"));
			result.add(addHint("net.sf.jasperreports.export.xls.wrap.text"));
			result.add(addHint("net.sf.jasperreports.export.xls.freeze.column.edge"));
			result.add(addHint("net.sf.jasperreports.export.xls.freeze.row.edge"));
			result.add(addHint("net.sf.jasperreports.export.flash.element.allow.script.access"));
		}
	
		if (scope == SCOPE_REPORT) {
			result.add(addHint("net.sf.jasperreports.export.xls.collapse.row.span"));
			result.add(addHint("net.sf.jasperreports.export.xls.create.custom.palette"));
			result.add(addHint("net.sf.jasperreports.export.xls.detect.cell.type"));
			result.add(addHint("net.sf.jasperreports.export.xls.fit.height"));
			result.add(addHint("net.sf.jasperreports.export.xls.fit.width"));
		 	result.add(addHint("net.sf.jasperreports.export.xls.font.size.fix.enabled"));
		 	result.add(addHint("net.sf.jasperreports.export.xls.ignore.cell.background"));
		 	result.add(addHint("net.sf.jasperreports.export.xls.ignore.cell.border"));
		 	result.add(addHint("net.sf.jasperreports.export.xls.ignore.graphics"));
		 	result.add(addHint("net.sf.jasperreports.export.xls.image.border.fix.enabled"));
		 	result.add(addHint("net.sf.jasperreports.export.xls.max.rows.per.sheet"));
		 	result.add(addHint("net.sf.jasperreports.export.xls.one.page.per.sheet"));
		 	result.add(addHint("net.sf.jasperreports.export.xls.remove.empty.space.between.columns"));
		 	result.add(addHint("net.sf.jasperreports.export.xls.remove.empty.space.between.rows"));
		 	result.add(addHint("net.sf.jasperreports.export.xls.white.page.background"));
		 	result.add(addHint("net.sf.jasperreports.export.xls.freeze.column"));
		 	result.add(addHint("net.sf.jasperreports.export.xls.freeze.row"));
		 	result.add(addHint("net.sf.jasperreports.export.xls.password"));
		 	result.add(addHint("net.sf.jasperreports.export.xls.sheet.direction"));
		 	result.add(addHint("net.sf.jasperreports.export.xls.sheet.footer.center"));
		 	result.add(addHint("net.sf.jasperreports.export.xls.sheet.footer.left"));
		 	result.add(addHint("net.sf.jasperreports.export.xls.sheet.footer.right"));
		 	result.add(addHint("net.sf.jasperreports.export.xls.sheet.header.center"));
		 	result.add(addHint("net.sf.jasperreports.export.xls.sheet.header.left"));
		 	result.add(addHint("net.sf.jasperreports.export.xls.sheet.header.right"));
		 	result.add(addHint("net.sf.jasperreports.export.xls.column.names"));
		 	result.add(addHint("net.sf.jasperreports.export.xls.sheet.names.{arbitrary_name}"));
		 	result.add(addHint("net.sf.jasperreports.virtual.page.element.size"));
		}
		return result;   
	}
	
	public static List<ElementDescription> getElementProperties(Object holder){
		List<ElementDescription> result = new ArrayList<ElementDescription>();
		result.addAll(getGenericHints());
		
    if (holder instanceof JasperDesign)
    {
    		result.addAll(getExporterHints());
        result.addAll(addHints(SCOPE_REPORT));
    }
    else if(holder instanceof JRTextField)
    {
    		result.addAll(addHints(SCOPE_TEXT_ELEMENT));
    }
    else if(holder instanceof JRElement)
    {
    		result.addAll(addHints(SCOPE_ELEMENT));
    }
    
    return result;
	}
	
	public static List<ElementDescription> getElementProperties(Class<?> holderType){
		List<ElementDescription> result = new ArrayList<ElementDescription>();
		result.addAll(getGenericHints());
		
    if (JasperDesign.class.isAssignableFrom(holderType))
    {
    		result.addAll(getExporterHints());
        result.addAll(addHints(SCOPE_REPORT));
    }
    else if(JRTextField.class.isAssignableFrom(holderType))
    {
    		result.addAll(addHints(SCOPE_TEXT_ELEMENT));
    }
    else if(JRElement.class.isAssignableFrom(holderType))
    {
    		result.addAll(addHints(SCOPE_ELEMENT));
    }
    
    return result;
	}

}
