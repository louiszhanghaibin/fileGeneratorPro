package com.louis.fileGenerator.config;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * entity for template configuration XML node "TplConfig"
 * 
 * @author louiszhang
 * @description
 * @time 2017年4月12日
 */
@XStreamAlias("tplConfig")
public class TplConfigCfg {

	@XStreamImplicit(itemFieldName = "fileType")
	public List<FileTypeCfg> fileTypes;

	public List<FileTypeCfg> getFileTypes() {
		return fileTypes;
	}

	public void setFileTypes(List<FileTypeCfg> fileTypes) {
		this.fileTypes = fileTypes;
	}

}
