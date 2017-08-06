package com.wyp.boot.earthlyfisher.deepen.postprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * Created by earthlyfisher on 2017/5/16.
 *
 * 通过实现BeanFactoryPostProcessor，对bean配置的元数据做一些处理(可以改变初始化bean的内容)，
 * 此处改变了xmlBeanDefinition bean的数值
 *
 */
@Component
public class CustomBeanFactoryPostProcessor implements BeanFactoryPostProcessor, Ordered {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinition definition = beanFactory.getBeanDefinition("xmlBeanDefinition");
        MutablePropertyValues propertyValues=definition.getPropertyValues();
        if (propertyValues.contains("name")) {
            PropertyValue property=propertyValues.getPropertyValue("name");
            String name=((TypedStringValue) property.getValue()).getValue();
            propertyValues.add("name",name.replace(" ",""));
        }

        if (propertyValues.contains("age")) {
            PropertyValue property=propertyValues.getPropertyValue("age");
            Double age=Double.parseDouble(((TypedStringValue) property.getValue()).getValue());
            propertyValues.add("age",Math.round(age));
        }
    }

    @Override
    public int getOrder() {
        return 3;
    }
}
