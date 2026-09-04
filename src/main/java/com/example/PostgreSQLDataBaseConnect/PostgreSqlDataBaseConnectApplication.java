package com.example.PostgreSQLDataBaseConnect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.jdbc.core.JdbcTemplate;
import lombok.extern.java.Log;

// import javax.sql.DataSource;
import java.util.TimeZone;

@SpringBootApplication
@Log
public class PostgreSqlDataBaseConnectApplication {

	// public final DataSource dataSource;

	// public PostgreSqlDataBaseConnectApplication(final DataSource dataSource) {
	// 	this.dataSource = dataSource;
	// }

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata"));
		SpringApplication.run(PostgreSqlDataBaseConnectApplication.class, args);
	}

	// @Override
	// public void run(final String... args) {
	// 	log.info("DataSource: " + dataSource.toString());
	// 	final JdbcTemplate restTemplate = new JdbcTemplate(dataSource);
	// 	restTemplate.execute("select 1");
	// }

}
