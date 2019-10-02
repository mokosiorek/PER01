package kosiorek.michal.service;

import kosiorek.michal.dto.CountryDto;
import kosiorek.michal.dto.ProducerDto;
import kosiorek.michal.dto.ProductDto;
import kosiorek.michal.dto.TradeDto;
import kosiorek.michal.exceptions.AppException;
import kosiorek.michal.model.*;
import kosiorek.michal.repository.*;
import kosiorek.michal.validators.ProducerDtoValidator;
import lombok.RequiredArgsConstructor;
import org.dom4j.rule.Mode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ProducerService {

    private final ProducerRepository producerRepository;
    private final CountryRepository countryRepository;
    private final TradeRepository tradeRepository;
    private final CustomerOrderRepository customerOrderRepository;
    private final StockRepository stockRepository;

    public ProducerDto addProducer(ProducerDto producerDto){

        if(producerDto==null){
            throw new AppException("add producer - producer object null");
        }

        if(producerDto.getName()==null){
            throw new AppException("add producer - producer name null");
        }

        CountryDto countryDto = producerDto.getCountryDto();

        if(countryDto==null){
            throw new AppException("add producer - producer country object is null");
        }

        if(countryDto.getName()==null){
            throw new AppException("add producer - producer country name is null");
        }

        TradeDto tradeDto = producerDto.getTradeDto();

        if(tradeDto == null){
            throw new AppException("add producer - producer trade object is null");
        }

        if(tradeDto.getName()==null){
            throw new AppException("add producer - producer trade name is null");
        }

        ProducerDtoValidator producerDtoValidator = new ProducerDtoValidator();
        Map<String, String> errors = producerDtoValidator.validate(producerDto);
        if(producerDtoValidator.hasErrors()){
            throw new AppException("add producer - producer validation not correct " + errors.toString());
        }

        if(producerRepository.findProducerByNameTradeAndCountryName(producerDto.getName(),tradeDto.getName(),countryDto.getName()).isPresent()){
            throw new AppException("producer with given name and trade in a country already exists");
        }

        Country country = countryRepository
                .findByName(countryDto.getName())
                .orElseThrow(() -> new AppException("add producer - no country with name " + countryDto.getName()));

        Trade trade = tradeRepository
                .findByName(tradeDto.getName())
                .orElseThrow(() -> new AppException("add producer - no trade with name " + tradeDto.getName()));

        Producer producer = ModelMapper.fromProducerDtoToProducer(producerDto);
        producer.setCountry(country);
        producer.setTrade(trade);

       return producerRepository.addOrUpdate(producer)
                .map(ModelMapper::fromProducerToProducerDto)
                .orElseThrow(() -> new AppException("add producer - error while inserting"));
    }


    public List<ProducerDto> getProducersByTradeNameWhichProducedMoreThan(String tradeName, Integer amount) {

        /*return producerRepository.findProducersByTradeNameWhichProducedMoreThan(tradeName,amount)
                .stream()
                .map(ModelMapper::fromProductToProductDto)
                .collect(Collectors.toList());*/

        var stocks = stockRepository.findStocksWithProductProducerTradeNameGiven(tradeName);
        var customerOrders = customerOrderRepository.findOrdersWithTradeNameOfProducerGiven(tradeName) ;

        Map<Long, Integer> groupedByQuantityInCustomerOrders  = customerOrders
                .stream()
                .collect(Collectors.groupingBy(co -> co.getProduct().getId(), Collectors.summingInt(CustomerOrder::getQuantity)));

        return stocks
                .stream()
                .filter(stock -> stock.getQuantity() + groupedByQuantityInCustomerOrders.get(stock.getProduct().getId()) > amount)
                .map(stock -> stock.getProduct().getProducer())
                .map(ModelMapper::fromProducerToProducerDto)
                .distinct()
                .collect(Collectors.toList());


    }

    public List<ProducerDto> getAllProducers(){

        return producerRepository.findAll().stream()
                .map(ModelMapper::fromProducerToProducerDto)
                .collect(Collectors.toList());

    }

    public ProducerDto editProducer(ProducerDto producerDto){

        if(producerDto == null){
            throw new AppException("editing producer - producer object null");
        }

        Producer producer = ModelMapper.fromProducerDtoToProducer(producerDto);

       return producerRepository.addOrUpdate(producer).map(ModelMapper::fromProducerToProducerDto)
                .orElseThrow(() -> new AppException("editing producer - exception while editing"));

    }

}
