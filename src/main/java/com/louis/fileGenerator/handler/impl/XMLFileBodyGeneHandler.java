package com.louis.fileGenerator.handler.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.RandomStringUtils;
import com.louis.fileGenerator.handler.Handler;
import com.louis.fileGenerator.xmlTplFileGene.template.Component;
import com.louis.fileGenerator.xmlTplFileGene.template.FieldCfg;
import com.louis.fileGenerator.xmlTplFileGene.template.FilterCfg;
import com.louis.fileGenerator.xmlTplFileGene.template.Template;
import com.thoughtworks.xstream.XStream;

public class XMLFileBodyGeneHandler implements Handler {

	private FilterCfg fileBodyFilter;
	private String fieldValue = "";
	private String randomString = "";
	private String numOnlyString = "";
	private String lettetOnlyString = "";
	private String componetsString = "";
	private List<String> dataList = new ArrayList<>();

	private int uniqueInt = 1;

	@Override
	public List<Map<String, String>> handle(List<Map<String, String>> mapList) throws Exception {
		String msg = null;

		try {
			initTpl(mapList);
		} catch (Exception e) {
			throw e;
		}

		for (Map<String, String> vMap : mapList) {
			msg = vMap.get("fileTypeDesc") + "文件:" + vMap.get("filePath") + "文件体【开始】生成。。。";
			System.out.println(msg);

			try {
				fileBodyGeneHandle(vMap);
			} catch (Exception e) {
				throw e;
			}

			msg = vMap.get("fileTypeDesc") + "文件:" + vMap.get("filePath") + "文件体生成【成功】！";
			System.out.println(msg);
		}

		return null;
	}

	/**
	 * initialization XML templates
	 * 
	 * @param mapList
	 * @throws Exception
	 */
	private void initTpl(List<Map<String, String>> mapList) throws Exception {
		String fileBodyXmlTpl = mapList.get(0).get("fileBodyXmlTpl");

		Template template = null;
		try {
			XStream xs = new XStream();
			xs.setMode(XStream.NO_REFERENCES);
			xs.processAnnotations(new Class[] { Template.class, FilterCfg.class, FieldCfg.class });
			InputStream in = this.getClass().getClassLoader().getResourceAsStream(fileBodyXmlTpl);
			template = (Template) xs.fromXML(in);
		} catch (Exception e) {
			String msg = mapList.get(0).get("fileTypeDesc") + "文件：" + "初始化xml文件体生成模板出错。。。";
			System.out.println(msg);
			e.printStackTrace();
			throw e;
		}

		fileBodyFilter = template.getFilter();
	}

	/**
	 * generate file body data by XML templates
	 * 
	 * @param vMap
	 * @throws Exception
	 */
	private void fileBodyGeneHandle(Map<String, String> vMap) throws Exception {
		String msg = null;
		int fileDataSum = Integer.parseInt(vMap.get("fileDataSum"));

		try {
			int fileDataNo = 0;
			while (fileDataNo < fileDataSum) {
				String data = "";

				for (FieldCfg field : fileBodyFilter.getFields()) {

					// generate the data line
					try {
						data += getFieldValueString(field, fileDataNo, vMap);
					} catch (Exception e) {
						msg = vMap.get("filePath") + "文件xml文件模板生成文件体出错...";
						System.out.println(msg);
						e.printStackTrace();
					}

					if ("sep".equals(fileBodyFilter.getType()) && (!"yes".equals(field.getIsEnd()))) {
						data += fileBodyFilter.getSeperator();
					}
				}

				// put the appended data into the list to store
				dataList.add(data);

				// write the data into the forming file when there are 1000
				// pieces of data
				if (dataList.size() >= 10000) {
					fileDataWriteHandle(dataList, vMap);
				}

				fileDataNo++;
			}
			uniqueInt += fileDataSum;

			// call the function of writing data into files finally in case the
			// dataList still contains data line
			fileDataWriteHandle(dataList, vMap);
		} catch (Exception e) {
			msg = vMap.get("filePath") + "文件xml文件模板生成文件体出错...";
			System.out.println(msg);
			e.printStackTrace();
		} finally {
			dataList.clear();
		}

	}

