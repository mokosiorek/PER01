package kosiorek.michal.service;

import kosiorek.michal.dto.*;
import kosiorek.michal.model.Error;
import kosiorek.michal.model.*;

import java.util.HashSet;

public interface ModelMapper {

    static CountryDto fromCountryToCountryDto(Country country) {
        return country == null ? null : CountryDto.builder()
                .id(country.getId())
                .name(country.getName())
                .build();
    }

    static Country fromCountryDtoToCountry(CountryDto countryDto) {
        return countryDto == null ? null : Country.builder()
                .id(countryDto.getId())
                .name(countryDto.getName())
                .customers(new HashSet<>())
                .shops(new HashSet<>())
                .producers(new HashSet<>())
                .build();
    }

    static CustomerDto fromCustomerToCustomerDto(Customer customer) {
        return customer == null ? null : CustomerDto.builder()
                .id(customer.getId())
                .name(customer.getName())
                .surname(customer.getSurname())
                .age(customer.getAge())
                .countryDto(customer.getCountry() == null ? null : fromCountryToCountryDto(customer.getCountry()))
                .build();
    }

    static Customer fromCustomerDtoToCustomer(CustomerDto customerDto) {
        return customerDto == null ? null : Customer.builder()
                .id(customerDto.getId())
                .name(customerDto.getName())
                .surname(customerDto.getSurname())
                .age(customerDto.getAge())
                .country(customerDto.getCountryDto() == null ? null : fromCountryDtoToCountry(customerDto.getCountryDto()))
                .build();
    }

