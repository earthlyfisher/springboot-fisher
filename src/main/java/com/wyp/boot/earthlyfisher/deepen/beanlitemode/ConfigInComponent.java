package com.wyp.boot.earthlyfisher.deepen.beanlitemode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created by earthlyfisher on 2017/5/16.
 */
@Component
public class ConfigInComponent {

    @Autowired
    SimpleBean simpleBean;

    @Bean
    public SimpleBean simpleBean() {
        return new SimpleBean();
    }

    @Bean
    public SimpleBeanConsumer simpleBeanConsumer() {
        return new SimpleBeanConsumer(simpleBean);
    }
}
