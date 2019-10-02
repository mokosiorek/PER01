package kosiorek.michal.service;

import kosiorek.michal.dto.CustomerDto;
import kosiorek.michal.dto.CustomerOrderDto;
import kosiorek.michal.dto.ProductDto;
import kosiorek.michal.exceptions.AppException;
import kosiorek.michal.model.Customer;
import kosiorek.michal.model.CustomerOrder;
import kosiorek.michal.model.Product;
import kosiorek.michal.model.Stock;
import kosiorek.michal.repository.CustomerOrderRepository;
import kosiorek.michal.repository.CustomerRepository;
import kosiorek.michal.repository.ProductRepository;
import kosiorek.michal.repository.StockRepository;
import kosiorek.michal.validators.CustomerOrderDtoValidator;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CustomerOrderService {

    private final CustomerOrderRepository customerOrderRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final StockRepository stockRepository;

    public CustomerOrderDto addCustomerOrder(CustomerOrderDto customerOrderDto) {

        if (customerOrderDto == null) {
            throw new AppException("add customer order - customer order object null");
        }

        if (customerOrderDto.getDate() == null) {
            throw new AppException("add customer order - customer order date null");
        }

        if (customerOrderDto.getDiscount() == null) {
            throw new AppException("add customer order - customer order discount null");
        }

        if (customerOrderDto.getQuantity() == null) {
            throw new AppException("add customer order - customer order quantity null");
        }

        CustomerDto customerDto = customerOrderDto.getCustomerDto();

        if (customerDto == null) {
            throw new AppException("add customer order - customer order customer null");
        }

        ProductDto productDto = customerOrderDto.getProductDto();

        if (productDto == null) {
            throw new AppException("add customer order - customer order product null");
        }

        CustomerOrderDtoValidator customerOrderDtoValidator = new CustomerOrderDtoValidator();
        Map<String, String> errors = customerOrderDtoValidator.validate(customerOrderDto);
        if (customerOrderDtoValidator.hasErrors()) {
            throw new AppException("add customer order - customer order validation not correct " + errors.toString());
        }

        Customer customer = customerRepository
                .findCustomerByNameSurnameAndCountryName(customerDto.getName(), customerDto.getSurname(), customerDto.getCountryDto().getName())
                .orElseThrow(() -> new AppException("add customer order - no customer with given name,surname and country name"));

        Product product = productRepository
                .findProductByNameCategoryAndProducerName(productDto.getName(), productDto.getCategoryDto().getName(), productDto.getProducerDto().getName())
                .orElseThrow(() -> new AppException("add customer order - no product with given name, category and producer name"));


        List<Stock> stocks = stockRepository.findAllStocksWithProduct(productDto.getName());

        //Check if there is enough product in stock worldwide.
        if (customerOrderDto.getQuantity() > stocks.stream().map(Stock::getQuantity).reduce(0, Integer::sum)) {
            throw new AppException("add customer order - not enough products in stock worldwide");
        } else {

            List<Stock> stocksInCountry = stockRepository.findAllStocksWithProductInCountry(productDto.getName(), customerDto.getCountryDto().getName());

            Integer quantityOrdered = customerOrderDto.getQuantity();

            for (Stock s : stocksInCountry) {

                if (s.getQuantity() >= quantityOrdered) {
                    s.setQuantity(s.getQuantity() - quantityOrdered);
                    stockRepository.addOrUpdate(s);
                    break;
                } else {
                    s.setQuantity(0);
                    quantityOrdered = quantityOrdered - s.getQuantity();
                    stockRepository.addOrUpdate(s);
                }
            }
            if (quantityOrdered > 0) {

                List<Stock> stocksOutsideOfCountry = stockRepository.findAllStocksWithProductOutsideCountry(productDto.getName(), customerDto.getCountryDto().getName());

                for (Stock s : stocksOutsideOfCountry) {

                    if (s.getQuantity() >= quantityOrdered) {
                        s.setQuantity(s.getQuantity() - quantityOrdered);
                        stockRepository.addOrUpdate(s);
                        break;
                    } else {
                        s.setQuantity(0);
                        quantityOrdered = quantityOrdered - s.getQuantity();
                        stockRepository.addOrUpdate(s);
                    }
                }
            }
        }


        CustomerOrder customerOrder = ModelMapper.fromCustomerOrderDtoToCustomerOrder(customerOrderDto);
        customerOrder.setCustomer(customer);
        customerOrder.setProduct(product);

        return customerOrderRepository
                .addOrUpdate(customerOrder)
                .map(ModelMapper::fromCustomerOrderToCustomerOrderDto)
                .orElseThrow(() -> new AppException("add customer order - error while inserting"));

    }


    public Map<ProductDto, Long> getMapOfProductsWithMaxPriceInCategoriesAndOrderNumber() {
        List<CustomerOrder> customerOrders = customerOrderRepository.findAllOrdersWithProductsOfMaxPriceInCategories();

        return customerOrders.stream().map(ModelMapper::fromCustomerOrderToCustomerOrderDto).collect(Collectors.groupingBy(CustomerOrderDto::getProductDto, Collectors.counting()));

    }

    public List<ProductDto> getProductsOrderedByCustomersFromCountryAndOfAgeBetween(String countryName, Integer ageFrom, Integer ageTo) {

        if (countryName == null) {
            throw new AppException("get All Products Ordered By Customers From Country And Of Age Between - country name null");
        }

        if (ageFrom.compareTo(ageTo) > 0) {
            throw new AppException("get All Products Ordered By Customers From Country And Of Age Between - ageTo can't be smaller than ageFrom");
        }

        List<CustomerOrder> customerOrders = customerOrderRepository.findAllOrdersOrderedByCustomersFromCountryAndOfAgeBetween(countryName, ageFrom, ageTo);

        return customerOrders.stream()
                .map(CustomerOrder::getProduct)
                .distinct()
                .sorted(Comparator.comparing(Product::getPrice).reversed())
                .map(ModelMapper::fromProductToProductDto)
                .collect(Collectors.toList());

    }

    public List<CustomerOrderDto> getOrdersBetweenDatesAndPriceAbove(LocalDate dateFrom, LocalDate dateTo, BigDecimal amount) {

        if (amount == null) {
            throw new AppException("get All Orders Between Dates and price above -  amount null");
        }

        if (dateFrom.compareTo(dateTo) > 0) {
            throw new AppException("get All Orders Between Dates and price above - dateTo can't be smaller than dateFrom");
        }

        return customerOrderRepository.findOrdersWithDateBetweenAndTotalPriceAbove(dateFrom, dateTo, amount).stream()
                .map(ModelMapper::fromCustomerOrderToCustomerOrderDto)
                .collect(Collectors.toList());
    }

    public List<CustomerOrderDto> getAllCustomerOrders() {
        return customerOrderRepository.findAll().stream()
                .map(ModelMapper::fromCustomerOrderToCustomerOrderDto)
                .collect(Collectors.toList());
    }

    public CustomerOrderDto editCustomerOrder(CustomerOrderDto customerOrderDto) {

        if (customerOrderDto == null) {
            throw new AppException("customer order edit - customer order object null");
        }

        CustomerOrder customerOrder = ModelMapper.fromCustomerOrderDtoToCustomerOrder(customerOrderDto);
         return customerOrderRepository.addOrUpdate(customerOrder)
                 .map(ModelMapper::fromCustomerOrderToCustomerOrderDto)
                 .orElseThrow(() -> new AppException("editing customer order - exception while editing"));


    }

}
