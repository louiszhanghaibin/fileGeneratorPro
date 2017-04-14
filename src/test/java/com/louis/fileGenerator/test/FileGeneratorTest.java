package com.louis.fileGenerator.test;

import org.junit.Test;

import com.louis.fileGenerator.config.TemplateConfig;

public class FileGeneratorTest extends BaseJunit {

	@Test
	public void test() {
		TemplateConfig templateConfig = new TemplateConfig();
		templateConfig.init();

		System.out.println("great work!!!");
	}

}
