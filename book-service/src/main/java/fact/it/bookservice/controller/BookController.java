package fact.it.bookservice.controller;

import fact.it.bookservice.dto.BookRequest;
import fact.it.bookservice.dto.BookResponse;
import fact.it.bookservice.model.Book;
import fact.it.bookservice.repository.BookRepository;
import fact.it.bookservice.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Book createBook(@RequestBody BookRequest bookRequest){
        return bookService.createBook(bookRequest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@RequestBody BookRequest bookRequestUpdate, @PathVariable("id") long bookId){
        return bookService.updateBook(bookRequestUpdate, bookId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BookResponse> getAllBooks(){
        return bookService.getAllBooks();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBook(@PathVariable("id") long bookId) {
         bookService.deleteBook(bookId);
    }
}
