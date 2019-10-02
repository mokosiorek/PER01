package kosiorek.michal.jsonconverters;

import kosiorek.michal.model.Customer;

import java.util.List;

public class CustomersDtoJsonConverter extends JsonConverter<List<Customer>> {

    public CustomersDtoJsonConverter(String jsonFilename) { super(jsonFilename);}

}
