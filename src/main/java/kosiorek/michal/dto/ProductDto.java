package kosiorek.michal.dto;

import kosiorek.michal.model.enums.EGuarantee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {

    private Long id;
    private String name;

    private BigDecimal price;
    private CategoryDto categoryDto;
    private ProducerDto producerDto;

    private Set<EGuarantee> eGuarantees;

}
