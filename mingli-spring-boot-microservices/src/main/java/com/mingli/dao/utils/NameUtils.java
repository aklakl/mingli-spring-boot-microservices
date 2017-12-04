package com.mingli.dao.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.mingli.model.Table;

/**
 * @author:Ming li
 * @desc:NameUtils for invoke the tablename via Annotation Property
 * 
 */

public class NameUtils {

	
	public static String getTableName(String entityClassName) {
		Class onwClass = null;
		String tn ="";
		try {
			onwClass = Class.forName(entityClassName);
			tn = getTableName(onwClass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return tn;
	}
	
	public static String getTableName(Class entityClass ){
		return getAnnotationValueFromProperty(entityClass,"name").toString();
	}
	
	public static Object getAnnotationValueFromProperty(String entityClassName,String field ){
		Class onwClass = null;
		Object obj ="";
		try {
			onwClass = Class.forName(entityClassName);
			obj = getAnnotationValueFromProperty(onwClass,field);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	/**
	 * get AnnotationValue
	 * @param entityClass
	 * @param field
	 * @return
	 */
	
	public static Object getAnnotationValueFromProperty(Class entityClass,String field ){
		//get class  of the specified type of annotation
		Object result = null;
		Table anno = (Table) entityClass.getAnnotation(Table.class);
	    if(anno != null){
	        Method[] met = anno.annotationType().getDeclaredMethods();  
	        for(Method me : met ){
	            if(!me.isAccessible()){  
	                me.setAccessible(true);  
	            }
                try {
                	System.out.println("class annotation:");
                	System.out.print("me.getName()::"+me.getName());
					System.out.println("|value::"+me.invoke(anno, null));
                	if (me.getName().equals(field)) {
                		result = me.invoke(anno, null);
                		break;
                	}
				} catch (InvocationTargetException e) {
					e.printStackTrace();
	            } catch (IllegalAccessException e) {  
	                e.printStackTrace();  
	            } catch (IllegalArgumentException e) {  
	                e.printStackTrace();  
	            }  
	        }  
	    }
		return result;
	}
	
	
//	public static void main(String[] args) {
//		String tablename = getTableName(EventHistory.class.getName());
//		System.out.println("tablename:"+tablename);
//	}
	
	
	
}
