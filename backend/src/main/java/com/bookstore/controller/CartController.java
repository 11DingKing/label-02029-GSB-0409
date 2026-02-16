package com.bookstore.controller;

import com.bookstore.dto.ApiResponse;
import com.bookstore.entity.CartItem;
import com.bookstore.repository.CartItemRepository;
import com.bookstore.security.JwtUtil;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    
    private final CartItemRepository cartItemRepository;
    private final JwtUtil jwtUtil;
    
    public CartController(CartItemRepository cartItemRepository, JwtUtil jwtUtil) {
        this.cartItemRepository = cartItemRepository;
        this.jwtUtil = jwtUtil;
    }
    
    @GetMapping
    public ApiResponse<?> getCart(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        Long userId = jwtUtil.getUserId(token);
        List<CartItem> items = cartItemRepository.findByUserId(userId);
        return ApiResponse.success(items);
    }
    
    @PostMapping
    public ApiResponse<?> addToCart(@RequestBody CartItem item, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        Long userId = jwtUtil.getUserId(token);
        
        CartItem existing = cartItemRepository.findByUserIdAndBookId(userId, item.getBookId()).orElse(null);
        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + 1);
            cartItemRepository.save(existing);
            return ApiResponse.success(existing);
        }
        
        item.setUserId(userId);
        item.setQuantity(1);
        cartItemRepository.save(item);
        return ApiResponse.success(item);
    }
    
    @PutMapping("/{id}")
    public ApiResponse<?> updateCartItem(@PathVariable Long id, @RequestBody CartItem item) {
        CartItem existing = cartItemRepository.findById(id).orElse(null);
        if (existing == null) {
            return ApiResponse.error(404, "购物车项不存在");
        }
        existing.setQuantity(item.getQuantity());
        cartItemRepository.save(existing);
        return ApiResponse.success(existing);
    }
    
    @DeleteMapping("/{id}")
    public ApiResponse<?> removeFromCart(@PathVariable Long id) {
        cartItemRepository.deleteById(id);
        return ApiResponse.success();
    }
    
    @DeleteMapping
    public ApiResponse<?> clearCart(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        Long userId = jwtUtil.getUserId(token);
        cartItemRepository.deleteByUserId(userId);
        return ApiResponse.success();
    }
}
