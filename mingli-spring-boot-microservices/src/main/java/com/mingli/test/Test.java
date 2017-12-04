package com.mingli.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.xml.bind.annotation.XmlRootElement;

import com.mingli.model.EventHistory;
import com.mingli.model.Table;

public class Test {


	public static void test() {
		//get class  of the specified type of annotation
				Table anno = EventHistory.class.getAnnotation(Table.class);  
			    if(anno != null){
			        Method[] met = anno.annotationType().getDeclaredMethods();  
			        for(Method me : met ){  
			            if(!me.isAccessible()){  
			                me.setAccessible(true);  
			            }  
			            try {  
			                try {
								System.out.println("class annotation:"+me.invoke(anno, null));
							} catch (InvocationTargetException e) {
								e.printStackTrace();
							}  
			            } catch (IllegalAccessException e) {  
			                e.printStackTrace();  
			            } catch (IllegalArgumentException e) {  
			                e.printStackTrace();  
			            }  
			        }  
			    }
	}
	
//	public static void main(String[] args) {
//		test();
//	} 
	
}
