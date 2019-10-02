package kosiorek.michal.validators;

import kosiorek.michal.dto.CustomerOrderDto;
import kosiorek.michal.validators.generic.AbstractValidator;

import java.math.BigDecimal;
import java.util.Map;

public class CustomerOrderDtoValidator extends AbstractValidator<CustomerOrderDto> {
    @Override
    public Map<String, String> validate(CustomerOrderDto customerOrderDto) {
        errors.clear();

        if (customerOrderDto.getQuantity() < 0) {
            errors.put("customer order quantity", "can't be lower than 0");
        }

        if (customerOrderDto.getDiscount().compareTo(BigDecimal.ZERO) < 0 && customerOrderDto.getDiscount().compareTo(BigDecimal.ONE) > 0) {
            errors.put("customer order discount", "must be between 0 and 1 inclusive");
        }

        errors.putAll(new ProductDtoValidator().validate(customerOrderDto.getProductDto()));
        errors.putAll(new CustomerDtoValidator().validate(customerOrderDto.getCustomerDto()));

        return errors;


    }
}
