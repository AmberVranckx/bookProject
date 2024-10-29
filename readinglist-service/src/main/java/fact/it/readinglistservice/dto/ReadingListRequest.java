package fact.it.readinglistservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadingListRequest {
    private List<ReadingListLineDto> readingListLineDto;
}
