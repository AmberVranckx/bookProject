package fact.it.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

//    @Bean
//    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity) {
//        serverHttpSecurity
//                .authorizeExchange(exchange ->
//                        exchange.pathMatchers(HttpMethod.GET,"/books")
//                                .permitAll()
//                                .anyExchange()
//                                .authenticated()
//                )
//                .oauth2ResourceServer(oauth2 -> oauth2
//                        .jwt(withDefaults())
//                );
//        return serverHttpSecurity.build();
//    }

        @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .authorizeHttpRequests(authorizeRequests -> {
                    authorizeRequests.requestMatchers("/").permitAll();
                    authorizeRequests.requestMatchers("/books").permitAll();
                    authorizeRequests.anyRequest().authenticated();
                })
                .oauth2Login(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
                .build();
    }
}