	/**
	 * get the field data
	 * 
	 * @param field
	 * @param fileDataNo
	 * @param vMap
	 * @return
	 * @throws Exception
	 */
	private String getFieldValueString(FieldCfg field, int fileDataNo, Map<String, String> vMap) throws Exception {

		fieldValue = "";

		if (field.getFixValue() != null && field.getFixValue().length() > 0) {
			// when the field has a fixed value
			fieldValue += field.getFixValue();
			return fieldValue;
		} else if (field.getComponent() != null) {
			// when the field has specific components
			fieldValue += getComponetsString(field, fileDataNo, vMap);
			return fieldValue;
		} else if (field.getFromInput() != null && field.getFromInput().length() > 0) {
			// when the field value is from the input
			return vMap.get(field.getFromInput());
		}

		if ("yes".equals(field.getIsNumOnly())) {
			fieldValue = getNumOnlyString(field);
		} else if ("yes".equals(field.getIsLetterOnly())) {
			fieldValue += getLetterOnlyString(field);
		} else if ("yes".equals(field.getIsUnique())) {
			try {
				fieldValue += getUniqueString(field, fileDataNo);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				throw e;
			}
		} else {
			fieldValue += getRandomString(field);
		}

		// loop to call the function of making random string by itself in order
		// to make sure the result is correct

		if (checkFieldValue(field, fieldValue, fileDataNo)) {
			return fieldValue;
		}
		getFieldValueString(field, fileDataNo, vMap);

		return fieldValue;
	}

	/**
	 * get random string which contains both letters and number
	 * 
	 * @param field
	 * @return
	 */
	private String getRandomString(FieldCfg field) {
		int length = field.getLength();

		randomString = "";
		randomString = RandomStringUtils.randomAlphanumeric(length);

		return randomString;
	}

	/**
	 * get the unique String
	 * 
	 * @param field
	 * @param fileDataNo
	 * @return
	 * @throws ParseException
	 */
	private String getUniqueString(FieldCfg field, int fileDataNo) throws ParseException {
		int unique = fileDataNo + uniqueInt;
		String uniqueString = String.valueOf(unique);
		int uniqueStringLength = uniqueString.length();
		int length = field.getLength();

		if (length < uniqueStringLength) {
			uniqueString = uniqueString.substring((uniqueStringLength - length));
		} else if (length > uniqueStringLength) {
			String format = "%0" + Integer.toString(length) + "d";
			uniqueString = String.format(format, unique);
		}

		return uniqueString;
	}

	/**
	 * get random string which only contains number
	 * 
	 * @param field
	 * @return
	 */
	private String getNumOnlyString(FieldCfg field) {
		int length = field.getLength();

		numOnlyString = "";
		numOnlyString = RandomStringUtils.randomNumeric(length);

		return numOnlyString;
	}

	/**
	 * get random string which only contains letters
	 * 
	 * @param field
	 * @return
	 */
	private String getLetterOnlyString(FieldCfg field) {
		int length = field.getLength();

		lettetOnlyString = "";
		lettetOnlyString = RandomStringUtils.randomAlphabetic(length);

		return lettetOnlyString;
	}

	private boolean checkFieldValue(FieldCfg field, String fieldValue, int fileDataNo) {

		if (field.getFixValue() != null && field.getFixValue().length() > 0) {
			return true;
		}

		String regx = field.getRegx();
		if (!fieldValue.matches(regx)) {
			return false;
		}

		return true;
	}

	/**
	 * get fieldValue of the field with specific components
	 * 
	 * @param field
	 * @param fileDataNo
	 * @param vMap
	 * @return
	 * @throws Exception
	 */
	private String getComponetsString(FieldCfg field, int fileDataNo, Map<String, String> vMap) throws Exception {
		Component component = field.getComponent();
		List<FieldCfg> componentFields = component.getFields();

		componetsString = "";
		for (FieldCfg fieldCfg : componentFields) {
			try {
				componetsString += getFieldValueString(fieldCfg, fileDataNo, vMap);
			} catch (Exception e) {
				throw e;
			}
		}

		return componetsString;
	}

	/**
	 * write the data into the forming file
	 * 
	 * @param dataList
	 * @param vMap
	 * @throws Exception
	 */
	private void fileDataWriteHandle(List<String> dataList, Map<String, String> vMap) throws Exception {

		File newFile = new File(vMap.get("filePath"));
		PrintWriter printWriter = null;
		OutputStreamWriter osw = null;
		try {
			osw = new OutputStreamWriter(new FileOutputStream(newFile, true), "UTF-8");
			printWriter = new PrintWriter(osw);
		} catch (Exception e) {
			String msg = vMap.get("fileTypeDesc") + "文件输入流操作出错,文件体生成失败";
			System.out.println(msg);
			e.printStackTrace();
			throw e;
		}

		try {
			for (String dataString : dataList) {
				printWriter.print(dataString);
				printWriter.print("\r\n");
			}
		} catch (Exception e) {
			String msg = vMap.get("filePath") + "文件xml文件模板生成文件体出错,写入文件出现异常。。。";
			System.out.println(msg);
			e.printStackTrace();
			printWriter.close();
			throw e;
		} finally {
			printWriter.close();
			osw.close();
		}

		// clear the list to release the memory
		dataList.clear();
	}
}
