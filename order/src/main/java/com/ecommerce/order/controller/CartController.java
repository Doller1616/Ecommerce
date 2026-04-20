package com.ecommerce.order.controller;

import com.ecommerce.order.dto.CartItemRequest;
import com.ecommerce.order.model.CartItem;
import com.ecommerce.order.service.CartService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<String> addToCrat(@RequestHeader("X-User-ID") String userId,
    @RequestBody CartItemRequest cartItemRequest) {
        if(!cartService.addToCart(userId, cartItemRequest)) {
            return ResponseEntity.badRequest().body("Product out of stock or User not found");
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @DeleteMapping("/Items/{productId}")
    public ResponseEntity<String> removeFromCrat(
            @RequestHeader("X-User-ID") String userId,
            @PathVariable String productId
    ) {
      boolean isRemoved = cartService.deleteItemFromCart(userId, productId);
      return  isRemoved ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }


    @GetMapping
    public ResponseEntity<List<CartItem>> getCart(
            @RequestHeader("X-User-ID") String userId ) {
        return ResponseEntity.ok(cartService.getCart(userId));
    }

}
