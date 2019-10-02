package kosiorek.michal.validators;

import kosiorek.michal.dto.CountryDto;
import kosiorek.michal.validators.generic.AbstractValidator;

import java.util.Map;

public class CountryDtoValidator extends AbstractValidator<CountryDto> {

    private static final String MODELREGEX = "[A-Z ]+";

    @Override
    public Map<String, String> validate(CountryDto countryDto) {
        errors.clear();

        if(countryDto.getName()==null && !countryDto.getName().matches(MODELREGEX)){
            errors.put("country name","doesn't match regex");
        }

        return errors;
    }
}
