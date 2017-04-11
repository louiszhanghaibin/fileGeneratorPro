package com.louis.fileGenerator.services;

import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("mGUpayFileGeneService")
public class MGUpayFileGeneService extends AbstractBasicService implements BasicService {

	@Value("${mgUpayfileNameTpl}")
	String fileNameTpl;
	@Value("${33fileHeaderTpl}")
	String fileHeaderTpl;
	@Value("${mgUpayfileBodyTpl}")
	String fileBodyTpl;
	@Value("${mgUpayFileBodyXmlTpl}")
	String fileBodyXmlTpl;

	String fileEndTpl = "none";

	@Override
	public Map<String, String> doService(Map<String, String> vMap) throws Exception {
		String msg = null;

		vMap.put("fileTypeDesc", "咪咕统一支付日全量");
		vMap.put("dataTable", "BOSS_DATA");
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
