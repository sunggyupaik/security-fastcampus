package com.sp.fc.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackageClasses = {
        com.sp.fc.paper.Paper.class
})
@EnableJpaRepositories(basePackageClasses = {
        com.sp.fc.paper.PaperRepository.class
})
public class JpaConfig {
}
