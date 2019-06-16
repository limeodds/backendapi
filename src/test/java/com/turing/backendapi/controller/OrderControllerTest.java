package com.turing.backendapi.controller;

import com.turing.backendapi.authentication.JwtTokenProvider;
import com.turing.backendapi.controller.dto.CartIdDto;
import com.turing.backendapi.controller.dto.CartProductDto;
import com.turing.backendapi.controller.dto.OrderIdDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class OrderControllerTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  public void createOrder() {
    String accessToken = TestControllerUtil.login(port, restTemplate);

    HttpHeaders headers = TestControllerUtil.headers();
    headers.add(JwtTokenProvider.AUTHORISATION_HEADER_NAME, accessToken);

    String cart_id = restTemplate.getForEntity("http://localhost:" + port + "/shoppingcart/generateUniqueId", CartIdDto.class)
                                 .getBody()
                                 .getCart_id();
    Integer product_id = 2;
    String attributes = "LG, red";

    MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
    params.add("cart_id", cart_id);
    params.add("product_id", product_id);
    params.add("attributes", attributes);


    //Add Product to cart
    CartProductDto[] cartProducts = restTemplate.postForEntity("http://localhost:" + port + "/shoppingcart/add",
                                                               new HttpEntity<>(params, headers),
                                                               CartProductDto[].class)
                                                .getBody();

    assertThat(cartProducts).hasSize(1);

    //Create Order
    params = new LinkedMultiValueMap<>();
    params.add("cart_id", cart_id);
    params.add("shipping_id", 2);
    params.add("tax_id", 1);

    Integer orderId = restTemplate.postForEntity("http://localhost:" + port + "/orders",
                                                 new HttpEntity<>(params, headers),
                                                 OrderIdDto.class)
                                  .getBody().getOrderId();


    assertThat(orderId).isGreaterThan(0);

  }
}