package com.mingli.dao.utils;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * sql SqlUtils
 * 
 * User: Ming Li
 * Date: 11/27/2017
 * 
 */
public class SqlUtils {
     
    private static final Logger LOG = LoggerFactory.getLogger(SqlUtils.class);
    /**
     * build insert sql
     *
     * @param entity ORM
     * @param nameHandler name convert Handler
     * @return
     */
    public static SqlContext buildInsertSql(Object entity, NameHandler nameHandler) {
        Class<?> clazz = entity.getClass();
        String tableName = nameHandler.getTableName(clazz.getName());
        String primaryName = nameHandler.getPrimaryName(clazz.getName());
        StringBuilder sql = new StringBuilder("insert into ");
        List<Object> params = new ArrayList<Object>();
        sql.append(tableName);
        BeanInfo beanInfo = ClassUtils.getSelfBeanInfo(clazz);
        PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
        sql.append("(");
        StringBuilder args = new StringBuilder();
        args.append("(");
        for (PropertyDescriptor pd : pds) {
            Object value = getReadMethodValue(pd.getReadMethod(), entity);
            if (value == null) {
                continue;
            }
            sql.append(nameHandler.getColumnName(pd.getName()));
            args.append("?");
            params.add(value);
            sql.append(",");
            args.append(",");
        }
        sql.deleteCharAt(sql.length() - 1);
        args.deleteCharAt(args.length() - 1);
        args.append(")");
        sql.append(")");
        sql.append(" values ");
        sql.append(args);
        return new SqlContext(sql, primaryName, params);
    }
    /**
     * build update sql
     * 
     * @param entity
     * @param nameHandler
     * @return
     */
    public static SqlContext buildUpdateSql(Object entity, NameHandler nameHandler) {
        Class<?> clazz = entity.getClass();
        StringBuilder sql = new StringBuilder();
        List<Object> params = new ArrayList<Object>();
        String tableName = nameHandler.getTableName(clazz.getName());
        String primaryName = nameHandler.getPrimaryName(clazz.getName());
        BeanInfo beanInfo = ClassUtils.getSelfBeanInfo(clazz);
        PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
        sql.append("update ");
        sql.append(tableName);
        sql.append(" set ");
        Object primaryValue = null;
        for (PropertyDescriptor pd : pds) {
            Object value = getReadMethodValue(pd.getReadMethod(), entity);
            if (value == null) {
                continue;
            }
            String columnName = nameHandler.getColumnName(pd.getName());
            if (primaryName.equalsIgnoreCase(columnName)) {
                primaryValue = value;
            }
            sql.append(columnName);
            sql.append(" = ");
            sql.append("?");
            params.add(value);
            sql.append(",");
        }
        sql.deleteCharAt(sql.length() - 1);
        sql.append(" where ");
        sql.append(primaryName);
        sql.append(" = ?");
        params.add(primaryValue);
        return new SqlContext(sql, primaryName, params);
    }
    /**
     * build query sql
     * 
     * @param entity
     * @param nameHandler
     */
    public static SqlContext buildQueryCondition(Object entity, NameHandler nameHandler) {
        BeanInfo beanInfo = ClassUtils.getSelfBeanInfo(entity.getClass());
        //PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(entityClass);
        PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
        StringBuilder condition = new StringBuilder();
        List<Object> params = new ArrayList<Object>();
        int count = 0;
        for (PropertyDescriptor pd : pds) {
            Object value = getReadMethodValue(pd.getReadMethod(), entity);
            if (value == null) {
                continue;
            }
            if (count > 0) {
                condition.append(" and ");
            }
            condition.append(nameHandler.getColumnName(pd.getName()));
            condition.append(" = ?");
            params.add(value);
            count++;
        }
        return new SqlContext(condition, null, params);
    }
    /**
     * get the information of the method
     *
     * @param readMethod
     * @param entity
     * @return
     */
    private static Object getReadMethodValue(Method readMethod, Object entity) {
        Object obj = null;
    	if (readMethod == null) {
            return obj;
        }
        try {
            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                readMethod.setAccessible(true);
            }
            obj  = readMethod.invoke(entity);
        } catch (Exception e) {
            LOG.error("get property failed", e);
            //throw new MincoderException(e);
        }
		return obj;
    }
}
