package fact.it.readinglistservice.service;

import fact.it.readinglistservice.dto.*;
import fact.it.readinglistservice.model.ReadingList;
import fact.it.readinglistservice.model.ReadingListLine;
import fact.it.readinglistservice.repository.ReadingListRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.awt.print.Book;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ReadingListService {

    //Link with repository
    private final ReadingListRepository readingListRepository;

    //Link with webclient to make HTTP requests
    private final WebClient webClient;

    //Url from bookservice via application properties
    @Value("${bookservice.baseurl}")
    private String bookServiceBaseUrl;


    //Url from userservice via application properties
    @Value("${userservice.baseurl}")
    private String userServiceBaseUrl;


    //Get all readinglists
    public List<ReadingListResponse> getAllReadingLists(){
        //Get all readinglists from repository
        List<ReadingList> readingLists = readingListRepository.findAll();

        //Get all unique bookIds
        List<Long> bookIds = readingLists.stream()
                .flatMap(readingList -> readingList.getReadingListLine().stream()
//                        .peek(readingListLine -> System.out.println("ReadingListLine: " + readingListLine))
                        .map(ReadingListLine::getBookId))
                .distinct()
                .collect(Collectors.toList());

        //Get all unique userIds
        List<String> userIds = readingLists.stream()
                .map(ReadingList::getUserId)
                .distinct()
                .collect(Collectors.toList());


        //Get all details from the bookservice via bookIds
        BookResponse[] bookResponseArray = webClient.get()
                .uri("http://" + bookServiceBaseUrl + "/api/book",
                        uriBuilder -> uriBuilder.queryParam("BookId", bookIds).build())
                .retrieve()
                .bodyToMono(BookResponse[].class)
                .block();

        //Get all details from the userservice via userIds
        UserResponse[] userResponseArray = webClient.get()
                .uri("http://" + userServiceBaseUrl + "/api/user",
                        uriBuilder -> uriBuilder.queryParam("UserId", userIds).build())
                .retrieve()
                .bodyToMono(UserResponse[].class)
                .block();

        //Response
        return readingLists.stream()
                .map(readingList -> {
                    //Map each readinglist to readinglistdto
                    List<ReadingListLineDto> readingListLineDtos = readingList.getReadingListLine().stream()
                            .map(readingListLine -> {
                                //Find details for given bookId
                                BookResponse book = Arrays.stream(bookResponseArray)
                                        .filter(b -> b.getId().equals(readingListLine.getBookId()))
                                        .findFirst()
                                        .orElse(null);

                                //Make dto for each readinglistline
                                return new ReadingListLineDto(
                                        readingListLine.getId(),
                                        readingListLine.getRating(),
                                        readingListLine.getFeedback(),
                                        book != null ? book.getName() : null,
                                        readingListLine.getBookId());
                            })
                            .collect(Collectors.toList());

                    //Find details for given userId
                    UserResponse user = Arrays.stream(userResponseArray)
                            .filter(u -> u.getId().equals(readingList.getUserId()))
                            .findFirst()
                            .orElse(null);

                    //Return readinglistresponse
                    return new ReadingListResponse(
                            readingList.getReadingListNumber(),
                            readingList.getUserId(),
                            user != null ? user.getFirstname() +  " " + user.getLastname() : "Unknown",
                            readingListLineDtos
                    );
                })
                .collect(Collectors.toList());



    }

    //Create new readinglist
    public boolean createReadingList(ReadingListRequest readingListRequest){
        //Create new readinglist object
        ReadingList readingList = new ReadingList();
        //Set new readinglistnumber
        readingList.setReadingListNumber(UUID.randomUUID().toString());
        //Set userId from request
        readingList.setUserId(readingListRequest.getUserId());

        //Map readinglistlinedto to readinglistline
        List<ReadingListLine> readingListLines = readingListRequest.getReadingListLineDto()
                .stream()
                .map(this::mapToReadingListLine)
                .toList();

        readingList.setReadingListLine(readingListLines);

        //Get all bookIds from readinglistlines
        List<Long> bookIds = readingList.getReadingListLine().stream()
                .map(ReadingListLine::getBookId)
                .toList();

        //Get details from bookservice via bookIds
        BookResponse[] bookResponseArray = webClient.get()
                .uri("http://" + bookServiceBaseUrl + "/api/book",
                        uriBuilder -> uriBuilder.queryParam("bookId", bookIds).build())
                .retrieve()
                .bodyToMono(BookResponse[].class)
                .block();

        //Map book details for each readinglistline
        readingList.getReadingListLine().stream()
                .map(readingListLine -> {
                    BookResponse book = Arrays.stream(bookResponseArray)
                            .filter(b -> b.getId().equals(readingListLine.getId()))
                            .findFirst()
                            .orElse(null);
                    if(book != null){
                        readingListLine.setBookId(book.getId());
                    }
                    return readingListLine;
                })
                .collect(Collectors.toList());

        //Save new readinglist
        readingListRepository.save(readingList);
        //readinglist was succesfully created
        return true;
    }

    //Method to map readinglistlinedto to readinglistline
    private ReadingListLine mapToReadingListLine(ReadingListLineDto readingListLineDto){
        ReadingListLine readingListLine = new ReadingListLine();
        readingListLine.setFeedback(readingListLineDto.getFeedback());
        readingListLine.setRating(readingListLineDto.getRating());
        readingListLine.setBookId(readingListLineDto.getBookId());
        return readingListLine;
    }
}
