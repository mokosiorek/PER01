package kosiorek.michal.jsonconverters;

import kosiorek.michal.dto.ShopDto;

import java.util.List;

public class ShopsDtoJsonConverter extends JsonConverter<List<ShopDto>> {

    public ShopsDtoJsonConverter(String jsonFilename) { super(jsonFilename);}

}
