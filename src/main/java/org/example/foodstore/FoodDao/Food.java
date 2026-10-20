package org.example.foodstore.FoodDao;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "category")
@Schema(description = "Food item entity")
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the food item", example = "1")
    private int id;

    @NotBlank(message = "Food name is mandatory")
    @Schema(description = "Name of the food item", example = "Margherita Pizza")
    private String name;

    @NotNull(message = "Price is mandatory")
    @DecimalMin(value = "0.0", message = "Price must be greater than or equal to 0")
    @Schema(description = "Price of the food item", example = "12.99")
    private BigDecimal price;

    @Schema(description = "Image URL of the food item", example = "https://images.unsplash.com/photo-1513104802838-83d67069a010")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @Schema(description = "Category to which this food belongs")
    private Category category;

    public Food(int id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Food(int id, String name, BigDecimal price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }
}
