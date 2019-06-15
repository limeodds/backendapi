package com.turing.backendapi.controller;

import com.turing.backendapi.controller.dto.LoginResponseDto;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class TestControllerUtil {
  public static final String DEFAULT_EMAIL = "liviu.marinescu@yahoo.com";
  public static final String DEFAULT_PASSWORD = "pass";

  public static HttpHeaders headers() {
    HttpHeaders headers = new HttpHeaders();
    headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
    headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);

    return headers;
  }

  public static String login(int port, TestRestTemplate restTemplate) {
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("email", TestControllerUtil.DEFAULT_EMAIL);
    params.add("password", TestControllerUtil.DEFAULT_PASSWORD);

    ResponseEntity<LoginResponseDto> loginResp = restTemplate.postForEntity("http://localhost:" + port + "/customers/login",
                                                                            new HttpEntity<>(params, TestControllerUtil.headers()),
                                                                            LoginResponseDto.class);

    return loginResp.getBody().getAccessToken();
  }
}
