package projet.cardlibrary_amrane_vallipuram.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import projet.cardlibrary_amrane_vallipuram.data.Card;
import projet.cardlibrary_amrane_vallipuram.exception.CardNotFoundException;
import projet.cardlibrary_amrane_vallipuram.service.CardService;

@RestController
public class CardWebService {

    private final Logger logger = LoggerFactory.getLogger(CardWebService.class);
    private final CardService cardService;

    public CardWebService(CardService cardService) {
        this.cardService = cardService;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CardNotFoundException.class)
    public void handleNotFound() { }

    // GET : liste des cartes
    @GetMapping("/cards")
    public Iterable<Card> getAllCards() {
        return cardService.getAllCards();
    }

    // GET : détail d'une carte
    @GetMapping("/cards/{name}")
    public Card getCardDetail(@PathVariable String name) throws CardNotFoundException {
        logger.info("Détail carte : " + name);
        return cardService.getCard(name);
    }


    // POST : ajouter une carte
    @PostMapping("/cards")
    @ResponseStatus(HttpStatus.CREATED)
    public Card addCard(@RequestBody Card card) {
        logger.info("Ajout d'une nouvelle carte : " + card.getName());
        return cardService.addCard(card);
    }

    // DELETE : supprimer une carte
    @DeleteMapping("/cards/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCard(@PathVariable String name) throws CardNotFoundException {
        logger.info("Suppression de la carte : " + name);
        cardService.deleteCard(name);
    }

    @PostMapping("/cards/{name}/buy")
    public void buyCard(@PathVariable String name, @RequestParam Long memberId) throws CardNotFoundException {
        cardService.buyCard(name, memberId);
    }
}
