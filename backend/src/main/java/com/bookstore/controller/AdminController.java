package com.bookstore.controller;

import com.bookstore.dto.ApiResponse;
import com.bookstore.entity.Book;
import com.bookstore.entity.Category;
import com.bookstore.entity.Order;
import com.bookstore.entity.User;
import com.bookstore.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final OrderRepository orderRepository;
    
    public AdminController(UserRepository userRepository, BookRepository bookRepository,
                          CategoryRepository categoryRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
        this.orderRepository = orderRepository;
    }
    
    @GetMapping("/dashboard")
    public ApiResponse<?> getDashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("userCount", userRepository.count());
        data.put("bookCount", bookRepository.countActiveBooks());
        data.put("orderCount", orderRepository.countAllOrders());
        BigDecimal totalSales = orderRepository.getTotalSales();
        data.put("totalSales", totalSales != null ? totalSales : BigDecimal.ZERO);
        return ApiResponse.success(data);
    }
    
    @GetMapping("/users")
    public ApiResponse<?> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<User> users = userRepository.findAll(pageRequest);
        return ApiResponse.success(users);
    }
    
    @PutMapping("/users/{id}/status")
    public ApiResponse<?> updateUserStatus(@PathVariable Long id, @RequestBody Map<String, Integer> body) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ApiResponse.error(404, "用户不存在");
        }
        user.setStatus(body.get("status"));
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
        return ApiResponse.success(user);
    }
    
    @GetMapping("/books")
    public ApiResponse<?> getBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Book> books = bookRepository.findAll(pageRequest);
        return ApiResponse.success(books);
    }
    
    @PutMapping("/books/{id}")
    public ApiResponse<?> updateBook(@PathVariable Long id, @RequestBody Book book) {
        Book existing = bookRepository.findById(id).orElse(null);
        if (existing == null) {
            return ApiResponse.error(404, "书籍不存在");
        }
        existing.setTitle(book.getTitle());
        existing.setAuthor(book.getAuthor());
        existing.setPrice(book.getPrice());
        existing.setStock(book.getStock());
        existing.setStatus(book.getStatus());
        existing.setCategoryId(book.getCategoryId());
        existing.setUpdatedAt(LocalDateTime.now());
        bookRepository.save(existing);
        return ApiResponse.success(existing);
    }
    
    @DeleteMapping("/books/{id}")
    public ApiResponse<?> deleteBook(@PathVariable Long id) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book == null) {
            return ApiResponse.error(404, "书籍不存在");
        }
        book.setStatus(0);
        bookRepository.save(book);
        return ApiResponse.success();
    }
    
    @GetMapping("/categories")
    public ApiResponse<?> getCategories() {
        return ApiResponse.success(categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "sortOrder")));
    }
    
    @PostMapping("/categories")
    public ApiResponse<?> createCategory(@RequestBody Category category) {
        categoryRepository.save(category);
        return ApiResponse.success(category);
    }
    
    @PutMapping("/categories/{id}")
    public ApiResponse<?> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        Category existing = categoryRepository.findById(id).orElse(null);
        if (existing == null) {
            return ApiResponse.error(404, "分类不存在");
        }
        existing.setName(category.getName());
        existing.setIcon(category.getIcon());
        existing.setSortOrder(category.getSortOrder());
        existing.setStatus(category.getStatus());
        categoryRepository.save(existing);
        return ApiResponse.success(existing);
    }
    
    @DeleteMapping("/categories/{id}")
    public ApiResponse<?> deleteCategory(@PathVariable Long id) {
        categoryRepository.deleteById(id);
        return ApiResponse.success();
    }
    
    @GetMapping("/orders")
    public ApiResponse<?> getOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String status) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Order> orders;
        if (status != null && !status.isEmpty()) {
            orders = orderRepository.findByStatus(status, pageRequest);
        } else {
            orders = orderRepository.findAll(pageRequest);
        }
        return ApiResponse.success(orders);
    }
    
    @PutMapping("/orders/{id}/status")
    public ApiResponse<?> updateOrderStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) {
            return ApiResponse.error(404, "订单不存在");
        }
        order.setStatus(body.get("status"));
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);
        return ApiResponse.success(order);
    }
}
