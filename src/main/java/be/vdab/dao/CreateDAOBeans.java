package be.vdab.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@ComponentScan
public class CreateDAOBeans {

	@Autowired
	private DataSource dataSource;

	@Bean
	JdbcTemplate JdbcTemplate() {
		return new JdbcTemplate(dataSource);
	}

	@Bean
	NamedParameterJdbcTemplate namedJdbcTemplate() {
		return new NamedParameterJdbcTemplate(dataSource);
	}

	@Bean
	DataSourceTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource);
	}

}
