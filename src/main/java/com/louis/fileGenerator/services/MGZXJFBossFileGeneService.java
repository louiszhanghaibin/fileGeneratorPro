package com.louis.fileGenerator.services;

import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("mGZXJFBossFileGeneService")
public class MGZXJFBossFileGeneService extends AbstractBasicService implements BasicService {

	@Value("${mgZXJFBossfileNameTpl}")
	String fileNameTpl;
	@Value("${33fileHeaderTpl}")
	String fileHeaderTpl;
	@Value("${mgZXJFBossfileBodyTpl}")
	String fileBodyTpl;
	@Value("${mgZXJFBossFileBodyXmlTpl}")
	String fileBodyXmlTpl;

	String fileEndTpl = "none";

	@Override
	public Map<String, String> doService(Map<String, String> vMap) throws Exception {
		String msg = null;

		vMap.put("fileTypeDesc", "咪咕在线计费省侧对账");
		vMap.put("dataTable", "ZXJF_BOSS_DATA");
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
