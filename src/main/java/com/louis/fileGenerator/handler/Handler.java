package com.louis.fileGenerator.handler;

import java.util.List;
import java.util.Map;

public interface Handler {

	public List<Map<String, String>> handle(List<Map<String, String>> mapList) throws Exception;
}
