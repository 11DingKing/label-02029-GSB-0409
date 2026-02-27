package com.bookstore.controller;

import com.bookstore.dto.ApiResponse;
import com.bookstore.dto.OrderRequest;
import com.bookstore.entity.Book;
import com.bookstore.entity.Order;
import com.bookstore.entity.OrderItem;
import com.bookstore.repository.BookRepository;
import com.bookstore.repository.CartItemRepository;
import com.bookstore.repository.OrderItemRepository;
import com.bookstore.repository.OrderRepository;
import com.bookstore.security.JwtUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final BookRepository bookRepository;
    private final CartItemRepository cartItemRepository;
    private final JwtUtil jwtUtil;
    
    public OrderController(OrderRepository orderRepository, OrderItemRepository orderItemRepository,
                          BookRepository bookRepository, CartItemRepository cartItemRepository, JwtUtil jwtUtil) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.bookRepository = bookRepository;
        this.cartItemRepository = cartItemRepository;
        this.jwtUtil = jwtUtil;
    }
    
    @GetMapping
    public ApiResponse<?> getOrders(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        String token = authHeader.substring(7);
        Long userId = jwtUtil.getUserId(token);
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Order> orders = orderRepository.findByUserId(userId, pageRequest);
        return ApiResponse.success(orders);
    }
    
    @GetMapping("/{id}")
    public ApiResponse<?> getOrder(@PathVariable Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) {
            return ApiResponse.error(404, "订单不存在");
        }
        return ApiResponse.success(order);
    }
    
    @PostMapping
    @Transactional
    public ApiResponse<?> createOrder(@RequestBody OrderRequest request, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        Long userId = jwtUtil.getUserId(token);
        
        BigDecimal totalAmount = BigDecimal.ZERO;
        
        for (OrderRequest.OrderItemRequest item : request.getItems()) {
            Book book = bookRepository.findById(item.getBookId()).orElse(null);
            if (book == null || book.getStock() < item.getQuantity()) {
                return ApiResponse.error(400, "库存不足");
            }
            totalAmount = totalAmount.add(book.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        
        Order order = new Order();
        order.setOrderNo(generateOrderNo());
        order.setUserId(userId);
        order.setTotalAmount(totalAmount);
        order.setAddress(request.getAddress());
        order.setPhone(request.getPhone());
        order.setReceiver(request.getReceiver());
        order.setRemark(request.getRemark());
        order.setStatus("PENDING");
        orderRepository.save(order);
        
        for (OrderRequest.OrderItemRequest item : request.getItems()) {
            Book book = bookRepository.findById(item.getBookId()).orElse(null);
            
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(order.getId());
            orderItem.setBookId(item.getBookId());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(book.getPrice());
            orderItemRepository.save(orderItem);
            
            book.setStock(book.getStock() - item.getQuantity());
            bookRepository.save(book);
        }
        
        // 订单创建成功后清空购物车
        cartItemRepository.deleteByUserId(userId);
        
        return ApiResponse.success(order);
    }
    
    @PutMapping("/{id}/cancel")
    public ApiResponse<?> cancelOrder(@PathVariable Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) {
            return ApiResponse.error(404, "订单不存在");
        }
        order.setStatus("CANCELLED");
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);
        return ApiResponse.success(order);
    }
    
    private String generateOrderNo() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) 
               + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }
}
