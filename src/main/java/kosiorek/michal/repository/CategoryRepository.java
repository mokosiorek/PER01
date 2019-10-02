package kosiorek.michal.repository;

import kosiorek.michal.model.Category;
import kosiorek.michal.repository.generic.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    Optional<Category> findByName(String name);

}
