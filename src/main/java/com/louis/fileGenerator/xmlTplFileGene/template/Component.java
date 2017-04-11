package com.louis.fileGenerator.xmlTplFileGene.template;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("component")
public class Component {

	public List<FieldCfg> fields;

	public List<FieldCfg> getFields() {
		return fields;
	}

	public void setFields(List<FieldCfg> fields) {
		this.fields = fields;
	}

}
