
package com.wyp.boot.earthlyfisher;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * 1.@SpringBootApplication<br/>
 * same as @Configuration @EnableAutoConfiguration @ComponentScan.<br/>
 * provides aliases to customize the attributes of @EnableAutoConfiguration
 * and @ComponentScan<br/>
 *
 * @author earthlyfisher
 */
//@SpringBootApplication
public class BootEntry4War extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(BootEntry4War.class);
    }

    /**
     * 1.If you need to find out what auto-configuration is currently being
     * applied, and why, start your application with the --debug switch .<br/>
     *
     * @param args
     */
    /**
     * @param args
     */
    /*public static void main(String[] args) {
        SpringApplication application = new SpringApplication(BootEntry4War.class);
        application.run(args);
    }*/

}
