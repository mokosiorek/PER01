package kosiorek.michal.service;

import kosiorek.michal.dto.*;
import kosiorek.michal.exceptions.AppException;
import kosiorek.michal.jsonconverters.*;
import kosiorek.michal.model.CustomerOrder;
import kosiorek.michal.repository.impl.*;

import java.time.LocalDate;
import java.util.List;

public class MenuService {

    private final ErrorRepositoryImpl errorRepository = new ErrorRepositoryImpl();

    private final CustomerOrderRepositoryImpl customerOrderRepository = new CustomerOrderRepositoryImpl();
    private final ProductRepositoryImpl productRepository = new ProductRepositoryImpl();
    private final CustomerRepositoryImpl customerRepository = new CustomerRepositoryImpl();
    private final StockRepositoryImpl stockRepository = new StockRepositoryImpl();
    private final CategoryRepositoryImpl categoryRepository = new CategoryRepositoryImpl();
    private final CountryRepositoryImpl countryRepository = new CountryRepositoryImpl();
    private final ProducerRepositoryImpl producerRepository = new ProducerRepositoryImpl();
    private final ShopRepositoryImpl shopRepository = new ShopRepositoryImpl();
    private final TradeRepositoryImpl tradeRepository = new TradeRepositoryImpl();
    private final PaymentRepositoryImpl paymentRepository = new PaymentRepositoryImpl();


    private final CustomerOrderService customerOrderService = new CustomerOrderService(customerOrderRepository, productRepository, customerRepository, stockRepository);
    private final ProductService productService = new ProductService(productRepository, categoryRepository, producerRepository, countryRepository, customerOrderRepository);
    private final ShopService shopService = new ShopService(shopRepository, countryRepository, stockRepository);
    private final ProducerService producerService = new ProducerService(producerRepository, countryRepository, tradeRepository, customerOrderRepository, stockRepository);
    private final CustomerService customerService = new CustomerService(customerRepository, countryRepository);
    private final CountryService countryService = new CountryService(countryRepository);
    private final PaymentService paymentService = new PaymentService(paymentRepository);
    private final TradeService tradeService = new TradeService(tradeRepository);
    private final CategoryService categoryService = new CategoryService(categoryRepository);
    private final StockService stockService = new StockService(stockRepository, shopRepository, productRepository);

    private final CountryDtoJsonConverter countryDtoJsonConverter = new CountryDtoJsonConverter("country.json");
    private final CustomerDtoJsonConverter customerDtoJsonConverter = new CustomerDtoJsonConverter("customer.json");
    private final CustomerOrderDtoJsonConverter customerOrderDtoJsonConverter = new CustomerOrderDtoJsonConverter("customerorder.json");
    private final ProducerDtoJsonConverter producerDtoJsonConverter = new ProducerDtoJsonConverter("producer.json");
    private final ProductDtoJsonConverter productDtoJsonConverter = new ProductDtoJsonConverter("product.json");
    private final ShopDtoJsonConverter shopDtoJsonConverter = new ShopDtoJsonConverter("shop.json");
    private final StockDtoJsonConverter stockDtoJsonConverter = new StockDtoJsonConverter("stock.json");
    private final TradeDtoJsonConverter tradeDtoJsonConverter = new TradeDtoJsonConverter("trade.json");
    private final CategoryDtoJsonConverter categoryDtoJsonConverter = new CategoryDtoJsonConverter("category.json");

    private final CountriesDtoJsonConverter countriesDtoJsonConverter = new CountriesDtoJsonConverter("countries.json");
    private final CategoriesDtoJsonConverter categoriesDtoJsonConverter = new CategoriesDtoJsonConverter("categories.json");
    private final TradesDtoJsonConverter tradesDtoJsonConverter = new TradesDtoJsonConverter("trades.json");

    private UserDataService userDataService = new UserDataService();
    private final ErrorService errorService = new ErrorService(errorRepository);


