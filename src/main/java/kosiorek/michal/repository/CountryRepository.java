package kosiorek.michal.repository;

import kosiorek.michal.model.Country;
import kosiorek.michal.repository.generic.CrudRepository;

import java.util.Optional;

public interface CountryRepository extends CrudRepository<Country, Long> {

    Optional<Country> findByName(String name);
}
