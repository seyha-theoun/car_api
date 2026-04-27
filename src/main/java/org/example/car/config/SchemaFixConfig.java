package org.example.car.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class SchemaFixConfig {

    private static final Logger log = LoggerFactory.getLogger(SchemaFixConfig.class);

    @Bean
    public ApplicationRunner schemaFixRunner(JdbcTemplate jdbcTemplate) {
        return args -> {
            // Keep existing databases compatible with updated entity lengths.
            runSafe(jdbcTemplate, "ALTER TABLE IF EXISTS users ALTER COLUMN name TYPE VARCHAR(150)");
            runSafe(jdbcTemplate, "ALTER TABLE IF EXISTS users ALTER COLUMN email TYPE VARCHAR(320)");
            runSafe(jdbcTemplate, "ALTER TABLE IF EXISTS users ALTER COLUMN password TYPE VARCHAR(255)");
            runSafe(jdbcTemplate, "ALTER TABLE IF EXISTS users ALTER COLUMN phone TYPE VARCHAR(30)");
            runSafe(jdbcTemplate, "ALTER TABLE IF EXISTS users ALTER COLUMN profile_image TYPE TEXT");

            // Rename legacy cars.year column to cars.car_year for reserved-keyword safety.
            runSafe(jdbcTemplate, "ALTER TABLE IF EXISTS cars RENAME COLUMN year TO car_year");
        };
    }

    private void runSafe(JdbcTemplate jdbcTemplate, String sql) {
        try {
            jdbcTemplate.execute(sql);
        } catch (Exception ex) {
            log.debug("Skipping schema fix statement [{}]: {}", sql, ex.getMessage());
        }
    }
}

