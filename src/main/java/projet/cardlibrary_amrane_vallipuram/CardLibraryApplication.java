package projet.cardlibrary_amrane_vallipuram;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

import projet.cardlibrary_amrane_vallipuram.data.CardRepository;
import projet.cardlibrary_amrane_vallipuram.data.MemberRepository;

@SpringBootApplication
public class CardLibraryApplication {

    public static void main(String[] args) {
        SpringApplication.run(CardLibraryApplication.class, args);
    }

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public CommandLineRunner initData(CardRepository cardRepository, MemberRepository memberRepository) {
        return (args) -> {

        };
    }
}
