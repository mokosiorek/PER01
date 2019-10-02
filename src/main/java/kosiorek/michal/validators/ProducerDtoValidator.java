package kosiorek.michal.validators;

import kosiorek.michal.dto.ProducerDto;
import kosiorek.michal.validators.generic.AbstractValidator;

import java.util.Map;

public class ProducerDtoValidator extends AbstractValidator<ProducerDto> {

    private static final String MODELREGEX = "[A-Z ]+";

    @Override
    public Map<String, String> validate(ProducerDto producerDto) {
        errors.clear();

        if(producerDto.getName() == null && !producerDto.getName().matches(MODELREGEX)){
            errors.put("producer name", "doesn't match regex");
        }

        errors.putAll(new CountryDtoValidator().validate(producerDto.getCountryDto()));
        errors.putAll(new TradeDtoValidator().validate(producerDto.getTradeDto()));

        return errors;
    }
}
