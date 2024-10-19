package fact.it.bookservice.service;

import fact.it.bookservice.dto.BookRequest;
import fact.it.bookservice.dto.BookResponse;
import fact.it.bookservice.model.Book;
import fact.it.bookservice.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public Book createBook(BookRequest bookRequest){
        Book book = Book.builder()
                .isbn(bookRequest.getIsbn())
                .name(bookRequest.getName())
                .description(bookRequest.getDescription())
                .author(bookRequest.getAuthor())
                .build();
        return bookRepository.save(book);
    }

    public List<BookResponse> getAllBooks(){
        List<Book> books = bookRepository.findAll();

        return books.stream().map(this::mapToBookResponse).toList();
    }

    private BookResponse mapToBookResponse(Book book){
        return BookResponse.builder()
                .id(book.getId())
                .name(book.getName())
                .author(book.getAuthor())
                .description(book.getDescription())
                .isbn(book.getIsbn())
                .build();
    }
}
