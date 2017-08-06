package com.wyp.boot.earthlyfisher.httpRest;

import com.wyp.boot.earthlyfisher.pojo.User;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * 模拟实现登陆后session认证的rest远程调用．
 * Created by earthlyfisher on 2017/5/19.
 */
public class RestTemplateTest {

    private static RestTemplate restTemplate;

    private static final String LOCAL_URL = "http://localhost:8080";

    private static String cookie;

    @BeforeClass
    public static void beforeTest() {
        restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
        //login and set cookie
        loginHttp("admin", "admin@inspur");
    }

    private static void loginHttp(String userName, String password) {
        HttpHeaders requestHeaders = new HttpHeaders();
        User user = new User();
        user.setName(userName);
        user.setPassword(password);
        HttpEntity<String> requestEntity = new HttpEntity(user, requestHeaders);
        HttpEntity<String> response = restTemplate.exchange(
                LOCAL_URL + "/users/login",
                HttpMethod.POST, requestEntity, String.class);
        HttpHeaders headers = response.getHeaders();
        if (headers.containsKey("Set-Cookie")) {
            cookie = headers.get("Set-Cookie").get(0);
        }
    }

    /**
     * post for https,
     * set header:accept and version
     */
    @Test
    public void loginHttps() {
        RestTemplate restTemplate = new RestTemplate(
                SslHttpClientUtilsByhttpClient.useApacheHttpClientWithSelfSignedSupport());
        HttpHeaders httpHeaders = new HttpHeaders();

        /**
         * `accept`: 表示请求端要求接收的类型
         * `content-type`：表示请求端发送的数据类型，restTemplate默认为text/plain;charset=ISO-8859-1
         */
        MediaType type = MediaType.parseMediaType("application/json;charset=utf-8");
        httpHeaders.setContentType(type);
        httpHeaders.set("accept", MediaType.APPLICATION_JSON.toString());
        httpHeaders.set("version", "5.0");

        String url = "https://100.2.30.152/authentication";
        String json = "{\"username\": \"admin\",\"password\": \"admin@inspur\",\"locale\": \"cn\",\"domain\": \"internal\",\"captcha\": \"\"}";
        HttpEntity<String> requestEntity = new HttpEntity(json, httpHeaders);

        HttpEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST, requestEntity, String.class);

        HttpHeaders headers = response.getHeaders();
        if (headers.containsKey("Set-Cookie")) {
            cookie = headers.get("Set-Cookie").get(1);
            System.out.println(cookie);
        }
    }

    /**
     * http.
     */
    @Test
    public void getTestByGet() {
        String result = restTemplate.getForObject(
                LOCAL_URL + "menus/{id}", String.class, 1);
        System.out.println(result);
    }

    /**
     * http.
     */
    @Test
    public void getTestByExchange() {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set("Cookie", cookie);
        HttpEntity<String> requestEntity = new HttpEntity(requestHeaders);
        HttpEntity<String> response = restTemplate
                .exchange(LOCAL_URL + "menus/{id}", HttpMethod.GET, requestEntity, String.class, 1);
        System.out.println(response.getBody());
    }

}
