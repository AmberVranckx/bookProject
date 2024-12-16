package fact.it.frontendservice.service;

import fact.it.frontendservice.model.Book;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class FrontEndService {
    @Value("${api.gateway.url}")
    private String apiGatewayUrl;

    private final WebClient webClient;
//    private final OAuth2AuthorizedClientService authorizedClientService;

    public FrontEndService(WebClient webClient){
        this.webClient = webClient;
//        this.authorizedClientService = authorizedClientService;
    }


    public List<Book> getBooks(){
        return webClient
                .get()
                .uri("/books")
                .retrieve()
                .bodyToFlux(Book.class)
                .collectList()
                .block();
    }

    public Book createBook(Book book){
//        OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(
//                authentication.getAuthorizedClientRegistrationId(), authentication.getName()
//        );
//        String token = authorizedClient.getAccessToken().getTokenValue();
//        System.out.println("Using token: " + token);
        return webClient
                .post()
               .uri( "/books")
//                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .bodyValue(book)
                .retrieve()
                .bodyToMono(Book.class)
                .block();
    }
}
