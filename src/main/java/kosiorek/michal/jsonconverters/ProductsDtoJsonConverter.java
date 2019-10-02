package kosiorek.michal.jsonconverters;

import kosiorek.michal.dto.ProductDto;

import java.util.List;

public class ProductsDtoJsonConverter extends JsonConverter<List<ProductDto>> {

    public ProductsDtoJsonConverter(String jsonFilename) { super(jsonFilename);}

}
