package com.turing.backendapi.controller;


import com.turing.backendapi.controller.converter.ShoppingCartDtoConverter;
import com.turing.backendapi.controller.dto.*;
import com.turing.backendapi.controller.exception.ErrorResponse;
import com.turing.backendapi.service.ShoppingCartService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import static com.turing.backendapi.controller.exception.ErrorCodes.GEN_01;

@RestController
@RequestMapping(value = "/shoppingcart", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(description = "Everything about Shopping Cart", tags = {"shoppingcart"})
public class ShoppingCartController implements Validation {

  private final ShoppingCartService shoppingCartService;

  @Autowired
  public ShoppingCartController(ShoppingCartService shoppingCartService) {
    this.shoppingCartService = shoppingCartService;
  }

  @GetMapping(value = "/generateUniqueId")
  @ApiOperation(value = "Generate unique CART ID")
  @ApiResponses({
                @ApiResponse(code = 200, message = "Json Object with unique Cart ID", response = CartIdDto.class),
                @ApiResponse(code = 400, message = "Return a error object", response = ErrorResponse.class)})
  public CartIdDto generateUniqueId() {
    return new CartIdDto(shoppingCartService.generateUniqueId());
  }

  @PostMapping(path = "/add", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  @ApiOperation(value = "Add a Product in the cart.", notes = "put a product in the cart")
  @ApiResponses({
                @ApiResponse(code = 200, message = "Return a array of products in the cart", response = CartProductDto[].class),
                @ApiResponse(code = 400, message = "Return a error object", response = ErrorResponse.class)})
  @ApiImplicitParams({
                     @ApiImplicitParam(name = "cart_id", value = "Cart ID.", required = true, dataType = "string", paramType = "form"),
                     @ApiImplicitParam(name = "product_id", value = "Product ID.", required = true, dataType = "int", paramType = "form"),
                     @ApiImplicitParam(name = "attributes", value = "Attributes of Product.", required = true, dataType = "string", paramType = "form")
                     })
  public CartProductDto[] addProductToCart(@RequestParam String cart_id,
                                           @RequestParam Integer product_id,
                                           @RequestParam String attributes) {
    checkNotEmpty(cart_id, GEN_01, "cart_id");
    checkNotEmpty(product_id, GEN_01, "product_id");
    checkNotEmpty(attributes, GEN_01, "attributes");

    shoppingCartService.addProduct(cart_id, product_id, attributes);

    return shoppingCartService.getProducts(cart_id).stream()
                              .map(ShoppingCartDtoConverter::toDto).toArray(CartProductDto[]::new);
  }

  @GetMapping("/{cart_id}")
  @ApiOperation(value = "Get List of Products in Shopping Cart")
  @ApiResponses({
                @ApiResponse(code = 200, message = "Return a array of products in the cart.", response = CartProductDto[].class),
                @ApiResponse(code = 400, message = "Return a error object", response = ErrorResponse.class)})
  @ApiImplicitParams({
                     @ApiImplicitParam(name = "cart_id", value = "Cart ID", required = true, dataType = "string", paramType = "path")})
  public CartProductDto[] getById(@PathVariable String cart_id) {
    checkNotEmpty(cart_id, GEN_01, "cart_Id");

    return shoppingCartService.getProducts(cart_id).stream()
                              .map(ShoppingCartDtoConverter::toDto).toArray(CartProductDto[]::new);
  }

  @PutMapping(path = "/update/{item_id}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  @ApiOperation(value = "Update the cart by item")
  @ApiResponses({
                @ApiResponse(code = 200, message = "Return a array of products in the cart", response = CartProductDto[].class),
                @ApiResponse(code = 400, message = "Return a error object", response = ErrorResponse.class)})
  @ApiImplicitParams({
                     @ApiImplicitParam(name = "item_id", value = "Item ID.", required = true, dataType = "int", paramType = "path"),
                     @ApiImplicitParam(name = "quantity", value = "Item Quantity.", required = true, dataType = "int", paramType = "form")})
  public CartProductDto[] addProductToCart(@PathVariable Integer item_id,
                                           @RequestParam Integer quantity) {
    checkNotEmpty(item_id, GEN_01, "item_id");
    checkNotEmpty(quantity, GEN_01, "quantity");

    shoppingCartService.updateCart(item_id, quantity);


    String cartId = shoppingCartService.getCartIdByItemId(item_id);

    if (cartId == null) {
      return new CartProductDto[0];
    }

    return shoppingCartService.getProducts(cartId).stream()
                              .map(ShoppingCartDtoConverter::toDto).toArray(CartProductDto[]::new);
  }

  @DeleteMapping("/empty/{cart_id}")
  @ApiOperation(value = "Empty cart")
  @ApiResponses({
                @ApiResponse(code = 200, message = "Return a empty Array.", response = CartProductDto[].class),
                @ApiResponse(code = 400, message = "Return a error object", response = ErrorResponse.class)})
  @ApiImplicitParams({
                     @ApiImplicitParam(name = "cart_id", value = "Cart ID", required = true, dataType = "string", paramType = "path")})
  public CartProductDto[] emptyShopingCart(@PathVariable String cart_id) {
    checkNotEmpty(cart_id, GEN_01, "cart_Id");

    shoppingCartService.emptyShopingCart(cart_id);

    return new CartProductDto[0];
  }

  @GetMapping("/moveToCart/{item_id}")
  @ApiOperation(value = "Move a product to cart")
  @ApiResponses({
                @ApiResponse(code = 200, message = "No data."),
                @ApiResponse(code = 400, message = "Return a error object", response = ErrorResponse.class)})
  @ApiImplicitParams({
                     @ApiImplicitParam(name = "item_id", value = "Item ID", required = true, dataType = "int", paramType = "path")})
  public void moveProductToCart(@PathVariable Integer item_id) {
    checkNotEmpty(item_id, GEN_01, "item_id");

    shoppingCartService.moveProductToCart(item_id);
  }

  @GetMapping("/totalAmount/{cart_id}")
  @ApiOperation(value = "Return a total Amount from Cart")
  @ApiResponses({
                @ApiResponse(code = 200, message = "Return the total amount", response = CartTotalAmountDto.class),
                @ApiResponse(code = 400, message = "Return a error object", response = ErrorResponse.class)})
  @ApiImplicitParams({
                     @ApiImplicitParam(name = "cart_id", value = "Cart ID", required = true, dataType = "string", paramType = "path")})
  public CartTotalAmountDto getTotalAmount(@PathVariable String cart_id) {
    checkNotEmpty(cart_id, GEN_01, "cart_Id");

    BigDecimal totalAmount = shoppingCartService.getTotalAmount(cart_id);

    return new CartTotalAmountDto(DtoUtils.format(totalAmount));
  }

  @GetMapping("/saveForLater/{item_id}")
  @ApiOperation(value = "Save a Product for latter")
  @ApiResponses({
                @ApiResponse(code = 200, message = "No data."),
                @ApiResponse(code = 400, message = "Return a error object", response = ErrorResponse.class)})
  @ApiImplicitParams({
                     @ApiImplicitParam(name = "item_id", value = "Item ID", required = true, dataType = "int", paramType = "path")})
  public void saveProductForLater(@PathVariable Integer item_id) {
    checkNotEmpty(item_id, GEN_01, "item_id");

    shoppingCartService.saveProductForLater(item_id);
  }

  @GetMapping("/getSaved/{cart_id}")
  @ApiOperation(value = "Get Products saved for latter")
  @ApiResponses({
                @ApiResponse(code = 200, message = "Return a object of item saved.", response = CartProductSavedDto[].class),
                @ApiResponse(code = 400, message = "Return a error object", response = ErrorResponse.class)})
  @ApiImplicitParams({
                     @ApiImplicitParam(name = "cart_id", value = "Cart ID", required = true, dataType = "string", paramType = "path")})
  public CartProductSavedDto[] getSavedProducts(@PathVariable String cart_id) {
    checkNotEmpty(cart_id, GEN_01, "cart_Id");

    return shoppingCartService.getSavedProducts(cart_id).stream()
                              .map(o -> CartProductSavedDto.builder()
                                                           .item_id(o.getItem_id())
                                                           .name(o.getName())
                                                           .attributes(o.getAttributes())
                                                           .price(DtoUtils.format(o.getPrice()))
                                                           .build()).toArray(CartProductSavedDto[]::new);
  }

  @DeleteMapping("/removeProduct/{item_id}")
  @ApiOperation(value = "Remove a product in the cart")
  @ApiResponses({
                @ApiResponse(code = 200, message = "No data."),
                @ApiResponse(code = 400, message = "Return a error object", response = ErrorResponse.class)})
  @ApiImplicitParams({
                     @ApiImplicitParam(name = "item_id", value = "Item ID", required = true, dataType = "int", paramType = "path")})
  public void removeProduct(@PathVariable Integer item_id) {
    checkNotEmpty(item_id, GEN_01, "item_id");

    shoppingCartService.removeProduct(item_id);
  }
}
