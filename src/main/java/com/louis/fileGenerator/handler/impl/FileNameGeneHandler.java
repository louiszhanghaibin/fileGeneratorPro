package com.louis.fileGenerator.handler.impl;

import java.io.File;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.stereotype.Component;

import com.louis.fileGenerator.handler.Handler;

@Component("fileNameGeneHandler")
public class FileNameGeneHandler implements Handler {
	private VelocityEngine ve = null;
	private int veloInitFlag = 0;

	@Override
	public List<Map<String, String>> handle(List<Map<String, String>> mapList) throws Exception {
		String msg = null;
		if (veloInitFlag != 1) {
			try {
				veloInit();
			} catch (VelocityException e) {
				e.printStackTrace();
				throw e;
			}
		}

		for (Map<String, String> vMap : mapList) {
			msg = vMap.get("fileTypeDesc") + "文件名开始生成";
			System.out.println(msg);

			Template template = ve.getTemplate(vMap.get("fileNameTpl"));
			VelocityContext context = new VelocityContext();

			context.put("fileNo", vMap.get("fileNo"));
			context.put("dataSum", vMap.get("dataSum"));
			context.put("fileSum", vMap.get("fileSum"));
			context.put("TDay", vMap.get("TDay"));
			context.put("settleDate", vMap.get("settleDate"));
			context.put("busiLine", vMap.get("busiLine"));
			context.put("province", vMap.get("province"));

			String fileName = null;
			try {
				fileName = getFileName(vMap, template, context);
			} catch (Exception e) {
				msg = vMap.get("fileTypeDesc") + "文件模板生成文件名出错";
				System.out.println(msg);
				e.printStackTrace();
				throw e;
			}

			vMap.put("fileName", fileName);
			String filePath = vMap.get("newFileslocalPath") + File.separator + fileName;
			vMap.put("filePath", filePath);

			msg = vMap.get("fileTypeDesc") + "文件:" + vMap.get("filePath") + "文件名生成成功";
			System.out.println(msg);
		}

		return mapList;
	}

	private String getFileName(Map<String, String> vMap, Template template, VelocityContext context) {

		StringWriter stringWriter = new StringWriter();
		template.merge(context, stringWriter);

		return stringWriter.toString();
	}

	private void veloInit() throws VelocityException {
		VelocityEngine vEngine = new VelocityEngine();
		vEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		vEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		vEngine.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.SimpleLog4JLogSystem");
		vEngine.setProperty("runtime.log.logsystem.log4j.category", "velocity");
		vEngine.setProperty("runtime.log.logsystem.log4j.logger", "velocity");
		vEngine.setProperty("runtime.log.error.stacktrace", false);
		vEngine.setProperty("runtime.log.warn.stacktrace", false);
		vEngine.setProperty("runtime.log.info.stacktrace", false);
		vEngine.init();

		this.veloInitFlag = 1;
		this.ve = vEngine;
	}
}
