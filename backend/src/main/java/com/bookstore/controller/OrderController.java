package com.bookstore.controller;

import com.bookstore.dto.ApiResponse;
import com.bookstore.dto.OrderRequest;
import com.bookstore.entity.Order;
import com.bookstore.security.JwtUtil;
import com.bookstore.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    
    private final OrderService orderService;
    private final JwtUtil jwtUtil;
    
    public OrderController(OrderService orderService, JwtUtil jwtUtil) {
        this.orderService = orderService;
        this.jwtUtil = jwtUtil;
    }
    
    @GetMapping
    public ApiResponse<?> getOrders(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        String token = authHeader.substring(7);
        Long userId = jwtUtil.getUserId(token);
        Page<Order> orders = orderService.getOrders(userId, page, size);
        return ApiResponse.success(orders);
    }
    
    @GetMapping("/{id}")
    public ApiResponse<?> getOrder(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        if (order == null) {
            return ApiResponse.error(404, "订单不存在");
        }
        return ApiResponse.success(order);
    }
    
    @PostMapping
    public ApiResponse<?> createOrder(
            @Valid @RequestBody OrderRequest request, 
            @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        Long userId = jwtUtil.getUserId(token);
        
        try {
            Order order = orderService.createOrder(request, userId);
            return ApiResponse.success(order);
        } catch (IllegalArgumentException e) {
            return ApiResponse.error(400, e.getMessage());
        }
    }
    
    @PutMapping("/{id}/cancel")
    public ApiResponse<?> cancelOrder(@PathVariable Long id) {
        Order order = orderService.cancelOrder(id);
        return ApiResponse.success(order);
    }
}
