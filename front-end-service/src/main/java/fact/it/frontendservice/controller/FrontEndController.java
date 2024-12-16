package fact.it.frontendservice.controller;

import fact.it.frontendservice.model.Book;
import fact.it.frontendservice.service.FrontEndService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class FrontEndController {
    private final FrontEndService frontEndService;

    public FrontEndController(FrontEndService frontEndService, OAuth2AuthorizedClientService oAuth2AuthorizedClientService){
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
        return "books";
    }

    @RequestMapping("/newBook")
    public String newBook(){
        return "newBook";
    }

    @RequestMapping("/createNewBook")
//    public String createNewBook(HttpServletRequest request, Model model, @AuthenticationPrincipal OAuth2AuthenticationToken authenticationToken){
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

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if(authentication instanceof OAuth2AuthenticationToken){
//            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
//            frontEndService.createBook(book, oauthToken);
//        }
//        else {
//            System.out.println("ERROR");
//        }
        frontEndService.createBook(book);
        List<Book> books = frontEndService.getBooks();
        model.addAttribute("books", books);

        return "/books";
    }


}
