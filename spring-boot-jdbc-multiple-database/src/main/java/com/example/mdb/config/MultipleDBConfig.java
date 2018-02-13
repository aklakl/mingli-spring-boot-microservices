package com.example.mdb.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class MultipleDBConfig {
	@Bean(name = "mysqlDb")
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource_mingli1")
	public DataSource mysqlDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "mysqlJdbcTemplate")
	public JdbcTemplate jdbcTemplate(@Qualifier("mysqlDb") DataSource dsMySQL) {
		return new JdbcTemplate(dsMySQL);
	}
	
	@Bean(name = "postgresDb")
	@ConfigurationProperties(prefix = "spring.datasource_mingli2")
	public DataSource postgresDataSource() {
		return  DataSourceBuilder.create().build();
	}

	@Bean(name = "postgresJdbcTemplate")
	public JdbcTemplate postgresJdbcTemplate(@Qualifier("postgresDb") 
                                              DataSource dsPostgres) {
		return new JdbcTemplate(dsPostgres);
	}
}
/*public class MultipleDBConfig {
	@Bean(name = "mingli1DB")
	@ConfigurationProperties(prefix = "spring.datasource_mingli1")
	public DataSource mysqlDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "JdbcTemplateMingli1")
	public JdbcTemplate jdbcTemplate(@Qualifier("mingli1DB") DataSource dsMySQL) {
		return new JdbcTemplate(dsMySQL);
	}
	
	@Bean(name = "mingli2DB")
	@ConfigurationProperties(prefix = "spring.datasource_mingli2")
	public DataSource postgresDataSource() {
		return  DataSourceBuilder.create().build();
	}

	@Bean(name = "JdbcTemplateMingli2")
	public JdbcTemplate postgresJdbcTemplate(@Qualifier("mingli2DB")  DataSource dsPostgres) {
		return new JdbcTemplate(dsPostgres);
	}
}*/
