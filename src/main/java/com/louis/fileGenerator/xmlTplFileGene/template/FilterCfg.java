package com.louis.fileGenerator.xmlTplFileGene.template;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("filter")
public class FilterCfg {

	public String type;
	public String startWith;
	public int fixRowNum;
	public String seperator;
	public String matcher;
	public int fixLength;

	public List<FieldCfg> fields;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStartWith() {
		return startWith;
	}

	public void setStartWith(String startWith) {
		this.startWith = startWith;
	}

	public int getFixRowNum() {
		return fixRowNum;
	}

	public void setFixRowNum(int fixRowNum) {
		this.fixRowNum = fixRowNum;
	}

	public String getSeperator() {
		return seperator;
	}

	public void setSeperator(String seperator) {
		this.seperator = seperator;
	}

	public int getFixLength() {
		return fixLength;
	}

	public void setFixLength(int fixLength) {
		this.fixLength = fixLength;
	}

	public List<FieldCfg> getFields() {
		return fields;
	}

	public void setFields(List<FieldCfg> fields) {
		this.fields = fields;
	}

	public String getMatcher() {
		return matcher;
	}

	public void setMatcher(String matcher) {
		this.matcher = matcher;
	}
}
