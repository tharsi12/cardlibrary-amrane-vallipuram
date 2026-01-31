package projet.cardlibrary_amrane_vallipuram.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import projet.cardlibrary_amrane_vallipuram.data.Card;
import projet.cardlibrary_amrane_vallipuram.data.Member;
import projet.cardlibrary_amrane_vallipuram.exception.MemberNotFoundException;
import projet.cardlibrary_amrane_vallipuram.service.CardClientService;
import projet.cardlibrary_amrane_vallipuram.service.MemberService;


@RestController
@RequestMapping("/members")
public class MemberWebService {

    private final MemberService memberService;
    private final CardClientService cardClientService;

    public MemberWebService(MemberService memberService, CardClientService cardClientService) {
        this.memberService = memberService;
        this.cardClientService = cardClientService;

    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(MemberNotFoundException.class)
    public void handleNotFound() { }

    @GetMapping
    public Iterable<Member> getAllUsers() {
        return memberService.getAllMembers();
    }

    @GetMapping("/{id}")
    public Member getUserById(@PathVariable Long id) throws MemberNotFoundException {
        return memberService.getMemberById(id);
    }

    @PostMapping
    public Member addUser(@RequestBody Member user) {
        return memberService.addMember(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) throws MemberNotFoundException {
        memberService.deleteMember(id);
    }

    @GetMapping("/{id}/cards")
    public List<Card> getMemberCards(@PathVariable Long id) throws MemberNotFoundException {
        Member member = memberService.getMemberById(id);
        return cardClientService.getCardsByNames(member.getOwnedCards());
    }

    @PostMapping("/{memberId}/buy/{cardName}")
        public void buyCard(@PathVariable Long memberId, @PathVariable String cardName) throws Exception {
        cardClientService.buyCard(cardName, memberId);
}

}
