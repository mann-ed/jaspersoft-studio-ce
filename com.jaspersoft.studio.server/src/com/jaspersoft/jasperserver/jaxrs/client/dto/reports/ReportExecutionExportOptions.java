/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/

package com.jaspersoft.jasperserver.jaxrs.client.dto.reports;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "options")
public class ReportExecutionExportOptions {

	private String outputFormat;
	private String attachmentsPrefix;
	private Boolean allowInlineScripts;

	public Boolean getAllowInlineScripts() {
		return allowInlineScripts;
	}

	public void setAllowInlineScripts(Boolean allowInlineScripts) {
		this.allowInlineScripts = allowInlineScripts;
	}

	public String getOutputFormat() {
		return outputFormat;
	}

	public void setOutputFormat(String contentType) {
		this.outputFormat = contentType;
	}

	public String getAttachmentsPrefix() {
		return attachmentsPrefix;
	}

	public void setAttachmentsPrefix(String fileName) {
		this.attachmentsPrefix = fileName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		ReportExecutionExportOptions that = (ReportExecutionExportOptions) o;

		if (outputFormat != null ? !outputFormat.equals(that.outputFormat) : that.outputFormat != null)
			return false;
		if (attachmentsPrefix != null ? !attachmentsPrefix.equals(that.attachmentsPrefix) : that.attachmentsPrefix != null)
			return false;
		if (allowInlineScripts != null ? !allowInlineScripts.equals(that.allowInlineScripts) : that.allowInlineScripts != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = outputFormat != null ? outputFormat.hashCode() : 0;
		result = 31 * result + (attachmentsPrefix != null ? attachmentsPrefix.hashCode() : 0);
		result = 31 * result + (allowInlineScripts != null ? allowInlineScripts.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "ReportExecutionExportOptions{" + "outputFormat='" + outputFormat + '\'' + ", attachmentsPrefix='" + attachmentsPrefix + '\'' + ", allowInlineScripts='" + allowInlineScripts + '\'' + '}';
	}
}
