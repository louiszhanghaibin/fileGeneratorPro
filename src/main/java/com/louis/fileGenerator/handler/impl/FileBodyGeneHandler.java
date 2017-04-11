package com.louis.fileGenerator.handler.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.stereotype.Component;
import com.louis.fileGenerator.dao.FileDataDAO;
import com.louis.fileGenerator.handler.Handler;

@Component("fileBodyGeneHandler")
public class FileBodyGeneHandler implements Handler {

	@Resource
	FileDataDAO fileDataDAO;

	private int veloInitFlag = 0;
	private VelocityEngine ve = null;

	@Override
	public List<Map<String, String>> handle(List<Map<String, String>> mapList) throws Exception {
		String msg = null;
		List<Map<String, String>> dataResultMapList = new ArrayList<>();

		if (veloInitFlag != 1) {
			try {
				veloInit();
			} catch (VelocityException e) {
				e.printStackTrace();
				throw e;
			}
		}

		try {
			dataResultMapList = fileDataDAO.selectStoredFileData(mapList.get(0));
		} catch (Exception e) {
			// TODO: handle exception
			msg = mapList.get(0).get("fileTypeDesc") + "文件：" + "数据库表" + mapList.get(0).get("dataTable") + "读取数据失败";
			System.out.println(msg);
			e.printStackTrace();
			throw e;
		}

		for (Map<String, String> vMap : mapList) {
			msg = vMap.get("fileTypeDesc") + "文件:" + vMap.get("filePath") + "文件体开始生成";
			System.out.println(msg);

			int fileDataSum = Integer.parseInt(vMap.get("fileDataSum"));

			File newFile = new File(vMap.get("filePath"));
			PrintWriter printWriter = null;
			try {
				OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(newFile, true), "UTF-8");
				printWriter = new PrintWriter(osw);
			} catch (Exception e) {
				msg = vMap.get("fileTypeDesc") + "文件输入流操作出错,文件体生成失败";
				System.out.println(msg);
				e.printStackTrace();
				throw e;
			}

			try {
				Template template = ve.getTemplate(vMap.get("fileBodyTpl"));
				int dataNo = 0;
				while (dataNo < fileDataSum && (!(dataResultMapList.isEmpty()))) {
					Map<String, String> dataMap = dataResultMapList.get(dataNo);

					VelocityContext context = new VelocityContext();
					context.put("dataMap", dataMap);
					String fileBody = null;
					try {
						fileBody = getFileBody(vMap, template, context);
					} catch (Exception e) {
						msg = vMap.get("filePath") + "文件velocity文件模板生成文件体出错";
						System.out.println(msg);
						e.printStackTrace();
						printWriter.close();
						throw e;
					}

					try {
						printWriter.print(fileBody);
						printWriter.print("\r\n");
					} catch (Exception e) {
						msg = vMap.get("filePath") + "文件velocity文件模板生成文件体出错";
						System.out.println(msg);
						e.printStackTrace();
						printWriter.close();
						throw e;
					}

					dataResultMapList.remove(dataNo);
					dataNo++;
				}
			} catch (Exception e) {
				msg = vMap.get("filePath") + "文件velocity文件模板生成文件体出错";
				System.out.println(msg);
				e.printStackTrace();
				printWriter.close();
				throw e;
			} finally {
				printWriter.close();
			}

			msg = vMap.get("fileTypeDesc") + "文件:" + vMap.get("filePath") + "文件体生成成功";
			System.out.println(msg);
		}
		return null;
	}

	private String getFileBody(Map<String, String> vMap, Template template, VelocityContext context) {
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
