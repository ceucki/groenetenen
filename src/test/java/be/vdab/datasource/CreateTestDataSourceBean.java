package be.vdab.datasource;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class CreateTestDataSourceBean {

	@Bean
	DataSource dataSource() {
		return new DriverManagerDataSource("jdbc:mysql://localhost/groenetenen", "cursist", "cursist");
	}

}
