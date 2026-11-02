package org.example.foodstore.FoodDao;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Authentication response containing JWT token details")
public class AuthResponse {

    @Schema(description = "JWT Access Token")
    private String token;

    @Schema(description = "Token Type", example = "Bearer")
    private String tokenType;

    @Schema(description = "Username of the authenticated user", example = "admin")
    private String username;

    @Schema(description = "Assigned roles of the authenticated user", example = "[\"ROLE_ADMIN\"]")
    private List<String> roles;

    @Schema(description = "Token expiration duration in milliseconds", example = "86400000")
    private long expiresIn;
}
