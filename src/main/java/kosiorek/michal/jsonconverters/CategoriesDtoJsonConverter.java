package kosiorek.michal.jsonconverters;

import kosiorek.michal.dto.CategoryDto;

import java.util.List;

public class CategoriesDtoJsonConverter extends JsonConverter<List<CategoryDto>> {

    public CategoriesDtoJsonConverter(String jsonFilename) { super(jsonFilename);}

}