    public void mainMenu() {

        String menu;
        while (true) {
            try {
                do {
                    displayMenu();
                    menu = userDataService.getString("Enter number:", "");

                    switch (menu) {
                        case "1":
                            option1();
                            break;
                        case "2":
                            option2();
                            break;
                        case "3":
                            option3();
                            break;
                        case "4":
                            option4();
                            break;
                        case "5":
                            option5();
                            break;
                        case "6":
                            option6();
                            break;
                        case "7":
                            option7();
                            break;
                        case "8":
                            option8();
                            break;
                        case "9":
                            option9();
                            break;
                        case "10":
                            option10();
                            break;
                        case "11":
                            option11();
                            break;
                        case "12":
                            userDataService.close();
                            System.out.println("The End");
                            return;
                        default:
                            System.out.println("Invalid option in menu. Enter number again.");
                            break;
                    }
                } while (true);

            } catch (AppException e) {
                ErrorDto errorDto = ErrorDto.builder().message(e.getMessage()).date(LocalDate.now()).build();
                errorService.addError(errorDto);
                System.out.println(e.getMessage());
            }
        }

    }

    private void displayMenu() {
        System.out.println("Menu - enter the number:");
        System.out.println("1 - Add record to database.");
        System.out.println("2 - Show all records of database.");
        System.out.println("3 - Edit record in database.");

        System.out.println("4 - Show map of products with maximum price in categories and order number.");
        System.out.println("5 - Show products ordered by customers from country and of age between.");
        System.out.println("6 - Show orders between dates and price above.");

        System.out.println("7 - Show customers who ordered at least one product from country of origin");

        System.out.println("8 - Show producers by trade name which produced more than");

        System.out.println("9 - Show products ordered by customer grouped by producer");

        System.out.println("10 - Show shops with products from other countries");

        System.out.println("11 - Data initialization.");

        System.out.println("12 - Exit");
    }

    private void option1() {

        displayMenuOfAddingRecord();

        int option;
        String menu;
        while (true) {
            try {
                do {
                    menu = userDataService.getString("Enter number:", "");

                    switch (menu) {
                        case "1":
                            countryService.addCountry(CountryDto.builder()
                                    .name(userDataService.getString("Enter country name:", "[A-Z ]+"))
                                    .build());
                            break;
                        case "2":

                            List<CountryDto> countries = countryService.getAllCountries();

                            customerService.addCustomer(CustomerDto.builder()
                                    .name(userDataService.getString("Enter customer name:", "[A-Z ]+"))
                                    .surname(userDataService.getString("Enter customer surname:", "[A-Z ]+"))
                                    .age(userDataService.getInt("Enter customer age:"))
                                    .countryDto(userDataService.getCountryDto(countries))
                                    .build());
                            break;
                        case "3":

                            List<CustomerDto> customerDtos = customerService.getAllCustomers();
                            List<ProductDto> productDtos = productService.getAllProducts();
                            List<PaymentDto> paymentDtos = paymentService.getAllPayments();


                            customerOrderService.addCustomerOrder(CustomerOrderDto.builder()
                                    .customerDto(userDataService.getCustomerDto(customerDtos))
                                    .productDto(userDataService.getProductDto(productDtos))
                                    .date(LocalDate.now())
                                    .discount(userDataService.getBigDecimal("Enter discount:"))
                                    .quantity(userDataService.getInt("Enter quantity:"))
                                    .paymentDto(userDataService.getPaymentDto(paymentDtos))
                                    .build());
                            break;

                        case "4":

                            List<CountryDto> countries2 = countryService.getAllCountries();
                            List<TradeDto> tradeDtos = tradeService.getAllTrades();

                            producerService.addProducer(ProducerDto.builder()
                                    .name(userDataService.getString("Enter producer name:", "[A-Z ]+"))
                                    .countryDto(userDataService.getCountryDto(countries2))
                                    .tradeDto(userDataService.getTradeDto(tradeDtos))
                                    .build());
                            break;

                        case "5":

                            List<ProducerDto> producerDtos = producerService.getAllProducers();
                            List<CategoryDto> categoryDtos = categoryService.getAllCategories();

                            productService.addProduct(ProductDto.builder()
                                    .name(userDataService.getString("Enter product name:", "[A-Z ]+"))
                                    .price(userDataService.getBigDecimal("Enter price:"))
                                    .producerDto(userDataService.getProducerDto(producerDtos))
                                    .categoryDto(userDataService.getCategoryDto(categoryDtos))
                                    .eGuarantees(userDataService.getEGuaranteeSet())
                                    .build());
                            break;

                        case "6":

                            List<CountryDto> countries3 = countryService.getAllCountries();

                            shopService.addShop(ShopDto.builder()
                                    .name(userDataService.getString("Enter shop name:", "[A-Z ]+"))
                                    .countryDto(userDataService.getCountryDto(countries3))
                                    .build());
                            break;

                        case "7":

                            List<ProductDto> productDtos2 = productService.getAllProducts();
                            List<ShopDto> shopDtos = shopService.getAllShops();

                            stockService.addStock(StockDto.builder()
                                    .productDto(userDataService.getProductDto(productDtos2))
                                    .shopDto(userDataService.getShopDto(shopDtos))
                                    .quantity(userDataService.getInt("Enter quantity:"))
                                    .build());
                            break;

                        case "8":

                            tradeService.addTrade(TradeDto.builder()
                                    .name(userDataService.getString("Enter trade name:", "[A-Z ]+"))
                                    .build());
                            break;

                        case "9":

                            categoryService.addCategory(CategoryDto.builder()
                                    .name(userDataService.getString("Enter category name:","[A-Z ]+"))
                                    .build());
                            break;

                        case "10":
                            return;
                        default:
                            System.out.println("Invalid option in menu. Enter number again.");
                            break;
                    }
                } while (true);

            } catch (AppException e) {
                System.out.println(e.getMessage());
            }
        }


    }

