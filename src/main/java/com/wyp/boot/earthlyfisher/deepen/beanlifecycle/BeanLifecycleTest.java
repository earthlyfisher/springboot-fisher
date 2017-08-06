package com.wyp.boot.earthlyfisher.deepen.beanlifecycle;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by earthlyfisher on 2017/5/16.
 */
@Configuration
@ImportResource(locations = {"application-xmlBeanDefinition.xml"})
public class BeanLifecycleTest {

    @Bean(initMethod = "initMethod", destroyMethod = "destroyMethod")
    public BeanLifeCycle beanLifeCycle() {
        return new BeanLifeCycle();
    }
}
