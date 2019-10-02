package kosiorek.michal.validators;

import kosiorek.michal.dto.CustomerDto;
import kosiorek.michal.validators.generic.AbstractValidator;

import java.util.Map;

public class CustomerDtoValidator extends AbstractValidator<CustomerDto> {

    private static final String MODELREGEX = "[A-Z ]+";

    @Override
    public Map<String, String> validate(CustomerDto customerDto) {
        errors.clear();

        if (customerDto.getName() == null && !customerDto.getName().matches(MODELREGEX)) {
            errors.put("customer name", "doesn't match regex");
        }

        if (customerDto.getSurname() == null && !customerDto.getSurname().matches(MODELREGEX)) {
            errors.put("customer surname", "doesn't match regex");
        }

        if (customerDto.getAge() < 18) {
            errors.put("customer age", "age lower than 18");
        }

        errors.putAll(new CountryDtoValidator().validate(customerDto.getCountryDto()));

        return errors;
    }
}
