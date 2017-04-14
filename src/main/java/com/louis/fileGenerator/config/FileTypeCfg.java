package com.louis.fileGenerator.config;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * entity for template configuration XML node "FileType"
 * 
 * @author louiszhang
 * @description
 * @time 2017年4月12日
 */

@XStreamAlias("fileType")
public class FileTypeCfg {

	private String id = null;
	private String fileTypeDesc = null;
	private String fileNameTpl = null;
	private String fileHeaderTpl = null;
	private String fileBodyTpl = null;
	private String fileBodyXmlTpl = null;
	private String fileEndTpl = null;
	private String dataTable = null;
	private String busiLine = null;

	public String getBusiLine() {
		return busiLine;
	}

	public void setBusiLine(String busiLine) {
		this.busiLine = busiLine;
	}

	public String getDataTable() {
		return dataTable;
	}

	public void setDataTable(String dataTable) {
		this.dataTable = dataTable;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileTypeDesc() {
		return fileTypeDesc;
	}

	public void setFileTypeDesc(String fileTypeDesc) {
		this.fileTypeDesc = fileTypeDesc;
	}

	public String getFileNameTpl() {
		return fileNameTpl;
	}

	public void setFileNameTpl(String fileNameTpl) {
		this.fileNameTpl = fileNameTpl;
	}

	public String getFileHeaderTpl() {
		return fileHeaderTpl;
	}

	public void setFileHeaderTpl(String fileHeaderTpl) {
		this.fileHeaderTpl = fileHeaderTpl;
	}

	public String getFileBodyTpl() {
		return fileBodyTpl;
	}

	public void setFileBodyTpl(String fileBodyTpl) {
		this.fileBodyTpl = fileBodyTpl;
	}

	public String getFileBodyXmlTpl() {
		return fileBodyXmlTpl;
	}

	public void setFileBodyXmlTpl(String fileBodyXmlTpl) {
		this.fileBodyXmlTpl = fileBodyXmlTpl;
	}

	public String getFileEndTpl() {
		return fileEndTpl;
	}

	public void setFileEndTpl(String fileEndTpl) {
		this.fileEndTpl = fileEndTpl;
	}

}
