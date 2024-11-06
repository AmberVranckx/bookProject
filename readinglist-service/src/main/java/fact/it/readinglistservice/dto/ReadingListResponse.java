package fact.it.readinglistservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
//Response to a request
public class ReadingListResponse {
    private String readingListNumber;
    private String userId;
    private String username;
    private List<ReadingListLineDto> readingListLines;
}
