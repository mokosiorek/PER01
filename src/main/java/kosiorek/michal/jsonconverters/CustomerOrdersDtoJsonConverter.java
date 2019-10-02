package kosiorek.michal.jsonconverters;

import kosiorek.michal.dto.CustomerOrderDto;

import java.util.List;

public class CustomerOrdersDtoJsonConverter extends JsonConverter<List<CustomerOrderDto>> {

    public CustomerOrdersDtoJsonConverter(String jsonFilename) { super(jsonFilename);}

}
