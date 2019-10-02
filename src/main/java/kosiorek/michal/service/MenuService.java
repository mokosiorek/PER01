package kosiorek.michal.service;

import kosiorek.michal.dto.*;
import kosiorek.michal.exceptions.AppException;
import kosiorek.michal.jsonconverters.*;
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
                            // option3();
                            break;
                        case "4":
                            //option4();
                            break;
                        case "5":
                            //option5();
                            break;
                        case "6":
                            //option6();
                            break;
                        case "7":
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

        System.out.println("4 - Show all movies.");
        System.out.println("5 - Buy a ticket.");
        System.out.println("6 - Show customer's purchase history.");

        System.out.println("7 - Exit");
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

        System.out.println("9 - Return");
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

        System.out.println("9 - Return");
    }

}
