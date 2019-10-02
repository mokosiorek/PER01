package kosiorek.michal.repository;

import kosiorek.michal.model.Shop;
import kosiorek.michal.repository.generic.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ShopRepository extends CrudRepository<Shop,Long> {

    Optional<Shop> findShopByNameAndCountryName(String shopName, String countryName);


}
