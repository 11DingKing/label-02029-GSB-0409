package com.bookstore.controller;

import com.bookstore.dto.ApiResponse;
import com.bookstore.entity.Book;
import com.bookstore.security.JwtUtil;
import com.bookstore.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BookController {
    
    private final BookService bookService;
    private final JwtUtil jwtUtil;
    
    public BookController(BookService bookService, JwtUtil jwtUtil) {
        this.bookService = bookService;
        this.jwtUtil = jwtUtil;
    }
    
    @GetMapping
    public ApiResponse<?> getBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String keyword) {
        
        Page<Book> books = bookService.getBooks(page, size, categoryId, keyword);
        return ApiResponse.success(books);
    }
    
    @GetMapping("/{id}")
    public ApiResponse<?> getBook(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        if (book == null) {
            return ApiResponse.error(404, "书籍不存在");
        }
        bookService.increaseViewCount(id);
        return ApiResponse.success(book);
    }
    
    @PostMapping
    public ApiResponse<?> createBook(@RequestBody Book book, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        Long userId = jwtUtil.getUserId(token);
        Book created = bookService.createBook(book, userId);
        return ApiResponse.success(created);
    }
    
    @GetMapping("/my")
    public ApiResponse<?> getMyBooks(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        String token = authHeader.substring(7);
        Long userId = jwtUtil.getUserId(token);
        Page<Book> books = bookService.getMyBooks(userId, page, size);
        return ApiResponse.success(books);
    }
}
