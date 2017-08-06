package com.wyp.boot.earthlyfisher.docker.common;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.DockerException;
import com.spotify.docker.client.messages.Container;
import com.spotify.docker.client.messages.ContainerConfig;
import org.apache.commons.collections.map.HashedMap;

import java.io.IOException;
import java.net.URI;
import java.util.*;

/**
 * Created by earthlyfisher on 2017/4/5.
 */
public class DockerManager {

    private static Map<Object, Object> dockerProps = new HashedMap();

    private static Map<String, DockerClient> dockerMap = new HashMap<>();

    static {
        loadDockerConfig();
    }

    public static DockerClient getDockerClient(String ip) {
        DockerClient docker = null;
        if (dockerMap.containsKey(ip)) {
            docker = dockerMap.get(ip);
        }

        if (docker != null) {
            return docker;
        }
       /* final DockerClient docker = DefaultDockerClient.builder()
                .uri(URI.create("https://boot2docker:2376"))
                .dockerCertificates(new DockerCertificates(Paths.get("/Users/rohan/.docker/boot2docker-vm/")))
                .build();*/

        DockerNodeInfo nodeInfo = (DockerNodeInfo) dockerProps.get(ip);
        docker = DefaultDockerClient.builder()
                .uri(URI.create("http://" + nodeInfo.getIp() + ":" + nodeInfo.getPort() + "/"))
                .build();
        dockerMap.put(ip, docker);
        return docker;
    }

    private static void loadDockerConfig() {
        Properties properties = new Properties();
        try {
            properties.load(ClassLoader.getSystemResourceAsStream(DockerConstant.CONFIG_FILE));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Iterator<Object> it = properties.keySet().iterator();
        String node;
        while (it.hasNext()) {
            node = (String) it.next();
            if (node.indexOf("docker.node") >= 0) {
                String[] value = ((String) properties.get(node)).split(":");
                DockerNodeInfo info = new DockerNodeInfo(value[0], value[1], value[2]);
                dockerProps.put(value[0], info);
            }
        }
    }

    public static void main(String[] args) throws DockerException, InterruptedException {
        DockerClient dockerClient = getDockerClient("192.168.30.131");
        final List<Container> containers = dockerClient.listContainers();
        System.out.println("  ");
        System.out.println(containers.size());
        System.out.println("  ");
        System.out.println(dockerClient.info());
        System.out.println("  ");
        System.out.println(dockerClient.listImages().size());
        dockerClient.close();

    }
}
