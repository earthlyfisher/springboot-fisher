package com.wyp.boot.earthlyfisher;

import com.wyp.boot.earthlyfisher.deepen.beanlifecycle.BeanLifeCycle;
import com.wyp.boot.earthlyfisher.deepen.beanlifecycle.BeanLifecycleTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by earthlyfisher on 2017/5/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BeanConfigTest.Config.class)
public class BeanConfigTest {

    @Autowired
    @Qualifier("myValue")
    String value;

    @Test
    public void test() {
        Assert.assertEquals("test", value);
    }

    @Configuration
    public static class Config {
        @Bean(name = "myValue")
        public String myValue() {
            return "test";
        }
    }


    public static void main(String[] args) {
        ApplicationContext context= new AnnotationConfigApplicationContext(BeanLifecycleTest.class);
        BeanLifeCycle bean=(BeanLifeCycle)context.getBean("beanLifeCycle");
        bean.initMethod();
    }
}
