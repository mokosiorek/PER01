package kosiorek.michal.repository;

import kosiorek.michal.model.Trade;
import kosiorek.michal.repository.generic.CrudRepository;

import java.util.Optional;

public interface TradeRepository extends CrudRepository<Trade,Long> {

    Optional<Trade> findByName(String name);

}
