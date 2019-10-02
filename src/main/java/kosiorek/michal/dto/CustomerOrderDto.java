package kosiorek.michal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerOrderDto {

    private Long id;
    private LocalDate date;

    private BigDecimal discount;
    private Integer quantity;

    private PaymentDto paymentDto;
    private CustomerDto customerDto;
    private ProductDto productDto;

}
