package com.mingli.dao.utils;

/**
 *  
 * 
 * User: Ming Li
 * Date: 11/27/2017
 * 
 */
public interface NameHandler {
     /**
      * Get the table name based on the entity name
      *
      * @param entityName
      * @return
      */
     public String getTableName (String entityName);
     /**
      * According to the table name to get the primary key name
      *
      * @param entityName
      * @return
      */
     public String getPrimaryName (String entityName);
     /**
      * According to the property name to get the column name
      *
      * @param fieldName
      * @return
      */
     public String getColumnName (String fieldName);
}