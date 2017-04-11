package com.louis.fileGenerator.services;

import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("mGZXJFMGFileGeneService")
public class MGZXJFMGFileGeneService extends AbstractBasicService implements BasicService {

	@Value("${mgZXJFMGfileNameTpl}")
	String fileNameTpl;
	@Value("${37fileHeaderTpl}")
	String fileHeaderTpl;
	@Value("${mgSettlefileBodyTpl}")
	String fileBodyTpl;
	@Value("${mgZXJFMGFileBodyXmlTpl}")
	String fileBodyXmlTpl;
	@Value("${37fileEndTpl}")
	String fileEndTpl;

	@Override
	public Map<String, String> doService(Map<String, String> vMap) throws Exception {
		String msg = null;

		vMap.put("fileTypeDesc", "咪咕在线计费咪咕侧对账");
		vMap.put("dataTable", "ZXJF_MIGU_DATA");
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
