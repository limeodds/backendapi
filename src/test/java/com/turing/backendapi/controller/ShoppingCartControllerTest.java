package com.turing.backendapi.controller;

import com.turing.backendapi.controller.dto.CartIdDto;
import com.turing.backendapi.controller.dto.CartProductDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShoppingCartControllerTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  public void addProductToCart() {
    ResponseEntity<CartIdDto> response = restTemplate.getForEntity("http://localhost:" + port + "/shoppingcart/generateUniqueId", CartIdDto.class);

    String cart_id = response.getBody().getCart_id();
    Integer product_id = 2;
    String attributes = "LG, red";

    MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
    params.add("cart_id", cart_id);
    params.add("product_id", product_id);
    params.add("attributes", attributes);

    HttpHeaders headers = TestControllerUtil.headers();

    //Add Product to cart
    CartProductDto[] cartProducts = restTemplate.postForEntity("http://localhost:" + port + "/shoppingcart/add",
                                                               new HttpEntity<>(params, headers),
                                                               CartProductDto[].class)
                                                .getBody();

    assertThat(cartProducts).hasSize(1);

    //Get List of Products in Shopping Cart
    cartProducts = restTemplate.getForEntity("http://localhost:" + port + "/shoppingcart/{cart_id}", CartProductDto[].class, cart_id)
                               .getBody();

    assertThat(cartProducts).hasSize(1);

    //Update the cart by item
    params = new LinkedMultiValueMap<>();
    params.add("quantity", 11);

    restTemplate.put("http://localhost:" + port + "/shoppingcart/update/{item_id}",
                     new HttpEntity<>(params, headers),
                     cartProducts[0].getItem_id());

    cartProducts = restTemplate.getForEntity("http://localhost:" + port + "/shoppingcart/{cart_id}", CartProductDto[].class, cart_id)
                               .getBody();

    assertThat(cartProducts).hasSize(1);
    assertThat(cartProducts[0].getQuantity()).isEqualTo(11);

    //Empty shopping cart
    restTemplate.delete("http://localhost:" + port + "/shoppingcart/empty/{cart_id}", cart_id);

    cartProducts = restTemplate.getForEntity("http://localhost:" + port + "/shoppingcart/{cart_id}", CartProductDto[].class, cart_id)
                               .getBody();

    assertThat(cartProducts).hasSize(0);

  }
}