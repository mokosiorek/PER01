package kosiorek.michal.service;

import kosiorek.michal.dto.CategoryDto;
import kosiorek.michal.dto.ProducerDto;
import kosiorek.michal.dto.ProductDto;
import kosiorek.michal.exceptions.AppException;
import kosiorek.michal.model.Category;
import kosiorek.michal.model.CustomerOrder;
import kosiorek.michal.model.Producer;
import kosiorek.michal.model.Product;
import kosiorek.michal.repository.*;
import kosiorek.michal.validators.ProducerDtoValidator;
import kosiorek.michal.validators.ProductDtoValidator;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProducerRepository producerRepository;
    private final CountryRepository countryRepository;
    private final CustomerOrderRepository customerOrderRepository;

    public ProductDto addProduct(ProductDto productDto) {

        if (productDto == null) {
            throw new AppException("add product - product object null");
        }

        if (productDto.getName() == null) {
            throw new AppException("add product - product name null");
        }

        if (productDto.getPrice() == null) {
            throw new AppException("add product - product price null");
        }

        ProducerDto producerDto = productDto.getProducerDto();

        if (producerDto == null) {
            throw new AppException("add product - product producer null");
        }

        if (producerDto.getName() == null) {
            throw new AppException("add product - product producer name object null");
        }

        if (producerDto.getCountryDto() == null) {
            throw new AppException("add product - product producer country null");
        }

        if (producerDto.getTradeDto() == null) {
            throw new AppException("add product - product producer trade null");
        }

        CategoryDto categoryDto = productDto.getCategoryDto();

        if (categoryDto == null) {
            throw new AppException("add product - product category null");
        }

        ProductDtoValidator productDtoValidator = new ProductDtoValidator();
        Map<String, String> errors = productDtoValidator.validate(productDto);
        if(productDtoValidator.hasErrors()){
            throw new AppException("add product - product validation not correct " + errors.toString());
        }

        if (productRepository.findProductByNameCategoryAndProducerName(productDto.getName(), categoryDto.getName(), producerDto.getName()).isPresent()) {
            throw new AppException("add product - product with given name, category and producer name already exists");
        }

        Category category = categoryRepository
                .findByName(categoryDto.getName())
                .orElseThrow(() -> new AppException("add product - no category with name " + categoryDto.getName()));

        Producer producer = producerRepository
                .findProducerByNameTradeAndCountryName(producerDto.getName(), producerDto.getTradeDto().getName(), producerDto.getCountryDto().getName())
                .orElseThrow(() -> new AppException("add product - no producer with name " + producerDto.getName()));

        Product product = ModelMapper.fromProductDtoToProduct(productDto);
        product.setCategory(category);
        product.setProducer(producer);

        return productRepository.addOrUpdate(product)
                .map(ModelMapper::fromProductToProductDto)
                .orElseThrow(() -> new AppException("add product - error while inserting"));


    }

    public Map<CategoryDto,List<ProductDto>> getAllProductsWithGuarantee(String... guarantees){



        return productRepository.findAll().stream()
                .map(ModelMapper::fromProductToProductDto)
                .filter(product -> product.getEGuarantees().stream().map(Enum::toString).collect(Collectors.toList()).containsAll(Arrays.asList(guarantees)))
                .collect(Collectors.groupingBy(ProductDto::getCategoryDto));

    }

    public List<ProductDto> getProductsOrderedByCustomerGroupedByProducer(String customerName, String customerSurname, String countryName){

        if(customerName==null){
            throw new AppException("getProductsOrderedByCustomerGroupedByProducer - customerName null");
        }
        if(customerSurname==null){
            throw new AppException("getProductsOrderedByCustomerGroupedByProducer - customerSurname null");
        }
        if(countryName==null){
            throw new AppException("getProductsOrderedByCustomerGroupedByProducer - countryName null");
        }

        return productRepository.findProductsOrderedByCustomerGroupedByProducer(customerName,customerSurname,countryName).stream()
                .map(ModelMapper::fromProductToProductDto)
                .collect(Collectors.toList());

    }

    public List<ProductDto> getAllProducts(){

        return productRepository.findAll().stream()
                .map(ModelMapper::fromProductToProductDto)
                .collect(Collectors.toList());

    }

}
