package org.example.bookstore.BookDao;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.URL;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class BookCreationRequest {

    @NotBlank(message = "Name is mandatory")
    @Size(max = 255, message = "Name must be less than 255 characters")
    private String name;
    
    @NotNull(message = "Price is mandatory")
    private BigDecimal price;

    @URL(message = "Image URL must be valid")
    private String imageUrl;

    @NotNull(message = "Author ID is mandatory")
    private Integer authorId;

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }
}
