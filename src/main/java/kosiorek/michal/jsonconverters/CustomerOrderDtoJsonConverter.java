package kosiorek.michal.jsonconverters;

import kosiorek.michal.dto.CustomerOrderDto;
import kosiorek.michal.model.CustomerOrder;

public class CustomerOrderDtoJsonConverter extends JsonConverter<CustomerOrderDto> {

    public CustomerOrderDtoJsonConverter(String jsonFilename) { super(jsonFilename);}

}