    private void displayMenuOfAddingRecord() {

        System.out.println("Menu - enter the number:");
        System.out.println("1 - Add new country.");
        System.out.println("2 - Add new customer.");
        System.out.println("3 - Add new customer order.");
        System.out.println("4 - Add new producer.");
        System.out.println("5 - Add new product.");
        System.out.println("6 - Add new shop.");
        System.out.println("7 - Add new stock.");
        System.out.println("8 - Add new trade.");
        System.out.println("9 - Add new category.");

        System.out.println("10 - Return");
    }

    public void option2() {

        int option;
        String menu;
        while (true) {
            try {
                do {
                    displayMenuOfShowingRecords();
                    menu = userDataService.getString("Enter number:", "");

                    switch (menu) {
                        case "1":

                            countryService.getAllCountries().stream()
                                    .map(countryDtoJsonConverter::toJson)
                                    .forEach(System.out::println);
                            break;
                        case "2":


                            customerService.getAllCustomers().stream()
                                    .map(customerDtoJsonConverter::toJson)
                                    .forEach(System.out::println);
                            break;

                        case "3":

                            customerOrderService.getAllCustomerOrders().stream()
                                    .map(customerOrderDtoJsonConverter::toJson)
                                    .forEach(System.out::println);

                            break;

                        case "4":
                            producerService.getAllProducers().stream()
                                    .map(producerDtoJsonConverter::toJson)
                                    .forEach(System.out::println);

                            break;

                        case "5":
                            productService.getAllProducts().stream()
                                    .map(productDtoJsonConverter::toJson)
                                    .forEach(System.out::println);

                            break;

                        case "6":
                            shopService.getAllShops().stream()
                                    .map(shopDtoJsonConverter::toJson)
                                    .forEach(System.out::println);

                            break;

                        case "7":
                            stockService.getAllStocks().stream()
                                    .map(stockDtoJsonConverter::toJson)
                                    .forEach(System.out::println);

                            break;

                        case "8":
                            tradeService.getAllTrades().stream()
                                    .map(tradeDtoJsonConverter::toJson)
                                    .forEach(System.out::println);

                            break;

                        case "9":
                            categoryService.getAllCategories().stream()
                                    .map(categoryDtoJsonConverter::toJson)
                                    .forEach(System.out::println);
                            break;


                        case "10":
                            return;
                        default:
                            System.out.println("Invalid option in menu. Enter number again.");
                            break;
                    }
                } while (true);

            } catch (AppException e) {
                System.out.println(e.getMessage());
            }
        }

    }

    private void displayMenuOfShowingRecords() {

        System.out.println("Menu - enter the number:");
        System.out.println("1 - Show all countries.");
        System.out.println("2 - Show all customers.");
        System.out.println("3 - Show all customer orders.");
        System.out.println("4 - Show all producers.");
        System.out.println("5 - Show all products.");
        System.out.println("6 - Show all shops.");
        System.out.println("7 - Show all stocks.");
        System.out.println("8 - Show all trades.");
        System.out.println("9 - Show all categories.");

        System.out.println("10 - Return");
    }

