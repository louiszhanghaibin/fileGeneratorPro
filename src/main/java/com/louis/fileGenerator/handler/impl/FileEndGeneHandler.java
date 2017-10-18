package com.louis.fileGenerator.handler.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
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

@Component("fileEndGeneHandler")
public class FileEndGeneHandler implements Handler {
	private VelocityEngine ve = null;
	private int veloInitFlag = 0;

	@Override
	public List<Map<String, String>> handle(List<Map<String, String>> mapList) throws Exception {
		String msg = null;

		if ("none".equals(mapList.get(0).get("fileEndTpl"))) {
			msg = mapList.get(0).get("fileTypeDesc") + "文件无文件尾，直接返回。。。";
			return null;
		}

		if (veloInitFlag != 1) {
			try {
				veloInit();
			} catch (VelocityException e) {
				e.printStackTrace();
				throw e;
			}
		}

		for (Map<String, String> vMap : mapList) {
			msg = vMap.get("fileTypeDesc") + "文件:" + vMap.get("filePath") + "文件尾开始生成";
			System.out.println(msg);

			Template template = ve.getTemplate(vMap.get("fileEndTpl"));
			VelocityContext context = new VelocityContext();
			context.put("fileNo", vMap.get("fileNo"));
			context.put("fileDataSum", vMap.get("fileDataSum"));
			context.put("fileSum", vMap.get("fileSum"));
			context.put("TDay", vMap.get("TDay"));
			context.put("busiLine", vMap.get("busiLine"));
			context.put("merchantCode", vMap.get("merchantCode"));
			String fileEnd = null;
			try {
				fileEnd = getFileEnd(vMap, template, context);
			} catch (Exception e) {
				msg = vMap.get("fileTypeDesc") + "文件模板生成文件尾出错";
				System.out.println(msg);
				e.printStackTrace();
				throw e;
			}

			File newFile = new File(vMap.get("filePath"));
			PrintWriter printWriter = null;
			try {
				OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(newFile, true), "UTF-8");
				printWriter = new PrintWriter(osw);
				printWriter.print(fileEnd);
				printWriter.print("\r\n");
			} catch (Exception e) {
				msg = vMap.get("fileTypeDesc") + "文件输入流操作出错,文件尾生成失败";
				System.out.println(msg);
				e.printStackTrace();
				throw e;
			} finally {
				printWriter.close();
			}

			msg = vMap.get("fileTypeDesc") + "文件:" + vMap.get("filePath") + "文件尾生成成功";
			System.out.println(msg);
		}

		return null;
	}

	private String getFileEnd(Map<String, String> vMap, Template template, VelocityContext context) {

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
