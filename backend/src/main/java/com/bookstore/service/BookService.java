package com.bookstore.service;

import com.bookstore.dto.BookRequest;
import com.bookstore.entity.Book;
import com.bookstore.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    
    private final BookRepository bookRepository;
    
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    
    public Page<Book> getBooks(int page, int size, Long categoryId, String keyword) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        
        if (keyword != null && !keyword.isEmpty()) {
            return bookRepository.searchByKeyword(keyword, 1, pageRequest);
        } else if (categoryId != null) {
            return bookRepository.findByCategoryIdAndStatus(categoryId, 1, pageRequest);
        } else {
            return bookRepository.findByStatus(1, pageRequest);
        }
    }
    
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }
    
    public Book increaseViewCount(Long id) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book != null) {
            book.setViewCount(book.getViewCount() + 1);
            return bookRepository.save(book);
        }
        return null;
    }
    
    public Book createBook(BookRequest request, Long userId) {
        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setDescription(request.getDescription());
        book.setPrice(request.getPrice());
        book.setOriginalPrice(request.getOriginalPrice());
        book.setStock(request.getStock() != null ? request.getStock() : 0);
        book.setIsbn(request.getIsbn());
        book.setPublisher(request.getPublisher());
        book.setQuality(request.getQuality());
        book.setCoverImage(request.getCoverImage());
        book.setCategoryId(request.getCategoryId());
        
        book.setSellerId(userId);
        book.setStatus(1);
        book.setViewCount(0);
        return bookRepository.save(book);
    }
    
    public Page<Book> getMyBooks(Long userId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return bookRepository.findBySellerIdAndStatus(userId, 1, pageRequest);
    }
}
