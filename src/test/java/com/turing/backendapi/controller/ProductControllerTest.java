package com.turing.backendapi.controller;

import com.turing.backendapi.authentication.JwtTokenProvider;
import com.turing.backendapi.controller.dto.DtoProductsPage;
import com.turing.backendapi.controller.dto.LoginResponseDto;
import com.turing.backendapi.controller.dto.ProductReviewDto;
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
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Random;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerTest {
  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  public void allProducts() {
    String url = "http://localhost:" + port + "/products";

    UriComponentsBuilder uricb = UriComponentsBuilder.fromHttpUrl(url)
                                                     .queryParam("page", 1)
                                                     .queryParam("limit", 20)
                                                     .queryParam("description_length", 40);

    ResponseEntity<DtoProductsPage> response = restTemplate.getForEntity(uricb.toUriString(), DtoProductsPage.class);

    DtoProductsPage re = response.getBody();

    assertThat(re.getCount()).isGreaterThan(0);
    assertThat(re.getRows()).hasSize(20);
    assertThat(re.getRows().get(0).getDescription().length()).isLessThan(41);


    response = restTemplate.getForEntity(UriComponentsBuilder.fromHttpUrl(url).toUriString(), DtoProductsPage.class);
    re = response.getBody();
    assertThat(re.getCount()).isGreaterThan(50);
  }

  @Test
  public void createReview() {
    String accessToken = TestControllerUtil.login(port, restTemplate);

    HttpHeaders headers = TestControllerUtil.headers();
    headers.add(JwtTokenProvider.AUTHORISATION_HEADER_NAME, accessToken);

    Integer product_id = 1;
    String review = "testReview - " + UUID.randomUUID().toString();
    Integer rating = new Random().nextInt() % 5 + 1;

    MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
    params.add("review", review);
    params.add("rating", rating);

    //Create review
    restTemplate.postForEntity("http://localhost:" + port + "/products/{product_id}/reviews",
                               new HttpEntity<>(params, headers),
                               LoginResponseDto.class,
                               product_id);

    //select All reviews for a product and check that we have the previously created review
    ResponseEntity<ProductReviewDto[]> response = restTemplate.getForEntity("http://localhost:" + port + "/products/{product_id}/reviews",
                                                                            ProductReviewDto[].class,
                                                                            product_id);

    ProductReviewDto[] lst = response.getBody();

    ProductReviewDto productReviewDto = Stream.of(lst).filter(o -> review.equalsIgnoreCase(o.getReview())).findFirst().orElse(null);

    assertThat(productReviewDto).isNotNull();
    assertThat(productReviewDto.getRating()).isEqualTo(rating);
  }
}