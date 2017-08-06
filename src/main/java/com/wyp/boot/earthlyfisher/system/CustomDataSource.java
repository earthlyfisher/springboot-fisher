package com.wyp.boot.earthlyfisher.system;

import com.wyp.boot.earthlyfisher.util.PasswordUtil;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 自己定义数据源生成，覆盖springboot自动配置为你生成的.
 *
 * @author earthlyfisher
 */
@Configuration
public class CustomDataSource {

    @Value("${spring.datasource.driver-class-name}")
    String driverClassName;

    @Value("${spring.datasource.url}")
    String url;

    @Value("${spring.datasource.username}")
    String username;

    @Value("${spring.datasource.password}")
    String password;

    @Bean
    @Primary
    public DataSource dataSource() {
        DataSource dataSource = new DataSource(); // org.apache.tomcat.jdbc.pool.DataSource;
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(PasswordUtil.decodePassword(password));
        return dataSource;
    }

    @Override
    public String toString() {
        return "CustomDataSource{" +
                "driverClassName='" + driverClassName + '\'' +
                ", url='" + url + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
