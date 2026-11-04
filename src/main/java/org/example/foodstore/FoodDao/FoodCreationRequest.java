package org.example.foodstore.FoodDao;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.URL;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Request payload for creating or updating a food item")
public class FoodCreationRequest {

    @Schema(description = "Name of the food item", example = "Margherita Pizza", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Name is mandatory")
    @Size(max = 255, message = "Name must be less than 255 characters")
    private String name;
    
    @Schema(description = "Price of the food item", example = "12.99", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Price is mandatory")
    private BigDecimal price;

    @Schema(description = "Direct image URL for the food item", example = "https://images.unsplash.com/photo-1513104802838-83d67069a010")
    @URL(message = "Image URL must be valid")
    private String imageUrl;

    @Schema(description = "Category ID to which this food belongs", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Category ID is mandatory")
    private Integer categoryId;

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

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}
