package com.mingli.model;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;



/**
 * @author:Ming li
 * @desc:common for dao access
 * 
 */

public class CommonRowMapper implements RowMapper<Object> {
 

	private Logger log = LoggerFactory.getLogger(CommonRowMapper.class);
    
	private Class<?> cl;

	public CommonRowMapper(Class<?> cl) {
			this.cl = cl;
		}

	public Object mapRow(ResultSet rs, int i) throws SQLException {

		try {
			Field[] fields = cl.getDeclaredFields();
			Object entity = cl.newInstance();
			for (Field f : fields) {
				f.setAccessible(true);
				this.typeMapper(f, entity, rs);
				f.setAccessible(false);
			}
			return entity;
		} catch (Exception e) {
			log.error("Exception:"+e.getLocalizedMessage());
			e.printStackTrace();
		}
		return null;
	}

	private void typeMapper(Field field, Object obj, ResultSet rs) throws Exception {
		String type = field.getType().getName();
		if (type.equals("java.lang.String")) {
			field.set(obj, rs.getString(field.getName()));
		} else if (type.equals("int") || type.equals("java.lang.Integer")) {
			field.set(obj, rs.getInt(field.getName()));
		} else if (type.equals("long") || type.equals("java.lang.Long")) {
			field.set(obj, rs.getLong(field.getName()));
		} else if (type.equals("boolean") || type.equals("java.lang.Boolean")) {
			field.set(obj, rs.getBoolean(field.getName()));
		} else if (type.equals("java.util.Date")) {
			field.set(obj, rs.getDate(field.getName()));
		}
	}
	
//	public List<?> getData(String sql) {
//		//event_history
//		List<?> pd = new ArrayList<Object>();
//		StringBuffer sb = new StringBuffer(sql);
//		//sb.append(b);
//		
//		try{
//			pd = jdbcTemplate.query(sb.toString(), new CommonDao());
//		} catch (DataAccessException sa) {
//			sa.printStackTrace();
//			throw new RuntimeException(sa);
//		}		
//		return pd; 
//	}
	
}
