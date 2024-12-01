package fact.it.bookservice;

import fact.it.bookservice.dto.BookRequest;
import fact.it.bookservice.dto.BookResponse;
import fact.it.bookservice.model.Book;
import fact.it.bookservice.repository.BookRepository;
import fact.it.bookservice.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceApplicationTests {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;
    @Test
    public void testCreateBook() {
        //Arrange
        BookRequest bookRequest = new BookRequest();
        bookRequest.setName("Test name");
        bookRequest.setIsbn("9789076174105");
        bookRequest.setAuthor("Test author");
        bookRequest.setDescription("Test description");

        //Act
        bookService.createBook(bookRequest);

        //Assert
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    public void testGetAllBooks(){
        //Arrange
        Book book = new Book();
        book.setId(1L);
        book.setName("Test name");
        book.setAuthor("Test author");
        book.setIsbn("9789076174105");
        book.setDescription("Test description");

        when(bookRepository.findAll()).thenReturn(Arrays.asList(book));

        //Act
        List<BookResponse> books = bookService.getAllBooks();

        //Assert
        assertEquals("Test name", books.get(0).getName());
        assertEquals("Test description", books.get(0).getDescription());
        assertEquals("Test author", books.get(0).getAuthor());
        assertEquals("9789076174105", books.get(0).getIsbn());
        assertEquals(1, books.size());

        verify(bookRepository, times(1)).findAll();

    }

    @Test
    public void testUpdateBook(){
        //Arrange
        long bookId = 1;

        Book book = new Book();
        book.setName("Test name");
        book.setIsbn("9789076174105");
        book.setAuthor("Test author");
        book.setDescription("Test description");

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        //Act
        BookRequest bookRequestUpdate = new BookRequest();
        bookRequestUpdate.setName("Test name update");
        bookRequestUpdate.setAuthor("Test author update");
        bookRequestUpdate.setDescription("Test description update");
        bookRequestUpdate.setIsbn("123456");

        bookService.updateBook(bookRequestUpdate,bookId);
        verify(bookRepository, times(1)).findById(bookId);
        verify(bookRepository, times(1)).save(any(Book.class));


        //Assert
        assertEquals("Test name update",book.getName());
        assertEquals("Test description update", book.getDescription());
        assertEquals("Test author update", book.getAuthor());
        assertEquals("123456",book.getIsbn());

    }

    @Test
    public void testDeleteBook(){
        //Arrange
        long bookId = 1L;

        //Assert
        bookService.deleteBook(bookId);

        //Act
        verify(bookRepository, times(1)).deleteById(bookId);
    }

}
