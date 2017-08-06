package com.wyp.boot.earthlyfisher.system;

import com.wyp.boot.earthlyfisher.security.SessionInterceptor;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.concurrent.TimeUnit;

@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {

    @Bean
    public SessionInterceptor sessionInterceptor() {
        return new SessionInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionInterceptor());
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        //使用后缀模式匹配,默认为true
        configurer.setUseSuffixPatternMatch(false);

        //末尾斜线匹配,默认为true
        configurer.setUseTrailingSlashMatch(true);
    }

    @Override
    /**
     * 此种类似与<mvc:resources>,通过静态指定路径，将访问资源进行路径匹配，并且<br/>
     * 不经过Interceptor的拦截
     */
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //registry.addResourceHandler("/internal/**").addResourceLocations("classpath:/");
    }

    /**
     * Tuning Tomcat via EmbeddedServletContainerCustomizer
     *
     * @return
     */
    @Bean
    public EmbeddedServletContainerCustomizer embeddedServletContainerCustomizer() {
        return new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
                container.setSessionTimeout(30, TimeUnit.MINUTES);
            }
        };
    }
}
