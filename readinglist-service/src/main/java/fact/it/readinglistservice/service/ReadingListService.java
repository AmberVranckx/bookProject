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
    private final ReadingListRepository readingListRepository;
    private final WebClient webClient;

    @Value("${bookservice.baseurl}")
    private String bookServiceBaseUrl;

    @Value("${userservice.baseurl}")
    private String userServiceBaseUrl;

    public List<ReadingListResponse> getAllReadingLists(){
        List<ReadingList> readingLists = readingListRepository.findAll();


//        return readingLists.stream()
//                .map(readingList -> new ReadingListResponse(
//                        readingList.getReadingListNumber(),
//                        mapToReadingListLineDto(readingList.getReadingListLine())
//                ))
//                .collect(Collectors.toList());


        List<Long> bookIds = readingLists.stream()
                .flatMap(readingList -> readingList.getReadingListLine().stream()
                        .peek(readingListLine -> System.out.println("ReadingListLine: " + readingListLine))
                        .map(ReadingListLine::getBookId))
                .distinct()
                .collect(Collectors.toList());

        List<String> userIds = readingLists.stream()
                .flatMap(readingList -> readingList.getReadingListLine().stream()
                        .peek(readingListLine -> System.out.println("ReadingListLine: " + readingListLine))
                        .map(ReadingListLine::getUserId))
                .distinct()
                .collect(Collectors.toList());


        BookResponse[] bookResponseArray = webClient.get()
                .uri("http://" + bookServiceBaseUrl + "/api/book",
                        uriBuilder -> uriBuilder.queryParam("BookId", bookIds).build())
                .retrieve()
                .bodyToMono(BookResponse[].class)
                .block();

        UserResponse[] userResponseArray = webClient.get()
                .uri("http://" + userServiceBaseUrl + "/api/user",
                        uriBuilder -> uriBuilder.queryParam("UserId", userIds).build())
                .retrieve()
                .bodyToMono(UserResponse[].class)
                .block();


        return readingLists.stream()
                .map(readingList -> {
                    List<ReadingListLineDto> readingListLineDtos = readingList.getReadingListLine().stream()
                            .map(readingListLine -> {
                                BookResponse book = Arrays.stream(bookResponseArray)
                                        .filter(b -> b.getId().equals(readingListLine.getBookId()))
                                        .findFirst()
                                        .orElse(null);

                                UserResponse user = Arrays.stream(userResponseArray)
                                        .filter(u -> u.getId().equals(readingListLine.getUserId()))
                                        .findFirst()
                                        .orElse(null);

                                System.out.println("ReadingListLine Book ID: " + readingListLine.getBookId());
                                System.out.println("ReadingListLine User ID: " + readingListLine.getUserId());

                                return new ReadingListLineDto(
                                        readingListLine.getId(),
                                        readingListLine.getRating(),
                                        readingListLine.getFeedback(),
                                        book != null ? book.getName() : null,
                                        user != null ? (user.getFirstname() + " " + user.getLastname()) : null,
                                        readingListLine.getBookId(),
                                        readingListLine.getUserId());
                            })
                            .collect(Collectors.toList());
                    return new ReadingListResponse(
                            readingList.getReadingListNumber(),
                            readingListLineDtos
                    );
                })
                .collect(Collectors.toList());



    }

    public boolean createReadingList(ReadingListRequest readingListRequest){
        ReadingList readingList = new ReadingList();
        readingList.setReadingListNumber(UUID.randomUUID().toString());

        List<ReadingListLine> readingListLines = readingListRequest.getReadingListLineDto()
                .stream()
                .map(this::mapToReadingListLine)
                .toList();

        readingList.setReadingListLine(readingListLines);

        List<Long> bookIds = readingList.getReadingListLine().stream()
                .map(ReadingListLine::getBookId)
                .toList();

        List<String> userIds = readingList.getReadingListLine().stream()
                .map(ReadingListLine::getUserId)
                .toList();

        BookResponse[] bookResponseArray = webClient.get()
                .uri("http://" + bookServiceBaseUrl + "/api/book",
                        uriBuilder -> uriBuilder.queryParam("bookId", bookIds).build())
                .retrieve()
                .bodyToMono(BookResponse[].class)
                .block();

        UserResponse[] userResponseArray = webClient.get()
                .uri("http://" + userServiceBaseUrl + "/api/user",
                        uriBuilder -> uriBuilder.queryParam("userId", userIds).build())
                .retrieve()
                .bodyToMono(UserResponse[].class)
                .block();

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

        readingListRepository.save(readingList);
        return true;
    }

//    private List<ReadingListLineDto> mapToReadingListLineDto(List<ReadingListLine> readingListLines, Map<Long, String> bookNames, Map<Long, String> usernames){
//        return readingListLines.stream()
//                .map(readingListLine -> new ReadingListLineDto(
//                        readingListLine.getId(),
//                        readingListLine.getRating(),
//                        readingListLine.getFeedback(),
//                        readingListLine.getBookId(),
//                        readingListLine.getUserId()
//                ))
//                .collect(Collectors.toList());
//    }

    private ReadingListLine mapToReadingListLine(ReadingListLineDto readingListLineDto){
        ReadingListLine readingListLine = new ReadingListLine();
        readingListLine.setFeedback(readingListLineDto.getFeedback());
        readingListLine.setRating(readingListLineDto.getRating());
        readingListLine.setBookId(readingListLineDto.getBookId());
        readingListLine.setUserId(readingListLineDto.getUserId());
        return readingListLine;
    }
}
