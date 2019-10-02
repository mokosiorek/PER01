package kosiorek.michal.repository;

import kosiorek.michal.model.Product;
import kosiorek.michal.repository.generic.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product,Long> {

    Optional<Product> findProductByNameCategoryAndProducerName(String productName, String categoryName, String producerName);
    List<Product> findProductsOrderedByCustomerGroupedByProducer(String customerName, String customerSurname, String countryName);

}
