package fact.it.readinglistservice;

import fact.it.readinglistservice.dto.*;
import fact.it.readinglistservice.model.ReadingList;
import fact.it.readinglistservice.model.ReadingListLine;
import fact.it.readinglistservice.repository.ReadingListRepository;
import fact.it.readinglistservice.service.ReadingListService;
import org.apache.catalina.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.awt.print.Book;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReadinglistServiceApplicationTests {

	@InjectMocks
	private ReadingListService readingListService;

	@Mock
	private ReadingListRepository readingListRepository;

	@Mock
	private WebClient webClient;

	@Mock
	private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

	@Mock
	private WebClient.RequestHeadersSpec requestHeadersSpec;

	@Mock
	private WebClient.ResponseSpec responseSpec;

	@BeforeEach
	void setUp(){
		ReflectionTestUtils.setField(readingListService, "bookServiceBaseUrl", "http://localhost:8082");
		ReflectionTestUtils.setField(readingListService, "userServiceBaseUrl", "http://localhost:8080");
	}
	@Test
	public void testCreateReadinglist() {
		//Arrange
		Long id = 1L;
		Integer rating = 4;
		String feedback = "Good";
		Long bookId = 1L;
		String userId = "1";
		String bookName = "Test book";


		ReadingListRequest readingListRequest = new ReadingListRequest();
		readingListRequest.setUserId(userId);

		ReadingListLineDto readingListLineDto = new ReadingListLineDto();
		readingListLineDto.setId(id);
		readingListLineDto.setRating(rating);
		readingListLineDto.setFeedback(feedback);
		readingListLineDto.setBookId(bookId);

		readingListRequest.setReadingListLineDto(Arrays.asList(readingListLineDto));

		BookResponse bookResponse = new BookResponse();
		bookResponse.setId(bookId);
		bookResponse.setName(bookName);

		UserResponse userResponse = new UserResponse();
		userResponse.setId(userId);


		ReadingList readingList = new ReadingList();
		readingList.setId(1L);
		readingList.setUserId(userId);

		ReadingListLine readingListLine = new ReadingListLine();
		readingListLine.setId(id);
		readingListLine.setRating(rating);
		readingListLine.setFeedback(feedback);
		readingListLine.setBookId(bookId);
		readingList.setReadingListLine(Arrays.asList(readingListLine));

		when(readingListRepository.save(any(ReadingList.class))).thenReturn(readingList);

		when(webClient.get()).thenReturn(requestHeadersUriSpec);
		when(requestHeadersUriSpec.uri(anyString(), any(Function.class))).thenReturn(requestHeadersSpec);
		when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
		when(responseSpec.bodyToMono(BookResponse[].class)).thenReturn(Mono.just(new BookResponse[]{bookResponse}));

		//Act
		boolean result = readingListService.createReadingList(readingListRequest);

		//Assert
		assertTrue(result);

		verify(readingListRepository, times(1)).save(any(ReadingList.class));
	}


	@Test
	public void testGetReadinglists(){
		//Arrange
		ReadingListLine readingListLine1 = new ReadingListLine(1L, 1L, 5, "Good");
		ReadingListLine readingListLine2 = new ReadingListLine(2L, 2L, 3, "Basic");

		ReadingList readingList1 = new ReadingList(1L, "1", "1",Arrays.asList(readingListLine1, readingListLine2));

		ReadingListLine readingListLine3 = new ReadingListLine(3L, 2L, 5, "Very good");
		ReadingListLine readingListLine4 = new ReadingListLine(4L, 1L, 2, "Not good");

		ReadingList readingList2 = new ReadingList(2L, "2", "2",Arrays.asList(readingListLine3, readingListLine4));

		when(readingListRepository.findAll()).thenReturn(Arrays.asList(readingList1, readingList2));

		BookResponse book1 = new BookResponse(1L, "123456", "book one", "book one test", "test author 1");
		BookResponse book2 = new BookResponse(2L, "654321", "book two", "book two test", "test author 2");

		UserResponse userResponse1 = new UserResponse("1", "Test", "one", "test@one.com", new Date(2004-9-12));
		UserResponse userResponse2 = new UserResponse("2", "Test", "Two", "test@two.com", new Date(2003-7-10));

		when(webClient.get()).thenReturn(requestHeadersUriSpec);
		when(requestHeadersUriSpec.uri(anyString(), any(Function.class))).thenReturn(requestHeadersSpec);
		when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
		when(responseSpec.bodyToMono(UserResponse[].class)).thenReturn(Mono.just(new UserResponse[]{userResponse1, userResponse2}));
		when(responseSpec.bodyToMono(BookResponse[].class)).thenReturn(Mono.just(new BookResponse[]{book1, book2}));

		//Act
		List<ReadingListResponse> result = readingListService.getAllReadingLists();

		//Assert
		assertEquals(2, result.size());
		assertEquals("1", result.get(0).getReadingListNumber());
		assertEquals("Test one", result.get(0).getUsername());
		assertEquals("1",result.get(0).getUserId());
		assertEquals(1L,result.get(0).getReadingListLines().get(0).getBookId());
		assertEquals(5,result.get(0).getReadingListLines().get(0).getRating());
		assertEquals("Good",result.get(0).getReadingListLines().get(0).getFeedback());
		assertEquals("book one",result.get(0).getReadingListLines().get(0).getBookname());
		assertEquals(1L,result.get(0).getReadingListLines().get(0).getId());

		assertEquals("2", result.get(1).getReadingListNumber());
		assertEquals("Test Two", result.get(1).getUsername());
		assertEquals("2",result.get(1).getUserId());
		assertEquals(2L,result.get(1).getReadingListLines().get(0).getBookId());
		assertEquals(5,result.get(1).getReadingListLines().get(0).getRating());
		assertEquals("Very good",result.get(1).getReadingListLines().get(0).getFeedback());
		assertEquals("book two",result.get(1).getReadingListLines().get(0).getBookname());
		assertEquals(3L,result.get(1).getReadingListLines().get(0).getId());

		verify(readingListRepository, times(1)).findAll();

	}

}
