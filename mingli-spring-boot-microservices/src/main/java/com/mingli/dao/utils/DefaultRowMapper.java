package com.mingli.dao.utils;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;


/**
 * 
 * @author MingLi
 * @Date: 11/27/2017
 * @name   
 * @desc 
 *
 */  
public class DefaultRowMapper<T> implements RowMapper<T> {  
  
	public static final Logger LOG = LoggerFactory.getLogger(DefaultRowMapper.class);
	
    /**   */  
    private Class<?>    clazz;  
  
    /**   */  
    private NameHandler nameHandler;  
  
    public DefaultRowMapper(Class<?> clazz, NameHandler nameHandler) {  
        this.clazz = clazz;  
        this.nameHandler = nameHandler;  
    }  
  
    //@Override  
    public T mapRow(ResultSet resultSet, int i) throws SQLException {  
        Object entity = null;
        BeanInfo beanInfo = null;
		try {
			entity = ClassUtils.newInstance(this.clazz);
			beanInfo = ClassUtils.getSelfBeanInfo(this.clazz);  
		} catch (Exception e1) {
			e1.printStackTrace();
		}  
        
        PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();  
        for (PropertyDescriptor pd : pds) {  
            String column = nameHandler.getColumnName(pd.getName());  
            Method writeMethod = pd.getWriteMethod();  
            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {  
                writeMethod.setAccessible(true);  
            }  
            try {  
            	Object value = resultSet.getObject(column);
            	LOG.debug("column:"+column+"|value:"+value+"|type:"+value.getClass().getSimpleName()); 
            	//warning:this must be make the entity's property's match same type，here should be fix in the futher using typeMapper functionality
                writeMethod.invoke(entity, value);
            } catch (Exception e) {  
            	//throw new MincoderException(e);
            	LOG.error(e.getLocalizedMessage());
            }  
        }  
        return (T) entity;  
    }
    
//    public Object mapRow(ResultSet rs, int i) throws SQLException {
//
//		try {
//			Field[] fields = cl.getDeclaredFields();
//			Object entity = cl.newInstance();
//			for (Field f : fields) {
//				f.setAccessible(true);
//				this.typeMapper(f, entity, rs);
//				f.setAccessible(false);
//			}
//			return entity;
//		} catch (Exception e) {
//			log.error("Exception:"+e.getLocalizedMessage());
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	private void typeMapper(Field field, Object obj, ResultSet rs) throws Exception {
//		String type = field.getType().getName();
//		if (type.equals("java.lang.String")) {
//			field.set(obj, rs.getString(field.getName()));
//		} else if (type.equals("int") || type.equals("java.lang.Integer")) {
//			field.set(obj, rs.getInt(field.getName()));
//		} else if (type.equals("long") || type.equals("java.lang.Long")) {
//			field.set(obj, rs.getLong(field.getName()));
//		} else if (type.equals("boolean") || type.equals("java.lang.Boolean")) {
//			field.set(obj, rs.getBoolean(field.getName()));
//		} else if (type.equals("java.util.Date")) {
//			field.set(obj, rs.getDate(field.getName()));
//		}
//	}
    
    
    
    
}  
