package projet.cardlibrary_amrane_vallipuram.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import projet.cardlibrary_amrane_vallipuram.data.Card;

@Service
public class CardClientService {

    private final WebClient webClient;

    public CardClientService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build(); 
    }
    

    public List<Card> getCardsByNames(List<String> names) {
        return names.stream()
                .map(name -> webClient.get()
                        .uri("/cards/{name}", name)
                        .retrieve()
                        .bodyToMono(Card.class)
                        .block())
                .toList();
    }

    public void buyCard(String cardName, Long memberId) {
    webClient.post()
        .uri("/cards/{name}/buy?memberId={id}", cardName, memberId)
        .retrieve()
        .toBodilessEntity()
        .block();
}

}
