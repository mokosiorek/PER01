package kosiorek.michal.repository;

import kosiorek.michal.model.CustomerOrder;
import kosiorek.michal.model.Product;
import kosiorek.michal.repository.generic.CrudRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface CustomerOrderRepository extends CrudRepository<CustomerOrder, Long> {

    List<CustomerOrder> findAllOrdersWithProductsOfMaxPriceInCategories();
    List<CustomerOrder> findAllOrdersOrderedByCustomersFromCountryAndOfAgeBetween(String countryName, Integer ageFrom, Integer ageTo);
    List<CustomerOrder> findOrdersWithDateBetweenAndTotalPriceAbove(LocalDate dateFrom, LocalDate dateTo, BigDecimal amount);
    List<CustomerOrder> findOrdersWithTradeNameOfProducerGiven(String tradeName);

}
