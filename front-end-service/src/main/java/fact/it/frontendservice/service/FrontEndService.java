package fact.it.frontendservice.service;

import fact.it.frontendservice.model.Book;
import fact.it.frontendservice.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class FrontEndService {
    @Value("${api.baseurl}")
    private String apiUrl;

    private final WebClient webClient;

    public FrontEndService(WebClient webClient){
        this.webClient = webClient;
    }


    public List<Book> getBooks(){
        return webClient
                .get()
                .uri("http://" + apiUrl + "/books")
                .retrieve()
                .bodyToFlux(Book.class)
                .collectList()
                .block();
    }

    public List<User> getUsers(){
        return webClient
                .get()
                .uri("http://" + apiUrl + "/users")
                .retrieve()
                .bodyToFlux(User.class)
                .collectList()
                .block();
    }



    public Book createBook(Book book){
        return webClient
                .post()
               .uri( "http://" + apiUrl + "/books")
                .bodyValue(book)
                .retrieve()
                .bodyToMono(Book.class)
                .block();
    }
}
