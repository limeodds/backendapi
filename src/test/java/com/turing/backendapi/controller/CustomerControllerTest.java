package com.turing.backendapi.controller;

import com.turing.backendapi.controller.dto.LoginResponseDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerControllerTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  public void register() {
    String name = UUID.randomUUID().toString();
    String email = UUID.randomUUID().toString() + "@turing.com";
    String password = UUID.randomUUID().toString();

    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("name", name);
    params.add("email", email);
    params.add("password", password);

    ResponseEntity<LoginResponseDto> loginResp = restTemplate.postForEntity("http://localhost:" + port + "/customers",
                                                                            new HttpEntity<>(params, TestControllerUtil.headers()),
                                                                            LoginResponseDto.class);

    LoginResponseDto loginResponse = loginResp.getBody();

    assertThat(loginResponse.getCustomer().getSchema().getName()).isEqualTo(name);
    assertThat(loginResponse.getCustomer().getSchema().getEmail()).isEqualTo(email);
    assertThat(loginResponse.getAccessToken()).startsWith("Bearer ");
  }

  @Test
  public void login() {
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("email", TestControllerUtil.DEFAULT_EMAIL);
    params.add("password", TestControllerUtil.DEFAULT_PASSWORD);

    ResponseEntity<LoginResponseDto> loginResp = restTemplate.postForEntity("http://localhost:" + port + "/customers/login",
                                                                            new HttpEntity<>(params, TestControllerUtil.headers()),
                                                                            LoginResponseDto.class);

    LoginResponseDto loginResponse = loginResp.getBody();

    assertThat(loginResponse.getCustomer().getSchema().getName()).isEqualTo("Liviu Teodor");
    assertThat(loginResponse.getCustomer().getSchema().getEmail()).isEqualTo(TestControllerUtil.DEFAULT_EMAIL);
    assertThat(loginResponse.getAccessToken()).startsWith("Bearer ");
  }

}