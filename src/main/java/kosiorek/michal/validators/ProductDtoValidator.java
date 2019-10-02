package kosiorek.michal.validators;

import kosiorek.michal.dto.ProductDto;
import kosiorek.michal.validators.generic.AbstractValidator;

import java.math.BigDecimal;
import java.util.Map;

public class ProductDtoValidator extends AbstractValidator<ProductDto> {

    private static final String MODELREGEX = "[A-Z ]+";

    @Override
    public Map<String, String> validate(ProductDto productDto) {
        errors.clear();

      if(productDto.getName() == null && !productDto.getName().matches(MODELREGEX)){
          errors.put("product name", "doesn't match regex");
      }

      if(productDto.getPrice().compareTo(BigDecimal.ZERO)<0){
          errors.put("product price", "can't be lower than 0");
      }

      errors.putAll(new CategoryDtoValidator().validate(productDto.getCategoryDto()));
      errors.putAll(new ProducerDtoValidator().validate(productDto.getProducerDto()));



      return errors;
    }
}
