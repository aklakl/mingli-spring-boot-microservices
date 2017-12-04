package com.mingli.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mingli.dao.BaseDaoImpl;
import com.mingli.dao.EventHistoryDao;
import com.mingli.model.EventHistory;

@Service
public class MingLiService {
	
	private static final Logger LOG = LoggerFactory.getLogger(MingLiService.class);
	
	public static final String welcomeWord = "This is Ming Li's microservices!";
	public static final String mentionWord = "your paramter is:";

	
	@Autowired
	private EventHistoryDao eventHistoryDao;
	
//	@Autowired
//	private BaseDaoImpl<EventHistoryModel> baseDaoImpl;  //<EventHistoryModel>
//	
	
	public String test(String param) {
		StringBuffer  sb = new StringBuffer(welcomeWord);
		sb.append("");
		if (!param.isEmpty()) {
			sb.append(mentionWord);
			sb.append(param);
		}
		sb.append("\n");
		return sb.toString();
	}
	
	
//	public List<MingLiModel> getMingLiModelList(HashMap filterMap) {
//		List<MingLiModel> mingLiModelList = new ArrayList<MingLiModel>();
//		for(int i = 0 ;i<100;i++) {
//			MingLiModel mingLiModel = new MingLiModel();
//			mingLiModel.setAccount_id(i);
//			mingLiModel.setAccount_name("account_name"+i);
//			mingLiModelList.add(mingLiModel);	
//		}
//		return mingLiModelList;
//	}
	
	public List<Object> getEventHistoryModelList(HashMap filterMap) {
		List<Object> EventHistoryModelList = eventHistoryDao.getDataCount();
		//sb.append("eventHistoryDao.getData():"+EventHistoryModelList.size());
		System.out.println("EventHistoryModelList.size(): "+EventHistoryModelList.size());
		return EventHistoryModelList;
	}
	
	public List<EventHistory> getEventHistoryModelListByCommonDaoImpl(HashMap filterMap) {
		List<EventHistory> eventHistoryModelList = new ArrayList<EventHistory>();
		eventHistoryModelList = eventHistoryDao.findAll();
		//sb.append("eventHistoryDao.getData():"+EventHistoryModelList.size());
		System.out.println("eventHistoryModelList.size(): "+eventHistoryModelList.size());
		return eventHistoryModelList;
	}
	
	public List<EventHistory> saveEventHistoryModelListByCommonDaoImpl(HashMap filterMap) {
		List<EventHistory> eventHistoryModelList = new ArrayList<EventHistory>();
		EventHistory entity = null; 
		for(int i =0 ; i<10; i++) {
			entity = new EventHistory();
			entity.setDb_user("user"+i);
			entity.setEvent_description("event_description"+i);
			entity.setEvent_name("event_name"+i);
			eventHistoryDao.insert(entity);
		}
		System.out.println("saveEventHistoryModelListByCommonDaoImpl");
		return eventHistoryModelList;
	}
	
	

 

}
