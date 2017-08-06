package com.wyp.boot.earthlyfisher.system;

import com.wyp.boot.earthlyfisher.util.PasswordUtil;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;

import java.io.IOException;

/**
 * 自定义文件加载器
 *
 * @author earthlyfisher
 */
public class CustomPropertySourceLoader implements PropertySourceLoader {

    @Override
    public String[] getFileExtensions() {
        return new String[]{"yml", "yaml"};
    }

    @Override
    public PropertySource<?> load(String name, Resource resource, String profile) throws IOException {
        YamlPropertySourceLoader ymlLoader = new YamlPropertySourceLoader();
        PropertySource<?> source = ymlLoader.load(name, resource, profile);
        String propertyKey = "spring.datasource.password";
        if (source instanceof MapPropertySource) {
            MapPropertySource target = (MapPropertySource) source;
            if (target.containsProperty(propertyKey)) {
                target.getSource().put(propertyKey, PasswordUtil.decodePassword(target.getProperty(propertyKey) + ""));
            }
        }
        return source;
    }

}
