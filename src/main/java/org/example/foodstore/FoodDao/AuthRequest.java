package org.example.foodstore.FoodDao;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request payload for user authentication / login")
public class AuthRequest {

    @NotBlank(message = "Username is mandatory")
    @Schema(description = "User's username", example = "admin", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @NotBlank(message = "Password is mandatory")
    @Schema(description = "User's password", example = "admin123", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
}
