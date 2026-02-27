package com.bookstore.service;

import com.bookstore.dto.OrderRequest;
import com.bookstore.entity.Book;
import com.bookstore.entity.Order;
import com.bookstore.entity.OrderItem;
import com.bookstore.repository.BookRepository;
import com.bookstore.repository.OrderItemRepository;
import com.bookstore.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class OrderService {
    
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final BookRepository bookRepository;
    
    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository,
                      BookRepository bookRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.bookRepository = bookRepository;
    }
    
    public Page<Order> getOrders(Long userId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return orderRepository.findByUserId(userId, pageRequest);
    }
    
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }
    
    @Transactional
    public Order createOrder(OrderRequest request, Long userId) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        
        // 校验库存并计算总价
        for (OrderRequest.OrderItemRequest item : request.getItems()) {
            Book book = bookRepository.findById(item.getBookId()).orElse(null);
            if (book == null || book.getStock() < item.getQuantity()) {
                throw new IllegalArgumentException("库存不足: " + (book != null ? book.getTitle() : "书籍不存在"));
            }
            totalAmount = totalAmount.add(book.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        
        // 创建订单
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
        
        // 创建订单明细并扣减库存
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
        
        return order;
    }
    
    public Order cancelOrder(Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) {
            throw new IllegalArgumentException("订单不存在");
        }
        order.setStatus("CANCELLED");
        order.setUpdatedAt(LocalDateTime.now());
        return orderRepository.save(order);
    }
    
    private String generateOrderNo() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) 
               + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }
}
