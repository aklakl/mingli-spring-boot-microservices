package com.mingli.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.mingli.dao.utils.DefaultNameHandler;
import com.mingli.dao.utils.DefaultRowMapper;
import com.mingli.dao.utils.NameHandler;
import com.mingli.dao.utils.Pager;
import com.mingli.dao.utils.PagingOrder;
import com.mingli.dao.utils.SqlContext;
import com.mingli.dao.utils.SqlUtils;

/**
 * Generic generic dao implementation depends on spring jdbc 
 * 
 * 
 * @author MingLi
 * @Date: 11/27/2017
 * @name   
 * @desc 
 *
 */
public abstract class BaseDaoImpl<T> implements BaseDao<T> { //abstract implements BaseDao<T> {
	
	private static final Logger LOG = LoggerFactory.getLogger(BaseDaoImpl.class);
	
	/** concrete operation of the entity class object */
	private Class<T> entityClass;
	
	/** Name Processors */
	private NameHandler nameHandler;
	
	/** spring jdbcTemplate object */
	@Autowired
	protected JdbcTemplate jdbcTemplate;

	/**
	 * * Constructor to get the concrete entity object at runtime
	 * 
	 */
	public BaseDaoImpl() {
		Type superclass = getClass().getGenericSuperclass();
		ParameterizedType type = (ParameterizedType) superclass;
		entityClass = (Class<T>) type.getActualTypeArguments()[0];
	}

	/**
	 * * Get the name of the actual runtime processor * * @return
	 * 
	 */
	private NameHandler getActualNameHandler() {
		if (nameHandler == null) {
			synchronized (this) {
				if (nameHandler == null) {
					nameHandler = this.getNameHandler();
				}
			}
		}
		return nameHandler;
	}

	/**
	 * * Get Name Processor, Subclass Override this method to implement your own
	 * name conversion processor * * @return
	 */
	protected NameHandler getNameHandler() {
		return new DefaultNameHandler();
	}

	/**
	 * * Insert a record * * @param entity
	 */
	public synchronized Long insert(T entity) {
		final SqlContext sqlContext = SqlUtils.buildInsertSql(entity, this.getActualNameHandler());
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(sqlContext.getSql().toString(),
						new String[] { sqlContext.getPrimaryKey() });
				int index = 0;
				for (Object param : sqlContext.getParams()) {
					index++;
					ps.setObject(index, param);
				}
				return ps;
			}
		}, keyHolder);
		return keyHolder.getKey().longValue();
	}

	/**
	 *      * update record           
	 *      * @param entity      
	 */
	public void update (T entity) {
		SqlContext sqlContext = SqlUtils.buildUpdateSql(entity, this.getActualNameHandler());
	   	jdbcTemplate.update(sqlContext.getSql().toString(), sqlContext.getParams().toArray());
    }

	/**
	 * * Delete Record * * @param id
	 */
	public void delete(Serializable id) {
		String tableName = this.getActualNameHandler().getTableName(entityClass.getName());
		String primaryName = this.getNameHandler().getPrimaryName(entityClass.getName());
		String sql = "DELETE FROM " + tableName + " WHERE " + primaryName + " =? ";
		jdbcTemplate.update(sql, id);
	}

	/**
	 * * Delete all records
	 */
	//@Override
	public void deleteAll() {
		String tableName = this.getActualNameHandler().getTableName(entityClass.getName());
		String sql = "TRUNCATE TABLE " + tableName;
		jdbcTemplate.execute(sql);
	}

	/**
	 * * Get recorded * * @param id * @return
	 */
	//@Override
	public T getById(Serializable id) {
		String tableName = this.getNameHandler().getTableName(entityClass.getName());
		String primaryName = this.getNameHandler().getPrimaryName(entityClass.getSimpleName());
		String sql = "SELECT * FROM " + tableName + " WHERE " + primaryName + "=?";
		return (T) jdbcTemplate.query(sql, new DefaultRowMapper(entityClass, this.getActualNameHandler()), id).get(0);
	}

	/**
	 * * Query all records * * @return
	 */
	//@Override
	public List<T> findAll() {
		String sql = "SELECT * FROM " + this.getActualNameHandler().getTableName(entityClass.getName());
		return (List<T>) jdbcTemplate.query(sql, new DefaultRowMapper(entityClass, this.getActualNameHandler()));
	}

	/**
	 * * Query the number of records * * @param entity * @return
	 * 
	 */
	public int queryCount(T entity) {
		String tableName = this.getActualNameHandler().getTableName(entityClass.getName());
		StringBuilder countSql = new StringBuilder("select count (*) as totalcount from ");
		countSql.append(tableName);
		SqlContext sqlContext = SqlUtils.buildQueryCondition(entity, this.getActualNameHandler());
		if (sqlContext.getSql().length() > 0) {
			countSql.append(" where ");
			countSql.append(sqlContext.getSql());
		}
		//queryForObject
		Map mapTotalCount = jdbcTemplate.queryForMap(countSql.toString(), sqlContext.getParams().toArray());
		Object totalcount = mapTotalCount.get("totalcount");
		return Integer.parseInt(totalcount.toString());
	}

	/**
	 * * Query paging list * * @param entity * @return
	 */
	public Pager queryPageList(T entity) {
		Pager pager = new Pager();
		PagingOrder pagingOrder = (PagingOrder) entity;
		pager.setCurPage(pagingOrder.getCurPage());
		pager.setItemsPerPage(pagingOrder.getItemsPerPage());

		String tableName = this.getActualNameHandler().getTableName(entityClass.getName());
		String primaryName = this.getActualNameHandler().getPrimaryName(entityClass.getName());
		StringBuilder querySql = new StringBuilder("select * from ");
		StringBuilder countSql = new StringBuilder("select count (*) from ");
		querySql.append(tableName);
		countSql.append(tableName);
		// Do not call the queryCount method, the conditions of a common assembly to
		// reduce the number of reflections to obtain
		SqlContext sqlContext = SqlUtils.buildQueryCondition(entity, this.getActualNameHandler());
		if (sqlContext.getSql().length() > 0) {
			querySql.append(" where ");
			countSql.append(" where ");
			querySql.append(sqlContext.getSql());
			countSql.append(sqlContext.getSql());
		}
		querySql.append(" order by ");
		querySql.append(primaryName);
		querySql.append(" desc ");
		querySql.append(" limit?,?");
		List<Object> queryParams = new ArrayList<Object>(sqlContext.getParams());
		queryParams.add(pager.getBeginIndex());
		queryParams.add(pager.getItemsPerPage());

		List<T> list = (List<T>) jdbcTemplate.query(querySql.toString(), queryParams.toArray(),
				new DefaultRowMapper(entityClass, this.getActualNameHandler()));

		Map mapTotalCount = jdbcTemplate.queryForMap(countSql.toString(), sqlContext.getParams().toArray());
		int totalCount = Integer.parseInt(mapTotalCount.get("totalcount").toString()) ;
		pager.setList(list);
		pager.setItems(totalCount);
		return pager;
	}
}