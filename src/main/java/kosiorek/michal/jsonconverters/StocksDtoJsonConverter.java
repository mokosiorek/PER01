package kosiorek.michal.jsonconverters;

import kosiorek.michal.dto.StockDto;

import java.util.List;

public class StocksDtoJsonConverter extends JsonConverter<List<StockDto>> {
    public StocksDtoJsonConverter(String jsonFilename) { super(jsonFilename);}
}
