package com.louis.fileGenerator.xmlTplFileGene.template;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("field")
public class FieldCfg {
	public String name;
	public String regx;
	public String fixValue;
	public int length;
	public String isNumOnly;
	public String isLetterOnly;
	public String isUnique;
	public String isEnd;
	public String fromInput;

	public Component component;

	public String getFromInput() {
		return fromInput;
	}

	public void setFromInput(String fromInput) {
		this.fromInput = fromInput;
	}

	public Component getComponent() {
		return component;
	}

	public void setComponent(Component component) {
		this.component = component;
	}

	public String getIsEnd() {
		return isEnd;
	}

	public void setIsEnd(String isEnd) {
		this.isEnd = isEnd;
	}

	public String getIsUnique() {
		return isUnique;
	}

	public void setIsUnique(String isUnique) {
		this.isUnique = isUnique;
	}

	public String getIsNumOnly() {
		return isNumOnly;
	}

	public void setIsNumOnly(String isNumOnly) {
		this.isNumOnly = isNumOnly;
	}

	public String getIsLetterOnly() {
		return isLetterOnly;
	}

	public void setIsLetterOnly(String isLetterOnly) {
		this.isLetterOnly = isLetterOnly;
	}

	public String getFixValue() {
		return fixValue;
	}

	public void setFixValue(String fixValue) {
		this.fixValue = fixValue;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRegx() {
		return regx;
	}

	public void setRegx(String regx) {
		this.regx = regx;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}
}
