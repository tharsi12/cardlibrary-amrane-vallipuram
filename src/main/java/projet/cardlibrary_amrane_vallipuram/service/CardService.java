package projet.cardlibrary_amrane_vallipuram.service;

import org.springframework.stereotype.Service;

import projet.cardlibrary_amrane_vallipuram.data.Card;
import projet.cardlibrary_amrane_vallipuram.data.CardRepository;
import projet.cardlibrary_amrane_vallipuram.data.Member;
import projet.cardlibrary_amrane_vallipuram.data.MemberRepository;
import projet.cardlibrary_amrane_vallipuram.exception.CardNotFoundException;

@Service
public class CardService {

    private final CardRepository cardRepository;
    private final MemberRepository memberRepository;

    public CardService(CardRepository cardRepository, MemberRepository memberRepository) {
        this.cardRepository = cardRepository;
        this.memberRepository = memberRepository;
    }

    public Iterable<Card> getAllCards() {
        return cardRepository.findAll();
    }

    public Card getCard(String name) throws CardNotFoundException {
        Card card = cardRepository.findByName(name);
        if (card == null) {
            throw new CardNotFoundException(name);
        }
        return card;
    }

    public void buyCard(String name, Long memberId) throws CardNotFoundException {
        Card card = getCard(name);
        if (card.isSold()) {
            throw new RuntimeException("❌ Carte déjà vendue !");
        }

        Member buyer = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("❌ Membre introuvable !"));

        // 🔹 Met à jour la carte
        card.setOwner(buyer);
        card.setSold(true);
        cardRepository.save(card);

        // 🔹 Met à jour la liste des cartes du membre
        var ownedCards = buyer.getOwnedCards();
        if (ownedCards == null) {
            ownedCards = new java.util.ArrayList<>();
        }
        ownedCards.add(card.getName());
        buyer.setOwnedCards(ownedCards);
        memberRepository.save(buyer);
    }

    public void makeCardAvailable(String name) throws CardNotFoundException {
        Card card = getCard(name);
        card.setSold(false);
        card.setOwner(null);
        cardRepository.save(card);
    }

    public Card addCard(Card card) {
        card.setSold(false);
        return cardRepository.save(card);
    }

    public void deleteCard(String name) throws CardNotFoundException {
        Card card = getCard(name);
        cardRepository.delete(card);
    }
}
