package projet.cardlibrary_amrane_vallipuram;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import projet.cardlibrary_amrane_vallipuram.data.Card;
import projet.cardlibrary_amrane_vallipuram.data.CardRepository;
import projet.cardlibrary_amrane_vallipuram.data.Member;
import projet.cardlibrary_amrane_vallipuram.data.MemberRepository;
import projet.cardlibrary_amrane_vallipuram.exception.CardNotFoundException;
import projet.cardlibrary_amrane_vallipuram.service.CardService;

class CardServiceTest {

    private CardRepository cardRepository;
    private MemberRepository memberRepository;
    private CardService cardService;

    @BeforeEach
    void setup() {
        cardRepository = mock(CardRepository.class);
        memberRepository = mock(MemberRepository.class);
        cardService = new CardService(cardRepository, memberRepository);
    }

    @Test
    void shouldReturnCardWhenFound() throws CardNotFoundException {
        Card card = new Card();
        card.setName("Pikachu");
        when(cardRepository.findByName("Pikachu")).thenReturn(card);

        Card result = cardService.getCard("Pikachu");
        assertEquals("Pikachu", result.getName());
    }

    @Test
    void shouldThrowExceptionWhenCardNotFound() {
        when(cardRepository.findByName("Unknown")).thenReturn(null);

        assertThrows(CardNotFoundException.class, () -> {
            cardService.getCard("Unknown");
        });
    }

    @Test
    void shouldBuyCardSuccessfully() throws CardNotFoundException {
        Card card = new Card();
        card.setName("Pikachu");
        card.setSold(false);

        Member member = new Member();
        member.setId(1L);

        when(cardRepository.findByName("Pikachu")).thenReturn(card);
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));

        cardService.buyCard("Pikachu", 1L);

        assertTrue(card.isSold());
        assertEquals(member, card.getOwner());
        verify(cardRepository).save(card);
    }
}
