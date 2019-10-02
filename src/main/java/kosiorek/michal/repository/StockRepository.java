package kosiorek.michal.repository;

import kosiorek.michal.dto.ProductDto;
import kosiorek.michal.model.Product;
import kosiorek.michal.model.Stock;
import kosiorek.michal.repository.generic.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends CrudRepository<Stock,Long> {

    Optional<Stock> findByProductNameAndShop(String productName, String shopName);
    void findByIdAndAddToQuantity(Long id, Integer amount);
    List<Stock> findAllStocksWithProduct(String productName);
    List<Stock> findAllStocksWithProductInCountry(String productName, String countryName);
    List<Stock> findAllStocksWithProductOutsideCountry(String productName, String countryName);
    List<Stock> findStocksWithProductsFromOtherCountries();
    List<Stock> findStocksWithProductProducerTradeNameGiven(String tradeName);

}
