package com.wyp.boot.earthlyfisher.util;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateUtils {

	private static RestTemplate restTemplate = null;

	public RestTemplateUtils(RestTemplateBuilder restTemplateBuilder) {
		if (restTemplate == null) {
			RestTemplateUtils.restTemplate = restTemplateBuilder.build();
		}
	}

	public static <T> T handleGetRest(String url, Class<T> cls) {
		return restTemplate.getForObject(url, cls);
	}
}
