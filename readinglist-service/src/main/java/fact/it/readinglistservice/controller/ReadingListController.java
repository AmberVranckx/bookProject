package fact.it.readinglistservice.controller;

import fact.it.readinglistservice.dto.ReadingListRequest;
import fact.it.readinglistservice.dto.ReadingListResponse;
import fact.it.readinglistservice.service.ReadingListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/readinglist")
@RequiredArgsConstructor
public class ReadingListController {
    private final ReadingListService readingListService;

    //Post new book
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public String createReadingList(@RequestBody ReadingListRequest readinglistRequest){
        boolean result = readingListService.createReadingList(readinglistRequest);
        return (result ? "Nieuwe leeslijst toegevoegd" : "Leeslijst toevoegen gefaald");
    }

    //Get all readinglists
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ReadingListResponse> getAllReadinglists(){
        return readingListService.getAllReadingLists();
    }
}
