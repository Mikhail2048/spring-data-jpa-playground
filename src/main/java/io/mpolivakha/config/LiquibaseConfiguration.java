package io.mpolivakha.config;

import javax.sql.DataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LiquibaseConfiguration {

  @Bean
  public SpringLiquibase liquibase(DataSource dataSource) {
    SpringLiquibase springLiquibase = new SpringLiquibase();
    springLiquibase.setChangeLog("classpath:migrations/master.xml");
    springLiquibase.setDataSource(dataSource);
    return springLiquibase;
  }
}
