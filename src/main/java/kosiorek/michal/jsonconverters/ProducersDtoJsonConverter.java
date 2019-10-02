package kosiorek.michal.jsonconverters;

import kosiorek.michal.dto.ProducerDto;

import java.util.List;

public class ProducersDtoJsonConverter extends JsonConverter<List<ProducerDto>> {

    public ProducersDtoJsonConverter(String jsonFilename) { super(jsonFilename);}

}
