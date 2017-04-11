package com.louis.fileGenerator.services;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import com.louis.fileGenerator.handler.Handler;
import com.louis.fileGenerator.handler.impl.XMLFileBodyGeneHandler;

public abstract class AbstractBasicService {

	@Value("${newFileslocalPath}")
	private String newFileslocalPath;

	@Resource(name = "fileNameGeneHandler")
	Handler fileNameGeneHandler;
	@Resource(name = "fileHeaderGeneHandler")
	Handler fileHeaderGeneHandler;
	@Resource(name = "fileBodyGeneHandler")
	Handler fileBodyGeneHandler;
	@Resource(name = "fileEndGeneHandler")
	Handler fileEndGeneHandler;

	private List<Map<String, String>> initialization(Map<String, String> variableMap) throws Exception {
		List<Map<String, String>> mapList = new ArrayList<>();
		int fileSum = 1;
		String fileSumString = "1";
		double fileDataSum = 1000000;
		String fileDataSumString = "001000000";
		double dataSum = 1000000;
		String dataSumString = "1000000";

		String settleDate = variableMap.get("settleDate");
		String busiLine = variableMap.get("busiLine");
		String province = variableMap.get("province");
		String dataTable = variableMap.get("dataTable");
		String fileTypeDesc = variableMap.get("fileTypeDesc");

		String fileNameTpl = variableMap.get("fileNameTpl");
		String fileHeaderTpl = variableMap.get("fileHeaderTpl");
		String fileBodyTpl = variableMap.get("fileBodyTpl");
		String fileEndTpl = variableMap.get("fileEndTpl");
		String fileBodyXmlTpl = variableMap.get("fileBodyXmlTpl");

		int TDay = 0;
		if (variableMap.get("TDay") != null && variableMap.get("TDay").length() == 8) {
			TDay = Integer.parseInt(variableMap.get("TDay"));
		} else {
			TDay = Integer.parseInt(settleDate) + 1;
		}

		if (variableMap.get("fileDataSum") != null && variableMap.get("fileDataSum").length() > 0) {
			fileDataSumString = String.format("%09d", Integer.parseInt(variableMap.get("fileDataSum")));
			fileDataSum = Integer.parseInt(fileDataSumString);
		}

		if (variableMap.get("dataSum") != null && variableMap.get("dataSum").length() > 0) {
			dataSumString = variableMap.get("dataSum");
			dataSum = Integer.parseInt(dataSumString);
		}

		if (!"auto".equals(variableMap.get("fileSum")) && variableMap.get("fileSum") != null
				&& variableMap.get("fileSum").length() > 0) {
			fileSumString = variableMap.get("fileSum");
			fileSum = Integer.parseInt(fileSumString);
		} else {
			double result = dataSum / fileDataSum;
			fileSum = (int) Math.ceil(result);
			fileSumString = String.format("%03d", fileSum);
		}

		int fileNo = 1;

		while (fileSum >= fileNo) {
			Map<String, String> vMap = new HashMap<>();
			vMap.put("settleDate", settleDate);
			vMap.put("busiLine", busiLine);
			vMap.put("province", province);

			vMap.put("fileSum", fileSumString);
			vMap.put("fileDataSum", fileDataSumString);
			vMap.put("dataSum", dataSumString);

			vMap.put("dataTable", dataTable);
			vMap.put("fileTypeDesc", fileTypeDesc);

			vMap.put("fileNameTpl", fileNameTpl);
			vMap.put("fileHeaderTpl", fileHeaderTpl);
			vMap.put("fileBodyTpl", fileBodyTpl);
			vMap.put("fileEndTpl", fileEndTpl);
			vMap.put("fileBodyXmlTpl", fileBodyXmlTpl);

			vMap.put("TDay", Integer.toString(TDay));

			String fileNoString = String.format("%03d", fileNo);
			vMap.put("fileNo", fileNoString);

			vMap.put("newFileslocalPath", newFileslocalPath);

			mapList.add(vMap);

			fileNo++;
		}

		return mapList;
	}

	public void fileGeneHandle(Map<String, String> variableMap) throws Exception {
		List<Map<String, String>> mapList = new ArrayList<>();

		mapList = initialization(variableMap);

		String fileTypeDesc = mapList.get(0).get("fileTypeDesc");
		String msg = "[开始]" + fileTypeDesc + "文件开始生成。。。";
		System.out.println(msg);

		try {
			mapList = fileNameGeneHandler.handle(mapList);
		} catch (Exception e) {
			msg = "[失败]" + fileTypeDesc + "文件名生成出错!";
			System.out.println(msg);

			mapList.clear();
			throw e;
		}

		try {
			fileHeaderGeneHandler.handle(mapList);
		} catch (Exception e) {
			msg = "[失败]" + fileTypeDesc + "文件头生成出错!";
			System.out.println(msg);

			mapList.clear();
			throw e;
		}

		if ("byDataBase".equals(variableMap.get("fileGeneMode"))) {
			try {
				fileBodyGeneHandler.handle(mapList);
			} catch (Exception e) {
				msg = "[失败]" + fileTypeDesc + "文件体生成出错!";
				System.out.println(msg);

				mapList.clear();
				throw e;
			}
		} else {
			try {
				XMLFileBodyGeneHandler xmlFileBodyGeneHandler = new XMLFileBodyGeneHandler();
				xmlFileBodyGeneHandler.handle(mapList);
			} catch (Exception e) {
				msg = "[失败]" + fileTypeDesc + "文件体生成出错!";
				System.out.println(msg);

				mapList.clear();
				throw e;
			}
		}

		try {
			fileEndGeneHandler.handle(mapList);
		} catch (Exception e) {
			msg = "[失败]" + fileTypeDesc + "文件尾生成出错!";
			System.out.println(msg);

			mapList.clear();
			throw e;
		} finally {
			mapList.clear();
		}

		msg = "[成功]" + fileTypeDesc + "文件生成全部成功!!!";
		System.out.println(msg);
	}

	private void fileGeneErrorHandle(List<Map<String, String>> mapList) {
		String msg = null;

		msg = mapList.get(0).get("fileTypeDesc") + "文件生成出错，删除所有相关错误文件，等待稍后重新生成。。。";
		System.out.println(msg);

		for (Map<String, String> map : mapList) {
			String filePath = map.get("filePath");
			File errorFile = new File(filePath);

			if (errorFile.exists()) {
				errorFile.delete();
				msg = filePath + "文件删除成功...";
				System.out.println(msg);
			}
		}
	}
}
