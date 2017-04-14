package com.louis.fileGenerator.test;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import com.louis.fileGenerator.services.BasicService;

public class FileGeneratorTest extends BaseJunit {

	@Resource(name = "fileGeneratorService")
	BasicService fileGeneratorService;

	@Test
	public void test() throws Exception {
		Map<String, String> vMap = new HashMap<>();
		vMap.put("id", "MGAlipayFile");

		try {
			fileGeneratorService.doService(vMap);
		} catch (Exception e) {
			throw e;
		}

		System.out.println("great work!!!");
	}

}
