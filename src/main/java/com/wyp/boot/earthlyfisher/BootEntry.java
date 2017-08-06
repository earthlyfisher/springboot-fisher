package com.wyp.boot.earthlyfisher;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.List;

/**
 * 1.@SpringBootApplication<br/>
 * same as @Configuration @EnableAutoConfiguration @ComponentScan.<br/>
 * provides aliases to customize the attributes of @EnableAutoConfiguration
 * and @ComponentScan<br/>
 *
 * @author earthlyfisher
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.wyp.boot.earthlyfisher")
public class BootEntry implements EnvironmentAware {

    /**
     * 1.If you need to find out what auto-configuration is currently being
     * applied, and why, start your application with the --debug switch .<br/>
     *
     * @param args
     */
    /**
     * @param args
     */
    public static void main(String[] args) {
        // SpringApplication.run(BootEntry.class, setBootArgs(args));

        SpringApplication application = new SpringApplication(BootEntry.class);
        /*
         * Banner.Mode.OFF:关闭; Banner.Mode.CONSOLE:控制台输出，默认方式;
		 * Banner.Mode.LOG:日志输出方式;
		 */
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
        //application.run(setBootArgs(args));
    }

    /**
     * command line args self define
     *
     * @param args
     * @return
     */
    private static String[] setBootArgs(String[] args) {
        List<String> argsLst = new ArrayList<String>();
        for (int i = 0; i < args.length; i++) {
            argsLst.add(args[i]);
        }

        /** command line properties always take precedence over other property
         * sources,and self-add command param
         */
        argsLst.add("--debug");
        String[] springArgs = new String[argsLst.size()];
        argsLst.toArray(springArgs);
        return springArgs;
    }

    @Override
    public void setEnvironment(Environment environment) {
        System.out.println(environment.getProperty("spring.datasource.password"));
    }

}
