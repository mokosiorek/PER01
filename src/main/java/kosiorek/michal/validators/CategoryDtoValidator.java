package kosiorek.michal.validators;

import kosiorek.michal.dto.CategoryDto;
import kosiorek.michal.validators.generic.AbstractValidator;

import java.util.Map;

public class CategoryDtoValidator extends AbstractValidator<CategoryDto> {

    private static final String MODELREGEX = "[A-Z ]+";

    @Override
    public Map<String, String> validate(CategoryDto categoryDto) {
        errors.clear();

        if(categoryDto.getName()==null && !categoryDto.getName().matches(MODELREGEX)){
            errors.put("category name", "doesn't match regex");
        }

        return errors;
    }
}
