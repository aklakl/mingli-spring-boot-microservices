package com.example.demo.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;



@Service
public class DemoService {
	
	private static final Logger LOG = LoggerFactory.getLogger(DemoService.class);
	
	public Map<String, Object> testService(String param) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("key", "value");
		map.put("your parameters", param);
 		return map;
	}
	
	
	
	

 

}
