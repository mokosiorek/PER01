package kosiorek.michal;

import kosiorek.michal.dto.CountryDto;
import kosiorek.michal.dto.CustomerDto;
import kosiorek.michal.model.Customer;
import kosiorek.michal.model.CustomerOrder;
import kosiorek.michal.repository.*;
import kosiorek.michal.repository.impl.*;
import kosiorek.michal.service.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        /*CountryDto countryDto = CountryDto.builder().name("JAPONIA").build();
        CustomerDto customerDto = CustomerDto.builder().name("MACIEJ").surname("B").age(18).countryDto(countryDto).build();
        CustomerRepository customerRepository = new CustomerRepositoryImpl();
        CountryRepository countryRepository = new CountryRepositoryImpl();
        CustomerService customerService = new CustomerService(customerRepository,countryRepository);
        customerService.addCustomer(customerDto);

         */

        MenuService menuService = new MenuService();
        menuService.mainMenu();

        CustomerOrderRepositoryImpl customerOrderRepository = new CustomerOrderRepositoryImpl();
        ProductRepositoryImpl productRepository = new ProductRepositoryImpl();
        CustomerRepositoryImpl customerRepository = new CustomerRepositoryImpl();
        StockRepositoryImpl stockRepository = new StockRepositoryImpl();
        CategoryRepositoryImpl categoryRepository = new CategoryRepositoryImpl();
        CountryRepositoryImpl countryRepository = new CountryRepositoryImpl();
        ProducerRepositoryImpl producerRepository = new ProducerRepositoryImpl();
        ShopRepositoryImpl shopRepository = new ShopRepositoryImpl();
        TradeRepositoryImpl tradeRepository = new TradeRepositoryImpl();


        CustomerOrderService customerOrderService = new CustomerOrderService(customerOrderRepository, productRepository, customerRepository, stockRepository);
        ProductService productService = new ProductService(productRepository, categoryRepository, producerRepository, countryRepository, customerOrderRepository);
        ShopService shopService = new ShopService(shopRepository, countryRepository, stockRepository);
        ProducerService producerService = new ProducerService(producerRepository, countryRepository, tradeRepository,customerOrderRepository,stockRepository);
        CustomerService customerService = new CustomerService(customerRepository,countryRepository);

        //producerRepository.findProducersByTradeNameWhichProducedMoreThan("A",1L).forEach(System.out::println);

        //productRepository.findProductsOrderedByCustomerGroupedByProducer("JAN","NOWAK","POLSKA").forEach(System.out::println);

        //A OK
        //customerOrderService.getMapOfProductsWithMaxPriceInCategoriesAndOrderNumber().forEach((k,v)->System.out.println(k+" "+v));

        //B OK
        //customerOrderService.getProductsOrderedByCustomersFromCountryAndOfAgeBetween("JAPONIA", 0, 100).forEach(System.out::println);

        //C OK
        /*
        productService.getAllProductsWithGuarantee("SERVICE","MONEY_BACK").forEach((k,v)->
        {
            System.out.println(k);
            v.forEach(System.out::println);

        });
        */

        //D OK
        //shopService.getShopsWithProductsFromOtherCountries().forEach(System.out::println);

        //E OK
        //producerService.getProducersByTradeNameWhichProducedMoreThan("A",10).forEach(System.out::println);

        //F OK
        //customerOrderService.getOrdersBetweenDatesAndPriceAbove(LocalDate.of(2000,1,1),LocalDate.now(), BigDecimal.ZERO).forEach(System.out::println);

        //G OK
        //productService.getProductsOrderedByCustomerGroupedByProducer("JAN","NOWAK","POLSKA").forEach(System.out::println);

        //H OK
        //customerService.getCustomersWhoOrderedAtLeastOneProductFromCountryOfOrigin().forEach(System.out::println);
    }
}
