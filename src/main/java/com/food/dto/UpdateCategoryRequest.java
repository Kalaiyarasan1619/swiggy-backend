package com.food.dto;

import lombok.Data;
import jakarta.validation.constraints.Size;

@Data
public class UpdateCategoryRequest {
    @Size(min = 2, max = 100, message = "Category name must be between 2 and 100 characters")
    private String name;
    
    private String image;
    
    @Size(max = 10000, message = "Description cannot exceed 10000 characters")
    private String description;
}
