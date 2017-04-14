package com.louis.fileGenerator.services;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.louis.fileGenerator.config.FileTypeCfg;
import com.louis.fileGenerator.config.TemplateConfig;

@Component("fileGeneratorService")
public class FileGeneratorService extends AbstractBasicService implements BasicService {

	@Resource(name = "templateConfig")
	TemplateConfig templateConfig;

	@Override
	public Map<String, String> doService(Map<String, String> vMap) throws Exception {
		String id = "";
		id = vMap.get("id");

		List<FileTypeCfg> fileTypeCfgs = templateConfig.getFileTypeCfgs();
		int count = 0;
		for (FileTypeCfg fileTypeCfg : fileTypeCfgs) {
			if (id == fileTypeCfg.getId()) {
				vMap.put("fileTypeDesc", fileTypeCfg.getFileTypeDesc());
				vMap.put("dataTable", fileTypeCfg.getDataTable());
				vMap.put("fileNameTpl", fileTypeCfg.getFileNameTpl());
				vMap.put("fileHeaderTpl", fileTypeCfg.getFileHeaderTpl());
				vMap.put("fileBodyTpl", fileTypeCfg.getFileBodyTpl());
				vMap.put("fileEndTpl", fileTypeCfg.getFileEndTpl());
				vMap.put("fileBodyXmlTpl", fileTypeCfg.getFileBodyXmlTpl());
				vMap.put("busiLine", fileTypeCfg.getBusiLine());
				count++;
			}
		}
		if (count > 1) {
			String mString = "id 为" + id + "的文件配置存在多个，id重复，请人工核查。";
			System.out.println(mString);
			vMap.put("result", "failed");
			vMap.put("resultDesc", mString);
			return vMap;
		} else if (count == 0) {
			String mString = "id 为" + id + "的文件配置不存在，请人工核查。";
			System.out.println(mString);
			vMap.put("result", "failed");
			vMap.put("resultDesc", mString);
			return vMap;
		}

		try {
			super.fileGeneHandle(vMap);
		} catch (Exception e) {
			String msg = vMap.get("fileTypeDesc") + "文件生成出错，请稍后重试。。。";
			System.out.println(msg);
			throw e;
		}

		return null;
	}

}
