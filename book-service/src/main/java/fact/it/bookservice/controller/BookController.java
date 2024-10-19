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

//    @PutMapping("/{id}")
//    public ResponseEntity<Book> updateBook(@RequestBody Book updateBook, @PathVariable("id") long bookId){
//        Optional<Book> book = bookRepository.findById(bookId);
//        if (book.isPresent()){
//            Book newBook = book.get();
//            newBook.setDescription(updateBook.getDescription());
//            newBook.setIsbn(updateBook.getIsbn());
//            newBook.setAuthor(updateBook.getAuthor());
//            newBook.setName(updateBook.getName());
//            bookRepository.save(newBook);
//            return new ResponseEntity<>(newBook, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BookResponse> getAllBooks(){
        return bookService.getAllBooks();
    }
}
