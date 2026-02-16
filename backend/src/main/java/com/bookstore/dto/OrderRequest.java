package com.bookstore.dto;

import lombok.Data;
import java.util.List;

@Data
public class OrderRequest {
    private String address;
    private String phone;
    private String receiver;
    private String remark;
    private List<OrderItemRequest> items;
    
    @Data
    public static class OrderItemRequest {
        private Long bookId;
        private Integer quantity;
    }
}
