package com.wyp.boot.earthlyfisher.docker.common;

/**
 * Created by earthlyfisher on 2017/4/5.
 */
public class DockerNodeInfo {

    private String ip;

    private String port;

    private String caPath;

    public DockerNodeInfo() {
    }

    public DockerNodeInfo(String ip, String port, String caPath) {
        this.ip = ip;
        this.port = port;
        this.caPath = caPath;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getCaPath() {
        return caPath;
    }

    public void setCaPath(String caPath) {
        this.caPath = caPath;
    }
}
