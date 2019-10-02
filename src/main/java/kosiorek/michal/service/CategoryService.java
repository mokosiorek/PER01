package kosiorek.michal.service;

import kosiorek.michal.dto.CategoryDto;
import kosiorek.michal.exceptions.AppException;
import kosiorek.michal.model.Category;
import kosiorek.michal.repository.CategoryRepository;
import kosiorek.michal.validators.CategoryDtoValidator;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryDto> getAllCategories(){
        return categoryRepository.findAll().stream()
                .map(ModelMapper::fromCategoryToCategoryDto)
                .collect(Collectors.toList());
    }

    public CategoryDto editCategory(CategoryDto categoryDto)
    {
        if(categoryDto == null){
            throw new AppException("editing category - category object null");
        }

        Category category = ModelMapper.fromCategoryDtoToCategory(categoryDto);
        return categoryRepository.addOrUpdate(category)
                .map(ModelMapper::fromCategoryToCategoryDto)
                .orElseThrow(() -> new AppException("editing category - exception while editing category"));

    }

    public CategoryDto addCategory(CategoryDto categoryDto){

        if(categoryDto == null){
            throw new AppException("add category - category object null");
        }

        if(categoryDto.getName()==null){
            throw new AppException("add category - category name object null");
        }

        CategoryDtoValidator categoryDtoValidator = new CategoryDtoValidator();
        Map<String, String> errors = categoryDtoValidator.validate(categoryDto);

        if (categoryDtoValidator.hasErrors()) {
            throw new AppException("add category - category validation not correct " + errors.toString());
        }

       Category category = ModelMapper.fromCategoryDtoToCategory(categoryDto);

        return categoryRepository.addOrUpdate(category)
                .map(ModelMapper::fromCategoryToCategoryDto)
                .orElseThrow(() -> new AppException("add category - exception while adding category"));

    }


}
