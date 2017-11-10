package com.louis.fileGenerator.config;

import java.io.InputStream;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * class for initialization of template configuration
 * 
 * @author louiszhang
 * @description
 * @time 2017年4月12日
 */
public class TemplateConfig {

	@XStreamImplicit(itemFieldName = "fileType")
	List<FileTypeCfg> fileTypeCfgs;

	public List<FileTypeCfg> getFileTypeCfgs() {
		return fileTypeCfgs;
	}

	public void setFileTypeCfgs(List<FileTypeCfg> fileTypeCfgs) {
		this.fileTypeCfgs = fileTypeCfgs;
	}

	@Override
	public String toString() {
		return "TemplateConfig [fileTypeCfgs=" + fileTypeCfgs + "]";
	}

	/**
	 * init method
	 * 
	 * @author louiszhang
	 * @time 20172017年4月12日上午11:19:35
	 */
	public void init() {

		try {
			XStream xs = new XStream();
			xs.setMode(XStream.NO_REFERENCES);
			xs.processAnnotations(new Class[] { TplConfigCfg.class, FileTypeCfg.class });

			System.out.println("开始解析服务配置文件【tplConfig.xml】");

			InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("tplConfig.xml");
			TplConfigCfg tplConfigCfg;
			tplConfigCfg = (TplConfigCfg) xs.fromXML(inputStream);

			System.out.println("服务配置文件【tplConfig.xml】解析成功！");

			this.setFileTypeCfgs(tplConfigCfg.getFileTypes());
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}
}
