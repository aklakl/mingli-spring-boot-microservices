package com.mingli.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.mingli.model.CommonRowMapper;
import com.mingli.model.EventHistory;

/**
 * 
 * @author MingLi
 * @Date: 11/27/2017
 * @name   
 * @desc 
 *
 */

@Repository
public class EventHistoryDao extends BaseDaoImpl<EventHistory> {

	
	public List<Object> getData() {
		//event_history
		List<Object> pd = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer("select * from event_history where 1=1");
		//sb.append(b);
		
		try{
			pd = jdbcTemplate.query(sb.toString(), new CommonRowMapper(EventHistory.class));
		} catch (DataAccessException sa) {
			sa.printStackTrace();
			throw new RuntimeException(sa);
		}		
		return pd; 
	}
	
	public List<Object> getDataCount() {
		//event_history
		List<Object> pd = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer("select * from event_history where 1=1");
		
		/**
int	getFetchSize()
Return the fetch size specified for this JdbcTemplate.
int	getMaxRows()
Return the maximum number of rows specified for this JdbcTemplate.
		 */
		try{
			pd = jdbcTemplate.query(sb.toString(), new CommonRowMapper(EventHistory.class));
			int fetchSize = jdbcTemplate.getFetchSize();
			int maxRows = jdbcTemplate.getMaxRows();
			System.out.println("fetchSize:"+fetchSize);
			System.out.println("maxRows:"+maxRows);
		} catch (DataAccessException sa) {
			sa.printStackTrace();
			throw new RuntimeException(sa);
		}		
		return pd; 
		 
	}
	
	
	
	
//		
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//
//	}

}
