package com.wyp.boot.earthlyfisher.deepen.beanlifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * bean生命周期，通过在BeanLifecycleTest通过@Bean实现生命周期的查看
 */
//@Component
public class BeanLifeCycle implements InitializingBean, DisposableBean {

    static {
        System.out.println("static init...");
    }

    {
        System.out.println("instance init ...");
    }

    public BeanLifeCycle() {
        System.out.println("constructor ....");
    }

    @PostConstruct
    public void PostConstruct() {
        System.out.println("init bean after constructor by @PostConstruct");
    }

    public void afterPropertiesSet() throws Exception {
        System.out.println("after  initial bean  by InitializingBean");
    }

    public void initMethod() {
        System.out.println("init bean after constructor by  init-method");
    }


    @PreDestroy
    public void PreDestroy() {
        System.out.println("before destroy bean by @PreDestroy");
    }

    public void destroyMethod() {
        System.out.println("before destroy bean by destroy-method");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("destroy bean by DisposableBean");
    }

}
