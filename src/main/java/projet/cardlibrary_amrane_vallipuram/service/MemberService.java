package projet.cardlibrary_amrane_vallipuram.service;

import org.springframework.stereotype.Service;

import projet.cardlibrary_amrane_vallipuram.data.Member;
import projet.cardlibrary_amrane_vallipuram.data.MemberRepository;
import projet.cardlibrary_amrane_vallipuram.exception.MemberNotFoundException;

@Service
public class MemberService {

    private final MemberRepository userRepository;

    public MemberService(MemberRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Iterable<Member> getAllMembers() {
        return userRepository.findAll();
    }

    public Member getMemberById(Long id) throws MemberNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException(id));
    }

    public Member getMemberByUsername(String username) throws MemberNotFoundException {
        Member user = userRepository.findByUsername(username);
        if (user == null) {
            throw new MemberNotFoundException(username);
        }
        return user;
    }

    public Member addMember(Member user) {
        return userRepository.save(user);
    }

    public void deleteMember(Long id) throws MemberNotFoundException {
        if (!userRepository.existsById(id)) {
            throw new MemberNotFoundException(id);
        }
        userRepository.deleteById(id);
    }
}
