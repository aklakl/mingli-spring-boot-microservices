package com.mingli.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mingli.model.EventHistory;
import com.mingli.service.MingLiService;

/**
 * 
 * @author MingLi
 * @Date: 11/27/2017
 * @name   
 * @desc 
 *
 */

@RestController
@RequestMapping("/")
public class MingLiController {
	
	private static final Logger LOG = LoggerFactory.getLogger(MingLiController.class);
	
	@Autowired
	private MingLiService mingLiService;
 
	@RequestMapping(value = "/default", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public String defaultMethod() {
		return "defaultMethod";
	}
	
	@RequestMapping(value = "/mingliTestController/{param}", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public String mingliTestController(@PathVariable String param) {
		return mingLiService.test(param);
	}
	
//	@RequestMapping(value = "/mingliTestControllerByJson/{param}", method = {RequestMethod.GET, RequestMethod.POST})
//	@ResponseBody
//	public  List<MingLiModel> mingliTestControllerByJson(@PathVariable String param) {
//		return mingLiService.getMingLiModelList(null);
//	}
	
	@RequestMapping(value = "/mingliTestfromDBViaJson/{param}", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public  List<Object> mingliTestfromDBViaJson(@PathVariable String param) {
		return mingLiService.getEventHistoryModelList(null);
	}
	
	
	/** 
     *  
     * @api {get,post} /overview/{param}  
     * @apiName getAdvisorDashboardSummaryFromRiaid
     * @apiGroup RobustWealth 
     * @apiVersion 0.1.0 
     * @apiDescription get Advisor Dashboard Summary via the parameter riaid  Business Description:this particular query gets all the basic overview/summary for an advisor when an advisor logs in to the dashboard 
     *  
     *  
     * @apiSuccess {int} ria_id   
     * @apiSuccess {BigDecimal} ria_aum   
     * @apiSuccess {BigDecimal} ria_robo_on_aum   
     * @apiSuccess {BigDecimal} client_avg   
     * @apiSuccess {BigDecimal} account_avg  
     * @apiSuccess {long} number_of_accounts  
     * @apiSuccess {long} households_counts 
     * @apiSuccess {long} households_count 
     * @apiSuccess {long} client_count 
     * @apiSuccessExample Success-Response: 
     *  HTTP/1.1 200 OK 
     * [{
	    "ria_id": 64,
	    "ria_aum": 54661780,
	    "ria_robo_on_aum": 17265540,
	    "client_avg": 926470.847458,
	    "account_avg": 691921.265823,
	    "number_of_accounts": 79,
	    "households_counts": 68,
	    "households_count": 63,
	    "client_count": 181
		}] 
     *   
     * @apiError All  <code>id</code>failed not find  
     * @apiErrorExample {json} Error-Response: 
     *  HTTP/1.1 404 Not Found 
     *  { 
     *   code:1, 
     *   msg:'user not found', 
     *   } 
     *    
     * @param param 
     * @return 
     * @throws Exception 
     */ 
	@RequestMapping(value = "/getEventHistoryModelListByCommonDaoImpl/{param}", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public  List<EventHistory> getEventHistoryModelListByCommonDaoImpl(@PathVariable String param) {
		mingLiService.saveEventHistoryModelListByCommonDaoImpl(null);
		return mingLiService.getEventHistoryModelListByCommonDaoImpl(null);
	}
	
	
	
	@RequestMapping(value = "/test",  method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public String test() {
		return "testMethod";
	}
	
/*
	 @RequestMapping(method=RequestMethod.GET)
    public @ResponseBody Greeting sayHello(@RequestParam(value="name", required=false, defaultValue="Stranger") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }
*/
 

}
