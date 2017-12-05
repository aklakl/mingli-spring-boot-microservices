package com.example.demo.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.DemoService;


/**
 * 
 * @author MingLi
 * @Date: 12/05/2017
 * @name   
 * @desc 
 *
 */

@RestController
@RequestMapping("/")
public class DemoController {
	
	private static final Logger LOG = LoggerFactory.getLogger(DemoController.class);
	
	@Autowired
	private DemoService demoService;
 
	@RequestMapping(value = "/default", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public String defaultMethod() {
		return "defaultMethod";
	}
	
	@RequestMapping(value = "/testcemocontroller/{param}", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public Map<String, Object> testDemoController(@PathVariable String param) {
		return demoService.testService(param);
	}
 
 
 

}
