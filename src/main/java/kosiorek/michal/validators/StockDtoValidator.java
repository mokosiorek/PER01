package kosiorek.michal.validators;

import kosiorek.michal.dto.StockDto;
import kosiorek.michal.validators.generic.AbstractValidator;

import java.util.Map;

public class StockDtoValidator extends AbstractValidator<StockDto> {
    @Override
    public Map<String, String> validate(StockDto stockDto) {
        errors.clear();

        if(stockDto.getQuantity()<0){
            errors.put("stock quantity", "can't be lower than 0");
        }

        errors.putAll(new ProductDtoValidator().validate(stockDto.getProductDto()));
        errors.putAll(new ShopDtoValidator().validate(stockDto.getShopDto()) );

        return errors;
    }
}
