package fact.it.readinglistservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadingListResponse {
    private String readingListNumber;
    private List<ReadingListLineDto> readingListLines;
}
