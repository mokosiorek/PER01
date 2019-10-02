package kosiorek.michal.jsonconverters;

import kosiorek.michal.dto.CountryDto;

import java.util.List;

public class CountriesDtoJsonConverter extends JsonConverter<List<CountryDto>> {

    public CountriesDtoJsonConverter(String jsonFilename) { super(jsonFilename);}

}
