package org.example.foodstore.FoodDao;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Response details for bulk food deletion")
public class BulkDeleteResponse {
    @Schema(description = "Total number of food items successfully deleted", example = "3")
    private int deletedCount;

    @Schema(description = "Total number of food IDs requested for deletion", example = "4")
    private int requestedCount;

    @Schema(description = "List of IDs of deleted food items", example = "[1, 2, 3]")
    private List<Integer> deletedIds;

    @Schema(description = "List of IDs that were not found", example = "[99]")
    private List<Integer> notFoundIds;
}
