package com.bookstore.controller;

import com.bookstore.dto.ApiResponse;
import com.bookstore.entity.Book;
import com.bookstore.repository.BookRepository;
import com.bookstore.repository.UserRepository;
import com.bookstore.repository.OrderRepository;
import com.bookstore.security.JwtUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/books")
public class BookController {
    
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final JwtUtil jwtUtil;
    
    public BookController(BookRepository bookRepository, UserRepository userRepository,
                         OrderRepository orderRepository, JwtUtil jwtUtil) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.jwtUtil = jwtUtil;
    }
    
    @GetMapping
    public ApiResponse<?> getBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String keyword) {
        
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Book> books;
        
        if (keyword != null && !keyword.isEmpty()) {
            books = bookRepository.searchByKeyword(keyword, 1, pageRequest);
        } else if (categoryId != null) {
            books = bookRepository.findByCategoryIdAndStatus(categoryId, 1, pageRequest);
        } else {
            books = bookRepository.findByStatus(1, pageRequest);
        }
        
        return ApiResponse.success(books);
    }
    
    @GetMapping("/{id}")
    public ApiResponse<?> getBook(@PathVariable Long id) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book == null) {
            return ApiResponse.error(404, "书籍不存在");
        }
        book.setViewCount(book.getViewCount() + 1);
        bookRepository.save(book);
        return ApiResponse.success(book);
    }
    
    @PostMapping
    public ApiResponse<?> createBook(@RequestBody Book book, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        Long userId = jwtUtil.getUserId(token);
        book.setSellerId(userId);
        book.setStatus(1);
        bookRepository.save(book);
        return ApiResponse.success(book);
    }
    
    @GetMapping("/my")
    public ApiResponse<?> getMyBooks(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        String token = authHeader.substring(7);
        Long userId = jwtUtil.getUserId(token);
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Book> books = bookRepository.findBySellerIdAndStatus(userId, 1, pageRequest);
        return ApiResponse.success(books);
    }
}

@RestController
@RequestMapping("/api/stats")
class StatsController {
    
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    
    StatsController(BookRepository bookRepository, UserRepository userRepository, OrderRepository orderRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }
    
    @GetMapping
    public ApiResponse<?> getStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("bookCount", bookRepository.countActiveBooks());
        stats.put("userCount", userRepository.count());
        stats.put("orderCount", orderRepository.countAllOrders());
        return ApiResponse.success(stats);
    }
}
