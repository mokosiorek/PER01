package kosiorek.michal.jsonconverters;

import kosiorek.michal.dto.TradeDto;

import java.util.List;

public class TradesDtoJsonConverter extends JsonConverter<List<TradeDto>> {
    public TradesDtoJsonConverter(String jsonFilename) { super(jsonFilename);}
}
