package com.example.devopsproject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import javax.sql.DataSource;

@Configuration
@Profile("integration-test")
public class DatabaseTestIntegration {

    @Primary
    @Bean
    public DataSource dataSource() {
        // Setup MySQL data source
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://mysqldb:3306/your_database");
        dataSource.setUsername("root");
        dataSource.setPassword("ayadinou");
        return dataSource;
    }
}
