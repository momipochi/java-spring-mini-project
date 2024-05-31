package com.example.mp5spring.DTO;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class ProductDTO {
    private Long id;
    private String productDescription;
    private String productName;
    public ProductDTO(Long id, String productName, String productDescription){
        this.id= id;
        this.productName=productName;
        this.productDescription = productDescription;
    }
}
