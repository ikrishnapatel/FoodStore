package org.example.foodstore.Exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Standard error response format")
public class ErrorResponse {
    @Schema(description = "Indicates failure (false)", example = "false")
    private boolean success;

    @Schema(description = "HTTP status code", example = "404")
    private int statusCode;

    @Schema(description = "Error description or details", example = "Food with ID 99 not found")
    private String message;

    @Schema(description = "Additional error payload (usually null)")
    private Object data;
}
