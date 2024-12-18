package fact.it.frontendservice.controller;

import fact.it.frontendservice.model.Book;
import fact.it.frontendservice.service.FrontEndService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequestMapping("/api/frontend")
public class FrontEndController {
    private final FrontEndService frontEndService;

    public FrontEndController(FrontEndService frontEndService){
        this.frontEndService = frontEndService;
    }

    @RequestMapping("/")
    public String home(){
        return "index";
    }

    @RequestMapping("/books")
    public String getBooksPage(Model model){
        List<Book> books = frontEndService.getBooks();
        model.addAttribute("books", books);
        return "getbooks";
    }

    @RequestMapping("/newBook")
    public String newBook(){
        return "newBook";
    }

    @RequestMapping("/createNewBook")
    public String createNewBook(HttpServletRequest request, Model model){
        String name = request.getParameter("name");
        String author = request.getParameter("author");
        String description = request.getParameter("description");
        String isbn = request.getParameter("isbn");

        Book book = new Book();
        book.setName(name);
        book.setIsbn(isbn);
        book.setAuthor(author);
        book.setDescription(description);

        frontEndService.createBook(book);
        List<Book> books = frontEndService.getBooks();
        model.addAttribute("books", books);

        return "getbooks";
    }


}
