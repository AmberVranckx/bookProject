package fact.it.frontendservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class FrontEndServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FrontEndServiceApplication.class, args);
    }

}
