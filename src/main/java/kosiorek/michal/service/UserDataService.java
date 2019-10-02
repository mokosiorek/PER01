package kosiorek.michal.service;

import kosiorek.michal.dto.*;
import kosiorek.michal.exceptions.AppException;
import kosiorek.michal.model.CustomerOrder;
import kosiorek.michal.model.enums.EGuarantee;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


public class UserDataService {

    private Scanner sc = new Scanner(System.in);

    public int getInt(String message) {
        System.out.println(message);

        String data = sc.nextLine();
        if (!data.matches("\\d+")) {
            throw new AppException("INT VALUE IS NOT CORRECT: " + data);
        }

        return Integer.parseInt(data);
    }

    public String getString(String message, String regex) {
        System.out.println(message);

        String data = sc.nextLine();
        if (regex != null && !regex.isEmpty() && !data.matches(regex)) {
            throw new AppException("STRING VALUE IS NOT CORRECT: " + data);
        }

        return data;
    }

    public BigDecimal getBigDecimal(String message) {
        System.out.println(message);

        String data = sc.nextLine();
        if (!data.matches("^([1-9]+\\d*(?:\\.\\d{2})?)*")) {
            throw new AppException("BIG DECIMAL VALUE IS NOT CORRECT: " + data);
        }

        return BigDecimal.valueOf(Double.parseDouble(data));
    }

    public boolean getBoolean(String message) {
        System.out.println(message + " [y/n?]");
        return sc.nextLine().charAt(0) == 'y';
    }

    public void close() {
        if (sc != null) {
            sc.close();
            sc = null;
        }
    }

    public LocalDate getDate(String message) {
        System.out.println(message);
        String regex = "([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))";

        String data = sc.nextLine();
        if (!data.matches(regex)) {
            throw new AppException("DATE VALUE IS NOT CORRECT: " + data);
        }
        LocalDate date = LocalDate.parse(data);
        return date;
    }

    private void displayList(List<String> list) {

        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ". " + list.get(i));
        }
    }

    public CountryDto getCountryDto(List<CountryDto> countryDtos) {

        displayList(countryDtos.stream().map(CountryDto::getName).collect(Collectors.toList()));
        int option = getInt("Enter number of country:");
        return countryDtos.get(option - 1);

    }

    public CustomerDto getCustomerDto(List<CustomerDto> customerDtos) {

        displayList(customerDtos.stream().map(customerDto -> customerDto.getName() + " " + customerDto.getSurname() + " " + customerDto.getCountryDto()).collect(Collectors.toList()));
        int option = getInt("Enter number of customer:");
        return customerDtos.get(option - 1);

    }

    public ProductDto getProductDto(List<ProductDto> productDtos){

        displayList(productDtos.stream().map(productDto -> productDto.getName()+" "+productDto.getPrice().toString()).collect(Collectors.toList()));
        int option = getInt("Enter number of product:");
        return productDtos.get(option - 1);

    }

    public PaymentDto getPaymentDto(List<PaymentDto> paymentDtos){

        displayList(paymentDtos.stream().map(paymentDto -> paymentDto.getEPayment().toString()).collect(Collectors.toList()));
        int option = getInt("Enter number of payment type:");
        return paymentDtos.get(option - 1);

    }

    public TradeDto getTradeDto(List<TradeDto> tradeDtos){
        displayList(tradeDtos.stream().map(TradeDto::getName).collect(Collectors.toList()));
        int option = getInt("Enter number of trade:");
        return tradeDtos.get(option - 1);
    }

    public ProducerDto getProducerDto(List<ProducerDto> producerDtos)
    {
        displayList(producerDtos.stream().map(ProducerDto::getName).collect(Collectors.toList()));
        int option = getInt("Enter number of producer:");
        return producerDtos.get(option - 1);
    }

    public CategoryDto getCategoryDto(List<CategoryDto> categoryDtos){
        displayList(categoryDtos.stream().map(CategoryDto::getName).collect(Collectors.toList()));
        int option = getInt("Enter number of category:");
        return categoryDtos.get(option - 1);
    }

    public Set<EGuarantee> getEGuaranteeSet(){
        Set<EGuarantee> eGuarantees = new HashSet<>();
        List<EGuarantee> displayEG = Arrays.asList(EGuarantee.values());
        displayList(displayEG.stream().map(Enum::toString).collect(Collectors.toList()));
        boolean addEGuarantee = getBoolean("Do you want to add guarantee service to product? y/n");
        while(addEGuarantee){
            int option = getInt("Enter number of guarantee service:");
            eGuarantees.add(displayEG.get(option-1));
            displayEG.remove(option-1);
            addEGuarantee = getBoolean("Do you want to add next guarantee service to product? y/n");
        }
        return eGuarantees;
    }

    public ShopDto getShopDto(List<ShopDto> shopDtos){
        displayList(shopDtos.stream().map(ShopDto::getName).collect(Collectors.toList()));
        int option = getInt("Enter number of shop:");
        return shopDtos.get(option - 1);
    }

    public CustomerOrderDto getCustomerOrderDto(List<CustomerOrderDto> customerOrderDtos){
        displayList(customerOrderDtos.stream()
                .map(customerOrderDto -> customerOrderDto.getDate().toString()+" "+
                        customerOrderDto.getCustomerDto().getName()+" "+
                        customerOrderDto.getProductDto().getName()+ " "+
                        customerOrderDto.getQuantity()+" "+
                        customerOrderDto.getDiscount()
                ).collect(Collectors.toList()));
        int option = getInt("Enter number of customer order:");
        return customerOrderDtos.get(option - 1);

    }

    public StockDto getStockDto(List<StockDto> stockDtos){

        displayList(stockDtos.stream()
                .map(stockDto -> stockDto.getShopDto().getName()+" "+
                        stockDto.getProductDto().getName()+" "+
                        stockDto.getQuantity()
                        ).collect(Collectors.toList()));

        int option = getInt("Enter number of stock:");
        return stockDtos.get(option - 1);

    }

}