    static CategoryDto fromCategoryToCategoryDto(Category category) {
        return category == null ? null : CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    static Category fromCategoryDtoToCategory(CategoryDto categoryDto) {
        return categoryDto == null ? null : Category.builder()
                .id(categoryDto.getId())
                .name(categoryDto.getName())
                .products(new HashSet<>())
                .build();
    }

    static CustomerOrderDto fromCustomerOrderToCustomerOrderDto(CustomerOrder customerOrder) {
        return customerOrder == null ? null : CustomerOrderDto.builder()
                .id(customerOrder.getId())
                .date(customerOrder.getDate())
                .discount(customerOrder.getDiscount())
                .quantity(customerOrder.getQuantity())
                .customerDto(customerOrder.getCustomer() == null ? null : fromCustomerToCustomerDto(customerOrder.getCustomer()))
                .paymentDto(customerOrder.getPayment()==null ? null : fromPaymentToPaymentDto(customerOrder.getPayment()))
                .productDto(customerOrder.getProduct()==null ? null : fromProductToProductDto(customerOrder.getProduct()))
                .build();
    }

    static CustomerOrder fromCustomerOrderDtoToCustomerOrder(CustomerOrderDto customerOrderDto){
        return customerOrderDto == null ? null : CustomerOrder.builder()
                .id(customerOrderDto.getId())
                .date(customerOrderDto.getDate())
                .discount(customerOrderDto.getDiscount())
                .quantity(customerOrderDto.getQuantity())
                .customer(customerOrderDto.getCustomerDto()==null ? null : fromCustomerDtoToCustomer(customerOrderDto.getCustomerDto()))
                .payment(customerOrderDto.getPaymentDto()==null ? null : fromPaymentDtoToPayment(customerOrderDto.getPaymentDto()))
                .product(customerOrderDto.getProductDto()==null ? null : fromProductDtoToProduct(customerOrderDto.getProductDto()))
                .build();
    }

    static ErrorDto fromErrorToErrorDto(Error error){
        return error == null ? null : ErrorDto.builder()
                .id(error.getId())
                .message(error.getMessage())
                .date(error.getDate())
                .build();
    }

    static Error fromErrorDtoToError(ErrorDto errorDto){
        return errorDto == null ? null : Error.builder()
                .id(errorDto.getId())
                .message(errorDto.getMessage())
                .date(errorDto.getDate())
                .build();
    }

    static PaymentDto fromPaymentToPaymentDto(Payment payment){
        return payment == null ? null : PaymentDto.builder()
                .id(payment.getId())
                .ePayment(payment.getEPayment())
                .build();
    }

    static Payment fromPaymentDtoToPayment(PaymentDto paymentDto){
        return paymentDto == null ? null : Payment.builder()
                .id(paymentDto.getId())
                .ePayment(paymentDto.getEPayment())
                .customerOrders(new HashSet<>())
                .build();
    }

    static ProducerDto fromProducerToProducerDto(Producer producer){
        return producer == null ? null : ProducerDto.builder()
                .id(producer.getId())
                .name(producer.getName())
                .countryDto(producer.getCountry() == null ? null : fromCountryToCountryDto(producer.getCountry()))
                .tradeDto(producer.getTrade()==null ? null : fromTradeToTradeDto(producer.getTrade()))
                .build();
    }

    static Producer fromProducerDtoToProducer(ProducerDto producerDto){
        return producerDto == null ? null : Producer.builder()
                .id(producerDto.getId())
                .name(producerDto.getName())
                .country(producerDto.getCountryDto() == null ? null : fromCountryDtoToCountry(producerDto.getCountryDto()))
                .products(new HashSet<>())
                .trade(producerDto.getTradeDto()==null ? null : fromTradeDtoToTrade(producerDto.getTradeDto()))
                .build();
    }

    static ProductDto fromProductToProductDto(Product product){
        return product==null ? null : ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .categoryDto(product.getCategory()==null ? null : fromCategoryToCategoryDto(product.getCategory()))
                .producerDto(product.getProducer()==null ? null : fromProducerToProducerDto(product.getProducer()))
                .eGuarantees(product.getEGuarantees())
                .build();
    }

    static Product fromProductDtoToProduct(ProductDto productDto){
        return productDto == null ? null : Product.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .price(productDto.getPrice())
                .category(productDto.getCategoryDto()==null ? null : fromCategoryDtoToCategory(productDto.getCategoryDto()))
                .producer(productDto.getProducerDto()==null ? null : fromProducerDtoToProducer(productDto.getProducerDto()))
                .eGuarantees(productDto.getEGuarantees())
                .stocks(new HashSet<>())
                .build();
    }

    static ShopDto fromShopToShopDto(Shop shop){
        return shop==null ? null : ShopDto.builder()
                .id(shop.getId())
                .name(shop.getName())
                .countryDto(shop.getCountry()==null ? null : fromCountryToCountryDto(shop.getCountry()))
                .build();
    }

    static Shop fromShopDtoToShop(ShopDto shopDto){
        return shopDto==null ? null : Shop.builder()
                .id(shopDto.getId())
                .name(shopDto.getName())
                .country(shopDto.getCountryDto()==null ? null : fromCountryDtoToCountry(shopDto.getCountryDto()))
                .stocks(new HashSet<>())
                .build();
    }

    static StockDto fromStockToStockDto(Stock stock){
        return stock==null ? null : StockDto.builder()
                .id(stock.getId())
                .quantity(stock.getQuantity())
                .productDto(stock.getProduct() == null ? null : fromProductToProductDto(stock.getProduct()))
                .shopDto(stock.getShop() == null ? null : fromShopToShopDto(stock.getShop()))
                .build();
    }

    static Stock fromStockDtoToStock(StockDto stockDto){
        return stockDto == null ? null : Stock.builder()
                .id(stockDto.getId())
                .quantity(stockDto.getQuantity())
                .product(stockDto.getProductDto()==null ? null : fromProductDtoToProduct(stockDto.getProductDto()))
                .shop(stockDto.getShopDto()==null ? null : fromShopDtoToShop(stockDto.getShopDto()))
                .build();
    }

    static TradeDto fromTradeToTradeDto(Trade trade){
        return trade ==null ? null : TradeDto.builder()
                .id(trade.getId())
                .name(trade.getName())
                .build();
    }

    static Trade fromTradeDtoToTrade(TradeDto tradeDto){
        return tradeDto==null ? null : Trade.builder()
                .id(tradeDto.getId())
                .name(tradeDto.getName())
                .producers(new HashSet<>())
                .build();
    }


}
