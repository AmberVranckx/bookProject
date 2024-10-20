package fact.it.bookservice.service;

import fact.it.bookservice.dto.BookRequest;
import fact.it.bookservice.dto.BookResponse;
import fact.it.bookservice.model.Book;
import fact.it.bookservice.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

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

    public ResponseEntity<Book> updateBook(BookRequest bookRequestUpdate, long bookId){
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isPresent()){
            Book newBook = book.get();
            newBook.setDescription(bookRequestUpdate.getDescription());
            newBook.setIsbn(bookRequestUpdate.getIsbn());
            newBook.setAuthor(bookRequestUpdate.getAuthor());
            newBook.setName(bookRequestUpdate.getName());
            bookRepository.save(newBook);
            return new ResponseEntity<>(newBook, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