    private void option3() {

        int option;
        String menu;
        while (true) {
            try {
                do {
                    displayMenuOfEditingRecord();
                    menu = userDataService.getString("Enter number:", "");

                    switch (menu) {
                        case "1":

                            List<CountryDto> countries = countryService.getAllCountries();
                            CountryDto countryDto = userDataService.getCountryDto(countries);
                            countryDto.setName(userDataService.getString("Enter new country name:", "[A-Z ]+"));
                            countryService.editCountry(countryDto);

                            break;
                        case "2":

                            List<CustomerDto> customerDtos = customerService.getAllCustomers();
                            List<CountryDto> countries2 = countryService.getAllCountries();

                            CustomerDto customerDto = userDataService.getCustomerDto(customerDtos);
                            customerDto.setName(userDataService.getString("Enter new customer name:","[A-Z ]+" ));
                            customerDto.setAge(userDataService.getInt("Enter new customer age:"));
                            customerDto.setCountryDto(userDataService.getCountryDto(countries2));

                            customerService.editCustomer(customerDto);

                            break;

                        case "3":

                            List<CustomerOrderDto> customerOrderDtos = customerOrderService.getAllCustomerOrders();

                            List<CustomerDto> customerDtos2 = customerService.getAllCustomers();
                            List<ProductDto> productDtos = productService.getAllProducts();
                            List<PaymentDto> paymentDtos = paymentService.getAllPayments();

                            CustomerOrderDto customerOrderDto = userDataService.getCustomerOrderDto(customerOrderDtos);
                            customerOrderDto.setDate(userDataService.getDate("Enter new customer order date:"));
                            customerOrderDto.setCustomerDto(userDataService.getCustomerDto(customerDtos2));
                            customerOrderDto.setProductDto(userDataService.getProductDto(productDtos));
                            customerOrderDto.setPaymentDto(userDataService.getPaymentDto(paymentDtos));
                            customerOrderDto.setDiscount(userDataService.getBigDecimal("Enter new customer order discount value:"));
                            customerOrderDto.setQuantity(userDataService.getInt("Enter new customer order quantity:"));

                            customerOrderService.editCustomerOrder(customerOrderDto);
                            break;

                        case "4":

                            List<ProducerDto> producerDtos = producerService.getAllProducers();
                            List<CountryDto> countries3 = countryService.getAllCountries();
                            List<TradeDto> tradeDtos = tradeService.getAllTrades();

                            ProducerDto producerDto = userDataService.getProducerDto(producerDtos);
                            producerDto.setName(userDataService.getString("Enter new producer name:","[A-Z ]+"));
                            producerDto.setCountryDto(userDataService.getCountryDto(countries3));
                            producerDto.setTradeDto(userDataService.getTradeDto(tradeDtos));

                            producerService.editProducer(producerDto);

                            break;

                        case "5":

                            List<ProductDto> productDtos1 = productService.getAllProducts();
                            List<ProducerDto> producerDtos2 = producerService.getAllProducers();
                            List<CategoryDto> categoryDtos = categoryService.getAllCategories();

                            ProductDto productDto = userDataService.getProductDto(productDtos1);
                            productDto.setName(userDataService.getString("Enter new product name","[A-Z ]+"));
                            productDto.setPrice(userDataService.getBigDecimal("Enter new price:"));
                            productDto.setCategoryDto(userDataService.getCategoryDto(categoryDtos));
                            productDto.setProducerDto(userDataService.getProducerDto(producerDtos2));
                            productDto.setEGuarantees(userDataService.getEGuaranteeSet());

                            productService.editProduct(productDto);

                            break;

                        case "6":

                            List<ShopDto> shopDtos = shopService.getAllShops();
                            List<CountryDto> countries4 = countryService.getAllCountries();

                            ShopDto shopDto = userDataService.getShopDto(shopDtos);
                            shopDto.setName(userDataService.getString("Enter new shop name:","[A-Z ]+"));
                            shopDto.setCountryDto(userDataService.getCountryDto(countries4));

                            shopService.editShop(shopDto);

                            break;

                        case "7":

                            List<StockDto> stockDtos = stockService.getAllStocks();
                            List<ProductDto> productDtos2 = productService.getAllProducts();
                            List<ShopDto> shopDtos2 = shopService.getAllShops();

                            StockDto stockDto = userDataService.getStockDto(stockDtos);
                            stockDto.setShopDto(userDataService.getShopDto(shopDtos2));
                            stockDto.setProductDto(userDataService.getProductDto(productDtos2));
                            stockDto.setQuantity(userDataService.getInt("Enter integer:"));

                            stockService.editStock(stockDto);

                            break;

                        case "8":

                            List<TradeDto> tradeDtos1 = tradeService.getAllTrades();

                            TradeDto tradeDto = userDataService.getTradeDto(tradeDtos1);
                            tradeDto.setName(userDataService.getString("Enter trade name:","[A-Z ]+"));

                            tradeService.editTrade(tradeDto);

                            break;

                        case "9":

                            List<CategoryDto> categoryDtos1 = categoryService.getAllCategories();

                            CategoryDto categoryDto = userDataService.getCategoryDto(categoryDtos1);
                            categoryDto.setName(userDataService.getString("Enter category name","[A-Z ]+"));

                            categoryService.editCategory(categoryDto);

                            break;

                        case "10":
                            return;
                        default:
                            System.out.println("Invalid option in menu. Enter number again.");
                            break;
                    }
                } while (true);

            } catch (AppException e) {
                System.out.println(e.getMessage());
            }
        }

    }

