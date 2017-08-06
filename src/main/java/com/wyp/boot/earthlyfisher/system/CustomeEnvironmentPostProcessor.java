package com.wyp.boot.earthlyfisher.system;

import java.util.Collection;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnumerableCompositePropertySource;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

import com.wyp.boot.earthlyfisher.util.PasswordUtil;

/**
 * 自定义环境变量加载完后处理器,以对加载的环境变量做进一步的处理.
 * 
 * @author earthlyfisher
 *
 */
public class CustomeEnvironmentPostProcessor implements EnvironmentPostProcessor {

	/**
	 * ConfigurableEnvironment的异变PropertySource key.
	 */
	private static final String SOURCE_NAME = "applicationConfigurationProperties";

	/**
	 * 需要的application变量的key
	 */
	private static final String ENV_KEY_NAME = "spring.datasource.password";

	@SuppressWarnings("unchecked")
	@Override
	public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
		MutablePropertySources ms = environment.getPropertySources();
		if (ms.contains(SOURCE_NAME)) {
			/*
			 * 1.获取加载application文件的PropertySource
			 */
			PropertySource<?> source = ms.get(SOURCE_NAME);

			if (source.containsProperty(ENV_KEY_NAME)) {
				/*
				 * 2.由于复合关系，
				 * 在这里找出加载application文件的PropertySource下的所有EnumerableCompositePropertySource
				 */
				List<EnumerableCompositePropertySource> lst = (List<EnumerableCompositePropertySource>) source
						.getSource();
				/*
				 * 3.遍历EnumerableCompositePropertySource集合，<br/>
				 * 找出每一个PropertySource的子PropertySource集合,并替换需要的key-value
				 */
				for (EnumerableCompositePropertySource target : lst) {
					if (target.containsProperty(ENV_KEY_NAME)) {
						Collection<PropertySource<?>> sourceSet = target.getSource();
						for (PropertySource<?> propertySource : sourceSet) {
							if (propertySource instanceof MapPropertySource) {
								MapPropertySource targetSource = (MapPropertySource) propertySource;
								addOrReplaceProperty(targetSource);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 在此做一些对application变量的替换
	 * 
	 * @param targetSource
	 */
	private void addOrReplaceProperty(MapPropertySource targetSource) {
		targetSource.getSource().put(ENV_KEY_NAME,
				PasswordUtil.decodePassword(targetSource.getProperty(ENV_KEY_NAME) + ""));
	}
}
