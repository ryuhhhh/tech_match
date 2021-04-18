package com.techmatch.base;

import com.zaxxer.hikari.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

  @Value("${spring.datasource.url}")
  private String dbUrl;

  @Bean
  public DataSource dataSource() {
      HikariConfig config = new HikariConfig();
      config.setJdbcUrl(dbUrl);
      config.setPassword("YOUR_PASSWORD");
      config.setUsername("YOUR_NAME");
      config.setDriverClassName("org.postgresql.Driver");
      return new HikariDataSource(config);
  }
}