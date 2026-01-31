package projet.cardlibrary_amrane_vallipuram.data;

import org.springframework.data.repository.CrudRepository;

public interface CardRepository extends CrudRepository<Card, Long> {
    Card findByName(String name);
}
