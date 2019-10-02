package kosiorek.michal.service;

import kosiorek.michal.dto.ProductDto;
import kosiorek.michal.dto.ShopDto;
import kosiorek.michal.dto.StockDto;
import kosiorek.michal.exceptions.AppException;
import kosiorek.michal.model.Product;
import kosiorek.michal.model.Shop;
import kosiorek.michal.model.Stock;
import kosiorek.michal.repository.ProductRepository;
import kosiorek.michal.repository.ShopRepository;
import kosiorek.michal.repository.StockRepository;
import kosiorek.michal.validators.StockDtoValidator;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;
    private final ShopRepository shopRepository;
    private final ProductRepository productRepository;

    public StockDto addStock(StockDto stockDto) {

        if (stockDto == null) {
            throw new AppException("add stock - stock object null");
        }

        if (stockDto.getQuantity() == null) {
            throw new AppException("add stock - quantity null");
        }

        ShopDto shopDto = stockDto.getShopDto();

        if (shopDto == null) {
            throw new AppException("add stock - stock shop null");
        }

        ProductDto productDto = stockDto.getProductDto();

        if (productDto == null) {
            throw new AppException("add stock - stock product null");
        }

        StockDtoValidator stockDtoValidator = new StockDtoValidator();
        Map<String,String> errors = stockDtoValidator.validate(stockDto);
        if(stockDtoValidator.hasErrors()){
            throw new AppException("add stock - stock validation not correct " + errors.toString());
        }

        Optional<Stock> stockOp = stockRepository.findByProductNameAndShop(productDto.getName(), shopDto.getName());

        if (stockOp.isPresent()) {
            Stock stock = stockOp.orElseThrow(() -> new AppException("add stock - no stock with given product name"));
            stockRepository.findByIdAndAddToQuantity(stock.getId(), stockDto.getQuantity());
            stockOp = stockRepository.findById(stock.getId());

            return stockOp.map(ModelMapper::fromStockToStockDto)
                    .orElseThrow(() -> new AppException("add stock - error while adding the stocks"));
        }

        Shop shop = shopRepository
                .findShopByNameAndCountryName(shopDto.getName(), shopDto.getCountryDto().getName())
                .orElseThrow(() -> new AppException("add stock - no shop with given name and country name"));

        Product product = productRepository
                .findProductByNameCategoryAndProducerName(productDto.getName(), productDto.getCategoryDto().getName(), productDto.getProducerDto().getName())
                .orElseThrow(() -> new AppException("add stock - no product with given product name, category name, and producer name"));

        Stock stock = ModelMapper.fromStockDtoToStock(stockDto);
        stock.setProduct(product);
        stock.setShop(shop);

        return stockRepository
                .addOrUpdate(stock)
                .map(ModelMapper::fromStockToStockDto)
                .orElseThrow(() -> new AppException("add stock - error while inserting"));


    }

    public List<StockDto> getAllStocks(){

        return stockRepository.findAll().stream()
                .map(ModelMapper::fromStockToStockDto)
                .collect(Collectors.toList());

    }

    public StockDto editStock(StockDto stockDto){
        if(stockDto==null){
            throw new AppException("edit stock - stock object null");
        }

        Stock stock = ModelMapper.fromStockDtoToStock(stockDto);

        return stockRepository.addOrUpdate(stock).map(ModelMapper::fromStockToStockDto)
                .orElseThrow(() -> new AppException("editing stock - exception while editing stock"));


    }

}
