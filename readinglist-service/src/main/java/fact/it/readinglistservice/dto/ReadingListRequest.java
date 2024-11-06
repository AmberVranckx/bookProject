package fact.it.readinglistservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
//data to make a new readinglist
public class ReadingListRequest {
    private String userId;
    private List<ReadingListLineDto> readingListLineDto;
}
