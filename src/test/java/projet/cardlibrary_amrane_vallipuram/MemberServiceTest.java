package projet.cardlibrary_amrane_vallipuram;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import projet.cardlibrary_amrane_vallipuram.data.Member;
import projet.cardlibrary_amrane_vallipuram.data.MemberRepository;
import projet.cardlibrary_amrane_vallipuram.exception.MemberNotFoundException;
import projet.cardlibrary_amrane_vallipuram.service.MemberService;

class MemberServiceTest {

    private final MemberRepository memberRepository = mock(MemberRepository.class);
    private final MemberService memberService = new MemberService(memberRepository);

    @Test
    void shouldReturnMemberById() throws MemberNotFoundException {
        Member member = new Member();
        member.setId(1L);
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));

        Member result = memberService.getMemberById(1L);
        assertEquals(1L, result.getId());
    }

    @Test
    void shouldThrowExceptionIfMemberNotFound() {
        when(memberRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(MemberNotFoundException.class, () -> memberService.getMemberById(99L));
    }

    @Test
    void shouldAddMemberSuccessfully() {
        Member member = new Member();
        member.setUsername("tachfine");
        when(memberRepository.save(any(Member.class))).thenReturn(member);

        Member result = memberService.addMember(member);
        assertEquals("tachfine", result.getUsername());
    }
}
