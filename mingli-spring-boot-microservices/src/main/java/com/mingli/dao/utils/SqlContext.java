package com.mingli.dao.utils;

import java.util.List;

/**
 * execute Sql Context
 * 
 * User: Ming Li
 * Date: 11/27/2017
 * 
 */
public class SqlContext {
	
	/** sql string  */ 
    private StringBuilder sql;
    /** primaryKey  */
    private String primaryKey;
    /** parameter，equals sql ?  */
    
    private List<Object>  params;
    public SqlContext(StringBuilder sql, String primaryKey, List<Object> params) {
        this.sql = sql;
        this.primaryKey = primaryKey;
        this.params = params;
    }
	public StringBuilder getSql() {
		return sql;
	}
	public void setSql(StringBuilder sql) {
		this.sql = sql;
	}
	public String getPrimaryKey() {
		return primaryKey;
	}
	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}
	public List<Object> getParams() {
		return params;
	}
	public void setParams(List<Object> params) {
		this.params = params;
	}
    
    
}
