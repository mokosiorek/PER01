package kosiorek.michal.repository;

import kosiorek.michal.model.Customer;
import kosiorek.michal.repository.generic.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Optional<Customer> findCustomerByNameSurnameAndCountryName(String customerName,String customerSurname, String countryName);
    List<Customer> findAllFromCountry(String countryName);
    List<Customer> findCustomersWhoOrderedAtLeastOneProductFromCountryOfOrigin();

}
