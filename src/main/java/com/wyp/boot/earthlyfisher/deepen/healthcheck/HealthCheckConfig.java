package com.wyp.boot.earthlyfisher.deepen.healthcheck;

import org.springframework.boot.actuate.endpoint.PublicMetrics;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

/**
 * Created by earthlyfisher on 2017/5/16.
 */
@Configuration
public class HealthCheckConfig {

    @Bean
    public PublicMetrics dbCountMetrics(Collection<CrudRepository> repositories) {
        return new DbCountMetrics(repositories);
    }
}


