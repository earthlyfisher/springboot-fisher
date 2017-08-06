package com.wyp.boot.earthlyfisher.system;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.Resource;

import java.util.Properties;

/**
 * 属性文件变更器，例如对加密的密码做解密操作，以供数据库连接使用.
 * 在此项目没有使用
 *
 * @author earthlyfisher
 */
public class CustomePropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

    private static final String CHANGE_PROPERTY_NAME = "spring.datasource.password";

    @Override
    public void setLocation(Resource location) {
        super.setLocation(location);
    }

    @Override
    protected String convertProperty(String propertyName, String propertyValue) {
        if (propertyName.equals(CHANGE_PROPERTY_NAME)) {
            System.out.println(propertyName + "=" + propertyValue);
            return propertyValue.substring(0, propertyValue.length() - 1);
        }
        return propertyValue;
    }

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
            throws BeansException {
        super.processProperties(beanFactoryToProcess, props);
    }

    @Override
    protected String resolvePlaceholder(String placeholder, Properties props) {
        return super.resolvePlaceholder(placeholder, props);
    }

    @Override
    protected String resolvePlaceholder(String placeholder, Properties props, int systemPropertiesMode) {
        return super.resolvePlaceholder(placeholder, props, systemPropertiesMode);
    }

    @Override
    protected String resolveSystemProperty(String arg0) {
        return super.resolveSystemProperty(arg0);
    }

}
