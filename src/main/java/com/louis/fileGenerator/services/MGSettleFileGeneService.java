package com.louis.fileGenerator.services;

import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("mGSettleFileGeneService")
public class MGSettleFileGeneService extends AbstractBasicService implements BasicService {

	@Value("${mgSettlefileNameTpl}")
	String fileNameTpl;
	@Value("${37fileHeaderTpl}")
	String fileHeaderTpl;
	@Value("${mgSettlefileBodyTpl}")
	String fileBodyTpl;
	@Value("${mgSettleFileBodyXmlTpl}")
	String fileBodyXmlTpl;
	@Value("${37fileEndTpl}")
	String fileEndTpl;

	@Override
	public Map<String, String> doService(Map<String, String> vMap) throws Exception {
		String msg = null;

		vMap.put("fileTypeDesc", "咪咕日全量");
		vMap.put("dataTable", "MIGU_DATA");
		vMap.put("fileNameTpl", fileNameTpl);
		vMap.put("fileHeaderTpl", fileHeaderTpl);
		vMap.put("fileBodyTpl", fileBodyTpl);
		vMap.put("fileEndTpl", fileEndTpl);
		vMap.put("fileBodyXmlTpl", fileBodyXmlTpl);

		try {
			super.fileGeneHandle(vMap);
		} catch (Exception e) {
			msg = vMap.get("fileTypeDesc") + "文件生成出错，请稍后重试。。。";
			System.out.println(msg);
			throw e;
		}
		return null;
	}
}
