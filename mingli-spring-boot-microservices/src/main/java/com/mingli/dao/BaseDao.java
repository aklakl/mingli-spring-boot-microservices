package com.mingli.dao;

import java.io.Serializable;

import java.util.List;


/**
 * 
 * @author MingLi
 * @Date: 11/27/2017
 * @name   
 * @desc 
 *
 */

public interface BaseDao<T> {

	Long insert(T entity);

	void update(T entity);

	void delete(Serializable id);

	void deleteAll();

	T getById(Serializable id);

	List<T> findAll();

}
