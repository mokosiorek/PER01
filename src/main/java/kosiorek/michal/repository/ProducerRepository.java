package kosiorek.michal.repository;

import kosiorek.michal.model.Producer;
import kosiorek.michal.model.Product;
import kosiorek.michal.repository.generic.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProducerRepository extends CrudRepository<Producer,Long> {

    Optional<Producer> findProducerByNameTradeAndCountryName(String producerName, String tradeName, String countryName);


}
