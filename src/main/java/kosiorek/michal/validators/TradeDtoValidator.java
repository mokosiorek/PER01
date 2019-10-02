package kosiorek.michal.validators;

import kosiorek.michal.dto.TradeDto;
import kosiorek.michal.validators.generic.AbstractValidator;

import java.util.Map;

public class TradeDtoValidator extends AbstractValidator<TradeDto> {

    private static final String MODELREGEX = "[A-Z ]+";

    @Override
    public Map<String, String> validate(TradeDto tradeDto) {
        errors.clear();

        if(tradeDto.getName() == null && !tradeDto.getName().matches(MODELREGEX)){
            errors.put("producer name", "doesn't match regex");
        }

        return errors;
    }
}
