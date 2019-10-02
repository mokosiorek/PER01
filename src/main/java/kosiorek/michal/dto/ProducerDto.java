package kosiorek.michal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProducerDto {

    private Long id;
    private String name;

    private CountryDto countryDto;
    private TradeDto tradeDto;



}
