package fact.it.readinglistservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadingListLineDto {
    private Long id;
    private int rating;
    private String feedback;
    private String bookname;
    private String username;
    private Long bookId;
    private String userId;
}
