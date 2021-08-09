package com.techmatch.base;

import com.zaxxer.hikari.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

  @Value("${spring.datasource.url}")
  private String dbUrl;

  @Value("${spring.datasource.username}")
  private String userName;

  @Value("${spring.datasource.password}")
  private String dbPass;
  
  
  @Bean
  public DataSource dataSource() {
	  
      HikariConfig config = new HikariConfig();
      config.setJdbcUrl(dbUrl);
      config.setPassword(dbPass);
      config.setUsername(userName);
      config.setDriverClassName("org.postgresql.Driver");
      return new HikariDataSource(config);
  }
}