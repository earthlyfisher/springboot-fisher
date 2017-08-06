package com.wyp.boot.earthlyfisher.system;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * autowire tomcat https propeties for your own.
 * 具体的参数值会配置到ServerProperties
 * @author earthlyfisher Component
 */

@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "custom.tomcat.https",locations = "classpath:/server.https.yml")
public  class TomcatSslConnectorProperties {
    private Integer port;
    private Boolean ssl = true;
    private Boolean secure = true;
    private String scheme = "https";
    private File keystore;
    private String keystorePassword;

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Boolean getSsl() {
        return ssl;
    }

    public void setSsl(Boolean ssl) {
        this.ssl = ssl;
    }

    public Boolean getSecure() {
        return secure;
    }

    public void setSecure(Boolean secure) {
        this.secure = secure;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public File getKeystore() {
        return keystore;
    }

    public void setKeystore(File keystore) {
        this.keystore = keystore;
    }

    public String getKeystorePassword() {
        return keystorePassword;
    }

    public void setKeystorePassword(String keystorePassword) {
        this.keystorePassword = keystorePassword;
    }

    //Skipping getters and setters to save space, but we do need them
    public void configureConnector(Connector connector) {
        if (port != null)
            connector.setPort(port);
        if (secure != null)
            connector.setSecure(secure);
        if (scheme != null)
            connector.setScheme(scheme);
        if (ssl != null)
            connector.setProperty("SSLEnabled", ssl.toString());
        if (keystore != null && keystore.exists()) {
            connector.setProperty("keystoreFile",
                    keystore.getAbsolutePath());
            connector.setProperty("keystorePassword", keystorePassword);
        }
    }
}