    private void displayMenuOfEditingRecord() {

        System.out.println("Menu - enter the number:");
        System.out.println("1 - Edit country.");
        System.out.println("2 - Edit customer.");
        System.out.println("3 - Edit customer order.");
        System.out.println("4 - Edit producer.");
        System.out.println("5 - Edit product.");
        System.out.println("6 - Edit shop.");
        System.out.println("7 - Edit stock.");
        System.out.println("8 - Edit trade.");
        System.out.println("9 - Edit category.");

        System.out.println("10 - Return");
    }

    public void option4(){

        customerOrderService.getMapOfProductsWithMaxPriceInCategoriesAndOrderNumber().forEach(
                (k,v)-> System.out.println(productDtoJsonConverter.toJson(k)+" "+v)
        );

    }

    public void option5(){

        customerOrderService.getProductsOrderedByCustomersFromCountryAndOfAgeBetween(
                userDataService.getString("Enter country name:","[A-Z ]+"),
                userDataService.getInt("Enter age from:"),
                userDataService.getInt("Enter age to:")
        ).forEach(product -> System.out.println(productDtoJsonConverter.toJson(product)));
    }

    public void option6(){
        customerOrderService.getOrdersBetweenDatesAndPriceAbove(
                userDataService.getDate("Enter date from:"),
                userDataService.getDate("Enter date to:"),
                userDataService.getBigDecimal("Enter price:")
        ).forEach(customerOrderDto -> System.out.println(customerOrderDtoJsonConverter.toJson(customerOrderDto)));

    }

    public void option7(){
        customerService.getCustomersWhoOrderedAtLeastOneProductFromCountryOfOrigin()
                .forEach(customerDto -> System.out.println(customerDtoJsonConverter.toJson(customerDto)));
    }

    public void option8(){

        producerService.getProducersByTradeNameWhichProducedMoreThan(
                userDataService.getString("Enter trade name:","[A-Z ]+"),
                userDataService.getInt("Enter integer:"))
                .forEach(producerDto -> System.out.println(producerDtoJsonConverter.toJson(producerDto)));

    }

    public void option9() {

        productService.getProductsOrderedByCustomerGroupedByProducer(
                userDataService.getString("Enter customer name:","[A-Z ]+"),
                userDataService.getString("Enter customer surname:","[A-Z ]+"),
                userDataService.getString("Enter country name:","[A-Z ]+")
        ).forEach(product -> System.out.println(productDtoJsonConverter.toJson(product)));

    }

    public void option10(){
        shopService.getShopsWithProductsFromOtherCountries().forEach(
                shopDto -> System.out.println(shopDtoJsonConverter.toJson(shopDto))
        );
    }

    public void option11(){

        countriesDtoJsonConverter.fromJson().ifPresent(countryDtos -> countryDtos.stream().forEach(countryService::addCountry));
        categoriesDtoJsonConverter.fromJson().ifPresent(categoryDtos -> categoryDtos.stream().forEach(categoryService::addCategory));
        tradesDtoJsonConverter.fromJson().ifPresent(tradeDtos -> tradeDtos.stream().forEach(tradeService::addTrade));
    }

}
