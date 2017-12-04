package com.mingli.dao.utils;


/**
 * The default name handler
 *
 * User: Ming Li
 * Date: 11/27/2017
 * 
 */
public class DefaultNameHandler implements NameHandler {
	
    /** field prefix */
    private static final String PREFIX = "_";
    
    /** primary key suffix */
    private static final String PRI_SUFFIX = "_id";
    /**
     * Get the table name based on the entity name
     *
     * @param entityName
     * @return
     */
    //@Override
    public String getTableName (String entityName) {
        // Camel naming of Java properties is converted back to the database underlined "_" delimited format
        return NameUtils.getTableName(entityName);
    }
    /**
     * According to the table name to get the primary key name
     *
     * @param entityName
     * @return
     */
    //@Override
    public String getPrimaryName (String entityName) {
        //As mentioned earlier, the database column name unified with "_", the primary key to the table name with "_id" If the user table primary key that is "_user_id"
        //return PREFIX + underlineName + PRI_SUFFIX;
        return NameUtils.getAnnotationValueFromProperty(entityName, "primaryKey").toString();
    }
    /**
     * According to the property name to get the column name
     *
     * @param fieldName
     * @return
     */
    //@Override
    public String getColumnName (String fieldName) {
        //String underlineName = NameUtils.getTableName(fieldName);//need modify
        // database column name unified beginning with "_"
        return fieldName;
    }
}
