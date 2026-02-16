package com.bookstore.repository;

import com.bookstore.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends JpaRepository<Book, Long> {
    Page<Book> findByStatus(Integer status, Pageable pageable);
    Page<Book> findByCategoryIdAndStatus(Long categoryId, Integer status, Pageable pageable);
    Page<Book> findBySellerIdAndStatus(Long sellerId, Integer status, Pageable pageable);
    
    @Query("SELECT b FROM Book b WHERE b.status = :status AND (b.title LIKE %:keyword% OR b.author LIKE %:keyword%)")
    Page<Book> searchBooks(@Param("keyword") String keyword, @Param("status") Integer status, Pageable pageable);
    
    @Query("SELECT COUNT(b) FROM Book b WHERE b.status = 1")
    Long countActiveBooks();
}
