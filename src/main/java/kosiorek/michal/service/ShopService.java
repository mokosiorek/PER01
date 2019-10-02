package kosiorek.michal.service;

import kosiorek.michal.dto.CountryDto;
import kosiorek.michal.dto.ShopDto;
import kosiorek.michal.exceptions.AppException;
import kosiorek.michal.model.Country;
import kosiorek.michal.model.Shop;
import kosiorek.michal.model.Stock;
import kosiorek.michal.repository.CountryRepository;
import kosiorek.michal.repository.ShopRepository;
import kosiorek.michal.repository.StockRepository;
import kosiorek.michal.validators.ShopDtoValidator;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;
    private final CountryRepository countryRepository;
    private final StockRepository stockRepository;

    public ShopDto addShop(ShopDto shopDto){

        if(shopDto == null){
            throw new AppException("add shop - shop object is null");
        }

        if(shopDto.getName() == null){
            throw new AppException("add shop - shop name is null");
        }

        CountryDto countryDto = shopDto.getCountryDto();

        if(countryDto == null){
            throw new AppException("add shop - countrydto object is null");
        }

        if (countryDto.getName() == null) {
            throw new AppException("add shop - countrydto name is null");
        }

        ShopDtoValidator shopDtoValidator = new ShopDtoValidator();
        Map<String,String> errors = shopDtoValidator.validate(shopDto);
        if(shopDtoValidator.hasErrors()){
            throw new AppException("add shop - shop validation not correct " + errors.toString());
        }

        if(shopRepository.findShopByNameAndCountryName(shopDto.getName(),countryDto.getName()).isPresent()){
            throw new AppException("add shop - shop with given name and country already exists");
        }

        Country country = countryRepository
                .findByName(countryDto.getName())
                .orElseThrow(() -> new AppException("add shop - no country with name " + countryDto.getName()));

        Shop shop = ModelMapper.fromShopDtoToShop(shopDto);
        shop.setCountry(country);

        return shopRepository.addOrUpdate(shop)
                .map(ModelMapper::fromShopToShopDto)
                .orElseThrow(() -> new AppException("add shop - exception while inserting shop"));

    }

    public List<ShopDto> getShopsWithProductsFromOtherCountries(){

        // najpierw zostaw zamowienia, ktore maja w sobie rozny kraj produktu oraz shop
        // select * from producer pr join product p on pr.id = p.producer_id join stock s on p.id = s.product_id join shop sh on s.shop_id = sh.id;

        // jpql
        // select distinct s from stock s join s.product p join pr.producer pr join s.shop sh join pr.country pr_c join shop.country s_c where pr.c.id = s_c.id;
        // teraz stosujac strumien przemapuj na strumien shop i potem distinct

        List<Stock> stocks = stockRepository.findStocksWithProductsFromOtherCountries();

        return stocks.stream().map(Stock::getShop).distinct()
                .map(ModelMapper::fromShopToShopDto)
                .collect(Collectors.toList());

    }

    public List<ShopDto> getAllShops(){
      return shopRepository.findAll().stream()
              .map(ModelMapper::fromShopToShopDto)
              .collect(Collectors.toList());
    }

    public ShopDto editShop(ShopDto shopDto){

        if(shopDto==null){
            throw new AppException("edit shop - shop object null");
        }

        Shop shop = ModelMapper.fromShopDtoToShop(shopDto);
        return shopRepository.addOrUpdate(shop).map(ModelMapper::fromShopToShopDto)
                .orElseThrow(() -> new AppException("editing category - exception while editing category"));

    }

}
