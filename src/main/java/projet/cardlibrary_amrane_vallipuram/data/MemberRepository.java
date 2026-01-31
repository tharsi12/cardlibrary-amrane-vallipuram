package projet.cardlibrary_amrane_vallipuram.data;

import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, Long> {
    Member findByUsername(String username);
}
