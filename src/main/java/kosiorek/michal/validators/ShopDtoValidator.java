package kosiorek.michal.validators;

import kosiorek.michal.dto.ShopDto;
import kosiorek.michal.validators.generic.AbstractValidator;

import java.util.Map;

public class ShopDtoValidator extends AbstractValidator<ShopDto> {

    private static final String MODELREGEX = "[A-Z ]+";

    @Override
    public Map<String, String> validate(ShopDto shopDto) {
        errors.clear();

        if(shopDto.getName()==null && !shopDto.getName().matches(MODELREGEX)){
            errors.put("shop name", "doesn't match regex");
        }

        errors.putAll(new CountryDtoValidator().validate(shopDto.getCountryDto()));

        return errors;
    }
}
