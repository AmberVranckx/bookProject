package fact.it.frontendservice.service;

import fact.it.frontendservice.model.Book;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class FrontEndService {
    @Value("${bookService.baseurl}")
    private String bookServiceUrl;

    private final WebClient webClient;

    public FrontEndService(WebClient webClient){
        this.webClient = webClient;
    }


    public List<Book> getBooks(){
        return webClient
                .get()
                .uri("http://" + bookServiceUrl + "/api/book")
                .retrieve()
                .bodyToFlux(Book.class)
                .collectList()
                .block();
    }

    public Book createBook(Book book){
        return webClient
                .post()
               .uri( "http://" + bookServiceUrl + "/api/book")
                .bodyValue(book)
                .retrieve()
                .bodyToMono(Book.class)
                .block();
    }
}
