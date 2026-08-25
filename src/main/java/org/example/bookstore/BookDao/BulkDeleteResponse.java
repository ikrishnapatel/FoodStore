package org.example.bookstore.BookDao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BulkDeleteResponse {
    private int deletedCount;
    private int requestedCount;
    private List<Integer> deletedIds;
    private List<Integer> notFoundIds;
}
