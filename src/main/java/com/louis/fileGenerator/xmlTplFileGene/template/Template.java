package com.louis.fileGenerator.xmlTplFileGene.template;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("template")
public class Template {

	private FilterCfg filter;

	public FilterCfg getFilter() {
		return filter;
	}

	public void setFilter(FilterCfg filter) {
		this.filter = filter;
	}

}
