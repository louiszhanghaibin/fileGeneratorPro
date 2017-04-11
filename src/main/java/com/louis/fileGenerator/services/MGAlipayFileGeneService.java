package com.louis.fileGenerator.services;

import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("mGAlipayFileGeneService")
public class MGAlipayFileGeneService extends AbstractBasicService implements BasicService {

	@Value("${mgAlipayfileNameTpl}")
	String fileNameTpl;
	@Value("${alipayFileHeaderTpl}")
	String fileHeaderTpl;
	@Value("${mgAlipayfileBodyTpl}")
	String fileBodyTpl;
	@Value("${mgAlipayFileBodyXmlTpl}")
	String fileBodyXmlTpl;

	String fileEndTpl = "none";

	@Override
	public Map<String, String> doService(Map<String, String> vMap) throws Exception {
		String msg = null;

		vMap.put("fileTypeDesc", "咪咕alipay");
		vMap.put("dataTable", "THIRDPAY_DATA");
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
