package kosiorek.michal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockDto {

    private Long id;
    private Integer quantity;

    private ProductDto productDto;
    private ShopDto shopDto;

}
