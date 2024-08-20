package com.example.team_7_case_8_product_management.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    private Long itemId;
    private String productName;
    private String description;
    private Float price;
    private Integer quantity;
    private MultipartFile image;
}
