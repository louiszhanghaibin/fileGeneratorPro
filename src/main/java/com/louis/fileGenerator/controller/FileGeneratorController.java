package com.louis.fileGenerator.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.alibaba.fastjson.JSON;
import com.cmsz.cmup.commons.utils.SpringUtil;
import com.louis.fileGenerator.services.BasicService;

@Controller
public class FileGeneratorController {

	@Resource(name = "springUtil")
	SpringUtil springUtil;

	@RequestMapping(value = "/startGeneFiles")
	public String startGeneFiles(@RequestParam(value = "fileGeneServiceId") String fileGeneServiceId,
			@RequestParam(value = "fileGeneMode") String fileGeneMode,
			@RequestParam(value = "settleDate", required = false) String settleDate,
			@RequestParam(value = "TDay", required = false) String TDay,
			@RequestParam(value = "province", required = false) String province,
			@RequestParam(value = "busiLine", required = false) String busiLine,
			@RequestParam(value = "dataSum", required = false) String dataSum,
			@RequestParam(value = "fileDataSum", required = false) String fileDataSum,
			@RequestParam(value = "fileSum", required = false) String fileSum, Model model) {

		// controller返回的页面路径
		String returnPage = "result.jsp";

		// 验证参数是否合法，不能含有特殊字符，不能有sql关键字
		if (!validateParameter(fileGeneServiceId) || !validateParameter(settleDate) || !validateParameter(TDay)
				|| !validateParameter(province) || !validateParameter(busiLine) || !validateParameter(dataSum)
				|| !validateParameter(fileDataSum) || !validateParameter(fileSum)) {
			String result = "启动流程失败，参数中不能含有特殊字符及sql关键字";

			// 返回的Json字符串
			model.addAttribute("result", JSON.toJSONString(result));

			return returnPage;
		}

		if (!("byXML".equals(fileGeneMode) || "byDataBase".equals(fileGeneMode))) {
			String result = "启动流程失败，参数fileGeneMode不能识别，模式或不存在，请重新选择";

			// 返回的Json字符串
			model.addAttribute("result", JSON.toJSONString(result));

			return returnPage;
		}

		if (!valiNumber(settleDate) || !valiNumber(TDay) || !valiNumber(province) || !valiNumber(busiLine)
				|| !valiNumber(dataSum) || !valiNumber(fileDataSum)
				|| ((!"auto".equals(fileSum)) && !valiNumber(fileSum))) {
			String result = "启动流程失败，关键参数必须为数字";

			// 返回的Json字符串
			model.addAttribute("result", JSON.toJSONString(result));

			return returnPage;
		}

		// 处理为null的参数
		settleDate = (settleDate == null) ? "" : settleDate;
		province = (province == null) ? "" : province;
		busiLine = (busiLine == null) ? "" : busiLine;
		dataSum = (dataSum == null) ? "" : dataSum;
		TDay = (TDay == null) ? "" : TDay;
		fileDataSum = (fileDataSum == null) ? "" : fileDataSum;
		fileSum = (fileSum == null) ? "" : fileSum;

		// 组装服务必须的参数Map
		Map<String, String> variableMap = new HashMap<>();
		variableMap.put("fileGeneMode", fileGeneMode);
		variableMap.put("settleDate", settleDate);
		variableMap.put("TDay", TDay);
		variableMap.put("province", province);
		variableMap.put("dataSum", dataSum);
		variableMap.put("busiLine", busiLine);
		variableMap.put("fileDataSum", fileDataSum);
		variableMap.put("fileSum", fileSum);

		// 启动服务
		BasicService basicService = null;
		String result = null;
		try {
			basicService = (BasicService) springUtil.getBean(fileGeneServiceId);
		} catch (Exception e1) {
			result = "[failed!!!!!!!!生成文件失败]" + "服务查找出错，或为不存在的服务！" + "异常信息为：" + e1.toString();
			e1.printStackTrace();

			// 返回的Json字符串
			model.addAttribute("result", JSON.toJSONString(result));

			// 跳转到执行结果页面
			return returnPage;
		}
		try {
			basicService.doService(variableMap);
		} catch (Exception e) {
			result = "[failed!!!!!!!!生成文件失败]" + "服务启动出错，请核实输入参数是否正确，相关数据表是否有数据！" + "异常信息为：" + e.toString();
			e.printStackTrace();

			// 返回的Json字符串
			model.addAttribute("result", JSON.toJSONString(result));

			// 跳转到执行结果页面
			return returnPage;
		}

		result = "[sucess!!!!!!!生成文件成功]";

		// 返回的Json字符串
		model.addAttribute("result", JSON.toJSONString(result));

		// 跳转到执行结果页面
		return returnPage;

	}

	/**
	 * 验证参数合法性
	 * 
	 * @param s
	 * @return
	 */
	private boolean validateParameter(String s) {
		if (StringUtils.isEmpty(s)) {
			return true;
		}
		if (!validateSpecialChars(s)) {
			return false;
		}
		if (!validateSqlKeyword(s)) {
			return false;
		} else {
			return true;
		}
	}

	/** 特殊字符正则表达式 **/
	private static Pattern specialCharsPattern = Pattern.compile("^[a-zA-Z0-9_.\\-\\(\\)\u4e00-\u9fa5]+$");
	/** sql关键字正则表达式 **/
	private static Pattern sqlPattern = Pattern.compile(
			"(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|(\\b(select|update|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)");

	/**
	 * 验证特殊字符,不含有特殊字符，则返回true
	 * 
	 * @param s
	 * @return
	 */
	private boolean validateSpecialChars(String s) {
		if (!specialCharsPattern.matcher(s).matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 验证sql关键字，不含有则返回true
	 * 
	 * @param s
	 * @return
	 */
	private boolean validateSqlKeyword(String s) {
		if (sqlPattern.matcher(s).find()) {
			return false;
		}
		return true;
	}

	private boolean valiNumber(String s) {
		if (!(s.matches("\\d{1,}"))) {
			return false;
		}
		return true;
	}
}
