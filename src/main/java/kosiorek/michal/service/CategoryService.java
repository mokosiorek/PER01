package kosiorek.michal.service;

import kosiorek.michal.dto.CategoryDto;
import kosiorek.michal.model.Category;
import kosiorek.michal.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryDto> getAllCategories(){
        return categoryRepository.findAll().stream()
                .map(ModelMapper::fromCategoryToCategoryDto)
                .collect(Collectors.toList());
    }

}
