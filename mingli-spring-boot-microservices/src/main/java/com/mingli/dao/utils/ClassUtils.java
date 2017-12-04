package com.mingli.dao.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ClassUtils
 *
 * User: Ming Li
 * Date: 11/27/2017
 * 
 */
public class ClassUtils {
	 
	private static final Logger LOG = LoggerFactory.getLogger(ClassUtils.class);
	/**
	 * Map keyed by class containing CachedIntrospectionResults. Needs to be a
	 * WeakHashMap with WeakReferences as values to allow for proper garbage
	 * collection in case of multiple class loaders.
	 */
	private static final Map<Class, BeanInfo> classCache = Collections
			.synchronizedMap(new WeakHashMap<Class, BeanInfo>());

	/**
	 * getSelfBeanInfo，not include the super class's property
	 * 
	 * @param clazz
	 * @return
	 * @throws Exception 
	 */
	public static BeanInfo getSelfBeanInfo(Class<?> clazz){
		BeanInfo beanInfo = null;
		try {
			if (classCache.get(clazz) == null) {
				beanInfo = Introspector.getBeanInfo(clazz, clazz.getSuperclass());
				classCache.put(clazz, beanInfo);
				// Immediately remove class from Introspector cache, to allow for proper
				// garbage collection on class loader shutdown - we cache it here anyway,
				// in a GC-friendly manner. In contrast to CachedIntrospectionResults,
				// Introspector does not use WeakReferences as values of its WeakHashMap!
				Class classToFlush = clazz;
				do {
					Introspector.flushFromCaches(classToFlush);
					classToFlush = classToFlush.getSuperclass();
				} while (classToFlush != null);
			} else {
				beanInfo = classCache.get(clazz);
			}
			return beanInfo;
		} catch (IntrospectionException e) {
			LOG.error("get BeanInfo failed", e);
			//throw new Exception(e);
		}
		return beanInfo;
	}

	/**
	 * newInstance
	 * 
	 * @param clazz
	 * @return
	 * @throws Exception 
	 */
	public static Object newInstance(Class<?> clazz) throws Exception {
		try {
			return clazz.newInstance();
		} catch (Exception e) {
			LOG.error("newInstance class failed", e);
			throw new Exception(e);
		}
	}
}
