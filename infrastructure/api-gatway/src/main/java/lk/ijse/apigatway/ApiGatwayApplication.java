package lk.ijse.apigatway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatwayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatwayApplication.class, args);
    }


    @Configuration
    public class WebClientConfig {

        @Bean
        public WebClient.Builder webClientBuilder() {
            return WebClient.builder();
        }
    }

}
