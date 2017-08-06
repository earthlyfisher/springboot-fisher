package com.wyp.boot.earthlyfisher.deepen.beanlitemode;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by earthlyfisher on 2017/5/16.
 */
@Configuration
public class ConfigInConfiguration {

    @Bean
    public SimpleBean simpleBean() {
        return new SimpleBean();
    }

    @Bean
    public SimpleBeanConsumer simpleBeanConsumer() {
        return new SimpleBeanConsumer(simpleBean());
    }
}


