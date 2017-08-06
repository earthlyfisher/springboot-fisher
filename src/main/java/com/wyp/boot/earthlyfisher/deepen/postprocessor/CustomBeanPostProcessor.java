package com.wyp.boot.earthlyfisher.deepen.postprocessor;

import com.wyp.boot.earthlyfisher.service.UserService;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * Created by earthlyfisher on 2017/5/16.
 * <p>
 * 通过实现BeanPostProcessor，可以实现在Spring容器在完成Bean的实例化，配置和其他的初始化前后做一些自己的业务处理，
 * 比如我们可以统计自定义的的Bean集合
 *
 **/
@Component
public class CustomBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(beanName.equals("userService")){
            UserService ds=(UserService) bean;
            System.out.println(ds);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(beanName.equals("userService")){
            UserService ds=(UserService) bean;
            System.out.println(ds);
        }
        return bean;
    }
}
