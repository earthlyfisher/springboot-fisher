package com.wyp.boot.earthlyfisher;

import com.wyp.boot.earthlyfisher.common.ResponseEntity;
import com.wyp.boot.earthlyfisher.pojo.City;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

/**
 * Created by earthlyfisher on 2017/2/4.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(BootEntry.class)
public class BootEntryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(BootEntryTest.class);

    private RestTemplate restTemplate;

    private static final String URL_S = "http://localhost:8080";

    private static final HttpHeaders headers = new HttpHeaders();

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Before
    public void prepare() {
        restTemplate = restTemplateBuilder.build();

        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
    }

    @Test
    public void createCity() {
        String params="{\"name\":\"天水\",\"state\":\"甘肃\"}";
        HttpEntity<String> formEntity = new HttpEntity<String>(params, headers);
        org.springframework.http.ResponseEntity<ResponseEntity> response =
                restTemplate.postForEntity(URL_S + "/city?daoType=jpa", formEntity, ResponseEntity.class);
        LOGGER.info(response.toString());
    }

}
